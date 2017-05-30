package org.gw4e.eclipse.refactoring;

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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.gw4e.eclipse.preferences.PreferenceManager;

public class Helper {
	public static IPath buildGeneratedAnnotationValue(IFile in) {
		IPath ret = null;
		if (!PreferenceManager.isGraphModelFile(in))
			return null;
		IProject project = in.getProject();
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		for (int i = 0; i < folders.length; i++) {
			IPath base = project.getFullPath().append(folders[i]);
			if (base.isPrefixOf(in.getFullPath())) {
				ret = in.getFullPath().makeRelativeTo(project.getFullPath());
				break;
			}
		}
		return ret;
	}

	public static IPath buildModelAnnotationValue (IFile in) {
		IPath ret = null;
		if (!PreferenceManager.isGraphModelFile(in)) return null;
		IProject project = in.getProject();
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		for (int i = 0; i < folders.length; i++) {
			IPath base = project.getFullPath().append(folders[i]);
			if (base.isPrefixOf(in.getFullPath())) {
				ret = in.getFullPath().makeRelativeTo(base);
				break;
			}
		}
		return ret;
	}
	
	public static IPath buildUsageValue (IFile in) {
		return buildModelAnnotationValue(in);
	}
	
	public static String getGeneratedAnnotationRegExp (IPath path) {
		String regex = "(@[G]enerated)\\s*\\(\\s*value\\s*=\\s*\"(" + path.toString() + ")\"\\)";
		return regex;
	}
	
	public static String getModelAnnotationRegExp (IPath path) {
		String regex = "(@[M]odel)\\s*\\(\\s*file\\s*=\\s*\"(" + path.toString() + ")\"\\)";
		return regex;
	}
	
	public static String getPathUsageRegExp (IPath path) {
		String regex = "([P]aths\\.get)\\s*\\(\\s*\"" +  path.toString() + "\"\\s*\\)" ;
		return regex;
	}
	 
}
