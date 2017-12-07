
package org.gw4e.eclipse.studio.search.searchprovider.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.studio.search.searchprovider.ModelSearchQuery;
import org.gw4e.eclipse.studio.search.searchprovider.ModelSearchQueryDefinition;
import org.gw4e.eclipse.studio.search.searchprovider.operator.Operators;
import org.gw4e.eclipse.wizard.convert.page.TableHelper;

public class ModelSearchPage extends DialogPage implements ISearchPage {

	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.search.widget.id";
	public static final String GW4E_SEARCH_PROJECTS_TABLE = "id.gw4e.search.table.id";

	private ISearchPageContainer fContainer;
	private Table tableProjects;
	private Table tableCriteria;
	private TableViewer tableCriteriaViewer;

	 
	private final String PROPERTY_COLUMN 		= MessageUtil.getString("Property");
	private final String OPERATOR_COLUMN 		= MessageUtil.getString("Operator");
	private final String VALUE_COLUMN 			= MessageUtil.getString("Value");;

	 
	private String[] columnNames = new String[] { 
			PROPERTY_COLUMN, 
			OPERATOR_COLUMN,
			VALUE_COLUMN,
			};
	
	private static class SearchComposite extends Composite {
		public SearchComposite(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		public void setLayoutData(Object layoutData) {
			if (getLayoutData() == null)
				super.setLayoutData(layoutData);
		}
	}

	public ModelSearchPage() {
		Operators.clear();
	}

	@Override
	public boolean performAction() {
		ModelSearchQuery searchQuery = new ModelSearchQuery(buildQueryDefinition());
		NewSearchUI.runQueryInForeground(fContainer.getRunnableContext(), searchQuery);

		return true;
	}

	@Override
	public void setContainer(ISearchPageContainer container) {
		fContainer = container;
	}

	@Override
	public void createControl(Composite parent) {
		SearchComposite result = new SearchComposite(parent, SWT.NONE);
		result.setFont(parent.getFont());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(result);
		GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(false).applyTo(result);

		Label lblScope = new Label(result, SWT.NONE);
		lblScope.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScope.setText(MessageUtil.getString("scope"));

		Composite scopeComposite = new Composite(result, SWT.NONE);
		scopeComposite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Button btnWorkspace = new Button(scopeComposite, SWT.RADIO);
		btnWorkspace.setSelection(false);
		btnWorkspace.setText(MessageUtil.getString("workspace"));

		Button btnProjects = new Button(scopeComposite, SWT.RADIO);
		btnProjects.setText(MessageUtil.getString("projects"));
		new Label(result, SWT.NONE);

		Label lblRoot = new Label(result, SWT.NONE);
		lblRoot.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRoot.setText(MessageUtil.getString("projects"));

		CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer.newCheckList(result,
				SWT.BORDER | SWT.FULL_SELECTION);
		tableProjects = checkboxTableViewer.getTable();
		tableProjects.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_SEARCH_PROJECTS_TABLE);
		tableProjects.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableColumn additionalColumn = new TableColumn(checkboxTableViewer.getTable(), SWT.FILL);
		additionalColumn.setText(MessageUtil.getString("projects"));
		additionalColumn.pack();
		checkboxTableViewer.getTable().setHeaderVisible(true);
		checkboxTableViewer.getTable().setLinesVisible(true);

		checkboxTableViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				IJavaProject[] elements = GraphWalkerContextManager.getGW4EProjects();
				return elements;
			}
		});
		checkboxTableViewer.setLabelProvider(new JavaElementLabelProvider());
		checkboxTableViewer.getTable().addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableHelper.handleEvent(event);
			}
		});

		new Label(result, SWT.NONE);

		Label lblFilter = new Label(result, SWT.NONE);
		lblFilter.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFilter.setText(MessageUtil.getString("search_criteria"));

		tableCriteriaViewer = new TableViewer(result, SWT.BORDER | SWT.FULL_SELECTION);
		tableCriteriaViewer.setColumnProperties(columnNames);
		tableCriteria = tableCriteriaViewer.getTable();
		tableCriteriaViewer.setUseHashlookup(true);
		tableCriteria.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TableViewerColumn col = createTableViewerColumn(tableCriteriaViewer,PROPERTY_COLUMN, 0);
		col.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(((Operators) cell.getElement()).getProperty());
            }
        });
        col.setEditingSupport(new PropertyEditingSupport(tableCriteriaViewer));
        
        col = createTableViewerColumn(tableCriteriaViewer,OPERATOR_COLUMN, 1);
        col.setLabelProvider(new CellLabelProvider() {
            @Override
            public void update(ViewerCell cell) {
                cell.setText(((Operators) cell.getElement()).getSoperator());
            }
        });
        col.setEditingSupport(new OperatorEditingSupport(tableCriteriaViewer));
        
        col = createTableViewerColumn(tableCriteriaViewer,VALUE_COLUMN, 2);
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Operators p = (Operators) element;
                return setToString(p.getChallengedValues());
            }
        });
        col.setEditingSupport(new ValueEditingSupport(tableCriteriaViewer));
        
		 

		tableCriteriaViewer.getTable().setHeaderVisible(true);
		tableCriteriaViewer.getTable().setLinesVisible(true);

		tableCriteriaViewer.setContentProvider(new OperatorContentProvider());
	 
		Composite composite = new Composite(result, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		Button btnAddCriteria = new Button(composite, SWT.NONE);
		btnAddCriteria.setText(MessageUtil.getString("add"));
		btnAddCriteria.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event ev) {
				switch (ev.type) {
				case SWT.Selection:
					Operators.addDefault();
					tableCriteriaViewer.setInput(Operators.getAll());
					packCriteriaTable();
				}
			}
		});

		Button btnRemoveCriteria = new Button(composite, SWT.NONE);
		btnRemoveCriteria.setText(MessageUtil.getString("remove"));

		setControl(result);

		checkboxTableViewer.setInput(ResourceManager.getWorkspaceRoot());

		btnWorkspace.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					tableProjects.setEnabled(!btnWorkspace.getSelection());
					TableItem[] items = checkboxTableViewer.getTable().getItems();
					for (int i = 0; i < items.length; i++) {
						TableItem ti = items[i];
						ti.setChecked(btnWorkspace.getSelection());
					}
				}
			}
		});
		btnProjects.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					tableProjects.setEnabled(btnProjects.getSelection());
				}
			}
		});
		btnWorkspace.setSelection(true);
		btnWorkspace.notifyListeners(SWT.Selection, new Event());

		btnAddCriteria.notifyListeners(SWT.Selection, new Event());

	}

	private void packCriteriaTable() {
		Stream<TableColumn> stream = Arrays.stream(tableCriteria.getColumns());
		stream.forEach(tc -> tc.pack());
	}

	private List<String> getModelPropertyNames() throws FileNotFoundException, CoreException, IOException {
		List<IProject> files = new ArrayList<IProject>();

		Stream<TableItem> stream = Arrays.stream(tableProjects.getItems());
		stream.filter(item -> item.getChecked()).map(item -> item.getData())
				.forEach(jp -> files.add(((IJavaProject) jp).getProject()));

		List<String> ret = GraphWalkerFacade.getPropertiesForGraphModels(files);
		if(ret!=null) ret.add(MessageUtil.getString("requirement_property"));
		return ret;
	}

	private String hanldeRequirementRequest(String property) {
		if (MessageUtil.getString("requirement_property").equalsIgnoreCase(property.trim())) {
			return GraphWalkerFacade.REQUIREMENT;
		}
		return property;
	}

	private ModelSearchQueryDefinition buildQueryDefinition() {

		Stream<TableItem> stream = Arrays.stream(tableCriteria.getItems());

		List<Operators> parts = stream
				.map(item -> new Operators(hanldeRequirementRequest(item.getText(0)), item.getText(1), item.getText(2)))
				.collect(Collectors.toList());

		Stream<TableItem> streamProjects = Arrays.stream(tableProjects.getItems());

		List<IProject> projects = streamProjects.filter(item -> item.getChecked())
				.map(item -> ((IJavaProject) item.getData()).getProject()).collect(Collectors.toList());

		ModelSearchQueryDefinition definition = new ModelSearchQueryDefinition(projects, parts);
		return definition;
	}
	
	
	private TableViewerColumn createTableViewerColumn(TableViewer viewer, String title, final int colNumber) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setResizable(true);
        return viewerColumn;
    }
	
	public class ValueEditingSupport extends EditingSupport {

	    private final TableViewer viewer;
	    private final CellEditor editor;

	    public ValueEditingSupport(TableViewer viewer) {
	        super(viewer);
	        this.viewer = viewer;
	        this.editor = new TextCellEditor(viewer.getTable());
	    }

	    @Override
	    protected CellEditor getCellEditor(Object element) {
	        return editor;
	    }

	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }

	    @Override
	    protected Object getValue(Object element) {
	        return setToString (((Operators) element).getChallengedValues());
	    }

	    @Override
	    protected void setValue(Object element, Object value) {
	    		Operators op = (Operators) element;
			op.setChallengedValues(Arrays.asList(((String)value).split("\\s*,\\s*")).stream().collect(Collectors.toSet()));
			viewer.update(element, null);
	    }
	}
	
	public class OperatorEditingSupport extends EditingSupport {
		private  TableViewer viewer;
		public OperatorEditingSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			String[] operators = ModelSearchQueryDefinition.getAvailableOperators().stream().toArray(String[]::new);
			return new ComboBoxCellEditor(tableCriteria, operators);
		}

		@Override
		protected Object getValue(Object element) {
			Operators op = (Operators) element;
			String s   = op.getSoperator();
			return Operators.getAvailableOperators().indexOf(s);
		}

		@Override
		protected void setValue(Object element, Object value) {
			Operators op = (Operators) element;
			Integer index = (Integer)value;
			if(index<0) return;
			op.setSoperator(Operators.getAvailableOperators().get(index));
			viewer.update(element, null);
		}
	}
	
	public class PropertyEditingSupport extends EditingSupport {
		private  TableViewer viewer;
		String[] properties;
		public PropertyEditingSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
			properties = new String[0];

		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			try {
				properties = Optional.ofNullable(getModelPropertyNames()).orElse(new ArrayList<String>()).stream()
						.toArray(String[]::new);
			} catch (CoreException | IOException e) {
				ResourceManager.logException(e);
			}
			return new ComboBoxCellEditor(viewer.getTable(), properties);
		}

		@Override
		protected Object getValue(Object element) {
			Operators op = (Operators) element;
			String p = op.getProperty();
			try {
				properties = Optional.ofNullable(getModelPropertyNames()).orElse(new ArrayList<String>()).stream()
						.toArray(String[]::new);
			} catch (CoreException | IOException e) {
				ResourceManager.logException(e);
			}
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].equalsIgnoreCase(p))
					return i;
			}
			return 0;
		}

		@Override
		protected void setValue(Object element, Object value) {
			Operators op = (Operators) element;
			Integer index = (Integer)value;
			if(index<0) return;
			op.setProperty(properties [index]);
			viewer.update(element, null);
		}
	}

	 

	class OperatorContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object arg0) {
			List<Operators> operators = (List<Operators>)arg0;
			Object[] ret =  new Object[operators.size()];
			operators.toArray(ret);
			return ret;
		}
	}

	 
	
	private String setToString (Set<String> values) {
		String s = values.stream().map(e -> e.toString()).reduce("", (elem,e) -> {
			elem = elem + "," + e;
			return elem; 
		});
		return s.substring(1);
	}

}
