
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
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.gw4e.eclipse.wizard.convert.ConvertToFileCreationWizard;
import org.gw4e.eclipse.wizard.convert.FolderSelectionGroup;
import org.gw4e.eclipse.wizard.convert.Problem;
import org.gw4e.eclipse.wizard.convert.model.ResourcePage;

/**
 * The Convert page that let the end user entering choices for graph
 * model file conversion
 *
 */
public class ConvertToResourceUIPage extends WizardPage implements Listener {

 
	
	/**
	 * The ui check box for a java conversion
	 */
	private Button eraseExistingFile;

	/**
	 * The ui check box for a java conversion
	 */
	private Button javaModelBasedCheckbox;

	/**
	 * The ui check box for a test conversion
	 */
	private Button javaTestBasedCheckbox;

	/**
	 * The ui check box for a java offline conversion
	 */
	private Button javaOfflineBasedCheckbox;
	
	/**
	 * The ui check box for a json conversion
	 */
	private Button jsonCheckbox;

	/**
	 * The ui check box for a dot conversion
	 */
	private Button dotCheckbox;

	/**
	 * The ui check box meaning that we want to open the generated file in an
	 * editor after conversion
	 */
	private Button openEditorCheckbox;

	/**
	 * The model file selected by the end user
	 */
	private IStructuredSelection selection;

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
	boolean allowNextPage = false;
	/**
	 * 
	 */
	IProject project;

	/**
	 * 
	 */
	FolderSelectionGroup fsg;

	

	/**
	 * 
	 */
	public static final String GW4E_CONVERSION_CHOICE_CHECKBOX_ID = "id.gw4e.conversion.choice.id";
	public static final String GW4E_CONVERSION_CHOICE_ERASE_CHECKBOX = "id.gw4e.conversion.choice.erase";

	/**
	 * Create an instance of this page. Hold the passed selection
	 * 
	 * @param workbench
	 * @param selection
	 */
	public ConvertToResourceUIPage(IWorkbench workbench, IStructuredSelection selection) {
		super("ConvertToFilePage");
		setPageComplete(false);
		 
		this.setTitle(MessageUtil.getString("Create_Converted_File")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("Create_a_new_converted_file_resource")); //$NON-NLS-1$
		this.selection = selection;
		IFile ifile = ResourceManager.toIFile(selection);
		this.project = ifile.getProject();
	}

