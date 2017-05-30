package org.gw4e.eclipse.property.table;

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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Item;

public abstract class CustomListModel {
	protected String groupname;
	
	protected abstract void add(TableViewer listViewer, SelectionEvent event);
	protected abstract void  remove(IStructuredSelection selection) ;
	protected abstract IStructuredContentProvider getContentProvider();
	protected abstract ITableLabelProvider getLabelProvider();
	protected abstract CustomTableSorter getViewerSorter();
	protected abstract ViewerFilter getViewerFilter();
	protected abstract String[]  getData ();
	protected abstract void update (Item item,String newValue);
	protected abstract void resetData(String[] data);
	/**
	 * @param groupname
	 */
	public CustomListModel(String groupname) {
		super();
		this.groupname = groupname;
	}
	
	/**
	 * 
	 */
	private CustomListModel() {
		super();
 
	}
	/**
	 * @return the groupname
	 */
	public String getGroupname() {
		return groupname;
	}
 
	public static abstract class CustomTableSorter extends ViewerSorter {
		protected static final int ASCENDING = 0;
		protected static final int DESCENDING = 1;
		protected int column;
		protected int direction;

		/**
		 * @param column
		 */
		public void doSort(int column) {
			if (column == this.column) {
				// Same column as last sort; toggle the direction
				direction = 1 - direction;
			} else {
				// New column; do an ascending sort
				this.column = column;
				direction = ASCENDING;
			}
		}

		public abstract int compare(Viewer viewer, Object e1, Object e2); 
	}
}
