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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.fwk.conditions.ErrorCountInProblemView;
import org.gw4e.eclipse.fwk.conditions.ErrorIsInProblemView;
import org.gw4e.eclipse.fwk.conditions.IsItemSelectedInErrors;
import org.gw4e.eclipse.fwk.conditions.ProblemsAreGenerated;
import org.gw4e.eclipse.fwk.project.GW4EProject;

public class ProblemView {
	private SWTBotView botView;
	SWTWorkbenchBot bot;
	int TIMEOUT = 60 * 1000;
 
	
	public final static String ERROR = "Errors";
	public final static String WARNINGS = "Warnings";

	private ProblemView(SWTWorkbenchBot bot ) {
		super();
		this.bot = bot;
	}

	public static ProblemView open(final SWTWorkbenchBot bot) {
		ProblemView pbview = new ProblemView(bot);
		pbview.init();
		return pbview;
	}

	 
	private void init() {
		Display.getDefault().syncExec(() -> {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.views.ProblemView");
			} catch (PartInitException e1) {
				e1.printStackTrace();
			}
		});
		botView = getBotView();
	}
	 
	public void close() {
		this.botView.close();
	}

	public void waitforErrorCount(String expectedErrorMessageInProblemView,int count) {
		bot.waitUntil(new ErrorCountInProblemView(this, expectedErrorMessageInProblemView, count));
	}

	public void executeQuickFixForErrorAllMessage(String expectedErrorMessageInProblemView, String quickfixmessage,
			ICondition[] conditions) {
		
		print ();
		
		bot.waitUntil(new ErrorIsInProblemView(this, expectedErrorMessageInProblemView));

		SWTBotTreeItem item = findErrorItemWithText(expectedErrorMessageInProblemView);
		item.setFocus();
		item.click();
		bot.waitUntil(new IsItemSelectedInErrors(this, item));

		ICondition cond = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				item.contextMenu().menu("Quick Fix").click();
				try {
					bot.waitUntil(Conditions.shellIsActive("Quick Fix"), 2*1000);
				} catch (Exception e) {
					return false;
				}
				return true;
			}
			@Override
			public String getFailureMessage() {
				return "Unable to open the Quick fix";
			}
		};
		
		bot.waitUntil(cond, 15 * 1000);
		
		SWTBotShell shell = bot.shell("Quick Fix");

		SWTBotTable table = shell.bot().tableWithLabel("Select a fix:");

		SWTBotTableItem thequickFixItem = table.getTableItem(quickfixmessage);
		thequickFixItem.click();
		thequickFixItem.select();

		SWTBotButton selectAll = shell.bot().button("Select All");
		selectAll.click();

		final SWTBotTable resourcesTable = shell.bot().tableWithLabel("Problems:");

		ICondition condition = new ICondition() {
			@Override
			public boolean test() throws Exception {
				int rowCount = resourcesTable.rowCount();
				int selectedCount = 0;
				for (int i = 0; i < rowCount; i++) {
					SWTBotTableItem item = resourcesTable.getTableItem(i);
					if (item.isChecked())
						selectedCount++;
				}
				return selectedCount == rowCount;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "not all items are selected.";
			}
		};

		bot.waitUntil(condition, TIMEOUT);

		SWTBotButton button = shell.bot().button("Finish");
		button.click();
		bot.waitUntil(Conditions.shellCloses(shell));

		if (conditions != null) {
			for (int i = 0; i < conditions.length; i++) {
				bot.waitUntil(conditions[i], TIMEOUT);
			}
		}

	}

	public void executeQuickFixForErrorMessage(String expectedErrorMessageInProblemView, String quickfixmessage,
			ICondition[] conditions) throws CoreException {
		
		print ();
		
		bot.waitUntil(new ErrorIsInProblemView(this, expectedErrorMessageInProblemView));

		SWTBotTreeItem item = findErrorItemWithText(expectedErrorMessageInProblemView);
		item.setFocus();
		item.click();
		bot.waitUntil(new IsItemSelectedInErrors(this, item));

		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				item.contextMenu().menu("Quick Fix").click();
				try {
					bot.waitUntil(Conditions.shellIsActive("Quick Fix"), 2 * 1000 );
				} catch (Exception e) {
					return false;
				}
				return true;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to open the Quick fix";
			}
			
		};
		
		bot.waitUntil(condition, 15 * 1000);
		
		SWTBotShell shell = bot.shell("Quick Fix");

		SWTBotTable table = shell.bot().tableWithLabel("Select a fix:");

		SWTBotTableItem thequickFixItem = table.getTableItem(quickfixmessage);
		thequickFixItem.click();
		thequickFixItem.select();

		SWTBotButton button = shell.bot().button("Finish");
		button.click();
		bot.waitUntil(Conditions.shellCloses(shell),TIMEOUT);

		bot.saveAllEditors();

		GW4EProject.fullBuild();
		
		if (conditions != null) {
			for (int i = 0; i < conditions.length; i++) {
				bot.waitUntil(conditions[i], TIMEOUT);
			}
		}

	}

	public int countErrorWithText(String text) {
		SWTBotTreeItem item = expandErrorItem();
		int found=0;
		SWTBotTreeItem[] child = item.getItems();
		for (int i = 0; i < child.length; i++) {
			if (child[i].row().get(0).trim().startsWith(text.trim())) {
				found++;
			}
		}
		return found;
	}
	
	public SWTBotTreeItem findErrorItemWithText(String text) {
		SWTBotTreeItem item = expandErrorItem();
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotTreeItem[] child = item.getItems();
				for (int i = 0; i < child.length; i++) {
					if (child[i].row().get(0).trim().equalsIgnoreCase(text.trim())) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot find the Error " + text;
			}
			
		};
		
		bot.waitUntil(condition);
		
		SWTBotTreeItem[] child = item.getItems();
		for (int i = 0; i < child.length; i++) {
			if (child[i].row().get(0).trim().equalsIgnoreCase(text.trim())) {
				return child[i];
			}
		}
		throw new WidgetNotFoundException("Cannot find the Error " + text);
	}
	
	public boolean errorIsInProblemView (String expected) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResource resource = workspace.getRoot();
		IMarker[] markers = resource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_INFINITE);
		for (IMarker m : markers) {
		    String msg = (String)m.getAttribute(IMarker.MESSAGE);
		    if (expected.equalsIgnoreCase(msg.trim())) return true;
		}		
		return false;
	}
	
	public void print() {
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IResource resource = workspace.getRoot();
			IMarker[] markers = resource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_INFINITE);
			for (IMarker m : markers) {
			    System.out.println("Id: " + m.getId());
			    System.out.println("Message: " + m.getAttribute(IMarker.MESSAGE));
			    System.out.println("Source ID: " + m.getAttribute(IMarker.SOURCE_ID));
			    System.out.println("Location: " + m.getAttribute(IMarker.LOCATION));
			    System.out.println("Line Number: " + m.getAttribute(IMarker.LINE_NUMBER));
			    System.out.println("Marker: " + m.getAttribute(IMarker.MARKER));
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public SWTBotTreeItem expandErrorItem() {
		ICondition condition = new DefaultCondition () {

			@Override
			public boolean test() throws Exception {
				SWTBotTree tree = botView.bot().tree();
				SWTBotTreeItem[] items = tree.getAllItems();
				for (int i = 0; i < items.length; i++) {
					System.out.println(items[i].getText());
					if (items[i].getText().contains("Errors (")) {
						if (!items[i].isExpanded())
							items[i].expand();
						return true;
					}
				}
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot find the Errors row in the problem view";
			}
			
		};
		
		bot.waitUntil(condition);

		SWTBotTree tree = botView.bot().tree();
		SWTBotTreeItem[] items = tree.getAllItems();
		for (int i = 0; i < items.length; i++) {
			System.out.println(items[i].getText());
			if (items[i].getText().contains("Errors (")) {
				if (!items[i].isExpanded())
					items[i].expand();
				return items[i];
			}
		}
		throw new WidgetNotFoundException("Cannot find the Errors row in the problem view");
	}

	public SWTBotView getBotView() {
		if (botView == null) {
			List<SWTBotView> views = bot.views();
			for (SWTBotView swtBotView : views) {
				String title = swtBotView.getTitle();
				if ("Problems".equalsIgnoreCase(title)) {
					botView = swtBotView;
				}
			}
		}
		return botView;
	}

	public int getDisplayedWarningCount() {
		return getCountofType("Warnings");
	}

	public int getDisplayedErrorCount() {
		return getCountofType("Errors");
	}

	public String getMessage(int rowNumber, String type) {
		final int MESSAGE_COLUMN = 0;
		SWTBotTree tree = botView.bot().tree();
		SWTBotTreeItem[] items = tree.getAllItems();
		for (SWTBotTreeItem item : items) {
			if (item.getText().contains(type)) {
				return item.expand().getItems()[rowNumber].row().get(MESSAGE_COLUMN);
			}
		}
		return "";
	}

	public void waitForAtLeastNofType(String problemType, int expected) {
		if (expected < 0 || (!checkType(problemType))) {
			throw new IllegalArgumentException();
		}
		bot.waitUntil(new ProblemsAreGenerated(this, expected, problemType));
	}

	private boolean checkType(String problemType) {
		return problemType.equals(ERROR) || problemType.equals(WARNINGS);
	}

	public int getCountofType(String type) {
		SWTBotTree tree = botView.bot().tree();
		SWTBotTreeItem[] items = tree.getAllItems();
		for (SWTBotTreeItem item : items) {
			if (item.getText().contains(type)) {
				return item.expand().getItems().length;
			}
		}
		return 0;
	}

	public static boolean hasErrorsInProblemsView(SWTWorkbenchBot bot) {
		// Open Problems View by Window -> show view -> Problems
		bot.menu("Window").menu("Show View").menu("Problems").click();

		SWTBotView view = bot.viewByTitle("Problems");
		view.show();
		SWTBotTree tree = view.bot().tree();

		for (SWTBotTreeItem item : tree.getAllItems()) {
			String text = item.getText();
			if (text != null && text.startsWith("Errors")) {
				return true;
			}
		}

		return false;
	}

}
