package org.gw4e.eclipse.studio.part.outline;

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

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;
import org.gw4e.eclipse.studio.Activator;
import org.gw4e.eclipse.studio.editor.properties.EdgeSectionProvider;
import org.gw4e.eclipse.studio.editpolicies.GW4EdgeDeletePolicy;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.part.editor.EdgePart;

public class EdgeTreeEditPart extends GW4EAbstractTreeEditPart  implements EdgeSectionProvider {

	protected List<GWNode> getModelChildren() {
		List<GWNode> ret = new ArrayList<GWNode>();
		return ret;
	}
	
	public void refreshVisuals() {
		GWNode model = (GWNode) getModel();
		setWidgetText(model.getName());
		setWidgetImage(Activator.getEdgeImage());
	}

	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySource.class)) {
			EdgePart twinEditPart = (EdgePart)registry.get(getModel());
			if (twinEditPart!=null) {
				return twinEditPart.getAdapter(adapter);
			}
		}
		if (adapter.equals(EdgePart.class)) {
			EdgePart twinEditPart = (EdgePart)registry.get(getModel());
			return twinEditPart;
		}
		return super.getAdapter(adapter);
	}
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GW4EdgeDeletePolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new GW4EdgeDeletePolicy());
	}
}
