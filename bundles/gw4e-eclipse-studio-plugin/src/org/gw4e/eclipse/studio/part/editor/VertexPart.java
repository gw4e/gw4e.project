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
import java.util.List;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.gw4e.eclipse.studio.editor.properties.VertexSectionProvider;
import org.gw4e.eclipse.studio.editpolicies.GW4ENodeGraphicalNodeEditPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4EVertexDeletePolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4EVertexDirectEditPolicy;
import org.gw4e.eclipse.studio.figure.VertexFigure;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.properties.GW4EVertexEditPartProperties;

public class VertexPart extends AbstractGW4EEditPart implements NodeEditPart,VertexSectionProvider {

	protected IFigure figure = null;
	protected GW4EVertexEditPartProperties properties= null;
	public VertexPart() {
	}

	@Override
	protected IFigure createFigure() {
		if (figure == null) {
			figure = new VertexFigure();
		}
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GW4EVertexDeletePolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GW4ENodeGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new GW4EVertexDirectEditPolicy());
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			performDirectEditing();
		}
	}

	private void performDirectEditing() {
		Label label = ((VertexFigure) getFigure()).getName();
		VertexDirectEditManager manager = new VertexDirectEditManager(this, TextCellEditor.class,
				new VertextCellEditorLocator(label), label);
		manager.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#
	 * getModelSourceConnections()
	 */
	@Override
	protected List<GWLink> getModelSourceConnections() {
		GWNode model = (GWNode) getModel();
		return model.getOutNeighbors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#
	 * getModelTargetConnections()
	 */
	@Override
	protected List<GWLink> getModelTargetConnections() {
		GWNode model = (GWNode) getModel();
		return model.getInNeighbors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	protected void refreshVisuals() {
		VertexFigure figure = (VertexFigure) getFigure();
		Vertex model = (Vertex) getModel();
		figure.setName(model.getName());
		figure.setLayout(model.getLayout());
		figure.setTooltipText(this.getTooltipData());
		figure.setIcons(model.isBlocked(),model.getInitScript().getSource(),model.isShared());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gw4e.eclipse.studio.part.editor.AbstractGW4EEditPart#
	 * getTooltipData()
	 */
	@Override
	protected String getTooltipData() {
		return ((GWNode) this.getModel()).getName();
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return ((VertexFigure) getFigure()).getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return ((VertexFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return ((VertexFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return ((VertexFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(Vertex.PROPERTY_NAME_UPDATED)) {
			refreshVisuals();
			refreshSourceConnections();
			refreshTargetConnections();
		}
		if (evt.getPropertyName().equals(Vertex.PROPERTY_UPDATED)) {
			refreshVisuals();
			refreshSourceConnections();
			refreshTargetConnections();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_LAYOUT)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_ADD)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_REMOVE)) {
			refreshVisuals();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_CONNECTION_IN_ADDED)) {
			refreshVisuals();
			refreshTargetConnections();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_CONNECTION_OUT_ADDED)) {
			refreshVisuals();
			refreshSourceConnections();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_CONNECTION_IN_REMOVED)) {
			refreshVisuals();
			refreshTargetConnections();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_CONNECTION_OUT_REMOVED)) {
			refreshVisuals();
			refreshSourceConnections();
		}
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			if (properties==null) {
				properties = new GW4EVertexEditPartProperties(this);
			}
			return properties;
		}
		return null;
	}

}
