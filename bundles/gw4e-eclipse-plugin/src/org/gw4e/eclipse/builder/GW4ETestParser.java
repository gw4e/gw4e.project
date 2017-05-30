package org.gw4e.eclipse.builder;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Edge.RuntimeEdge;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.graphwalker.io.factory.ContextFactoryException;
import org.gw4e.eclipse.builder.exception.GeneratedModelPathConfigurationException;
import org.gw4e.eclipse.builder.exception.GraphAnnotationPathGeneratorConfigurationException;
import org.gw4e.eclipse.builder.exception.InvalidStartEdgeConfigurationException;
import org.gw4e.eclipse.builder.exception.ParserContextProperties;
import org.gw4e.eclipse.builder.exception.ParserException;
import org.gw4e.eclipse.builder.exception.PathGeneratorConfigurationException;
import org.gw4e.eclipse.builder.exception.UnSynchronizedGraphException;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.AnnotationParsing;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.MarkerManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

import com.google.gson.JsonSyntaxException;

/**
 * Parse .java file. It raises an error when a GraphWalker annotation value is not a correct one.  
 *
 */
public class GW4ETestParser extends GW4EParser {

	public GW4ETestParser() {
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.builder.GW4EParser#parse(org.eclipse.core.resources.IFile)
	 */
	@Override
	public void doParse(IFile in) {
		removeMarkers(in);
		ICompilationUnit compilationUnit = JavaCore.createCompilationUnitFrom(in);
 
		AnnotationParsing annoParsing = JDTManager.findAnnotationParsingInGraphWalkerAnnotation(compilationUnit,"value");
		String pathGenerator = annoParsing.getValue();
		if (pathGenerator == null || pathGenerator.trim().length() == 0) {
			return; 
		}
		boolean validated = GraphWalkerFacade.parsePathGenerator(pathGenerator);
		if (!validated) {
			ParserContextProperties props = new ParserContextProperties ();
			props.setProperty(GraphAnnotationPathGeneratorConfigurationException.JAVAFILENAME,in.getFullPath().toString());
			props.setProperty(PathGeneratorConfigurationException.PATH_GENERATOR,pathGenerator);
			String msg = "Invalid path generator : '" + pathGenerator + "'";
			MarkerManager.addMarker(in,this,
					new ParserException(annoParsing.getLocation(),new GraphAnnotationPathGeneratorConfigurationException(annoParsing.getLocation(),msg,props)), 
					IMarker.SEVERITY_ERROR);
		}
		String graphFilePath = JDTManager.getGW4EGeneratedAnnotationValue(compilationUnit,"value");
		if (graphFilePath==null) return;
		IFile graphFile=null;
		try {
			graphFile = (IFile) ResourceManager.toResource(
					in.getProject().getFullPath().append(new Path(graphFilePath)));
			if (graphFile==null) {
		 		hanldeInvalidModelPath (in,compilationUnit,graphFilePath);
			}
		} catch (FileNotFoundException e) {
			hanldeInvalidModelPath (in,compilationUnit,graphFilePath);
		}
		
		String attribut = "start";
		annoParsing =  JDTManager.findAnnotationParsingInGraphWalkerAnnotation(compilationUnit,attribut);
		if (graphFile!=null && (annoParsing!=null) ) {
			try {
				String startValue = null;
				int size = annoParsing.getAnnotations().size();
				if (size>0) {
					IAnnotationBinding binding = annoParsing.getAnnotations().get(0);
					IMemberValuePairBinding[] pairs = binding.getAllMemberValuePairs();
					for (int j = 0; j < pairs.length; j++) {
						IMemberValuePairBinding pair = pairs[j];
						if (attribut.equalsIgnoreCase(pair.getName())) {
							startValue=(String)pair.getValue(); 
						}
					}
					Context context = GraphWalkerFacade.getContext(ResourceManager.toFile(graphFile.getFullPath()).getAbsolutePath());
					boolean found = false;
					List<RuntimeEdge> edges = context.getModel().findEdges(startValue);
					if ( (edges != null) && (edges.size() > 0) ) {
						found = true;
					}
					List<RuntimeVertex> vertices = context.getModel().findVertices(startValue);
					if ( (vertices != null) && (vertices.size() > 0) ) {
						found = true;
					}					
					if (!found)  {
						hanldeInvalidStartElement (in,compilationUnit,annoParsing.getLocation(),startValue);
					}					
				}
			} catch (Exception e) {
				e.printStackTrace(); // will do a better job soon ...
			}
		}
		
		try {
			handleUnSynchronizedGraph (compilationUnit,graphFile,in) ;
		} catch (Exception e) {
			e.printStackTrace(); // will do a better job soon ...
		}
	}

	protected void hanldeInvalidStartElement (IFile in,ICompilationUnit compilationUnit,Location location,String start) {
		if (start==null || start.trim().length()==0) return;
		ParserContextProperties props = new ParserContextProperties ();
		props.setProperty(InvalidStartEdgeConfigurationException.STARTEDGE,start);
		ParserException e = new ParserException(location,new InvalidStartEdgeConfigurationException (location,"Unexisting element : " + start ,props));
		MarkerManager.addMarker(in, this, e, IMarker.SEVERITY_ERROR) ;
	}
	
	protected void hanldeInvalidModelPath (IFile in,ICompilationUnit compilationUnit,String graphFilePath) {
		AnnotationParsing annoParsing = JDTManager.findAnnotationParsingInGeneratedAnnotation(compilationUnit,"value");
		ParserContextProperties props = new ParserContextProperties ();
		props.setProperty(GeneratedModelPathConfigurationException.GRAPHMODELPATH,graphFilePath);
		ParserException e = new ParserException(annoParsing.getLocation(),new GeneratedModelPathConfigurationException (Location.NULL_LOCATION,"Unexisting Graph Model : " + graphFilePath,props));
		MarkerManager.addMarker(in, this, e, IMarker.SEVERITY_ERROR) ;
	}


	protected void handleUnSynchronizedGraph (ICompilationUnit icu, IFile graphFile, IFile in) throws JavaModelException, JsonSyntaxException, ContextFactoryException, FileNotFoundException, IOException {
		if (graphFile==null) return;
		Context context = GraphWalkerFacade.getContext(ResourceManager.toFile(graphFile.getFullPath()).getAbsolutePath());
		Map<String,RuntimeEdge> mapEdges = new HashMap<String,RuntimeEdge>();
		List<RuntimeEdge> edges = context.getModel().getEdges();
		for (RuntimeEdge runtimeEdge : edges) {
			String name = runtimeEdge.getName();
			if (name==null || name.trim().length()==0) continue;
			mapEdges.put(runtimeEdge.getName(),runtimeEdge);
		}
		Map<String,RuntimeVertex> mapVertices = new HashMap<String,RuntimeVertex>();
		List<RuntimeVertex> vertices = context.getModel().getVertices();
		for (RuntimeVertex runtimeVertex : vertices) {
			String name = runtimeVertex.getName();
			if (name==null || name.trim().length()==0) continue;
			if (Constant.START_VERTEX_NAME.equalsIgnoreCase(name)) continue;
			mapVertices.put(runtimeVertex.getName(), runtimeVertex);
		}
		
	 
		ITypeHierarchy h  = icu.findPrimaryType().newTypeHierarchy(new NullProgressMonitor ());
		IType[] classTypes = h.getAllClasses();
		IType[] interfaceTypes = h.getAllInterfaces();
		 
		Stream<IType> stream1 = Stream.of(classTypes);
		Stream<IType> stream2 = Stream.of(interfaceTypes);
		 
		Stream<IType> stream3 = Stream.concat(stream1, stream2);
		IType[] types = stream3.toArray(IType[]::new);
		
		for (int j = 0; j < types.length; j++) {
			IType type = types[j];
			IMethod[] methods =  type.getMethods();
			for (int i = 0; i < methods.length; i++) {
				IMethod method = methods[i];
				String name = method.getElementName();
				mapEdges.remove(name);
			}
			for (int i = 0; i < methods.length; i++) {
				IMethod method = methods[i];
				String name = method.getElementName();
				mapVertices.remove(name);
			}
		}
		
		if (mapEdges.size()==0 && mapVertices.size()==0) return;
		
		StringBuffer errors = new StringBuffer("(");
		Iterator<String> iter = mapEdges.keySet().iterator();
		while (iter.hasNext()) {
			String key =  iter.next();
			errors.append(key);
			if (iter.hasNext()) errors.append(",");
		}
		
		iter = mapVertices.keySet().iterator();

		if (iter.hasNext() &&  errors.toString().length()>0) errors.append(",");

		while (iter.hasNext()) {
			String key =  iter.next();
			errors.append(key);
			if (iter.hasNext()) errors.append(",");
		}
		errors.append(")");
		ParserContextProperties props = new ParserContextProperties ();
		ParserException e = new ParserException(Location.NULL_LOCATION,new UnSynchronizedGraphException (Location.NULL_LOCATION,"Graph model "  + graphFile + " not synchronized with " + in + " " + errors.toString(),props));
		String level = PreferenceManager.getSeverityForUnSynchronizedContext(in.getProject().getName());
		MarkerManager.addMarker(in, this, e, BuildPolicy.getSeverityLevel(level)) ;
	}
}
