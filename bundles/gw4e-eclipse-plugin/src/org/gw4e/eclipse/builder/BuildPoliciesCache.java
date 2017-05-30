package org.gw4e.eclipse.builder;

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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.preferences.ProjectPropertyChangeListener;

public class BuildPoliciesCache implements ProjectPropertyChangeListener {

	IFile resource;

	/**
	 * @param resource
	 * @throws CoreException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	public BuildPoliciesCache(IFile resource) throws CoreException, FileNotFoundException, InterruptedException {
		this.resource = resource;
	}

	/**
	 * 
	 */
	public BuildPoliciesCache() {
	}

	/**
	 * @param buildPolicyFile
	 * @return
	 */
	public static String makeFileCacheName(String buildPolicyFile) {
		return "." + buildPolicyFile;
	}

	/**
	 * @param projectName
	 * @return
	 */
	public static String getFileCacheName(String projectName) {
		return makeFileCacheName(PreferenceManager.getBuildPoliciesFileName(projectName));
	}

	/**
	 * @param resource
	 * @return
	 * @throws CoreException
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	private IFile getCache() throws CoreException, InterruptedException, FileNotFoundException {
		String filename = getFileCacheName(resource.getProject().getName());
		IContainer folder = resource.getParent();
		IFile cache = (IFile) ResourceManager.resfreshFileInContainer(folder, filename);
		if (cache != null && cache.exists())
			return cache;
		cache = ResourceManager.get(folder, filename);
		File f = null;
		try {
			URI uri = cache.getLocationURI();
			String path = URLDecoder.decode(uri.getRawPath(), "UTF-8");
			f = new File(path);
			 
			f.createNewFile();
			cache = ResourceManager.toIFile(f);
			cache.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (Exception e) {
			ResourceManager.logException(e);
			return null;
		}
		return cache;
	}

	/**
	 * @throws CoreException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void save(final Properties p, IProgressMonitor monitor)
			throws CoreException, IOException, InterruptedException {
		try {
			String newline = System.getProperty("line.separator");

			IFile fileCache = getCache();

			Iterator iter = p.keySet().iterator();
			StringBuffer sb = new StringBuffer();
			List<String> sortedKeys = new ArrayList<String>();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				sortedKeys.add(key);
			}
			Collections.sort(sortedKeys);
			iter = sortedKeys.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String value = (String) p.get(key);
				sb.append(key).append("=").append(value).append(newline).append(newline);
			}
			byte[] bytes = sb.toString().getBytes();
			InputStream source = new ByteArrayInputStream(bytes);
			fileCache.setContents(source, IResource.FORCE, monitor);
			fileCache.refreshLocal(IResource.DEPTH_INFINITE, monitor);

			log("BuildPoliciesCache.save (" + fileCache + ") saved for resource " + resource + " " + p.toString());
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * @param graphFile
	 * @throws CoreException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void update(List<BuildPolicy> policies) throws CoreException, IOException, InterruptedException {
		Job job = new WorkspaceJob("Updating cache") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					IFile fileCache = getCache();
					Properties p = ResourceManager.loadIFileAsProperties(fileCache,
							getFileCacheName(resource.getProject().getName()));
					String serialized = serialize(policies);
					if (serialized == null) {
						log("BuildPoliciesCache.update " + fileCache + " failed to updated for resource " + resource
								+ " " + policies.toString());
						return Status.OK_STATUS;
					}
					p.put(resource.getName(), System.currentTimeMillis() + ":" + serialized);
					log("BuildPoliciesCache.update " + fileCache + " updated for resource " + resource + " "
							+ p.toString());
					save(p, monitor);
					return Status.OK_STATUS;
				} catch (Exception e) {
					ResourceManager.logException(e);
					return Status.CANCEL_STATUS;
				}
			}
		};
		job.setRule(resource.getProject());
		job.setUser(true);
		job.schedule();
	}

	/**
	 * @param policies
	 * @return
	 */
	private String serialize(List<BuildPolicy> policies) {
		StringBuffer sb = new StringBuffer();
		for (BuildPolicy buildPolicy : policies) {
			sb.append(BuildPolicy.serialize(buildPolicy)).append(";");
		}
		return sb.toString();
	}

	/**
	 * @param projectName
	 * @param container
	 * @param monitor
	 */
	private static void deleteCache(String cachename, IContainer container, IProgressMonitor monitor) {
		try {
			IResource[] members = container.members();
			for (IResource member : members) {
				if (member instanceof IContainer) {
					deleteCache(cachename, (IContainer) member, monitor);
				} else if (member instanceof IFile) {
					IFile file = (IFile) member;
					if (cachename.equals(file.getName())) {
						file.delete(true, monitor);
					}
				}
			}
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 *  
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void clean(IProject project, String cachename, IProgressMonitor monitor) {
		deleteCache(cachename, project,  monitor);
	}

	/**
	 * @param graphFile
	 * @return
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public void invalidate(IProgressMonitor monitor) throws CoreException, IOException, InterruptedException {
		try {
			IFile fileCache = getCache();
			Properties p = ResourceManager.loadIFileAsProperties(fileCache,
					getFileCacheName(resource.getProject().getName()));
			p.remove(resource.getName());
			log("BuildPoliciesCache.invalidate " + fileCache + " invalidated for resource " + resource);
			save(p, monitor);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * @param message
	 */
	private void log(String message) {
		ResourceManager.logInfo(resource.getProject().getName(), message);
	}

	/**
	 * @param policies
	 * @return
	 */
	public boolean needBuild(List<BuildPolicy> policies) {
		try {
			IFile fileCache = getCache();
			if (fileCache == null) {
				return true;
			}
			Properties p = ResourceManager.loadIFileAsProperties(fileCache,
					getFileCacheName(resource.getProject().getName()));
			String svalue = p.getProperty(resource.getName(), null);
			if (svalue == null) {
				return true;
			}
			StringTokenizer st = new StringTokenizer(svalue, ":");
			long graphfiletime = resource.getLocalTimeStamp();
			long lastUpdate = Long.parseLong(st.nextToken());
			boolean need = graphfiletime > lastUpdate;
			if (need)
				return true;
			List<BuildPolicy> list = BuildPolicy.deserialize(st.nextToken());
			List<BuildPolicy> targetPolicies = new ArrayList<BuildPolicy>(policies);
			targetPolicies.removeAll(list);
			need = (targetPolicies.size() > 0);
			return need;
		} catch (Exception e) {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gw4e.eclipse.preferences.ProjectPropertyChangeListener#
	 * projectPropertyUpdated(java.lang.String, java.lang.String,
	 * java.lang.String[], java.lang.String[])
	 */
	@Override
	public void projectPropertyUpdated(String projectName, String property, String[] oldValues, String[] newValues) {
		if (PreferenceManager.BUILD_POLICIES_FILENAME.equals(property)) {
			IProject project = ResourceManager.getProject(projectName);
			String previousCacheName = makeFileCacheName(oldValues.length > 0 ? oldValues[0] : "");
			try {
				ResourceManager.renameFile(project, previousCacheName ,makeFileCacheName(newValues[0]));
			} catch (CoreException e) {
				ResourceManager.logException(e);
			}
			clean(project, previousCacheName, new NullProgressMonitor());
		}
	}
}
