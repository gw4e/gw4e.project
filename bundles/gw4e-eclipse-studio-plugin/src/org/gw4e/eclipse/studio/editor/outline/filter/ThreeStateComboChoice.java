package org.gw4e.eclipse.studio.editor.outline.filter;

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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ThreeStateComboChoice extends ComboViewer {
	private static ThreeStateChoice[] choices = new ThreeStateChoice[] { 
			ThreeStateChoice.NO_VALUE, ThreeStateChoice.NO, ThreeStateChoice.YES, };
	
	public ThreeStateComboChoice(Composite parent,ISelectionChangedListener listener) {
		super(parent, SWT.READ_ONLY);
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
	        @Override
	        public String getText(Object element) {
	            if (element instanceof ThreeStateChoice) {
	            	ThreeStateChoice choice = (ThreeStateChoice) element;
	            	return choice.getLabel();	                 
	            }
	            return "";
	        }
	    });
		addSelectionChangedListener(listener);
		
		setInput(choices);
	}
	
	public void setData (String key, Object value) {
		this.getControl().setData(key,value);
	}
	
	public void initialize (GridData gd) {
		getControl().setLayoutData(gd);
		Display.getDefault().asyncExec(new Runnable () {
			@Override
			public void run() {
				setSelection(new StructuredSelection(choices[0]), true);
			}
		});
	}

}
