package org.gw4e.eclipse.preferences;

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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.JavaModelException;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.conversion.AfterElementMethodExtension;
import org.gw4e.eclipse.conversion.AfterExecutionMethodExtension;
import org.gw4e.eclipse.conversion.BeforeElementMethodExtension;
import org.gw4e.eclipse.conversion.BeforeExecutionMethodExtension;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.conversion.FunctionalTestMethodExtension;
import org.gw4e.eclipse.conversion.JUnitLifeCycleMethodExtension;
import org.gw4e.eclipse.conversion.MethodExtension;
import org.gw4e.eclipse.conversion.ModelBasedMethodExtension;
import org.gw4e.eclipse.conversion.SmokeTestMethodExtension;
import org.gw4e.eclipse.conversion.StabillityTestMethodExtension;
import org.gw4e.eclipse.facade.GraphWalkerContextManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion;
import org.gw4e.eclipse.wizard.convert.ResourceContext;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A class that manage the GraphWalker preferences
 *
 */
public class PreferenceManager {
	public static String PROJECT_SCOPED = "PROJECT_SCOPED.";
	public static String GLOBAL_SCOPED = "GLOBAL_SCOPED.";
	public static String GW4E = "GW4E.";
	public static String SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION = PROJECT_SCOPED + GW4E
			+ "SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION";
	public static String SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION = PROJECT_SCOPED + GW4E
			+ "SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION";
	public static String TIMEOUT_FOR_TEST_OFFLINE_GENERATION = PROJECT_SCOPED + GW4E
			+ "TIMEOUT_FOR_TEST_OFFLINE_GENERATION";
	public static String BUILD_POLICIES_FILENAME = PROJECT_SCOPED + GW4E + "BUILD_POLICIES_FILENAME";
	public static String LOG_INFO_ENABLED = PROJECT_SCOPED + GW4E + "LOG_INFO_ENABLED";
	public static String PERFORMANCE_CONFIGURATION = PROJECT_SCOPED + GW4E + "PERFORMANCE_CONFIGURATION";
	public static String DEFAULT_POLICIES = PROJECT_SCOPED + GW4E + "DEFAULT_POLICIES";
	public static String DEFAULT_SEVERITY = PROJECT_SCOPED + GW4E + "DEFAULT_SEVERITY";
	public static String GW4E_ENABLE_BUILD = PROJECT_SCOPED + GW4E + "GW4E_ENABLE_BUILD";
	public static String GW4E_ENABLE_PERFORMANCE = PROJECT_SCOPED + GW4E
			+ "GW4E_ENABLE_PERFORMANCE";
	public static String GW4E_MAIN_SOURCE_GENERATED_INTERFACE = PROJECT_SCOPED + GW4E
			+ "GW4E_MAIN_SOURCE_GENERATED_INTERFACE";
	public static String GW4E_TEST_SOURCE_GENERATED_INTERFACE = PROJECT_SCOPED + GW4E
			+ "GW4E_TEST_SOURCE_GENERATED_INTERFACE";	
	
	
	public static String GW4E_HOOK_SOURCE = PROJECT_SCOPED + GW4E + "GW4E_HOOK_SOURCE";
	public static String BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED = PROJECT_SCOPED + GW4E
			+ "BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED";
	public static String SEVERITY_FOR_ABSTRACT_CONTEXT = PROJECT_SCOPED + GW4E
			+ "SEVERITY_FOR_ABSTRACT_CONTEXT";
	public static String SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT = PROJECT_SCOPED + GW4E
			+ "SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT";

	public static String AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION = GLOBAL_SCOPED + GW4E
			+ "AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION";
	public static String GRAPHWALKER_JAVALIBRARIES = GLOBAL_SCOPED + GW4E + "GRAPHWALKER_JAVALIBRARIES";
	public static String GRAPHWALKER_VERSION = GLOBAL_SCOPED + GW4E + "GRAPHWALKER_VERSION";
	
	//
	public static String GW4E_SOURCE_MAIN_JAVA = GLOBAL_SCOPED + GW4E + "GW4E_SOURCE_MAIN_JAVA";
	public static String GW4E_SOURCE_TEST_JAVA = GLOBAL_SCOPED + GW4E + "SOURCE_TEST_JAVA";

