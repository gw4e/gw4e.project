package org.gw4e.eclipse.builder;

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

/**
 * @author  
 *
 */
public class Location {
	
	/**
	 * 
	 */
	public static Location NULL_LOCATION = new Location(-1,-1,-1);
	
	
	/**
	 * 
	 */
	int lineNumber;
	/**
	 * 
	 */
	int start;
	/**
	 * 
	 */
	int end;
	/**
	 * @param lineNumber
	 * @param start
	 * @param end
	 */
	public Location(int lineNumber, int start, int end) {
		super();
		this.lineNumber = lineNumber;
		this.start = start;
		this.end = end;
	}
	
	public void relocateAtLastChar () {
		this.start = this.end-1;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return "[" + lineNumber + "," + start +"," + end + "]";
	}
	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	 
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			Location other = (Location) obj;
			return other.lineNumber == this.lineNumber && other.start == this.start && other.end == this.end;
		}
		return false;
	}
 }
