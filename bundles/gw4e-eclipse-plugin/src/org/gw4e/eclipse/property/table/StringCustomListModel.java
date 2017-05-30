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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;
import org.gw4e.eclipse.message.MessageUtil;

/**
 * A Custom Model managing String
 *
 */
public   class StringCustomListModel extends CustomListModel {

	private CustomTableSorter sorter = new CustomTableSorter() {
		public int compare(Viewer viewer, Object e1, Object e2) {
			int rc = 0;
			String s1 = (String) e1;
			String s2 = (String) e2;
			switch (column) {
			case 0:
				rc = (s1).compareTo(s2);
				break;
			}
			if (direction == DESCENDING) {
				rc = -rc;
			}
			return rc;
		}	
	};
	
	List<String> folders = new ArrayList<String>();

	public StringCustomListModel(String groupname, String[] values) {
		super(groupname);
		for (int i = 0; i < values.length; i++) {
			this.folders.add(values[i]);
		}
	}

	@Override
	protected void add(TableViewer listViewer, SelectionEvent event) {
		folders.add(MessageUtil.getString("enteranewvalue"));
	}

	@Override
	protected void remove(IStructuredSelection selection) {
		String text = (String) selection.getFirstElement();
		boolean b = folders.remove(text);
	}

	@Override
	protected IStructuredContentProvider getContentProvider() {
		return new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				String[] ret = new String[folders.size()];
				folders.toArray(ret);
				return ret;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

			}
		};

	}

	@Override
	protected ITableLabelProvider getLabelProvider() {
		return new ITableLabelProvider() {

			@Override
			public void addListener(ILabelProviderListener arg0) {

			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				return false;
			}

			@Override
			public void removeListener(ILabelProviderListener arg0) {

			}

			@Override
			public Image getColumnImage(Object arg0, int arg1) {
				return null;
			}

			@Override
			public String getColumnText(Object arg0, int arg1) {
				String ret = (String) arg0;
				return ret;
			}

		};
	}

	@Override
	protected CustomTableSorter getViewerSorter() {
		return sorter;
	}

	@Override
	protected ViewerFilter getViewerFilter() {
		return new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return true;
			}
		};
	}

	@Override
	protected String[] getData() {
		String[] ret = new String[folders.size()];
		folders.toArray(ret);
		return ret;
	}
 	 
	 

	@Override
	protected void update(Item item, String newValue) {
		if (item==null) return; 
		String value = (String) item.getData();
		int index = this.folders.indexOf(value);
		if (index==-1) return;
		this.folders.set(index, newValue);
		
	}

	@Override
	protected void resetData(String[] data) {
		folders.clear();
		for (int i = 0; i < data.length; i++) {
			folders.add(data[i]);
		}
	}

}
