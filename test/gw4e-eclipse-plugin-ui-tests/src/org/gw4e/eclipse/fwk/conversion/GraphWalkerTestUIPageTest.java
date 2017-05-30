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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.wizard.convert.page.GraphWalkerTestUIPage;

public class GraphWalkerTestUIPageTest {
	SWTBot bot;
	SWTBotShell shell;
	
	public GraphWalkerTestUIPageTest(SWTBotShell shell) {
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
	
	public void completeFullPage (String pathGenerator,String groups,String startElementName) {
		allowGraphWalkerTest();
		allowGraphWalkerModelBasedTestTest();

		setPathGenerator(pathGenerator);
		setGroupTest(groups);
		setTargetEdge(startElementName);
		
	
	}
	
	public void allowGraphWalkerTest () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(GraphWalkerTestUIPage.GW4E_CONVERSION_CONTROL_ID, GraphWalkerTestUIPage.GW4E_CONVERSION_CHOICE_GENERATE_GRAPHWALKER_CHECKBOX);
		checkButton.click();
	}
	   
	public void allowGraphWalkerModelBasedTestTest () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(GraphWalkerTestUIPage.GW4E_CONVERSION_CONTROL_ID, GraphWalkerTestUIPage.GW4E_CONVERSION_CHOICE_GENERATE_RUN_MODEL_TEST_CHECKBOX);
		checkButton.click();
	}	
	
	public void setPathGenerator (String pathGenerator) {
		SWTBotText text = bot.textWithId(GraphWalkerTestUIPage.GW4E_CONVERSION_CONTROL_ID, GraphWalkerTestUIPage.GW4E_CONVERSION_PATHGENERATORTEXT_ID);
		text.setText(pathGenerator);
	}
	
	public void setGroupTest (String grouptest) {
		SWTBotText text = bot.textWithId(GraphWalkerTestUIPage.GW4E_CONVERSION_CONTROL_ID, GraphWalkerTestUIPage.GW4E_CONVERSION_GROUP_TEXT_ID);
		text.setText(grouptest);
	}
	
	public void setTargetEdge (String value) {
		SWTBotCombo combo = bot.comboBoxWithId(GraphWalkerTestUIPage.GW4E_CONVERSION_CONTROL_ID, GraphWalkerTestUIPage.GW4E_CONVERSION_COMBO_START_ELEMENT); 
		combo.setSelection(value);
	}
	
}
