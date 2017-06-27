
package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.staticgenerator.model.GraphElementPage;
import org.gw4e.eclipse.wizard.staticgenerator.model.ResourcePage;

/**
 * A Wizard to convert a graph model file into another format (java, json, dot)
 *
 */
public class GeneratorToFileCreationWizard extends Wizard implements INewWizard {

	static final ImageDescriptor WIZARD_BANNER;

	static {
		WIZARD_BANNER = Activator.getDefaultImageDescriptor();
	}

	IFile model;

	/**
	 * The graph elements model
	 */

	List<Element> elements;

	/**
	 * The elements id (ordered)
	 */
	List<String> ids;

	/**
	 * The Eclipse workbench
	 */
	private IWorkbench workbench;

	/**
	 *  
	 */
	GeneratorResourceUIPage resourceUIPage;

	/**
	 *  
	 */
	GraphElementSelectionUIPage graphElementSelectionUIPage;

	public static void open(IStructuredSelection sel) {
		try {
			GeneratorToFileCreationWizard wizard = new GeneratorToFileCreationWizard();
			wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection) sel);
			Shell activeShell = Display.getDefault().getActiveShell();
			if (activeShell == null)
				return;
			WizardDialog dialog = new WizardDialog(activeShell, wizard);
			dialog.open();
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * Create an instance of this Wizard
	 */
	public GeneratorToFileCreationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * @param resourcePage
	 *            the resourcePage to set
	 */
	ResourcePage resourcePage;

	public void setResourcePage(ResourcePage resourcePage) {
		this.resourcePage = resourcePage;
	}

	GraphElementPage elementsPage;

	public void setGraphElementPage(GraphElementPage elementsPage) {
		this.elementsPage = elementsPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		try {
			graphElementSelectionUIPage = new GraphElementSelectionUIPage("GraphElementSelectionUIPage", model, ids);
			addPage(graphElementSelectionUIPage);
			File file = ResourceManager.toFile(model.getFullPath());
			resourceUIPage = new GeneratorResourceUIPage(workbench, file);
			addPage(resourceUIPage);
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
		}
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
		setWindowTitle((MessageUtil.getString("GraphWalker_Generator_File"))); //$NON-NLS-1$
		setDefaultPageImageDescriptor(WIZARD_BANNER);

		List models = selection.toList();
		model = (IFile) models.get(0);
		ids = (List<String>) models.get(1);
	}

	public boolean canFinish() {
		return resourceUIPage.isPageComplete() && graphElementSelectionUIPage.isPageComplete();
	}

	/*
	 * Perform the conversion & open the generated file in a dedicated editor if
	 * needed (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */

	@Override
	public boolean performFinish() {
		DialogManager.displayWarning("Wizard in progress", "Work not yet completed. Be patient !");
		return true;
	}

}
