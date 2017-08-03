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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.generator.PathGenerator;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.MissingPoliciesForGraphConfigurationException;
import org.gw4e.eclipse.builder.exception.NoBuildRequiredException;
import org.gw4e.eclipse.builder.exception.ParserContextProperties;
import org.gw4e.eclipse.builder.exception.PathGeneratorConfigurationException;
import org.gw4e.eclipse.builder.exception.SeverityConfigurationException;
import org.gw4e.eclipse.builder.exception.UnexistingGraphFileConfigurationException;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.preferences.ProjectPropertyChangeListener;

/**
 * A facade that manage all build policies
 *
 */
public class BuildPolicyManager implements ProjectPropertyChangeListener {

	/**
	 * @param projectName
	 * @param folder
	 * @param key
	 * @return
	 * @throws CoreException
	 * @throws FileNotFoundException
	 * @throws BuildPolicyConfigurationException
	 */
	public static String[] valuesToArray(String projectName, IFile buildPolicyFile, IFile graphModel, String key)
			throws CoreException, FileNotFoundException, BuildPolicyConfigurationException {
		Properties p = ResourceManager.loadProperties(ResourceManager.toFile(buildPolicyFile.getFullPath()));
		String policies = p.getProperty(key, "");
		List<BuildPolicy> buildPolicies = getPolicies(projectName, buildPolicyFile, graphModel, policies);
		String[] ret = new String[buildPolicies.size()];
		int index = 0;
		for (BuildPolicy buildPolicy : buildPolicies) {
			ret[index] = buildPolicy.toString();
			index++;
		}
		return ret;
	}

	/**
	 * @param file
	 * @return The build policies file located in the same folder as the graph
	 *         file
	 * @throws FileNotFoundException
	 */
	public static IFile getBuildPoliciesForGraph(IFile file) throws FileNotFoundException {
		String name = PreferenceManager.getBuildPoliciesFileName(file.getProject().getName());
		IFolder folder = (IFolder) file.getParent();
		IFile policiesFile = folder.getFile(name);
		if (policiesFile == null || !policiesFile.exists())
			throw new FileNotFoundException(folder.getFullPath().append(name).toString());
		return policiesFile;
	}

	public static IFile getBuildPoliciesForGraph(IFile file, boolean create)
			throws CoreException, FileNotFoundException {
		String name = PreferenceManager.getBuildPoliciesFileName(file.getProject().getName());
		IFolder folder = (IFolder) file.getParent();
		IFile policiesFile = folder.getFile(name);
		if (policiesFile == null || !policiesFile.exists()) {
			if (create) {
				byte[] bytes = getDefaultBuildFileComment().getBytes();
				InputStream source = new ByteArrayInputStream(bytes);
				policiesFile.create(source, IResource.NONE, null);
				policiesFile.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} else {
				throw new FileNotFoundException(folder.getFullPath().append(name).toString());
			}
		}
		return policiesFile;
	}

	/**
	 * Load the build policy file contained in the same folder as the passed
	 * file
	 * 
	 * @param resource
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Properties loadBuildPolicies(IFile resource) throws FileNotFoundException {
		Properties p = ResourceManager.loadIFileAsProperties(resource,
				PreferenceManager.getBuildPoliciesFileName(resource.getProject().getName()));
		return p;
	}
	
	
	public static List<String> getMissingModelFiles (IFile resource)   {
		List<String> ret = new ArrayList<String> ();
		try {
			Properties properties = loadBuildPolicies(resource);
			List<IFile> files = new ArrayList<IFile> ();
			ResourceManager.getAllGraphFiles(resource.getParent(), files); 
			Iterator iter = properties.keySet().iterator();
			while (iter.hasNext()) {
				String filename = (String) iter.next();
				IFile f = resource.getParent().getFile(new Path(filename));
				files.remove(f);
			}
			for (IFile f : files) {
				ret.add(f.getName());
			}

		} catch (Exception e) {
			 ResourceManager.logException(e);
		} 
		return ret;
	}
	

	/**
	 * Build and return a string that correspond to the default policy to parse
	 * a graph model
	 * 
	 * @param file
	 * @return
	 */
	private static String getBuildFileContent(String projectName) {
		String[] paths = PreferenceManager.getBasicPolicies(projectName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < paths.length; i++) {
			sb.append(paths[i]).append(";");
		}
		return sb.toString();
	}

