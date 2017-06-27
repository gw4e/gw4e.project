package org.gw4e.eclipse.fwk.conditions;

import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.gw4e.eclipse.fwk.project.GW4EProject;
import org.gw4e.eclipse.fwk.view.ProblemView;

public class ErrorIsInProblemView extends DefaultCondition {
	private String error;
	private ProblemView pbView;
	public ErrorIsInProblemView(ProblemView pbView, String error) {
		this.error = error.trim();
		this.pbView = pbView;
	}

	public String getFailureMessage() {
		return "error '" + error + "' is not in the problem view";
	}

	public boolean test() throws Exception {
		try {
			pbView.errorIsInProblemView (error);
			return  true;
		} catch (Exception e) {
			return false;
		}
	}
}
