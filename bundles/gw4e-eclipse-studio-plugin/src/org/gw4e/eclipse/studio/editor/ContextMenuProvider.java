package org.gw4e.eclipse.studio.editor;

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

 
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.EditorPartAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager.GraphSelection;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.part.editor.SharedVertexPart;

public class ContextMenuProvider extends org.eclipse.gef.ContextMenuProvider {

    private ActionRegistry actionRegistry;
    private GW4EEditor editor;
    public ContextMenuProvider(GW4EEditor editor, EditPartViewer viewer, ActionRegistry registry) {
            super(viewer);
            this.editor=editor;
            setActionRegistry(registry);
    }

    @Override
    public void buildContextMenu(IMenuManager menu) {
            IAction action;

            GEFActionConstants.addStandardActionGroups(menu);       

          IAction openGraphAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.openGraphAction");
            		setText("Open Graph");
            		setToolTipText("Open Graph");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
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
            
	        IAction revealAction = new EditorPartAction (editor) {
	        	protected void init() {
            		setId("grapphwalker.showin.package.explorer");
            		setText("Package Explorer");
            		setToolTipText("Show In Package Explorer");
            	}
	        	
	        	private IFile getFile () {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		GWGraph graph = editor.getGraph();
            		return graph.getFile();
	        	}
	        	
	        	public void run() {
	        		IFile file  = getFile ();
	    			BasicNewResourceWizard.selectAndReveal(file, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					IFile file  = getFile ();
					return (file!=null && file.exists());
				}
		            
	        };
            
            IAction treeLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.TreeLayoutAlgorithm");
            		setText("Tree Layout");
            		setToolTipText("Tree Layout");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		editor.treeLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            };
            
            IAction springLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SpringLayoutAlgorithm");
            		setText("Spring Layout");
            		setToolTipText("Spring Layout");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();      		
            		editor.springLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            };           
           IAction sugiyamaLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SugiyamaLayoutAlgorithm");
            		setText("Sugiyama Layout");
            		setToolTipText("Sugiyama Layout");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		editor.sugiyamaLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            };   
            IAction spaceTreeTopDownLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SpaceTreeTopDownLayoutAlgorithm");
            		setText("Space Tree (Top Down)");
            		setToolTipText("Space Tree (Top Down)");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		editor.spaceTreeTopDownLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            }; 
            
            IAction spaceTreeBottomUpLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SpaceTreeBottomUpLayoutAlgorithm");
            		setText("Space Tree (Bottom Up)");
            		setToolTipText("Space Tree (Bottom Up)");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		editor.spaceTreeBottomUpLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            }; 
            
            IAction spaceTreeRightLeftLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SpaceTreeRightLeftLayoutAlgorithm");
            		setText("Space Tree (Right Left)");
            		setToolTipText("Space Tree (Right Left)");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
            		editor.spaceTreeRightLeftLayout();
            	}
            	
				@Override
				protected boolean calculateEnabled() {
					return true;
				}
            	
            }; 
            
            
            IAction spaceTreeLeftRightLayoutAction = new EditorPartAction (editor) {
            	
            	protected void init() {
            		setId("grapphwalker.layout.algorithm.SpaceTreeLeftRightLayoutAlgorithm");
            		setText("Space Tree (Left Right)");
            		setToolTipText("Space Tree (Left Right)");
            	}

            	public void run() {
            		GW4EEditor editor = (GW4EEditor)getEditorPart();
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

            
            
    }

    private ActionRegistry getActionRegistry() {
            return actionRegistry;
    }

    private void setActionRegistry(ActionRegistry registry) {
            actionRegistry = registry;
    }
}