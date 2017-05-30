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

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.launching.test.GW4ELaunchConfigurationTab;
 

public class GW4ETestRunner extends AbstractRunner {

	
	public GW4ETestRunner(SWTWorkbenchBot bot) {
		super(bot);
	}

	protected String getLauncherName () {
		return "GW4E Test Launcher";
	}
	
	
	public void addRun (String configurationName,String projectName,String mainContext,String [] otherContexts) {
		SWTBotShell shell = openRun ();
		
		SWTBot bot = shell.bot();
		SWTBotTreeItem item = bot.tree().getTreeItem(getLauncherName()).select();
		
		item.click();
		item.contextMenu("New").click();
		bot.tree().getTreeItem(getLauncherName()).expand();
		
		bot.waitUntil(new ICondition (){
			@Override
			public boolean test() throws Exception {
				return bot.textWithLabel("Name:")!=null;
			}

			@Override
			public void init(SWTBot bot) {

			}

			@Override
			public String getFailureMessage() {
				return "Configuration dialog not found";
			}
		});
		bot.textWithLabel("Name:").setText(configurationName);
		
		SWTBotButton projectButton = bot.buttonWithId(
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, 
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT_BUTTON);
		projectButton.click();
	
		bot.waitUntil(Conditions.shellIsActive("GW4E projects"));
		shell = bot.shell("GW4E projects");
		
		SWTBotText projectText = shell.bot().textWithLabel("Select a project").setText(projectName);
		bot.waitUntil(new ICondition (){
			@Override
			public boolean test() throws Exception {
				return projectText.getText().equalsIgnoreCase(projectName);
			}

			@Override
			public void init(SWTBot bot) {

			}

			@Override
			public String getFailureMessage() {
				return "Project name not set";
			}
		});
		
		SWTBotButton okButton  = shell.bot().button("OK");
		okButton.click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
		
		
		bot.waitUntil(new ICondition () {

			@Override
			public boolean test() throws Exception {
				SWTBotCombo mainTestCombo = bot.comboBoxWithId(
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, 
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_MAIN_TEST);
				return mainTestCombo!=null;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Main test combo not found";
			}
			
		});
		
		SWTBotCombo mainTestCombo = bot.comboBoxWithId(
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, 
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_MAIN_TEST);
		mainTestCombo.setSelection(mainContext);
		
		
		SWTBotCheckBox hintButton = bot.checkBoxWithId(
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, 
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON);
		hintButton.click();
		
		
		SWTBotTable additionaltests = bot.tableWithId(
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_TEST);
		bot.waitUntil(new ICondition () {

			@Override
			public boolean test() throws Exception {
				return additionaltests.rowCount() == otherContexts.length;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Wrong number of Additional tests";
			}
		});		
		
		int max = additionaltests.rowCount();
		String [] actual = new String [max];
		for (int i = 0; i <  max; i++) {
			SWTBotTableItem ti = additionaltests.getTableItem(i);
			String text  = ti.getText();
			actual[i] = text;
		}

		org.junit.Assert.assertArrayEquals( otherContexts, actual);
		
		
		SWTBotButton selectAllButton = bot.buttonWithId(
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, 
				GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_SELECTALL_BUTTON);
		selectAllButton.click();
		 
		bot.waitUntil(new ICondition () {
			@Override
			public boolean test() throws Exception {
				int count = 0;
				for (int i = 0; i <  max; i++) {
					SWTBotTableItem ti = additionaltests.getTableItem(i);
					if (ti.isChecked()) count++;
				}
				return count==otherContexts.length;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				for (int i = 0; i <  max; i++) {
					SWTBotTableItem ti = additionaltests.getTableItem(i);
					System.out.println(ti.getText() + " " + ti.isChecked());
				}
				return "Sounds like 'Select All' button did not select all items... Wrong number of checked Additional tests";
			}
		});	
		
		SWTBotButton applyButton = bot.button("Apply");
		applyButton.click();
		
		SWTBotButton closeButton = bot.button("Close");
		closeButton.click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
	}

	@Override
	protected ICondition getWaitConditionWhenOpeningConfiguration() {
		ICondition condition = Conditions.waitForWidget(
				WidgetMatcherFactory.withId(
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT));
		return condition;
	}
}

 
