package org.gw4e.eclipse.refactoring.change.delete;

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

import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.ResourceManager;

public class DeleteChange extends DeleteResourceChange {
	protected IPath fResourcePath;
	protected boolean forceOutOfSync;
	protected String value;
	public DeleteChange(IPath resourcePath, boolean forceOutOfSync) {
		super(resourcePath, forceOutOfSync);
		this.fResourcePath=resourcePath;
	}

	public DeleteChange(IPath resourcePath, boolean forceOutOfSync, String value) {
		super(resourcePath, forceOutOfSync);
		this.fResourcePath=resourcePath;
		this.forceOutOfSync=forceOutOfSync;
		this.value=value;
	}
	
	@Override
	public String getName() {
		return "Remove entry in build policies file";
	}
	
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		IFile bpf = null;
		try {
			String name = fResourcePath.lastSegment();
			IFolder folder = (IFolder) ResourceManager.getResource(fResourcePath.removeLastSegments(1).toString());
			IFile graphFile = folder.getFile(name);
			bpf = BuildPolicyManager.getBuildPoliciesForGraph (graphFile);
			Properties p = BuildPolicyManager.loadBuildPolicies(bpf);
			if (this.value == null) { // a graph has been deleted
				String value = (String)p.remove(name);
				BuildPolicyManager.savePolicies(bpf, p, pm);
				return new DeleteChange(fResourcePath, this.forceOutOfSync, value);
			} else { // the undo delete 
				 p.put(name, value);
				 BuildPolicyManager.savePolicies(bpf, p, pm);
				 return new DeleteChange(fResourcePath, this.forceOutOfSync);
			}
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
		return null;		
	}
	
	
	public Change createCloseEditorChange () {
		IFolder folder = (IFolder) ResourceManager.getResource(fResourcePath.removeLastSegments(1).toString());
		IProject project = folder.getProject();
		CloseEditorChange cec =  new CloseEditorChange (project,fResourcePath);
		return cec;
	}
}
