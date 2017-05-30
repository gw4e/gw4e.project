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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.refactoring.change.move.MoveChange;
import org.gw4e.eclipse.refactoring.change.move.MoveGraphFileChange;

public class MoveGraphParticipant extends MoveParticipant implements ISharableParticipant {
	
	IFile originalFile = null;
	IPath destination = null;

	List<MoveChange> moveOperations = new ArrayList<MoveChange>();

	public MoveGraphParticipant() {
	}

	public MoveGraphParticipant(List<MoveChange> moveOperations) {
		this.moveOperations=moveOperations;
	}
	
	public List<MoveChange> create (IFolder source, IFolder target) {
		MoveArguments arguments = new MoveArguments(target, true);
		process(source, arguments);
		return moveOperations;
	}
	
	@Override
	protected boolean initialize(Object element) {
		return process(element, (MoveArguments) getArguments());
	}

	protected boolean process(Object element, MoveArguments arguments) {
		if (element instanceof IFile) {
			originalFile = (IFile) element;
			destination = ((IFolder) arguments.getDestination()).getFullPath();
			if (!acceptDestination(originalFile.getProject(),destination) || !acceptFile(originalFile))
				return false;
			MoveChange operation = new MoveGraphFileChange(originalFile, destination);
			moveOperations.add(operation);
			return true;
		}
		if (element instanceof IFolder) {
			destination = ((IFolder) arguments.getDestination()).getFullPath();
			
			if (!acceptDestination(((IFolder)element).getProject(), destination))
				return false;

			IFolder folder = (IFolder) element;
			try {
				boolean added = false;
				IResource[] resources = folder.members();
				for (int i = 0; i < resources.length; i++) {
					IResource resource = resources[i];
					if (resource instanceof IFile) {
						IFile originalFile = (IFile) resource;
						if (!acceptFile(originalFile))
							continue;
						
						String fileFolder = originalFile.getParent().getName();
						IPath path = destination.append(fileFolder);
						MoveChange operation = new MoveGraphFileChange(originalFile, path, false);
						moveOperations.add(operation);
						added = true;
					}
				}
				return added;
			} catch (CoreException e) {
				ResourceManager.logException(e);
				return false;
			}
		}
		return false;
	}

	private boolean acceptDestination(IProject project,IPath target) {
		 
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		for (int i = 0; i < folders.length; i++) {
			IPath base = project.getFullPath().append(folders[i]);
			if (base.isPrefixOf(target)) {
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
		return "Move GW4E artefact(s)";
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return getContentChanges(pm);
	}

	private Change getContentChanges(IProgressMonitor pm) throws CoreException {
		final CompositeChange changes = new CompositeChange(getName());
		for (MoveChange moveOperation : moveOperations) {
			
			// Update build.policies file
			changes.add(moveOperation);
			// Move the test compilation unit
			Change ci = moveOperation.createMoveChangeForTestImplementation(pm);
			if (ci != null)
				changes.add(ci);
			// Move the interface compilation unit
			Change cti = moveOperation.createMoveChangeForTestInterface(pm);
			if (cti != null)
				changes.add(cti);
			
			// Update the Generated annotation
			Change moveGeneratedAnnotationChange  = moveOperation.createMoveGeneratedAnnotation( );
			changes.add(moveGeneratedAnnotationChange);
			
			// Update the Model annotation
			Change moveModelAnnotationChange =  moveOperation.createMoveModelAnnotation( );
			changes.add(moveModelAnnotationChange);
			
			// Update Path Usage such as Path.get("foo/bar/Test.json")
			Change movePathUsageChange = moveOperation.createMovePathUsage( );
			changes.add(movePathUsageChange);
			
		    // Open/Close editors	
			changes.add(moveOperation.createEditorChangeForGraph (pm));
		}
		return changes;
	}

	@Override
	public void addElement(Object element, RefactoringArguments arguments) {
		process(element, (MoveArguments) arguments);
	}

}
