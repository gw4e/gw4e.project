package org.gw4e.eclipse.wizard.staticgenerator.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.gw4e.eclipse.facade.ResourceManager;

public class ResourcePage {
	IPath containerFullPath;
	String filename;
	IFile modelFile;
	boolean openEditorCheckbox;
	boolean eraseExistingFile;
	boolean javaApiBasedCheckbox;
	
	public ResourcePage() {
		super();
	}

	public ResourcePage(IPath containerFullPath, String filename, IFile modelFile, boolean openEditorCheckbox,
			boolean eraseExistingFile, boolean javaApiBasedCheckbox) {
		super();
		this.containerFullPath = containerFullPath;
		this.filename = filename;
		this.modelFile = modelFile;
		this.openEditorCheckbox = openEditorCheckbox;
		this.eraseExistingFile = eraseExistingFile;
		this.javaApiBasedCheckbox = javaApiBasedCheckbox;
	}

	public File getOutputFile () throws FileNotFoundException, CoreException, InterruptedException {
		IFolder folder = ResourceManager.getWorkspaceRoot().getFolder(containerFullPath);
		IFile outfile = folder.getFile(new Path(filename));
		InputStream source = new ByteArrayInputStream("".getBytes());
		if (outfile.exists()) {
			outfile.setContents(source, IResource.FORCE, new NullProgressMonitor());
		} else {
			outfile.create(source, IResource.FORCE, new NullProgressMonitor());
		}
		outfile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		long max = System.currentTimeMillis() + 15 * 1000;
		while (true) {
			IFile out = folder.getFile(new Path(filename));
			if (out.exists()) break;
			if (System.currentTimeMillis() > max) {
				throw new InterruptedException (out.getFullPath() + " does not exist.");
			}
			Thread.sleep(500);
		}
		return ResourceManager.toFile(outfile.getFullPath());
		 
	}
	
	public IPath getContainerFullPath() {
		return containerFullPath;
	}

	public String getFilename() {
		return filename;
	}

	public IFile getModelFile() {
		return modelFile;
	}

	public boolean isOpenEditorCheckbox() {
		return openEditorCheckbox;
	}

	public boolean isEraseExistingFile() {
		return eraseExistingFile;
	}

	public boolean isJavaApiBasedCheckbox() {
		return javaApiBasedCheckbox;
	}
	 
}
