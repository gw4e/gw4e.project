package org.gw4e.eclipse.wizard.runasmanual;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.gw4e.eclipse.launching.ui.ModelData;
import org.gw4e.eclipse.message.MessageUtil;

public class TestPresentationPage extends WizardPage {
	String modelPath;
	ModelData[] additionalModels;
	String generatorstopcondition;
	String startnode;
	boolean removeBlockedElement;

	protected TestPresentationPage(String pageName, String modelPath, ModelData[] additionalModels,
			String generatorstopcondition, String startnode, boolean removeBlockedElement) {
		super(pageName);
		this.setTitle(MessageUtil.getString("summary_of_manual_test"));
		this.modelPath = modelPath;
		this.additionalModels = additionalModels;
		this.generatorstopcondition = generatorstopcondition;
		this.startnode = startnode;
		this.removeBlockedElement = removeBlockedElement;
	}

	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		setControl(control);
		control.setLayout(new GridLayout(1, false));

		StyledText summaryText = new StyledText(control, SWT.BORDER);
		summaryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		StringBuffer text = new StringBuffer();
		text.append(MessageUtil.getString("your_test_will_be_based_on_the_following") + "\n\n");
		text.append(MessageUtil.getString("main_model") + "\n");
		text.append( modelPath + "\n");
		text.append(MessageUtil.getString("additional_models") + "\n");
		for (ModelData path : additionalModels) {
			text.append(path.getFullPath() + "\n");
		}
		text.append(MessageUtil.getString("generator_stop_condition" ) + "\n");
		text.append( generatorstopcondition + "\n");
		text.append( MessageUtil.getString("start_element" ) +  "\n");
		text.append(startnode + "\n");
		text.append(MessageUtil.getString("remove_Blocked_Element") + "\n");
		text.append((removeBlockedElement ? "Yes" : "No") + "\n");

		summaryText.setText(text.toString());

		int start = 2;
		int length = 1;
		summaryText.setLineBullet(start, length, buildMainModelStyle());
		start =  start + length;
		summaryText.setLineBullet(start, length, buildCheckedlStyle());
		start =  start + length;
		summaryText.setLineBullet(start, length, buildAdditionalTitleModelStyle());
		start =  start + length;
		length = additionalModels.length;
		summaryText.setLineBullet(start,length, buildCheckedlStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildMainModelStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildCheckedlStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildMainModelStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildCheckedlStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildMainModelStyle());
		start =  start + length;
		length = 1;
		summaryText.setLineBullet(start, length, buildCheckedlStyle());

	}

	private Bullet buildMainModelStyle() {
		StyleRange style = new StyleRange();
		style.metrics = new GlyphMetrics(0, 0, 40);
		style.foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		Bullet bullet = new Bullet(style);
		return bullet;
	}

	private Bullet buildAdditionalTitleModelStyle() {
		StyleRange style = new StyleRange();
		style.metrics = new GlyphMetrics(0, 0, 40);
		style.foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		Bullet bullet = new Bullet(style);
		return bullet;
	}

	private Bullet buildCheckedlStyle() {
		StyleRange style2 = new StyleRange();
		style2.metrics = new GlyphMetrics(0, 0, 80);
		style2.foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		Bullet bullet = new Bullet(ST.BULLET_TEXT, style2);
		bullet.text = "\u2713";
		return bullet;
	}

}
