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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.junit.JUnitCore;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.container.GW4ELibrariesContainer;
 

public class ClasspathManager   {

	/**
	 * @return true if the M2_REPO exists in the workbench
	 */
	public static boolean isMavenInstalled() {
		return JavaCore.getClasspathVariable("M2_REPO") != null;
	}

	/**
	 * 
	 * @param project
	 * @return whether the project has the GW4E ClassPathContainer
	 * @throws JavaModelException
	 */
	public static boolean hasGW4EClassPathContainer(IProject project) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getPath().toString().startsWith(GW4ELibrariesContainer.ID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add GraphWalker libraries to the passed project
	 * 
	 * @param project
	 * @throws JavaModelException
	 */
	public static void addGW4EClassPathContainer(IProject project) throws JavaModelException {
		if (hasGW4EClassPathContainer(project)) {
			return;
		}
		IJavaProject javaProject = JavaCore.create(project); 
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];
		System.arraycopy(entries, 0, newEntries, 0, entries.length);
		Path lcp = new Path(GW4ELibrariesContainer.ID);
		IClasspathEntry libEntry = JavaCore.newContainerEntry(lcp, true);
		newEntries[entries.length] = JavaCore.newContainerEntry(libEntry.getPath(), true);
		javaProject.setRawClasspath(newEntries, null);

	  	addJunit4Libraries(project);
	}

