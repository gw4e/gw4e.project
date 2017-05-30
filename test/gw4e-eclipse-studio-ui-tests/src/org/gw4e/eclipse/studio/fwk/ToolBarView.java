package org.gw4e.eclipse.studio.fwk;

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

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;

public class ToolBarView extends AbstractToolBarEditor {
	protected SWTBotView view;
	
	public ToolBarView(SWTBotView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.fwk.IToolbar#delete(org.eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	@Override
	public void delete(ICondition condition) {
		 List<SWTBotToolbarButton> swtBotToolbarButtons = view.getToolbarButtons();
		 for (SWTBotToolbarButton swtBotToolbarButton : swtBotToolbarButtons) {
			if ("Delete".equals(swtBotToolbarButton.getToolTipText())) {
				swtBotToolbarButton.click();
			}
		}
		if (condition!=null)
			view.bot().waitUntil(condition);
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.fwk.IToolbar#undoDelete(org.eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	@Override
	public void undoDelete(ICondition condition) {
		 List<SWTBotToolbarButton> swtBotToolbarButtons = view.getToolbarButtons();
		 for (SWTBotToolbarButton swtBotToolbarButton : swtBotToolbarButtons) {
			if ("Undo Delete".equals(swtBotToolbarButton.getToolTipText())) {
				swtBotToolbarButton.click();
			}
		}
		if (condition!=null)
			view.bot().waitUntil(condition);
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.fwk.IToolbar#redoDelete(org.eclipse.swtbot.swt.finder.waits.ICondition)
	 */
	@Override
	public void redoDelete(ICondition condition) {
		 List<SWTBotToolbarButton> swtBotToolbarButtons = view.getToolbarButtons();
		 for (SWTBotToolbarButton swtBotToolbarButton : swtBotToolbarButtons) {
			if ("Redo Delete".equals(swtBotToolbarButton.getToolTipText())) {
				swtBotToolbarButton.click();
			}
		}
		if (condition!=null)
			view.bot().waitUntil(condition);
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.studio.fwk.IToolbar#resetEdgeRoute()
	 */
	@Override
	public void resetEdgeRoute() {
		 List<SWTBotToolbarButton> swtBotToolbarButtons = view.getToolbarButtons();
		 for (SWTBotToolbarButton swtBotToolbarButton : swtBotToolbarButtons) {
			if ("Reset Edge Route".equals(swtBotToolbarButton.getToolTipText())) {
				swtBotToolbarButton.click();
			}
		}
	}
}
