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

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

/**
 * A class defining the UI (listViewer of tabs) that will compose our gw4e
 * Launcher
 *
 */
public class GW4ETabGroup extends AbstractLaunchConfigurationTabGroup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.
	 * debug.ui.ILaunchConfigurationDialog, java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String arg1) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { 
				// A launch configuration tab that displays end edits GraphWalker offline command argument
				new GW4ELaunchConfigurationTab(),
				// A launch configuration tab that displays and edits program
				// arguments, VM arguments, and working directory launch
				// configuration attributes.
				new JavaArgumentsTab(),
				// A launch configuration tab that displays and edits the user
				// and bootstrap classes comprising the classpath launch
				// configuration attribute.
				new JavaClasspathTab(),
				// A launch configuration tab that displays and edits the VM
				// install launch configuration attributes.
				new JavaJRETab(),
				// Launch configuration tab for configuring the environment
				// passed into Runtime.exec(...) when a config is launched.
				new EnvironmentTab(),
				// Launch configuration tab used to specify the location a
				// launch configuration is stored in, whether it should appear
				// in the favorites listViewer, and perspective switching behavior for
				// an associated launch.
				new CommonTab() };
		setTabs(tabs);
	}

}
