package org.gw4e.eclipse.wizard.convert.page;

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

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.convert.ConvertToFileCreationWizard;
import org.gw4e.eclipse.wizard.convert.model.GraphWalkerHookPage;

public class GW4EHookUIPage extends WizardPage implements Listener {

	public static final String GW4E_CONVERSION_CONTROL_ID = "id.gw4e.conversion.control.id";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_EXECUTON_BEFORE_AFTER_CHECKBOX = "id.gw4e.conversion.choice.before.after.execution";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_ELEMENT_BEFORE_AFTER_CHECKBOX = "id.gw4e.conversion.choice.before.after.element";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_PERFORMNCE_REPORT_CHECKBOX = "id.gw4e.conversion.choice.performance.report";

	 

	/**
	 * The ui check box for a Before/After execution method generation
	 */
	private Button generateBeforeAfterExecutionGeneration;

	/**
	 * 
	 */
	private Button generatePerformanceReport;

	/**
	 * 
	 */
	private Button generateBeforeAfterElement;
 

	/**
	 * 
	 */
	boolean doHint = false;

	public GW4EHookUIPage(  IWorkbench workbench) {
		super("GraphWalkerHookTestImplementationPage");
		 
		this.setTitle(MessageUtil.getString("generate_a_graphwalker_annotated_test")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("choose_the_generation_option")); //$NON-NLS-1$
		setPageComplete(false);
	}
	
	Composite composite = null;
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
 

		Group groupOptions = new Group(composite, SWT.NONE);
		groupOptions.setLayout(new GridLayout());
		groupOptions.setText(MessageUtil.getString("generationOptions")); //$NON-NLS-1$
		groupOptions.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		generateBeforeAfterExecutionGeneration = new Button(groupOptions, SWT.CHECK);
		generateBeforeAfterExecutionGeneration.setText(MessageUtil.getString("generateBeforeAfterExecutionGeneration")); //$NON-NLS-1$
		generateBeforeAfterExecutionGeneration.setSelection(false);
		generateBeforeAfterExecutionGeneration.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_EXECUTON_BEFORE_AFTER_CHECKBOX);

		generateBeforeAfterExecutionGeneration.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateBeforeAfterExecutionGeneration.setFocus();
					updatePage();
					setPageComplete(validatePage());
					break;
				}
			}
		});

		generatePerformanceReport = new Button(groupOptions, SWT.CHECK);
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		generatePerformanceReport.setLayoutData(gd);
		generatePerformanceReport.setText(MessageUtil.getString("generatePerformanceReport")); //$NON-NLS-1$
		generatePerformanceReport.setSelection(false);
		generatePerformanceReport.setEnabled(false);
		generatePerformanceReport.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_PERFORMNCE_REPORT_CHECKBOX);

		generatePerformanceReport.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generatePerformanceReport.setFocus();

					setPageComplete(validatePage());
					break;
				}
			}
		});

		generateBeforeAfterElement = new Button(groupOptions, SWT.CHECK);
		generateBeforeAfterElement.setText(MessageUtil.getString("generateBeforeAfterElement")); //$NON-NLS-1$
		generateBeforeAfterElement.setSelection(false);
		generateBeforeAfterElement.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_ELEMENT_BEFORE_AFTER_CHECKBOX);

		generateBeforeAfterElement.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateBeforeAfterElement.setFocus();

					setPageComplete(validatePage());
					break;
				}
			}
		});


		setControl(composite);

		setPageComplete(false);
 
	}
	
	public void setVisible(boolean visible) {
		((ConvertToFileCreationWizard) this.getWizard()).setHookPage(null);
		setPageComplete(validatePage());
	    super.setVisible(visible);
	}
	

	@Override
	public void handleEvent(Event event) {
	}

	private void updatePage() {
		generatePerformanceReport.setEnabled(this.generateBeforeAfterExecutionGeneration.getSelection());
	}

	protected boolean validatePage() {
		 
		((ConvertToFileCreationWizard) this.getWizard()).setHookPage(null);
		this.setErrorMessage(null);
		this.setMessage(null);

		((ConvertToFileCreationWizard) this.getWizard()).setHookPage(
				new GraphWalkerHookPage(
					generateBeforeAfterExecutionGeneration.getSelection(),
					generateBeforeAfterElement.getSelection(),
					generatePerformanceReport.getSelection())
				);
		return true;
	}

	public void reset () {
		generateBeforeAfterExecutionGeneration.setSelection(false);
		generateBeforeAfterElement.setSelection(false);
		generatePerformanceReport.setSelection(false);
	}
}
