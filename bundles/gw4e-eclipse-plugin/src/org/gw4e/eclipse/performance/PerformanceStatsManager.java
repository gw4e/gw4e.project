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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gw4e.eclipse.facade.SettingsManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.preferences.ProjectPropertyChangeListener;

/**
 * @author  
 *
 */
public class PerformanceStatsManager implements ProjectPropertyChangeListener {

	static {
		SettingsManager.addListener(new PerformanceStatsManager());
	}
	
	public static boolean underTest = false;
	
	private final static Map<PerformanceStats, PerformanceStats> statMap =
			Collections.synchronizedMap(new HashMap<PerformanceStats,PerformanceStats>());
	
	private final static Map<String, Long> thresholdMap = Collections.synchronizedMap(new HashMap<String, Long>());
	
 	private final static PerformanceStats NULLSTATS = new PerformanceStats ("","","","") {
 		public void start () {};
 		public void end () {};
 	};
 	
 	public static void setUnderTest () {
 		underTest = true;
 	}
 	
 	public static boolean isUnderTest () {
 		return underTest;
 	}
 	
	/**
	 * 
	 */
	private PerformanceStatsManager() {
	}
	
	
	/**
	 * @return
	 */
	public static Execution [] getAllExecutions () {
		List<Execution> executions = new ArrayList<Execution> ();
		Iterator<PerformanceStats> iterator = statMap.keySet().iterator();
		while (iterator.hasNext()) {
			PerformanceStats stat = (PerformanceStats) iterator.next();
			executions.addAll(stat.getExecutions());
		}
		Collections.sort(executions);
		Execution [] temp =  new Execution [executions.size()];
		executions.toArray(temp);
		return temp;
	}
	
	/**
	 * @param p
	 * @return
	 */
	public static boolean performanceConfigured (Properties p) {
		Set<Object> set = p.keySet(); 
		Iterator iter = set.iterator(); 
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (key.startsWith(org.gw4e.eclipse.Activator.PLUGIN_ID)) return true;
		}
		return false;
	}
	
	/**
	 * @param kind
	 * @param context
	 * @return
	 */
	public static PerformanceStats getStats(String project,String enabler, String kind, String context) {
		if (!isEnabled(project,enabler)) {
			return NULLSTATS;
		}
		PerformanceStats newStat = new PerformanceStats(project,enabler,kind, context);
		PerformanceStats oldStat = statMap.get(newStat);
		if (oldStat != null)
			return oldStat;
		statMap.put(newStat, newStat);
		return newStat;
	}
	
	/**
	 * 
	 */
	public static void clear () {
		statMap.clear();
	}
	
	/**
	 * @param eventName
	 * @return
	 */
	public static boolean isEnabled(String project, String kind) {
		if (!PreferenceManager.isPerformanceEnabled(project))
			return false;
		boolean b = true; 
	//  The configuration for this is heavy. Let's remove this burden ...
    //  Need time to think about a better configuration mode
	//	String option = Platform.getDebugOption(kind);
	//	boolean b = option != null && !option.equalsIgnoreCase("false") && !option.equalsIgnoreCase("-1");
	//	if (!b) {
	//		return  isUnderTest (); // Only for tests.  activated by  the swtbot tests
	//	}
		return b;
	}
	
	
	/**
	 * @param eventName
	 * @return
	 */
	private static long getThreshold(String projectName,String config) {
		Long value = thresholdMap.get(config);
		if (value == null) {
			String option = null;
			String[] values = PreferenceManager.getPerformanceConfiguration(projectName);
			for (String val : values) {
				if (val.startsWith(config)) {
					String[] temps = val.trim().split("=");
					option = temps[1].trim(); 
					break;
				}
			}
			if (option != null) {
				try {
					value = new Long(option);
				} catch (NumberFormatException e) {
					//invalid option, just ignore
				}
			}
			if (value == null)
				value = new Long(Long.MAX_VALUE);
			thresholdMap.put(config, value);
		}
		return value.longValue();
	}
	
	/**
	 * @param execution
	 */
	public static void evaluate (Execution execution) {
		String config = execution.getStat().getConfiguration();
		long maxValue = getThreshold(execution.getProject(), config);
		execution.setFailure((execution.elapsed() > maxValue));
	}

	@Override
	public void projectPropertyUpdated(String projectName, String property, String[] oldValues, String[] newValue) {
		clear ();
		thresholdMap.clear();
	}
}
