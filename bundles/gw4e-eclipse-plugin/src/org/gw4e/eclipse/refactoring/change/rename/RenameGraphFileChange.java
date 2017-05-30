package org.gw4e.eclipse.refactoring.change.rename;

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
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class RenameGraphFileChange extends RenameChange {
	private IPath resourcePath;
	private IFile buildPolicyFile = null;
	public RenameGraphFileChange(IProject project, IPath resourcePath, String newName) {
		super(project,resourcePath, newName);
		this.resourcePath=resourcePath;
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		String value = null;
		try {
			Properties p = BuildPolicyManager.loadBuildPolicies(getOriginalFile());
			value = (String) p.get(getOriginalFile().getName());
			if (value == null || value.trim().length() == 0) {
				value = PreferenceManager.getDefaultPathGenerator()+";"+PreferenceManager.getDefaultSeverity(getProject().getName());
			}
		} catch (Exception e) {
			value = PreferenceManager.getDefaultPathGenerator()+";"+PreferenceManager.getDefaultSeverity(getProject().getName());
		}
		try {
			buildPolicyFile = null;
			try {
				IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(getOriginalFile());
				buildPolicyFile = (IFile)ResourceManager.getResource(buildPolicyPath.toString());
			} catch (FileNotFoundException e) {
				String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(getProject().getName());
				buildPolicyFile = ((IFolder)(getOriginalFile().getParent())).getFile(buildPolicyFilename);
				BuildPolicyManager.createBuildPoliciesFile(buildPolicyFile,pm);
			}
			BuildPolicyManager.setPolicies (buildPolicyFile,this.getNewName(),value,pm);
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException (e);
		}

		try {
			Properties p = BuildPolicyManager.loadBuildPolicies(getOriginalFile());
			value = (String) p.remove(getOriginalFile().getName());
			if (value != null && value.trim().length() > 0) {
				BuildPolicyManager.savePolicies(getOriginalFile(), p, pm);
			}
		} catch (IOException | InterruptedException e) {
			ResourceManager.logException(e);
		}
		
		return new  RenameGraphFileChange(getProject(),resourcePath, getOriginalFile().getName());
	}

	@Override
	public String getName()  {
		return "Rename " + resourcePath.toString() + " to " + resourcePath.removeLastSegments(1).append(getNewName());
	}

	@Override
	public Object getModifiedElement() {
		return buildPolicyFile;
	}

}
