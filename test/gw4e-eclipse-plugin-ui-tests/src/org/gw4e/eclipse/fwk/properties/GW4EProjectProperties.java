package org.gw4e.eclipse.fwk.properties;

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
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.property.ProjectPropertyPage;
import org.gw4e.eclipse.property.checkbox.LabelizedCheckBoxes;
import org.gw4e.eclipse.property.text.LabelizedTexts;

public class GW4EProjectProperties {
	SWTBotShell shell;
	SWTWorkbenchBot parentBot;
	public GW4EProjectProperties(SWTWorkbenchBot parentBot,SWTBotShell shell) {
		this.shell=shell;
		this.parentBot=parentBot;
	}
	
	public void apply ( ) {
		SWTBotButton button = shell.bot().button("Apply");
		button.click();
	}

	public void ok ( ) {
		SWTBotButton button = shell.bot().button("OK");
		button.click();
		parentBot.waitUntil(Conditions.shellCloses(shell));	
	}
	
	public void cancel ( ) {
		SWTBotButton button = shell.bot().button("Cancel");
		button.click();
		parentBot.waitUntil(Conditions.shellCloses(shell));	
	}
	
	private SWTBotTable getPerformanceConfigurationTable () {
		SWTBotTable table = shell.bot().tableInGroup("Performance Configuration");
		return table;
	}
	
	private SWTBotTable getBuildPoliciesTable () {
		SWTBotTable table = shell.bot().tableInGroup("Build Default Policies");
		return table;
	}
	
	public boolean hasGenerator (String value) {
		SWTBotTable table = getBuildPoliciesTable();
		int max  = table.rowCount();
		for (int i = 0; i < max; i++) {
			SWTBotTableItem item = table.getTableItem(i);
			if (value.equalsIgnoreCase(item.getText())) {
				return true;
			}
		}
		return false;
	}
	
	public void replaceGenerator (String oldValue,String newValue) {
		SWTBotTable table = getBuildPoliciesTable();
		int max  = table.rowCount();
		for (int i = 0; i < max; i++) {
			SWTBotTableItem item = table.getTableItem(i);
			int j = i;
			if (item.getText().equals(oldValue)) {
				ICondition condition = new DefaultCondition () {
					@Override
					public boolean test() throws Exception {
						table.click(j, 0);
						shell.bot().sleep(1000);
						shell.bot().text(oldValue, 0).setText(newValue);
						table.click(j, 0);
						SWTBotTable table = getBuildPoliciesTable();
						SWTBotTableItem current = table.getTableItem(j);
						return current.getText().equals(newValue);
					}

					@Override
					public String getFailureMessage() {
						return "failed to update the generator " + oldValue;
					}
				};
				shell.bot().waitUntil(condition);
				break;
			}
		}
	}
	
	public void updateBuildPoliciesFileName (String value) {
		SWTBotText text = shell.bot().textWithId(LabelizedTexts.PROJECT_PROPERTY_PAGE_WIDGET_ID, LabelizedTexts.TEXT + "." + ProjectPropertyPage.TEXTUAL_PROPERTIES + "." + 0);
		text.setText(value);
	}

	public String getBuildPoliciesFileName () {
		SWTBotText text = shell.bot().textWithId(LabelizedTexts.PROJECT_PROPERTY_PAGE_WIDGET_ID, LabelizedTexts.TEXT + "." + ProjectPropertyPage.TEXTUAL_PROPERTIES + "." + 0);
		return text.getText();
	}
	
	public void toggleBuildButton () {
		SWTBotCheckBox button = shell.bot().checkBoxWithId(LabelizedCheckBoxes.PROJECT_PROPERTY_PAGE_WIDGET_ID, LabelizedCheckBoxes.BUTTON + ".0");
		button.click();
	}
	
	public void toggleSynchronizationButton () {
		SWTBotCheckBox button = shell.bot().checkBoxWithId(LabelizedCheckBoxes.PROJECT_PROPERTY_PAGE_WIDGET_ID, LabelizedCheckBoxes.BUTTON + ".3");
		button.click();
	}
	
	public void togglePerformanceLoggingButton () {
		SWTBotCheckBox button = shell.bot().checkBoxWithId(LabelizedCheckBoxes.PROJECT_PROPERTY_PAGE_WIDGET_ID, LabelizedCheckBoxes.BUTTON + ".2");
		button.click();
	}
}
