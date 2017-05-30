package org.gw4e.eclipse.constant;

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

import org.eclipse.core.runtime.QualifiedName;

/**
 * Holds all constant used in this plugin
 *
 */
public class Constant {
    /**
     * The plugin id
     */
    public static final String PLUGIN_ID = "gw4e-eclipse-plugin"; //$NON-NLS-1$
    /**
     * A string helper prefix 
     */
    public static final String PREFIX = PLUGIN_ID + "."; //$NON-NLS-1$
    
	public static final QualifiedName QN_PROPERTY_NAME_FOR_TESTS_GENERATION = new QualifiedName("org.gw4e.eclipse.test.generation",   "implementaions");
    
	/**
	 * The maven main java folder
	 */
	public static String SOURCE_MAIN_JAVA = "src/main/java";
	/**
	 * The maven main resource folder
	 */
	public static String SOURCE_MAIN_RESOURCES = "src/main/resources";
	/**
	 * The maven test java folder
	 * Notice that this is the   place where this plugin takes into consideartion the graph model file
	 */
	public static String SOURCE_TEST_JAVA = "src/test/java";
	/**
	 * The maven test resource folder
	 *
	 */
	/**
	 * The maven test resource folder
	 *  Notice that this is the   place where this plugin takes into consideartion the graph model file
	 */
	public static String SOURCE_TEST_RESOURCES = "src/test/resources";
	/**
	 * The folder where GW expects the generated interface
	 */
	public static String SOURCE_GENERATED_INTERFACE = "target/generated-sources";
	/**
	 * The folder where GW expects the generated interface
	 */
	public static String TEST_GENERATED_INTERFACE = "target/generated-test-sources";	
	/**
	 * 
	 */
	public static String BUILD_POLICIES_SYNCHRONIZATION_AUTHORIZED = "true";
	
	/**
	 * Severity Problem Level whenever the java source code is parsed to get the path generator and the abstract 'Context' class is found 
	 */
	public static String SEVERITY_FOR_ABSTRACT_CONTEXT = "W";
	
	
	public static String  SEVERITY_FOR_UNSYNCHRONIZED_CONTEXT = "E";
	
	/**
	 * The build policies file that should be located in the same directory as the graph model files
	 */
	public static String BUILD_POLICIES_FILENAME = "build.policies" ;
	/**
	 * Supported extension for the graph model
	 */
	public static String GRAPHML_FILE = "graphml";
	/**
	 * Supported extension for the graph model
	 */
	public static String GRAPH_JSON_FILE = "json";
	/**
	 * Supported extension for gw3 files
	 */
	public static String GW3_FILE = "gw3";
    /**
     * 
     */
	public static String GRAPH_GW4E_FILE = "gw4e";
	
    public static final String CREATION_WIZARD_PAGE_CONTEXT = PREFIX
            + "creation_wizard_page_context"; //$NON-NLS-1$
    public static final String GRAPHWALKER_NAME = "Graphwalker"; //$NON-NLS-1$
    
  
   
    public static final String START_VERTEX_NAME = "Start"; //$NON-NLS-1$
    
}
