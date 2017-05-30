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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jdt.core.refactoring.descriptors.MoveDescriptor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.PerformRefactoringOperation;
import org.eclipse.ltk.core.refactoring.RefactoringContext;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class MoveCompilationUnitChange extends MoveChange {

	protected CompilationUnitType type;
	protected IFile graphFile;

	public MoveCompilationUnitChange(IFile graphFile, IFile originalFile, IPath destination,
			CompilationUnitType type) {
		super(originalFile, destination);
		this.type = type;
		this.graphFile = graphFile;
	}

	private IFolder computeCompilationUnitDestination() throws CoreException {
		IPath pkg = null;
		switch (type) {
		case TEST_INTERFACE:
			IProject project = graphFile.getProject();
			boolean isMain = PreferenceManager.isInMainPath(getDestination().getFile(graphFile.getName()).getFullPath());
			pkg = ResourceManager.getPathWithinPackageFragment(getDestination().getFile("foo.txt")).removeLastSegments(1);
			IPath pathFolderForTestInterface = project.getFullPath()
					.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(), isMain))
					.append(pkg);
			ResourceManager.ensureFolderPath(pathFolderForTestInterface);
			return (IFolder) ResourceManager.getResource(pathFolderForTestInterface.toString());

		case TEST_IMPLEMENTATION:
			IPath path = PreferenceManager.getTargetFolderForGeneratedTests(getDestination().getFile(graphFile.getName()));
			pkg = ResourceManager.getPathWithinPackageFragment(getDestination().getFile("foo.txt")).removeLastSegments(1);
			IPath newpath = path.append(pkg);
			ResourceManager.ensureFolderPath(newpath);
			return (IFolder) ResourceManager.getResource(newpath.toString());
		}

		throw new IllegalStateException("Unknown type " + type);
	}

	@Override
	public String getName() {
		IFolder folder = null;
		try {
			folder = computeCompilationUnitDestination();
		} catch (Exception e) {
			return "Move " + originalFile.getName() + " to ??? ";
		}
		return "Move " + originalFile.getName() + " to " + folder;
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {

		RefactoringContribution refactoringContribution = RefactoringCore
				.getRefactoringContribution(IJavaRefactorings.MOVE);
		RefactoringDescriptor desc = refactoringContribution.createDescriptor();
		MoveDescriptor moveDes = (MoveDescriptor) desc;
		moveDes.setComment("Moving " + originalFile);
		moveDes.setDescription("Moving " + originalFile);
		IFolder dest = computeCompilationUnitDestination();
		moveDes.setDestination(JavaCore.create(dest));
		moveDes.setProject(originalFile.getProject().getName());
	 
		moveDes.setMoveResources(new IFile[0], new IFolder[0],
				new ICompilationUnit[] { JavaCore.createCompilationUnitFrom(originalFile) });
		moveDes.setUpdateReferences(true);

		RefactoringStatus status = new RefactoringStatus();

		RefactoringContext context = moveDes.createRefactoringContext(status);
		PerformRefactoringOperation op = new PerformRefactoringOperation(context,
				CheckConditionsOperation.ALL_CONDITIONS);
		Job job = new WorkspaceJob("GW4E Moving Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				Display.getDefault().syncExec(() -> {
					try {
						
						op.run(monitor);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.setRule(originalFile.getProject()); // lock so that we serialize the
												// refactoring of the "test
												// interface" AND the "test
												// implementation"
		job.setUser(true);
		job.schedule();

		return op.getUndoChange();
	}
}
