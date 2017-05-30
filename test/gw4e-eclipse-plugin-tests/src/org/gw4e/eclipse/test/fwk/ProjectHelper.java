package org.gw4e.eclipse.test.fwk;

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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.builder.GW4EParser;
import org.gw4e.eclipse.builder.marker.MissingBuildPoliciesFileMarkerResolution;
import org.gw4e.eclipse.builder.marker.SetSyncPoliciesForFileMarkerResolution;
import org.gw4e.eclipse.container.GW4ELibrariesContainer;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ICondition;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.product.GW4ENature;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion.ConversionRunnable;
import org.gw4e.eclipse.wizard.template.SharedTemplate;
import org.gw4e.eclipse.wizard.template.SimpleTemplate;

import junit.framework.TestCase;

public class ProjectHelper {

	public static void addGWClassPathEntry(IJavaProject javaProject) throws JavaModelException {
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];
		System.arraycopy(entries, 0, newEntries, 0, entries.length);
		Path lcp = new Path(GW4ELibrariesContainer.ID);
		IClasspathEntry libEntry = JavaCore.newContainerEntry(lcp, true);
		newEntries[entries.length] = JavaCore.newContainerEntry(libEntry.getPath(), true);
		javaProject.setRawClasspath(newEntries, new NullProgressMonitor());
	}

	public static IJavaProject getProject(String name) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(name);
		IJavaProject jproject = JavaCore.create(project);
		return jproject;
	}

	public static IJavaProject createSharedCompleteProject(String name) throws Exception {
		ProjectHelper.getOrCreateSharedGW4Project(name, true);
		Waiter.waitUntil(new EditorOpenedCondition("Model_AImpl.java"));
		Waiter.waitUntil(new EditorOpenedCondition("Model_BImpl.java"));
		IJavaProject jp = ProjectHelper.getProject(name);
		IMarker[] ms = ProjectHelper.getMarkers(jp, GW4EParser.MISSING_BUILD_POLICIES_FILE);
		TestCase.assertNotNull(ms);
		TestCase.assertTrue(ms.length == 2);
		jp = ProjectHelper.getProject(name);

		new MissingBuildPoliciesFileMarkerResolution().run(ms[0]);

		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_BUILD_POLICIES_FILE);
				return m == null;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		});

		jp.getProject().getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_POLICIES_FOR_FILE);
				return m != null;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		});

		ms = ProjectHelper.getMarkers(jp, GW4EParser.MISSING_POLICIES_FOR_FILE);

		for (IMarker iMarker : ms) {
			new SetSyncPoliciesForFileMarkerResolution().run(iMarker);
		}

		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_POLICIES_FOR_FILE);
				return m == null;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		});
		return jp;
	}

	public static IJavaProject createSimpleCompleteProject(String name) throws Exception {
		ProjectHelper.getOrCreateSimpleGW4EProject(name, true, true);
		Waiter.waitUntil(new EditorOpenedCondition("SimpleImpl.java"));
		IJavaProject jp = ProjectHelper.getProject(name);
		IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_BUILD_POLICIES_FILE);
		TestCase.assertNotNull(m);
		jp = ProjectHelper.getProject(name);
		m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_BUILD_POLICIES_FILE);
		TestCase.assertNotNull(m);

		new MissingBuildPoliciesFileMarkerResolution().run(m);

		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_BUILD_POLICIES_FILE);
				return m == null;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		});

		jp.getProject().getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		IMarker[] markers = new IMarker[1];
		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				markers[0]  = ProjectHelper.getMarker(jp, GW4EParser.MISSING_POLICIES_FOR_FILE);
				boolean b = (markers[0] != null);
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		},500, 15 * 1000);

		new SetSyncPoliciesForFileMarkerResolution().run(markers[0]);

		Waiter.waitUntil(new ICondition() {
			@Override
			public boolean checkCondition() throws Exception {
				IJavaProject jp = ProjectHelper.getProject(name);
				IMarker m = ProjectHelper.getMarker(jp, GW4EParser.MISSING_POLICIES_FOR_FILE);
				return m == null;
			}

			@Override
			public String getFailureMessage() {
				return "Marker not fixed";
			}
		});
		return jp;
	}

	public static IJavaProject getOrCreateSimpleGW4EProject(String name, boolean createModel, boolean generate)
			throws CoreException, InvocationTargetException, InterruptedException, TimeoutException {

		boolean autoBuilding = ResourceManager.isAutoBuilding();
		ResourceManager.setAutoBuilding(false);
		try {
			IJavaProject p = getProject(name);
			if (p == null || !p.exists()) {
				p = createProject(name);
			}
			GraphWalkerContextManager.configureProject(p.getProject());
			GW4ENature.setGW4ENature(p.getProject());
			
			Waiter.waitUntil(new ICondition () {

				@Override
				public boolean checkCondition() throws Exception {
					IJavaProject p = getProject(name); 
					IFolder folder = p.getProject().getFolder("src/test/resources");
					return folder!= null && folder.exists();
				}

				@Override
				public String getFailureMessage() {
					// TODO Auto-generated method stub
					return "src/test/resources" + " does not exists" ;
				}
				
			});
			
			if (createModel) {
				p = getProject(name); 
				SimpleTemplate provider = new SimpleTemplate();
				IFolder folder = p.getProject().getFolder("src/test/resources");
				String[] resources = provider.getResources();
				for (String resource : resources) {
					try {
						IFile file = provider.create(folder, resource, null, new NullProgressMonitor());
						provider.addCreatedResources(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				Display.getDefault().syncExec(() -> provider.openInEditor(PlatformUI.getWorkbench()));
				
				Waiter.waitUntil(new ICondition() {
					IFile f;

					@Override
					public boolean checkCondition() throws Exception {
						IJavaProject p = getProject(name);
						f = (IFile) ResourceManager.getResource(
								p.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
						return f != null && f.exists();
					}

					@Override
					public String getFailureMessage() {
						return "File " + f + " not found";
					}

				});

				if (generate) {

					IFile f = (IFile) ResourceManager.getResource(
							p.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
					IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

					AbstractPostConversion converter = GraphWalkerContextManager.getDefaultGraphConversion(f,false);
					 
					ClassExtension ce = converter.getContext().getClassExtension();
					ce.setGenerateRunFunctionalTest(true);
					ce.setStartElementForJunitTest("start_app");
					
					ConversionRunnable runnable = converter.createConversionRunnable(iww);
					runnable.run(new NullProgressMonitor());
					
					Waiter.waitUntil(new ICondition() {
						IFile interf;

						@Override
						public boolean checkCondition() throws Exception {
							IJavaProject p = getProject(name);
							interf = (IFile) ResourceManager.getResource(p.getProject().getFullPath()
									.append("target/generated-test-sources/Simple.java").toString());
							return interf != null && interf.exists();
						}

						@Override
						public String getFailureMessage() {
							return "File " + interf + " not found";
						}

					});

					Waiter.waitUntil(new ICondition() {
						IFile impl;

						@Override
						public boolean checkCondition() throws Exception {
							IJavaProject p = getProject(name);
							impl = (IFile) ResourceManager.getResource(
									p.getProject().getFullPath().append("src/test/java/SimpleImpl.java").toString());
							return impl != null && impl.exists();
						}

						@Override
						public String getFailureMessage() {
							return "File " + impl + " not found";
						}
					});
				}
			}
			ResourceManager.setAutoBuilding(true);
			waitBuild();
			p = getProject(name);
			return p;
		} finally {
			ResourceManager.setAutoBuilding(autoBuilding);
		}

	}

	public static IJavaProject getOrCreateSharedGW4Project(String name, boolean createModel)
			throws CoreException, InvocationTargetException, InterruptedException, TimeoutException {

		boolean autoBuilding = ResourceManager.isAutoBuilding();
		ResourceManager.setAutoBuilding(false);
		try {
			IJavaProject p = getProject(name);
			if (p == null || !p.exists()) {
				p = createProject(name);
			}
			GraphWalkerContextManager.configureProject(p.getProject());
			GW4ENature.setGW4ENature(p.getProject());
			if (createModel) {
				p = getProject(name);
				SharedTemplate provider = new SharedTemplate();
				 
				IFolder folder = p.getProject().getFolder("src/test/resources");
				String[] resources = provider.getResources();
				for (String resource : resources) {
					try {
						IFile file = provider.create(folder, resource, null, new NullProgressMonitor());
						provider.addCreatedResources(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				Display.getDefault().syncExec(() -> {provider.openInEditor( PlatformUI.getWorkbench());});

				Waiter.waitUntil(new ICondition() {
					IFile fA, fB;

					@Override
					public boolean checkCondition() throws Exception {
						IJavaProject p = getProject(name);
						fA = (IFile) ResourceManager.getResource(
								p.getProject().getFullPath().append("src/test/resources/Model_A.json").toString());
						fB = (IFile) ResourceManager.getResource(
								p.getProject().getFullPath().append("src/test/resources/Model_B.json").toString());
						return fA != null && fA.exists() && fB != null && fB.exists();
					}

					@Override
					public String getFailureMessage() {
						return "File " + fA + " not found or " + " File " + fB + " not found ";
					}

				});

				IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IFile fA = (IFile) ResourceManager
						.getResource(p.getProject().getFullPath().append("src/test/resources/Model_A.json").toString());
				GraphWalkerContextManager.generateDefaultGraphConversion(iww, fA, new NullProgressMonitor());

				IFile fB = (IFile) ResourceManager
						.getResource(p.getProject().getFullPath().append("src/test/resources/Model_B.json").toString());
				GraphWalkerContextManager.generateDefaultGraphConversion(iww, fB, new NullProgressMonitor());

				Waiter.waitUntil(new ICondition() {
					IFile interfA;
					IFile interfB;

					@Override
					public boolean checkCondition() throws Exception {
						IJavaProject p = getProject(name);
						interfA = (IFile) ResourceManager.getResource(p.getProject().getFullPath()
								.append("target/generated-test-sources/Model_A.java").toString());
						interfB = (IFile) ResourceManager.getResource(p.getProject().getFullPath()
								.append("target/generated-test-sources/Model_B.java").toString());
						return interfA != null && interfA.exists() && interfB != null && interfB.exists();
					}

					@Override
					public String getFailureMessage() {
						return "File " + interfA + " not found or " + " File " + interfB + " not found ";
					}

				});

				Waiter.waitUntil(new ICondition() {
					IFile implA;
					IFile implB;

					@Override
					public boolean checkCondition() throws Exception {
						IJavaProject p = getProject(name);
						implA = (IFile) ResourceManager.getResource(
								p.getProject().getFullPath().append("src/test/java/Model_AImpl.java").toString());
						implB = (IFile) ResourceManager.getResource(
								p.getProject().getFullPath().append("src/test/java/Model_BImpl.java").toString());
						return implA != null && implA.exists() && implB != null && implB.exists();
					}

					@Override
					public String getFailureMessage() {
						return "File " + implA + " not found or " + " File " + implB + " not found ";
					}
				});
			}
			ResourceManager.setAutoBuilding(true);
			waitBuild();
			p = getProject(name);
			return p;
		} finally {
			ResourceManager.setAutoBuilding(autoBuilding);
		}

	}

	public static void waitBuild() throws CoreException {
		boolean wasInterrupted = false;
		do {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, new NullProgressMonitor());
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, new NullProgressMonitor());
				wasInterrupted = false;
			} catch (OperationCanceledException ignore) {
			} catch (InterruptedException e) {
				wasInterrupted = true;
			} catch (SWTException swtex) {
				
			}
		} while (wasInterrupted);
	}

	
	
	public static void deleteProject(String name) throws CoreException, IOException, InterruptedException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		Job job = new WorkspaceJob("GW4E Delete Project Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				IProject project = root.getProject(name);
				IPath p = project.getLocation();
				if (p == null)
					return Status.OK_STATUS;
				File f = p.makeAbsolute().toFile();
				java.nio.file.Path directory = Paths.get(f.getAbsolutePath());
				try {
					java.nio.file.Files.walkFileTree(directory, new SimpleFileVisitor<java.nio.file.Path>() {
						@Override
						public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs) throws IOException {
							Files.delete(file);
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException exc) throws IOException {
							Files.delete(dir);
							return FileVisitResult.CONTINUE;
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				project.delete(true, new NullProgressMonitor());
				return Status.OK_STATUS;
			}
		};
		job.setRule(root);
		job.schedule();
		job.join();
	}

	public static IJavaProject createProject(String name) throws CoreException {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(name);
		if (!project.exists()) {
			project.create(new NullProgressMonitor());
		} else {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}

		if (!project.isOpen()) {
			project.open(new NullProgressMonitor()); 
		}

		IFolder binFolder = project.getFolder("bin");
		if (!binFolder.exists()) {
			createFolder(binFolder, false, true, new NullProgressMonitor());
		}
		IPath outputLocation = binFolder.getFullPath();

		addNatureToProject(project, JavaCore.NATURE_ID, new NullProgressMonitor());

		IJavaProject jproject = JavaCore.create(project);
		jproject.setOutputLocation(outputLocation, new NullProgressMonitor());

		IClasspathEntry[] entries = PreferenceConstants.getDefaultJRELibrary();

		jproject.setRawClasspath(entries, new NullProgressMonitor());

		return jproject;
	}

	public static void createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor)
			throws CoreException {
		if (!folder.exists()) {
			IContainer parent = folder.getParent();
			if (parent instanceof IFolder) {
				createFolder((IFolder) parent, force, local, new NullProgressMonitor());
			}
			folder.create(force, local, monitor);
		}
	}

	public static void addToClasspath(IJavaProject jproject, IClasspathEntry cpe) throws JavaModelException {
		IClasspathEntry[] oldEntries = jproject.getRawClasspath();
		for (int i = 0; i < oldEntries.length; i++) {
			if (oldEntries[i].equals(cpe)) {
				return;
			}
		}
		int nEntries = oldEntries.length;
		IClasspathEntry[] newEntries = new IClasspathEntry[nEntries + 1];
		System.arraycopy(oldEntries, 0, newEntries, 0, nEntries);
		newEntries[nEntries] = cpe;
		jproject.setRawClasspath(newEntries, new NullProgressMonitor());
	}

	public static boolean isFolderInClassPath(IJavaProject jproject, String f) throws CoreException {
		IClasspathEntry[] entries = jproject.getRawClasspath();
		IPath folder = jproject.getPath().append(f);
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getPath().equals(folder)) {
				return true;
			}
		}
		return false;
	}

	public static void addSourceAndTestFolders(IJavaProject jproject) throws CoreException {
		addFolderToClassPath(jproject, "src/main/java");
		addFolderToClassPath(jproject, "src/test/java");
	}

	public static IPackageFragmentRoot addFolderToClassPath(IJavaProject jproject, String containerName)
			throws CoreException {
		IProject project = jproject.getProject();

		IFolder folder = project.getFolder(containerName);
		if (!folder.exists()) {
			createFolder(folder, false, true, new NullProgressMonitor());
		}

		IClasspathEntry cpe = JavaCore.newLibraryEntry(folder.getFullPath(), null, null);
		addToClasspath(jproject, cpe);
		return jproject.getPackageFragmentRoot(folder);
	}

	private static void addNatureToProject(IProject proj, String natureId, IProgressMonitor monitor)
			throws CoreException {
		IProjectDescription description = proj.getDescription();
		String[] prevNatures = description.getNatureIds();
		String[] newNatures = new String[prevNatures.length + 1];
		System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
		newNatures[prevNatures.length] = natureId;
		description.setNatureIds(newNatures);
		proj.setDescription(description, monitor);
	}

	public static IMarker getMarker(IJavaProject project, int problemID) throws CoreException {
		IMarker[] markers = project.getProject().findMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		for (IMarker iMarker : markers) {
			Object attr = iMarker.getAttribute(IJavaModelMarker.ID);
			Integer pbid = (Integer) attr;
			if (pbid == null)
				continue;
			if (pbid.equals(problemID)) {
				return iMarker;
			}
		}
		return null;
	}

	public static IMarker[] getMarkers(IJavaProject project, int problemID) throws CoreException {
		IMarker[] markers = project.getProject().findMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		List<IMarker> ms = new ArrayList<IMarker>();
		for (IMarker iMarker : markers) {
			Object attr = iMarker.getAttribute(IJavaModelMarker.ID);
			Integer pbid = (Integer) attr;
			if (pbid == null)
				continue;
			if (pbid.equals(problemID)) {
				ms.add(iMarker);
			}
		}
		IMarker[] ret = new IMarker[ms.size()];
		ms.toArray(ret);
		return ret;
	}
	
	// Dont remove the import statement. Used by a specific test (testReorganizeImport)
	// Let the class being formatted in 1 line. Used by a specific test (testFormatUnitSourceCode)
	public static IFile createDummyClass (IJavaProject project) throws CoreException, IOException {
		String clazz = "import org.gw4e.core.machine.ExecutionContext ; public class Dummy extends org.gw4e.core.machine.ExecutionContext {}";
		IFolder folder = project.getProject().getFolder("src/test/java");
		IPackageFragmentRoot srcFolder = project.getPackageFragmentRoot(folder);
		IPackageFragment pkg = srcFolder.getPackageFragment("");
		ICompilationUnit cu = pkg.createCompilationUnit("Dummy.java", clazz, false, new NullProgressMonitor ());
		return (IFile) cu.getResource();
	}
	
	public static IFile createDummyClassWitherror (IJavaProject project) throws CoreException, IOException {
		String clazz = "import org.gw4e.core.machine.ExecutionContext ; public class Dummy1 extends org.gw4e.core.machine.ExecutionContext {}";
		IFolder folder = project.getProject().getFolder("src/test/java");
		IPackageFragmentRoot srcFolder = project.getPackageFragmentRoot(folder);
		IPackageFragment pkg = srcFolder.getPackageFragment("");
		ICompilationUnit cu = pkg.createCompilationUnit("Dummy.java", clazz, false, new NullProgressMonitor ());
		return (IFile) cu.getResource();
	}
}
