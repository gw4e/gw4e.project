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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.ConvertToFileCreationWizard;
import org.gw4e.eclipse.wizard.convert.OffLinePostConversionImpl;
import org.gw4e.eclipse.wizard.convert.ResourceContext;

public class OfflineGW4ETestUIPage extends WizardPage implements Listener {

	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.conversion.widget.id";
	public static final String GW4E_OFFLINE_TIMEOUT_TEXT = "id.gw4e.offline.timeout.id";
	
	IStructuredSelection selection;

	GeneratorChoiceComposite gcc ;
	BuildPoliciesCheckboxTableViewer buildPoliciesViewer;
	Text timeoutText;
	IPath path;
 
	/**
	 * @param wizard
	 * @param workbench
	 * @param selection
	 */
	public OfflineGW4ETestUIPage(ConvertToFileCreationWizard wizard, IWorkbench workbench,
			IStructuredSelection selection) {
		super("OfflineGW4ETestUIPage");
		this.selection = selection;

		this.setTitle(MessageUtil.getString("generate_a_graphwalker_offline_test")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("choose_the_generation_option")); //$NON-NLS-1$
	 
	}

	/**
	 * @param parent
	 */
	private void skip(Composite parent) {
		Label lblDummy = new Label(parent, SWT.NONE);
		lblDummy.setText("");
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		lblDummy.setLayoutData(gd);
	}

	
	 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
	 
		gcc =  new GeneratorChoiceComposite (composite, SWT.NONE, selection, new Listener () {
			@Override
			public void handleEvent(Event event) {
				setPageComplete(validatePage());
			}
		});
		
		skip(composite);

		Label labelChooseAdditionalContext = new Label(composite, SWT.BORDER);
		labelChooseAdditionalContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		labelChooseAdditionalContext.setText(MessageUtil.getString("choose_one_or_more_path_generator"));
		labelChooseAdditionalContext.setEnabled(true);

 
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				// event.detail == SWT.CHECK
				validatePage();
			}
		};
		
		IFile file = (IFile) selection.getFirstElement();
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalIndent = 25;
		buildPoliciesViewer = BuildPoliciesCheckboxTableViewer.create(file,composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, gd,listener);
		
		skip(composite);

		Label labelTimeout = new Label(composite, SWT.BORDER);
		labelTimeout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		labelTimeout.setText(MessageUtil.getString("set_offline_timeout"));
		labelTimeout.setEnabled(true);

		timeoutText = new Text(composite, SWT.BORDER);
		timeoutText.setData(GW4E_CONVERSION_WIDGET_ID,GW4E_OFFLINE_TIMEOUT_TEXT);
		
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.horizontalIndent = 25;
		timeoutText.setLayoutData(gd);
		timeoutText.setEnabled(true);

		timeoutText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				validatePage();
			}
		});

		Label labelTimeoutExplanation = new Label(composite, SWT.BORDER);
		labelTimeoutExplanation.setText(MessageUtil.getString("set_offline_timeout_explanation"));
		labelTimeoutExplanation.setEnabled(true);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.horizontalIndent = 25;
		labelTimeoutExplanation.setLayoutData(gd);

		file = (IFile) selection.getFirstElement();
		timeoutText.setText(PreferenceManager.getTimeOutForTestOfflineGeneration(file.getProject().getName()) + "");
		
		buildPoliciesViewer.loadBuildPolicies();
 
		setControl(composite);
		setPageComplete(validatePage());

	}

	public void reset() {
		TableItem[] items = buildPoliciesViewer.getTable().getItems();
		for (TableItem tableItem : items) {
			tableItem.setChecked(false);
		}
	}

	/**
	 * @param p
	 * @param name
	 */
	public void setTarget(IPath p, String name) {
		 gcc.setTarget(p, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	@Override
	public void handleEvent(Event event) {
		validatePage();
	}

	/**
	 * @return
	 */
	protected boolean validatePage() {
		this.setErrorMessage(null);
		this.setPageComplete(false);

		if (gcc != null) {
			String msg = gcc.validate();
			if (msg != null) {
				this.setErrorMessage(msg);
				return false;
			}
		}
		
		if (buildPoliciesViewer != null) {
			TableItem[] items = buildPoliciesViewer.getTable().getItems();
			int count = 0;
			for (TableItem tableItem : items) {
				if (tableItem.getChecked()) {
					count++;
				}
			}
			if (count == 0) {
				String msg = MessageUtil.getString("you_must_select_at_least_one_path_generator");
				this.setErrorMessage(msg);
				return false;
			}
		}
		if (timeoutText != null) {
			try {
				Integer.parseInt(timeoutText.getText());
			} catch (NumberFormatException e) {
				String msg = MessageUtil.getString("you_must_select_a_valid_timeout_value");
				this.setErrorMessage(msg);
				return false;
			}
		}


		this.setPageComplete(true);
		return true;
	}

	 
	 

	 

	/**
	 * @param context
	 * @return
	 * @throws CoreException
	 */
	public OffLinePostConversionImpl createConvertor(ResourceContext context) throws CoreException {
		List<BuildPolicy> selected = new ArrayList<BuildPolicy>();
		TableItem[] items = buildPoliciesViewer.getTable().getItems();
		for (TableItem tableItem : items) {
			if (tableItem.getChecked()) {
				selected.add((BuildPolicy) tableItem.getData());
			}
		}
		BuildPolicy[] policies = new BuildPolicy[selected.size()];
		selected.toArray(policies);
	 
		ResourceContext ctx = new ResourceContext(
				context.getContainerFullPath(), 
				gcc.getRoot(), 
				gcc.getPackageFragment(), 
				gcc.getClassName(),
				gcc.getExtendedClassName(),
				context.getSelectedFile(), 
				gcc.getMode(), 
				context.getClassExtension());
		return new OffLinePostConversionImpl(ctx, policies, Integer.parseInt(timeoutText.getText()));
	}

 
}
