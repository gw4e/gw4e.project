package org.gw4e.eclipse.decorator;

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

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.swt.graphics.Image;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.product.GW4ENature;

public class ProjectLabelDecorator extends BaseLabelProvider implements ILabelDecorator {
	private static final ImageDescriptor GW_DESCRIPTOR = Activator.getImageDescriptor("icons/gw.png");
	private final ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	public ProjectLabelDecorator() {
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		if (image!=null) {
			if (element instanceof IProject) {
				IProject project = (IProject) element;
				boolean b = GW4ENature.hasGW4ENature(project);
				if (b) {
					DecorationOverlayIcon icon = new DecorationOverlayIcon(image, GW_DESCRIPTOR, IDecoration.TOP_LEFT);
					Image ret = (Image)resourceManager.get(icon);
					return ret;
				}
			}
		}
		return image;
	}

	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

}
