package org.gw4e.eclipse.launching.runasmanual;

import java.util.ArrayList;
import java.util.List;

public class StepDetail {
	String description;
	String result;
	List<String> requirements;
	boolean vertex;
	String name;
	boolean failed;
	boolean performed;
    boolean voidStatus = false;
    
	public StepDetail(String name, String description, List<String> requirements, boolean vertex) {
		super();
		this.name = name;
		this.description = description == null ? "" : description;
		this.requirements = requirements == null ? new ArrayList<String>() : requirements;
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

	public boolean hasDescription() {
		return this.description.trim().length() > 0;
	}

	public boolean isEdge() {
		return !vertex;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isPerformed() {
		return performed;
	}

	public void setPerformed(boolean performed) {
		this.performed = performed;
	}

	public String getStatus() {
		String status = "";
		if (isVoidStatus()) return "";
		if (isPerformed()) {
			if (isFailed()) {
				status = "0";
			} else {
				status = "1";
			}
		} else {
			status = "2";
		}
		return status;
	}

	public boolean isVoidStatus() {
		return voidStatus;
	}

	public StepDetail setVoidStatus(boolean voidStatus) {
		this.voidStatus = voidStatus;
		return this;
	}
}
