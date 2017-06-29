package org.gw4e.eclipse.wizard.staticgenerator.model;

import java.util.regex.Pattern;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.gw4e.eclipse.facade.JDTManager;

public class ExecutionContextPage {
	ICompilationUnit ancestor;
 
	public ExecutionContextPage(ICompilationUnit ancestor) {
		super();
		this.ancestor = ancestor;
	}
	 
	public String getClassName () {
		try {
			return JDTManager.getJavaFullyQualifiedName(ancestor);
		} catch (JavaModelException e) {
			return ancestor.getElementName().split(Pattern.quote("."))[0];
		}
		
	}
}
