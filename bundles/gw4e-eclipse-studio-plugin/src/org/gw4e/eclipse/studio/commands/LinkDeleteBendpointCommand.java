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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.gw4e.eclipse.studio.model.GWLink;

public class LinkDeleteBendpointCommand extends Command {

	private GWLink gWLink;
	private int index;
	private Point location;

	@Override
	public boolean canExecute() {
		return (gWLink != null) && (gWLink.bendpointsSize() > index);
	}

	@Override
	public void execute() {
		location = gWLink.getBendpoints(index);
		gWLink.removeBendpoints(index);
	}

	@Override
	public void undo() {
		gWLink.addBendpoints(index, location);
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public void setLink(final GWLink gWLink) {
		this.gWLink = gWLink;
	}
}
