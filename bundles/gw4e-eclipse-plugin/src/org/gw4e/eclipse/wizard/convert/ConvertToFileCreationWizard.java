
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

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion.ConversionRunnable;
import org.gw4e.eclipse.wizard.convert.model.GraphWalkerTestPage;
import org.gw4e.eclipse.wizard.convert.model.GraphWalkerHookPage;
import org.gw4e.eclipse.wizard.convert.model.JUnitTestPage;
import org.gw4e.eclipse.wizard.convert.model.ResourcePage;
import org.gw4e.eclipse.wizard.convert.page.ConvertToResourceUIPage;
import org.gw4e.eclipse.wizard.convert.page.GW4EHookUIPage;
import org.gw4e.eclipse.wizard.convert.page.GraphWalkerTestUIPage;
import org.gw4e.eclipse.wizard.convert.page.JUnitGW4ETestUIPage;
import org.gw4e.eclipse.wizard.convert.page.OfflineGW4ETestUIPage;

/**
 * A Wizard to convert a graph model file into another format (java, json, dot)
 *
 */
public class ConvertToFileCreationWizard extends Wizard implements INewWizard {

	static final ImageDescriptor WIZARD_BANNER;
 
	static {
		WIZARD_BANNER = Activator.getDefaultImageDescriptor();
	}

	/**
	 * The current selection
	 */
	private IStructuredSelection selection;

	/**
	 * The Eclipse workbench
	 */
	private IWorkbench workbench; 
	/**
	 * 
	 */
	private ResourcePage resourcePage;
	/**
	 * 
	 */
	private GraphWalkerTestPage graphWalkerTestPage;
	/**
	 * 
	 */
	private JUnitTestPage junitTestPage;
	/**
	 * 
	 */
	private GraphWalkerHookPage hookPage;

	/**
	 * The main page wizard
	 */
	private ConvertToResourceUIPage resourceUIPage;

	/**
	 * 
	 */
	private GraphWalkerTestUIPage gwTestUIPage;

	/**
	 * 
	 */
	private JUnitGW4ETestUIPage junitTestUIPage;

	/**
	 * 
	 */
	private GW4EHookUIPage hookUIPage;

	/**
	 * 
	 */
	private	OfflineGW4ETestUIPage offlinePage;
	
	/**
	 * Do we open an editor after conversion
	 */
	boolean openEditor = false;

