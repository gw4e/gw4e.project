package org.gw4e.eclipse.launching.runasmanual;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.machine.SimpleMachine;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.ui.ModelData;

public class Engine {
	Machine machine ;
	Map<Context,File> contexts = new HashMap<Context,File> ();
	String description;
	String component;
	public final class ModelTestContext extends ExecutionContext {
	}

	public Machine createMachine (String mainModel, ModelData[] additionalModels, String pathgenerator, String startElement,boolean removeBlockedElement)
			throws IOException {
		Context context = new ModelTestContext();
		IFile f = (IFile) ResourceManager.getResource(mainModel);
		File mainFile = ResourceManager.toFile(f.getFullPath());

		RuntimeModel rm = GraphWalkerFacade.getModel(mainFile);
		description = rm.getProperty("description")+"";
		component = rm.getProperty("component")+"";
 		context.setModel(rm).setPathGenerator(GraphWalkerFacade.createPathGenerator(pathgenerator));
		context.setNextElement(context.getModel().findElements(startElement).get(0));
		contexts.put(context, mainFile);
		List<Context> all = new ArrayList<Context>();
		all.add(context);
		for (ModelData md : additionalModels) {
			IFile fModel = (IFile) ResourceManager.getResource(md.getFullPath());
			File additionalModel = ResourceManager.toFile(fModel.getFullPath());
			rm = GraphWalkerFacade.getModel(additionalModel);
			context = new ModelTestContext();
			contexts.put(context, additionalModel);
			context.setModel(rm).setPathGenerator(GraphWalkerFacade.createPathGenerator(pathgenerator));
			all.add(context);
		}
		if (removeBlockedElement) {
			org.graphwalker.io.common.Util.filterBlockedElements(all);
		}
		machine = new SimpleMachine(all);
		return machine; 
	}
	
	public boolean hasNextstep () {
		return machine.hasNextStep();
			
	}
	
	public StepDetail step () {
		if (machine.hasNextStep()) {
			machine.getNextStep();
			Context context = machine.getCurrentContext();
			return createStepDetail (context);
		}
		return null;
	}
	
	private StepDetail createStepDetail (Context context) {
		if (context == null) {
			return null;
		}
		Element element = context.getCurrentElement();
		if (element == null) {
			return null;
		}
		String name = context.getCurrentElement().getName();
		String elementId = context.getCurrentElement().getId();
		Element elt = context.getModel().getElementById(elementId);
		String description = (String) elt.getProperty("description");
		List<String> requirements = elt.getRequirements().stream().map(item -> item.getKey()).collect(Collectors.toList());
		return new StepDetail(name,description,requirements, elt instanceof RuntimeVertex )  ;      
	}

	public String getDescription() {
		return description;
	}

	public String getComponent() {
		return component;
	}
}
