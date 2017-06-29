package org.gw4e.eclipse.fwk.staticgenerator;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.conditions.ShellActiveCondition;
import org.gw4e.eclipse.wizard.convert.FolderSelectionGroup;
import org.gw4e.eclipse.wizard.staticgenerator.ExecutionContextSelectionUIPage;
import org.gw4e.eclipse.wizard.staticgenerator.GeneratorResourceUIPage;
import org.gw4e.eclipse.wizard.staticgenerator.GeneratorToFileCreationWizard;
import org.gw4e.eclipse.wizard.staticgenerator.GraphElementSelectionUIPage;

public class StaticGeneratorWizard {
	SWTBot bot;
	static String SHELL_TITLE = "GW4E Generator File";
	public StaticGeneratorWizard(SWTBot bot) {
		super();
		this.bot = bot;
		bot.waitUntil(new ShellActiveCondition(SHELL_TITLE), 5 * 1000);
	}

	public static void open (IFile file, List<String> ids) {
		Display.getDefault().asyncExec(new Runnable () {
			@Override
			public void run() {
				StructuredSelection sel = new StructuredSelection(new Object[] { file , ids });
				GeneratorToFileCreationWizard.open(sel);
			}
		});
	}
	
	public void enterDestination (String targetFilename, String targetFilenamefullpath, String  ... nodeToExpand) {
		ICondition nodeSelectedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				try {
					SWTBotTree tree = bot.treeWithId(FolderSelectionGroup.GW4E_CONVERSION_TREE,FolderSelectionGroup.GW4E_CONVERSION_TREE);
					if (tree.isEnabled()) {
						tree.setFocus();
						SWTBotTreeItem item = tree.expandNode(nodeToExpand);
						item.setFocus();
						item.select();
					}
					return true;
				} catch (Throwable e) {
					return false;
				}
			}

			@Override
			public String getFailureMessage() {
				String nodeMsg = "";
				for (int i = 0; i < nodeToExpand.length; i++) {
					nodeMsg = nodeMsg + "/" + nodeToExpand[i] ;
				}
				String msg = "Unable to  expand nodes " + nodeMsg ;
				return msg;
			}
		};
		
		bot.waitUntil(nodeSelectedCondition);
		
		SWTBotText textFile = bot.textWithLabel("Convert to file");
		if(textFile.isEnabled()) {
			textFile.setText(targetFilename);
		}
		
		textFile = bot.textWithLabel("Output file");
		 
		String text = textFile.getText();
		org.junit.Assert.assertEquals (targetFilenamefullpath, text);
	}
	
	public void assertTargetElements(String... names) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_TARGET_TABLE_ID);
		for (String name : names) {
			int index = table.indexOf(name, 0);
			if (index == -1)
				org.junit.Assert.fail(name + " not found in the target column");
		}
	}

	public void assertSourceElements(String... names) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_SOURCE_TABLE_ID);
		for (String name : names) {
			int index = table.indexOf(name, 0);
			if (index == -1)
				org.junit.Assert.fail(name + " not found in the source column");
		}
	}

	public void assertExtensionValue(int index,String value) {
		SWTBotCombo combo = bot.comboBoxWithId(ExecutionContextSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				ExecutionContextSelectionUIPage.GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST);
		String[] items = combo.items();
		try {
			org.junit.Assert.assertEquals (value, items[index]);
		} catch (Exception ex) {
			String all = "";
			for (String string : items) {
				all = all + string + ",";
			}
			org.junit.Assert.fail(" Cannot find " + value + " in " + all);
		}
	}

	public void moveSourceToTarget(String value) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_SOURCE_TABLE_ID);
		table.select(value);
		bot.button("Add").click();
	}

	public void assertErrorMessage (String expected) {
		int index=0;
		try {
			while (true) {
				String text = bot.text(index).getText();
				System.out.println(text );
				if (expected.trim().equals(text.trim()))  {
					break;
				}
				index++;
			}
		} catch (Exception e) {
			org.junit.Assert.fail(" Cannot find " + expected );
		}
	}
	
	public void moveTargetToSource(String value) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_TARGET_TABLE_ID);
		table.select(value);
		bot.button("Remove").click();		
	}
	
	public void moveUp(String value) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_TARGET_TABLE_ID);
		table.select(value);
		bot.button("Up").click();		
	}
	
	public void moveDown(String value) {
		SWTBotTable table = bot.tableWithId(GraphElementSelectionUIPage.GW4E_CONVERSION_WIDGET_ID,
				GraphElementSelectionUIPage.GW4E_CONVERSION_TARGET_TABLE_ID);
		table.select(value);
		bot.button("Down").click();		
	}
	
	public void next() {
		bot.button("Next >").click();
	}
	
	public void finish() {
		bot.button("Finish").click();
	}
	
	public void assertFinishEnabled() {
		boolean b = bot.button("Finish").isEnabled();
		org.junit.Assert.assertTrue (b);
	}
	
	public void assertNextEnabled() {
		boolean b = bot.button("Next >").isEnabled();
		org.junit.Assert.assertTrue (b);
	}
	
	public void checkEraseExistingFile() {
		SWTBotCheckBox b = bot.checkBoxWithId(GeneratorResourceUIPage.GW4E_GENERATOR_CHOICE_CHECKBOX_ID, GeneratorResourceUIPage.GW4E_GENERATOR_CHOICE_ERASE_CHECKBOX);
		b.click();
	}
	 
}
