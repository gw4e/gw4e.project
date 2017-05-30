package org.gw4e.eclipse.facade;

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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.graphwalker.core.machine.ExecutionContext;

public class NewExecutionContextClassWizardPageRunner implements Runnable {
	TestResourceGeneration info;
	IProgressMonitor monitor;
	IType type;
	boolean done = false;

	public NewExecutionContextClassWizardPageRunner(TestResourceGeneration info, IProgressMonitor monitor) {
		super();
		this.info = info;
		this.monitor = monitor;
	}

	@Override
	public void run() {
		 
		NewClassWizardPage page = new NewClassWizardPage();
		 
		page.setPackageFragmentRoot(info.getImplementationFragmentRoot(), false);
		page.setPackageFragment(info.getTargetPkg(), false);
		String pkgName = info.getTargetPkg().getElementName();
		if (pkgName == null)
			pkgName = "";
		if (pkgName.trim().length() > 0)
			pkgName = pkgName + ".";
		String test = pkgName + info.getInterfaceName();
		page.addSuperInterface(test);
		page.setTypeName(info.getClassName(), false);
		page.setDescription("Generated from GW4E");
		
		String ancestor = null;
		if (info.isExtendSource()) {
			ancestor=info.getExtendedClassname();
		} else {
			ancestor = ExecutionContext.class.getName();
		} 
		page.setSuperClass(ancestor, false);
		page.setMethodStubSelection(false, false, true, false);
		try {
			page.createType(null);
			type = page.getCreatedType();

 		} catch (Exception e) {
			ResourceManager.logException(e);
		} finally {
			done = true;
		}
	}

	public boolean isDone() {
		return done;
	}

	public IType getCreatedType() {
		return type;
	}

	/**
	 * @return the type
	 */
	public IType getType() {
		return type;
	}
}
