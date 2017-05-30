package org.gw4e.eclipse.menu;

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

import org.gw4e.eclipse.product.GW4ENature;
/**
 * A helper class to enable/disable the Remove the GW4E Nature menu item. See plugin.xml
 * 
 */
public class GW4ENatureSet implements IMenuTester {
	
	public GW4ENatureSet( ) {
		super();
	}


	/* 
	 * Allow if the project has the GW4E nature
	 * (non-Javadoc)
	 * @see org.gw4e.eclipse.menu.IMenuTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean hasGW4ENature =  GW4ENature.hasGW4ENature(receiver);
		return hasGW4ENature;
	}

}
