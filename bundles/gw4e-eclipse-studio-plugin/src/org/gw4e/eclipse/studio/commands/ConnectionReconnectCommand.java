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

import java.util.Iterator;

import org.eclipse.gef.commands.Command;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;

public class ConnectionReconnectCommand  extends Command {
	private GWGraph gWGraph;
	private GWNode newSource;
	private GWNode newTarget;
	private GWNode oldSource;
	private GWNode oldTarget;

	private GWLink link;
	public ConnectionReconnectCommand() {
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(GWLink link) {
		this.link = link;
		oldSource=link.getSource();
		oldTarget=link.getTarget();
	}
	/**
	 * @param source the source to set
	 */
	public void setNewSource(GWNode newsource) {
		this.setLabel("move connection startpoint");
		this.newSource = newsource;
		this.newTarget=null;
	}

	/**
	 * @param newTarget the newTarget to set
	 */
	public void setNewTarget(GWNode newTarget) {
		this.setLabel("move connection endpoint");
		this.newTarget = newTarget;
		this.newSource = null;
	}
	/**
	 * @param gWGraph the gWGraph to set
	 */
	public void setGraph(GWGraph gWGraph) {
		this.gWGraph = gWGraph;
	}

	public boolean canExecute() {
		if (newSource != null) {
			return checkSourceReconnection();
		} else if (newTarget != null) {
			return checkTargetReconnection();
		}
		return false;
	}
	
	private boolean checkSourceReconnection() {
		if (newSource.equals(oldTarget)) {
			return false;
		}
		for (Iterator iter = newSource.getOutNeighbors().iterator(); iter.hasNext();) {
			GWLink conn = (GWLink) iter.next();
			if (conn.getTarget().equals(oldTarget) && !conn.equals(link)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkTargetReconnection() {
		if (newTarget.equals(oldSource)) {
			return false;
		}
		for (Iterator iter = newTarget.getInNeighbors().iterator(); iter.hasNext();) {
			GWLink conn = (GWLink) iter.next();
			if (conn.getSource().equals(oldSource) && !conn.equals(link)) {
				return false;
			}
		}
		return true;
	}
	
	public void execute() {
		if (newSource != null) {
			link.reconnect(newSource, oldTarget);
		} else if (newTarget != null) {
			link.reconnect(oldSource, newTarget);
		} else {
			throw new IllegalStateException("No source or target specified.");
		}
	}

	public void undo() {
		link.reconnect(oldSource, oldTarget);
	}
}
