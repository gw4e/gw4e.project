package org.gw4e.eclipse.studio.part.editor;

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

import java.beans.PropertyChangeListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;

public abstract class AbstractGW4EEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener  {

	public AbstractGW4EEditPart() {
	}
	
	public GWNode getModel() {
		return (GWNode) super.getModel();
	}

	public void activate() {
		if (!isActive()) {
			getModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	public void deactivate() {
		if (isActive()) {
			getModel().removePropertyChangeListener(this);
		}
		super.deactivate();
	}

	protected abstract String getTooltipData();
	
	@Override
	public Object getAdapter(Class adapter) {
		EditPart ep = this;
		if (adapter == IFile.class) {
			while (ep!=null && !(ep instanceof GraphPart)) {
				ep  = ep.getParent();
			}
			if (ep!=null) {
				GraphPart  gp = (GraphPart) ep;
				GWGraph model = (GWGraph)gp.getModel();
				return model.getFile();
			}
			return null;
		}
		
		return null;
	}
}
