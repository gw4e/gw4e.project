package org.gw4e.eclipse.wizard.convert.page;

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

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TableHelper {
	
	public static void handleEvent(Event event) {

		Table table = (Table) event.widget;
		int columnCount = table.getColumnCount();
		if (columnCount == 0)
			return;
		Rectangle area = table.getClientArea();
		int totalAreaWdith = area.width;
		int lineWidth = table.getGridLineWidth();
		int totalGridLineWidth = (columnCount - 1) * lineWidth;
		int totalColumnWidth = 0;
		for (TableColumn column : table.getColumns()) {
			totalColumnWidth = totalColumnWidth + column.getWidth();
		}
		int diff = totalAreaWdith - (totalColumnWidth + totalGridLineWidth);

		TableColumn lastCol = table.getColumns()[columnCount - 1];

		lastCol.setWidth(diff + lastCol.getWidth());

	}
}