	public static List<String> getOptions () {
		 String [] all = new String [] {
				 MessageUtil.getString("convertto_extension_java"),
				 MessageUtil.getString("convertto_extension_testjava"),
				 MessageUtil.getString("convertto_extension_offlinejava"),
				 MessageUtil.getString("convertto_extension_json"),
				 MessageUtil.getString("convertto_extension_dot")
		 };
		 List<String> ret = new ArrayList<String> ();
		 for (int i = 0; i < all.length; i++) {
			 ret.add(all[i]);
		}
		 return ret;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));

		composite.setFont(parent.getFont());

		fsg = new FolderSelectionGroup(composite, this, this.project);

		this.setFileName(ResourceManager.stripFileExtension(selection)); // $NON-NLS-1$

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout());
		group.setText(MessageUtil.getString("converttotargetformat")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		javaModelBasedCheckbox = new Button(group, SWT.RADIO);
		javaModelBasedCheckbox.setText(MessageUtil.getString("convertto_extension_java")); //$NON-NLS-1$
		javaModelBasedCheckbox.addListener(SWT.Selection, this);
		javaModelBasedCheckbox.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID, "java");
		javaModelBasedCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IFile ifile = ResourceManager.toIFile(selection);
					IPath path = PreferenceManager.getTargetFolderForGeneratedTests(ifile);
					try {
						IPath p = ResourceManager.getPathWithinPackageFragment(ifile);
						IPath newpath = path.append(p.removeLastSegments(1));
						if (ResourceManager.resourcePathExists(newpath.toString())) {
							path = newpath;
						}
						ConvertToResourceUIPage.this.fsg.setContainerFullPath(ifile.getProject(),path);
					} catch (Exception e1) {
						ResourceManager.logException(e1);
					}
					
					updateExtensionFile();
					enableFolderSelection();
					javaModelBasedCheckbox.setFocus();
					updateNextPageStatus(false);
					setPageComplete(validatePage());
					break;
				}
			}
		});
		javaModelBasedCheckbox.setSelection(false);
		
		javaTestBasedCheckbox = new Button(group, SWT.RADIO);
		javaTestBasedCheckbox.setText(MessageUtil.getString("convertto_extension_testjava")); //$NON-NLS-1$
		javaTestBasedCheckbox.addListener(SWT.Selection, this);
		javaTestBasedCheckbox.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID, "test");
		javaTestBasedCheckbox.setSelection(false);
		javaTestBasedCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IFile ifile = ResourceManager.toIFile(selection);
					IPath path = PreferenceManager.getTargetFolderForGeneratedTests(ifile);
					try {
						IPath p = ResourceManager.getPathWithinPackageFragment(ifile);
						IPath newpath = path.append(p.removeLastSegments(1));
						if (ResourceManager.resourcePathExists(newpath.toString())) {
							path = newpath;
						}
						ConvertToResourceUIPage.this.fsg.setContainerFullPath(ifile.getProject(),newpath);
					} catch (Exception e1) {
						ResourceManager.logException(e1);
					}
							
					String filename = ifile.getName().substring(0, ifile.getName().indexOf("."));
					
					ConvertToResourceUIPage.this.fsg
							.setResource(filename + PreferenceManager.suffixForTestImplementation(project.getName()));
					disableFolderSelection();
					updateExtensionFile();
					javaTestBasedCheckbox.setFocus();
					updateNextPageStatus(true);
					setPageComplete(validatePage());
					break;
				}
			}
		});
		
		javaOfflineBasedCheckbox = new Button(group, SWT.RADIO);
		javaOfflineBasedCheckbox.setText(MessageUtil.getString("convertto_extension_offlinejava")); //$NON-NLS-1$
		javaOfflineBasedCheckbox.addListener(SWT.Selection, this);
		javaOfflineBasedCheckbox.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID, "offline");
		javaOfflineBasedCheckbox.setSelection(false);
		javaOfflineBasedCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					IFile ifile = ResourceManager.toIFile(selection);
					IPath path = PreferenceManager.getTargetFolderForGeneratedTests(ifile);
					try {
						IPath p = ResourceManager.getPathWithinPackageFragment(ifile);
						IPath newpath = path.append(p.removeLastSegments(1));
						if (ResourceManager.resourcePathExists(newpath.toString())) {
							path = newpath;
						}
						ConvertToResourceUIPage.this.fsg.setContainerFullPath(ifile.getProject(),newpath);
					} catch (Exception e1) {
						ResourceManager.logException(e1);
					}
							
					String filename = ifile.getName().split(Pattern.quote("."))[0];
					
					ConvertToResourceUIPage.this.fsg
							.setResource(filename + PreferenceManager.suffixForTestOfflineImplementation(project.getName()));
					disableFolderSelection();
					updateExtensionFile();
					javaOfflineBasedCheckbox.setFocus();
					updateNextPageStatus(true);
					setPageComplete(validatePage());
					break;
				}
			}
		});
		
		

		jsonCheckbox = new Button(group, SWT.RADIO);
		jsonCheckbox.setText(MessageUtil.getString("convertto_extension_json")); //$NON-NLS-1$
		jsonCheckbox.setSelection(false);
		jsonCheckbox.addListener(SWT.Selection, this);
		jsonCheckbox.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID, "json");
		jsonCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					enableFolderSelection();
					updateExtensionFile();
					jsonCheckbox.setFocus();
					updateNextPageStatus(false);
					selectFolder ();
					setPageComplete(validatePage());
					break;
				}
			}
		});

		dotCheckbox = new Button(group, SWT.RADIO);
		dotCheckbox.setText(MessageUtil.getString("convertto_extension_dot")); //$NON-NLS-1$
		dotCheckbox.setSelection(false);
		dotCheckbox.addListener(SWT.Selection, this);
		dotCheckbox.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID, "dot");
		dotCheckbox.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					enableFolderSelection();
					updateExtensionFile();
					dotCheckbox.setFocus();
					updateNextPageStatus(false);
					getWizard().getContainer().updateButtons();
					selectFolder();
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
		eraseExistingFile.setData(GW4E_CONVERSION_CHOICE_CHECKBOX_ID,
				GW4E_CONVERSION_CHOICE_ERASE_CHECKBOX);

		eraseExistingFile.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					eraseExistingFile.setFocus();
					ConvertToResourceUIPage.this.fsg.setAllowExistingResources(eraseExistingFile.getSelection());
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

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		((ConvertToFileCreationWizard) this.getWizard()).setResourcePage(null);
		setPageComplete(validatePage());
	    super.setVisible(visible);
	}
	
	/**
	 * 
	 */
	private void selectFolder () {
		IStructuredSelection s = (IStructuredSelection) ConvertToResourceUIPage.this.selection;
		IFile file = (IFile)s.getFirstElement();
		try {
			IPath p = ResourceManager.getPathWithinPackageFragment(file);
			p = project.getFullPath().append(PreferenceManager.getMainResourceFolder()).append(p);
			p = p.removeLastSegments(1);
			ConvertToResourceUIPage.this.fsg.setContainerFullPath(file.getProject(),p);
		} catch (CoreException e1) {
			ResourceManager.logException(e1);
		}
	}
	
	/**
	 * @param allowNext
	 */
	private void updateNextPageStatus (boolean allowNext) {
		allowNextPage = allowNext;
		getWizard().getContainer().updateButtons();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return allowNextPage;
	}

	/**
	 * 
	 */
	private void enableFolderSelection() {
		fsg.enable();
		eraseExistingFile.setEnabled(true);
		
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

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
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
		String ret =  "java";		 
		if (jsonCheckbox.getSelection()) {
			ret = "json";
		}
		if (dotCheckbox.getSelection()) {
			ret = "dot";
		}
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
		((ConvertToFileCreationWizard) this.getWizard()).setResourcePage(null);
		this.setErrorMessage(null);
		this.setMessage(null);
		setPageComplete(false);

		if (fsg == null | javaModelBasedCheckbox == null )
			return false;
		Problem pb = fsg.validate();
		if (pb != null) {
			this.setErrorMessage(pb.getProblemMessage());
			return false;
		}

 
		
		if (!javaModelBasedCheckbox.getSelection() && !javaOfflineBasedCheckbox.getSelection() &&
				!javaTestBasedCheckbox.getSelection() && 
				!jsonCheckbox.getSelection() && !dotCheckbox.getSelection()) {
			this.setErrorMessage(MessageUtil.getString("choose_the_conversion_you_want_to_make"));
			setPageComplete (false);
			getContainer().updateButtons();
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
				if (!(javaTestBasedCheckbox != null && javaTestBasedCheckbox.getSelection())
						&&
					!(javaOfflineBasedCheckbox != null && javaOfflineBasedCheckbox.getSelection())) {
					IPath path = this.getContainerFullPath()
							.append(new Path(this.getFileName()));
					boolean exists = ResourceManager.fileExists(fsg.getSelectedContainer().getProject(), path.toString());
					 
					if (exists) {
						this.setErrorMessage(MessageUtil.getString("convertto_fileAlreadyExists"));
						return false;
					}
				}
			} catch (CoreException e) {
				ResourceManager.logException(e);
				return false;
			}
		}
		 
		ResourcePage rp = new ResourcePage(
				getContainerFullPath(), 
				getFileName(), 
				ResourceManager.toIFile(selection), 
				openEditorCheckbox.getSelection(),
				eraseExistingFile.getSelection(),  
				javaModelBasedCheckbox.getSelection(), 
				javaTestBasedCheckbox.getSelection(), 
				javaOfflineBasedCheckbox.getSelection(),
				jsonCheckbox.getSelection(),
				dotCheckbox.getSelection());
		((ConvertToFileCreationWizard) this.getWizard()).setResourcePage(rp);
		return true;
	}

}
