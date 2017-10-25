package org.gw4e.eclipse.launching.test;

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

import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.product.GW4ENature;

/**
 * Launch configuration tab used to specify the GraphWalker Parameters for the
 * offline command
 *
 */
public class GW4ELaunchConfigurationTab extends AbstractLaunchConfigurationTab implements LaunchingConstant {
	
	public static String GW4E_LAUNCH_CONFIGURATION_CONTROL_ID = "gw4e.launch.configuration.control.id";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT = "gw4e.launch.test.project";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT_BUTTON = "gw4e.launch.test.project.button";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_MAIN_TEST = "gw4e.launch.test.project.main.test";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_TEST = "gw4e.launch.test.project.additional.test";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON = "gw4e.launch.test.project.hint.button";
	public static String GW4E_LAUNCH_TEST_CONFIGURATION_SELECTALL_BUTTON = "gw4e.launch.test.project.selectall.button";
	public static String GW4E_LAUNCH_TEST_TEST_REMOVE_BLOCKED_ELEMENT_BUTTON = "gw4e.launch.config.test.execution.remove.blocked.elements.configuration";

	private Text 	fProjectText;
	private Button	fProjectButton;
	 
	private Button 	fSelectAllButton ;
	private Button 	fDeselectAllButton; 
	private ComboViewer fMainTestExecutionComboViewer;
	private CheckboxTableViewer  fAdditionalTestViewer;
	private Composite compositeContainer;
	private IType additionalExecutionContexts[]; 
	private IType mainExecutionContexts[];
	private boolean doHint=false;
	 
	private Button hintButton;
	private Button removeBockedElementButton;
	private Button displayDetailsButton;
	private GridData gd_1;
	
