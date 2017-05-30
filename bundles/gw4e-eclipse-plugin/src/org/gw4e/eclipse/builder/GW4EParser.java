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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.gw4e.eclipse.facade.ResourceManager;
 
/**
 * A contract for parsers
 *
 */
public abstract class GW4EParser {
	
	/**
	 * Error code for problem marker
	 */
	public static Integer MISSING_BUILD_POLICIES_FILE = new Integer("9999");
	/**
	 *  Error code for problem marker
	 */
	public static Integer MISSING_POLICIES_FOR_FILE= new Integer("9998");
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_PATH_GENERATOR = new Integer("9997");
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_SEVERITY= new Integer("9996");
	 
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_UNEXISTING_GRAPHFILE= new Integer("9995");
	
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_ANNOTATION_PATH_GENERATOR = new Integer("9994");
	
	/**
	 *  Error code for problem marker
	 */
	public static Integer GRAPH_MODEL_SYNTAX = new Integer("9993");
	
	/**
	 *  Error code for problem marker
	 */
	public static Integer ABSTRACT_CONTEXT_USED = new Integer("9992");
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_PATH_MODEL_GENERATED = new Integer("9991");
	/**
	 *  Error code for problem marker
	 */
	public static Integer INVALID_START_EDGE = new Integer("9990");
	
	/**
	 *  Error code for problem marker
	 */
	public static Integer UNSYNCHRONIZED_GRAPH = new Integer("8989");
	 
	
	/**
	 * Parse the passed file
	 * @param in
	 */
	protected abstract void doParse (IFile in);
	
	protected void parse (IFile in) {
		doParse(in);
	}
	
	 
	/**
	 * @param in
	 */
	public void removeMarkers(IFile in) {
		try {
			IMarker[] markers =  in.findMarkers(null, true, IResource.DEPTH_INFINITE);
			for (int i = 0; i < markers.length; i++) {
				IMarker marker = markers[i];
				Object object = marker.getAttribute(IMarker.SOURCE_ID);
				if (object instanceof String) {
					String sourceid = (String) object;
					if ( (sourceid==null) || (sourceid.trim().length()==0) ) {
						continue;
					}
					 
					if (this.getClass().getName().equals(sourceid)) {
						try {
							marker.delete();
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}
				}
			}
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}
}
