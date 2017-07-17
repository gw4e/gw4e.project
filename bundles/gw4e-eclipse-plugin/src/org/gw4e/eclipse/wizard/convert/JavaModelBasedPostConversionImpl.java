package org.gw4e.eclipse.wizard.convert;

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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;

 
/**
 * Post processing for Java file
 *
 */
public class JavaModelBasedPostConversionImpl extends AbstractPostConversion {
	 
	/**
	 * @param context
	 */
	public JavaModelBasedPostConversionImpl(ResourceContext context) {
		super(context);
	}

	 
	/**
	 * Format the source file
	 * @param file
	 * @param monitor
	 */
	private void formatSource(IFile file, IProgressMonitor monitor) {
		try {
			JDTManager.formatUnitSourceCode(file,monitor);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * The gw generated code contains a classname that might be different from the filename chosen in the wizard , which lead to a compile error.
	 * We need to fix that by modifying the source code so that names are aligned.
	 * @param monitor
	 * @return
	 */
	private void rename(IProgressMonitor monitor)  {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		 try {
			 String newClassname = getConvertedFile().getName();
			 newClassname  = newClassname.substring(0,newClassname.indexOf("."));
			
			 String oldClassname = context.getSelectedGraphFileName();
			 oldClassname  = oldClassname.substring(0,oldClassname.indexOf("."));
			 subMonitor.split(10);
			 convertedFile =  JDTManager.renameClass(getConvertedFile(), oldClassname, newClassname,monitor);
		} catch (Exception e) {
			ResourceManager.logException(e);
		} finally {
			subMonitor.split(90);
		}
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.wizard.convert.AbstractPostConversion#afterConversion(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void afterConversion(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		rename(subMonitor.split(80));
		if (convertedFile != null) {
			formatSource(convertedFile, subMonitor.split(20));
		}
		subMonitor.done();
	}

 
}
