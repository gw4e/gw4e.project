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
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.gw4e.eclipse.fwk.conditions.ViewOpened;
import org.gw4e.eclipse.views.PerformanceView;

public class GW4EPerformanceView {

	private SWTBotView botView;
	SWTWorkbenchBot bot;

	static String VIEW_TITLE = "GW4E Plugin Performance";
	
	private GW4EPerformanceView(SWTWorkbenchBot bot) {
		super();
		this.bot = bot;
	}

	public static GW4EPerformanceView open(final SWTWorkbenchBot bot) {
		GW4EPerformanceView pbview = new GW4EPerformanceView(bot);
		pbview.init();
		return pbview;
	}

	public int getRowCount () {
		SWTBotTable table = bot.tableWithId(PerformanceView.PERFORMANCE_VIEW_WIDGET_ID, PerformanceView.PERFORMANCE_VIEW_TABLE);
		return table.rowCount();
	}
	
	private void init() {
		SWTBotMenu menu = bot.menu("Window");
		menu = menu.menu("Show View");
		menu = menu.menu("Other...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("Show View"));
		SWTBotShell shell = bot.shell("Show View").activate();
		SWTBotText text = shell.bot().text();
		text.setText(VIEW_TITLE);
		bot.waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				SWTBotTree tree = shell.bot().tree();
				if(tree!=null) {
					TableCollection col = tree.selection();
					String selected = col.get(0, 0);
					if (VIEW_TITLE.equals(selected)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Failed to set the text.";
			}

		});
 
		shell.bot().button("OK").click();
		
		bot.waitUntil(new ViewOpened(bot, VIEW_TITLE));
		botView = getBotView();
	}

	public SWTBotView getBotView() {
		if (botView == null) {
			List<SWTBotView> views = bot.views();
			for (SWTBotView swtBotView : views) {
				String title = swtBotView.getTitle();
				if (VIEW_TITLE.equalsIgnoreCase(title)) {
					botView = swtBotView;
				}
			}
		}
		return botView;
	}

	public void close() {
		this.botView.close();
	}

	public void clickClearButton () {
		String text = PerformanceView.getClearToolBarButtonText();
		SWTBotToolbarButton button = findToolBarButton (text);
		button.click();
	}

	public void clickLoadButton () {
		String text = PerformanceView.getLoadToolBarButtonText();
		SWTBotToolbarButton button = findToolBarButton (text);
		button.click();
	}
	
	private SWTBotToolbarButton findToolBarButton (String text) {
		List<SWTBotToolbarButton> items = this.botView.getToolbarButtons();
		for (SWTBotToolbarButton button : items) {
		    if (text.equals(button.getToolTipText())) {
		        return button;
		    }
		}
		throw new WidgetNotFoundException (text);
	}
	 
}
