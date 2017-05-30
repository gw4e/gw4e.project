package org.gw4e.eclipse.builder.exception;

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

import java.util.Properties;

import org.gw4e.eclipse.builder.Location;

public class BuildPolicyConfigurationException extends Exception {

	public static String JAVAFILENAME="javafilename";
	public static String GRAPHMODELPATH="graphmodelfilename";
	public static String BUILDPOLICIESPATH = "buildpolicies";
	public static String PATH_GENERATOR = "pathgenerator";
	public static String SEVERITY = "gwseverity";
	public static String STARTEDGE="graphmodelstartedge";
	 
	ParserContextProperties attributes;
	Location location = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Used when a build policy configuration is found
	 * @param message
	 */
	public BuildPolicyConfigurationException(Location location, String message, ParserContextProperties p) {
		super(message);
		this.location=location;
		this.attributes=p;
	}

	 

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return
	 */
	public int getProblemId () {
		return -1;
	}



	/**
	 * @return the attributes
	 */
	public Properties getAttributes() {
		return attributes;
	}
	
}
