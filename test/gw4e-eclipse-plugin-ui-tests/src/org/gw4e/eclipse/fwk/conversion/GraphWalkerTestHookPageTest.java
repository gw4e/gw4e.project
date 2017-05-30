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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.gw4e.eclipse.wizard.convert.page.GW4EHookUIPage;

public class GraphWalkerTestHookPageTest {
	SWTBot bot;
	SWTBotShell shell;
	
	public GraphWalkerTestHookPageTest(SWTBotShell shell) {
		super();
		this.shell = shell;
		this.bot = shell.bot();
	}
	
	public void finish () {
		 
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				SWTBotButton fbutton  = bot.button("Finish");
				return fbutton.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return " Finish button not enabled";
			}
		});
		SWTBotButton fbutton  = bot.button("Finish");
		fbutton.click();
	}
	
	public void completeFullPage () {
		allowBeforeAfterExecutionHook ();
		allowPerformanceReport();
		allowBeforeAfterElementHook();
	}
	
	public void allowBeforeAfterExecutionHook () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(GW4EHookUIPage.GW4E_CONVERSION_CONTROL_ID, GW4EHookUIPage.GW4E_CONVERSION_CHOICE_GENERATE_EXECUTON_BEFORE_AFTER_CHECKBOX);
		checkButton.click();
	}
	public void allowPerformanceReport() {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(GW4EHookUIPage.GW4E_CONVERSION_CONTROL_ID, GW4EHookUIPage.GW4E_CONVERSION_CHOICE_GENERATE_PERFORMNCE_REPORT_CHECKBOX);
		checkButton.click();
	}
	public void allowBeforeAfterElementHook () {
		SWTBotCheckBox checkButton = bot.checkBoxWithId(GW4EHookUIPage.GW4E_CONVERSION_CONTROL_ID, GW4EHookUIPage.GW4E_CONVERSION_CHOICE_GENERATE_ELEMENT_BEFORE_AFTER_CHECKBOX);
		checkButton.click();
	}
	
	 
	
}
