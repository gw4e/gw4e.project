package org.gw4e.eclipse.property;

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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

/**
 *  An implementation of a workbench property page ( IWorkbenchPropertyPage). The implementation is a JFace preference 
 *  page with an adaptable element
 *  
 *  It represent the properties displayed whenever and end user right click on a .graphml file
 *  Among other things, it gives the requirements and the methods of the graphml file 
 *  See the Requirement & Method command client from the GraphWalker documentation
 *
 */
public class GraphModelPropertyPage extends PropertyPage {

	public static final String PROPERTY_PAGE_CONTEXT = Constant.PLUGIN_ID + ".graphml_property_page_context"; //$NON-NLS-1$
	public static final String GW4E_FILE_REQUIREMENT_TEXT_ID = "gw4e.file.requirement";
	public static final String GW4E_FILE_METHODS_TEXT_ID = "gw4e.file.methods";
	public static final String GW4E_LABEL_ID = "id.label.graphml.property";
	
	
	/**
	 * Create a GridLayout with 2 columns
	 * 
	 * @param parent
	 * @param numColumns
	 * @return
	 */
	protected Composite createComposite(Composite parent, int numColumns) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = numColumns;
		composite.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);
		return composite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	public Control createContents(Composite parent) {

		noDefaultAndApplyButton();
		Composite panel = createComposite(parent, 2);

		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),PROPERTY_PAGE_CONTEXT);

		IResource resource = (IResource) getElement();

		if (resource.getType() == IResource.FILE) {
			Label label = createLabel(panel, MessageUtil.getString("File_name")); //$NON-NLS-1$
			label = createLabel(panel, resource.getName());
			label.setData(GW4E_LABEL_ID,GW4E_LABEL_ID);
			fillExcessHorizontalSpace(label);

			//
			createLabel(panel, MessageUtil.getString("Path")); //$NON-NLS-1$
			label = createLabel(panel, resource.getFullPath().setDevice(null).toString());
			fillExcessHorizontalSpace(label);

			createLabel(panel, MessageUtil.getString("modified")); //$NON-NLS-1$
			IFile file = (IFile) resource;
			label = createLabel(panel, formatDate(new Date(file.getLocalTimeStamp())));
			fillExcessHorizontalSpace(label);

			createrequirementSection(panel, file);
			createMethodSection(panel, file);
		}
		return new Canvas(panel, 0);
	}

	/**
	 * Create the Text that will hold the Requirement coming from the graphml file
	 * @param panel
	 * @param file
	 */
	protected void createrequirementSection(Composite panel, IFile file) {
		Label label = createLabel(panel, MessageUtil.getString("Requirements")); //$NON-NLS-1$
		Text t = new Text(panel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		t.setData(GW4E_FILE_REQUIREMENT_TEXT_ID,GW4E_FILE_REQUIREMENT_TEXT_ID );
		Set<String> requirements = null;
		String reqMessage = "";
		try {
			requirements = GraphWalkerFacade.getRequirement(file);
			String newline = System.getProperty("line.separator");
			if (requirements == null || requirements.size() == 0) {
				reqMessage = MessageUtil.getString("NoRequirements");
			} else {
				StringBuffer sb = new StringBuffer();

				for (String req : requirements) {
					sb.append(req).append(newline);
				}
				t.setText(sb.toString());
				reqMessage = requirements.size() + " " + MessageUtil.getString("requirementsfound");
			}
		} catch (Exception e) {
			ResourceManager.logException(e);
			t.setText(e.getMessage());
		}
		t.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, true).hint(150, 150).applyTo(t);

		createLabel(panel, MessageUtil.getString("requirementMessage")); //$NON-NLS-1$
		label = createLabel(panel, reqMessage);
		fillExcessHorizontalSpace(label);
	}
	
	/**
	 * Create the Text that will hold the Methods coming from the graphml file
	 * 
	 * @param panel
	 * @param file
	 */
	protected void createMethodSection(Composite panel, IFile file) {
		Label label = createLabel(panel, MessageUtil.getString("Methods")); //$NON-NLS-1$
		Text t = new Text(panel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		t.setData(GraphModelPropertyPage.GW4E_FILE_METHODS_TEXT_ID, GW4E_FILE_METHODS_TEXT_ID);
		Set<String> methods = null;
		String reqMessage = "";
		try {
			methods = GraphWalkerFacade.getMethods(file);
			String newline = System.getProperty("line.separator");
			if (methods == null || methods.size() == 0) {
				reqMessage = MessageUtil.getString("NoMethods");
			} else {
				StringBuffer sb = new StringBuffer();

				for (String meth : methods) {
					sb.append(meth).append(newline);
				}
				t.setText(sb.toString());
				reqMessage = methods.size() + " " + MessageUtil.getString("methodsfound");
			}
		} catch (Exception e) {
			ResourceManager.logException(e);
			t.setText(e.getMessage());
		}
		t.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, true).hint(150, 150).applyTo(t);

		createLabel(panel, MessageUtil.getString("methodMessage")); //$NON-NLS-1$
		label = createLabel(panel, reqMessage);
		fillExcessHorizontalSpace(label);
	}

	/**
	 * Label creation helper method
	 * @param parent
	 * @param text
	 * @return
	 */
	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	/**
	 * Grab Excessive Space 
	 * @param control
	 */
	private void fillExcessHorizontalSpace(Control control) {
		GridData gd = (GridData) control.getLayoutData();
		if (gd != null) {
			gd.grabExcessHorizontalSpace = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		return true;
	}
	
	
	private String formatDate (Date d) {
		Locale userLocale = Locale.getDefault();; 
		DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, userLocale); 
		return df.format(d);
	}
}
