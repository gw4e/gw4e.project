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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.model.WorkbenchViewerComparator;
import org.gw4e.eclipse.builder.InitialBuildPolicies;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.product.GW4ENature;

public class TemplateComposite extends Composite implements ISelectionProvider {
	ISelection selection = null;
	TreeViewer fDestinationField = null;
	Button createPackageButton = null;
	boolean displayProjects = false;
	Label lblChooseName = null;
	TemplateProvider provider = null;
	String errorMessage;
	List<ISelectionChangedListener> listeners = new ArrayList<ISelectionChangedListener>();
	Text nameField = null;

	public TemplateComposite(ISelectionChangedListener listener, List<TemplateProvider> templates,
			boolean displayProjects, Composite parent, int style) {
		super(parent, style);
		this.displayProjects = displayProjects;

		addSelectionChangedListener(listener);
		if (displayProjects) {
			setLayout(new GridLayout(4, false));
			addNameSection();
			skip();
			addCustomTargetFolderSection();
		} else {
			setLayout(new GridLayout(1, false));
			addNameSection();
			addDefaultTargetFolderSection();
		}
		skip();
		addTemplatesSection(templates);
		skip();
		addBuildPoliciesSection();
	}

	private void skip() {
		Label lblDummy = new Label(this, SWT.NONE);
		lblDummy.setText("");
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
		lblDummy.setLayoutData(gd);
	}