	/**
	 * Add an entry for the specified file in the build policy file
	 * 
	 * @param resource
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void setPolicies(IFile buildPolicyFile, String key, String entry, IProgressMonitor monitor)
			throws IOException, CoreException, InterruptedException {
		Properties p = loadBuildPolicies(buildPolicyFile);
		p.setProperty(key, entry);
		savePolicies(buildPolicyFile, p, monitor);
	}

	/**
	 * Add an entry for the specified file in the build policy file
	 * 
	 * @param resource
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void addDefaultPolicies(IFile resource, IProgressMonitor monitor)
			throws IOException, CoreException, InterruptedException {
		BuildPolicyManager.getBuildPoliciesForGraph(resource, true);
		String entry = getBuildFileContent(resource.getProject().getName());
		Properties p = loadBuildPolicies(resource);
		p.setProperty(resource.getName(), entry);
		savePolicies(resource, p, monitor);
	}

	/**
	 * Add an entry for the specified file in the build policy file
	 * 
	 * @param resource
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void addNoCheckPolicies(IFile resource, IProgressMonitor monitor)
			throws IOException, CoreException, InterruptedException {
		BuildPolicyManager.getBuildPoliciesForGraph(resource, true);
		String entry = NoBuildRequiredException.NO_CHECK;
		Properties p = loadBuildPolicies(resource);
		p.setProperty(resource.getName(), entry);
		savePolicies(resource, p, monitor);
	}

	/**
	 * Add an entry for the specified file in the build policy file
	 * 
	 * @param resource
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void addSyncPolicies(IFile resource, IProgressMonitor monitor)
			throws IOException, CoreException, InterruptedException {
		BuildPolicyManager.getBuildPoliciesForGraph(resource, true);
		String entry = NoBuildRequiredException.SYNCH;
		Properties p = loadBuildPolicies(resource);
		p.setProperty(resource.getName(), entry);
		savePolicies(resource, p, monitor);
	}

	/**
	 * @param message
	 */
	private static void log(String projectName, String message) {
		ResourceManager.logInfo(projectName, message);
	}

