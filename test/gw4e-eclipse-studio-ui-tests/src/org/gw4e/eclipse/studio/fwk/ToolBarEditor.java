package org.gw4e.eclipse.studio.fwk;

import java.util.Iterator;
import java.util.List;

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

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarPushButton;
import org.gw4e.eclipse.studio.model.GraphElement;

public class ToolBarEditor extends  AbstractToolBarEditor {
	protected SWTGefBot bot;
	protected SWTBotGefEditor editor;

	public ToolBarEditor(SWTGefBot bot, SWTBotGefEditor editor) {
		this.editor = editor;
		this.bot = bot;
	}
 
	public void delete(ICondition condition) {
		SWTBotToolbarButton swtBotToolbarButton = bot.toolbarButtonWithTooltip("Delete");
		swtBotToolbarButton.click(); 
		if (condition!=null)
			bot.waitUntil(condition,1000*60);
	}

	public void undoDelete(ICondition condition) {
		SWTBotToolbarButton swtBotToolbarButton = bot.toolbarButtonWithTooltip("Undo Delete");
		swtBotToolbarButton.click();
		if (condition!=null)
			bot.waitUntil(condition);
	}

	public void redoDelete(ICondition condition) {
		SWTBotToolbarButton swtBotToolbarButton = bot.toolbarButtonWithTooltip("Redo Delete");
		swtBotToolbarButton.click();
		if (condition!=null)
			bot.waitUntil(condition);
	}

	public void resetEdgeRoute() {
		SWTBotToolbarButton resetButton = bot.toolbarButtonWithTooltip("Reset Edge Route");
		resetButton.click();
	}

	public void copy() {
		copy(null);
	}
	
	public void selectVertex (SWTBotGefEditor editor, VertexProperties gp, String elementName) {
		ICondition condition = new org.eclipse.swtbot.swt.finder.waits.ICondition() {
			@Override
			public boolean test() throws Exception {
				SWTBotGefEditPart vA = editor.getEditPart(elementName);
				gp.selectPart(vA);
				List<SWTBotGefEditPart> parts = editor.selectedEditParts();
				Iterator<SWTBotGefEditPart> iter = parts.iterator();
				while (iter.hasNext()) {
					SWTBotGefEditPart p = iter.next();
					return p == vA;
				}
				return false;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
			  return "Unable to select " + elementName;
			}
			
		};
		
		bot.waitUntil(condition);
	}
 	
	
	
	
	
	
	public void copy(ICondition condition) {
		CopyAction button = new CopyAction();
		button.click();
		editor.click(50, 50);
		if (condition == null) {
			condition = new org.eclipse.swtbot.swt.finder.waits.ICondition() {
				@Override
				public boolean test() throws Exception {
					PasteAction button = new PasteAction();
					return button.isEnabled();
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Paste button not enabled";
				}
				
			};
		}
		bot.waitUntil(condition);
	}
	 
	public void paste() {
		paste(null);
	}
	
	public void paste(ICondition condition) {
		PasteAction action = new PasteAction();
		action.click();
		editor.click(10, 10) ; // Give the editor the focus
		if (condition!=null)
			bot.waitUntil(condition);
	}

	public class CopyAction {
		SWTBotToolbarPushButton button;

		public CopyAction() {
			
		}
		
		public boolean isEnabled () {
			SWTBotMenu fileMenu = bot.menu("Edit");
			SWTBotMenu copyMenu = fileMenu.menu("Copy");
			return copyMenu.isEnabled();
		}		
		
		public void click () {
			SWTBotMenu fileMenu = bot.menu("Edit");
			SWTBotMenu copyMenu = fileMenu.menu("Copy");
			copyMenu.click();
		}
	}
	
	public class PasteAction {
		public PasteAction() {
		}
		public boolean isEnabled () {
			SWTBotMenu fileMenu = bot.menu("Edit");
			SWTBotMenu pasteMenu = fileMenu.menu("Paste");
			return pasteMenu.isEnabled();
		}		
		public void click () {
			SWTBotMenu fileMenu = bot.menu("Edit");
			SWTBotMenu pasteMenu = fileMenu.menu("Paste");
			pasteMenu.click();
		}
	}
}
