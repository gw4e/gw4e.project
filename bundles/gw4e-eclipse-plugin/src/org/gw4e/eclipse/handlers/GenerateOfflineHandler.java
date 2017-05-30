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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class GenerateOfflineHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow aww = HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel.isEmpty())
			return null;
		if (sel instanceof IStructuredSelection) {
			IResource selectedFolder = null;
			IStructuredSelection selection = (IStructuredSelection) sel;
			if (selection != null) {
				Object obj = selection.getFirstElement();
				if (obj != null && obj instanceof IResource) {
					selectedFolder = (IResource) obj;
					if (!selectedFolder.exists())
						return null;
					// This is where the generation is done ...
					boolean autoBuilding = ResourcesPlugin.getWorkspace().getDescription().isAutoBuilding();
					try {
						ResourceManager.setAutoBuilding(false);
						BuildPolicy[]  generators = dialog((IFile) selectedFolder);
						if (generators.length == 0) return null;
						int timeout = PreferenceManager.getTimeOutForTestOfflineGeneration(((IFile) selectedFolder).getProject().getName());
						IPackageFragment pkg = null;
						String classfile = null;
						GraphWalkerContextManager.generateOffline(selectedFolder,pkg,classfile,generators, timeout, aww);
					} catch (Exception e) {
						ResourceManager.logException(e);
					} finally {
						ResourceManager.setAutoBuilding(autoBuilding);
					}
				}
			}
		}
		return null;
	}

	private BuildPolicy[] dialog(IFile graphModel) {
		List<BuildPolicy> policies;
		try {
			policies = BuildPolicyManager.getBuildPolicies(graphModel,false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BuildPolicy[] input = new BuildPolicy[policies.size()];
		input = policies.toArray(input);
		int timeout = PreferenceManager.getTimeOutForTestOfflineGeneration(graphModel.getProject().getName());
		   
		Object[] args = { new Integer(timeout) };
		MessageFormat fmt = new MessageFormat(MessageUtil.getString("select_the_generator_for_wich_you_want_to_run_the_offline_tool"));
		
		ListSelectionDialog dialog = new ListSelectionDialog(Display.getDefault().getActiveShell(), input,
				ArrayContentProvider.getInstance(), new LabelProvider() {
					public String getText(Object element) {
						BuildPolicy policy = (BuildPolicy) element;
						return policy.getPathGenerator();
					}
				}, fmt.format(args) );

		dialog.setTitle(MessageUtil.getString("Offline_Generation"));
		dialog.setInitialSelections(input);
		dialog.open();
		Object[] result = dialog.getResult();
		if (result == null) {
			return new BuildPolicy[0];
		}
		BuildPolicy[] ret = new BuildPolicy[result.length];
		int index=0;
		for (Object obj : result) {
			ret[index++] = (BuildPolicy) obj;
		}
		return ret;
	}

}
