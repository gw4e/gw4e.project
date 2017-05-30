package org.gw4e.eclipse.builder.marker;

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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;

public abstract class MultipleMarkerResolution extends org.eclipse.ui.views.markers.WorkbenchMarkerResolution {

	public MultipleMarkerResolution() {
 
	}

	@Override
	public String getDescription() {
		return getLabel();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		List<IMarker> similars = new ArrayList<IMarker>();
		MarkerResolutionGenerator mrg = new MarkerResolutionGenerator();
		for (int i = 0; i < markers.length; i++) {
			IMarker marker = markers[i];
			IMarkerResolution[] resolutions = mrg.getResolutions(marker);
			if (resolutions==null) continue;
			for (int j = 0; j < resolutions.length; j++) {
				if (resolutions[j].getClass().getName().equals(this.getClass().getName())){
					similars.add(marker);
				}
			}
		}
		IMarker[] ret =  new IMarker[similars.size()];
		similars.toArray(ret);
		return ret;
	}
}
