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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * A contract to define what to do next after having converted a file to another
 * format
 *
 */
public abstract class AbstractPostConversion  {

	protected ResourceContext context;

	protected IFile convertedFile;
	protected String inputFileName;
	protected IFile inputFile;
	
	@SuppressWarnings("unused")
	private AbstractPostConversion() {

	}

	/**
	 * @param context
	 */
	public AbstractPostConversion(ResourceContext context) {
		super();
		this.context = context;
		this.inputFileName = ResourceManager.getSelectedFileLocation(getInputFile());
	}

	/**
	 * Once the source is generated , do whatever you want ... For example :
	 * Reformat it ...
	 * 
	 * @param monitor
	 */
	public  void afterConversion(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 1);
		subMonitor.split(1);
		subMonitor.done();
	}
	
	/**
	 * Return the passed parameters (a folder and a file) as an absolute OS path
	 * path file
	 * 
	 * @param containerFullPath
	 * @param filename
	 * @return
	 */
	public String getSelectedOuputAbsolutePath() {
		return ResourceManager.getSelectedOuputFileLocation(context.getContainerFullPath(),
				context.getSelectedFilename());
	}


	/**
	 * Perform the conversion by calling GraphWalker apis
	 * 
	 * @param monitor
	 * @return
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 * @throws Exception
	 */
	protected String doConversion(IProgressMonitor monitor) throws IOException, CoreException, InterruptedException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		subMonitor.setTaskName("Converting file...");
		String ret = "";
		try {
			String outputFileName = getSelectedOuputAbsolutePath();
			subMonitor.split(20);
			ret = GraphWalkerFacade.convert(inputFileName, outputFileName);
			subMonitor.split(80);
		} finally {
			subMonitor.done();
		}
		return ret;
	}

	/**
	 * @return
	 */
	public String getSelectedFilename() {
		return context.getSelectedFilename();
	}

	/**
	 * @param context
	 *            The conversion context
	 * @throws InterruptedException
	 * @throws CoreException
	 * @throws IOException
	 */
	public List<IFile>  convert(IWorkbenchWindow ww,IProgressMonitor monitor) throws IOException, CoreException, InterruptedException {
		String contentOfConversion=null;
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		subMonitor.setTaskName("Processing file " + this.getInputFile().getName());
		List<IFile> ret;
		try {
			try {
				contentOfConversion = doConversion(subMonitor.split(80));
				if (contentOfConversion==null) {
					throw new Exception(("Unable to convert the file "+this.getInputFile()) );
				}
			} catch (Exception e) {
				ResourceManager.logException(e);
				return null;
			}
			convertedFile = ResourceManager.createFileDeleteIfExists(
																context.getContainerFullPath().toString(),
																getSelectedFilename(), 
																contentOfConversion, 
																subMonitor.split(20));
			
			openEditor(convertedFile, ww);
			ret = new ArrayList<IFile> ();
			ret.add(convertedFile);
		} finally {
			subMonitor.done();
		}
		return ret;
	}

	/**
	 * @param file
	 */
	protected void openEditor(IFile file,String editorid, IWorkbenchWindow aww) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					ResourceManager.closeEditor (file,aww);
				} catch (PartInitException e) {
					ResourceManager.logException(e);
				}
				JDTManager.openEditor(file,editorid,aww);
			}
		});
	}

	protected void openEditor(IFile file,  IWorkbenchWindow aww) {
		openEditor(file,null,aww);
	}
	 
	 
	/**
	 * 
	 */
	public String getQualifiedNameForImplementation () {
		return this.context.getQualifiedNameForImplementation();
	}
	
	 
	
	/**
	 * @return the convertedFile
	 */
	public IFile getConvertedFile() {
		return convertedFile;
	}

	/**
	 * @return the context
	 */
	public ResourceContext getContext() {
		return context;
	}

	/**
	 * @return the inputFile
	 */
	public IFile getInputFile() {
		return context.getSelectedFile();
	}
	
	/**
	 * @param  
	 */
	public ConversionRunnable createConversionRunnable (IWorkbenchWindow ww) {
		return new ConversionRunnable (ww);
	}
	 
	
	public class ConversionRunnable implements IRunnableWithProgress {
		List<IFile> generatedFiles = null;
		IWorkbenchWindow ww;
		public ConversionRunnable (IWorkbenchWindow ww) {
			this.ww=ww;
 
		}
		 
		public void run (IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
			subMonitor.setTaskName("Processing file ");
			try {
				 generatedFiles = convert(ww,subMonitor.split(80));
				 afterConversion(subMonitor.split(20));
			} catch (Exception e) {
				ResourceManager.logException(e);
			} finally {
				subMonitor.done();
			}			 
		}
		/**
		 * @return the generatedFile
		 */
		public List<IFile> getGeneratedFiles() {
			return generatedFiles;
		}
	}
	
	
}
