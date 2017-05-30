package org.gw4e.eclipse.test.fwk;

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

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.TestResourceGeneration;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.ResourceContext;

public class GenerationFactory {

	
	public static TestResourceGeneration get (IFile file) throws CoreException, FileNotFoundException {
		String targetFolder = GraphWalkerContextManager.getTargetFolderForTestImplementation(file);
		IPath pkgFragmentRootPath = file.getProject().getFullPath().append(new Path(targetFolder));
		IPackageFragmentRoot implementationFragmentRoot = JDTManager.getPackageFragmentRoot(file.getProject(), pkgFragmentRootPath);
		String classname = file.getName().split("\\.")[0];
		classname = classname + PreferenceManager.suffixForTestImplementation(implementationFragmentRoot.getJavaProject().getProject().getName()) + ".java";

		ClassExtension ce = PreferenceManager.getDefaultClassExtension(file);
		IPath p = ResourceManager.getPathWithinPackageFragment(file).removeLastSegments(1);
		p = implementationFragmentRoot.getPath().append(p);
		
		ResourceContext context = new ResourceContext(p, classname, file, true, false, false, ce);
		
		TestResourceGeneration trg = new TestResourceGeneration(context);
		return trg;
	}
	
	public static ResourceContext getResourceContext (IFile file) throws CoreException, FileNotFoundException {
		String targetFolder = GraphWalkerContextManager.getTargetFolderForTestImplementation(file);
		IPath pkgFragmentRootPath = file.getProject().getFullPath().append(new Path(targetFolder));
		IPackageFragmentRoot implementationFragmentRoot = JDTManager.getPackageFragmentRoot(file.getProject(), pkgFragmentRootPath);
		String classname = file.getName().split("\\.")[0];
		classname = classname + PreferenceManager.suffixForTestImplementation(implementationFragmentRoot.getJavaProject().getProject().getName()) + ".java";

		ClassExtension ce = PreferenceManager.getDefaultClassExtension(file);
		IPath p = ResourceManager.getPathWithinPackageFragment(file).removeLastSegments(1);
		p = implementationFragmentRoot.getPath().append(p);
		
		ResourceContext context = new ResourceContext(p, classname, file, true, false, false, ce);
		 
		return context;
	}
}
