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

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.studio.fwk.Graph;
import org.gw4e.eclipse.studio.fwk.IToolbar;
import org.gw4e.eclipse.studio.fwk.OutLineView;
import org.gw4e.eclipse.studio.fwk.ToolBarView;
import org.gw4e.eclipse.studio.fwk.VertexProperties;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.VertexId;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class ToolbarTest extends SWTBotGefTestCase {
	protected static final String PROJECT_NAME = "gwproject";
	protected SWTBotGefEditor editor;

	public ToolbarTest() {
	}

	@Before
	public void setUp() {
		try {
			super.setUp();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			GW4EProject.cleanWorkspace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		try {
			bot.viewByTitle("Welcome").close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			SettingsManager.setM2_REPO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public abstract IToolbar getToolbar ();
	
	@Test
	public void testDeleteVertex() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		IToolbar toolbar = getToolbar();
		
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.selectPart(vA);
		VertexId vertexId = new VertexId (((GWNode) vA.part().getModel()).getId());
		
		toolbar.delete(new UnexistingVertexCondition(vertexId,"v_A"));

		map = oview.geVisibleTreeItems();
		assertNull(map.get("v_A"));

		String[] keys = new String[] { "Start", "v_B", "e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid delete result", map.size() == 0);
	}
	
	@Test
	public void testUndoDeleteVertex() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		IToolbar toolbar = new ToolBarView(oview.getBotView());
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.selectPart(vA); 
		VertexId vertexId = new VertexId (((GWNode) vA.part().getModel()).getId());
		
		toolbar.delete(new UnexistingVertexCondition(vertexId,"v_A"));

		toolbar.undoDelete(new ExistingVertexCondition("v_A"));

		map = oview.geVisibleTreeItems();

		String[] keys = new String[] { "Start", "v_A", "v_B", "e_to_V_A", "e_v_A_to_v_A", "e_v_A_to_v_B",
				"e_v_B_to_v_A", "e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid undo delete result " + map, map.size() == 0);
	}
	
	
	@Test
	public void testRedoDeleteVertex() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		IToolbar toolbar = new ToolBarView(oview.getBotView());
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("v_A");
		gp.selectPart(vA);
		VertexId vertexId = new VertexId (((GWNode) vA.part().getModel()).getId());
		
		toolbar.delete(new UnexistingVertexCondition(vertexId,"v_A"));

		toolbar.undoDelete(new ExistingVertexCondition("v_A"));

		toolbar.redoDelete(new UnexistingVertexCondition(vertexId,"v_A"));

		map = oview.geVisibleTreeItems();
		assertNull(map.get("v_A"));

		String[] keys = new String[] { "Start", "v_B", "e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid delete result", map.size() == 0);
	}
	

	@Test
	public void testDeleteEdge() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		IToolbar toolbar = getToolbar();
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart eTovA = editor.getEditPart("e_to_V_A");
		gp.selectPart(eTovA);

		toolbar.delete(new UnexistingEdgeCondition("e_to_V_A"));

		map = oview.geVisibleTreeItems();
		assertNull(map.get("e_to_V_A"));

		String[] keys = new String[] { "Start", "v_A", "v_B", "e_v_A_to_v_A", "e_v_A_to_v_B", "e_v_B_to_v_A",
				"e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid delete result", map.size() == 0);
	}

	@Test
	public void testUndoDeleteEdge() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("v_A"));
		IToolbar toolbar = getToolbar();
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("e_to_V_A");
		gp.selectPart(vA);

		toolbar.delete(new UnexistingEdgeCondition("e_to_V_A"));

		toolbar.undoDelete(new ExistingEdgeCondition("e_to_V_A"));

		map = oview.geVisibleTreeItems();

		String[] keys = new String[] { "Start", "v_A", "v_B", "e_to_V_A", "e_v_A_to_v_A", "e_v_A_to_v_B",
				"e_v_B_to_v_A", "e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid undo delete result " + map, map.size() == 0);
	}

	@Test
	public void testRedoDeleteEdge() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("e_to_V_A"));
		IToolbar toolbar = getToolbar();
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("e_to_V_A");
		gp.selectPart(vA);

		toolbar.delete(new UnexistingEdgeCondition("e_to_V_A"));

		toolbar.undoDelete(new ExistingEdgeCondition("e_to_V_A"));

		toolbar.redoDelete(new UnexistingEdgeCondition("e_to_V_A"));

		map = oview.geVisibleTreeItems();
		assertNull(map.get("e_to_V_A"));

		String[] keys = new String[] { "Start", "v_A", "v_B", "e_v_A_to_v_A", "e_v_A_to_v_B", "e_v_B_to_v_A",
				"e_v_B_to_v_B" };
		for (String key : keys) {
			map.remove(key);
		}
		assertTrue("Invalid delete result", map.size() == 0);
	}


	@Test
	public void testResetEdgeRoute() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);

		editor = bot.gefEditor(resources[0]);
		editor.show();

		OutLineView oview = new OutLineView(bot, editor);
		Map<String, SWTBotTreeItem> map = oview.geVisibleTreeItems();

		assertNotNull(map.get("e_v_A_to_v_A"));
		IToolbar toolbar = getToolbar();
		VertexProperties gp = new VertexProperties(bot, editor);
		SWTBotGefEditPart vA = editor.getEditPart("e_v_A_to_v_A");
		gp.selectPart(vA);
		
		GWEdge edge = (GWEdge) vA.part().getModel();
		Iterator<Point> pointIterator = edge.getBendpointsIterator();
		int count  = 0;
		while (pointIterator.hasNext()) {
			pointIterator.next();
			count++;
		}
		assertTrue("Invalid reset edge result", count > 0);
		
		toolbar.resetEdgeRoute();
		
		edge = (GWEdge) vA.part().getModel();
		pointIterator = edge.getBendpointsIterator();
		count  = 0;
		while (pointIterator.hasNext()) {
			pointIterator.next();
			count++;
		}
		assertTrue("Invalid reset edge result", count == 0);
	}
	
	
	public class UnexistingVertexCondition implements ICondition {
		String vertextName;
		VertexId vertexId;
		public UnexistingVertexCondition(VertexId vertexId , String vertextName) {
			super();
			this.vertextName = vertextName;
			this.vertexId = vertexId;
		}

		@Override
		public boolean test() throws Exception {
			Graph graphFactory = new Graph(bot, editor);
			SWTBotGefEditPart ep = editor.getEditPart(vertextName);
			return graphFactory.getGraph().getVertex(vertexId) == null && ep == null;
		}

		@Override
		public void init(SWTBot bot) {
		}

		@Override
		public String getFailureMessage() {
			return vertextName + " is still in the model";
		}
	}

	public class ExistingVertexCondition implements ICondition {
		String vertextName;

		public ExistingVertexCondition(String vertextName) {
			super();
			this.vertextName = vertextName;
		}

		@Override
		public boolean test() throws Exception {
			Graph graphFactory = new Graph(bot, editor);
			SWTBotGefEditPart ep = editor.getEditPart(vertextName);
			VertexId vertexId = new VertexId (((GWNode) ep.part().getModel()).getId());
			return graphFactory.getGraph().getVertex(vertexId) != null && ep != null;
		}

		@Override
		public void init(SWTBot bot) {
		}

		@Override
		public String getFailureMessage() {
			return vertextName + " is still in the model";
		}
	}

	public class UnexistingEdgeCondition implements ICondition {
		String edgeName;

		public UnexistingEdgeCondition(String edgeName) {
			super();
			this.edgeName = edgeName;
		}

		@Override
		public boolean test() throws Exception {
			Graph graphFactory = new Graph(bot, editor);
			SWTBotGefEditPart ep = editor.getEditPart(edgeName);
			return graphFactory.getGraph().getLink(edgeName) == null && ep == null;
		}

		@Override
		public void init(SWTBot bot) {
		}

		@Override
		public String getFailureMessage() {
			return edgeName + " is still in the model";
		}
	}

	public class ExistingEdgeCondition implements ICondition {
		String edgeName;

		public ExistingEdgeCondition(String edgeName) {
			super();
			this.edgeName = edgeName;
		}

		@Override
		public boolean test() throws Exception {
			Graph graphFactory = new Graph(bot, editor);
			SWTBotGefEditPart ep = editor.getEditPart(edgeName);
			return graphFactory.getGraph().getLink(edgeName) != null && ep != null;
		}

		@Override
		public void init(SWTBot bot) {
		}

		@Override
		public String getFailureMessage() {
			return edgeName + " is still in the model";
		}
	}
}
