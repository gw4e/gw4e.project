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

public class RequirementCriteria extends AbstractCriteria {

	String text;

	public RequirementCriteria(OutLineFilter filter) {
		this.text = filter.getRequirement();
	}

	@Override
	public GraphElement meetCriteria(GraphElement element) {
		if (text == null || text.trim().length() == 0) {
			return this.executeNext(element);
		}
		if (!(element instanceof Vertex)) {
			
			return null;
		}
		Vertex vertex = (Vertex)element;
		String requirement = vertex.getRequirement().getValue();
		if (requirement == null || requirement.trim().length() == 0) {
			requirement = "";
		}
		if ("*".equalsIgnoreCase(text.trim()) && requirement.trim().length() > 0)  {
			return this.executeNext(element);
		}
		if (requirement.contains(text)) {
			return this.executeNext(element);
		}
		return null;
	}

}
