package org.gw4e.eclipse.property.table;

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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.property.IPropertyUI;
import org.gw4e.eclipse.property.table.CustomListModel.CustomTableSorter;

/**
 * Create a List with 2 buttons Add & remove features.
 *
 */
public class CustomListWithButtons extends Group implements IPropertyUI {
	
	static int ID=0;
	protected CustomListModel model;
	protected TableViewer listViewer = null;
	protected IStructuredSelection selection;
	public static final String[] PROPS = { "VALUE" };
	 
	private String property ;
	Button btnAdd;
	Button btnRemove;
	 
	
	public static String PROJECT_PROPERTY_PAGE_WIDGET_ID 	= "project.property.widget.id";
	public static String CUSTOM_LIST_WITH_BUTTON			= "project.property.custom.list.with.button";
	public static String ADD_BUTTON							= "project.property.custom.add.button";
	public static String REMOVE_BUTTON						= "project.property.custom.remove.button";
 	
	public static String  getID () {
		ID++;
		return ID + "";
	}
	
	/**
	 * Create a List with 2 buttons Add & remove features.
	 * 
	 * @param parent
	 * @param style
	 */	
	public CustomListWithButtons(Composite parent, int style, boolean addEnabled, boolean removeEnabled, final CustomListModel model ) {
		this (parent,style,true,model);
		btnAdd.setEnabled(addEnabled);
		btnRemove.setEnabled(removeEnabled);
	}
	
	/**
	 * Create a List with 2 buttons Add & remove features.
	 * 
	 * @param parent
	 * @param style
	 */
	public CustomListWithButtons(Composite parent, int style, boolean enabled, final CustomListModel model ) {
		super(parent, SWT.NONE);
		String id = getID ();
		setText(model.getGroupname());
		setLayout(new GridLayout(6, false));
		
		this.model=model;
		listViewer = new TableViewer(this, SWT.BORDER);
		listViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		listViewer.getTable().setHeaderVisible(true);
		listViewer.getTable().setLinesVisible(true);
		listViewer.getTable().setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, CUSTOM_LIST_WITH_BUTTON + id );
		
		System.out.println(model.getGroupname());
		
		
		TableLayout tableLayout = new TableLayout();
		listViewer.getTable().setLayout(tableLayout);
		tableLayout.addColumnData(new ColumnWeightData(100));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 2, 1));

		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAdd.setText(MessageUtil.getString("addbutton"));
		btnAdd.setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, ADD_BUTTON + id );

		btnRemove = new Button(composite, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText(MessageUtil.getString("removebutton"));
		btnRemove.setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, REMOVE_BUTTON + id );

		listViewer.setContentProvider(model.getContentProvider());
		listViewer.setInput(model.getData());
		listViewer.setLabelProvider(model.getLabelProvider());
		listViewer.setSorter(model.getViewerSorter());
 		
		// listViewer.addFilter(model.getViewerFilter());

		TableColumn tc = new TableColumn(listViewer.getTable(), SWT.LEFT);
		tc.setText("Value");

		tc.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				CustomTableSorter sorter = (CustomTableSorter) model.getViewerSorter();
				sorter.doSort(0);
				listViewer.refresh();
			}
		});

		CellEditor[] editors = new CellEditor[1];
		editors[0] = new TextCellEditor(listViewer.getTable());
		listViewer.setColumnProperties(PROPS);
		listViewer.setCellModifier(new StringCellModifier(listViewer, model));
		listViewer.setCellEditors(editors);

		btnAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				model.add(listViewer, e);
				listViewer.update(model.getData(), null);
				listViewer.refresh();
			}
		});

		btnRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
				if (selection.getFirstElement() != null)
					model.remove(selection);
				listViewer.update(model.getData(), null);
				listViewer.refresh();
			}
		});

		 
		refresh();
		
		if (!enabled)  disable( ) ;
	}

	private void disable( ) {
		this.setEnabled(false);
		listViewer.getTable().setEnabled(false);
		btnAdd.setEnabled(false);
		btnRemove.setEnabled(false);
	}

	/**
	 * Refresh the Table Viewer
	 */
	private void refresh() {
		listViewer.refresh();
		Table table = listViewer.getTable();
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Group#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	 
	/**
	 * A String Cell editor
	 *
	 */
	class StringCellModifier implements ICellModifier {
		private Viewer viewer;
		private CustomListModel model;

		public StringCellModifier(Viewer viewer, CustomListModel model) {
			this.viewer = viewer;
			this.model = model;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
		 * java.lang.String)
		 */
		public boolean canModify(Object element, String property) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
		 * java.lang.String)
		 */
		public Object getValue(Object element, String property) {
			return element;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
		 * java.lang.String, java.lang.Object)
		 */
		public void modify(Object element, String property, Object value) {
			if (element instanceof Item) {
				model.update((Item) element,(String)value);
				viewer.refresh();
			}
		}
	}


	@Override
	public void setPropertyNames(String[] propertyNames) {
		this.property=propertyNames[0];
		this.setData("id", property);
		listViewer.getTable().setData("id.table", property);
		 
		btnAdd.setData("id.button.add", property);
		btnRemove.setData("id.button.remove", property);
	}

	@Override
	public void resetToDefaultValue() {
		this.model.resetData(PreferenceManager.getDefaultPreference(property));
		this.refresh();
	}

	
	@Override
	public void resetToDefaultValue(String[] data) {
		this.model.resetData(data);
		this.refresh();
	}
	
	@Override
	public Map<String, String[]> getValues() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put(property,model.getData());
		return map;
	} 
}
