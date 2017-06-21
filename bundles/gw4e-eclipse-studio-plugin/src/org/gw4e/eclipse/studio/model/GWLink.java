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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.draw2d.geometry.Point;
import org.gw4e.eclipse.studio.commands.LinkCreateCommand;
import org.gw4e.eclipse.studio.commands.LinkDeleteCommand;

public abstract class GWLink extends GraphElement implements Comparable<GWLink>  {

	public static final String PROPERTY_NAME_UPDATED = "LinkNameUpdated";
	public static final String PROPERTY_BENDPOINTS_UPDATED = "LinkBendPointUpdated";
	
	private GWNode source, target;
	private Double weight;
	public List<Point> bendpoints = new ArrayList<Point>();
	private boolean reflexive = false;
	
	public GWLink(GWGraph gWGraph,UUID uuid, String name,String id) {
		this(gWGraph,uuid,name,id,1d);
	}

	public GWLink(GWGraph gWGraph,UUID uuid,String name,String id, Double weight) {
		super(null,uuid,name,id);
		this.setWeight(weight);
	}

	public GWNode getNeighbor(GWNode current) {
		if (!(current.equals(source) || current.equals(target))) {
			return null;
		}
		return (current.equals(source)) ? target : source;
	}

	public GWNode getSource() {
		return this.source;
	}

	public GWNode getTarget() {
		return this.target;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public int compareTo(GWLink other) {
		if (this.weight > other.weight) return 1;
		if (this.weight == other.weight) return 0;
		return -1;
	}
	
	public String toString() {
		return  this.gWGraph + " " + this.getName() + "( {" + source + ", " + target + "}, " + weight + ")";
	}

	public int hashCode() {
		return (this.getUUID().toString() + source.getUUID().toString() + target.getUUID().toString()).hashCode();
	}

	public boolean equals(Object other) {
		if (!(other instanceof GWLink)) {
			return false;
		}
		GWLink o = (GWLink) other;
		return o.getUUID().equals(this.getUUID()) && o.source.equals(this.source) && o.target.equals(this.target);
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(GWNode source) {
		this.reflexive=false;
		this.source = source;
		if (source!=null && target!=null && source.equals(target)) {
			this.reflexive=true;
		}
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(GWNode target) {
		this.reflexive=false;
		this.target = target;
		if (source!=null && target!=null && source.equals(target)) {
			this.reflexive=true;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.model.GWNode#setName(java.lang.String)
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
	

	

	/**
	 * @return the bendpoints
	 */
	public Iterator<Point> getBendpointsIterator() {
		return bendpoints.iterator();
	}
	 
	public Point getBendpoints(int index) {
		return bendpoints.get(index);
	}
	
	public void clearBendpoints () {
		bendpoints.clear();
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_BENDPOINTS_UPDATED, null, null);
		}
	}
	
	public void setDefaultBendpoints(List<Point> points) {
		if (bendpoints.size()>0) return;
		bendpoints.addAll(points);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_BENDPOINTS_UPDATED, null, null);
		}
	}
	
	public void setBendpoints(int index, Point location) {
		bendpoints.set(index, location);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_BENDPOINTS_UPDATED, null, null);
		}
	}
	
	public void addBendpoints(int index, Point location) {
		bendpoints.add(index,location);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_BENDPOINTS_UPDATED, null, null);
		}
	}
	
	public void removeBendpoints(int index) {
		bendpoints.remove(index);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_BENDPOINTS_UPDATED, null, null);
		}
	}
	
	public int bendpointsSize( ) {
		return bendpoints.size();
	}
	
	@Override
	public void updateGraph (String id, String oldName, String newName ) {
		this.getGraph().updateLink(this, oldName, newName);
	}

	/**
	 * @return the reflexive
	 */
	public boolean isReflexive() {
		return reflexive;
	}
	
	/**
	 * 
	 */
	public void reconnect (GWNode source, GWNode target) {
		LinkDeleteCommand ldc =  new LinkDeleteCommand();
		ldc.setModel(this);
		ldc.execute();

		LinkCreateCommand lcc = new LinkCreateCommand();
		lcc.setSource(source);
		lcc.setTarget(target);
		lcc.setLink(this);
		lcc.setGraph(this.getGraph());
		lcc.execute();
	}
 
}
