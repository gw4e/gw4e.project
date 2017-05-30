package org.gw4e.eclipse.builder.marker;

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
public class PathGeneratorDescription {
	/**
	 * 
	 */
	static List<ResolutionMarkerDescription> resolutionMarkerDescriptions = new ArrayList<ResolutionMarkerDescription> ();
	static {
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(0,"Walk randomly, until the vertex coverage has reached 100%","random(vertex_coverage(100))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(1,"Walk randomly, until the edge coverage has reached 100%","random(edge_coverage(100))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(2,"Walk randomly, until we have executed for 30 seconds","time_duration(500)"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(3,"Walk the shortest path to the edge e_SomeEdge, and the stop.","a_star(reached_edge(e_SomeEdge))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(4,"Walk the shortest path to the vertex e_SomeVertex, and the stop.","a_star(reached_vertex(e_SomeVertex))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(5,"Will never stop generating a path sequence. Executes for ever in a random fashion.","random(never)"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(6,"Walk randomly, until the edge coverage has reached 50%","random(edge_coverage(50))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(7,"Walk randomly, until the vertex v_SomeVertex is reached.","random(reached_vertex(v_SomeVertex))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(8,"Walk the shortest path to the edge e_SomeEdge, and the stop.","a_star(reached_edge(e_SomeEdge))"));
 		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(9,"Walk randomly for 500 seconds.","random(time_duration(500))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(10,"Walk randomly, until the edge coverage has reached 100%, or we have executed for 500 seconds.","random(edge_coverage(100) or time_duration(500))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(11,"Walk randomly, until the edge coverage has reached 100%, and we have reached the vertex v_SomeVertex.","random(reached_vertex(v_SomeVertex) && edge_coverage(100))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(12,"Walk randomly, until we have executed for 500 seconds, or we have both reached vertex v_SomeVertex and  reached 100% vertex coverage.","random((reached_vertex(v_SomeVertex) and vertex_coverage(100)) || time_duration(500))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(13,"Walk randomly, until the edge coverage has reached 100% and we have reached the vertex v_SomeVertex.Then we will start using the next strategy. Walk randomly for 1 hour","random(reached_vertex(v_SomeVertex) and edge_coverage(100)) random(time_duration(3600))"));
		resolutionMarkerDescriptions.add(new ResolutionMarkerDescription(14,"Walk randomly, until all the edges with dependency higher or equal to 85% are reached","random(dependency_edge_coverage(85))"));
	
		resolutionMarkerDescriptions.sort(new ResolutionMarkerDescription(1,"",""));
	}

	/**
	 * @return
	 */
	public static List<ResolutionMarkerDescription> getDescriptions () {
		return resolutionMarkerDescriptions;
	}
}
