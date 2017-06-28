package org.gw4e.eclipse.wizard.staticgenerator.model;

import java.util.regex.Pattern;

import org.eclipse.jdt.core.ICompilationUnit;

public class ExecutionContextPage {
	ICompilationUnit ancestor;
 
	public ExecutionContextPage(ICompilationUnit ancestor) {
		super();
		this.ancestor = ancestor;
	}
	 
	public String getClassName () {
		return ancestor.getElementName().split(Pattern.quote("."))[0];
	}
}
