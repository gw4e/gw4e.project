package org.gw4e.eclipse.test.template;

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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.junit.JUnitView;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.run.GW4ETestRunner;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4ETemplateTestCase {
	 
	private static SWTWorkbenchBot bot;
	private static String gwproject = "gwproject";
	public static long	RUN_TIMEOUT	= 3 * 60 * 1000;
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
	public void testCreateProjectWithSimpleTemplate () throws CoreException  {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createWithSimpleTemplate(gwproject);
		
		String mainSourceFolder  = gwproject + "/src/main/java";
		String pkgFragmentRoot	 =	"src/main/java";
		String pkg = "com.company";
		String targetFilename = "SimpleImpl";
		String targetFormat = "test";
		String[] graphFilePath = new String []  { gwproject, "src/main/resources", pkg, "Simple.json" };
		String checkTestBox = "Java Test Based";
		String [] contexts = new String [0];
		FileParameters fp = new FileParameters(mainSourceFolder, gwproject, pkgFragmentRoot, pkg, targetFilename,  targetFormat, graphFilePath);
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.prepareconvertTo(
						fp.getProject(),
						fp.getPackageFragmentRoot(),
						fp.getPackage(), 
						fp.getTargetFilename(), 
						targetFormat,
						checkTestBox,
						"e_StartApp","v_VerifyPreferencePage", "e_StartApp",contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition, 3 * 60 * 1000);
		
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		
		String [] otherContexts = new String [0];
		String mainContext = "com.company.SimpleImpl";
		gwtr.addRun("SimpleImplMyRunTest", gwproject, mainContext, otherContexts);
 		
		gwtr.run("SimpleImplMyRunTest",RUN_TIMEOUT);
	
		String[] expected = new String[] {
				  "\"totalFailedNumberOfModels\": 0,",
				  "\"totalNotExecutedNumberOfModels\": 0,",
				  "\"totalNumberOfUnvisitedVertices\": 1,",
				   
				  "\"totalNumberOfFailedRequirement\": 0,",
				  "\"totalNumberOfModels\": 1,",
				  "\"totalCompletedNumberOfModels\": 1,",
				  "\"requirementsNotCovered\": [],",
				  "\"totalNumberOfVisitedEdges\": 3,",
				  "\"totalIncompleteNumberOfModels\": 0,",
				  "\"edgesNotVisited\": [],",
				  "\"requirementCoverage\": 100,",
				  "\"requirementsPassed\": [{",
				    "\"modelName\": \"Simple\",",
				    "\"requirementKey\": \"REQ001\"",
				  "\"requirementsFailed\": [],",
				  "\"vertexCoverage\": 66,",
				  "\"totalNumberOfEdges\": 3,",
				  "\"totalNumberOfVisitedVertices\": 2,",
				  "\"totalNumberOfRequirement\": 1,",
				  "\"totalNumberOfUncoveredRequirement\": 0,",
				  "\"edgeCoverage\": 100,",
				  "\"totalNumberOfVertices\": 3,",
				  "\"totalNumberOfPassedRequirement\": 1,",
				  "\"totalNumberOfUnvisitedEdges\": 0" };
		
		gwtr.validateRunResult (expected);
		
		String[] nodes = new String[4];
		nodes[0] = gwproject;
		nodes[1] = "src/main/java";
		nodes[2] = "com.company";
		nodes[3] = "SimpleImpl.java";
		JUnitView junit = new JUnitView(bot);
		junit.run(project, nodes, 4, 0, 0,3* 60*1000);
		
	}
	@Test
	public void testCreateProjectWithSimpleScriptedTemplate () throws CoreException  {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createtWithSimpleScriptedTemplate(gwproject);
		
		String mainSourceFolder  = gwproject + "/src/main/java";
		String pkgFragmentRoot	 =	"src/main/java";
		String pkg = "com.company";
		String targetFilename = "SimplewithscriptImpl";
		String targetFormat = "test";
		String[] graphFilePath = new String []  { gwproject, "src/main/resources",  pkg, "Simplewithscript.json" };
		String checkTestBox = "Java Test Based";
		String [] contexts = new String [0];
		FileParameters fp = new FileParameters(mainSourceFolder, gwproject, pkgFragmentRoot, pkg, targetFilename,  targetFormat, graphFilePath);
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.prepareconvertTo(
						fp.getProject(),
						fp.getPackageFragmentRoot(),
						fp.getPackage(), 
						fp.getTargetFilename(), 
						targetFormat,
						checkTestBox,
						"e_init", "v_Browse", "e_init",contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition, 3 * 60 * 1000);
		
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		
		String [] otherContexts = new String [0];
		String mainContext = "com.company.SimplewithscriptImpl";
		gwtr.addRun("SimplewithscriptImplMyRunTest", gwproject, mainContext, otherContexts);
 		
		gwtr.run("SimplewithscriptImplMyRunTest",RUN_TIMEOUT);
		String[] expected = new String[] {
				  "\"totalFailedNumberOfModels\": 0,",
				  "\"totalNotExecutedNumberOfModels\": 0,",
				  "\"totalNumberOfUnvisitedVertices\": 1,",
				  "\"verticesNotVisited\":",
				    "\"modelName\": \"Simplewithscript\",",
				    "\"vertexName\": \"Start\",",
				    "\"vertexId\": \"d71c9cad-f8a8-44e5-a6cd-bfe9f4a46a64\"",
				  "\"totalNumberOfModels\": 1,",
				  "\"totalCompletedNumberOfModels\": 1,",
				  "\"totalNumberOfVisitedEdges\": 9,",
				  "\"totalIncompleteNumberOfModels\": 0,",
				  "\"edgesNotVisited\": [],",
				  "\"vertexCoverage\": 75,",
				  "\"totalNumberOfEdges\": 9,",
				  "\"totalNumberOfVisitedVertices\": 3,",
				  "\"edgeCoverage\": 100,",
				  "\"totalNumberOfVertices\": 4,",
				  "\"totalNumberOfUnvisitedEdges\": 0"};
		gwtr.validateRunResult (expected);

		String[] nodes = new String[4];
		nodes[0] = gwproject;
		nodes[1] = "src/main/java";
		nodes[2] = "com.company";
		nodes[3] = "SimplewithscriptImpl.java";
		JUnitView junit = new JUnitView(bot);
		junit.run(project, nodes, 4, 0, 0, 5*60*1000);
		 
	}
	@Test
	public void testCreateProjectWithEmptyTemplate () throws CoreException  {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createWithEmptyTemplate(gwproject);
	}
	
	
	@Test
	public void testCreateProjectWithSharedTemplate () throws CoreException  {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createWithSharedTemplate(gwproject);
		
		String mainSourceFolder  = gwproject + "/src/main/java";
		String pkgFragmentRoot	 =	"src/main/java";
		String pkg = "com.company";
		String targetFilename = "Model_AImpl";
		String targetFormat = "test";
		String[] graphFilePath = new String []  { gwproject, "src/main/resources", pkg,"Model_A.json" };
		String checkTestBox = "Java Test Based";
		String [] contexts = new String [] {"/gwproject/src/main/resources/com/company/Model_B.json"};
		FileParameters fp = new FileParameters(mainSourceFolder, gwproject, pkgFragmentRoot, pkg, targetFilename,  targetFormat, graphFilePath);
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.prepareconvertTo(
						fp.getProject(),
						fp.getPackageFragmentRoot(),
						fp.getPackage(), 
						fp.getTargetFilename(),   
						targetFormat,
						checkTestBox,
						"e_to_V_A", "v_B", "e_to_V_A",contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition, 3 * 60 * 1000);
	}
	
	@Test
	public void testCreateProjectWithSharedTemplateWithRun () throws CoreException  {
		testCreateProjectWithSharedTemplate ();
		GW4EProject project = new GW4EProject(bot, gwproject);
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		
		String [] otherContexts = new String [ ] {"com.company.Model_BImpl"};
		String mainContext = "com.company.Model_AImpl";
		gwtr.addRun("Model_AImplMyRunTest", gwproject, mainContext, otherContexts);
 		
		gwtr.run("Model_AImplMyRunTest",RUN_TIMEOUT);
		
		String[] expected = new String[] {
				  "\"totalFailedNumberOfModels\": 0,",
				  "\"totalNotExecutedNumberOfModels\": 0,",
				  "\"totalNumberOfUnvisitedVertices\": 1,",
				  "\"verticesNotVisited\": [{",
				    "\"modelName\": \"Model_A\",",
				    "\"vertexName\": \"Start\",",
				    "\"vertexId\": \"35570bf4-652f-4269-86b9-414aebfe7fad\"",
				  "\"totalNumberOfModels\": 2,",
				  "\"totalCompletedNumberOfModels\": 2,",
				  "\"totalNumberOfVisitedEdges\": 8,",
				  "\"totalIncompleteNumberOfModels\": 0,",
				  "\"edgesNotVisited\": [],",
				  "\"vertexCoverage\": 80,",
				  "\"totalNumberOfEdges\": 8,",
				  "\"totalNumberOfVisitedVertices\": 4,",
				  "\"edgeCoverage\": 100,",
				  "\"totalNumberOfVertices\": 5,",
				  "\"totalNumberOfUnvisitedEdges\": 0",
		};
		gwtr.validateRunResult (expected);

		String[] nodes = new String[4];
		nodes[0] = gwproject;
		nodes[1] = "src/main/java";
		nodes[2] = "com.company";
		nodes[3] = "Model_AImpl.java";
		JUnitView junit = new JUnitView(bot);
		junit.run(project, nodes, 4, 0, 0, 3*60*1000);
	}
}
