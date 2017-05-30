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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.part.editor.GraphPart;

public class GraphSelectionManager implements ISelectionChangedListener , org.eclipse.swt.events.MouseListener {

	public static GraphSelectionManager ME;
	static {
		ME = new GraphSelectionManager();
	}
	
	GraphSelection selection = new GraphSelection(); ;
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selection.setCurrentSelection(event.getSelection());
	}

	public void selectionPartChanged(GraphPart part) {
		selection.setGpart(part); 
	}
	
	/**
	 * @return the currentSelection
	 */
	public GraphSelection getSelection() {
		return selection;
	}


	@Override
	public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
	}

	@Override
	public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
	}

	@Override
	public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
		selection.setCurrentPoint(new Point(e.x,e.y));
	}

	
	public class GraphSelection {
		 
		ISelection currentSelection ;
		Point currentPoint;
		GraphPart gpart ;
		
		/**
		 * @return the gwGraph
		 */
		public GWGraph getGwGraph() {
			if (gpart==null) {
				if (currentSelection!=null && ((IStructuredSelection) currentSelection).getFirstElement() instanceof GraphPart) {
					GraphPart gp =  (GraphPart) ((IStructuredSelection) currentSelection).getFirstElement();
					return  (GWGraph) gp.getModel();
				}
			}
			return (GWGraph)gpart.getModel();
		}
		 
		/**
		 * @return the currentSelection
		 */
		public ISelection getCurrentSelection() {
			return currentSelection;
		}
		/**
		 * @param currentSelection the currentSelection to set
		 */
		public void setCurrentSelection(ISelection currentSelection) {
			this.currentSelection = currentSelection;
		}
		/**
		 * @return the currentPoint
		 */
		public Point getCurrentPoint() {
			return currentPoint;
		}
		/**
		 * @param currentPoint the currentPoint to set
		 */
		public void setCurrentPoint(Point currentPoint) {
			this.currentPoint = currentPoint;
		}
		/**
		 * @return the gpart
		 */
		public GraphPart getGpart() {
			return gpart;
		}
		/**
		 * @param gpart the gpart to set
		 */
		public void setGpart(GraphPart gpart) {
			this.gpart = gpart;
		}
		
		public String toString () {
			return currentSelection.toString();
		}
	}
 
	 
}
