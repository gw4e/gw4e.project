package org.gw4e.eclipse.wizard.project;

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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.gw4e.eclipse.builder.InitialBuildPolicies;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.product.GW4ENature;
import org.gw4e.eclipse.wizard.template.Folder;
import org.gw4e.eclipse.wizard.template.GW4ETemplatePage;
import org.gw4e.eclipse.wizard.template.MavenTemplatePage;
import org.gw4e.eclipse.wizard.template.NoneTemplate;
import org.gw4e.eclipse.wizard.template.TemplateProvider;

/**
 * A wizard to create a java project with a GW4E Nature
 *
 */
public class GW4ECreationWizard extends Wizard
		implements IExecutableExtension, INewWizard, ISelectionChangedListener {

	private NewJavaProjectWizardPageOne mainPage;
	private NewJavaProjectWizardPageTwo javaPage;
	private GW4ETemplatePage fExtraPage;
	private MavenTemplatePage fMavenPage;
	protected IWorkbench workbench;
	protected TemplateProvider provider;
	protected Folder targetFolder;
	protected InitialBuildPolicies policies;
	private IConfigurationElement fConfigElement;
	protected String filename;
	/**
	 * Set the wizard title
	 */
	public GW4ECreationWizard() {
		setWindowTitle("GW4E Project Creation Wizard");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.
	 * eclipse.core.runtime.IConfigurationElement, java.lang.String,
	 * java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		fConfigElement = cfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Wizard#addPages
	 */
	public void addPages() {
		super.addPages();

		mainPage = new NewJavaProjectWizardPageOne() {
			public void createControl(Composite parent) {
				initializeDialogUnits(parent);

				Composite composite = new Composite(parent, SWT.NULL);
				composite.setFont(parent.getFont());
				composite.setLayout(new GridLayout(1, false));
				composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

				// create UI elements
				Control nameControl = createNameControl(composite);
				nameControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				Control jreControl = createJRESelectionControl(composite);
				jreControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				Control workingSetControl = createWorkingSetControl(composite);
				workingSetControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				workingSetControl.setVisible(false);

				Control infoControl = createInfoControl(composite);
				infoControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				infoControl.setVisible(false);

				setControl(composite);
			}

			public IClasspathEntry[] getSourceClasspathEntries() {
				return  GW4ENature.getSourceClasspathEntries(getProjectName());
			}

			public IPath getOutputLocation() {
				return GW4ENature.getOutputLocation(getProjectName());
			}
		};

		addPage(mainPage);
		javaPage = new NewJavaProjectWizardPageTwo(mainPage);
		addPage(javaPage);
		List<TemplateProvider>  templates = TemplateProvider.getTemplates();
		fExtraPage = new GW4ETemplatePage(this,templates,false);
		addPage(fExtraPage);
		fMavenPage = new MavenTemplatePage(this);
		addPage(fMavenPage);
	}

	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage p = super.getNextPage(page);
		if (page instanceof GW4ETemplatePage) {
			IJavaProject newElement = javaPage.getJavaProject();
			fMavenPage.setProject(newElement.getProject());
		}
		 
		return p;
	}
	
	 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see Wizard#performFinish
	 */
	public boolean performFinish() {
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				javaPage.performFinish(monitor);
			}
		};
		try {
			getContainer().run(false, true, op);

			IJavaProject newElement = javaPage.getJavaProject();

			GW4ENature.setGW4ENature(newElement.getProject());

			IWorkingSet[] workingSets = mainPage.getWorkingSets();
			if (workingSets.length > 0) {
				PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(newElement, workingSets);
			}

			BasicNewProjectResourceWizard.updatePerspective(fConfigElement);
			BasicNewResourceWizard.selectAndReveal(newElement.getResource(),
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		
			fMavenPage.create();
			
			if (provider==null) {
				provider = new NoneTemplate();
			}
 
			IPath p = newElement.getProject().getFullPath().append(targetFolder.getName()).append(fMavenPage.getPackagePath());
			ResourceManager.ensureFolderPath(p);
			
			IFolder f = (IFolder) ResourceManager.getResource(p.toString());

			
			IRunnableWithProgress operation = provider.createResourceOperation(newElement.getProject(),f,policies,filename);
			getContainer().run(false, false, operation);
			return provider.openInEditor(workbench);
		} catch (Exception e) {
			ResourceManager.logException(e);
			return false;
		}
	}

	public boolean performCancel() {
		javaPage.performCancel();
		return true;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object selected = selection.getFirstElement();
		if (selected instanceof TemplateProvider) {
			provider = (TemplateProvider) selected;
		}
		if (selected instanceof Folder) {
			targetFolder = (Folder)selected;
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
