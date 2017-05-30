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

import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.refactoring.RenameSupport;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.refactoring.change.move.CompilationUnitType;

public class RenameCompilationUnitChange extends RenameChange {
	protected String newName;
	

	private final IPath resourcePath;
    private final CompilationUnitType type;
    
	public RenameCompilationUnitChange(IProject project, IPath path, String name, CompilationUnitType type) {
		super(project, path, name);
		this.resourcePath = path;
		this.newName = name;
		this.type = type;
		setValidationMethod(SAVE_IF_DIRTY);
	}

	private IResource getResource() {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(resourcePath);
	}

	private IPath getRenamedResourcePath(IPath path, String name) {
		return path.removeLastSegments(1).append(name);
	}

	private String getSuffix() {
		switch (type) {
		case TEST_INTERFACE:
			return "";

		case TEST_IMPLEMENTATION:
			return PreferenceManager.suffixForTestImplementation(getOriginalFile().getProject().getName());
		}
		throw new IllegalStateException("Unknown type " + type);
	}
	
	private String getFinalNewName () {
		String first = newName.substring(0, 1).toUpperCase();
		String finalNewName = first + newName.substring(1);
		String extensionRemoved = finalNewName.split(Pattern.quote("."))[0];
		return extensionRemoved + getSuffix() + ".java" ;
	}
	
	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		try {
			pm.beginTask("Renaming resource", 1);

			IFile resource = (IFile) getResource();
			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(resource);
			
			RenameSupport rs = RenameSupport.create(cu, getFinalNewName(), RenameSupport.UPDATE_REFERENCES);
			Job job = new WorkspaceJob("GW4E Renaming Job") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					Display.getDefault().syncExec(() -> {
						try {
							IWorkbench wb = PlatformUI.getWorkbench();
							IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
							Shell shell = Display.getDefault().getActiveShell();

						    if (shell == null) {
						        Shell[] shells = Display.getDefault().getShells();
						        for (Shell sh : shells) {
						            if (sh.getShells().length == 0) {
						                shell = sh;
						                if (shell!=null) break;
						            }
						        }
						    }
							rs.perform(shell,win);
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					});
					return Status.OK_STATUS;
				}
			};
			job.setRule(resource.getProject());  // lock so that we serialize the refactoring of   the "test interface" AND the "test implementation"
			job.setUser(true);
			job.schedule();

			IPath newPath = getRenamedResourcePath(resourcePath, newName);
			String oldName = resourcePath.lastSegment();
			return new RenameCompilationUnitChange(getProject(), newPath, oldName, type);
		} finally {
			pm.done();
		}
	}

	public IPath getTargetFilePath () {
		return resourcePath.removeLastSegments(1).append(getFinalNewName());
	}
 
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	@Override
	public String getName() {
		return "Rename " + resourcePath.toString() + " to " + getTargetFilePath ().toString();
	}

	@Override
	public Object getModifiedElement() {
		return getResource();
	}

}
