package org.gw4e.eclipse.studio.facade;

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

import java.util.List;

import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.graph.Node;
import org.eclipse.gef4.layout.ILayoutAlgorithm;
import org.eclipse.gef4.layout.LayoutContext;
import org.eclipse.gef4.layout.LayoutProperties;
import org.eclipse.gef4.layout.algorithms.SpaceTreeLayoutAlgorithm;
import org.eclipse.gef4.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.layout.algorithms.SugiyamaLayoutAlgorithm;
import org.eclipse.gef4.layout.algorithms.SugiyamaLayoutAlgorithm.Direction;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.Vertex;
import org.eclipse.gef4.layout.algorithms.TreeLayoutAlgorithm;

 

public class LayoutAlgoritmManager {
	public static String TREELAYOUT_SPACE = "treelayout.space";
	
	private LayoutAlgoritmManager() {
	}

	private static void layout (Graph gef4Graph, org.eclipse.draw2d.geometry.Dimension dimension, ILayoutAlgorithm algorithm) {
		LayoutContext context = new LayoutContext();
		 
		context.setGraph(gef4Graph);
	
		LayoutProperties.setBounds(gef4Graph, new Rectangle(0, 0, dimension.width, dimension.height));
		context.setLayoutAlgorithm(algorithm);
	 
		context.applyLayout(true);
		
		List<Node> nodes = gef4Graph.getNodes();
		for (Node node : nodes) {
			Vertex vertex = (Vertex) node.getAttributes().get(Vertex.OWNER);
			Point newLocation =  LayoutProperties.getLocation(node);
			
			org.eclipse.draw2d.geometry.Point p =  new  org.eclipse.draw2d.geometry.Point(new Double(newLocation.x).intValue(),new Double(newLocation.y).intValue());
			org.eclipse.draw2d.geometry.Rectangle rectangle = new org.eclipse.draw2d.geometry.Rectangle (p.x,p.y,vertex.getLayout().width,vertex.getLayout().height);
			vertex.setLayout(rectangle);
		}
	}
	
	public static void treeLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		Dimension space = (Dimension) gef4Graph.getAttributes().get(TREELAYOUT_SPACE);
		layout (gef4Graph,dimension,new TreeLayoutAlgorithm(TreeLayoutAlgorithm.TOP_DOWN, space));
	}

	public static void springLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SpringLayoutAlgorithm());
	}
	
	public static void sugiyamaLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SugiyamaLayoutAlgorithm(Direction.VERTICAL, new SugiyamaLayoutAlgorithm.DFSLayerProvider()));
	}
 
	public static void spaceTreeTopDownLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SpaceTreeLayoutAlgorithm(SpaceTreeLayoutAlgorithm.TOP_DOWN));
	}
	
	public static void spaceTreeBottomUpLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SpaceTreeLayoutAlgorithm(SpaceTreeLayoutAlgorithm.BOTTOM_UP));
	}

	public static void spaceTreeLeftRightLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SpaceTreeLayoutAlgorithm(SpaceTreeLayoutAlgorithm.LEFT_RIGHT));
	}

	public static void spaceTreeRightLeftLayout (GWGraph graph, org.eclipse.draw2d.geometry.Dimension dimension) {
		Graph gef4Graph = (Graph)graph.getAdapter(Graph.class);
		layout (gef4Graph,dimension,new SpaceTreeLayoutAlgorithm(SpaceTreeLayoutAlgorithm.RIGHT_LEFT));
	}
}
