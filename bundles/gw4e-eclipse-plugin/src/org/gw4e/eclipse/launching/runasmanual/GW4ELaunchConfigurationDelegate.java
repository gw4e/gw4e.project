package org.gw4e.eclipse.launching.runasmanual;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.ui.ModelData;
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
		String modelPath = getMainModel(configuration);
		String generatorstopcondition = getMainPathGenerators(configuration).getPathGenerator();
		String startnode = configuration.getAttribute(CONFIG_LAUNCH_STARTNODE, (String) null);
		String removeBlockedElements = configuration.getAttribute(CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION,"true");
		String omitEgdeswithoutDescription = configuration.getAttribute(GW4E_LAUNCH_CONFIGURATION_BUTTON_ID_OMIT_EMPTY_EDGE_DESCRIPTION,"true");
		String models = configuration.getAttribute(CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION, "");
		StringTokenizer st = new StringTokenizer(models, ";");
		ModelData[] additionalModels = getModels(configuration);
		RunAsManualWizard.open(modelPath,  
				additionalModels, 
				generatorstopcondition, 
				startnode,  
				new Boolean(removeBlockedElements), 
				new Boolean (omitEgdeswithoutDescription));
	}

	private ModelData[] getModels(ILaunchConfiguration config) throws CoreException {
		List<ModelData> temp = new ArrayList<ModelData>();
		String paths = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		 
		if (paths == null || paths.trim().length() == 0)
			return new ModelData[0];
		StringTokenizer st = new StringTokenizer(paths, ";");
		st.nextToken(); // the main model path
		st.nextToken(); // main model : Always "1"
		st.nextToken(); // the main path generator
		while (st.hasMoreTokens()) {
			String path = st.nextToken();
			IFile file = (IFile) ResourceManager
					.getResource(new Path(path).toString());
			if (file==null) continue;
			ModelData data = new ModelData(file);
			boolean selected = st.nextToken().equalsIgnoreCase("1");
			String pathGenerator = st.nextToken();
			data.setSelected(selected);
			data.setSelectedPolicy(pathGenerator);
			if (selected) temp.add(data);
		}
		ModelData[] ret = new ModelData[temp.size()];
		temp.toArray(ret);
		return ret;
	}
	
	private String getMainModel(ILaunchConfiguration config) throws CoreException {
		String ret = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		StringTokenizer st = new StringTokenizer(ret, ";");
		if (st.hasMoreTokens())
			return st.nextToken();
		return "";
	}
	
	private BuildPolicy getMainPathGenerators(ILaunchConfiguration config) throws CoreException {
		String paths = config.getAttribute(CONFIG_GRAPH_MODEL_PATHS, "");
		if (paths == null || paths.trim().length() == 0)
			return null;
		StringTokenizer stFirst = new StringTokenizer(paths, ";");
		stFirst.nextToken(); // the main model path
		stFirst.nextToken(); // main model : Always "1"
		String generators = stFirst.nextToken(); // the main path generator
		if (generators == null || generators.trim().length() == 0)
			return null;
		StringTokenizer st = new StringTokenizer(generators, ";");
		if (st.hasMoreTokens()) {
			String path = st.nextToken();
			if (path==null || path.trim().length()==0) return null;
			String model = getMainModel(config);
			IFile file = (IFile) ResourceManager
					.getResource(new Path(model).toString());
			if (file==null) return null;
			ModelData md = new ModelData(file);
			BuildPolicy[] policies = md.getPolicies();
			for (BuildPolicy buildPolicy : policies) {
				if (path.trim().equals(buildPolicy.getPathGenerator())) {
					return buildPolicy;
				}
			}
			if (policies.length > 0) return policies[0];
		}
		return null;
	}
}
