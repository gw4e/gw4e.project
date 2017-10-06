package org.gw4e.eclipse.test.perspective;

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

import java.io.FileNotFoundException;
 
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.gw4e.eclipse.fwk.conditions.ViewOpened;
import org.gw4e.eclipse.fwk.perpective.GW4EPerspective;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GW4EPerspectiveTestCase {
	private static SWTWorkbenchBot bot;

	@BeforeClass
	public static void beforeClass() throws Exception {
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US";
		SWTBotPreferences.TIMEOUT = 10000;
		bot = new SWTWorkbenchBot();
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
		}

	}

	@Before
	public void setUp() throws Exception {
		bot.saveAllEditors();
		bot.closeAllEditors();
		bot.resetActivePerspective();

		bot.defaultPerspective().activate();
		bot.resetActivePerspective();
		bot.closeAllShells();

		GW4EProject.cleanWorkspace();
	}

 
	
 	@Test
	public void testDisplayGW4EPerspective () throws CoreException, FileNotFoundException {
 		GW4EPerspective.resetGWPerspective(bot);
 		bot.waitUntil(new ViewOpened(bot, "Package Explorer"));
 		bot.waitUntil(new ViewOpened(bot, "Properties"));
		bot.waitUntil(new ViewOpened(bot, "Problems"));
		bot.waitUntil(new ViewOpened(bot, "GW4E Plugin Performance"));
		bot.waitUntil(new ViewOpened(bot, "Outline"));
	}
 	
	@Test
	public void atestWizarProjectdMenus () throws CoreException, FileNotFoundException {
		GW4EPerspective.openPerspective(bot, "GW4E Perspective");
		GW4EPerspective.openNewGW4EProject(bot);
	}
	
	@Test
	public void atestWizarModeldMenus () throws CoreException, FileNotFoundException {
		GW4EPerspective.openPerspective(bot, "GW4E Perspective");
		GW4EPerspective.openNewGraphWalkerModel(bot);
	}
}
