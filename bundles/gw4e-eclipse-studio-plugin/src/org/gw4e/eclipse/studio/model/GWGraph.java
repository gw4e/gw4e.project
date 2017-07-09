package org.gw4e.eclipse.studio.model;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.gw4e.eclipse.studio.facade.LayoutAlgoritmManager;
import org.gw4e.eclipse.studio.part.editor.EdgePart;
import org.gw4e.eclipse.studio.preference.PreferenceManager;
  

public class GWGraph extends GWNode {

	public static final String PROPERTY_NAME_UPDATED = "GraphNameUpdated";
	
	IFile file;
	private HashMap<VertexId, Vertex> vertices;
	private HashMap<Integer, GWLink> gWLinks;
	private boolean readOnly = false;
	private GWNode startElement = null;
	private String component;


	public GWGraph(UUID uuid,String name,String id) {
		super (uuid,name,id);
		this.vertices = new HashMap<VertexId, Vertex>();
		this.gWLinks = new HashMap<Integer, GWLink>();
	}

	public GWGraph(UUID uuid,String name,String id, List<Vertex> vertices) {
		super (uuid,name,id);
		this.vertices = new HashMap<VertexId, Vertex>();
		this.gWLinks = new HashMap<Integer, GWLink>();
		for (Vertex v : vertices) {
			this.vertices.put(new VertexId(v.getId()), v);
		}
	}

	public GWNode getStartElement() {
		return startElement;
	}

	public void setStartElement(GWNode startElement) {
		this.startElement = startElement;
	}
	
