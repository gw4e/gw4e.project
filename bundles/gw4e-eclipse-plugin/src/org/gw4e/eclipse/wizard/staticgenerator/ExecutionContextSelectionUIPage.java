
package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.staticgenerator.model.ExecutionContextPage;

/**
 * The Generator page that let the end user entering choices for the execution
 * context we want to extend file conversion
 *
 */
public class ExecutionContextSelectionUIPage extends WizardPage {
	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.conversion.widget.id";
	public static final String GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST = "id.gw4e.conversion.combo.ancestor.extend.id";

	ICompilationUnit ancestor;
	IFile model;

	protected ExecutionContextSelectionUIPage(String pageName, IFile model) throws FileNotFoundException {
		super(pageName, MessageUtil.getString("execution_context_choice"), null);
		this.model = model;
	}

	private void skip(Composite parent) {
		Label space = new Label(parent, SWT.NONE);
		space.setText("");
	}

	List<IFile> ancestors = null;

	private void loadAncestor(IFile file) {
		Display display = Display.getCurrent();
		Runnable longJob = new Runnable() {
			public void run() {
				display.syncExec(new Runnable() {
					public void run() {
						ancestors = JDTManager.findAvailableExecutionContextAncestors(file);
					}
				});
				display.wake();
			}
		};
		BusyIndicator.showWhile(display, longJob);

	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);

		skip(container);

		Label explanation = new Label(container, SWT.NONE);
		explanation.setText(MessageUtil.getString("choose_the_execution_context_you_want_to_extend"));

		skip(container);

		ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		Combo combo = comboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		loadAncestor(model);

		setupAncestor(comboViewer);

		setControl(container);
	}

	private void setupAncestor(ComboViewer comboViewer) {
		comboViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				List<IFile> files = (List<IFile>) inputElement;
				Object[] ret = new Object[files.size()];
				int index = 0;
				for (IFile file : files) {
					ret[index++] = JavaCore.create(file);
				}
				return ret;
			}
		});
		comboViewer.setLabelProvider(new JavaElementLabelProvider(
				JavaElementLabelProvider.SHOW_QUALIFIED | JavaElementLabelProvider.SHOW_ROOT));
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() > 0) {
					ancestor = (ICompilationUnit) selection.getFirstElement();
				}
			}
		});
		comboViewer.getCombo().setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST);

		comboViewer.setInput(ancestors);
		if (hasItems()) {
			comboViewer.setSelection(new StructuredSelection(JavaCore.create(ancestors.get(0))));
		}
	}

	public boolean hasItems() {
		return ancestors.size() > 0;
	}

	public void setVisible(boolean visible) {
		((GeneratorToFileCreationWizard) this.getWizard()).setExecutionContextPage(null);
		setPageComplete(validatePage());
		super.setVisible(visible);
	}

	protected boolean validatePage() {
		((GeneratorToFileCreationWizard) this.getWizard()).setExecutionContextPage(null);
		this.setErrorMessage(null);
		this.setMessage(null);
		setPageComplete(false);
		String msg = validateSelection();
		if (msg != null) {
			this.setErrorMessage(msg);
			return false;
		}
		ExecutionContextPage executionContextPage = new ExecutionContextPage(ancestor);
		((GeneratorToFileCreationWizard) this.getWizard()).setExecutionContextPage(executionContextPage);
		setPageComplete(true);
		return true;
	}

	private String validateSelection() {
		if (ancestor == null) {
			return MessageUtil.getString("you_must_select_an_execution_context_class");
		}
		return null;
	}

}
