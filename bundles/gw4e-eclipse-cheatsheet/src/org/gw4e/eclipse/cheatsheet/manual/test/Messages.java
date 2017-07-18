package org.gw4e.eclipse.cheatsheet.manual.test;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.gw4e.eclipse.cheatsheet.manual.test.messages"; //$NON-NLS-1$
	public static String ProjectImport_error;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
