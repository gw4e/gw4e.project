package org.gw4e.eclipse.wizard.runasmanual;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;

public class StepPage extends WizardPage {

	public static String GW4E_LAUNCH_CONFIGURATION_CONTROL_ID = "gw4e.runasmanual.steppage.control.id";
	public static String GW4E_STEP_PAGE_DESCRIPTION_ID = "gw4e.runasmanual.steppage.action.id";
	public static String GW4E_STEP_PAGE_RESULT_ID = "gw4e.runasmanual.steppage.result.id";

	StepDetail detail;

	protected StepPage(StepDetail detail) {
		super(detail.getName());
		this.detail = detail;
	}

	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout(20, false));
		setControl(control);

		Label descriptionLabel = new Label(control, SWT.NONE);
		descriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 5, 1));
		descriptionLabel.setText(MessageUtil.getString("description"));

		StyledText descriptionText = new StyledText(control, SWT.BORDER);
		descriptionText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_STEP_PAGE_DESCRIPTION_ID);
	 	 
		
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 15, 1));
		descriptionText.setText(detail.getDescription());
		descriptionText.setEditable(false);

		Label resultLabel = new Label(control, SWT.NONE);
		resultLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 5, 1));
		resultLabel.setText(MessageUtil.getString("result"));
		resultLabel.setEnabled(detail.isVertex());

		StyledText resultText = new StyledText(control, SWT.BORDER);
		resultText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_STEP_PAGE_RESULT_ID);
 

		resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 15, 1));
		resultText.setEnabled(detail.isVertex());
		if (detail.isVertex()) {
			resultText.setText(RunAsManualWizard.ENTER_DEFAULT_RESULT_MESSAGE);
			StyleRange styleRange = new StyleRange();
			styleRange.start = 0;
			styleRange.length = RunAsManualWizard.ENTER_DEFAULT_RESULT_MESSAGE.length();
			styleRange.fontStyle = SWT.ITALIC;
			styleRange.foreground = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
			resultText.setStyleRange(styleRange);
			resultText.addMouseListener(new MouseListener() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
				}

				@Override
				public void mouseDown(MouseEvent e) {
					if (resultText.getText().equals(RunAsManualWizard.ENTER_DEFAULT_RESULT_MESSAGE)) {
						resultText.setText("");
					}
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
			});
			resultText.addListener(SWT.FocusOut, new Listener() {
				public void handleEvent(Event e) {
					if (resultText.getText().trim().length()>0) {
						detail.setResult(resultText.getText().trim());
					}
				}
			});
		}

		Label filler = new Label(control, SWT.NONE);
		filler.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		filler.setText("");

		Composite comp = new Composite(control, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		comp.setLayout(new GridLayout(3, false));
		
		Button btnFailedCheckButton = new Button(comp, SWT.CHECK);
		btnFailedCheckButton.setText(MessageUtil.getString("failed"));
		btnFailedCheckButton.setEnabled(detail.isVertex());
		
		Button skipButton = new Button(comp, SWT.CHECK);
		skipButton.setText(MessageUtil.getString("skip_to_summary"));
		skipButton.setEnabled(false);
		skipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RunAsManualWizard wizard = (RunAsManualWizard) StepPage.this.getWizard();
				wizard.setSkipToSummary(skipButton.getSelection());
			}
		});
		
		btnFailedCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				detail.setFailed(btnFailedCheckButton.getSelection());
				skipButton.setEnabled(btnFailedCheckButton.getSelection());
				if (!btnFailedCheckButton.getSelection()) {
					RunAsManualWizard wizard = (RunAsManualWizard) StepPage.this.getWizard();
					skipButton.setSelection(false);
					wizard.setSkipToSummary(false);
				}
			}
		});
	}

	
	
	public StepDetail getDetail() {
		return detail;
	}
	
	public void stepPerformed () {
		detail.setPerformed(true);
	}

}
