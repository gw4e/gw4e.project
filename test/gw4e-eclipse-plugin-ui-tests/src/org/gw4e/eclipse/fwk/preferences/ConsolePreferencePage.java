package org.gw4e.eclipse.fwk.preferences;

/*-
 * #%L
 * gw4e
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 gw4e-project
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.fwk.conditions.ShellActiveCondition;
import org.gw4e.eclipse.fwk.platform.GW4EPlatform;
import org.gw4e.eclipse.launching.test.OSUtils;
import org.hamcrest.Matcher;

public class ConsolePreferencePage {

	static SWTWorkbenchBot bot;
	SWTBotShell shell;

	public ConsolePreferencePage(SWTWorkbenchBot wbot) {
		bot = wbot;
	}
	
	public void unlimitoutput () {
		shell = showPreferenceDialog();
		showConsolePreference(shell);
	}
	
	private void showConsolePreference(SWTBotShell shell) {
		SWTBotTree tree = bot.tree().select("Run/Debug");
		tree.expandNode("Run/Debug", true);
		 
		SWTBotTreeItem[] items = tree.getAllItems();
		for (SWTBotTreeItem swtBotTreeItem : items) {
			if (swtBotTreeItem.getText().equalsIgnoreCase("Run/Debug")) {
				swtBotTreeItem.getNode("Console").select();
			}
		}
		
		try {
			int max = 10;
			for (int i = 0; i < max; i++) {
				SWTBotCheckBox button = bot.checkBox(i);
				if (button.getText().equalsIgnoreCase("&Limit console output")) {
					if (button.isChecked()) {
						button.click();
						break;
					}
				}
			} 
		} catch (Exception e) {
		}
		
		String name = "OK";
		if (GW4EPlatform.isEclipse47()) name = "Apply and Close";
		bot.button(name).click();
	}
	
	private SWTBotShell showPreferenceDialog() {
		if (OSUtils.isWindows() || OSUtils.isLinux()) {
			return showPreferenceDialogWindowPreference();
		} else {
			return showPreferenceDialogMAC() ;
		}
	}
	
	private SWTBotShell showPreferenceDialogWindowPreference() {
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				try {
					bot.menu("Window").menu("Preferences").click();
					bot.waitUntil(new ShellActiveCondition("Preferences"), 5 * 1000);
					return true;
				} catch (Throwable e) {
				} 
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot open the Preference page";
			}
		};
		bot.waitUntil(condition, 30 * 1000);
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		return shell;	
	}
	
	private SWTBotShell showPreferenceDialogMAC() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Menu appMenu = workbench.getDisplay().getSystemMenu();
					for (MenuItem item : appMenu.getItems()) {
						if (item.getText().startsWith("Preferences")) {
							Event event = new Event();
							event.time = (int) System.currentTimeMillis();
							event.widget = item;
							event.display = workbench.getDisplay();
							item.setSelection(true);
							item.notifyListeners(SWT.Selection, event);
							break;
						}
					}
				}
			}
		});
		return  getPreferenceDialog() ;
	}

	private SWTBotShell getPreferenceDialog() {
		Matcher<Shell> matcher = WidgetMatcherFactory.withText("Preferences");
		bot.waitUntil(Conditions.waitForShell(matcher));
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		return shell;
	}
	 
}
