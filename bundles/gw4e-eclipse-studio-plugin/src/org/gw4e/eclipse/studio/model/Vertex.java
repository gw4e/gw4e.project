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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.LayoutProperties;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.util.ID;

public class Vertex extends GraphElement implements Cloneable {
	public static final String PROPERTY_NAME_UPDATED = "VertexNameUpdated";
	public static final String PROPERTY_UPDATED = "VertexPropertyUpdated";
	
	protected boolean shared = false;
	protected String sharedName;
	protected boolean start = false;
	 
	protected Requirement requirement = Requirement.NULL;
	protected InitScript initScript = InitScript.NULL;

	public Vertex(GWGraph gWGraph,UUID uuid,String name,String id) {
		super(gWGraph,uuid,name,id);
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return the shared
	 */
	public boolean isShared() {
		return shared;
	}
	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}
	 
	/**
	 * @return the requirements
	 */
	public Requirement getRequirement() {
		return requirement;
	}
	/**
	 * @return the initScript
	 */
	public InitScript getInitScript() {
		return initScript;
	}

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
	 * @param blocked
	 * @param shared
	 * @param description
	 * @param requirement
	 * @param init
	 */
	public void update (boolean blocked, boolean shared, String sharedName, String description, Requirement requirement,
			InitScript init,Map<String, Object> properties) {
		this.getGraph().updateVertex(getId(), getName(), blocked, shared, sharedName, description, requirement, init,properties);
		if (getListeners()!=null) {
			getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
		}
	}
	
	@Override
	public void updateGraph(String id,String oldName, String newName) {
		this.getGraph().updateVertex(id,oldName, newName);
	}
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.model.GWNode#isUpdatable(java.lang.String)
	 */
	public boolean isUpdatable(String property) {
		if (ModelProperties.PROPERTY_VERTEX_SHARED.equalsIgnoreCase(property)) {
			return false;
		}
		if (ModelProperties.PROPERTY_VERTEX_SHAREDNAME.equalsIgnoreCase(property)) {
			return this.isShared();
		}
		return super.isUpdatable(property);
	}


	/**
	 * @param shared the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}
 

	/**
	 * @param requirement the requirement to set
	 */
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	/**
	 * @param initScript the initScript to set
	 */
	public void setInitScript(InitScript initScript) {
		this.initScript = initScript;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append(this.getName()).append(" ").append(this.getLayout()).append(" ").append("shared:").append(shared).append(" ").append("blocked:").append(blocked);
		sb.append(" ").append("start").append(start).append(" ").append(requirement.toString()).append(initScript.toString());
		return sb.toString();
	}


	public Object clone() {
		UUID uuid = UUID.randomUUID();
		Vertex vertex = new Vertex (gWGraph,uuid,"v_" + ID.getId(),uuid.toString());
		vertex.setBlocked(this.isBlocked());
		vertex.setGraph(this.getGraph());
		vertex.setInitScript((InitScript)this.getInitScript().clone());
		vertex.setLabel(this.getLabel());
		vertex.setRequirement((Requirement)this.getRequirement().clone());
		vertex.setShared(this.isShared());
		vertex.setStart(false);
		
		Point p = GraphSelectionManager.ME.getSelection().getCurrentPoint();
		int X = getLayout().x + 10;
		int Y = getLayout().y + 10;
		if (p!=null) {
			X = p.x();
			Y = p.y();
		}
		vertex.setLayout(new Rectangle(X, Y,getLayout().width, getLayout().height));
		return vertex;
	}

	public static int INDEX = 1;
	public Object getAdapter(Class key) {
		if (key.equals(Node.class)) {
			 Node node =  new Node ();
			 node.getAttributes().put(OWNER, this);
			 
			 org.eclipse.gef4.geometry.planar.Point point = new org.eclipse.gef4.geometry.planar.Point(0,0);
			 Dimension dimension =  new Dimension( 100, 100 );
			 if (this.getLayout()!=null) {
				 point = new org.eclipse.gef4.geometry.planar.Point(this.getLayout().x(),this.getLayout().y());
				 dimension = new Dimension(this.getLayout().width, this.getLayout().height );
			 }
			 LayoutProperties.setLocation(node,point);
			 LayoutProperties.setSize(node,dimension);
			 return node;
		}
		return null;
	}

	/**
	 * @return the sharedName
	 */
	public String getSharedName() {
		return sharedName;
	}

	/**
	 * @param sharedName the sharedName to set
	 */
	public void setSharedName(String sharedName) {
		this.sharedName=sharedName;
	}

	
}
