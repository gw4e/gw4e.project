package org.gw4e.eclipse.studio.editor;

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

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Point;
import org.gw4e.eclipse.studio.commands.LinkCreateCommand;
import org.gw4e.eclipse.studio.commands.VertexCreateCommand;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.StartVertex;
import org.gw4e.eclipse.studio.model.Vertex;

public class GraphTemplateFactory {

	private GraphTemplateFactory() {
	}
 
	public static GWGraph createSimpleGraph(IFile file) {
		GWGraph gWGraph = new GWGraph(UUID.randomUUID(),file.getName(),UUID.randomUUID().toString());
		gWGraph.setFile(file);
		
		Vertex v1 = new StartVertex(gWGraph,UUID.randomUUID(),"v1",UUID.randomUUID().toString()) ;
		Vertex v2 = new Vertex(gWGraph,UUID.randomUUID(),"v2",UUID.randomUUID().toString()) ;
		Vertex v3 = new Vertex(gWGraph,UUID.randomUUID(),"v3",UUID.randomUUID().toString()) ;
		Vertex v4 = new Vertex(gWGraph,UUID.randomUUID(),"v4",UUID.randomUUID().toString()) ;
		Vertex v5 = new Vertex(gWGraph,UUID.randomUUID(),"v5",UUID.randomUUID().toString()) ;
		
		Vertex [] vertices = new Vertex [] {v1,v2,v3,v4,v5};
		for (int i = 0; i < vertices.length; i++) {
			VertexCreateCommand vcc  = new VertexCreateCommand ();
			vcc.setVertex(vertices[i]);
			vcc.setLocation(new Point(0,0));
			vcc.execute();
		}
	 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(v1);
		lcc.setTarget(v2);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v1->v2",UUID.randomUUID().toString()));
		lcc.execute(); 
		
		lcc.setSource(v2);
		lcc.setTarget(v3);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v2->v3",UUID.randomUUID().toString()));
		lcc.execute(); 

		lcc.setSource(v2);
		lcc.setTarget(v4);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v2->v4",UUID.randomUUID().toString()));
		lcc.execute(); 
		
		lcc.setSource(v4);
		lcc.setTarget(v5);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v4->v5",UUID.randomUUID().toString()));
		lcc.execute(); 

		lcc.setSource(v2);
		lcc.setTarget(v5);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v2->v5",UUID.randomUUID().toString()));
		lcc.execute(); 

		lcc.setSource(v1);
		lcc.setTarget(v4);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "v1->v4",UUID.randomUUID().toString()));
		lcc.execute(); 

		
		return gWGraph;
	}
	 
	
}