	/**
	 * Add JUnit libraries to the passed project
	 * 
	 * @param project
	 * @throws JavaModelException
	 */
	private static void addJunit4Libraries(IProject project) throws JavaModelException {
		IClasspathEntry entry = JavaCore.newContainerEntry(JUnitCore.JUNIT4_CONTAINER_PATH);
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		boolean junitFound = false;
		String s = entry.getPath().toString();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getPath().toString().indexOf(s) != -1) {
				junitFound = true;
				break;
			}
		}
		if (!junitFound) {
			IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];
			System.arraycopy(entries, 0, newEntries, 0, entries.length);
			newEntries[entries.length] = entry;
			javaProject.setRawClasspath(newEntries, null);
		}
	}

	/**
	 * Manage source folders exclusion
	 * 
	 * @param project
	 * @param rootSrcEntry
	 * @param relative
	 * @return
	 * @throws JavaModelException
	 */
	private static IClasspathEntry ensureExcludedPath(IProject project, IClasspathEntry rootSrcEntry,
			String relative) throws JavaModelException {
		if (rootSrcEntry == null)
			return rootSrcEntry;
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		IPath[] excluded = rootSrcEntry.getExclusionPatterns();
		boolean entryFound = false;
		for (int i = 0; i < excluded.length; i++) {
			if (excluded[i].toString().equalsIgnoreCase(relative)) {
				entryFound = true;
				break;
			}
		}
		if (!entryFound) {
			IPath rootSrcPath = javaProject.getPath().append("src");
			IPath[] newEntries = new IPath[excluded.length + 1];
			System.arraycopy(excluded, 0, newEntries, 0, excluded.length);
			newEntries[excluded.length] = new Path(relative);
			rootSrcEntry = JavaCore.newSourceEntry(rootSrcPath, newEntries);
			entries = javaProject.getRawClasspath();
			List<IClasspathEntry> temp = new ArrayList<IClasspathEntry>();
			temp.add(rootSrcEntry);
			for (int i = 0; i < entries.length; i++) {
				if (!(entries[i].getPath().equals(rootSrcPath))) {
					temp.add(entries[i]);
				}
			}
			IClasspathEntry[] array = new IClasspathEntry[temp.size()];
			temp.toArray(array);
			javaProject.setRawClasspath(array, null);
		}
		return rootSrcEntry;
	}

	/**
	 * Return whether folderPath has children in the classpath
	 * 
	 * @param project
	 * @param folderPath
	 * @param monitor
	 * @return
	 * @throws JavaModelException
	 */
	public static IResource childrenExistInClasspath(IProject project, String folderPath, IProgressMonitor monitor)
			throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		IPath folder = project.getFolder(folderPath).getFullPath();
		for (int i = 0; i < entries.length; i++) {
			if (folder.isPrefixOf(entries[i].getPath())) {
				IPath path = entries[i].getPath();
				IResource resource = ResourceManager.getResource(path.toString());
				return resource;
			}
		}
		return null;
	}

	/**
	 * Make sure the passed folder is in the classpath
	 * 
	 * @param project
	 * @param folderPath
	 * @param monitor
	 * @return
	 * @throws JavaModelException
	 */
	public static IClasspathEntry ensureFolderInClasspath(IProject project, String folderPath,
			IProgressMonitor monitor) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);

		if (folderPath.startsWith("src")) {
			handleFolderExclusion(project, folderPath);
		}

		IClasspathEntry[] entries = javaProject.getRawClasspath();

		boolean classpathentryFound = false;
		IPath folder = project.getFolder(folderPath).getFullPath();
		for (int i = 0; i < entries.length; i++) {

			if (entries[i].getPath().equals(folder)) {
				classpathentryFound = true;
				return entries[i];
			}
		}
		if (!classpathentryFound) {
			IClasspathEntry[] newEntries = new IClasspathEntry[entries.length + 1];
			System.arraycopy(entries, 0, newEntries, 0, entries.length);
			IPath srcPath = javaProject.getPath().append(folderPath);
			IClasspathEntry srcEntry = JavaCore.newSourceEntry(srcPath, null);
			newEntries[entries.length] = JavaCore.newSourceEntry(srcEntry.getPath());
			javaProject.setRawClasspath(newEntries, monitor);
			return srcEntry;
		}
		return null;
	}

	/**
	 * Manage source folders exclusion
	 * 
	 * @param project
	 * @param folderPath
	 * @throws JavaModelException
	 */
	private static void handleFolderExclusion(IProject project, String folderPath) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		IClasspathEntry rootSrcEntry = null;
		IPath srcPath = javaProject.getPath().append("src");
		for (int i = 0; i < entries.length; i++) {
			if ((entries[i].getPath().equals(srcPath))) {
				rootSrcEntry = entries[i];
				break;
			}
		}

		// 'src' folder by itslef is not in the build path ...
		if (rootSrcEntry == null)
			return;

		String relative = folderPath.substring("src/".length()).concat("/");

		StringTokenizer st = new StringTokenizer(relative, "/");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			sb.append(temp).append("/");
			rootSrcEntry = ClasspathManager.ensureExcludedPath(project, rootSrcEntry, sb.toString());
		}

	}

	/**
	 * Remove GW4E ClassPath Container
	 * 
	 * @param project
	 * @param monitor
	 * @throws JavaModelException
	 */
	public static void removeGW4EClassPathContainer(IProject project, IProgressMonitor monitor)
			throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		List<IClasspathEntry> newEntries = new ArrayList<IClasspathEntry>();
		for (int i = 0; i < entries.length; i++) {
			if (!GW4ELibrariesContainer.isMe(entries[i])) {
				newEntries.add(entries[i]);
			}
		}
		entries = new IClasspathEntry[newEntries.size()];
		newEntries.toArray(entries);
		javaProject.setRawClasspath(entries, monitor);
	}

	/**
	 * Remove the passed folder from ClassPath
	 * 
	 * @param project
	 * @param folderPath
	 * @param monitor
	 * @throws JavaModelException
	 */
	public static void removeFolderFromClasspath(IProject project, String folderPath, IProgressMonitor monitor)
			throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		List<IClasspathEntry> newEntries = new ArrayList<IClasspathEntry>();
		IPath folder = project.getFolder(folderPath).getFullPath();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].getPath().equals(folder)) {
				newEntries.add(entries[i]);
			}
		}
		entries = new IClasspathEntry[newEntries.size()];
		newEntries.toArray(entries);
		javaProject.setRawClasspath(entries, monitor);

	}

	 

	/**
	 * Set the GW4E builder
	 * 
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 */
	public static void setBuilder(IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(GW4EBuilder.BUILDER_ID)) {
				return;
			}
		}

		ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		ICommand command = desc.newCommand();
		command.setBuilderName(GW4EBuilder.BUILDER_ID);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
		project.setDescription(desc, null);
	}

	/**
	 * Remove the GW4E builder
	 * 
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 */
	public static void unsetBuilder(IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(GW4EBuilder.BUILDER_ID)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
				description.setBuildSpec(newCommands);
				project.setDescription(description, null);
				GW4EBuilder.removeProjectProblemMarker(project, monitor);
				return;
			}
		}
	}

}
