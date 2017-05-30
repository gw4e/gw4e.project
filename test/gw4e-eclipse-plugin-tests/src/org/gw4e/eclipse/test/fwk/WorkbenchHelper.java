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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class WorkbenchHelper {
	public static void resetWorkspace()   {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				try {
					resetWorkbench();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void resetWorkbench() {
		try {
			IViewReference[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getViewReferences();
			for (IViewReference iViewReference : views) {

				if (iViewReference.getTitle().equals("Welcome")) {
					iViewReference.getPage().hideView(iViewReference);
					break;
				}
			}

			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			Shell activeShell = Display.getCurrent().getActiveShell();
			if (activeShell != null && activeShell != workbenchWindow.getShell()) {
				activeShell.close();
			}
			page.closeAllEditors(false);

		 	page.resetPerspective();
		 	String defaultPerspectiveId = workbench.getPerspectiveRegistry().getDefaultPerspective();
		 	workbench.showPerspective(defaultPerspectiveId, workbenchWindow);
		 	page.resetPerspective();
		} catch (WorkbenchException e) {
			throw new RuntimeException(e);
		}
	}
}
