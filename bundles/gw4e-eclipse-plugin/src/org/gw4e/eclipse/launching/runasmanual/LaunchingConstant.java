package org.gw4e.eclipse.launching.runasmanual;

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
 * Defines a set of string to get/set the value entered in the GW4E Offline Launcher Tab 
 * They are the values of the offline command argument
 * See the GraphWalker offline command documentation for more information
 */
public interface LaunchingConstant {
	/**
	 * The config attribute used to get and set the selected project
	 */
	public static String CONFIG_PROJECT = "gw4e.launch.config.project.name";
	/**
	 * The config attribute used to get and set the selected model file
	 */
	public static String CONFIG_GRAPH_MODEL_PATH = "gw4e.launch.config.graph.model.path";
	/**
	 * The config attribute used to get and set the unvisited option value
	 */
	public static String CONFIG_UNVISITED_ELEMENT = "gw4e.launch.config.unvisited.element";
	/**
	 *  The config attribute used to get and set the verbose option value
	 */
	public static String CONFIG_VERBOSE = "gw4e.launch.config.verbose";
	/**
	 * The config attribute used to get and set the start element value
	 */
	public static String CONFIG_LAUNCH_STARTNODE= "gw4e.launch.config.start.node";
	/**
	 * The config attribute used to get and set the generator and stop condition value
	 */
	public static String CONFIG_GRAPH_GENERATOR_STOP_CONDITIONS = "gw4e.launch.config.generatorstopconditions";
	/**
	 * The class launched  to execute the offline command
	 */
	public static String CONFIG_LAUNCH_CLASS = "org.graphwalker.cli.CLI";
	
	/**
	 * The config used to set whether the configuration will be logged before the test execution
	 */
	public static String CONFIG_LAUNCH_REMOVE_BLOCKED_ELEMENT_CONFIGURATION = "gw4e.launch.config.remove.blocked.elements.configuration";
	
	/**
	 * The config used to set the additional models used ofr the test
	 */
	public static String CONFIG_LAUNCH_ADDITIONNAL_MODELS_CONFIGURATION = "gw4e.launch.config.additional.models.configuration";
	
 

}

