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

import static org.junit.Assert.assertEquals;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.launching.offline.GW4ELaunchConfigurationTab;
import org.gw4e.eclipse.message.MessageUtil;

public class GW4EOfflineRunner extends AbstractRunner {

	
	public GW4EOfflineRunner(SWTWorkbenchBot bot) {
		super(bot);
	}

	protected String getLauncherName () {
		return "GW4E Offline Launcher";
	}
	

 
	
	public void validateRun (String configurationName,String projectName,String filepath,boolean printUnvisited,boolean verbose,String startElement,String generator) {
		SWTBotShell shell = openExistingRun (configurationName);
		
		SWTBotText projectText = shell.bot().textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT);
		assertEquals("Wrong project name",projectName,projectText.getText());
		
		SWTBotText modelText = shell.bot().textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL);
		assertEquals("Wrong model name",filepath,modelText.getText());
		
		SWTBotCheckBox verboseButton  = shell.bot().checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_VERBOSE);
		assertEquals("Wrong verbose value",verbose,verboseButton.isChecked());
		
		SWTBotCheckBox unvisitedButton  = shell.bot().checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_PRINT_UNVISITED);
		assertEquals("Wrong verbose value",verbose,unvisitedButton.isChecked());
		
		SWTBotText startElementText = shell.bot().textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT);
		assertEquals("Wrong start element",startElement,startElementText.getText());
		
		SWTBotCombo generatorCombo = shell.bot().comboBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_COMBO_PATH_GENERATOR_ID_MODEL);
		assertEquals("Wrong generator value",generator,generatorCombo.getText());
		
		SWTBotButton closeButton = bot.button("Close");
		closeButton.click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
	}
	
	public void addOfflineRun (String configurationName,String projectName,String filepath,boolean printUnvisited,boolean verbose,String startElement,String generator) {
		SWTBotShell shell = openRun ();
		
		SWTBot bot = shell.bot();
		SWTBotTreeItem item = bot.tree().getTreeItem(getLauncherName()).select();
		
		item.click();
		item.contextMenu("New").click();
		bot.tree().getTreeItem(getLauncherName()).expand();
		
		
		bot.textWithLabel("Name:").setText(configurationName);
		
		SWTBotButton projectButton = bot.buttonWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_BROWSER_BUTTON_ID_PROJECT);
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
		
		SWTBotButton modelButton = bot.buttonWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD);
		modelButton.click();
		
		bot.waitUntil(Conditions.shellIsActive(MessageUtil.getString("methoddialog_title")));
		shell = bot.shell(MessageUtil.getString("methoddialog_title"));
		
		SWTBotText methodText = shell.bot().textWithLabel("Select a file").setText(filepath);
		bot.waitUntil(new ICondition (){
			@Override
			public boolean test() throws Exception {
				return methodText.getText().equalsIgnoreCase(filepath);
			}

			@Override
			public void init(SWTBot bot) {

			}

			@Override
			public String getFailureMessage() {
				return "File name not set";
			}
		});
		
		okButton  = shell.bot().button("OK");
		okButton.click();
	
		bot.waitUntil(Conditions.shellCloses(shell));
		
		shell = bot.shell("Run Configurations");
		if (printUnvisited) {
			SWTBotCheckBox unvisitedButton  = shell.bot().checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_PRINT_UNVISITED);
			unvisitedButton.click();
			bot.waitUntil(new ICondition (){
				@Override
				public boolean test() throws Exception {
					return unvisitedButton.isChecked();
				}

				@Override
				public void init(SWTBot bot) {

				}

				@Override
				public String getFailureMessage() {
					return "Unvisited Button name not checked";
				}
			});
		}
		
		if (verbose) {
			SWTBotCheckBox verboseButton  = shell.bot().checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_VERBOSE);
			verboseButton.click();
			bot.waitUntil(new ICondition (){
				@Override
				public boolean test() throws Exception {
					return verboseButton.isChecked();
				}

				@Override
				public void init(SWTBot bot) {

				}

				@Override
				public String getFailureMessage() {
					return "Verbose Button name not checked";
				}
			});
		}
		
		SWTBotText startElementText = shell.bot().textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT);
		startElementText.setText(startElement);
		bot.waitUntil(new ICondition (){
			@Override
			public boolean test() throws Exception {
				return startElementText.getText().equalsIgnoreCase(startElement);
			}

			@Override
			public void init(SWTBot bot) {

			}

			@Override
			public String getFailureMessage() {
				return "Start Element name not set";
			}
		});
		
		
		ModelPathGeneratorHelper helper = new ModelPathGeneratorHelper(bot);
		
		selectComboPathGenerator (generator);
		
		SWTBotButton applyButton = shell.bot().button("Apply");
		applyButton.click();
		
		SWTBotButton closeButton = shell.bot().button("Close");
		closeButton.click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
	}

	@Override
	protected ICondition getWaitConditionWhenOpeningConfiguration() {
		ICondition condition = Conditions.waitForWidget(
				WidgetMatcherFactory.withId(
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT));
		return condition;
	}
	
	private void selectComboPathGenerator (String path) {
		DefaultCondition condition = new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				try {
 
					SWTBotCombo combo = bot.comboBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_COMBO_PATH_GENERATOR_ID_MODEL);
					combo.setSelection(path);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			public String getFailureMessage() {
				return "Unable to open the path generator combo";
			}
		};
		bot.waitUntil(condition);
	}
}

 
