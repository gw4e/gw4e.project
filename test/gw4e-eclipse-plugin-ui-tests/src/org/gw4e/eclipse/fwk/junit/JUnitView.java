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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.hamcrest.Matcher;

public class JUnitView {
	SWTBot bot;
	 
	public JUnitView(SWTBot bot ) {
		this.bot=bot;
	}
	
	public void run (GW4EProject project,String[] nodes, int runCount,int expectedError,int expectedFailure,long timeout) {
		SWTBotTree tree = project.getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				String gwRunnerMenuItem = "3 JUnit Test";
				List<String> menus = item.contextMenu("Run As").menuItems();
				for (String menu : menus) {
					if (menu.indexOf("JUnit Test") != -1) {
						gwRunnerMenuItem = menu;
					}
				}
				
				SWTBotMenu menu =item.contextMenu("Run As").contextMenu(gwRunnerMenuItem);
				menu.click();	
			}
		});
	    bot.waitUntil(new RunCompletedCondition(runCount),timeout);  
	    bot.waitUntil(new ExpectedErrorCondition(expectedError));  
	    bot.waitUntil(new ExpectedFailureCondition(expectedFailure));  
	}
	
	public class RunCompletedCondition extends DefaultCondition {
		int runCount;
		SWTBotText runText;
		List<String> labels = new ArrayList<String> ();
		public RunCompletedCondition(int runCount) {
			super();
			this.runCount = runCount;
		}

		@Override
		public boolean test() throws Exception {
			SWTBotCTabItem item = bot.cTabItem("JUnit");
			runText = null;
			labels.clear();
			if (item!=null) {
				runText = bot.textWithLabel("Runs: ");
				if ( (runCount+"/"+ runCount).equalsIgnoreCase(runText.getText()) ) {
					Matcher matcher = allOf(widgetOfType(Label.class));
					List<Label> list = bot.getFinder().findControls(matcher);
					boolean[] temp = new boolean [] {false}; 
					for (Label label : list) {
						Display.getDefault().syncExec(new Runnable () {
							@Override
							public void run() {
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
