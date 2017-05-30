package org.gw4e.eclipse.container;

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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * Implements a specific classpath container initializer
 * Will help to add the GraphWalker libraries to a GW4E project
 */
public class ContainerInitializer extends ClasspathContainerInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#initialize(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	@Override
	public void initialize(IPath containerPath, IJavaProject project) throws CoreException {
		GW4ELibrariesContainer container = new GW4ELibrariesContainer(containerPath, project);
        JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { container }, null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.ClasspathContainerInitializer#canUpdateClasspathContainer(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject)
	 */
	@Override
    public boolean canUpdateClasspathContainer(IPath containerPath, IJavaProject project) {
        return true;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jdt.core.ClasspathContainerInitializer#requestClasspathContainerUpdate(org.eclipse.core.runtime.IPath, org.eclipse.jdt.core.IJavaProject, org.eclipse.jdt.core.IClasspathContainer)
     */
    @Override
    public void requestClasspathContainerUpdate(IPath containerPath, IJavaProject project, IClasspathContainer containerSuggestion) throws CoreException {
        JavaCore.setClasspathContainer(containerPath, new IJavaProject[] { project }, new IClasspathContainer[] { containerSuggestion }, null);
    }
}
