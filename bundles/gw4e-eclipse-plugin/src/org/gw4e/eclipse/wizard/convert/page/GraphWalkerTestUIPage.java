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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.ConvertToFileCreationWizard;
import org.gw4e.eclipse.wizard.convert.model.GraphWalkerTestPage;

public class GraphWalkerTestUIPage extends WizardPage implements Listener {

	public static final String GW4E_CONVERSION_CONTROL_ID = "id.gw4e.conversion.GraphWalkerTestUIPage.id";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_GRAPHWALKER_CHECKBOX = "id.gw4e.conversion.graphwalker.test";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_RUN_MODEL_TEST_CHECKBOX = "id.gw4e.conversion.choice.run.model.test";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_CACHE_ENABLED_CHECKBOX = "id.gw4e.conversion.cache.enabled";
	public static final String GW4E_CONVERSION_COMBO_START_ELEMENT= "id.gw4e.conversion.combo.start.element";
	public static final String GW4E_CONVERSION_PATHGENERATORTEXT_ID= "id.gw4e.conversion.path.generator";
	public static final String GW4E_CONVERSION_GROUP_TEXT_ID= "id.gw4e.conversion.group.generator";

    
	/**
	 * 
	 */
	private ElementCombo elementsCombo;
	/**
	 * 
	 */
	private Button generateRunModelBasedTest;

	/**
	 * 
	 */
	private Button graphwalkerBasedTest;

	/**
	 * 
	 */
	private Button useCacheButton;



	 
	/**
	 * 
	 */
	Label labelGroupTest;
	/**
	 * 
	 */
	Label labelPathGenerator;
	
	/**
	 * 
	 */
	Label labelTargetVertex;

	/**
	 * 
	 */
	private IStructuredSelection selection;

	/**
	 * 
	 */
	private Text pathGeneratortext;
	/**
	 * 
	 */
	private Text testGroupText;

 

	/**
	 * 
	 */
	boolean doHint = false;

