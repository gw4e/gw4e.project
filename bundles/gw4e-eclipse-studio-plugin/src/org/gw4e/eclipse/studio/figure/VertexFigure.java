package org.gw4e.eclipse.studio.figure;

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
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.studio.Activator;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager;
import org.gw4e.eclipse.studio.editor.GraphSelectionManager.GraphSelection;
import org.gw4e.eclipse.studio.facade.ResourceManager;
import org.gw4e.eclipse.studio.model.SharedVertex;
import org.gw4e.eclipse.studio.part.editor.SharedVertexPart;
import org.gw4e.eclipse.studio.preference.PreferenceManager;

public class VertexFigure extends AbstractFigure {

	protected Label name = new Label();
	protected RoundedRectangle rectangle;
	protected ConnectionAnchor connectionAnchor;
	protected VertextStateFigure vertextStateFigure;

	protected XYLayout layout;

	public VertexFigure() {
		this(true);
	}

	public VertexFigure(boolean addStatusFigure) {
		createLayout();
		if (addStatusFigure) {
			addStatusFigure();
		}
	}

	protected void createLayout() {
		setLayoutManager(new XYLayout());
		setBackgroundColor(Activator.getVertexImageColor());
		setOpaque(true);
		setBorder(null);
		rectangle = new RoundedRectangle();
		add(rectangle);
		name = new Label("");
		add(name);
	}

	protected void addStatusFigure() {
		vertextStateFigure = new VertextStateFigure();
		add(vertextStateFigure);
	}

	public boolean isBlocked() {
		return vertextStateFigure.isBlocked();
	}

	public boolean hasInitScript() {
		return vertextStateFigure.hasInitScript();
	}

	public boolean hasOpenSharedLinkAvailable() {
		return vertextStateFigure.hasOpenSharedLinkAvailable();
	}

	 
	protected Object constraintRectangle;
	protected Object constraintName;
	protected Object constraintStateFigure;
	 
	@Override protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		Object tempRectangle = new Rectangle(0, 0, r.width, r.height);
		if ( constraintRectangle==null || !constraintRectangle.equals(tempRectangle)) {
			constraintRectangle = tempRectangle;
			setConstraint(rectangle, constraintRectangle);
			rectangle.invalidate();
		}
		 
		Object tempName = new Rectangle(0, 0, r.width, r.height);
		if (constraintName==null || !constraintName.equals(tempName)) {
			constraintName = tempName;
			setConstraint(name, constraintName);
			name.invalidate();	
		}
		
