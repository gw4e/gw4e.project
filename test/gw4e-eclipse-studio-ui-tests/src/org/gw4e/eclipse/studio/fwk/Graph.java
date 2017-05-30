package org.gw4e.eclipse.studio.fwk;

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
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.Connection;
import org.eclipse.gef.EditPart;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.SWTGefBot;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefConnectionEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.studio.editor.GW4EGraphicalEditorPalette;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.part.editor.EdgePart;
import org.gw4e.eclipse.studio.part.editor.SharedVertexPart;
import org.gw4e.eclipse.studio.part.editor.StartVertexPart;
import org.gw4e.eclipse.studio.part.editor.VertexPart;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import junit.framework.TestCase;

public class Graph extends GraphHelper{
	SWTBotGefEditor editor;
	GWGraph graph;
	
	public Graph(SWTGefBot	bot	, SWTBotGefEditor editor) {
		 this.editor=editor;
	}

	public SWTBotGefEditPart addStartNode() {
		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_START_VERTEX_LABEL);
		editor.click(350, 30);
		assertStartVertex();
		SWTBotGefEditPart part = editor.getEditPart(Constant.START_VERTEX_NAME);

		return part;
	}
	
	public SWTBotGefEditPart addVertexNode(String name, int x, int y) {
		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_VERTEX_LABEL);
		editor.click(x, y);
		editor.directEditType(name);
		assertVertex(name);
		SWTBotGefEditPart part = editor.getEditPart(name);
		return part;
	}
	
	public SWTBotGefEditPart addSharedVertexNode(String name, int x, int y) {
		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_SHARED_VERTEX_LABEL);
		editor.click(x, y);
		editor.directEditType(name);
		assertSharedVertex(name);
		SWTBotGefEditPart part = editor.getEditPart(name);
		return part;
	}

	public SWTBotGefConnectionEditPart addEdge(String name, SWTBotGefEditPart fromPart, SWTBotGefEditPart toPart) {
		editor.activateTool(GW4EGraphicalEditorPalette.TOOL_EDGE_LABEL);
		editor.click(fromPart);
		syncWithUIThread();
		editor.click(toPart);
		syncWithUIThread();
		SWTBotGefConnectionEditPart edgePart = fromPart.sourceConnections().get(0);
		GWLink edge = (GWLink) (edgePart.part()).getModel();
		Connection edgeFigure = (Connection) edgePart.part().getFigure();
		edgePart.activateDirectEdit();
		editor.directEditType(name);
		assertEdge(name);
		return edgePart;
	}
	
	public IFile getFile () {
		return  getGraph() .getFile();
	}
	
	/**
	 * @return the graph
	 */
	public GWGraph getGraph() {
		if (graph==null) {
			 List<SWTBotGefEditPart> parts = editor.editParts(new BaseMatcher<EditPart>() {
				@Override
				public boolean matches(Object item) {
					if (item instanceof org.gw4e.eclipse.studio.part.editor.GraphPart) return true;
					if (item instanceof org.gw4e.eclipse.studio.part.editor.VertexPart) return true;
					if (item instanceof org.gw4e.eclipse.studio.part.editor.EdgePart) return true;
					return false;
				}
				@Override
				public void describeTo(Description description) {
				}
			});
			 
			if (parts==null || parts.size() ==0) {
				throw new RuntimeException("Empty Graph");
			}
			graph = getGraph (parts.get(0));	
		}
		return graph;
	}
	
	private GWGraph getGraph (SWTBotGefEditPart part) {
		if (part.part().getModel() instanceof GWGraph) {
			return (GWGraph)part.part().getModel() ;
		} else {
			if (part.part().getModel() instanceof GraphElement) {
				return ((GraphElement)part.part().getModel()).getGraph() ;
			}
		}
		return null;
	}
	
	private void assertStartVertex() {
		SWTBotGefEditPart botPart = editor.getEditPart(Constant.START_VERTEX_NAME);
		TestCase.assertNotNull(botPart);
		TestCase.assertTrue(botPart.part() instanceof StartVertexPart);
	}

	private void assertVertex(String name) {
		SWTBotGefEditPart botPart = editor.getEditPart(name);
		TestCase.assertNotNull(botPart);
		TestCase.assertTrue(botPart.part() instanceof VertexPart);
	}

	private void assertSharedVertex(String name) {
		SWTBotGefEditPart botPart = editor.getEditPart(name);
		TestCase.assertNotNull(botPart);
		TestCase.assertTrue(botPart.part() instanceof SharedVertexPart);
	}

	private void assertEdge(String name) {
		SWTBotGefEditPart botPart = editor.getEditPart(name);
		TestCase.assertNotNull(botPart);
		TestCase.assertTrue(botPart.part() instanceof EdgePart);
	}
	
	
	public static SWTBotGefEditor openGWEditor (SWTGefBot bot, IFile file) throws CoreException {
		SWTBotTree tree = getProjectTree(bot);
		IPath path = file.getFullPath();
		String filename = file.getName();
		String projectname = path.segment(0);
		String folders = path.removeFirstSegments(1).removeLastSegments(1).toString();
		IPackageFragment pkg = ResourceManager.getPackageFragment(file.getProject(), new Path (folders));
 		SWTBotTreeItem item = tree.expandNode(new String [] {projectname,pkg.getParent().getPath().removeFirstSegments(1).toString(),pkg.getElementName(),filename});
		item.setFocus();
		item.select();
		ICondition condition = new DefaultCondition () {
			private boolean getChild (SWTBotTreeItem[] items) {
				for (SWTBotTreeItem item : items) {
					if (item.getText().equals(filename) && item.isSelected()) 
						return true;
					boolean b=  getChild (item.getItems());
					if (b) return b;
				}
				return false;
			}
			@Override
			public boolean test() throws Exception {
				SWTBotTreeItem[] items = tree.getAllItems();
				for (SWTBotTreeItem item : items) {
					if (getChild (item.getItems())) return true;
				}
				return false;
			}

			@Override
			public String getFailureMessage() {
				return "File " + filename + " not selected ";
			}
		};
		bot.waitUntil(condition);
		item.contextMenu("Open With").menu("Other...").click();
		
		String editorSelectionShellTitle = "Editor Selection";
		Matcher<Shell> matcher = WidgetMatcherFactory.withText(editorSelectionShellTitle);
		bot.waitUntil(Conditions.waitForShell(matcher));
		SWTBotShell shell = bot.shell(editorSelectionShellTitle);
		shell.activate();
		
		String editorName = "GW4E Editor";
		SWTBotText text = shell.bot().text();
		text.setText(editorName);
		condition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return text.getText().equals(editorName);
			}

			@Override
			public String getFailureMessage() {
				return "Cannot set the textfield to editorname";
			}
		};
		bot.waitUntil(condition);
		
		
		
		condition = new DefaultCondition () {
			String value = null;
			@Override
			public boolean test() throws Exception {
				SWTBotTree t = shell.bot().tree();
				t.select(0);
				value =  t.selection().get(0, 0);
				return editorName.equalsIgnoreCase(value);
			}

			@Override
			public String getFailureMessage() {
				return "Cannot find editorname in the tree " + value;
			}
		};
		bot.waitUntil(condition);		
		
		shell.bot().button("OK").click();	
		
		return bot.gefEditor(filename);
	}
	
	protected  static  SWTBotTree getProjectTree(SWTGefBot bot) {
		SWTBotTree tree = getPackageExplorer(bot).bot().tree();
		return tree;
	}

	protected  static SWTBotView getPackageExplorer(SWTGefBot bot) {
		SWTBotView view = bot.viewByTitle("Package Explorer");
		return view;
	}


}
