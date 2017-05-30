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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.refactoring.Helper;
import org.gw4e.eclipse.refactoring.change.move.CompilationUnitType;

public abstract class RenameChange extends RenameResourceChange {

	private IFile originalFile;
	private IPath originalPath;
	
	public IPath getOriginalPath() {
		return originalPath;
	}

	private IProject project;
	public RenameChange(IProject project,IPath resourcePath, String newName) {
		super(resourcePath, newName);
		this.originalPath = resourcePath;
		this.project = project;
	}
	
	protected IFile getNewFile () {
		IFile ret = null;
		IFile original = getOriginalFile ();
		if (original==null) {
			ret = (IFile)ResourceManager.getResource(originalPath.removeLastSegments(1).append(this.getNewName()).toString());
		} else {
		   ret = ((IFolder)original.getParent()).getFile(getNewName());
		}
		return ret;
	}
	
	protected IFile getOriginalFile () {
		if (originalFile==null) {
			originalFile = (IFile)ResourceManager.getResource(originalPath.toString());
		}
	
		return originalFile;
	}

	protected IProject getProject () {
		return this.project;
	}
	
	@Override
	public void initializeValidationData(IProgressMonitor pm) {
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}
	
	public Change createRenameChangeForTestImplementation(IProgressMonitor pm) {
		IResource[] roots = { getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildGeneratedAnnotationValue(getOriginalFile());
		Pattern pattern = Pattern.compile(Helper.getGeneratedAnnotationRegExp(path));
		List<Change> changes = new ArrayList<Change>();
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				Change rename = new RenameCompilationUnitChange(getProject(),file.getFullPath(), getNewName(),CompilationUnitType.TEST_IMPLEMENTATION);
				changes.add(rename);
				return true;
			}
		};

		TextSearchEngine.create().search(scope, collector, pattern, pm);

		if (changes.isEmpty())
			return null;

		CompositeChange result = new CompositeChange("Rename GraphWalker Test Implementation(s)");
		Map<IPath,IPath> maps = new HashMap<IPath,IPath> ();
		for (Iterator<Change> iter = changes.iterator(); iter.hasNext();) {
			RenameCompilationUnitChange mc = (RenameCompilationUnitChange)iter.next();
			IPath mcPath = mc.getTargetFilePath();
			IPath p = maps.get(mcPath);
			if (p!=null) {
				String[] parts = mc.getNewName().split(Pattern.quote("."));
				String newValue = parts[0] + System.currentTimeMillis() + "." + parts[1];
				mc.setNewName(newValue);
			}
			maps.put(mc.getTargetFilePath(),mc.getTargetFilePath());
			result.add(mc);
		}

		return result;		
	}

	public Change createEditorChangeForGraph(IProgressMonitor pm) {
		return new RenameEditorChange(getProject(), originalPath, getNewName());
	}
	
	public Change createRenameChangeForTestInterface(IProgressMonitor pm) {
		IResource[] roots = { getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildModelAnnotationValue(originalFile);
		Pattern pattern = Pattern.compile(Helper.getModelAnnotationRegExp(path));
		List<Change> changes = new ArrayList<Change>();
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				Change rename = new RenameCompilationUnitChange(getProject(),file.getFullPath(), getNewName(),CompilationUnitType.TEST_INTERFACE);
				changes.add(rename);
				return true;
			} 
		};

		TextSearchEngine.create().search(scope, collector, pattern, pm);

		if (changes.isEmpty())
			return null;

		CompositeChange result = new CompositeChange("Rename GraphWalker Test Interface(s)");
		Map<IPath,IPath> maps = new HashMap<IPath,IPath> ();
		for (Iterator<Change> iter = changes.iterator(); iter.hasNext();) {
			RenameCompilationUnitChange mc = (RenameCompilationUnitChange)iter.next();
			IPath p = maps.get(mc.getTargetFilePath());
			if (p!=null) {
				String[] parts = mc.getNewName().split(Pattern.quote("."));
				mc.setNewName(parts[0] + System.currentTimeMillis() + "." + parts[1]);
			}
			result.add(mc);
		}

		return result;
	}
	
	@Override 
	public abstract String getName();

	@Override
	public abstract Change perform(IProgressMonitor pm) throws CoreException;
	
	@Override
	public abstract Object getModifiedElement() ;
	
}