	public GW4ELaunchConfigurationTab( ) {
		super();
	}
	
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
	public void createControl(Composite parent) {
		compositeContainer = new Composite(parent, SWT.NONE);
		setControl(compositeContainer);

		GridLayout gl = new GridLayout();
		gl.numColumns= 3;
		compositeContainer.setLayout(gl);
		 
		createProjectSection (compositeContainer);
		createTestContainerSelectionGroup(compositeContainer);
		createRemoveBlockedElementGroup (compositeContainer);
		createDisplayReportElementGroup(compositeContainer);
		createAdditionalExecutionContextContainer (compositeContainer);

	}

	
	private void createProjectSection (Composite parent) {
		Label label= new Label(parent, SWT.NONE);
		GridData gd= new GridData();
		gd.horizontalSpan= 3;
		gd.grabExcessHorizontalSpace=true;
		label.setLayoutData(gd);
		label.setText(MessageUtil.getString("label_project_explanation"));
		
		gd = new GridData();
		gd.horizontalSpan = 3;
		label = new Label(parent, SWT.NONE);
		label.setText(MessageUtil.getString("label_project1"));
		gd_1= new GridData();
		gd_1.horizontalAlignment = SWT.RIGHT;
		gd_1.horizontalIndent = 25;
		label.setLayoutData(gd_1);

		fProjectText= new Text(parent, SWT.SINGLE | SWT.BORDER);
		fProjectText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fProjectText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) { 
				validatePage();
				GW4ELaunchConfigurationTab.this.setDirty(true);
				setDefaultMainExecutionContextTest();
			}
		});
		fProjectText.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT);
 
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Composite compButtons = new Composite(composite, SWT.NONE);
		compButtons.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
		compButtons.setLayout(new FillLayout(SWT.VERTICAL));
		
		fProjectButton = new Button(compButtons, SWT.PUSH);
		fProjectButton.setText(MessageUtil.getString("label_browse"));
		fProjectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				handleProjectButtonSelected();
			}
		});
		fProjectButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_PROJECT_BUTTON);
	}
	
	private void createAdditionalExecutionContextContainer (Composite parent) {


		Label lblNewLabel = new Label(compositeContainer, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		lblNewLabel.setText(MessageUtil.getString("additionalExecutionContext"));

		ILabelProvider labelProvider = new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_QUALIFIED);
		fAdditionalTestViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		fAdditionalTestViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				Display display = Display.getCurrent();
				Runnable longJob = new Runnable() {
					public void run() {
						display.syncExec(new Runnable() {
							public void run() {
								if (inputElement == null) {
									additionalExecutionContexts=new IType[0];
								}
								IType type = (IType) inputElement;
								List<IType> all=null;
								try {
									all = JDTManager.getOrphanGraphWalkerClasses(type,doHint);
								} catch (Exception e) {
									ResourceManager.logException(e);
									additionalExecutionContexts=new IType[0];
								}
								additionalExecutionContexts = new IType[all.size()];
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
		fAdditionalTestViewer.setLabelProvider(labelProvider);
		fAdditionalTestViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		fAdditionalTestViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				validatePage();
			}
		});
		fAdditionalTestViewer.getTable().setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_ADDITIONAL_TEST);
 		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.TOP;
		composite.setLayoutData(gd);

		Composite compButtons = new Composite(composite, SWT.NONE);
		compButtons.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
		compButtons.setLayout(new FillLayout(SWT.VERTICAL));

		fSelectAllButton = new Button(compButtons, SWT.NONE);
		fSelectAllButton.setText(MessageUtil.getString("selectAll"));
		fSelectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				fAdditionalTestViewer.setAllChecked(true);
				validatePage();
			}
		});
		fSelectAllButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_SELECTALL_BUTTON);
		
		fDeselectAllButton = new Button(compButtons, SWT.NONE);
		fDeselectAllButton.setText(MessageUtil.getString("deselectAll"));
		
		fDeselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				fAdditionalTestViewer.setAllChecked(false);
				validatePage();
			}
		});
		
		hintButton = new Button(compButtons, SWT.CHECK);
		hintButton.setText(MessageUtil.getString("hint"));
		hintButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				hint();
			}
		});
		hintButton.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_HINT_BUTTON);

	}
	
	private void createTestContainerSelectionGroup (Composite parent) {
		Label fTestLabel = new Label(parent, SWT.NONE);
		GridData gd = new GridData( );
		gd.horizontalAlignment = SWT.RIGHT;
		gd.horizontalIndent = 25;
		gd.verticalAlignment=SWT.TOP;
		fTestLabel.setLayoutData(gd);
		fTestLabel.setText(MessageUtil.getString("mainTestExecutionContext"));
		 
		fMainTestExecutionComboViewer = new ComboViewer(parent,SWT.DROP_DOWN);
		Combo combo = fMainTestExecutionComboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		fMainTestExecutionComboViewer.setContentProvider(new   IStructuredContentProvider(){
			@Override
			public Object[] getElements(Object inputElement) {
				String projectName= (String) inputElement;
				loadMainExecutionContextTests(projectName);
				return mainExecutionContexts;
			}
		});
		ILabelProvider labelProvider = new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_QUALIFIED);
		fMainTestExecutionComboViewer.setLabelProvider(labelProvider);
		fMainTestExecutionComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
	        @Override
	        public void selectionChanged(SelectionChangedEvent event) {
	        	 	fAdditionalTestViewer.setInput(null);
	                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	                if (selection.size() > 0){
	                	  resetDoHint();
	                      IType type =  (IType) selection.getFirstElement();
	                      fAdditionalTestViewer.setInput(type);
	                      validatePage();
	                }
	        }
		});
		combo.setData(GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,GW4E_LAUNCH_TEST_CONFIGURATION_MAIN_TEST);
	}
	
	private void createRemoveBlockedElementGroup (Composite parent) {
		Label lfiller = new Label(parent, SWT.NONE);
		lfiller.setText("");
		
		Label lblRemoveBlockedElement = new Label(parent, SWT.NONE);
		lblRemoveBlockedElement.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRemoveBlockedElement.setText(MessageUtil.getString("removeBlockedElement"));
		
		removeBockedElementButton = new Button(parent, SWT.CHECK);
		removeBockedElementButton.setText("");
		removeBockedElementButton.setSelection(true);
		 
		removeBockedElementButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				validatePage();
			}
		});
		
	
 	}
	
	private void createDisplayReportElementGroup (Composite parent) {
		Label lfiller = new Label(parent, SWT.NONE);
		lfiller.setText("");
		
		Label lblDisplayReporElement = new Label(parent, SWT.NONE);
		lblDisplayReporElement.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDisplayReporElement.setText(MessageUtil.getString("displayReport"));
		
		displayDetailsButton = new Button(parent, SWT.CHECK);
		displayDetailsButton.setText("");
		displayDetailsButton.setSelection(true);
		new Label(parent, SWT.NONE);
		displayDetailsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				validatePage();
			}
		});
 	}

	 
	private void hint ( ) {
		this.doHint=!this.doHint;
		IStructuredSelection selection = (IStructuredSelection)fMainTestExecutionComboViewer.getSelection();
		if (selection==null) return;
		IType type = (IType)selection.getFirstElement();
		if (type==null) return;
		fAdditionalTestViewer.setInput(type);
	}
  
	private void loadMainExecutionContextTests(String projectName) {
		List<IType> all=null;;
		try {
			if (projectName==null || projectName.trim().length() == 0) return;
			all = JDTManager.getStartableGraphWalkerClasses(projectName);
		} catch (Exception e) {
			ResourceManager.logException(e);
			mainExecutionContexts = new IType  [0]; 
		}
		mainExecutionContexts = new IType[all.size()];
		all.toArray(mainExecutionContexts);
		
	}
	
	
	private void resetDoHint () {
		this.doHint=false;
		hintButton.setSelection(false);
	}
	
	private void setDefaultMainExecutionContextTest () {
		String projectName = fProjectText.getText();
		if (projectName != null && projectName.trim().length() > 0) {
			fMainTestExecutionComboViewer.setInput(projectName);
			if (mainExecutionContexts != null && mainExecutionContexts.length > 0) {
				fMainTestExecutionComboViewer.setSelection(new StructuredSelection(mainExecutionContexts[0]));
			}
		}
	}
	
	private void handleProjectButtonSelected() {
		IJavaProject project = chooseJavaProject();
		if (project == null) {
			return;
		}
		String projectName = project.getElementName();
		fProjectText.setText(projectName);

	}
 
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		configuration.setAttribute(CONFIG_TEST_CLASSES, "");
		configuration.setAttribute(EXECUTION_TEST_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, true);
		if (fProjectText!=null) fProjectText.setText("");
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			String projectName = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if (projectName==null || projectName.trim().length() == 0) {
				additionalExecutionContexts=new IType[0];
				mainExecutionContexts=new IType[0];
				fMainTestExecutionComboViewer.setInput(null);
				fAdditionalTestViewer.setInput(null);
			};
			fProjectText.setText(projectName);
			removeBockedElementButton.setSelection(configuration.getAttribute(EXECUTION_TEST_REMOVE_BLOCKED_ELEMENT_CONFIGURATION, true));
			displayDetailsButton.setSelection(new Boolean(configuration.getAttribute(EXECUTION_TEST_DISPLAY_CONFIGURATION, "true")).booleanValue());
			String classes = configuration.getAttribute(CONFIG_TEST_CLASSES, "");  
			StringTokenizer st = new StringTokenizer (classes,";");
			if (st.hasMoreTokens()) {
				String mainContext = st.nextToken();
				for (int i = 0; i < mainExecutionContexts.length; i++) {
					IType executionContext = mainExecutionContexts[i];
					if (executionContext.getFullyQualifiedName().equalsIgnoreCase(mainContext)) {
						fMainTestExecutionComboViewer.setSelection(new StructuredSelection(executionContext));
						break;
					}
				}
			}
 
			while (st.hasMoreTokens()) {
				String clazz = st.nextToken();
				TableItem[] items = this.fAdditionalTestViewer.getTable().getItems();
				for (int i = 0; i < items.length; ++i) {
					IType type = (IType) items[i].getData();
					String name = type.getFullyQualifiedName();
					if (name.equalsIgnoreCase(clazz)) {
						items[i].setChecked(true);
					}
				}
			}
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
 		configuration.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjectText.getText().trim());
 		IStructuredSelection selection = fMainTestExecutionComboViewer.getStructuredSelection();
 		IType type = (IType)selection.getFirstElement();
 		if (type==null) return;
 		StringBuffer sb = new StringBuffer ();
 		sb.append(type.getFullyQualifiedName()).append(";");
 		TableItem [] items = fAdditionalTestViewer.getTable().getItems();
 		for (int i = 0; i < items.length; ++i) {
 		  boolean checked = items[i].getChecked();
 		  type = (IType) items[i].getData();
 		  String name = type.getFullyQualifiedName();
 		  if (checked) sb.append(name).append(";").toString();
 		 }
		configuration.setAttribute(CONFIG_TEST_CLASSES, sb.toString());
		configuration.setAttribute(EXECUTION_TEST_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,removeBockedElementButton.getSelection());
		configuration.setAttribute(EXECUTION_TEST_DISPLAY_CONFIGURATION,displayDetailsButton.getSelection()+"");
		
 	}
 		
  
	@Override
	public String getName() {
		return MessageUtil.getString("graphwalkerName");
	}

	private void updateButtons (boolean enabled) {
		fSelectAllButton.setEnabled(enabled);
		fDeselectAllButton.setEnabled(enabled);
	}
 
	
	private void validatePage() {
		setErrorMessage(null);
		setMessage(null);
		updateButtons(false);
		
		String projectName= fProjectText.getText().trim();
		if (projectName.length() == 0) {
			setErrorMessage(MessageUtil.getString("project_not_defined"));
			return;
		}
		 
		IProject project= ResourceManager.getProject(projectName);
		if (!project.exists()) {
			setErrorMessage(MessageUtil.getString("invalid_project_name"));
			return;
		}
		
		if (!GW4ENature.hasGW4ENature(project)) {
			setErrorMessage(MessageUtil.getString("not_a_gw4e_project"));
			return;
		}
		GW4ELaunchConfigurationTab.this.setDirty(true);
		updateLaunchConfigurationDialog();
		updateButtons(true);
		 
	}

	private IJavaProject chooseJavaProject() {
		IJavaProject project = GraphWalkerContextManager.chooseGW4EProject(null);
		return project; 
	}
 
}
