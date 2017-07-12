package org.gw4e.eclipse.launching.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.convert.page.TableHelper;

public class ModelPathGenerator extends Composite {
	private Table table;
	private Button hintButton;
	private Button filterGraphmlButton;
	private ModelData additionalExecutionContexts[];
	private CheckboxTableViewer tableViewer;
	private ComboBoxViewerCellEditor editor;

	public static String GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_ID = "gw4e.launch.configuration.control.id";

	public static String GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_TABLE = "gw4e.launch.configuration.path.generator.table";

	public static String GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_COMBO_EDITOR = "gw4e.launch.configuration.combo.editor";
	

	public ModelPathGenerator(Composite parent, int style, Listener listener) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		tableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setData(GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_ID,GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_TABLE);
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				Display display = Display.getDefault();

				Runnable longJob = new Runnable() {
					public void run() {
						display.syncExec(new Runnable() {
							public void run() {
								if (additionalExecutionContexts != null && !forceUpdate)
									return; // Otherwise the selected value in
											// the combobox is reseted
								forceUpdate = false;
								if (inputElement == null) {
									additionalExecutionContexts = new ModelData[0];
								}
								IFile ifile = (IFile) inputElement;
								List<IFile> all = new ArrayList<IFile>();
								try {
									ResourceManager.getAllGraphFiles(ifile.getProject(), all);
									if (ModelPathGenerator.this.hintButton.getSelection()) {
										all = GraphWalkerFacade.findSharedContexts(ifile, all);
									}
									if (ModelPathGenerator.this.filterGraphmlButton.getSelection()) {
										all = all.stream()
												.filter(file -> !Constant.GRAPHML_FILE.equals(file.getFileExtension()))
												.collect(Collectors.toList());
									}
									all.remove(ifile);
								} catch (Exception e) {
									ResourceManager.logException(e);
									additionalExecutionContexts = new ModelData[0];
								}
								additionalExecutionContexts = all.stream().map(item -> new ModelData(item))
										.toArray(ModelData[]::new);
							}
						});
						display.wake();
					}
				};
				BusyIndicator.showWhile(display, longJob);
				return additionalExecutionContexts;
			}
		});

		TableViewerColumn tableViewerColumnModel = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumnModel.getColumn().setWidth(100);
		tableViewerColumnModel.getColumn().setText(MessageUtil.getString("methoddialog_title"));
		tableViewerColumnModel.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				ModelData data = (ModelData) cell.getElement();
				data.initialize(initialModels);
				cell.setText(data.getName());
			}

			public String getToolTipText(Object element) {
				ModelData data = (ModelData) element;
				return data.getFullPath();
			}

			public Point getToolTipShift(Object object) {
				return new Point(5, 5);
			}

			public int getToolTipDisplayDelayTime(Object object) {
				return 500;
			}

			public int getToolTipTimeDisplayed(Object object) {
				return 5000;
			}

		});

		TableViewerColumn tableViewerColumnPathGenerator = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumnPathGenerator.getColumn().setWidth(100);
		tableViewerColumnPathGenerator.getColumn().setText(MessageUtil.getString("path_generator_header_title"));
		tableViewerColumnPathGenerator.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				ModelData data = (ModelData) cell.getElement();
				cell.setText(data.getSelectedPolicy());
			}
		});

		tableViewerColumnPathGenerator.setEditingSupport(new EditingSupport(tableViewer) {

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				ModelData data = (ModelData) element;

				editor = new ComboBoxViewerCellEditor(table);
				editor.getControl().setData(GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_ID,GW4E_LAUNCH_CONFIGURATION_PATH_GENERATOR_COMBO_EDITOR);
				
				((CCombo) editor.getControl()).addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
						ModelData value = (ModelData) sel.getFirstElement();
					}
				});
				editor.setContentProvider(new ArrayContentProvider());
				editor.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						BuildPolicy data = (BuildPolicy) element;
						return data.getPathGenerator();
					}
				});
				editor.setInput(data.getPolicies());
				return editor;
			}

			@Override
			protected Object getValue(Object element) {
				ModelData data = (ModelData) element;
				return data.getSelectedPolicy();
			}

			@Override
			protected void setValue(Object element, Object value) {
				ModelData data = (ModelData) element;
				if (value != null && value instanceof BuildPolicy) {
					data.setSelectedPolicy(((BuildPolicy) value).getPathGenerator());
					listener.handleEvent(null);
				}
				tableViewer.update(element, null);

			}
		});

		tableViewer.getTable().addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableHelper.handleEvent(event);
			}
		});

		tableViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				ModelData data = (ModelData) event.getElement();
				data.setSelected(event.getChecked());
				listener.handleEvent(null);
			}
		});
	}

	IFile mainFile;

	public void refresh(IFile mainFile) {
		this.mainFile = mainFile;
		updateModelsAndPathGenerators();
	}

	boolean forceUpdate = false;

	private void updateModelsAndPathGenerators() {
		forceUpdate = true;
		tableViewer.setInput(mainFile);
		TableItem []  all = table.getItems();
		int index=0;
		for (TableItem tableItem : all) {
			ModelData d =  (ModelData) tableItem.getData();
			tableItem.setChecked(additionalExecutionContexts[index++].isSelected());
		}
		for (int i = 0, n = tableViewer.getTable().getColumnCount(); i < n; i++)
			tableViewer.getTable().getColumn(i).pack();
	}

	ModelData[] initialModels;

	public void initialize(ModelData[] models) {
		this.initialModels = models;
	}

	public String validate() {
		TableItem[] items = tableViewer.getTable().getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem ti = items[i];
			if (ti.getChecked()) {
				ModelData data = (ModelData) ti.getData();
				return data.validatePolicy();
			}
		}
		return null;
	}

	public ModelData[] getModel() {
		List<ModelData> temp = new ArrayList<ModelData>();

		TableItem[] items = tableViewer.getTable().getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem ti = items[i];
			ModelData data = (ModelData) ti.getData();
			temp.add(data);
		}
		ModelData[] ret = new ModelData[temp.size()];
		temp.toArray(ret);
		return ret;
	}

	public void setButtons(Button hintButton, Button filterGraphmlButton) {
		this.hintButton = hintButton;
		this.filterGraphmlButton = filterGraphmlButton;
		hintButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateModelsAndPathGenerators();
			}
		});

		filterGraphmlButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateModelsAndPathGenerators();
			}
		});
	}

}
