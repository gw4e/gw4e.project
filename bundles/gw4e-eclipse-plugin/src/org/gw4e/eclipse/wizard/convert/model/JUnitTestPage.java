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

import java.util.List;

import org.eclipse.core.resources.IFile;

public class JUnitTestPage {
	boolean junitSmokeTest;
	boolean junitStabilityTest;
	boolean junitFuncitonalTest;
	String 	targetVertex;
	String startElement;
	List<IFile> additionalContext;
	
	public JUnitTestPage(boolean junitSmokeTest, boolean junitStabilityTest, boolean junitFuncitonalTest,
			String 	targetVertex,String startElement, List<IFile> additionalContext) {
		super();
		this.junitSmokeTest = junitSmokeTest;
		this.junitStabilityTest = junitStabilityTest;
		this.junitFuncitonalTest = junitFuncitonalTest;
		this.additionalContext = additionalContext;
		this.targetVertex = targetVertex;
		this.startElement = startElement;
	}
	
	/**
	 * @return
	 */
	public int testCount () {
		int count = 0;
		if (junitSmokeTest) count++;
		if (junitStabilityTest) count++;
		if (junitFuncitonalTest) count++;
		return count;
	}
	
	
	/**
	 * @return the junitSmokeTest
	 */
	public boolean isJunitSmokeTest() {
		return junitSmokeTest;
	}
	/**
	 * @return the junitStabilityTest
	 */
	public boolean isJunitStabilityTest() {
		return junitStabilityTest;
	}
	/**
	 * @return the junitFuncitonalTest
	 */
	public boolean isJunitFuncitonalTest() {
		return junitFuncitonalTest;
	}
	/**
	 * @return the additionalContext
	 */
	public List<IFile> getAdditionalContext() {
		return additionalContext;
	}

	/**
	 * @return the targetVertex
	 */
	public String getTargetVertex() {
		return targetVertex;
	}

	/**
	 * @return the startElement
	 */
	public String getStartElement() {
		return startElement;
	}
	
 

}
