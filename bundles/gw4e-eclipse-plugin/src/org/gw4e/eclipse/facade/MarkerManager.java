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

import java.util.Iterator;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.builder.exception.ParserException;

public class MarkerManager   {

	/**
	 * Add a marker in the problem view for the passed file
	 * 
	 * @param file
	 * @param e
	 * @param severity
	 * @param problemid
	 * @throws CoreException
	 */
	public static void addMarker(IFile file, Object owner, ParserException e, int severity) {
		try {
			IMarker marker = file.createMarker(GW4EBuilder.MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, e.getMessage());
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IJavaModelMarker.ID, e.getProblemId());
			marker.setAttribute(IMarker.CHAR_START, e.getStart());
			marker.setAttribute(IMarker.CHAR_END, e.getEnd());
			marker.setAttribute(IMarker.LINE_NUMBER, e.getLineNumber());
			marker.setAttribute(IMarker.SOURCE_ID, owner.getClass().getName());
			Properties p = e.getAttributes();
			Iterator iter = p.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				marker.setAttribute(key, p.get(key));
			}
		} catch (Exception e1) {
			ResourceManager.logException(e1);
		}
	}

	 
}
