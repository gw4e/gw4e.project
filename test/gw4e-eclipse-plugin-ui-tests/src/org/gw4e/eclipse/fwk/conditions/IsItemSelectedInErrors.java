package org.gw4e.eclipse.fwk.conditions;

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

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.view.ProblemView;

public class IsItemSelectedInErrors extends DefaultCondition {
	/**
	 * 
	 */
	private final ProblemView problemView;
	private SWTBotTreeItem checkedItem ;
	 
	public IsItemSelectedInErrors(ProblemView problemView, SWTBotTreeItem item) {
		this.problemView = problemView;
		this.checkedItem = item;
	}

	public String getFailureMessage() {
		String newline = System.getProperty("line.separator");
		SWTBotTreeItem item = this.problemView.expandErrorItem ();
		SWTBotTreeItem[] child = item.getItems();
		StringBuffer sb = new StringBuffer (newline);
		for (int i = 0; i < child.length; i++) {
			String row = child[i].isSelected() + " " + child[i].row().get(0) + newline;
			sb.append(row);
				 
		}
		return "item '" + checkedItem + "' is not clicked in the problem view in  " + sb.toString();
	}

	public boolean test() throws Exception {
		try {
			
			SWTBotTreeItem item = this.problemView.expandErrorItem ();
			SWTBotTreeItem[] child = item.getItems();
			for (int i = 0; i < child.length; i++) {
				if (child[i].isSelected() && child[i].row().get(0).equalsIgnoreCase(checkedItem.row().get(0))) {
					return true;
				}
			}
			retry ();
			return false;
		} catch (WidgetNotFoundException e) {
			retry ();
			return false;
		}
	}
	
	
	private void retry () {
		checkedItem.setFocus();
		checkedItem.click();
		checkedItem.select();
	}

}
