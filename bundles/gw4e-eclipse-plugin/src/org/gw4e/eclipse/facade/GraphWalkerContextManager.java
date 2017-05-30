package org.gw4e.eclipse.facade;

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

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.product.GW4ENature;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion;
import org.gw4e.eclipse.wizard.convert.JavaTestBasedPostConversionImpl;
import org.gw4e.eclipse.wizard.convert.OffLinePostConversionImpl;
import org.gw4e.eclipse.wizard.convert.ResourceContext;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion.ConversionRunnable;

public class GraphWalkerContextManager {

 
	public static IJavaProject[] getGW4EProjects() {
		IJavaProject[] projects;
		try {
			projects = JavaCore.create(ResourceManager.getWorkspaceRoot()).getJavaProjects();
		} catch (JavaModelException e) {
			ResourceManager.logException(e);
			projects = new IJavaProject[0];
		}
		List<IJavaProject> gwps = new ArrayList<IJavaProject>();
		for (int i = 0; i < projects.length; i++) {
			if (GW4ENature.hasGW4ENature(projects[i].getProject())) {
				gwps.add(projects[i]);
			}
		}

		IJavaProject[] gwprojects = new IJavaProject[gwps.size()];
		gwps.toArray(gwprojects);
		return gwprojects;
	}

	/*
	 * Allow to select a GW4E project among a listViewer of GW4E
	 * project
	 * 
	 * @param javaProject
	 * 
	 * @return
	 */
	public static IJavaProject chooseGW4EProject(IJavaProject javaProject) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IJavaProject[] projects = getGW4EProjects();
		ILabelProvider labelProvider = new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setTitle(MessageUtil.getString("projectdialog_title"));
		dialog.setMessage(MessageUtil.getString("projectdialog_message"));
		dialog.setElements(projects);