	public static String GW4E_RESOURCE_MAIN = GLOBAL_SCOPED + GW4E + "GW4E_RESOURCE_MAIN";
	public static String GW4E_RESOURCE_TEST = GLOBAL_SCOPED + GW4E + "RESOURCE_TEST";

	static boolean cacheEnabled = true;

	/**
	 * @return the name of the supported preference property names
	 */
	public static String[] getPreferencePropertyNames() {
		String[] values = new String[] { 
				SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION,
				AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION, 
				BUILD_POLICIES_FILENAME, 
				DEFAULT_SEVERITY, 
				DEFAULT_POLICIES,
				GRAPHWALKER_JAVALIBRARIES, 
				GW4E_ENABLE_BUILD, 
				GW4E_ENABLE_PERFORMANCE, 
				LOG_INFO_ENABLED,
				BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED , 
				SEVERITY_FOR_ABSTRACT_CONTEXT,SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT,
				GW4E_MAIN_SOURCE_GENERATED_INTERFACE ,  
				GW4E_TEST_SOURCE_GENERATED_INTERFACE, 
				PERFORMANCE_CONFIGURATION, 
				SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION,
				TIMEOUT_FOR_TEST_OFFLINE_GENERATION,
				GRAPHWALKER_VERSION};
		return values;
	}
	 
 

	/**
	 * @param key
	 *            : the property
	 * @return whether the property is a project one or a global one
	 */
	public static boolean isProjectScoped(String key) {
		boolean projectScoped = key.startsWith(PROJECT_SCOPED);
		return projectScoped;
	}

	/**
	 * @param key
	 * @return Default preference for the passed key (same as
	 *         getDefaultPreference but might have been overridden in the
	 *         GW4E Preference Page)
	 */
	 
	/**
	 * @param key
	 * @return Default preferences set by the PreferenceInitializer
	 */
	public static String[] getDefaultPreference(String key) {
		String[] values = PreferenceInitializer.getDefautValues(key);
		return values;
	}

	/**
	 * Remove preference settings for the projects
	 * 
	 * @param projectName
	 * @throws BackingStoreException 
	 */
	public static void removePreferences(String projectName) throws BackingStoreException {
		IProject project = ResourceManager.getProject(projectName);
		String[] values = getPreferencePropertyNames();
		SettingsManager.remove(project, values);

	}

	/**
	 * Set preference settings for the projects
	 * 
	 * @param projectName
	 * @param key
	 * @param values
	 */
	public static void setPreference(String projectName, String key, String[] values) {
		boolean projectScoped = isProjectScoped(key);
		IProject project = null;
		if (projectScoped) {
			project = ResourceManager.getProject(projectName);
		}
		SettingsManager.putValues(project, key, values, projectScoped);
	}

	/**
	 * Set default preference settings for the projects
	 * 
	 * @param projectName
	 */
	public static void setDefaultPreference(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String[] keys = getPreferencePropertyNames();
		for (int i = 0; i < keys.length; i++) {
			boolean projectScoped = isProjectScoped(keys[i]);
			SettingsManager.putValues(project, keys[i], SettingsManager.getDefaultGlobalValues(keys[i]),
					projectScoped);
		}
		// getCurrentDefaultPreference(project, keys[i])
	}

