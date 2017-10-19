package org.gw4e.eclipse.facade;

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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Generated;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.generator.PathGeneratorBase;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.graphwalker.java.annotation.Model;
import org.gw4e.eclipse.builder.Location;
import org.gw4e.eclipse.conversion.ClassExtension;
import org.gw4e.eclipse.refactoring.Helper;
import org.gw4e.eclipse.wizard.convert.AbstractPostConversion;

public class JDTManager {


	public static List<IFile> findAvailableExecutionContextAncestors(IFile file) {
		List<IFile> files = new ArrayList<IFile>();
		
		IResource[] roots = { file.getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildGeneratedAnnotationValue(file);
		Pattern pattern = Pattern.compile(Helper.getGeneratedAnnotationRegExp(path));
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				files.add(file);
				return true;
			}
		};
		TextSearchEngine.create().search(scope, collector, pattern, new NullProgressMonitor());
		return files;
	}
	
	/**
	 * @param elt
	 * @return
	 * @throws JavaModelException
	 */
	public static String getFullyQualifiedName(IJavaElement elt) throws JavaModelException {
		IPackageFragmentRoot[] roots = elt.getJavaProject().getPackageFragmentRoots();
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].getPath().isPrefixOf(elt.getPath())) {
				IPath p = elt.getPath().makeRelativeTo(roots[i].getPath());
				return p.toString();
			}
		}
		return elt.getElementName();
	}

	
	public static String getJavaFullyQualifiedName(IJavaElement elt) throws JavaModelException {
		String name = getFullyQualifiedName(elt);
		name = name.split("\\.")[0];
		name = name.replaceAll("\\/", "\\.");
		return name;
	}
	
	/**
	 * Find the path of the graph model file that is the origin of the passed
	 * type The one which has been converted ..
	 * 
	 * @param project
	 * @param itype
	 * @return
	 * @throws JavaModelException
	 */
	public static IPath getGraphModelPath(IProject project, IType itype) throws JavaModelException {
		IPath path = findPathInGeneratedAnnotation(project, itype);
		if (path == null) {
			path = findPathInStaticField(project, itype);
		}
		return path;
	}

	/**
	 * @param project
	 * @param itype
	 * @return
	 * @throws JavaModelException
	 */
	private static IPath findPathInGeneratedAnnotation(IProject project, IType itype) throws JavaModelException {
		ICompilationUnit cu = itype.getCompilationUnit();
		List<IAnnotationBinding> annotations = resolveAnnotation(cu, Generated.class).getAnnotations();
		if ((annotations != null) && (annotations.size() > 0)) {
			IAnnotationBinding ab = annotations.get(0);
			IMemberValuePairBinding[] attributes = ab.getAllMemberValuePairs();
			for (int i = 0; i < attributes.length; i++) {
				IMemberValuePairBinding attribut = attributes[i];
				if (attribut.getName().equalsIgnoreCase("value")) {
					Object[] o = (Object[]) attribut.getValue();
					if (o != null && o.length > 0 && String.valueOf(o[0]).trim().length() > 0) {
						try {
							IPath p = ResourceManager.find(project, String.valueOf(o[0]).trim());
							return p;
						} catch (Exception e) {
							ResourceManager.logException(e);
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	public static ICompilationUnit[] getOrCreateGeneratedTestInterfaces(IProject project)
			throws CoreException, FileNotFoundException {
		
		project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		IPath folderForMain = project.getFullPath()
				.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(), true));
		IPath folderForTest = project.getFullPath()
				.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(), false));
		if (ResourceManager.getResource(folderForMain.toString()) == null
				&& ResourceManager.getResource(folderForTest.toString()) == null) {
			throw new IllegalStateException(
					"No target/generated-sources or target/generated-test-sources folders found");
		}

	 
	 
		// In main resource folder
		ICompilationUnit[] inMain = getExistingGeneratedTestInterfaces(project, true);
		// In test resource folder
		ICompilationUnit[] inTest = getExistingGeneratedTestInterfaces(project, false);
		ICompilationUnit[] ret = new ICompilationUnit[inMain.length+inTest.length];
		System.arraycopy(inMain, 0, ret, 0, inMain.length);
		System.arraycopy(inTest, 0, ret, inMain.length, inTest.length);
		return ret;
	}

	public static IPath guessPackageRootFragment(IProject project, boolean main)
			throws CoreException, FileNotFoundException {
		IPath folder = project.getFullPath()
				.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(), main));

		IResource interfaceFolder = ResourceManager.toResource(folder);
		List<IPath> paths = new ArrayList<IPath>();
		if (interfaceFolder != null) {
			interfaceFolder.accept(new IResourceVisitor() {
				@Override
				public boolean visit(IResource resource) throws CoreException {
					IJavaElement element = JavaCore.create(resource);
					if (element != null && element instanceof ICompilationUnit) {
						try {
							ICompilationUnit cu = (ICompilationUnit) element;
							CompilationUnit ast = parse(cu);
							ast.accept(new ASTVisitor() {
								public boolean visit(PackageDeclaration node) {
									PackageDeclaration decl = (PackageDeclaration) node;
									String pkgname = decl.getName().getFullyQualifiedName();
									int sizePath = pkgname.split("\\.").length;
									paths.add(resource.getParent().getFullPath().removeLastSegments(sizePath));
									return false;
								}
							});
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}
					return true;
				}
			});
		}
		if (paths.size() == 0)
			return null;
		return paths.get(0);
	}

	/**
	 * @param project
	 * @return
	 * @throws CoreException
	 * @throws FileNotFoundException
	 */
	public static ICompilationUnit[] getExistingGeneratedTestInterfaces(IProject project, boolean main)
			throws CoreException, FileNotFoundException {
		IPath folder = project.getFullPath()
				.append(GraphWalkerContextManager.getTargetFolderForTestInterface(project.getName(), main));
		List<ICompilationUnit> units = new ArrayList<ICompilationUnit>();
		IResource interfaceFolder = ResourceManager.toResource(folder);
		if (interfaceFolder != null) {
			interfaceFolder.accept(new IResourceVisitor() {
				@Override
				public boolean visit(IResource resource) throws CoreException {
					IJavaElement element = JavaCore.create(resource);
					if (element != null && element instanceof ICompilationUnit) {
						try {
							ICompilationUnit cu = (ICompilationUnit) element;
							IType interf = cu.findPrimaryType();
							if (interf != null && interf.isInterface()) {
								units.add(cu);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return true;
				}
			});
		}
		ICompilationUnit[] ret = new ICompilationUnit[units.size()];
		units.toArray(ret);
		return ret;
	}

	/**
	 * @param project
	 * @param itype
	 * @return
	 * @throws JavaModelException
	 */
	public static IPath findPathInModelAnnotation(IProject project, IType itype) throws JavaModelException {
		ICompilationUnit cu = itype.getCompilationUnit();
		List<IAnnotationBinding> annotations = resolveAnnotation(cu, Model.class).getAnnotations();
		if ((annotations != null) && (annotations.size() > 0)) {
			IAnnotationBinding ab = annotations.get(0);
			IMemberValuePairBinding[] attributes = ab.getAllMemberValuePairs();
			for (int i = 0; i < attributes.length; i++) {
				IMemberValuePairBinding attribut = attributes[i];
				if (attribut.getName().equalsIgnoreCase("value")) {
					Object[] o = (Object[]) attribut.getValue();
					if (o != null && o.length > 0 && String.valueOf(o[0]).trim().length() > 0) {
						try {
							IPath p = ResourceManager.find(project, String.valueOf(o[0]).trim());
							return p;
						} catch (Exception e) {
							ResourceManager.logException(e);
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	private static boolean isContext(ITypeBinding tbinding) {
		String executionContextClass = org.graphwalker.core.machine.ExecutionContext.class.getName();
		String contextClass = org.graphwalker.core.machine.Context.class.getName();
		while (tbinding != null) {
			String clazz = tbinding.getQualifiedName();
			if (executionContextClass.equals(clazz))
				return true;
			if (contextClass.equals(clazz))
				return true;
			ITypeBinding[] interfaces = tbinding.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				ITypeBinding interf = interfaces[i];
				if (contextClass.equals(interf.getQualifiedName()))
					return true;
			}
			tbinding = tbinding.getSuperclass();
		}
		return false;

	}

	public static AnnotationParsing findAnnotationParsingInGeneratedAnnotation(ICompilationUnit cu, String attribut) {
		AnnotationParsing annoParsing = resolveAnnotation(cu, Generated.class, attribut);
		return annoParsing;
	}

	/**
	 * @param cu
	 * @return
	 * @throws JavaModelException
	 */
	public static AnnotationParsing findAnnotationParsingInGraphWalkerAnnotation(ICompilationUnit cu, String attribut) {
		AnnotationParsing annoParsing = resolveAnnotation(cu, GraphWalker.class, attribut);
		return annoParsing;
	}

	public static AnnotationParsing findAnnotationParsingInModelAnnotation(ICompilationUnit cu, String attribut) {
		AnnotationParsing annoParsing = resolveAnnotation(cu, Model.class, attribut);
		return annoParsing;
	}

	/**
	 * @param cu
	 * @return
	 */
	public static String findPathGeneratorInGraphWalkerAnnotation(ICompilationUnit cu) {
		CompilationUnit ast = parse(cu);
		Map<String, String> ret = new HashMap<String, String>();
		ast.accept(new ASTVisitor() {
			public boolean visit(MemberValuePair node) {
				String name = node.getName().getFullyQualifiedName();
				if ("value".equals(name) && node.getParent() != null && node.getParent() instanceof NormalAnnotation) {
					IAnnotationBinding annoBinding = ((NormalAnnotation) node.getParent()).resolveAnnotationBinding();
					String qname = annoBinding.getAnnotationType().getQualifiedName();
					if (GraphWalker.class.getName().equals(qname)) {
						StringLiteral sl = (StringLiteral) node.getValue();
						ret.put("ret", sl.getLiteralValue());
					}
				}
				return true;
			}
		});
		return ret.get("ret");
	}

	/**
	 * @param project
	 * @param itype
	 * @return
	 */
	public static Set<String> findPathGeneratorClassInstanceCreation(IJavaProject project, IType itype) {
		Set<String> ret = new HashSet<String>();
		ICompilationUnit cu = itype.getCompilationUnit();
		CompilationUnit ast = parse(cu);

		ast.accept(new ASTVisitor() {
			public boolean visit(ClassInstanceCreation node) {
				Type createdType = node.getType();
				ITypeBinding binding = createdType.resolveBinding();
				IType boundType = (IType) binding.getJavaElement();
				try {
					IType execContextType = project.findType(PathGeneratorBase.class.getName());
					ITypeHierarchy th = boundType.newTypeHierarchy(null);
					if (th.contains(execContextType)) {
						int start = node.getStartPosition();
						int end = node.getLength();
						try {
							String code = cu.getSource().substring(start, start + end);
							ret.add(code);
						} catch (JavaModelException e) {
							ResourceManager.logException(e);
						}
					}
					;
				} catch (JavaModelException e) {
					ResourceManager.logException(e);
				}
				return true;
			}
		});
		return ret;
	}

	/**
	 * @param project
	 * @param itype
	 * @return
	 * @throws JavaModelException
	 */
	public static Map<String, List<String>> findSetPathGeneratorInvocation(IProject project, IType itype)
			throws JavaModelException {
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		ICompilationUnit cu = itype.getCompilationUnit();
		CompilationUnit ast = parse(cu);

		ast.accept(new ASTVisitor() {
			public boolean visit(MethodDeclaration node) {
				List<?> modifiers = (List<?>) node.getStructuralProperty(MethodDeclaration.MODIFIERS2_PROPERTY);
				for (Object modifier : modifiers) {
					if (modifier instanceof org.eclipse.jdt.core.dom.Annotation) {
						IAnnotationBinding annotationBinding = ((org.eclipse.jdt.core.dom.Annotation) modifier)
								.resolveAnnotationBinding();
						if (annotationBinding != null) {

							final String qualifiedName = annotationBinding.getAnnotationType().getQualifiedName();

							if ("org.junit.Test".equalsIgnoreCase(qualifiedName)) {
								Map<String, String> variables = new HashMap<String, String>();
								node.accept(new ASTVisitor() {
									public boolean visit(VariableDeclarationStatement node) {
										for (int i = 0; i < node.fragments().size(); ++i) {
											VariableDeclarationFragment frag = (VariableDeclarationFragment) node
													.fragments().get(i);
											if (isContext(node.getType().resolveBinding())) {
												Expression initializer = frag.getInitializer();
												JDTManager.ExpressionVisitor ev = new ExpressionVisitor(variables);
												initializer.accept(ev);
												if (ev.getValue() != null) {
													variables.put(frag.getName().getFullyQualifiedName(),
															ev.getValue());
												}
											}
										}
										return true;
									}

									public boolean visit(MethodInvocation node) {
										SimpleName simpleName = node.getName();

										IBinding bding = simpleName.resolveBinding();

										if (bding instanceof IMethodBinding) {
											IMethodBinding imb = (IMethodBinding) bding;
											if ("setPathGenerator".equalsIgnoreCase(imb.getName())) {
												ITypeBinding[] arguments = imb.getParameterTypes();
												if (arguments.length == 1) {
													if (isContext(imb.getDeclaringClass())
															&& PathGenerator.class.getName()
																	.equals(arguments[0].getQualifiedName())
															&& isContext(imb.getReturnType())) {
														int start = node.getStartPosition();
														int end = node.getLength();
														try {
															String code = cu.getSource().substring(start, start + end);
															// System.out.println(code);
														} catch (JavaModelException e) {
															ResourceManager.logException(e);
														}
														List args = node.arguments();
														Expression argumentExpression = (Expression) args.get(0);
														ITypeBinding typeBinding = argumentExpression
																.resolveTypeBinding();
														String parameterName = "";
														if (typeBinding != null) {
															parameterName = argumentExpression.toString();
															JDTManager.ExpressionVisitor ev = new ExpressionVisitor(
																	variables);
															Expression expression = node.getExpression();
															expression.accept(ev);
															if (ev.getValue() != null) {
																String contextClass = ev.getValue();
																List<String> generators = ret.get(contextClass);
																if (generators == null) {
																	generators = new ArrayList<String>();
																	ret.put(contextClass, generators);
																}

																if (!"null".equals(parameterName)
																		&& !generators.contains(parameterName)) {
																	generators.add(parameterName);
																}
															}
														}
													}
												}
											}
										}
										return true;
									}
								});
							}
						}
					}
				}
				return true;
			}
		});
		return ret;
	}

	static class ExpressionVisitor extends ASTVisitor {
		String value;
		Map<String, String> variables;

		ExpressionVisitor(Map<String, String> variables) {
			this.variables = variables;
		}

		public String getValue() {
			return value;
		}

		public boolean visit(ClassInstanceCreation cic) {
			try {
				this.value = cic.getType().resolveBinding().getQualifiedName().toString();
			} catch (Throwable e) {
			}
			return false;
		}

		public boolean visit(MethodInvocation node) {
			try {
				SimpleName simpleName = node.getName();
				IBinding bding = simpleName.resolveBinding();
				if (bding instanceof IMethodBinding) {
					IMethodBinding imb = (IMethodBinding) bding;
					if (isContext(imb.getReturnType())) {
						this.value = imb.getReturnType().getQualifiedName().toString();
					}
				}
			} catch (Throwable e) {
			}
			return false;
		}

		public boolean visit(SimpleName node) {
			try {
				this.value = variables.get(node.getFullyQualifiedName());
			} catch (Throwable e) {
			}
			return true;
		}

	}

	public static Set<String> findGeneratorFactoryParseInvocation(IProject project, IType itype)
			throws JavaModelException {
		Set<String> ret = new HashSet<String>();
		ICompilationUnit cu = itype.getCompilationUnit();
		CompilationUnit ast = parse(cu);

		ast.accept(new ASTVisitor() {
			public boolean visit(MethodInvocation node) {
				SimpleName simpleName = node.getName();
				IBinding bding = simpleName.resolveBinding();
				if (bding instanceof IMethodBinding) {
					IMethodBinding imb = (IMethodBinding) bding;
					if ("parse".equalsIgnoreCase(imb.getName())) {
						ITypeBinding[] arguments = imb.getParameterTypes();
						if (arguments.length == 1) {
							if (String.class.getName().equals(arguments[0].getQualifiedName())
									&& PathGenerator.class.getName().equals(imb.getReturnType().getQualifiedName())) {
								int start = node.getStartPosition();
								int end = node.getLength();
								try {
									String code = cu.getSource().substring(start, start + end);
									// System.out.println(code);
								} catch (JavaModelException e) {
									ResourceManager.logException(e);
								}
								List args = node.arguments();
								Expression argumentExpression = (Expression) args.get(0);
								ITypeBinding typeBinding = argumentExpression.resolveTypeBinding();
								if (typeBinding != null) {
									if (argumentExpression instanceof StringLiteral) {
										StringLiteral sl = (StringLiteral) argumentExpression;
										String lv = sl.getLiteralValue();
										ret.add(lv);
									}
								}
							}
						}
					}
				}

				return true;
			}
		});
		return ret;
	}

	/**
	 * @param project
	 * @param itype
	 * @return
	 * @throws JavaModelException
	 */
	private static IPath findPathInStaticField(IProject project, IType itype) throws JavaModelException {
		List<IPath> wrapper = new ArrayList<IPath>();
		ICompilationUnit cu = itype.getCompilationUnit();
		CompilationUnit ast = parse(cu);
		ast.accept(new ASTVisitor() {
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName simpleName = node.getName();
				IBinding bding = simpleName.resolveBinding();
				if (bding instanceof IVariableBinding) {
					IVariableBinding binding = (IVariableBinding) bding;
					String type = binding.getType().getBinaryName(); //
					String name = simpleName.getFullyQualifiedName();
					if ("MODEL_PATH".equals(name) && "java.nio.file.Path".equals(type)) {
						Expression expression = node.getInitializer();
						if (expression instanceof MethodInvocation) {
							MethodInvocation mi = (MethodInvocation) expression;
							if ("get".equals(mi.resolveMethodBinding().getName())
									&& "java.nio.file.Path".equals(mi.resolveTypeBinding().getBinaryName())) {
								StringLiteral sl = (StringLiteral) mi.arguments().get(0);
								String argument = sl.getLiteralValue();
								try {
									IPath p = ResourceManager.find(project, argument);
									wrapper.add(p);
								} catch (CoreException e) {
									ResourceManager.logException(e);
								}
							}
						}
					}
				}
				return true;
			}
		});
		if (wrapper.size() > 0)
			return wrapper.get(0);
		return null;
	}

	private static IType getClassesWithAnnotation(ICompilationUnit compilationUnit, Class annotationClass,
			String attributName, boolean valued) throws JavaModelException {
		List<IAnnotationBinding> annotations = resolveAnnotation(compilationUnit, annotationClass).getAnnotations();
		if ((annotations != null) && (annotations.size() > 0)) {
			IAnnotationBinding ab = annotations.get(0);
			IMemberValuePairBinding[] attributes = ab.getAllMemberValuePairs();
			for (int i = 0; i < attributes.length; i++) {
				IMemberValuePairBinding attribut = attributes[i];
				if (attribut.getName().equalsIgnoreCase(attributName)) {
					if (valued) {
						if (String.valueOf(attribut.getValue()).trim().length() > 0) {
							return compilationUnit.findPrimaryType();
						}
					} else {
						if (String.valueOf(attribut.getValue()).trim().length() == 0) {
							return compilationUnit.findPrimaryType();
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param projectName
	 * @return
	 * @throws JavaModelException
	 */
	private static List<IType> findClassesWithAnnotation(String projectName, Class annotationClass, String attributName,
			boolean valued) throws JavaModelException {
		List<IType> classList = new ArrayList<IType>();
		IProject project = ResourceManager.getProject(projectName);
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment packageFragment : packages) {
			for (final ICompilationUnit compilationUnit : packageFragment.getCompilationUnits()) {
				if (compilationUnit.exists()) {
					IType type = getClassesWithAnnotation(compilationUnit, annotationClass, attributName, valued);
					if (type != null)
						classList.add(type);
				}
			}
		}
		return classList;
	}

	/**
	 * @param compilationUnit
	 * @param progressMonitor
	 * @return
	 * @throws JavaModelException
	 */

	public static CompilationUnit parse(final ICompilationUnit cu) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(cu);
		parser.setResolveBindings(true);
		parser.setEnvironment(null, null, null, true);
		parser.setBindingsRecovery(true);
		final CompilationUnit ast = (CompilationUnit) parser.createAST(new NullProgressMonitor());

		return ast;
	}

	public static boolean hasStartableGraphWalkerAnnotation(Object receiver) {
		ICompilationUnit cu = null;
		if (receiver instanceof ICompilationUnit) {
			cu = (ICompilationUnit) receiver;
			List<IAnnotationBinding> annotations = resolveAnnotation(cu, GraphWalker.class).getAnnotations();
			if ((annotations != null) && annotations.size() > 0) {
				IAnnotationBinding ab = annotations.get(0);
				IMemberValuePairBinding[] attributes = ab.getAllMemberValuePairs();
				for (int i = 0; i < attributes.length; i++) {
					IMemberValuePairBinding attribut = attributes[i];
					if (attribut.getName().toLowerCase().equalsIgnoreCase("start")) {

						if (attribut.getValue() != null && String.valueOf(attribut.getValue()).trim().length() > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param testInterface
	 * @return
	 * @throws JavaModelException
	 */
	public static boolean isGraphWalkerExecutionContextClass(ICompilationUnit unit) throws JavaModelException {
		IType[] types = unit.getAllTypes();

		if (types == null || types.length == 0) {
			ResourceManager.logInfo(unit.getJavaProject().getProject().getName(),
					"getAllTypes return null" + unit.getPath());
			return false;
		}
		IType execContextType = unit.getJavaProject().findType(ExecutionContext.class.getName());

		for (int i = 0; i < types.length; i++) {
			IType type = types[i];
			String typeNname = type.getFullyQualifiedName();
			String compilationUnitName = JDTManager.getJavaFullyQualifiedName(unit);
			if (typeNname.equals(compilationUnitName)) {
				try {
					ITypeHierarchy th = types[0].newTypeHierarchy(new NullProgressMonitor());
					return th.contains(execContextType);
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
			}
		}
		return false;
	}

	/**
	 * @param receiver
	 * @return
	 * @throws JavaModelException
	 */
	public static String getGW4EGeneratedAnnotationValue(Object receiver, String attribut) {
		ICompilationUnit cu = null;
		if (receiver instanceof ICompilationUnit) {
			cu = (ICompilationUnit) receiver;
			List<IAnnotationBinding> annotations = resolveAnnotation(cu, Generated.class).getAnnotations();
			if ((annotations != null) && annotations.size() > 0) {
				IAnnotationBinding generated = annotations.get(0);
				IMemberValuePairBinding[] vpb = generated.getAllMemberValuePairs();
				for (int i = 0; i < vpb.length; i++) {
					IMemberValuePairBinding pair = vpb[i];
					if (attribut.equals(pair.getName())) {
						Object[] value = (Object[]) pair.getValue();
						if (value == null || value.length == 0)
							return null;
						return String.valueOf(value[0]);
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param receiver
	 * @return
	 * @throws JavaModelException
	 */
	public static boolean hasGraphWalkerAnnotation(Object receiver) {
		ICompilationUnit cu = null;
		if (receiver instanceof ICompilationUnit) {
			cu = (ICompilationUnit) receiver;
			List<IAnnotationBinding> annotations = resolveAnnotation(cu, GraphWalker.class).getAnnotations();
			return ((annotations != null) && annotations.size() > 0);
		}
		return false;
	}

	public static AnnotationParsing resolveAnnotation(ICompilationUnit cu, Class inputClass) {
		return resolveAnnotation(cu, inputClass, "value");
	}

	/**
	 * @param cu
	 * @param annotationName
	 * @return
	 * @throws JavaModelException
	 */
	public static AnnotationParsing resolveAnnotation(ICompilationUnit cu, Class inputClass, final String attribut) {

		CompilationUnit ast = parse(cu);
		AnnotationParsing ret = new AnnotationParsing();
		String annotationName = inputClass.getName();
		ast.accept(new ASTVisitor() {
			public boolean visit(MemberValuePair node) {
				String name = node.getName().getFullyQualifiedName();
				if (attribut.equals(name) && node.getParent() != null && node.getParent() instanceof NormalAnnotation) {
					IAnnotationBinding annoBinding = ((NormalAnnotation) node.getParent()).resolveAnnotationBinding();
					String qname = annoBinding.getAnnotationType().getQualifiedName();
					if (inputClass.getName().equals(qname)) {
						int start = node.getStartPosition();
						int end = start + node.getLength();
						int lineNumber = ast.getLineNumber(start);
						Location location = new Location(lineNumber, start, end);
						ret.setLocation(location);
					}
				}
				return true;
			}

			public final boolean visit(final TypeDeclaration node) {
				List<?> modifiers = (List<?>) node.getStructuralProperty(TypeDeclaration.MODIFIERS2_PROPERTY);
				for (Object modifier : modifiers) {
					if (modifier instanceof org.eclipse.jdt.core.dom.Annotation) {
						IAnnotationBinding annotationBinding = ((org.eclipse.jdt.core.dom.Annotation) modifier)
								.resolveAnnotationBinding();

						if (annotationBinding != null) {
							final String qualifiedName = annotationBinding.getAnnotationType().getQualifiedName();
							if (annotationName.equalsIgnoreCase(qualifiedName))
								ret.add(annotationBinding);

						}
					}
				}
				return true;
			}
		});
		return ret;
	}

	/**
	 * @param projectName
	 * @return
	 * @throws JavaModelException
	 */
	public static List<IType> getStartableGraphWalkerClasses(String projectName) {
		try {

			return findClassesWithAnnotation(projectName, GraphWalker.class, "start", true);
		} catch (Exception e) {
			ResourceManager.logException(e);
			return new ArrayList<IType>();
		}
	}

	/**
	 * @param projectName
	 * @return
	 */
	public static List<IType> getOrphanGraphWalkerClasses(IType type, boolean hint) {
		try {
			IProject project = ResourceManager.getResource(type.getPath().toString()).getProject();
			String projectName = project.getName();

			List<IType> all = findClassesWithAnnotation(projectName, GraphWalker.class, "value", true);
			if (hint) {
				all = GraphWalkerFacade.getSharedContexts(project, type, all);
				all.remove(type);
			}

			Collections.sort(all, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					IType type1 = (IType) o1;
					IType type2 = (IType) o2;
					return type1.getFullyQualifiedName().compareTo(type2.getFullyQualifiedName());
				}
			});
			return all;
		} catch (Exception e) {
			ResourceManager.logException(e);
			return new ArrayList<IType>();
		}
	}

	/**
	 * Generate a test implementation if it does not exist
	 * 
	 * @param implementationFolder
	 * @param implementationFragmentRoot
	 * @param targetPkg
	 * @param interfaceCompUnit
	 * @param monitor
	 * @throws CoreException
	 */
	public static IFile generateTestImplementation(TestResourceGeneration provider, IProgressMonitor monitor)
			throws Exception {

		IFile ifile = provider.toIFile();

		if (ifile.exists()) {
			JDTManager.rename(ifile, monitor);
			ifile.delete(true, monitor);
		}
		if (ifile.exists())
			return null;

		NewExecutionContextClassWizardPageRunner execRunner = new NewExecutionContextClassWizardPageRunner(provider,
				monitor);
		Display.getDefault().syncExec(execRunner);
		IPath path = execRunner.getType().getPath();
		IFile createdFile = (IFile) ResourceManager.getResource(path.toString());
		return createdFile;
	}

	/**
	 * Save the AST int he Compilation Unit
	 * 
	 * @param testInterface
	 * @param rewrite
	 * @throws CoreException
	 */
	public static void save(CompilationUnit unit, ASTRewrite rewrite) throws CoreException {

		ITextFileBufferManager bufferManager = FileBuffers.getTextFileBufferManager();
		IPath path = unit.getJavaElement().getPath();
		try {
			bufferManager.connect(path, null);
			ITextFileBuffer textFileBuffer = bufferManager.getTextFileBuffer(path);
			IDocument document = textFileBuffer.getDocument();
			TextEdit edit = rewrite.rewriteAST(document, null);
			edit.apply(document);
			textFileBuffer.commit(null /* ProgressMonitor */, true /* Overwrite */);
		} catch (Exception e) {
			ResourceManager.logException(e);
		} finally {
			// disconnect the path
			bufferManager.disconnect(path, null);
		}
	}

	/**
	 * @param cu
	 * @param rewrite
	 * @param ast
	 * @param pkgDeclaration
	 */
	private static void addPackageDeclaration(CompilationUnit cu, ASTRewrite rewrite, AST ast,
			String[] pkgDeclaration) {
		PackageDeclaration packageDeclaration = ast.newPackageDeclaration();
		Name name = ast.newName(pkgDeclaration);
		packageDeclaration.setName(name);
		rewrite.set(cu, CompilationUnit.PACKAGE_PROPERTY, packageDeclaration, null);

	}

	/**
	 * @param file
	 * @param cu
	 * @param rewrite
	 * @param ast
	 * @throws JavaModelException
	 */
	private static void addPackageDeclarationIfNeeded(IFile file, CompilationUnit cu, ASTRewrite rewrite, AST ast)
			throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(file.getProject());
		IClasspathEntry[] entries = javaProject.getRawClasspath();
		for (int i = 0; i < entries.length; i++) {
			IClasspathEntry entry = entries[i];
			if (IClasspathEntry.CPE_SOURCE == entry.getEntryKind()) {
				if (entry.getPath().isPrefixOf(file.getFullPath())) {
					IPath path = file.getFullPath().makeRelativeTo(entry.getPath());
					path = path.removeLastSegments(1); // remove the file
														// name from the
														// path
					if (path.segmentCount() == 0)
						return; // no package declaration needs to be done
					int length = path.segmentCount();
					String[] pkgDeclaration = new String[length];
					for (int j = 0; j < length; j++) {
						pkgDeclaration[j] = path.segment(j);
					}
					addPackageDeclaration(cu, rewrite, ast, pkgDeclaration);
					return;
				}
			}

		}
	}

	/**
	 * Rename a class name into another name
	 * 
	 * @param file
	 * @param oldClassname
	 * @param newName
	 * @param monitor
	 * @return
	 * @throws MalformedTreeException
	 * @throws BadLocationException
	 * @throws CoreException
	 */
	public static IFile renameClass(IFile file, String oldClassname, String newName, IProgressMonitor monitor)
			throws MalformedTreeException, BadLocationException, CoreException {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				try {

					ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
					CompilationUnit cu = parse(unit);
					AST ast = cu.getAST();
					ASTRewrite rewrite = ASTRewrite.create(ast);

					String classname = file.getName();
					classname = classname.substring(0, classname.indexOf("."));
					final String clazz = classname;
					ASTVisitor visitor = new ASTVisitor() {
						public boolean visit(SimpleName node) {
							String s = node.getIdentifier();
							if (oldClassname.equalsIgnoreCase(s)) {
								rewrite.replace(node, ast.newSimpleName(newName), null);
							}
							return true;
						}
					};
					cu.accept(visitor);

					addPackageDeclarationIfNeeded(file, cu, rewrite, ast);
					file.refreshLocal(IResource.DEPTH_ZERO, monitor);
					cu = parse(JavaCore.createCompilationUnitFrom(file));
					save(cu, rewrite);
				} catch (Exception e) {
					ResourceManager.logException(e);
				}
			}
		});
		return file;
	}

	/**
	 * @param ifile
	 * @param monitor
	 * @throws JavaModelException
	 */
	public static void rename(IFile ifile, IProgressMonitor monitor) throws JavaModelException {
		if (!ifile.getName().endsWith(".java"))
			return;
		ICompilationUnit unit = JavaCore.createCompilationUnitFrom(ifile);
		String name = ifile.getName();
		String[] segments = name.split("\\.");

		name = segments[0] + System.currentTimeMillis() + "." + segments[1];
		unit.rename(name, false, monitor);
		unit.close();
	}

	/**
	 * @param file
	 * @param info
	 * @param monitor
	 * @throws MalformedTreeException
	 * @throws BadLocationException
	 * @throws CoreException
	 */
	@SuppressWarnings("deprecation")
	public static void enrichClass(IFile file, TestResourceGeneration info, IProgressMonitor monitor)
			throws MalformedTreeException, BadLocationException, CoreException {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {

			String source = compilationUnit.getSource();

			Document document = new Document(source);
			compilationUnit.becomeWorkingCopy(new SubProgressMonitor(monitor, 1));
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(compilationUnit);
			parser.setResolveBindings(true);
			CompilationUnit astRoot = (CompilationUnit) parser.createAST(new SubProgressMonitor(monitor, 1));
			astRoot.recordModifications();
			final AST ast = astRoot.getAST();

			boolean[] hasGraphWalkerAnnotation = new boolean[1];
			boolean[] hasGeneratedAnnotation = new boolean[1];
			boolean[] hasStaticField = new boolean[1];
			// Add a System.out.println("xxx") statement to the methods
			astRoot.accept(new ASTVisitor() {
				@SuppressWarnings("unchecked")
				@Override
				public boolean visit(MethodDeclaration node) {
					if (info.isAppendSource())
						return false; // dont touch the existing code if we are
										// appending new source code.
					ExpressionStatement md = info.getClassExtension().getBodyMethod(node.getName().toString());
					ASTNode copiedNode = ASTNode.copySubtree(node.getAST(), md);
					node.setBody(ast.newBlock());
					node.getBody().statements().add(copiedNode);
					return false;
				}

				@Override
				public boolean visit(NormalAnnotation node) {
					if (!hasGeneratedAnnotation[0])
						hasGeneratedAnnotation[0] = (node.getTypeName().getFullyQualifiedName()
								.indexOf(Generated.class.getSimpleName()) != -1);
					if (!hasGraphWalkerAnnotation[0])
						hasGraphWalkerAnnotation[0] = (node.getTypeName().getFullyQualifiedName()
								.indexOf(GraphWalker.class.getSimpleName()) != -1);
					return true;
				}

				@Override
				public boolean visit(FieldDeclaration node) {
					if (!hasStaticField[0]) {
						VariableDeclarationFragment fragment = (VariableDeclarationFragment) node.fragments().get(0);
						hasStaticField[0] = fragment.getName().toString()
								.equals(ClassExtension.getStaticVariableName());
					}
					return true;
				}

			});

			final ImportRewrite importRewrite = ImportRewrite.create(astRoot, true);

			astRoot.accept(new ASTVisitor() {
				@SuppressWarnings("unchecked")
				@Override
				public boolean visit(TypeDeclaration node) {
					ITypeBinding binding = node.resolveBinding();

					// Add import for class annotation
					String[] imports = info.getClassExtension().getImports();
					List<String> allImports = new ArrayList<String>();
					for (int i = 0; i < imports.length; i++) {
						if (allImports.contains(imports[i]))
							continue;
						allImports.add(imports[i]);
					}
					// Add import for methods annotation & code
					for (int i = 0; i < info.getMethods().length; i++) {
						imports = info.getMethods()[i].getImports();
						for (int j = 0; j < imports.length; j++) {
							if (allImports.contains(imports[j]))
								continue;
							allImports.add(imports[j]);
						}
					}
					// Add import for methods annotation & code
					for (int i = 0; i < info.getMethods().length; i++) {
						imports = info.getMethods()[i].getStaticImports();
						for (int j = 0; j < imports.length; j++) {
							if (allImports.contains(imports[j]))
								continue;
							allImports.add(imports[j]);
						}
					}

					ASTNode copiedNode = null;
					Collections.sort(allImports);
					Iterator<String> iterator = allImports.iterator();
					while (iterator.hasNext()) {
						String imp = (String) iterator.next();
						importRewrite.addImport(imp);
					}
					// Add Generated annotation
					if (!hasGeneratedAnnotation[0]) {
						NormalAnnotation annotation = info.getClassExtension().getGeneratedClassAnnotation();
						if (annotation != null) {
							copiedNode = ASTNode.copySubtree(node.getAST(), annotation);
							node.modifiers().add(0, copiedNode);
						}
					}
					// Add graphwalker annotation
					if (!hasGraphWalkerAnnotation[0]) {
						NormalAnnotation annotation = info.getClassExtension().getGraphWalkerClassAnnotation();
						if (annotation != null) {
							copiedNode = ASTNode.copySubtree(node.getAST(), annotation);
							node.modifiers().add(1, copiedNode);
						}
					}

					if (!hasStaticField[0]) {
						FieldDeclaration field = info.getClassExtension().getField();
						if (field != null) {
							copiedNode = ASTNode.copySubtree(node.getAST(), field);
							node.bodyDeclarations().add(0, copiedNode);
						}
					}

					List<IFile> additionals = info.getAdditionalContexts();
					List<String> temp = new ArrayList<String>();
					for (IFile iFile : additionals) {
						try {
							AbstractPostConversion converter = GraphWalkerContextManager
									.getDefaultGraphConversion(iFile, false);
							temp.add(converter.getQualifiedNameForImplementation());

						} catch (CoreException e) {
							ResourceManager.logException(e);
						}
					}
					String[] adds = new String[temp.size()];
					temp.toArray(adds);
					// add helper hook & test methods
					for (int i = 0; i < info.getMethods().length; i++) {
						try {
							MethodDeclaration methodDeclaration = info.getMethods()[i].getMethodDeclaration(adds,
									node.getName().toString());
							if (methodDeclaration == null)
								continue;

							copiedNode = ASTNode.copySubtree(node.getAST(), methodDeclaration);
							node.bodyDeclarations().add(copiedNode);
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}

					return super.visit(node);
				}
			});

			TextEdit rewrite = astRoot.rewrite(document, compilationUnit.getJavaProject().getOptions(true));
			rewrite.apply(document);

			TextEdit rewriteImports = importRewrite.rewriteImports(new SubProgressMonitor(monitor, 1));
			rewriteImports.apply(document);

			String newSource = document.get();

			compilationUnit.getBuffer().setContents(newSource);

			compilationUnit.reconcile(ICompilationUnit.NO_AST, false, null, new SubProgressMonitor(monitor, 1));
			compilationUnit.commitWorkingCopy(false, new SubProgressMonitor(monitor, 1));
		} finally {
			compilationUnit.discardWorkingCopy();
			monitor.done();
		}

	}

	/**
	 * @param file
	 * @param info
	 * @param monitor
	 * @throws MalformedTreeException
	 * @throws BadLocationException
	 * @throws CoreException
	 */
	@SuppressWarnings("deprecation")
	public static void addGeneratedAnnotation(IFile file, IFile graphFile, IProgressMonitor monitor)
			throws MalformedTreeException, BadLocationException, CoreException {
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(file);
		try {

			String source = compilationUnit.getSource();
			Document document = new Document(source);
			compilationUnit.becomeWorkingCopy(new SubProgressMonitor(monitor, 1));
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(compilationUnit);
			parser.setResolveBindings(true);
			CompilationUnit astRoot = (CompilationUnit) parser.createAST(new SubProgressMonitor(monitor, 1));
			astRoot.recordModifications();

			final ImportRewrite importRewrite = ImportRewrite.create(astRoot, true);
			importRewrite.addImport("javax.annotation.Generated");

			astRoot.accept(new ASTVisitor() {
				@SuppressWarnings("unchecked")
				@Override
				public boolean visit(TypeDeclaration node) {
					ASTNode copiedNode = null;
					// Add Generated annotation
					ClassExtension ce;
					try {
						ce = new ClassExtension(false, false, false, false, false, false, "", "", null, false, false,
								"", "", "", graphFile);
						NormalAnnotation annotation = ce.getGeneratedClassAnnotation();
						if (annotation != null) {
							copiedNode = ASTNode.copySubtree(node.getAST(), annotation);
							node.modifiers().add(0, copiedNode);
						}
					} catch (JavaModelException e) {
						ResourceManager.logException(e);
					}

					return super.visit(node);
				}
			});

			TextEdit rewrite = astRoot.rewrite(document, compilationUnit.getJavaProject().getOptions(true));
			rewrite.apply(document);

			TextEdit rewriteImports = importRewrite.rewriteImports(new SubProgressMonitor(monitor, 1));
			rewriteImports.apply(document);

			String newSource = document.get();
			compilationUnit.getBuffer().setContents(newSource);

			compilationUnit.reconcile(ICompilationUnit.NO_AST, false, null, new SubProgressMonitor(monitor, 1));
			compilationUnit.commitWorkingCopy(false, new SubProgressMonitor(monitor, 1));
		} finally {
			compilationUnit.discardWorkingCopy();
			monitor.done();
		}
		// WorkbenchFacade.JDTManager.reorganizeImport(compilationUnit);
	}

	/**
	 * @param project
	 * @param targetSite
	 * @throws CoreException
	 */
	private static void organizeImports(final ICompilationUnit cu, final IWorkbenchSite targetSite)
			throws CoreException {
		Runnable job = new Runnable() {
			@Override
			public void run() {
				OrganizeImportsAction org = new OrganizeImportsAction(targetSite);
				org.run(cu);
				try {
					if (cu.isWorkingCopy())
						cu.commitWorkingCopy(true, new NullProgressMonitor ());
				} catch (JavaModelException e) {
					ResourceManager.logException(e);
				}
				try {
					cu.getCorrespondingResource().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor ());
				} catch (Exception e) {
				}
			}
		};
		Display.getDefault().syncExec(job);
	}

	/**
	 * @param project
	 */
	public static void reorganizeImport(final ICompilationUnit cu) {

		Display.getDefault().syncExec(() -> {
			try {
				IWorkbenchWindow iww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (iww == null)
					return;
				IPartService partService = iww.getPartService();
				if (partService == null)
					return;
				IWorkbenchPart wp = partService.getActivePart();
				if (wp == null)
					return;
				IWorkbenchPartSite targetSite = wp.getSite();
				if (targetSite == null)
					return;
				organizeImports(cu, targetSite);
			} catch (Exception e) {
				ResourceManager.logException(e);
			}
		});
	}

	 

	/**
	 * Format a Unit Source Code
	 * 
	 * @param testInterface
	 * @param monitor
	 * @throws CoreException 
	 */
	@SuppressWarnings("unchecked")
	public static void formatUnitSourceCode(IFile file, IProgressMonitor monitor) throws CoreException {
		@SuppressWarnings("rawtypes")
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
		subMonitor.split(50);
		ICompilationUnit workingCopy = unit.getWorkingCopy(monitor);

		Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);

		options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS,
				DefaultCodeFormatterConstants.createAlignmentValue(true,
						DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
						DefaultCodeFormatterConstants.INDENT_ON_COLUMN));

		final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);
		ISourceRange range = unit.getSourceRange();
		TextEdit formatEdit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, unit.getSource(),
				range.getOffset(), range.getLength(), 0, null);
		subMonitor.split(30);
		if (formatEdit != null /* && formatEdit.hasChildren()*/) {
			workingCopy.applyTextEdit(formatEdit, monitor);
			workingCopy.reconcile(ICompilationUnit.NO_AST, false, null, null);
			workingCopy.commitWorkingCopy(true, null);
			workingCopy.discardWorkingCopy();
		}
		file.refreshLocal(IResource.DEPTH_INFINITE, subMonitor);
		subMonitor.split(20);
	}

	/**
	 * Return a java model
	 * 
	 * @return
	 */
	public static IJavaModel getJavaModel() {
		return JavaCore.create(ResourceManager.getWorkspaceRoot());
	}

	/**
	 * 
	 * Open a default editor
	 * 
	 * @param resource
	 * @throws FileNotFoundException
	 */
	public static void openDefaultEditor(final IResource resource) {
		openEditor(resource, "org.eclipse.ui.DefaultTextEditor", null);
	}

	/**
	 * 
	 * Open an editor
	 * 
	 * @param resource
	 * @throws FileNotFoundException
	 */
	public static void openEditor(final IResource resource, IWorkbenchWindow aww) {
		openEditor(resource, null, aww);
	}

	/**
	 * Open an editor
	 * 
	 * @param resource
	 * @param editorId
	 */
	public static void openEditor(final IResource resource, String editorId, IWorkbenchWindow aww) {
		openFileEditor(resource, editorId, aww);
	}

	/**
	 * Open an editor
	 * 
	 * @param resource
	 * @param extension
	 * @param editor
	 * @throws FileNotFoundException
	 */
	public static void openFileEditor(final IResource resource, String editorId, IWorkbenchWindow window) {
		try {
			Display.getDefault().syncExec(() -> {
				IWorkbenchWindow[] ww = new IWorkbenchWindow[] { window };
				if (resource == null)
					return;
				if (resource.getType() != IResource.FILE) {
					return;
				}
				IFile file = (IFile) resource;
				if (!file.exists())
					return;
				if (ww[0] == null) {
					ww[0] = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				}

				if (ww[0] == null) {
					return;
				}
				final IWorkbenchPage activePage = ww[0].getActivePage();
				try {
					if (editorId == null) {
						if (file.getFileExtension().equals("java")) {
							IDE.openEditor(activePage, file, JavaUI.ID_CU_EDITOR, true);
						} else {
							IDE.openEditor(activePage, file, "org.eclipse.ui.DefaultTextEditor", true);
						}
					} else {
						IDE.openEditor(activePage, file, editorId, true);
					}
				} catch (Throwable e) {
					ResourceManager.logException(e);
				}
				BasicNewResourceWizard.selectAndReveal(resource, activePage.getWorkbenchWindow());
			});
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	/**
	 * Adapt the passed object to an IProject
	 * 
	 * @param receiver
	 * @return
	 */
	public static IProject toJavaProject(Object receiver) {
		IProject project = null;
		if (receiver instanceof IProject) {
			project = (IProject) receiver;
		}
		if (receiver instanceof IJavaProject) {
			project = ((IJavaProject) receiver).getProject();
		} else if (receiver instanceof IAdaptable) {
			project = (IProject) ((IAdaptable) receiver).getAdapter(IProject.class);
		}
		return project;
	}

	public static IPackageFragment getPackageFragment(IFile file) throws JavaModelException {
		IPackageFragmentRoot root1 = JDTManager.findPackageFragmentRoot(file.getProject(), file.getFullPath());
		IPath path = file.getParent().getFullPath().makeRelativeTo(root1.getPath());
		IPackageFragment pf =root1.getPackageFragment(path.toString().replace("/", "."));
		return pf;
	}
	
	/**
	 * Return a package fragment with the passed path
	 * 
	 * @param project
	 * @param path
	 * @return
	 * @throws JavaModelException
	 */
	public static IPackageFragmentRoot getPackageFragmentRoot(IProject project, IPath path) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].getPath().equals(path))
				return roots[i];
		}
		return null;
	}

	public static IPackageFragmentRoot findPackageFragmentRoot(IProject project, IPath path) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].getPath().isPrefixOf(path))
				return roots[i];
		}
		return null;
	}

	public static IPath removePackageFragmentRoot(IProject project, IPath path) throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].getPath().isPrefixOf(path))
				return path.makeRelativeTo(roots[i].getPath());
		}
		return null;
	}

	public static boolean validateClassName(String name) {
		String temp = name + ".java";
		IStatus status = JavaConventions.validateCompilationUnitName(temp, JavaCore.VERSION_1_8, JavaCore.VERSION_1_8);
		boolean b = (status.getCode() == IStatus.OK);
		return b;
	}
}