	/**
	 * @param resource
	 * @throws CoreException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void savePolicies(IFile resource, Properties p, IProgressMonitor monitor)
			throws CoreException, IOException, InterruptedException {
		log(resource.getProject().getName(),
				"BuildPolicyManager.save (build.policy) : " + resource + "," + p.toString());
		String buildFilename = PreferenceManager.getBuildPoliciesFileName(resource.getProject().getName());
		String newline = System.getProperty("line.separator");
		File file = ResourceManager.getExistingFileInTheSameFolder(resource, buildFilename);
		IFile iFile = ResourceManager.toIFile(file);

		Iterator iter = p.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append(getDefaultBuildFileComment()).append(newline).append(newline);
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
		iFile.setContents(source, IResource.FORCE, monitor);
		iFile.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	/**
	 * @param project
	 * @param buildPolicyFile
	 * @param graphFilePath
	 * @param updatedGenerators
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static void update(IProject project, IFile buildPolicyFile, String graphFilePath,
			List<String> updatedGenerators) throws IOException, CoreException, InterruptedException {
		Job job = new WorkspaceJob("Updating policies") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					_update(project, buildPolicyFile, graphFilePath, updatedGenerators, monitor);
				} catch (FileNotFoundException e) {
					ResourceManager.logException(e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(buildPolicyFile.getProject());
		job.setUser(true);
		job.schedule();
	}

	/**
	 * @param project
	 * @param buildPolicyFile
	 * @param graphFilePath
	 * @param updatedGenerators
	 * @param monitor
	 * @throws FileNotFoundException
	 */
	private static void _update(IProject project, IFile buildPolicyFile, String graphFilePath,
			List<String> updatedGenerators, IProgressMonitor monitor) throws FileNotFoundException {

		IPath fullpath = project.getFullPath().append(graphFilePath);
		IFile graphFile = (IFile) ResourceManager.getResource(fullpath.toString());
		log(project.getName(), "BuildPolicyManager.update " + project.getName() + " " + buildPolicyFile + " "
				+ graphFilePath + " " + updatedGenerators);
		Properties p=null;
		try {
			p = loadBuildPolicies(graphFile);
		} catch (Exception ignore) {
			// might happen in tests when shutting down
			return;
		}

		String key = graphFile.getName();
		String generators = (String) p.get(key);
		boolean updated = false;

		if (generators == null || generators.trim().length() == 0
				|| NoBuildRequiredException.SYNCH.equalsIgnoreCase(generators.trim())) {
			StringBuffer sb = new StringBuffer();
			for (String generator : updatedGenerators) {
				try {
					BuildPolicy.validPathGenerator(generator);
					sb.append(generator).append(";").append(PreferenceManager.getDefaultSeverity(project.getName()))
							.append(";");
				} catch (Exception e) {
					ResourceManager.logException(e);
					continue;
				}
			}
			p.put(key, sb.toString().trim());
			updated = true;
		} else {
			for (String updatedGenerator : updatedGenerators) {
				try {

					PathGenerator<StopCondition> pgUpdated = BuildPolicy.validPathGenerator(updatedGenerator);
					if (pgUpdated == null)
						continue;

					generators = ((String) p.get(key)).trim();
					if (BuildPolicy.isNoCheck(graphFile, generators))
						continue;
					if (!generators.endsWith(";"))
						generators += ";";
					StringTokenizer st = new StringTokenizer(generators, ";");
					boolean eq = false;
					boolean similar = false;
					Map<String, String> validated = new HashMap<String, String>();
					while (st.hasMoreTokens()) {
						String path = null;
						String severity = null;
						try {
							path = st.nextToken();
							severity = st.nextToken();
						} catch (Exception e) {
							ResourceManager.logException(e,
									"Malformed policies in [" + graphFile.getFullPath() + "] : " + generators);
							return;
						}
						String valid = path + ";" + severity + ";";
						PathGenerator<StopCondition> pg;
						try {
							pg = BuildPolicy.validPathGenerator(path);
							if (pg == null)
								continue;
							BuildPolicy.validate(project.getName(), buildPolicyFile, graphFile, path, severity);
							validated.put(valid, valid);
						} catch (Exception e) {
							ResourceManager.logException(e);
							continue;
						}
					}
					Iterator<String> iter = validated.keySet().iterator();
					while (iter.hasNext()) {
						String pathGeneratorAndSeverity = (String) iter.next();
						st = new StringTokenizer(pathGeneratorAndSeverity, ";");
						String path = st.nextToken();
						String severity = st.nextToken();
						eq = GraphWalkerFacade.equalsPathGenerator(pgUpdated, path);
						if (eq)
							break;
					}
					if (eq)
						continue;

					iter = validated.keySet().iterator();
					while (iter.hasNext()) {
						String pathGeneratorAndSeverity = (String) iter.next();
						st = new StringTokenizer(pathGeneratorAndSeverity, ";");
						String path = st.nextToken();
						String severity = st.nextToken();
						similar = GraphWalkerFacade.similarPathGenerator(pgUpdated, path);
						if (similar) {
							generators = ((String) p.get(key)).trim();
							generators.replace(pathGeneratorAndSeverity, "");
							if (!generators.endsWith(";"))
								generators += ";";
							generators = generators.replace(pathGeneratorAndSeverity, "") + updatedGenerator + ";"
									+ severity + ";";
							p.put(key, generators);
							updated = true;
							break;
						}
					}

					if (!eq && !similar) {
						StringBuffer sb = new StringBuffer(generators);
						sb.append(updatedGenerator).append(";")
								.append(PreferenceManager.getDefaultSeverity(project.getName())).append(";");
						p.put(key, sb.toString().trim());
						updated = true;
					}
				} catch (Exception e) {
					ResourceManager.logException(e, fullpath + " " + p.toString());
				}
			}
		}
		if (updated) {
			try {
				new BuildPoliciesCache(graphFile).invalidate(monitor);
				savePolicies(graphFile, p, monitor);
				ResourceManager.touchFile(graphFile);
			} catch (Exception e) {
				ResourceManager.logException(e, fullpath + " " + p.toString());
			}
		}
		;
	}

