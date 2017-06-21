package org.gw4e.eclipse.studio.part.editor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.gw4e.eclipse.studio.editor.GW4EEditor;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.editor.properties.GraphSectionProvider;
import org.gw4e.eclipse.studio.editpolicies.GW4EEditLayoutPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4ENodeGraphicalNodeEditPolicy;
import org.gw4e.eclipse.studio.editpolicies.GW4EVertexDeletePolicy;
import org.gw4e.eclipse.studio.figure.GraphFigure;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
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

	FanRouter router = null;
	@Override
	public void activate() {
		super.activate();
		if (router == null) {
			ScalableRootEditPart root = (ScalableRootEditPart) getViewer().getRootEditPart();
			ConnectionLayer connLayer = (ConnectionLayer) root.getLayer(LayerConstants.CONNECTION_LAYER);
			GraphicalEditPart contentEditPart = (GraphicalEditPart) root.getContents();
			router = new FanRouter();
			router.setSeparation(100);
			ShortestPathConnectionRouter spRouter = new ShortestPathConnectionRouter(contentEditPart.getFigure());

			router.setNextRouter(spRouter);
			connLayer.setConnectionRouter(router);
		}
		GraphSelectionManager.ME.selectionPartChanged(this);
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			GraphSelectionManager.ME.selectionPartChanged(null);
		}
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
		if (evt.getPropertyName().equals(GWLink.PROPERTY_BENDPOINTS_UPDATED)) {
			GraphFigure figure = (GraphFigure) getFigure();
			 
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
