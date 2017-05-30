package org.gw4e.eclipse.refactoring.change.move;

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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.refactoring.DeleteEditorAction;

public class MoveGraphFileChange extends MoveChange {
	boolean filermove = false;

	public MoveGraphFileChange(IFile originalFile, IPath destination) {
		this(originalFile, destination, true);
	}

	public MoveGraphFileChange(IFile originalFile, IPath destination, boolean filermove) {
		super(originalFile, destination);
		this.filermove = filermove;
	}

	@Override
	public String getName() {
		return "Create an entry in build policies file for " + originalFile.getName();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		if (this.filermove) { // When the folder is moved we have nothing to do
			IFile originalBuildPoliciesIFile = null;
			String value = null;
			try {
				originalBuildPoliciesIFile = BuildPolicyManager.getBuildPoliciesForGraph(originalFile);
				Properties p = BuildPolicyManager.loadBuildPolicies(originalBuildPoliciesIFile);
				value = (String) p.get(originalFile.getName());
				if (value == null || value.trim().length() == 0) {
					value = PreferenceManager.getDefaultPathGenerator() + ";"
							+ PreferenceManager.getDefaultSeverity(originalFile.getProject().getName());
				}
			} catch (Exception e) {
				value = PreferenceManager.getDefaultPathGenerator() + ";"
						+ PreferenceManager.getDefaultSeverity(originalFile.getProject().getName());
			}
			try {
				IFile graphModel = getDestination().getFile(originalFile.getName());
				IFile buildPolicyFile = null;
				try {
					IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
					buildPolicyFile = (IFile) ResourceManager.getResource(buildPolicyPath.toString());
				} catch (FileNotFoundException e) {
					String buildPolicyFilename = PreferenceManager
							.getBuildPoliciesFileName(originalFile.getProject().getName());
					buildPolicyFile = getDestination().getFile(buildPolicyFilename);
					BuildPolicyManager.createBuildPoliciesFile(buildPolicyFile, pm);
				}
				BuildPolicyManager.setPolicies(buildPolicyFile, originalFile.getName(), value, pm);
			} catch (InterruptedException | IOException e) {
				throw new RuntimeException(e);
			}

			try {
				if (originalBuildPoliciesIFile != null) {
					Properties p = BuildPolicyManager.loadBuildPolicies(originalBuildPoliciesIFile);
					value = (String) p.remove(originalFile.getName());
					if (value != null && value.trim().length() > 0) {
						if (p.size() > 0) {
							BuildPolicyManager.savePolicies(originalFile, p, pm);
						} else {
							DeleteEditorAction action = new DeleteEditorAction(originalBuildPoliciesIFile.getProject(),
									originalBuildPoliciesIFile.getFullPath());
							action.run();
							originalBuildPoliciesIFile.delete(true, pm);
						}
					}
				}
			} catch (Exception e) {
				ResourceManager.logException(e);
			}
		}
		return new MoveGraphFileChange(getDestination().getFile(originalFile.getName()),
				((IFolder) originalFile.getParent()).getFullPath());
	}

}
