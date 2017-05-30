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

import java.util.UUID;

import org.eclipse.gef.requests.CreationFactory;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.util.ID;

public class VertexFactory implements CreationFactory {

	public VertexFactory() {
	}

	@Override
	public Object getNewObject() {
		GWGraph gWGraph =  GraphSelectionManager.ME.getSelection().getGwGraph ();
		if (gWGraph!=null) {
			UUID uuid = UUID.randomUUID();
			return new Vertex (gWGraph,uuid,"v_" + ID.getId(),uuid.toString());
		}
		return null;
	}

	@Override
	public Object getObjectType() {
		return Vertex.class;
	}

}
