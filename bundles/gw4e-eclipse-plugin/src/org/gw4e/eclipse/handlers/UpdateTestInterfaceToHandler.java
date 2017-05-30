package org.gw4e.eclipse.handlers;

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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ResourceManager;

public class UpdateTestInterfaceToHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow aww = HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel.isEmpty())   return null;
		if (sel instanceof IStructuredSelection) {
			 IResource selectedFolder = null;
			 IStructuredSelection selection = (IStructuredSelection) sel ;
			 if (selection != null) {
				 Object obj = selection.getFirstElement();
				 if (obj != null && obj instanceof IResource) {
					 selectedFolder =  (IResource) obj;
					 if (!selectedFolder.exists()) return null;
					 // This is where the generation is done ...
					boolean autoBuilding = ResourcesPlugin.getWorkspace().getDescription().isAutoBuilding();
					 try {
						ResourceManager.setAutoBuilding(false);
						GraphWalkerContextManager.generateInterface(selectedFolder,aww);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}  finally {
						ResourceManager.setAutoBuilding(autoBuilding);
					}
				 }
			 }
	    }		
		return null;
	}
	
 
}
