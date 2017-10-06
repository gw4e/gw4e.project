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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.condition.VertexCoverage;
import org.graphwalker.core.event.EventType;
import org.graphwalker.core.event.Observer;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.model.Action;
import org.graphwalker.core.model.Builder;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Edge.RuntimeEdge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.graphwalker.core.model.Requirement;
import org.graphwalker.core.model.Vertex;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.graphwalker.dsl.antlr.DslException;
import org.graphwalker.dsl.antlr.generator.GeneratorFactory;
import org.graphwalker.io.factory.ContextFactory;
import org.graphwalker.io.factory.ContextFactoryException;
import org.graphwalker.io.factory.dot.DotContextFactory;
import org.graphwalker.io.factory.java.JavaContextFactory;
import org.graphwalker.io.factory.json.JsonContextFactory;
import org.graphwalker.io.factory.yed.YEdContextFactory;
import org.graphwalker.java.source.CodeGenerator;
import org.graphwalker.java.source.SourceFile;
import org.graphwalker.java.source.cache.CacheEntry;
import org.graphwalker.java.source.cache.SimpleCache;
import org.graphwalker.java.test.IsolatedClassLoader;
import org.graphwalker.java.test.Result;
import org.graphwalker.java.test.TestExecutionException;
import org.graphwalker.java.test.TestExecutor;
import org.graphwalker.modelchecker.ContextsChecker;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.wizard.convert.OfflineContext;

import com.google.gson.JsonSyntaxException;

/**
 * A facade to GraphWalker apis
 *
 */
public class GraphWalkerFacade {
	/**
	 * Retrieve the graph models contained in the container
	 * 
	 * @param container
	 * @param models
	 * @throws CoreException
	 */
	public static void getGraphModels(IContainer container, List<IFile> models) throws CoreException {
		if (!container.exists())
			return;
		IResource[] members = container.members();
		for (IResource member : members) {
			if (member instanceof IContainer)
				getGraphModels((IContainer) member, models);
			else if (member instanceof IFile) {
				IFile file = (IFile) member;
				if (PreferenceManager.isGraphModelFile(file))
					models.add(file);
				if (PreferenceManager.isGW3ModelFile(file))
					models.add(file);
				if (PreferenceManager.isJSONModelFile(file))
					models.add(file);

			}
		}
	}

	/**
	 * @param sharedName
	 * @param container
	 * @return
	 * @throws JsonSyntaxException
	 * @throws ContextFactoryException
	 * @throws IOException
	 * @throws CoreException
	 */
	public static List<IFile> getSharedGraphModels(String sharedName, IContainer container)
			throws JsonSyntaxException, ContextFactoryException, IOException, CoreException {
		List<IFile> ret = new ArrayList<IFile>();
		List<IFile> files = new ArrayList<IFile>();
		getGraphModels(container, files);
		for (IFile iFile : files) {
			File f = ResourceManager.toFile(iFile.getFullPath());
			Context c = GraphWalkerFacade.getContext(f.getAbsolutePath());
			Set<String> names = c.getModel().getSharedStates();
			if (names.contains(sharedName))
				ret.add(iFile);
		}
		return ret;
	}

	/**
	 * Paths are similar of they have the same PathGenerator class and the same
	 * StopCondition class
	 * 
	 * @param pg1
	 * @param pathGenerator2
	 * @return
	 */
	public static boolean similarPathGenerator(PathGenerator<StopCondition> pg1, String pathGenerator2) {

		PathGenerator<StopCondition> pg2;
		try {
			pg2 = GeneratorFactory.parse(pathGenerator2);
		} catch (Exception e) {
			ResourceManager.logException(e);
			return false;
		}

		String clazz1 = pg1.getClass().getName();
		String clazz2 = pg2.getClass().getName();
		if (!clazz1.equals(clazz2))
			return false;

		StopCondition sc1 = pg1.getStopCondition();
		StopCondition sc2 = pg2.getStopCondition();
		if (!sc1.getClass().getName().equals(sc2.getClass().getName()))
			return false;

		return true;
	}

