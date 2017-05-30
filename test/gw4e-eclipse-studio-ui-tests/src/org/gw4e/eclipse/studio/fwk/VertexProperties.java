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

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.editor.properties.vertex.VertexDefaultSection;
import org.gw4e.eclipse.studio.figure.VertexFigure;

import junit.framework.TestCase;

public class VertexProperties extends GraphElementProperties implements SectionWidgetID {
	private static final String INIT = "Init";
	
	public VertexProperties(SWTGefBot bot, SWTBotGefEditor editor) {
		super(bot, editor);
	}

	public void setInit(SWTBotGefEditPart part, String script) {
		selectPart(part);
		selectTab(part, INIT);
		SWTBotStyledText st = bot.styledTextWithId( WIDGET_ID,  WIDGET_SCRIPT);
		st.setText(script);
		UIThreadRunnable.syncExec(Display.getDefault(), new VoidResult() {
			@Override
			public void run() {
				st.widget.notifyListeners(SWT.FocusOut, null);
				editor.setFocus();
			}
		});
	}
	
	public boolean isShared(SWTBotGefEditPart part) {
		selectPart(part);
		selectTab(part, PROPERTIES);
		SWTBotCheckBox checkbox = bot.checkBoxWithId(VertexDefaultSection.WIDGET_ID,VertexDefaultSection.WIDGET_BUTTON_SHARED);
		return checkbox.isChecked();
	}
	


	public void setSharedName(SWTBotGefEditPart part, String sharedname, boolean enabled) {
		selectPart(part);
		SWTBotText text = botView.bot().textWithId(VertexDefaultSection.WIDGET_ID,
				VertexDefaultSection.WIDGET_TEXT_SHAREDNAME);
		if (enabled) {
			text.setText(sharedname);
			editor.setFocus();
			text.setFocus();
		} else {
			TestCase.assertFalse("Text should be disabled", text.isEnabled());
		}
	}



	public void setRequirement(SWTBotGefEditPart part, String requirement) {
		selectPart(part);
		SWTBotText text = botView.bot().textWithId(VertexDefaultSection.WIDGET_ID,
				VertexDefaultSection.WIDGET_TEXT_REQUIREMENTS);
		text.setText(requirement);
		editor.setFocus();
		text.setFocus();
	}

	public boolean isBlocked (SWTBotGefEditPart part) {
		AbstractGraphicalEditPart gep = (AbstractGraphicalEditPart) part.part();
		VertexFigure figure = (VertexFigure)gep.getFigure();
		return figure.isBlocked();
	}
	
	public boolean hasInitScript (SWTBotGefEditPart part) {
		AbstractGraphicalEditPart gep = (AbstractGraphicalEditPart) part.part();
		VertexFigure figure = (VertexFigure)gep.getFigure();
		return figure.hasInitScript();
	}	
	
	public boolean hasOpenSharedLinkEnabled (SWTBotGefEditPart part) {
		AbstractGraphicalEditPart gep = (AbstractGraphicalEditPart) part.part();
		VertexFigure figure = (VertexFigure)gep.getFigure();
		return figure.hasOpenSharedLinkAvailable();
	}
	
}
