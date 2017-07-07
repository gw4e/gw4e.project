package org.gw4e.eclipse.launching.runasmanual;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.machine.SimpleMachine;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;

public class Engine {
	Machine machine ;
	public final class ModelTestContext extends ExecutionContext {
	}

	public Machine createMachine (String mainModel, List<String> additionalModels, String pathgenerator, String startElement)
			throws IOException {
		Context context = new ModelTestContext();
		IFile f = (IFile) ResourceManager.getResource(mainModel);
		File mainFile = ResourceManager.toFile(f.getFullPath());

		RuntimeModel rm = GraphWalkerFacade.getModel(mainFile);
		PathGenerator pg = GraphWalkerFacade.createPathGenerator(pathgenerator);
		context.setModel(rm).setPathGenerator(pg);
		context.setNextElement(context.getModel().findElements(startElement).get(0));

		List<Context> contexts = new ArrayList<Context>();
		contexts.add(context);
		for (String model : additionalModels) {
			IFile fModel = (IFile) ResourceManager.getResource(model);
			File additionalModel = ResourceManager.toFile(fModel.getFullPath());
			rm = GraphWalkerFacade.getModel(additionalModel);
			context = new ModelTestContext();
			context.setModel(rm).setPathGenerator(pg);
			contexts.add(context);
		}

		machine = new SimpleMachine(contexts);
		return machine; 
	}
	
	public boolean hasNextstep () {
		return machine.hasNextStep();
			
	}
	public Context step () {
		if (machine.hasNextStep()) {
			machine.getNextStep();
			return machine.getCurrentContext();
		}
		return null;
	}
}
