package org.gw4e.eclipse.studio.search.searchprovider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.studio.search.searchprovider.operator.Criteria;
import org.gw4e.eclipse.studio.search.searchprovider.operator.Operators;

public class ModelSearchQueryDefinition {

	List<Operators> operators;
	List<IProject> projects;

	public ModelSearchQueryDefinition(List<IProject> projects, List<Operators> operators) {
		super();
		this.projects = projects;
		this.operators = operators;
	}

	public List<IFile> execute() {
		Iterator<Operators> iter = operators.iterator();
		Criteria op = iter.next();
		while (iter.hasNext()) {
			op = op.setNextCriteria(iter.next());
		}
		
		List<IFile> ret = projects.stream().map( project -> { return hanldeProject (operators.get(0),project); } ).flatMap(x -> x.stream() ).collect(Collectors.toList());
		return ret;
	}

	private static List<IFile> hanldeProject (Operators op, IProject project) {
		try {
			Map<RuntimeModel,File> mapping = new HashMap<RuntimeModel,File>();
			List<IFile> models = new ArrayList<IFile> ();
			GraphWalkerFacade.getGraphModels(project, models);
			List<RuntimeModel> rmodels= models.stream().map(file -> {
				try {
					return ResourceManager.toFile(file.getFullPath());
				} catch (FileNotFoundException e) {
					 throw new RuntimeException(e);
				}
			}).map(f -> {
				try {
					RuntimeModel rm =  GraphWalkerFacade.getModel(f);
					mapping.put(rm, f);
					return rm;
				} catch (IOException e) {
					 throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
			
			List<RuntimeModel> filtered = rmodels.stream().filter(model -> op.meetCriteria(model)!=null).collect(Collectors.toList());
			List<IFile> modelFiles = filtered.stream().map(rm -> mapping.get(rm)).map(file -> ResourceManager.toIFile(file)).collect(Collectors.toList());
			return modelFiles;
		} catch (Exception e) {
			 ResourceManager.logException(e);
		}
		return new ArrayList<IFile>();
	}
	
	public static String getDefaultOperator() {
		return "=";
	}

	public static String getDefaultValue() {
		return MessageUtil.getString("enter_a_search_value");
	}

	public static String getDefaultPropertyName() {
		return MessageUtil.getString("requirement_property");
	}
	
	 

	public static List<String> getAvailableOperators() {
		return Operators.getAvailableOperators();
	}

}
