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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ICondition;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.test.fwk.IOHelper;
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
public class GraphWalkerContextManagerTest extends TestCase {
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
		ProjectHelper.createProject(PROJECT_NAME);
	}

	@After
	public void tearDown() throws Exception {
		ProjectHelper.deleteProject(PROJECT_NAME);
	}

	@Test
	public void testGetGW4EProjects() throws Exception {
		ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false,false);
		IJavaProject[] projects = GraphWalkerContextManager.getGW4EProjects();
		assertTrue(projects.length == 1);
	}

	@Test
	public void testDeconfigureProject() throws Exception {
		ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false,false);
		IJavaProject[] projects = GraphWalkerContextManager.getGW4EProjects();
		assertTrue(projects.length == 1);
		GraphWalkerContextManager.deconfigureProject(projects[0].getProject());
		Waiter.waitUntil(new ICondition () {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject[]  projects = GraphWalkerContextManager.getGW4EProjects();
				return projects.length == 0;
			}
			@Override
			public String getFailureMessage() {
				return "Project still configured";
			}
		});
	}

	@Test
	public void testConfigureProject() throws Exception {
		ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false,false);
		IJavaProject[] projects = GraphWalkerContextManager.getGW4EProjects();
		assertTrue(projects.length == 1);
	}

	@Test
	public void testGenerateDefaultGraphConversion() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true,true);

		IFile f = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(f.exists());

		IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		GraphWalkerContextManager.generateDefaultGraphConversion(iww, f, null);

		IFile interf = (IFile) ResourceManager.getResource(
				project.getProject().getFullPath().append("target/generated-test-sources/Simple.java").toString());
		assertTrue(interf.exists());
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		assertTrue(impl.exists());
	}

	@Test
	public void testGenerateFromFolder() throws Exception {
		ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
	}

	@Test
	public void testSynchronizeBuildPolicies1() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		
		IFolder folder = jp.getProject().getFolder(new Path("src/test/java"));
	 
		IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		IFile sourceFile = folder.getFile(new Path("SimpleImpl.java"));
		IOHelper.replace(sourceFile, "random(edge_coverage(100))", "random(edge_coverage(80)))");
		
		GraphWalkerContextManager.synchronizeBuildPolicies(folder, iww);
		
		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				boolean b = IOHelper.findInFile(sourceFile, "random(edge_coverage(80)))");
				return b;
			}

			@Override 
			public String getFailureMessage() {
				return "Buid policy file not updated";
			}
		});
	}

 
 
}
