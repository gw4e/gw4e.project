package org.gw4e.eclipse.test.project;

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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.IMarkerResolution;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.NoBuildRequiredException;
import org.gw4e.eclipse.builder.marker.InvalidSeverityMarkerResolution;
import org.gw4e.eclipse.builder.marker.PathGeneratorDescription;
import org.gw4e.eclipse.builder.marker.ResolutionMarkerDescription;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.conditions.ErrorIsInProblemView;
import org.gw4e.eclipse.fwk.conditions.NoErrorInProblemView;
import org.gw4e.eclipse.fwk.conditions.PathFoundCondition;
import org.gw4e.eclipse.fwk.conditions.PathGeneratorValueEqualsCondition;
import org.gw4e.eclipse.fwk.conditions.PropertyValueCondition;
import org.gw4e.eclipse.fwk.perpective.GW4EPerspective;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.project.PetClinicProject;
import org.gw4e.eclipse.fwk.source.SourceHelper;
import org.gw4e.eclipse.fwk.view.ProblemView;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GW4EFixesTestCase {
	 
	public static long	RUN_TIMEOUT	= 180 * 1000;
	 
	private static String TEST_RESOURCE_FOLDER;
	static {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		TEST_RESOURCE_FOLDER = folders[0] ;
	}
	SWTBotPerspective p;
	private static SWTWorkbenchBot bot;
	private static String gwproject = "gwproject";
	private static String graphMLFilename = "ShoppingCart.graphml";
	private static String graphMLImplFilenameTest = "ShoppingCartImpl.java";
	private static String graphMLInterfaceFilenameTest = "ShoppingCart.java";
	private static String PACKAGE_NAME =  "pkgname";
	
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
		GW4EPerspective.openGWPerspective(bot);
	}

	
 
	
	@Test
	public void testQuickFixesSetNoCheckMode()
			throws CoreException, BuildPolicyConfigurationException, IOException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testQuickFixesSetNoCheckMode");
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createInitialProject(gwproject,TEST_RESOURCE_FOLDER,PACKAGE_NAME,graphMLFilename);
		 
		ProblemView pv = ProblemView.open(bot);

		ICondition [] conditions =  new ICondition [] {
			new ErrorIsInProblemView(pv, GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG),
			new EditorOpenedCondition(bot,PreferenceManager.getBuildPoliciesFileName(gwproject)),
		};
		
		

		pv.executeQuickFixForErrorMessage(
				project.getMissingErroMessage (gwproject,TEST_RESOURCE_FOLDER,PACKAGE_NAME,graphMLFilename),
				GW4EProject.QUICK_FIX_MSG_MISSING_BULD_POLICIES_FILE,
				conditions);
		pv.close(); // Mandatory
		
		pv = ProblemView.open(bot);
		pv.executeQuickFixForErrorMessage(
				GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG,
				GW4EProject.ADD_NOCHECK_POLICIES,
				new ICondition [] {new NoErrorInProblemView(pv)}
		);

		 
		String buildPoliciPath = gwproject + "/" + TEST_RESOURCE_FOLDER + "/"+ PACKAGE_NAME + "/" + PreferenceManager.getBuildPoliciesFileName(gwproject) ;
		IFile buildPolicyModel =  (IFile)  ResourceManager.getResource(buildPoliciPath);
		IPath pathModel = buildPolicyModel.getFullPath().removeLastSegments(1).append(graphMLFilename);
		IFile iFileModel = (IFile)  ResourceManager.getResource(pathModel.toString());
 		
		String policies = project.getPathGenerator (buildPolicyModel , graphMLFilename);
		String expectedPolicies =   NoBuildRequiredException.NO_CHECK  ;

		assertEquals("Wrong policies found in the build policies file", expectedPolicies, policies);

	}
 
	@Test
	public void testQuickFixesSetSyncMode()
			throws CoreException, BuildPolicyConfigurationException, IOException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testQuickFixesSetSyncMode");
		GW4EProject project = new GW4EProject(bot, gwproject);

		
		project.createInitialProject(gwproject,TEST_RESOURCE_FOLDER,PACKAGE_NAME,graphMLFilename);
		
		
		
		ProblemView pv = ProblemView.open(bot);

		ICondition [] conditions =  new ICondition [] {
			new ErrorIsInProblemView(pv,GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG),
			new EditorOpenedCondition(bot,PreferenceManager.getBuildPoliciesFileName(gwproject)),
		};
		
		pv.executeQuickFixForErrorMessage(
				project.getMissingErroMessage (gwproject,TEST_RESOURCE_FOLDER,PACKAGE_NAME,graphMLFilename),
				GW4EProject.QUICK_FIX_MSG_MISSING_BULD_POLICIES_FILE,
				conditions);
		pv.close(); // Mandatory
		
		pv = ProblemView.open(bot);
		pv.executeQuickFixForErrorMessage(
				GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG,
				GW4EProject.ADD_SYNCED_POLICIES,
				new ICondition [] {new NoErrorInProblemView(pv)}
		);

		 
		String buildPoliciPath = gwproject + "/" + TEST_RESOURCE_FOLDER + "/"+ PACKAGE_NAME + "/" + PreferenceManager.getBuildPoliciesFileName(gwproject) ;
		IFile buildPolicyFile =  (IFile)  ResourceManager.getResource(buildPoliciPath);
		IPath pathModel = buildPolicyFile.getFullPath().removeLastSegments(1).append(graphMLFilename);
		IFile iFileModel = (IFile)  ResourceManager.getResource(pathModel.toString());
		
		
		String policies = project.getPathGenerator (buildPolicyFile , graphMLFilename);
		String expectedPolicies =   NoBuildRequiredException.SYNCH   ;

		assertEquals("Wrong policies found in the build policies file", expectedPolicies, policies);

	}

	

	
	@Test
	public void testUpdatePathGeneratorInSourceFileFromMenu () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testUpdatePathGeneratorInSourceFileFromMenu");
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createProjectWithoutError( TEST_RESOURCE_FOLDER,  PACKAGE_NAME,  graphMLFilename);
		String targetFormat="test";
		String checkTestBox = "Java Test Based";
		FileParameters fp = FileParameters.create("java");
		String [] contexts = new String [0];
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
						"e_StartBrowser",
						"v_ShoppingCart",
						"e_ShoppingCart",
						contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override 
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition);
		
		IProject pj = ResourceManager.getProject(fp.getProject());
		bot.waitUntil(new PathFoundCondition(pj,fp.getPath()),15000);
		bot.waitUntil(new EditorOpenedCondition(bot, fp.getElementName()),15000);
		
		String policiesFileName = PreferenceManager.getBuildPoliciesFileName(gwproject);
		IPath path = new Path (gwproject).append(TEST_RESOURCE_FOLDER).append(PACKAGE_NAME).append(policiesFileName);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(path.toString());
		project.setPathGenerator (buildPolicyFile , graphMLFilename, NoBuildRequiredException.SYNCH); 

		SWTBotTree tree = project.getProjectTree();
		String[] nodes = new String[3];
		nodes[0] = gwproject;
		nodes[1] = "src/main/java";
		nodes[2] = "pkgname";
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				SWTBotMenu menu =item.contextMenu("GW4E").contextMenu("Synchronize Build Policies Files");
				menu.click();	
			}
		});
		
		String expectedValue = "AStarPath(ReachedVertex(v_ShoppingCart));I;RandomPath(EdgeCoverage(100));I;RandomPath(TimeDuration(30));I;random(edge_coverage(100));I;";
		PropertyValueCondition condition = new PropertyValueCondition(buildPolicyFile,graphMLFilename,expectedValue);
		bot.waitUntil(condition);
	}
	
	
	@Test
	public void testInvalidGenerator  () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testInvalidGenerator");
		String wrongPathGenerator = "xxx";
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createProjectWithoutError( TEST_RESOURCE_FOLDER,  PACKAGE_NAME,  graphMLFilename);
		 
		String policiesFileName = PreferenceManager.getBuildPoliciesFileName(gwproject);
		IPath path = new Path (gwproject).append(TEST_RESOURCE_FOLDER).append(PACKAGE_NAME).append(policiesFileName);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(path.toString());
		
		project.setPathGenerator (buildPolicyFile , graphMLFilename, wrongPathGenerator);
		 
		project.cleanBuild(); 
		
		String expectedErrorMessageInProblemView = "Missing severity flag for '" + wrongPathGenerator + "'";
		ProblemView pv = ProblemView.open(bot);
		IMarkerResolution [] markers = InvalidSeverityMarkerResolution.getResolvers();
		String error1 = MessageUtil.getString("invalidpathgeneratoroudinbuildpolicies") + wrongPathGenerator;
		pv.executeQuickFixForErrorMessage(
				expectedErrorMessageInProblemView,
				markers[0].getLabel(),
				new ICondition [] {new ErrorIsInProblemView(pv,error1)}
		);
		pv.close();//Mandatory
		
		
		pv.open(bot);
		ResolutionMarkerDescription description = PathGeneratorDescription.getDescriptions().get(9);
		 
		pv.executeQuickFixForErrorMessage(
				error1,
				description.toString(),
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		
		String  updatedValue  = project.getPathGenerator (buildPolicyFile , graphMLFilename);
		String expectedValue = description.getGenerator() + ";" + PreferenceManager.getDefaultSeverity(gwproject);
		assertEquals("Wrong policies found in the build policies file", expectedValue, updatedValue);
	}
	
	@Test
	public void testInvalidSeverity () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testInvalidSeverity");
		String wrongSeverity = "Z";
		String pathGeneratorPrefix = "random(vertex_coverage(100));E;random(edge_coverage(100));";
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createProjectWithoutError( TEST_RESOURCE_FOLDER,  PACKAGE_NAME,  graphMLFilename);
		 
		String policiesFileName = PreferenceManager.getBuildPoliciesFileName(gwproject);
		IPath path = new Path (gwproject).append(TEST_RESOURCE_FOLDER).append(PACKAGE_NAME).append(policiesFileName);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(path.toString());
		
		project.setPathGenerator (buildPolicyFile , graphMLFilename, pathGeneratorPrefix + wrongSeverity);
		 
		project.cleanBuild(); 
		
		String expectedErrorMessageInProblemView = "Invalid severity flag found  '" + wrongSeverity + "'  (Choose one of E,W,I)" ;
		                                            
		ProblemView pv = ProblemView.open(bot);
		IMarkerResolution [] resolutions = InvalidSeverityMarkerResolution.getResolvers();
		 
		pv.executeQuickFixForErrorMessage(
				expectedErrorMessageInProblemView,
				resolutions [0].getLabel(),
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		pv.close();//Mandatory
		
		String  updatedValue  = project.getPathGenerator (buildPolicyFile , graphMLFilename);
		String expectedValue = pathGeneratorPrefix  + ((InvalidSeverityMarkerResolution)resolutions [0]).getSeverity();
		assertEquals("Wrong severity found in the build policies file", expectedValue, updatedValue);
	}
	
	@Test
	public void testUpdatePathGeneratorInSourceFile () throws CoreException, FileNotFoundException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testUpdatePathGeneratorInSourceFile");
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
 	}

	@Test
	public void testMissingPoliciesAddSynced () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testMissingPoliciesAddSynced");
 		PetClinicProject.create (bot,gwproject); // At this step the generator is "random(edge_coverage(100))"
 		GW4EProject project = new GW4EProject(bot, gwproject);
 		IFile iFile = (IFile)ResourceManager.getResource(gwproject + "/src/test/resources/com/company/build.policies");
 		project.clearBuildPoliciesFile(iFile);
 		
 		project.cleanBuild();
 		
 		ProblemView pv = ProblemView.open(bot); 
		pv.close();//Mandatory 
		
		pv = ProblemView.open(bot);
		pv.waitforErrorCount("No policies found for",5);
		
		pv.executeQuickFixForErrorAllMessage(
				"No policies found for FindOwnersSharedState.graphml",
				GW4EProject.ADD_SYNCED_POLICIES,
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		pv.close();//Mandatory
	}
	
	@Test
	public void testMissingPoliciesAddNoCheck () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testMissingPoliciesAddNoCheck");
 		PetClinicProject.create (bot,gwproject); // At this step the generator is "random(edge_coverage(100))"
 		GW4EProject project = new GW4EProject(bot, gwproject);
 		IFile iFile = (IFile)ResourceManager.getResource(gwproject + "/src/test/resources/com/company/build.policies");
 		project.clearBuildPoliciesFile(iFile);
 		
 		project.cleanBuild();
 		
		ProblemView pv = ProblemView.open(bot); 
		pv.close();//Mandatory 
		
		pv = ProblemView.open(bot);
		pv.executeQuickFixForErrorAllMessage(
				"No policies found for FindOwnersSharedState.graphml",
				GW4EProject.ADD_NOCHECK_POLICIES,
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		pv.close();//Mandatory 
		
		String expectedNewGenerator = "random(edge_coverage(50))";
		IFile veterinarien = PetClinicProject.getVeterinariensSharedStateImplFile(gwproject);
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(veterinarien);
		String oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, expectedNewGenerator);
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		String newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		assertEquals(newGenerator,expectedNewGenerator);
		
 		project.cleanBuild();
 		pv = ProblemView.open(bot);
		bot.waitUntil(new NoErrorInProblemView(pv));
		pv.close();//Mandatory
		
		IPath graphMLPath = PetClinicProject.getCorrespondingGraphMlFile(cu);
		IFile graphMLFile = (IFile) ResourceManager.getResource(graphMLPath.toString());
		IPath buildPolicyPath =  ResourceManager.getBuildPoliciesPathForGraphModel(graphMLFile);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(buildPolicyPath.toString());
		
		String pathGenerator = project.getPathGenerator (buildPolicyFile , graphMLPath.lastSegment());
		String expectedPolicies =   NoBuildRequiredException.NO_CHECK  ;
		assertEquals("Wrong policies found in the build policies file", expectedPolicies, pathGenerator);
		 
		project.setPathGenerator(buildPolicyFile, graphMLPath.lastSegment(), NoBuildRequiredException.SYNCH);
		project.cleanBuild();
		
		expectedNewGenerator = "random(edge_coverage(80))";
		cu = JavaCore.createCompilationUnitFrom(veterinarien);
		oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, expectedNewGenerator);
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		assertEquals(newGenerator,expectedNewGenerator);
	 
		PathGeneratorValueEqualsCondition condition = new PathGeneratorValueEqualsCondition(buildPolicyFile,graphMLPath.lastSegment(),expectedNewGenerator+ ";" + PreferenceManager.getDefaultSeverity(gwproject)+";");
		bot.waitUntil(condition);
	}
	

	
	@Test
	public void testFixUnExistingEntry () throws CoreException, IOException, InterruptedException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testFixUnExistingEntry");
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProjectWithoutError(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);

		addUnExistingEntry (project);
		project.cleanBuild(); 
		
		
		ProblemView pv = ProblemView.open(bot);
 
		pv.executeQuickFixForErrorMessage(
				"Unexisting Graph Model : dummy.graphml",
				MessageUtil.getString("removethisentryfromthebuildpoliciesfile"), 
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		pv.close();//Mandatory
 
	}
	@Test
	public void testInvalidGeneratorInSourceFile () throws CoreException, FileNotFoundException {
		System.out.println("XXXXXXXXXXXXXXXXXXXX testInvalidGeneratorInSourceFile");
		String expectedNewGenerator = "xxx";
		 
 		PetClinicProject.create (bot,gwproject); // At this step the generator is "random(edge_coverage(100))"
 		 
 		IFile veterinarien = PetClinicProject.getVeterinariensSharedStateImplFile(gwproject);
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(veterinarien);
		String oldGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		SourceHelper.updatePathGenerator(veterinarien, oldGenerator, expectedNewGenerator);
 		cu = JavaCore.createCompilationUnitFrom(veterinarien);
 		String newGenerator = JDTManager.findPathGeneratorInGraphWalkerAnnotation(cu);
 		assertEquals(newGenerator,expectedNewGenerator);
 		
 		GW4EProject project = new GW4EProject(bot, gwproject);
 		project.cleanBuild(); 
 		
 		String expectedErrorMessageInProblemView = "Invalid path generator : '"+ expectedNewGenerator + "'";
		ProblemView pv = ProblemView.open(bot);
		List<ResolutionMarkerDescription> markers = PathGeneratorDescription.getDescriptions();
		 
		pv.executeQuickFixForErrorMessage(
				expectedErrorMessageInProblemView,
				markers.get(0).toString(), 
				new ICondition [] {new NoErrorInProblemView(pv)}
		);
		pv.close();//Mandatory
 		
 		String graphmlSourcePath = JDTManager.getGW4EGeneratedAnnotationValue(cu,"value");
 		IPath path = new Path (gwproject).append(graphmlSourcePath);
 		IFile graphModel =  (IFile)ResourceManager.getResource(path.toString());
 		IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
 		IFile buildPolicyFile =  (IFile)ResourceManager.getResource(buildPolicyPath.toString());
 		
		PropertyValueCondition condition = new PropertyValueCondition(buildPolicyFile,graphModel.getName(),"random(vertex_coverage(100));I;random(edge_coverage(100));I;");
		bot.waitUntil(condition);
 	    
 	}
  
	private void addUnExistingEntry (GW4EProject project) throws IOException, CoreException, InterruptedException {
		String buildPoliciPath = gwproject + "/" + TEST_RESOURCE_FOLDER + "/"+ PACKAGE_NAME + "/" + PreferenceManager.getBuildPoliciesFileName(gwproject) ;
		IFile buildPolicyFile =  (IFile)  ResourceManager.getResource(buildPoliciPath);
		IPath pathModel = buildPolicyFile.getFullPath().removeLastSegments(1).append(graphMLFilename);
		project.setPathGenerator (buildPolicyFile , "dummy.graphml", "random(edge_coverage(100));I;");
	}
}

