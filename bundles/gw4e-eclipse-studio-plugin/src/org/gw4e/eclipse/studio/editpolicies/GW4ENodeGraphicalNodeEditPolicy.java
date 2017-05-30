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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.gw4e.eclipse.studio.commands.ConnectionReconnectCommand;
import org.gw4e.eclipse.studio.commands.LinkCreateCommand;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GraphElement;

public class GW4ENodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	public GW4ENodeGraphicalNodeEditPolicy() {
	}

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		LinkCreateCommand result = (LinkCreateCommand) request.getStartCommand();
		if (getHost().getModel() instanceof GraphElement) {
			GraphElement target = (GraphElement) getHost().getModel();
			result.setLink((GWLink) request.getNewObject());
			result.setTarget(target);
			return result;
		}
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		LinkCreateCommand result = new LinkCreateCommand();
		if (getHost().getModel() instanceof GraphElement) {
			result.setSource((GraphElement) getHost().getModel());
			
			result.setGraph(((GraphElement) getHost().getModel()).getGraph());
			request.setStartCommand(result);
			return result;
		}
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionReconnectCommand result = new ConnectionReconnectCommand();
		GWLink link = (GWLink) request.getConnectionEditPart().getModel();
		if (getHost().getModel() instanceof GraphElement) {
			result.setGraph(((GraphElement) getHost().getModel()).getGraph());
			result.setNewSource((GraphElement) getHost().getModel());
			result.setLink(link);
			return result;
		}
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ConnectionReconnectCommand result = new ConnectionReconnectCommand();
		GWLink link = (GWLink) request.getConnectionEditPart().getModel();
		if (getHost().getModel() instanceof GraphElement) {
			result.setGraph(((GraphElement) getHost().getModel()).getGraph());
			result.setNewTarget((GraphElement) getHost().getModel());
			result.setLink(link);
			return result;
		}
		return UnexecutableCommand.INSTANCE;
	}

}
