package org.gw4e.swtbot.studio.tests;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.UUID;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.gw4e.eclipse.studio.commands.LinkCreateCommand;
import org.gw4e.eclipse.studio.commands.VertexCreateCommand;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilter;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilterBuilderImpl;
import org.gw4e.eclipse.studio.editor.outline.filter.ExecutableFilterDirector;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.editor.outline.filter.ThreeStateChoice;
import org.gw4e.eclipse.studio.model.Action;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.Guard;
import org.gw4e.eclipse.studio.model.InitScript;
import org.gw4e.eclipse.studio.model.Requirement;
import org.gw4e.eclipse.studio.model.SharedVertex;
import org.gw4e.eclipse.studio.model.StartVertex;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.VertexId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterTests {
	private static final String VIEW_ID = "org.eclipse.ui.views.ContentOutline";

	org.eclipse.ui.part.PageBookView view;

	public FilterTests() {
	}

	@Before
	public void setUp() throws Exception {
		resetWorkspace();
		openView();
		 
	}

	@After
	public void tearDown() throws Exception {
		closeView();
	} 

	@Test
	public void testName() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText("A");
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'name' filter " + count, count==1);
		gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'name' filter " + count, count==1);
	}

	@Test
	public void testBlocked() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.YES);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setBlocked(true);
		model.getVertex(new VertexId("v1id")).setBlocked(true);
		
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'blocked' filter " + count, count==1);
		
		gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'blocked' filter " + count, count==1);
	}

	@Test
	public void testDescription() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription("Who");
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setLabel("Who knows ...");
		model.getVertex(new VertexId("v1id")).setLabel("Who knows ...");
		
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'description' filter " + count, count==1);
		gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'description' filter " + count, count==1);
	}
	
	@Test
	public void testDescriptionStar() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription("*");
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setLabel("aaaa");
		model.getVertex(new VertexId("v1id")).setLabel("bbbb");
		
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'description' filter " + count, count==1);
		gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'description' filter " + count, count==1);
	}
	@Test
	public void testInitScriptYes() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText("v1");
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.YES);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		model.getVertex(new VertexId("v1id")).setInitScript(new InitScript("i++;"));
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'initScript' filter " + count, count==1);
	}
	
	@Test
	public void testInitScriptNo() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		model.getVertex(new VertexId("v1id")).setInitScript(new InitScript("i++;"));
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'initScript' filter " + count, count == (gWNodes.size() - 1));
	}
	@Test
	public void testActionScriptYes() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText("A1");
		filter.setActionChoice(ThreeStateChoice.YES);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setAction(new Action("i++;"));
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'action' filter " + count, count==1);
	}
	
	@Test
	public void testActionScriptNo() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setAction(new Action("i++;"));
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'action' filter " + count, count==(gWNodes.size()-1));
	}
	
	@Test
	public void testGuardScriptYes() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText("A1");
		filter.setActionChoice(ThreeStateChoice.YES);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setAction(new Action("i++;"));
		((GWEdge)model.getLink("A1->v2")).setGuard(new Guard("i++;"));
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'guard' filter " + count, count==1);
	}
	@Test
	public void testGuardScriptNo() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setAction(new Action("i++;"));
		((GWEdge)model.getLink("A1->v2")).setGuard(new Guard("i++;"));
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'guard' filter " + count, count==(gWNodes.size()-1));
	}
	
	@Test
	public void testRequirement() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement("xxx");
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		model.getVertex(new VertexId("v1id")).setRequirement(new Requirement("xxxxx"));
	 
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'requirement' filter " + count, count==1);
	}
	
	
	@Test
	public void testRequirementStar() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement("*");
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		model.getVertex(new VertexId("v1id")).setRequirement(new Requirement("yyyy"));
		model.getVertex(new VertexId("v2id")).setRequirement(new Requirement("zzzz"));
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'requirement' filter " + count, count==2);
	}
	
	@Test
	public void testSharedNo() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'requirement' filter " + count, count==(gWNodes.size()-1));
	}
	
	@Test
	public void testSharedYes() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO_VALUE);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(null);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.YES);
		filter.setWeight(null);
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		List<GWNode> gWNodes = (List<GWNode>) model.getVerticesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'requirement' filter " + count, count==1);
	}
	
	
	@Test
	public void testWeightEqual() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(OutLineFilter.EQUAL_OPERATOR);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight("0.5");
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setWeight(0.5);
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'weight' filter " + count, count==1);
	}
	
	@Test
	public void testWeightLower() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(OutLineFilter.LOWER_OPERATOR);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight("0.3");
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setWeight(0.5);
		
		 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(model.getVertex(new VertexId("v3id")));
		lcc.setTarget(model.getVertex(new VertexId("v4id")));
		lcc.setGraph(model);
		lcc.setLink(new GWEdge(model,UUID.randomUUID(), "v3->v4",UUID.randomUUID().toString()));
		lcc.execute(); 
		((GWEdge)model.getLink("v3->v4")).setWeight(0.2);
		
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'weight' filter " + count, count==1);
	}
	
	
	@Test
	public void testWeightLowerEqual() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(OutLineFilter.LOWER_OR_EQUAL_OPERATOR);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight("0.5");
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setWeight(0.5);
		
		 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(model.getVertex(new VertexId("v3id")));
		lcc.setTarget(model.getVertex(new VertexId("v4id")));
		lcc.setGraph(model);
		lcc.setLink(new GWEdge(model,UUID.randomUUID(), "v3->v4",UUID.randomUUID().toString()));
		lcc.execute(); 
		((GWEdge)model.getLink("v3->v4")).setWeight(0.2);
		
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'weight' filter " + count, count==2);
	}
	
	@Test
	public void testWeightUpper() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(OutLineFilter.UPPER_OPERATOR);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight("0.4");
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setWeight(0.5);
		
		 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(model.getVertex(new VertexId("v3id")));
		lcc.setTarget(model.getVertex(new VertexId("v4id")));
		lcc.setGraph(model);
		lcc.setLink(new GWEdge(model,UUID.randomUUID(), "v3->v4",UUID.randomUUID().toString()));
		lcc.execute(); 
		((GWEdge)model.getLink("v3->v4")).setWeight(0.2);
		
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'weight' filter " + count, count==1);
	}
	
	@Test
	public void testWeightUpperEqual() {
		OutLineFilter filter = createOutLineFilter();
		filter.setNameText(null);
		filter.setActionChoice(ThreeStateChoice.NO);
		filter.setBlocked(ThreeStateChoice.NO_VALUE);
		filter.setDescription(null);
		filter.setGuardChoice(ThreeStateChoice.NO_VALUE);
		filter.setInitScript(ThreeStateChoice.NO_VALUE);
		filter.setOperator(OutLineFilter.UPPER_OR_EQUAL_OPERATOR);
		filter.setRequirement(null);
		filter.setShared(ThreeStateChoice.NO_VALUE);
		filter.setWeight("0.2");
		
		ExecutableFilter executable = createFilter (filter);
		GWGraph model = createModel ();
		((GWEdge)model.getLink("A1->v2")).setWeight(0.5);
		
		 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(model.getVertex(new VertexId("v3id")));
		lcc.setTarget(model.getVertex(new VertexId("v4id")));
		lcc.setGraph(model);
		lcc.setLink(new GWEdge(model,UUID.randomUUID(), "v3->v4",UUID.randomUUID().toString()));
		lcc.execute(); 
		((GWEdge)model.getLink("v3->v4")).setWeight(0.2);
		
		
		List<GWNode> gWNodes = (List<GWNode>) model.getEdgesChildrenArray();
		int count=0;
		for (GWNode gWNode : gWNodes) {
			if (executable.meetCriteria((GraphElement)gWNode)!=null) {
				count++;
			}
		}
		Assert.assertTrue("Wrong 'weight' filter " + count, count==2);
	}
	
	private void openView() throws PartInitException {
		view = (org.eclipse.ui.part.PageBookView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.showView(VIEW_ID);
	}

	private void closeView() {
		if (view != null)
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
	}

 
	private void resetWorkbench() {
		try {
			IViewReference[] views = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
			for (IViewReference iViewReference : views) {
				System.out.println(iViewReference.getTitle());
				 if ( iViewReference.getTitle().equals( "Welcome" ) ) {
					 	iViewReference.getPage().hideView(iViewReference);
						break;
				}
			}
			
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			Shell activeShell = Display.getCurrent().getActiveShell();
			if ( activeShell != null && activeShell != workbenchWindow.getShell() ) {
				activeShell.close();
			}
			page.closeAllEditors( false );
			page.resetPerspective();
			String defaultPerspectiveId = workbench.getPerspectiveRegistry().getDefaultPerspective();
			workbench.showPerspective( defaultPerspectiveId, workbenchWindow );
			page.resetPerspective();
		}
		catch ( WorkbenchException e ) {
			throw new RuntimeException( e );
		}

	}
	
	protected void resetWorkspace() throws Exception {
		Display.getDefault().syncExec( new Runnable() {
			public void run() {
				resetWorkbench();
			}
		} );
	}

	private PropertyChangeListener getListener () {
		PropertyChangeListener listener = new PropertyChangeListener () {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
			}
		};
		return listener;
	}
	
	private OutLineFilter createOutLineFilter (  ) { 
		OutLineFilter of = new OutLineFilter (getListener());
		of.setFilterOn(true);
		return of;
	}
	
	private ExecutableFilter createFilter (OutLineFilter filter ) {
		ExecutableFilterDirector director = new ExecutableFilterDirector (new ExecutableFilterBuilderImpl());
		ExecutableFilter executableFilter = director.construct(filter);
		return executableFilter;
	}
	
	private GWGraph createModel () {
		GWGraph gWGraph = new GWGraph(UUID.randomUUID(),"nonname",UUID.randomUUID().toString());
		
		Vertex v1 = new StartVertex(gWGraph,UUID.randomUUID(),"v1","v1id") ;
		Vertex v2 = new SharedVertex (gWGraph,UUID.randomUUID(),"v2","v2id","asharedname") ;
		Vertex v3 = new Vertex(gWGraph,UUID.randomUUID(),"v3","v3id") ;
		Vertex v4 = new Vertex(gWGraph,UUID.randomUUID(),"v4","v4id") ;
		Vertex v5 = new Vertex(gWGraph,UUID.randomUUID(),"v5","v5id") ;
		Vertex a1 = new Vertex(gWGraph,UUID.randomUUID(),"A1","A1id") ;
		
		Vertex [] vertices = new Vertex [] {v1,v2,v3,v4,v5,a1};
		for (int i = 0; i < vertices.length; i++) {
			VertexCreateCommand vcc  = new VertexCreateCommand ();
			vcc.setVertex(vertices[i]);
			vcc.setLocation(null);
			vcc.execute();
		}
	 
		LinkCreateCommand lcc = new LinkCreateCommand ();
		lcc.setSource(a1);
		lcc.setTarget(v2);
		lcc.setGraph(gWGraph);
		lcc.setLink(new GWEdge(gWGraph,UUID.randomUUID(), "A1->v2",UUID.randomUUID().toString()));
		lcc.execute(); 

		
		return gWGraph;
	}

}
