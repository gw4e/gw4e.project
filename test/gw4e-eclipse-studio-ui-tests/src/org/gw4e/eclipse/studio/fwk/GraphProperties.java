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

import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCCombo;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;

public class GraphProperties extends GraphElementProperties implements SectionWidgetID {
	 
	public GraphProperties(SWTGefBot bot, SWTBotGefEditor editor) {
		super(bot, editor);
	}
	
	public void setItem(String value) {
		selectPart(null);
		selectTab(null, PROPERTIES);
		SWTBotCCombo combo = bot.ccomboBoxWithId( WIDGET_ID,  WIDGET_COMBO_EDGE);
		combo.setSelection(value);
	}
	
	public String getItem() {
		selectPart(null);
		selectTab(null, PROPERTIES);
		SWTBotCCombo combo = bot.ccomboBoxWithId( WIDGET_ID,  WIDGET_COMBO_EDGE);
		return combo.getText();
	}
	 
}
