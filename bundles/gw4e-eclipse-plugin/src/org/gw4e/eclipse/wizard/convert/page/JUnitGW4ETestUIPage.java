package org.gw4e.eclipse.wizard.convert.page;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.convert.ConvertToFileCreationWizard;
import org.gw4e.eclipse.wizard.convert.model.JUnitTestPage;

public class JUnitGW4ETestUIPage extends WizardPage implements Listener {

	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.conversion.widget.id";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_RUN_SMOKE_TEST_CHECKBOX = "id.gw4e.conversion.choice.run.smoke.test";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_RUN_FUNCTIONNAL_TEST_CHECKBOX = "id.gw4e.conversion.choice.run.func.test";
	public static final String GW4E_CONVERSION_CHOICE_GENERATE_RUN_STABILITY_TEST_CHECKBOX = "id.gw4e.conversion.choice.run.stability.test";
	public static final String GW4E_CONVERSION_COMBO_TARGET_ELEMENT = "id.gw4e.conversion.combo.target.id";
	public static final String GW4E_CONVERSION_BUTTON_HINT_ID = "id.gw4e.conversion.button.hint.id";
	public static final String GW4E_CONVERSION_COMBO_START_ELEMENT = "id.gw4e.conversion.combo.start.element";
	public static final String GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON = "gw4e.launch.test.project.hint.button";
	public static final String GW4E_LAUNCH_TEST_CONFIGURATION_FILTER_GRAPHML_BUTTON = "gw4e.launch.test.project.filtergraphml.button";
	public static final String GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_CONTEXT = "gw4e.launch.test.configuration.additional.context";

	/**
	 * 
	 */
	Context context = null;

	/**
	 * 
	 */
	private Button generateRunSmokeTest;
	/**
	 * 
	 */
	private Button generateRunFunctionalTest;
	/**
	 * 
	 */
	private Button generateRunStabilityTest;

	/**
	 * 
	 */
	private Combo comboVertex;
	/**
	 * 
	 */
	private ComboViewer comboReachedVertexViewer;

	/**
	 * 
	 */
	Label labelTargetVertex;

	/**
	 * 
	 */
	private ElementCombo edgesCombo;

	/**
	 * 
	 */
	private IStructuredSelection selection;

	/**
	 * 
	 */
	private RuntimeVertex reachedVertex = null;

	/**
	 * 
	 */
	private CheckboxTableViewer fAdditionalTestViewer;

	/**
	 * 
	 */
	Label labelChooseAdditionalContext;

	/**
	 * 
	 */
	private IFile additionalExecutionContexts[];

	/**
	 * 
	 */
	Button hintButton;

	/**
	 * 
	 */
	Button filterGraphmlButton;
	
	public JUnitGW4ETestUIPage(ConvertToFileCreationWizard wizard, IWorkbench workbench,
			IStructuredSelection selection) {
		super("JUnitGraphWalkerTestImplementationPage");
		this.selection = selection;

		this.setTitle(MessageUtil.getString("generate_a_graphwalker_annotated_test")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("choose_the_generation_option")); //$NON-NLS-1$
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());

		Group groupJunitTest = new Group(composite, SWT.NONE);
		groupJunitTest.setLayout(new GridLayout());
		groupJunitTest.setText(MessageUtil.getString("generationJunitTestOptions")); //$NON-NLS-1$
		groupJunitTest.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		generateRunSmokeTest = new Button(groupJunitTest, SWT.CHECK);
		generateRunSmokeTest.setText(MessageUtil.getString("generateSmokeTest")); //$NON-NLS-1$
		generateRunSmokeTest.setSelection(false);
		generateRunSmokeTest.setEnabled(true);
		generateRunSmokeTest.setData(GW4E_CONVERSION_WIDGET_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_RUN_SMOKE_TEST_CHECKBOX);

		generateRunSmokeTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateRunSmokeTest.setFocus();
					comboVertex.setEnabled(generateRunSmokeTest.getSelection());
					labelTargetVertex.setEnabled(generateRunSmokeTest.getSelection());
					updateUI();
					break;
				}
			}
		});

		labelTargetVertex = new Label(groupJunitTest, SWT.BORDER);
		labelTargetVertex.setText(MessageUtil.getString("targetVertex"));
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd.horizontalIndent = 25;
		labelTargetVertex.setLayoutData(gd);
		labelTargetVertex.setEnabled(false);

		comboReachedVertexViewer = new ComboViewer(groupJunitTest);
		comboVertex = comboReachedVertexViewer.getCombo();
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.horizontalIndent = 25;
		comboVertex.setLayoutData(gd);
		comboVertex.setEnabled(false);

		comboReachedVertexViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				List<RuntimeVertex> loadVertices = (List<RuntimeVertex>) inputElement;
				Object[] ret = new Object[loadVertices.size()];
				loadVertices.toArray(ret);
				return ret;
			}
		});
		comboReachedVertexViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof RuntimeVertex) {
					RuntimeVertex vertex = (RuntimeVertex) element;
					return vertex.getName();
				}
				return "?";
			}
		});
		comboReachedVertexViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() > 0) {
					reachedVertex = (RuntimeVertex) selection.getFirstElement();
					updateUI();
				}
			}
		});
		comboVertex.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_COMBO_TARGET_ELEMENT);

		generateRunFunctionalTest = new Button(groupJunitTest, SWT.CHECK);
		generateRunFunctionalTest.setText(MessageUtil.getString("generateRunFunctional")); //$NON-NLS-1$
		generateRunFunctionalTest.setSelection(false);
		generateRunFunctionalTest.setEnabled(true);
		generateRunFunctionalTest.setData(GW4E_CONVERSION_WIDGET_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_RUN_FUNCTIONNAL_TEST_CHECKBOX);

		generateRunFunctionalTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateRunFunctionalTest.setFocus();
					updateUI();
					break;
				}
			}
		});

		generateRunStabilityTest = new Button(groupJunitTest, SWT.CHECK);
		generateRunStabilityTest.setText(MessageUtil.getString("generateRunStability")); //$NON-NLS-1$
		generateRunStabilityTest.setSelection(false);
		generateRunStabilityTest.setEnabled(true);
		generateRunStabilityTest.setData(GW4E_CONVERSION_WIDGET_ID,
				GW4E_CONVERSION_CHOICE_GENERATE_RUN_STABILITY_TEST_CHECKBOX);

		generateRunStabilityTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					generateRunStabilityTest.setFocus();
					updateUI();
					break;
				}
			}
		});

		Group groupStartElelement = new Group(composite, SWT.NONE);
		groupStartElelement.setLayout(new GridLayout());
		groupStartElelement.setText(MessageUtil.getString("StartElement")); //$NON-NLS-1$
		groupStartElelement.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		edgesCombo = new ElementCombo(groupStartElelement, SWT.NONE, false, this);
		edgesCombo.getCombo().setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_COMBO_START_ELEMENT);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		edgesCombo.setLayoutData(gridData);

		Group groupAdditionalTest = new Group(composite, SWT.NONE);
		groupAdditionalTest.setLayout(new GridLayout());
		groupAdditionalTest.setText(MessageUtil.getString("what_are_the_additional_graph_file_to_add")); //$NON-NLS-1$
		groupAdditionalTest.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		Composite composite_1 = new Composite(groupAdditionalTest, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite_1.setLayout(new GridLayout(3, false));

		labelChooseAdditionalContext = new Label(composite_1, SWT.BORDER);
		labelChooseAdditionalContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		labelChooseAdditionalContext.setSize(190, 14);
		labelChooseAdditionalContext.setText(MessageUtil.getString("additionalExecutionContext"));
		labelChooseAdditionalContext.setEnabled(false);
		new Label(composite_1, SWT.NONE);
		
				filterGraphmlButton = new Button(composite_1, SWT.CHECK);
				filterGraphmlButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
				filterGraphmlButton.setSize(44, 18);
				filterGraphmlButton.setText(MessageUtil.getString("filter_graphml_file"));
				filterGraphmlButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent evt) {
						hint();
					}
				});
				filterGraphmlButton.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_LAUNCH_TEST_CONFIGURATION_FILTER_GRAPHML_BUTTON);

		fAdditionalTestViewer = CheckboxTableViewer.newCheckList(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		Table additionalTable = fAdditionalTestViewer.getTable();
		additionalTable.setData(GW4E_CONVERSION_WIDGET_ID,
				GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_CONTEXT);
		additionalTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		additionalTable.setSize(107, 34);
		additionalTable.setEnabled(false);

		TableColumn additionalColumn = new TableColumn(fAdditionalTestViewer.getTable(), SWT.FILL);
		additionalColumn.setText(MessageUtil.getString("graphfiles"));
		additionalColumn.pack();
		fAdditionalTestViewer.getTable().setHeaderVisible(true);
		fAdditionalTestViewer.getTable().setLinesVisible(true);
		new Label(composite_1, SWT.NONE);
		
		
		hintButton = new Button(composite_1, SWT.CHECK);
		hintButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		hintButton.setSize(44, 18);
		hintButton.setText(MessageUtil.getString("hint"));
		hintButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				hint();
			}
		});
		hintButton.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON);
		
		fAdditionalTestViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				Display display = Display.getCurrent();

				Runnable longJob = new Runnable() {
					public void run() {
						display.syncExec(new Runnable() {
							public void run() {
								if (inputElement == null) {
									additionalExecutionContexts = new IFile[0];
								}
								IFile ifile = (IFile) inputElement;
								List<IFile> all = new ArrayList<IFile>();
								try {
									ResourceManager.getAllGraphFiles(ifile.getProject(), all);
									if (hintButton.getSelection()) {
										all = GraphWalkerFacade.findSharedContexts(ifile, all);
									}
									if (filterGraphmlButton.getSelection()) {
										all = all.stream()
												.filter(file -> !Constant.GRAPHML_FILE.equals(file.getFileExtension()))
												.collect(Collectors.toList()); 										
									}
 									all.remove(ifile);
								} catch (Exception e) {
									ResourceManager.logException(e);
									additionalExecutionContexts = new IFile[0];
								}
								additionalExecutionContexts = new IFile[all.size()];
								all.toArray(additionalExecutionContexts);
							}
						});
						display.wake();
					}
				};
				BusyIndicator.showWhile(display, longJob);
				return additionalExecutionContexts;
			}
		});
		ILabelProvider labelProvider = new ILabelProvider() {
			@Override
			public void addListener(ILabelProviderListener arg0) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener arg0) {
			}

			@Override
			public Image getImage(Object arg0) {
				return null;
			}

			@Override
			public String getText(Object object) {
				if (object instanceof IFile) {
					return ((IFile) object).getFullPath().toString();
				}
				return null;
			}

		};

		fAdditionalTestViewer.setLabelProvider(labelProvider);

		fAdditionalTestViewer.getTable().addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableHelper.handleEvent(event);
			}
		});

		setControl(composite);

		setPageComplete(validatePage());

		loadVertices();

		edgesCombo.loadElements(selection);

		handleEvent(null);

		loadAdditional();
	}

	/**
	 * 
	 */
	private void hint() {
		IFile ifile = ResourceManager.toIFile(selection);
		fAdditionalTestViewer.setInput(ifile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		((ConvertToFileCreationWizard) this.getWizard()).setJunitTestPage(null);
		validatePage();
		super.setVisible(visible);
	}

	/**
	 * 
	 */
	private void loadAdditional() {
		IFile ifile = ResourceManager.toIFile(selection);
		fAdditionalTestViewer.setInput(ifile);
	}

	/**
	 * 
	 */
	public void updateUI() {
		boolean enabled = generateRunStabilityTest.getSelection() || generateRunFunctionalTest.getSelection()
				|| generateRunSmokeTest.getSelection();

		labelChooseAdditionalContext.setEnabled(enabled);
		this.fAdditionalTestViewer.getTable().setEnabled(enabled);
		this.edgesCombo.getCombo().setEnabled(enabled);
		this.hintButton.setEnabled(enabled);
		this.filterGraphmlButton.setEnabled(enabled);
		setPageComplete(validatePage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	@Override
	public void handleEvent(Event event) {
		validatePage();
	}

	/**
	 * @return
	 */
	protected boolean validatePage() {
		((ConvertToFileCreationWizard) this.getWizard()).setJunitTestPage(null);
		this.setPageComplete(false);
		this.setErrorMessage(null);
		this.setMessage(null);

		if (generateRunSmokeTest.getSelection() && reachedVertex == null) {
			this.setErrorMessage(MessageUtil.getString("youMustSetAReachedVertex"));
			return false;
		}

		if (this.edgesCombo.getCombo().isEnabled()) {
			String startElement = this.edgesCombo.getCombo().getText();
			if (startElement == null || startElement.trim().length() == 0) {
				this.setErrorMessage(MessageUtil.getString("youMustSetAStartElement"));
				return false;
			}
		}
		List<IFile> additionalContexts = new ArrayList<IFile>();
		TableItem[] items = fAdditionalTestViewer.getTable().getItems();
		for (int i = 0; i < items.length; i++) {
			TableItem ti = items[i];
			if (ti.getChecked())
				additionalContexts.add((IFile) ti.getData());
		}

		((ConvertToFileCreationWizard) this.getWizard()).setJunitTestPage(new JUnitTestPage(
				generateRunSmokeTest.getSelection(), generateRunStabilityTest.getSelection(),
				generateRunFunctionalTest.getSelection(), reachedVertex == null ? null : reachedVertex.getName(),
				edgesCombo == null ? null : edgesCombo.getStartElement() == null ? null
						: edgesCombo.getStartElement().getName(),
				additionalContexts));

		if (((ConvertToFileCreationWizard) this.getWizard()).testCount() == 0) {
			((ConvertToFileCreationWizard) this.getWizard()).setJunitTestPage(null);
			return false;
		}

		this.setPageComplete(true);
		return true;
	}

	/**
	 * 
	 */
	private void loadVertices() {
		Display display = Display.getCurrent();
		Runnable longJob = new Runnable() {
			public void run() {
				display.syncExec(new Runnable() {
					public void run() {
						IFile ifile = ResourceManager.toIFile(selection);
						String modelFileName = null;

						try {
							modelFileName = ResourceManager.getAbsolutePath(ifile);
							context = GraphWalkerFacade.getContext(modelFileName);
							updateUI();
						} catch (Exception e) {
							ResourceManager.logException(e);
							JUnitGW4ETestUIPage.this.setErrorMessage(
									"Unable to load the graph model. See error logs in the Error View.");
							return;
						}

						comboReachedVertexViewer.setInput(_loadVertices(context));
					}
				});
				display.wake();
			}
		};
		BusyIndicator.showWhile(display, longJob);
	}

	/**
	 * @param context
	 * @return
	 */
	private List<RuntimeVertex> _loadVertices(Context context) {
		try {
			List<RuntimeVertex> all = context.getModel().getVertices();
			List<RuntimeVertex> ret = new ArrayList<RuntimeVertex>();
			Map<String, RuntimeVertex> stored = new HashMap<String, RuntimeVertex>();
			for (RuntimeVertex runtimeVertex : all) {
				if (runtimeVertex.getName() == null)
					continue;
				if (stored.get(runtimeVertex.getName()) != null)
					continue;
				stored.put(runtimeVertex.getName(), runtimeVertex);
				ret.add(runtimeVertex);
			}
			Collections.sort(ret, new Comparator<RuntimeVertex>() {
				@Override
				public int compare(RuntimeVertex rv1, RuntimeVertex rv2) {
					return rv1.getName().compareToIgnoreCase(rv2.getName());
				}
			});
			return ret;
		} catch (Exception e) {
			ResourceManager.logException(e);
			JUnitGW4ETestUIPage.this
					.setErrorMessage("Unable to load the graph model. See error logs in the Error View.");
			return new ArrayList<RuntimeVertex>();
		}
	}
	
	public boolean hasSelection () {
		  boolean oneSelection = (generateRunSmokeTest.getSelection() || 
				  					generateRunFunctionalTest.getSelection() ||
				  					generateRunStabilityTest.getSelection());
		return  (oneSelection && this.isPageComplete())  ;
	}

	public void reset () {
		generateRunSmokeTest.setSelection(false);
		generateRunFunctionalTest.setSelection(false);
		generateRunStabilityTest.setSelection(false);
	}
	
}
