package org.gw4e.eclipse.fwk.conditions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.gw4e.eclipse.facade.ResourceManager;

public class ResourceExists extends DefaultCondition {
	IPath  path ;
	public ResourceExists(IPath path) {
		super();
		this.path = path;
	}

	@Override
	public boolean test() throws Exception {
		IResource resource = ResourceManager.getResource(path.toString());
		return resource!=null && resource.exists();
	}

	@Override
	public String getFailureMessage() {
		return   path  + " does not exists" ;
	}

}
