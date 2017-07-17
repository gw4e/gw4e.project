package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;

public class JavaApiBasedConverter implements IRunnableWithProgress {
	File outputModelFile;
	File outputjavafile;
	IFile sourceFileModel;
	Element[] elements;
	String executionContext;

	public JavaApiBasedConverter(File outputModelFile, File outputjavafile, IFile sourceFileModel,
			String executionContext, Element[] elements) {
		super();
		this.outputModelFile = outputModelFile;
		this.sourceFileModel = sourceFileModel;
		this.outputjavafile = outputjavafile;
		this.elements = elements;
		this.executionContext = executionContext;
	}
	
	private void delete (IFile file) {
		try {
			file.delete(true, new NullProgressMonitor());
			while (file.exists()) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		subMonitor.setTaskName("Processing file ");
		try {
			File f = ResourceManager.toFile(sourceFileModel.getFullPath());
			String name = outputjavafile.getName().split(Pattern.quote("."))[0];
			String content = GraphWalkerFacade.reduceModel(f, name, elements);
			ResourceManager.write(outputModelFile, content);
			IFile outFile = ResourceManager.toIFile(outputjavafile);
			
			delete (outFile);
			
			IPackageFragment pf = JDTManager.getPackageFragment(outFile);
			boolean pkg = (String.valueOf(pf.getElementName()).trim().length() > 0);
			StringBuffer sb = (pkg ? new StringBuffer("package " + pf.getElementName() + ";\n") : new StringBuffer(""));
			sb.append(GraphWalkerFacade.convert(outputModelFile.getAbsolutePath(), outputjavafile.getAbsolutePath()));
			String source = sb.toString();
			source = source.replaceAll("ExecutionContext", executionContext);
			ResourceManager.write(outputjavafile, source);

			outFile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			Thread.sleep(1000);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						JDTManager.formatUnitSourceCode(outFile, monitor);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
				}
			});

			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(outFile);
			int count = 0;
			CompilationUnit ast = null;
			while (ast == null && count < 10) {
				Thread.sleep(1000);
				ast = JDTManager.parse(cu);
				count++;
			}

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
						JDTManager.openEditor(outFile, ww);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
				}
			});

		} catch (Exception e) {
			throw new InvocationTargetException(e);
		} finally {
			subMonitor.done();
		}
	}

}
