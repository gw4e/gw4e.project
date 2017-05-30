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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.editpolicies.GW4EVertexDeletePolicy;
import org.gw4e.eclipse.studio.model.GWNode;

public abstract class GW4EAbstractTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener {
	protected OutLineFilter filter;
	protected Map registry;
	
	public void activate() {
		super.activate();
		((GWNode) getModel()).addPropertyChangeListener(this);
		
	}

	public void deactivate() {
		((GWNode) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	public GWNode getModel(){
		return (GWNode) super.getModel();
	}
	
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GW4EVertexDeletePolicy());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
	}

	public void setFilter (OutLineFilter filter) {
		this.filter=filter;
	}

	/**
	 * @param registry the registry to set
	 */
	public void setRegistry(Map registry) {
		this.registry = registry;
	}
	

}
