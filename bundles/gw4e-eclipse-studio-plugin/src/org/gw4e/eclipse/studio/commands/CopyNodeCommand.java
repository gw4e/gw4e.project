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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.StartVertex;
import org.gw4e.eclipse.studio.model.Vertex;

public class CopyNodeCommand extends Command {

	 
	private Map<GWNode,EditPart> map = new HashMap<GWNode,EditPart>();
	public boolean addElement(EditPart part) {
		GWNode gWNode = (GWNode) part.getModel();
		if (map.get(gWNode)==null) {
			map.put(gWNode,part);
			return true;
		}
		return false;
	}

	@Override
	public boolean canExecute() {
		if (map == null || map.isEmpty())
			return false;
		Iterator<GWNode> it = map.keySet().iterator();
		while (it.hasNext()) {
			if (!isCopyableNode(it.next()))
				return false;
		}
		return true;
	}

	@Override
	public void execute() {
		boolean execute = canExecute();
		if (execute) {
			Clipboard.getDefault().setContents(map);
		}
			
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	public boolean isCopyableNode(GWNode gWNode) {
		if (gWNode instanceof StartVertex) {
			return false;
		}
		if (gWNode instanceof Vertex) {
			return true;
		}
		return false;
	}

}
