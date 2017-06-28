package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
	
	public JavaApiBasedConverter(File outputModelFile, File outputjavafile, IFile sourceFileModel, String executionContext, Element[] elements) {
		super();
		this.outputModelFile = outputModelFile;
		this.sourceFileModel = sourceFileModel;
		this.outputjavafile = outputjavafile;
		this.elements = elements;
		this.executionContext = executionContext;
	}
 
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		subMonitor.setTaskName("Processing file ");
		try {
			File f = ResourceManager.toFile(sourceFileModel.getFullPath());
			String name = outputjavafile.getName().split(Pattern.quote("."))[0];
			String content =  GraphWalkerFacade.reduceModel(f, name, elements);
			ResourceManager.write(outputModelFile, content);
			IFile outFile = ResourceManager.toIFile(outputjavafile);
			IPackageFragment pf = JDTManager.getPackageFragment(outFile);
			boolean pkg = (String.valueOf(pf.getElementName()).trim().length()>0);
			StringBuffer sb = (pkg ? new StringBuffer ("package " + pf.getElementName() + ";\n") : new StringBuffer (""));
			sb.append(GraphWalkerFacade.convert(outputModelFile.getAbsolutePath(), outputjavafile.getAbsolutePath()));
			String source = sb.toString();
			source=source.replaceAll("ExecutionContext", executionContext);
			ResourceManager.write(outputjavafile, source);
			
			JDTManager.formatUnitSourceCode(outFile, new NullProgressMonitor());
			JDTManager.reorganizeImport(JavaCore.createCompilationUnitFrom(outFile));
			IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			JDTManager.openFileEditor(outFile, JavaUI.ID_CU_EDITOR, iww);
		} catch (Exception e) {
			throw new InvocationTargetException (e);
		} finally {
			subMonitor.done();
		}
	}

}
