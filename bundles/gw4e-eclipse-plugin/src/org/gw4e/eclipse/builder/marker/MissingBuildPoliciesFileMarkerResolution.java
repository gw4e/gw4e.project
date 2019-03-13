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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.builder.GW4EParser;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

/**
 * Implements the quick fix when the build policy file is missing It adds a
 * build policy file in the same directory as the parsed file
 *
 */
public final class MissingBuildPoliciesFileMarkerResolution extends MultipleMarkerResolution {
	IFile buildFile = null;
	
	@Override
	public String getLabel() {
		return MessageUtil.getString("addtherequiredbuildpoliciesfile");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void run(IMarker marker) {
		IWorkbenchWindow ww=  PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Job job = new WorkspaceJob("GW4E Synchronization Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				 fix(marker,ww,monitor);
				 return Status.OK_STATUS;
			}
		};
		job.setRule(marker.getResource().getProject());
		job.setUser(true);
		job.schedule();
	}

	private void fix(IMarker marker,IWorkbenchWindow ww,IProgressMonitor monitor) {
		
		IResource resource = marker.getResource();
		if (resource instanceof IFile) {
			try {
				IFile file = (IFile) resource;
				buildFile = BuildPolicyManager.createBuildPoliciesFile(file,monitor);
				marker.delete();
				// remove all markers with this problem. The above
				// resolution fixes also all others of the same type
				IProject project = file.getProject();
				IMarker[] markers = project.findMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
				IContainer container = file.getParent();
				for (int i = 0; i < markers.length; i++) {
					IMarker m = markers[i];
					IResource r = m.getResource();
					if (r instanceof IFile) {
						IFile f = (IFile) resource;
						IContainer c = f.getParent();
						if (c.equals(container) && m.exists()) {
							Object attr = m.getAttribute(IJavaModelMarker.ID);
							Integer pbid = (Integer) attr;
							if (pbid == null)
								continue;
							if (pbid.equals(GW4EParser.MISSING_BUILD_POLICIES_FILE)) {
								m.delete();
							}
						}
					}
				}
				 
				 Display.getDefault().syncExec(new Runnable () {
					@Override
					public void run() {
						JDTManager.openEditor(buildFile, ww);
						ResourceManager.touchFolderResources(file);
					}
				 });
			} catch (Exception e) {
				ResourceManager.logException(e);
			}
		}
	}
}
