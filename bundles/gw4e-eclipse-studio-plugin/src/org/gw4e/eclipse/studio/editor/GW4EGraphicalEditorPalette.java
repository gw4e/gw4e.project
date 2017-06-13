package org.gw4e.eclipse.studio.editor;

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

import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.gw4e.eclipse.studio.Activator;
import org.gw4e.eclipse.studio.editor.tool.CreationAndDirectEditTool;

public class GW4EGraphicalEditorPalette extends PaletteRoot   {
	public static String TOOL_VERTEX_LABEL = "Vertex";
	public static String TOOL_SHARED_VERTEX_LABEL = "Shared Vertex";
	public static String TOOL_START_VERTEX_LABEL = "Start Vertex";
	public static String TOOL_EDGE_LABEL = "Edge";
	
	PaletteGroup group;
	public GW4EGraphicalEditorPalette() {
		addGroup();
		addSelectionTool();
		addGW4EObjectTool();
		 
	}

	private void addSelectionTool() {
		SelectionToolEntry entry = new SelectionToolEntry();
		group.add(entry);
		group.add(new MarqueeToolEntry());
		setDefaultEntry(entry);
	}

	private void addGroup() {
		group = new PaletteGroup("GW4E Group");
		add(group);
	}


	private void addGW4EObjectTool() {
		CreationToolEntry entry = new CreationToolEntry(TOOL_VERTEX_LABEL, "Create a new Vertex", new VertexFactory(),
				Activator.getImageDescriptor("icons/vertex.png"), null);
		entry.setToolClass(CreationAndDirectEditTool.class);
		group.add(entry);
		entry = new CreationToolEntry(TOOL_SHARED_VERTEX_LABEL, "Create a new Shared Vertex", new SharedVertexFactory(),
				Activator.getImageDescriptor("icons/sharedvertex.png"), null);
		entry.setToolClass(CreationAndDirectEditTool.class);
		group.add(entry);		
		entry = new CreationToolEntry(TOOL_START_VERTEX_LABEL, "Create a Start Vertex", new StartVertexFactory(),
				Activator.getImageDescriptor("icons/startvertex.png"), null);
		entry.setToolClass(CreationAndDirectEditTool.class);
		group.add(entry);		
		entry = new CreationToolEntry(TOOL_EDGE_LABEL, "Create a new Edge", new EdgeFactory(),
				Activator.getImageDescriptor("icons/connection.gif"), null);
		entry.setToolClass(ConnectionCreationTool.class);
		entry.setToolProperty(AbstractTool.PROPERTY_UNLOAD_WHEN_FINISHED,true); 
		group.add(entry);
		 
	}

 
	 
}
