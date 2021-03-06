package org.gw4e.eclipse.fwk.junit;

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

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.run.GW4ETestRunner;
import org.hamcrest.Matcher;

public class JUnitView {
	SWTBot bot;
	 
	public JUnitView(SWTBot bot ) {
		this.bot=bot;
	}
	
	private void openView () {
		Display.getDefault().syncExec(() -> {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page != null) {
					page.showView("org.eclipse.jdt.junit.ResultView");
				}
			} catch (PartInitException e) {
				ResourceManager.logException(e);
			}
		});
	}
	
	public void run (GW4EProject project,GW4ETestRunner runner,String[] nodes, int runCount,int expectedError,int expectedFailure,long timeout,String ... OptionalExpected) {
		DefaultCondition condition = new DefaultCondition () {
			boolean found = false;
			@Override
			public boolean test() throws Exception {
				SWTBotTree tree = project.getProjectTree();
				SWTBotTreeItem item = tree.expandNode(nodes);
				item.setFocus();
				item.select();
				Display.getDefault().syncExec(new Runnable () {
					@Override
					public void run() {
						try {
							String gwRunnerMenuItem = "3 JUnit Test";
							List<String> menus = item.contextMenu("Run As").menuItems();
							for (String menu : menus) {
								if (menu.indexOf("JUnit Test") != -1) {
									gwRunnerMenuItem = menu;
								}
							}
							SWTBotMenu menu =item.contextMenu("Run As").contextMenu(gwRunnerMenuItem);
							menu.click();
							found = true;
						} catch (Exception e) {
						}	
					}
				});
				return found;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot Run Junit Test";
			}
			
		};
		
		bot.waitUntil(condition,timeout);  
	    bot.waitUntil(new RunCompletedCondition(runner,runCount,OptionalExpected),timeout);  
	    bot.waitUntil(new ExpectedErrorCondition(expectedError));  
	    bot.waitUntil(new ExpectedFailureCondition(expectedFailure));  
	}
	
	public class RunCompletedCondition extends DefaultCondition {
		int runCount;
		SWTBotText runText;
		GW4ETestRunner runner;
		String [] optionalExpected;
		List<String> labels = new ArrayList<String> ();
		public RunCompletedCondition(GW4ETestRunner runner, int runCount,String ... OptionalExpected) {
			super();
			this.runCount = runCount;
			this.runner = runner;
			this.optionalExpected = OptionalExpected;
			openView ();
		}

		@Override
		public boolean test() throws Exception {
			SWTBotCTabItem item = bot.cTabItem("JUnit");
			runText = null;
			labels.clear();
			if (item!=null) {
				runText = bot.textWithLabel("Runs: ");
				System.out.println("STATUS ");
				SWTBotText failureText = bot.textWithLabel("Failures: ");
				SWTBotText errorsText = bot.textWithLabel("Errors: ");
				System.out.println("errorsText ----> >" + errorsText.getText() + "<");
				System.out.println("failureText ----> >" + failureText.getText() + "<");
				System.out.println("runText ----> >" + runText.getText() + "<");
				System.out.println("END STATUS ");
				if ( (runCount+"/"+ runCount).equalsIgnoreCase(runText.getText()) ) {
					Matcher matcher = allOf(widgetOfType(Label.class));
					List<Label> list = bot.getFinder().findControls(matcher);
					System.out.println("labels found ----> " + list.size());
					boolean[] temp = new boolean [] {false}; 
					for (Label label : list) {
						Display.getDefault().syncExec(new Runnable () {
							@Override
							public void run() {
								System.out.println("label ----> " + label.getText());
								labels.add(label.getText());
								if (label.getText().indexOf("Finished after") != -1) {
									temp [0] =  true;
								}
							}
						});
						if (temp [0]) return true;
					}
				}
			}
			 // JUnitView does not always display the label "Finished after", give a second chance to see whether the job is completed or not
			String result =	runner.getConsoleText();
			if (optionalExpected!=null && optionalExpected.length > 0 && validateRunResult (result,optionalExpected)) {
				return true;
			}
			
			return false;
		}

		@Override
		public String getFailureMessage() {
			if (runText == null) {
				return "Unable to get the Runs text field";
			} else {
				return "test not completed. Only " +  runText.getText() + " ran. " + labels;
			}
		}
		
		private boolean validateRunResult (String result,String []  expectations) {
			boolean found = true;
			 for (int i = 0; i < expectations.length; i++) {
				 if (expectations[i].trim().length()==0) continue;
				if (result.indexOf(expectations[i])==-1) {
					found = false;
				}
			}
			 return found;
		}
	}

	public class ExpectedErrorCondition extends DefaultCondition {
		int expectedError;
		SWTBotText errorsText;
		public ExpectedErrorCondition(int expectedError) {
			super();
			this.expectedError = expectedError;
		}

		@Override
		public boolean test() throws Exception {
			SWTBotCTabItem item = bot.cTabItem("JUnit");
			errorsText = null;
			if (item!=null) {
				errorsText = bot.textWithLabel("Errors: ");
				if ( (expectedError+"").equalsIgnoreCase(errorsText.getText()) ) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getFailureMessage() {
			if (errorsText == null) {
				return "Unable to get the Errors text field";
			} else {
				return "tests not completed as expected. Found " +  errorsText.getText() + " errors while expecting " + expectedError;
			}
		}
		
	}
	
	public class ExpectedFailureCondition extends DefaultCondition {
		int expectedFailure;
		SWTBotText failureText;
		public ExpectedFailureCondition(int expectedFailure) {
			super();
			this.expectedFailure = expectedFailure;
		}

		@Override
		public boolean test() throws Exception {
			SWTBotCTabItem item = bot.cTabItem("JUnit");
			failureText = null;
			if (item!=null) {
				failureText = bot.textWithLabel("Failures: ");
				if ( (expectedFailure+"").equalsIgnoreCase(failureText.getText()) ) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getFailureMessage() {
			if (failureText == null) {
				return "Unable to get the Failures text field";
			} else {
				return "tests not completed as expected. Found " +  failureText.getText() + " failures while expecting " + expectedFailure;
			}
		}
		
	}
}
