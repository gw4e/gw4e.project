package org.gw4e.eclipse.wizard.runasmanual;

import java.io.File;
import java.util.List;

import org.gw4e.eclipse.launching.runasmanual.StepDetail;

public interface ITestPersistence {
	public void persist (File file, String title,boolean exportAsTemplate,String dateFormat, String testcaseid,String component,String priority,String description,boolean updateDetailSheet,List<StepDetail> details);
}