	/**
	 * @param pathgenerator1
	 * @param pathGenerator2
	 * @return
	 */
	public static boolean equalsPathGenerator(PathGenerator<StopCondition> pg1, String pathGenerator2) {
		if (!similarPathGenerator(pg1, pathGenerator2))
			return false;
		PathGenerator<StopCondition> pg2;
		try {
			pg2 = GeneratorFactory.parse(pathGenerator2);
		} catch (Exception e) {
			ResourceManager.logException(e);
			return false;
		}
		return pg1.getStopCondition().getValue().equals(pg2.getStopCondition().getValue());
	}

	/**
	 * @param cache
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static boolean isModified(SimpleCache cache, Path file) throws IOException {
		return !Files.getLastModifiedTime(file).equals(cache.get(file).getLastModifiedTime());
	}

	/**
	 * @param cache
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static boolean isNotGenerated(SimpleCache cache, Path file) throws IOException {
		return  !cache.contains(file) || (!cache.get(file).isGenerated());
	}

	public static String getStartElement(File file) throws IOException {
		ContextFactory factory = getContextFactory (file.toPath());
		List<Context> readContexts = factory.create(file.toPath());

		// RuntimeModel model = readContexts.get(0).getModel();
		Element startElement = readContexts.get(0).getNextElement();
		if (startElement == null)
			return null;
		return startElement.getName();
	}
	
	public static List<Element> getElements(File file) throws IOException {
		List<Element> elements = getModel(file).getElements();
		return elements;
	}

	public static RuntimeModel getModel(File file) throws IOException {
		ContextFactory factory = getContextFactory (file.toPath());
		List<Context> readContexts = factory.create(file.toPath());
		RuntimeModel model = readContexts.get(0).getModel();
	
		return model;
	}
	
	public static List<String> getVertices(File file) throws IOException {
		List<RuntimeVertex> vertices = getModel(file).getVertices();
		List<String> ret = vertices.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());
		return ret;
	}
	
	public static List<String> getEdges(File file) throws IOException {
		List<RuntimeEdge> edges = getModel(file).getEdges();
		List<String> ret = edges.stream().map(edge -> edge.getName()).collect(Collectors.toList());
		return ret;
	}
	
	public static boolean isValidEdge(File file,String edge) throws IOException {
		return getModel(file).findEdges(edge) != null;
	}
	
	public static boolean isValidVertex(File file,String vertex) throws IOException {
		return getModel(file).findVertices(vertex) != null;
	}
	
	/**
	 * Filter a model. It keeps only the passed element in the source model (removing the others) 
	 * @param sourceFileModel
	 * @param elements
	 * @return a string representation of the reduced model on json format
	 * @throws IOException
	 */
	public static String reduceModel (File sourceFileModel,String name,Element[] elements) throws IOException {
		Map<RuntimeVertex,Vertex> mapping = new HashMap<RuntimeVertex,Vertex>();
		Builder<? extends Element> startElement = null;
		 
		RuntimeModel sourceModel = GraphWalkerFacade.getModel(sourceFileModel);
		Model model = new Model();
		model.setName(name);
		model.setProperties(sourceModel.getProperties());
		model.setId(UUID.randomUUID().toString());
		List<Vertex.RuntimeVertex> vertices = sourceModel.getVertices();
		for (RuntimeVertex v : vertices) {
			boolean found  = false;
			for (int i = 0; i < elements.length; i++) {
				Element element = elements[i];
				if (element.getId().equals(v.getId())) {
					found = true;
					break;
				}
			}
			if (!found) continue;
			org.graphwalker.core.model.Vertex vertex = new org.graphwalker.core.model.Vertex().setName(v.getName()).setId(v.getId());
			if (elements[0].equals(v)) {
				startElement = vertex;
			}
			vertex.setId(UUID.randomUUID().toString());
			vertex.setProperties(v.getProperties());
			vertex.setSharedState(v.getSharedState());
			vertex.setRequirements(v.getRequirements());
			List<Action> actions = v.getActions();
			for (Action action : actions) {
				model.addAction(action);
			}
			model.addVertex(vertex);
			mapping.put(v, vertex);
		}
		List<Edge.RuntimeEdge> edges = sourceModel.getEdges();
		for (RuntimeEdge runtimeEdge : edges) {
			boolean found  = false;
			for (int i = 0; i < elements.length; i++) {
				Element element = elements[i];
				if (element.getId().equals(runtimeEdge.getId())) {
					found = true;
					break;
				}
			}
			if (!found) continue;
			
			org.graphwalker.core.model.Vertex sourceVertex = mapping.get(runtimeEdge.getSourceVertex());
			org.graphwalker.core.model.Vertex targetVertex = mapping.get(runtimeEdge.getTargetVertex());
			Edge edge = new Edge()
					.setSourceVertex(sourceVertex)
					.setTargetVertex(targetVertex)
					.setName(runtimeEdge.getName())
					.setId(UUID.randomUUID().toString());

			if (elements[0].getId().equals(runtimeEdge.getId())) {
				startElement = edge;
			}
			edge.setProperties(runtimeEdge.getProperties());
			edge.setGuard(runtimeEdge.getGuard());
			edge.setActions(runtimeEdge.getActions());
			edge.setDependency(runtimeEdge.getDependency());
			edge.setRequirements(runtimeEdge.getRequirements());
			edge.setWeight(runtimeEdge.getWeight());
			
			model.addEdge(edge);
		}
		Context writeContext = new ExecutionContext() {
		};
		writeContext.setModel(model.build());
		if (startElement!=null) {
			writeContext.setNextElement(startElement);
		}
		
		List<Context> writeContexts = new ArrayList<>();
		writeContexts.add(writeContext);

		ContextFactory factory =  new JsonContextFactory ();
		String content = factory.getAsString(writeContexts);
		return content;
	}
	
