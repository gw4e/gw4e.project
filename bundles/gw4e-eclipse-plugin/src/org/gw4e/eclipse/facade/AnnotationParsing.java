package org.gw4e.eclipse.facade;

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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.gw4e.eclipse.builder.Location;

public class AnnotationParsing {
	List<IAnnotationBinding> annotations = new ArrayList<IAnnotationBinding>();
	Location location;
	public AnnotationParsing() {
	}
	/**
	 * @return the annotations
	 */
	public List<IAnnotationBinding> getAnnotations() {
		return annotations;
	}
	 /**
	 * @param binding
	 */
	public void add (IAnnotationBinding binding) {
		annotations.add(binding);
	 }
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return
	 */
	public String getValue ( ) {
		return getValue (0);
	}
	/**
	 * @param index
	 * @return
	 */
	public String getValue (int index) {
		if ((annotations != null) && annotations.size() > 0) {
			IAnnotationBinding generated = annotations.get(index);
			IMemberValuePairBinding[] vpb = generated.getAllMemberValuePairs();
			for (int i = 0; i < vpb.length; i++) {
				IMemberValuePairBinding pair = vpb[i];
				if ("value".equals(pair.getName())) {
					Object object =  pair.getValue();
					String value = null;
					if (object!=null && object.getClass().isArray()) {
						Object [] values = (Object []) object;
						value = (String) values[0];
					} else {
						value = (String) object;
					}
					return value;
				}
			}
		}
		return null;
	}
	
	public String getValue (String attribute) {
		return getValue (attribute,0); 
	}
	
	public String getValue (String attribute, int index) {
		if ((annotations != null) && annotations.size() > 0) {
			IAnnotationBinding generated = annotations.get(index);
			IMemberValuePairBinding[] vpb = generated.getAllMemberValuePairs();
			for (int i = 0; i < vpb.length; i++) {
				IMemberValuePairBinding pair = vpb[i];
				if (attribute.equals(pair.getName())) {
					Object object =  pair.getValue();
					String value = null;
					if (object!=null && object.getClass().isArray()) {
						Object [] values = (Object []) object;
						value = (String) values[0];
					} else {
						value = (String) object;
					}
					return value;
				}
			}
		}
		return null;
	}
}