	/**
	 * @param projectName
	 * @return
	 */
	public static boolean isPerformanceEnabled(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = GW4E_ENABLE_PERFORMANCE;
			boolean projectScoped = isProjectScoped(key);
			return Boolean.parseBoolean(SettingsManager.getValues(project, key, projectScoped)[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * @return whether the GW4E build is enabled. True by default
	 */
	public static boolean isBuildEnabled(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = GW4E_ENABLE_BUILD; 
			boolean projectScoped = isProjectScoped(key);
			return Boolean.parseBoolean(SettingsManager.getValues(project, key, projectScoped)[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * @return the suffix that will be added to the interface name to get the
	 *         java class name implementation
	 */
	public static String suffixForTestImplementation(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION;
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}

	public static int getTimeOutForTestOfflineGeneration(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = TIMEOUT_FOR_TEST_OFFLINE_GENERATION;
			boolean projectScoped = isProjectScoped(key);
			return Integer.parseInt(SettingsManager.getValues(project, key, projectScoped)[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return 15;
		}
	}
	
	/**
	 * @return the suffix that will be added to the interface name to get the
	 *         java class name implementation
	 */
	public static String suffixForTestOfflineImplementation(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION;
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * @param resource
	 * @return whether the passed resource is the target for test interfaces
	 */
	public static boolean isTargetFolderForTestInterface(IResource resource,boolean main) {
		if (resource == null) {
			return false;
		}
		IPath pathFolderForTestInterface = resource.getProject().getFullPath()
				.append(PreferenceManager.getTargetFolderForTestInterface(resource.getProject().getName(),main));
		return resource.getFullPath().equals(pathFolderForTestInterface);
	}

	/**
	 * @param path
	 */
	public static void setTargetFolderForTestInterface(IResource resource,boolean main) {
		IProject project = resource.getProject();
		String path = resource.getFullPath().removeFirstSegments(1).toString();
		String key = GW4E_MAIN_SOURCE_GENERATED_INTERFACE;
		if (!main) {
			key = GW4E_TEST_SOURCE_GENERATED_INTERFACE;
		}		
		boolean projectScoped = isProjectScoped(key);
		SettingsManager.putValues(project, key,
				new String[] { path }, projectScoped);
	}

	
	public static String getOfflineTestMethodName () {
		return "test";
	}

	
	public static boolean isInMainPath (IPath p) {
		return p.toString().indexOf(PreferenceManager.getMainResourceFolder()) != -1;
	}

	/**
	 * Return the folder for test interface (where java test source interface
	 * are generated)
	 * 
	 * For graph located in src/main/resources then return 
	 * For graph located in src/test/resources then  return
	 * @return
	 */
	public static String getTargetFolderForTestInterface(String projectName,boolean main) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			 
			String key = GW4E_MAIN_SOURCE_GENERATED_INTERFACE;
			if (!main) {
				key = GW4E_TEST_SOURCE_GENERATED_INTERFACE;
			}
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public static String[] getAllTargetFolderForTestInterface(String projectName) {
		return new String[] { getTargetFolderForTestInterface(projectName,true), getTargetFolderForTestInterface(projectName,false) };
	}

	/**
	 * @param selectedGraphFile
	 * @return
	 */
	public static IPath computeBaseTargetFolderForGeneratedTests(IResource selectedGraphFile) {
		IProject project = selectedGraphFile.getProject();
		IPath graphFolderParentPath = selectedGraphFile.getParent().getFullPath();

		IPath pathResourceTestFolder = project.getFullPath().append(getTestResourceFolder());
		if (ResourceManager.isInFolder(pathResourceTestFolder, graphFolderParentPath)) {
			IPath parentPath = project.getFullPath().append(getTestResourceFolder());
			return parentPath;
		}

		IPath pathResourceMainFolder = project.getFullPath().append(getMainResourceFolder());
		if (ResourceManager.isInFolder(pathResourceMainFolder, graphFolderParentPath)) {
			IPath parentPath = project.getFullPath().append(getMainResourceFolder());
			return parentPath;
		}

		return null;
	}

	/**
	 * Return the allowed folder where test can be generated in depending on the
	 * user selection if the selected graph is in src/main/resource then
	 * src/main/java if the selected graph is in src/test/resource then
	 * src/test/java
	 * 
	 * @return
	 */
	public static IPath getTargetFolderForGeneratedTests(IResource selectedGraphFile) {

		IProject project = selectedGraphFile.getProject();

		IPath pathResourceTestFolder = project.getFullPath().append(getTestResourceFolder());
		if (ResourceManager.isInFolder(pathResourceTestFolder,
				selectedGraphFile.getParent().getFullPath()))
			return project.getFullPath().append(getTestSourceFolder());

		IPath pathResourceMainFolder = project.getFullPath().append(getMainResourceFolder());
		if (ResourceManager.isInFolder(pathResourceMainFolder,
				selectedGraphFile.getParent().getFullPath()))
			return project.getFullPath().append(getMainSourceFolder());
		return null;
	}

	/**
	 * Return the folder for main source folder (where java test source
	 * implementation are generated)
	 * 
	 * @return
	 */
	public static String getMainSourceFolder() {
		return getDefaultPreference(GW4E_SOURCE_MAIN_JAVA)[0];
	}

	/**
	 * Return the folder for test resource folder (where java test source
	 * implementation are generated)
	 * 
	 * @return
	 */
	public static String getTestSourceFolder() {
		return getDefaultPreference(GW4E_SOURCE_TEST_JAVA)[0];
	}

	/**
	 * Return the folder for main resource folder (where java test source
	 * implementation are generated)
	 * 
	 * @return
	 */
	public static String getMainResourceFolder() {
		return getDefaultPreference(GW4E_RESOURCE_MAIN)[0];
	}

	/**
	 * Return the folder for test source folder (where java test source
	 * implementation are generated)
	 * 
	 * @return
	 */
	public static String getTestResourceFolder() {
		return getDefaultPreference(GW4E_RESOURCE_TEST)[0];
	}

	/**
	 * List folders that are considered as containing graph models files
	 * 
	 * @return
	 */
	public static String[] getAuthorizedFolderForGraphDefinition() {
		String key = AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION;
		boolean projectScoped = isProjectScoped(key);
		return SettingsManager.getValues(null, key, projectScoped);
	}
	
	
	/**
	 * @return
	 */
	public static String getGW4EEditorName() {
		return "org.gw4e.eclipse.studio.editor.GW4EEditor";
	}
	
	/**
	 * 
	 * @return Default values for the Authorized Folders For Graphs Definitions
	 */
	public static String[] getDefaultAuthorizedFolderForGraphDefinition() {
		return SettingsManager.getDefautValues(AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
	}

	/**
	 * 
	 * @return Default values for the GraphWalker Libraries
	 */
	public static String[] getDefaultGraphWalkerLibraries() {
		return SettingsManager.getDefautValues(GRAPHWALKER_JAVALIBRARIES);
	}

	/**
	 * @return Default value for severity in build.policies file
	 */
	public static String getDefaultSeverity(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = DEFAULT_SEVERITY;
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "?";
		}
	}

	/**
	 * @return the GraphWalker version
	 */
	public static String getGraphWalkerVersion(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = GRAPHWALKER_VERSION;
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "?";
		}
	}	 
	
	/**
	 * @return the build policies file name
	 */
	public static String getBuildPoliciesFileName(String projectName) {
		try {
			IProject project = ResourceManager.getProject(projectName);
			String key = BUILD_POLICIES_FILENAME;
			boolean projectScoped = isProjectScoped(key);
			return SettingsManager.getValues(project, key, projectScoped)[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}

	/**
	 * @param projectName
	 * @return
	 */
	private static final String INFO = "gw4e-eclipse-plugin/info";

	/**
	 * There are 2 ways to configure logging ...
	 * The first one is  the Eclipse way .. the second one is mine :-)
	 * @param projectName
	 * @return
	 */
	public static boolean isLogInfoEnabled(String projectName) {
		String debugOption = Platform.getDebugOption(INFO);
		boolean option1 = new Boolean(debugOption).booleanValue();
		if (option1) return true; 
		
		IProject project = ResourceManager.getProject(projectName); 
		String key = LOG_INFO_ENABLED;
		boolean projectScoped = isProjectScoped(key);
		String value = SettingsManager.getValues (project,key,projectScoped)[0]; 
		boolean option2 = new Boolean (value).booleanValue();
		return  option2;
	}

	
	/**
	 * Enable/Disable plugin logs
	 * @param projectName
	 * @param enabled
	 */
	public static void setLogInfoEnabled (String projectName, boolean enabled) {
		IProject project = ResourceManager.getProject(projectName); 
		String key = LOG_INFO_ENABLED;
		boolean projectScoped = isProjectScoped(key);
		SettingsManager.putValues(project,key, new String [] {"true"}, projectScoped);
	}
	
	 
	/**
	 * @return the build policies file extension
	 */
	public static String getBuildPoliciesFileExtension(String filename) {
		int pos = filename.lastIndexOf(".");
		String extension = filename.substring(pos + 1);
		return extension;
	}

	/**
	 * Return the extension of the passed file , if the file can be parsed/built
	 * by our GW4E Builder
	 * 
	 * @param resource
	 * @return
	 */
	public static String supportedFileForBuild(IFile resource) {
		String projectName = resource.getProject().getName();
		String extension = resource.getFileExtension();
		if ("java".equalsIgnoreCase(extension)) {
			return "java";
		}
		if (Constant.GRAPHML_FILE.equalsIgnoreCase(extension)) {
			boolean folderOK = allowedFolder(resource);
			if (folderOK)
				return Constant.GRAPHML_FILE;
			return "";
		}
		if (Constant.GRAPH_JSON_FILE.equalsIgnoreCase(extension)) {
			boolean folderOK = allowedFolder(resource);
			if (folderOK)
				return Constant.GRAPH_JSON_FILE;
			return "";
		}
		if (Constant.GRAPH_GW4E_FILE.equalsIgnoreCase(extension)) {
			boolean folderOK = allowedFolder(resource);
			if (folderOK)
				return Constant.GRAPH_GW4E_FILE;
			return "";
		}
		String bpf = PreferenceManager.getBuildPoliciesFileName(projectName);
		if (bpf.equalsIgnoreCase(resource.getName())) {
			boolean folderOK = allowedFolder(resource);
			if (folderOK)
				return PreferenceManager.getBuildPoliciesFileExtension(bpf);
		}
		return "";
	}

	/**
	 * File that can be used with the gw3 convert command such as gw3 convert -i
	 * Login.graphml Login.java
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isConvertable(String extension) {
		if (Constant.GRAPHML_FILE.equalsIgnoreCase(extension)) {
			return true;
		}
		if (Constant.GRAPH_JSON_FILE.equalsIgnoreCase(extension)) {
			return true;
		}
		if (Constant.GRAPH_GW4E_FILE.equalsIgnoreCase(extension)) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 */
	public static boolean isCacheEnabled() {
		return cacheEnabled;
	}

	/**
	 * 
	 */
	public static void toggleCacheEnabled() {
		cacheEnabled = !cacheEnabled;
	}

	/**
	 * The performance configuration  
	 *  
	 * 
	 * @return
	 */
	public static String[] getPerformanceConfiguration(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String key = PERFORMANCE_CONFIGURATION;
		boolean projectScoped = isProjectScoped(key);
		return SettingsManager.getValues(project, key, projectScoped);
	}	
	
	/**
	 * The default policies that will be set whenever the end user choose to add
	 * an entry in the build policies file using the Quick Fix in the problem
	 * view
	 * 
	 * @return
	 */
	public static String[] getBasicPolicies(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String key = DEFAULT_POLICIES;
		boolean projectScoped = isProjectScoped(key);
		return SettingsManager.getValues(project, key, projectScoped);
	}

	/**
	 * Do wa want to synchronize the build.policy file according to test content
	 * 
	 * @param projectName
	 * @return
	 */
	public static boolean isBuildPoliciesSynchronisationWithTestsAuthrorized(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String key = BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED;
		boolean projectScoped = isProjectScoped(key);
		String[] values = SettingsManager.getValues(project, key, projectScoped);
		if (values == null || values.length == 0)
			return false;
		return new Boolean(values[0]);
	}
	
	public static String getSeverityForUnSynchronizedContext(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String key = SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT;
		boolean projectScoped = isProjectScoped(key);
		String[] values = SettingsManager.getValues(project, key, projectScoped);
		if (values == null || values.length == 0)
			return "";
		return   values[0];
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public static String getSeverityForAbstractContext(String projectName) {
		IProject project = ResourceManager.getProject(projectName);
		String key = SEVERITY_FOR_ABSTRACT_CONTEXT;
		boolean projectScoped = isProjectScoped(key);
		String[] values = SettingsManager.getValues(project, key, projectScoped);
		if (values == null || values.length == 0)
			return "";
		return   values[0];
	}
	
	
	public static String getDefaultGraphWalkerVersion() {
		return PreferenceInitializer.getDefautValues(GRAPHWALKER_VERSION)[0];
	}
	
	/**
	 * @return
	 */
	public static String getDefaultSeverityForAbstractContext() {
		return PreferenceInitializer.getDefautValues(SEVERITY_FOR_ABSTRACT_CONTEXT)[0];
	}
	
	public static String getDefaultSeverityForUnSynchronizedContext() {
		return PreferenceInitializer.getDefautValues(SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT)[0];
	}
	
	/**
	 * The listViewer of library that are needed to run GraphWalker features used
	 * in a GW4E project
	 * 
	 * @return
	 */
	public static String[] getGraphWalkerJavaLibName() {
		String key = GRAPHWALKER_JAVALIBRARIES;
		boolean projectScoped = isProjectScoped(key);
		return SettingsManager.getValues(null, key, projectScoped);
	}

	///////////////////////////////////////////

	/**
	 * Check whether the passed input is a folder that is considered as
	 * containing graph models files
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isAuthorizedFolderForGraphDefinition(String projectName, String input) {
		String[] authorized = getAuthorizedFolderForGraphDefinition();
		for (int i = 0; i < authorized.length; i++) {
			if (authorized[i].equalsIgnoreCase(input))
				return true;
		}
		return false;
	}

	/**
	 * Test whether the passed resource is in a folder considered as containing
	 * graph models files
	 * 
	 * @param resource
	 * @return
	 */
	private static boolean allowedFolder(IFile resource) {
		String[] folders = getAuthorizedFolderForGraphDefinition();
		return ResourceManager.isFileInFolders(resource, folders);
	}

	/**
	 * Is the passed file can be parsed / built by our GW4E Builder
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isSupportedFileForBuild(IFile resource) {
		return supportedFileForBuild(resource).trim().length() > 0;
	}

	/**
	 * Is this file a graphml model file ?
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isGraphModelFile(IFile resource) {
		String extension = resource.getFileExtension();
		if (Constant.GRAPHML_FILE.equalsIgnoreCase(extension) && allowedFolder(resource)) {
			return true;
		}
		if (Constant.GRAPH_JSON_FILE.equalsIgnoreCase(extension) && allowedFolder(resource)) {
			return true;
		}
		if (Constant.GRAPH_GW4E_FILE.equalsIgnoreCase(extension) && allowedFolder(resource)) {
			return true;
		}		
		return false;
	}
	
	
	/**
	 * @return the GraphModel extensions file
	 */
	public static String [] getGraphModelExtensionFile() {
		return new String [] {Constant.GRAPHML_FILE,Constant.GRAPH_JSON_FILE,Constant.GRAPH_GW4E_FILE};
	}

	/**
	 * @return
	 */
	public static String getDefaultGroups() {
		return "default";
	}

	/**
	 * Is the passed file a GW3 file ?
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isGW3ModelFile(IFile resource) {
		if (resource == null) return false;
		String extension = resource.getFileExtension();
		if (Constant.GW3_FILE.equalsIgnoreCase(extension) && allowedFolder(resource)) {
			return true;
		}
		return false;
	}

	/**
	 * @param resource
	 * @return
	 */
	public static boolean isJSONModelFile(IFile resource) {
		String extension = resource.getFileExtension();
		if (Constant.GW3_FILE.equalsIgnoreCase(extension) && allowedFolder(resource)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public static boolean getGenerateExecutionAnnotation() {
		return true;
	}

	/**
	 * @return
	 */
	public static boolean getGenerateElementAnnotation() {
		return true;
	}

	/**
	 * @return
	 */
	public static String getDefaultPathGenerator() {
		return "random(edge_coverage(100))";
	}

	/**
	 * @return
	 */
	public static String getDefaultStartElement() {
		return "to_be_changed";
	}

	/**
	 * @return
	 */
	public static boolean getDefaultOptionForGraphWalkerAnnotationGeneration() {
		return true;
	}

	/**
	 * @return
	 * @throws JavaModelException
	 */
	public static ClassExtension getDefaultClassExtension(IFile ifile) throws JavaModelException {
		boolean generateExecutionHook = false;
		boolean generatePerformance = false;
		boolean generateElementHook = false;
		boolean generateRunSmokeTest = false;
		boolean generateRunFunctionalTest = false;
		boolean generateRunStabilityTest = false;
		boolean generateModelBased = false;
		String startElement = null;
		String targetVertex = null;
		String startElementForJunitTest = null;
		List<IFile> additionalContexts = new ArrayList<IFile>();

		try {
			String f = ResourceManager.getAbsolutePath(ifile);
			startElement = GraphWalkerFacade.getNextElement(f);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}

		return new ClassExtension(generateExecutionHook, generatePerformance, generateElementHook, generateRunSmokeTest,
				generateRunFunctionalTest, generateRunStabilityTest, targetVertex, startElementForJunitTest,
				additionalContexts, generateModelBased, getDefaultOptionForGraphWalkerAnnotationGeneration(),
				getDefaultPathGenerator(), startElement, getDefaultGroups(), ifile);
	}

	/**
	 * @return
	 */
	public static MethodExtension[] getMethodExtensionsToAddToTestImplementation(ResourceContext context) {
		boolean executionHook = true;
		boolean generatePerformance = true;
		boolean elementHook = false;
		boolean runSmokeTest = false;
		boolean runFunctionalTest = false;
		boolean runStabilityTest = false;
		boolean runGenerateModelBased = false;
		List<String> additionalContexts = new ArrayList<String>();
		String classname = null;
		if (context != null) {
			executionHook = context.isGenerateExecutionHook();
			elementHook = context.isGenerateElementHook();
			generatePerformance = context.isGeneratePerformance();
			runFunctionalTest = context.isGenerateRunFunctionalTest();
			runSmokeTest = context.isGenerateRunSmokeTest();
			runStabilityTest = context.isGenerateRunStabilityTest();
			runGenerateModelBased = context.isGenerateRunModelBased();
			classname = context.getQualifiedNameForImplementation();
			for (IFile iFile : context.getClassExtension().getAdditionalContexts()) {
				try {
					AbstractPostConversion converter = GraphWalkerContextManager
							.getDefaultGraphConversion(iFile,false);
					additionalContexts.add(converter.getQualifiedNameForImplementation());
				} catch (CoreException e) {
					ResourceManager.logException(e);
				}
			}
		}
		List<MethodExtension> extensions = new ArrayList<MethodExtension>();
		if (executionHook) {
			extensions.add(new BeforeExecutionMethodExtension(classname, additionalContexts, "_beforeExecution"));
			extensions.add(new AfterExecutionMethodExtension(classname, additionalContexts, "_afterExecution",
					generatePerformance));
		}
		if (elementHook) {
			extensions.add(new BeforeElementMethodExtension(classname, additionalContexts, "_beforeElement"));
			extensions.add(new AfterElementMethodExtension(classname, additionalContexts, "_afterElement"));
		}

		if (runSmokeTest) {
			extensions.add(new SmokeTestMethodExtension(classname, additionalContexts,
					context.getClassExtension().getStartElementForJunitTest(),
					context.getClassExtension().getTargetVertex()));
		}
		if (runFunctionalTest) {
			extensions.add(new FunctionalTestMethodExtension(classname, additionalContexts,
					context.getClassExtension().getStartElementForJunitTest()));
		}
		if (runStabilityTest) {
			extensions.add(new StabillityTestMethodExtension(classname, additionalContexts,
					context.getClassExtension().getStartElementForJunitTest()));
		}
		if (runSmokeTest || runFunctionalTest || runStabilityTest) {
			extensions.addAll(JUnitLifeCycleMethodExtension.createAllJUnitMethodExtensions(classname,
					additionalContexts, context.getClassExtension().getStartElementForJunitTest()));
		}

		if (runGenerateModelBased) {
			extensions.add(new ModelBasedMethodExtension(classname, additionalContexts, context.getSelectedFile()));
		}
		MethodExtension[] ret = new MethodExtension[extensions.size()];
		extensions.toArray(ret);
		return ret;
	}

}
