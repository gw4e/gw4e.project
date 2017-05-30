package org.gw4e.eclipse.preferences;

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

import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.property.table.CustomListWithButtons;
import org.gw4e.eclipse.property.table.StringCustomListModel;

/**
 * This class represents the GW4E preference page  
 */

public class GW4EPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	CustomListWithButtons gw4eLibraries;
	CustomListWithButtons authorizedFolders;

	/**
	 * 
	 */
	public GW4EPreferencePage() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);

		String[] values = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		String[] propertyNames = new String[] { PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION };
		authorizedFolders = new CustomListWithButtons(parent, SWT.NONE, true,
				new StringCustomListModel(MessageUtil.getString("authorizedfolderforgraphmodel"), values));
		authorizedFolders.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		authorizedFolders.setPropertyNames(propertyNames);

		values = PreferenceManager.getGraphWalkerJavaLibName();
		propertyNames = new String[] { PreferenceManager.GRAPHWALKER_JAVALIBRARIES };
		gw4eLibraries = new CustomListWithButtons(parent, SWT.NONE, true,
				new StringCustomListModel(MessageUtil.getString("graphwalkerlibraries"), values));
		gw4eLibraries.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		gw4eLibraries.setPropertyNames(propertyNames);

		return new Canvas(parent, 0);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		super.performDefaults();
		gw4eLibraries.resetToDefaultValue(PreferenceManager.getDefaultGraphWalkerLibraries());
		authorizedFolders.resetToDefaultValue(PreferenceManager.getDefaultAuthorizedFolderForGraphDefinition());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	protected void performApply() {
		super.performApply();
		updatePreferences ();
	}

	/**
	 * 
	 */
	private void updatePreferences () {
		updatePreference (gw4eLibraries.getValues());
		updatePreference (authorizedFolders.getValues());
	}
	
	/**
	 * @param map
	 */
	private void updatePreference (Map<String, String[]> map ) {
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key =  iter.next();
			String[]values = map.get(key);
			PreferenceManager.setPreference(null, key, values);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		super.performOk();
		updatePreferences ();
		return true;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}
