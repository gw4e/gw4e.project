package org.gw4e.eclipse.test.facade;

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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.IJavaProject;
import org.gw4e.eclipse.builder.GW4EBuilder;
import org.gw4e.eclipse.builder.Location;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.ParserContextProperties;
import org.gw4e.eclipse.builder.exception.ParserException;
import org.gw4e.eclipse.facade.MarkerManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.test.fwk.MarkerCondition;
import org.gw4e.eclipse.test.fwk.ProjectHelper;
import org.gw4e.eclipse.test.fwk.WorkbenchHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;
@RunWith(JUnit4.class)
public class MarkerManagerTest extends  TestCase {
	private final static String PROJECT_NAME = "gwproject";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		WorkbenchHelper.resetWorkspace();
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception {
		ProjectHelper.deleteProject(PROJECT_NAME);
	} 

	private ParserException createParserException(String msg) {
		Location location = Location.NULL_LOCATION;
		String message = msg;
		ParserContextProperties p = new ParserContextProperties(new Properties ());
		BuildPolicyConfigurationException bcp = new BuildPolicyConfigurationException(location, message, p);
		ParserException pe = new ParserException (bcp);
		return pe;
	}
	
	 
	private void addMarker(String msg,int severity) throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME,true,false);
		IFile file = (IFile) ResourceManager.getResource(pj.getProject().getFullPath().append("src/test/resources/Simple.json").toString());
		MarkerManager.addMarker(file, this, createParserException(msg), severity);
		MarkerCondition mc = new MarkerCondition(pj.getProject(),GW4EBuilder.MARKER_TYPE, msg, severity);
		Waiter.waitUntil(mc);
	}
	
	@Test
	public void testAddMarker() throws Exception {
		addMarker("test",IMarker.SEVERITY_ERROR);
	}
 
	 

}
