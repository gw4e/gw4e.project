package org.gw4e.eclipse.facade;

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

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.message.MessageUtil;

public class TestConvertor {
	ICompilationUnit testInterface;
	IFile oldGraphFile;
	IFile newGraphFile;
	String model;
	public TestConvertor(ICompilationUnit unit) {
		this.testInterface = unit;
	}

	public void apply() throws CoreException {
		Job job = new WorkspaceJob("GW4E Conversion Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					_apply(monitor);
				} catch (Exception e) {
					DialogManager.displayErrorMessage(MessageUtil.getString("project_conversion"), MessageUtil.getString("an_error_has_occured_while_configuring_the_project"), e);
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(testInterface.getJavaProject().getProject());
		job.setUser(true);
		job.schedule();
	}

 
	public void _apply(IProgressMonitor monitor) throws CoreException, IOException, MalformedTreeException, BadLocationException {
		findOldGraphFile(monitor);
		if (oldGraphFile==null) {
			return;
		}
 		setNewGraphFile(monitor);
 		if (newGraphFile.exists()) {
 			return;
 		}
		createNewGraphFileFromOldGraphFile(monitor);
		updateCompilationUnitWithNewGraphFile(monitor);
		updateTests(findTests(monitor),monitor);
		
		Job job = new WorkspaceJob("GW4E Completing Coversion Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					IFile buildFile = BuildPolicyManager.createBuildPoliciesFile(oldGraphFile,monitor);
					BuildPolicyManager.addNoCheckPolicies(oldGraphFile,monitor);
					BuildPolicyManager.addSyncPolicies(newGraphFile,monitor);
				} catch (Exception e) {
					DialogManager.displayErrorMessage(MessageUtil.getString("project_conversion"), MessageUtil.getString("an_error_has_occured_while_configuring_the_project"), e);
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(oldGraphFile.getProject());
		job.setUser(true);
		job.schedule();
		
	}

	private void updateTests(List<ICompilationUnit> findTests,IProgressMonitor monitor) throws MalformedTreeException, BadLocationException, CoreException {
		for (ICompilationUnit iCompilationUnit : findTests) {
			AnnotationParsing ap = JDTManager.findAnnotationParsingInGeneratedAnnotation(iCompilationUnit, "value");
			if (ap.getAnnotations().size()==0) {
				JDTManager.addGeneratedAnnotation((IFile)iCompilationUnit.getResource(), newGraphFile, monitor);
			}
		}
	}

	private List<ICompilationUnit> findTests(IProgressMonitor monitor) throws JavaModelException {
		List<ICompilationUnit> units =  new ArrayList<ICompilationUnit>();
		ITypeHierarchy th = testInterface.findPrimaryType().newTypeHierarchy(testInterface.getJavaProject(),monitor);
		IType[] types = th.getImplementingClasses(testInterface.findPrimaryType());
		for (int i = 0; i < types.length; i++) {
			units.add(types[i].getCompilationUnit());
		}
		return units;
	}

	private void updateCompilationUnitWithNewGraphFile(IProgressMonitor monitor) throws CoreException, IOException {
		String newModel = this.model.substring(0,this.model.lastIndexOf("/")) + "/" + newGraphFile.getName();
		IOHelper.replace((IFile)testInterface.getResource(), this.model, newModel);
		newGraphFile.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	private void createNewGraphFileFromOldGraphFile(IProgressMonitor monitor) throws IOException, CoreException {
		String inputFileName = ResourceManager.getSelectedFileLocation(oldGraphFile);
		String outputFileName = ResourceManager.getSelectedOuputFileLocation(newGraphFile.getFullPath().removeLastSegments(1), newGraphFile.getName());
 		String ret = GraphWalkerFacade.convert(inputFileName, outputFileName);
 		newGraphFile.create(new ByteArrayInputStream (ret.getBytes()), true, monitor);
	}

	private void setNewGraphFile(IProgressMonitor monitor) throws CoreException {
		String extension = oldGraphFile.getFileExtension();
		int pos  = oldGraphFile.getName().indexOf(extension);
		String name = oldGraphFile.getName().substring(0,pos);
		IPath path = oldGraphFile.getFullPath().removeLastSegments(1);
		IFolder f  =  (IFolder) ResourceManager.getResource(path.toString());
		newGraphFile = f.getFile(name + "json");
	}

	private void findOldGraphFile(IProgressMonitor monitor) throws JavaModelException, FileNotFoundException {
		AnnotationParsing ap = JDTManager.findAnnotationParsingInModelAnnotation(testInterface, "file");
		this.model = ap.getValue("file");
		IPackageFragmentRoot[] roots = testInterface.getJavaProject().getPackageFragmentRoots();
		for (int j = 0; j < roots.length; j++) {
			IPackageFragmentRoot root = roots[j];
			IFolder resource = (IFolder) root.getResource();
			if (resource==null) throw new FileNotFoundException (model);
			IFile graphFile = resource.getFile(model);
			if (graphFile.exists()) {
				oldGraphFile = graphFile;
				break;
			}
		}
	}
	
	 
}
