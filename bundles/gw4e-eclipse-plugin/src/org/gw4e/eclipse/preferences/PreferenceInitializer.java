package org.gw4e.eclipse.preferences;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.core.Version;
import org.gw4e.eclipse.facade.SettingsManager;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	static Map<String, String[] > DEFAULTS = new HashMap<String, String[] > ();
	static {
		DEFAULTS.put(PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_IMPLEMENTATION, new String[] {"Impl"});
		DEFAULTS.put(PreferenceManager.TIMEOUT_FOR_GRAPHWALKER_TEST_EXECUTION, new String[] {"180"});
		DEFAULTS.put(PreferenceManager.TIMEOUT_FOR_TEST_OFFLINE_GENERATION, new String[] {"15"});
		DEFAULTS.put(PreferenceManager.SUFFIX_PREFERENCE_FOR_TEST_OFFLINE_IMPLEMENTATION, new String[] {"OffLineImpl"});
		DEFAULTS.put(PreferenceManager.BUILD_POLICIES_FILENAME, new String[] {Constant.BUILD_POLICIES_FILENAME});
		DEFAULTS.put(PreferenceManager.DEFAULT_SEVERITY, new String[] {"I"});
		DEFAULTS.put(PreferenceManager.LOG_INFO_ENABLED, new String[] {"false"});
		DEFAULTS.put(PreferenceManager.PERFORMANCE_CONFIGURATION, new String[] {
			"gw4e-eclipse-plugin/perf/builders/GW4EParserImpl/Analysing=800",
			"gw4e-eclipse-plugin/perf/builders/GW4EParserImpl/Parsing=800",
		});
		
		DEFAULTS.put(PreferenceManager.DEFAULT_POLICIES, new String[] { 
				"random(vertex_coverage(100));I",
				"random(edge_coverage(100));I" }); 
		DEFAULTS.put(PreferenceManager.GW4E_ENABLE_BUILD, new String[] {"true"});
		DEFAULTS.put(PreferenceManager.GRAPHWALKER_VERSION, new String[] {Version.GW_VERSION});
		DEFAULTS.put(PreferenceManager.GRAPHWALKER_JAVALIBRARIES, new String[] {
				"M2_REPO/org/graphwalker/graphwalker-cli/" + DEFAULTS.get(PreferenceManager.GRAPHWALKER_VERSION)[0] + "/graphwalker-cli-" +  DEFAULTS.get(PreferenceManager.GRAPHWALKER_VERSION) [0] + ".jar", });
		DEFAULTS.put(PreferenceManager.AUTHORIZED_FOLDERS_FOR_GRAPH_DEFINITION, new String[] {
				Constant.SOURCE_MAIN_RESOURCES, 
				Constant.SOURCE_TEST_RESOURCES });
		DEFAULTS.put(PreferenceManager.GW4E_ENABLE_PERFORMANCE, new String[] {"false"});
		
		DEFAULTS.put(PreferenceManager.GW4E_SOURCE_MAIN_JAVA, new String[] {Constant.SOURCE_MAIN_JAVA });
		DEFAULTS.put(PreferenceManager.GW4E_SOURCE_TEST_JAVA, new String[] {Constant.SOURCE_TEST_JAVA });
		DEFAULTS.put(PreferenceManager.GW4E_RESOURCE_MAIN, new String[] {Constant.SOURCE_MAIN_RESOURCES });
		DEFAULTS.put(PreferenceManager.GW4E_RESOURCE_TEST, new String[] {Constant.SOURCE_TEST_RESOURCES});
		DEFAULTS.put(PreferenceManager.GW4E_MAIN_SOURCE_GENERATED_INTERFACE, new String[] {Constant.SOURCE_GENERATED_INTERFACE });
		DEFAULTS.put(PreferenceManager.GW4E_TEST_SOURCE_GENERATED_INTERFACE, new String[] {Constant.TEST_GENERATED_INTERFACE });
		DEFAULTS.put(PreferenceManager.GW4E_TEST_MAX_STEPS_MANUAL_TEST_WIZARD, new String[] { "50" });
		
		DEFAULTS.put(PreferenceManager.BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED, new String[] {Constant.BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED });
		DEFAULTS.put(PreferenceManager.SEVERITY_FOR_ABSTRACT_CONTEXT, new String[] {Constant.SEVERITY_FOR_ABSTRACT_CONTEXT });
		DEFAULTS.put(PreferenceManager.SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT, new String[] {Constant.SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT });		
	}
		
	
	/**
	 * @return the name of the default supported preference property names
	 */
	public static String[] getDefaultPropertyNames() {
		String[] values = new String[DEFAULTS.size()];
		int[] indexes = new int[] {0};
		DEFAULTS.keySet().forEach(new Consumer<String> () {
			@Override
			public void accept(String t) {
				values[indexes[0]++] = t; 
			}
		});
		return values;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		Iterator<String> iterator = DEFAULTS.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String[] values = SettingsManager.getValues(null,key,false);
			if (values!=null && values.length>0) continue; 
			values = DEFAULTS.get(key);
			SettingsManager.putValues(null, key, values, false);
		}
		
		
 	}
 
	/**
	 * @param key
	 * @return
	 */
	public static String[] getDefautValues (String key) {
		String[] values = DEFAULTS.get(key);
		return values;
	}

}
