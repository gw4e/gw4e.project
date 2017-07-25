package org.gw4e.eclipse.test.runasmanual;

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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.run.GW4EManualRunner;
import org.gw4e.eclipse.fwk.runasmanual.RunAsManualWizard;
import org.gw4e.eclipse.fwk.runasmanual.RunAsManualWizard.SummaryExecutionRow;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4ERunAsManualTestCase {
	private static SWTWorkbenchBot bot;
	 
	public static long	RUN_TIMEOUT	= 180 * 1000;
	private static String gwproject = "gwproject";
 
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.TIMEOUT = 6000;
		bot = new SWTWorkbenchBot();
		try {
			bot.viewByTitle("Welcome").close();

		} catch (Exception e) {
		}
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() throws CoreException {
		bot.resetWorkbench();
		GW4EProject.cleanWorkspace();
	}
	 
	@Test
	public void testGenerateManualAsTemplate () throws CoreException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
 
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		String targetFolder =  "/gwproject/src/main/resources/com/company";
		IFile modelFile = runner.importJSONModel("search.json", targetFolder);
		BuildPolicyManager.addDefaultPolicies(modelFile, new NullProgressMonitor ());
		String config = "testGenerateManualAsTemplate";
		runner.addRun(config, gwproject, modelFile.getFullPath().toString(), new String [0], "random(edge_coverage(100))", true, true, "e_startBrowser");
		SWTBotShell page = runner.run(config);
		
		String [] expected  = new String []  {
				"/gwproject/src/main/resources/com/company/search.json",
				"Additional model(s) :",
				"Generator/Stop Condition :", 
				"random(edge_coverage(100))",
				"Start Element :", 
				"e_startBrowser",
				"Remove Blocked Element :",
				"Yes",
		};
		
		RunAsManualWizard wizard = new RunAsManualWizard (bot);
		wizard.assertTestPresentatioPageContainsTexts(page, expected);
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");

		SummaryExecutionRow[] rows = new SummaryExecutionRow[6];
		rows[0] = new SummaryExecutionRow ("1", "e_startBrowser", "", "Start a browser");
		rows[1] = new SummaryExecutionRow ("1", "v_browserStarted", "", "Browser is started");
		rows[2] = new SummaryExecutionRow ("1", "e_enterURL", "", "Write the url - http://google.com in the browser's URL bar and press enter.");
		rows[3] = new SummaryExecutionRow ("1", "v_enterURL", "", "Google home page is displayed.");
		rows[4] = new SummaryExecutionRow ("1", "e_enterSearchedWord", "", "Enter the search term - “GW4E” in the google search bar and Press enter.");
		rows[5] = new SummaryExecutionRow ("1", "v_searchResultDisplayed", "", "Search results related to 'GW4E' are displayed");
		
		wizard.assertNextStepPage (page,  rows[0].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[1].getDescription(), defaultResult);
		wizard.assertNextStepPage (page,  rows[2].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[3].getDescription(), defaultResult);
		wizard.assertNextStepPage (page,  rows[4].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[5].getDescription(), defaultResult);
		
		wizard.assertSummaryExecution(page,  rows);
	 
		boolean exportAsTest = false;
		String workbookfile = "mycampaign.xlsx";
		String workbooktitle = "Web Campaign";
		String caseid = "Case Search Happy Path - 1";
		boolean updatemode = false;
		String componentname = "Web Search Component";
		String priority = "High";
		String dateformat = null;
		wizard.feed (exportAsTest,workbookfile,workbooktitle,caseid,updatemode,componentname,priority,dateformat);
		
		wizard.finish();
		 
		String format = "MM/dd/yy";
		DateFormat dateFormat =  new SimpleDateFormat (format);
		Date date = new Date ();
		String description = "Verify that when a user writes a search term and presses enter, search results should be displayed.";
		int row = 2;
		wizard.assertManuelTestTemplateSpreadSheet ("/gwproject/"+workbookfile,
				rows,workbooktitle,caseid,componentname,priority,date,dateFormat,description,row,"",true);
		
		System.out.println("ended");
	}
	 
	 
	private long executeTest (String config ,
			boolean updatemode,
			IFile modelFile,
			SummaryExecutionRow[] rows, 
			String errorMsg, 
			String status,
			int row,
			String caseid) throws CoreException, IOException, InterruptedException {
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		runner.addRun(config, gwproject, modelFile.getFullPath().toString(), new String [0], "random(edge_coverage(100))", true, true, "e_startBrowser");
		SWTBotShell page = runner.run(config);
		
		String [] expected  = new String []  {
				"/gwproject/src/main/resources/com/company/search.json",
				"Additional model(s) :",
				"Generator/Stop Condition :", 
				"random(edge_coverage(100))",
				"Start Element :", 
				"e_startBrowser",
				"Remove Blocked Element :",
				"Yes",
		};
		
		RunAsManualWizard wizard = new RunAsManualWizard (bot);
		wizard.assertTestPresentatioPageContainsTexts(page, expected);
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		
		wizard.assertNextStepPage (page,  rows[0].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[1].getDescription(), defaultResult);
		wizard.assertNextStepPage (page,  rows[2].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[3].getDescription(), defaultResult);
		wizard.assertNextStepPage (page,  rows[4].getDescription(), "");
		wizard.assertNextStepPage (page,  rows[5].getDescription(), errorMsg);
		
		wizard.assertSummaryExecution(page,  rows);
	 
		boolean exportAsTest = true;
		String workbookfile = "mycampaign.xlsx";
		String workbooktitle = "Web Campaign";

		String componentname = "Web Search Component";
		String priority = "High";
		String dateformat = null;
		wizard.feed (exportAsTest,workbookfile,workbooktitle,caseid,updatemode,componentname,priority,dateformat);
		
		wizard.finish();
		 
		String format = "MM/dd/yy";
		DateFormat dateFormat =  new SimpleDateFormat (format);
		Date date = new Date ();
		String description = "Verify that when a user writes a search term and presses enter, search results should be displayed.";
		return wizard.assertManuelTestTemplateSpreadSheet ("/gwproject/"+workbookfile,rows,workbooktitle,caseid,componentname,priority,date,dateFormat,description,row,status,false);
	}
	 
	@Test
	public void testGenerateManualAsTest () throws CoreException, IOException, InterruptedException {
		String config = "testGenerateManualAsTest";
		boolean updatemode = false;
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
 
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		String targetFolder =  "/gwproject/src/main/resources/com/company";
		IFile modelFile = runner.importJSONModel("search.json", targetFolder);
		BuildPolicyManager.addDefaultPolicies(modelFile, new NullProgressMonitor ());
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		
		SummaryExecutionRow[] rows = new SummaryExecutionRow[6];
		rows[0] = new SummaryExecutionRow ("1", "e_startBrowser", "", "Start a browser");
		rows[1] = new SummaryExecutionRow ("1", "v_browserStarted", defaultResult, "Browser is started");
		rows[2] = new SummaryExecutionRow ("1", "e_enterURL", "", "Write the url - http://google.com in the browser's URL bar and press enter.");
		rows[3] = new SummaryExecutionRow ("1", "v_enterURL", defaultResult, "Google home page is displayed.");
		rows[4] = new SummaryExecutionRow ("1", "e_enterSearchedWord", "", "Enter the search term - “GW4E” in the google search bar and Press enter.");
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result", "Search results related to 'GW4E' are displayed");
		
		String caseid = "Case Search Happy Path - 1";
		executeTest (config , updatemode, modelFile,rows,"error wrong result","0",2,caseid);
	}

	@Test
	public void testTwiceGenerateManualAsTestWithUpdateMode () throws CoreException, IOException, InterruptedException {
		System.out.println("testTwiceGenerateManualAsTestWithUpdateMode started");
		String config = "testTwiceGenerateManualAsTestWithUpdateMode";
		boolean updatemode = false;

		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
 
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		String targetFolder =  "/gwproject/src/main/resources/com/company";
		IFile modelFile = runner.importJSONModel("search.json", targetFolder);
		BuildPolicyManager.addDefaultPolicies(modelFile, new NullProgressMonitor ());
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		
		SummaryExecutionRow[] rows = new SummaryExecutionRow[6];
		rows[0] = new SummaryExecutionRow ("1", "e_startBrowser", "", "Start a browser");
		rows[1] = new SummaryExecutionRow ("1", "v_browserStarted", defaultResult, "Browser is started");
		rows[2] = new SummaryExecutionRow ("1", "e_enterURL", "", "Write the url - http://google.com in the browser's URL bar and press enter.");
		rows[3] = new SummaryExecutionRow ("1", "v_enterURL", defaultResult, "Google home page is displayed.");
		rows[4] = new SummaryExecutionRow ("1", "e_enterSearchedWord", "", "Enter the search term - “GW4E” in the google search bar and Press enter.");
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result", "Search results related to 'GW4E' are displayed");

		String caseid = "Case Search Happy Path - 1";
		long time = executeTest (config , updatemode, modelFile, rows,"error wrong result","0",2,caseid);
		config = "testTwiceGenerateManualAsTestWithUpdateMode1";
		updatemode = true;
		rows[5] = new SummaryExecutionRow ("1", "v_searchResultDisplayed", defaultResult, "Search results related to 'GW4E' are displayed");
		executeTest (config , updatemode, modelFile,rows,defaultResult,"1",2,caseid);
		
		System.out.println("testTwiceGenerateManualAsTestWithUpdateMode ended");
	}

	@Test
	public void testTwiceGenerateManualAsTestWithNoUpdateMode () throws CoreException, IOException, InterruptedException {
		System.out.println("testTwiceGenerateManualAsTestWithNoUpdateMode started");
		String config = "testTwiceGenerateManualAsTestWithNoUpdateMode";
		boolean updatemode = false;

		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
 
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		String targetFolder =  "/gwproject/src/main/resources/com/company";
		IFile modelFile = runner.importJSONModel("search.json", targetFolder);
		BuildPolicyManager.addDefaultPolicies(modelFile, new NullProgressMonitor ());
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		
		SummaryExecutionRow[] rows = new SummaryExecutionRow[6];
		rows[0] = new SummaryExecutionRow ("1", "e_startBrowser", "", "Start a browser");
		rows[1] = new SummaryExecutionRow ("1", "v_browserStarted", defaultResult, "Browser is started");
		rows[2] = new SummaryExecutionRow ("1", "e_enterURL", "", "Write the url - http://google.com in the browser's URL bar and press enter.");
		rows[3] = new SummaryExecutionRow ("1", "v_enterURL", defaultResult, "Google home page is displayed.");
		rows[4] = new SummaryExecutionRow ("1", "e_enterSearchedWord", "", "Enter the search term - “GW4E” in the google search bar and Press enter.");
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result", "Search results related to 'GW4E' are displayed");

		String caseid = "Case Search Happy Path - 1";
		long time = executeTest (config , updatemode, modelFile, rows,"error wrong result","0",2,caseid);
		config = "testTwiceGenerateManualAsTestWithNoUpdateMode1";
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result 1", "Search results related to 'GW4E' are displayed");
		 
 		executeTest (config , updatemode, modelFile, rows,"error wrong result 1","0",3,caseid);
		
		System.out.println("testTwiceGenerateManualAsTestWithUpdateMode ended");
	}
	
	@Test
	public void testTwiceGenerateManualAsTestWithDiffCaseid () throws CoreException, IOException, InterruptedException {
		System.out.println("testTwiceGenerateManualAsTestWithDiffCaseid started");
		String config = "testTwiceGenerateManualAsTestWithDiffCaseid";
		boolean updatemode = false;

		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
 
		GW4EManualRunner runner = new GW4EManualRunner (bot);
		String targetFolder =  "/gwproject/src/main/resources/com/company";
		IFile modelFile = runner.importJSONModel("search.json", targetFolder);
		BuildPolicyManager.addDefaultPolicies(modelFile, new NullProgressMonitor ());
		
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		
		SummaryExecutionRow[] rows = new SummaryExecutionRow[6];
		rows[0] = new SummaryExecutionRow ("1", "e_startBrowser", "", "Start a browser");
		rows[1] = new SummaryExecutionRow ("1", "v_browserStarted", defaultResult, "Browser is started");
		rows[2] = new SummaryExecutionRow ("1", "e_enterURL", "", "Write the url - http://google.com in the browser's URL bar and press enter.");
		rows[3] = new SummaryExecutionRow ("1", "v_enterURL", defaultResult, "Google home page is displayed.");
		rows[4] = new SummaryExecutionRow ("1", "e_enterSearchedWord", "", "Enter the search term - “GW4E” in the google search bar and Press enter.");
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result", "Search results related to 'GW4E' are displayed");

		String caseid = "Case Search Happy Path - 1";
		long time = executeTest (config , updatemode, modelFile, rows,"error wrong result","0",2,caseid);
		
		
		config = "testTwiceGenerateManualAsTestWithDiffCaseid1";
		rows[5] = new SummaryExecutionRow ("0", "v_searchResultDisplayed", "error wrong result 1", "Search results related to 'GW4E' are displayed");
		caseid = "Case Search Happy Path - 2";
		executeTest (config , updatemode, modelFile, rows,"error wrong result 1","0",3,caseid);
		
		System.out.println("testTwiceGenerateManualAsTestWithDiffCaseid ended");
	}
}
