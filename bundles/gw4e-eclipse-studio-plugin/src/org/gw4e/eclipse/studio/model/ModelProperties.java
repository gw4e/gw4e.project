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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ModelProperties {
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_BLOCKED = "blocked";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_CUSTOM = "custom";
	
	
	public static final String PROPERTY_GRAPH_START_ELEMENT = "graph.start.element";
	
	public static final String PROPERTY_VERTEX_SHARED = "vertex.shared";
	public static final String PROPERTY_VERTEX_REQUIREMENTS = "vertex.requirements";
	public static final String PROPERTY_VERTEX_SHAREDNAME = "vertex.shared.name";
	public static final String PROPERTY_VERTEX_INIT = "vertex.init";
	
	public static final String PROPERTY_EDGE_GUARD = "edge.guard";
	public static final String PROPERTY_EDGE_ACTION = "edge.action";
	public static final String PROPERTY_EDGE_WEIGHT = "edge.weight";
	public static final String PROPERTY_EDGE_DEPENDENCY= "edge.dependency";
	
	
	public static final String PROPERTY_EDGE_BENDPOINT= "gw.edge.bendpoint.";
	public static final String PROPERTY_VERTEX_INIT_SCRIPT = "gw.vertex.init.script";
	public static final String PROPERTY_VERTEX_WIDTH = "width";
	public static final String PROPERTY_VERTEX_HEIGHT = "height";
	public static final String PROPERTY_VERTEX_X = "x";
	public static final String PROPERTY_VERTEX_Y = "y";
	
	public static boolean isCustomProperty(String key) {
		if (key == null) return false;
		String temp = key.trim();
		if (PROPERTY_DESCRIPTION.equalsIgnoreCase(temp)) return false;
		if (PROPERTY_BLOCKED.equalsIgnoreCase(temp)) return false;
		
		if (PROPERTY_VERTEX_INIT_SCRIPT.equalsIgnoreCase(temp)) return false;
		if (PROPERTY_VERTEX_WIDTH.equalsIgnoreCase(temp)) return false;
		if (PROPERTY_VERTEX_HEIGHT.equalsIgnoreCase(temp)) return false;
		if (PROPERTY_VERTEX_X.equalsIgnoreCase(temp)) return false;
		if (PROPERTY_VERTEX_Y.equalsIgnoreCase(temp)) return false;
		
		if (temp.startsWith(PROPERTY_EDGE_BENDPOINT)) return false;
		return true;
	}

	public static Map<String, Object> filterCustomProperty(Map<String, Object> properties) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Iterator<String> iter = properties.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (!isCustomProperty(key)) continue;
			Object value = properties.get(key);
			ret.put(key, value);
		}
		return ret;
	}
}
