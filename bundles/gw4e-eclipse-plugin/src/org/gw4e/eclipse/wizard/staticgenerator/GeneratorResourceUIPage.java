
package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.File;
import java.text.MessageFormat;

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
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.FolderSelectionGroup;
import org.gw4e.eclipse.wizard.convert.Problem;
import org.gw4e.eclipse.wizard.staticgenerator.model.ResourcePage;

/**
 * The Generator page that let the end user entering choices for graph model
 * file conversion
 *
 */
public class GeneratorResourceUIPage extends WizardPage implements Listener {

	private Button eraseExistingFile;

	/**
	 * The ui check box for a java Api conversion
	 */
	private Button javaApiBasedCheckbox;

	/**
	 * The ui check box meaning that we want to open the generated file in an
	 * editor after conversion
	 */
	private Button openEditorCheckbox;

	 
	/**
	 * A listener to wait for the generated file and perform action after the
	 * conversion
	 */
	IResourceChangeListener listener = null;

	/**
	 * 
	 */
	boolean validate = true;

	/**
	 * 
	 */
	IProject project;

	/**
	 * 
	 */
	FolderSelectionGroup fsg;

	IFile modelFile;
	
	/**
	 * 
	 */
	public static final String GW4E_GENERATOR_CHOICE_CHECKBOX_ID = "id.gw4e.generator.choice.id";
	public static final String GW4E_GENERATOR_CHOICE_ERASE_CHECKBOX = "id.gw4e.generator.choice.erase";

	/**
	 * Create an instance of this page. Hold the passed selection
	 * 
	 * @param workbench
	 * @param selection
	 */
	public GeneratorResourceUIPage(IWorkbench workbench, File model) {
		super("GeneratorFilePage");
		setPageComplete(false);

		this.setTitle(MessageUtil.getString("Generate_File")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("Create_a_new_converted_file_resource")); //$NON-NLS-1$
		 

		this.modelFile = ResourceManager.toIFile(model);
		this.project = modelFile.getProject();
	}

