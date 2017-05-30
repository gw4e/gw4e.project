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

public abstract class GraphElement extends GWNode  {
	GWGraph gWGraph;
	boolean blocked;
	public static String OWNER = "GW4E.graphelement.owner" ;
	
	public GraphElement(GWGraph gWGraph,UUID uuid,String name,String id) {
		super(uuid,name,id);
		this.gWGraph=gWGraph;
	}
	
	/**
	 * @return the blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	/**
	 * @return the gWGraph
	 */
	public GWGraph getGraph() {
		return gWGraph;
	}

	/**
	 * @param gWGraph the gWGraph to set
	 */
	public void setGraph(GWGraph gWGraph) {
		this.gWGraph = gWGraph;
	}

	public abstract void updateGraph (String id, String oldName, String newName);

	 
 
}
