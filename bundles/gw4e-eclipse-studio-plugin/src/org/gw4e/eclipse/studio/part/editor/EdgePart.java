package org.gw4e.eclipse.studio.part.editor;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.editor.properties.EdgeSectionProvider;
import org.gw4e.eclipse.studio.editpolicies.GW4EEdgeDirectEditPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4ELinkBendpointEditPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4EdgeDeletePolicy;
import org.gw4e.eclipse.studio.figure.EdgeFigure;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.properties.EdgeGW4EEditPartProperties;

public class EdgePart extends AbstractConnectionEditPart implements PropertyChangeListener, EdgeSectionProvider {
	IFigure figure;

	public EdgePart() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GW4EdgeDeletePolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new GW4EdgeDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new GW4EEdgeDirectEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new GW4ELinkBendpointEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		EdgeFigure figure = (EdgeFigure) getFigure();
		GWEdge model = (GWEdge) getModel();
		figure.setName(model.getName());
		
		Connection connection = getConnectionFigure();
		 
		List<AbsoluteBendpoint> figureConstraint = new ArrayList<AbsoluteBendpoint>();
		Iterator<Point> iter = ((GWLink) getModel()).getBendpointsIterator(); 
		while(iter.hasNext()) {
			Point p = iter.next();
			figureConstraint.add(new AbsoluteBendpoint(p));
		}
		connection.setRoutingConstraint(figureConstraint);
		 
		figure.setTooltipText(this.getTooltipData());
		figure.setBlockedOrGuarded(model.isBlocked(),model.getGuard().getSource());
		figure.setActionScripted(model.getAction().getSource()); 
	}
	 
	@Override
	protected IFigure createFigure() {
		if (figure == null) {
			figure = new EdgeFigure();
		}
		return figure;
	}

	public GWNode getModel() {
		return (GWNode) super.getModel();
	}
	
	protected String getTooltipData() {
		return getModel().getName();
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			performDirectEditing();
		}
	}

	private void performDirectEditing() {
		Label label = ((EdgeFigure) getFigure()).getLabel();
		EdgeDirectEditManager manager = new EdgeDirectEditManager(this, TextCellEditor.class,
				new EdgeCellEditorLocator(label), label);
		manager.show();
	}

	@Override
	protected void activateFigure() {
		super.activateFigure();
		getModel().addPropertyChangeListener(this);
		 
	}

	@Override
	protected void deactivateFigure() {
		super.deactivateFigure();
		 getModel().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GWLink.PROPERTY_NAME_UPDATED)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(GWLink.PROPERTY_BENDPOINTS_UPDATED)) {
			refreshVisuals();
 		}
		if (evt.getPropertyName().equals(GWLink.PROPERTY_BENDPOINTS_CLEARED)) {
			refreshVisuals();
			GraphSelectionManager.ME.getSelection().redraw();
 		}
		if (evt.getPropertyName().equals(GWEdge.PROPERTY_UPDATED)) {
			refreshVisuals();
		}
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			return new EdgeGW4EEditPartProperties(this);
		}
		if (adapter.equals(EdgePart.class)) {
			return this;
		}
		return super.getAdapter(adapter);
	}
	
	public void initializeBendpoints () {
		propertyChange(new PropertyChangeEvent(this, GWLink.PROPERTY_BENDPOINTS_UPDATED, null, null));
	}
	
	public void  clearBendpoints () {
	  ((GWLink)getModel()).clearBendpoints();
	}
	
}
 