	/**
	 * Create an instance of this Wizard
	 */
	public ConvertToFileCreationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		resourceUIPage = new ConvertToResourceUIPage(workbench, selection);
		addPage(resourceUIPage);
		offlinePage = new OfflineGW4ETestUIPage(this, workbench, selection);
		addPage(offlinePage);		
		gwTestUIPage = new GraphWalkerTestUIPage(workbench, selection);
		addPage(gwTestUIPage);
		junitTestUIPage = new JUnitGW4ETestUIPage(this, workbench, selection);
		addPage(junitTestUIPage);
		hookUIPage = new GW4EHookUIPage(workbench);
		addPage(hookUIPage);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage p = super.getNextPage(page);
		if (page instanceof ConvertToResourceUIPage) {
			 if (resourcePage.isOfflineBasedCheckbox()) {
				 String name=resourceUIPage.getFileName();
				 offlinePage.setTarget(resourceUIPage.getContainerFullPath(),name);
				 return offlinePage;
			 } else {
				 return gwTestUIPage;
			 }
			
		}
		if (page instanceof OfflineGW4ETestUIPage) {
			return null;
		}
		return p;
	}
	
    @Override
	public IWizardPage getPreviousPage(IWizardPage page) {
    	IWizardPage p = null;
    	if (page instanceof GraphWalkerTestUIPage ) {
    		p = resourceUIPage;
    	}
    	p = super.getPreviousPage(page);
    	if (null == p) {
    		offlinePage.reset();
    		gwTestUIPage.reset();
    		junitTestUIPage.reset();
    		hookUIPage.reset();
    	}
    	return p;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle((MessageUtil.getString("GraphWalker_Conversion_File"))); //$NON-NLS-1$
		setDefaultPageImageDescriptor(WIZARD_BANNER);
	}

	public boolean canFinish() {
		return (
				resourceUIPage.isPageComplete() && !resourceUIPage.canFlipToNextPage())
				|| 
				(resourceUIPage.canFlipToNextPage() && ( gwTestUIPage.hasSelection() || junitTestUIPage.hasSelection() ) 
				|| 
				(resourceUIPage.canFlipToNextPage() && offlinePage.isPageComplete() )		
			);
	}

	/*
	 * Perform the conversion & open the generated file in a dedicated editor if
	 * needed (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */

	@Override
	public boolean performFinish() {
		if (resourceUIPage != null) {
			try {
				ClassExtension ce = new ClassExtension(
						this.hookPage == null ? false : this.hookPage.isBeforeAfterExecution(),
						this.hookPage == null ? false : this.hookPage.isGrabPerformance(),
						this.hookPage == null ? false : this.hookPage.isBeforeAfterElement(),
						this.junitTestPage == null ? false : this.junitTestPage.isJunitSmokeTest(),
						this.junitTestPage == null ? false : this.junitTestPage.isJunitFuncitonalTest(),
						this.junitTestPage == null ? false : this.junitTestPage.isJunitStabilityTest(),
						this.junitTestPage == null ? "" : this.junitTestPage.getTargetVertex(),
						this.junitTestPage == null ? "" : this.junitTestPage.getStartElement(),
						this.junitTestPage == null ? new ArrayList<IFile>() : this.junitTestPage.getAdditionalContext(),
						this.graphWalkerTestPage == null ? false
								: this.graphWalkerTestPage.isGraphWalkerModelBasedTest(),
						this.graphWalkerTestPage == null ? false 
								: this.graphWalkerTestPage.isGraphWalkerAnnotatedTest() || this.resourcePage.isOfflineBasedCheckbox(),
						this.graphWalkerTestPage == null ? "" : this.graphWalkerTestPage.getPathGenerator(),
						this.graphWalkerTestPage == null ? "" : this.graphWalkerTestPage.getStartElement(),
						this.graphWalkerTestPage == null ? "" : this.graphWalkerTestPage.getGroups(),
						ResourceManager.toIFile(selection));

				ResourceContext context = new ResourceContext(this.resourcePage.getContainerFullPath(),
						this.resourcePage.getFilename(), this.resourcePage.getFileToConvert(),
						this.resourcePage.isOpenEditorCheckbox(), this.resourcePage.isEraseExistingFile(), false, ce);

				AbstractPostConversion converter = null;
				if (this.resourcePage.isJavaModelBasedCheckbox()) {
					converter = new JavaModelBasedPostConversionImpl(context);
				}
				if (this.resourcePage.isJsonCheckbox()) {
					converter = new JSONPostConversionImpl(context);
				}
				if (this.resourcePage.isDotCheckbox()) {
					converter = new DotPostConversionImpl(context);
				}
				if (this.resourcePage.isJavaTestBasedCheckbox()) {
					converter = new JavaTestBasedPostConversionImpl(context);
				}
				if (this.resourcePage.isOfflineBasedCheckbox()) {
					converter =  offlinePage.createConvertor(context);
				}
				boolean autoBuilding = ResourcesPlugin.getWorkspace().getDescription().isAutoBuilding();
				try {
					ResourceManager.setAutoBuilding(false);
					ConversionRunnable runnable = converter.createConversionRunnable(workbench.getActiveWorkbenchWindow());
					getContainer().run(true, true, runnable);
				} finally {
					ResourceManager.setAutoBuilding(autoBuilding);
				}

			} catch (Exception e) {
				ResourceManager.logException(e);
			}
		}
		return true;
	}

	/**
	 * @param resourcePage
	 *            the resourcePage to set
	 */
	public void setResourcePage(ResourcePage resourcePage) {
		this.resourcePage = resourcePage;
	}

	/**
	 * @param graphWalkerTestPage
	 *            the graphWalkerTestPage to set
	 */
	public void setGraphWalkerTestPage(GraphWalkerTestPage graphWalkerTestPage) {
		this.graphWalkerTestPage = graphWalkerTestPage;
	}

	/**
	 * @param junitTestPage
	 *            the junitTestPage to set
	 */
	public void setJunitTestPage(JUnitTestPage junitTestPage) {
		this.junitTestPage = junitTestPage;
	}

	/**
	 * @param hookPage
	 *            the hookPage to set
	 */
	public void setHookPage(GraphWalkerHookPage hookPage) {
		this.hookPage = hookPage;
	}

	public int testCount() {
		int count = 0;
		if (graphWalkerTestPage != null)
			count += graphWalkerTestPage.testCount();
		if (junitTestPage != null)
			count += junitTestPage.testCount();
		return count;
	}

}
