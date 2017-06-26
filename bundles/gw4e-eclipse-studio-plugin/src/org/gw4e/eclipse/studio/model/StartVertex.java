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

import java.util.UUID;

import org.eclipse.draw2d.geometry.Rectangle;
import org.gw4e.eclipse.studio.util.ID;

public class StartVertex extends Vertex {

	public StartVertex(GWGraph gWGraph,UUID uuid,String name,String id) {
		super(gWGraph,uuid,name,id);
		this.start=true;
	}
	public boolean isUpdatable(String property) {
		if (ModelProperties.PROPERTY_NAME.equalsIgnoreCase(property)) {
			return false;
		}
		if (ModelProperties.PROPERTY_BLOCKED.equalsIgnoreCase(property)) {
			return false;
		}
		if (ModelProperties.PROPERTY_VERTEX_REQUIREMENTS.equalsIgnoreCase(property)) {
			return false;
		}

		return super.isUpdatable(property);
	}
	
	public Vertex duplicate(GWGraph graph, int X , int Y, String name) {
		UUID uuid = UUID.randomUUID();
		if (name==null) {
			name = "v_" + ID.getId();
		}
 
		Vertex vertex = new StartVertex (graph,uuid,name,uuid.toString());
		vertex.setBlocked(this.isBlocked());
		vertex.setGraph(graph);
		vertex.setInitScript((InitScript)this.getInitScript().clone());
		vertex.setLabel(this.getLabel());
		vertex.setRequirement((Requirement)this.getRequirement().clone());
		vertex.setShared(this.isShared());
		vertex.setStart(false);
		vertex.setProperties(this.getProperties());
		vertex.setLayout(new Rectangle(X, Y, this.getLayout().width, this.getLayout().height));
		return vertex;		
	}
}
