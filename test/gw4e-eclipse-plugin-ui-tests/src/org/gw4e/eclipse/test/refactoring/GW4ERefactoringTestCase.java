package org.gw4e.eclipse.test.refactoring;

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

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.gw4e.eclipse.facade.IOHelper;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.conditions.PathFoundCondition;
import org.gw4e.eclipse.fwk.perpective.GW4EPerspective;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.refactoring.ModelRefactoring;
import org.gw4e.eclipse.fwk.source.ImportHelper;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.gw4e.eclipse.test.template.GW4ETemplateTestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4ERefactoringTestCase {
	 
	
	private static SWTWorkbenchBot bot;
	private static String gwproject = "gwproject";

	@BeforeClass
	public static void beforeClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.TIMEOUT = 3 * 10 * 1000;
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
	public void testRenameModelFile() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		String[] path = fp.getGraphmlFilePath();
		ModelRefactoring refactor = new ModelRefactoring(bot, gwproject);
		refactor.refactorModelName ("NewSimple.json",path);
		
		ICondition condition = new EditorOpenedCondition(bot,  "NewSimple.json");
		bot.waitUntil(condition,SWTBotPreferences.TIMEOUT);
	
		condition = new EditorOpenedCondition(bot,  "NewSimpleImpl.java");
		bot.waitUntil(condition,SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/company/NewSimpleImpl.java");
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"com/company/NewSimple.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/company/NewSimpleImpl.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/com/company/NewSimple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/target/generated-sources/com/company/NewSimple.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"com/company/NewSimple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
	}
	
	@Test
	public void testRenameFolderModelFiles() throws Exception {
		GW4ETemplateTestCase.beforeClass();
		new GW4ETemplateTestCase().testCreateProjectWithSharedTemplate();

		ModelRefactoring refactor = new ModelRefactoring(bot, gwproject);
		String [] nodes = new String [] {"gwproject","src/main/resources","com.company"};
		refactor.refactorRenameFolder (nodes, "aaa");
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/aaa/Model_AImpl.java");
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"aaa/Model_A.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/aaa/Model_BImpl.java");
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"aaa/Model_B.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/aaa/Model_AImpl.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/aaa/Model_A.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/aaa/Model_BImpl.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/aaa/Model_B.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/target/generated-sources/aaa/Model_A.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"aaa/Model_A.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/target/generated-sources/aaa/Model_B.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"aaa/Model_B.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
	}
	
	@Test
	public void testMoveModelFolder() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		String[] path = new String [] {"gwproject","src/main/resources","com.company"};
		ModelRefactoring refactor = new ModelRefactoring(bot, gwproject);
		String [] destination = new String [] {"gwproject","src/test/resources"};
		refactor.refactorMoveModel(path, destination,"com.company"); 
 
	}
	
	@Test
	public void testMoveModelFile() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		String[] path = fp.getGraphmlFilePath();
		ModelRefactoring refactor = new ModelRefactoring(bot, gwproject);
		String [] destination = new String [] {"gwproject","src/main/resources","com"};
		refactor.refactorMoveModel(path, destination,"Simple.json"); 
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/SimpleImpl.java");
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"com/Simple.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/main/java/com/SimpleImpl.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/com/Simple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/target/generated-sources/com/Simple.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"com/Simple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
	}
 
	@Test
	public void testMoveModelFile2() throws Exception {
		GW4EProject project = new GW4EProject(bot, gwproject);
		FileParameters fp = project.createSimpleProject ();
		String[] path = fp.getGraphmlFilePath();
		ModelRefactoring refactor = new ModelRefactoring(bot, gwproject);
		String [] destination = new String [] {"gwproject","src/test/resources"};
		
		refactor.createPackage (path,destination, "Simple.json" , "newpkg");
		
		destination = new String [] {"gwproject","src/test/resources","newpkg"};
		refactor.refactorMoveModel(path, destination,"Simple.json"); 
		
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/test/java/newpkg/SimpleImpl.java");
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"newpkg/Simple.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/src/test/java/newpkg/SimpleImpl.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/test/resources/newpkg/Simple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);

		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource("gwproject/target/generated-test-sources/newpkg/Simple.java");
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"newpkg/Simple.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
	}

	@Test
	public void testMove0Level () throws FileNotFoundException, CoreException {
		GW4EProject project = loadProjectAndConvertToGW4EProject();
		
		generateTest (project);
		
		 
		String[] nodetoselect = new String [] {project.getProjectName(),"src/test/resources","Model0Level.json"};
		
		ModelRefactoring refactor = new ModelRefactoring(bot, project.getProjectName());
		String [] destination = new String [] {project.getProjectName(),"src/main/resources"};
		
		refactor.refactorMoveModel(nodetoselect, destination, "Model0Level.json" ); 
	
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/Model0LevelImpl.java");
				if (resource==null) return false;
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"Model0Level.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/Model0LevelImpl.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/Model0Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/target/generated-sources/Model0Level.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"Model0Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		 
		project.cleanBuild();
		String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
	}
	
	
	@Test
	public void testMove1Level () throws FileNotFoundException, CoreException {
		GW4EProject project = loadProjectAndConvertToGW4EProject();
		
		generateTest (project);
		
		String pkg = "toto";
		String[] nodetoselect = new String [] {project.getProjectName(),"src/test/resources",pkg};
		
		ModelRefactoring refactor = new ModelRefactoring(bot, project.getProjectName());
		String [] destination = new String [] {project.getProjectName(),"src/main/resources"};
		
		refactor.refactorMoveModel(nodetoselect, destination, pkg ); 
	
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/toto/Model1LevelImpl.java");
				if (resource==null) return false;
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"toto/Model1Level.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/toto/Model1LevelImpl.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/toto/Model1Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/target/generated-sources/toto/Model1Level.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"toto/Model1Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		 
		project.cleanBuild();
		String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
	}
	
	
	@Test
	public void testMove2Level () throws FileNotFoundException, CoreException {
		GW4EProject project = loadProjectAndConvertToGW4EProject();
		
		generateTest (project);
		
		String pkg = "com.company";
		String[] nodetoselect = new String [] {project.getProjectName(),"src/test/resources",pkg};
		
		ModelRefactoring refactor = new ModelRefactoring(bot, project.getProjectName());
		String [] destination = new String [] {project.getProjectName(),"src/main/resources"};
		
		refactor.refactorMoveModel(nodetoselect, destination, pkg ); 
	
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/com/company/Model2LevelImpl.java");
				if (resource==null) return false;
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"com/company/Model2Level.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/com/company/Model2LevelImpl.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/com/company/Model2Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/target/generated-sources/com/company/Model2Level.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"com/company/Model2Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		 
		project.cleanBuild();
		String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
	}
	
	
	@Test
	public void testMove3Level () throws FileNotFoundException, CoreException {
		GW4EProject project = loadProjectAndConvertToGW4EProject();
		
		generateTest (project);
		
		String pkg = "a.b.c";
		String[] nodetoselect = new String [] {project.getProjectName(),"src/test/resources",pkg};
		
		ModelRefactoring refactor = new ModelRefactoring(bot, project.getProjectName());
		String [] destination = new String [] {project.getProjectName(),"src/main/resources"};
		
		refactor.refactorMoveModel(nodetoselect, destination, pkg ); 
	
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/a/b/c/Model3LevelImpl.java");
				if (resource==null) return false;
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"a/b/c/Model3Level.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/a/b/c/Model3LevelImpl.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/a/b/c/Model3Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/target/generated-sources/a/b/c/Model3Level.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"a/b/c/Model3Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		 
		project.cleanBuild();
		String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
	}
	
	@Test
	public void testMove4Level () throws FileNotFoundException, CoreException {
		GW4EProject project = loadProjectAndConvertToGW4EProject();
		
		generateTest (project);
		
		String pkg = "a.b.c.d";
		String[] nodetoselect = new String [] {project.getProjectName(),"src/test/resources",pkg};
		
		ModelRefactoring refactor = new ModelRefactoring(bot, project.getProjectName());
		String [] destination = new String [] {project.getProjectName(),"src/main/resources"};
		
		refactor.refactorMoveModel(nodetoselect, destination, pkg ); 
	
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/a/b/c/d/Model4LevelImpl.java");
				if (resource==null) return false;
				boolean fieldUpdated = IOHelper.findInFile(resource, "Paths.get(\"a/b/c/d/Model4Level.json\")");
				return fieldUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "field not Updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/src/main/java/a/b/c/d/Model4LevelImpl.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Generated (value =\"src/main/resources/a/b/c/d/Model4Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Generated not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				IFile resource = (IFile) ResourceManager.getResource(project.getProjectName()+"/target/generated-sources/a/b/c/d/Model4Level.java");
				if (resource==null) return false;
				boolean annotationUpdated = IOHelper.findInFile(resource, "@Model (file =\"a/b/c/d/Model4Level.json\")");
				return annotationUpdated;
			}

			@Override
			public String getFailureMessage() {
				return "@Model not updated ";
			}
		};
		bot.waitUntil(condition, SWTBotPreferences.TIMEOUT);
		 
		project.cleanBuild();
		String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
	}
	
	private void generateTest (GW4EProject project) {
		String[] path = new String [] {project.getProjectName(),"src/test/resources"};
		project.generateSource(path);
		bot.waitUntil(new EditorOpenedCondition(bot, "Model0LevelImpl.java"), 3 * 60000);
		bot.waitUntil(new EditorOpenedCondition(bot, "Model1LevelImpl.java"), 3 * 60000);
		bot.waitUntil(new EditorOpenedCondition(bot, "Model2LevelImpl.java"), 3 * 60000);
		bot.waitUntil(new EditorOpenedCondition(bot, "Model3LevelImpl.java"), 3 * 60000);
		bot.waitUntil(new EditorOpenedCondition(bot, "Model4LevelImpl.java"), 3 * 60000);
	}
	
	private GW4EProject loadProjectAndConvertToGW4EProject () throws CoreException, FileNotFoundException {
		String path = new File("src/test/resources/refactorProject.zip").getAbsolutePath();
		ResourcesPlugin.getWorkspace().getDescription().setAutoBuilding(false);
		ImportHelper.importProjectFromZip(bot, path);
        
        GW4EProject project = new GW4EProject(bot, "refactorProject");
        project.convertExistingProject();
        
        IFile ifile = (IFile)ResourceManager.getResource("refactorProject/src/test/resources/a/b/c/d/Model4Level.json");
        bot.waitUntil(new PathFoundCondition(ifile.getProject(),ifile.getFullPath().toString()));
		
        String[] errors = new String[] { };
		project.waitForBuildAndAssertErrors(errors);
		
		return project;
	}

}
