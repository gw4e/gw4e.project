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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.gw4e.eclipse.facade.ResourceManager;

public class JDTHelper {

	public JDTHelper() {
		// TODO Auto-generated constructor stub
	}

	public static boolean containsMethod (String path, String[] requiredMethods) throws JavaModelException {
		IResource resource = ResourceManager.getResource(path);
		IFile file = (IFile) resource;
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		IType[] types = cu.getAllTypes();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < types.length; i++) {
			IMethod[] methods = types[i].getMethods();
			for (int j = 0; j < methods.length; j++) {
				list.add(methods[j].getElementName());
			}
		} 
		for (int i = 0; i < requiredMethods.length; i++) {
			String method = requiredMethods[i];
			if (!list.contains(method)) return false;
		}     
		return true;
	}
}
