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

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.facade.ClasspathManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.test.fwk.ProjectHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;
@RunWith(JUnit4.class)
public class ClasspathManagerTest extends TestCase{

	private final static  String PROJECT_NAME="gwproject";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
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
	public void testIsMavenInstalled() throws Exception {
		boolean installed = ClasspathManager.isMavenInstalled (); 
		boolean b = JavaCore.getClasspathVariable("M2_REPO") != null;
		assertTrue("Invalid Maven detection property ", installed == b);
	}

	@Test
	public void testHasGraphWalkerClassPathContainer() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		boolean has = ClasspathManager.hasGW4EClassPathContainer(p.getProject());
		assertFalse("Project has no GW Library ", has);
	}
 
	@Test
	public void testAddGraphWalkerClassPathContainer() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		ClasspathManager.addGW4EClassPathContainer(p.getProject());
		boolean has = ClasspathManager.hasGW4EClassPathContainer(p.getProject());
		assertTrue("Project has GW Library ", has);
	}
 
	@Test
	public void testChildrenExistInClasspath() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		IResource resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "src", null);
		assertNull(resource);
		
		ProjectHelper.addSourceAndTestFolders(p);
		ProjectHelper.addFolderToClassPath(p, "target/generated-sources");
		
		resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "target/generated-sources", null);
		assertNotNull(resource);
		
	}

	@Test
	public void testEnsureFolderInClasspath() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		IResource resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "src", null);
		assertNull(resource);
		IClasspathEntry entry = ClasspathManager.ensureFolderInClasspath(p.getProject(), "src", null);
		assertNotNull(entry);
		boolean classpathentryFound = ProjectHelper.isFolderInClassPath(p, "src");
		assertTrue(classpathentryFound);
	}

	@Test
	public void testRemoveGraphWalkerClassPathContainer() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		ClasspathManager.addGW4EClassPathContainer(p.getProject());
		boolean has = ClasspathManager.hasGW4EClassPathContainer(p.getProject());
		assertTrue("Project has GW Library ", has);
		ClasspathManager.removeGW4EClassPathContainer(p.getProject(), null);
		has = ClasspathManager.hasGW4EClassPathContainer(p.getProject());
		assertFalse("Project has not GW Library ", has);
	}

	@Test
	public void testRemoveFolderFromClasspath() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		IResource resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "src", null);
		assertNull(resource);
		
		ProjectHelper.addSourceAndTestFolders(p);
		ProjectHelper.addFolderToClassPath(p, "target/generated-sources");
		resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "target/generated-sources", null);
		assertNotNull(resource);
		
		ClasspathManager.removeFolderFromClasspath(p.getProject(), "target/generated-sources", null);
		resource = ClasspathManager.childrenExistInClasspath(p.getProject(), "target/generated-sources", null);
		assertNull(resource);
	}

	@Test
	public void testSetBuilder() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		ClasspathManager.setBuilder(p.getProject(), null);
		IProjectDescription desc = p.getProject().getDescription();
		ICommand[] commands = desc.getBuildSpec();
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(GW4EBuilder.BUILDER_ID)) {
				found=true;
			}
		}
		assertTrue(found);
	}

	@Test
	public void testUnsetBuilder() throws Exception {
		IJavaProject p = ProjectHelper.getProject(PROJECT_NAME);
		ClasspathManager.setBuilder(p.getProject(), null);
		IProjectDescription desc = p.getProject().getDescription();
		ICommand[] commands = desc.getBuildSpec();
		boolean found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(GW4EBuilder.BUILDER_ID)) {
				found=true;
			}
		}
		assertTrue(found);
		ClasspathManager.unsetBuilder(p.getProject(), null);
		desc = p.getProject().getDescription();
		commands = desc.getBuildSpec();
		found = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(GW4EBuilder.BUILDER_ID)) {
				found=true;
			}
		}
		assertFalse(found);
	}
 
}
