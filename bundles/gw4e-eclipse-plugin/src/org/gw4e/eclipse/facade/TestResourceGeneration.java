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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.conversion.MethodExtension;
import org.gw4e.eclipse.conversion.OfflineMethodExtension;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.OfflineContext;
import org.gw4e.eclipse.wizard.convert.ResourceContext;
import org.gw4e.eclipse.wizard.convert.ResourceContext.GENERATION_MODE;

public class TestResourceGeneration {
	IPackageFragmentRoot implementationFragmentRoot;
	IPackageFragment targetPkg;
	String interfaceName;
	MethodExtension[] methods;
	ClassExtension classExtension;
    IFile graphIFile;
    File graphFile ;
    IJavaProject jproject;
	String filename; // ShoppingCartImpl.java for example
	java.nio.file.Path inputPath; // /Users/xxxx/runtime-New_configuration/test/src/main/resources/ShoppingCart.graphml
	java.nio.file.Path basePath; // /Users/xxxx/runtime-New_configuration/test/src/main/resources
	java.nio.file.Path outputPath; // /Users/xxxx/runtime-New_configuration/test/target/generated-sources
    boolean generateOnlyInterface = false;
    boolean offline = false;
    String extendedClassname;
    public String getExtendedClassname() {
		return extendedClassname;
	}

	ResourceContext.GENERATION_MODE mode;
    List<OfflineContext> offlineContexts =  new ArrayList<OfflineContext>();
    
