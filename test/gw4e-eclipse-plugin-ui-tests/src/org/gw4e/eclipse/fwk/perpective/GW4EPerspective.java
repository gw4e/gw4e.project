package org.gw4e.eclipse.fwk.perpective;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.gw4e.eclipse.fwk.conditions.ShellActiveCondition;
import org.gw4e.eclipse.fwk.platform.GW4EPlatform;
public class GW4EPerspective {
	
	public static void openNewGW4EProject(SWTWorkbenchBot bot) {
		

		bot.menu("File").menu("New").menu("GW4E Project").click();
		bot.waitUntil(new ShellActiveCondition("GW4E Project Creation Wizard"));

		SWTBotShell shell = bot.shell("GW4E Project Creation Wizard");
		assertTrue(shell.isOpen());
		
		shell.bot().button("Cancel").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
	
	public static void openNewGraphWalkerModel(SWTWorkbenchBot bot) {
		
		SWTBotMenu all = bot.menu("File").menu("New");
		
		/*
		Function<String, String>   f =  new Function<String, String> () {

			@Override
			public String apply(String t) {
				return t;
			}
			
		};
		all.menuItems().stream().map(f);
		*/
		
		bot.menu("File").menu("New").menu("GW4E Model").click();
		bot.waitUntil(new ShellActiveCondition("GW4E"));
		SWTBotShell shell = bot.shell("GW4E");
		assertTrue(shell.isOpen());
		shell.bot().button("Cancel").click();
		bot.waitUntil(Conditions.shellCloses(shell));
	}
	
	public static void openGWPerspective(SWTWorkbenchBot bot) {
		openPerspective(bot, "GW4E Perspective");
	}
	
	public static void resetGWPerspective(SWTWorkbenchBot bot) {
		resetPerspective(bot, "GW4E Perspective");
	}


	public static void openPerspective(SWTWorkbenchBot bot,String perspectiveLabel) {
		SWTBotShell shell = null;
		try {
			bot.menu("Window").menu("Perspective").menu("Open Perspective").menu("Other...").click();
			
			bot.waitUntil(new ShellActiveCondition("Open Perspective"));
			shell = bot.shell("Open Perspective");
			assertTrue(shell.isOpen());

			shell.bot().table().select(perspectiveLabel);
			
			 
			String buttonName ="OK";
			if (GW4EPlatform.isEclipse47 ()) buttonName = "Open";
			shell.bot().button(buttonName).click();
			bot.waitUntil(Conditions.shellCloses(shell));
		} catch (Exception e) {
			if (shell != null && shell.isOpen())
				shell.close();
			SWTBotPerspective perspective = bot
					.perspectiveByLabel(perspectiveLabel);
			perspective.activate();
			assertTrue(perspective.isActive());
		}
		assertEquals(perspectiveLabel, bot.activePerspective().getLabel());
		
		ICondition condition= new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTWorkbenchBot bot = new SWTWorkbenchBot();
				SWTBotView view = bot.viewByTitle("Package Explorer");
				return view!=null;
			}

			@Override
			public String getFailureMessage() {
				return "Package Explorer not displayed";
			}
		};
		bot.waitUntil(condition);
	}
	
	public static void resetPerspective(SWTWorkbenchBot bot,String perspectiveLabel) {
		openPerspective(bot,perspectiveLabel);
		SWTBotShell shell = null;
		try {
			bot.menu("Window").menu("Perspective").menu("Reset Perspective...").click();
			
			bot.waitUntil(new ShellActiveCondition("Reset Perspective"));
			shell = bot.shell("Reset Perspective");
			assertTrue(shell.isOpen());

			shell.bot().button("Yes").click();

			bot.waitUntil(Conditions.shellCloses(shell));
		} catch (Exception e) {

		}
		 
		
		ICondition condition= new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				SWTWorkbenchBot bot = new SWTWorkbenchBot();
				return  bot.activePerspective().getLabel().equals(perspectiveLabel);
			}

			@Override
			public String getFailureMessage() {
				return perspectiveLabel + " not displayed";
			}
		};
		bot.waitUntil(condition);
	}
}
