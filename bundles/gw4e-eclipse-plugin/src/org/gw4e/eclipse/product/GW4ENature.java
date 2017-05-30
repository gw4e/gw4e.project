package org.gw4e.eclipse.product;

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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * The class managing the GW4E Nature
 *
 */
public class GW4ENature implements IProjectNature {

	/**
	 * The ID of the GW4E nature
	 * ID of the nature :   Bundle-SymbolicName + ID
	 */
	public static final String NATURE_ID = "gw4e-eclipse-plugin.GW4ENature";

	/**
	 * The selected project
	 */
	private IProject project;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	@Override
	public void configure() throws CoreException {
		GraphWalkerContextManager.configureProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	@Override
	public void deconfigure() throws CoreException {
		GraphWalkerContextManager.deconfigureProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	@Override
	public IProject getProject() {
		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.
	 * resources.IProject)
	 */
	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * Has this project a GW4E nature ?
	 * 
	 * @param project
	 * @return
	 */
	public static boolean hasGW4ENature(Object project) {
		return ResourceManager.hasNature(project, NATURE_ID);
	}

	/**
	 * Remove the GW4E nature from this project This remove the nature
	 * and the GraphWalker libraries from its classpath
	 * 
	 * @param project
	 */
	public static void removeGW4ENature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			List<String> newNatures = new ArrayList<String>();
			for (String natureId : description.getNatureIds()) {
				if (!NATURE_ID.equals(natureId)) {
					newNatures.add(natureId);
				}
			}
			description.setNatureIds(newNatures.toArray(new String[newNatures.size()]));
			project.setDescription(description, null);
		} catch (CoreException e) {
			ResourceManager.logException(e);
			return;
		}
	}

	/**
	 * Set the GW4E Nature to the passed project
	 * 
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static IStatus setGW4ENature(IProject project) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);

		// add our id
		newNatures[natures.length] = GW4ENature.NATURE_ID;

		// validate the natures
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateNatureSet(newNatures);

		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		}
		return status;
	}

	
	public static IClasspathEntry[] getSourceClasspathEntries(String project) {
		IPath path1 = new Path(project).append(Constant.SOURCE_MAIN_JAVA).makeAbsolute();
		IPath path2 = new Path(project).append(Constant.SOURCE_MAIN_RESOURCES).makeAbsolute();
		IPath path3 = new Path(project).append(Constant.SOURCE_TEST_JAVA).makeAbsolute();
		IPath path4 = new Path(project).append(Constant.SOURCE_TEST_RESOURCES).makeAbsolute();
		IPath path5 = new Path(project).append(Constant.SOURCE_GENERATED_INTERFACE).makeAbsolute();
		IPath path6 = new Path(project).append(Constant.TEST_GENERATED_INTERFACE).makeAbsolute();
		return new IClasspathEntry[] { JavaCore.newSourceEntry(path1), JavaCore.newSourceEntry(path2),
				JavaCore.newSourceEntry(path3), JavaCore.newSourceEntry(path4),
				JavaCore.newSourceEntry(path5) , JavaCore.newSourceEntry(path6)};
	}

	public static IPath getOutputLocation(String project) {
		IPath path1 = new Path(project).append("classes").makeAbsolute();
		return path1;
	}
}
