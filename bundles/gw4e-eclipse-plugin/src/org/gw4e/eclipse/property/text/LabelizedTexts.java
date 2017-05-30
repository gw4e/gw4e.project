package org.gw4e.eclipse.property.text;

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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.property.IPropertyUI;
import org.gw4e.eclipse.property.Property;

public class LabelizedTexts extends Composite implements IPropertyUI {
	private Text [] texts;
	String[] propertyNames;
	Property[]  properties;
	
	public static String PROJECT_PROPERTY_PAGE_WIDGET_ID 	= "project.property.widget.id";
	public static String LABEL								= "project.property.custom.label";
	public static String TEXT								= "project.property.custom.text";

	 
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LabelizedTexts(Composite parent, 
			int style,
			Property[]  properties,
			String id) {
		super(parent, style);
		
		
		this.properties = properties;
		
		setLayout(new GridLayout(10, false));
		
		texts = new Text [properties.length];
		for (int i = 0; i < texts.length; i++) {
			Label lblNewLabel = new Label(this, SWT.NONE);
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = 4;
			lblNewLabel.setLayoutData(gridData);
			lblNewLabel.setText(properties [i].getLabel() );
			lblNewLabel.setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, LABEL + "." + id + "." + i );
			
			final int index = i;  
			ModifyListener listener = new ModifyListener() {
			    public void modifyText(ModifyEvent e) {
			    	properties[index].check(new String [] {texts [index].getText() });
			    }
			};
			
			if (properties [i].isMultitext()) {
				texts [i] = new Text(this, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
				texts [i] .setText(properties [i].getValue());
				texts [i] .setEnabled(properties [i].isEditable());
				texts [i] .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
				texts [i].addModifyListener(listener);
			} else {
				texts [i] = new Text(this, SWT.BORDER);
				texts [i] .setText(properties [i].getValue());
				texts [i] .setEnabled(properties [i].isEditable());
				texts [i] .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
				texts [i].addModifyListener(listener);
			}
			texts [i].setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, TEXT + "." + id + "." + i );
		}
	}
	
	/**
	 * @return the text value
	 */
	public String getText (int i) {
		return texts [i].getText();
	}
	
	/**
	 * @return the text value
	 */
	public int getTextCount( ) {
		return texts.length;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	@Override
	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames=propertyNames;
	}

	@Override
	public void resetToDefaultValue() {
		for (int i = 0; i < propertyNames.length; i++) {
			String[] values = PreferenceManager.getDefaultPreference(propertyNames[i]);
			texts [i] .setText(values[0]);
		}
	}
	@Override
	public Map<String, String[]> getValues() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (int i = 0; i < propertyNames.length; i++) {
			List<String> temp = new ArrayList<String> ();
			temp.add(texts [i].getText());
			String[] ret = new String[temp.size()];
			temp.toArray(ret);
			map.put(propertyNames[i],ret);
		}
		return map;
	}

	@Override
	public void resetToDefaultValue(String[] data) {
		for (int i = 0; i < propertyNames.length; i++) {
			texts [i] .setText(data[0]);
		}
	}
	 
}
