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

import java.util.Map;
import java.util.UUID;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Node;
 
public class GWEdge extends GWLink {
	public static final String PROPERTY_UPDATED = "EdgePropertyUpdated";
	
	 
	Action action = Action.NULL;
	Guard guard = Guard.NULL;
	Integer dependency;
	

	public GWEdge(GWGraph gWGraph,UUID uuid ,String name,String id) {
		super(gWGraph,uuid,name,id,1d);
	}

	public GWEdge(GWGraph gWGraph,UUID uuid,String name, String id,Double weight) {
		super(gWGraph,uuid, name,id,weight);
	}

	public Integer getDependency() {
		return dependency;
	}

	public void setDependency(Integer dependency) {
		this.dependency = dependency;
	}


	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the guard
	 */
	public Guard getGuard() {
		return guard;
	}

	/**
	 * @param guard the guard to set
	 */
	public void setGuard(Guard guard) {
		this.guard = guard;
	}
	
	public void update (boolean blocked, String description, Double weight, Action action, Guard guard,Integer dependency,Map<String, Object> properties) {
		this.getGraph().updateEdge(this,blocked,description,weight,action,guard,dependency,properties);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
		}
	}
	
	public Object getAdapter(Class key) {
		if (key.equals(Edge.class)) {
			Node source = (Node)this.getSource().getAdapter(Node.class);
			Node target = (Node)this.getTarget().getAdapter(Node.class);
			Edge edge =  new Edge (source,target);
			edge.getAttributes().put(OWNER, GWEdge.this);
			return edge;
		}
		return null;
	}
	
	
	public  GWEdge duplicate (GWGraph graph) {
		GWEdge edge = new GWEdge(graph,UUID.randomUUID() ,getName(), getId());
		edge.setAction(this.getAction());
		edge.setBlocked(this.isBlocked());
		edge.setDependency(this.getDependency());
		edge.setGuard(this.getGuard());
		edge.setLabel(this.getLabel());
		edge.setLayout(this.getLayout());
		edge.setName(this.getName());
		edge.setProperties(this.getProperties());
		edge.setWeight(this.getWeight());
		int index=0;
		for (Point point : bendpoints) {
			edge.setBendpoints(index, point);
			index++;
		}
		return edge;
	}

	@Override
	public Object clone() {
		return null;
	}
}