	/**
	 *
	 * @return The comment that will be at the top of the generated build policy
	 *         file
	 */
	private static String getDefaultBuildFileComment() {
		return "#Format --> GRAPHFILE=PATHGENERATOR;SEVERITYFLAG;PATHGENERATOR1;SEVERITYFLAG1;...";
	}

	/**
	 * Create from scratch a build policy file if it does not exists
	 * 
	 * @param resource
	 * @return the build file
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static IFile createBuildPoliciesFile(IFile resource, IProgressMonitor monitor)
			throws CoreException, InterruptedException {
		String buildPolicyFilename = PreferenceManager.getBuildPoliciesFileName(resource.getProject().getName());
		IFile buildfile = (IFile) ResourceManager.resfreshFileInContainer(resource.getParent(), buildPolicyFilename);
		if (buildfile == null || !buildfile.exists()) {
			byte[] bytes = getDefaultBuildFileComment().getBytes();
			InputStream source = new ByteArrayInputStream(bytes);
			buildfile = resource.getParent().getFile(new Path(buildPolicyFilename));
			buildfile.create(source, IResource.NONE, null);
			buildfile.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}
		return buildfile;
	}

	/**
	 * @param resource
	 * @return
	 */
	public static boolean isBuildPoliciesFile(IFile resource) {
		if (resource == null)
			return false;
		return resource.getName()
				.equalsIgnoreCase(PreferenceManager.getBuildPoliciesFileName(resource.getProject().getName()));
	}

	/**
	 * Lookup the build policy file located in the same directory as the passed
	 * file and return a listViewer of what have been loaded
	 * 
	 * @param resource
	 * @return
	 * @throws FileNotFoundException
	 * @throws BuildPolicyConfigurationException
	 * @throws MissingPoliciesForGraphConfigurationException
	 */
 

	public static List<BuildPolicy> getBuildPolicies(IFile graphModel, boolean check) throws FileNotFoundException,
			BuildPolicyConfigurationException, MissingPoliciesForGraphConfigurationException {
		if (!PreferenceManager.isGraphModelFile(graphModel)) {
			throw new IllegalArgumentException(graphModel.getFullPath().toString());
		}
		Properties p = loadBuildPolicies(graphModel);
		IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(buildPolicyPath.toString());
		String key = graphModel.getName();
		String listOFPathGenerators = p.getProperty(key, null);
		
		if (check && listOFPathGenerators == null || listOFPathGenerators.trim().length() == 0) {
			ParserContextProperties props = new ParserContextProperties();
			p.setProperty(MissingPoliciesForGraphConfigurationException.BUILDPOLICIESPATH, buildPolicyPath.toString());
			p.setProperty(MissingPoliciesForGraphConfigurationException.GRAPHMODELPATH,
					graphModel.getFullPath().toString());
			Location location = ResourceManager.locationOfKeyValue(buildPolicyFile, graphModel.getName(),
					graphModel.getName());
			throw new MissingPoliciesForGraphConfigurationException(location, key, props);
		}

		if (check && listOFPathGenerators.indexOf(NoBuildRequiredException.NO_CHECK) != -1) {
			ParserContextProperties props = new ParserContextProperties();
			p.setProperty(MissingPoliciesForGraphConfigurationException.BUILDPOLICIESPATH, buildPolicyPath.toString());
			p.setProperty(MissingPoliciesForGraphConfigurationException.GRAPHMODELPATH,
					graphModel.getFullPath().toString());
			throw new NoBuildRequiredException(Location.NULL_LOCATION, key, props);
		}

		if (check && listOFPathGenerators.indexOf(NoBuildRequiredException.SYNCH) != -1) {
			ParserContextProperties props = new ParserContextProperties();
			p.setProperty(MissingPoliciesForGraphConfigurationException.BUILDPOLICIESPATH, buildPolicyPath.toString());
			p.setProperty(MissingPoliciesForGraphConfigurationException.GRAPHMODELPATH,
					graphModel.getFullPath().toString());
			throw new NoBuildRequiredException(Location.NULL_LOCATION, key, props);
		}

		String generators = listOFPathGenerators;
		if(!check) {
			generators = cleanPathGenerators(listOFPathGenerators);
		}
		
		List<BuildPolicy> ret = getPolicies(graphModel.getProject().getName(), buildPolicyFile, graphModel, generators);
		return ret;
	}

