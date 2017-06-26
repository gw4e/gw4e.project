package org.gw4e.eclipse.wizard.staticgenerator.model;

import java.util.List;

public class GenModel {
	GenVertex startVertex;
	List<GenVertex> vertices;
	List<GenEdge> edges;
	String pathgenerator;
	public GenModel(GenVertex startVertex, String pathgenerator, List<GenVertex> vertices, List<GenEdge> edges) {
		super();
		this.startVertex = startVertex;
		this.vertices = vertices;
		this.edges = edges;
		this.pathgenerator = pathgenerator;
	}
	
	
}
