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

import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.figure.EdgeFigure;

public class EdgeProperties extends GraphElementProperties implements SectionWidgetID {
	private static final String GUARD = "Guard";
	private static final String ACTION = "Action";
	
	public EdgeProperties(SWTGefBot bot, SWTBotGefEditor editor) {
		super(bot, editor);
	}

	public void setWeight(SWTBotGefEditPart part, double value) {
		selectPart(part);
		selectTab(part, PROPERTIES);
		final SWTBotText text = botView.bot().textWithId(WIDGET_ID,WIDGET_TEXT_WEIGHT);
		text.setText(value+"");
	 
		Display.getDefault().asyncExec(new Runnable () {
			@Override
			public void run() {
				//SWTBotText text = botView.bot().textWithId(WIDGET_ID,WIDGET_TEXT_WEIGHT);
				text.widget.notifyListeners(SWT.FocusOut,  createFocusEvent((Control)text.widget));
			}
		});
	}

	
	public String getWeight(SWTBotGefEditPart part) {
		selectPart(part);
		SWTBotText text = botView.bot().textWithId(WIDGET_ID,WIDGET_TEXT_DESCRIPTION);
		String s = text.getText();
		if (s==null) return "";
		return s;
	}
	
	public void setGuard(SWTBotGefEditPart part, String script) {
		selectPart(part);
		selectTab(part, GUARD);
		SWTBotStyledText st = bot.styledTextWithId(WIDGET_ID, WIDGET_GUARD_SCRIPT);
		st.setText(script);
		UIThreadRunnable.syncExec(Display.getDefault(), new VoidResult() {
			@Override
			public void run() {
				st.widget.notifyListeners(SWT.FocusOut, null);
				editor.setFocus();
			}
		});
	}
	
	public String getGuard(SWTBotGefEditPart part) {
		selectPart(part);
		selectTab(part, GUARD);
		SWTBotStyledText st = bot.styledTextWithId(WIDGET_ID, WIDGET_GUARD_SCRIPT);
		String s = st.getText();
		if (s==null) return "";
		return s;
	}
	
	public void setAction(SWTBotGefEditPart part, String script) {
		selectPart(part);
		selectTab(part, ACTION);
		SWTBotStyledText st = bot.styledTextWithId(WIDGET_ID, WIDGET_ACTION_SCRIPT);
		st.setText(script);
		UIThreadRunnable.syncExec(Display.getDefault(), new VoidResult() {
			@Override
			public void run() {
				st.widget.notifyListeners(SWT.FocusOut, null);
				editor.setFocus();
			}
		});
	}
	
	public String getAction(SWTBotGefEditPart part) {
		selectPart(part);
		selectTab(part, ACTION);
		SWTBotStyledText st = bot.styledTextWithId(WIDGET_ID, WIDGET_ACTION_SCRIPT);
		String s = st.getText();
		if (s==null) return "";
		return s;
	}
	
	public boolean isBlocked (SWTBotGefEditPart part) {
		AbstractConnectionEditPart cep = (AbstractConnectionEditPart) part.part();
		EdgeFigure figure = (EdgeFigure)cep.getFigure();
		return figure.isBlocked();
	}
	
	public boolean hasAction (SWTBotGefEditPart part) {
		AbstractConnectionEditPart cep = (AbstractConnectionEditPart) part.part();
		EdgeFigure figure = (EdgeFigure)cep.getFigure();
		return figure.hasAction();
	}	
	
	public boolean hasGuard (SWTBotGefEditPart part) {
		AbstractConnectionEditPart cep = (AbstractConnectionEditPart) part.part();
		EdgeFigure figure = (EdgeFigure)cep.getFigure();
		return figure.hasGuard();
	}		
	 
}
