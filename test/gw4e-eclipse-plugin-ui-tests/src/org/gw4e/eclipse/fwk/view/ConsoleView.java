package org.gw4e.eclipse.fwk.view;

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

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.gw4e.eclipse.fwk.conditions.ViewOpened;

public class ConsoleView {
	private SWTBotView botView;
	SWTWorkbenchBot bot;
	int TIMEOUT = 60 * 1000;

	private ConsoleView(SWTWorkbenchBot bot) {
		super();
		this.bot = bot;
	}

	public static ConsoleView open(final SWTWorkbenchBot bot) {
		ConsoleView pbview = new ConsoleView(bot);
		pbview.init();
		return pbview;
	}

	private void init() {
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotMenu menu = bot.menu("Window");
				menu = menu.menu("Show View");
				menu = menu.menu("Console");
				menu.click();
				try {
					bot.waitUntil(new ViewOpened(ConsoleView.this.bot, "Console"), 3 * 1000);
				} catch (Exception e) {
					return false;
				}
				return true;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot open Console view";
			}
		};
		bot.waitUntil(condition, TIMEOUT);
		botView = getBotView();
	}

	public void close() {
		this.botView.close();
	}
	 
	public SWTBotView getBotView() {
		if (botView == null) {
			List<SWTBotView> views = bot.views();
			for (SWTBotView swtBotView : views) {
				String title = swtBotView.getTitle();
				if ("Console".equalsIgnoreCase(title)) {
					botView = swtBotView;
				}
			}
		}
		return botView;
	}

}
