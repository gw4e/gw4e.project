package org.gw4e.eclipse.studio.locator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.MidpointLocator;

public class MidpointLocatorWrapper implements Locator {
	MidpointLocator locator;

	public MidpointLocatorWrapper(MidpointLocator locator) {
		super();
		this.locator = locator;
	}
	
 	@Override
	public void relocate(IFigure target) {
		try {
			locator.relocate(target);
		} catch (Exception e) {
		}
	}
}
