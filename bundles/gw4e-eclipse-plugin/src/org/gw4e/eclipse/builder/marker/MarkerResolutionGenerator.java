/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.gw4e.eclipse.builder.marker;

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
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.gw4e.eclipse.builder.GW4EParser;
import org.gw4e.eclipse.facade.ResourceManager;
 

 
/**
 * A resolution class to resolve markers set by the GW4E builder (
 * Help to present Quick Fix in the problem view
 *
 */
public class MarkerResolutionGenerator implements IMarkerResolutionGenerator2 {

	static Map<Integer, IMarkerResolution[]> resolvers = new HashMap<Integer, IMarkerResolution[]>();
	static {
		resolvers.put(GW4EParser.MISSING_BUILD_POLICIES_FILE, new IMarkerResolution[] { new MissingBuildPoliciesFileMarkerResolution() });
		resolvers.put(GW4EParser.MISSING_POLICIES_FOR_FILE, new IMarkerResolution[] { new MissingPoliciesForFileMarkerResolution() , new SetSyncPoliciesForFileMarkerResolution(), new SetNoCheckPoliciesForFileMarkerResolution() });
		resolvers.put(GW4EParser.INVALID_PATH_GENERATOR , InvalidPathGeneratorMarkerResolution.getResolvers() );
		resolvers.put(GW4EParser.INVALID_SEVERITY, InvalidSeverityMarkerResolution.getResolvers() );
		resolvers.put(GW4EParser.INVALID_UNEXISTING_GRAPHFILE, new IMarkerResolution[] { new UnexistingGraphFileMarkerResolution() });
		resolvers.put(GW4EParser.INVALID_ANNOTATION_PATH_GENERATOR, InvalidAnnotationPathGeneratorMarkerResolution.getResolvers());
		resolvers.put(GW4EParser.INVALID_PATH_MODEL_GENERATED, new IMarkerResolution[0]);
		resolvers.put(GW4EParser.INVALID_START_EDGE, new IMarkerResolution[0]);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		Object attr;
		try {
			attr = marker.getAttribute(IJavaModelMarker.ID);
			IMarkerResolution[] resolutions = resolvers.get(attr);
			return resolutions;
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
		return new IMarkerResolution[0];
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public boolean hasResolutions(IMarker marker) {
		try {
			Object attr = marker.getAttribute(IJavaModelMarker.ID);
			return resolvers.get(attr) != null && resolvers.get(attr).length > 0;
		} catch (CoreException e) {
			return false;
		}
	}

	static void printAttributes (IMarker marker) {
		try {
			Map<String, Object> attributes = marker.getAttributes();
			Iterator iter = attributes.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				ResourceManager.logInfo(marker.getResource().getProject().getName(), key + " " + String.valueOf(attributes.get(key)));
			}
		} catch (CoreException e) {
			 ResourceManager.logException(e);
		}
	}
}
