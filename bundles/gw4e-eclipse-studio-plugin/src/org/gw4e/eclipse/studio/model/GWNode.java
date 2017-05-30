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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.draw2d.geometry.Rectangle;

public abstract class GWNode  {

	public static final String PROPERTY_LAYOUT = "NodeLayout";

	public static final String PROPERTY_CONNECTION_IN_ADDED = "ConnectionInAdded";
	public static final String PROPERTY_CONNECTION_OUT_ADDED = "ConnectionOutAdded";
	public static final String PROPERTY_CONNECTION_IN_REMOVED = "ConnectionInRemoved";
	public static final String PROPERTY_CONNECTION_OUT_REMOVED = "ConnectionOutRemoved";
	public static final String PROPERTY_ADD = "NodeAdd";
	public static final String PROPERTY_REMOVE = "NodeRemove";
	public static final String PROPERTY_UPDATED = "NodeUpdated";
	
	public static final String PROPERTY_CONNECTION_ADD = "ConnectionAdd";
	
	private PropertyChangeSupport listeners;
	
	private Rectangle layout;
	private List<GWLink> in;
	private List<GWLink> out;
	protected String name;
	protected String label="";
	protected UUID uuid ;
	protected String id;
	protected Map<String, Object> properties = new HashMap<String, Object> ();
	
	public GWNode(UUID uuid,String name,String id) {
		this.uuid = uuid;
		this.id = id;
		this.setName(name);
		this.in = new ArrayList<GWLink>();
		this.out = new ArrayList<GWLink>();
		this.listeners = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
	public void addInNeighbor(GWLink gWLink) {
		if (this.in.contains(gWLink)) {
			return;
		}
		this.in.add(gWLink);
		getListeners().firePropertyChange(Vertex.PROPERTY_CONNECTION_IN_ADDED, null, this);
	}

	public void addOutNeighbor(GWLink gWLink) {
		if (this.out.contains(gWLink)) {
			return;
		}
		this.out.add(gWLink);
		getListeners().firePropertyChange(Vertex.PROPERTY_CONNECTION_OUT_ADDED, null, this);

	}
	
	private GWLink getLinkByName (List<GWLink> list,String name) {
		for (GWLink gWLink : list) {
			if (gWLink.getName().equals(name)) {
				return gWLink;
			}
		}
		return null;
	}
	
	private GWLink getInByName (String name) {
		return getLinkByName (in, name);
	}
	
	private GWLink getOutByName (String name) {
		return getLinkByName (in, name);
	}
	
	public GWLink removeNeighbor(String name) {
		GWLink gWLink = getInByName (name);
		if (gWLink!=null) {
			this.in.remove(gWLink);
			return gWLink;
		}
		gWLink = getOutByName (name);
		if (gWLink!=null) {
			this.out.remove(gWLink);
			return gWLink;
		}
		return null;
	}
	
	public boolean containsInNeighbor(GWLink e) {
		return this.in.contains(e);
	}
	
	public boolean containsOutNeighbor(GWLink e) {
		return this.out.contains(e);
	}
	
	public void removeInNeighbor(GWLink l) {
		this.in.remove(l);
		getListeners().firePropertyChange(Vertex.PROPERTY_CONNECTION_IN_REMOVED, null, l);
	}
	
	public void removeOutNeighbor(GWLink l) {
		this.out.remove(l);
		getListeners().firePropertyChange(Vertex.PROPERTY_CONNECTION_OUT_REMOVED, null, l);
	}
	
	public void removeNeighbor(GWLink e) {
		removeInNeighbor(e);
		removeOutNeighbor(e);
	}
	
	public GWLink getInNeighbor(int index) {
		return this.in.get(index);
	}
	
	public GWLink getOutNeighbor(int index) {
		return this.out.get(index);
	}
	
	public void removeNeighbors() {
		this.in.clear();
		this.out.clear();
	}
	
	public int getInNeighborCount() {
		return this.in.size();
	}
	
	public int getOutNeighborCount() {
		return  this.out.size();
	}

	 
	public String toString() {
		return this.getClass().getName() + ":" + name;
	}

	public int hashCode() {
		return this.uuid.toString().hashCode();
	}

	public boolean equals(Object other) {
		if (!(other instanceof GWNode)) {
			return false;
		}
		GWNode v = (GWNode) other;
		return this.uuid.equals(v.uuid);
	}

	public List<GWLink> getInNeighbors() {
		List<GWLink> gWLinks = new ArrayList<GWLink>();
		gWLinks.addAll(in);
		return gWLinks;
	}

	public List<GWLink> getOutNeighbors() {
		List<GWLink> gWLinks = new ArrayList<GWLink>();
		gWLinks.addAll(out);
		return gWLinks;
	}

	
	/**
	 * @return the layout
	 */
	public Rectangle getLayout() {
		return layout;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(Rectangle newLayout) {
		Rectangle oldLayout = this.layout;
		this.layout = newLayout;
	 
		getListeners().firePropertyChange(PROPERTY_LAYOUT, oldLayout, newLayout);
	}
 

	public abstract void setName(String name) ;

	public abstract Object clone() ;
	/**
	 * @return the id
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * @param property
	 * @return
	 */
	public boolean isUpdatable(String property) {
		return true;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @param name the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public abstract Object getAdapter(Class key) ;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
