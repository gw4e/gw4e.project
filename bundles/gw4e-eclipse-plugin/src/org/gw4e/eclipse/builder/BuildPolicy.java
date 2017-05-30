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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.graphwalker.core.condition.StopCondition;
import org.graphwalker.core.condition.TimeDuration;
import org.graphwalker.core.generator.PathGenerator;
import org.graphwalker.dsl.antlr.generator.GeneratorFactory;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.MissingBuildPoliciesFileException;
import org.gw4e.eclipse.builder.exception.NoBuildRequiredException;
import org.gw4e.eclipse.builder.exception.ParserContextProperties;
import org.gw4e.eclipse.builder.exception.PathGeneratorConfigurationException;
import org.gw4e.eclipse.builder.exception.SeverityConfigurationException;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.preferences.PreferenceManager;

/**
 * Build Policy for a graph model file
 * This class contains directives to parse a graph model file with the GW parser (GeneratorFactory.parse(...)) 
 * and to set the level of error that will be displayed in the Eclipse problem view
 */
public class BuildPolicy {
	/**
	 * the GENERATOR(STOP_CONDITION) that will be used by the GW Parser
	 */
	String pathGenerator;
	
	/**
	 * The severity that will be used in case of error found by the GW parser (Info,Warning,Error)
	 */
	String severity;
	/**
	 * Same as above expected as integer format
	 */
	int sev;
	/**
	 * The project to which the policies file belong to
	 */
	String projectName;
	 
	/**
	 * 
	 */
	IFile buildPolicyFile;
	
	
	/**
	 * 
	 */
	IFile graphModel;
	
	/**
	 * Build an instance 
	 * @param projectName
	 * @param resourceFile
	 * @param pathGenerator
	 * @param severity
	 * @throws BuildPolicyConfigurationException
	 */
	public BuildPolicy(String projectName, IFile buildPolicyFile, IFile graphModel, String pathGenerator, String severity)
			throws BuildPolicyConfigurationException {
		super();
		this.buildPolicyFile=buildPolicyFile;
		this.pathGenerator = pathGenerator;
		this.severity = severity;
		this.projectName = projectName;
		this.graphModel = graphModel;
		validate();
	}

	/**
	 * @return the GENERATOR(STOPCONDITION)  
	 */
	public String getPathGenerator() {
		return pathGenerator;
	}