	public GraphWalkerTestUIPage(  IWorkbench workbench, IStructuredSelection selection) {
		super("GraphWalkerTestImplementationPage");
		this.selection = selection;
 
		this.setTitle(MessageUtil.getString("generate_a_graphwalker_annotated_test")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("choose_the_generation_option")); //$NON-NLS-1$
		setPageComplete(false);
	}
	
	 
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());

		Group groupGraphWalkerTest = new Group(composite, SWT.NONE);
		groupGraphWalkerTest.setLayout(new GridLayout());
		groupGraphWalkerTest.setText(MessageUtil.getString("generationGraphWalkerTestOptions")); //$NON-NLS-1$
		groupGraphWalkerTest.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		graphwalkerBasedTest = new Button(groupGraphWalkerTest, SWT.CHECK);
		graphwalkerBasedTest.setText(MessageUtil.getString("generateGraphWalkerTest")); //$NON-NLS-1$
		graphwalkerBasedTest.setSelection(false);
		graphwalkerBasedTest.setEnabled(true);
		graphwalkerBasedTest.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_GRAPHWALKER_CHECKBOX);

		graphwalkerBasedTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					graphwalkerBasedTest.setFocus();
					elementsCombo.getCombo().setEnabled(graphwalkerBasedTest.getSelection());
					testGroupText.setEnabled(graphwalkerBasedTest.getSelection());
					pathGeneratortext.setEnabled(graphwalkerBasedTest.getSelection());
					labelGroupTest.setEnabled(graphwalkerBasedTest.getSelection());
					labelPathGenerator.setEnabled(graphwalkerBasedTest.getSelection());
					elementsCombo.getLabelStartElement().setEnabled(graphwalkerBasedTest.getSelection());
					setPageComplete(validatePage());
					break;
				}
			}
		});
		
		elementsCombo = new ElementCombo (groupGraphWalkerTest,SWT.NONE,true,this);
		elementsCombo.getCombo().setData(GW4E_CONVERSION_CONTROL_ID, GW4E_CONVERSION_COMBO_START_ELEMENT);		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		elementsCombo.setLayoutData(gridData);
		
		labelPathGenerator = new Label(groupGraphWalkerTest, SWT.BORDER);
		labelPathGenerator.setText(MessageUtil.getString("whatPathGeneratorShouldIUse"));
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		labelPathGenerator.setLayoutData(gd);
		labelPathGenerator.setEnabled(false);
		
		pathGeneratortext = new Text(groupGraphWalkerTest, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		pathGeneratortext.setLayoutData(gd);
		pathGeneratortext.setEnabled(false);
		pathGeneratortext.setText(PreferenceManager.getDefaultPathGenerator());
		pathGeneratortext.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		 
		pathGeneratortext.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_PATHGENERATORTEXT_ID);

		labelGroupTest = new Label(groupGraphWalkerTest, SWT.BORDER);
		labelGroupTest.setText(MessageUtil.getString("toWhichGroupDoThisBelongTo"));
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		labelGroupTest.setLayoutData(gd);

		testGroupText = new Text(groupGraphWalkerTest, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		testGroupText.setLayoutData(gd);
		testGroupText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		testGroupText.setData(GW4E_CONVERSION_CONTROL_ID, GW4E_CONVERSION_GROUP_TEXT_ID);
		testGroupText.setEnabled(false);

		generateRunModelBasedTest = new Button(groupGraphWalkerTest, SWT.CHECK);
		generateRunModelBasedTest.setText(MessageUtil.getString("generateRunModelBasedTest")); //$NON-NLS-1$
		generateRunModelBasedTest.setSelection(false);
		generateRunModelBasedTest.setEnabled(true);
		generateRunModelBasedTest.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_RUN_MODEL_TEST_CHECKBOX);

		generateRunModelBasedTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateRunModelBasedTest.setFocus();

					setPageComplete(validatePage());
					break;
				}
			}
		});
		
		Group groupExplanation = new Group(composite, SWT.NONE);
		groupExplanation.setLayout(new GridLayout());
		groupExplanation.setText(MessageUtil.getString("generationPoliciesGroup")); //$NON-NLS-1$
		groupExplanation.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		labelGroupTest = new Label(groupExplanation, SWT.BORDER);
		labelGroupTest.setText(MessageUtil.getString("generationInformationText"));
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		labelGroupTest.setLayoutData(gd);
		
		useCacheButton = new Button(groupExplanation, SWT.CHECK);
		useCacheButton.setText(MessageUtil.getString("avoidthepolicies")); //$NON-NLS-1$
		useCacheButton.setSelection(PreferenceManager.isCacheEnabled());
		useCacheButton.setEnabled(true);
		useCacheButton.setData(GW4E_CONVERSION_CONTROL_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_CACHE_ENABLED_CHECKBOX);

		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		useCacheButton.setLayoutData(gd);

		
		useCacheButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					PreferenceManager.toggleCacheEnabled();
					break;
				}
			}
		});
		
		setControl(composite);

		elementsCombo.loadElements(selection);

		handleEvent(null); 
	}

	public void reset () {
		graphwalkerBasedTest.setSelection(false);
		generateRunModelBasedTest.setSelection(false);
	}
	
	public void setVisible(boolean visible) {
		((ConvertToFileCreationWizard) this.getWizard()).setGraphWalkerTestPage(null); 
	    validatePage();
	    super.setVisible(visible);
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event!=null && event.text!=null) {
			GraphWalkerTestUIPage.this.setErrorMessage(event.text);
			return;
		}
		if (!elementsCombo.hasStartElement()) {
			graphwalkerBasedTest.setSelection(true);
			graphwalkerBasedTest.setEnabled(false);
			generateRunModelBasedTest.setEnabled(false);
			elementsCombo.getCombo().setEnabled(false);
		}
		setPageComplete(validatePage());
	}

 
	protected boolean validatePage() {
		((ConvertToFileCreationWizard) this.getWizard()).setGraphWalkerTestPage(null);
		this.setPageComplete(false);
		 
		this.setErrorMessage(null);
		this.setMessage(null);

		if (graphwalkerBasedTest.getSelection()) {
			if (elementsCombo.getStartElement() == null) {
				this.setErrorMessage(MessageUtil.getString("EnterStartElement"));
				return false;
			}
			if (pathGeneratortext.getText().trim().length() == 0) {
				this.setErrorMessage(MessageUtil.getString("youMustSetAPathGenerator"));
				return false;
			}
		}
		
		GraphWalkerTestPage gwtp = new GraphWalkerTestPage(
				graphwalkerBasedTest.getSelection(),
				generateRunModelBasedTest.getSelection(), 
				elementsCombo.getStartElement()==null ? "" : elementsCombo.getStartElement().getName(),
				pathGeneratortext.getText().trim(), 
				testGroupText.getText().trim());
		((ConvertToFileCreationWizard) this.getWizard()).setGraphWalkerTestPage(gwtp);
		this.setPageComplete(true);
		return true;
	}

	
	public boolean hasSelection () {
		return ( (graphwalkerBasedTest.getSelection() && this.isPageComplete()) || generateRunModelBasedTest.getSelection() );
	}

  
}
