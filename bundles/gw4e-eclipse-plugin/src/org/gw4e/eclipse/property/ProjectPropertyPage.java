package org.gw4e.eclipse.property;

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

import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.property.checkbox.LabelizedCheckBoxes;
import org.gw4e.eclipse.property.table.CustomListWithButtons;
import org.gw4e.eclipse.property.table.StringCustomListModel;
import org.gw4e.eclipse.property.text.LabelizedTexts;

/**
 * An implementation of a workbench property page ( IWorkbenchPropertyPage). The
 * implementation is a JFace preference page with an adaptable element
 * 
 * It represent the properties displayed whenever and end user right click on a
 * .graphml file Among other things, it gives the requirements and the methods
 * of the graphml file See the Requirement & Method command client from the
 * GraphWalker documentation
 *
 */
public class ProjectPropertyPage extends PropertyPage {



	public static final String PROPERTY_PAGE_CONTEXT = Constant.PLUGIN_ID + ".project_property_page_context"; //$NON-NLS-1$
	
	public static String PROJECT_PROPERTY_PAGE_WIDGET_ID = "project.property.widget.id";
	public static String PROJECT_PROPERTY_PAGE_WIDGET_SECURITY_LEVEL_FOR_ABSTRACT_CONTEXT = "project.property.widget.security.level.for.abstract.context";
	public static String PROJECT_PROPERTY_PAGE_WIDGET_SEVERITY_LEVEL_FOR_ABSTRACT_CONTEXT = "project.property.widget.severity.level.for.unsynchronized.context";

