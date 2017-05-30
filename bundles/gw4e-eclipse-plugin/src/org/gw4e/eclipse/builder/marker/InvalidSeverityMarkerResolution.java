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
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IMarkerResolution;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

/**
 * @author  
 *
 */
public final class InvalidSeverityMarkerResolution extends AbstractBuildPoliciesMarkerResolution {

	String severity;
	String label;
	
	public InvalidSeverityMarkerResolution(String label,String severity) {
		this.severity = severity;
		this.label = label;
	}

	public static IMarkerResolution [] getResolvers () {
		String label = MessageUtil.getString("update_severity_to");
		List<IMarkerResolution> resolvers = new ArrayList<IMarkerResolution>();
		resolvers.add(new InvalidSeverityMarkerResolution(label + " Information","I"));
		resolvers.add(new InvalidSeverityMarkerResolution(label + " Warning","W"));
		resolvers.add(new InvalidSeverityMarkerResolution(label + " Error","E"));
		IMarkerResolution []  ret = new IMarkerResolution [resolvers.size()];
		resolvers.toArray(ret);
		return ret;
	}
	
	@Override
	public String getLabel() {
		return label ;
	}

	@Override
	protected void doRun(IMarker marker,IProgressMonitor monitor) {
		try {
			String key = this.getEntryKey(marker);
			String value = p.getProperty(key);
			String pathGenerator = this.getPathGenerator(marker);
			 
			String sevonError = (String)marker.getAttribute(BuildPolicyConfigurationException.SEVERITY);
			if ((sevonError==null) || (sevonError.trim().length()==0)) {
				String temp = pathGenerator+";"+severity;
				value = value.replace(pathGenerator, temp);
			} else {
				value = value.replace(pathGenerator+";"+sevonError, pathGenerator+";"+severity);
			}
			setValue(key, value);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	
}
