package org.gw4e.eclipse;

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

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.gw4e.eclipse.builder.BuildPoliciesCache;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
 
	// The plug-in ID
	public static final String PLUGIN_ID = "gw4e-eclipse-plugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	IResourceChangeListener listener ;
	/**
	 * The constructor
	 */
	public Activator() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		// Is this ugly ? ... certainly ... anyway it works ...
		makeSurePreferenceInitalizerIsCalled ();
		startSaveParticipant ();
		// Make sure change done the the project preference are reflected in the BuildPolicy stuff
		SettingsManager.addListener(new BuildPolicyManager());
		SettingsManager.addListener(new BuildPoliciesCache());
	}
	
	private void makeSurePreferenceInitalizerIsCalled () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.getString("");		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		removeSaveParticipant ();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	
	
	public static ImageDescriptor getDefaultImageDescriptor() {
		return imageDescriptorFromPlugin(PLUGIN_ID, "icons/wizban/gw_64.png");
	}	
	/**
	 * 
	 */
	private void removeSaveParticipant () {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		 workspace.removeResourceChangeListener(listener);
	}
	
	/**
	 * 
	 */
	private void startSaveParticipant () {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		listener = new ResourceManager();
		workspace.addResourceChangeListener(listener);
	}
}
