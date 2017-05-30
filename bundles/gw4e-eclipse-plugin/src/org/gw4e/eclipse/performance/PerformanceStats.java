package org.gw4e.eclipse.performance;

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

/**
 * @author  
 *
 */
public class PerformanceStats {
	/**
	 * 
	 */
	String project;	
	/**
	 * 
	 */
	String enabler;
	/**
	 * 
	 */
	String kind;
	/**
	 * 
	 */
	String context;
	
	/**
	 * 
	 */
	int runCount = 0;
	
	/**
	 * 
	 */
	long runningTime = 0;
	
	/**
	 * 
	 */
	private List<Execution> executions = new ArrayList<Execution> ();
	
	/**
	 * 
	 */
	private Execution current = null;
	
	 
	
	/**
	 * @param kind
	 * @param context
	 */
	public PerformanceStats(String project,String enabler,String kind, String context) {
		this.kind=kind;
		this.context=context;
		this.project=project;
		this.enabler=enabler;
	}

	/**
	 * 
	 */
	public void start () {
		current = new Execution (this);
		runCount++;
		executions.add(current);
	}
	
	
	/**
	 * 
	 */
	public void end () {
		current.end();
		runningTime+=current.elapsed();
		PerformanceStatsManager.evaluate(current);
		current  = null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PerformanceStats))
			return false;
		PerformanceStats that = (PerformanceStats) obj;
		if (!this.project.equals(that.project))
			return false;
		if (!this.kind.equals(that.kind))
			return false;
		if (!this.context.equals(that.context))
			return false;
		return true;
	}
	
	/**
	 * @return
	 */
	public String getConfiguration () {
		StringBuffer sb = new StringBuffer();
		sb.append(enabler);
		if (!enabler.endsWith("/")) sb.append("/");
		sb.append(kind);
		return sb.toString();
	}

	/**
	 * @return the runningTime
	 */
	public long getRunningTime() {
		return runningTime;
	}
	
	/**
	 * @return
	 */
	public int getRunCount () {
		return executions.size();
	}

	/**
	 * @return the executions
	 */
	public List<Execution> getExecutions() {
		return executions;
	}

	/**
	 * @return the project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	
}