	public static String TEXTUAL_PROPERTIES = "textualproperties";
	
	
	// CustomListWithButtons gw4eLibraries;
	CustomListWithButtons buildDefaultPolicies;
	// CustomListWithButtons authorizedFolders;
	CustomListWithButtons performanceConfiguration;
	LabelizedCheckBoxes cbs ;
	LabelizedTexts lts ;
	String projectName = null;
	Text securityLevelForAbstractContext ;
	Text severityLevelForUnSynchronizedContext ;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.
	 * swt.widgets.Composite)
	 */
	public Control createContents(Composite parent) {

		// noDefaultAndApplyButton();
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),PROPERTY_PAGE_CONTEXT);

		IJavaProject resource = (IJavaProject) getElement();
		projectName = resource.getProject().getName();
		
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);
		
		String [] values = PreferenceManager.getBasicPolicies(projectName);
		String [] propertyNames = new String [] {PreferenceManager.DEFAULT_POLICIES};
		buildDefaultPolicies = new CustomListWithButtons(parent, SWT.NONE,true, new StringCustomListModel(MessageUtil.getString("builddefaultpolicies"),values)) ;
		buildDefaultPolicies.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		buildDefaultPolicies.setPropertyNames(propertyNames);
		
		
		values = PreferenceManager.getPerformanceConfiguration(projectName);
		propertyNames = new String [] {PreferenceManager.PERFORMANCE_CONFIGURATION};
		performanceConfiguration = new CustomListWithButtons(parent, SWT.NONE,false,false, new StringCustomListModel(MessageUtil.getString("performanceConfiguration"),values)) ;
		performanceConfiguration.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		performanceConfiguration.setPropertyNames(propertyNames);		
		
		String [] labels = new String [] {
				MessageUtil.getString("buildpoliciefilename"),
				MessageUtil.getString("suffixfortestimplementation"),
				MessageUtil.getString("suffixforofflinetestimplementation"),
				MessageUtil.getString("timeoutforofflinetestgeneration"),
				MessageUtil.getString("waittimeoutforgraphwalkertestexecution"),
				MessageUtil.getString("targetmainfolderfortestinterface"),
				MessageUtil.getString("targettestfolderfortestinterface"),
				MessageUtil.getString("defaultseverity"),
		};	
		
		propertyNames = new String [] {
				PreferenceManager.BUILD_POLICIES_FILENAME,
				PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION,
				PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION,
				PreferenceManager.TIMEOUT_FOR_TEST_OFFLINE_GENERATION,
				PreferenceManager.TIMEOUT_FOR_GRAPHWALKER_TEST_EXECUTION,
				PreferenceManager.GW4E_MAIN_SOURCE_GENERATED_INTERFACE,
				PreferenceManager.GW4E_TEST_SOURCE_GENERATED_INTERFACE,
				PreferenceManager.DEFAULT_SEVERITY,
		};
		
		PropertyChecker []  propertyCheckers = new PropertyChecker [] {
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
					}
				},
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
					}
				},
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
					}
				},
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
						String temp = values[0].trim();
						try {
							Integer.parseInt(temp);
						} catch (NumberFormatException e) {
							ProjectPropertyPage.this.setValid(false);
							ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_timeout"));
						}
					}
				},	
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
						String temp = values[0].trim();
						try {
							Integer.parseInt(temp);
						} catch (NumberFormatException e) {
							ProjectPropertyPage.this.setValid(false);
							ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_timeout"));
						}
					}
				},	
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
					}
				},
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
					}
				},
				new PropertyChecker() {
					public void check (String[] values) {
						ProjectPropertyPage.this.setErrorMessage(null);
						ProjectPropertyPage.this.setValid(true);
						String temp = values[0].trim();
						int ret = BuildPolicy.getSeverityLevel(temp);
						if (ret == -1) {
							ProjectPropertyPage.this.setValid(false);
							ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_severity"));
						}
					}
				},
		};
		
		values = new String [] {
			PreferenceManager.getBuildPoliciesFileName(projectName),
			PreferenceManager.suffixForTestImplementation(projectName),
			PreferenceManager.suffixForTestOfflineImplementation(projectName),
			PreferenceManager.getTimeOutForTestOfflineGeneration(projectName)+"",
			PreferenceManager.getTimeOutForGraphWalkerTestExecution(projectName)+"",
			PreferenceManager.getTargetFolderForTestInterface(projectName,true),
			PreferenceManager.getTargetFolderForTestInterface(projectName,false),
			PreferenceManager.getDefaultSeverity(projectName),
		};
		
		boolean [] editable = new boolean  [] {
			true,true,true,true,true,false,false,true,
		};
		
		boolean [] multitext = new boolean [] {
			false,false,false,false,false,false,false,false
		};
		
		Property[]  properties = new Property [propertyNames.length];
		for (int i = 0; i < multitext.length; i++) {
			properties[i] = new Property(
					propertyNames[i], 
					propertyCheckers[i], 
					labels[i], 
					values[i], 
					editable[i], 
					multitext[i]);
		}

		lts = new LabelizedTexts (parent,SWT.NONE,properties,TEXTUAL_PROPERTIES);
		lts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lts.setPropertyNames(propertyNames);
		
		String [] checklabels = new String [] {
				MessageUtil.getString("graphwalkerbuildenabled"),
				MessageUtil.getString("loginforenabled"),
				MessageUtil.getString("performanceenabled"),
				MessageUtil.getString("graphwalkerbuildpolicysynchronizationenabled"),
		};
		
		boolean [] enabled = new boolean [] {
				true,
				true,
				true,
				true};
		
		boolean [] checked = new boolean [] {
				PreferenceManager.isBuildEnabled(projectName),
				PreferenceManager.isLogInfoEnabled(projectName),
				PreferenceManager.isPerformanceEnabled(projectName),
				PreferenceManager.isBuildPoliciesSynchronisationWithTestsAuthrorized(projectName),
				};
		
		propertyNames = new String [] {
				PreferenceManager.GW4E_ENABLE_BUILD,
				PreferenceManager.LOG_INFO_ENABLED,
				PreferenceManager.GW4E_ENABLE_PERFORMANCE,
				PreferenceManager.BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED,
		};

		SelectionAdapter [] checkBocSelectionAdapters = new SelectionAdapter [] {
				null,
				null,
				null, 
				new SelectionAdapter () {
					@Override
				    public void widgetSelected(SelectionEvent e) {
						Button synchronizationAuthorized = (Button) e.getSource();
						securityLevelForAbstractContext.setEnabled(synchronizationAuthorized.getSelection());
				    }					
				}
		};
		
		cbs = new LabelizedCheckBoxes(parent,SWT.NONE,checklabels,enabled,checked,checkBocSelectionAdapters);
		cbs.setPropertyNames(propertyNames);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(10, true));
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 10, 1)); 
		
		Label securityLevelForAbstractContextLabel = new Label(composite, SWT.BORDER);
		securityLevelForAbstractContextLabel.setText(MessageUtil.getString("severityonabstractcontext"));
		securityLevelForAbstractContextLabel.setEnabled(PreferenceManager.isBuildPoliciesSynchronisationWithTestsAuthrorized(projectName));
		GridData gd = new GridData(SWT.LEFT, SWT.CENTER, true, false, 7, 1);
		gd.horizontalIndent = 25; 
		securityLevelForAbstractContextLabel.setLayoutData(gd);

		securityLevelForAbstractContext = new Text(composite, SWT.BORDER);
		securityLevelForAbstractContext.setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, PROJECT_PROPERTY_PAGE_WIDGET_SECURITY_LEVEL_FOR_ABSTRACT_CONTEXT);
		securityLevelForAbstractContext.setText(PreferenceManager.getSeverityForAbstractContext(projectName));
		securityLevelForAbstractContext.setEnabled(PreferenceManager.isBuildPoliciesSynchronisationWithTestsAuthrorized(projectName));
		securityLevelForAbstractContext.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		ModifyListener listener = new ModifyListener() {
		    public void modifyText(ModifyEvent e) {
				ProjectPropertyPage.this.setErrorMessage(null);
				ProjectPropertyPage.this.setValid(true);
				String temp = securityLevelForAbstractContext.getText().trim();
				if (temp.length()==0) return;
				int ret = BuildPolicy.getSeverityLevel(temp);
				if (ret == -1) {
					ProjectPropertyPage.this.setValid(false);
					ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_severity"));
				}

		    }
		};
		securityLevelForAbstractContext.addModifyListener(listener);
		
		
		Label severityLevelForUnSynchronizedContextLabel = new Label(composite, SWT.BORDER);
		severityLevelForUnSynchronizedContextLabel.setText(MessageUtil.getString("severityonunsynchronizedtcontext"));
 		gd = new GridData(SWT.LEFT, SWT.CENTER, true, false, 7, 1);
		severityLevelForUnSynchronizedContextLabel.setLayoutData(gd);
		
		severityLevelForUnSynchronizedContext = new Text(composite, SWT.BORDER);
		severityLevelForUnSynchronizedContext.setData(PROJECT_PROPERTY_PAGE_WIDGET_ID, PROJECT_PROPERTY_PAGE_WIDGET_SEVERITY_LEVEL_FOR_ABSTRACT_CONTEXT);
		severityLevelForUnSynchronizedContext.setText(PreferenceManager.getSeverityForUnSynchronizedContext(projectName));
		severityLevelForUnSynchronizedContext.setEnabled(PreferenceManager.isBuildPoliciesSynchronisationWithTestsAuthrorized(projectName));
		severityLevelForUnSynchronizedContext.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1));
		listener = new ModifyListener() {
		    public void modifyText(ModifyEvent e) {
				ProjectPropertyPage.this.setErrorMessage(null);
				ProjectPropertyPage.this.setValid(true);
				String temp = severityLevelForUnSynchronizedContext.getText().trim();
				if (temp.length()==0) {
					ProjectPropertyPage.this.setValid(false);
					ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_severity"));
				};
				int ret = BuildPolicy.getSeverityLevel(temp);
				if (ret == -1) {
					ProjectPropertyPage.this.setValid(false);
					ProjectPropertyPage.this.setErrorMessage(MessageUtil.getString("invalid_severity"));
				}

		    }
		};
		severityLevelForUnSynchronizedContext.addModifyListener(listener);
		
		
		return new Canvas(parent, 0);
	}


	protected void performDefaults() {
		super.performDefaults();
		// gw4eLibraries.resetToDefaultValue();
		buildDefaultPolicies.resetToDefaultValue();
		// authorizedFolders.resetToDefaultValue();
		performanceConfiguration.resetToDefaultValue();
		cbs.resetToDefaultValue();
		lts.resetToDefaultValue();
		securityLevelForAbstractContext.setText(PreferenceManager.getDefaultSeverityForAbstractContext());
		severityLevelForUnSynchronizedContext.setText(PreferenceManager.getDefaultSeverityForUnSynchronizedContext());
	}
 
	private void updatePreferences () {
		// updatePreference (gw4eLibraries.getValues());
		updatePreference (buildDefaultPolicies.getValues());
		updatePreference (performanceConfiguration.getValues());
		// updatePreference (authorizedFolders.getValues());
		updatePreference (cbs.getValues());
		updatePreference (lts.getValues());
		PreferenceManager.setPreference(projectName, PreferenceManager.SEVERITY_FOR_ABSTRACT_CONTEXT, new String [] {securityLevelForAbstractContext.getText()});
		PreferenceManager.setPreference(projectName, PreferenceManager.SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT, new String [] {severityLevelForUnSynchronizedContext.getText()});

	}
	
	private void updatePreference (Map<String, String[]> map ) {
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key =  iter.next();
			String[]values = map.get(key);
			PreferenceManager.setPreference(projectName, key, values);
		}
	}
	
	public boolean performOk() {
		updatePreferences ();
		return true;
	}
	
}
