package org.gw4e.eclipse.fwk.project;

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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.source.ImportHelper;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class PetClinicProject {

	public static GW4EProject create (SWTWorkbenchBot bot,String gwproject) throws CoreException, FileNotFoundException  {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();
		
		File path = new File("src/test/resources/petclinic");
		IContainer destFolder = (IContainer) ResourceManager.getResource(gwproject+ "/src/test/resources");
		ImportHelper.copyFiles(path,destFolder);
		
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		String[] temp = new String[2];
		temp[0] = gwproject;
		temp[1] = folders[1];
		project.generateSource(temp);
		bot.waitUntil(new EditorOpenedCondition(bot, "VeterinariensSharedStateImpl.java"), 3 * 60000);
		project.waitForBuildAndAssertNoErrors();
		return project;
	}

	public static IFile getVeterinariensSharedStateImplFile (String gwproject) {
		IPath path = new Path (gwproject).append("/src/test/java").append("com/company").append("VeterinariensSharedStateImpl.java");
		return (IFile)ResourceManager.getResource(path.toString());
	}
	
	public static IPath getCorrespondingGraphMlFile (ICompilationUnit cu) {
		String location = JDTManager.getGW4EGeneratedAnnotationValue(cu,"value");
 		IPath path = new Path (cu.getJavaProject().getProject().getName()).append(location);
 		return path;
	}
	
	
}
