package org.gw4e.eclipse.property;

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

public class Property  {
	String name;
	String label;
	String value;
	boolean editable;
	boolean multitext;
	PropertyChecker checker;
	 
	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param editable
	 * @param multitext
	 */
	public Property(String name, PropertyChecker checker, String label, String value, boolean editable, boolean multitext) {
		super();
		this.name = name;
		this.label = label;
		this.value = value;
		this.editable = editable;
		this.multitext = multitext;
		this.checker = checker;
	}

	/**
	 * @param values
	 */
	public void check (String[] values) {
		checker.check(values);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}
	/**
	 * @return the multitext
	 */
	public boolean isMultitext() {
		return multitext;
	}
}
