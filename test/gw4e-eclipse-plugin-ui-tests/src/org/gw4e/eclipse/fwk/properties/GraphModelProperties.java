package org.gw4e.eclipse.fwk.properties;

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

import java.util.Set;

import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotLabel;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.property.GraphModelPropertyPage;

public class GraphModelProperties {
	SWTBot bot;
	String project;
	String folder;
	String pkg;
	String filename;
 
	
	public GraphModelProperties(SWTBot bot, String project, String folder,String pkg,  String filename) {
		super();
		this.bot = bot;
		this.project = project;
		this.folder = folder;
		this.pkg = pkg;
		this.filename = filename;
		bot.tree().setFocus();
		
		bot.tree().select("GW4E");
		bot.waitUntil(Conditions.waitForWidget(WidgetMatcherFactory.withId(GraphModelPropertyPage.GW4E_LABEL_ID, GraphModelPropertyPage.GW4E_LABEL_ID)));
	}

	public void cancel () {
		bot.button("Cancel").click();
	}
	 
	public boolean hasValidFilename () {
		SWTBotLabel sl = bot.label(filename);
		return sl!=null;
	}
	
	public boolean hasValidPath ( ) {
		String label = "/"+project+"/"+folder+"/"+ pkg +"/"+filename;
		SWTBotLabel sl = bot.label(label);
		return sl!=null;
	}
	
	public boolean hasRequirements (Set<String> requirements) {
		SWTBotText st = bot.textWithId(GraphModelPropertyPage.GW4E_FILE_REQUIREMENT_TEXT_ID,GraphModelPropertyPage.GW4E_FILE_REQUIREMENT_TEXT_ID);
		String currentText = st.getText();
		for (String req : requirements) {
			if (currentText.indexOf(req)!=-1) continue;
			return false;
		}
		return true;
	}
	 
	public boolean hasMethods (Set<String> methods) {
		SWTBotText st = bot.textWithId(GraphModelPropertyPage.GW4E_FILE_METHODS_TEXT_ID,GraphModelPropertyPage.GW4E_FILE_METHODS_TEXT_ID);
		String currentText = st.getText();
		for (String method : methods) {
			if (currentText.indexOf(method)!=-1) continue;
			return false;
		}
		return true;
	}
}
