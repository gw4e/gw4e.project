package org.gw4e.eclipse.studio.commands;

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

import org.eclipse.draw2d.geometry.Rectangle;
import org.gw4e.eclipse.studio.model.Vertex;

public class VertexChangeLayoutCommand extends AbstractLayoutCommand {
	private Vertex model;
	private Rectangle layout;
	private Rectangle previousLayout;
	public VertexChangeLayoutCommand() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		model.setLayout(layout);
	}
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.commands.AbstractLayoutCommand#setConstraint(org.eclipse.draw2d.Rectangle)
	 */
	@Override
	public void setConstraint(Rectangle rect) {
		this.layout = rect;
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.commands.AbstractLayoutCommand#setModel(java.lang.Object)
	 */
	@Override
	public void setModel(Object model) {
		this.model = (Vertex) model;
		this.previousLayout = ((Vertex)model).getLayout();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
        this.model.setLayout(this.previousLayout);
	}
}
