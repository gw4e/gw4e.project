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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.ui.ModelData;
import org.gw4e.eclipse.launching.ui.ModelPathGenerator;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

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

	private Button fOmitEmptyEdgeElementsButton;
	private Button fRemoveBlockedElementsButton;

 	private Text fStartNodeText;

	public static String GW4E_LAUNCH_CONFIGURATION_CONTROL_ID = "gw4e.launch.configuration.control.id";

	public static String GW4E_BROWSER_BUTTON_ID_PROJECT = "gw4e.launch.configuration.browse.button.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE = "gw4e.launch.configuration.browse.button.id.omit";
	public static String GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS = "gw4e.launch.configuration.browse.button.id.remove.blocked.elements";
	public static String GW4E_LAUNCH_CONFIGURATION_BROWSER_BUTTON_ID_METHOD = "gw4e.launch.configuration.browse.button.id.model";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_START_ELEMENT = "gw4e.launch.configuration.text.id.start.element";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_GENERATOR = "gw4e.launch.configuration.text.id.generator";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_PROJECT = "gw4e.launch.configuration.text.id.project";
	public static String GW4E_LAUNCH_CONFIGURATION_TEXT_ID_MODEL = "gw4e.launch.configuration.text.id.model";
	public static String GW4E_LAUNCH_CONFIGURATION_COMBO_PATH_GENERATOR = "gw4e.launch.configuration.combo.path.generator";

	private Composite parent_1;
	GridData gd;
	private Composite composite;
	private Composite composite_1;
	private Button hintButton;
	private Button filterGraphmlButton;
	private Composite composite_2;
	private Composite composite_3;
	private Label lblPathGenerator;
	private Combo combo;
	private ComboViewer comboViewer;
	private Label lblNewLabel_1;
	private ModelPathGenerator mpg;

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

	}

	/**
	 * Create the element that allow to select a model file See the GraphWalker
	 * offline command for more information
	 */
	private void createModelSection(Composite parent) {

		composite_2 = new Composite(parent_1, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_2.setLayout(new GridLayout(1, false));

		fProjButton = new Button(composite_2, SWT.PUSH);
		fProjButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fProjButton.setText(MessageUtil.getString("label_browse"));
		fProjButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
				updateConfigState();
			}
		});
		fProjButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_BROWSER_BUTTON_ID_PROJECT);
		setButtonGridData(fProjButton);
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
											refreshComboViewer(file);
											mpg.refresh(file);
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
		fModelButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
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
		setButtonGridData(fModelButton);

		lblPathGenerator = new Label(parent, SWT.NONE);
		lblPathGenerator.setText("New Label");
		gd = new GridData();
		gd.horizontalIndent = 25;
		lblPathGenerator.setLayoutData(gd);
		lblPathGenerator.setText(MessageUtil.getString("path_generator"));

		comboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		combo = comboViewer.getCombo();
		combo.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_CONFIGURATION_COMBO_PATH_GENERATOR);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof BuildPolicy) {
					BuildPolicy bp = (BuildPolicy) element;
					return bp.getPathGenerator();
				}
				return super.getText(element);
			}
		});
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		    @Override
		    public void selectionChanged(SelectionChangedEvent event) {
		    	updateConfigState();
		    }
		});
		Label lblFiller = new Label(parent, SWT.NONE);

	}

	private void refreshComboViewer(IFile file) {
		ModelData md = new ModelData(file);
		comboViewer.setInput(md.getPolicies());
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

		Label fVerbodeLabel = new Label(parent, SWT.NONE);
		fVerbodeLabel.setText(MessageUtil.getString("omitEdgeElementwithoutDescription"));
		gd = new GridData();
		gd.horizontalIndent = 25;
		fVerbodeLabel.setLayoutData(gd);

		fOmitEmptyEdgeElementsButton = new Button(parent, SWT.CHECK);
		fOmitEmptyEdgeElementsButton.setEnabled(true);
		fOmitEmptyEdgeElementsButton.setText("");
		gd = new GridData();
		gd.horizontalSpan = 2;
		fOmitEmptyEdgeElementsButton.setLayoutData(gd);
		fOmitEmptyEdgeElementsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				updateConfigState();
			}
		});
		fOmitEmptyEdgeElementsButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID, GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE);

		Label fRemoveBlockedElementsLabel = new Label(parent, SWT.NONE);
		fRemoveBlockedElementsLabel.setText("Remove Blocked Element(s)");
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
		fRemoveBlockedElementsButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
				GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_REMOVE_BLOCKED_ELEMENTS);
	}

	/**
	 * Create the element that allow to select a start element See the
	 * GraphWalker offline command for more information
	 */
	private void createStartElementSection(Composite parent) {

		Label fGeneratorLabel = new Label(parent, SWT.NONE);
		fGeneratorLabel.setText("Start Element");
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
	}

	private void createBuildPoliciesGeneratorSection(Composite parent) {
		composite = new Composite(parent_1, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		GridLayout gl_composite = new GridLayout(1, false);
		gl_composite.marginWidth = 0;
		composite.setLayout(gl_composite);

		Button fRefreshButton = new Button(composite, SWT.PUSH);
		fRefreshButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fRefreshButton.setEnabled(true);
		fRefreshButton.setText(MessageUtil.getString("refresh"));
		fRefreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				refreshStartElement();
			}
		});

		lblNewLabel_1 = new Label(parent_1, SWT.NONE);
		lblNewLabel_1.setText(MessageUtil.getString("additionalGraphModels"));
		gd = new GridData();
		gd.horizontalIndent = 25;
		lblNewLabel_1.setLayoutData(gd);

		mpg = new ModelPathGenerator(parent_1, SWT.NONE, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateConfigState();
			}
		});
		mpg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gridLayout = (GridLayout) mpg.getLayout();
		gridLayout.marginWidth = 0;

		composite_1 = new Composite(parent_1, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		composite_1.setLayout(new GridLayout(1, false));

		hintButton = new Button(composite_1, SWT.CHECK);
		hintButton.setText(MessageUtil.getString("hint"));

		filterGraphmlButton = new Button(composite_1, SWT.CHECK);
		filterGraphmlButton.setText(MessageUtil.getString("filter_graphml_file"));
		Label fDummyGeneratorLabel = new Label(parent, SWT.NONE);
		GridData gd_1 = new GridData();
		gd_1.horizontalSpan = 1;
		fDummyGeneratorLabel.setLayoutData(gd_1);

		mpg.setButtons(hintButton, filterGraphmlButton);

		composite_3 = new Composite(parent_1, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(1, false);
		gl_composite_3.marginWidth = 0;
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	/**
	 * Create the complete UI allowing to enter GraphWalker "offline" command
	 * parameters
	 */
	private void createAllSections(Composite parent) {
		createProjectSection(parent);
		createModelSection(parent);
		createOptionSection(parent);
		createStartElementSection(parent);
		createBuildPoliciesGeneratorSection(parent);
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
			mpg.initialize(getModels(config));
			fProjText.setText(config.getAttribute(CONFIG_PROJECT, ""));
			fModelText.setText(getMainModel(config));
			fOmitEmptyEdgeElementsButton.setSelection(Boolean.parseBoolean(config.getAttribute(GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE_DESCRIPTION, "false")));
			fStartNodeText.setText(config.getAttribute(CONFIG_LAUNCH_STARTNODE, ""));
			fRemoveBlockedElementsButton.setSelection(
					new Boolean(config.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, "true")));

			BuildPolicy bp = getMainPathGenerators(config);
			if (bp!=null) {
				comboViewer.setSelection(new StructuredSelection (bp), true); 
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
		try {
			setModels(config);
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
		config.setAttribute(GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE_DESCRIPTION, this.fOmitEmptyEdgeElementsButton.getSelection() + "");
		config.setAttribute(CONFIG_LAUNCH_STARTNODE, this.fStartNodeText.getText() + "");
		config.setAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,
				this.fRemoveBlockedElementsButton.getSelection() + "");
	}

	private String getMainModel(ILaunchConfiguration config) throws CoreException {
		String ret = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		StringTokenizer st = new StringTokenizer(ret, ";");
		if (st.hasMoreTokens())
			return st.nextToken();
		return "";
	}

	private ModelData[] getModels(ILaunchConfiguration config) throws CoreException {
		List<ModelData> temp = new ArrayList<ModelData>();
		String paths = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		 
		if (paths == null || paths.trim().length() == 0)
			return new ModelData[0];
		StringTokenizer st = new StringTokenizer(paths, ";");
		st.nextToken(); // the main model path
		st.nextToken(); // main model : Always "1"
		st.nextToken(); // the main path generator
		while (st.hasMoreTokens()) {
			String path = st.nextToken();
			IFile file = (IFile) ResourceManager
					.getResource(new Path(path).toString());
			if (file==null) continue;
			ModelData data = new ModelData(file);
			boolean selected = st.nextToken().equalsIgnoreCase("1");
			String pathGenerator = st.nextToken();
			data.setSelected(selected);
			data.setSelectedPolicy(pathGenerator);
			temp.add(data);
		}
		ModelData[] ret = new ModelData[temp.size()];
		temp.toArray(ret);
		return ret;
	}

	private BuildPolicy getMainPathGenerators(ILaunchConfiguration config) throws CoreException {
		String paths = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		if (paths == null || paths.trim().length() == 0)
			return null;
		StringTokenizer stFirst = new StringTokenizer(paths, ";");
		stFirst.nextToken(); // the main model path
		stFirst.nextToken(); // main model : Always "1"
		String generators = stFirst.nextToken(); // the main path generator
		if (generators == null || generators.trim().length() == 0)
			return null;
		StringTokenizer st = new StringTokenizer(generators, ";");
		if (st.hasMoreTokens()) {
			String path = st.nextToken();
			if (path==null || path.trim().length()==0) return null;
			String model = getMainModel(config);
			IFile file = (IFile) ResourceManager
					.getResource(new Path(model).toString());
			if (file==null) return null;
			ModelData md = new ModelData(file);
			BuildPolicy[] policies = md.getPolicies();
			for (BuildPolicy buildPolicy : policies) {
				if (path.trim().equals(buildPolicy.getPathGenerator())) {
					return buildPolicy;
				}
			}
			if (policies.length > 0) return policies[0];
		}
		return null;
	}
	
 

	private void setModels(ILaunchConfigurationWorkingCopy config) throws CoreException {
		StringBuffer sb = new StringBuffer();
		// Main 
		if (fModelText.getText() == null || fModelText.getText().trim().length() == 0) {
			sb.append("").append(";");
		} else {
			sb.append((fModelText.getText().trim())).append(";");
		}
		sb.append("1").append(";");
		IStructuredSelection selection = (IStructuredSelection) comboViewer.getSelection();
		if (selection == null || selection.getFirstElement() == null) {
			sb.append("?").append(";");
		} else {
			sb.append(((BuildPolicy) selection.getFirstElement()).getPathGenerator()).append(";");
		}
		// Others
		ModelData[] others = mpg.getModel();
		for (int i = 0; i < others.length; i++) {
			sb.append(others[i].getFullPath()).append(";");
			if (others[i].isSelected()) {
				sb.append("1").append(";");
			} else {
				sb.append("0").append(";");
			}
			sb.append(others[i].getSelectedPolicy()).append(";");
		}
		config.setAttribute(CONFIG_GRAPH_MODEL_PATHS, sb.toString());
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
		config.setAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		config.setAttribute(CONFIG_UNVISITED_ELEMENT, "false");
		config.setAttribute(GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE_DESCRIPTION, "false");
		config.setAttribute(CONFIG_LAUNCH_STARTNODE, "");
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

			String mpgError = mpg.validate();
			if (mpgError != null) {
				setErrorMessage(mpgError);
				return false;
			}

			IStructuredSelection selection = (IStructuredSelection) comboViewer.getSelection();
			if (selection == null) {
				setErrorMessage(MessageUtil.getString("empty_path_generator"));
				return false;
			}
			if (selection.getFirstElement() == null) {
				setErrorMessage(MessageUtil.getString("empty_path_generator"));
				return false;
			}
			String s = ((BuildPolicy) (selection.getFirstElement())).getPathGenerator();
			if (s == null || s.trim().length() == 0) {
				setErrorMessage(MessageUtil.getString("empty_path_generator"));
				return false;
			}
			if (!GraphWalkerFacade.parsePathGenerator(s.trim())) {
				setErrorMessage(MessageUtil.getString("invalid_path_generator"));
				return false;
			}
		} catch (CoreException e) {
			ResourceManager.logException(e);
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
	 * A helper method to customize the button
	 * 
	 * @param button
	 */
	private void setButtonGridData(Button button) {
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
		fOmitEmptyEdgeElementsButton.setEnabled(false);
		fStartNodeText.setEnabled(false);

		if (fProjText.getText().trim().length() > 0) {
			fModelLabel.setEnabled(true);
			fModelText.setEnabled(true);
			fModelButton.setEnabled(true);
		}

		if (fModelText.getText().trim().length() > 0) {
			fOmitEmptyEdgeElementsButton.setEnabled(true);
			if (isGW3SelectedFile()) {
				fStartNodeText.setEnabled(false);
			} else {
				fStartNodeText.setEnabled(true);
				fStartNodeText.setFocus();
			}
		}
	}
}
