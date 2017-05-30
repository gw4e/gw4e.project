package org.gw4e.eclipse.builder.marker;

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

import java.util.Comparator;
 

/**
 * @author  
 *
 */
public class ResolutionMarkerDescription implements Comparator<ResolutionMarkerDescription>{
	String label;
	String generator;
	int order;
	
	/**
	 * @param order
	 * @param label
	 * @param generator
	 */
	public ResolutionMarkerDescription (int order, String label, String generator) {
		this.label=label;
		this.order=order;
		this.generator=generator;
	}
	
	@Override
	public int compare(ResolutionMarkerDescription d1, ResolutionMarkerDescription d2) {
		int rc  = d1.order - d2.order; 
		if (rc == 0) {
			rc = d1.generator.compareTo(d2.generator);
		}
		return rc;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		String index = getOrder() < 10 ? "0" + getOrder() : getOrder()+"";
		return index + "\t" + getGenerator() + " : " + getLabel();
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @return the generator
	 */
	public String getGenerator() {
		return generator;
	}
}
