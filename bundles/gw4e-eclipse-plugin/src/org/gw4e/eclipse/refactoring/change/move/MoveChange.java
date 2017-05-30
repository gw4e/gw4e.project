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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.refactoring.Helper;

public abstract class MoveChange extends Change {

	protected IFile originalFile;
	protected IPath destination;

	public MoveChange(IFile originalFile, IPath destination) {
		super();
		this.originalFile = originalFile;
		this.destination = destination;
	}
	
	
	protected IFolder getDestination () {
		return (IFolder)ResourceManager.getResource(destination.toString());
	}

	@Override
	public void initializeValidationData(IProgressMonitor pm) {
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Object getModifiedElement() {
		return originalFile;
	}

	public Change createEditorChangeForGraph(IProgressMonitor pm) {
		return new MoveEditorChange(originalFile.getProject(),originalFile,destination);
	}
	
	public Change createMoveChangeForTestImplementation(IProgressMonitor pm) {
		IResource[] roots = { originalFile.getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildGeneratedAnnotationValue(originalFile);
		Pattern pattern = Pattern.compile(Helper.getGeneratedAnnotationRegExp(path));
		List<Change> changes = new ArrayList<Change>();
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				MoveChange move = new MoveCompilationUnitChange(originalFile,file, destination,CompilationUnitType.TEST_IMPLEMENTATION);
				changes.add(move);
				return true;
			}
		};

		TextSearchEngine.create().search(scope, collector, pattern, pm);

		if (changes.isEmpty())
			return null;

		CompositeChange result = new CompositeChange("Move GraphWalker Test Implementation(s)");
		for (Iterator<Change> iter = changes.iterator(); iter.hasNext();) {
			Change mc = iter.next();
			result.add(mc);
		}

		return result;
	}

	public Change createMoveGeneratedAnnotation( ) {
		Change change  = new MoveGeneratedAnnotation (originalFile,destination);
		return change;
	}
	
	public Change createMoveModelAnnotation( ) {
		Change change  = new MoveModelAnnotation (originalFile,destination);
		return change;
	}
	
	public Change createMovePathUsage( ) {
		Change change  = new MovePathUsage (originalFile,destination);
		return change;
	}
	
	
	
	public Change createMoveChangeForTestInterface(IProgressMonitor pm) {
		IResource[] roots = { originalFile.getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildModelAnnotationValue(originalFile);
		Pattern pattern = Pattern.compile(Helper.getModelAnnotationRegExp(path));
		List<Change> changes = new ArrayList<Change>();
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				MoveChange move = new MoveCompilationUnitChange(originalFile,file, destination,CompilationUnitType.TEST_INTERFACE);
				changes.add(move);
				return true;
			}
		};

		TextSearchEngine.create().search(scope, collector, pattern, pm);

		if (changes.isEmpty())
			return null;

		CompositeChange result = new CompositeChange("Move GraphWalker Test Interface(s)");
		for (Iterator<Change> iter = changes.iterator(); iter.hasNext();) {
			Change mc = iter.next();
			result.add(mc);
		}

		return result;
	}
}
