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

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class TreeNodeFoundCondition implements ICondition {
	SWTWorkbenchBot bot;
	String text;
	public TreeNodeFoundCondition(SWTWorkbenchBot bot, String text) {
		this.bot=bot;
		this.text=text;
	}

	@Override
	public boolean test() throws Exception {
		return isExpanded (bot.tree().getTreeItem(text) ,text);
	}

	@Override
	public void init(SWTBot bot) {
	}

	@Override
	public String getFailureMessage() {
		return "Fail to expande the node with text " + text;
	}

	private boolean isExpanded (SWTBotTreeItem  parent , String nodename) {
		SWTBotTreeItem[] items = parent.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equalsIgnoreCase(nodename)) {
				return items[i].isExpanded();
			}
		}
		return false;
	}
}
