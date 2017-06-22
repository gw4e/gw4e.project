package org.gw4e.eclipse.studio.figure;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.gw4e.eclipse.studio.Activator;

public class SharedVertexFigure extends VertexFigure {
	 
	 
	
	public SharedVertexFigure() {
		super (true);
	}
	
	protected void createLayout () {
		setLayoutManager(new XYLayout());
		setBackgroundColor(Activator.getSharedVertexImageColor());
		setOpaque(true);
		this.setBorder(null); 
		rectangle = new RoundedRectangle();
		add(rectangle);
		name = new Label("");
		add(name);
		 
	}

 
	 
}