		if (vertextStateFigure!=null) {
			if (constraintStateFigure==null || !constraintStateFigure.equals(r)) {
				constraintStateFigure = r; 
				vertextStateFigure.setConstraint(r);
				vertextStateFigure.invalidate();
			}
		}
	}
	
	
	/**
	 * @param rect
	 */
	public void setLayout(Rectangle rect) {
		if (rect == null)
			return;
		Rectangle r = rect;
		if (PreferenceManager.isAutomaticResizingOn()) {
			Dimension dimension = this.name.getPreferredSize();
			r = new Rectangle(rect.x, rect.y, dimension.width + PreferenceManager.getWidthMarge(), dimension.height
					+ PreferenceManager.getImageBlocked().getBounds().height + PreferenceManager.getHeightMarge());
		}
		getParent().setConstraint(this, r);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name.setText(name);
	}

	public void setIcons(boolean b, String script, boolean isShared) {
		if (vertextStateFigure != null) {
			vertextStateFigure.updateIcons(b, script, isShared);
		}
	}

	/**
	 * @return the name
	 */
	public Label getName() {
		return name;
	}

	/**
	 * @return
	 */
	public ConnectionAnchor getConnectionAnchor() {
		if (connectionAnchor == null) {
			connectionAnchor = new ChopboxAnchor(this);
		}
		return connectionAnchor;
	}

	private String stripSource(String source) {
		if (source == null)
			return null;
		String ret = "";
		StringTokenizer st = new StringTokenizer(source, "\r\n");
		int max = PreferenceManager.getMaxRowInTooltip();
		int index = 1;
		while (st.hasMoreTokens()) {
			ret = ret + st.nextToken();
			if (index >= max)
				return ret;
			ret = ret + "\r\n";
			index++;
		}
		return ret;
	}

	public class VertextStateFigure extends Figure {
		Label lblocked;
		Label lscripted;
		Label lShared;
		boolean blocked = false;
		boolean scripted = false;
		boolean shared = false;

		public VertextStateFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setHorizontal(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(null);
			lblocked = new Label("", PreferenceManager.getImageBlocked());
			lscripted = new Label("", PreferenceManager.getImageActionScripted());
			lShared = new Label("", PreferenceManager.getImageShared());
			lShared.addMouseMotionListener(new MouseMotionListener() {
				Cursor cursor = null;

				@Override
				public void mouseDragged(MouseEvent arg0) {
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					cursor = lShared.getCursor();
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					lShared.setCursor(cursor);
				}

				@Override
				public void mouseHover(MouseEvent arg0) {
					lShared.setCursor(org.eclipse.draw2d.Cursors.HAND);
				}

				@Override
				public void mouseMoved(MouseEvent arg0) {
				}
			});
			lShared.addMouseListener(new MouseListener() {
				@Override
				public void mouseDoubleClicked(MouseEvent arg0) {
					Runnable job = new ModelFinder(false);
					BusyIndicator.showWhile(Display.getDefault(), job);
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					Runnable job = new ModelFinder(true);
					BusyIndicator.showWhile(Display.getDefault(), job);
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
			});
		}

		public void setTooltip(String source) {
			String text = stripSource(source);
			if (text == null)
				return;
			lscripted.setToolTip(new Label(text));
		}

		public boolean isBlocked() {
			List list = this.getChildren();
			for (Object object : list) {
				if (object.equals(lblocked))
					return true;
			}
			return false;
		}

		public boolean hasInitScript() {
			List list = this.getChildren();
			for (Object object : list) {
				if (object.equals(lscripted))
					return true;
			}
			return false;
		}

		public boolean hasOpenSharedLinkAvailable() {
			List list = this.getChildren();
			for (Object object : list) {
				if (object.equals(lShared))
					return true;
			}
			return false;
		}

		public void updateIcons(boolean blocked, String script, boolean isShared) {
			try {
				vertextStateFigure.setTooltip("");
				vertextStateFigure.remove(lscripted);
			} catch (Exception ignore) {
			}
			try {
				vertextStateFigure.remove(lblocked);
			} catch (Exception ignore) {
			}
			try {
				vertextStateFigure.remove(lShared);
			} catch (Exception ignore) {
			}
			//

			if (isShared) {
				vertextStateFigure.add(lShared);
			}

			if (blocked) {
				vertextStateFigure.add(lblocked);
			} else {
				if (script != null && script.trim().length() > 0) {
					vertextStateFigure.setTooltip(script);
					vertextStateFigure.add(lscripted);
				}
			}
		}

		public void setConstraint(Rectangle r) {
			int width = 0;
			int height = 0;
			List children = getChildren();
			for (Object object : children) {
				Figure l = (Figure) object;
				width = width + l.getBounds().width;
				height = Math.max(height, l.getBounds().height);
			}
			VertexFigure.this.setConstraint(this,
					new Rectangle(r.width - width - 3, r.height - height - 3, r.width, r.height));
		}
	}

	public class ModelFinder implements Runnable {

		boolean onlyProject;

		public ModelFinder(boolean onlyProject) {
			super();
			this.onlyProject = onlyProject;
		}

		@Override
		public void run() {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					doIt();
				}
			});
		}

		private void doIt() {
			GraphSelection gs = GraphSelectionManager.ME.getSelection();
			ISelection selection = gs.getCurrentSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection sel = (IStructuredSelection) selection;
				if (sel.getFirstElement() instanceof SharedVertexPart) {
					SharedVertexPart vp = (SharedVertexPart) sel.getFirstElement();
					SharedVertex sv = (SharedVertex) vp.getModel();
					String sharedName = sv.getSharedName();
					IProject project = sv.getGraph().getProject();
					IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = ww.getActivePage();
					List<IFile> files = null;
					IContainer container = null;
					if (onlyProject) {
						container = project;
					} else {
						container = org.gw4e.eclipse.facade.ResourceManager.getWorkspaceRoot();
					}
					try {
						files = GraphWalkerFacade.getSharedGraphModels(sharedName, container);
					} catch (Exception ex) {
						org.gw4e.eclipse.facade.ResourceManager.logException(ex,
								"Error while looking for shared context");
					}
					for (IFile iFile : files) {
						try {
							if (iFile.equals(sv.getGraph().getFile()))
								continue;
							if (iFile.getFileExtension().equalsIgnoreCase(Constant.GRAPHML_FILE)
									&& !PreferenceManager.openSharedGraphmlFile())
								continue;
							page.openEditor(new FileEditorInput(iFile),
									org.gw4e.eclipse.preferences.PreferenceManager.getGW4EEditorName());
						} catch (PartInitException exception) {
							ResourceManager.logException(exception, "Unable to open " + iFile);
						}
					}
				}
			}
		}
	}

}
