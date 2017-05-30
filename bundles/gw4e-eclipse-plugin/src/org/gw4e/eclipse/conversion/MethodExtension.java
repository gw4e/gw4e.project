package org.gw4e.eclipse.conversion;

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

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.gw4e.eclipse.facade.ResourceManager;
 
 

/**
 *   
 *
 */
public abstract class MethodExtension {
	 
	/**
	 * 
	 */
	protected String name;
	/**
	 * 
	 */
	protected MethodDeclaration  methodDeclaration = null;
	/**
	 * 
	 */
	protected String startElement = null;
	/**
	 * 
	 */
	private String qclassname = null;
	/**
	 * 
	 */
	List<String> additionalContexts;
	/**
	 * @param classname
	 * @param additionalContexts
	 * @param name
	 * @param startElement
	 */
	public MethodExtension(String classname,List<String> additionalContexts, String name,String startElement) {
		super();
		this.name = name;
		this.startElement = startElement;
		this.qclassname=classname;
		this.additionalContexts=additionalContexts;
 
	}
	
	
	/**
	 * @return
	 */
	protected Class getTestClassImplementation () {
		try {
			return this.getClass().getClassLoader().loadClass(qclassname);
		} catch (ClassNotFoundException e) {
			ResourceManager.logException(e, qclassname);
		}
		return null;
	}
	
	/**
	 * @return
	 */
	public String [] getImports() {
		List<String> all = new ArrayList<String>();
		String []  classes = getImportedClasses ();
		for (int i = 0; i < classes.length; i++) {
			all.add(classes[i]);
		} 
		for (String context : additionalContexts) {
			all.add(context);
		}
		String [] ret = new String [all.size()];
		all.toArray(ret); 
		return ret;
	}
	
	/**
	 * @return
	 */
	public String [] getStaticImports() {
		List<String> all = new ArrayList<String>();
		String []  classes = getStaticImportedClasses ();
		for (int i = 0; i < classes.length; i++) {
			all.add(classes[i]);
		} 
		String [] ret = new String [all.size()];
		all.toArray(ret); 
		return ret;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return
	 */
	public abstract String getSource(String [] additionalContext, String value) ;
 
	/**
	 * @return
	 */
	public abstract String [] getImportedClasses () ;

	/**
	 * @return
	 */
	public String [] getStaticImportedClasses () {
		return new String[0]; 
	}
	
	/**
	 * @return
	 */
	protected int getAnnotationIndex () {
		return 0;
	}

	
	/**
	 * @return
	 */
	public MethodDeclaration getMethodDeclaration (String [] additionalContext,String value) {
		String source = getSource(additionalContext,value);
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_CLASS_BODY_DECLARATIONS);
		TypeDeclaration cu=null;
		try {
			cu = (TypeDeclaration) parser.createAST(null);
		} catch (Exception e) {
			ResourceManager.logException(e,source);
			return null;
		}
		 
		cu.accept(new ASTVisitor() {
			public boolean visit(TypeDeclaration  node) {
				methodDeclaration = (MethodDeclaration)node.bodyDeclarations().get(0);
				return false;
			}
		});
		 
		return methodDeclaration;
	}
	 
	/**
	 * @return the annotation
	 */
	public String getAnnotation() {
		return getImportedClasses ()[ getAnnotationIndex () ];
	}


	/**
	 * @return the classname
	 */
	public String getQualifiedClassname() {
		return qclassname;
	}
 
	/**
	 * @return
	 */
	public String getClassname() {
		return getSimplifiedClassName(qclassname);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static String getSimplifiedClassName (String name) {
		String [] segments = name.split("\\.");
		return segments [segments.length-1];
	}
}
