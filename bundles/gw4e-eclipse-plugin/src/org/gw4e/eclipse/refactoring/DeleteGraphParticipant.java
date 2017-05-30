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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteArguments;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.refactoring.change.delete.DeleteChange;

public class DeleteGraphParticipant extends DeleteParticipant  implements ISharableParticipant {
    List<IFile> files = new ArrayList<IFile> ();

    public DeleteGraphParticipant() {
	}

	@Override
	protected boolean initialize(Object element) {
		DeleteArguments arguments = ((DeleteArguments)getArguments());
		return process(element, arguments);
	}
	
	private boolean process(Object element, DeleteArguments arguments) {
		if (element instanceof IFile) {
			IFile in = (IFile)element;
			if (acceptFile(in )) {
				files.add(in);
				return true;
			}
		}
		return false;
	}

	private boolean acceptFile(IFile in) {
		if (!PreferenceManager.isGraphModelFile(in))
			return false;
		IProject project = in.getProject();
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		for (int i = 0; i < folders.length; i++) {
			IPath base = project.getFullPath().append(folders[i]);
			if (base.isPrefixOf(in.getFullPath())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		return "Remove GW4E artefact(s)";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		CompositeChange cg = new  CompositeChange(getName());
		for (IFile iFile : files) {
			DeleteChange dc = new  DeleteChange(iFile.getFullPath(), true);
			cg.add(dc);
			cg.add(dc.createCloseEditorChange());
		}
		return cg;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		process(element, (DeleteArguments) arguments);
		
	}

}
