package org.gw4e.eclipse.cheatsheet.manual.test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.ui.wizards.datatransfer.ZipFileImportWizard;
import org.gw4e.eclipse.cheatsheet.Activator;
 

 

public class ProjectImport extends Action implements ICheatSheetAction {
	private List<File> readFiles(String root) throws IOException {
		return Files.walk(Paths.get(root)).filter(path -> !path.equals(Paths.get(root))).map(path -> path.toFile())
				.collect(Collectors.toList());
	}
	
	private static void displayErrorMessage(String title, String msg, Throwable t) {
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				MultiStatus status = createMultiStatus(msg, t);
				ErrorDialog.openError(Display.getDefault().getActiveShell(), title, msg, status);
			}
		});
	}
	
	private static MultiStatus createMultiStatus(String msg, Throwable t) {

		List<Status> childStatuses = new ArrayList<Status>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

		for (StackTraceElement stackTrace : stackTraces) {
			Status status = new Status(IStatus.ERROR, "org.gw4e.eclipse.facade", stackTrace.toString());
			childStatuses.add(status);
		}

		MultiStatus ms = new MultiStatus("org.gw4e.eclipse.facade", IStatus.ERROR,
				childStatuses.toArray(new Status[] {}), t.toString(), t);
		return ms;
	}
	
	public void unzip(String zipFile, String outputFolder){

	     byte[] buffer = new byte[1024];

	     try{
	    	ZipInputStream zis =
	    		new ZipInputStream(new FileInputStream(zipFile));
	    	ZipEntry ze = zis.getNextEntry();
	    	while(ze!=null){
	    	   String fileName = ze.getName();
	           File newFile = new File(outputFolder + File.separator + fileName);
	           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
	            if (!newFile.exists()) {
	            	if (ze.isDirectory()) {
	            			newFile.mkdir();
	            	} else {
	            		newFile.createNewFile();
	    	            FileOutputStream fos = new FileOutputStream(newFile);
	    	            int len;
	    	            while ((len = zis.read(buffer)) > 0) {
	    	       		fos.write(buffer, 0, len);
	    	            }
	    	            fos.close();
	            	}
	            }
	            ze = zis.getNextEntry();
	    	}
	        zis.closeEntry();
	    	zis.close();
	    } catch(Exception ex) {
	    	displayErrorMessage("Error", "Unable to load the project", ex);
	    }
	   }

	public File createTempDirectory() throws IOException {
		final File temp;

		temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		return (temp);
	}

	@Override
	public void run(String[] params, ICheatSheetManager manager) {

		if (params == null || params[0] == null) {
			return;
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		String projectName = params[0];
		String zipName = params[1];
		IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
		IProject newProject = workspace.getRoot().getProject(projectName);
		try {
			newProject.create(newProjectDescription, null);
			newProject.open(null);

			URL url = this.getClass().getClassLoader().getResource(zipName);
			File f = new File(FileLocator.toFileURL(url).getPath());

			IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
				public String queryOverwrite(String file) {
					System.out.println(file);
					return ALL;
				}
			};

			FileSystemStructureProvider provider = FileSystemStructureProvider.INSTANCE;

			File root = createTempDirectory();

			unzip(f.getAbsolutePath(), root.getAbsolutePath());

			List<File> files = readFiles(root.getAbsolutePath());
			ImportOperation importOperation = new ImportOperation(newProject.getFullPath(), root, provider,
					overwriteQuery, files);
			importOperation.setCreateContainerStructure(false);
			importOperation.run(new NullProgressMonitor());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

 
}
