package org.gw4e.eclipse.fwk.run;

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

import static org.junit.Assert.assertTrue;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.view.ConsoleView;
import org.hamcrest.Matcher;

public abstract class AbstractRunner  {

	SWTWorkbenchBot bot;
	
	public AbstractRunner(SWTWorkbenchBot bot) {
		super();
		this.bot = bot;
	}

	
	protected abstract String getLauncherName ();
	
	protected   String getConsoleText(SWTWorkbenchBot bot) {
		SWTBotView console = waitForConsoleBeDisplayed ();
		SWTBotStyledText textWidget = console.bot().styledText();
		return textWidget.getText();
	}

	protected SWTBotShell openRun () {
		bot.menu("Run").menu("Run Configurations...").click();
		bot.waitUntil(Conditions.shellIsActive("Run Configurations"));
		SWTBotShell shell = bot.shell("Run Configurations");
		SWTBotTreeItem item = shell.bot().tree().getTreeItem(getLauncherName()).select();
		item.select();
		return shell;
	}
	
	
	private static Matcher<Widget> getConsoleMatcher () {
		Matcher<Widget> console = WidgetMatcherFactory.withMnemonic("Console");
		return console;
	}
	
	private   SWTBotView  waitForConsoleBeDisplayed () {
		getConsoleView(bot);
		Matcher<Widget> console = getConsoleMatcher () ;
		bot.waitUntil(Conditions.waitForWidget(console));
		SWTBotView consoleView = getConsoleView(bot);
		consoleView.setFocus();
		return consoleView;
	}
	
	public SWTBotView getConsoleView(SWTWorkbenchBot bot) { 
		ConsoleView cv = ConsoleView.open(bot);
		return cv.getBotView();
	} 
	
	
	private void  waitForRunCompleted (String configurationName, long timeout) {
		 
		bot.waitUntil(new ICondition (){
			@Override
			public boolean test() throws Exception {
				ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
				for (ILaunch launch : launchManager.getLaunches()) { 
					ILaunchConfiguration config = launch.getLaunchConfiguration();
					if (config.getName().equalsIgnoreCase(configurationName)) {
						return launch.isTerminated();
					}
				}
				return false;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "run not completed.";
			}
		},timeout);
	}
	
	public void  waitForRunCompleted (String[] expectations , long timeout) {
		 
		
		bot.waitUntil(new ICondition (){
			public boolean test() throws Exception {
				String result = getConsoleText(bot);
				int count=0;
				for (int i = 0; i < expectations.length; i++) {
					if (result.indexOf(expectations[i]) != -1)  count++;
				}
				return expectations.length == count;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "run not completed or did not get the expected result on time";
			}
		},timeout);
	}
	
	
	public void run (String configurationName, long timeout) {
		SWTBotShell shell = openExistingRun (configurationName);
		
		SWTBotButton runButton = shell.bot().button("Run");
		runButton.click();

		bot.waitUntil(Conditions.shellCloses(shell));	
		
		waitForConsoleBeDisplayed();
		 
		waitForRunCompleted (configurationName,timeout);
	}
	
	public void validateRunResult (String []  expectations) {
		 String result = getConsoleText(bot);
		 for (int i = 0; i < expectations.length; i++) {
			 if (expectations[i].trim().length()==0) continue;
			assertTrue("Result does not contain " + expectations[i] , (result.indexOf(expectations[i]) != -1));
		}
	}

	protected abstract ICondition getWaitConditionWhenOpeningConfiguration () ;
	 
	protected SWTBotShell openExistingRun (String configurationName) {
		SWTBotShell shell = openRun ();
		SWTBotTreeItem item = shell.bot().tree().getTreeItem(this.getLauncherName()).select();
		item.expand().select(configurationName);
		bot.waitUntil(getWaitConditionWhenOpeningConfiguration ());
		return shell;
		 
	}
}
