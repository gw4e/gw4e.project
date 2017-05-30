package org.gw4e.eclipse.fwk.preferences;

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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.fwk.conditions.ShellActiveCondition;
import org.gw4e.eclipse.launching.test.OSUtils;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.hamcrest.Matcher;

public class GW4EPreferencePage {

	static SWTWorkbenchBot bot;
	SWTBotShell shell;

	public GW4EPreferencePage(SWTWorkbenchBot wbot) {
		bot = wbot;
	}

	public void cancel() {
		shell =  getPreferenceDialog();
		cancelGW4EPreference(shell.getText());
	}
	
	public void open() {
		shell = showPreferenceDialog();
		showGW4EPreference(shell);
	}
	
	public boolean isGraphWalkerLibrariesSize(int size) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.GRAPHWALKER_JAVALIBRARIES);
		return ct.isTableSize(size);
	}
	
	
	public boolean graphWalkerLibrariesContainValues(String [] thevalues ) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.GRAPHWALKER_JAVALIBRARIES);
		return ct.tableContainValues(thevalues);
	}
	
	public void addRowValueToGraphWalkerLibraries(String stringtoadd) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.GRAPHWALKER_JAVALIBRARIES);
		ct.addRowValue(stringtoadd);
	}
	
	public boolean graphWalkerLibrariesContainValue(String thevalue) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.GRAPHWALKER_JAVALIBRARIES);
		return ct.tableContainValue(thevalue);
	}
	
	public void removeRowValueFromGraphWalkerLibraries(String stringtremove) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.GRAPHWALKER_JAVALIBRARIES);
		ct.removeRow(stringtremove);
	}
	
	public boolean isAuthorizedFoldersForGraphModelsSize(int size) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		return ct.isTableSize(size);
	}
	
	
	public boolean authorizedFoldersForGraphModelsContainValues(String [] thevalues ) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		return ct.tableContainValues(thevalues);
	}
	
	public void addRowValueToAuthorizedFoldersForGraphModels(String stringtoadd) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		ct.addRowValue(stringtoadd);
	}
	
	public boolean authorizedFoldersForGraphModelsContainValue(String thevalue) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		return ct.tableContainValue(thevalue);
	}
	
	public void removeRowValueFromAuthorizedFoldersForGraphModels(String stringtremove) {
		CustomTableWithButtons ct = new CustomTableWithButtons (PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		ct.removeRow(stringtremove);
	}
	
	public void apply() {
		SWTBotButton applyButton = getApplyButton();
		applyButton.click();
	}
	 
	public void restoreDefaults () {
		SWTBotButton button = getRestoreDefaultsButton();
		button.click();
	}
	
	public class CustomTableWithButtons {
		String id;
		
		public CustomTableWithButtons (String id) {
			this.id = id;
		}	 
		
		private SWTBotTable getTable() {
			SWTBotTable table = bot.tableWithId("id.table", id);
			return table;
		}

		boolean tableContainValue(String thevalue) {
			SWTBotTable table = getTable();
			return table.containsItem(thevalue);
		}

		void selectRowWithValue (SWTBotTable table,String value) {
			SWTBotTableItem item = table.getTableItem(value);
			item.select();
			item.click();
			 
		}
		
		boolean tableContainValues(String [] thevalues ) {
			SWTBotTable table = getTable();
			for (int i = 0; i < thevalues.length; i++) {
				if (!table.containsItem(thevalues[i]) ) return false ; 
			}
			return true;
		}
		
		boolean isTableSize(int size ) {
			SWTBotTable table = getTable();
			return table.rowCount() == size;
		}		 
		
		void editRowWithValue (SWTBotTable table,String oldvalue, String newvalue) {
			selectRowWithValue(table,oldvalue);
			bot.text(oldvalue).setText(newvalue);
		}
		
	    void addRowValue(final String stringtoadd) {
			SWTBotTable table = getTable();
			bot.waitUntil(waitForTable());

			int currentCount = table.rowCount();
			SWTBotButton addButton = getAddButton();
			addButton.click();
			waitForRowAdded(table, currentCount + 1);
			int row = 0;
		 
			editRowWithValue (table,MessageUtil.getString("enteranewvalue"), stringtoadd);
			
			leaveEditingCellClickingOnAnotherCell(table, row + 1);
			waitForExpectedValueAtRowCol(table,stringtoadd);
		}

		void removeRow( String stringtoremove) {
			SWTBotTable table = getTable();
			bot.waitUntil(waitForTable());
			
			selectRowWithValue(table,stringtoremove);
			waitForExpectedValueAtRowCol(table,stringtoremove);
		
			int currentCount = table.rowCount();
			SWTBotButton removeButton = getRemoveButton();
			
			removeButton.click();
			waitForRowAdded(table, currentCount - 1);
			waitForNotExpectedValueAtRowCol(table,stringtoremove);
		}

		
		 
		
		private  ICondition waitForTable() {
			return new DefaultCondition() {
				public boolean test() throws Exception {
					SWTBotTable table = getTable();
					return table != null;
				}

				public String getFailureMessage() {
					return "Table : Authorized Folder For Graph Definition has not been found";
				}
			};

		}
		private SWTBotButton getAddButton() {
			SWTBotButton addButton = bot.buttonWithId("id.button.add",this.id);
			return addButton;
		}

		private SWTBotButton getRemoveButton() {
			SWTBotButton removeButton = bot.buttonWithId("id.button.remove",this.id);
			return removeButton;
		}
		
		private void waitForRowAdded(final SWTBotTable table, final int expectedCount) {
			ICondition condition = new DefaultCondition() {
				public boolean test() throws Exception {
					return table.rowCount() == expectedCount;
				}

				public String getFailureMessage() {
					return "Table " + id + " has not " + expectedCount + " row(s). It has " + expectedCount + " row(s)";
				}
			};
			bot.waitUntil(condition);
		}

		private void waitForExpectedValueAtRowCol(final SWTBotTable table, final String expectedValue) {
			ICondition condition = new DefaultCondition() {
				public boolean test() throws Exception {
					return table.containsItem(expectedValue);
				}

				public String getFailureMessage() {
					return "Table '"+ id + "  does not contains " + expectedValue;
				}
			};
			bot.waitUntil(condition);
		}

		private void waitForNotExpectedValueAtRowCol(final SWTBotTable table,  
				final String expectedValue) {
			ICondition condition = new DefaultCondition() {
				public boolean test() throws Exception {
					return !table.containsItem(expectedValue);
				}

				public String getFailureMessage() {
					return "Table '"+ id + " does not contains " + expectedValue;
				}
			};
			bot.waitUntil(condition);
		}

		private void leaveEditingCellClickingOnAnotherCell(SWTBotTable table, int row) {
			table.click(row, 0);
		}
	}
	
	 
	private SWTBotButton getApplyButton() {
		SWTBotButton applyButton = bot.button("Apply");
		return applyButton;
	}
	
	private SWTBotButton getRestoreDefaultsButton() {
		SWTBotButton button = bot.button("Restore &Defaults");
		return button;
	}
	
	private void cancelGW4EPreference (String title) {
		 SWTBotButton cancelButton = bot.button("Cancel");	
		 cancelButton.click();
		 bot.waitWhile(Conditions.shellIsActive(title));
	}
	
	private void showGW4EPreference(SWTBotShell shell) {
		bot.tree().select("GW4E Preferences");
		Matcher<Shell> matcher = WidgetMatcherFactory.withId("id", PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION);
		bot.waitUntil(Conditions.waitForWidget(matcher, shell.widget));
	}

	private SWTBotShell getPreferenceDialog() {
		Matcher<Shell> matcher = WidgetMatcherFactory.withText("Preferences");
		bot.waitUntil(Conditions.waitForShell(matcher));
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		return shell;
	}
	
	private SWTBotShell showPreferenceDialog() {
		if (OSUtils.isWindows() || OSUtils.isLinux()) {
			return showPreferenceDialogWindowPreference();
		} else {
			return showPreferenceDialogMAC() ;
		}
	}
	
	private SWTBotShell showPreferenceDialogWindowPreference() {
		ICondition condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				try {
					bot.menu("Window").menu("Preferences").click();
					bot.waitUntil(new ShellActiveCondition("Preferences"), 5 * 1000);
					return true;
				} catch (Throwable e) {
				} 
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "Cannot open the Preference page";
			}
		};
		bot.waitUntil(condition, 30 * 1000);
		SWTBotShell shell = bot.shell("Preferences");
		shell.activate();
		return shell;	
	}
	
	private SWTBotShell showPreferenceDialogMAC() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					Menu appMenu = workbench.getDisplay().getSystemMenu();
					for (MenuItem item : appMenu.getItems()) {
						if (item.getText().startsWith("Preferences")) {
							Event event = new Event();
							event.time = (int) System.currentTimeMillis();
							event.widget = item;
							event.display = workbench.getDisplay();
							item.setSelection(true);
							item.notifyListeners(SWT.Selection, event);
							break;
						}
					}
				}
			}
		});
		return  getPreferenceDialog() ;
	}

	 
}
