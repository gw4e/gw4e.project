package org.gw4e.eclipse.fwk.run;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.fwk.conditions.ResourceExists;
import org.gw4e.eclipse.fwk.source.ImportHelper;
import org.gw4e.eclipse.launching.runasmanual.GW4ELaunchConfigurationTab;

public class GW4EManualRunner extends AbstractRunner {

	
	public GW4EManualRunner(SWTWorkbenchBot bot) {
		super(bot);
	}

	protected String getLauncherName () {
		return "GW4E Manual Test Launcher";
	}

	@Override
	protected ICondition getWaitConditionWhenOpeningConfiguration() {
		ICondition condition = Conditions.waitForWidget(
				WidgetMatcherFactory.withId(
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
						GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE));
		return condition;
	}
	
	public SWTBotShell run (String configurationName) {
		SWTBotShell[] shells = new SWTBotShell[1];
		ICondition condition = new DefaultCondition () {

			@Override
			public boolean test() throws Exception {
				try {
					openExistingRun(configurationName);
					SWTBotButton button = bot.button("Run");
					button.click();
					bot.waitUntil(Conditions.shellIsActive("Run As Manual"));
					shells[0] =  bot.shell("Run As Manual").activate() ;
					return true;
				} catch (Throwable t) {
				} finally {
					try {
						SWTBotButton button = bot.button("Close");
						button.click();
					} catch (Throwable e) {
					}
				}
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "Inable to run " + configurationName;
			}
		};
		bot.waitUntil(condition);
		return shells[0];
	}
	public void  addRun (String configurationName,String projectName,String mainContext,String [] otherContexts,String pathgenerator,boolean omitedgeswithoutdescription,boolean removeblockedElement,String startElement) {
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
		
		setProjectName (projectName);
		setModel (mainContext);
		setPathGenerator (pathgenerator);
		setOmitOption (omitedgeswithoutdescription);
		setRemoveBlockedElement (removeblockedElement);
		
		SWTBotButton applyButton = bot.button("Apply");
		applyButton.click();
		
		SWTBotButton closeButton = bot.button("Close");
		closeButton.click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
		

	}	

	private void setProjectName (String projectName) {
		SWTBotText text = bot.textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT);
		text.setText(projectName);
	}
 
	private void setModel (String model) {
		SWTBotText text = bot.textWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL);
		text.setText(model);
	}
	 
	private void setPathGenerator (String pathGenerator) {
		ICondition condition = new DefaultCondition () {

			@Override
			public boolean test() throws Exception {
				try {
					SWTBotCombo combo = bot.comboBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_COMBO_PATH_GENERATOR);
					combo.setSelection(pathGenerator);
				} catch (Exception e) {
					return false;
				}
				return true;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to set " + pathGenerator+ " in the combo";
			}
		};
		bot.waitUntil(condition);
	}
	
	private void setOmitOption (boolean value) {
		SWTBotCheckBox button = bot.checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE);
		if (value && !button.isChecked()) {
			button.select();
		} else {
			if (!value && button.isChecked()) {
				button.deselect();
			}
		}
	}
	
	private void setRemoveBlockedElement (boolean value) {
		SWTBotCheckBox button = bot.checkBoxWithId(GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4ELaunchConfigurationTab.GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS);
		if (value && !button.isChecked()) {
			button.select();
		} else {
			if (!value && button.isChecked()) {
				button.deselect();
			}
		}
	}
	
	public IFile importJSONModel (String sourceFile,String targetFolder) throws IOException, CoreException {
		URL url = this.getClass().getClassLoader().getResource(sourceFile);
		File f = new File(FileLocator.toFileURL(url).getPath());
		IContainer container  = (IContainer) ResourceManager.getResource(targetFolder);
		ImportHelper.copyFile(f, container);
		IPath p = new Path(targetFolder).append(sourceFile);
		ICondition condition = new ResourceExists (p);
		bot.waitUntil(condition);
		return (IFile) ResourceManager.getResource(p.toString());
	}
}
