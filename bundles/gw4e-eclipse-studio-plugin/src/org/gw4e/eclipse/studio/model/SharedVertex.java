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

public class SharedVertex extends Vertex {
	 
	public SharedVertex(GWGraph gWGraph,UUID uuid,String name,String id,String sharedName) {
		super(gWGraph,uuid,name,id);
		this.shared=true;
		setSharedStateName(sharedName);
	}
	
	/**
	 * @param sharedStateName the sharedStateName to set
	 */
	public void setSharedStateName(String sharedName) {
		this.sharedName = sharedName;
	}

	public Object clone() {
		UUID uuid = UUID.randomUUID();
		Vertex vertex = new SharedVertex (gWGraph,uuid,"v_" + ID.getId(),uuid.toString(),this.getSharedName());
		vertex.setBlocked(this.isBlocked());
		vertex.setGraph(this.getGraph());
		vertex.setInitScript((InitScript)this.getInitScript().clone());
		vertex.setLabel(this.getLabel());
		vertex.setRequirement((Requirement)this.getRequirement().clone());
		vertex.setShared(this.isShared());
		vertex.setStart(false);
		vertex.setLayout(new Rectangle(
                					getLayout().x + 10, getLayout().y + 10,
                					getLayout().width, getLayout().height)
						);
		return vertex;
	}
	 
}
