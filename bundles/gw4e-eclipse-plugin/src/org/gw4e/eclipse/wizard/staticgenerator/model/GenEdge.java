package org.gw4e.eclipse.wizard.staticgenerator.model;

public class GenEdge {
	GenVertex sourceVertex;
	GenVertex targetVertex;
	GenGuard guard;
	GenAction action;
	String name;
	String id;
	 
	public GenEdge(GenVertex sourceVertex, GenVertex targetVertex, GenGuard guard, GenAction action, String name,
			String id) {
		super();
		this.sourceVertex = sourceVertex;
		this.targetVertex = targetVertex;
		this.guard = guard;
		this.action = action;
		this.name = name;
		this.id = id;
	}
	public GenVertex getSourceVertex() {
		return sourceVertex;
	}
	public GenVertex getTargetVertex() {
		return targetVertex;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public GenGuard getGuard() {
		return guard;
	}
	public GenAction getAction() {
		return action;
	}
}
