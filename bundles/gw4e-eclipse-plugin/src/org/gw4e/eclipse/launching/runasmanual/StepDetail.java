package org.gw4e.eclipse.launching.runasmanual;

import java.util.ArrayList;
import java.util.List;

public class StepDetail {
	String description;
	List<String> requirements;
	boolean vertex;
	String name;
	public StepDetail(String name, String description, List<String> requirements, boolean vertex) {
		super();
		this.name=name;
		this.description = description == null ? "" :  description;
		this.requirements = requirements == null ? new ArrayList<String> () : requirements;
		this.vertex = vertex;
	}
	public String getDescription() {
		return description;
	}
	public List<String> getRequirements() {
		return requirements;
	}
	public boolean isVertex() {
		return vertex;
	}
	public String getName() {
		return name;
	}
	
}
