package org.gw4e.eclipse.facade;

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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class MavenFacade {
    // TO DO  : Use org.apache.maven.model.io.xpp3.MavenXpp3Writer
	
	// A quick & dirty implementation
	public static void create(IProject project, String groupId, String version, String artifactId, String name, String gwversion) throws IOException, CoreException {
		String newline = System.getProperty("line.separator");
		URL url = IOHelper.class.getResource("pom.xml");
		InputStream input = url.openStream();
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append(newline);
		}
		String data = String.format(sb.toString(), groupId, version, artifactId, name, gwversion);
		IFile pom = project.getFile("pom.xml");
		pom.create(new ByteArrayInputStream(data.getBytes()), true, new NullProgressMonitor());

	}

}
