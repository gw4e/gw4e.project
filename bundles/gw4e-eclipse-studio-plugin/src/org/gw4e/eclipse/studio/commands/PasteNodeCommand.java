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
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.Vertex;

public class PasteNodeCommand extends Command {
	private Map<GWNode, GWNode> list = new HashMap<GWNode, GWNode>();
	private Map<GWNode, VertexCreateCommand> commands = new HashMap<GWNode, VertexCreateCommand>();
	Map<GWNode,EditPart> map = null;
	@Override
	public boolean canExecute() {
		map = (Map<GWNode,EditPart>)Clipboard.getDefault().getContents();
		if (map == null || map.isEmpty())
			return false;

		Iterator<GWNode> it = map.keySet().iterator();
		while (it.hasNext()) {
			GWNode gWNode = (GWNode) it.next();
			if (!isPastableNode(gWNode)) {
				map.remove(gWNode);
			}
		}
		return true;
	}

	@Override
	public void execute() {
		if (!canExecute())
			return;

		Iterator<GWNode> it = map.keySet().iterator();
		while (it.hasNext()) {
			GWNode gWNode = (GWNode) it.next();
			if (gWNode instanceof Vertex) {
				Vertex srv = (Vertex) gWNode;
				 
				Vertex clone = (Vertex) srv.clone();
				VertexCreateCommand vcc = doIt(clone);
				commands.put(clone, vcc);
				list.put(gWNode, clone);
			}
		}
		 
	}

	private VertexCreateCommand doIt(Vertex clone) {
		VertexCreateCommand vcc = new VertexCreateCommand();
		
		GWGraph graph = GraphSelectionManager.ME.getSelection().getGwGraph();
		clone.setGraph(graph);
		
		vcc.setVertex(clone);
		vcc.setConstraints(clone.getLayout());
		vcc.execute();
		return vcc;
	}

	@Override
	public void redo() {
		Iterator<GWNode> it = list.values().iterator();
		while (it.hasNext()) {
			GWNode gWNode = it.next();
			if (isPastableNode(gWNode)) {
				VertexCreateCommand vcc = commands.get(gWNode);
				vcc.redo();
			}
		}
	}

	@Override
	public boolean canUndo() {
		return !(list.isEmpty());
	}

	@Override
	public void undo() {
		Iterator<GWNode> it = list.values().iterator();
		while (it.hasNext()) {
			GWNode gWNode = it.next();
			if (isPastableNode(gWNode)) {
				VertexCreateCommand vcc = commands.get(gWNode);
				vcc.undo();
			}
		}
	}

	public boolean isPastableNode(GWNode gWNode) {
		if (gWNode instanceof Vertex)
			return true;
		return false;
	}
}
