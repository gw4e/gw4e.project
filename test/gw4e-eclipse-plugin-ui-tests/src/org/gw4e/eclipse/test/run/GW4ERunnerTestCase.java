package org.gw4e.eclipse.test.run;

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

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.NoBuildRequiredException;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.perpective.GW4EPerspective;
import org.gw4e.eclipse.fwk.preferences.ConsolePreferencePage;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.project.PetClinicProject;
import org.gw4e.eclipse.fwk.run.GW4EOfflineRunner;
import org.gw4e.eclipse.fwk.run.GW4ETestRunner;
import org.gw4e.eclipse.fwk.view.ProblemView;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4ERunnerTestCase {
	 
	static SWTWorkbenchBot bot;
	public static long	RUN_TIMEOUT	= 180 * 1000;
	private static String TEST_RESOURCE_FOLDER;
	
	static {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		TEST_RESOURCE_FOLDER = folders[0] ;
	}
	private static String PACKAGE_NAME =  "pkgname";
	private static String gwproject = "gwproject";
	private static String graphMLFilename = "ShoppingCart.graphml";
			
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
		long timeout = SWTBotPreferences.TIMEOUT;
		try {
			SWTBotPreferences.TIMEOUT = 3 * 60 * 1000;
			bot.resetWorkbench();
			GW4EProject.cleanWorkspace();
			GW4EPerspective.openGWPerspective(bot);
		} finally {
			SWTBotPreferences.TIMEOUT = timeout;
		}
	}
	
	
	 
	
	@Test
	public void testGW4ETestRunner () throws CoreException, FileNotFoundException {
		ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);

		PetClinicProject.create (bot,gwproject);
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		
		String [] otherContexts = new String [] {
				"com.company.FindOwnersSharedStateImpl",
				"com.company.NewOwnerSharedStateImpl",
				"com.company.OwnerInformationSharedStateImpl",
				"com.company.VeterinariensSharedStateImpl"};
		String mainContext = "com.company.PetClinicSharedStateImpl";
		gwtr.addRun("MyRunTest", gwproject, mainContext, otherContexts);
 		
		gwtr.run("MyRunTest",RUN_TIMEOUT);
		String[] expected = new String[] { "Result :",   "  \"totalFailedNumberOfModels\": 0,",
				"  \"totalNotExecutedNumberOfModels\": 0,", "  \"totalNumberOfUnvisitedVertices\": 0,",
				"  \"verticesNotVisited\": [],", "  \"totalNumberOfModels\": 5,",
				"  \"totalCompletedNumberOfModels\": 5,", "  \"totalNumberOfVisitedEdges\": 25,",
				"  \"totalIncompleteNumberOfModels\": 0,", "  \"edgesNotVisited\": [],", "  \"vertexCoverage\": 100,",
				"  \"totalNumberOfEdges\": 25,", "  \"totalNumberOfVisitedVertices\": 16,", "  \"edgeCoverage\": 100,",
				"  \"totalNumberOfVertices\": 16,", "  \"totalNumberOfUnvisitedEdges\": 0" };
		gwtr.validateRunResult (expected);
	}
	
	@Test
	public void testRunAsRunner () throws CoreException, FileNotFoundException {
		ResourceManager.setAutoBuilding(false);
		
		ConsolePreferencePage cpp = new ConsolePreferencePage (bot);
		cpp.unlimitoutput();
		
		
		gwproject =  gwproject + "1";
		try {
			// Changing the project name 
			// Sounds like the testRunAsMultipleRunner execution leads to  a file locking on windows 
			// To do ... 
			PetClinicProject.create (bot,gwproject);
		} finally {
			ResourceManager.setAutoBuilding(true);
		}
		GW4EProject project = new GW4EProject(bot, gwproject);
		SWTBotTree tree = project.getProjectTree();
		String[] nodes = new String[4];
		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "PetClinicSharedStateImpl.java";
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				String gwRunnerMenuItem = "2 GW4E Test";
				List<String> menus = item.contextMenu("Run As").menuItems();
				for (String menu : menus) {
					if (menu.indexOf("GW4E Test") != -1) {
						gwRunnerMenuItem = menu;
					}
				}
				
				SWTBotMenu menu =item.contextMenu("Run As").contextMenu(gwRunnerMenuItem);
				menu.click();	
			}
		});
		
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		String[] expected = new String[] { "com.company.PetClinicSharedStateImpl(RandomPath(EdgeCoverage(100)) )"  };
		gwtr.waitForRunCompleted (expected,RUN_TIMEOUT);
	}
	
 
	
	@Test
	public void testRunAsMultipleRunner () throws CoreException, FileNotFoundException {
		ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);

		ConsolePreferencePage cpp = new ConsolePreferencePage (bot);
		cpp.unlimitoutput();
		
		// No configuration yet
		assertEquals(GW4EProject.getGW4ETestRunnerConfigurationCount(),0);
		
		PetClinicProject.create (bot,gwproject); 
		GW4EProject project = new GW4EProject(bot, gwproject);
		SWTBotTree tree = project.getProjectTree();
		String[] nodes = new String[4];
		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "PetClinicSharedStateImpl.java";
		SWTBotTreeItem item0 = tree.expandNode(nodes);
		 
		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "FindOwnersSharedStateImpl.java";
		SWTBotTreeItem item1 = tree.expandNode(nodes);

		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "NewOwnerSharedStateImpl.java";
		SWTBotTreeItem item2 = tree.expandNode(nodes);

		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "OwnerInformationSharedStateImpl.java";
		SWTBotTreeItem item3 = tree.expandNode(nodes);
	 
		nodes[0] = gwproject;
		nodes[1] = "src/test/java";
		nodes[2] = "com.company";
		nodes[3] = "VeterinariensSharedStateImpl.java";
		SWTBotTreeItem item4 = tree.expandNode(nodes);
	 
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				try {
					SWTBotMenu menu =tree.select(new SWTBotTreeItem[] {item0,item1,item2,item3,item4}).contextMenu("Run As").contextMenu("1 GW4E Test");
					menu.click();
				} catch (WidgetNotFoundException e) {
					SWTBotMenu menu =tree.select(new SWTBotTreeItem[] {item0,item1,item2,item3,item4}).contextMenu("Run As").contextMenu("2 GW4E Test");
					menu.click();
				}	
			}
		});
		
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
 
		String[] expected = new String[] {
	    "com.company.FindOwnersSharedStateImpl(RandomPath(EdgeCoverage(100)) )",
	    "com.company.PetClinicSharedStateImpl(RandomPath(EdgeCoverage(100)) )",
	    "com.company.VeterinariensSharedStateImpl(RandomPath(EdgeCoverage(100)) )",
	    "com.company.NewOwnerSharedStateImpl(RandomPath(EdgeCoverage(100)) )",
	    "com.company.OwnerInformationSharedStateImpl(RandomPath(EdgeCoverage(100)) )",};
		
		gwtr.waitForRunCompleted (expected,RUN_TIMEOUT);
 
		// Configuration should have been created
		assertEquals(GW4EProject.getGW4ETestRunnerConfigurationCount(),1);
		
		// Configuration should not be recreated
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				try {
					SWTBotMenu menu =tree.select(new SWTBotTreeItem[] {item0,item1,item2,item3,item4}).contextMenu("Run As").contextMenu("1 GW4E Test");
					menu.click();
				} catch (WidgetNotFoundException e) {
					SWTBotMenu menu =tree.select(new SWTBotTreeItem[] {item0,item1,item2,item3,item4}).contextMenu("Run As").contextMenu("2 GW4E Test");
					menu.click();
				}	
			}
		});
		
		assertEquals(GW4EProject.getGW4ETestRunnerConfigurationCount(),1);
	}
	
	
	@Test
	public void testOfflineRunner () throws CoreException, BuildPolicyConfigurationException, InterruptedException, IOException {
		String pathGenerator = "random(reached_vertex( v_SearchResult ))";
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();
		IFile file = project.createGraphMLFile(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);
		 
		IFile buildPolicyFile = BuildPolicyManager.createBuildPoliciesFile(file, new NullProgressMonitor ());
		 
		project.setPathGenerator(buildPolicyFile, file.getFullPath().lastSegment(), pathGenerator+";I");
	 
		DefaultCondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				ProblemView pv = ProblemView.open(GW4ERunnerTestCase.this.bot);
				GW4EProject.cleanBuild();
				boolean b = pv.getDisplayedErrorCount() == 0;
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Failed to create a project withour error";
			}
		};
		bot.waitUntil(condition, 3 * 60 * 1000);
		
		String filepath = "/"+gwproject+"/"+TEST_RESOURCE_FOLDER+"/"+PACKAGE_NAME+"/"+graphMLFilename;
		GW4EOfflineRunner runner = new GW4EOfflineRunner (bot);
		
 	
		runner.addOfflineRun		("MyRun",gwproject,filepath,true,true,"e_StartBrowser",pathGenerator);
		runner.validateRun	("MyRun",gwproject,filepath,true,true,"e_StartBrowser",pathGenerator);
		
		runner.run("MyRun",RUN_TIMEOUT);
		
		String []  expectations = new String [] {
				"\"currentElementName\":\"v_SearchResult\"",
		};
		
		runner.validateRunResult(expectations);
 	}
}
