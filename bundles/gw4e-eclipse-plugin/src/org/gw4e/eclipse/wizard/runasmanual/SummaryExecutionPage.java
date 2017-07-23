package org.gw4e.eclipse.wizard.runasmanual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;

public class SummaryExecutionPage extends WizardPage {
	private Table table;
	private List<StepDetail> details;
	public static String NAME = "SummaryExecutionPage";
	
	TableViewer tv;
	Map<Object, Button> buttons = new HashMap<Object, Button>();
	
	public static final String GW4E_MANUAL_ELEMENT_ID = "id.gw4e.manual.id";
	public static final String GW4E_MANUAL_TABLE_VIEWER_SUMMARY_ID = "id.gw4e.manual.tableviewer.summary";

	
	protected SummaryExecutionPage(String pageName, List<StepDetail> details) {
		super(pageName);
		this.details = details;
	}

	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		setControl(control);
		control.setLayout(new GridLayout(1, false));

		tv = new TableViewer(control, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		ColumnViewerToolTipSupport.enableFor(tv);
		table = tv.getTable();
		table.setData(GW4E_MANUAL_ELEMENT_ID,GW4E_MANUAL_TABLE_VIEWER_SUMMARY_ID);
	 	
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tableViewerColumnStatus = new TableViewerColumn(tv, SWT.NONE);
		tableViewerColumnStatus.getColumn().setWidth(200);
		tableViewerColumnStatus.getColumn().setText(MessageUtil.getString("status"));
		tableViewerColumnStatus.setLabelProvider(new ColumnLabelProvider() {

			private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());

			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				StepDetail sd = (StepDetail) element;
				if (sd.isPerformed()) {
					if (sd.isFailed()) {
						ImageDescriptor descriptor = Activator.getImageDescriptor("icons/redball.png");
						return resourceManager.createImage(descriptor);
					} else {
						ImageDescriptor descriptor = Activator.getImageDescriptor("icons/greenball.png");
						return resourceManager.createImage(descriptor);
					}
				} else {
					ImageDescriptor descriptor = Activator.getImageDescriptor("icons/orangeball.png");
					return resourceManager.createImage(descriptor);
				}
			}

			@Override
			public void dispose() {
				super.dispose();
				resourceManager.dispose();
			}
		});

		TableViewerColumn tableViewerColumnSteps = new TableViewerColumn(tv, SWT.NONE);
		tableViewerColumnSteps.getColumn().setWidth(100);
		tableViewerColumnSteps.getColumn().setText(MessageUtil.getString("steps"));
		tableViewerColumnSteps.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StepDetail sd = (StepDetail) element;
				return sd.getName();
			}
		});

		TableViewerColumn tableViewerColumnResults = new TableViewerColumn(tv, SWT.NONE);
		tableViewerColumnResults.getColumn().setWidth(100);
		tableViewerColumnResults.getColumn().setText(MessageUtil.getString("results"));
		tableViewerColumnResults.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StepDetail sd = (StepDetail) element;
				String text = sd.getResult();
				if (text != null && RunAsManualWizard.ENTER_DEFAULT_RESULT_MESSAGE.equalsIgnoreCase(text.trim())) {
					return "";
				}
				return sd.getResult();
			}
		});

		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("       ");

		TableViewerColumn actionsNameCol = new TableViewerColumn(tv, column);
		actionsNameCol.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public void update(ViewerCell cell) {

				TableItem item = (TableItem) cell.getItem();
				Button button;
				if (buttons.containsKey(cell.getElement())) {
					button = buttons.get(cell.getElement());
				} else {
					button = new Button((Composite) cell.getViewerRow().getControl(), SWT.NONE);
					button.setText("...");
					button.setToolTipText(MessageUtil.getString("update_results"));
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "",
									MessageUtil.getString("enter_result"), "", null);
							if (dlg.open() != Window.OK) {
								return;
							}
							StepDetail sd = (StepDetail) item.getData();
							sd.setResult(dlg.getValue());
							setInput();
						}
					});

					buttons.put(cell.getElement(), button);

				}
				TableEditor editor = new TableEditor(item.getParent());
				editor.grabHorizontal = true;
				editor.grabVertical = true;
				editor.setEditor(button, item, cell.getColumnIndex());
				editor.layout();
			}

		});

		TableViewerColumn tableViewerColumnDescription = new TableViewerColumn(tv, SWT.NONE);
		tableViewerColumnDescription.getColumn().setWidth(100);
		tableViewerColumnDescription.getColumn().setText(MessageUtil.getString("description"));
		tableViewerColumnDescription.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				StepDetail sd = (StepDetail) element;
				return sd.getDescription();
			}

			@Override
			public String getToolTipText(Object element) {
				StepDetail sd = (StepDetail) element;
				return sd.getDescription();
			}
		});

		Menu menu = new Menu(table);
		table.setMenu(menu);
		MenuItem setStatusItem = new MenuItem(menu, SWT.CASCADE);
		setStatusItem.setText(MessageUtil.getString("set_status_to"));
		Menu subMenu = new Menu(menu);
		setStatusItem.setMenu(subMenu);
		MenuItem setStatusToSuccess = new MenuItem(subMenu, SWT.PUSH);
		setStatusToSuccess.setText(MessageUtil.getString("set_status_to_success"));
		setStatusToSuccess.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = table.getSelection();
				for (TableItem tableItem : items) {
					StepDetail sd = (StepDetail) tableItem.getData();
					sd.setFailed(false);
					sd.setPerformed(true);
					sd.setResult("");
				}
				setInput();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem setStatusToWarning = new MenuItem(subMenu, SWT.PUSH);
		setStatusToWarning.setText(MessageUtil.getString("set_status_to_notperformed"));
		setStatusToWarning.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "",
						MessageUtil.getString("enter_notperformed_reason"), "", null);
				if (dlg.open() != Window.OK) {
					return;
				}

				TableItem[] items = table.getSelection();
				for (TableItem tableItem : items) {
					StepDetail sd = (StepDetail) tableItem.getData();
					sd.setPerformed(false);
					sd.setResult(dlg.getValue());
				}
				setInput();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		MenuItem setStatusToFailed = new MenuItem(subMenu, SWT.PUSH);
		setStatusToFailed.setText(MessageUtil.getString("set_status_to_failed"));
		setStatusToFailed.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "",
						MessageUtil.getString("enter_failure_reason"), "", null);
				if (dlg.open() != Window.OK) {
					return;
				}
				TableItem[] items = table.getSelection();
				for (TableItem tableItem : items) {
					StepDetail sd = (StepDetail) tableItem.getData();
					sd.setFailed(true);
					sd.setPerformed(true);
					sd.setResult(dlg.getValue());
				}
				setInput();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		// Do not show menu, when no item is selected
		table.addListener(SWT.MenuDetect, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (table.getSelectionCount() <= 0) {
					event.doit = false;
				}

			}
		});

		tv.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				List<StepDetail> in = (List<StepDetail>) inputElement;
				StepDetail[] ret = new StepDetail[in.size()];
				in.toArray(ret);
				return ret;
			}
		});

		table.pack();
	}

	@Override
	public void setVisible(boolean visible) {
		setInput();
		super.setVisible(visible);
	}

	private void setInput() {
		tv.setInput(details);
		for (int i = 0, n = table.getColumnCount(); i < n; i++)
			table.getColumn(i).pack();
	}

	public void dispose() {
		try {
			buttons.values().stream().forEach(item -> item.dispose());
		} catch (Exception e) {

		}
		super.dispose();
	}

	public List<StepDetail> getStepDetails() {
		TableItem[] items = table.getItems();
		List<StepDetail> details = new ArrayList<StepDetail>();
		for (TableItem tableItem : items) {
			StepDetail sd = (StepDetail) tableItem.getData();
			details.add(sd);
		}
		return details;
	}

}
