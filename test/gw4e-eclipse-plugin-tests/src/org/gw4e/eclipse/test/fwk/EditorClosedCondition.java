package org.gw4e.eclipse.test.fwk;

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

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.facade.ICondition;

public class EditorClosedCondition implements ICondition {


	String title = null;
	boolean found = false;
	public EditorClosedCondition(String title) {
		super();
		this.title = title;
	}
	@Override
	public boolean checkCondition() throws Exception {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				 
				if (ww == null)
					return;
				IWorkbenchPage page = ww.getActivePage();
				if (page == null)
					return;
				IEditorReference[] part = page.getEditorReferences();
				if (part == null)
					return;
				for (IEditorReference iEditorReference : part) {
					if( title.equals(iEditorReference.getTitle()) ) 
						found = true;
				}
			}
		});
		return !found;
	}

	@Override
	public String getFailureMessage() {
		return "Editor opened";
	}

}
