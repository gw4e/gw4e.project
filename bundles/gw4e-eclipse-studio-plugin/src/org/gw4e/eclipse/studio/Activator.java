package org.gw4e.eclipse.studio;

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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "gw4e-eclipse-studio-plugin"; //$NON-NLS-1$
	private static Image startVertexImage = null;
	private static Image sharedVertexImage = null;
	private static Image vertexImage = null;
	private static Image edgeImage = null;
	private static Image resetEdgeImage = null;
	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
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
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	static Color vertexImageColor;
	public static Color getVertexImageColor () {
		if (vertexImageColor==null) {
			Display display = Display.getCurrent();
			vertexImageColor = new Color (display,92,157,216);
		}
		return vertexImageColor;
	}
	
	static Color startVertexImageColor;
	public static Color getStartVertexImageColor () {
		if (startVertexImageColor==null) {
			Display display = Display.getCurrent();
			startVertexImageColor = new Color (display,84,130,53);
		}
		return startVertexImageColor;
	}
	
	static Color sharedVertexImageColor;
	public static Color getSharedVertexImageColor () {
		if (sharedVertexImageColor==null) {
			Display display = Display.getCurrent();
			sharedVertexImageColor = new Color (display,203,130,93);
		}
		return sharedVertexImageColor;
	}
	
	public static Image getSharedVertexImage () {
		if (sharedVertexImage==null) {
			sharedVertexImage = getImageDescriptor("icons/sharedvertex.png").createImage();
		}
		return sharedVertexImage;
	}
	public static Image getStartVertexImage () {
		if (startVertexImage==null) {
			startVertexImage = getImageDescriptor("icons/startvertex.png").createImage();
		}
		return startVertexImage;
	}
	public static Image getVertexImage () {
		if (vertexImage==null) {
			vertexImage = getImageDescriptor("icons/vertex.png").createImage();
		}
		return vertexImage;
	}
	
	public static Image getEdgeImage () {
		if (edgeImage==null) {
			edgeImage = getImageDescriptor("icons/connection.gif").createImage();
		}
		return edgeImage;
	}
	
	public static Image getResetEdgeImage () {
		if (resetEdgeImage==null) {
			resetEdgeImage =  getResetEdgeImageDescriptor () .createImage();
		}
		return resetEdgeImage;
	}
	
	public static ImageDescriptor getResetEdgeImageDescriptor () {
		return getImageDescriptor("icons/resetconnection.png");
	}
	
	public static ImageDescriptor getDisabledResetEdgeImageDescriptor () {
		return getImageDescriptor("icons/resetconnectiondis.png");
	}
	
}
