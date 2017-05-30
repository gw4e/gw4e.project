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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * A Command handler to call GW4E Generator when selection fits
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GenerateSourceHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public GenerateSourceHandler() {
	}

	/**
	 * Check if selection is a valid one and generate code 
	 * Allowed folders are filtered by using the enabledWhen section of the commmand in the plugin.xml
	 *      <handler
     *       commandId="gw4e-eclipse-plugin.commands.generateSourceCommand"
     *       class="org.gw4e.eclipse.handlers.GenerateSourceHandler">
     *       	   <enabledWhen>
	 *             		<with variable="selection">
	 *	               		<iterate ifEmpty="false">
  	 *							<test property="org.gw4e.eclipse.menu.isAuthorizedFolderForGraphDefinition" forcePluginActivation="true"/>
	 *	               		</iterate>
	 *              		</with>
	 *             </enabledWhen>>
     *       </handler>
	 *  See also {@link #MenuStateTester}
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow aww = HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel.isEmpty())   return null;
		if (sel instanceof IStructuredSelection) {
			 IFolder selectedFolder = null;
			 IStructuredSelection selection = (IStructuredSelection) sel ;
			 if (selection != null) {
				 Object obj = selection.getFirstElement();
				 if (obj != null && obj instanceof IPackageFragmentRoot) {
					 selectedFolder = (IFolder) ((IPackageFragmentRoot) obj).getResource();
					 if (!selectedFolder.exists()) return null;
					 // This is where the generation is done ...
					boolean autoBuilding = ResourcesPlugin.getWorkspace().getDescription().isAutoBuilding();
					 try {
						ResourceManager.setAutoBuilding(false);
						GraphWalkerContextManager.generateFromFolder(selectedFolder,generateOnlyInterface(),aww);
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
	
	
	protected boolean generateOnlyInterface () {
		return false;
	}

}
