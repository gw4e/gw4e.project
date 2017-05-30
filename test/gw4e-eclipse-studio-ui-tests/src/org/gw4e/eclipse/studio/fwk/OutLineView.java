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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.TableCollection;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.gw4e.eclipse.fwk.conditions.ViewOpened;
import org.gw4e.eclipse.studio.editor.GW4EEditor.GW4EOutlinePage;
import org.gw4e.eclipse.studio.editor.outline.OutLineComposite;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.editor.outline.filter.ThreeStateChoice;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class OutLineView   extends GraphHelper {
	private static final String SHOW_VIEW = "Show View";
	protected static final String OUTLINE = "Outline";
	
	protected SWTGefBot bot;
	protected SWTBotView botView;
	protected SWTBotGefEditor editor;
	
	public OutLineView(SWTGefBot bot, SWTBotGefEditor editor) {
		this.editor = editor;
		this.bot = bot;
		open();
		reset ();
	}
 
	private void open() {
		SWTBotMenu menu = bot.menu("Window");
		menu = menu.menu(SHOW_VIEW);
		menu = menu.menu("Other...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive(SHOW_VIEW));
		SWTBotShell shell = bot.shell(SHOW_VIEW).activate();
		SWTBotText text = shell.bot().text();
		text.setText(OUTLINE);
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				SWTBotTree tree = shell.bot().tree();
				if (tree != null) {
					TableCollection col = tree.selection();
					String selected = col.get(0, 0);
					if (OUTLINE.equals(selected)) {
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
		shell.bot().button("OK").click();

		bot.waitUntil(new ViewOpened(bot, OUTLINE));
		botView = getBotView();
	}
	
	public SWTBotView getBotView() {
		if (botView == null) {
			List<SWTBotView> views = bot.views();
			for (SWTBotView swtBotView : views) {
				String title = swtBotView.getTitle();
				if (OUTLINE.equalsIgnoreCase(title)) {
					botView = swtBotView;
				}
			}
		}
		return botView;
	}
 
	public void reset () {
		toggleFilterOn();
		expandVertexSection();
		expandEdgeSection();
		setNameText("");
		setDescriptionText("");
		setRequirementText("");
		setWeightText("");
		setBlockedComboToNoValue();
		setInitScriptComboToNoValue();
		setSharedComboToNoValue();
		setGuardComboToNoValue();
		setActionComboToNoValue();
		toggleFilterOff();
	}
	
	public String getNameText () {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_NAME_TEXT);
		return text.getText();
	}
	
	public void setNameText (String value) {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_NAME_TEXT);
		text.setText(value);
	}
	
	public String getDescriptionText () {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_DESCRIPTION_TEXT);
		return text.getText();
	}
	
	public void setDescriptionText (String value) {
		SWTBotText text=  botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_DESCRIPTION_TEXT);
		text.setText(value);
	}
	
	public String getRequirementText () {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_REQUIREMENT_TEXT);
		return text.getText();
	}
	
	public void setRequirementText (String value) {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_REQUIREMENT_TEXT);
		text.setText(value);
	}
	
	public String getWeightText () {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_WEIGHT_TEXT);
		return text.getText();
	}
	
	public void setWeightText (String value) {
		SWTBotText text= botView.bot().textWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_WEIGHT_TEXT);
		text.setText(value);
	}
	
	private void setOperatorComboTo(String value) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_WEIGHT_OPERATOR_COMBO);
		combo.setSelection(value);
	}
	
	public void setOperatorComboToOpNOOerator() {
		setOperatorComboTo(OutLineFilter.NO_OPERATOR);
	}

	public void setOperatorComboToEqual() {
		setOperatorComboTo(OutLineFilter.EQUAL_OPERATOR);
	}

	public void setOperatorComboToNotEqual() {
		setOperatorComboTo(OutLineFilter.NOT_EQUAL_OPERATOR);
	}

	public void setOperatorComboToUpper() {
		setOperatorComboTo(OutLineFilter.UPPER_OPERATOR);
	}

	public void setOperatorComboToUpperOrEqual() {
		setOperatorComboTo(OutLineFilter.UPPER_OR_EQUAL_OPERATOR);
	}

	public void setOperatorComboToLower() {
		setOperatorComboTo(OutLineFilter.LOWER_OPERATOR);
	}

	public void setOperatorComboToLowerOrEqual() {
		setOperatorComboTo(OutLineFilter.LOWER_OR_EQUAL_OPERATOR);
	}

	public void toggleFilterOn () {
		SWTBotCheckBox button = botView.bot().checkBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_FILTER_BUTTON);
		if (button.isChecked()) return;
		button.click();
	}
	public void toggleFilterOff() {
		SWTBotCheckBox button = botView.bot().checkBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_FILTER_BUTTON);
		if (button.isChecked()) {
			button.click();
		}
	}
	
	public void setBlockedComboToNo () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_BLOCKED_COMBO);
		combo.setSelection(ThreeStateChoice.NO.getLabel());
	}
	public void setBlockedComboToYes () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_BLOCKED_COMBO);
		combo.setSelection(ThreeStateChoice.YES.getLabel());
	}
	public void setBlockedComboToNoValue () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_BLOCKED_COMBO);
		combo.setSelection(ThreeStateChoice.NO_VALUE.getLabel());
	}
	
	public void setInitScriptComboToNo () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_INITSCRIPT_COMBO);
		combo.setSelection(ThreeStateChoice.NO.getLabel());
	}
	public void setInitScriptComboToYes () {
		SWTBotCombo combo = bot.comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_INITSCRIPT_COMBO);
		combo.setSelection(ThreeStateChoice.YES.getLabel());
	}
	public void setInitScriptComboToNoValue () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_INITSCRIPT_COMBO);
		combo.setSelection(ThreeStateChoice.NO_VALUE.getLabel());
	}
	public void setSharedComboToNo () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_SHARED_COMBO);
		combo.setSelection(ThreeStateChoice.NO.getLabel());
	}
	public void setSharedComboToYes ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_SHARED_COMBO);
		combo.setSelection(ThreeStateChoice.YES.getLabel());
	}
	public void setSharedComboToNoValue ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_SHARED_COMBO);
		combo.setSelection(ThreeStateChoice.NO_VALUE.getLabel());
	}
	public void setGuardComboToNo () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_GUARD_COMBO);
		combo.setSelection(ThreeStateChoice.NO.getLabel());
	}
	public void setGuardComboToYes ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_GUARD_COMBO);
		combo.setSelection(ThreeStateChoice.YES.getLabel());
	}
	public void setGuardComboToNoValue ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_GUARD_COMBO);
		combo.setSelection(ThreeStateChoice.NO_VALUE.getLabel());
	}
	public void setActionComboToNO () {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_ACTION_COMBO);
		combo.setSelection(ThreeStateChoice.NO.getLabel());
	}
	public void setActionComboToYes ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_ACTION_COMBO);
		combo.setSelection(ThreeStateChoice.YES.getLabel());
	}
	public void setActionComboToNoValue ( ) {
		SWTBotCombo combo = botView.bot().comboBoxWithId(OutLineComposite.GW_WIDGET_ID, OutLineComposite.GW_OUTLINE_ACTION_COMBO);
		combo.setSelection(ThreeStateChoice.NO_VALUE.getLabel());
	}
	
	public boolean isSelected (SWTBotGefEditPart part) {
		GraphElement element  = (GraphElement) part.part().getModel();
		SWTBotTree tree = botView.bot().treeWithId(GW4EOutlinePage.GW_WIDGET_ID,GW4EOutlinePage.GW_OUTLINE_ELEMENTS_TREE);
		SWTBotTreeItem item = tree.getTreeItem(element.getName()); 
		return item.isSelected();
	}
	
	public void select (String name) {
		SWTBotTree tree = botView.bot().treeWithId(GW4EOutlinePage.GW_WIDGET_ID,GW4EOutlinePage.GW_OUTLINE_ELEMENTS_TREE);
		SWTBotTreeItem[] items = tree.getAllItems();
		for (SWTBotTreeItem swtBotTreeItem : items) {
			if (swtBotTreeItem.getText().equals(name)) {
				 
				swtBotTreeItem.select();
				break;
			}
		}
		ICondition conditon = new ICondition () {
			@Override
			public boolean test() throws Exception {
				SWTBotTreeItem[] items = tree.getAllItems();
				SWTBotTreeItem selected = null;
				for (SWTBotTreeItem swtBotTreeItem : items) {
					if (swtBotTreeItem.getText().equals(name)) {
						selected = swtBotTreeItem;
						break;
					}
				}
				return selected.isSelected();
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {				 
				return "item not selected ";
			}
		};
		bot.waitUntil(conditon);
	}
	
	public Map<String,SWTBotTreeItem> geVisibleTreeItems () {
		Map<String,SWTBotTreeItem> temp = new HashMap<String,SWTBotTreeItem>();
		SWTBotTree tree = botView.bot().treeWithId(GW4EOutlinePage.GW_WIDGET_ID,GW4EOutlinePage.GW_OUTLINE_ELEMENTS_TREE);
		SWTBotTreeItem[] items = tree.getAllItems();
		for (SWTBotTreeItem swtBotTreeItem : items) {
			if (swtBotTreeItem.isVisible()) temp.put(swtBotTreeItem.getText(),swtBotTreeItem);
		}
		return temp;
	}
	
	public int geVisibleRowCount () {
		SWTBotTree tree = botView.bot().treeWithId(GW4EOutlinePage.GW_WIDGET_ID,GW4EOutlinePage.GW_OUTLINE_ELEMENTS_TREE);
		return tree.visibleRowCount();
	}
	
	public int geRowCount () {
		SWTBotTree tree = botView.bot().treeWithId(GW4EOutlinePage.GW_WIDGET_ID,GW4EOutlinePage.GW_OUTLINE_ELEMENTS_TREE);
		return tree.getAllItems().length;
	}
	
	public void expandVertexSection () {
		expandSection(OutLineComposite.GW_OUTLINE_VERTEX_EXPAND);
	}
	
	public void expandEdgeSection () {
		expandSection(OutLineComposite.GW_OUTLINE_EDGE_EXPAND);
	}

	public void expandSection (String which) {
		 
		botView.bot().widget(new BaseMatcher  () {
			@Override
			public boolean matches(Object item) {
				if (item instanceof org.eclipse.ui.forms.widgets.ExpandableComposite) {
					ExpandableComposite ec =  (ExpandableComposite)item;
					if (ec.isExpanded()) return true;
					String value = (String)ec.getData(GW4EOutlinePage.GW_WIDGET_ID);
					if (value != null && which.equals(value)) {
						Method method = null;
						try {
							method = ExpandableComposite.class.getDeclaredMethod("programmaticToggleState"); //$NON-NLS-1$
							method.setAccessible(true);
							method.invoke(ec);
							ec.getParent().layout(true);
							 
						} catch (Exception e) {
							// ignore
						}
						return true;
					}
				}
				return false;
			}
			@Override
			public void describeTo(Description description) {
			}
		});
	}
	
	private Event createEvent(Control control) {
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
	
	public ICondition createTreeRowCountCondition (final int count) {
		return new ICondition () {
			@Override
			public boolean test() throws Exception {
				return  geVisibleRowCount () == count;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Expected row coutn not found ";
			}
			
		};
	}
}
