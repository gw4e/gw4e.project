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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.graphwalker.java.annotation.GraphWalker;
import org.gw4e.eclipse.facade.ResourceManager;

public class ClassExtension {

	/**
	 * 
	 */
	String pathGenerator = null;

	/**
	 * 
	 */
	String annotationStartElement = null;

	/**
	 * 
	 */
	String groups = null;

	/**
	 * 
	 */
	NormalAnnotation annotation = null;

	/**
	 * 
	 */
	ExpressionStatement expression;
	
	/**
	 * 
	 */
	FieldDeclaration field;
	
	/**
	 * 
	 */
	boolean generateAnnotation;
	
	/**
	 * 
	 */
	IPath graphPath;
	
	/**
	 * 
	 */
	IFile ifile;
	/**
	 * 
	 */
	boolean generateExecutionHook;
	/**
	 * 
	 */
	boolean generatePerformance;
	/**
	 * 
	 */
	boolean generateElementHook;
	/**
	 * 
	 */
	boolean generateRunSmokeTest;
	/**
	 * 
	 */
	boolean generateRunFunctionalTest;
	/**
	 * 
	 */
	boolean generateRunStabilityTest;
	
	/**
	 * 
	 */
	boolean generateRunModelBased;

	/**
	 * 
	 */
	String targetVertex;

	/**
	 * 
	 */
	List<IFile> additionalContexts;
	
	/**
	 * 
	 */
	String startElementForJunitTest;
	
	public ClassExtension(boolean generateExecutionHook,
 			boolean generatePerformance,
 			boolean generateElementHook,
 			boolean generateRunSmokeTest,
 			boolean generateRunFunctionalTest,
 			boolean generateRunStabilityTest,
 			String targetVertex,
 			String startElementForJunitTest,
 			List<IFile> additionalContexts,
 			boolean generateModelBased,
 			boolean generateAnnotation,
 			String pathGenerator, 
 			String annotationStartElement, 
 			String groups,
 			IFile ifile) throws JavaModelException {
		super();
		this.pathGenerator = pathGenerator;
		this.annotationStartElement = annotationStartElement;
		this.groups = groups;
		this.ifile = ifile;
		this.graphPath = ResourceManager.getPathWithinPackageFragment(ifile);
		this.generateAnnotation=generateAnnotation;
		this.generateExecutionHook=generateExecutionHook;
		this.generatePerformance=generatePerformance;
		this.generateElementHook=generateElementHook;
		this.generateRunSmokeTest=generateRunSmokeTest;
		this.generateRunFunctionalTest=generateRunFunctionalTest;
		this.generateRunStabilityTest=generateRunStabilityTest;
		this.generateRunModelBased=generateModelBased;	
		this.targetVertex=targetVertex;
		this.startElementForJunitTest=startElementForJunitTest;
		this.additionalContexts=additionalContexts;			
	}
	
	/**
	 * @return the import annotation
	 */
	public String [] getImports() {
		List<String> classes = new ArrayList<String>();
		classes.add(javax.annotation.Generated.class.getName());
		classes.add(java.nio.file.Path.class.getName());
		classes.add(java.nio.file.Paths.class.getName());
		if (this.generateAnnotation) {
			classes.add(GraphWalker.class.getName());
		}
		String [] ret = classes.toArray(new String[classes.size()]);
		return ret;
	}

