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

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.studio.fwk.IToolbar;
import org.gw4e.eclipse.studio.fwk.OutLineView;
import org.gw4e.eclipse.studio.fwk.ToolBarEditor;
import org.gw4e.eclipse.studio.fwk.VertexProperties;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.VertexId;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToolbarEditorTest extends ToolbarTest {
	private static final String PROJECT_NAME = "gwproject";

	public ToolbarEditorTest() {
	}

	@BeforeClass
	public static void closeWelcomePage() {
		try {
			SWTBotPreferences.TIMEOUT = 6000;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@After
	public void tearDown() throws Exception {
		if (editor != null)
			editor.close();
		super.tearDown();
	}

	@Test
	public void testCopyPasteVertex() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[1]);
		editor.close();
		
		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		ToolBarEditor toolbar = new ToolBarEditor(bot, editor);
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.selectPart(vA);

		toolbar.copy();
		 
		GWGraph model = ((GraphElement)vA.part().getModel()).getGraph();
		List<GWNode> before = model.getVerticesChildrenArray() ;
		int sizeBefore = before.size();
		toolbar.paste(new ICondition () {
			@Override
			public boolean test() throws Exception {
				int sizeAfter = model.getVerticesChildrenArray().size();
				return sizeAfter == (sizeBefore+1);
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Model not modified after paste action.";
			}
		});
		
		List<GWNode> after = model.getVerticesChildrenArray() ;
		after.removeAll(before);
		assertTrue("Invalid size result", after.size() == 1);
		
		GWNode createdVertex =  (GWNode) after.get(0);
		SWTBotGefEditPart vcreated = editor.getEditPart(createdVertex.getName());
		assertNotNull(vcreated);
		
		gp.selectPart(vcreated);
		bot.waitUntil(new ICondition () {
			@Override
			public boolean test() throws Exception {
				return oview.isSelected (vcreated);
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Part not selected in the Outline view";
			}
		});
		
		gp.assertPropertiesShown(vcreated);
 	}
	
	@Test
	public void testSelectionVertex() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[1]);
		editor.close();
		
		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		 
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		editor.select(vA);
		oview.isSelected(vA);
		gp.assertPropertiesShown(vA);
 	}
	
	@Test
	public void testSelectionVertex2() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[1]);
		editor.close();
		
		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		VertexProperties gp = new VertexProperties(bot, editor);
		
		oview.select("v_A");
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		oview.isSelected(vA);
		gp.assertPropertiesShown(vA);
		VertexId vertexId = new VertexId (((GWNode) vA.part().getModel()).getId());
		
		IToolbar toolbar = getToolbar();
		toolbar.delete(new UnexistingVertexCondition(vertexId,"v_A"));
		 
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems();
		assertNull(map.get("v_A"));
 	}
	
	@Test
	public void testSelectionEdge() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[1]);
		editor.close();
		
		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		 
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("e_to_V_A");
		editor.select(vA);
		oview.isSelected(vA);
		gp.assertPropertiesShown(vA);
 	}
	
	@Test
	public void testSelectionEdge2() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[1]);
		editor.close();
		
		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		 
		VertexProperties gp = new VertexProperties(bot, editor);
		oview.select("e_to_V_A");
		SWTBotGefEditPart vA = editor.getEditPart("e_to_V_A");
		oview.isSelected(vA);
		gp.assertPropertiesShown(vA);
		
		IToolbar toolbar = getToolbar();
		toolbar.delete(new UnexistingEdgeCondition("e_to_V_A"));
		 
		Map<String,SWTBotTreeItem> map = oview.geVisibleTreeItems();
		assertNull(map.get("e_to_V_A"));
 	}
	


	@Override
	public IToolbar getToolbar() {
		ToolBarEditor toolbar = new ToolBarEditor(bot, editor);
		return toolbar;
	}
}
