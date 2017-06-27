
package org.gw4e.eclipse.wizard.staticgenerator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * The Generator page that let the end user entering choices for graph model
 * file conversion
 *
 */
public class GraphElementSelectionUIPage extends WizardPage {
	private GridData gd_1;
	private GridData gd_2;
	private Table tableL;
	private GridData gd_3;
	private Table table;

	protected GraphElementSelectionUIPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 13;
		container.setLayout(gridLayout);
		
		Composite compositeL = new Composite(container, SWT.NULL);
		compositeL.setLayout(new GridLayout(1, false));
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 6;
		compositeL.setLayoutData(gd);
		
		tableL = new Table(compositeL, SWT.BORDER | SWT.FULL_SELECTION);
		tableL.setHeaderVisible(true);
		tableL.setLinesVisible(true);
		gd_3 = new GridData();
		gd_3.grabExcessVerticalSpace = true;
		gd_3.verticalAlignment = SWT.FILL;
		gd_3.grabExcessHorizontalSpace = true;
		gd_3.horizontalAlignment = SWT.FILL;
		gd_3.horizontalSpan = 1;
		tableL.setLayoutData(gd_3);
		
		Composite compositeC = new Composite(container, SWT.NULL);
		gd_1 = new GridData();
		gd_1.horizontalAlignment = SWT.FILL;
		gd_1.grabExcessVerticalSpace = true;
		gd_1.horizontalSpan = 1;
		compositeC.setLayoutData(gd_1);
		
		Composite compositeR= new Composite(container, SWT.NULL);
		gd_2 = new GridData();
		gd_2.verticalAlignment = SWT.FILL;
		gd_2.grabExcessHorizontalSpace = true;
		gd_2.horizontalAlignment = SWT.FILL;
		gd_2.grabExcessVerticalSpace = true;
		gd_2.horizontalSpan = 6;
		compositeR.setLayoutData(gd_2);
		
		table = new Table(compositeR, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		gd_3 = new GridData();
		gd_3.grabExcessVerticalSpace = true;
		gd_3.verticalAlignment = SWT.FILL;
		gd_3.grabExcessHorizontalSpace = true;
		gd_3.horizontalAlignment = SWT.FILL;
		 
		
		
		setControl(container);
		
	}
}
