package org.gw4e.eclipse.test.staticgenerator;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.run.GW4ETestRunner;
import org.gw4e.eclipse.fwk.staticgenerator.StaticGeneratorWizard;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4EStaticApiBasedTestCase {
	private static SWTWorkbenchBot bot;
	 
	public static long	RUN_TIMEOUT	= 180 * 1000;
	private static String gwproject = "gwproject";
	static Map<String,String> elements = new HashMap<String,String> ();
	static {
		elements.put("Start", "20842969-3927-464d-9042-b64e6a732559");
		elements.put("v_VerifyPreferencePage", "0457d7d6-c659-46de-9b93-9a026334035f");
		elements.put("v_VerifyAppRunning", "b5fceb6b-d831-4528-acea-e53a6f3c3e7b");
		elements.put("e_ClosePreferencePage", "a030ef25-c699-43ac-9e78-dd24b6fcd2bd");
		elements.put("e_StartApp", "933b96e2-dd5d-4b85-8168-018a99fa065b");
		elements.put("e_OpenPreferencesPage", "d38ae993-1276-4105-b4a6-bdda540d1f02");
	}	
	
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
	
	private List<String> buildIds (String ... names) {
		List<String> ids = new ArrayList<String> ();
		for (String name : names) {
			ids.add(elements.get(name));
		}
		return ids;
	}
	
	@Test
	public void testGenerateApiBasedTest () throws CoreException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleStatic");
		 
		List<String> ids = buildIds ("Start","e_StartApp","v_VerifyAppRunning");
		IPath p = new Path("/"+ gwproject+ "/src/main/resources/com/company/Simple.json");
		IFile file = (IFile) ResourceManager.getResource(p.toString());
		StaticGeneratorWizard.open(file, ids);
		
		StaticGeneratorWizard ges = new StaticGeneratorWizard(bot);
		ges.assertTargetElements("Start","e_StartApp","v_VerifyAppRunning");
		ges.assertSourceElements("v_VerifyPreferencePage","e_ClosePreferencePage","e_OpenPreferencesPage");
		ges.next();
		ges.assertExtensionValue(0,"com.company.SimpleImpl.java - gwproject/src/main/java"); 
		ges.next();
		ges.enterDestination("SimpleStatic", "/gwproject/src/main/java/com/company/SimpleStatic.java", "src/main/java", "com", "company");
		ges.finish();	
		
		DefaultCondition condition = new EditorOpenedCondition(bot,"SimpleStatic.java");
		bot.waitUntil(condition,RUN_TIMEOUT);
		
		String[] nodes = new String[4];
		nodes[0] =  gwproject;
		nodes[1] = "src/main/java";
		nodes[2] = "com.company";
		nodes[3] = "SimpleStatic.java";
		
		GW4ETestRunner gwtr = new  GW4ETestRunner(bot);
		String[] expected = new String[] { "Executing:v_VerifyAppRunning" };
		gwtr.runAsJavaApplication(expected,RUN_TIMEOUT,nodes);
		System.out.println("ended");
	}
	 
	@Test
	public void testGraphSelectElementTest () throws CoreException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleStatic");
		 
		List<String> ids = buildIds ("Start","e_StartApp","v_VerifyAppRunning");
		IPath p = new Path("/"+ gwproject+ "/src/main/resources/com/company/Simple.json");
		IFile file = (IFile) ResourceManager.getResource(p.toString());
		StaticGeneratorWizard.open(file, ids);
		
		StaticGeneratorWizard ges = new StaticGeneratorWizard(bot);
		ges.moveSourceToTarget("v_VerifyPreferencePage");
		ges.assertErrorMessage("Was expecting an edge after v_VerifyAppRunning");
		ges.moveTargetToSource("v_VerifyPreferencePage");
		
		ges.moveSourceToTarget("e_ClosePreferencePage");
		ges.assertErrorMessage("Was expecting an edge, but not this one : e_ClosePreferencePage");
		ges.moveTargetToSource("e_ClosePreferencePage");
		
		ges.moveSourceToTarget("v_VerifyPreferencePage");
		ges.assertErrorMessage("Was expecting an edge after v_VerifyAppRunning");
		ges.moveTargetToSource("v_VerifyPreferencePage");

		ges.moveTargetToSource("Start");
		ges.moveTargetToSource("e_StartApp");
		ges.moveTargetToSource("v_VerifyAppRunning");
		ges.assertErrorMessage("You need to select target elements");
		ges.moveSourceToTarget("Start");
		ges.moveSourceToTarget("e_StartApp");
		ges.moveSourceToTarget("v_VerifyAppRunning");
		
		ges.moveTargetToSource("Start");
		ges.moveTargetToSource("e_StartApp");
		ges.moveTargetToSource("v_VerifyAppRunning");
		ges.moveSourceToTarget("Start");
		ges.moveSourceToTarget("e_StartApp");
		ges.moveSourceToTarget("v_VerifyPreferencePage");
		ges.assertErrorMessage("Was expecting a vertex node : v_VerifyAppRunning after e_StartApp");
		ges.moveTargetToSource("v_VerifyPreferencePage");
		ges.moveSourceToTarget("v_VerifyAppRunning");
		
		ges.moveTargetToSource("Start");
		ges.moveTargetToSource("e_StartApp");
		ges.moveTargetToSource("v_VerifyAppRunning");
		ges.moveSourceToTarget("Start");
		ges.moveSourceToTarget("e_StartApp");
		ges.moveSourceToTarget("e_OpenPreferencesPage");
		ges.assertErrorMessage("Was expecting a vertex node, but found : e_OpenPreferencesPage");
		ges.moveTargetToSource("e_OpenPreferencesPage");
		ges.moveSourceToTarget("v_VerifyAppRunning");
		
		ges.moveDown("Start");
		ges.assertErrorMessage("Was expecting a vertex node : v_VerifyAppRunning after e_StartApp");
		ges.moveUp("Start");
		
		ges.moveUp("v_VerifyAppRunning");
		ges.assertErrorMessage("Was expecting an edge after Start");
		ges.moveDown("v_VerifyAppRunning");
		ges.assertNextEnabled();
		
		System.out.println("ended");
	}
	 
	@Test
	public void testFolderSelectionTest () throws CoreException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleStatic");
		 
		List<String> ids = buildIds ("Start","e_StartApp","v_VerifyAppRunning");
		IPath p = new Path("/"+ gwproject+ "/src/main/resources/com/company/Simple.json");
		IFile file = (IFile) ResourceManager.getResource(p.toString());
		StaticGeneratorWizard.open(file, ids);
		
		StaticGeneratorWizard ges = new StaticGeneratorWizard(bot);
		ges.assertTargetElements("Start","e_StartApp","v_VerifyAppRunning");
		ges.assertSourceElements("v_VerifyPreferencePage","e_ClosePreferencePage","e_OpenPreferencesPage");
		ges.next();
		ges.assertExtensionValue(0,"com.company.SimpleImpl.java - gwproject/src/main/java"); 
		ges.next();
		
		ges.enterDestination("SimpleStatic", "/gwproject/src/main/java/com/company/SimpleStatic.java", "src/main/java", "com", "company");
		ges.assertFinishEnabled();

		ges.enterDestination("SimpleStatic", "/gwproject/src/main/resources/SimpleStatic.java", "src/main/resources");
		ges.assertErrorMessage("Invalid folder. You can only choose a folder below : /gwproject/src/main/java or /gwproject/src/test/java");
		
		ges.enterDestination("SimpleStatic", "/gwproject/src/test/java/SimpleStatic.java", "src/test/java");
		ges.assertFinishEnabled();

		ges.finish();	

		DefaultCondition condition = new EditorOpenedCondition(bot,"SimpleStatic.java");
		bot.waitUntil(condition,RUN_TIMEOUT);

		StaticGeneratorWizard.open(file, ids);
		
		ges = new StaticGeneratorWizard(bot);
		ges.assertTargetElements("Start","e_StartApp","v_VerifyAppRunning");
		ges.assertSourceElements("v_VerifyPreferencePage","e_ClosePreferencePage","e_OpenPreferencesPage");
		ges.next();
		ges.assertExtensionValue(0,"com.company.SimpleImpl.java - gwproject/src/main/java"); 
		ges.next();
		
		ges.enterDestination("SimpleStatic", "/gwproject/src/test/java/SimpleStatic.java", "src/test/java");
		ges.assertErrorMessage("File already exists");
		ges.checkEraseExistingFile();
		
		ges.assertFinishEnabled();
 
		
		System.out.println();
	}
}
