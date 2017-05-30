package org.gw4e.eclipse.menu;

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

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

/**
 * A helper class to enable/disable the Generate Test and Interface menu item. See plugin.xml
 * 
 * <pre>
 * {@code
 * <xml>
 *     <handler
 *            commandId="gw4e-eclipse-plugin.commands.generateSourceCommand"
 *            class="org.gw4e.eclipse.handlers.GenerateSourceHandler">
 *            	   <enabledWhen>
 *	               		<with variable="activeMenuSelection">
 *		               		<iterate ifEmpty="false">
 * 								<test property="org.gw4e.eclipse.menu.isAuthorizedFolderForGraphDefinition" forcePluginActivation="true"/>
 *		               		</iterate>
 *	               		</with>
 *	               </enabledWhen>
 *      </handler> 
 * </xml>
 * }
 * </pre>
 */
public class AuthorizedFolderForGraphDefinition implements IMenuTester {

	/*
	 * Depending on what the end user selected enable or disable the menu item
	 * 
	 * @see org.gw4e.eclipse.menu.IMenuTester#test(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if ("isAuthorizedFolderForGraphDefinition".equals(property)) {
			// Only PackageFragmentRoot is allowed to enable the menu
			// Represents a set of package fragments, and maps the fragments to
			// an underlying resource which is either
			// a folder, JAR, or ZIP file. (Child of IJavaProject)
			// See JDT Plug-in Developer Guide > Programmer's Guide > JDT Core
			// for more information on Java Model
			if (!ResourceManager.isPackageFragmentRoot(receiver)) {
				return false;
			}
			// Lets get the path of hat have been selected in the UI - the
			// complete path
			// a path is something like "src/main/resources"
			String input = ResourceManager.getSelectedPathInProject(receiver);
			// Now we have it, check whether it is listed in the Preference
			boolean authorized = PreferenceManager.isAuthorizedFolderForGraphDefinition(((IPackageFragmentRoot)receiver).getJavaProject().getProject().getName(),input);
			// Sounds like we have an answer now !
			return authorized;
		}
		// Boo !
		return false;
	}

}
