package org.gw4e.eclipse.wizard.convert.model;

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

public class GraphWalkerTestPage {

	boolean graphWalkerAnnotatedTest;
	boolean graphWalkerModelBasedTest;
	String startElement;
	String pathGenerator;
	String  groups;
	public GraphWalkerTestPage(boolean graphWalkerAnnotatedTest, boolean graphWalkerModelBasedTest, String startElement,
			String pathGenerator, String groups) {
		super();
		this.graphWalkerAnnotatedTest = graphWalkerAnnotatedTest;
		this.graphWalkerModelBasedTest = graphWalkerModelBasedTest;
		this.startElement = startElement;
		this.pathGenerator = pathGenerator;
		this.groups = groups;
	}
	
	/**
	 * @return
	 */
	public int testCount () {
		int count = 0;
		if (graphWalkerAnnotatedTest) count++;
		if (graphWalkerModelBasedTest) count++;
		return count;
	}
	
	/**
	 * @return the graphWalkerAnnotatedTest
	 */
	public boolean isGraphWalkerAnnotatedTest() {
		return graphWalkerAnnotatedTest;
	}
	/**
	 * @return the graphWalkerModelBasedTest
	 */
	public boolean isGraphWalkerModelBasedTest() {
		return graphWalkerModelBasedTest;
	}
	/**
	 * @return the startElement
	 */
	public String getStartElement() {
		return startElement;
	}
	/**
	 * @return the pathGenerator
	 */
	public String getPathGenerator() {
		return pathGenerator;
	}
	/**
	 * @return the groups
	 */
	public String getGroups() {
		return groups;
	}
	
 

}
