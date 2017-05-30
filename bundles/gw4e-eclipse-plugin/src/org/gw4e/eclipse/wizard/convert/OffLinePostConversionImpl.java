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

import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.IWorkbenchWindow;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.TestResourceGeneration;

 
/**
 * Post processing for Java file
 *
 */
public class OffLinePostConversionImpl extends AbstractPostConversion {
	List<IFile> implementations =  null;
	BuildPolicy[]  generators;
	int timeout;
	 
	/**
	 * @param context
	 */
	public OffLinePostConversionImpl(ResourceContext context,BuildPolicy[]  generators,int timeout) {
		super(context);
		this.generators=generators;
		this.timeout=timeout;
	}

	 
	 
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.wizard.convert.AbstractPostConversion#convert(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<IFile> convert(IWorkbenchWindow ww,IProgressMonitor monitor) throws IOException, CoreException, InterruptedException   {
		TestResourceGeneration trg = new TestResourceGeneration(context);
		trg.setOffline(true);
		return GraphWalkerFacade.generateOffLineFromFile(ww,trg,generators,timeout,monitor); 
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.wizard.convert.AbstractPostConversion#afterConversion(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void afterConversion(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.split(1);
		subMonitor.done();
	}
	
}
