package org.gw4e.eclipse.fwk.conditions;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.gw4e.eclipse.facade.ResourceManager;

public class PropertyValueCondition extends DefaultCondition {
	IFile buildPolicyiFile;
	File buildPolicyFile;
	String key;
	String expectedValue;
	String value;
	public PropertyValueCondition(IFile buildPolicyiFile,String key,String expectedValue) throws FileNotFoundException {
		this.buildPolicyiFile=buildPolicyiFile;
		this.buildPolicyFile = ResourceManager.toFile(buildPolicyiFile.getFullPath());
		this.key = key;
		this.expectedValue=expectedValue;
	}

	@Override
	public boolean test() throws Exception {
		Properties p = new Properties ();
		FileInputStream fis = new FileInputStream(buildPolicyFile);
		try {
			p.load(fis);
			value =  p.getProperty(key,null);
			return expectedValue.equalsIgnoreCase(value);
		} finally {
			if (fis!=null) {
				fis.close();
			}
		}
	}

	@Override
	public String getFailureMessage() {
		return "Expected (key,value) : (" + key + "," + expectedValue + ") but found (" + key + "," + value + ") in " + buildPolicyFile;
	}

}
