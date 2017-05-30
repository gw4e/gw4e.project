package org.gw4e.eclipse.fwk.refactoring;

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
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class ModelRefactoring {
	
	SWTWorkbenchBot bot;
	String projectName;

	public ModelRefactoring(SWTWorkbenchBot wbot, String projectName) {
		bot = wbot;
		this.projectName = projectName;
	}
	
	public   SWTBotTree getProjectTree() {
		SWTBotTree tree = getPackageExplorer().bot().tree();
		return tree;
	}

	protected   SWTBotView getPackageExplorer() {
		SWTBotView view = bot.viewByTitle("Package Explorer");
		return view;
	}
	
	
	public void refactorModelName (String newName,String [] nodes) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.select();
		item.setFocus();

		SWTBotMenu menu = item.contextMenu("Refactor").contextMenu("Rename...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("Rename Resource"));
		SWTBotShell shell = bot.shell("Rename Resource");
		
		SWTBotText text = shell.bot().textWithLabel("New name:");
		text.setText(newName);
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return shell.bot().button("OK").isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "OK button not enabled";
			}
		};
		
		bot.waitUntil(condition);
		
		shell.bot().button("OK").click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
	}
	
	public void createPackage (String [] nodes,String [] parent, String name , String pkg) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.select();
		item.setFocus();

		SWTBotMenu menu = item.contextMenu("Refactor").contextMenu("Move...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("Move"));
		SWTBotShell shell = bot.shell("Move");
		
		SWTBotTree packageTree = shell.bot().treeWithLabel("Choose destination for '"+ name + "':");
		SWTBotTreeItem target = packageTree.expandNode(parent);
		target.select();
		target.setFocus();
		
		
		shell.bot().button("Create Package...").click();
		
		bot.waitUntil(Conditions.shellIsActive("New Java Package"));
		SWTBotShell shellNewPackage = bot.shell("New Java Package");
		
		SWTBotText text = shellNewPackage.bot().textWithLabel("Name:");
		text.setText(pkg);
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return shellNewPackage.bot().button("Finish").isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "Finish button not enabled";
			}
		};
		
		bot.waitUntil(condition);
		
		shellNewPackage.bot().button("Finish").click();
		
		bot.waitUntil(Conditions.shellCloses(shellNewPackage));
		
		
		shell.bot().button("Cancel").click();
	}
	
	
	public void refactorRenameFolder (String [] nodes, String name) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.select();
		item.setFocus();

		SWTBotMenu menu = item.contextMenu("Refactor").contextMenu("Rename...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("Rename Package"));
		SWTBotShell shell = bot.shell("Rename Package");
	

		SWTBotText text = shell.bot().textWithLabel("New name:");
		text.setText(name);
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return shell.bot().button("OK").isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "OK button not enabled";
			}
		};
		
		bot.waitUntil(condition);
		
		shell.bot().button("OK").click();
		
		bot.waitUntil(Conditions.shellCloses(shell), 30 * 1000);
	}
	
	public void refactorMoveModel (String [] nodes,String [] destination, String name) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.select();
		item.setFocus();

		SWTBotMenu menu = item.contextMenu("Refactor").contextMenu("Move...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("Move"));
		SWTBotShell shell = bot.shell("Move");
		
		SWTBotTree packageTree = shell.bot().treeWithLabel("Choose destination for '"+ name + "':");
		SWTBotTreeItem target = packageTree.expandNode(destination);
		target.select();
		target.setFocus();
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return shell.bot().button("OK").isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "OK button not enabled";
			}
		};
		
		bot.waitUntil(condition);
		
		shell.bot().button("OK").click();
		
		bot.waitUntil(Conditions.shellCloses(shell));
	}

}