	/**
	 * @return the severity that will be raised if any error is found by the parser
	 */
	public int getSeverity() {
		return sev;
	}

 

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return pathGenerator + ";" + severity;
	}

	/**
	 * @param pathgenerator
	 * @return
	 */
	public boolean hasTimeDuratioStopCondition() {
		try {
			PathGenerator pg = GeneratorFactory.parse(getPathGenerator());
			StopCondition condition = pg.getStopCondition();
			return condition instanceof TimeDuration;
		} catch (Exception e) {
			return true;
		}
	}
	 

	private void validate() throws BuildPolicyConfigurationException {
		sev =  validate (projectName, buildPolicyFile, graphModel, pathGenerator, severity);
		IPath buildPoliciesPath = null;
		try {
			buildPoliciesPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
		} catch (FileNotFoundException e) {
			ParserContextProperties p = new ParserContextProperties ();
			p.setProperty(MissingBuildPoliciesFileException.GRAPHMODELPATH, graphModel.getFullPath().toString());
	
			throw new MissingBuildPoliciesFileException(Location.NULL_LOCATION,
					MessageUtil.getString("invalidpathgeneratoroudinbuildpolicies") + pathGenerator,p);
		}
		if ((pathGenerator == null) || pathGenerator.trim().length() == 0 || (validPathGenerator(pathGenerator)==null)) {
			ParserContextProperties p = new ParserContextProperties ();
			p.setProperty(PathGeneratorConfigurationException.PATH_GENERATOR, String.valueOf(pathGenerator));
			p.setProperty(PathGeneratorConfigurationException.BUILDPOLICIESPATH, buildPoliciesPath.toString());
			p.setProperty(PathGeneratorConfigurationException.GRAPHMODELPATH, graphModel.getFullPath().toString());
			Location location = ResourceManager.locationOfKeyValue(buildPolicyFile, graphModel.getName(), pathGenerator);
			throw new PathGeneratorConfigurationException(location,
					MessageUtil.getString("invalidpathgeneratoroudinbuildpolicies") + pathGenerator,p);
		}
	}
	
	public static int getSeverityLevel(String severity)  {
		String temp = severity.trim();
		if (temp.equalsIgnoreCase("E")) {
			return IMarker.SEVERITY_ERROR;
		} else {
			if (temp.equalsIgnoreCase("W")) {
				return IMarker.SEVERITY_WARNING;
			} else {
				if (temp.equalsIgnoreCase("I")) {
					return IMarker.SEVERITY_INFO;
				} 
			}
		}
		return -1;	
	}

	
	/**
	 * @param severity
	 * @return
	 * @throws BuildPolicyConfigurationException
	 */
	private static int getSeverity(String projectName, IFile graphModel, String pathgenerator, String severity)
			throws BuildPolicyConfigurationException {
		 
		int ret = getSeverityLevel(severity);
		if (ret != -1) {
			return ret;
		}
		Location location = Location.NULL_LOCATION;
		ParserContextProperties p = new ParserContextProperties();
		p.setProperty(SeverityConfigurationException.SEVERITY, String.valueOf(severity));
		p.setProperty(SeverityConfigurationException.PATH_GENERATOR, pathgenerator);
		p.setProperty(SeverityConfigurationException.GRAPHMODELPATH, graphModel.getFullPath().toString());
		try {
			IPath buildPoliciesPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
			IFile buildPoliciesFile = (IFile) ResourceManager.getResource(buildPoliciesPath.toString());
			location = ResourceManager.locationOfKeyValue(buildPoliciesFile, graphModel.getName(),
					pathgenerator + ";" + severity);
			location.relocateAtLastChar();
			p.setProperty(SeverityConfigurationException.BUILDPOLICIESPATH, buildPoliciesFile.getFullPath().toString());
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
		}
		throw new SeverityConfigurationException(location,
				"Invalid severity flag found  '" + severity + "'  (Choose one of E,W,I) ", p);
	}
 
	/**
	 * validate the content of the values passed in the constructor
	 * @throws BuildPolicyConfigurationException
	 */
	public static int validate(String projectName,IFile buildPolicyFile,IFile graphModel,String pathgenerator, String severity) throws BuildPolicyConfigurationException {
		if (severity == null || severity.trim().length() == 0) {
			ParserContextProperties p = new ParserContextProperties ();
			p.setProperty(SeverityConfigurationException.SEVERITY,String.valueOf(severity));
			p.setProperty(SeverityConfigurationException.PATH_GENERATOR,pathgenerator);
			p.setProperty(SeverityConfigurationException.GRAPHMODELPATH,graphModel.getFullPath().toString());
			p.setProperty(SeverityConfigurationException.BUILDPOLICIESPATH , buildPolicyFile.getFullPath().toString());
			 
			Location location = ResourceManager.locationOfKeyValue(buildPolicyFile, graphModel.getName(), pathgenerator+";"+severity);
			location.relocateAtLastChar();

			throw new SeverityConfigurationException(location,
					"Invalid severity flag found in " + PreferenceManager.getBuildPoliciesFileName(projectName)+ ". No severity flag found " + severity
							+ "  (Choose one of E,W,I) ",p);
		}
		return getSeverity(projectName,graphModel,pathgenerator,severity);
	}
	
	public static boolean isNoCheck (IFile file, String pathGenerator) {
		boolean b = pathGenerator == null  || pathGenerator.trim().length() == 0 || pathGenerator.indexOf(NoBuildRequiredException.NO_CHECK) != -1;
		if (b && file != null) {
			ResourceManager.logInfo(file.getProject().getName(),"No build generator for " + file.toString());
		}
		return b;
	}
	
	public static boolean isSync (IFile file, String pathGenerator) {
		boolean b = pathGenerator == null  || pathGenerator.trim().length() == 0 || pathGenerator.indexOf(NoBuildRequiredException.SYNCH) != -1;
		if (b && file != null) {
			ResourceManager.logInfo(file.getProject().getName(),"No build generator or Sync generator for " + file.toString());
		}
		return b;
	}
	
	/**
	 * Parse the generator 
	 * @param pathGenerator
	 * @return
	 */
	public static PathGenerator<StopCondition> validPathGenerator (String pathGenerator) {
		try {
			if (isNoCheck(null, pathGenerator)) return null;
			if (isSync(null, pathGenerator)) return null;
			return GeneratorFactory.parse(pathGenerator);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return the graphModel
	 */
	public IFile getGraphModel() {
		return graphModel;
	}

	/**
	 * @return the buildPolicyFile
	 */
	public IFile getBuildPolicyFile() {
		return buildPolicyFile;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object o)  {
		if (o instanceof BuildPolicy) {
			BuildPolicy other = (BuildPolicy) o;
			return this.projectName.equalsIgnoreCase(other.projectName) &&
					this.graphModel.getFullPath().toString().equalsIgnoreCase(other.graphModel.getFullPath().toString()) &&
					this.toString().equalsIgnoreCase(other.toString());
		}
		return false;
	}
	
	 
	
	/**
	 * @param bp
	 * @return
	 */
	public static String serialize (BuildPolicy bp) {
		return bp.projectName+";"+bp.graphModel.getFullPath().toString()+";"+ bp.getBuildPolicyFile().getFullPath().toString()+";"+bp.pathGenerator+";"+bp.severity;
	}
	
	/**
	 * @param serialized
	 * @return
	 */
	public static List<BuildPolicy> deserialize(String serialized) {
		try {
			List<BuildPolicy> list = new ArrayList<BuildPolicy>();
			StringTokenizer st = new StringTokenizer(serialized, ";");
			while (st.hasMoreTokens()) {
				String projectName = st.nextToken();
				IFile graphModel = (IFile) ResourceManager.getResource(st.nextToken());
				IFile buildPolicyFile = (IFile) ResourceManager.getResource(st.nextToken());
				String pathGenerator = st.nextToken();
				String severity = st.nextToken();
				if (graphModel==null) continue;
				list.add(new BuildPolicy(projectName, buildPolicyFile, graphModel, pathGenerator, severity));
			}
			return list;
		} catch (Exception e) {
			ResourceManager.logException(e, "Error while deserializing " + serialized);
		}
		return null;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
}


