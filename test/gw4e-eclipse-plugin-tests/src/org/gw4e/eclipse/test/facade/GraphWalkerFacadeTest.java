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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.Context;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.test.fwk.GenerationFactory;
import org.gw4e.eclipse.test.fwk.ProjectHelper;
import org.gw4e.eclipse.test.fwk.WorkbenchHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;
@RunWith(JUnit4.class)
public class GraphWalkerFacadeTest extends TestCase {
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
	public void testGetGraphModels() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		List<IFile> models = new ArrayList<IFile> ();
		GraphWalkerFacade.getGraphModels(jp.getProject(),models);
		assertTrue (models.size()==1);
	}

	@Test
	public void testGetSharedGraphModels() throws Exception {
		IJavaProject jp = ProjectHelper.createSharedCompleteProject(PROJECT_NAME);
		List<IFile> shared = GraphWalkerFacade.getSharedGraphModels("SHARED:B", jp.getProject());
		assertTrue (shared.size()==2);
	}

	@Test
	public void testSimilarPathGenerator() throws Exception {
		PathGenerator<StopCondition> pg1 = BuildPolicy.validPathGenerator("random(edge_coverage(100))");
		boolean b = GraphWalkerFacade.similarPathGenerator(pg1, "random(edge_coverage(80))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(edge_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(vertex_coverage(100))");
		assertFalse (b);
		
		
		pg1 = BuildPolicy.validPathGenerator("random(vertex_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(vertex_coverage(100))");
		assertTrue (b);
		
	 	pg1 = BuildPolicy.validPathGenerator("random(requirement_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(requirement_coverage(90))");
		assertTrue (b);
	 
		pg1 = BuildPolicy.validPathGenerator("random(dependency_edge_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(dependency_edge_coverage(80))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_vertex(e_SomeEdge))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(reached_vertex(e_SomeOtherEdge))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_edge(e_SomeEdge))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(reached_edge(e_SomeOtherEdge))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_vertex(e_SomeEdge) and edge_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(reached_vertex(e_SomeEdge) and edge_coverage(80))");
		assertTrue (b);
	}

	@Test
	public void testEqualsPathGenerator() throws Exception {
		PathGenerator<StopCondition> pg1 = BuildPolicy.validPathGenerator("random(edge_coverage(100))");
		boolean b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(edge_coverage(100))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(edge_coverage(100))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(vertex_coverage(100))");
		assertFalse (b);
		
		
		pg1 = BuildPolicy.validPathGenerator("random(vertex_coverage(100))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(vertex_coverage(100))");
		assertTrue (b);
		
	 	pg1 = BuildPolicy.validPathGenerator("random(requirement_coverage(100))");
		b = GraphWalkerFacade.similarPathGenerator(pg1, "random(requirement_coverage(90))");
		assertTrue (b);
	 	
		pg1 = BuildPolicy.validPathGenerator("random(dependency_edge_coverage(100))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(dependency_edge_coverage(100))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_vertex(e_SomeEdge))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(reached_vertex(e_SomeEdge))");
		assertTrue (b);
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_edge(e_SomeEdge))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(reached_edge(e_SomeOtherEdge))");
		assertFalse (b);
		
		
		pg1 = BuildPolicy.validPathGenerator("random(reached_vertex(e_SomeEdge) and edge_coverage(100))");
		b = GraphWalkerFacade.equalsPathGenerator(pg1, "random(reached_vertex(e_SomeEdge) and edge_coverage(100))");
		assertTrue (b);
	}

	@Test
	public void testGenerateFromFile() throws Exception {
		IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());

				
		GraphWalkerFacade.generateFromFile(iww, GenerationFactory.get(file), new NullProgressMonitor());
		
		IFile interf = (IFile) ResourceManager.getResource(
				pj.getProject().getFullPath().append("target/generated-test-sources/Simple.java").toString());
		assertTrue(interf.exists());
		IFile impl = (IFile) ResourceManager
				.getResource(pj.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		assertTrue(impl.exists());
	}
 
	@Test
	public void testGetNextElement() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File f = ResourceManager.toFile(file.getFullPath());
		String next = GraphWalkerFacade.getNextElement(f.getAbsolutePath());
		assertEquals("Start",next);
	}

	@Test
	public void testGetContext() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File f = ResourceManager.toFile(file.getFullPath());
		Context c = GraphWalkerFacade.getContext(f.getAbsolutePath());
		assertNotNull(c);
	}

	@Test
	public void testGetSharedContexts() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSharedGW4Project(PROJECT_NAME,true);
		IType type = pj.findType("Model_AImpl");
		IType typeB = pj.findType("Model_BImpl");
		List<IType> others = new ArrayList<IType> ();
		others.add(typeB);
		List<IType> shared = GraphWalkerFacade.getSharedContexts(pj.getProject(), type, others);
		assertEquals(shared.size(),1);
	}

	@Test
	public void testParse() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File f = ResourceManager.toFile(file.getFullPath());
		Context c = GraphWalkerFacade.getContext(f.getAbsolutePath());
		
		List<String> messages = GraphWalkerFacade.parse(c, "random(reached_vertex(unknownVertex))");
		assertEquals(messages.size(),1);
	}

	 
	@Test
	public void testGetRequirementString() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File f = ResourceManager.toFile(file.getFullPath());
		Set<String> reqs =GraphWalkerFacade.getRequirement(f.getAbsolutePath());
		assertEquals(reqs.size(),1);
		assertEquals(reqs.iterator().next(),"REQ001");
	}

	@Test
	public void testGetRequirementIFile() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		Set<String> reqs = GraphWalkerFacade.getRequirement(file);
		assertEquals(1,reqs.size());
	}

	@Test
	public void testGetMethodsString() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File f = ResourceManager.toFile(file.getFullPath());
		Set<String> methods = GraphWalkerFacade.getMethods(f.getAbsolutePath());
		assertEquals(methods.size(),6);
	}

	@Test
	public void testGetMethodsIFile() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		Set<String> methods = GraphWalkerFacade.getMethods(file);
		assertEquals(methods.size(),6);
	}

	@Test
	public void testConvert() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		File fin = ResourceManager.toFile(file.getFullPath());
		int pos = fin.getAbsolutePath().indexOf(".");
		String name = fin.getAbsolutePath().substring(pos) + ".dot"; 
		File fout = new File (fin.getParent(), name);
		String conversion = GraphWalkerFacade.convert(fin.getAbsolutePath(), fout.getAbsolutePath());
		assertTrue(conversion.length()>0);
	}

}
