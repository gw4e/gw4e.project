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
import java.util.Comparator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilter;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilterBuilderImpl;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilterDirector;
import org.gw4e.eclipse.studio.editor.properties.GraphSectionProvider;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.part.editor.AbstractGW4EEditPart;

public class GraphTreeEditPart extends GW4EAbstractTreeEditPart implements GraphSectionProvider {

	public GraphTreeEditPart() {
	}
 
	protected List<GWNode> getModelChildren() {
		
		ExecutableFilterDirector director = new ExecutableFilterDirector (new ExecutableFilterBuilderImpl());
		ExecutableFilter executableFilter = director.construct(filter);
		
		List<GWNode>  ret = new ArrayList<GWNode> ();
		
		List<GWNode>  vertices = new ArrayList<GWNode> ();
		GWGraph gWGraph = (GWGraph) getModel();
		List<GWNode> gWNodes = (List<GWNode>) gWGraph.getVerticesChildrenArray();
		for (GWNode gWNode : gWNodes) {
			if (executableFilter.meetCriteria((GraphElement)gWNode)!=null) {
				vertices.add(gWNode);
			}
		}
		
		List<GWNode>  edges = new ArrayList<GWNode> ();
		gWNodes = (List<GWNode>) gWGraph.getEdgesChildrenArray();
		for (GWNode gWNode : gWNodes) {
			if (executableFilter.meetCriteria((GraphElement)gWNode)!=null) {
				edges.add(gWNode);
			}
		}
		
		Comparator<GWNode> c = new Comparator<GWNode>() {
			@Override
			public int compare(GWNode node1, GWNode node2) {
				return node1.getName().compareTo(node2.getName());
			}
 		};
		
		vertices.sort (c);
		edges.sort (c);
		
		ret.addAll(vertices);
		ret.addAll(edges);
		return ret;
	}

	// allow to display properties according to the selected element in the outline tree  
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySource.class)) {
			AbstractGW4EEditPart twinEditPart = (AbstractGW4EEditPart)registry.get(getModel());
			if (twinEditPart!=null) {
				return twinEditPart.getAdapter(adapter);
			}
		}
		return super.getAdapter(adapter);
	}
	
	public EditPartViewer getViewer() {
		RootEditPart root = getRoot();
		if (root == null) {
			return null;
		}
		return root.getViewer();
	}
	
	public void setParent(EditPart parent) {
		super.setParent(parent); 
	}
	
	protected EditPart createChild(Object model) {
		EditPartViewer epv =  getViewer();
		if (epv ==null) {
			 epv =  getViewer();
		}
		EditPartFactory epf = epv.getEditPartFactory();
		EditPart part = epf.createEditPart(this, model);
		return part;
	}


}
