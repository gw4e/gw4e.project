package org.gw4e.eclipse.wizard.staticgenerator.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.facade.ResourceManager;

public class GraphElementPage {
	Element [] elements;
	File sourceFileModel;
	String outputName;
	public GraphElementPage(File sourceFileModel, Element[] elements) {
		super();
		this.elements = elements;
		this.sourceFileModel = sourceFileModel;
	}
	
	public File getModelOutputFile (String outputName) throws CoreException, InterruptedException, FileNotFoundException {
		IFile file = ResourceManager.toIFile(sourceFileModel);
		IFolder folder = ResourceManager.ensureFolder(file.getProject(), ".gw4eoutput", new NullProgressMonitor());

		
		IFile outfile = folder.getFile(new Path(outputName));
		InputStream source = new ByteArrayInputStream("".getBytes());
		if (outfile.exists()) {
			outfile.setContents(source, IResource.FORCE, new NullProgressMonitor());
		} else {
			outfile.create(source, IResource.FORCE, new NullProgressMonitor());
		}
		outfile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		long max = System.currentTimeMillis() + 15 * 1000;
		while (true) {
			IFile out = folder.getFile(new Path(outputName));
			if (out.exists()) break;
			if (System.currentTimeMillis() > max) {
				throw new InterruptedException (out.getFullPath() + " does not exist.");
			}
			Thread.sleep(500);
		}
		return ResourceManager.toFile(outfile.getFullPath());
	}

	public Element[] getElements() {
		return elements;
	}
}
