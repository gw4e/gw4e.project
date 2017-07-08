package org.gw4e.eclipse.wizard.runasmanual;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;

public class StepPage extends WizardPage {

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
		descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 15, 1));
		descriptionText.setText(detail.getDescription());
		descriptionText.setEditable(false);

		Label resultLabel = new Label(control, SWT.NONE);
		resultLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 5, 1));
		resultLabel.setText(MessageUtil.getString("result"));
		resultLabel.setEnabled(detail.isVertex());

		StyledText resultText = new StyledText(control, SWT.BORDER);
		resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 15, 1));
		resultText.setEnabled(detail.isVertex());
		if (detail.isVertex()) {
			String text = "Enter a result, if verification failed";
			resultText.setText(text);
			StyleRange styleRange = new StyleRange();
			styleRange.start = 0;
			styleRange.length = text.length();
			styleRange.fontStyle = SWT.ITALIC;
			styleRange.foreground = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
			resultText.setStyleRange(styleRange);
			resultText.addMouseListener(new MouseListener () {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
				}

				@Override
				public void mouseDown(MouseEvent e) {
					if (resultText.getText().equals(text)) {
						resultText.setText("");
					}
				}

				@Override
				public void mouseUp(MouseEvent e) {
				}
				
			});
			
		}

		Label filler = new Label(control, SWT.NONE);
		filler.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		filler.setText("");

		Button btnFailedCheckButton = new Button(control, SWT.CHECK);
		btnFailedCheckButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 15, 1));
		btnFailedCheckButton.setText(MessageUtil.getString("failed"));
		btnFailedCheckButton.setEnabled(detail.isVertex());
	}

}
