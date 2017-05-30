package org.gw4e.eclipse.test.preferences;

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

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.preferences.GW4EPreferencePage;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4EPreferencePageTestCase {
	private static SWTWorkbenchBot bot;

	@BeforeClass
	public static void beforeClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.TIMEOUT = 6000;
		bot = new SWTWorkbenchBot();
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
		}
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
    public static void afterClass() {
		try {
			GW4EPreferencePage page = new GW4EPreferencePage(bot);
			page.open(); 
			try {
				page.restoreDefaults();
				page.apply();
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("Unable to clean up the GW librairies preference ... GW Project classpath project might be corrupted.");
			} finally {
				page.cancel();
			} 
		} finally {
			bot.resetWorkbench();
		}
		
    }
	

	@Test
	public void testAddRemoveGraphWalkerLibraries() {
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();
		try {

			String stringtoadd = "new row added";

			// Add a row to the GraphWalker Libraries Table
			page.addRowValueToGraphWalkerLibraries(stringtoadd);
			Assert.assertTrue("new row not added in the table", page.graphWalkerLibrariesContainValue(stringtoadd));
			// Remove a row to the GraphWalker Libraries Table
			page.removeRowValueFromGraphWalkerLibraries(stringtoadd);
			Assert.assertFalse("new row added in the table not removed",
					page.graphWalkerLibrariesContainValue(stringtoadd));
		} finally {
			page.cancel();
		}

	}

	@Test
	public void testAddRemoveAuthorizedFolderForGraphDefinition() {
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();

		try {
			String stringtoadd = "new row added";

			// Add a row to the Authorized Folders For Graph Models Table
			page.addRowValueToAuthorizedFoldersForGraphModels(stringtoadd);
			Assert.assertTrue("new row not added in the table",
					page.authorizedFoldersForGraphModelsContainValue(stringtoadd));
			// Remove a row to the Authorized Folders For Graph Models Table
			page.removeRowValueFromAuthorizedFoldersForGraphModels(stringtoadd);
			Assert.assertFalse("new row added in the table not removed",
					page.authorizedFoldersForGraphModelsContainValue(stringtoadd));
		} finally {
			page.cancel();
		}

	}

	@Test
	public void testUpdateAndApplyAuthorizedFolderForGraphDefinition() {
		String stringtoadd = "new row added";
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();

		try {
			// Add a row to the Authorized Folders For Graph Models Table
			page.addRowValueToAuthorizedFoldersForGraphModels(stringtoadd);
			Assert.assertTrue("new row not added in the table",
					page.authorizedFoldersForGraphModelsContainValue(stringtoadd));
			// Remove a row to the Authorized Folders For Graph Models Table
			page.removeRowValueFromAuthorizedFoldersForGraphModels("src/main/resources");
			Assert.assertFalse("new row added in the table not removed",
					page.authorizedFoldersForGraphModelsContainValue("src/main/resources"));
			page.apply();
		} finally {
			page.cancel();
		}

		page.open();
		try {
			Assert.assertFalse("new row added in the table not removed",
					page.authorizedFoldersForGraphModelsContainValue("src/main/resources"));
			Assert.assertTrue("new row not added in the table",
					page.authorizedFoldersForGraphModelsContainValue(stringtoadd));
		} finally {
			page.cancel();
		}
	}
	
	@Test
	public void testUpdateAndApplyGraphWalkerLibraries() {
		String stringtoadd = "new row added";
		String [] libraries = PreferenceManager.getDefaultGraphWalkerLibraries();
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();
		try {
			// Add a row to the GraphWalker Libraries Table
			page.addRowValueToGraphWalkerLibraries(stringtoadd);
			Assert.assertTrue("new row not added in the table", page.graphWalkerLibrariesContainValue(stringtoadd));
			// Remove a row to the GraphWalker Libraries Table

			page.removeRowValueFromGraphWalkerLibraries(libraries[0]);
			Assert.assertFalse("new row added in the table not removed",
					page.graphWalkerLibrariesContainValue(libraries[0]));
			page.apply();
		} finally {
			page.cancel();
		}
		page.open();
		try {
			Assert.assertTrue("apply button did not save the state", page.graphWalkerLibrariesContainValue(stringtoadd));
			Assert.assertFalse("apply button did not save the state",page.graphWalkerLibrariesContainValue(libraries[0]));
		} finally {
			page.cancel();
		}
	}
	
	@Test
	public void testRestoreDefaultAuthorizedFolderForGraphDefinition() {
		testUpdateAndApplyAuthorizedFolderForGraphDefinition() ;	 
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();

		try {
			page.restoreDefaults();
			String [] folders = PreferenceManager.getDefaultAuthorizedFolderForGraphDefinition();
			Assert.assertTrue("Invalid table size after restoreDefaults",
					page.isAuthorizedFoldersForGraphModelsSize(folders.length));
			Assert.assertTrue("Invalid table contents after restoreDefaults",
					page.authorizedFoldersForGraphModelsContainValues(folders));
			 
			page.apply();
		} finally {
			page.cancel();
		}
	}
	@Test
	public void testRestoreDefaultGraphWalkerLibraries() {
		testUpdateAndApplyGraphWalkerLibraries() ;	 
		// Open the GraphWalker preference page
		GW4EPreferencePage page = new GW4EPreferencePage(bot);
		page.open();

		try {
			page.restoreDefaults();
			String [] libs = PreferenceManager.getDefaultGraphWalkerLibraries();
			Assert.assertTrue("Invalid table size after restoreDefaults",
					page.isGraphWalkerLibrariesSize(libs.length));
			Assert.assertTrue("Invalid table contents after restoreDefaults",
					page.graphWalkerLibrariesContainValues(libs));
			 
			page.apply();
		} finally {
			page.cancel();
		}
	}
}
