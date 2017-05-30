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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;

public class ModelBasedMethodExtension extends MethodExtension {
	IFile graphfile;
	String source;
	public ModelBasedMethodExtension(String classname,List<String> additionalContexts,IFile graphfile) {
		super(classname,additionalContexts,"runModelBasedTest",null);
		this.graphfile=graphfile;
	}

	@Override
	public String getSource( String[] additionalContext,String value) {
		if (source==null) {
			try {
				source = GraphWalkerFacade.convert(ResourceManager.getAbsolutePath(graphfile), "dummy.java");
			} catch (Exception e) {
				source="a";
				ResourceManager.logException(e); 
			}
		}
		return source;
	}
	
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.conversion.MethodExtension#getMethodDeclaration(java.lang.String)
	 */
	public MethodDeclaration getMethodDeclaration (String[] additionalContext,String value) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(this.getSource(additionalContext,value).toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		final  CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		 
		cu.accept(new ASTVisitor() {
			public boolean visit(MethodDeclaration  node) {
				String methodName = node.getName().getFullyQualifiedName();
				if ("run".equalsIgnoreCase(methodName)) {
					final AST ast = cu.getAST();
					node.modifiers().clear();
					
					NormalAnnotation annotation = ast.newNormalAnnotation();
			        Name annotationTypeName = ast.newName("Test");
			        annotation.setTypeName(annotationTypeName);
			        node.modifiers().add(annotation);

					node.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
					node.setName(ast.newSimpleName(getName()));
					
					node.accept(new ASTVisitor() {
						public boolean visit(VariableDeclarationFragment node) {
							String variable = node.getName().getFullyQualifiedName();
							if ("context".equalsIgnoreCase(variable)) {
								node.setInitializer(ast.newThisExpression());
							}
							return true;
						}
					});
					
					methodDeclaration = node;
				}
				return false;
			}
		});
		 
		return methodDeclaration;
	}

	@Override
	public String[] getImportedClasses() {
		return new String [] {
		  org.graphwalker.core.condition.EdgeCoverage.class.getName(),
		  org.graphwalker.core.generator.RandomPath.class.getName(),
		  org.graphwalker.core.machine.Context.class.getName(),
		  org.graphwalker.core.machine.Machine.class.getName(),
		  org.graphwalker.core.machine.SimpleMachine.class.getName(),
		  org.graphwalker.core.model.Action.class.getName(),
		  org.graphwalker.core.model.Edge.class.getName(),
		  org.graphwalker.core.model.Guard.class.getName(),
		  org.graphwalker.core.model.Model.class.getName(),
		  org.graphwalker.core.model.Vertex.class.getName(),
		  org.junit.Test.class.getName()				
		};
	}

}
