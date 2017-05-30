package org.gw4e.eclipse.studio.model;

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
import java.util.StringTokenizer;

public class Script {
	
	public static Script NULL = new Script ("");
	
	String source = "";
	public Script(String source) {
		this.source=source;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	public boolean hasSourceCode () {
		return source!=null && source.trim().length() > 0;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer ();
		sb.append(source);
		return sb.toString();
	}
	
	public String asString () {
		if (source==null || source.trim().length() == 0) return null;
		return this.toString();
	}
	
	public List<String> asList () {
		String newline = System.getProperty("line.separator");
		List<String> ret = new ArrayList<String> ();
		if (source==null || source.trim().length() == 0) return ret;
		StringTokenizer st = new StringTokenizer (source,newline);
		while (st.hasMoreTokens()) {
			ret.add(st.nextToken());
		}
		return ret;
	}
}
