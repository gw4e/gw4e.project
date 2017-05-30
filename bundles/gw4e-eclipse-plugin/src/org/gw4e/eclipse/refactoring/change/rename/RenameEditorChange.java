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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.gw4e.eclipse.refactoring.UpdateEditorAction;

public class RenameEditorChange extends RenameChange {

	public RenameEditorChange(IProject project, IPath resourcePath, String newName) {
		super(project, resourcePath, newName);
	}

	@Override
	public String getName() {
		return "Close editor for " + getOriginalFile() + " and Open editor for " + getNewFile ();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		UpdateEditorAction action = new UpdateEditorAction(getProject(),  getOriginalPath(), getNewFile()); 
		action.openclose();
		return new RenameEditorChange(getProject(), getNewFile().getFullPath(), getOriginalPath().lastSegment());
	}

	@Override
	public Object getModifiedElement() {
		return getOriginalFile();
	}

}
