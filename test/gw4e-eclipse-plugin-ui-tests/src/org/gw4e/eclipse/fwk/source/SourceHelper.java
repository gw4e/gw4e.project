package org.gw4e.eclipse.fwk.source;

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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;
 

public class SourceHelper {

	public static void updatePathGenerator1 (IFile ifile, String oldPathGenerator,String newPathGenerator) throws JavaModelException {
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(ifile);
		String source = cu.getBuffer().getContents();
		source = source.replace(oldPathGenerator, newPathGenerator);
		final  Document document = new Document(source);
		cu.getBuffer().setContents(document.get());
	    cu.save(new NullProgressMonitor(), true);
	}

	public static void updatePathGenerator (IFile ifile, String oldPathGenerator,String newPathGenerator) throws CoreException {
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(ifile);
		 
		ICompilationUnit workingCopy = cu.getWorkingCopy(new NullProgressMonitor());
		
		IBuffer buffer = ((IOpenable)workingCopy).getBuffer();
		String source = buffer.getContents();
		int start = source.indexOf(oldPathGenerator);
		buffer.replace(start, oldPathGenerator.length(), newPathGenerator);
		workingCopy.reconcile(ICompilationUnit.NO_AST, false, workingCopy.getOwner(), new NullProgressMonitor());
	    workingCopy.commitWorkingCopy(true, null);
	    workingCopy.discardWorkingCopy();
	    
	    ifile.touch(new NullProgressMonitor ());
	}
}
