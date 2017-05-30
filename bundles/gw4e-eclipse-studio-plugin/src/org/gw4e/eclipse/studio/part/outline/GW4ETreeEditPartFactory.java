package org.gw4e.eclipse.studio.part.outline;

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

import java.util.Map;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.SharedVertex;
import org.gw4e.eclipse.studio.model.StartVertex;
import org.gw4e.eclipse.studio.model.Vertex;

public class GW4ETreeEditPartFactory implements EditPartFactory {
	OutLineFilter filter;
	Map registry;
	GraphTreeEditPart gtep = null;
	
	public GW4ETreeEditPartFactory(Map registry, OutLineFilter filter  ) {
		super();	 
		this.filter=filter;
		this.registry=registry;
	}

	 
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		GW4EAbstractTreeEditPart part = null;
		if (model instanceof GWGraph) {
			if (gtep==null) {
				gtep = new GraphTreeEditPart();	
			}
			part = 	gtep;	 
		} else {
			if (model instanceof SharedVertex) {
				part = new SharedVertexTreeEditPart();
			} else if (model instanceof StartVertex) {
				part = new StartVertexTreeEditPart();
			} else if (model instanceof Vertex) {
				part = new VertexTreeEditPart();
			} else if (model instanceof GWLink) {
				part = new EdgeTreeEditPart();
			}
		}
		if (part == null) {
			throw new RuntimeException(" UnManaged Model " + model.getClass().getName());
		}
		
		part.setModel(model);
		part.setFilter(filter);
		part.setRegistry(registry); 
		
		return part;
	}
}
