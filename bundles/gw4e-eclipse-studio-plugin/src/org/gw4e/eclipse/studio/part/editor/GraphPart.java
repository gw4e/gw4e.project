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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.editor.GW4EEditor;
import org.gw4e.eclipse.studio.editor.properties.GraphSectionProvider;
import org.gw4e.eclipse.studio.editpolicies.GW4EEditLayoutPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4ENodeGraphicalNodeEditPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4EVertexDeletePolicy;
import org.gw4e.eclipse.studio.figure.GraphFigure;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.properties.GW4EGraphEditPartProperties;

public class GraphPart extends AbstractGW4EEditPart implements GraphSectionProvider, ITabbedPropertySheetPageContributor {
	 
	
	public GraphPart() {
	}

	@Override
	protected IFigure createFigure() {
		IFigure figure = new GraphFigure();
		return figure;
	}

	
	public void activate() {
		super.activate();
		GraphSelectionManager.ME.selectionPartChanged(this);
	}
	
	public void deactivate() {
		super.deactivate();
		GraphSelectionManager.ME.selectionPartChanged(null);
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GW4EEditLayoutPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GW4EVertexDeletePolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GW4ENodeGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
	}

	protected void refreshVisuals() {
		GraphFigure figure = (GraphFigure) getFigure();
		
		GWGraph model = (GWGraph) getModel();
		figure.setName(model.isReadOnly() ?  "Read only. Convert this graph to Json." : "");
		figure.setTooltipText(this.getTooltipData());
	}

	public List<GWNode> getModelChildren() {
		return ((GWGraph) getModel()).getVerticesChildrenArray();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(GWGraph.PROPERTY_NAME_UPDATED)) {
			refreshVisuals();
			refreshSourceConnections();
			refreshTargetConnections();
		}
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
			refreshChildren();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_REMOVE)) {
			refreshChildren();
		}
		if (evt.getPropertyName().equals(GWNode.PROPERTY_UPDATED)) {
			refreshChildren();
		}
		 
	}

	// Workaround to ramdom processStale (..) NPE :-(
	private void escape () {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
		} catch (AWTException e) {
		}
	}
	
	@Override 
	public Object getAdapter(Class key) {
	    if (key == SnapToHelper.class) {
	        List<SnapToHelper> helpers = new ArrayList<SnapToHelper>();
	        if (Boolean.TRUE.equals(getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED))) {
	            helpers.add(new SnapToGeometry(this));
	        }
	        if (Boolean.TRUE.equals(getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED))) {
	            helpers.add(new SnapToGrid(this));
	        }
	        if(helpers.size()==0) {
	            return null;
	        } else {
	            return new CompoundSnapToHelper(helpers.toArray(new SnapToHelper[0]));
	        }
	    }
	    if (key == IPropertySource.class) {
	       return new GW4EGraphEditPartProperties(this);
	    }
	    return null;
	}

	@Override
	protected String getTooltipData() {
		return ((GWNode)this.getModel()).getName();
	}

	@Override
	public String getContributorId() {
		return GW4EEditor.ID;
	} 


}
