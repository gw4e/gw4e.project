package org.gw4e.eclipse.launching.runasmanual;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.gw4e.eclipse.wizard.runasmanual.RunAsManualWizard;

/**
 * a Java launch implementation configuration delegate. Provides convenience
 * methods for accessing and verifying launch configuration attributes.
 *
 */
public class GW4ELaunchConfigurationDelegate extends AbstractJavaLaunchConfigurationDelegate
		implements LaunchingConstant {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.
	 * eclipse.debug.core.ILaunchConfiguration, java.lang.String,
	 * org.eclipse.debug.core.ILaunch,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public synchronized void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		String modelPath = configuration.getAttribute(CONFIG_GRAPH_MODEL_PATH, (String) null);
		String generatorstopcondition = configuration.getAttribute(CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS,(String) null);
		String startnode = configuration.getAttribute(CONFIG_LAUNCH_STARTNODE, (String) null);
		String removeBlockedElements = configuration.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,"true");
		String models = configuration.getAttribute(CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION, "");
		StringTokenizer st = new StringTokenizer(models, ";");
		List<String> additionalPaths = new ArrayList<String> ();
		while (st.hasMoreTokens()) {
			String model = st.nextToken();
			additionalPaths.add(model);
		}
		RunAsManualWizard.open(modelPath,  additionalPaths,  generatorstopcondition,  startnode,  new Boolean(removeBlockedElements));
	}

}