	private void addNameSection() {
		Composite p = new Composite(this, SWT.NONE);
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 4;
		p.setLayout(topLayout);

		lblChooseName = new Label(this, SWT.NONE);
		lblChooseName.setText("Enter a file name (with a '.json' extension):");
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
		lblChooseName.setLayoutData(gd);

		nameField = new Text(this, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
		nameField.setLayoutData(gd);

		nameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setSelection(new StructuredSelection(nameField.getText()));
			}
		});
	}

	private void addBuildPoliciesSection() {
		Composite p = new Composite(this, SWT.NONE);
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 1;
		p.setLayout(topLayout);

		Label lblChooseATemplate = new Label(p, SWT.NONE);
		lblChooseATemplate.setText("Choose the build policies:");
		List<InitialBuildPolicies> set = InitialBuildPolicies.ALL();
		boolean defaultSet = false;
		for (InitialBuildPolicies initialBuildPolicies : set) {
			Button button = new Button(p, SWT.RADIO);
			button.setText(initialBuildPolicies.getName());
			GridData gd = new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
			button.setLayoutData(gd);
			if (!defaultSet) {
				defaultSet = true;
				button.setSelection(true);
				setSelection(new StructuredSelection(initialBuildPolicies));
			}
			button.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					setSelection(new StructuredSelection(initialBuildPolicies));
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
		}
	}

	private void addTemplatesSection(List<TemplateProvider> templates) {
		Composite p = new Composite(this, SWT.NONE);
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 4;
		p.setLayout(topLayout);

		Label lblChooseATemplate = new Label(p, SWT.NONE);
		lblChooseATemplate.setText("Choose a template model:");
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
		lblChooseATemplate.setLayoutData(gd);

		List<Button> buttons = new ArrayList<Button>();
		boolean defaultSet = false;
		for (TemplateProvider provider : templates) {
			Button button = new Button(p, SWT.RADIO);
			buttons.add(button);
			button.setText(provider.getLabel());
			boolean isDefault = provider.isNoneProvider();

			GridData gdButton = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
			button.setLayoutData(gdButton);

			ISelection sel = new StructuredSelection(new Object[] { provider });

			button.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateFilename(provider);
					setSelection(sel);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});

			if (isDefault) {
				defaultSet = true;
				button.setSelection(true);
				setSelection(new StructuredSelection(provider));
			}
		}
		if (!defaultSet) {
			buttons.get(0).setSelection(true);
			updateFilename(templates.get(0));
			setSelection(new StructuredSelection(templates.get(0)));
		}
	}

	private void updateFilename(TemplateProvider provider) {
		this.provider = provider;
		nameField.setEnabled(false);
		lblChooseName.setEnabled(false);
		nameField.setText("");
		String filename = provider.getDefaultFileName();
		if (filename != null) {
			nameField.setEnabled(true);
			lblChooseName.setEnabled(true);
			nameField.setText(filename);
		}
	}

	private void addCustomTargetFolderSection() {
		Label lblChooseATemplate = new Label(this, SWT.NONE);
		lblChooseATemplate.setText("Choose a target folder for the graph model:");
		GridData gd1 = new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1);
		lblChooseATemplate.setLayoutData(gd1);

		fDestinationField = new TreeViewer(this, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true, 3, 1);

		fDestinationField.getTree().setLayoutData(gd);
		fDestinationField.setLabelProvider(new WorkbenchLabelProvider());
		fDestinationField.setContentProvider(new BaseWorkbenchContentProvider());
		fDestinationField.setComparator(new WorkbenchViewerComparator());
		fDestinationField.setInput(ResourcesPlugin.getWorkspace());
		fDestinationField.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IProject) {
					IProject project = (IProject) element;
					return project.isAccessible() && GW4ENature.hasGW4ENature(project);
				} else if (element instanceof IFolder) {
					String[] folders = PreferenceManager.getDefaultAuthorizedFolderForGraphDefinition();
					for (String f : folders) {
						IFolder tmp = (IFolder) element;
						IProject project = tmp.getProject();
						IPath p = new Path(project.getFullPath().toString()).append(f);
						IPath tmpPath = tmp.getFullPath();

						if (tmpPath.segmentCount() <= p.segmentCount()) {
							if (tmpPath.isPrefixOf(p)) {
								return true;
							}
						} else {
							if (p.isPrefixOf(tmpPath)) {
								return true;
							}
						}
					}
					return false;
				}
				return false;
			}
		});
		fDestinationField.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setSelection(event.getSelection());
			}
		});

		createPackageButton = new Button(this, SWT.PUSH);
		createPackageButton.setEnabled(false);
		createPackageButton.setText("Create package...");
		GridData gdButton = new GridData(GridData.FILL, SWT.TOP, false, false, 1, 1);
		createPackageButton.setLayoutData(gdButton);

		createPackageButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CreatePackageDialog dialog = new CreatePackageDialog();
				dialog.create();
				if (dialog.open() == Window.OK) {
					ITreeSelection sl = fDestinationField.getStructuredSelection();
					IFolder folder = (IFolder) sl.getFirstElement();
					try {
						IFolder f = ResourceManager.createFolder(folder.getFullPath(),
								new Path(dialog.getValue().replaceAll(Pattern.quote("."), "/")));
						fDestinationField.setInput(ResourcesPlugin.getWorkspace());
						fDestinationField.refresh();
						fDestinationField.setSelection(new StructuredSelection(f), true);
					} catch (CoreException e1) {
						DialogManager.displayErrorMessage("An error has occured while creatng the package");
						ResourceManager.logException(e1);
					}
				}
			}
		});

	}

	private void addDefaultTargetFolderSection() {

		Label lblChooseATemplate = new Label(this, SWT.NONE);
		lblChooseATemplate.setText("Choose a target folder :");

		String[] temp = PreferenceManager.getDefaultAuthorizedFolderForGraphDefinition();
		Folder[] folders = new Folder [temp.length];
		int i = 0;
		for (String f : temp) {
			folders[i] = new Folder (f);
			i++;
		}
		
		ComboViewer viewerTarget = new ComboViewer(this, SWT.READ_ONLY);
		Combo combo = viewerTarget.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		viewerTarget.setContentProvider(ArrayContentProvider.getInstance());
		viewerTarget.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				Folder folder = (Folder) element;
				return folder.getName();
			}
		});
		viewerTarget.setInput(folders);

		viewerTarget.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() > 0) {
					setSelection(event.getSelection());
				}
			}
		});
		ISelection selection = new StructuredSelection(folders[0]);
		viewerTarget.setSelection(selection);
		setSelection(selection);
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		return this.selection;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		this.selection = selection;
		for (ISelectionChangedListener iSelectionChangedListener : listeners) {
			iSelectionChangedListener.selectionChanged(new SelectionChangedEvent(this, selection));
		}
	}

	public void setErrorMessage(String msg) {
		this.errorMessage = msg;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public boolean validatePage() {
		setErrorMessage(null);
		if (!displayProjects)
			return true;
		if (createPackageButton != null)
			createPackageButton.setEnabled(false);
		if (fDestinationField != null) {
			ITreeSelection sl = fDestinationField.getStructuredSelection();
			if (sl.isEmpty()) {
				setErrorMessage(MessageUtil.getString("choose_a_target_folder_for_the_graph"));
				return false;	
			}
			if (!(sl.getFirstElement() instanceof IFolder)) {
				setErrorMessage(MessageUtil.getString("choose_a_target_folder_for_the_graph"));
				return false;
			}
			if (sl.getFirstElement() instanceof IFolder) {
				IFolder folder = (IFolder) sl.getFirstElement();
				String[] folders = PreferenceManager.getDefaultAuthorizedFolderForGraphDefinition();
				boolean found = false;
				for (String f : folders) {
					IFolder tmp = (IFolder) folder;
					if (tmp == null)
						return false;
					IProject project = tmp.getProject();
					IPath p = new Path(project.getFullPath().toString()).append(f);
					IPath tmpPath = tmp.getFullPath();
					if (p.isPrefixOf(tmpPath)) {
						createPackageButton.setEnabled(true);
						found = true;
					}
				}
				if (!found) {
					setErrorMessage(MessageUtil.getString("choose_a_target_folder_for_the_graph"));
					return false;
				}
			}
		}
		if (this.provider != null) {
			if (this.provider.getDefaultFileName() != null
					&& ((nameField.getText() == null) || (nameField.getText().trim().length() == 0))) {
				setErrorMessage(MessageUtil.getString("choose_a_valid_filename"));
				return false;
			}
			String value = nameField.getText();
			if (this.provider.getDefaultFileName() != null && !isValidFilename(value)) {
				setErrorMessage(MessageUtil.getString("choose_a_valid_filename"));
				return false;
			}
		}

		return true;
	}

	public boolean isValidFilename(String file) {
		if (file==null) return false;
		if (file.trim().length() == 0) return false;
		File f = new File(file);
		try {
			f.getCanonicalPath();
			String[] temp = file.split(Pattern.quote("."));
			if (temp.length != 2) {
				return false;
			}
			if (temp[0].trim().length()==0) return false;
			if (!temp[1].trim().equals("json")) return false;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public class CreatePackageDialog extends TitleAreaDialog {

		private Text txtValue;
		private String value;

		public CreatePackageDialog() {
			super(Display.getDefault().getActiveShell());
		}

		@Override
		public void create() {
			super.create();
			setTitle("Create a new package");
			setMessage("Enter a dotted name", IMessageProvider.INFORMATION);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite container = new Composite(area, SWT.NONE);
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			GridLayout layout = new GridLayout(2, false);
			container.setLayout(layout);

			createFirstName(container);
			return area;
		}

		private void createFirstName(Composite container) {
			Label lbtName = new Label(container, SWT.NONE);
			lbtName.setText("Name");

			GridData dataName = new GridData();
			dataName.grabExcessHorizontalSpace = true;
			dataName.horizontalAlignment = GridData.FILL;

			txtValue = new Text(container, SWT.BORDER);
			txtValue.setLayoutData(dataName);
		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		private void saveInput() {
			value = txtValue.getText();
		}

		@Override
		protected void okPressed() {
			saveInput();
			super.okPressed();
		}

		public String getValue() {
			return value;
		}

	}

}