	public TestResourceGeneration(ResourceContext context) throws FileNotFoundException {
		super();
		this.implementationFragmentRoot = context.getPackageFragmentRoot();
		this.targetPkg = context.getTargetPkg();
		this.jproject = this.targetPkg.getJavaProject();
		this.interfaceName = context.getInterfaceName();
		this.filename = context.getSelectedFilename();
		this.classExtension = context.getClassExtension();
		this.generateOnlyInterface = context.isGenerateOnlyInterface();
		this.methods = PreferenceManager.getMethodExtensionsToAddToTestImplementation(context);
		this.extendedClassname=context.getExtendedClassname();
		// The chosen graph from which we want to generates interface
		this.graphFile = ResourceManager.toFile(context.getSelectedFile().getFullPath());
		this.inputPath = graphFile.toPath();

		// The base file , which will help to define the java package into
		// which the generated interface will belong to
		IPath base = PreferenceManager.computeBaseTargetFolderForGeneratedTests(context.getSelectedFile());
		this.basePath = ResourceManager.toPath(base);

		// Target Interface folder
		IProject project = context.getSelectedFile().getProject();
		
		boolean isMain = PreferenceManager.isInMainPath(context.getSelectedFile().getFullPath());
		IPath pathFolderForTestInterface = project.getFullPath()
				.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(),isMain));
		this.outputPath = ResourceManager.toPath(pathFolderForTestInterface);
		this.graphIFile = context.getSelectedFile();
		this.mode = context.getMode();
	}
	
	public IJavaProject getJavaProject () {
		return this.jproject;
	}
	 
	public static void getProjectClassPath(IJavaProject project, List<File> dst) throws Exception {
		IRuntimeClasspathEntry [] rentries = JavaRuntime.computeUnresolvedRuntimeClasspath(project);
		for (IRuntimeClasspathEntry entry : rentries) {
			switch (entry.getType()) {
			case IClasspathEntry.CPE_SOURCE: 
				break;
			case IClasspathEntry.CPE_PROJECT:
				break;
			case IClasspathEntry.CPE_LIBRARY:
				break;
			case IClasspathEntry.CPE_VARIABLE:
				// JRE like entries
				IRuntimeClasspathEntry [] variableEntries  = JavaRuntime.resolveRuntimeClasspathEntry(entry, project);
				break;
			case IClasspathEntry.CPE_CONTAINER:
				IRuntimeClasspathEntry [] containerEntries  = JavaRuntime.resolveRuntimeClasspathEntry(entry, project);
				for (IRuntimeClasspathEntry containerentry : containerEntries) {
					dst.add(new File (containerentry.getLocation()));
				}
				break;
			default:
				throw new Exception("unsupported classpath entry "+entry);
			}
		}
	}
	

	
	public void updateWithOfflines () {
		int max  = offlineContexts.size();
		String name = PreferenceManager.getOfflineTestMethodName ();
		MethodExtension[] temp = new MethodExtension [methods.length+max];
		System.arraycopy(methods, 0, temp, 0, methods.length);
		for (int i = 0; i < max; i++) {
			OfflineMethodExtension ome =  new OfflineMethodExtension(getClassName(), new ArrayList(), (name + "_" + (System.currentTimeMillis() + i)), null, offlineContexts.get(i));
			temp[methods.length+i] = ome;
		}
		methods = temp;
	}
	
	public boolean isExtendSource() {
		return mode == GENERATION_MODE.EXTEND;
	}
	
	public boolean isAppendSource() {
		return mode == GENERATION_MODE.APPEND;
	}

	public boolean isCreateSource() {
		return mode == GENERATION_MODE.CREATE;
	}
	
	public void addOfflineContext (OfflineContext context) {
		offlineContexts.add(context);
	}
	
	public File getGraphFile () {
		return graphFile;
	}
	
	public IFile getGraphIFile () {
		return graphIFile;
	}

	public boolean isGenerateOnlyInterface() {
		return generateOnlyInterface;
	}
	
	 

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	/**
	 * @return
	 */
	public String getClassName() {
		return this.filename.split(Pattern.quote("."))[0];
	 
	}

	
	private String getSuffix () {
		if (offline) {
			return PreferenceManager.suffixForTestOfflineImplementation(implementationFragmentRoot.getJavaProject().getProject().getName()); 
		}
		return PreferenceManager.suffixForTestImplementation(implementationFragmentRoot.getJavaProject().getProject().getName());
	}
	
	/**
	 * @return
	 */
	public String getInterfaceName() {
		String temp = this.interfaceName;
		int pos = temp.indexOf(".java");
		if (pos == -1)
			return null;
		temp = temp.substring(0, pos);
		return temp;
	}

	/**
	 * @return
	 */
	public IFile toIFile() {
		File folder = targetPkg.getResource().getLocation().toFile();
		String javaSourceFile = getClassName() + ".java";
		File file = new File(folder, javaSourceFile);
		IFile ifile = ResourceManager.toIFile(file);
		return ifile;
	}

	public IFile getExtendedClassIFile() {
		File folder = targetPkg.getResource().getLocation().toFile();
		String javaSourceFile = this.getExtendedClassname() + ".java";
		File file = new File(folder, javaSourceFile);
		IFile ifile = ResourceManager.toIFile(file);
		return ifile;
	}
	
	/**
	 * @return the implementationFragmentRoot
	 */
	public IPackageFragmentRoot getImplementationFragmentRoot() {
		return implementationFragmentRoot;
	}

	/**
	 * @return the targetPkg
	 */
	public IPackageFragment getTargetPkg() {
		return targetPkg;
	}

	/**
	 * @return the elementName
	 */
	public String getElementName() {
		return interfaceName;
	}

	/**
	 * @return the runMode
	 */
	public String getRunMode() {
		return classExtension.getPathGenerator();
	}

	/**
	 * @return the startNode
	 */
	public String getStartNode() {
		return classExtension.getAnnotationStartElement();
	}

	public MethodExtension[] getMethods() {
		return methods;
	}

	/**
	 * @return the classExtension
	 */
	public ClassExtension getClassExtension() {
		return classExtension;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the inputPath
	 */
	public java.nio.file.Path getInputPath() {
		return inputPath;
	}

	/**
	 * @return the basePath
	 */
	public java.nio.file.Path getBasePath() {
		return basePath;
	}

	/**
	 * @return the outputPath
	 */
	public java.nio.file.Path getOutputPath() {
		return outputPath;
	}

	/**
	 * @return the additionalContexts
	 */
	public List<IFile> getAdditionalContexts() {
		return classExtension.getAdditionalContexts();
	}

}
