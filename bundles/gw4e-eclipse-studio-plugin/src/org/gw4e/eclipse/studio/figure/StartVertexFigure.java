package org.gw4e.eclipse.studio.figure;

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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.gw4e.eclipse.studio.Activator;

public class StartVertexFigure extends VertexFigure {
	 
	private Ellipse rectangle;
	
	public StartVertexFigure() {
		super (true);
	}
	
	protected void createLayout () {
		setLayoutManager(new XYLayout());
		setBackgroundColor(Activator.getStartVertexImageColor());
		setOpaque(true);
		this.setBorder(null); 
		rectangle = new Ellipse();
		add(rectangle);
		name = new Label("");
		add(name);
		 
	}
	 
	@Override protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		setConstraint(rectangle, new Rectangle(0, 0, r.width, r.height));
		setConstraint(name, new Rectangle(0, 0, r.width, r.height));
		if (vertextStateFigure!=null) vertextStateFigure.setConstraint(r);
		name.invalidate();		
		rectangle.invalidate();
		if (vertextStateFigure!=null) vertextStateFigure.invalidate();
	}
	
	public ConnectionAnchor getConnectionAnchor() {
	    if (connectionAnchor == null) {
	      connectionAnchor = new EllipseAnchor(this);
	    }
	    return connectionAnchor;
	  }   
	 
}
