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
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.wizard.convert.page.BuildPoliciesCheckboxTableViewer;
import org.gw4e.eclipse.wizard.convert.page.GeneratorChoiceComposite;
import org.gw4e.eclipse.wizard.convert.page.OfflineGW4ETestUIPage;

public class OfflineTestUIPageTest {
	SWTBot bot;
	SWTBotShell shell;
	
	public OfflineTestUIPageTest(SWTBotShell shell) {
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
	
	public void finish () {
		SWTBotButton button = bot.button("Finish");
		button.click();
	}
	
	public void selectTimeout (String timeout) {
		SWTBotText text  = bot.textWithId(OfflineGW4ETestUIPage.GW4E_CONVERSION_WIDGET_ID, OfflineGW4ETestUIPage.GW4E_OFFLINE_TIMEOUT_TEXT);
		text.setText(timeout);
	}
	
	public void selectGenerators (String [] generators) {
		SWTBotTable table = bot.tableWithId(BuildPoliciesCheckboxTableViewer.GW4E_CONVERSION_WIDGET_ID, BuildPoliciesCheckboxTableViewer.GW4E_CONVERSION_TABLE_GENERATORS);
		int max = table.rowCount();
		for (int i = 0; i < max; i++) {
			SWTBotTableItem item = table.getTableItem(i);
			for (String generator : generators) {
				if (generator.equalsIgnoreCase(item.getText())) {
					item.check();
				}
			}
		}
	}
	
	public void selectStandAloneMode (String classname) {
		SWTBotRadio radio = bot.radioWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_NEWCLASS_CHECKBOX);
		radio.click();
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotText text  = bot.textWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID,GeneratorChoiceComposite.GW4E_NEWCLASS_TEXT);	
				return text.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				// TODO Auto-generated method stub
				return "Text not enabled";
			}
			
		};
		bot.waitUntil(condition);
		SWTBotText text  = bot.textWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID,GeneratorChoiceComposite.GW4E_NEWCLASS_TEXT);
		text.setText(classname);
	}
	
	public void selectExtendedMode (String ancestor,String classname) {
		SWTBotRadio radio = bot.radioWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_EXTEND_CHECKBOX);
		radio.click();
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotCombo combo = bot.comboBoxWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST); 
				return combo.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "Combo not enabled";
			}
			
		};
		bot.waitUntil(condition);
		SWTBotCombo combo = bot.comboBoxWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST); 
		combo.setSelection(ancestor);	
		
		SWTBotText text  = bot.textWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID,GeneratorChoiceComposite.GW4E_EXTEND_CLASS_TEXT);
		text.setText(classname);
	}
	 
	 
	public void selectAppendMode (String classname) {
		SWTBotRadio radio = bot.radioWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_APPEND_CHECKBOX);
		radio.click();
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotCombo combo = bot.comboBoxWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_CONVERSION_COMBO_ANCESTOR_APPEND_TEST); 
				return combo.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				// TODO Auto-generated method stub
				return "Combo not enabled";
			}
			
		};
		bot.waitUntil(condition);
		SWTBotCombo combo = bot.comboBoxWithId(GeneratorChoiceComposite.GW4E_CONVERSION_WIDGET_ID, GeneratorChoiceComposite.GW4E_CONVERSION_COMBO_ANCESTOR_APPEND_TEST); 
		combo.setSelection(classname);
	}
}
