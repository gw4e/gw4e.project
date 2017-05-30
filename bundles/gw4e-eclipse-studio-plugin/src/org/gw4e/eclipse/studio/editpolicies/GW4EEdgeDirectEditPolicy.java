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
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.studio.commands.GraphElementRenameCommand;
import org.gw4e.eclipse.studio.figure.EdgeFigure;
import org.gw4e.eclipse.studio.model.GraphElement;

public class GW4EEdgeDirectEditPolicy extends DirectEditPolicy {

	public GW4EEdgeDirectEditPolicy() {
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		GraphElementRenameCommand command = new GraphElementRenameCommand();
		GraphElement model = (GraphElement) getHost().getModel();
		command.setModel(model);
		command.setOldName(model.getName());
		command.setNewName(value);
		return command; 
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		((EdgeFigure) getHostFigure()).getLabel().setText(value);
	}

}
