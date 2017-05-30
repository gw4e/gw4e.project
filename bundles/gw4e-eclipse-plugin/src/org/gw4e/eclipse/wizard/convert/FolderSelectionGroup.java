package org.gw4e.eclipse.wizard.convert;

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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class FolderSelectionGroup   {
	private Listener listener;
	
	protected IContainer selectedContainer;
	
	protected Text resourceNameField;
	 
	protected Text outputField;
	
	protected IProject project;

	private boolean allowExistingResources;
	
	TreeViewer typeTreeViewer;
	
	private String resourceExtension;

	private FilteredTree tree;
	
	public static final String GW4E_CONVERSION_TREE = "id.gw4e.conversion.tree";
	
	public FolderSelectionGroup(Composite parent, Listener client, IProject project) {
		 
		this.listener = client;
		this.project=project;
		 
		Composite composite = new Composite(parent, SWT.NONE);
	 	composite.setLayout(new GridLayout());
	 	composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		
		composite.setFont(parent.getFont());
 
		Label label = new Label(composite, SWT.NONE);
		label.setText(MessageUtil.getString("Convert_To_File_Enter_filter"));
		 
		PatternFilter filter = new PatternFilter(); 
        tree = new FilteredTree(parent, SWT.BORDER, filter, true); 
        
        
        tree.getViewer().getTree().setData(GW4E_CONVERSION_TREE,GW4E_CONVERSION_TREE);
        
        typeTreeViewer = tree.getViewer(); 
        typeTreeViewer.setContentProvider(new ContentProvider()); 
        typeTreeViewer.setLabelProvider(new DecoratingLabelProvider(new LabelProvider(),
                PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator())); 
        typeTreeViewer.setComparator(new ViewerComparator());
        typeTreeViewer.setUseHashlookup(true);
        typeTreeViewer.addSelectionChangedListener(event -> {
        	IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        	setContainer((IContainer) selection.getFirstElement());
		});
        typeTreeViewer.setInput(project); 
 
		Composite nameGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		nameGroup.setLayout(layout);
		nameGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		nameGroup.setFont(parent.getFont());

		label = new Label(nameGroup, SWT.NONE);
		label.setText(MessageUtil.getString("Convert_To_File_Name"));
	 
		resourceNameField = new Text(nameGroup, SWT.BORDER);
		resourceNameField.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				updateOutputResource();
				fireEvent ();
			}
		});
		
		
		updateOutputResource();
		resourceNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				setResource(resourceNameField.getText());
			}
		});
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = 250;
		resourceNameField.setLayoutData(data);
		resourceNameField.setFont(parent.getFont());
		
		 
		
		label = new Label(nameGroup, SWT.NONE);
		label.setText(MessageUtil.getString("output_To_File_Name"));
		
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = 250;
		outputField = new Text(nameGroup, SWT.BORDER);
		outputField.setLayoutData(data);
		outputField.setFont(parent.getFont());
		outputField.setEnabled(false);
		
		initFirstExpansion(); 
	}
  
	
	private void updateOutputResource ()  {
		if (outputField==null) return;
		try {
			IPath path = selectedContainer.getFullPath().append(new Path(resourceNameField.getText()));
			outputField.setText(path.toString() + "." + this.resourceExtension); 
		} catch (Exception e) {
			outputField.setText(""); 
		}
	}
	
	public void setResource(String value) {
		resourceNameField.setText(value);
		updateOutputResource ();
		fireEvent ();
	}
	
	public void setContainer(IContainer value) {
		selectedContainer=value;
		updateOutputResource ();
		fireEvent ();
	}
	
	private void fireEvent () {
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			listener.handleEvent(changeEvent);
		}		
	}
	
	public void disable () {
		tree.setEnabled(false);
		resourceNameField.setEnabled(false);
	}
	
	public void enable () {
		tree.setEnabled(true);
		resourceNameField.setEnabled(true);
	}
	
	public class LabelProvider implements ILabelProvider {
		 
		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getImage(Object element) {
			IResource resource = (IResource) element;
			IJavaElement javaElement = JavaCore.create(resource);
			
			if (javaElement instanceof IJavaProject) {
				return PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
			}
			
			org.eclipse.ui.ISharedImages sharedImages= PlatformUI.getWorkbench().getSharedImages();
			
			Image image = sharedImages.getImage(org.eclipse.ui.ISharedImages.IMG_OBJ_FOLDER);
			return image;
 
		}

		@Override
		public String getText(Object element) {
			IResource resource = (IResource) element;
			IJavaElement javaElement = JavaCore.create(resource);
			
			if (javaElement instanceof IJavaProject) {
				String path  =  resource.getFullPath().toString();
				return path.substring(1);
			}
			 
			if (javaElement instanceof IPackageFragmentRoot) {
				String path  =  resource.getFullPath().removeFirstSegments(1).toString();
				return path;
			}
			if (javaElement instanceof IPackageFragment) {
				String path  =  resource.getName();
				return path;
			}
			return "";
		}
		
		 

	}
	   
	   public class ContentProvider implements ITreeContentProvider {
		public ContentProvider () {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			
			if (parentElement instanceof IWorkspace) {
				return new Object[] { FolderSelectionGroup.this.project };
			}
			if (parentElement instanceof IProject) {
				try {
					IJavaProject jproject = JavaCore.create(FolderSelectionGroup.this.project);
					IPackageFragmentRoot[] roots = jproject.getAllPackageFragmentRoots();
					List<IContainer> ret = new ArrayList<IContainer>();
					for (int i = 0; i < roots.length; i++) {
						IPackageFragmentRoot pfr = roots[i];
						if (pfr.isArchive()) continue;
						if (!pfr.getJavaProject().equals(jproject)) continue;
						IResource resource =  pfr.getUnderlyingResource();
						if (resource==null) continue;
						if (PreferenceManager.isTargetFolderForTestInterface(resource,true)) continue;
						if (PreferenceManager.isTargetFolderForTestInterface(resource,false)) continue;
						ret.add((IContainer)resource);
					}
					return ret.toArray(); 
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
			}
			if (parentElement instanceof IPackageFragmentRoot) {
				try {
					IPackageFragmentRoot container = (IPackageFragmentRoot) parentElement;
					List<IContainer> ret = new ArrayList<IContainer>();
					IJavaElement[] elements = container.getChildren();
					for (int i = 0; i < elements.length; i++) {
						IJavaElement element = elements[i];
						if (IJavaElement.PACKAGE_FRAGMENT == element.getElementType()) {
							IResource resource =  element.getUnderlyingResource();
							ret.add((IContainer)resource);
						}
					}
					return ret.toArray(); 
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
			}
			if (parentElement instanceof IContainer) {
				IContainer container = (IContainer) parentElement;
				try {
					List children = new ArrayList();
					IResource[] members = container.members();
					for (int i = 0; i < members.length; i++) {
						if (members[i].getType() != IResource.FILE) {
							children.add(members[i]);
						}
					}
					return children.toArray();
				} catch (CoreException e) {
					ResourceManager.logException(e);
				}
			}			
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
	        if (element instanceof IResource) {
				return ((IResource) element).getParent();
			}
	        return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}

	/**
	 * @return the selectedContainer
	 */
	public IContainer getSelectedContainer() {
		return selectedContainer;
	}

	
	/**
	 * @param container
	 */
	public void initFirstExpansion() {
		typeTreeViewer.expandToLevel (project,1);
		typeTreeViewer.setSelection(new StructuredSelection(project), true);
		fireEvent ();
	}
	
	/**
	 * @param container
	 * @throws CoreException 
	 */
	public void setContainerFullPath(IProject project, IPath path) throws CoreException {
		IResource container = ResourceManager.getResource(path.toString());
		if (container==null)
			container = ResourceManager.ensureFolder(project, path.removeFirstSegments(1).toString(), new NullProgressMonitor());
		typeTreeViewer.refresh();
		selectedContainer=(IContainer)container;
		typeTreeViewer.expandToLevel(path, 1);
		typeTreeViewer.setSelection(new StructuredSelection(selectedContainer), true);
		fireEvent ();
	}
	
	
	/**
	 * @return the resourceNameField
	 */
	public String getSelectedResourceName() {
		return resourceNameField.getText();
	}
	
	public void setAllowExistingResources(boolean value) {
		allowExistingResources = value;
	}
	

	public void setFileExtension(String resourceExtension) {
		this.resourceExtension = resourceExtension;
		updateOutputResource();
	}

	/**
	 * @return
	 */
	public String getResource() {
		String resource = resourceNameField.getText();
		return resource  ;
	}
	
	/**
	 * @return
	 */
	public String getResourceWithFileExtension() {
		String resource = resourceNameField.getText();
		if (resourceExtension==null) return resource;
		return resource + '.' + resourceExtension;
	}
	
	
	/**
	 * @return
	 */
	public String getResourceWithOutFileExtension() {
		String resource = resourceNameField.getText();
		if (resourceExtension==null) return resource;
		return resource;
	}
	
	/**
	 * @param resourcePath
	 * @return
	 */
	protected boolean validateFullResourcePath(IPath resourcePath,Problem pb) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IStatus result = workspace.validatePath(resourcePath.toString(),
				IResource.FOLDER);
		if (!result.isOK()) {
			pb.raiseProblem(result.getMessage(), Problem.PROBLEM_PATH_INVALID);
			return false;
		}

 
		return true;
	}
	
	/**
	 * @return
	 */
	protected boolean validateResourceName(Problem pb) {
		
		String resource = resourceNameField.getText().trim();
		if (resource.length() == 0) {
			pb.raiseProblem(MessageUtil.getString("filename_is_empty"), Problem.PROBLEM_RESOURCE_EMPTY);
			return false;
		}
		
		String resourceName = getResourceWithFileExtension();

		if (!Path.ROOT.isValidPath(resourceName)) {
			pb.raiseProblem(MessageUtil.getString("filename_is_invalid"), Problem.PROBLEM_NAME_INVALID);
			return false;
		}
		return true;
	}
	/**
	 * @return
	 */
	protected boolean validateContainer(Problem pb) {
		if (selectedContainer==null)  {
			pb.raiseProblem(MessageUtil.getString("select_a_folder"), Problem.PROBLEM_RESOURCE_EMPTY);
			return false;
		}
		if (!selectedContainer.exists()) {
			pb.raiseProblem(MessageUtil.getString("folder_does_not_exists"), Problem.FOLDER_PROJECT_DOES_NOT_EXIST);
			return false;
		}
		IPath path = selectedContainer.getFullPath();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectName = path.segment(0);
		if (projectName == null
				|| !workspace.getRoot().getProject(projectName).exists()) {
			pb.raiseProblem(MessageUtil.getString("project_does_not_exist"), Problem.PROBLEM_PROJECT_DOES_NOT_EXIST);
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public Problem validate() {
		Problem pb = new Problem ();
		if (!validateContainer(pb) || !validateResourceName(pb)) {
			return pb;
		}
		IPath path = selectedContainer.getFullPath().append(getResourceWithFileExtension());
		if(!validateFullResourcePath(path,pb)) {
			return pb;
		};
		return null;
	}
	
	
 
}
