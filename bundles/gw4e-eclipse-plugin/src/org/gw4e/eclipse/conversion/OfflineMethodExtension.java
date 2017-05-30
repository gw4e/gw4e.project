package org.gw4e.eclipse.conversion;

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

import java.util.List;

import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.wizard.convert.OfflineContext;

public class OfflineMethodExtension extends MethodExtension {
	OfflineContext offline;
	public OfflineMethodExtension(String classname, List<String> additionalContexts, String name, String startElement, OfflineContext offline) {
		super(classname, additionalContexts, name, startElement);
		this.offline=offline;
	}

	@Override
	public String getSource(String[] additionalContext, String value) {
		String newline = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer ();
		sb.append("@Test ").append(newline);
		
		sb.append("public void "+ this.getName() + "()  {").append(newline);
		sb.append("System.out.println(\"Generated with : ").append(offline.getGenerator()).append("\");").append(newline);
		List<String> methodNames = offline.getMethodNames();
		for (String methodName : methodNames) {
			if (Constant.START_VERTEX_NAME.equalsIgnoreCase(methodName)) continue;
			sb.append(methodName).append("();").append(newline);;
		}
		sb.append("}").append(newline);
		return sb.toString();
	}

	@Override
	public String[] getImportedClasses() {
		String[] ret = new String[] { 
			org.junit.Test.class.getName(),
		};
		return ret;
	}

	 
}
