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

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.BendpointLocator;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RelativeLocator;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.gw4e.eclipse.studio.preference.PreferenceManager;
import org.gw4e.eclipse.studio.util.ID;

public class EdgeFigure extends PolylineConnection {
	
	protected Label label;
	protected BlockedFigure blockedFigure;
	protected GuardFigure guardedFigure;
	
	protected ActionFigure actionFigure;
	protected TooltipFigure tooltipFigure;
	 
	public EdgeFigure() {
		tooltipFigure = new TooltipFigure();
		setToolTip(tooltipFigure);
		
		this.setLineWidth(this.getLineWidth() * 2);
		
	  	this.setConnectionRouter(new BendpointConnectionRouter());

		this.setTargetDecoration(new PolylineDecoration());
 
		label = new Label("e_"+ID.getId());
		label.setOpaque(true);
		label.setBackgroundColor(ColorConstants.buttonLightest);
		label.setBorder(new LineBorder());
	 	add(label, new MidpointLocator(this, 0));
		
		guardedFigure = new GuardFigure();
		blockedFigure = new BlockedFigure();
		actionFigure = new ActionFigure ();
		add (actionFigure, new RelativeLocator(label,0.5,1.5));
	}
	
	public void setTooltipText(String tooltipText) {
	 	tooltipFigure.setMessage(tooltipText);
	}

	public void setLabelAtIndex (int index) {
		try {
			this.remove(label);
		} catch (Exception ignore) {
		}
		add(label, new MidpointLocator(this, index));
	}
	
	public void setBlockedOrGuarded(boolean blocked, String guard) {
		guardedFigure.setTooltip ("");
		try {
			this.remove(guardedFigure);
		} catch (Exception ign) {
		}
		try {
			this.remove(blockedFigure);
		} catch (Exception ign) {
		}
		if (blocked) {
			BendpointLocator locator = new BendpointLocator(this, 0);
			this.add(blockedFigure, locator);
		} else {
			if (guard!=null && guard.trim().length()>0) {
				BendpointLocator locator = new BendpointLocator(this, 0);
				guardedFigure.setTooltip (guard);
				this.add(guardedFigure, locator);
			}
		}
	}

	public void setActionScripted(String source) {
		actionFigure.updateActionScriptedStatus (source);
	}
	 	
	/**
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.label.setText(name);
	}
 
	private String stripSource (String source) {
		if (source==null) return null;
		String ret  =  "";
		StringTokenizer st = new StringTokenizer(source,"\r\n");
		int max = PreferenceManager.getMaxRowInTooltip();
		int index = 1;
		while (st.hasMoreTokens()) {
			ret = ret + st.nextToken();
			if (index>=max) return ret;
			ret = ret+"\r\n";
			index++;
		}
		return ret;
	}
	
	public boolean isBlocked () {
		List figures = getChildren();
		for (Object figure : figures) {
			if (figure instanceof BlockedFigure) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAction () {
		List figures = getChildren();
		for (Object figure : figures) {
			if (figure instanceof ActionFigure) {
				return ((ActionFigure)figure).hasScript();
			}
		}
		return false;
	}
	
	public boolean hasGuard () {
		List figures = getChildren();
		for (Object figure : figures) {
			if (figure instanceof GuardFigure) {
				return true;
			}
		}
		return false;
	}
	
	
	public class BlockedFigure extends Figure {
		Label lblocked ;
		public BlockedFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setHorizontal(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(null);
			lblocked = new Label("", PreferenceManager.getImageBlocked());
			add(lblocked);
		}
	}
	
	public class GuardFigure extends Figure {
		Label lguarded ;
		public GuardFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setHorizontal(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(null);
			lguarded = new Label("", PreferenceManager.getImageGuardScripted());
			add(lguarded);
		}
		private void setTooltip (String source) {
			String text = stripSource(source);
			if (text==null) return;
			lguarded.setToolTip(new Label(text));
		}
	}
	
	
	
	public class ActionFigure extends Figure {
		Label lActionScripted = new Label("", PreferenceManager.getImageActionScripted());
		boolean hasScript = false;
		public ActionFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setHorizontal(true);
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(0);
			setLayoutManager(layout);
			setBorder(null);
		}		
		
		private void setTooltip (String source) {
			String text = stripSource(source);
			if (text==null) return;
			lActionScripted.setToolTip(new Label(text));
		}
		
		public void updateActionScriptedStatus (String source) {
			this.setTooltip("");
			if (source!=null && source.trim().length()>0) {
				setActionWithScript(source);
			} else {
				setActionWithoutScript();
			}
		}
		
		private void setActionWithScript (String source) {	
			this.setTooltip(source);
			hasScript = true;
			if (actionFigure.getChildren().contains(lActionScripted)) return;
			actionFigure.add(lActionScripted);
		}
		
		private void setActionWithoutScript () {
			try {
				hasScript = false;
				actionFigure.remove(lActionScripted);
			} catch (Exception ignore) {
			}
		}
		
 		public void setConstraint(Rectangle r) {
			EdgeFigure.this.setConstraint(this, new Rectangle(r.width - getChildren().size() * (PreferenceManager.getImageActionScripted().getBounds().width) - 3, r.height - PreferenceManager.getImageActionScripted().getBounds().height - 3 , r.width, r.height));
		}

		/**
		 * @return the hasScript
		 */
		public boolean hasScript() {
			return hasScript;
		}
	}
}