	public boolean addLink(GWLink l) {
		GWNode source = l.getSource();
		GWNode target =  l.getTarget();
 
		if (gWLinks.containsKey(l.hashCode())) {
			return false;
		} else if (source.containsOutNeighbor(l) || target.containsInNeighbor(l)) {
			return false;
		}
 
		gWLinks.put(l.hashCode(), l);
		source.addOutNeighbor(l);
		target.addInNeighbor(l);
	 
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, l);
		return true;
	}
	
	public boolean updateLink(GWLink l,String oldName, String newName) {
		GWLink current = this.gWLinks.remove(l.hashCode());
		if (current != null) {
			current.setName(newName);
			gWLinks.put(current.hashCode(), current);
		    getListeners().firePropertyChange(PROPERTY_UPDATED, null, current);
 		}
		
		return true;
	}
	
	public boolean updateEdge(GWEdge e, boolean blocked, String description,Double weight, Action action, Guard guard,Integer dependency,Map<String, Object> properties) {
		GWEdge gWEdge = (GWEdge) this.gWLinks.remove(e.hashCode());
		if (gWEdge != null) {
			gWEdge.setName(e.getName());
			gWEdge.setLabel(description);
			gWEdge.setAction(action);
			gWEdge.setGuard(guard);
			gWEdge.setWeight(weight);
			gWEdge.setBlocked(blocked);
			gWEdge.setDependency(dependency);
			gWEdge.setProperties(properties);
			gWLinks.put(gWEdge.hashCode(), gWEdge);
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, gWEdge);
		}
		
		return true;
	}
	
	public boolean containsLink(GWLink l) {
		if (l.getSource() == null || l.getTarget() == null) {
			return false;
		}
		return this.gWLinks.containsKey(l.hashCode());
	}

	public GWLink removeLink(GWLink l) {
		l.getSource().removeOutNeighbor(l);
		l.getTarget().removeInNeighbor(l);
		GWLink gWLink = this.gWLinks.remove(l.hashCode());
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, l);
		return gWLink;
	}

	public boolean containsVertex(Vertex vertex) {
		return this.vertices.get(new VertexId(vertex.getId())) != null;
	}

	public Vertex getVertex(VertexId id) {
		return vertices.get(id);
	}

	public boolean addVertex(Vertex vertex, boolean overwriteExisting) {
		Vertex current = this.vertices.get(new VertexId(vertex.getId()));
		if (current != null) {
			if (!overwriteExisting) {
				return false;
			}
			while (current.getInNeighborCount() > 0) {
				this.removeLink(current.getInNeighbor(0));
			}
			while (current.getOutNeighborCount() > 0) {
				this.removeLink(current.getOutNeighbor(0));
			}
		}
		 
		vertices.put(new VertexId(vertex.getId()), vertex);
		getListeners().firePropertyChange(PROPERTY_ADD, null, vertex);
		return true;
	}

	
	public void updateVertex (String id, String name, boolean blocked, boolean shared, String sharedName , String description, Requirement requirement,
			InitScript init, Map<String, Object> properties) {
		Vertex current = this.vertices.remove(new VertexId(id));
		if (current != null) {
			current.setName(name);
			current.setBlocked(blocked);
			current.setShared(shared);
			current.setSharedName(sharedName);
			current.setLabel(description);
			current.setRequirement(requirement);
			current.setInitScript(init);
			current.setProperties(properties);
			vertices.put(new VertexId(id), current);
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, current);
		}
	}
	
	public boolean updateVertex(String id, String oldName, String newName) {
		Vertex current = this.vertices.remove(new VertexId(id));
		if (current != null) {
			vertices.put(new VertexId(id), current);
			current.setName(newName);
		}
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, current);
 		return true;
	}
	
	public Vertex removeVertex(String id,String name) {
		Vertex vertex = vertices.remove(new VertexId(id));
		if (vertex==null) return null;
		while (vertex.getInNeighborCount() > 0) {
			this.removeLink(vertex.getInNeighbor(0));
		}
		while (vertex.getOutNeighborCount() > 0) {
			this.removeLink(vertex.getOutNeighbor(0));
		}
		getListeners().firePropertyChange(PROPERTY_REMOVE, null, vertex);
		return vertex;
	}
	
	public Vertex removeVertex(Vertex vertex) {
		return removeVertex(vertex.getId(),vertex.getName()) ;
	}
	
	public Set<VertexId> vertexKeys() {
		return this.vertices.keySet();
	}

	public  GWLink  getLink(String name) {
		Set<GWNode>  links = getLinks();
		for (GWNode gwLink : links) {
			if (name.equals(gwLink.getName())) return (GWLink)gwLink;
		}
		return null;
	}
	
	public Set<GWNode> getLinks() {
		return new HashSet<GWNode>(this.gWLinks.values());
	}

	public Set<GWNode> getVertices() {
		return new HashSet<GWNode>(this.vertices.values());
	}
	
	/**
	 * @return
	 */
	public List<GWNode> getVerticesChildrenArray() {
		List<GWNode> gWNodes = new ArrayList<GWNode>();
		Iterator<VertexId> iter = vertices.keySet().iterator();
		while (iter.hasNext()) {
			VertexId id = iter.next();
			gWNodes.add(vertices.get(id));
		}
		return gWNodes;
	}
	 
	public List<GWNode> getEdgesChildrenArray() {
		List<GWNode> gWNodes = new ArrayList<GWNode>();
		Iterator<GWLink> iter = gWLinks.values().iterator();
		while (iter.hasNext()) {
			gWNodes.add(iter.next());
		}
		return gWNodes;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name==null) {
			name = UUID.randomUUID().toString();
		}
		String oldName = this.name;
		this.name = name;
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_NAME_UPDATED, oldName, name);
		}
	}
	
	public void update(Map<String, Object> properties) {
		this.setProperties(properties);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, this);
		}
	}
	
	public void update(GWNode startElement) {
		this.setStartElement(startElement);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, this);
		}
	}

	public boolean isUpdatable(String property) {
		if (ModelProperties.PROPERTY_NAME.equalsIgnoreCase(property)) {
			return false;
		}
		return super.isUpdatable(property);
	}
  
	public void update(String description) {
		this.setLabel(description);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, this);
		}
	}
	
	public void updateDescription(String description) {
		this.setLabel(description);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, this);
		}
	}
	
	public void updateComponent(String value) {
		this.setComponent(value);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, this);
		}
	}
	
	public Object getAdapter(Class key) {
		if (key.equals(IFile.class)) {
			return this.getFile();
		}
		if (key.equals(Graph.class)) {
			int maxWidth  = -1;
			int maxHeight = -1;
			Graph graph = new Graph ();
			 
			Collection<Vertex> vertices = this.vertices.values();
			Map<Vertex,Node> dic = new HashMap<Vertex,Node> ();
			for (Vertex vertex : vertices) {
				Node node = (Node)vertex.getAdapter(Node.class);
				maxWidth = Math.max(maxWidth, vertex.getLayout().width);
				maxHeight = Math.max(maxHeight, vertex.getLayout().height);
				dic.put(vertex, node);
				node.setGraph(graph);
				graph.getNodes().add(node);
			}
		 	org.eclipse.gef4.geometry.planar.Dimension space = new org.eclipse.gef4.geometry.planar.Dimension(
					maxWidth+PreferenceManager.getSpaceWidthMargeForTreeLayoutAlgorithm(),
					maxHeight+PreferenceManager.getSpaceHeightMargeForTreeLayoutAlgorithm());
			graph.getAttributes().put(LayoutAlgoritmManager.TREELAYOUT_SPACE, space);
		 
			Collection<GWLink> edges = gWLinks.values();
			for (GWLink gwLink : edges) {
				Edge edge = (Edge) gwLink.getAdapter(Edge.class); 
				// The vertex attached to the edge at this are node linked to the ones created above ,  
				// We need to get the real ones , that is the ones related to the created above ...
				Vertex sourceVertex = (Vertex) edge.getSource().getAttributes().get(Vertex.OWNER);
				Vertex targetVertex = (Vertex) edge.getTarget().getAttributes().get(Vertex.OWNER);
				edge.setSource(dic.get(sourceVertex));
				edge.setTarget(dic.get(targetVertex));
				edge.setGraph(graph);
				graph.getEdges().add(edge);
			}
			return graph;
		}
		return null;
	}

	@Override
	public Object clone() {
		return null;
	}

	/**
	 * @return the file
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(IFile file) {
		this.file = file;
	} 
	
	
	/**
	 * @param registry
	 */
	public void initialize (Map registry) {
		List<GWNode> edges = getEdgesChildrenArray();
		for (GWNode gwNode : edges) {
			 EdgePart edgePart = (EdgePart) registry.get(gwNode);
			 edgePart.initializeBendpoints();
		}
	}
	
	public void resetEdgesRouteLayout (Map registry) {
		List<GWNode> edges = getEdgesChildrenArray();
		for (GWNode gwNode : edges) {
			 EdgePart edgePart = (EdgePart) registry.get(gwNode);
			 edgePart.clearBendpoints();
		}
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getName() {
		return file.getName();
	}
	
	public IProject getProject () {
		return file.getProject();
	}
 
	public GWGraph duplicate () {
		GWGraph gWGraph = new GWGraph(UUID.randomUUID(), this.getName(), UUID.randomUUID().toString());
		gWGraph.setFile(null);
		gWGraph.setName(this.getName());
		gWGraph.setProperties(this.getProperties());
		return gWGraph;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
}
