package org.gw4e.eclipse.fwk.conversion;

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

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.gw4e.eclipse.wizard.convert.page.JUnitGW4ETestUIPage;

public class JUnitGraphWalkerTestUIPageTest {
	SWTBot bot;
	SWTBotShell shell;
	
	public JUnitGraphWalkerTestUIPageTest(SWTBotShell shell) {
		super();
		this.shell = shell;
		this.bot = shell.bot();
	}
	
	public void nextPage () {
		 
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				SWTBotButton fbutton  = bot.button("Next >");
				return fbutton.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return " Next button not enabled";
			}
		});
		SWTBotButton fbutton  = bot.button("Next >");
		fbutton.click();
	}
	
	public void completeFullPage (String vertex,String edge,String [] contexts) {
		allowJUnitSmokeTest ();
		allowJUnitFunctionalTest ();
		allowJUnitStabilityTest ();
		selectTargetVertex (vertex);
		selectStartElement (edge);
		allowHint();
		selectAdditionalContext (contexts);
	}
	
 
	
	public void allowJUnitSmokeTest () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_CONVERSION_CHOICE_GENERATE_RUN_SMOKE_TEST_CHECKBOX);
		checkButton.click();
	}	
	public void allowJUnitFunctionalTest () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_CONVERSION_CHOICE_GENERATE_RUN_FUNCTIONNAL_TEST_CHECKBOX);
		checkButton.click();
	}	
	public void allowJUnitStabilityTest () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_CONVERSION_CHOICE_GENERATE_RUN_STABILITY_TEST_CHECKBOX);
		checkButton.click();
	}	
	public void selectTargetVertex (String vertex) {
		SWTBotCombo combo = bot.comboBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_CONVERSION_COMBO_TARGET_ELEMENT); 
		combo.setSelection(vertex);
	}
	public void selectStartElement (String edge) {
		SWTBotCombo combo = bot.comboBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_CONVERSION_COMBO_START_ELEMENT); 
		combo.setSelection(edge);
	}
	public void allowHint () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, JUnitGW4ETestUIPage.GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON);
		checkButton.click();
	}
	
	public void selectAdditionalContext (String [] additionalsContext) {
		SWTBotTable table = bot.tableWithId(JUnitGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID,JUnitGW4ETestUIPage.GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_CONTEXT);
		int count = table.rowCount();
		for (String context : additionalsContext) {
			for (int i = 0; i < count; i++) {
				SWTBotTableItem item = table.getTableItem(i);
				int cols = table.columnCount();
				for (int j = 0; j < cols; j++) {
					System.out.println(context);
					System.out.println(item.getText(j));
					if (item.getText(j).equals(context)) {
						item.check();
					};
				}
			}			
		}
	}
}
