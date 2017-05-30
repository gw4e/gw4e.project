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

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.ICondition;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.facade.Waiter;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.preferences.ProjectPropertyChangeListener;
import org.gw4e.eclipse.test.fwk.ProjectHelper;
import org.gw4e.eclipse.test.fwk.WorkbenchHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class SettingsManagerTest extends TestCase {
	private final static String PROJECT_NAME = "gwproject";
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

	@Test
	public void testAddListener() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		boolean[] notifications = new boolean[] { false };
		SettingsManager.addListener(new ProjectPropertyChangeListener() {
			@Override
			public void projectPropertyUpdated(String projectName, String property, String[] oldValues,
					String[] newValue) {
				notifications[0] = true;
			}
		});
		PreferenceManager.setLogInfoEnabled(pj.getProject().getName(), true);
		assertTrue(notifications[0]);
	}

	@Test
	public void testSetM2_REPO() throws Exception {
		SettingsManager.setM2_REPO();
		IPath path = JavaCore.getClasspathVariable("M2_REPO");
		assertNotNull(path);
	}

	@Test
	public void testRemove() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		String[] values = new String[] { PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION };
		SettingsManager.remove(pj.getProject(), values);

		IScopeContext context = new ProjectScope(pj.getProject());
		IEclipsePreferences projectPreferences = context.getNode(Activator.PLUGIN_ID);
		String val = projectPreferences.get(PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, "");

		assertEquals("", val);
	}

	@Test
	public void testGetValues() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		Waiter.waitUntil(new ICondition () {
			@Override
			public boolean checkCondition() throws Exception {
				String[] values = SettingsManager.getValues(pj.getProject(),
						PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, true);
				return "Impl".equals(values[0]);
			}
			@Override
			public String getFailureMessage() {
				return "invalid value";
			}
		});
	}

	@Test
	public void testPutValues() throws Exception {
		IJavaProject pj = ProjectHelper.getOrCreateSimpleGW4EProject(PROJECT_NAME, false, false);
		String[] values  =  new String[] {"xyz"};
		SettingsManager.putValues(pj.getProject(), PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, values, true);
		String[] vals = SettingsManager.getValues(pj.getProject(),  PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, true);
		Waiter.waitUntil(new ICondition () {
			@Override
			public boolean checkCondition() throws Exception {
				String[] values = SettingsManager.getValues(pj.getProject(),
						PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, true);
				return "xyz".equals(values[0]);
			}
			@Override
			public String getFailureMessage() {
				return "invalid value";
			}
		});
	}

	@Test
	public void testToString() throws Exception {
		String[] values  =  new String[] {"mats","wilander","stephen","edberg","bjorn","borg"};
		String amazingTennisPlayers = SettingsManager.toString(values);
		assertEquals("mats%wilander%stephen%edberg%bjorn%borg%",amazingTennisPlayers);
		
	}

	@Test
	public void testFromString() throws Exception {
		String[] values = SettingsManager.fromString("mats%wilander%stephen%edberg%bjorn%borg%");
		assertEquals("mats",values[0]);
		assertEquals("wilander",values[1]);
		assertEquals("stephen",values[2]);
		assertEquals("edberg",values[3]);
		assertEquals("bjorn",values[4]);
		assertEquals("borg",values[5]);
	}

	@Test
	public void testGetDefautValues() throws Exception {
		String[]  values = SettingsManager.getDefautValues(PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION);
		assertEquals("Impl",values[0]);
	}

}