	private static String cleanPathGenerators (String listOFPathGenerators) {
		String pattern = "\\s*"+NoBuildRequiredException.SYNCH+"\\s*;*";
		String temp = listOFPathGenerators = listOFPathGenerators.replaceAll(pattern, "");
		pattern = "\\s*"+NoBuildRequiredException.NO_CHECK+"\\s*;*";
		temp = listOFPathGenerators = listOFPathGenerators.replaceAll(pattern, "");
		return temp;
	}
	
	/**
	 * @param project
	 * @throws CoreException
	 */
	public static void invalidateCache(IContainer container) throws CoreException {
		ResourceManager.deleteFile(container, BuildPoliciesCache.getFileCacheName(container.getProject().getName()));
	}

	/**
	 * Load the build policies
	 * 
	 * @param projectName
	 * @param resource
	 * @param value
	 * @return
	 * @throws BuildPolicyConfigurationException
	 */
	public static List<BuildPolicy> getPolicies(String projectName, IFile buildPolicyFile, IFile graphModel,
			String listOfGenerators) throws BuildPolicyConfigurationException {
		List<BuildPolicy> ret = new ArrayList<BuildPolicy>();
		StringTokenizer st = new StringTokenizer(listOfGenerators.trim(), ";");
		String pathGenerator = null;
		String severity = null;
		while (st.hasMoreTokens()) {
			try {
				pathGenerator = (String) st.nextToken();
				severity = (String) st.nextToken();
				BuildPolicy bp = new BuildPolicy(projectName, buildPolicyFile, graphModel, pathGenerator, severity);
				ret.add(bp);
			} catch (NoSuchElementException e) {
				ParserContextProperties p = new ParserContextProperties();
				p.setProperty(SeverityConfigurationException.GRAPHMODELPATH, graphModel.getFullPath().toString());
				p.setProperty(SeverityConfigurationException.SEVERITY, String.valueOf(pathGenerator));
				p.setProperty(SeverityConfigurationException.PATH_GENERATOR, pathGenerator);
				p.setProperty(SeverityConfigurationException.BUILDPOLICIESPATH,
						buildPolicyFile.getFullPath().toString());
				Location location = ResourceManager.locationOfKeyValue(buildPolicyFile, graphModel.getName(),
						pathGenerator + ";" + severity);
				location.relocateAtLastChar();

				throw new SeverityConfigurationException(location, "Missing severity flag for '" + pathGenerator + "'",
						p);
			}
		}
		return ret;
	}

