package org.gw4e.eclipse.studio.preference;

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

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.gw4e.eclipse.studio.Activator;

public class PreferenceManager implements PreferenceConstants {
	private static  Image imageBlocked = null;
	private static  Image imageActionScripted = null;
	private static  Image imageShared = null;
    private static  Image imageGuardScripted = null;
    
	private PreferenceManager() {
	}
	
	
	public static boolean openSharedGraphmlFile () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getBoolean(P_OPEN_SHARED_GRAPHML_FILE);
	}
	
	public static boolean isAutomaticResizingOn () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getBoolean(P_AUTO_RESIZING);
	}

	public static int getWidthMarge () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_AUTO_NODE_DIMENSION_MARGE_WIDTH);
	}
	
	public static int getHeightMarge () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_AUTO_NODE_DIMENSION_MARGE_HEIGHT);
	}
	
	public static Dimension getNodeDimension () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int width = store.getInt(P_NODE_DIMENSION_WIDTH);
		int height =  store.getInt(P_NODE_DIMENSION_HEIGHT);
		return new Dimension(width, height);
	}
	
	public static int getRowCountForVertexTextDescription () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_ROW_COUNT_FOR_VERTEX_TEXT_DESCRIPTION);
	}
	
	public static int getRowCountForEdgeTextDescription () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_ROW_COUNT_FOR_EDGE_TEXT_DESCRIPTION);
	}
	
	public static int getRowCountForVertexTextRequirements () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_ROW_COUNT_FOR_TEXT_REQUIREMENTS);
	}

	public static int getMaxRowInTooltip () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_MAX_ROW_IN_TOOLTIPS);
	}
	
	public static int getSpaceHeightMargeForTreeLayoutAlgorithm () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_SPACE_HEIGHT_MARGE_FOR_TREE_LAYOUT_ALGORITHM);
	}
	
	public static int getSpaceWidthMargeForTreeLayoutAlgorithm () {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		return store.getInt(P_SPACE_WIDTH_MARGE_FOR_TREE_LAYOUT_ALGORITHM);
	}

	
	/**
	 * @return the imageblocked
	 */
	public static Image getImageBlocked() {
		if (imageBlocked==null) {
			try {
				imageBlocked = ImageDescriptor.createFromURL(
						new URL("platform:/plugin/org.eclipse.jdt.ui/icons/full/obj16/fatalerror_obj.png")).createImage();
			} catch (MalformedURLException e) {
			}
		}
		return imageBlocked;		
	}

	/**
	 * @return the imageactionscripted
	 */
	public static Image getImageActionScripted() {
		if (imageActionScripted==null) {
			try {
				imageActionScripted = ImageDescriptor.createFromURL(
						new URL("platform:/plugin/org.eclipse.debug.ui/icons/full/obj16/debugt_obj.gif")).createImage();
			} catch (Exception e) {
				try {
					imageActionScripted = ImageDescriptor.createFromURL(
							new URL("platform:/plugin/org.eclipse.debug.ui/icons/full/obj16/debugt_obj.png")).createImage();
				} catch (MalformedURLException e1) {
				}
			}
		}
		return imageActionScripted;
	}

	/**
	 * @return the imageactionscripted
	 */
	public static Image getImageShared() {
		if (imageShared==null) {
			try {
				imageShared = ImageDescriptor.createFromURL(
						new URL("platform:/plugin/org.eclipse.debug.ui/icons/full/elcl16/synced.png")).createImage();
			} catch (MalformedURLException e) {
			}
		}
		return imageShared;
	}

	
	/**
	 * @return the imageguardscripted
	 */
	public static Image getImageGuardScripted() {
		if (imageGuardScripted==null) {
			try {
				imageGuardScripted = ImageDescriptor.createFromURL(
					 	new URL("platform:/plugin/org.eclipse.debug.ui/icons/full/elcl16/stepbystep_co.gif")).createImage();
			} catch (Exception e) {
				try {
					imageGuardScripted = ImageDescriptor.createFromURL(
						 	new URL("platform:/plugin/org.eclipse.debug.ui/icons/full/elcl16/stepbystep_co.png")).createImage();
				} catch (MalformedURLException e1) {
				}
			}
		}
		return imageGuardScripted;
	}

	
	public static Font getStartNodeFont () {
	 Font f = new Font(Display.getCurrent(), "Arial", 8, SWT.NORMAL);
	 return f;
	}
}
