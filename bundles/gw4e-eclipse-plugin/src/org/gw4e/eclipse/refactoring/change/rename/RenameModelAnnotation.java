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
import java.util.List;
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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEditGroup;
import org.gw4e.eclipse.refactoring.Helper;

public class RenameModelAnnotation extends RenameChange {
	private final IPath resourcePath;

	public RenameModelAnnotation(IProject project,IPath resourcePath, String newName) {
		super(project,resourcePath, newName);
		this.resourcePath = resourcePath;
		getOriginalFile();
	}

	@Override
	public String getName() {
		return "Update Model annotation to " + makeNewChanges(getNewName());
	}

	protected String makeNewChanges(String newName) {
		IResource r =  ResourcesPlugin.getWorkspace().getRoot().findMember(resourcePath.removeLastSegments(1));
		IJavaElement element = JavaCore.create(r);
		if (element instanceof IPackageFragmentRoot) {
			String file = new Path(this.getNewName()).toString();
			return "@Model (file =\"" + file + "\")";
		} 
		String file = new Path(element.getElementName().replaceAll("\\.", "//")).append(this.getNewName()).toString();
		return "@Model (file =\"" + file + "\")";
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		IResource[] roots = { getProject() };

		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildModelAnnotationValue(getOriginalFile());
		Pattern pattern = Pattern.compile(Helper.getModelAnnotationRegExp(path));
		List<Change> changes = new ArrayList<Change>();
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();

				TextFileChange change = new TextFileChange(file.getName(), file);
				change.setSaveMode(TextFileChange.FORCE_SAVE);
				change.setEdit(new MultiTextEdit());
				ReplaceEdit edit = new ReplaceEdit(matchAccess.getMatchOffset(), matchAccess.getMatchLength(),
						makeNewChanges(getNewName()));
				change.addEdit(edit);
				change.addTextEditGroup(new TextEditGroup("Update reference", edit)); //$NON-NLS-1$
				changes.add(change);

				return true;
			}
		};

		Job job = new WorkspaceJob("GW4E Update Model Annotation Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				TextSearchEngine.create().search(scope, collector, pattern, monitor);
				if (changes.isEmpty())
					return Status.OK_STATUS;
				for (Change change : changes) {
					change.perform(monitor);
				}
				return Status.OK_STATUS;
			}
		};

		job.setRule(getProject());  // lock so that we serialize the refactoring 
		job.setUser(true);
		job.schedule();

		return new RenameModelAnnotation(getProject(),resourcePath, resourcePath.lastSegment());
	}

	private IResource getResource() {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(resourcePath);
	}

	@Override
	public Object getModifiedElement() {
		return getResource();
	}

}
