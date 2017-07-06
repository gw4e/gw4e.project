package org.gw4e.eclipse.launching.offline;

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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

/** 
 * a Java launch implementatoin configuration delegate. 
 * Provides convenience methods for accessing and verifying launch configuration attributes. 
 *
 */
public class GW4ELaunchConfigurationDelegate extends AbstractJavaLaunchConfigurationDelegate implements LaunchingConstant {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
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
			// Specific GraphWalker Offline arguments from the configuration
			progArgs.add("offline");
			
 			String removeBlockedElements= configuration.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,"true");  

 			String unvisited= configuration.getAttribute(CONFIG_UNVISITED_ELEMENT,(String) null);  
			String verbose= configuration.getAttribute(CONFIG_VERBOSE,(String) null);  
			String modelPath= configuration.getAttribute(CONFIG_GRAPH_MODEL_PATH  ,(String) null); 
			String generatorstopcondition= configuration.getAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS ,(String) null); 
			String startnode= configuration.getAttribute(CONFIG_LAUNCH_STARTNODE ,(String) null); 
			
			// Set the model
			if ( (modelPath==null)  || (modelPath.trim().length()==0)) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageUtil.getString("error_nomodelsettoruntheofflinecommand"), new Exception(MessageUtil.getString("error_modelNamenotdefined"))));
			} else {
				File modelFile = ResourceManager.stringPathToFile(modelPath.trim());
				if (!modelFile.exists()) {
					throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageUtil.getString("error_modelsettoruntheofflinecommanddoesnotexist"), new FileNotFoundException(modelFile.getAbsolutePath().toString())));
				}
				progArgs.add("-m");
				progArgs.add(modelFile.getAbsolutePath().toString());
			}
			// Set the codegenerator & stop condition
			if ( (generatorstopcondition==null)  || (generatorstopcondition.trim().length()==0)) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, MessageUtil.getString("error_nogeneratorstopconditionsettoruntheofflinecommand"), new Exception(MessageUtil.getString("error_graphmodel_not_found"))));
			} else {
				progArgs.add("\"" + generatorstopcondition + "\"");
			}
			// Specify the optional start element
			if (!( (startnode==null)  || (startnode.trim().length()==0))) {
				progArgs.add("-e");
				progArgs.add(startnode);
			}
			// Print unvisited element
			if (unvisited!=null && Boolean.parseBoolean(unvisited)) {
				progArgs.add("-u");
			}
			// Verbose mode
			if (verbose!=null && Boolean.parseBoolean(verbose)) {
				progArgs.add("-o");
			}
			
			if (removeBlockedElements!=null && Boolean.parseBoolean(removeBlockedElements)) {
				progArgs.add("-b");
				progArgs.add("true");
			} else  {
				progArgs.add("-b");
				progArgs.add("false");
			}		 
			
			// VM-specific attributes
			Map<String, Object> vmAttributesMap= getVMSpecificAttributesMap(configuration);
			// Get the classpath
			String[] classpath= getClasspath(configuration);
			// Set the properties 
			VMRunnerConfiguration runConfig= new VMRunnerConfiguration(CONFIG_LAUNCH_CLASS, classpath);
			runConfig.setVMArguments(VMArgs.toArray(new String[VMArgs.size()]));
			runConfig.setProgramArguments(progArgs.toArray(new String[progArgs.size()]));
			runConfig.setEnvironment(envp);
			runConfig.setWorkingDirectory(workingDirName);
			runConfig.setVMSpecificAttributesMap(vmAttributesMap);
			runConfig.setBootClassPath(getBootpath(configuration));
			// Here we are ... 
			runner.run(runConfig, launch, monitor);
		} finally {
			monitor.done();
		}
	}

}
