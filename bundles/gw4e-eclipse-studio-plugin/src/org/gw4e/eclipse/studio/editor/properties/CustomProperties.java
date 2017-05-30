package org.gw4e.eclipse.studio.editor.properties;

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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;

public class CustomProperties extends AbstractPropertySection {
	protected TableViewer listViewer = null;
	protected IStructuredSelection selection;
	public static final String[] PROPS = { "PROPERTY", "VALUE" };

	Button btnAdd;
	Button btnRemove;

	public static String PROJECT_PROPERTY_PAGE_WIDGET_ID = "project.property.widget.id";
	public static String CUSTOM_PROPERTY_LIST_WITH_BUTTON = "project.property.custom.list.with.button";
	public static String ADD_BUTTON = "project.property.custom.add.button";
	public static String REMOVE_BUTTON = "project.property.custom.remove.button";

	private Map<String, Object> properties;
	private TableViewerColumn keyColumn;
	private TableViewerColumn valueColumn;
	private PropertiesViewerComparator comparator;
		
	private boolean resizeColumnDone = false;
	private SectionProvider node;

	public CustomProperties() {
	}

	protected void fillComposite(Composite composite) {
		composite.setLayout(new GridLayout(1, false));

		listViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		listViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		listViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.getTable().setHeaderVisible(true);
		listViewer.getTable().setLinesVisible(true);
		listViewer.getTable().setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, CUSTOM_PROPERTY_LIST_WITH_BUTTON);

		keyColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		keyColumn.getColumn().setText(PROPS[0]);
		keyColumn.getColumn().setResizable(true);
		keyColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return String.valueOf(((Entry<?, ?>) element).getKey());
			}
		});
		keyColumn.setEditingSupport(new KeyEditingSupport(listViewer));
		keyColumn.getColumn().addSelectionListener(getSelectionAdapter(keyColumn.getColumn(), 0));

		valueColumn = new TableViewerColumn(listViewer, SWT.LEFT);
		valueColumn.getColumn().setText(PROPS[1]);
		valueColumn.getColumn().setResizable(true);
		valueColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(String.valueOf(((Entry<?, ?>) cell.getElement()).getValue()));
			}
		});
		valueColumn.setEditingSupport(new ValueEditingSupport(listViewer));
		valueColumn.getColumn().addSelectionListener(getSelectionAdapter(valueColumn.getColumn(), 1));

		listViewer.setColumnProperties(new String[] { PROPS[0], PROPS[1] });

		Menu contextMenu = new Menu(listViewer.getTable());
		listViewer.getTable().setMenu(contextMenu);

		final MenuItem addMenu = new MenuItem(contextMenu, SWT.NONE);
		addMenu.setText("Add entry");
		addMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				properties.put("new key", "new value");
				IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
				p.setPropertyValue(ModelProperties.PROPERTY_CUSTOM, duplicate(properties));
				refresh();
			}
		});

		final MenuItem removeMenu = new MenuItem(contextMenu, SWT.NONE);
		removeMenu.setText("Remove entry");
		removeMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) selection.getFirstElement();
				if (ModelProperties.isCustomProperty(entry.getKey())) {
					properties.remove(entry.getKey());
					IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
					p.setPropertyValue(ModelProperties.PROPERTY_CUSTOM, duplicate(properties));
					refresh();
				}
			}
		});

		listViewer.getTable().addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
			}

			@Override
			public void controlResized(ControlEvent e) {
				if (resizeColumnDone)
					return;
				resizeColumnDone = true;
				int width = composite.getBounds().width / 2;
				valueColumn.getColumn().setWidth(width);
				keyColumn.getColumn().setWidth(width);
			}
		});
		
		comparator = new PropertiesViewerComparator();

		listViewer.setComparator(comparator);
	}

	public void refresh() {
		listViewer.refresh();
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		this.node = (SectionProvider) input;
		GWNode model = this.node.getModel();
		Map<String, Object> p = model.getProperties();
		this.properties = ModelProperties.filterCustomProperty(p);
		listViewer.setInput(this.properties.entrySet());
	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		fillComposite(composite);
	}

	public class KeyEditingSupport extends EditingSupport {

		private final CellEditor editor;

		public KeyEditingSupport(TableViewer viewer) {
			super(viewer);

			this.editor = new TextCellEditor(viewer.getTable());
			this.editor.setValidator(new ICellEditorValidator() {
				public String isValid(Object value) {
					if ((value == null) || (String.valueOf(value).trim().length() == 0)
							|| (!ModelProperties.isCustomProperty(String.valueOf(value)))) {
						return "Invalid value";
					}
					return null;
				}
			});
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			if (!ModelProperties.isCustomProperty(entry.getKey()))
				return false;
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			return entry.getKey();
		}

		@Override
		protected void setValue(Object element, Object userInputValue) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			String key = String.valueOf(userInputValue);

			if (!ModelProperties.isCustomProperty(key))
				return;

			properties.put(key, properties.remove(entry.getKey()));
			IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
			p.setPropertyValue(ModelProperties.PROPERTY_CUSTOM, duplicate(properties));
			refresh();
		}
	}

	public class ValueEditingSupport extends EditingSupport {

		private final TextCellEditor textEditor;

		public ValueEditingSupport(TableViewer viewer) {
			super(viewer);

			this.textEditor = new TextCellEditor(viewer.getTable());

		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			boolean valid = (entry.getValue() instanceof String) || (entry.getValue() instanceof Number)
					|| (entry.getValue() instanceof Boolean);

			if (valid)
				return textEditor;
			return null;
		}

		@Override
		protected boolean canEdit(Object element) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;

			if (!ModelProperties.isCustomProperty(entry.getKey()))
				return false;

			boolean editable = (entry.getValue() instanceof String) || (entry.getValue() instanceof Boolean)
					|| (entry.getValue() instanceof Number);
			return editable;
		}

		@Override
		protected Object getValue(Object element) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			boolean valid = (entry.getValue() instanceof String) || (entry.getValue() instanceof Number)
					|| (entry.getValue() instanceof Boolean);
			if (valid) {
				return String.valueOf(entry.getValue());
			}
			return null;
		}

		@Override
		protected void setValue(Object element, Object userInputValue) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) element;
			properties.put((String) entry.getKey(), userInputValue);
			IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
			p.setPropertyValue(ModelProperties.PROPERTY_CUSTOM, duplicate(properties));
			refresh();
		}
	}

	private Map<String, Object> duplicate(Map<String, Object> properties) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Iterator<String> iter = properties.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = properties.get(key);
			ret.put(key, value);
		}
		return ret;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				listViewer.getTable().setSortDirection(dir);
				listViewer.getTable().setSortColumn(column);
				listViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	public class PropertiesViewerComparator extends ViewerComparator {
		private int propertyIndex;
		private static final int DESCENDING = 1;
		private int direction = DESCENDING;

		public PropertiesViewerComparator() {
			this.propertyIndex = 0;
			direction = DESCENDING;
		}

		public int getDirection() {
			return direction == 1 ? SWT.DOWN : SWT.UP;
		}

		public void setColumn(int column) {
			if (column == this.propertyIndex) {
				// Same column as last sort; toggle the direction
				direction = 1 - direction;
			} else {
				// New column; do an ascending sort
				this.propertyIndex = column;
				direction = DESCENDING;
			}
		}

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			Map.Entry<String, Object> p1 = (Map.Entry<String, Object>) e1;
			Map.Entry<String, Object> p2 = (Map.Entry<String, Object>) e2;
			int rc = 0;
			switch (propertyIndex) {
			case 0:
				rc = p1.getKey().compareTo(p2.getKey());
				break;
			case 1:
				rc = String.valueOf(p1.getValue()).compareTo(String.valueOf(p2.getValue()));
				break;
			default:
				rc = 0;
			}
			if (direction == DESCENDING) {
				rc = -rc;
			}
			return rc;
		}
	}

}
