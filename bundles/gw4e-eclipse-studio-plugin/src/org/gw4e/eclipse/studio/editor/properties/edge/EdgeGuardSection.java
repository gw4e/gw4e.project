package org.gw4e.eclipse.studio.editor.properties.edge;

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

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.javascript.ViewerHelper;
import org.gw4e.eclipse.studio.model.ModelProperties;

public class EdgeGuardSection extends EdgeJavaScriptSection  implements SectionWidgetID  {
	private boolean notification = true;
 
	public EdgeGuardSection() {
	}
	
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        getViewer().getControl().setEnabled(getProperties().isUpdatable(ModelProperties.PROPERTY_EDGE_GUARD));
    }

	protected  SourceViewer createViewer (Composite composite) {
		SourceViewer viewer = ViewerHelper.createEditor(composite);	
		viewer.getControl().setData(WIDGET_ID, WIDGET_GUARD_SCRIPT);
		FocusListener listener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent event) {
				if (!notification) return;
	            if (viewer.getDocument() == null) return;
	            String content  = viewer.getDocument().get();
	            getProperties().setPropertyValue(ModelProperties.PROPERTY_EDGE_GUARD, content);
			}
		};
		viewer.getControl().addFocusListener(listener);
		return viewer;		
	}

	@Override
	public void refresh() {
    	notification = false;
    	try { 
    		ViewerHelper.setSource(getViewer(), getSource());
    	} finally {
    		notification = true;
    	}  
    }

	@Override
	public String getSource() {
		return getProperties().getGuard().getSource();
	}     	
}
