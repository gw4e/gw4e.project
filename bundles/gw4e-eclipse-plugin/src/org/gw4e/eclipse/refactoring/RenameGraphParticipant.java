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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.refactoring.change.move.MoveChange;
import org.gw4e.eclipse.refactoring.change.rename.RenameChange;
import org.gw4e.eclipse.refactoring.change.rename.RenameGeneratedAnnotation;
import org.gw4e.eclipse.refactoring.change.rename.RenameGraphFileChange;
import org.gw4e.eclipse.refactoring.change.rename.RenameModelAnnotation;
import org.gw4e.eclipse.refactoring.change.rename.RenamePathUsage;

public class RenameGraphParticipant extends RenameParticipant implements ISharableParticipant  {
	IFile originalFile = null;
	String newName = null;
	List<RenameChange> renameOperations = new ArrayList<RenameChange>();
	List<MoveChange> moveOperations = new ArrayList<MoveChange>();
	 
	public RenameGraphParticipant() {
		super();
	}

	private boolean process(Object element, RenameArguments arguments) {
		if (element instanceof IFile) {
			originalFile = (IFile) element;
			newName =   arguments.getNewName();
			if (!acceptFile(originalFile))
				return false;
			RenameChange operation = new RenameGraphFileChange(originalFile.getProject(),originalFile.getFullPath(), newName);
			renameOperations.add(operation);
			return true;
		}
		if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			MoveGraphParticipant mgp = new MoveGraphParticipant();
			moveOperations.addAll(mgp.create(folder,(folder.getParent().getFolder(new Path(arguments.getNewName())))));
			return moveOperations.size()>0;
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
	protected boolean initialize(Object element) {
		return process(element, (RenameArguments) getArguments());
	}

	@Override
	public String getName() {
		return "Rename GW4E artefact(s)";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final CompositeChange changes = new CompositeChange(getName());
		for (RenameChange renameOperation : renameOperations) {
			
			// Update build.policies file
			changes.add(renameOperation);
			// Rename the test compilation unit
			Change ci = renameOperation.createRenameChangeForTestImplementation(pm);
			if (ci != null)
				changes.add(ci);
			// rename the interface compilation unit
			Change cti = renameOperation.createRenameChangeForTestInterface(pm);
			if (cti != null)
				changes.add(cti);
			
			// Update the Generated annotation
			Change renameGeneratedAnnotationChange  = new RenameGeneratedAnnotation (originalFile.getProject(),originalFile.getFullPath(),newName);
			changes.add(renameGeneratedAnnotationChange);
			
			// Update the Model annotation
			Change renameModelAnnotationChange = new RenameModelAnnotation (originalFile.getProject(),originalFile.getFullPath(),newName);
			changes.add(renameModelAnnotationChange);
			
			// Update Path Usage such as Path.get("foo/bar/Test.json")
			Change renamePathUsageChange = new RenamePathUsage (originalFile.getProject(),originalFile.getFullPath(),newName);
			changes.add(renamePathUsageChange);
			
			// Open/Close editors 
			changes.add(renameOperation.createEditorChangeForGraph(pm)); 
		}
		
		// When renaming a folder, it becomes a Move...
		MoveGraphParticipant  mgp = new MoveGraphParticipant(moveOperations);
		Change moveChange = mgp.createChange(new NullProgressMonitor ());
		changes.add(moveChange);
		
		return changes;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		process(element, (RenameArguments) arguments);
	}

}
