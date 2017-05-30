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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.studio.commands.ClearEdgeBendpointLayoutAction;
import org.gw4e.eclipse.studio.commands.CopyNodeAction;
import org.gw4e.eclipse.studio.commands.PasteNodeAction;
import org.gw4e.eclipse.studio.editor.outline.OutLineComposite;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.facade.LayoutAlgoritmManager;
import org.gw4e.eclipse.studio.facade.ResourceManager;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.part.editor.GW4EEditPartFactory;
import org.gw4e.eclipse.studio.part.outline.GW4ETreeEditPartFactory;

public class GW4EEditor extends GraphicalEditorWithFlyoutPalette
		implements ISelectionProvider, ITabbedPropertySheetPageContributor {

	public static String ID = "org.gw4e.eclipse.studio.editor.GW4EEditor";
 
	private GWGraph gWGraph;
	private GW4EEditPartFactory gw4eEditPartFactory = new GW4EEditPartFactory();
	private KeyHandler keyHandler;

	public GW4EEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.parts.GraphicalEditor#init(org.eclipse.ui.IEditorSite,
	 * org.eclipse.ui.IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setSite(site);
		setPartName(input.getName());
		setInputWithNotify(input);
		site.setSelectionProvider(this);
		if (getEditorInput() instanceof FileEditorInput) {
			FileEditorInput fei = (FileEditorInput) getEditorInput();
			IFile file = fei.getFile();
			gWGraph = ResourceManager.load(file);
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					gWGraph.initialize(getGraphicalViewer().getEditPartRegistry());
					if (!ResourceManager.isEditable(file)) {
						gWGraph.setReadOnly(true);
						getGraphicalViewer().getControl().setEnabled(false);
						String title = MessageUtil.getString("conversion");
						String message = MessageUtil.getString("not_formatted_as_json_convert_it");
						DialogManager.displayWarning(title, message);
					}
				}
			});
		}

	}

	public Dimension getDimension() {
		GraphicalViewer viewer = getGraphicalViewer();
		Dimension viewSize = (((FigureCanvas) viewer.getControl()).getViewport().getSize());
		return new Dimension(viewSize.width, viewSize.height);
	}

	public void resetEdgesRouteLayout() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				gWGraph.resetEdgesRouteLayout(getGraphicalViewer().getEditPartRegistry());
			}
		});
	}

	public void treeLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
			 	gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.treeLayout(gWGraph, getDimension());
			}
		});
	}

	public void springLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.springLayout(gWGraph, getDimension());
			}
		});
	}

	public void spaceTreeTopDownLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.spaceTreeTopDownLayout(gWGraph, getDimension());
			}
		});
	}

	public void spaceTreeBottomUpLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.spaceTreeBottomUpLayout(gWGraph, getDimension());
			}
		});
	}

	public void spaceTreeLeftRightLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.spaceTreeLeftRightLayout(gWGraph, getDimension());
			}
		});
	}

	public void spaceTreeRightLeftLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.spaceTreeRightLeftLayout(gWGraph, getDimension());
			}
		});
	}

	public void sugiyamaLayout() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				resetEdgesRouteLayout();
				gWGraph.setLayout(new Rectangle(0, 0, getDimension().width, getDimension().height));
				LayoutAlgoritmManager.sugiyamaLayout(gWGraph, getDimension());
			}
		});		
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		if (viewer == null)
			return;
		viewer.setContents(gWGraph);
		viewer.addSelectionChangedListener(GraphSelectionManager.ME);
		viewer.getControl().addMouseListener(GraphSelectionManager.ME);
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			boolean saved = ResourceManager.save(gWGraph, monitor);
			if (saved) {
				getCommandStack().markSaveLocation();
			}
		} catch (Exception e) {
			ResourceManager.logException(e, "Unable to save " + gWGraph.getFile());
		}
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	public void doSaveAs() {

	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.EditorPart#gotoMarker(org.eclipse.core.resources.
	 * IMarker)
	 */
	public void gotoMarker(IMarker marker) {

	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		double[] zoomLevels;
		List<String> zoomContributions;

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(gw4eEditPartFactory);

		keyHandler = new KeyHandler();

		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));

		keyHandler.put(KeyStroke.getPressed(SWT.F2, 0), getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));

		keyHandler.put(KeyStroke.getPressed('+', SWT.KEYPAD_ADD, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		keyHandler.put(KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));

		getGraphicalViewer().setKeyHandler(new GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(keyHandler));

		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);

		ZoomManager manager = rootEditPart.getZoomManager();
		getActionRegistry().registerAction(new ZoomInAction(manager));
		getActionRegistry().registerAction(new ZoomOutAction(manager));

		zoomLevels = new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0 };
		manager.setZoomLevels(zoomLevels);

		zoomContributions = new ArrayList<String>();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		manager.setZoomLevelContributions(zoomContributions);

		getActionRegistry().registerAction(new ToggleGridAction(getGraphicalViewer()));
		getActionRegistry().registerAction(new ToggleSnapToGeometryAction(getGraphicalViewer()));
		getActionRegistry().registerAction(new ClearEdgeBendpointLayoutAction(this));

		ContextMenuProvider provider = new ContextMenuProvider(this, viewer, getActionRegistry());
		viewer.setContextMenu(provider);
	}

	public Object getAdapter(Class adapter) {

		if (adapter == GraphicalViewer.class || adapter == EditPartViewer.class)
			return getGraphicalViewer();
		else if (adapter == CommandStack.class)
			return getCommandStack();
		else if (adapter == EditDomain.class)
			return getEditDomain();
		else if (adapter == ActionRegistry.class)
			return getActionRegistry();
		else if (adapter == IPropertySheetPage.class)
			return new PropertiesView(true);
		else if (adapter == IContentOutlinePage.class) {
			return new GW4EOutlinePage();
		} else if (adapter == ZoomManager.class)
			return ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
		return super.getAdapter(adapter);
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithPalette#getPaletteRoot()
	 */
	GW4EGraphicalEditorPalette palette;

	protected PaletteRoot getPaletteRoot() {
		if (palette == null) {
			palette = new GW4EGraphicalEditorPalette();
		}
		return palette;
	}

	/*
	 * (Non Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
	 */
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();

		IAction action = new DirectEditAction((IWorkbenchPart) this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CopyNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new PasteNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ClearEdgeBendpointLayoutAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.
	 * ITabbedPropertySheetPageContributor#getContributorId()
	 */
	@Override
	public String getContributorId() {
		return ID;
	}

	protected List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();
	protected ISelection currentEditorSelection;

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		return currentEditorSelection;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		currentEditorSelection = selection;
		for (ISelectionChangedListener listener : selectionChangedListeners) {
			listener.selectionChanged(new SelectionChangedEvent(this, selection));
		}
	}

	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(PROP_DIRTY);
		super.commandStackChanged(event);
	}

	public class GW4EOutlinePage extends ContentOutlinePage implements PropertyChangeListener {
		private GW4ETreeEditPartFactory gwtpf = null;
		public static final String GW_WIDGET_ID = "GW_WIDGET_ID";
		public static final String GW_OUTLINE_ELEMENTS_TREE = "GW_OUTLINE_ELEMENTS_TREE";
		OutLineFilter filter = null;
		private SashForm sash;

		public GW4EOutlinePage() {
			super(new TreeViewer());
			gWGraph.addPropertyChangeListener(this);
			filter = new OutLineFilter(this);
			Map registry = getGraphicalViewer().getEditPartRegistry();
			gwtpf = new GW4ETreeEditPartFactory(registry, filter);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			update();
		}

		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);
			IActionBars bars = getSite().getActionBars();
			ActionRegistry ar = getActionRegistry();

			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), ar.getAction(ActionFactory.COPY.getId()));
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), ar.getAction(ActionFactory.PASTE.getId()));

			OutLineComposite composite = new OutLineComposite(filter, sash, SWT.NONE);

			Control tree = getViewer().createControl(composite.getComposite());
			tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
			tree.setData(GW_WIDGET_ID,GW_OUTLINE_ELEMENTS_TREE);
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(gwtpf);
			getViewer().setContents(gWGraph);
			
			getSelectionSynchronizer().addViewer(getViewer());

		}

		public void init(IPageSite pageSite) {
			super.init(pageSite);

			IActionBars bars = pageSite.getActionBars();

			bars.getToolBarManager().add(getActionRegistry().getAction(ClearEdgeBendpointLayoutAction.ID));
			bars.getToolBarManager().add(getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			bars.getToolBarManager().add(getActionRegistry().getAction(ActionFactory.UNDO.getId()));
			bars.getToolBarManager().add(getActionRegistry().getAction(ActionFactory.REDO.getId()));

			bars.setGlobalActionHandler(ClearEdgeBendpointLayoutAction.ID,
					getActionRegistry().getAction(ClearEdgeBendpointLayoutAction.ID));
			bars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					getActionRegistry().getAction(ActionFactory.UNDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					getActionRegistry().getAction(ActionFactory.REDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			bars.updateActionBars();

			getViewer().setKeyHandler(keyHandler);

			pageSite.setSelectionProvider(getViewer());
		}

		public Control getControl() {
			return sash;
		}

		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			gWGraph.removePropertyChangeListener(this);
			super.dispose();
		}

		public void update() {
			if (getControl()==null) return;
			getControl().setRedraw(false);
			getViewer().setContents(gWGraph);
			getControl().setRedraw(true);
		}
	}

	/**
	 * @return the gWGraph
	 */
	public GWGraph getGraph() {
		return gWGraph;
	}

	 
	 

}
