package org.gw4e.eclipse.refactoring;

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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class UpdateEditorAction  {
	 
	IPath previousPath;
	IFile currentFile;
	IProject project;
	public UpdateEditorAction(IProject project,IPath previousPath, IFile currentFile) {
		super();
		this.previousPath = previousPath;
		this.currentFile = currentFile;
		this.project = project;
	}

	 
	public void openclose() {
		Job job = new WorkspaceJob("GW4E Open/Close Editor Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
						if (ww != null) {
							IWorkbenchPage page = ww.getActivePage();
							if (page != null) {
								IEditorReference[] editors = page.getEditorReferences();
								for (IEditorReference iEditorReference : editors) {
									try {
										IEditorInput input = iEditorReference.getEditorInput();
										if (input instanceof FileEditorInput) {
											FileEditorInput feditorInput = (FileEditorInput) input;
											if (previousPath.equals(feditorInput.getFile().getFullPath())) {
												IEditorPart editorPart = iEditorReference.getEditor(false);
												page.closeEditor(editorPart, false);
												JDTManager.openEditor(currentFile,PreferenceManager.getGW4EEditorName(),null);
											}
										}
									} catch (PartInitException e) {
										ResourceManager.logException(e);
									}
								}
							}
						}
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.setRule(project); // lock so that we serialize the
										// refactoring
		job.setUser(true);
		job.schedule();
	}

 
}
