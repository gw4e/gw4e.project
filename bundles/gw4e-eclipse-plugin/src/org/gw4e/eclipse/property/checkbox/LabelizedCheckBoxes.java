package org.gw4e.eclipse.property.checkbox;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.property.IPropertyUI;

public   class LabelizedCheckBoxes extends Composite  implements IPropertyUI {
	private Button [] buttons;
	String[] propertyNames;
	
	public static String PROJECT_PROPERTY_PAGE_WIDGET_ID 	= "project.property.widget.id";
	public static String BUTTON								= "project.property.custom.button";
	 
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LabelizedCheckBoxes(Composite parent,
			int style, 
			String [] labels,
			boolean [] enabled,
			boolean [] checked,
			SelectionAdapter [] checkBoxSelectionAdapters) {
		super(parent, style);
		setLayout(new GridLayout(10, false));
		buttons = new Button [labels.length]; 
		for (int i = 0; i < labels.length; i++) {
			buttons [i] = new Button(parent, SWT.CHECK);
			buttons [i].setEnabled(enabled [i]);
			buttons [i].setSelection(checked [i]);
			buttons [i].setText(labels [i]);
			if (checkBoxSelectionAdapters[i]!=null) {
				buttons [i].addSelectionListener(checkBoxSelectionAdapters[i]);
			}
			buttons [i].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
			buttons [i].setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, BUTTON+"."+i);
 
 		}
	}

	
	
	
	/**
	 * @return the text value
	 */
	public boolean isEnabled (int i) {
		return buttons [i].getSelection();
	}
	
	/**
	 * @return the text value
	 */
	public int getButtonCount( ) {
		return buttons.length;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	 
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.property.IPropertyUI#setPropertyNames(java.lang.String[])
	 */
	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames=propertyNames;
		
	}
	


	@Override
	public Map<String, String[]> getValues() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < propertyNames.length; i++) {
			List<String> temp = new ArrayList<String> ();
			temp.add(buttons [i].getSelection()+"");
			String[] ret = new String[temp.size()];
			temp.toArray(ret);
			map.put(propertyNames[i],ret);
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.property.IPropertyUI#resetToDefaultValue()
	 */
	public void resetToDefaultValue() {
		for (int i = 0; i < propertyNames.length; i++) {
			boolean value = Boolean.parseBoolean(PreferenceManager.getDefaultPreference(propertyNames[i])[0]);
			buttons [i].setSelection(value);
		}
	}
	
	@Override
	public void resetToDefaultValue(String[] data) {
		for (int i = 0; i < propertyNames.length; i++) {
			boolean value = Boolean.parseBoolean(data[i]);
			buttons [i].setSelection(value);
		}
	}
}
