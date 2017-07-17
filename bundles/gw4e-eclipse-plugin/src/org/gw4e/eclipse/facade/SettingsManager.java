package org.gw4e.eclipse.facade;

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
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.preferences.PreferenceInitializer;
import org.gw4e.eclipse.preferences.ProjectPropertyChangeListener;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A class to manage the preference settings in Eclipse
 *
 */
public class SettingsManager {
	public static String SEPARATOR = "%";
	static List<ProjectPropertyChangeListener> listeners = new ArrayList<ProjectPropertyChangeListener>();

	/**
	 * @param listener
	 */
	public static void addListener(ProjectPropertyChangeListener listener) {
		listeners.add(listener);
	}

	public static void setM2_REPO() throws JavaModelException, InterruptedException {
		Job job = new WorkspaceJob("GW4E set M2_REPO  Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				// This setting is done in the following pom.xml file :
				// .../test/graphwalker-swtbot-tests/pom.xml
				// It is used to setup the Eclipse environment before
				// running tests.
				// See the Setup method of swtbot tests classes
				IPath path = JavaCore.getClasspathVariable("M2_REPO");
				System.out.println("M2_REPO " + path);
				if (path == null) {
					String pathTorepo = System.getProperty("gw.mvn.repository", null);
					if (pathTorepo!=null) {
						System.out.println("M2_REPO " + pathTorepo);
						path = new Path(pathTorepo);

						JavaCore.setClasspathVariables(new String[] { "M2_REPO" }, new IPath[] { path }, monitor);
						System.out.println("M2_REPO " + pathTorepo + " has been set...");
					} 
				}
			 
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		job.join();
	}

	/**
	 * Remove preferences for the passed project
	 * 
	 * @param project
	 * @param values
	 * @throws BackingStoreException
	 */
	public static void remove(IProject project, String[] values) throws BackingStoreException {
		IEclipsePreferences projectPreferences = getProjectPreference(project);
		for (int i = 0; i < values.length; i++) {
			projectPreferences.remove(values[i]);
		}
		projectPreferences.flush();
	}

	/**
	 * Return the the default preference values for the passed project and key
	 * 
	 * @param project
	 * @param key
	 * @return
	 */
	public static String[] getDefaultGlobalValues(String key) {
		IEclipsePreferences projectPreferences = getGlobalPreference();
		String values = projectPreferences.get(key, "");
		String[] ret = fromString(values);
		return ret;
	}

	/**
	 * Return the the preference values for the passed project and key
	 * 
	 * @param project
	 * @param key
	 * @return
	 */
	public static String[] getValues(IProject project, String key, boolean projectScoped) {
 
		IEclipsePreferences projectPreferences = null;
		if (projectScoped) {
			projectPreferences = getProjectPreference(project);
		} else {
			projectPreferences = getGlobalPreference();
		}
		String values = projectPreferences.get(key, "");
		if ((values == null) || (values.trim().length() == 0) && projectScoped) {
			IEclipsePreferences globalPreferences = getGlobalPreference();
			values = globalPreferences.get(key, "");
			final String gloablValues = values;

			Job job = new WorkspaceJob("GW4E Configure Project Preference Job") {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					SubMonitor subMonitor = SubMonitor.convert(monitor, 60);
					try {
						putValues(project, key, fromString(gloablValues), projectScoped);
						return Status.OK_STATUS;
					} catch (Exception e) {
						ResourceManager.logException(e);
						return Status.CANCEL_STATUS;
					} finally {
						subMonitor.done();
					}
				}
			};
			job.setRule(project);
			job.setUser(true);
			job.schedule();
		}
		String[] ret = fromString(values);
		return ret;
	}

	/**
	 * Set the preference value for the project and the passed key
	 * 
	 * @param project
	 * @param key
	 * @param value
	 */
	public static void putValues(IProject project, String key, String[] values, boolean projectScoped) {
		if (project!= null && ( !project.isAccessible() || !project.exists()) ) return;
		IEclipsePreferences projectPreferences = null;
		if (projectScoped) {
			projectPreferences = getProjectPreference(project);
		} else {
			projectPreferences = getGlobalPreference();
		}

		String[] oldValues = fromString(projectPreferences.get(key, ""));
		try {
			if (values.length > 0) {
				projectPreferences.put(key, toString(values));
				projectPreferences.flush();
				if (projectScoped) {
					for (ProjectPropertyChangeListener listener : listeners) {
						try {
							listener.projectPropertyUpdated(project.getName(), key, oldValues, values);
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}
				}
			} else {
				projectPreferences.remove(key);
				projectPreferences.flush();
			}
		} catch (BackingStoreException e) {
			ResourceManager.logException(e);
		}

	}

	/**
	 * Serialize the array
	 * 
	 * @param values
	 * @return
	 */
	public static String toString(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]).append(SEPARATOR);
		}
		return sb.toString();
	}

	/**
	 * Deserialize the string
	 * 
	 * @param values
	 * @return
	 */
	public static String[] fromString(String values) {
		StringTokenizer st = new StringTokenizer(values, SEPARATOR);
		String[] ret = new String[st.countTokens()];
		int index = 0;
		while (st.hasMoreElements()) {
			String value = (String) st.nextElement();
			ret[index] = value;
			index++;
		}
		return ret;
	}

	/**
	 * Set the preference value for the project and the passed key
	 * 
	 * @param project
	 * @param key
	 * @param value
	 */
	private static IEclipsePreferences getProjectPreference(IProject project) {
		IScopeContext context = new ProjectScope(project);
		IEclipsePreferences projectPreferences = context.getNode(Activator.PLUGIN_ID);
		return projectPreferences;
	}

	/**
	 * Set the global preference value for the passed key
	 * 
	 * @param project
	 * @param key
	 * @param value
	 */
	public static IEclipsePreferences getGlobalPreference() {
		IScopeContext context = ConfigurationScope.INSTANCE;
		IEclipsePreferences projectPreferences = context.getNode(Activator.PLUGIN_ID);
		return projectPreferences;
	}

	/**
	 * @param key
	 * @return Default for the passed key
	 */
	public static String[] getDefautValues(String key) {
		return PreferenceInitializer.getDefautValues(key);
	}
}
