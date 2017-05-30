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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IMarkerResolution;
import org.gw4e.eclipse.facade.ResourceManager;

/**
 *  
 *
 */
public final class InvalidPathGeneratorMarkerResolution extends AbstractBuildPoliciesMarkerResolution {
	ResolutionMarkerDescription resolutionMarkerDescription;
	
	public static IMarkerResolution [] getResolvers () {
		List<IMarkerResolution> resolvers = new ArrayList<IMarkerResolution>();
		List<ResolutionMarkerDescription> resolutionMarkerDescriptions = PathGeneratorDescription.getDescriptions();
		Iterator<ResolutionMarkerDescription> iter = resolutionMarkerDescriptions.iterator();
		while (iter.hasNext()) {
			ResolutionMarkerDescription resolutionMarkerDescription = (ResolutionMarkerDescription) iter.next();
			resolvers.add(new InvalidPathGeneratorMarkerResolution(resolutionMarkerDescription));
		}
		IMarkerResolution []  ret = new IMarkerResolution [resolvers.size()];
		resolvers.toArray(ret);
		return ret;
	}
	
	public InvalidPathGeneratorMarkerResolution(ResolutionMarkerDescription resolutionMarkerDescription) {
		this.resolutionMarkerDescription=resolutionMarkerDescription;
	}
	
	public String getLabel() {
		return resolutionMarkerDescription.toString();
	}

	/**
	 * @return the generatorpath
	 */
	public String getTargetGeneratorPath() {
		return resolutionMarkerDescription.getGenerator();
	}
	

	@Override
	protected void doRun(IMarker marker,IProgressMonitor monitor) {
		try {
			String key = this.getEntryKey(marker);
			String value = p.getProperty(key);
			String pathGenerator = this.getPathGenerator(marker);
			value = value.replace(pathGenerator, this.getTargetGeneratorPath());
			this.setValue(key, value);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}
	
}
