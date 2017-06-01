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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.gw4e.eclipse.builder.InitialBuildPolicies;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

public abstract class TemplateProvider {



	static List<TemplateProvider> templates = null;

	String  label;
	String[] resources;
	String[] targetFiles;
	
	private static void loadTemplate () {
		templates = new ArrayList<TemplateProvider>();
		templates.add(new NoneTemplate());
		templates.add(new EmptyTemplate ());
		templates.add(new SimpleTemplate());
		templates.add(new SimpleTemplateWithScript());
		templates.add(new SharedTemplate());
 	}
	
	public static List<TemplateProvider> getTemplates() {
		if (templates==null) {
			loadTemplate ();
		}
		return templates;
	}
	
	public static List<TemplateProvider> getActiveTemplates() {
		List<TemplateProvider> ret = new ArrayList<TemplateProvider> ();
		for (TemplateProvider templateProvider : getTemplates()) {
			if (templateProvider.isNoneProvider()) continue;
			ret.add(templateProvider);
		}
		return ret;
	}
	
	public abstract String getLabel ();
	
	public abstract String getDefaultFileName ();

	public TemplateProvider(String label, String resource) {
		super();
		this.label =  label ;
		this.resources = new String[] { resource };
	}

	public TemplateProvider(String label, String[] resources) {
		super();
		this.label = label;
		this.resources = resources;
	}
	
	public boolean isNoneProvider() {
		return false;
	}
	
	public void setTargetFiles(String[] targetFiles) {
		this.targetFiles = targetFiles;
	}
	
	public String[] getResources() {
		return resources;
	}
	
	protected InputStream getInputStream(String resource) throws IOException {
		java.net.URL url = this.getClass().getResource(resource);
		return url.openStream();
	}

	protected String getData(String resource) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(getInputStream(resource)));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
		} catch (Exception ex) {
			return null;
		} finally {
			if (in != null)
				in.close();
		}
		
		return sb.toString();
	}

	private IFile hanldeExistingFile (IFile theFileToCreate) {
		IFile temp = theFileToCreate;
		int i=0;
		while (temp.exists()) {
			String name  = theFileToCreate.getName().substring(0,theFileToCreate.getName().indexOf(".")); 
			
			temp = ((IFolder)theFileToCreate.getParent()).getFile(name + i + "." + theFileToCreate.getFileExtension());
			System.out.println(" hanldeExistingFile >"+ name + "< temp " + temp.getFullPath()); 
			i++;
		}
		return temp;
	}
	
	public IFile create(IFolder folder, String resource, String targetFile,IProgressMonitor monitor) throws CoreException, IOException {
		String target = targetFile;
		if ( (target == null) || (target.trim().length() == 0 )) target = resource;
		IFile theFileToCreate = hanldeExistingFile(folder.getFile(target));
		String data = getData(resource); 
		if (data == null)
			return null;
		data = data.replace(new StringBuffer (resource), new StringBuffer (target));

		System.out.println("folder " + folder + " target " + target + " resource " + resource + " targetFile " + targetFile ); 
		ResourceManager.save(theFileToCreate, data, monitor);
		return theFileToCreate;
	}

	public IRunnableWithProgress createResourceOperation(IProject project,IFolder folder,InitialBuildPolicies policies,String filename) {
		if (filename!=null && filename.trim().length()>0) {
			setTargetFiles(new String [] {filename});
		} else {
			setTargetFiles (getResources());
		}
		return createResourceOperation(folder,policies);
	}

	List<IFile> createdResources = new ArrayList<IFile>();
	
	public void addCreatedResources (IFile file) {
		createdResources.add(file);	
	}
	
	protected IRunnableWithProgress createResourceOperation(IFolder folder,InitialBuildPolicies policies) {
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor) {
				String resource = "";
				String targetFile = "";
				try {
					for (int i = 0; i < resources.length; i++) {
						resource = resources[i];
						targetFile =  targetFiles[i];
						IFile file =  create(folder, resource, targetFile, monitor);
						if (file != null) {
							createdResources.add(file);
							policies.setFile(file);
							policies.run();
							BasicNewResourceWizard.selectAndReveal(file,
									PlatformUI.getWorkbench().getActiveWorkbenchWindow());
						}
					}			
				} catch (Exception exception) {
					ResourceManager.logException(exception, "Unable to create " + resource);
				} finally {
					monitor.done();
				}
			}
		};
		return operation;
	}

 
	public boolean openInEditor(IWorkbench workbench) {
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = workbenchWindow.getActivePage();

		for (IFile iFile : createdResources) {
			try {
				page.openEditor(new FileEditorInput(iFile),
						PreferenceManager.getGW4EEditorName());
			} catch (PartInitException exception) {
				ResourceManager.logException(exception, "Unable to open " + iFile);
				return false;
			}
		}
		final IWorkbenchPart activePart = page.getActivePart();
		if (activePart instanceof ISetSelectionTarget) {
			for (IFile iFile : createdResources) {
				final ISelection targetSelection = new StructuredSelection(iFile);
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						((ISetSelectionTarget) activePart).selectReveal(targetSelection);
					}
				});
			}
		}
		createdResources.clear();
		return true;
	}
}
