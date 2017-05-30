package org.gw4e.eclipse.fwk.preferences;

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
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.properties.GW4EProjectProperties;
import org.gw4e.eclipse.property.ProjectPropertyPage;

public class GW4EProjectPreference {
	 
	
	static SWTWorkbenchBot bot;
	String projectName;

	public GW4EProjectPreference(SWTWorkbenchBot wbot, String projectName) {
		bot = wbot;
		this.projectName = projectName;
	}
	
	
	public static SWTBotTree getProjectTree() {
		SWTBotTree tree = getPackageExplorer().bot().tree();
		return tree;
	}

	protected static SWTBotView getPackageExplorer() {
		SWTBotView view = bot.viewByTitle("Package Explorer");
		return view;
	}

	public GW4EProjectProperties openPropertiesPage ( ) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(this.projectName);
		item.setFocus();
		item.select();

		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotTreeItem treeItem = tree.getTreeItem(projectName);
				return treeItem.isSelected();
			}

			@Override
			public String getFailureMessage() {
				return "Project " + projectName + " not selected ";
			}
			
		};
		bot.waitUntil(condition);
		bot.menu( "File").menu("Properties").click();		 
		bot.waitUntil(Conditions.shellIsActive("Properties for " + projectName));
		SWTBotShell shell = bot.shell("Properties for " + projectName).activate();
		shell.bot().tree().select("GW4E");
		bot.waitUntil(Conditions.waitForWidget(WidgetMatcherFactory.withId(	ProjectPropertyPage.PROJECT_PROPERTY_PAGE_WIDGET_ID, ProjectPropertyPage.PROJECT_PROPERTY_PAGE_WIDGET_SECURITY_LEVEL_FOR_ABSTRACT_CONTEXT)));
		return new GW4EProjectProperties(bot,shell);
	}
	
	
	
}
