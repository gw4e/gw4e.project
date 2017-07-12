package org.gw4e.eclipse.fwk.run;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.gw4e.eclipse.launching.ui.ModelData;
import org.gw4e.eclipse.launching.ui.ModelPathGenerator;

public class ModelPathGeneratorHelper {
	SWTBot bot;
	public ModelPathGeneratorHelper(SWTBot bot) {
		 this.bot=bot;
	}

	private SWTBotTable getTable () {
		SWTBotTable table = bot.tableWithId(ModelPathGenerator.GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_ID, ModelPathGenerator.GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_TABLE);
		return table;
	}
	
	public void setSelectionFileStatus (String path,boolean checked) {
		SWTBotTable table= getTable ();
		int max=table.rowCount();
		for (int i = 0; i < max; i++) {
			SWTBotTableItem tableItem = table.getTableItem(i);
			ModelData d =  (ModelData) tableItem.widget.getData();
			if (d.getName().equalsIgnoreCase(path)) {
				tableItem.widget.setChecked(checked);
			}
		}
	}
	
	public void selectPathGenerator (String file,String path) {
		SWTBotTable table= getTable ();
		int max=table.rowCount();
		for (int i = 0; i < max; i++) {
			SWTBotTableItem tableItem = table.getTableItem(i);
			ModelData d =  (ModelData) tableItem.widget.getData();
			if (d.getName().equalsIgnoreCase(file)) {
				selectComboPathGenerator(tableItem,path);
			}
		}		
	}
	
	private void selectComboPathGenerator (SWTBotTableItem tableItem,String path) {
		DefaultCondition condition = new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				try {
					tableItem.click(1);
					SWTBotCCombo combo = bot.ccomboBoxWithId(ModelPathGenerator.GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_ID,ModelPathGenerator.GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_COMBO_EDITOR);
					combo.setSelection(path);
					return true;
				} catch (Exception e) {
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
