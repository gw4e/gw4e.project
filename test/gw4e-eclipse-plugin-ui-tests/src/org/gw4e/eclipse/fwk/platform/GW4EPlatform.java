package org.gw4e.eclipse.fwk.platform;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;
public class GW4EPlatform {
	
	public static boolean isEclipse47 () {
		Version version = Platform.getBundle("org.eclipse.platform").getVersion();
		return (version.getMajor() == 4) && (version.getMinor() == 7);
	}
	
}
