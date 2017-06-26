package org.gw4e.eclipse.wizard.staticgenerator.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

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
	 
}