		if (javaProject != null) {
			dialog.setInitialSelections(new Object[] { javaProject });
		}
		if (dialog.open() == Window.OK) {
			return (IJavaProject) dialog.getFirstResult();
		}
		return null;
	}

	/**
	 * Select a graph model file among a listViewer of graph model file owned by
	 * the passed project
	 * 
	 * @param projectName
	 * @return
	 */
	public static IFile chooseGraphWalkerModel(String projectName) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		try {
			IJavaProject project = JDTManager.getJavaModel().getJavaProject(projectName);
			List<IFile> models = new ArrayList<IFile>();
			GraphWalkerFacade.getGraphModels(project.getProject(), models);
			IFile[] files = new IFile[models.size()];
			models.toArray(files);

			ILabelProvider labelProvider = new ILabelProvider() {
				@Override
				public void addListener(ILabelProviderListener arg0) {
				}

				@Override
				public void dispose() {
				}

				@Override
				public boolean isLabelProperty(Object arg0, String arg1) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void removeListener(ILabelProviderListener arg0) {
				}

				@Override
				public Image getImage(Object arg0) {
					return null;
				}

				@Override
				public String getText(Object object) {
					if (object instanceof IFile) {
						return ((IFile) object).getFullPath().toString();
					}
					return null;
				}

			};
			ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
			dialog.setTitle(MessageUtil.getString("methoddialog_title"));
			dialog.setMessage(MessageUtil.getString("methoddialog_message"));
			dialog.setElements(files);

			if (dialog.open() == Window.OK) {
				return (IFile) dialog.getFirstResult();
			}

		} catch (Exception e) {
			ResourceManager.logException(e);
			return null;
		}

		return null;
	}

	/**
	 * Remove the GW4E nature from the passed project Remove sources and
	 * tests folders Remove GW4E libraries to the project classpath
	 * Remove the GW4E Builder
	 * 
	 * @param project
	 */
	public static void deconfigureProject(IProject project) {
		Job job = new Job("GW4E Deconfigure Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor subMonitor = SubMonitor.convert(monitor, 40);
				try {
					try {
						SubMonitor sm1 = subMonitor.newChild(10);
						ClasspathManager.removeFolderFromClasspath(project,
								getTargetFolderForTestInterface(project.getName(), true), sm1);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					try {
						SubMonitor sm2 = subMonitor.newChild(10);
						ClasspathManager.removeFolderFromClasspath(project,
								getTargetFolderForTestInterface(project.getName(), false), sm2);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					try {
						SubMonitor sm3 = subMonitor.newChild(10);
						ClasspathManager.removeGW4EClassPathContainer(project, sm3);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					try {
						SubMonitor sm4 = subMonitor.newChild(10);
						ClasspathManager.unsetBuilder(project, sm4);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					try {
						GW4ENature.removeGW4ENature(project);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					try {
						PreferenceManager.removePreferences(project.getName());

					} catch (Exception e) {
						ResourceManager.logException(e);
					}
					return Status.OK_STATUS;
				} finally {
					subMonitor.done();
				}
			}
		};
		job.setUser(true);
		job.schedule();
	}

	/**
	 * Configure the passed project as a GW4E project 
	 * Add sources and tests folders 
	 * Add Graphwalker libraries to the project classpath 
	 * Add the GW4E Builder
	 * 
	 * @param project
	 */
	public static void configureProject(IProject project) {
		Job job = new WorkspaceJob("GW4E Configure Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				SubMonitor subMonitor = SubMonitor.convert(monitor, 60);
				try {
					internal_configureProject(project, subMonitor);
					return Status.OK_STATUS;
				} catch (FileNotFoundException e) {
					DialogManager.displayErrorMessage(MessageUtil.getString("project_conversion"),
							MessageUtil.getString("no_interface_in_target_folder"), e);
					ResourceManager.logException(e);
					GW4ENature.removeGW4ENature(project);
					return Status.CANCEL_STATUS;
				} catch (Throwable e) {
					DialogManager.displayErrorMessage(MessageUtil.getString("project_conversion"),
							MessageUtil.getString("an_error_has_occured_while_configuring_the_project"), e);
					ResourceManager.logException(e);
					GW4ENature.removeGW4ENature(project);
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
				}
			}

		};
		job.setRule(ResourceManager.getWorkspaceRoot());
		job.setUser(true);
		job.schedule();

	}

	public static void internal_configureProject(IProject project, SubMonitor subMonitor) throws Exception {
		
		ClasspathManager.addGW4EClassPathContainer(project);
		SubMonitor sm2 = subMonitor.newChild(10);
		ResourceManager.ensureFolder(project, Constant.SOURCE_MAIN_JAVA, sm2);
		ClasspathManager.ensureFolderInClasspath(project, Constant.SOURCE_MAIN_JAVA, sm2);
		SubMonitor sm3 = subMonitor.newChild(10);
		ResourceManager.ensureFolder(project, Constant.SOURCE_MAIN_RESOURCES, sm3);
		ClasspathManager.ensureFolderInClasspath(project, Constant.SOURCE_MAIN_RESOURCES, sm3);
		SubMonitor sm4 = subMonitor.newChild(10);
		ResourceManager.ensureFolder(project, Constant.SOURCE_TEST_JAVA, sm4);
		ClasspathManager.ensureFolderInClasspath(project, Constant.SOURCE_TEST_JAVA, sm4);
		SubMonitor sm5 = subMonitor.newChild(10);
		ResourceManager.ensureFolder(project, Constant.SOURCE_TEST_RESOURCES, sm5);
		ClasspathManager.ensureFolderInClasspath(project, Constant.SOURCE_TEST_RESOURCES, sm5);
		SubMonitor sm6 = subMonitor.newChild(10);
		
		PreferenceManager.setDefaultPreference(project.getName());
		
		// Graphs in src/main/resources will generate interfaces in
		// target/generated-sources/
		String targetMainFolderForInterface = getTargetFolderForTestInterface(project.getName(), true);
		
		IPath targetMainFolderForInterfacePath = JDTManager.guessPackageRootFragment(project, true);
		if (targetMainFolderForInterfacePath!=null) {
			targetMainFolderForInterface=  targetMainFolderForInterfacePath.makeRelativeTo(project.getFullPath()).toString();
		}
		//IResource resourceMain = ClasspathManager.childrenExistInClasspath(project, targetMainFolderForInterface, sm6);
		//if (resourceMain == null) {
			ResourceManager.ensureFolder(project, targetMainFolderForInterface, sm6);
			ClasspathManager.ensureFolderInClasspath(project, targetMainFolderForInterface, sm6);
		//}
		
		// Graphs in src/test/resources will generate interfaces in
		// target/generated-test-sources

		String targetTestFolderForInterface = getTargetFolderForTestInterface(project.getName(), false);
		IPath targetTestFolderForInterfacePath = JDTManager.guessPackageRootFragment(project, false);
		if (targetTestFolderForInterfacePath!=null) {
			targetTestFolderForInterface = targetTestFolderForInterfacePath.makeRelativeTo(project.getFullPath()).toString();
		}
	//	IResource resourceTest = ClasspathManager.childrenExistInClasspath(project, targetTestFolderForInterface, sm6);
	//	if (resourceTest == null) {
			ResourceManager.ensureFolder(project, targetTestFolderForInterface, sm6);
			ClasspathManager.ensureFolderInClasspath(project, targetTestFolderForInterface, sm6);
	//	}

		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();

		Arrays.sort(entries, new Comparator<IClasspathEntry>() {
			@Override
			public int compare(IClasspathEntry e1, IClasspathEntry e2) {
				if (e1.getEntryKind() > e2.getEntryKind())
					return 1;
				if (e1.getEntryKind() < e2.getEntryKind())
					return -1;
				return e1.getPath().toString().compareTo(e2.getPath().toString());
			}
		});

		javaProject.setRawClasspath(entries, null);

		//
		SubMonitor sm8 = subMonitor.newChild(10);
		ClasspathManager.setBuilder(project, sm8);
		//

		if (targetMainFolderForInterfacePath != null) {
			setTargetFolderForTestInterface(ResourceManager.getResource(targetMainFolderForInterfacePath.toString()), true);
		}
		if (targetTestFolderForInterfacePath != null) {
			setTargetFolderForTestInterface(ResourceManager.getResource(targetTestFolderForInterfacePath.toString()), false);
		}
 
		ICompilationUnit[] interfaces = JDTManager.getOrCreateGeneratedTestInterfaces(project);
		for (int i = 0; i < interfaces.length; i++) {
			new TestConvertor(interfaces[i]).apply();
		}
	}

	/**
	 * @param file
	 * @throws InvocationTargetException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void generateDefaultGraphConversion(IWorkbenchWindow ww, IFile file, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException, CoreException {
		AbstractPostConversion converter = getDefaultGraphConversion(file,false);
		ConversionRunnable runnable = converter.createConversionRunnable(ww);
		runnable.run(monitor);
	}

	/**
	 * @param file
	 * @return
	 * @throws CoreException
	 */
	public static AbstractPostConversion getOfflineConversion(IFile file, IPackageFragment pkg, String classfile, BuildPolicy[]  generators, int timeout) throws CoreException {
		AbstractPostConversion converter = null;
		boolean canBeConverted = PreferenceManager.isGraphModelFile(file);
		if (canBeConverted) {
			ClassExtension ce = PreferenceManager.getDefaultClassExtension(file);
			ResourceContext context = new ResourceContext(pkg.getPath(), classfile, file, true, false, false, ce);
			converter = new OffLinePostConversionImpl(context,generators,timeout);
		}
		return converter;
	}
	
	/**
	 * @param file
	 * @return
	 * @throws CoreException
	 */
	public static AbstractPostConversion getDefaultGraphConversion(IFile file,boolean generateOnlyInterface) throws CoreException {
		AbstractPostConversion converter = null;
		boolean canBeConverted = PreferenceManager.isGraphModelFile(file);
		if (canBeConverted) {
			String targetFolder = GraphWalkerContextManager.getTargetFolderForTestImplementation(file);
			IPath pkgFragmentRootPath = file.getProject().getFullPath().append(new Path(targetFolder));
			IPackageFragmentRoot implementationFragmentRoot = JDTManager.getPackageFragmentRoot(file.getProject(),
					pkgFragmentRootPath);
			String classname = file.getName().split("\\.")[0];
			classname = classname + PreferenceManager.suffixForTestImplementation(
					implementationFragmentRoot.getJavaProject().getProject().getName()) + ".java";

			ClassExtension ce = PreferenceManager.getDefaultClassExtension(file);
			IPath p = ResourceManager.getPathWithinPackageFragment(file).removeLastSegments(1);
			p = implementationFragmentRoot.getPath().append(p);
			ResourceContext context = new ResourceContext(p, classname, file, true, false, generateOnlyInterface, ce);
			converter = new JavaTestBasedPostConversionImpl(context);
		}
		return converter;
	}
	
	public static void generateOffline(final IResource resource, IPackageFragment pkg, String classfile , BuildPolicy[]  generators, int timeout, IWorkbenchWindow aww) {
		Job job = new Job("GW4E Offline Generation Source Job") {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					if (resource instanceof IFile) {
						SubMonitor subMonitor = SubMonitor.convert(monitor, 120);
						IFile file = (IFile) resource;
						if (PreferenceManager.isGraphModelFile(file)) {
							AbstractPostConversion converter = getOfflineConversion(file,pkg,classfile,generators,timeout);
							ConversionRunnable runnable = converter.createConversionRunnable(aww);
							subMonitor.subTask("Processing converter ");
							SubMonitor child = subMonitor.split(1);
							runnable.run(child);
						}						
					}
				} catch (Exception e) {
					e.printStackTrace();
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}
	
	public static void generateInterface(final IResource resource, IWorkbenchWindow aww) {
		Job job = new Job("GW4E Generation Source Job") {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					if (resource instanceof IFile) {
						SubMonitor subMonitor = SubMonitor.convert(monitor, 120);
						IFile file = (IFile) resource;
						if (PreferenceManager.isGraphModelFile(file)) {
							AbstractPostConversion converter = getDefaultGraphConversion(file, true);
							ConversionRunnable runnable = converter.createConversionRunnable(aww);
							subMonitor.subTask("Processing converter ");
							SubMonitor child = subMonitor.split(1);
							runnable.run(child);
						}						
					} else {
						if (resource instanceof IFolder) {
							generateFromFolder(resource, true, aww);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	
	public static void generateFromFolder(final IResource selectedResource, boolean generateOnlyInterface, IWorkbenchWindow aww)
			throws CoreException, InterruptedException {
		List<AbstractPostConversion> converters = new ArrayList<AbstractPostConversion>();

		Job job = new Job("GW4E Generation Source Job") {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, 120);
					selectedResource.accept(new IResourceVisitor() {
						@Override
						public boolean visit(IResource resource) throws CoreException {
							if (resource instanceof IFile) {
								IFile file = (IFile) resource;
								if (PreferenceManager.isGraphModelFile(file)) {
									AbstractPostConversion converter = getDefaultGraphConversion(file,generateOnlyInterface);
									if (converter != null) {
										converters.add(converter);
									} else {
										ResourceManager.logException(new NullPointerException(),
												"Null converter for " + file.toString());
									}
								}
							}
							return true;
						}
					});
					subMonitor.split(20);

					int max = converters.size();
					int index = 1;
					subMonitor.setWorkRemaining(max);
					for (AbstractPostConversion converter : converters) {
						if (monitor.isCanceled())
							return Status.CANCEL_STATUS;
						ConversionRunnable runnable = converter.createConversionRunnable(aww);
						subMonitor.subTask("Processing converter #" + index++);
						SubMonitor child = subMonitor.split(1);
						runnable.run(child);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		job.join();
	}

	/**
	 * @param selectedResource
	 * @return
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void synchronizeBuildPolicies(final IResource selectedResource, IWorkbenchWindow aww)
			throws CoreException, InterruptedException {
		List<ICompilationUnit> executionContexts = new ArrayList<ICompilationUnit>();

		Job job = new WorkspaceJob("GW4E Synchronization Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					SubMonitor subMonitor = SubMonitor.convert(monitor, 120);
					selectedResource.accept(new IResourceVisitor() {
						@Override
						public boolean visit(IResource resource) throws CoreException {
							if (resource instanceof IFile) {
								IFile file = (IFile) resource;
								if ("java".equals(file.getFileExtension())) {
									ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
									if (cu != null) {
										if (JDTManager.isGraphWalkerExecutionContextClass(cu)) {
											executionContexts.add(cu);
										}
									}
								}
							}
							return true;
						}
					});
					subMonitor.split(20);

					int max = executionContexts.size();
					int index = 1;
					subMonitor.setWorkRemaining(max);
					for (ICompilationUnit executionContext : executionContexts) {
						if (monitor.isCanceled())
							return Status.CANCEL_STATUS;
						subMonitor.subTask("Processing file #" + index++);
						SubMonitor child = subMonitor.split(1);
						ResourceManager.updateBuildPolicyFileForCompilatioUnit(executionContext);
					}
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(selectedResource.getProject());
		job.setUser(true);
		job.schedule();
	}

	public static boolean isFolderMainResources(IProject project, IPath path) {
		IPath sourcemainPath = project.getFullPath().append(Constant.SOURCE_MAIN_RESOURCES);
		return sourcemainPath.isPrefixOf(path);
	}

	/**
	 * Return the folder for test implementation
	 * 
	 * @param selectedFolder
	 * @return
	 */
	public static String getTargetFolderForTestImplementation(IFile file) {
		IProject project = file.getProject();
		IPath p = file.getFullPath();
		if (isFolderMainResources(project, p))
			return Constant.SOURCE_MAIN_JAVA;
		return Constant.SOURCE_TEST_JAVA;
	}

	/**
	 * Return the folder for test interface
	 * 
	 * @return
	 */
	public static String getTargetFolderForTestInterface(String projectName, boolean main) {
		return PreferenceManager.getTargetFolderForTestInterface(projectName, main);
	}

	/**
	 * @param path
	 */
	public static void setTargetFolderForTestInterface(IResource resource, boolean main) {
		PreferenceManager.setTargetFolderForTestInterface(resource, main);
	}
}
