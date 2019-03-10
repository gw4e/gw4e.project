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

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.waits.WaitForObjectCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList.ListElement;
import org.gw4e.eclipse.fwk.conditions.ViewOpened;
import org.gw4e.eclipse.studio.editor.properties.CustomProperties;
import org.gw4e.eclipse.studio.editor.properties.vertex.VertexDefaultSection;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import junit.framework.TestCase;

public class GraphElementProperties   extends GraphHelper {
	private static final String SHOW_VIEW = "Show View";
	protected static final String PROPERTIES = "Properties";
	protected static final String CUSTOM = "Custom";
	
	protected SWTGefBot bot;
	protected SWTBotView botView;
	protected SWTBotGefEditor editor;
	
	public GraphElementProperties(SWTGefBot bot, SWTBotGefEditor editor) {
		this.editor = editor;
		this.bot = bot;
		open();
	}

	public void assertPropertiesShown(SWTBotGefEditPart part) {
		GraphElement element  = (GraphElement) part.part().getModel();
		selectTab(part, PROPERTIES);
		ICondition condition= new ICondition () {

			@Override
			public boolean test() throws Exception {
				return botView.bot().text(element.getName()) != null;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return null;
			}
			
		};
		bot.waitUntil(condition);
	}
	
	public String getCustomProperty(SWTBotGefEditPart part, String key) {
		GraphElement element = selectPart(part);
		selectTab(part, CUSTOM);
		SWTBotTable table = botView.bot().tableWithId(CustomProperties.PROJECT_PROPERTY_PAGE_WIDGET_ID, CustomProperties.CUSTOM_PROPERTY_LIST_WITH_BUTTON);
		int row = table.indexOf(key, "PROPERTY");
		return table.cell(row, "VALUE");
	}
	
