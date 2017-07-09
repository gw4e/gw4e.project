package org.gw4e.eclipse.studio.facade;

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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.model.Action;
import org.graphwalker.core.model.Builder;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Edge.RuntimeEdge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Guard;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.graphwalker.core.model.Requirement;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.graphwalker.io.factory.ContextFactory;
import org.graphwalker.io.factory.json.JsonContextFactory;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.studio.Activator;
import org.gw4e.eclipse.studio.commands.LinkCreateCommand;
import org.gw4e.eclipse.studio.commands.VertexCreateCommand;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.InitScript;
import org.gw4e.eclipse.studio.model.SharedVertex;
import org.gw4e.eclipse.studio.model.StartVertex;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.preference.PreferenceManager;

public class ResourceManager {

	public static void logException(Throwable t, String message) {
		ILog log = Activator.getDefault().getLog();
		log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Status.ERROR, message, t));
	}

	private static String makeName (String name) {
		try {
			return name.split(Pattern.quote(".")) [0];
		} catch (Exception e) {
			return name;
		}
	}
	
	public static boolean save(GWGraph gw,IProgressMonitor progressMonitor) throws CoreException, FileNotFoundException  {
		Builder<? extends Element> startElement = null;
		
		Model model = new Model();
		model.setName(makeName(gw.getName()));
		model.setProperties(gw.getProperties());
		model.setProperty("description", gw.getLabel())  ;
		Map<GWNode, org.graphwalker.core.model.Vertex> outVertices = new HashMap<GWNode, org.graphwalker.core.model.Vertex>();
		List<GWNode> vertices = gw.getVerticesChildrenArray();
		for (GWNode gwNode : vertices) {
			Vertex v = (Vertex) gwNode;
			org.graphwalker.core.model.Vertex vertex = new org.graphwalker.core.model.Vertex().setName(v.getName())
					.setId(v.getId());

			if (gw.getStartElement()!=null && v.equals(gw.getStartElement())) {
				startElement =  vertex;
			}
			
			Map<String, Object> properties = v.getProperties();
			if (properties != null) {
				Iterator iter = properties.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					Object value = properties.get(key);
					vertex.setProperty(key, value);
				}
			}
			vertex.setProperty("x", v.getLayout().x()).setProperty("y", v.getLayout().y())
					.setProperty("width", v.getLayout().width()).setProperty("height", v.getLayout().height())
					.setProperty("blocked", v.isBlocked()).setProperty("description", v.getLabel());

			if (v.isShared()) {
				vertex.setSharedState(v.getSharedName() == null ? "" : v.getSharedName().trim());
			}

			List<String> reqs = v.getRequirement().asList();
			for (String req : reqs) {
				vertex.addRequirement(new org.graphwalker.core.model.Requirement(req));
			}

			InitScript initScript = v.getInitScript();

			List<String> scripts = initScript.asList();
			for (String script : scripts) {
				model.addAction(new Action(script));
			}

			String source = initScript.getSource();
			if (source != null) {
				vertex.setProperty("gw.vertex.init.script", source);
			}
			model.addVertex(vertex);
			outVertices.put(gwNode, vertex);
		}

		
		List<GWNode> edges = gw.getEdgesChildrenArray();
		for (GWNode gwNode : edges) {
			GWEdge e = (GWEdge) gwNode;
			
			
			org.graphwalker.core.model.Vertex sourceVertex = outVertices.get(e.getSource());
			org.graphwalker.core.model.Vertex targetVertex = outVertices.get(e.getTarget());
			Edge edge = new Edge().setSourceVertex(sourceVertex).setTargetVertex(targetVertex).setName(e.getName())
					.setId(e.getId());

			if (gw.getStartElement()!=null && e.equals(gw.getStartElement())) {
				startElement =  edge;
			}

			
			Map<String, Object> properties = e.getProperties();
			if (properties != null) {
				Iterator iter = properties.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					if (key.startsWith("gw.edge.bendpoint.")) continue;
					Object value = properties.get(key);
					edge.setProperty(key, value);
				}
			}
			edge.setProperty("blocked", e.isBlocked());
			
			if (e.getDependency()!=null) {
				edge.setDependency(e.getDependency());
			}
			
			String description = e.getLabel();
			if ((description!=null) && description.length()>0) {
				edge.setProperty("description", description.trim());
			}

			String source = e.getGuard().getSource();
			if (source != null) {
				edge.setGuard(new Guard(source));
			}

			List<String> scripts = e.getAction().asList();
			for (String script : scripts) {
				edge.addAction(new Action(script));
			}

			int max = e.bendpointsSize();
			for (int i = 0; i < max; i++) {
				Point p = e.getBendpoints(i);
				edge.setProperty("gw.edge.bendpoint.x." + i, p.x);
				edge.setProperty("gw.edge.bendpoint.y." + i, p.y);
			}
 
			if (e.getWeight() != null) {
				edge.setWeight(e.getWeight());
			}
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
		
		String extension = gw.getFile().getFileExtension();
		String filename = gw.getFile().getName();
		String newname = filename.substring(0,filename.indexOf(extension)).concat(Constant.GRAPH_JSON_FILE);
		IFile file = (((IFolder)gw.getFile().getParent())).getFile(newname);
	 
		org.gw4e.eclipse.facade.ResourceManager.save(file, content, progressMonitor);
		return true;
	}
	
 
	
	public static boolean isEditable (IFile file) {
		if (file.getFileExtension().equalsIgnoreCase(Constant.GRAPH_JSON_FILE)) return true;
		return false;
	}
	
	public static GWGraph load(IFile input) {

		GWGraph gWGraph = new GWGraph(UUID.randomUUID(), input.getName(), input.getName());
		gWGraph.setFile(input);
		Map<RuntimeVertex, Vertex> cache = new HashMap<RuntimeVertex, Vertex>();
		try {
			String newline = System.getProperty("line.separator");
			File file = org.gw4e.eclipse.facade.ResourceManager.toFile(input.getFullPath());
			
			ContextFactory factory = GraphWalkerFacade.getContextFactory (file.toPath());
			List<Context> readContexts =  factory.create(file.toPath());
			 
			RuntimeModel model = readContexts.get(0).getModel();
			Element startElement = readContexts.get(0).getNextElement();
			
			gWGraph.setName(model.getName());
			String description = (String)model.getProperty("description");
			gWGraph.setLabel(description==null ? "" : description);
			Map<String, Object> modelProps = model.getProperties();
			Iterator<String> iter = modelProps.keySet().iterator();
			Map<String, Object> props = new HashMap<String, Object>();
			while (iter.hasNext()) {
				String key = iter.next();
				props.put(key, modelProps.get(key));
			}
			gWGraph.setProperties(props);

			Vertex startVertex = null;
			List<RuntimeVertex> vertices = model.getVertices();
			for (RuntimeVertex runtimeVertex : vertices) {
				Vertex v = null;
				String vname = runtimeVertex.getName();

				if (vname == null)
					vname = runtimeVertex.getId();
				UUID uuid = UUID.randomUUID();
				if (vname == null)
					vname = uuid.toString();
				if (vname != null && "start".equalsIgnoreCase(vname.trim().toLowerCase())) {
					v = new StartVertex(gWGraph, uuid, Constant.START_VERTEX_NAME, runtimeVertex.getId());
					startVertex = v;
				} else {
					if (runtimeVertex.hasSharedState()) {
						v = new SharedVertex(gWGraph, uuid, vname, runtimeVertex.getId(),
								runtimeVertex.getSharedState());
					} else {
						v = new Vertex(gWGraph, uuid, vname, runtimeVertex.getId());
					}
				}

				modelProps = runtimeVertex.getProperties();
				iter = modelProps.keySet().iterator();
				props = new HashMap<String, Object>();
				while (iter.hasNext()) {
					String key = iter.next();
					props.put(key, modelProps.get(key));
				}
				v.setProperties(props);
				
				Set<Requirement> requirements = runtimeVertex.getRequirements();

				if (requirements != null) {
					StringBuffer sb = new StringBuffer();
					for (Requirement requirement : requirements) {
						sb.append(requirement.getKey()).append(newline);
					}
					v.setRequirement(new org.gw4e.eclipse.studio.model.Requirement(sb.toString()));
				}

				List<Action> actions = runtimeVertex.getActions();
				StringBuffer sb = new StringBuffer();
				for (Action action : actions) {
					sb.append(action.getScript()).append(newline);
				}
				v.setInitScript(new org.gw4e.eclipse.studio.model.InitScript(sb.toString()));

				Map<String, Object> properties = runtimeVertex.getProperties();
				if (properties != null) {
					
					String initScript = (String)properties.get("gw.vertex.init.script");
					if (initScript!=null && initScript.trim().length()>0) {
						v.setInitScript(new InitScript(initScript));
					}
					
					if (properties.get("blocked") != null && properties.get("blocked") instanceof Boolean
							&& (Boolean) properties.get("blocked")) {
						v.setBlocked(true);
					}
					if (properties.get("description") != null && properties.get("description") instanceof String) {
						v.setLabel((String) properties.get("description"));
					}
					if (properties.get("x") != null) {
						int x = ((Double) properties.get("x")).intValue();
						int y = ((Double) properties.get("y")).intValue();
						v.setLayout(new Rectangle(x, y, PreferenceManager.getNodeDimension().width(),
								PreferenceManager.getNodeDimension().height()));
					}
				}

				if (startElement!=null && startElement.getId() != null && v.getId().equals(startElement.getId()) ) {
					gWGraph.setStartElement(v);
				}
				
				VertexCreateCommand vcc = new VertexCreateCommand();
				vcc.setVertex(v);
				if (v.getLayout() == null) {
					vcc.setLocation(new Point(0, 0));
				}
				vcc.execute();
				cache.put(runtimeVertex, v);
			}

			List<RuntimeEdge> edges = model.getEdges();
			int max = edges.size();
			for (int i = 0; i < max; i++) {
				RuntimeEdge edge = edges.get(i);
				
				RuntimeVertex vertexSource = edge.getSourceVertex();
				RuntimeVertex vertexTarget = edge.getTargetVertex();

				if (vertexSource == null && vertexTarget == null)
					continue;

				Vertex gwVertexSource = null;
				if (vertexSource == null) {
					if (startVertex == null) {
						UUID uuid = UUID.randomUUID();
						startVertex = new StartVertex(gWGraph, UUID.randomUUID(), Constant.START_VERTEX_NAME, uuid.toString());
						VertexCreateCommand vcc = new VertexCreateCommand();
						vcc.setVertex(startVertex);
						vcc.setLocation(new Point(0, 0));
						vcc.execute();
					}
					gwVertexSource = startVertex;
				} else {
					gwVertexSource = cache.get(vertexSource);
				}

				Vertex gwVertexTarget = cache.get(vertexTarget);

				if (gwVertexSource == null && gwVertexTarget == null)
					continue;

				LinkCreateCommand lcc = new LinkCreateCommand();
				lcc.setSource(gwVertexSource);
				lcc.setTarget(gwVertexTarget);
				lcc.setGraph(gWGraph);
				String name = ""; // gwVertexSource.getName() + "->" + gwVertexTarget.getName();
				if (edge.getId() != null)
					name = edge.getId();
				if (edge.getName() != null)
					name = edge.getName();
				if ((edge.getName() == null) || (edge.getName() != null && (edge.getName().trim().length()==0))) {
					name="";
				}
				 
				GWEdge gwEdge = new GWEdge(gWGraph, UUID.randomUUID(), name, edge.getId(), edge.getWeight());
				 
				if (startElement!=null && startElement.getId() != null && edge.getId().equals(startElement.getId()) ) {
					gWGraph.setStartElement(gwEdge);
				}
 
				modelProps = edge.getProperties();
				iter = modelProps.keySet().iterator();
				props = new HashMap<String, Object>();
				while (iter.hasNext()) {
					String key = iter.next();
					props.put(key, modelProps.get(key));
				}
				gwEdge.setProperties(props);
				
				
				gwEdge.setDependency(edge.getDependency());

				List<Action> actions = edge.getActions();
				StringBuffer sb = new StringBuffer();
				for (Action action : actions) {
					sb.append(action.getScript()).append(newline);
				}
				gwEdge.setAction(new org.gw4e.eclipse.studio.model.Action(sb.toString()));

				Guard guard = edge.getGuard();
				if (guard != null) {
					gwEdge.setGuard(new org.gw4e.eclipse.studio.model.Guard(guard.getScript()));
				}
				Map<String, Object> properties = edge.getProperties();
				if (properties != null) {
					if (properties.get("blocked") != null && properties.get("blocked") instanceof Boolean
							&& (Boolean) properties.get("blocked")) {
						gwEdge.setBlocked(true);
					}
					if (properties.get("description") != null && properties.get("description") instanceof String) {
						gwEdge.setLabel((String) properties.get("description"));
					}

					int index = 0;
					while (true) {
						Double x = (Double) properties.get("gw.edge.bendpoint.x."+index);
						Double y = (Double) properties.get("gw.edge.bendpoint.y."+index);
						if (x!=null && y!=null) {
							Point p = new Point (x,y);
							gwEdge.addBendpoints(index, p);
						} else {
							break;
						}
						index++;
					}		
				}
				lcc.setLink(gwEdge);
				lcc.execute();
			}
		} catch (Exception e) {
			logException(e, "Unable to load " + input);
		}
		return gWGraph;
	}

}