	public static List<String> getOptions() {
		String[] all = new String[] { MessageUtil.getString("generateto_extension_api_based"), };
		List<String> ret = new ArrayList<String>();
		for (int i = 0; i < all.length; i++) {
			ret.add(all[i]);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
	 * widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		composite.setFont(parent.getFont());

		fsg = new FolderSelectionGroup(composite, this, this.project);

		this.setFileName(modelFile.getName().split(Pattern.quote("."))[0]+"Static"); // $NON-NLS-1$

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout());
		group.setText(MessageUtil.getString("converttotargetformat")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		javaApiBasedCheckbox = new Button(group, SWT.RADIO);
		javaApiBasedCheckbox.setText(MessageUtil.getString("generateto_extension_api_based")); //$NON-NLS-1$
		javaApiBasedCheckbox.addListener(SWT.Selection, this);
		javaApiBasedCheckbox.setData(GW4E_GENERATOR_CHOICE_CHECKBOX_ID, "offline");
		javaApiBasedCheckbox.setSelection(true);
		javaApiBasedCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
				 
					IPath path = PreferenceManager.getTargetFolderForGeneratedTests(modelFile);
					try {
						IPath p = ResourceManager.getPathWithinPackageFragment(modelFile);
						IPath newpath = path.append(p.removeLastSegments(1));
						if (ResourceManager.resourcePathExists(newpath.toString())) {
							path = newpath;
						}
						GeneratorResourceUIPage.this.fsg.setContainerFullPath(modelFile.getProject(), newpath);
					} catch (Exception e1) {
						ResourceManager.logException(e1);
					}

					String filename = modelFile.getName().split(Pattern.quote("."))[0];

					GeneratorResourceUIPage.this.fsg.setResource(
							filename + PreferenceManager.suffixForTestOfflineImplementation(project.getName()));
					disableFolderSelection();
					updateExtensionFile();
					javaApiBasedCheckbox.setFocus();
					updateNextPageStatus(true);
					setPageComplete(validatePage());
					break;
				}
			}
		});

		Group groupOptions = new Group(composite, SWT.NONE);
		groupOptions.setLayout(new GridLayout());
		groupOptions.setText(MessageUtil.getString("convertToOptions")); //$NON-NLS-1$
		groupOptions.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		openEditorCheckbox = new Button(groupOptions, SWT.CHECK);
		openEditorCheckbox.setText(MessageUtil.getString("Open_file_for_editing_when_done")); //$NON-NLS-1$
		openEditorCheckbox.setSelection(true);
		openEditorCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					openEditorCheckbox.setFocus();
					break;
				}
			}
		});

		eraseExistingFile = new Button(groupOptions, SWT.CHECK);
		eraseExistingFile.setText(MessageUtil.getString("convertEraseExistingFile")); //$NON-NLS-1$
		eraseExistingFile.setSelection(false);
		eraseExistingFile.setData(GW4E_GENERATOR_CHOICE_CHECKBOX_ID, GW4E_GENERATOR_CHOICE_ERASE_CHECKBOX);

		eraseExistingFile.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					eraseExistingFile.setFocus();
					GeneratorResourceUIPage.this.fsg.setAllowExistingResources(eraseExistingFile.getSelection());
					setPageComplete(validatePage());
					break;
				}
			}
		});

		setControl(composite);
		setErrorMessage(null);
		setMessage(null);
		updateExtensionFile();
		setPageComplete(validatePage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		((GeneratorToFileCreationWizard) this.getWizard()).setResourcePage(null);
		setPageComplete(validatePage());
		super.setVisible(visible);
	}

	/**
	 * @param allowNext
	 */
	private void updateNextPageStatus(boolean allowNext) {
		getWizard().getContainer().updateButtons();
	}

	/**
	 * 
	 */
	private void disableFolderSelection() {
		fsg.disable();
		eraseExistingFile.setEnabled(false);
	}

	/**
	 * @param value
	 */
	public void setFileName(String value) {
		fsg.setResource(value);
	}

	/**
	 * @param parent
	 */
	protected void createAdvancedControls(Composite parent) {
		// no advanced
	}

	/**
	 * @return
	 */
	protected IStatus validateLinkedResource() {
		return Status.OK_STATUS;
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
		setPageComplete(validatePage());
	}

	/**
	 * @return
	 */
	protected String getNewFileLabel() {
		return MessageUtil.getString("Convert_To_File_Name"); //$NON-NLS-1$
	}

	/**
	 * @param value
	 */
	public void setFileExtension(String value) {
		fsg.setFileExtension(value);
	}

	/**
	 * Set the extension file
	 */
	public void updateExtensionFile() {
		this.setFileExtension(getExtensionFile());
	}

	/**
	 * @return
	 */
	private String getExtensionFile() {
		String ret = "java";
		return ret;
	}

	/**
	 * @return
	 */
	public IPath getContainerFullPath() {
		return fsg.getSelectedContainer().getFullPath();
	}

	/**
	 * @return
	 */
	public String getFileName() {
		return fsg.getResourceWithFileExtension();
	}

	public String getClassName() {
		return fsg.getResourceWithOutFileExtension();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		((GeneratorToFileCreationWizard) this.getWizard()).setResourcePage(null);
		this.setErrorMessage(null);
		this.setMessage(null);
		setPageComplete(false);

		if (fsg == null)
			return false;
		Problem pb = fsg.validate();
		if (pb != null) {
			this.setErrorMessage(pb.getProblemMessage());
			return false;
		}

		String filename = this.fsg.getSelectedResourceName();
		// Dont want the end user to enter the extension file
		if (filename.indexOf(".") != -1) {
			this.setErrorMessage(MessageUtil.getString("Dont_set_extension_To_File_Name"));
			return false;
		}

		if (eraseExistingFile != null && !eraseExistingFile.getSelection()) {
			try {
				IPath path = this.getContainerFullPath().append(new Path(this.getFileName()));
				boolean exists = ResourceManager.fileExists(fsg.getSelectedContainer().getProject(), path.toString());
				if (exists) {
					this.setErrorMessage(MessageUtil.getString("convertto_fileAlreadyExists"));
					return false;
				}
			} catch (CoreException e) {
				ResourceManager.logException(e);
				return false;
			}
		}
		
		String folderSelected = this.fsg.getSelectedContainer().getFullPath().toString();
		String folderMainSource = project.getFullPath().append(PreferenceManager.getMainSourceFolder()).toString();
		String foldertestSource = project.getFullPath().append(PreferenceManager.getTestSourceFolder()).toString();
		
		if (folderSelected!=null && folderSelected.trim().length()>0) {
			if (!(folderSelected.startsWith(folderMainSource)) && !(folderSelected.startsWith(foldertestSource))) {
				String message = MessageUtil.getString("convertto_invalidfolder_you_can_only_choose_format");
				MessageFormat mf = new MessageFormat(message);
				String msg = mf.format(new Object[] {folderMainSource, foldertestSource});
				this.setErrorMessage(msg);
				return false;
			}
		}
		
		 
		
		ResourcePage rp = new ResourcePage(
				getContainerFullPath(), 
				getFileName(), 
				modelFile,
				openEditorCheckbox.getSelection(), 
				eraseExistingFile.getSelection(),
				javaApiBasedCheckbox.getSelection());

		((GeneratorToFileCreationWizard) this.getWizard()).setResourcePage(rp);

		return true;
	}
	
	
	 

}
