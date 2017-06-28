package org.gw4e.eclipse.studio.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.EditorPartAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.graphwalker.core.model.Element;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager.GraphSelection;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.part.editor.SharedVertexPart;
import org.gw4e.eclipse.wizard.staticgenerator.GeneratorToFileCreationWizard;

public class ContextMenuProvider extends org.eclipse.gef.ContextMenuProvider {

	private ActionRegistry actionRegistry;
	private GW4EEditor editor;

	public ContextMenuProvider(GW4EEditor editor, EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		this.editor = editor;
		setActionRegistry(registry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		IAction action;

		GEFActionConstants.addStandardActionGroups(menu);

		IAction openGraphAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.openGraphAction");
				setText("Open Graph");
				setToolTipText("Open Graph");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.treeLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				GraphSelection gs = GraphSelectionManager.ME.getSelection();
				ISelection selection = gs.getCurrentSelection();
				if (selection instanceof SharedVertexPart) {

				}
				return true;
			}

		};

		IAction openApiBasedOfflineDialogAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.openApiBasedOfflineDialogAction");
				setText("Open Api Based Offline Test Dialog");
				setToolTipText("Open Api Based Offline Test Dialog");

			}

			public void run() {
				GraphSelection gs = GraphSelectionManager.ME.getSelection();
				IStructuredSelection selection = (IStructuredSelection) gs.getCurrentSelection();
				if (selection != null) {
					try {
						Iterator sequence = selection.iterator();
						List<String> ids = new ArrayList<String> ();
						while (sequence.hasNext()) {
							EditPart part = (EditPart) sequence.next();
							if (!(part.getModel() instanceof GraphElement)) continue;
							GraphElement element = (GraphElement)part.getModel();
							ids.add(element.getId());
						}
						StructuredSelection sel = new StructuredSelection(new Object[] { editor.getGraph().getFile() , ids });
						GeneratorToFileCreationWizard.open(sel);
					} catch (Exception e) {
						ResourceManager.logException(e);
					}
				}
			}

			@Override
			protected boolean calculateEnabled() {
				GraphSelection gs = GraphSelectionManager.ME.getSelection();
				ISelection selection = gs.getCurrentSelection();
				if (selection != null) {
					if (!selection.isEmpty())
						return true;
				}
				return false;
			}
		};

		IAction revealAction = new EditorPartAction(editor) {
			protected void init() {
				setId("grapphwalker.showin.package.explorer");
				setText("Package Explorer");
				setToolTipText("Show In Package Explorer");
			}

			private IFile getFile() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				GWGraph graph = editor.getGraph();
				return graph.getFile();
			}

			public void run() {
				IFile file = getFile();
				BasicNewResourceWizard.selectAndReveal(file, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			}

			@Override
			protected boolean calculateEnabled() {
				IFile file = getFile();
				return (file != null && file.exists());
			}

		};

		IAction treeLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.TreeLayoutAlgorithm");
				setText("Tree Layout");
				setToolTipText("Tree Layout");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.treeLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};

		IAction springLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SpringLayoutAlgorithm");
				setText("Spring Layout");
				setToolTipText("Spring Layout");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.springLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};
		IAction sugiyamaLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SugiyamaLayoutAlgorithm");
				setText("Sugiyama Layout");
				setToolTipText("Sugiyama Layout");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.sugiyamaLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};
		IAction spaceTreeTopDownLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SpaceTreeTopDownLayoutAlgorithm");
				setText("Space Tree (Top Down)");
				setToolTipText("Space Tree (Top Down)");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.spaceTreeTopDownLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};

		IAction spaceTreeBottomUpLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SpaceTreeBottomUpLayoutAlgorithm");
				setText("Space Tree (Bottom Up)");
				setToolTipText("Space Tree (Bottom Up)");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.spaceTreeBottomUpLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};

		IAction spaceTreeRightLeftLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SpaceTreeRightLeftLayoutAlgorithm");
				setText("Space Tree (Right Left)");
				setToolTipText("Space Tree (Right Left)");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.spaceTreeRightLeftLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};

		IAction spaceTreeLeftRightLayoutAction = new EditorPartAction(editor) {

			protected void init() {
				setId("grapphwalker.layout.algorithm.SpaceTreeLeftRightLayoutAlgorithm");
				setText("Space Tree (Left Right)");
				setToolTipText("Space Tree (Left Right)");
			}

			public void run() {
				GW4EEditor editor = (GW4EEditor) getEditorPart();
				editor.spaceTreeLeftRightLayout();
			}

			@Override
			protected boolean calculateEnabled() {
				return true;
			}

		};

		MenuManager subMenuSpaceTree = new MenuManager("Space Tree Layout", null);
		subMenuSpaceTree.add(spaceTreeBottomUpLayoutAction);
		subMenuSpaceTree.add(spaceTreeTopDownLayoutAction);
		subMenuSpaceTree.add(spaceTreeLeftRightLayoutAction);
		subMenuSpaceTree.add(spaceTreeRightLeftLayoutAction);

		MenuManager subMenu = new MenuManager("Layout", null);
		subMenu.add(treeLayoutAction);
		subMenu.add(springLayoutAction);
		subMenu.add(sugiyamaLayoutAction);
		subMenu.add(subMenuSpaceTree);

		menu.appendToGroup(GEFActionConstants.MB_ADDITIONS, subMenu);

		action = getActionRegistry().getAction(ActionFactory.UNDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = getActionRegistry().getAction(ActionFactory.REDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		MenuManager subMenuShowIn = new MenuManager("Show In", null);
		subMenuShowIn.add(revealAction);
		menu.appendToGroup(GEFActionConstants.GROUP_VIEW, subMenuShowIn);

		MenuManager subMenuGenerate = new MenuManager("Generate", null);
		subMenuGenerate.add(openApiBasedOfflineDialogAction);
		menu.appendToGroup(GEFActionConstants.MB_ADDITIONS, subMenuGenerate);

	}

	private ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	private void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}
}
