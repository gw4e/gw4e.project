package org.gw4e.eclipse.container;

import java.io.File;
import java.io.FileNotFoundException;

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

import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.widgets.Display;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

/**
 * Implementation of an Interface of a classpath container. A classpath container provides a way to indirectly reference a set of classpath entries 
 * through a classpath entry of kind CPE_CONTAINER. Typically, a classpath container can be used to describe a complex 
 * library composed of multiple JARs
 *
 */
public class GW4ELibrariesContainer implements IClasspathContainer {
	
	/**
	 * Container id referenced in the plugin.xml file 
	 */
	public static String ID="org.gw4e.eclipse.container.libraries";
	
	/**
	 * The container id 
	 */
	private IPath path;
	
	/**
	 * The associated project 
	 */
	private IJavaProject project; 
 
	
	/**
	 * Will hold the listViewer of libraries that belong to GraphWalker
	 * @param path
	 * @param project
	 */
	public GW4ELibrariesContainer(IPath path, IJavaProject project) {
		super();
		this.path = path;
		this.project = project;
	}
	 
	
	/**
	 * Helper method to check whether this instance of IClasspathContainer is the one representing the GW4E Container
	 * @param who
	 * @return
	 */
	public static boolean isMe (IClasspathEntry who) {
		if(who==null) return false;
		return (who.getPath().toString().startsWith(ID));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
	 */
	@Override
	public IClasspathEntry[] getClasspathEntries() {
		 
		String []  libNames = PreferenceManager.getGraphWalkerJavaLibName( );
		ArrayList<IClasspathEntry> libsList = new ArrayList<IClasspathEntry>();
		for (int i = 0; i < libNames.length; i++) {
			try {
				IPath libPath = new Path(libNames[i]);
				
				IClasspathEntry libEntry = JavaCore.newVariableEntry(libPath, null, null);
				IClasspathEntry entry = JavaCore.getResolvedClasspathEntry(libEntry);
				 
				if (entry==null) {
					System.out.println("************************");
					System.out.println("************************");
					System.out.println("************************");
					System.out.println("Is M2_REPO Variabe set ?");
					System.out.println("************************");
					System.out.println("************************");
					System.out.println("************************");
				}
				libsList.add(entry);
			} catch (Exception e) {
				e.printStackTrace();
				 ResourceManager.logException(e, "Unable to add '" + libNames[i] + "' to the classpath");
			}
		}
        IClasspathEntry[] entriesArray = new IClasspathEntry[libsList.size()];
        return (IClasspathEntry[]) libsList.toArray(entriesArray);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
	 */
	@Override
	public String getDescription() {
		return Constant.GRAPHWALKER_NAME;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
	 */
	@Override
	public int getKind() {
	   return IClasspathContainer.K_APPLICATION;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
	 */
	@Override
	public IPath getPath() {
		return path;
	}

}
