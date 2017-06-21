package org.gw4e.eclipse.studio.locator;

import org.eclipse.draw2d.BendpointLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;

public class BendpointLocatorWrapper implements Locator {
	BendpointLocator locator;

	public BendpointLocatorWrapper(BendpointLocator locator) {
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
