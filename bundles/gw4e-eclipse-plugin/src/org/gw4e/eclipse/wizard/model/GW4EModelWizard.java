 
package org.gw4e.eclipse.wizard.model;

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


import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.builder.InitialBuildPolicies;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.wizard.template.GW4ETemplatePage;
import org.gw4e.eclipse.wizard.template.TemplateProvider;
 

public class GW4EModelWizard extends Wizard implements INewWizard, ISelectionChangedListener  {

	protected static String FILE_EXTENSIONS = "json";
 
	protected GW4ETemplatePage fExtraPage;
	protected TemplateProvider provider;
	protected IProject project;
	protected IFolder targetFolder;
	protected InitialBuildPolicies policies;
	protected IStructuredSelection selection;
	protected IWorkbench workbench;
	protected String filename;
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle("GW4E");
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(Activator.getDefaultImageDescriptor());
	}

	@Override
	public boolean performFinish() {
		try {
			 
			IRunnableWithProgress operation = provider.createResourceOperation(project,targetFolder,policies,filename);
			getContainer().run(false, false, operation);
			return provider.openInEditor(workbench);
		} catch (Exception exception) {
			ResourceManager.logException(exception, "Error occured.");
			return false;
		}
	}
	 
	public void addPages() {
		super.addPages();
		List<TemplateProvider>  templates = TemplateProvider.getActiveTemplates();
		fExtraPage = new GW4ETemplatePage(this,templates,true);
		addPage(fExtraPage);
	}
 
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object selected = selection.getFirstElement();
		if (selected instanceof TemplateProvider) {
			provider = (TemplateProvider) selected;
		}
		if (selected instanceof IFolder) {
			targetFolder 	= ((IFolder) selected);
			project  		= targetFolder.getProject();
		}
		if (selected instanceof InitialBuildPolicies) {
			policies 	= ((InitialBuildPolicies) selected);
		}
		if (selected instanceof String) {
			filename 	= ((String) selected);
		}		
		fExtraPage.validatePage();
	}

}
