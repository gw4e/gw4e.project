package org.gw4e.eclipse.test.facade;

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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.builder.Location;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.facade.AnnotationParsing;
import org.gw4e.eclipse.facade.ICondition;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.facade.TestResourceGeneration;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.test.fwk.EditorOpenedCondition;
import org.gw4e.eclipse.test.fwk.GenerationFactory;
import org.gw4e.eclipse.test.fwk.IOHelper;
import org.gw4e.eclipse.test.fwk.ProjectHelper;
import org.gw4e.eclipse.test.fwk.WorkbenchHelper;
import org.gw4e.eclipse.wizard.convert.ResourceContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;
@RunWith(JUnit4.class)
public class JDTManagerTest extends  TestCase {
	private final static String PROJECT_NAME = "gwproject";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		WorkbenchHelper.resetWorkspace();
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	@After
	public void tearDown() throws Exception {
		ProjectHelper.deleteProject(PROJECT_NAME);
	} 

	@Test
	public void testGetFullyQualifiedName() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IType type = project.findType("SimpleImpl");
		String s = JDTManager.getFullyQualifiedName(type);
		assertEquals("SimpleImpl.java", s);
	}

	@Test
	public void testGetGraphModelPath() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IType type = project.findType("SimpleImpl");
		IPath path = JDTManager.getGraphModelPath(project.getProject(),type);
		assertEquals("/" + PROJECT_NAME +"/src/test/resources/Simple.json", path.toString());
	}

	@Test
	public void testFindAnnotationParsingInGeneratedAnnotation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		 
		AnnotationParsing annoParsing = JDTManager.findAnnotationParsingInGeneratedAnnotation(compilationUnit, "value");
		Location location = annoParsing.getLocation();
		assertNotNull(location);
		 
		int line = IOHelper.findLocationLineInFile(impl, "@Generated");
		assertEquals(line,location.getLineNumber());
		
		Location loc = IOHelper.findLocationInFile(impl, line, "value = \"src/test/resources/Simple.json\"");
		assertEquals(location,loc);
		
		String value = annoParsing.getValue ( );
		assertEquals("src/test/resources/Simple.json", value);
	}

	@Test
	public void testFindAnnotationParsingInGraphWalkerAnnotation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		AnnotationParsing annoParsing = JDTManager.findAnnotationParsingInGraphWalkerAnnotation(compilationUnit, "value");
		Location location = annoParsing.getLocation();
		assertNotNull(location);
		int line = IOHelper.findLocationLineInFile(impl, "@GraphWalker");
		assertEquals(line,location.getLineNumber());
		Location loc = IOHelper.findLocationInFile(impl, line, "value = \"random(edge_coverage(100))\"");
		assertEquals(location,loc);
		
		String value = annoParsing.getValue ( );
		assertEquals("random(edge_coverage(100))", value);
	}

	@Test
	public void testFindPathGeneratorInGraphWalkerAnnotation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		String value = JDTManager.findPathGeneratorInGraphWalkerAnnotation(compilationUnit);
		assertEquals("random(edge_coverage(100))", value);
	}

	 

 
	@Test
	public void testFindGeneratorFactoryParseInvocation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		
		IOHelper.appendParseGeneratorCall (impl);
		IType type = project.findType("SimpleImpl");
		Set<String> invocations = JDTManager.findGeneratorFactoryParseInvocation(project.getProject(), type);
		assertEquals (1,invocations.size());
		String value = invocations.iterator().next();
		assertEquals("random(edge_coverage(100))", value);
	}

	@Test
	public void testParse() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		CompilationUnit cu = JDTManager.parse(compilationUnit);
		IProblem[] pbs = cu.getProblems();
		assertEquals(0, pbs.length);
	}

	@Test
	public void testHasStartableGraphWalkerAnnotation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		boolean b =  JDTManager.hasStartableGraphWalkerAnnotation(compilationUnit);
		assertEquals(true, b);
		
		IOHelper.replace(impl, ", start = \"Start\"", "");
		ICondition condition = new ICondition () {

			@Override
			public boolean checkCondition() throws Exception {
				IFile impl = (IFile) ResourceManager.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
				ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
				boolean b =  JDTManager.hasStartableGraphWalkerAnnotation(compilationUnit);
				return !b;
			}

			@Override
			public String getFailureMessage() {
				return "Replacement failed";
			}
			
		};
		
		Waiter.waitUntil(condition);
		 
	}

	@Test
	public void testIsGraphWalkerExecutionContextClass() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		boolean b = JDTManager.isGraphWalkerExecutionContextClass(compilationUnit);
		assertEquals(true, b);
	}

	@Test
	public void testGetGraphWalkerGeneratedAnnotationValue() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		String graphFilePath = JDTManager.getGW4EGeneratedAnnotationValue(compilationUnit,"value");
		assertEquals("src/test/resources/Simple.json",graphFilePath);
	}

	@Test
	public void testHasGraphWalkerAnnotation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		boolean b = JDTManager.hasGraphWalkerAnnotation(compilationUnit);
		assertEquals(true, b);
	}

	@Test
	public void testGetStartableGraphWalkerClasses() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		List<IType> types = JDTManager.getStartableGraphWalkerClasses(project.getProject().getName());
		assertTrue (types.size()==1);
		IType type = project.findType("SimpleImpl");
		assertEquals(type,types.get(0));
	}

	@Test
	public void testGetOrphanGraphWalkerClasses() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSharedGW4Project(PROJECT_NAME, true);
		IType type = project.findType("Model_AImpl");
		List<IType> types = JDTManager.getOrphanGraphWalkerClasses(type, true);
		assertTrue (types.size()==1);
		IType typeB = project.findType("Model_BImpl");
		assertEquals(typeB,types.get(0));
	}
 

	@Test
	public void testRenameClass() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		
		// The rename method only renames the class name within the file. It does not rename the file and it is expected.
		// See the comment of the JDTManager.renameClass (...) api
		// And this is why we expect 1 pb "assertEquals(1, pbs.length);" below
		IFile f = JDTManager.renameClass(impl, "SimpleImpl", "SimpleImpl1", new NullProgressMonitor());
		
		Waiter.waitUntil(new ICondition () {
			@Override
			public boolean checkCondition() throws Exception {
				IFolder folder = (IFolder) ResourceManager.getResource(project.getProject().getFullPath().append("src/test/java").toString());
				IFile frenamed = folder.getFile("SimpleImpl1.java");
				return frenamed.getName().equals("SimpleImpl1.java");
			}

			@Override
			public String getFailureMessage() {
				return "file not renamed";
			}
			
		});
		
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(f);
		CompilationUnit cu = JDTManager.parse(compilationUnit);
		IProblem[] pbs = cu.getProblems();
		assertEquals(1, pbs.length);
	}

	@Test
	public void testRename() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		JDTManager.rename(impl, new NullProgressMonitor());
		assertFalse (impl.exists());
	}

	@Test
	public void testEnrichClass() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		
		IFile impl = ProjectHelper.createDummyClass (pj);
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		IMethod m = compilationUnit.getTypes() [0].getMethod("runFunctionalTest",new String[0]);
		assertFalse (m.exists());

		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		ResourceContext context =  GenerationFactory.getResourceContext(file);
		ClassExtension ce = context.getClassExtension();
		ce.setGenerateRunFunctionalTest(true);
		ce.setStartElementForJunitTest("start_app");	
		TestResourceGeneration trg = new TestResourceGeneration(context);	 
		JDTManager.enrichClass(impl, trg, new NullProgressMonitor());
		
		m = compilationUnit.getTypes() [0].getMethod("runFunctionalTest",new String[0]);
		assertTrue (m.exists());
		
	}

	@Test
	public void testReorganizeImport() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		
		IFile impl = ProjectHelper.createDummyClass (pj);
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		int line = IOHelper.findLocationLineInFile((IFile) compilationUnit.getResource(), "import");
		assertTrue(line!=-1);
		
		JDTManager.reorganizeImport(compilationUnit);
		
		line = IOHelper.findLocationLineInFile((IFile) compilationUnit.getResource(), "import");
		assertEquals (-1,line);
	}

	@Test
	public void testFormatUnitSourceCode() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		
		IFile impl = ProjectHelper.createDummyClass (pj);
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(impl);
		int line = IOHelper.findLocationLineInFile((IFile) compilationUnit.getResource(), "Dummy");
		assertTrue(line==1);
		
		JDTManager.formatUnitSourceCode(impl, new NullProgressMonitor ());
		
		line = IOHelper.findLocationLineInFile((IFile) compilationUnit.getResource(), "Dummy");
		assertTrue(line!=1);
 
	}

	@Test
	public void testOpenDefaultEditor() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile impl = ProjectHelper.createDummyClass (pj);
		JDTManager.openDefaultEditor(impl);
		Waiter.waitUntil(new EditorOpenedCondition("Dummy.java"));
	}
 

	@Test
	public void testOpenFileEditor() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile impl = ProjectHelper.createDummyClass (pj);
		IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		JDTManager.openFileEditor(impl, "org.eclipse.ui.DefaultTextEditor", iww);
		Waiter.waitUntil(new EditorOpenedCondition("Dummy.java"));
	}

	@Test
	public void testToJavaProject() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IProject p = JDTManager.toJavaProject(pj);
		assertNotNull (p);
		p = JDTManager.toJavaProject(pj.getProject());
		assertNotNull (p);
	}

	@Test
	public void testGetPackageFragmentRoot() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IPackageFragmentRoot pfr = JDTManager.getPackageFragmentRoot(pj.getProject(), pj.getProject().getFullPath().append("src/test/java"));
		assertNotNull (pfr);
	}

}