	/**
	 * @param methodname
	 * @return
	 */
	public ExpressionStatement getBodyMethod(String methodname) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(getBodyMethodSource (methodname).toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final  CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(ExpressionStatement node) {
				expression = node;
				return true;
			}
		});		 
		return expression;
	}
	
	/**
	 * @param methodname
	 * @return
	 */
	public FieldDeclaration getField(   ) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		String s = getStaticVariable ( );
		if (s==null) return null;
		parser.setSource(s.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final  CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(FieldDeclaration node) {
				field = node;
				return true;
			}
		});		 
		return field;
	}
	
	/**
	 * @return
	 */
	public NormalAnnotation getGraphWalkerClassAnnotation() {
		String source = getSource ();
		if(source==null) return null;
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(NormalAnnotation node) {
				annotation = node;
				return true;
			}
		});
		if (this.generateAnnotation) return annotation;
		return null;
	}

	public NormalAnnotation getGeneratedClassAnnotation() {
		String source = getGeneratedAnnotationSource ();
		if(source==null) return null;
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(new ASTVisitor() {
			public boolean visit(NormalAnnotation node) {
				annotation = node;
				return true;
			}
		});
	    return annotation;
		 
	}
	
	public static String getStaticVariableName () {
		return "MODEL_PATH";
	}
	
	/**
	 * @return
	 */
	private String getStaticVariable () {
		if (this.graphPath==null) return null;
		String source = "public class dummy { public final static Path " + getStaticVariableName() + "= Paths.get(\"" + this.graphPath.toString() +"\") }";
		return source;
	}
	
	/**
	 * @return
	 */
	private String getGeneratedAnnotationSource () {
		if (this.graphPath==null) return null;
		String fullpath  = ifile.getFullPath().removeFirstSegments(1).toString();
		String source = "@Generated(value = \"" + fullpath + "\") public class dummy() { }";
		return source;
	}
	
	/**
	 * @param methodname
	 * @return
	 */
	private String getBodyMethodSource (String methodname) {
		String source = "public class dummy {   public void print () { System.out.println(\"Executing:" + methodname +"\"); } }";
		return source;
	}
	
	
	/**
	 * @return
	 */
	private boolean startAttributForAnnotationNeeded () {
		if (getAnnotationStartElement() == null || getAnnotationStartElement().trim().length()==0) return false;
		return true;
	}
	
	/**
	 * @return
	 */
	public String getSource () {
		String source = null;
		if (startAttributForAnnotationNeeded())  {
		  source = "@GraphWalker(value = \"" + pathGenerator + "\", start = \""+ getAnnotationStartElement() +"\", groups = { \"" + getGroups() + "\" }) public class dummy() { }";
		} else {
		  source = "@GraphWalker(value = \"" + pathGenerator + "\") public class dummy() { }";
		}
		return source;
	}
	
	/**
	 * @return the pathGenerator
	 */
	public String getPathGenerator() {
		return pathGenerator;
	}


	/**
	 * @return the startElement
	 */
	public String getAnnotationStartElement() {
		return annotationStartElement;
	}


	/**
	 * @return the groups
	 */
	public String getGroups() {
		if (groups==null) return "default";
		if (groups.trim().length()==0) return "default";
		return groups;
	}

	/**
	 * @return the generateExecutionHook
	 */
	public boolean isGenerateExecutionHook() {
		return generateExecutionHook;
	}

	/**
	 * @return the generatePerformance
	 */
	public boolean isGeneratePerformance() {
		return generatePerformance;
	}

	/**
	 * @return the generateElementHook
	 */
	public boolean isGenerateElementHook() {
		return generateElementHook;
	}

	/**
	 * @return the appendInAnExistingTest
	 */
	public boolean isGenerateRunSmokeTest() {
		return generateRunSmokeTest;
	}

	/**
	 * @return the generateRunFunctionalTest
	 */
	public boolean isGenerateRunFunctionalTest() {
		return generateRunFunctionalTest;
	}

	/**
	 * @return the generateRunStabilityTest
	 */
	public boolean isGenerateRunStabilityTest() {
		return generateRunStabilityTest;
	}

	/**
	 * @return the generateRunModelBased
	 */
	public boolean isGenerateRunModelBased() {
		return generateRunModelBased;
	}

	/**
	 * @param annotationStartElement the annotationStartElement to set
	 */
	public void setAnnotationStartElement(String annotationStartElement) {
		this.annotationStartElement = annotationStartElement;
	}

	/**
	 * @return the targetVertex
	 */
	public String getTargetVertex() {
		return targetVertex;
	}

	/**
	 * @return the additionalContexts
	 */
	public List<IFile> getAdditionalContexts() {
		return additionalContexts;
	}

	/**
	 * @return the startElementForJunitTest
	 */
	public String getStartElementForJunitTest() {
		return startElementForJunitTest;
	}
	
	public void setPathGenerator(String pathGenerator) {
		this.pathGenerator = pathGenerator;
	}

	public void setGenerateExecutionHook(boolean generateExecutionHook) {
		this.generateExecutionHook = generateExecutionHook;
	}

	public void setGeneratePerformance(boolean generatePerformance) {
		this.generatePerformance = generatePerformance;
	}

	public void setGenerateElementHook(boolean generateElementHook) {
		this.generateElementHook = generateElementHook;
	}

	public void setGenerateRunSmokeTest(boolean generateRunSmokeTest) {
		this.generateRunSmokeTest = generateRunSmokeTest;
	}

	public void setGenerateRunFunctionalTest(boolean generateRunFunctionalTest) {
		this.generateRunFunctionalTest = generateRunFunctionalTest;
	}

	public void setGenerateRunStabilityTest(boolean generateRunStabilityTest) {
		this.generateRunStabilityTest = generateRunStabilityTest;
	}

	public void setGenerateRunModelBased(boolean generateRunModelBased) {
		this.generateRunModelBased = generateRunModelBased;
	}

	public void setTargetVertex(String targetVertex) {
		this.targetVertex = targetVertex;
	}

	public void setStartElementForJunitTest(String startElementForJunitTest) {
		this.startElementForJunitTest = startElementForJunitTest;
	}

}
