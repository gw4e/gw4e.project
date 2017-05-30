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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;
import org.gw4e.eclipse.studio.Activator;

public class ClearEdgeBendpointLayoutAction extends SelectionAction {

	public static String ID = ClearEdgeBendpointLayoutAction.class.getName();
	public static String LABEL = "Reset Edge Route";
	
	public ClearEdgeBendpointLayoutAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
 	}
	
	public static ImageDescriptor imageDescriptor () {
		return Activator.getResetEdgeImageDescriptor();
	}
	public static ImageDescriptor disabledImageDescriptor () {
		return Activator.getDisabledResetEdgeImageDescriptor();
	}
	
	private Command createClearEdgeBendpointLayoutCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}
		return new ClearEdgeBendpointLayoutCommand(selectedObjects);
	}

	protected void init() {
		setId(ID);
		setText(LABEL);
		setToolTipText(LABEL);
		 
		setImageDescriptor(imageDescriptor ());
		setDisabledImageDescriptor(disabledImageDescriptor());
		setEnabled(false);
	}

	@Override
	protected boolean calculateEnabled() {
		Command command = createClearEdgeBendpointLayoutCommand(getSelectedObjects());
		return command!=null && command.canExecute();
	}

	@Override
	public void run() {
		Command command = createClearEdgeBendpointLayoutCommand(getSelectedObjects());
		if (command!=null && command.canExecute())
			execute(command);
	}

}
