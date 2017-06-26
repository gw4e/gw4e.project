package org.gw4e.eclipse.test.preferences;

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
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.conditions.PropertyValueCondition;
import org.gw4e.eclipse.fwk.preferences.GW4EProjectPreference;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.project.PetClinicProject;
import org.gw4e.eclipse.fwk.properties.GW4EProjectProperties;
import org.gw4e.eclipse.fwk.source.SourceHelper;
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
public class GW4EPreferenceProjectTestCase {
	private static String TEST_RESOURCE_FOLDER;
	static {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		TEST_RESOURCE_FOLDER = folders[0] ;
	}
	
	private static SWTWorkbenchBot bot;
	private static String gwproject = "gwproject";
	private static String graphMLFilename = "ShoppingCart.graphml";
	private static String graphMLImplFilenameTest = "ShoppingCartImpl.java";
	private static String graphMLInterfaceFilenameTest = "ShoppingCart.java";
	private static String PACKAGE_NAME =  "pkgname";
 


	@BeforeClass
	public static void beforeClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.TIMEOUT = 10000;
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
	public void testUpdateGenerator() throws CoreException, InterruptedException, IOException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProjectWithoutError(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);

		GW4EProjectPreference gwpp = new GW4EProjectPreference(bot, gwproject);
		GW4EProjectProperties page = gwpp.openPropertiesPage( );
		
		String newValue = "never;I";
		page.replaceGenerator("random(edge_coverage(100));I", newValue);
		page.ok();
		
		page = gwpp.openPropertiesPage( );
		assertTrue("Invalid value ", page.hasGenerator (newValue));
		page.ok();
	}

	
	@Test
	public void testUpdateBuildPolicyName() throws CoreException, InterruptedException, IOException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProjectWithoutError(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);

		GW4EProjectPreference gwpp = new GW4EProjectPreference(bot, gwproject);
		GW4EProjectProperties page = gwpp.openPropertiesPage( );
		
		String newname = "newName.policies";
		page.updateBuildPoliciesFileName (newname);
		page.ok();
		
		page = gwpp.openPropertiesPage( );
		String name = page.getBuildPoliciesFileName ();
		assertEquals("Invalid state ",name, newname);
		page.cancel();
	}

	@Test
	public void testEnableDisableBuild() throws CoreException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProjectWithoutError(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);

		GW4EProjectPreference gwpp = new GW4EProjectPreference(bot, gwproject);
		GW4EProjectProperties page = gwpp.openPropertiesPage( );
		
		page.toggleBuildButton();
		page.ok();
		
		project.cleanBuild();
		
 		IFile iFile = (IFile)ResourceManager.getResource(gwproject + "/src/main/resources/pkgname/build.policies");

		project.clearBuildPoliciesFile(iFile);
 		
 		project.cleanBuild();
	 
		page = gwpp.openPropertiesPage( );
		page.toggleBuildButton();
		page.ok();
		
 		project.cleanBuild();
 		
		ProblemView pv = ProblemView.open(bot);

		ICondition condition =new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return pv.getDisplayedErrorCount() == 1;
			}
			@Override
			public String getFailureMessage() {
				return "Invalid problem count ";
			}
		};
 		bot.waitUntil(condition);
		
 	}
	
	@Test
	public void testEnableDisableSynchronization() throws CoreException, IOException, InterruptedException {
		String expectedNewGenerator = "random(vertex_coverage(50))";
		 
 		PetClinicProject.create (bot,gwproject); // At this step the generator is "random(edge_coverage(100))"
 		 
 		IFile veterinarien = PetClinicProject.getVeterinariensSharedStateImplFile(gwproject);
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(veterinarien);
		String oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, expectedNewGenerator);
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		String newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		assertEquals(newGenerator,expectedNewGenerator);
 		 
 		String location = JDTManager.getGW4EGeneratedAnnotationValue(cu,"value");
 		IPath path = new Path (gwproject).append(location);
 		IFile graphModel =  (IFile)ResourceManager.getResource(path.toString());
 		IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
 		IFile buildPolicyFile =  (IFile)ResourceManager.getResource(buildPolicyPath.toString());
 		
		PropertyValueCondition condition = new PropertyValueCondition(buildPolicyFile,graphModel.getName(),"random(edge_coverage(100));I;random(vertex_coverage(50));I;");
		bot.waitUntil(condition);
		
		GW4EProjectPreference gwpp = new GW4EProjectPreference(bot, gwproject);
		GW4EProjectProperties page = gwpp.openPropertiesPage( );
		
		page.toggleSynchronizationButton();
		page.ok();
		
		cu = JavaCore.createCompilationUnitFrom(veterinarien);
		oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, "random(edge_coverage(80))");
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		
 		// Nothing should have changed
 		condition = new PropertyValueCondition(buildPolicyFile,graphModel.getName(),"random(edge_coverage(100));I;random(vertex_coverage(50));I;");
		bot.waitUntil(condition);

		page = gwpp.openPropertiesPage();
		page.toggleSynchronizationButton();
		page.ok();
		
		cu = JavaCore.createCompilationUnitFrom(veterinarien);
		oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, "random(edge_coverage(80))");
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		
 		
 		//  should have changed
 		try {
			condition = new PropertyValueCondition(buildPolicyFile,graphModel.getName(),"random(vertex_coverage(50));I;random(edge_coverage(80));I;");
			bot.waitUntil(condition);
		} catch (TimeoutException e) {
			condition = new PropertyValueCondition(buildPolicyFile,graphModel.getName(),"random(edge_coverage(80));I;random(vertex_coverage(50));I;");
			bot.waitUntil(condition);
		}
 	}
}
 
