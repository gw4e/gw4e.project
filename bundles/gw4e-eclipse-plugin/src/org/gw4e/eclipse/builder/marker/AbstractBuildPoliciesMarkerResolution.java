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

import java.io.File;
import java.io.IOException;
import java.util.Properties;

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
import org.eclipse.ui.IMarkerResolution;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.ResourceManager;

public abstract class AbstractBuildPoliciesMarkerResolution implements IMarkerResolution {
	/**
	 * 
	 */
	Properties p;
	/**
	 * 
	 */
	File buildPoliciesFile;

	/**
	 * 
	 */
	public AbstractBuildPoliciesMarkerResolution() {
	}

	/**
	 * @param marker
	 */
	protected abstract void doRun(IMarker marker, IProgressMonitor monitor);

	/**
	 * @param key
	 * @return
	 */
	protected String getValue(String key) {
		return p.getProperty(key);
	}

	/**
	 * @param key
	 * @param value
	 */
	protected void setValue(String key, String value) {
		p.setProperty(key, value);
	}

	/**
	 * @param key
	 * @return
	 */
	protected Object remove(String key) {
		return p.remove(key);
	}

	/**
	 * @param marker
	 * @return
	 * @throws CoreException
	 */
	protected String getEntryKey(IMarker marker) throws CoreException {
		IPath graphModelPath = new Path((String) marker.getAttribute(BuildPolicyConfigurationException.GRAPHMODELPATH));
		String key = graphModelPath.lastSegment();
		return key;
	}

	/**
	 * @param marker
	 * @return
	 * @throws CoreException
	 */
	protected String getPathGenerator(IMarker marker) throws CoreException {
		String pathGenerator = (String) marker.getAttribute(BuildPolicyConfigurationException.PATH_GENERATOR);
		return pathGenerator;
	}

	/**
	 * @throws InterruptedException 
	 * @throws IOException
	 * @throws CoreException
	 */
	protected void save(IProgressMonitor monitor) throws CoreException, IOException, InterruptedException   {
		IFile iFile = ResourceManager.toIFile(buildPoliciesFile);
		BuildPolicyManager.savePolicies(iFile, p, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
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
			IPath path = new Path((String) marker.getAttribute(BuildPolicyConfigurationException.BUILDPOLICIESPATH));
			buildPoliciesFile = ResourceManager.toFile(path);
			p = ResourceManager.getProperties(buildPoliciesFile);
		} catch (Exception e) {
			ResourceManager.logException(e);
			return;
		}
		doRun(marker, monitor);
		try {
			save(monitor);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}
}
