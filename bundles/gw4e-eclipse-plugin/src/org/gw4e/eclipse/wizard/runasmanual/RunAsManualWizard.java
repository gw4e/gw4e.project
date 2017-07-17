
package org.gw4e.eclipse.wizard.runasmanual;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.runasmanual.Engine;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.launching.ui.ModelData;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.xl.util.XLFacade;

 
public class RunAsManualWizard extends Wizard implements INewWizard {

	static final ImageDescriptor WIZARD_BANNER;
	public static String ENTER_DEFAULT_RESULT_MESSAGE = MessageUtil.getString("enter_a_result_if_verification_failed");

	static {
		WIZARD_BANNER = Activator.getDefaultImageDescriptor();
	}
	String projectname = null;
	String modelPath;
	ModelData[] additionalModels;
	String generatorstopcondition;
	String startnode;
	boolean removeBlockedElement;
	boolean skipToSummary = false;
	boolean omitEgdeswithoutDescription;
	/**
	 * The Eclipse workbench
	 */
	private IWorkbench workbench;

	List<WizardPage> pages = new ArrayList<WizardPage>();

	public static void open(String modelPath, ModelData[] additionalModels, String generatorstopcondition,
			String startnode, boolean removeBlockedElements, boolean omitEgdeswithoutDescription) {
		try {
			Display.getDefault().asyncExec(() -> {
				RunAsManualWizard wizard = new RunAsManualWizard(modelPath, additionalModels, generatorstopcondition,
						startnode, removeBlockedElements,omitEgdeswithoutDescription);
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
	public RunAsManualWizard(String modelPath, ModelData[] additionalModels, String generatorstopcondition,
			String startnode, boolean removeBlockedElement, boolean omitEgdeswithoutDescription) {
		super();
		this.modelPath = modelPath;
		this.additionalModels = additionalModels;
		this.generatorstopcondition = generatorstopcondition;
		this.startnode = startnode;
		this.omitEgdeswithoutDescription = omitEgdeswithoutDescription;
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
			TestPresentationPage page = new TestPresentationPage("TestPresentationPage", modelPath, additionalModels,
					generatorstopcondition, startnode, removeBlockedElement);
			addPage(page);
			engine = new Engine();
			List<StepDetail> details = null;
			
			try {
				projectname = ResourceManager.getResource(modelPath).getProject().getName();
				engine.createMachine(modelPath, additionalModels, generatorstopcondition, startnode, removeBlockedElement);
				details = setupPages (projectname,engine);
			} catch (IOException e) {}
			summaryPage = new SummaryExecutionPage (SummaryExecutionPage.NAME,details);
			summaryPage.setTitle(MessageUtil.getString("summaryExecutionPage"));
			summaryPage.setMessage(MessageUtil.getString("summaryMessageExecutionPage"));
			summaryPage.setPageComplete(true);
			addPage(summaryPage);
			
			stp = new SaveTestPage("SaveTestPage",projectname,engine.getComponent());
			addPage(stp);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}
	
	private List<StepDetail> setupPages (String projectname, Engine engine) throws Exception {
		int max = PreferenceManager.getMaxStepsForManualTestWizard(projectname);
		List<StepPage> all = new ArrayList<StepPage>();
		int index=0;
		while (engine.hasNextstep()) {
			StepPage p = computeNextPage(); 
			if (p==null) continue;
			// System.out.println("title " + p.getTitle());
			addPage(p);
			all.add(p);
			index++;
			if (index>max) {
				DialogManager.displayWarning(MessageUtil.getString("incomplete_steps_list_in_manual_wizard"),MessageUtil.getString("max_steps_reached_change_setting_in_preference_project"));
				break;
			}
		}
		index = 1;
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
	
	private void resetSkipState () {
		setSkipToSummary(false);
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (this.isSkipToSummary()) {
			resetSkipState ();
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
		return !engine.hasNextstep() && stp.isPageComplete();
	}

	Engine engine = null;
	private SummaryExecutionPage summaryPage;
	private SaveTestPage stp;

	private StepPage computeNextPage() {
		StepDetail detail = engine.step();
		if (detail == null) {
			return null;
		}
		if (detail.isEdge() && !detail.hasDescription() &&  this.omitEgdeswithoutDescription) {
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
	    try {
			List<StepDetail> details = this.summaryPage.getStepDetails();
			File file = stp.getWorkbookFile();
			String title = stp.getWorkbookTitle();
			boolean exportAsTemplate = stp.exportAsTemplate();
			String dateFormat = stp.getDateFormat();
			String testcaseid = stp.getTestCaseId();
			String component = stp.getComponentNme();
			String priority = stp.getPriority();
			String description = engine.getDescription()+"";
			boolean updateDetailSheet = stp.isUpdateMode();
			XLFacade.getPersistenceService().persist(file, title, exportAsTemplate, dateFormat, testcaseid, component, priority, description, updateDetailSheet, details);
			ResourceManager.resfresh(ResourceManager.getProject(projectname));
			return true;
		} catch (Exception e) {
			ResourceManager.logException(e);
			return false;
		}
	}

	public boolean isSkipToSummary() {
		return skipToSummary;
	}

	public void setSkipToSummary(boolean skipToSummary) {
		this.skipToSummary = skipToSummary;
	}

}
