package org.gw4e.eclipse.builder;

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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.ParserException;
import org.gw4e.eclipse.facade.MarkerManager;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 * A class to parse build policy files
 *
 */
public class BuildPolicyFileParserImpl extends GW4EParser {
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gw4e.eclipse.builder.GW4EParser#parse(org.eclipse.core
	 * .resources.IFile)
	 */
	@Override
	public void doParse(IFile in) {
		ResourceManager.logInfo(in.getProject().getName(), "Parsing Build Policies File " + in.getFullPath().toOSString());
		removeMarkers(in);
		List<BuildPolicyConfigurationException> exceptions = BuildPolicyManager.validate(in);

		for (BuildPolicyConfigurationException exception : exceptions) {
			MarkerManager.addMarker(
					in, 
					this,
					new ParserException(exception.getLocation(),exception),
					IMarker.SEVERITY_ERROR);
		}
		 
	    BuildPolicyManager.touchImpactedGraphModels (in); 
	}


}
