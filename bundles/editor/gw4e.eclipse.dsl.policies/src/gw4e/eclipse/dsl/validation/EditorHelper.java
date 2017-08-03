package gw4e.eclipse.dsl.validation;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.gw4e.eclipse.facade.ResourceManager;

import gw4e.eclipse.dsl.dSLPolicies.GraphPolicies;
import gw4e.eclipse.dsl.dSLPolicies.StopCondition;

public class EditorHelper {
	
	public static String getModelFileName (StopCondition condition) {
		EObject object = condition.eContainer();
		String modelFile = null;
		while (object !=null && object.eContainer() != null) {
			if ((object.eClass().getInstanceClassName().equals(GraphPolicies.class.getName()))) {
				GraphPolicies  ap = (GraphPolicies) object ;
				modelFile = ap.getGraphModelPolicies();
			}
			object = object.eContainer();
		}	
		return modelFile;
	}
	
	public static File getEditedFileFolder() {
		IWorkbenchPage page = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			if (windows[i] != null) {
				IWorkbenchWindow window = windows[i];
				page = windows[i].getActivePage();
				if (page != null)
					break;
			}
		}
		IEditorPart part = page.getActiveEditor();
		FileEditorInput editor = (FileEditorInput) part.getEditorInput();
		IFile file = editor.getFile();
		IFolder folder = (IFolder) file.getParent();
		File f = null;
		try {
			f = ResourceManager.toFile(folder.getFullPath());
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
		}
		return f;
	}
	
	public static File getEditedFile() {
		IWorkbenchPage page = null;
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			if (windows[i] != null) {
				IWorkbenchWindow window = windows[i];
				page = windows[i].getActivePage();
				if (page != null)
					break;
			}
		}
		IEditorPart part = page.getActiveEditor();
		FileEditorInput editor = (FileEditorInput) part.getEditorInput();
		IFile file = editor.getFile();
		File f = null;
		try {
			f = ResourceManager.toFile(file.getFullPath());
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
		}
		return f;
	}
}
