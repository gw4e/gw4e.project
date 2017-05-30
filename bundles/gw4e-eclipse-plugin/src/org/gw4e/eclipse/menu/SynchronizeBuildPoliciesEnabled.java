package org.gw4e.eclipse.menu;

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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.gw4e.eclipse.product.GW4ENature;

/**
 * A helper class to enable/disable the Run As -> GW4E  To menu item. See plugin.xml
 * 
 */
public class SynchronizeBuildPoliciesEnabled implements IMenuTester {

	public SynchronizeBuildPoliciesEnabled() {
		super();
	}

	 
	 
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.menu.IMenuTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		IProject project = null;
		boolean authorized = false; 
		if (receiver instanceof IJavaProject) {
			project =  ((IJavaProject)receiver).getProject();
			authorized = true;
		} else {
			if (receiver instanceof IPackageFragmentRoot) {
				IPackageFragmentRoot pfr = ((IPackageFragmentRoot)receiver);
				project = pfr.getJavaProject().getProject();
				try {
					authorized =  (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);
				} catch (JavaModelException ignore) {
				}
			} else {
				if (receiver instanceof IPackageFragment) {
					IPackageFragment  pf = (IPackageFragment)receiver;
					project = pf.getJavaProject().getProject();
					authorized = true;
				}
			}
		}
		if (authorized && project!= null) {
			if (GW4ENature.hasGW4ENature(project)) 
				return true;
		}
 
		return false; 
	}

}
