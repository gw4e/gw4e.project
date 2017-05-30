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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.expressions.PropertyTester;
/**
 * A property tester class to enable/disable the menu items. See plugin.xml
 * 
 * <pre>
 * {@code
 * <xml>
 *   <extension
 *         point="org.eclipse.core.expressions.propertyTesters">
 *		 	<propertyTester
 *		         namespace="org.gw4e.eclipse.menu"
 *		       id="org.gw4e.eclipse.menu.GenerateSource.MenuStateTester"
 *		       properties="isAuthorizedFolderForGraphDefinition"
 *		       type="org.eclipse.jdt.core.IPackageFragmentRoot"
 *		       class="org.gw4e.eclipse.menu.MenuStateTester">
 *		     </propertyTester>
 *		 	<propertyTester
 *		         namespace="org.gw4e.eclipse.menu"
 *		       id="org.gw4e.eclipse.menu.ProjectNature.MenuStateTester"
 *		       properties="isGW4ENatureSet,isConvertToEnabled"
 *		       type="java.lang.Object"
 *		       class="org.gw4e.eclipse.menu.MenuStateTester">
 *		     </propertyTester>		     
 *  </extension>
 * </xml>
 * }
 * </pre>
 */
public class MenuStateTester extends PropertyTester {
	/**
	 * Map for indirection execution
	 */
	static Map<String,IMenuTester> testers = new HashMap<String,IMenuTester> ();
	// Load our well known property testers
	static {
		testers.put("isAuthorizedFolderForGraphDefinition", new AuthorizedFolderForGraphDefinition());
		testers.put("isGW4ENatureSet", new GW4ENatureSet());
		testers.put("isConvertToEnabled", new ConvertToEnabled());
		testers.put("isGW4ERunToEnabled", new GW4ERunToEnabled());
		testers.put("isAuthorizedContainerForBuildPoliciesSynchronization", new SynchronizeBuildPoliciesEnabled());
	}

	/* 
	 * Depending on the property tested select the right tester
	 * (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		 
    	IMenuTester tester = testers.get(property);
    	if (tester!=null) {
    		boolean test = tester.test(receiver, property, args, expectedValue);
    		return test;
    	}
    	return false;
    }
	
}
