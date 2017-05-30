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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * A class holding conversion parameters
 *
 */
 
public class ResourceContext {
	
	public static enum GENERATION_MODE {
		  CREATE,
		  APPEND,
		  EXTEND,
	}
	
	/**
	 * The location of the converted file selected by the end user
	 */
	IPath containerFullPath = null;
	/**
	 * The filename chosen by the end user
	 */
	String selectedFilename = null;
	/**
	 * Whether we will open an editor after having converted the file
	 */
	boolean openEditor;
	
	/**
	 * 
	 */
	boolean erase;
	 
	/**
	 * Whether we only generate the test interface
	 */
	boolean generateOnlyInterface = false;

	/**
	 * 
	 */
	IFile selectedFile = null;
	
	/**
	 * 
	 */
	Exception exception ;
	 
	
	/**
	 * 
	 */
	ClassExtension classExtension;
	
	/**
	 * 
	 */
	String startElement;
	
	/**
	 * 
	 */
	IPackageFragmentRoot packageFragmentRoot;
	
	
	/**
	 * 
	 */
	IPackageFragment targetPkg;
	
	/**
	 * 
	 */
	String extendedClassname;
	
	public String getExtendedClassname() {
		return extendedClassname;
	}

	/**
	 * 
	 */
	String interfaceName;
	
	/**
	 * 
	 */	
	GENERATION_MODE mode = GENERATION_MODE.CREATE;
	
	
	public GENERATION_MODE getMode() {
		return mode;
	}

	/**
	 * 
	 * @param containerFullPath
	 * @param selectedFilename
	 * @param openEditor
	 * @param convertedFile
	 * @throws CoreException 
	 */
	public ResourceContext(
			IPath containerFullPath, 
			String selectedFilename, 
			IFile selectedFile , 
			boolean openEditor,
			boolean erase,
			boolean generateOnlyInterface,
			ClassExtension ce) throws CoreException {
		super();
		this.containerFullPath = containerFullPath;
		this.selectedFilename = selectedFilename;
		this.selectedFile=selectedFile;
		this.openEditor = openEditor;
		this.erase = erase;
		this.classExtension=ce;
		this.interfaceName = selectedFile.getName().split("\\.")[0] + ".java";
		IPath p = ResourceManager.getPathWithinPackageFragment(selectedFile).removeLastSegments(1);
	 	p = this.containerFullPath.removeFirstSegments(1); 
		targetPkg  = ResourceManager.getPackageFragment(selectedFile.getProject(),p);
		packageFragmentRoot = ResourceManager.getPackageFragmentRoot(selectedFile.getProject(),targetPkg);
		this.generateOnlyInterface = generateOnlyInterface;
	}
	
	/**
	 * @param containerFullPath
	 * @param packageFragmentRoot
	 * @param targetPkg
	 * @param selectedFilename
	 * @param selectedFile
	 * @param openEditor
	 * @param ce
	 * @throws CoreException
	 */
	public ResourceContext(
			IPath containerFullPath, 
			IPackageFragmentRoot packageFragmentRoot,
			IPackageFragment targetPkg,
			String selectedFilename, 
			String extendedClassname,
			IFile selectedFile,
			GENERATION_MODE mode,
			ClassExtension ce) throws CoreException {
		this.selectedFile=selectedFile;
		this.packageFragmentRoot = packageFragmentRoot;
		this.targetPkg  = targetPkg;
		this.interfaceName = selectedFile.getName().split("\\.")[0] + ".java";
		this.selectedFilename = selectedFilename;
		this.containerFullPath = containerFullPath;
		this.mode=mode;
		this.classExtension=ce;
		this.generateOnlyInterface = false;
		this.openEditor = false;
		this.erase = true;
		this.extendedClassname=extendedClassname; 
	}
	

	/**
	 * @return the containerFullPath
	 */
	public IPath getContainerFullPath() {
		return containerFullPath;
	}
	/**
	 * @return the selectedFilename
	 */
	public String getSelectedFilename() {
		return selectedFilename;
	}
	/**
	 * @return the openEditor
	 */
	public boolean isOpenEditor() {
		return openEditor;
	}
	 
	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * @return the selectedFileorConversion
	 */
	public String getSelectedGraphFileName() {
		return selectedFile.getName();
	}

	/**
	 * @return the erase
	 */
	public boolean isErase() {
		return erase;
	}
	
	 

	/**
	 * @return the classExtension
	 */
	public ClassExtension getClassExtension() {
		return classExtension;
	}



	/**
	 * @return the generateExecutionHook
	 */
	public boolean isGenerateExecutionHook() {
		return classExtension.isGenerateExecutionHook();
	}



	/**
	 * @return the generatePerformance
	 */
	public boolean isGeneratePerformance() {
		return classExtension.isGeneratePerformance();
	}



	/**
	 * @return the generateElementHook
	 */
	public boolean isGenerateElementHook() {
		return classExtension.isGenerateElementHook();
	}



	/**
	 * @return the appendInAnExistingTest
	 */
	public boolean isGenerateRunSmokeTest() {
		return classExtension.isGenerateRunSmokeTest();
	}



	/**
	 * @return the generateRunFunctionalTest
	 */
	public boolean isGenerateRunFunctionalTest() {
		return classExtension.isGenerateRunFunctionalTest();
	}



	/**
	 * @return the generateRunStabilityTest
	 */
	public boolean isGenerateRunStabilityTest() {
		return classExtension.isGenerateRunStabilityTest();
	}

 
	/**
	 * @return the generateRunModelBased
	 */
	public boolean isGenerateRunModelBased() {
		return classExtension.isGenerateRunModelBased();
	}



	/**
	 * @return the selectedFile
	 */
	public IFile getSelectedFile() {
		return selectedFile;
	}



	/**
	 * @return the startElement
	 */
	public String getStartElement() {
		return startElement;
	}
	 
	/**
	 * @return the packageFragmentRoot
	 */
	public IPackageFragmentRoot getPackageFragmentRoot() {
		return packageFragmentRoot;
	}

	/**
	 * @return the targetPkg
	 */
	public IPackageFragment getTargetPkg() {
		return targetPkg;
	}

	/**
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
		return interfaceName;
	}
 
	public String getQualifiedNameForImplementation () {
		return targetPkg.getElementName()+ "." + this.selectedFilename.split("\\.")[0];
	}
	
	/**
	 * @return
	 */
	public boolean isGenerateOnlyInterface() {
		return generateOnlyInterface;
	}
}
