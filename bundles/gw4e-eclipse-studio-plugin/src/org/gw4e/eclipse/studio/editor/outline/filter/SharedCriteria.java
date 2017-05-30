package org.gw4e.eclipse.studio.editor.outline.filter;

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

import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.Vertex;

public class SharedCriteria extends AbstractCriteria {
	ThreeStateChoice value;

	public SharedCriteria(OutLineFilter filter) {
		value = filter.isShared();
	}

	@Override
	public GraphElement meetCriteria(GraphElement element) {
		if (!(element instanceof Vertex)) {
			if (ThreeStateChoice.NO_VALUE.equals(value)) {
				return this.executeNext(element);
			}
			return null;
		}
		Vertex vertex = (Vertex) element;
		if (value==null )return this.executeNext(element);
		if (ThreeStateChoice.NO_VALUE.equals(value)) return this.executeNext(element);
		boolean expectedValue = value.equals(ThreeStateChoice.YES);
		boolean shared = vertex.isShared();
		if (shared == expectedValue) {
			return this.executeNext(element);
		}
		return null;
	}

}
