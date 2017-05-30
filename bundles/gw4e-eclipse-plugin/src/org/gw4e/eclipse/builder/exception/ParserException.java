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

/**
 * A class to represent the parsing exception Contains all things need to report
 * the parsing error
 */
public class ParserException {
	Location location;

	BuildPolicyConfigurationException rootCause;

	public ParserException(Location location, BuildPolicyConfigurationException rootCause) {
		super();
		this.location = location;
		this.rootCause = rootCause;
	}

	public ParserException(BuildPolicyConfigurationException rootCause) {
		this(new Location(-1, -1, -1), rootCause);
	}

	public int getLineNumber() {
		return location.getLineNumber();
	}

	public String getMessage() {
		return rootCause.getMessage();
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return location.getStart();
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return location.getEnd();
	}

	/**
	 * @return
	 */
	public int getProblemId() {
		return rootCause.getProblemId();
	}
	
	
	/**
	 * @return
	 */
	public Properties getAttributes () {
		return rootCause.getAttributes();
	}

}
