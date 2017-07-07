package org.gw4e.eclipse.launching.offline;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.page.BuildPoliciesCheckboxTableViewer;

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
	private Text fModelText;
	private Text generatorText;
	private Button fVerbosedButton;
	private Button fRemoveBlockedElementsButton;
	private BuildPoliciesCheckboxTableViewer buildPoliciesViewer;
	private Button fPrintUnvisitedButton;
	private Text fStartNodeText;

	public static String GW4E_LAUNCH_CONFIGURATION_CONTROL_ID = "gw4e.launch.configuration.control.id";

	public static String GW4E_BROWSER_BUTTON_ID_PROJECT ="gw4e.launch.configuration.browse.button.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_PRINT_UNVISITED ="gw4e.launch.configuration.browse.button.id.printunvisited";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_VERBOSE ="gw4e.launch.configuration.browse.button.id.verbose";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS ="gw4e.launch.configuration.browse.button.id.remove.blocked.elements";
	public static String GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD ="gw4e.launch.configuration.browse.button.id.model";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT ="gw4e.launch.configuration.text.id.start.element";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_GENERATOR ="gw4e.launch.configuration.text.id.generator";
	
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT ="gw4e.launch.configuration.text.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL ="gw4e.launch.configuration.text.id.model";
	private Label fGeneratorTitleLabel;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite p) {
		Composite parent = new Composite(p, SWT.NONE);
		setControl(parent);

		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 3;
		parent.setLayout(topLayout);

		createAllSections(parent);

		Dialog.applyDialogFont(parent);
		
		validatePage();
		updateLaunchConfigurationDialog();
		updateConfigState();
	}

	/**
	 * Create the element that allow to select a project 
	 *  See the GraphWalker offline command for more information
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
		fProjText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT);
		
		fProjButton = new Button(parent, SWT.PUSH);
		fProjButton.setText(MessageUtil.getString("label_browse"));
		fProjButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
				updateConfigState();
			}
		});
		fProjButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_BROWSER_BUTTON_ID_PROJECT);
		setButtonGridData(fProjButton);

	}

	/**
	 * Create the element that allow to select a model file 
	 *  See the GraphWalker offline command for more information
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
					if (fModelText.getText() != null  &&  (fModelText.getText().trim().length()>0)) {
						Display display = Display.getCurrent();
						Runnable longJob = new Runnable() {
							public void run() {
								display.syncExec(new Runnable() {
									public void run() {
										try {
											refreshStartElement();
											//
											IFile file = (IFile)ResourceManager.getResource(new Path(fModelText.getText()).toString());
											if (buildPoliciesViewer!=null) buildPoliciesViewer.setFile(file);
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
		fModelText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL);
		
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
		fModelButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD);
		setButtonGridData(fModelButton);
	}
	
	
	private void refreshStartElement () {
		Path path = null;
		String element = null;
		try {
			path = new Path(fModelText.getText());
			IResource resource = ResourceManager.getResource(path.toString());
			if (resource==null) {
				element  = "";
			} else {
				File f = ResourceManager.toFile(path);
				element = GraphWalkerFacade.getNextElement(f.getAbsolutePath());
				if (element == null) {
					element  = "";
				}
			}
			fStartNodeText.setText(element);
		} catch (Exception e) {
			ResourceManager.logException(e);
			fStartNodeText.setText("");
		}
	}
	
	/**
	 * Create the element that allow to select the options 
	 *  See the GraphWalker offline command for more information
	 */
	private void createOptionSection(Composite parent) {
		Label fPrintUnvisitedLabel = new Label(parent, SWT.NONE);
		fPrintUnvisitedLabel.setText(MessageUtil.getString("launching_print_unvisited"));
		GridData gd = new GridData();
		gd.horizontalIndent = 25;
		fPrintUnvisitedLabel.setLayoutData(gd);

		fPrintUnvisitedButton = new Button(parent, SWT.CHECK);
		fPrintUnvisitedButton.setEnabled(true);
		fPrintUnvisitedButton.setText("");
		gd = new GridData();
		gd.horizontalSpan = 2;
		fPrintUnvisitedButton.setLayoutData(gd);
		fPrintUnvisitedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		fPrintUnvisitedButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_PRINT_UNVISITED);
	
		Label fVerbodeLabel = new Label(parent, SWT.NONE);
		fVerbodeLabel.setText(MessageUtil.getString("launching_verbose"));
		gd = new GridData();
		gd.horizontalIndent = 25;
		fVerbodeLabel.setLayoutData(gd);

		fVerbosedButton = new Button(parent, SWT.CHECK);
		fVerbosedButton.setEnabled(true);
		fVerbosedButton.setText("");
		gd = new GridData();
		gd.horizontalSpan = 2;
		fVerbosedButton.setLayoutData(gd);
		fVerbosedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		fVerbosedButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_VERBOSE);
		
		Label fRemoveBlockedElementsLabel = new Label(parent, SWT.NONE);
		fRemoveBlockedElementsLabel.setText(MessageUtil.getString("removeBlockedElement"));
		gd = new GridData();
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
		fRemoveBlockedElementsButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS);
	}
	
	/**
	 * Create the element that allow to select a start element 
	 *  See the GraphWalker offline command for more information
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
		fStartNodeText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT);
		
		Button fRefreshButton = new Button(parent, SWT.PUSH);
		 
		fRefreshButton.setLayoutData(gd);
		fRefreshButton.setEnabled(true);
		fRefreshButton.setText(MessageUtil.getString("refresh"));
		fRefreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				refreshStartElement();
			}
		});		
	}
	
	
	private int getSelectedPathGeneratorCount  () {
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
		
		IResource resource = (IResource)ResourceManager.getResource(new Path(fModelText.getText()).toString());
		buildPoliciesViewer = BuildPoliciesCheckboxTableViewer.create(resource, parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, gd, listener);
		
		Label filler = new Label(parent, SWT.NONE);
		filler.setText("");
		filler.setEnabled(false);
	}
	
	/**
	 * Create the element that allow to select a generator & stop condition
	 * See the GraphWalker offline command for more information
	 */
	private void createGeneratorSection(Composite parent) {
		Label fDummyGeneratorLabel = new Label(parent, SWT.NONE);
		fDummyGeneratorLabel.setText("");
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		fDummyGeneratorLabel.setLayoutData(gd);

		fGeneratorTitleLabel = new Label(parent, SWT.NONE);
		fGeneratorTitleLabel.setText(MessageUtil.getString("enter_generator"));
		FontData  fontData = fGeneratorTitleLabel.getFont().getFontData()[0];
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
		generatorText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_TEXT_ID_GENERATOR);
		generatorText.setEnabled(false);
		
		Label filler = new Label(parent, SWT.NONE);
		filler.setText("");
		filler.setEnabled(false);

	}
	
	/**
	 * Create the complete UI allowing to enter GraphWalker "offline" command parameters
	 */
	private void createAllSections(Composite parent) {
		createProjectSection(parent);
		createModelSection(parent);
		createOptionSection(parent);
		createStartElementSection(parent); 
		createBuildPoliciesGeneratorSection(parent);
		createGeneratorSection(parent);
	}

	public boolean isValid(ILaunchConfiguration launchConfig) {
		return validatePage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		return MessageUtil.getString("graphwalkerName");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		try {
			fProjText.setText(config.getAttribute(CONFIG_PROJECT, ""));
			fModelText.setText(config.getAttribute(CONFIG_GRAPH_MODEL_PATH, ""));
			fPrintUnvisitedButton
					.setSelection(Boolean.parseBoolean(config.getAttribute(CONFIG_UNVISITED_ELEMENT, "false")));
			fVerbosedButton.setSelection(Boolean.parseBoolean(config.getAttribute(CONFIG_VERBOSE, "false")));
			fStartNodeText.setText(config.getAttribute(CONFIG_LAUNCH_STARTNODE, ""));
			generatorText.setText(config.getAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS, ""));
			fRemoveBlockedElementsButton.setSelection(new Boolean(config.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, "true")));
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(CONFIG_PROJECT, fProjText.getText());
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjText.getText());
		config.setAttribute(CONFIG_GRAPH_MODEL_PATH, this.fModelText.getText());
		config.setAttribute(CONFIG_UNVISITED_ELEMENT, this.fPrintUnvisitedButton.getSelection() + "");
		config.setAttribute(CONFIG_VERBOSE, this.fVerbosedButton.getSelection() + "");
		config.setAttribute(CONFIG_LAUNCH_STARTNODE, this.fStartNodeText.getText() + "");
		config.setAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS, this.generatorText.getText());
		config.setAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, this.fRemoveBlockedElementsButton.getSelection()+"");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
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
	 * A helper method to customize the button
	 * @param button
	 */
	private void setButtonGridData(Button button) {
		GridData gridData = new GridData();
		button.setLayoutData(gridData);
		Object gd = button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData) gd).widthHint = getButtonWidthHint(button);
			((GridData) gd).horizontalAlignment = GridData.FILL;
		}
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
	 *  Here is what happen when the button is selected (Selection of a model file)
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
	 * Check what kind of model file has been chosen
	 * We can have here a graphml modle file or a gw3 model file
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
		fPrintUnvisitedButton.setEnabled(false);
		fVerbosedButton.setEnabled(false);
		fStartNodeText.setEnabled(false);
		generatorText.setEnabled(false);

		if (fProjText.getText().trim().length() > 0) {
			fModelLabel.setEnabled(true);
			fModelText.setEnabled(true);
			fModelButton.setEnabled(true);
		}

		if (fModelText.getText().trim().length() > 0) {
			fVerbosedButton.setEnabled(true);
			fPrintUnvisitedButton.setEnabled(true);
			if (isGW3SelectedFile()) {
				fStartNodeText.setEnabled(false);
				generatorText.setEnabled(false);
			} else {
				fStartNodeText.setEnabled(true);
				generatorText.setEnabled(true);
				fStartNodeText.setFocus();
			}
		}

	}

}
