package org.gw4e.eclipse.studio;

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

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.studio.fwk.EdgeProperties;
import org.gw4e.eclipse.studio.fwk.OutLineView;
import org.gw4e.eclipse.studio.fwk.VertexProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OutLineTest extends SWTBotGefTestCase {
	private static final String PROJECT_NAME = "gwproject";

	private SWTBotGefEditor editor;

	public OutLineTest() {
	}

	@BeforeClass
	public static void closeWelcomePage() {
		try {
			SWTBotPreferences.TIMEOUT = 6000;
			new SWTGefBot().viewByTitle("Welcome").close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setUp() {
		try {
			super.setUp();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bot.resetWorkbench();
		try {
			GW4EProject.cleanWorkspace();
		} catch (CoreException e) {
		} 
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
		}
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
		}
	}

	@After
	public void tearDown() throws Exception {
		if (editor != null)
			editor.close();
		super.tearDown();
	}
 
	@Test
	public void testFilterName() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		oview.setNameText("v_A"); 
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		
		String [] keys  = new String [] { "v_A" ,"e_v_A_to_v_A" ,"e_v_A_to_v_B", "e_v_B_to_v_A" };
		
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (name)", map.size()==0);
	}
	
	@Test
	public void testFilterDescription() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		VertexProperties gp = new VertexProperties(bot,editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		 
		gp.setDescription(vA, "new description");
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		oview.setDescriptionText("new description"); 
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		
		String [] keys  = new String [] { "v_A" };
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (description)", map.size()==0);
	}
	 
	@Test
	public void testFilterBlocked() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setBlockedComboToYes();
		bot.waitUntil(oview.createTreeRowCountCondition (0));
		
		oview.setBlockedComboToNo();
		bot.waitUntil(oview.createTreeRowCountCondition (oview.geRowCount()));
		
		VertexProperties gp = new VertexProperties(bot,editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.toggleBlocked(vA);
		oview.setBlockedComboToYes();
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] { "v_A" };
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (description)", map.size()==0);
		
		gp.toggleBlocked(vA);
		bot.waitUntil(oview.createTreeRowCountCondition (oview.geRowCount()));
		
		
	}
	
	@Test
	public void testFilterRequirement() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setRequirementText("*");
		assertTrue("Invalid filter result (no filter)", 0==oview.geVisibleRowCount ());
		
		VertexProperties gp = new VertexProperties(bot,editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.setRequirement(vA, "req1");
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"v_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (requirement)", map.size()==0);
		
		oview.setRequirementText("xxx");
		bot.waitUntil(oview.createTreeRowCountCondition (0));
		
		oview.setRequirementText("req");
		bot.waitUntil(oview.createTreeRowCountCondition (1));
	}
	
	@Test
	public void testFilterInitScript() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setInitScriptComboToNo();
		bot.waitUntil(oview.createTreeRowCountCondition (3));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] { "Start","v_A","v_B" };
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (initscript)", map.size()==0);
		
		oview.setInitScriptComboToNoValue();
		bot.waitUntil(oview.createTreeRowCountCondition (oview.geRowCount()));
 
		oview.setInitScriptComboToYes();
		bot.waitUntil(oview.createTreeRowCountCondition (0));
		
		VertexProperties gp = new VertexProperties(bot,editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.setInit(vA, "i++;");
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		map = oview.geVisibleTreeItems ();
		keys  = new String [] {"v_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (initscript)", map.size()==0);
	}

	@Test
	public void testFilterShared() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setSharedComboToYes();
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] { "v_B",};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (shared)", map.size()==0);
		
		oview.setSharedComboToNo();
		bot.waitUntil(oview.createTreeRowCountCondition (2));
		map = oview.geVisibleTreeItems ();
		keys  = new String [] { "Start","v_A",};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (shared)", map.size()==0);
		
		oview.setSharedComboToNoValue();
		bot.waitUntil(oview.createTreeRowCountCondition (oview.geRowCount()));
	}

	@Test
	public void testFilterGuard() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setGuardComboToYes();
		assertTrue("Invalid filter result (no filter)", 0==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart vA = (SWTBotGefConnectionEditPart)editor.getEditPart("e_v_A_to_v_A");
		gp.setGuard(vA, "i==1");
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_v_A_to_v_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (guard)", map.size()==0);
		
		oview.setGuardComboToNo();
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		map = oview.geVisibleTreeItems ();
		keys  = new String [] {"e_to_V_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (guard)", map.size()==0);
		
		oview.setActionComboToNoValue();
		bot.waitUntil(oview.createTreeRowCountCondition (oview.geRowCount()));
	}
	
	@Test
	public void testFilterAction() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setActionComboToNO();
		assertTrue("Invalid filter result (no filter)", 0==oview.geVisibleRowCount ());
		
		oview.setActionComboToYes();
		bot.waitUntil(oview.createTreeRowCountCondition (5));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_to_V_A","e_v_A_to_v_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (guard)", map.size()==0);
		 
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setAction(e_to_V_A,"");
		
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		map = oview.geVisibleTreeItems ();
		keys  = new String [] {"e_v_A_to_v_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (guard)", map.size()==0);
	}
	
	
	@Test
	public void testFilterWeightEqual() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.5");
		oview.setOperatorComboToEqual();
		assertTrue("Invalid filter result (weight)", 0==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.5);
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_to_V_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
	
	@Test
	public void testFilterWeightNotEqual() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.5");
		oview.setOperatorComboToNotEqual();
		assertTrue("Invalid filter result (weight)", oview.geRowCount()==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.5);
		
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_v_A_to_v_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
	
	@Test
	public void testFilterWeightUpper() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.4");
		oview.setOperatorComboToUpper();
		assertTrue("Invalid filter result (weight)", 0==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.5);
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_to_V_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
	
	@Test
	public void testFilterWeightUpperOrEqual() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.5");
		oview.setOperatorComboToUpperOrEqual();
		assertTrue("Invalid filter result (weight)", 0==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.5);
		
		bot.waitUntil(oview.createTreeRowCountCondition (1));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_to_V_A"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
	
	@Test
	public void testFilterWeightLower() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.6");
		oview.setOperatorComboToLower();
		assertTrue("Invalid filter result (weight)",oview.geRowCount()==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.7);
		
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_v_A_to_v_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
	
	@Test
	public void testFilterWeightLowerOrEqual() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[0]);
		editor.show();
		
		OutLineView oview = new OutLineView(bot,editor);
		oview.toggleFilterOn();
		assertTrue("Invalid filter result (no filter)", oview.geRowCount()==oview.geVisibleRowCount ());

		oview.setWeightText("0.5");
		oview.setOperatorComboToLowerOrEqual();
		assertTrue("Invalid filter result (weight)",oview.geRowCount()==oview.geVisibleRowCount ());
		
		EdgeProperties gp = new EdgeProperties(bot,editor);
		SWTBotGefConnectionEditPart e_to_V_A = (SWTBotGefConnectionEditPart)editor.getEditPart("e_to_V_A");
		gp.setWeight(e_to_V_A, 0.7);
		
		bot.waitUntil(oview.createTreeRowCountCondition (4));
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems ();
		String [] keys  = new String [] {"e_v_A_to_v_A","e_v_A_to_v_B","e_v_B_to_v_A","e_v_B_to_v_B"};
		for (String key : keys) {
			map.remove(key);
		}		
		assertTrue("Invalid filter result (weight)", map.size()==0);
	}
}
