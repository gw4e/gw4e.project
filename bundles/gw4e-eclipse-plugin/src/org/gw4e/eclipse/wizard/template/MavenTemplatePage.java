package org.gw4e.eclipse.wizard.template;

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

import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.MavenFacade;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class MavenTemplatePage extends WizardPage {
	ISelectionChangedListener listener;
	 
	private Composite composite;
	private String groupId; 
	private String version;
	private String artifactId;
	private String name; 
	private String gwversion;
    private IProject project ;
	private Text textName;
	private Text textArtifactID;
	private Text textGrpID;
	private Text textVersionID;
	private Text textGraphWalkerVersion;
    
	public MavenTemplatePage(ISelectionChangedListener listener) {
		super("Maven Template Page");
		this.setDescription("Create Maven pom file");
		this.listener = listener;
		validatePage();
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new GridLayout(12, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 12, 1));
		
		createGroupIdArea(composite);
		createVersionIdArea(composite);
		createArtifactIdArea(composite);
		createNameArea(composite);
		createGWVersionArea(composite);
	 
		this.setPageComplete(validatePage());
	}

	private void createGroupIdArea (Composite composite) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewLabel.setText(MessageUtil.getString("mvn_group_id"));
		textGrpID = new Text(composite, SWT.BORDER);
		textGrpID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		textGrpID.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				MavenTemplatePage.this.setGroupId(textGrpID.getText());
				validatePage();
			}
		});
		textGrpID.setText("com.company");
	}
	private void createVersionIdArea (Composite composite) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewLabel.setText(MessageUtil.getString("mvn_version_id"));
		textVersionID = new Text(composite, SWT.BORDER);
		textVersionID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		textVersionID.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				MavenTemplatePage.this.setVersion(textVersionID.getText());
				validatePage();
			}
		});		
		textVersionID.setText("1.0-SNAPSHOT");
	}
	
	private void createArtifactIdArea (Composite composite) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewLabel.setText(MessageUtil.getString("mvn_artifact_id"));
		textArtifactID = new Text(composite, SWT.BORDER);
		textArtifactID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		textArtifactID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		textArtifactID.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				MavenTemplatePage.this.setArtifactId(textArtifactID.getText());
				validatePage();
			}
		});	
	}	
	
	private void createNameArea (Composite composite) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewLabel.setText(MessageUtil.getString("mvn_name"));
		textName = new Text(composite, SWT.BORDER);
		textName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		textName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				MavenTemplatePage.this.setName(textName.getText());
				validatePage();
			}
		});	
	}	
	
	private void createGWVersionArea (Composite composite) {
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewLabel.setText(MessageUtil.getString("gw_version"));
		textGraphWalkerVersion = new Text(composite, SWT.BORDER);
		textGraphWalkerVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		textGraphWalkerVersion.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent evt) {
				MavenTemplatePage.this.setGwversion(textGraphWalkerVersion.getText());
				validatePage();
			}
		});
		textGraphWalkerVersion.setText(PreferenceManager.getDefaultGraphWalkerVersion());
	}	
	
	public boolean validatePage() {
		setErrorMessage(null);
		setPageComplete(false);
	 
		 if ( ( groupId == null ) || (groupId.trim().length() ==0) ) {
			 setErrorMessage(MessageUtil.getString("missing_maven_group_id"));
			 return false;
		 }
		 if ( ( version == null ) || (version.trim().length() ==0) ) {
			 setErrorMessage(MessageUtil.getString("missing_version_id"));
			 return false;
		 }
		 if ( ( artifactId == null ) || (artifactId.trim().length() ==0) ) {
			 setErrorMessage(MessageUtil.getString("missing_artifact_id"));
			 return false;
		 }
		 if ( ( name == null ) || (name.trim().length() ==0) ) {
			 setErrorMessage(MessageUtil.getString("missing_maven_name"));
			 return false;
		 }
		 if ( ( gwversion == null ) || (gwversion.trim().length() ==0) ) {
			 setErrorMessage(MessageUtil.getString("missing_graphWalker_version"));
			 return false;
		 }	
		 
		setPageComplete(true);
		return true;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGwversion() {
		return gwversion;
	}

	public void setGwversion(String gwversion) {
		this.gwversion = gwversion;
	}

	public void setProject(IProject project) {
		this.project = project;
		textName.setText(project.getName());
		textArtifactID.setText(project.getName());
	}

	public void create () throws IOException, CoreException {
		MavenFacade.create(project, groupId, version, artifactId, name, gwversion);
	}
	
	public IPath getPackagePath () {
		return new Path (getGroupId().replaceAll(Pattern.quote("."), "/"));
	}
}
