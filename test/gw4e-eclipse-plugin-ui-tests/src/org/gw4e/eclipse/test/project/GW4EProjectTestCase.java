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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.IOHelper;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.conditions.FolderExists;
import org.gw4e.eclipse.fwk.conditions.PathFoundCondition;
import org.gw4e.eclipse.fwk.conversion.OfflineTestUIPageTest;
import org.gw4e.eclipse.fwk.perpective.GW4EPerspective;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.properties.GraphModelProperties;
import org.gw4e.eclipse.fwk.source.ImportHelper;
import org.gw4e.eclipse.fwk.source.JDTHelper;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.google.common.io.CharStreams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4EProjectTestCase {
	private static String TEST_RESOURCE_FOLDER;
	static {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		TEST_RESOURCE_FOLDER = folders[0] ; // src/main/resources
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
		GW4EPerspective.openGWPerspective(bot);
	}

 	@Test
	public void testAddGW4ENature () throws CoreException, FileNotFoundException {
		String path = new File("src/test/resources/java-amazon.zip").getAbsolutePath();
		ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);
		ImportHelper.importProjectFromZip(bot, path);
        
        GW4EProject project = new GW4EProject(bot, "java-amazon");
        project.convertExistingProject();
        
        IFile ifile = (IFile)ResourceManager.getResource("java-amazon/src/main/resources/org/graphwalker/ShoppingCart.graphml");
        bot.waitUntil(new PathFoundCondition(ifile.getProject(),ifile.getFullPath().toString()));
		
        String[] errors = new String[] { "Expecting a build.policies file in /java-amazon/src/main/resources/org/graphwalker including /java-amazon/src/main/resources/org/graphwalker/ShoppingCart.graphml" };
		project.waitForBuildAndAssertErrors(errors);
	}
	
 	@Test
	public void testRemoveNature() throws CoreException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();
		project.removeNature();

		String[] menus = new String[] { "GW4E", "Remove GW4E Nature" };
		boolean[] states = new boolean[] { true, false };
		project.assertMenuEnabled(menus, states, gwproject);

		menus = new String[] { "Configure", "Convert to GW4E" };
		states = new boolean[] { true, true };
		project.assertMenuEnabled(menus, states, gwproject);
	}

    @Test
	public void testInitialMenuStates() throws CoreException {
		GW4EProject project = new GW4EProject(bot, "gwproject1");
		project.resetToJavPerspective();
		project.createProject();

		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		project.assertHasSourceFolders(folders);
		String folder = PreferenceManager.getTargetFolderForTestInterface("gwproject1",false);
		project.assertHasFolder(folder);

		String[] menus = new String[] { "GW4E", "Remove GW4E Nature" };
		boolean[] states = new boolean[] { true, true };
		project.assertMenuEnabled(menus, states, "gwproject1");

		menus = new String[] { "Configure", "Convert to GW4E" };
		states = new boolean[] { true, false };
		project.assertMenuEnabled(menus, states, "gwproject1");
	}



 	@Test
	public void testAddGraphmlFile() throws CoreException, FileNotFoundException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();

		project.createGraphMLFile(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);

		String[] errors = new String[] { project.getMissingErroMessage (gwproject,TEST_RESOURCE_FOLDER,PACKAGE_NAME,graphMLFilename) };
		project.waitForBuildAndAssertErrors(errors);
	}



	@Test
	public void testGenerateSource() throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createInitialProjectWithoutError(TEST_RESOURCE_FOLDER,  PACKAGE_NAME, graphMLFilename);
		
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		String[] temp = new String[2];
		temp[0] = gwproject;
		temp[1] = folders[0];

		project.generateSource(temp);

		bot.waitUntil(new EditorOpenedCondition(bot, (graphMLImplFilenameTest)), 30000);

		project.waitForBuildAndAssertNoErrors();

		//
		String path = "/" + gwproject + "/" + PreferenceManager.getMainSourceFolder() + "/" + PACKAGE_NAME + "/"
				+ graphMLImplFilenameTest;
		IProject pj = (IProject)ResourceManager.getResource(gwproject);
		boolean testImplFound = ResourceManager.fileExists(pj,path);
		assertTrue("Cannot find Test Implementation in " + path, testImplFound);

		path = "/" + gwproject + "/" + PreferenceManager.getTargetFolderForTestInterface(gwproject,true) + "/" + PACKAGE_NAME + "/"
				+ graphMLInterfaceFilenameTest;
		boolean testInterfaceFound = ResourceManager.fileExists(pj,path);
		assertTrue("Cannot find Test Interface in " + path, testInterfaceFound);

	}
	
 	@Test
	public void testAuthorizedFolderGenerateSource() throws CoreException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();

		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();

		for (int i = 0; i < folders.length; i++) {
			String[] temp = new String[2];
			temp[0] = gwproject;
			temp[1] = folders[i];

			project.canGenerateSource(true, temp);
		}

		List<String> sourceFolders = ResourceManager.getFilteredSourceFolders(gwproject, folders);
		for (String folder : sourceFolders) {
			String[] temp = new String[2];
			temp[0] = gwproject;
			temp[1] = folder;
			project.canGenerateSource(false, temp);
		}
	 
	}

	@Test
	public void testConvertToJavaModelbased() throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		testConvertTo("java","Java Model Based");
	}
	
	@Test
	public void testConvertToJavaTestbased() throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		testPrepareConvertTo("test","Java Test Based");
	}
	
	@Test
	public void testConvertToJSON() throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		testConvertTo("json","Json");
	}
	
	@Test
	public void testConvertToDot() throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		testConvertTo("dot","Dot"); 
	}
	
	
	
	
	@Test
	public void testOfflineAppendMode() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleOffLineImpl");
		OfflineTestUIPageTest page = walkToToOfflinePage(gwproject,fp); 
		page.selectAppendMode("com.company.SimpleImpl.java - gwproject/src/main/java");
		page.selectGenerators(new String [] {"random(edge_coverage(100))"});
		page.finish();
															  
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/company/SimpleImpl.java");
				boolean methodAppended = IOHelper.findInFile(resource, "Generated with : random(edge_coverage(100))");
				return methodAppended;
			}

			@Override
			public String getFailureMessage() {
				return "method not generated ";
			}
		};
		bot.waitUntil(condition, 3 * 6 * SWTBotPreferences.TIMEOUT); //3mn
		closeWizard ();
	}

	@Test
	public void testOfflineStandAloneMode() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleOffLineImpl");
		OfflineTestUIPageTest page = walkToToOfflinePage(gwproject,fp); 
		page.selectStandAloneMode("MyClazz");
		page.selectGenerators(new String [] {"random(edge_coverage(100))"});
		page.finish();
															  
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/company/MyClazz.java");
				boolean methodAppended = IOHelper.findInFile(resource, "Generated with : random(edge_coverage(100))");
				return methodAppended;
			}

			@Override
			public String getFailureMessage() {
				return "method not generated ";
			}
		};
		bot.waitUntil(condition, 3 * 6 * SWTBotPreferences.TIMEOUT); //3mn
		closeWizard ();
	}
	
	@Test
	public void testOfflineStandAloneModeWithTimeout() throws Exception {
		boolean[] result = new boolean [] {false};
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				 if (status.getMessage().indexOf("Operation cancelled either manually or a timeout occured.")!=-1) {
					 result[0] = true;
				 }
			}
		};
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createSimpleProjectWithoutGeneration ();

		IFile buildPolicyFile = (IFile) ResourceManager.getResource("gwproject/src/main/resources/com/company/build.policies");
		BuildPolicyManager.setPolicies(buildPolicyFile, "Simple.json", "random(never);I", new NullProgressMonitor ());
		buildPolicyFile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor ());
		FileParameters fp = project.generateForSimpleProject ();
		fp.setTargetFilename("SimpleOffLineImpl");
		OfflineTestUIPageTest page = walkToToOfflinePage(gwproject,fp); 
		page.selectTimeout("1");
		page.selectStandAloneMode("MyClazz");
		page.selectGenerators(new String [] {"random(never)"});
		
		
		try {
			ErrorDialog.AUTOMATED_MODE = true;
			ResourceManager.addLogListener(listener);
			page.finish();
			
			ICondition condition = new DefaultCondition () {
				@Override
				public boolean test() throws Exception {
					return result[0];
				}

				@Override
				public String getFailureMessage() {
					return "Operation not cancelled";
				}
			};

			bot.waitUntil(condition,3 * 6 * SWTBotPreferences.TIMEOUT); //3mn
		} finally {
			ErrorDialog.AUTOMATED_MODE = false;
			ResourceManager.removeLogListener(listener);
		}
		closeWizard ();
	}
	
	private void closeWizard () {
		try {
			SWTBotShell shell = bot.shell("Generate a GraphWalker Offline Test");
			Conditions.shellCloses(shell);
		} catch (Exception e) {
		}		
	}
	
	@Test
	public void testOfflineExpandedMode() throws CoreException, BuildPolicyConfigurationException, IOException, InterruptedException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		fp.setTargetFilename("SimpleOffLineImpl");
		OfflineTestUIPageTest page = walkToToOfflinePage(gwproject,fp); 
		page.selectExtendedMode("com.company.SimpleImpl.java - gwproject/src/main/java","MyClazz");
		page.selectGenerators(new String [] {"random(edge_coverage(100))"});
		page.finish();
															  
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/company/MyClazz.java");
				boolean methodAppended = IOHelper.findInFile(resource, "Generated with : random(edge_coverage(100))");
				return methodAppended;
			}

			@Override
			public String getFailureMessage() {
				return "method not generated ";
			}
		};
		bot.waitUntil(condition, 3 * 6 * SWTBotPreferences.TIMEOUT); //3mn

		closeWizard ();
		
	}
	 
	@Test
	public void testConvertToExistingTestBasedJava( ) throws CoreException, IOException, BuildPolicyConfigurationException {
		long start = System.currentTimeMillis();
		String targetFormat = "test";
		String checkFormatText = "Java Test Based";
		testPrepareConvertTo(targetFormat,checkFormatText);
		FileParameters fp = FileParameters.create(targetFormat);
		
		String spath = PreferenceManager.getTargetFolderForTestInterface(fp.getProject(),true);
		IPath path = ResourceManager.getProject(gwproject).getFullPath().append(spath);
		
		File cacheFile =  new File (ResourceManager.toFile(path),"cache.json");
		assertTrue("Cache '" + cacheFile.getAbsolutePath() + "' does not exist ", cacheFile.exists());
		
		String key = fp.getGraphFile().getAbsolutePath();
		assertFileInCache (cacheFile,key); 
		assertFileGenerationTime (cacheFile,key,start, System.currentTimeMillis());
		// Graph file has not been modified, Generation should not happen...
		long before = readModifedTimeInCacheForFile (cacheFile,key);
		GW4EProject project = new GW4EProject(bot, gwproject);
		String [] contexts = new String [0];
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = 		project.prepareconvertTo(
						fp.getProject(),
						fp.getPackageFragmentRoot(),
						fp.getPackage(), 
						fp.getTargetFilename(), 
						targetFormat,
						checkFormatText,
						"e_StartBrowser","v_ShoppingCart", "e_ShoppingCart",contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition,3 * SWTBotPreferences.TIMEOUT);
		long after = readModifedTimeInCacheForFile (cacheFile,key);
		assertTrue("Files have been modified. It should not", before==after);
		 
		// Simulate the graph has been updated
		fp.getGraphFile().setLastModified(System.currentTimeMillis());
		 
		start = System.currentTimeMillis();
		project.prepareconvertTo(
				fp.getProject(),
				fp.getPackageFragmentRoot(),
				fp.getPackage(), 
				fp.getTargetFilename(), 
				targetFormat,
				checkFormatText,
				"e_StartBrowser","v_ShoppingCart", "e_ShoppingCart",contexts,
				fp.getGraphmlFilePath());
 
		
		long after2 = readModifedTimeInCacheForFile (cacheFile,key);
		assertTrue("Files have not been modified. It should. ", after2!=after);
		
	}
	
	private void assertFileInCache (File file,String key) throws IOException {
		 InputStream in = new FileInputStream(file);
		 try {
			Reader reader = new InputStreamReader(in);
			String text = CharStreams.toString(reader);
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(text);
			JsonObject jsonCache = je.getAsJsonObject();
			JsonElement elt = jsonCache.get(key);
			JsonObject generatedObj = elt.getAsJsonObject();
			boolean generated = generatedObj.get("generated").getAsBoolean();
			assertTrue("Test not generated", generated);
		} finally {
			if (in!=null) in.close();
		}
	}
	
	private void assertFileGenerationTime (File file,String key,long lowWaterMark, long highWaterMark) throws IOException {
		 long modified = readModifedTimeInCacheForFile (file,key);
		 assertTrue("wrong modified time for generated file ", modified > lowWaterMark);
		 assertTrue("wrong modified time for generated file ", modified < highWaterMark);
	}
	
	private long readModifedTimeInCacheForFile (File file,String key) throws IOException {
		 InputStream in =  new FileInputStream(file);
		 try {
			Reader reader = new InputStreamReader(in);
			String text = CharStreams.toString(reader);
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(text);
			JsonObject jsonCache = je.getAsJsonObject();
			JsonElement elt = jsonCache.get(key);
			JsonObject generatedObj = elt.getAsJsonObject();
			long modified = generatedObj.get("modified").getAsLong();
			return modified;
		} finally {
			if (in!=null) in.close();
		}
	}
	
	@Test
	public void testConvertToExistingModelBasedJava( ) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException   {
		String targetFormat = "java";
		testConvertToExistingResource(targetFormat,"Java Model Based");
	}
	 
	@Test
	public void testConvertToExistingJSON( ) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		String targetFormat = "json";
		testConvertToExistingResource(targetFormat,"Json");
	}	 

	@Test
	public void testConvertToExistingDot( ) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException  {
		String targetFormat = "dot";
		testConvertToExistingResource(targetFormat,"Dot");
	}

	private void testConvertToExistingResource(String targetFormat,String checkTestBox) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		testConvertTo(targetFormat,checkTestBox);
		FileParameters fp = FileParameters.create(targetFormat);
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.convertToExisting(fp.getProject(),fp.getPackageFragmentRoot(),fp.getPackage(), fp.getTargetFilename(), targetFormat,checkTestBox,fp.getGraphmlFilePath());
		
		
		
		
		IProject pj = ResourceManager.getProject(fp.getProject());
		bot.waitUntil(new PathFoundCondition(pj,fp.getPath()));
		bot.waitUntil(new EditorOpenedCondition(bot, fp.getElementName()),30 * 1000);
	}

	private OfflineTestUIPageTest walkToToOfflinePage(String gwproject,FileParameters fp) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		String targetFormat="offline";
		String checkTestBox="Java Offline Test Based";
		GW4EProject project = new GW4EProject(bot, gwproject);
		 
		
		ICondition convertPageReachedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.walkToToOfflinePage(fp.getProject(),fp.getPackageFragmentRoot(),fp.getPackage(), fp.getTargetFilename(), targetFormat,checkTestBox,fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		
		bot.waitUntil(convertPageReachedCondition, 3 * SWTBotPreferences.TIMEOUT);
		
		SWTBotShell shell = bot.shell("GW4E Conversion File");
		return new  OfflineTestUIPageTest(shell);
		 
	}
	
	private void testConvertTo(String targetFormat,String checkTestBox) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createInitialProjectWithoutError(TEST_RESOURCE_FOLDER,  PACKAGE_NAME, graphMLFilename);
		FileParameters fp = FileParameters.create(targetFormat);
		IFolder folder = ResourceManager.createFolder(fp.getMainSourceFolder(), PACKAGE_NAME);
	 
		ICondition folderExistsCondition = new FolderExists (folder);
		bot.waitUntil(folderExistsCondition, SWTBotPreferences.TIMEOUT);
		
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.convertTo(fp.getProject(),fp.getPackageFragmentRoot(),fp.getPackage(), fp.getTargetFilename(), targetFormat,checkTestBox,fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		
		bot.waitUntil(convertPageCompletedCondition, 3 * SWTBotPreferences.TIMEOUT);
		
		IProject pj = ResourceManager.getProject(fp.getProject());
		bot.waitUntil(new PathFoundCondition(pj,fp.getPath()),30 * 1000);
	}
	
	private void testPrepareConvertTo(String targetFormat,String checkTestBox) throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		 
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.createInitialProjectWithoutError(TEST_RESOURCE_FOLDER,  PACKAGE_NAME, graphMLFilename);
		FileParameters fp = FileParameters.create(targetFormat);
		ResourceManager.createFolder(fp.getMainSourceFolder(), PACKAGE_NAME);
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
						"e_StartBrowser","v_ShoppingCart", "e_ShoppingCart",contexts,
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
		bot.waitUntil(new PathFoundCondition(pj,fp.getPath()),30 * 1000);
		bot.waitUntil(new EditorOpenedCondition(bot, fp.getElementName()),30 * 1000);			
	
		String [] names = {"_beforeExecution", 
			 	 "_afterExecution",   
			 	 "_beforeElement",  
			 	 "_afterElement",   
			 	 "runSmokeTest",  
			 	 "runFunctionalTest", 
			 	 "runStabilityTest", 
				 "runModelBasedTest", 
			 	 "setUpBeforeClass", 
			 	 "tearDownAfterClass", 
			 	 "setUp", 
			 	 "tearDown"} ;
		boolean b = JDTHelper.containsMethod(fp.getPath(),names);
		assertTrue("Missing methods", b);
	}
	
	@Test
	public void testGraphmlProperties() throws Exception {
		testAddGraphmlFile();
		
		GW4EProject project = new GW4EProject(bot, gwproject);
		GraphModelProperties gp = project.getGraphmlProperties (TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);
		 
		File file = ResourceManager.getFile (gwproject,TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);
		 
		assertTrue("Invalid filename",gp.hasValidFilename());
		assertTrue("Invalid path",gp.hasValidPath());
		assertTrue("Invalid requirements",gp.hasRequirements(GraphWalkerFacade.getRequirement(file.getAbsolutePath())));
		assertTrue("Invalid methods",gp.hasMethods(GraphWalkerFacade.getMethods(file.getAbsolutePath())));
		
		gp.cancel();
	}
	
	
	
	public static class FileParameters {
		String[] graphmlFilePath = new String[4];
		String mainSourceFolder;
		String targetParentFolder;
		String targetFilename;
		 
		String targetFormat;
		private String project;
		private String pkgFragmentRoot;
		private String pkg;
		
		public FileParameters(String mainSourceFolder, String project, String pkgFragmentRoot, String pkg, String targetFilename,  String targetFormat, String[] graphmlFilePath) {
			super();
			this.graphmlFilePath = graphmlFilePath;
			this.mainSourceFolder = mainSourceFolder;
			this.targetFilename = targetFilename;
			this.pkgFragmentRoot = pkgFragmentRoot;
			this.project = project;
			this.pkg = pkg;
			this.targetFormat = targetFormat;
		}

		public static FileParameters create (String targetFormat) {
			return create (targetFormat,PACKAGE_NAME);
		}
		
		public static FileParameters create (String targetFormat,String packg) {
			String[] defaultGraphmlFolders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
			String[] graphmlFilePath = null;
			boolean addPackage = !"test".equalsIgnoreCase(targetFormat);
			graphmlFilePath = new String[4];
			graphmlFilePath[0] = gwproject;
			graphmlFilePath[1] = defaultGraphmlFolders[0];
			graphmlFilePath[2] = packg;
			graphmlFilePath[3] = graphMLFilename;

			String pkg = (addPackage ? packg : null);

			String mainSourceFolder = gwproject + "/" + PreferenceManager.getMainSourceFolder();
			 
			String targetFilename = graphMLFilename.substring(0, graphMLFilename.indexOf(".")) + (targetFormat.equalsIgnoreCase("offline") ? "OffLine" :  "") +PreferenceManager.suffixForTestImplementation(gwproject);

			return new FileParameters(mainSourceFolder, gwproject, PreferenceManager.getMainSourceFolder(), pkg, targetFilename, targetFormat, graphmlFilePath);
		}

		public File getGraphFile () throws FileNotFoundException {
			String[] localPath = getGraphmlFilePath();
			IPath path = ResourceManager.getProject(gwproject).getFullPath();
			for (int i =1; i < localPath.length; i++) {
				path = path.append(localPath[i]);
			}
			return ResourceManager.toFile(path);
		}
		
		
		public IFile getGeneratedOutPutIFile () throws FileNotFoundException {
			String path = getPath();
			File f = ResourceManager.toFile(new Path(path));
			IFile ifile =  ResourceManager.toIFile(f);
			return ifile;
		}
		
		public String getElementName () {
			return 	targetFilename + "." +  (targetFormat.equalsIgnoreCase("test")? "java" : targetFormat);
		}
		
		/**
		 * @return the graphmlFilePath
		 */
		public String[] getGraphmlFilePath() {
			return graphmlFilePath;
		}

		/**
		 * @return the mainSourceFolder
		 */
		public String getMainSourceFolder() {
			return mainSourceFolder;
		}

		/**
		 * @return the targetParentFolder
		 */
		public String getProject() {
			return project;
		}

	
		public String getPackageFragmentRoot() {
			return pkgFragmentRoot;
		}
		
		public String getPackage() {
			return pkg;
		}		 
		
		/**
		 * @return the targetFilename
		 */
		public String getTargetFilename() {
			return targetFilename;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			String targetParentFolder = mainSourceFolder + "/" + PACKAGE_NAME ;
			String path = "/" + targetParentFolder + "/" + targetFilename + "." + (targetFormat.equalsIgnoreCase("test") ? "java" : targetFormat);
			return path;
		}

		public void setTargetFilename(String targetFilename) {
			this.targetFilename = targetFilename;
		}
	}
}
