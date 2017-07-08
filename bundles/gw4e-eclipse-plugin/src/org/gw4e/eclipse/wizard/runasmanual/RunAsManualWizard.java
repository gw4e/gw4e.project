
package org.gw4e.eclipse.wizard.runasmanual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.runasmanual.Engine;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;

/**
 * A Wizard to convert a graph model file into another format (java, json, dot)
 *
 */
public class RunAsManualWizard extends Wizard implements INewWizard {

	static final ImageDescriptor WIZARD_BANNER;

	static {
		WIZARD_BANNER = Activator.getDefaultImageDescriptor();
	}

	String modelPath;
	List<String> additionalPaths;
	String generatorstopcondition;
	String startnode;
	boolean removeBlockedElement;

	/**
	 * The Eclipse workbench
	 */
	private IWorkbench workbench;

	List<WizardPage> pages = new ArrayList<WizardPage>();

	public static void open(String modelPath, List<String> additionalPaths, String generatorstopcondition,
			String startnode, boolean removeBlockedElements) {
		try {
			Display.getDefault().asyncExec(() -> {
				RunAsManualWizard wizard = new RunAsManualWizard(modelPath, additionalPaths, generatorstopcondition,
						startnode, removeBlockedElements);
				wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection) null);
				Shell activeShell = Display.getDefault().getActiveShell();
				if (activeShell == null)
					return;
				WizardDialog dialog = new WizardDialog(activeShell, wizard);
				dialog.open();
			});
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * Create an instance of this Wizard
	 */
	public RunAsManualWizard(String modelPath, List<String> additionalPaths, String generatorstopcondition,
			String startnode, boolean removeBlockedElement) {
		super();
		this.modelPath = modelPath;
		this.additionalPaths = additionalPaths;
		this.generatorstopcondition = generatorstopcondition;
		this.startnode = startnode;
		this.removeBlockedElement = removeBlockedElement;
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		try {
			TestPresentationPage page = new TestPresentationPage("TestPresentationPage", modelPath, additionalPaths,
					generatorstopcondition, startnode, removeBlockedElement);
			addPage(page);
			engine = new Engine();
			try {
				engine.createMachine(modelPath, additionalPaths, generatorstopcondition, startnode);
				 
				List<WizardPage> all = new ArrayList<WizardPage>();
				while (engine.hasNextstep()) {
					WizardPage p = computeNextPage(); 
					addPage(p);
					all.add(p);
				}
				int index = 1;
				for (WizardPage wizardPage : all) {
					wizardPage.setMessage(" Step (" + index + "/" + all.size() + ")" );
				 
					index++;
				}
				WizardPage p = new SummaryExecutionPage ("");
				p.setTitle("SummaryExecutionPage");
				p.setPageComplete(true);
				addPage(p);
			} catch (IOException e) {
				return;
			}
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	@Override
	public boolean canFinish() {
		return !engine.hasNextstep();
	}

	Engine engine = null;

	private WizardPage computeNextPage() {
		StepDetail detail = engine.step();
		if (detail == null) {
			return null;
		}
		WizardPage p = new StepPage(detail);
		p.setTitle(detail.getName());
		p.setPageComplete(true);
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
		setWindowTitle((MessageUtil.getString("Run_As_Manual"))); //$NON-NLS-1$
		setDefaultPageImageDescriptor(WIZARD_BANNER);
	}

	/*
	 * Perform the conversion & open the generated file in a dedicated editor if
	 * needed (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */

	@Override
	public boolean performFinish() {
		return true;
	}

}
