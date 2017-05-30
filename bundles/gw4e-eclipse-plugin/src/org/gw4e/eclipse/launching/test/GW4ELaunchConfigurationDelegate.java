package org.gw4e.eclipse.launching.test;

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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.gw4e.eclipse.facade.ResourceManager;

/** 
 * a Java launch implementation configuration delegate. 
 * Provides convenience methods for accessing and verifying launch configuration attributes. 
 *
 */
public class GW4ELaunchConfigurationDelegate extends AbstractJavaLaunchConfigurationDelegate implements LaunchingConstant {

	 
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public synchronized void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		try {
			// A VM runner starts a Java VM running a Java program. 
			IVMRunner runner= getVMRunner(configuration, mode);
			// Retrieve the working fir
			File workingDir = verifyWorkingDirectory(configuration);
			String workingDirName = null;
			if (workingDir != null) {
				workingDirName= workingDir.getAbsolutePath();
			}
			//  Retrieve the environment from the configuration
			String[] envp= getEnvironment(configuration);
			// Now build the the java standard arguments
			ArrayList<String> VMArgs= new ArrayList<>();
			ArrayList<String> progArgs= new ArrayList<>();
			
			String pgmArgs= getProgramArguments(configuration);
			String vmArgs= getVMArguments(configuration);
			ExecutionArguments execArgs= new ExecutionArguments(vmArgs, pgmArgs);
			VMArgs.addAll(Arrays.asList(execArgs.getVMArgumentsArray()));
			progArgs.addAll(Arrays.asList(execArgs.getProgramArgumentsArray()));
			// Specific GraphWalker Test arguments from the configuration

			String [] classpath = updateClassPath (configuration);
			Path tempFile = ClasspathSerializer.serialize(classpath);
			

			progArgs.add(tempFile.toFile().getAbsolutePath());
			
			String classnames= configuration.getAttribute(CONFIG_TEST_CLASSES,"unknown class");  
			tempFile = TestsSerializer.serialize(classnames);
			progArgs.add(tempFile.toFile().getAbsolutePath());
			
			String execReportDir= configuration.getAttribute(EXECUTION_TEST_REPORT_DIR,".");  
			progArgs.add(execReportDir);
			
			String displayReport= configuration.getAttribute(EXECUTION_TEST_DISPLAY_CONFIGURATION,"true"); 
			progArgs.add(displayReport);
			
			 
			int fPort= SocketUtil.findFreePort();
			progArgs.add("-port");  
			progArgs.add(String.valueOf(fPort));
			System.out.println("Debug Port " + fPort);
			// VM-specific attributes
			Map<String, Object> vmAttributesMap= getVMSpecificAttributesMap(configuration);
			// Set the properties 
			VMRunnerConfiguration runConfig= new VMRunnerConfiguration(CONFIG_LAUNCH_CLASS, classpath);
			runConfig.setVMArguments(VMArgs.toArray(new String[VMArgs.size()]));
			runConfig.setProgramArguments(progArgs.toArray(new String[progArgs.size()]));
			runConfig.setEnvironment(envp);
			runConfig.setWorkingDirectory(workingDirName);
			runConfig.setVMSpecificAttributesMap(vmAttributesMap);
			runConfig.setBootClassPath(getBootpath(configuration));
			
			setDefaultSourceLocator(launch, configuration);
			
			// Here we are ... 
			// 
			runner.run(runConfig, launch, monitor);
		} finally {
			monitor.done();
		}
	}

	private String [] updateClassPath (ILaunchConfiguration configuration) throws CoreException {
		URL resource  = this.getClass().getResource(GW4ELaunchConfigurationDelegate.class.getSimpleName() + ".class");
 
		String jarpath = GW4ELaunchConfigurationDelegate.class.getProtectionDomain().getCodeSource().getLocation().getPath ();
		 
		try {
			resource = FileLocator.toFileURL(resource);
		} catch (IOException e) {
			ResourceManager.logException(e);
		}
		
		String root = resource.toString();
		 
		if (root.startsWith("jar:")) {
		     String vals[] = root.split("/");
		     for (String val: vals) {
		       if (val.contains("!")) {
		    	   root = val.substring(0, val.length() - 1);
		       }
		     }
		} else {
			int pos = root.indexOf((GW4ELaunchConfigurationDelegate.class.getName() ).replaceAll("\\.", "/"));
			root = root.substring(0,pos);
		}
		 
		String[] defaultCp = getClasspath(configuration);
		String[] extendedCp = new String[defaultCp.length+2];
		System.arraycopy(defaultCp, 0, extendedCp, 0, defaultCp.length);
		extendedCp[extendedCp.length-1] = normalizePath(root);
		extendedCp[extendedCp.length-2] = normalizePath(jarpath);
		return extendedCp;
	}
	
	private String normalizePath(String path) {
		if (OSUtils.isWindows()) {
			if (path.startsWith("/")) {
				return path.substring(1);
			}
			if (path.startsWith("file:/")) {
				return path.substring("file:/".length());
			}
		}
		return path;
	}
	 
}