	public void updateKeyCustomProperty(SWTBotGefEditPart part, String key, String newkey) {
		GraphElement element = selectPart(part);
		selectTab(part, CUSTOM);
		SWTBotTable table = botView.bot().tableWithId(CustomProperties.PROJECT_PROPERTY_PAGE_WIDGET_ID, CustomProperties.CUSTOM_PROPERTY_LIST_WITH_BUTTON);
		 
		 
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				int row = table.indexOf(key, "PROPERTY");
				if (row == -1) return true;
				table.click(row, 0);
			    bot.sleep(1000);
			    bot.text(key, 0).setText(newkey);
			    bot.text(KeyStroke.getInstance(SWT.TAB)+"" , 0);
			    return false;
			}
			@Override
			public String getFailureMessage() {
				return "key not set";
			}
		},2 * 60 * 1000);		
	}
	
	public void updateValueCustomProperty(SWTBotGefEditPart part, String key, String oldvalue, String newValue) {
		GraphElement element = selectPart(part);
		selectTab(part, CUSTOM);
		SWTBotTable table = botView.bot().tableWithId(CustomProperties.PROJECT_PROPERTY_PAGE_WIDGET_ID, CustomProperties.CUSTOM_PROPERTY_LIST_WITH_BUTTON);
		 
		 
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				int row = table.indexOf(oldvalue, "VALUE");
				if (row == -1) return true;
				table.click(row, 1);
			    bot.sleep(1000);
			    bot.text(oldvalue, 0).setText(newValue);
			    bot.text(KeyStroke.getInstance(SWT.TAB)+"" , 0);
			    return false;
			}
			@Override
			public String getFailureMessage() {
				return "value not set";
			}
		},2 * 60 * 1000);		
	}
	
	public void addCustomProperty(SWTBotGefEditPart part, String key, String value) {
		GraphElement element = selectPart(part);
		selectTab(part, CUSTOM);
		SWTBotTable table = botView.bot().tableWithId(CustomProperties.PROJECT_PROPERTY_PAGE_WIDGET_ID, CustomProperties.CUSTOM_PROPERTY_LIST_WITH_BUTTON);
		 
		table.header("PROPERTY").contextMenu("Add entry").click();
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				return table.indexOf("new key", "PROPERTY")!=-1;
			}
			@Override
			public String getFailureMessage() {
				return "row not clicked";
			}
		},60 * 1000);
		
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				int row = table.indexOf("new key", "PROPERTY");
				 
				if (row == -1) return true;
				table.click(row, 0);
			    bot.sleep(1000);
			    bot.text("new key", 0).setText(key);
			    bot.text(KeyStroke.getInstance(SWT.TAB)+"" , 0);
			    return false;
			}
			@Override
			public String getFailureMessage() {
				return "key not set";
			}
		},2 * 60 * 1000);

		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				int row = table.indexOf("new value", "VALUE");
				if (row == -1) return true;
 				table.click(row, 1);
			    bot.sleep(1000);
			    bot.text("new value").setText(value);
			    bot.text(KeyStroke.getInstance(SWT.TAB)+"" , 0);
			    return false;
			}
			@Override
			public String getFailureMessage() {
				return "value not set";
			}
		},2 * 60 * 1000);
 
	}
	
	public void deleteCustomProperty(SWTBotGefEditPart part, String key) {
		GraphElement element = selectPart(part);
		selectTab(part, CUSTOM);
		SWTBotTable table = botView.bot().tableWithId(CustomProperties.PROJECT_PROPERTY_PAGE_WIDGET_ID, CustomProperties.CUSTOM_PROPERTY_LIST_WITH_BUTTON);
		 
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				int row = table.indexOf(key, "PROPERTY");
				table.click(row, 0);
				table.contextMenu("Remove entry").click();
				return table.indexOf(key, 0) == -1;
			}
			@Override
			public String getFailureMessage() {
				return "row not deleted";
			}
		},2 * 60 * 1000);
	}
	
	public void setName(SWTBotGefEditPart part, String name, boolean enabled) {
		GraphElement element = selectPart(part);
		selectTab(part, PROPERTIES);
		SWTBotText text = botView.bot().text(element.getName());
		if (enabled) {
			text.setText(name);
			bot.waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					try {
						botView.bot().text(name);
						return true;
					} catch (Exception e) {
						return false;
					}
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Cannot find text with " + element.getName();
				}
			});
			// 'text' needs a focus out event to take the change into account. Try to make a better job ...
			text = botView.bot().text(name);
			focusOut(part,text,PROPERTIES);
		} else {
			TestCase.assertFalse("Text should be disabled", text.isEnabled());
		}
	}
	
	protected void focusOut(SWTBotGefEditPart part,SWTBotStyledText text,String tab ) {
		editor.setFocus();
		selectPart(part);
		selectTab(part, tab);
	 	try {
			text.setFocus();
		} catch (Throwable e) {
		}
	} 
	protected void focusOut(SWTBotGefEditPart part,SWTBotText text,String tab ) {
		editor.setFocus();
		selectPart(part);
		selectTab(part, tab);
	 	try {
			text.setFocus();
		} catch (Throwable e) {
		}
	}
	
	public void setDescription(SWTBotGefEditPart part, String description) {
		selectPart(part);
		SWTBotStyledText text = botView.bot().styledTextWithId(VertexDefaultSection.WIDGET_ID,
				VertexDefaultSection.WIDGET_TEXT_DESCRIPTION);
		text.setText(description);
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				try {
					botView.bot().styledText(description);
					return true;
				} catch (Exception e) {
					return false;
				}
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Cannot find text with " + description;
			}
		});
		text = botView.bot().styledText(description);
		focusOut(part,text,PROPERTIES);
	}
	
	public void toggleBlocked(SWTBotGefEditPart part) {
		selectPart(part);
		selectTab(part, PROPERTIES);
		SWTBotCheckBox checkbox = bot.checkBoxWithId(VertexDefaultSection.WIDGET_ID,VertexDefaultSection.WIDGET_BUTTON_BLOCKED);
		if (checkbox.isChecked()) {
			checkbox.deselect();
		} else {
			checkbox.select();
		}
	}
	
	public GraphElement selectPart(SWTBotGefEditPart part) {
		editor.show();
		if (part==null) {
			editor.click(1, 1);
			return null;
		}
		part.select();
		GraphElement element = (GraphElement) (part.part()).getModel();
		return element;
	}
	
	
	protected void selectTab(SWTBotGefEditPart part, String label) {
		selectPart(part);
		Matcher<TabbedPropertyList> matcher = new BaseMatcher<TabbedPropertyList>() {
			@Override
			public boolean matches(Object item) {
				if (item instanceof TabbedPropertyList) {
					return true;
				}
				return false;
			}
			@Override
			public void describeTo(Description description) {
			}
		};
		List<TabbedPropertyList> allWidgets = widget(matcher);
		UIThreadRunnable.syncExec(Display.getDefault(), new VoidResult() {
			@Override
			public void run() {
				for (final TabbedPropertyList tabbedProperty : allWidgets) {
					final ListElement tabItem = getTabItem(label, tabbedProperty);
					if (tabItem != null) {
						final Event mouseEvent = createEvent(tabItem);
						tabItem.notifyListeners(SWT.MouseUp, mouseEvent);
						tabItem.setFocus();
						return;
					}
				}
				throw new IllegalStateException("Unable to select the tab " + label);
			}
		});
	}
	
	private ListElement getTabItem(String label, TabbedPropertyList tabbedProperty) {
		for (final Object le : tabbedProperty.getTabList()) {
			if (le instanceof ListElement) {
				String s = ((ListElement) le).getTabItem().getText();
				if (label.equals(s)) {
					return (ListElement) le;
				}
			}
		}
		return null;
	}

	public SWTBotView getBotView() {
		if (botView == null) {
			List<SWTBotView> views = bot.views();
			for (SWTBotView swtBotView : views) {
				String title = swtBotView.getTitle();
				if (PROPERTIES.equalsIgnoreCase(title)) {
					botView = swtBotView;
				}
			}
		}
		return botView;
	}
	private void open() {
		SWTBotMenu menu = bot.menu("Window");
		menu = menu.menu(SHOW_VIEW);
		menu = menu.menu("Other...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive(SHOW_VIEW));
		SWTBotShell shell = bot.shell(SHOW_VIEW).activate();
		SWTBotText text = shell.bot().text();
		text.setText(PROPERTIES);
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				SWTBotTree tree = shell.bot().tree();
				if (tree != null) {
					TableCollection col = tree.selection();
					String selected = col.get(0, 0);
					if (PROPERTIES.equals(selected)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Failed to set the text.";
			}

		});
		String name = "OK";
		if (GW4EPlatform.isEclipse47()) name = "Open";
		shell.bot().button(name).click();
 
		bot.waitUntil(new ViewOpened(bot, PROPERTIES));
		botView = getBotView();
	}
	

	protected <T extends Widget> List<T> widget(final Matcher<T> matcher) {
		WaitForObjectCondition<T> waiWidget = Conditions.waitForWidget(matcher);
		bot.waitUntilWidgetAppears(waiWidget);
		return waiWidget.getAllMatches();
	}

	protected Event createEvent(Control control) {
		Event evt = new Event();
		evt.widget = control;
		evt.x = control.getBounds().x;
		evt.y = control.getBounds().y;
		evt.display = bot.getDisplay();
		evt.count = 1;
		evt.button = 1;
		evt.stateMask = SWT.BUTTON1;
		evt.time = (int) System.currentTimeMillis();
		return evt;
	}   
 
	protected Event createFocusEvent(Control control) {
		Event evt = new Event();
		evt.widget = control;
		evt.x = control.getBounds().x;
		evt.y = control.getBounds().y;
		evt.display = bot.getDisplay();
		 
		evt.time = (int) System.currentTimeMillis();
		return evt;
	}  
}
