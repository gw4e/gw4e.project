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

import org.eclipse.gef.commands.Command;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;

public class LinkCreateCommand extends Command {
	private GWNode source;
	private GWNode target;
	private GWLink gWLink;
	private GWGraph gWGraph;

	public LinkCreateCommand() {
	}

	@Override
	public boolean canExecute() {
		if ( source == null ) return false;
		if ( target == null ) return false;
		if ( gWLink == null ) return false;
		if (target.getName().equalsIgnoreCase(Constant.START_VERTEX_NAME)) return false;
		if (source.getName().equalsIgnoreCase(Constant.START_VERTEX_NAME)) {
			if (source.getOutNeighborCount() > 0) return false;
		}
		return true;		 
	}

	@Override
	public void execute() {
		gWLink.setSource(source);
		gWLink.setTarget(target);
		gWLink.setGraph(gWGraph);
		gWGraph.addLink(gWLink);
	}

	public void setTarget(GWNode target) {
		this.target = target;
	}

	public void setSource(GWNode source) {
		this.source = source;
	}

	public void setLink(GWLink gWLink) {
		this.gWLink = gWLink;
	}

	public void setGraph(GWGraph gWGraph) {
		this.gWGraph = gWGraph;
	}
	
	@Override public void undo() {
	    gWGraph.removeLink(gWLink);
	  }

	/**
	 * @return the source
	 */
	public GWNode getSource() {
		return source;
	}
}