	/**
	 * @param buildPolicy
	 * @param graphModelFile
	 * @return
	 */
	private static IPath graphModelExists(IFile buildPolicy, String graphModelFile) throws FileNotFoundException {
		IPath path = null;
		try {
			path = buildPolicy.getFullPath().removeLastSegments(1).append(graphModelFile);
			if (ResourceManager.fileExists(buildPolicy.getProject(), path.toString()))
				return path;
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
		throw new FileNotFoundException(path.toString());
	}

	/**
	 * @param buildPolicyFile
	 */
	public static void touchImpactedGraphModels(IFile buildPolicyFile) {
		ResourceManager.touchFolderForRebuild(buildPolicyFile);
	}

	/**
	 * @param projectName
	 * @param resource
	 * @param value
	 * @return
	 * @throws BuildPolicyConfigurationException
	 */
	public static List<BuildPolicyConfigurationException> validate(IFile buildPolicyFile) {
		List<BuildPolicyConfigurationException> ret = new ArrayList<BuildPolicyConfigurationException>();
		try {
			Properties p = ResourceManager.loadIFileAsProperties(buildPolicyFile,
					PreferenceManager.getBuildPoliciesFileName(buildPolicyFile.getProject().getName()));

			Iterator iter = p.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				IPath graphModelPath = buildPolicyFile.getFullPath().removeLastSegments(1).append(key);
				IFile graphModelFile = (IFile) ResourceManager.getResource(graphModelPath.toString());
				if (graphModelFile == null) {
					ParserContextProperties props = new ParserContextProperties();
					props.setProperty(UnexistingGraphFileConfigurationException.BUILDPOLICIESPATH,
							buildPolicyFile.getFullPath().toString());
					props.setProperty(UnexistingGraphFileConfigurationException.GRAPHMODELPATH,
							graphModelPath.toString());
					Location location = ResourceManager.locationOfKey(buildPolicyFile, key);
					ret.add(new UnexistingGraphFileConfigurationException(location, "Unexisting Graph Model : " + key,
							props));
					continue;
				}

				String value = p.getProperty(key);

				if (value.indexOf(NoBuildRequiredException.NO_CHECK) != -1) {
					continue;
				}
				if (value.indexOf(NoBuildRequiredException.SYNCH) != -1) {
					continue;
				}
				StringTokenizer st = new StringTokenizer(value.trim(), ";");
				String pathGenerator = null;
				String severity = null;
				while (st.hasMoreTokens()) {

					try {
						pathGenerator = (String) st.nextToken().trim();
						severity = (String) st.nextToken().trim();
						new BuildPolicy(buildPolicyFile.getProject().getName(), buildPolicyFile, graphModelFile,
								pathGenerator, severity);
					} catch (NoSuchElementException e) {
						ParserContextProperties props = new ParserContextProperties();
						props.setProperty(SeverityConfigurationException.GRAPHMODELPATH,
								graphModelFile.getFullPath().toString());
						props.setProperty(SeverityConfigurationException.SEVERITY, String.valueOf(""));
						props.put(SeverityConfigurationException.PATH_GENERATOR, pathGenerator);
						props.put(PathGeneratorConfigurationException.BUILDPOLICIESPATH,
								buildPolicyFile.getFullPath().toString());
						Location location = ResourceManager.locationOfKeyValue(buildPolicyFile,
								graphModelFile.getName(), pathGenerator + ";" + severity);
						location.relocateAtLastChar();
						ret.add(new SeverityConfigurationException(location,
								"Missing severity flag for '" + pathGenerator + "'", props));
					} catch (SeverityConfigurationException e) {
						ret.add(e);
					} catch (Exception e) {
						ParserContextProperties props = new ParserContextProperties();
						props.setProperty(PathGeneratorConfigurationException.GRAPHMODELPATH,
								graphModelFile.getFullPath().toString());
						props.setProperty(PathGeneratorConfigurationException.PATH_GENERATOR, pathGenerator);
						props.setProperty(PathGeneratorConfigurationException.BUILDPOLICIESPATH,
								buildPolicyFile.getFullPath().toString());
						Location location = ResourceManager.locationOfKeyValue(buildPolicyFile, key, pathGenerator);
						ret.add(new PathGeneratorConfigurationException(location, e.getMessage(), props));
					}
				}
			}
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
		}
		return ret;
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
			try {
				IProject project = ResourceManager.getProject(projectName);
				if (oldValues.length == 0)
					return;
				ResourceManager.renameFile(project, oldValues[0], newValues[0]);
			} catch (CoreException e) {
				ResourceManager.logException(e);
			}
		}
	}

}
