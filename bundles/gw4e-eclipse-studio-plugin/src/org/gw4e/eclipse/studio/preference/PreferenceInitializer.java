package org.gw4e.eclipse.studio.preference;

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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.gw4e.eclipse.studio.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_OPEN_SHARED_GRAPHML_FILE, false);
		store.setDefault(PreferenceConstants.P_AUTO_RESIZING, true);
		store.setDefault(PreferenceConstants.P_AUTO_NODE_DIMENSION_MARGE_WIDTH , 25); 
		store.setDefault(PreferenceConstants.P_AUTO_NODE_DIMENSION_MARGE_HEIGHT, 30);
		store.setDefault(PreferenceConstants.P_NODE_DIMENSION_WIDTH	,100);
		store.setDefault(PreferenceConstants.P_NODE_DIMENSION_HEIGHT ,100);
		store.setDefault(PreferenceConstants.P_ROW_COUNT_FOR_VERTEX_TEXT_DESCRIPTION ,3);
		store.setDefault(PreferenceConstants.P_ROW_COUNT_FOR_EDGE_TEXT_DESCRIPTION  ,3);
		store.setDefault(PreferenceConstants.P_ROW_COUNT_FOR_TEXT_REQUIREMENTS ,3);
		store.setDefault(PreferenceConstants.P_MAX_ROW_IN_TOOLTIPS ,3);
		store.setDefault(PreferenceConstants.P_SPACE_HEIGHT_MARGE_FOR_TREE_LAYOUT_ALGORITHM ,60);
		store.setDefault(PreferenceConstants.P_SPACE_WIDTH_MARGE_FOR_TREE_LAYOUT_ALGORITHM,60);	 
	}

}
