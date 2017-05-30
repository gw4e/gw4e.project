package org.gw4e.eclipse.builder.marker;

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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * Add a quick fix when an entry is missing in the build policy file
 * An entry corresponds to a graph model file
 */
public final class MissingPoliciesForFileMarkerResolution extends MultipleMarkerResolution {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Add default policies";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void run(IMarker marker) {
		Job job = new WorkspaceJob("GW4E Fix Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				fix(marker, monitor);
				return Status.OK_STATUS;
			}
		};
		job.setRule(marker.getResource().getProject());
		job.setUser(true);
		job.schedule();
	}

	private void fix(IMarker marker, IProgressMonitor monitor) {
		try {
			IPath graphModelPath = new Path ((String)marker.getAttribute(BuildPolicyConfigurationException.GRAPHMODELPATH));
			IFile ifile = (IFile) ResourceManager.toResource(graphModelPath);
			BuildPolicyManager.addDefaultPolicies(ifile,monitor);
			marker.delete();
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}


}
