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

import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

/**
 * A helper class to enable/disable the Convert To menu item. See plugin.xml
 * 
 * <pre>
 * {@code
 * <xml>
 *       <handler
 *             commandId="gw4e-eclipse-plugin.commands.convertToCommand"
 *            class="org.gw4e.eclipse.handlers.ConvertToHandler">
 * 					<enabledWhen>
 * 					    <with variable="selection">
 * 					        <iterate ifEmpty="false">
 * 					           <test property="org.gw4e.eclipse.menu.isConvertToEnabled" forcePluginActivation="true"/>
 * 					        </iterate>
 * 					    </with>
 * 					</enabledWhen> 
 *     </handler> 
 * </xml>
 * }
 * </pre>
 */
public class ConvertToEnabled implements IMenuTester {

	public ConvertToEnabled() {
		super();
	}

	/*
	 * Only specified extension file in the GW4E preference are allowed
	 * The selected file must belong to a GW4E project
	 * (non-Javadoc)
	 * 
	 * @see org.gw4e.eclipse.menu.IMenuTester#test(java.lang.Object,
	 * java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		String extension = ResourceManager.getExtensionFile(receiver);
		if (extension == null)
			return false;
		return PreferenceManager.isConvertable(extension);
	}

}
