package org.gw4e.eclipse.launching.runasmanual;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.page.BuildPoliciesCheckboxTableViewer;
import org.gw4e.eclipse.wizard.convert.page.TableHelper;

/**
 * Launch configuration tab used to specify the GraphWalker Parameters for the
 * offline command
 *
 */
public class GW4ELaunchConfigurationTab extends AbstractLaunchConfigurationTab implements LaunchingConstant {

	private Label fProjLabel;
	private Text fProjText;
	private Button fProjButton;
	private Button fModelButton;
	private Label fModelLabel;
	private Button hintButton;
	private Text fModelText;
	private Text generatorText;
	private Button fRemoveBlockedElementsButton;
	private BuildPoliciesCheckboxTableViewer buildPoliciesViewer;
	private Text fStartNodeText;
	private CheckboxTableViewer fAdditionalTestViewer;
	/**
	 * 
	 */
	private IStructuredSelection selection;
	/**
	 * 
	 */
	private IFile additionalExecutionContexts[];

	public static String GW4E_LAUNCH_CONFIGURATION_CONTROL_ID = "gw4e.launch.configuration.control.id";

	public static String GW4E_BROWSER_BUTTON_ID_PROJECT = "gw4e.launch.configuration.browse.button.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_PRINT_UNVISITED = "gw4e.launch.configuration.browse.button.id.printunvisited";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_VERBOSE = "gw4e.launch.configuration.browse.button.id.verbose";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS = "gw4e.launch.configuration.browse.button.id.remove.blocked.elements";
	public static String GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD = "gw4e.launch.configuration.browse.button.id.model";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT = "gw4e.launch.configuration.text.id.start.element";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_GENERATOR = "gw4e.launch.configuration.text.id.generator";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_CONTEXT = "gw4e.launch.test.configuration.additional.context";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON = "gw4e.launch.test.project.hint.button";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT = "gw4e.launch.configuration.text.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL = "gw4e.launch.configuration.text.id.model";
	private Label fGeneratorTitleLabel;
	private Composite parent_1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.
	 * swt.widgets.Composite)
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite p) {
		parent_1 = new Composite(p, SWT.NONE);
		setControl(parent_1);

		GridLayout gl_parent_1 = new GridLayout();
		gl_parent_1.numColumns = 3;
		parent_1.setLayout(gl_parent_1);

		createAllSections(parent_1);

		Dialog.applyDialogFont(parent_1);
		new Label(parent_1, SWT.NONE);

		validatePage();
		updateLaunchConfigurationDialog();
		updateConfigState();
	}

	/**
	 * Create the element that allow to select a project See the GraphWalker
	 * offline command for more information
	 */
	private void createProjectSection(Composite parent) {
		fProjLabel = new Label(parent, SWT.NONE);
		fProjLabel.setText(MessageUtil.getString("label_project"));
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fProjLabel.setLayoutData(gd);

		fProjText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		fProjText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fProjText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				validatePage();
				updateConfigState();

			}
		});
		fProjText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT);

		fProjButton = new Button(parent, SWT.PUSH);
		fProjButton.setText(MessageUtil.getString("label_browse"));
		fProjButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
				updateConfigState();
			}
		});
		fProjButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_BROWSER_BUTTON_ID_PROJECT);

	}

	private void createAdditionnalModelsSection(Composite parent) {
		fModelLabel = new Label(parent, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fModelLabel.setLayoutData(gd);
		fModelLabel.setText(MessageUtil.getString("additionalGraphModels"));

		fAdditionalTestViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		Table additionalTable = fAdditionalTestViewer.getTable();
		additionalTable.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
				GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_CONTEXT);
		GridData gd_additionalTable = new GridData(GridData.FILL_HORIZONTAL);
		gd_additionalTable.verticalAlignment = SWT.FILL;
		additionalTable.setLayoutData(gd_additionalTable);

		TableColumn additionalColumn = new TableColumn(fAdditionalTestViewer.getTable(), SWT.FILL);
		additionalColumn.setText(MessageUtil.getString("graphfiles"));
		additionalColumn.pack();
		fAdditionalTestViewer.getTable().setHeaderVisible(true);
		fAdditionalTestViewer.getTable().setLinesVisible(true);

		fAdditionalTestViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				updateConfigState();
			}
		});

		Composite compAdditionnalButton = new Composite(parent, SWT.NONE);
		compAdditionnalButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		compAdditionnalButton.setLayout(new GridLayout(1, false));

		hintButton = new Button(compAdditionnalButton, SWT.CHECK);
		hintButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		hintButton.setText(MessageUtil.getString("hint"));
		hintButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		hintButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON);

		Button filterGraphmlButton = new Button(compAdditionnalButton, SWT.CHECK);
		filterGraphmlButton.setBounds(0, 0, 94, 18);
		filterGraphmlButton.setText(MessageUtil.getString("filter_graphml_file"));
		filterGraphmlButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		//
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
	}

	/**
	 * Create the element that allow to select a model file See the GraphWalker
	 * offline command for more information
	 */
	private void createModelSection(Composite parent) {
		fModelLabel = new Label(parent, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fModelLabel.setLayoutData(gd);
		fModelLabel.setText(MessageUtil.getString("GraphModel"));

		fModelText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		fModelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fModelText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				validatePage();
				updateConfigState();
				try {
					if (fModelText.getText() != null && (fModelText.getText().trim().length() > 0)) {
						Display display = Display.getCurrent();
						Runnable longJob = new Runnable() {
							public void run() {
								display.syncExec(new Runnable() {
									public void run() {
										try {
											refreshStartElement();
											//
											IFile file = (IFile) ResourceManager
													.getResource(new Path(fModelText.getText()).toString());
											if (buildPoliciesViewer != null)
												buildPoliciesViewer.setFile(file);
										} catch (Exception e) {
											ResourceManager.logException(e);
										}
									}
								});
								display.wake();
							}
						};
						BusyIndicator.showWhile(display, longJob);
					}
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
				fModelText.setFocus();
			}
		});
		fModelText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL);

		fModelButton = new Button(parent, SWT.PUSH);
		fModelButton.setEnabled(fProjText.getText().length() > 0);
		fModelButton.setText(MessageUtil.getString("model_browse"));
		fModelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				handleBrowseModelButtonSelected();
				updateConfigState();
			}
		});
		fModelButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD);

	}

	private void refreshStartElement() {
		Path path = null;
		String element = null;
		try {
			path = new Path(fModelText.getText());
			IResource resource = ResourceManager.getResource(path.toString());
			if (resource == null) {
				element = "";
			} else {
				File f = ResourceManager.toFile(path);
				element = GraphWalkerFacade.getNextElement(f.getAbsolutePath());
				if (element == null) {
					element = "";
				}
			}
			fStartNodeText.setText(element);
		} catch (Exception e) {
			ResourceManager.logException(e);
			fStartNodeText.setText("");
		}
	}

	/**
	 * Create the element that allow to select the options See the GraphWalker
	 * offline command for more information
	 */
	private void createOptionSection(Composite parent) {
		Label fRemoveBlockedElementsLabel = new Label(parent, SWT.NONE);
		fRemoveBlockedElementsLabel.setText(MessageUtil.getString("removeBlockedElement"));
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fRemoveBlockedElementsLabel.setLayoutData(gd);

		fRemoveBlockedElementsButton = new Button(parent, SWT.CHECK);
		fRemoveBlockedElementsButton.setEnabled(true);
		fRemoveBlockedElementsButton.setSelection(true);
		fRemoveBlockedElementsButton.setText("");
		gd = new GridData();
		gd.horizontalSpan = 2;
		fRemoveBlockedElementsButton.setLayoutData(gd);
		fRemoveBlockedElementsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		fRemoveBlockedElementsButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
				GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS);
	}

	/**
	 * Create the element that allow to select a start element See the
	 * GraphWalker offline command for more information
	 */
	private void createStartElementSection(Composite parent) {
		Label fDummyGeneratorLabel = new Label(parent, SWT.NONE);
		fDummyGeneratorLabel.setText("");
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		fDummyGeneratorLabel.setLayoutData(gd);

		Label fGeneratorTitleLabel = new Label(parent, SWT.NONE);
		fGeneratorTitleLabel.setText(MessageUtil.getString("EnterStartElement"));
		FontData fontData = fGeneratorTitleLabel.getFont().getFontData()[0];
		Font font = new Font(this.getShell().getDisplay(),
				new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
		fGeneratorTitleLabel.setFont(font);
		gd = new GridData();
		gd.horizontalSpan = 2;
		fGeneratorTitleLabel.setLayoutData(gd);

		Label fGeneratorLabel = new Label(parent, SWT.NONE);
		fGeneratorLabel.setText("");
		gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalIndent = 25;
		fGeneratorLabel.setLayoutData(gd);

		fStartNodeText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		fStartNodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fStartNodeText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				validatePage();
				updateConfigState();
				fStartNodeText.setFocus();
			}
		});
		fStartNodeText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT);

		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(1, false));

		Button fRefreshButton = new Button(c, SWT.PUSH);
		fRefreshButton.setAlignment(SWT.LEFT);
		fRefreshButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false, 1, 1));

		fRefreshButton.setEnabled(true);
		fRefreshButton.setText(MessageUtil.getString("refresh"));
		fRefreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				refreshStartElement();
			}
		});
	}

	private int getSelectedPathGeneratorCount() {
		TableItem[] items = buildPoliciesViewer.getTable().getItems();
		int count = 0;
		for (TableItem tableItem : items) {
			if (tableItem.getChecked()) {
				count++;
			}
		}
		return count;
	}

	private void createBuildPoliciesGeneratorSection(Composite parent) {
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				generatorText.setText("");
				generatorText.setEnabled(false);
				fGeneratorTitleLabel.setEnabled(false);
				// event.detail == SWT.CHECK
				if (buildPoliciesViewer != null) {
					int count = getSelectedPathGeneratorCount();
					if (count == 0) {
						fGeneratorTitleLabel.setEnabled(true);
						generatorText.setEnabled(true);
						generatorText.setFocus();
					}
					if (count > 0) {
						TableItem[] items = buildPoliciesViewer.getTable().getItems();
						for (TableItem tableItem : items) {
							if (tableItem.getChecked()) {
								String text = tableItem.getText();
								generatorText.setText(text);
								break;
							}
						}
					}
				}
				validatePage();
			}
		};

		Label fDummyGeneratorLabel = new Label(parent, SWT.NONE);
		fDummyGeneratorLabel.setText("");
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		fDummyGeneratorLabel.setLayoutData(gd);

		Label fGeneratorTitleLabel = new Label(parent, SWT.NONE);
		fGeneratorTitleLabel.setText(MessageUtil.getString("select_a_path_generatpr"));
		FontData fontData = fGeneratorTitleLabel.getFont().getFontData()[0];
		Font font = new Font(this.getShell().getDisplay(),
				new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
		fGeneratorTitleLabel.setFont(font);
		gd = new GridData();
		gd.horizontalSpan = 2;
		fGeneratorTitleLabel.setLayoutData(gd);

		Label fGeneratorLabel = new Label(parent, SWT.NONE);
		fGeneratorLabel.setText("");
		gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalIndent = 25;
		fGeneratorLabel.setLayoutData(gd);

		gd = new GridData(GridData.FILL_BOTH);

		IResource resource = (IResource) ResourceManager.getResource(new Path(fModelText.getText()).toString());
		buildPoliciesViewer = BuildPoliciesCheckboxTableViewer.create(resource, parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, gd, listener);

		Label filler = new Label(parent, SWT.NONE);
		filler.setText("");
		filler.setEnabled(false);
	}

	/**
	 * Create the element that allow to select a generator & stop condition See
	 * the GraphWalker offline command for more information
	 */
	private void createGeneratorSection(Composite parent) {
		Label fDummyGeneratorLabel = new Label(parent, SWT.NONE);
		fDummyGeneratorLabel.setText("");
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		fDummyGeneratorLabel.setLayoutData(gd);

		fGeneratorTitleLabel = new Label(parent, SWT.NONE);
		fGeneratorTitleLabel.setText(MessageUtil.getString("enter_generator"));
		FontData fontData = fGeneratorTitleLabel.getFont().getFontData()[0];
		Font font = new Font(this.getShell().getDisplay(),
				new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
		fGeneratorTitleLabel.setFont(font);
		gd = new GridData();
		gd.horizontalSpan = 2;
		fGeneratorTitleLabel.setLayoutData(gd);

		Label fGeneratorLabel = new Label(parent, SWT.NONE);
		fGeneratorLabel.setText("");
		gd = new GridData();
		gd.horizontalSpan = 1;
		gd.horizontalIndent = 25;
		fGeneratorLabel.setLayoutData(gd);

		generatorText = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);

		generatorText.setLayoutData(gd);
		ModifyListener listener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
				updateConfigState();
				generatorText.setFocus();
			}
		};
		generatorText.addModifyListener(listener);
		generatorText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_TEXT_ID_GENERATOR);
		generatorText.setEnabled(false);

		Label filler = new Label(parent, SWT.NONE);
		filler.setText("");
		filler.setEnabled(false);

	}

	/**
	 * Create the complete UI allowing to enter GraphWalker "offline" command
	 * parameters
	 */
	private void createAllSections(Composite parent) {
		createProjectSection(parent);
		createModelSection(parent);
		createAdditionnalModelsSection(parent);
		createOptionSection(parent);
		createStartElementSection(parent);
		createBuildPoliciesGeneratorSection(parent);
		createGeneratorSection(parent);
	}

	public boolean isValid(ILaunchConfiguration launchConfig) {
		return validatePage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return MessageUtil.getString("graphwalkerName");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.
	 * debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		try {
			fProjText.setText(config.getAttribute(CONFIG_PROJECT, ""));
			fModelText.setText(config.getAttribute(CONFIG_GRAPH_MODEL_PATH, ""));
			fStartNodeText.setText(config.getAttribute(CONFIG_LAUNCH_STARTNODE, ""));
			generatorText.setText(config.getAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS, ""));
			fRemoveBlockedElementsButton.setSelection(
					new Boolean(config.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, "true")));
			updateConfigState();
			String models = config.getAttribute(CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION, "");
			StringTokenizer st = new StringTokenizer(models, ";");
			while (st.hasMoreTokens()) {
				String model = st.nextToken();
				IFile resource = (IFile) ResourceManager.getResource(model);
				if (resource == null)
					continue;

				TableItem[] items = this.fAdditionalTestViewer.getTable().getItems();
				for (int i = 0; i < items.length; ++i) {
					String name = items[i].getText();
					if (name.equalsIgnoreCase(model)) {
						items[i].setChecked(true);
					}
				}
			}
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(CONFIG_PROJECT, fProjText.getText());
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjText.getText());
		config.setAttribute(CONFIG_GRAPH_MODEL_PATH, this.fModelText.getText());
		config.setAttribute(CONFIG_LAUNCH_STARTNODE, this.fStartNodeText.getText() + "");
		config.setAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS, this.generatorText.getText());
		config.setAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,
				this.fRemoveBlockedElementsButton.getSelection() + "");
		StringBuffer sb = new StringBuffer();
		TableItem[] items = fAdditionalTestViewer.getTable().getItems();
		for (int i = 0; i < items.length; ++i) {
			boolean checked = items[i].getChecked();
			IFile model = (IFile) items[i].getData();

			if (checked)
				sb.append(model.getFullPath().toString()).append(";").toString();
		}
		config.setAttribute(CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION, sb.toString());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(CONFIG_PROJECT, "");
		config.setAttribute(CONFIG_GRAPH_MODEL_PATH, "");
		config.setAttribute(CONFIG_UNVISITED_ELEMENT, "false");
		config.setAttribute(CONFIG_VERBOSE, "false");
		config.setAttribute(CONFIG_LAUNCH_STARTNODE, "");
		config.setAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS, "");
		config.setAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, "true");
		config.setAttribute(CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION, "");

	}

	/**
	 * @return
	 */
	private boolean validatePage() {

		setErrorMessage(null);
		setMessage(null);

		String projectName = fProjText.getText().trim();
		if (projectName.length() == 0) {
			setErrorMessage(MessageUtil.getString("error_projectnotdefined"));
			return false;
		}

		if (!ResourceManager.validProjectPath(projectName)) {
			setErrorMessage(MessageUtil.getString("invalidProjectName") + " " + projectName);
			return false;
		}

		if (!ResourceManager.projectExists(projectName)) {
			setErrorMessage(MessageUtil.getString("error_projectnotexists"));
			return false;
		}
		IProject project = ResourceManager.getProject(projectName);
		try {
			if (!project.hasNature(JavaCore.NATURE_ID)) {
				setErrorMessage("error_notJavaProject");
				return false;
			}
			String modelName = fModelText.getText().trim();
			if (modelName.length() == 0) {
				setErrorMessage(MessageUtil.getString("error_modelNamenotdefined"));
				return false;
			}

			if (!(modelName.startsWith("/" + projectName + "/"))) {
				setErrorMessage(MessageUtil.getString("error_file_not_found_in_project"));
				return false;
			}
			boolean fileExists = ResourceManager.resourcePathExists(modelName);
			if (!fileExists) {
				setErrorMessage(MessageUtil.getString("error_graphmodel_not_found"));
				return false;
			}
			TableItem[] items = fAdditionalTestViewer.getTable().getItems();
			for (int i = 0; i < items.length; ++i) {
				IFile model = (IFile) items[i].getData();
				if (!model.exists()) {
					setErrorMessage(MessageUtil.getString("error_graphmodel_not_found") + " at row : " + (i+1));
					return false;
				}
			}
			try {
				IFile model = (IFile)ResourceManager.getResource(modelName);
				File file = ResourceManager.toFile(model.getFullPath());
				boolean found = false;
				List<Element>  elements  = GraphWalkerFacade.getElements(file);
				for (Element element : elements) {
					if (fStartNodeText.getText().equals(element.getName())) {
						found=true;
						break;
					}
				}
				if (!found) {
					setErrorMessage(MessageUtil.getString("start_element_not_found") + " : " + fStartNodeText.getText());
				}
			} catch (Exception e) {
				ResourceManager.logException(e);
				setErrorMessage(e.getMessage());
				return false;
			}

		} catch (CoreException e) {
			ResourceManager.logException(e);
		}

		int count = getSelectedPathGeneratorCount();

		if (count > 1) {
			setErrorMessage(MessageUtil.getString("select_only_one_generator_and_stop_condition"));
			return false;
		}

		if ((this.generatorText.getText() == null || this.generatorText.getText().trim().length() == 0)) {
			setErrorMessage(MessageUtil.getString("error_no_generator_and_stop_conditions_set"));
			return false;
		}
		return true;
	}

	/**
	 * A helper method to size the button
	 * 
	 * @param button
	 * @return
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter = new PixelConverter(button);
		int widthHint = converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

	/**
	 * A helper method to provide the selected java project
	 */
	private IJavaProject getJavaProject() {
		String projectName = fProjText.getText().trim();
		if (projectName.length() < 1) {
			return null;
		}
		return JDTManager.getJavaModel().getJavaProject(projectName);
	}

	/**
	 * Here is what happen when the button is selected (Selection of a project)
	 */
	private void handleProjectButtonSelected() {
		IJavaProject project = GraphWalkerContextManager.chooseGW4EProject(getJavaProject());
		if (project == null) {
			return;
		}

		String projectName = project.getElementName();
		fProjText.setText(projectName);
	}

	/**
	 * Here is what happen when the button is selected (Selection of a model
	 * file)
	 */
	private void handleBrowseModelButtonSelected() {
		IFile resource = GraphWalkerContextManager.chooseGraphWalkerModel(fProjText.getText());
		if (resource == null) {
			return;
		}

		String elementName = resource.getFullPath().toString();
		fModelText.setText(elementName);
	}

	/**
	 * Check what kind of model file has been chosen We can have here a graphml
	 * modle file or a gw3 model file
	 */
	private boolean isGW3SelectedFile() {
		IResource resource = ResourceManager.getResource(fModelText.getText());
		return PreferenceManager.isGW3ModelFile((IFile) resource);
	}

	/**
	 * Update the state of the UI
	 */
	private void updateConfigState() {

		setDirty(true);
		updateLaunchConfigurationDialog();

		fProjText.setEnabled(true);
		fProjLabel.setEnabled(true);
		fProjButton.setEnabled(true);
		fModelLabel.setEnabled(false);
		fModelText.setEnabled(false);
		fModelButton.setEnabled(false);
		fStartNodeText.setEnabled(false);
		generatorText.setEnabled(false);

		if (fProjText.getText().trim().length() > 0) {
			fModelLabel.setEnabled(true);
			fModelText.setEnabled(true);
			fModelButton.setEnabled(true);
		}

		if (fModelText.getText().trim().length() > 0) {
			if (isGW3SelectedFile()) {
				fStartNodeText.setEnabled(false);
				generatorText.setEnabled(false);
			} else {
				fStartNodeText.setEnabled(true);
				generatorText.setEnabled(true);
				IFile resource = (IFile) ResourceManager.getResource(fModelText.getText());
				fAdditionalTestViewer.setInput(resource);
				fStartNodeText.setFocus();
			}
		}

	}
}
