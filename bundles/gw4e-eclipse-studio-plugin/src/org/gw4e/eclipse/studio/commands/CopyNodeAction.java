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
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.gw4e.eclipse.studio.model.GWNode;

public class CopyNodeAction extends SelectionAction {
	public CopyNodeAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("Copy");
		setId(ActionFactory.COPY.getId());
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	private Command createCopyCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}

		CopyNodeCommand cmd = new CopyNodeCommand();
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			Object obj =  it.next();
			if (!(obj instanceof EditPart)) continue;
			EditPart ep = (EditPart) obj;
			if (!(ep.getModel() instanceof GWNode)) continue;
			GWNode gWNode = (GWNode) ep.getModel();
			if (!cmd.isCopyableNode(gWNode))
				return null;
			cmd.addElement(ep);
		}
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	@Override
	public void run() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
		}
	}

}
