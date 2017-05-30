package org.gw4e.eclipse.studio.editor.tool;

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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.widgets.Display;

public class CreationAndDirectEditTool extends CreationTool {

	@Override protected void performCreation(int button) {
		super.performCreation(button);
		
		EditPartViewer viewer = getCurrentViewer();
		final Object model = getCreateRequest().getNewObject();
		if (model == null || viewer == null) {
			return;
		}
		
		final Object o = getCurrentViewer().getEditPartRegistry().get(model);
		if(o instanceof EditPart) {
			Display.getCurrent().asyncExec(new Runnable() {
				
				@Override public void run() {
					EditPart part = (EditPart)o;
					Request request = new DirectEditRequest();
					part.performRequest(request);
				}
			});
		}
	}
}
