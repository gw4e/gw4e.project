package org.gw4e.eclipse.test.fwk;

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

import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.debug.ui.console.IConsoleLineTracker;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;

public class ConsoleTracker implements IConsoleLineTracker {

	private IConsole m_console;
	private boolean started = false;
	private List<String> messages = null;

	public void init(IConsole console) {
		m_console = console;
	}

	public synchronized void start() {
		started = true;
		messages = new ArrayList<String> ();
	}
	
	public synchronized void stop() {
		started = false;
		messages.clear();
		messages = null;
	}
	
	public synchronized boolean isStarted() {
		return started;
	}
	
	@Override
	public void lineAppended(IRegion region) {
		if (!isStarted()) return;
		try {
			String line = m_console.getDocument().get(region.getOffset(), region.getLength());
			messages.add(line);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public synchronized boolean containsMessage (String expected) {
		return messages.contains(expected);
	}
	
	public void dispose() {
	}

}