	private static void addContexts(List<Context> executionContexts, Path modelFileName, String generator,
			String startElement) throws IOException {
 
		ContextFactory factory = getContextFactory (modelFileName);
		List<Context> contexts = factory.create(modelFileName);
		contexts.get(0).setPathGenerator(GeneratorFactory.parse(generator));
		Element element = contexts.get(0).getModel().findElements(startElement).get(0);
		contexts.get(0).setNextElement(element);
		executionContexts.addAll(contexts);
	}

	public static List<IFile> generateOffLineFromFile(IWorkbenchWindow ww, TestResourceGeneration dcp,
			BuildPolicy[] generators, int timeout, IProgressMonitor monitor)
			throws IOException, CoreException, InterruptedException {
		List<IFile> ret = new ArrayList<IFile>();
		IFile graphModel = dcp.getGraphIFile();

		List<Context> executionContexts = new ArrayList<Context>();
		for (BuildPolicy policy : generators) {
			String startElement = getStartElement(dcp.getGraphFile());
			Path path = dcp.getInputPath();
			String generator = policy.getPathGenerator();
			String msg = "Offline arguments: " + path + " " + generator + " " + startElement;
			ResourceManager.logInfo(graphModel.getProject().getName(), msg);
			addContexts(executionContexts, path, generator, startElement);
		}

		int index = 0;
		for (Context context : executionContexts) {
			OfflineContext oc = new OfflineContext(generators[index]);
			index++;
			dcp.addOfflineContext(oc);
			TestExecutor executor = new TestExecutor(context);
			executor.getMachine().addObserver(new Observer() {
				@Override
				public void update(Machine machine, Element element, EventType type) {
					if (EventType.BEFORE_ELEMENT.equals(type)) {
						oc.addMethodName(element.getName());
						if (monitor.isCanceled()) {
							throw new RuntimeException(new InterruptedException(MessageUtil.getString("timeoutofflineorcancelled")));
						}
					}
				}
			});

			Timer canceller = new Timer();
			canceller.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						 monitor.setCanceled(true);
					} catch (Throwable e) {
					}
				}

			}, timeout * 1000);
			
			try {
				Result result = executor.execute();
				canceller.cancel();
			} catch (TestExecutionException e) {

				String reason = e.getResult().getResultsAsString();
				canceller.cancel();
				ResourceManager.logException(e, reason);
				 
				if (!ErrorDialog.AUTOMATED_MODE) { // Avoid displaying a window while running automated mode
					DialogManager.asyncDisplayErrorMessage(MessageUtil.getString("error"),
							reason, e);
				}
			} catch (Throwable e) {
				canceller.cancel();
				ResourceManager.logException(e);
				if (!ErrorDialog.AUTOMATED_MODE) { // Avoid displaying a window while running automated mode
					DialogManager.asyncDisplayErrorMessage(MessageUtil.getString("error"),
							MessageUtil.getString("an_error_occured_while_running_offline_tool"), e);
				}
				return ret;
			}
		}
		dcp.updateWithOfflines();

		generateFromFile(ww, dcp, monitor);
		return ret;
	}

	/**
	 * Generate code
	 * 
	 * @param input
	 * @param output
	 * @throws IOException
	 * @throws CoreException
	 * @throws InterruptedException
	 */
	public static List<IFile> generateFromFile(IWorkbenchWindow ww, TestResourceGeneration dcp,
			IProgressMonitor monitor) throws IOException, CoreException, InterruptedException {
		List<IFile> ret = new ArrayList<IFile>();
		List<IFile> additionals = dcp.getClassExtension().getAdditionalContexts();
		int max = additionals.size() + 1;

		SubMonitor subMonitor = SubMonitor.convert(monitor, max);
		if (additionals != null) {
			for (IFile iFile : additionals) {
				subMonitor.setTaskName("Processing " + iFile);
				try {
					try {
						GraphWalkerContextManager.generateDefaultGraphConversion(ww, iFile, monitor);
					} catch (InvocationTargetException e) {
						ResourceManager.logException(e);
					}
				} finally {
					subMonitor.split(1);
				}
			}
		}
		final SimpleCache cache = new SimpleCache(dcp.getOutputPath());
		if (!PreferenceManager.isCacheEnabled() || isNotGenerated(cache, dcp.getInputPath())
				|| isModified(cache, dcp.getInputPath()) || dcp.isOffline() ) {
			try {
				subMonitor.setTaskName("Processing " + dcp.getInputPath().getFileName());
				// write interface
				SourceFile sourceFile = new SourceFile(dcp.getInputPath(), dcp.getBasePath(), dcp.getOutputPath());
				ContextFactory factory = getContextFactory(sourceFile.getInputPath());
				List<IPath> interfaces = write(factory, sourceFile, true, monitor);
				
				if (dcp.isGenerateOnlyInterface())
					return ret;
				
				// write implementation
				IFile type = null;
				if (dcp.isAppendSource()) {
				    type = dcp.toIFile();
				} else {
				    type = JDTManager.generateTestImplementation(dcp, monitor);
					ResourceManager.updateBuildPolicyFileFor(type);
				}
				
				ret.add(type);

				 
							  type = ret.get(ret.size()-1) ;
							JDTManager.enrichClass(type, dcp, monitor);
							JDTManager.formatUnitSourceCode(type , monitor);
							JDTManager.openEditor(type, ww);
						 
				ICompilationUnit cu = JavaCore.createCompilationUnitFrom(type);
				int count = 0;
				CompilationUnit ast = null;
				while (ast==null && count < 10) {
					Thread.sleep(200);
					ast = JDTManager.parse(cu);
					count++;
				}

				 
							  type = ret.get(ret.size()-1) ;
							JDTManager.reorganizeImport(JavaCore.createCompilationUnitFrom(type));
						 	
				cache.add(dcp.getInputPath(), new CacheEntry(dcp.getInputPath().toFile().lastModified(), true));
				IFile iFileCache = ResourceManager.toIFile(dcp.getOutputPath().toFile());
				ResourceManager.resfresh(iFileCache.getParent());

			} catch (Exception e) {
				ResourceManager.logException(e);
				cache.add(dcp.getInputPath(), new CacheEntry(dcp.getInputPath().toFile().lastModified(), false));
			} finally {
				subMonitor.split(1);
			}
		}
		return ret;
	}
	
	
	public static List<IFile> generateFromFile1(IWorkbenchWindow ww, TestResourceGeneration dcp,
			IProgressMonitor monitor) throws IOException, CoreException, InterruptedException {
		List<IFile> ret = new ArrayList<IFile>();
		List<IFile> additionals = dcp.getClassExtension().getAdditionalContexts();
		int max = additionals.size() + 1;

		SubMonitor subMonitor = SubMonitor.convert(monitor, max);
		if (additionals != null) {
			for (IFile iFile : additionals) {
				subMonitor.setTaskName("Processing " + iFile);
				try {
					try {
						GraphWalkerContextManager.generateDefaultGraphConversion(ww, iFile, monitor);
					} catch (InvocationTargetException e) {
						ResourceManager.logException(e);
					}
				} finally {
					subMonitor.split(1);
				}
			}
		}
		final SimpleCache cache = new SimpleCache(dcp.getOutputPath());
		if (!PreferenceManager.isCacheEnabled() || isNotGenerated(cache, dcp.getInputPath())
				|| isModified(cache, dcp.getInputPath()) || dcp.isOffline() ) {
			try {
				subMonitor.setTaskName("Processing " + dcp.getInputPath().getFileName());
				// write interface
				SourceFile sourceFile = new SourceFile(dcp.getInputPath(), dcp.getBasePath(), dcp.getOutputPath());
				ContextFactory factory = getContextFactory(sourceFile.getInputPath());
				List<IPath> interfaces = write(factory, sourceFile, true, monitor);
				
				if (dcp.isGenerateOnlyInterface())
					return ret;
				
				// write implementation
				IFile type = null;
				if (dcp.isAppendSource()) {
				    type = dcp.toIFile();
				} else {
				    type = JDTManager.generateTestImplementation(dcp, monitor);
					ResourceManager.updateBuildPolicyFileFor(type);
				}
				
				ret.add(type);

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							IFile type = ret.get(ret.size()-1) ;
							JDTManager.enrichClass(type, dcp, monitor);
							JDTManager.formatUnitSourceCode(type , monitor);
							JDTManager.openEditor(type, ww);
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}
				});

				ICompilationUnit cu = JavaCore.createCompilationUnitFrom(type);
				int count = 0;
				CompilationUnit ast = null;
				while (ast==null && count < 10) {
					Thread.sleep(1000);
					ast = JDTManager.parse(cu);
					count++;
				}

				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							IFile type = ret.get(ret.size()-1) ;
							JDTManager.reorganizeImport(JavaCore.createCompilationUnitFrom(type));
						} catch (Exception e) {
							ResourceManager.logException(e);
						}
					}
				});				
				cache.add(dcp.getInputPath(), new CacheEntry(dcp.getInputPath().toFile().lastModified(), true));
				IFile iFileCache = ResourceManager.toIFile(dcp.getOutputPath().toFile());
				ResourceManager.resfresh(iFileCache.getParent());

			} catch (Exception e) {
				ResourceManager.logException(e);
				cache.add(dcp.getInputPath(), new CacheEntry(dcp.getInputPath().toFile().lastModified(), false));
			} finally {
				subMonitor.split(1);
			}
		}
		return ret;
	}

	/**
	 * @param factory
	 * @param file
	 * @throws IOException
	 */
	private static List<IPath> write(ContextFactory factory, SourceFile file, boolean erase, IProgressMonitor monitor)
			throws IOException {
		List<IPath> ret = new ArrayList<IPath>();
		List<Context> contexts = factory.create(file.getInputPath());
		for (Context context : contexts) {
			try {
				RuntimeModel model = context.getModel();
				File outfile = file.getOutputPath().toFile();
				if (erase) {
					IFile ifileTodelete = ResourceManager.toIFile(outfile);
					if (ifileTodelete.exists()) {
						ifileTodelete.delete(true, monitor);
					}
				}

				String source = new CodeGenerator().generate(file, model);

				File f = file.getOutputPath().getParent().toFile();
				IFolder ifolder = ResourceManager.toIFolder(f);
				ResourceManager.ensureFolder((IFolder) ifolder);

				IFile ifile = ResourceManager.createFileDeleteIfExists(file.getOutputPath().toFile(), source, monitor);

				ret.add(ifile.getFullPath());
			} catch (Throwable t) {
				ResourceManager.logException(t);
			}
		}
		return ret;
	}

	/**
	 * Generate code
	 * 
	 * @param input
	 * @param output
	 */
	public static void generateFromFolder(final Path input, final Path output) {
		CodeGenerator.generate(input, output);
	}

	/**
	 * @param modelFileName
	 * @return
	 * @throws IOException
	 */
	public static String getNextElement(String modelFileName) throws IOException {
		Context c = getContext(modelFileName);
		if (c.getNextElement() != null) {
			return c.getNextElement().getName();
		}
		return null;
	}

	/**
	 * Return the Context built after having read and parsed the passed graph model
	 * GraphWalker lookup factory fails in OSGI env.  
	 * 
	 * @param modelFileName
	 * @return
	 * @throws IOException
	 */
	static Map<String, ContextFactory> cache = new HashMap<String, ContextFactory>();
    static {
    	cache.put("json", new JsonContextFactory());
    	cache.put("graphml", new YEdContextFactory());
    	cache.put("dot", new  DotContextFactory());
    	cache.put("java", new  JavaContextFactory());
    }
    
	public static ContextFactory getContextFactory(Path path) {
		String extension = FilenameUtils.getExtension(path.toString());
		ContextFactory factory = cache.get(extension);
		if (factory ==null) {
			throw new NullPointerException(" No factory found for : " + path);
		}
		return factory;
	}

	/**
	 * @param modelFileName
	 * @return
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws ContextFactoryException
	 */
	public static Context getContext(String modelFileName)
			throws IOException, JsonSyntaxException, ContextFactoryException {
		Path path = Paths.get(modelFileName);
		ContextFactory factory = getContextFactory(path);
		List<Context> contexts = factory.create(Paths.get(modelFileName));
		if (contexts != null && contexts.size() > 0) {
			return contexts.get(0);
		}
		return null;
	}

	/**
	 * @param main
	 * @param candidatesGraphFiles
	 * @return
	 * @throws IOException
	 * @throws JavaModelException
	 * @throws InterruptedException
	 */
	public static List<IFile> findSharedContexts(IFile main, List<IFile> candidatesGraphFiles)
			throws IOException, JavaModelException, InterruptedException {
		IPath path = main.getFullPath();
		File file = ResourceManager.toFile(path);
		Context mainContext = GraphWalkerFacade.getContext(file.getAbsolutePath());
		List<Context> candidates = new ArrayList<Context>();
		Map<Context, IFile> mapping = new HashMap<Context, IFile>();

		for (IFile graphFile : candidatesGraphFiles) {
			try {
				File gfile = ResourceManager.toFile(graphFile.getFullPath());
				Context candidatContext = GraphWalkerFacade.getContext(gfile.getAbsolutePath());
				mapping.put(candidatContext, graphFile);
				candidates.add(candidatContext);
			} catch (Exception e) {
				ResourceManager.logException(e);
			}

		}

		List<Context> contexts = new ArrayList<Context>();
		findSharedContexts(contexts, mainContext, candidates);
		List<IFile> ret = new ArrayList<IFile>();
		for (Context context : contexts) {
			IFile t = mapping.get(context);
			ret.add(t);
		}
		return ret;
	}

	/**
	 * @param filtered
	 * @param main
	 * @param candidates
	 * @throws IOException
	 * @throws JavaModelException
	 */
	private static void findSharedContexts(List<Context> filtered, Context main, List<Context> candidates)
			throws IOException, JavaModelException {
		List<Context> newCandidates = new ArrayList<Context>();
		Set<String> sharedStates = main.getModel().getSharedStates();
		for (String shared : sharedStates) {
			for (Context context : candidates) {
				if (context.equals(main))
					continue;
				List<RuntimeVertex> states = context.getModel().getSharedStates(shared);
				if (states != null) {
					if (!filtered.contains(context)) {
						newCandidates.add(context);
						filtered.add(context);
					}
				}
			}
		}
		for (Context c : newCandidates) {
			findSharedContexts(filtered, c, candidates);
		}
	}

	/**
	 * @param project
	 * @param type
	 * @param others
	 * @return
	 * @throws IOException
	 * @throws JavaModelException
	 */
	public static List<IType> getSharedContexts(IProject project, IType type, List<IType> others)
			throws IOException, JavaModelException {
		Map<Context, IType> mapping = new HashMap<Context, IType>();
		IPath path = JDTManager.getGraphModelPath(project, type);
		if (path == null)
			return new ArrayList<IType>();
		File file = ResourceManager.toFile(path);
		Context main = GraphWalkerFacade.getContext(file.getAbsolutePath());
		mapping.put(main, type);
		List<Context> candidates = new ArrayList<Context>();
		for (IType t : others) {
			path = JDTManager.getGraphModelPath(project, t);
			file = ResourceManager.toFile(path);
			Context c = GraphWalkerFacade.getContext(file.getAbsolutePath());
			candidates.add(c);
			mapping.put(c, t);
		}
		List<Context> contexts = new ArrayList<Context>();
		findSharedContexts(contexts, main, candidates);

		List<IType> ret = new ArrayList<IType>();
		for (Context context : contexts) {
			IType t = mapping.get(context);
			ret.add(t);
		}
		return ret;
	}

	/**
	 * Parse a graph model file with its directives held by the buildPolicy
	 * parameter
	 * 
	 * @param context
	 * @param pathgenerator
	 * @return
	 */
	public static List<String> parse(Context context, String pathgenerator) {
		List<Context> contexts = new ArrayList<Context>();
		contexts.add(context);
		try {
			context.setPathGenerator(GeneratorFactory.parse(pathgenerator));
		} catch (Throwable e) {
			List<String> issues = new ArrayList<String>();
			issues.add(e.getMessage());
			return issues;
		}
		List<String> issues = ContextsChecker.hasIssues(contexts);
		
		// assess whether we have 100% coverage on vertex while having a start vertex and start element which is not the Start vertex
		List<RuntimeVertex>  vertices = context.getModel().getVertices();
		RuntimeVertex[] filteredVertex =  vertices.stream().filter(item -> item.getName().equalsIgnoreCase(Constant.START_VERTEX_NAME)).toArray(RuntimeVertex[]::new);
		if (filteredVertex!=null && filteredVertex.length > 0 && context!=null && context.getNextElement()!=null && !context.getNextElement().getName().equalsIgnoreCase(Constant.START_VERTEX_NAME)) {
			PathGenerator<StopCondition> pg = GeneratorFactory.parse(pathgenerator);
			StopCondition condition = pg.getStopCondition();
			if (condition instanceof VertexCoverage && condition.getValue().trim().equals("100"))  {
				issues.add(MessageUtil.getString("cannot_reach_one_hundred_vertex_coverage_with_start_vertex"));
			}
		}
		return issues;
	}

	/**
	 * @param pathgenerator
	 * @return
	 */
	public static boolean parsePathGenerator(String pathgenerator) {
		try {
			GeneratorFactory.parse(pathgenerator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * @param pathgenerator
	 * @return
	 */
	public static PathGenerator createPathGenerator(String pathgenerator) {
		return GeneratorFactory.parse(pathgenerator);
	}

	/**
	 * A listViewer of context for the passed model iterator
	 * 
	 * @param itr
	 * @return
	 * @throws Exception
	 */
	private static List<Context> getContexts(Iterator<String> itr) throws Exception {
		List<Context> executionContexts = new ArrayList<>();
		while (itr.hasNext()) {
			String modelFileName = itr.next();
			ContextFactory factory = getContextFactory(Paths.get(modelFileName));
			List<Context> contexts;
			try {
				contexts = factory.create(Paths.get(modelFileName));
			} catch (DslException e) {
				ResourceManager.logException(e);
				throw new Exception("Model syntax error : " + "When parsing model: '" + modelFileName + "' ");
			}
			executionContexts.addAll(contexts);
		}
		return executionContexts;
	}

	/**
	 * Get the requirement located in the graphml file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getRequirement(String filepath) throws Exception {
		List<String> input = new ArrayList<String>();
		input.add(filepath);
		SortedSet<String> reqs = new TreeSet<>();
		for (Context context : getContexts(input.iterator())) {
			for (Requirement req : context.getRequirements()) {
				reqs.add(req.getKey());
			}
		}
		return reqs;
	}

	/**
	 * Get the requirement located in the graphml file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getRequirement(IFile file) throws Exception {
		return getRequirement(file.getRawLocation().makeAbsolute().toString());
	}

	/**
	 * Get the "methods" for the graphml file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getMethods(String filepath) throws Exception {
		List<String> input = new ArrayList<String>();
		input.add(filepath);
		SortedSet<String> names = new TreeSet<>();
		for (Context context : getContexts(input.iterator())) {
			for (Vertex.RuntimeVertex vertex : context.getModel().getVertices()) {
				if (null != vertex.getName()) {
					names.add(vertex.getName());
				}
			}
			for (Edge.RuntimeEdge edge : context.getModel().getEdges()) {
				if (edge.getName() != null) {
					names.add(edge.getName());
				}
			}
		}
		return names;
	}

	/**
	 * Get the "methods" for the graphml file
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getMethods(IFile file) throws Exception {
		return getMethods(file.getRawLocation().makeAbsolute().toString());
	}

	/**
	 * Convert a graph model file into another format
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 * @throws IOException
	 */
	public static String convert(String inputFileName, String outputFileName) throws IOException {
		ContextFactory inputFactory = getContextFactory(Paths.get(inputFileName));
		List<Context> contexts = inputFactory.create(Paths.get(inputFileName));
		ContextFactory outputFactory = getContextFactory(Paths.get(outputFileName));
		return outputFactory.getAsString(contexts);
	}

	public static class GW4EExecutor {
		List<String> classpathElements;
		List<String> classnames;
		File reportDir;
		boolean displayDetail;
		boolean removedBlockedElements;
		
		public GW4EExecutor(List<String> classpathElements, List<String> classnames, File reportDir,
				boolean displayDetail,boolean removedBlockedElements) {
			super();
			this.classpathElements = classpathElements;
			this.removedBlockedElements=removedBlockedElements;
			this.reportDir = reportDir;
			this.displayDetail = displayDetail;
			this.classnames = new ArrayList<String>();
			for (String clazz : classnames) {
				if (this.classnames.contains(clazz))
					continue;
				this.classnames.add(clazz);
			}
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("reportDir : " + reportDir);
			System.out.println("displayDetail : " + displayDetail);
			System.out.println("removedBlockedElements : " + removedBlockedElements);
			System.out.println("classnames : " + classnames);
			System.out.println("classpathElements : " + classpathElements);
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
		}

		private void installEntryPointExecutionContext(Class[] contextClasses, List<Context> contextInstances) {
			for (Context context : contextInstances) {
				if (contextClasses[0].equals(context.getClass())) {
					continue;
				}
				context.setCurrentElement(null);
				context.setNextElement((Element) null);
			}
		}

		private void displayConfiguration(List<Context> contexts) {
			if (!displayDetail)
				return;
			System.out.println("------------------------------------------------------------------------");
			for (Context context : contexts) {
				System.out.println(
						"    " + context.getClass().getName() + "(" + context.getPathGenerator().toString() + " )");
			}
			System.out.println("------------------------------------------------------------------------");
		}

		private void displayResult(boolean displayDetail, Result result) {
			if (!displayDetail)
				return;
			if (result.hasErrors()) {
				System.err.println("------------------------------------------------------------------------");
				for (String error : result.getErrors()) {
					System.err.println(error);
				}
			}
			System.out.println("------------------------------------------------------------------------");
			System.out.println("Result :");
			System.out.println(result.getResultsAsString());
		}

		private ClassLoader switchClassLoader(ClassLoader classLoader) {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(classLoader);
			return contextClassLoader;
		}

		private Class[] loadTests(ClassLoader classLoader, List<String> classnames) throws ClassNotFoundException {
			List<Class> ret = new ArrayList<Class>();
			for (String test : classnames) {
				Class clazz = classLoader.loadClass(test);
				ret.add(clazz);
			}
			Class[] classes = new Class[ret.size()];
			ret.toArray(classes);
			return classes;
		}

		public void execute() throws IOException, ClassNotFoundException {
			Date starttime = new Date();
			ClassLoader classLoader = new IsolatedClassLoader(classpathElements);
			ClassLoader contextClassLoader = switchClassLoader(classLoader);
			Class[] contexts = loadTests(contextClassLoader, classnames);
			TestExecutor executor;

			switchClassLoader(contextClassLoader);
		 
			executor = new TestExecutor(contexts);
			
			List<Context> all = executor.getMachine().getContexts();
			if (removedBlockedElements) {
				org.graphwalker.io.common.Util.filterBlockedElements(all);
			}
			
			installEntryPointExecutionContext(contexts, executor.getMachine().getContexts());
			displayConfiguration(executor.getMachine().getContexts());
			Result result = null;
			try {
				executor.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = executor.getResult();
			displayResult(displayDetail, result);
			executor.reportResults(reportDir, starttime, System.getProperties());
		}
	}

}
