package org.gw4e.eclipse.test.fwk;

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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.gw4e.eclipse.facade.ICondition;

public class MarkerCondition implements ICondition {


	IProject project;
	String expected;
	int severity;
	String type;
	public MarkerCondition(IProject project,String type, String expected, int severity) {
		super();
		this.project = project;
		this.expected = expected;
		this.severity = severity;
		this.type = type;
	}
	
	@Override
	public boolean checkCondition() throws Exception {
		IMarker[] markers = project.findMarkers(type, true, IResource.DEPTH_INFINITE);
		for (IMarker iMarker : markers) {
			if (!iMarker.getType().equals(type)) continue;
			if ((int)iMarker.getAttribute(IMarker.SEVERITY) != severity) continue;
			String msg = (String) iMarker.getAttribute(IMarker.MESSAGE);
			if (expected!=null && !msg.equalsIgnoreCase(expected)) continue;
			return true;
			
		}
		return false;
	}

	@Override
	public String getFailureMessage() {
		return "Marker not found : " + type + " " + expected + " " + severity;
	}

}
