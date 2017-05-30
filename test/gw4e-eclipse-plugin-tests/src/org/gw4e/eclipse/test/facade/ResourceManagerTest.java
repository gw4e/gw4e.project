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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.builder.Location;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ICondition;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.product.GW4ENature;
import org.gw4e.eclipse.test.fwk.EditorClosedCondition;
import org.gw4e.eclipse.test.fwk.IOHelper;
import org.gw4e.eclipse.test.fwk.MarkerCondition;
import org.gw4e.eclipse.test.fwk.NoMarkerCondition;
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
public class ResourceManagerTest extends TestCase {
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
		System.setProperty(DialogManager.AUTOMATE_MODE, "true");
	}

	@After
	public void tearDown() throws Exception {
		ProjectHelper.deleteProject(PROJECT_NAME);
		System.setProperty(DialogManager.AUTOMATE_MODE, "false");
	}
  
 
	 
	@Test
	public void testCloseEditor() throws Exception {
		
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		Display.getDefault().syncExec(() -> {
			try {
				IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				ResourceManager.closeEditor(impl, iww);
			} catch (PartInitException e) {
				ResourceManager.logException(e);
			}});
		 
		Waiter.waitUntil(new EditorClosedCondition("SimpleImpl.java"));
	}

	@Test
	public void testEnsureFolderIProjectStringIProgressMonitor() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		ResourceManager.ensureFolder(project.getProject(), "src/test/main", new NullProgressMonitor());
		IFolder f = project.getProject().getFolder("src/test/main");
		assertTrue(f.exists());
	}

	@Test
	public void testIsInFolder() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());

		boolean b = ResourceManager.isInFolder(file.getParent().getFullPath(), file.getFullPath());
		assertTrue(b);
	}

	@Test
	public void testCreateFileDeleteIfExistsStringStringStringIProgressMonitor() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		int[] count = new int[1];

		count[0] = 0;
		IResourceVisitor visitor = new IResourceVisitor() {
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource.getFileExtension() != null && resource.getFileExtension().equalsIgnoreCase("java")) {
					count[0] = count[0] + 1;
				}
				return true;
			}
		};
		impl.getParent().accept(visitor);
		assertTrue(count[0] == 1);

		ResourceManager.createFileDeleteIfExists(impl.getParent().getFullPath().toString(), "SimpleImpl.java", "",
				new NullProgressMonitor());
		String s = IOHelper.getContent(impl);
		assertTrue(s.length() == 0);

		count[0] = 0;
		impl.getParent().accept(visitor);
		assertTrue(count[0] == 2);
	}

	@Test
	public void testSave() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		ResourceManager.save(impl, "", new NullProgressMonitor());
		String s = IOHelper.getContent(impl);
		assertTrue(s.length() == 0);
	}

	@Test
	public void testCreateFileDeleteIfExistsFileStringIProgressMonitor() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		IFolder folder = project.getProject().getFolder("src/test/java");
		IFile file = folder.getFile("SimpleImpl1.java");

		File f = ResourceManager.createFile(file, new NullProgressMonitor());
		assertTrue(f.exists());
	}

	@Test
	public void testEnsureFolderIFolder() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		IFolder folder = project.getProject().getFolder("src/test/javaX");
		ResourceManager.ensureFolder(folder);
		folder = project.getProject().getFolder("src/test/javaX");
		assertTrue(folder.exists());
	}

	@Test
	public void testCreateFolder() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		IFolder folder = project.getProject().getFolder("src/test");
		ResourceManager.createFolder(folder.getFullPath().toString(), "kawa");
		folder = project.getProject().getFolder("src/test/kawa");
		assertTrue(folder.exists());
	}

	@Test
	public void testGetAllGraphFiles() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		List<IFile> files = new ArrayList<IFile>();
		ResourceManager.getAllGraphFiles(project.getProject(), files);
		assertEquals(1, files.size());
	}

	@Test
	public void testFindIProjectString() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IPath p = ResourceManager.find(project.getProject(), "src/test/resources/Simple.json");
		assertNotNull(p);
	}

	@Test
	public void testCreateFileStringStringStringStringString() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile f = ResourceManager.createFile(project.getProject().getName(), "src/test/resources1", "mypkg/subpkg",
				"test.txt", "content");
		assertNotNull(f);
		assertTrue(f.exists());
		String s = IOHelper.getContent(f);
		assertEquals("content", s);
	}

	@Test
	public void testGetIFileFromQualifiedName() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		assertNotNull(f);
		assertTrue(f.exists());
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg", "test.java",
				"");
		f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "mypkg.subpkg.test");
		assertNotNull(f);
		assertTrue(f.exists());
	}

	@Test
	public void testToIFileIStructuredSelection() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		IStructuredSelection selection = new StructuredSelection(f);
		IFile iFile = ResourceManager.toIFile(selection);
		assertNotNull(iFile);
	}

	@Test
	public void testCreateFileIFileIProgressMonitor() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		f.delete(true, new NullProgressMonitor());
		assertFalse(f.exists());
		ResourceManager.createFile(f, new NullProgressMonitor());
		assertTrue(f.exists());
	}

	@Test
	public void testToFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		File file = ResourceManager.toFile(f.getFullPath());
		assertNotNull(file);
		assertTrue(file.exists());
	}

	@Test
	public void testToPath() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		java.nio.file.Path p = ResourceManager.toPath(f.getFullPath());
		assertNotNull(p);
	}

	@Test
	public void testToResource() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IResource r = ResourceManager.toResource(project.getPath().append("/src/test/java/SimpleImpl.java"));
		assertNotNull(r);
		IFile f = (IFile) r;
		assertTrue(f.exists());
	}

	@Test
	public void testGetAbsolutePath() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile f = ResourceManager.getIFileFromQualifiedName(project.getProject().getName(), "SimpleImpl");
		String s = ResourceManager.getAbsolutePath(f);
		assertNotNull(s);
		assertTrue(new File(s).exists());
	}

	@Test
	public void testGetBuildPoliciesPathForGraphModel() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(PROJECT_NAME);
		IFile expectedBuildPolicyFile = ResourceManager.createFile(project.getProject().getName(), "src/test/resources",
				"", buildPolicyFilename, "");
		IPath path = ResourceManager.getBuildPoliciesPathForGraphModel(file);
		assertNotNull(path);
		IFile buildPolicyFile = (IFile) ResourceManager.toResource(path);
		assertEquals(expectedBuildPolicyFile, buildPolicyFile);
	}

	@Test
	public void testGetExistingFileInTheSameFolder() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(PROJECT_NAME);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "", buildPolicyFilename, "");
		File buildPolicyFile = ResourceManager.getExistingFileInTheSameFolder(file, buildPolicyFilename);
		assertNotNull(buildPolicyFile);
		assertTrue(buildPolicyFile.exists());
	}

	@Test
	public void testGetExtensionFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());

		String extension = ResourceManager.getExtensionFile(file);
		assertEquals("json", extension);
	}

	@Test
	public void testGetIFileAsOutputStream() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(PROJECT_NAME);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "", buildPolicyFilename, "");
		OutputStream out = null;
		try {
			out = ResourceManager.getIFileAsOutputStream(file, buildPolicyFilename);
			assertNotNull(out);
		} finally {
			if (out!=null) out.close();
		}
	}

	@Test
	public void testGetSelectedFileLocation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		String s = ResourceManager.getSelectedFileLocation(file);
		assertNotNull(s);
	}

	@Test
	public void testToIFolder() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());

		File f = ResourceManager.toFile(file.getFullPath());
		IFolder folder = ResourceManager.toIFolder(new File(f.getParent()));

		assertEquals((IFolder) file.getParent(), folder);
	}

	@Test
	public void testGetSelectedOuputFileLocation() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		IPath p = file.getParent().getFullPath();

		String s = ResourceManager.getSelectedOuputFileLocation(p, "Simple.json");
		assertTrue(new File(s).exists());
	}

	@Test
	public void testGetResource() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		IPath p = file.getParent().getFullPath();

		IResource resource = ResourceManager.getResource(p.toString());
		assertTrue(resource.exists());
	}

	@Test
	public void testGetFilteredSourceFolders() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		List<String> folders = ResourceManager.getFilteredSourceFolders(project.getProject().getName(),
				new String[] { "src/test/java" });
		assertEquals(5, folders.size());
	}

	@Test
	public void testGetProject() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IProject p = ResourceManager.getProject(PROJECT_NAME);
		assertEquals(project.getProject(), p);
	}

	@Test
	public void testGetPackageFragmentRoot() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg", "test.java",
				"");
		IPackageFragment frag = (IPackageFragment) project.findElement(new Path("mypkg/subpkg"));

		IPackageFragmentRoot pfr = ResourceManager.getPackageFragmentRoot(project.getProject(), frag);
		assertNotNull(pfr);
	}

	@Test
	public void testGetPackageFragment() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg", "test.java",
				"");
		IPackageFragment pf = ResourceManager.getPackageFragment(project.getProject(),
				new Path("src/test/resources/mypkg/subpkg"));
		assertNotNull(pf);

		IPackageFragment frag = (IPackageFragment) project.findElement(new Path("mypkg/subpkg"));
		IPackageFragmentRoot pfr = ResourceManager.getPackageFragmentRoot(project.getProject(), frag);
		pf = ResourceManager.getPackageFragment(project.getProject(), pfr.getPath().removeFirstSegments(1));
		assertNotNull(pf);
	}

	@Test
	public void testGetPathWithinPackageFragment() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile f = ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg",
				"test.java", "");
		IPath p = ResourceManager.getPathWithinPackageFragment(f);
		assertNotNull(p);
		assertEquals(new Path("mypkg/subpkg/test.java"), p);
	}

	@Test
	public void testGetSelectedPathInProject() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg", "test.java",
				"");

		IPackageFragment frag = (IPackageFragment) project.findElement(new Path("mypkg/subpkg"));
		IPackageFragmentRoot pfr = ResourceManager.getPackageFragmentRoot(project.getProject(), frag);
		String s = ResourceManager.getSelectedPathInProject(pfr);
		assertEquals("src/test/resources", s);
	}

	@Test
	public void testGetWorkspaceRoot() throws Exception {
		IWorkspaceRoot root = ResourceManager.getWorkspaceRoot();
		assertNotNull(root);
	}

	@Test
	public void testHasNature() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		boolean b = ResourceManager.hasNature(project, GW4ENature.NATURE_ID);
		assertTrue(b);
	}

	@Test
	public void testIsFileInFolders() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		boolean b = ResourceManager.isFileInFolders(impl, new String[] { "src/test/java" });
		assertTrue(b);
	}

	@Test
	public void testIsPackageFragmentRoot() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		ResourceManager.createFile(project.getProject().getName(), "src/test/resources", "mypkg/subpkg", "test.java",
				"");

		IPackageFragment frag = (IPackageFragment) project.findElement(new Path("mypkg/subpkg"));
		IPackageFragmentRoot pfr = ResourceManager.getPackageFragmentRoot(project.getProject(), frag);

		boolean b = ResourceManager.isPackageFragmentRoot(pfr);
		assertTrue(b);

		b = ResourceManager.isPackageFragmentRoot(project);
		assertFalse(b);
	}

	@Test
	public void testLoadProperties() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile file = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/build.policies").toString());

		Properties p = ResourceManager.loadProperties(ResourceManager.toFile(file.getFullPath()));
		assertNotNull(p.getProperty("Simple.json"));

	}

	@Test
	public void testLoadIFileAsProperties() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile resource = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(PROJECT_NAME);
		Properties p = ResourceManager.loadIFileAsProperties(resource, buildPolicyFilename);
		assertNotNull(p.getProperty("Simple.json"));
	}

	@Test
	public void testLocationOfKeyValue() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile file = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/build.policies").toString());
		Location location = ResourceManager.locationOfKeyValue(file, "Simple.json", "sync");
		assertEquals(3, location.getLineNumber());
	}

	@Test
	public void testLocationOfKey() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile file = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/build.policies").toString());
		Location location = ResourceManager.locationOfKey(file, "Simple.json");
		assertEquals(3, location.getLineNumber());
	}

	@Test
	public void testGetProperties() throws Exception {
		IJavaProject jp = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile file = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/build.policies").toString());

		Properties p = ResourceManager.getProperties(ResourceManager.toFile(file.getFullPath()));
		assertNotNull(p.getProperty("Simple.json"));
	}

	@Test
	public void testProjectExists() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		boolean b = ResourceManager.projectExists(project.getProject().getName());
		assertTrue(b);
	}

	@Test
	public void testFindIContainerString() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
		IPath p = ResourceManager.find(project.getProject(), impl.getFullPath().toString());
		assertNotNull(p);
	}

	@Test
	public void testGet() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, true);
		IFile impl = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());

		IFile file = ResourceManager.get(impl.getParent(), impl.getName());
		assertNotNull(file);
	}

	@Test
	public void testResourcePathExists() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		boolean b = ResourceManager.resourcePathExists(
				project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(b);
	}

	@Test
	public void testFileExists() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		boolean b = ResourceManager.fileExists(project.getProject(),
				project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(b);
	}

	@Test
	public void testDeleteFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(file.exists());
		ResourceManager.deleteFile(project.getProject(), file.getName());
		assertFalse(file.exists());
	}

	@Test
	public void testRenameFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(file.exists());
		ResourceManager.renameFile(project.getProject(), file.getName(), "foo.txt");
		assertFalse(file.exists());
		file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/foo.txt").toString());
		assertTrue(file.exists());
	}

	@Test
	public void testTouchFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		long before = file.getModificationStamp();
		Thread.sleep(10);
		ResourceManager.touchFile(file);
		file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		long after = file.getModificationStamp();
		assertTrue(before < after);
	}

	@Test
	public void testTouchFolderResources() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		long before = file.getModificationStamp();
		Thread.sleep(10);
		ResourceManager.touchFolderResources(file);
		file = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		long after = file.getModificationStamp();
		assertTrue(before < after);
	}

	@Test
	public void testStringPathToFile() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		File f = ResourceManager.stringPathToFile(
				project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		assertTrue(f.exists());
	}

	@Test
	public void testStripFileExtension() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile f = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		IStructuredSelection selection = new StructuredSelection(f);
		String s = ResourceManager.stripFileExtension(selection);
		assertEquals("Simple", s);
	}

	@Test
	public void testValidProjectPath() throws Exception {
		IJavaProject project = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		boolean b = ResourceManager.validProjectPath(project.getProject().getName());
		assert(true);
	}

	@Test
	public void testSetAutoBuilding() throws Exception {
		ResourceManager.setAutoBuilding(false);
		IJavaProject jp = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, true, false);
		IFile f = (IFile) ResourceManager
				.getResource(jp.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		jp.getProject().deleteMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		Waiter.waitUntil(new NoMarkerCondition(jp.getProject()));

		ResourceManager.setAutoBuilding(true);
		ResourceManager.touchFolderForRebuild(f);

		Waiter.waitUntil(new MarkerCondition(jp.getProject(), GW4EBuilder.MARKER_TYPE, null, IMarker.SEVERITY_ERROR));
	}

	@Test
	public void testIsAutoBuilding() throws Exception {
		ResourceManager.setAutoBuilding(true);
		boolean b = ResourceManager.isAutoBuilding();
		assertTrue(b);
		ResourceManager.setAutoBuilding(false);
		b = ResourceManager.isAutoBuilding();
		assertFalse(b);
	}

	 

	@Test
	public void testLogExceptionThrowable() throws Exception {
		ILog log = Activator.getDefault().getLog();
		String [] msg = new String [] {"",""};
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				msg [0] = status.getMessage();
				if (status.getSeverity()==IStatus.ERROR) {
					msg [1] =  "error";
				}
			}
		};
		log.addLogListener(listener);
		try {
			ResourceManager.logException(new Exception("test"));
			assertEquals("test",msg [0]);
			assertEquals("error",msg [1]);
		} finally {
			log.removeLogListener(listener);
		}
	}

	@Test
	public void testLogExceptionThrowableString() throws Exception {
		ILog log = Activator.getDefault().getLog();
		String [] msg = new String [] {"",""};
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				msg [0] = status.getMessage();
				if (status.getSeverity()==IStatus.ERROR) {
					msg [1] =  "error";
				}
			}
		};
		log.addLogListener(listener);
		try {
			ResourceManager.logException(new Exception("message"));
			assertEquals("message",msg [0]);
			assertEquals("error",msg [1]);
		} finally {
			log.removeLogListener(listener);
		}
	}

	@Test
	public void testLogInfo() throws Exception {
		ILog log = Activator.getDefault().getLog();
		String [] msg = new String [] {"",""};
		ILogListener listener = new ILogListener() {
			@Override
			public void logging(IStatus status, String plugin) {
				msg [0] = status.getMessage();
				if (status.getSeverity()==IStatus.INFO) {
					msg [1] =  "info";
				}
			}
		};
		log.addLogListener(listener);
		IJavaProject jp = null;
		try {
			jp = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
			PreferenceManager.setLogInfoEnabled (jp.getProject().getName(),true);
			ResourceManager.logInfo(jp.getProject().getName(), "infomessage");
			assertEquals("infomessage",msg [0]);
			assertEquals("info",msg [1]);
		} finally {
			PreferenceManager.setLogInfoEnabled (jp.getProject().getName(),false);
			log.removeLogListener(listener);
		}
		
	}
 

	@Test
	public void testUpdateBuildPolicyFileFor() throws Exception {
		IJavaProject project = ProjectHelper.createSimpleCompleteProject(PROJECT_NAME);
		IFile file = (IFile) ResourceManager.toResource(project.getPath().append("/src/test/java/SimpleImpl.java"));
		IFile policies = (IFile) ResourceManager
				.getResource(project.getProject().getFullPath().append("src/test/resources/build.policies").toString());

		Properties p = ResourceManager.getProperties(ResourceManager.toFile(policies.getFullPath()));
		String generators = (String) p.get("Simple.json");
		assertEquals("sync",generators);
		ResourceManager.updateBuildPolicyFileFor(file);
		
		Waiter.waitUntil(new ICondition () {
			@Override
			public boolean checkCondition() throws Exception {
				Properties p = ResourceManager.getProperties(ResourceManager.toFile(policies.getFullPath()));
				String generators = (String) p.get("Simple.json");
				return "RandomPath(EdgeCoverage(100));I;random(edge_coverage(100));I;".equals(generators);
			}

			@Override
			public String getFailureMessage() {
				return "Build policies not updated";
			}
		});		 
	}
 
}
