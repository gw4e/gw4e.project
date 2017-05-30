package org.gw4e.eclipse.studio.editpolicies;

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
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;
import org.gw4e.eclipse.studio.commands.LinkCreateBendpointCommand;
import org.gw4e.eclipse.studio.commands.LinkDeleteBendpointCommand;
import org.gw4e.eclipse.studio.commands.LinkMoveBendpointCommand;
import org.gw4e.eclipse.studio.model.GWLink;

public class GW4ELinkBendpointEditPolicy extends BendpointEditPolicy {
	 
	  /**
	   * {@inheritDoc}
	   */
	  @Override protected Command getCreateBendpointCommand(final BendpointRequest request) {
	    LinkCreateBendpointCommand command = new LinkCreateBendpointCommand();
	    Point p = request.getLocation();
	    command.setLink((GWLink) request.getSource().getModel());
	    command.setLocation(p);
	    command.setIndex(request.getIndex());
	    return command;
	  }
	 
	  /**
	   * {@inheritDoc}
	   */
	  @Override protected Command getMoveBendpointCommand(final BendpointRequest request) {
	    LinkMoveBendpointCommand command = new LinkMoveBendpointCommand();
	    Point p = request.getLocation();
	    command.setLink((GWLink) request.getSource().getModel());
	    command.setLocation(p);
	    command.setIndex(request.getIndex());
	    return command;
	  }
	 
	  /**
	   * {@inheritDoc}
	   */
	  @Override protected Command getDeleteBendpointCommand(final BendpointRequest request) {
	    LinkDeleteBendpointCommand command = new LinkDeleteBendpointCommand();
	     
	    command.setLink((GWLink)request.getSource().getModel());
	    command.setIndex(request.getIndex());
	    return command;
	  }
	}
