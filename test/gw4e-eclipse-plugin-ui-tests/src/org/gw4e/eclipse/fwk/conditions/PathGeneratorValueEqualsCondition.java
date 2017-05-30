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
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.gw4e.eclipse.facade.ResourceManager;

public class PathGeneratorValueEqualsCondition extends DefaultCondition {
	IFile buildPolicyFile;
	String key;
	String value;
	String currentValue;
	public PathGeneratorValueEqualsCondition(IFile buildPolicyFile,String key,String value) {
		super();
		this.buildPolicyFile = buildPolicyFile;
		this.key = key;
		this.value = value;
	}
	
	@Override
	public boolean test() throws Exception {
		File f = ResourceManager.toFile(buildPolicyFile.getFullPath());
		Properties p = new Properties ();
		InputStream in = new FileInputStream(f);
		try {
			p.load(in);
		} finally {
			if (in!=null) in.close();
		}
		currentValue = p.getProperty(key,"???");
		return currentValue.equalsIgnoreCase(value);
	}

	@Override
	public String getFailureMessage() {
		return  "Unexpected value:'" + currentValue + "' while expecting '" +  value + "' for key '" +  key + "' in " + buildPolicyFile;
	}


}
