package org.gw4e.eclipse.launching.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.message.MessageUtil;

public class ModelData {
	IFile file;
	BuildPolicy[] policies;
	String selectedPolicy;
	boolean initialized = false;
	boolean selected = false;
	public ModelData(IFile file) {
		this.file = file;
		this.policies = loadPolicies(file);
		if (this.policies.length > 0) {
			this.setSelectedPolicy(policies[0].getPathGenerator());
		} else {
			this.setSelectedPolicy("?");
		}
	}
	
	public boolean equals (Object o) {
		if (o instanceof ModelData) {
			ModelData other = (ModelData) o;
			return other.getFullPath().equals(getFullPath());
		}
		return false;
	}
	
	public void initialize(ModelData[] models) {
		if (initialized) return;
		initialized = true;
		for (int i = 0; i < models.length; i++) {
			if (models[i].getFullPath().equals(getFullPath())) {
				this.setSelectedPolicy(models[i].getSelectedPolicy());
				this.selected = models[i].isSelected();
			}
		}
	}
	
	public String validatePolicy() {
		if (selectedPolicy == null || selectedPolicy.trim().length() == 0) {
			return MessageUtil.getString("empty_path_generator");
		}
		if (!GraphWalkerFacade.parsePathGenerator(selectedPolicy.trim())) {
			return MessageUtil.getString("invalid_path_generator");
		}
		return null;
	}

	public String getName() {
		return file.getName();
	}

	public String getFullPath() {
		return file.getFullPath().toString();
	}

	private BuildPolicy[] loadPolicies(IFile file) {
		List<BuildPolicy> policies;
		try {
			if (file != null) {
				policies = BuildPolicyManager.getBuildPolicies(file, false);
			} else {
				policies = new ArrayList<BuildPolicy>();
			}
		} catch (Exception e) {
			policies = new ArrayList<BuildPolicy>();
		}
		BuildPolicy[] input = policies.stream().filter(item -> !item.hasTimeDuratioStopCondition())
				.toArray(BuildPolicy[]::new);
		return input;
	}

	public String getSelectedPolicy() {
		return selectedPolicy;
	}

	public void setSelectedPolicy(String selectedPolicy) {
		this.selectedPolicy = selectedPolicy;
	}

	public BuildPolicy[] getPolicies() {
		return policies;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}