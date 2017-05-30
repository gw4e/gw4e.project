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

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.gw4e.eclipse.studio.Activator;
 

 
public class PreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage,PreferenceConstants {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("GraphWalker Preference Page");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_OPEN_SHARED_GRAPHML_FILE,
					PreferenceConstants.P_OPEN_SHARED_GRAPHML_FILE_LABEL,
					getFieldEditorParent()));
		
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_AUTO_RESIZING,
				PreferenceConstants.P_AUTO_RESIZING_LABEL,
				getFieldEditorParent()));

		addField(
				new IntegerFieldEditor(
						P_AUTO_NODE_DIMENSION_MARGE_WIDTH, 
						P_AUTO_NODE_DIMENSION_MARGE_WIDTH_LABEL, 
						getFieldEditorParent()));
			
			addField(
					new IntegerFieldEditor(
							P_AUTO_NODE_DIMENSION_MARGE_HEIGHT, 
							P_AUTO_NODE_DIMENSION_MARGE_HEIGHT_LABEL, 
							getFieldEditorParent()));
			
		addField(
			new IntegerFieldEditor(
					P_NODE_DIMENSION_WIDTH, 
					P_NODE_DIMENSION_WIDTH_LABEL, 
					getFieldEditorParent()));
		
		addField(
				new IntegerFieldEditor(
						P_NODE_DIMENSION_HEIGHT, 
						P_NODE_DIMENSION_HEIGHT_LABEL, 
						getFieldEditorParent()));
	 	
		addField(
				new IntegerFieldEditor(
						P_ROW_COUNT_FOR_VERTEX_TEXT_DESCRIPTION, 
						P_ROW_COUNT_FOR_VERTEX_TEXT_DESCRIPTION_LABEL, 
						getFieldEditorParent()));		
		
		addField(
				new IntegerFieldEditor(
						P_ROW_COUNT_FOR_EDGE_TEXT_DESCRIPTION, 
						P_ROW_COUNT_FOR_EDGE_TEXT_DESCRIPTION_LABEL, 
						getFieldEditorParent()));
		
		addField(
				new IntegerFieldEditor(
						P_ROW_COUNT_FOR_TEXT_REQUIREMENTS, 
						P_ROW_COUNT_FOR_TEXT_REQUIREMENTS_LABEL, 
						getFieldEditorParent()));		
		
		addField(
				new IntegerFieldEditor(
						P_MAX_ROW_IN_TOOLTIPS, 
						P_MAX_ROW_IN_TOOLTIPS_LABEL, 
						getFieldEditorParent()));		
		
		addField(
				new IntegerFieldEditor(
						P_SPACE_HEIGHT_MARGE_FOR_TREE_LAYOUT_ALGORITHM, 
						P_SPACE_HEIGHT_MARGE_FOR_TREE_LAYOUT_ALGORITHM_LABEL, 
						getFieldEditorParent()));		
		
		addField(
				new IntegerFieldEditor(
						P_SPACE_WIDTH_MARGE_FOR_TREE_LAYOUT_ALGORITHM, 
						P_SPACE_WIDTH_MARGE_FOR_TREE_LAYOUT_ALGORITHM_LABEL, 
						getFieldEditorParent()));		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}
