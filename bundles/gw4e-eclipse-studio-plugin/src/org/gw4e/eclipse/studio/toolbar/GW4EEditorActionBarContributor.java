package org.gw4e.eclipse.studio.toolbar;

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

import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.gw4e.eclipse.studio.commands.ClearEdgeBendpointLayoutAction;

public class GW4EEditorActionBarContributor extends ActionBarContributor {

	public GW4EEditorActionBarContributor() {
	}

	@Override
	protected void buildActions() {
		  IWorkbenchWindow iww = getPage().getWorkbenchWindow(); 
		  addRetargetAction(new UndoRetargetAction());
          addRetargetAction(new RedoRetargetAction());
          addRetargetAction(new DeleteRetargetAction());
          addRetargetAction(new ZoomInRetargetAction());
          addRetargetAction(new ZoomOutRetargetAction());
          addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY, GEFMessages.ToggleGrid_Label, IAction.AS_CHECK_BOX));   
          addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY, GEFMessages.ToggleSnapToGeometry_Label, IAction.AS_CHECK_BOX));
          addRetargetAction(new RetargetAction(ActionFactory.SELECT_ALL.getId(), GEFMessages.SelectAllAction_Label));
          addRetargetAction((RetargetAction)ActionFactory.COPY.create(iww));
          addRetargetAction((RetargetAction)ActionFactory.PASTE.create(iww));
          addRetargetAction(new ClearEdgeBenpointLayoutRetargetAction(iww));
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
        toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
        toolBarManager.add(getAction(ActionFactory.REDO.getId()));
        toolBarManager.add(getAction(ActionFactory.DELETE.getId()));  
        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
        toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
        toolBarManager.add(new ZoomComboContributionItem(getPage()));
        toolBarManager.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
        toolBarManager.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY)); 
        toolBarManager.add(getAction(ActionFactory.COPY.getId()));
        toolBarManager.add(getAction(ActionFactory.PASTE.getId()));
        toolBarManager.add(new Separator());
        toolBarManager.add(getAction(ClearEdgeBendpointLayoutAction.ID));
    }

	@Override
	protected void declareGlobalActionKeys() {
		addGlobalActionKey(ClearEdgeBendpointLayoutAction.ID); 
	}

}
