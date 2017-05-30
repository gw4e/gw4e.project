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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.editor.GW4EGraphicalEditorPalette;
import org.gw4e.eclipse.studio.fwk.EdgeProperties;
import org.gw4e.eclipse.studio.fwk.Graph;
import org.gw4e.eclipse.studio.fwk.GraphProperties;
import org.gw4e.eclipse.studio.fwk.VertexProperties;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.VertexId;
import org.gw4e.eclipse.studio.part.editor.GraphPart;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SWTBotJunit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GraphTests extends SWTBotGefTestCase {
	private static final String PROJECT_NAME = "gwproject";

	private SWTBotGefEditor editor;

	public GraphTests() {
	}

	@BeforeClass
	public static void closeWelcomePage() {
		try {
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

	public void saveCurrentEditor() throws Exception {
		bot.menu("File").menu("Save").click();
	}

	private String getActiveToolLabel() {
		return editor.getActiveTool().getLabel();
	}

    @Test
	public void testPalette() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithEmptyTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[0]);

		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_VERTEX_LABEL);
		assertEquals(GW4EGraphicalEditorPalette.TOOL_VERTEX_LABEL, getActiveToolLabel());

		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_SHARED_VERTEX_LABEL);
		assertEquals(GW4EGraphicalEditorPalette.TOOL_SHARED_VERTEX_LABEL, getActiveToolLabel());

		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_START_VERTEX_LABEL);
		assertEquals(GW4EGraphicalEditorPalette.TOOL_START_VERTEX_LABEL, getActiveToolLabel());

		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_EDGE_LABEL);
		assertEquals(GW4EGraphicalEditorPalette.TOOL_EDGE_LABEL, getActiveToolLabel());
	}

	@Test
	public void testCreateFromScratch() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithEmptyTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[0]);

		Graph graphFactory =  new Graph(bot,editor);
		SWTBotGefEditPart startNode = graphFactory.addStartNode();
		VertexId vStartid = new VertexId (((GWNode) startNode.part().getModel()).getId());
		
		SWTBotGefEditPart v1 = graphFactory.addVertexNode("v1", 350, 160);
		VertexId v1id = new VertexId (((GWNode) v1.part().getModel()).getId());
		
		SWTBotGefEditPart sv2 = graphFactory.addSharedVertexNode("sv1", 171, 160);
		VertexId sv2id = new VertexId (((GWNode) sv2.part().getModel()).getId());
		
		SWTBotGefConnectionEditPart e1 = graphFactory.addEdge("e1", startNode, v1);
		SWTBotGefConnectionEditPart e2 = graphFactory.addEdge("e2", v1, sv2);
		editor.saveAndClose();
		IFile file = graphFactory.getFile();
		
		SWTBotGefEditor openedEditor = Graph.openGWEditor (bot,file);
		graphFactory =  new Graph(bot,openedEditor);
		GWGraph loadedGraph = graphFactory.getGraph();
		
		List<GWNode> nodes = loadedGraph.getVerticesChildrenArray();
		assertTrue("Invalid vertices count ", nodes.size() == 3);
		List<GWNode> edges = loadedGraph.getEdgesChildrenArray();
		assertTrue("Invalid edges count ", edges.size() == 2);
		
		Vertex v = loadedGraph.getVertex(vStartid);
		assertNotNull (v);
		assertTrue("Invalid Start Vertex", v.isStart());
		assertFalse("Invalid Start Vertex", v.isShared());
		
		v = loadedGraph.getVertex(v1id);
		assertNotNull (v);
		assertFalse("Invalid  Vertex", v.isStart());
		assertFalse("Invalid  Vertex", v.isShared());
		
		v = loadedGraph.getVertex(sv2id);
		assertNotNull (v);
		assertFalse("Invalid Shared Vertex", v.isStart());
		assertTrue("Invalid  Shared Vertex", v.isShared());
		
		GWLink e = loadedGraph.getLink("e1");
		assertNotNull (e);
		assertTrue("Invalid  Source", e.getSource().getName().equals(Constant.START_VERTEX_NAME));
		assertTrue("Invalid  Target", e.getTarget().getName().equals("v1"));
		
		e = loadedGraph.getLink("e2");
		assertNotNull (e);
		assertEquals("Invalid  Source", "v1",e.getSource().getName());
		assertEquals("Invalid  Target", "sv1", e.getTarget().getName());
	}

  	@Test
	public void testVertextProperties() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createtWithSimpleScriptedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[0]);
		VertexProperties gp = new VertexProperties(bot,editor);
		
		SWTBotGefEditPart start = editor.getEditPart(Constant.START_VERTEX_NAME);
		gp.setName(start, "shouldnotchange", false);
		
		SWTBotGefEditPart clientNotRunning = editor.getEditPart("v_ClientNotRunning");
		gp.setName(clientNotRunning, "currentFile", true);
		gp.setDescription(clientNotRunning, "new description");
		gp.setRequirement(clientNotRunning, "new requirement");
		gp.setSharedName(clientNotRunning, "shouldnotchange", false);
		gp.setInit (clientNotRunning  ,"i++;");
		gp.toggleBlocked(clientNotRunning);
		
		Assert.assertFalse("Invalid Vertex type", gp.isShared(clientNotRunning));
		
		editor.saveAndClose();
		Graph graphFactory =  new Graph(bot,editor);
		IFile file = graphFactory.getFile();
		
		SWTBotGefEditor openedEditor = Graph.openGWEditor (bot,file);
		graphFactory =  new Graph(bot,openedEditor);
		GWGraph loadedGraph = graphFactory.getGraph();
		
		VertexId vertexId = new VertexId (((GWNode) clientNotRunning.part().getModel()).getId());
		Vertex v = loadedGraph.getVertex(vertexId);
		Assert.assertNotNull(v);
		Assert.assertTrue("description not set", "new description".equals(v.getLabel()));
		Assert.assertTrue("shared name not set", (!"shouldnotchange".equals(v.getSharedName())));
		Assert.assertTrue("init not set", "i++;".equals(v.getInitScript().getSource()));
		Assert.assertTrue("blocked not set", v.isBlocked());
		Assert.assertFalse("Invalid Vertex type", v.isShared());
		openedEditor.close();
		
		//
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new VertexProperties(bot,openedEditor);
		gp.addCustomProperty(clientNotRunning, "key", "value");
		openedEditor.saveAndClose();
		 
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new VertexProperties(bot,openedEditor);
		String val = gp.getCustomProperty(clientNotRunning, "key");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new VertexProperties(bot,openedEditor);
		gp.updateKeyCustomProperty(clientNotRunning, "key", "newkey");
		val = gp.getCustomProperty(clientNotRunning, "newkey");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new VertexProperties(bot,openedEditor);
		gp.updateValueCustomProperty(clientNotRunning, "newkey", "value", "newValue");
		val = gp.getCustomProperty(clientNotRunning, "newkey");
		Assert.assertEquals("newValue",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new VertexProperties(bot,openedEditor);
		gp.deleteCustomProperty(clientNotRunning, "newkey");
		openedEditor.saveAndClose();
		
	}
	
	@Test
	public void testSharedVertextProperties() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createWithSharedTemplate(PROJECT_NAME);
		
		editor = bot.gefEditor(resources[1]);
		VertexProperties gp = new VertexProperties(bot,editor);
		SWTBotGefEditPart vb = editor.getEditPart("v_B");
		
		Assert.assertTrue("script not displayed", gp.hasInitScript(vb));
		Assert.assertFalse("blocked displayed", gp.isBlocked(vb));
		Assert.assertTrue("shared link not displayed", gp.hasOpenSharedLinkEnabled(vb));
		
		SWTBotGefEditPart vc = editor.getEditPart("v_C");
		Assert.assertFalse("script displayed", gp.hasInitScript(vc));
		Assert.assertFalse("blocked displayed", gp.isBlocked(vc));
		Assert.assertFalse("shared link not displayed", gp.hasOpenSharedLinkEnabled(vc));
		
		editor = bot.gefEditor(resources[0]);
		gp = new VertexProperties(bot,editor);
		
		 
		vb = editor.getEditPart("v_B");
		Assert.assertTrue("Invalid Vertex type", gp.isShared(vb));
		
		gp.setName(vb, "new_vb", true);
		
		SWTBotGefEditPart new_vb = editor.getEditPart("new_vb");
		gp.setDescription(new_vb, "new description");
		gp.setRequirement(new_vb, "new requirement");
		gp.setSharedName(new_vb, "shouldchange", true);
		
		Assert.assertFalse("script displayed", gp.hasInitScript(new_vb));
		gp.setInit (new_vb  ,"i--;");
		Assert.assertTrue("script not displayed", gp.hasInitScript(new_vb));
		
		gp.toggleBlocked(new_vb);
		Assert.assertTrue("blocked not displayed", gp.isBlocked(new_vb));
		
		
		
		editor.saveAndClose();
		Graph graphFactory =  new Graph(bot,editor);
		IFile file = graphFactory.getFile();
		
		SWTBotGefEditor openedEditor = Graph.openGWEditor (bot,file);
		graphFactory =  new Graph(bot,openedEditor);
		GWGraph loadedGraph = graphFactory.getGraph();
		
		VertexId vertexId = new VertexId (((GWNode) new_vb.part().getModel()).getId());
		Vertex v = loadedGraph.getVertex(vertexId);
		Assert.assertNotNull(v);
		Assert.assertTrue("description not set", "new description".equals(v.getLabel()));
		Assert.assertTrue("shared name not set", ("shouldchange".equals(v.getSharedName())));
		Assert.assertTrue("init not set", "i--;".equals(v.getInitScript().getSource()));
		Assert.assertTrue("blocked not set", v.isBlocked());
		Assert.assertTrue("Invalid Vertex type", v.isShared());
		 
	}
	
	@Test
	public void testGraphProperties() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createtWithSimpleScriptedTemplate(PROJECT_NAME);
		SWTBotGefEditor openedEditor = bot.gefEditor(resources[0]);
		Graph graphFactory =  new Graph(bot,openedEditor);
		IFile file = graphFactory.getFile();
	 
		GraphProperties gp = new GraphProperties(bot,openedEditor);
		gp.selectPart(null);
		GraphPart graphPart = GraphSelectionManager.ME.getSelection().getGpart();
		gp.addCustomProperty(null, "key", "value");
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		String val = gp.getCustomProperty(null, "key");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		gp.updateKeyCustomProperty(null, "key", "newkey");
		val = gp.getCustomProperty(null, "newkey");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		gp.updateValueCustomProperty(null, "newkey", "value", "newValue");
		val = gp.getCustomProperty(null, "newkey");
		Assert.assertEquals("newValue",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		gp.deleteCustomProperty(null, "newkey");
		openedEditor.saveAndClose();
	 
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		gp.setItem("e_close");
		String value = gp.getItem();
		assertEquals ("e_close",value);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		gp = new GraphProperties(bot,openedEditor);
		value = gp.getItem();
		assertEquals ("e_close",value);
		 
	}
	
	@Test
	public void testEdgeProperties() throws CoreException {
		GW4EProject project = new GW4EProject(bot, PROJECT_NAME);
		String[] resources = project.createtWithSimpleScriptedTemplate(PROJECT_NAME);
		editor = bot.gefEditor(resources[0]);
		EdgeProperties ep = new EdgeProperties(bot,editor);
		
		SWTBotGefConnectionEditPart e_ValidPremiumCredentials = (SWTBotGefConnectionEditPart)editor.getEditPart("e_ValidPremiumCredentials");
		Assert.assertTrue("action figure not displayed", ep.hasAction(e_ValidPremiumCredentials));
		Assert.assertFalse("blocked figure displayed", ep.isBlocked(e_ValidPremiumCredentials));
		Assert.assertFalse("guarded figure displayed", ep.hasGuard(e_ValidPremiumCredentials));
		
		SWTBotGefConnectionEditPart e_InvalidCredentials = (SWTBotGefConnectionEditPart)editor.getEditPart("e_InvalidCredentials");
		Assert.assertTrue("action figure not displayed", ep.hasAction(e_InvalidCredentials));
		Assert.assertFalse("blocked figure displayed", ep.isBlocked(e_InvalidCredentials));
		Assert.assertFalse("guarded figure displayed", ep.hasGuard(e_InvalidCredentials));
		
		
		SWTBotGefConnectionEditPart e_StartClient = (SWTBotGefConnectionEditPart)editor.getEditPart("e_StartClient");
		Assert.assertFalse("action figure not displayed", ep.hasAction(e_StartClient));
		Assert.assertFalse("blocked figure displayed", ep.isBlocked(e_StartClient));
		Assert.assertTrue("guarded figure displayed", ep.hasGuard(e_StartClient));
		
		SWTBotGefConnectionEditPart edgePart1 = (SWTBotGefConnectionEditPart)editor.getEditPart("e_startClient");
		GWEdge edge = (GWEdge) (edgePart1.part()).getModel();

		String guard = edge.getGuard().getSource();
		String action = edge.getAction().getSource();
		Assert.assertTrue("Invaid guard",  guard.equals(ep.getGuard(edgePart1)));
		Assert.assertTrue("Invaid action", action.equals(ep.getAction(edgePart1)));
		
		ep.setName(edgePart1, "newedge", true);
		ep.setDescription(edgePart1, "new desc");
		ep.toggleBlocked(edgePart1);
		ep.setAction(edgePart1, "action++");
		ep.setGuard(edgePart1, "guard++");
		ep.setWeight(edgePart1, 12);
		
		SWTBotGefConnectionEditPart edgePartNewegde = (SWTBotGefConnectionEditPart)editor.getEditPart("newedge");
		Assert.assertTrue("action figure not displayed", ep.hasAction(edgePartNewegde));
		Assert.assertTrue("blocked figure not displayed", ep.isBlocked(edgePartNewegde));
		Assert.assertFalse("guarded figure displayed", ep.hasGuard(edgePartNewegde));
		
		SWTBotGefConnectionEditPart edgePartStartClient = (SWTBotGefConnectionEditPart)editor.getEditPart("e_StartClient");
		Assert.assertFalse("action figure displayed", ep.hasAction(edgePartStartClient));
		Assert.assertFalse("blocked figure displayed", ep.isBlocked(edgePartStartClient));
		Assert.assertTrue("guarded figure not displayed", ep.hasGuard(edgePartStartClient));
		
		editor.saveAndClose();
		Graph graphFactory =  new Graph(bot,editor);
		IFile file = graphFactory.getFile();
		
		SWTBotGefEditor openedEditor = Graph.openGWEditor (bot,file);
		graphFactory =  new Graph(bot,openedEditor);
		GWGraph loadedGraph = graphFactory.getGraph();
		
		GWEdge l = (GWEdge)loadedGraph.getLink("newedge");
		Assert.assertNotNull(l);
		Assert.assertTrue("description not set", "new desc".equals(l.getLabel()));
		 
		Assert.assertTrue("action not set", "action++".equals(l.getAction().getSource().trim()));
		Assert.assertTrue("guard not set", "guard++".equals(l.getGuard().getSource().trim()));
		
		// Assert.assertTrue("weigth not set", "12.0".equals(l.getWeight()));
		Assert.assertTrue("blocked not set", l.isBlocked());
		editor.close();
		
		
		openedEditor = Graph.openGWEditor (bot,file);
		edgePartStartClient = (SWTBotGefConnectionEditPart)editor.getEditPart("e_StartClient");
		ep = new EdgeProperties(bot,editor);
		ep.addCustomProperty(edgePartStartClient, "key", "value");
		openedEditor.saveAndClose();
		 
		openedEditor = Graph.openGWEditor (bot,file);
		ep = new EdgeProperties(bot,editor);
		String val = ep.getCustomProperty(edgePartStartClient, "key");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		ep = new EdgeProperties(bot,editor);
		ep.updateKeyCustomProperty(edgePartStartClient, "key", "newkey");
		val = ep.getCustomProperty(edgePartStartClient, "newkey");
		Assert.assertEquals("value",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		ep = new EdgeProperties(bot,editor);
		ep.updateValueCustomProperty(edgePartStartClient, "newkey", "value", "newValue");
		val = ep.getCustomProperty(edgePartStartClient, "newkey");
		Assert.assertEquals("newValue",val);
		openedEditor.saveAndClose();
		
		openedEditor = Graph.openGWEditor (bot,file);
		ep = new EdgeProperties(bot,editor);
		ep.deleteCustomProperty(edgePartStartClient, "newkey");
		openedEditor.saveAndClose();
		
	
 	}
}
