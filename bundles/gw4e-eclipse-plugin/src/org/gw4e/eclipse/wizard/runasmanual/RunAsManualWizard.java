
package org.gw4e.eclipse.wizard.runasmanual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.runasmanual.Engine;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;

 
public class RunAsManualWizard extends Wizard implements INewWizard {

	static final ImageDescriptor WIZARD_BANNER;
	public static String ENTER_DEFAULT_RESULT_MESSAGE = MessageUtil.getString("enter_a_result_if_verification_failed");

	static {
		WIZARD_BANNER = Activator.getDefaultImageDescriptor();
	}

	String modelPath;
	List<String> additionalPaths;
	String generatorstopcondition;
	String startnode;
	boolean removeBlockedElement;
	boolean skipToSummary = false;
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
			List<StepDetail> details = null;
			try {
				engine.createMachine(modelPath, additionalPaths, generatorstopcondition, startnode);
				details = setupPages (engine);
			} catch (IOException e) {}
			WizardPage p = new SummaryExecutionPage (SummaryExecutionPage.NAME,details);
			p.setTitle(MessageUtil.getString("summaryExecutionPage"));
			p.setPageComplete(true);
			addPage(p);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}
	
	private List<StepDetail> setupPages (Engine engine) {
		List<StepPage> all = new ArrayList<StepPage>();
		while (engine.hasNextstep()) {
			StepPage p = computeNextPage(); 
			if (p==null) continue;
			addPage(p);
			all.add(p);
		}
		int index = 1;
		for (WizardPage wizardPage : all) {
			wizardPage.setMessage(" Step (" + index + "/" + all.size() + ")" );
			index++;
		}
		List<StepDetail> ret = all.stream().map(item -> item.getDetail()).collect(Collectors.toList());
		return ret;
	}
	
	
	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if (this.isSkipToSummary()) {
			return null;
		}
		return super.getPreviousPage(page);
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (this.isSkipToSummary()) {
			if (SummaryExecutionPage.NAME.equalsIgnoreCase(page.getName())) return null;
			return this.getPage(SummaryExecutionPage.NAME);
		}
		IWizardPage p = super.getNextPage(page);
		if (page instanceof StepPage) {
			StepPage sp =  (StepPage) page;
			sp.stepPerformed();
		}
		return p;
	}
	
	@Override
	public boolean canFinish() {
		return !engine.hasNextstep();
	}

	Engine engine = null;

	private StepPage computeNextPage() {
		StepDetail detail = engine.step();
		if (detail == null) {
			return null;
		}
		if (detail.isEdge() && !detail.hasDescription()) {
			return null;
		}
		StepPage p = new StepPage(detail);
		String type = detail.isEdge() ? MessageUtil.getString("action") : MessageUtil.getString("verification");
		p.setTitle(type + detail.getName());
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

	public boolean isSkipToSummary() {
		return skipToSummary;
	}

	public void setSkipToSummary(boolean skipToSummary) {
		this.skipToSummary = skipToSummary;
	}

}
