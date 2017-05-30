package org.gw4e.eclipse.test.view;

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

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.preferences.GW4EProjectPreference;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.properties.GW4EProjectProperties;
import org.gw4e.eclipse.fwk.view.GW4EPerformanceView;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4EViewTestCase {
	private static String TEST_RESOURCE_FOLDER;
	static {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		TEST_RESOURCE_FOLDER = folders[0] ;
	} 
	
	private static SWTWorkbenchBot bot;
	private static String gwproject = "gwproject";
	private static String graphMLFilename = "ShoppingCart.graphml";
 
	private static String PACKAGE_NAME =  "pkgname";
	public static long	RUN_TIMEOUT	= 180 * 1000;
 
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

	@Before
	public void setUp() throws CoreException {
		bot.resetWorkbench();
		GW4EProject.cleanWorkspace();
	}
	
	
	private void setPathGeneratorToRandom (GW4EProject project) throws IOException, CoreException, InterruptedException {
		String buildPoliciPath = gwproject + "/" + TEST_RESOURCE_FOLDER + "/"+ PACKAGE_NAME + "/" + PreferenceManager.getBuildPoliciesFileName(gwproject) ;
		IFile buildPolicyFile =  (IFile)  ResourceManager.getResource(buildPoliciPath);
		IPath pathModel = buildPolicyFile.getFullPath().removeLastSegments(1).append(graphMLFilename);
		IFile iFileModel = (IFile)  ResourceManager.getResource(pathModel.toString());
		project.setPathGenerator (buildPolicyFile , graphMLFilename, "random(edge_coverage(100));I;");
	}
	
	@Test
	public void testPerformanceViewTest  () throws CoreException, IOException, InterruptedException {
		 
			GW4EProject project = new GW4EProject(bot, gwproject);
			project.resetToJavPerspective();
			project.createProjectWithoutError(TEST_RESOURCE_FOLDER, PACKAGE_NAME, graphMLFilename);
			setPathGeneratorToRandom(project);
			
			GW4EPerformanceView gpv =  GW4EPerformanceView.open(bot);
			
			GW4EProjectPreference gwpp = new GW4EProjectPreference(bot, gwproject);
			GW4EProjectProperties page = gwpp.openPropertiesPage(  );
			page.togglePerformanceLoggingButton();
			page.ok();

			gpv.clickLoadButton();

			project.cleanBuild();
			
			gpv.clickLoadButton();
			
			ICondition condition = new DefaultCondition () {
				@Override
				public boolean test() throws Exception { 
					return  gpv.getRowCount()>0;
				}

				@Override
				public String getFailureMessage() {
					return "Performance view not loaded";
				}
			};
			bot.waitUntil(condition);
			
			gpv.clickClearButton();
			
			condition = new DefaultCondition () {
				@Override
				public boolean test() throws Exception {
					return  gpv.getRowCount()==0;
				}

				@Override
				public String getFailureMessage() {
					return "Performance view not cleared";
				}
			};
			bot.waitUntil(condition);
			
		 
	}
	 
	
	 
}
