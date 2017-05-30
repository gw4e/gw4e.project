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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.graphwalker.core.machine.Context;
import org.graphwalker.io.factory.ContextFactoryException;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.builder.exception.CannotBuildException;
import org.gw4e.eclipse.builder.exception.GraphModelSyntaxException;
import org.gw4e.eclipse.builder.exception.MissingBuildPoliciesFileException;
import org.gw4e.eclipse.builder.exception.MissingPoliciesForGraphConfigurationException;
import org.gw4e.eclipse.builder.exception.NoBuildRequiredException;
import org.gw4e.eclipse.builder.exception.ParserContextProperties;
import org.gw4e.eclipse.builder.exception.ParserException;
import org.gw4e.eclipse.builder.exception.PathGeneratorConfigurationException;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.MarkerManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.performance.PerformanceStats;
import org.gw4e.eclipse.performance.PerformanceStatsManager;

import com.google.gson.JsonSyntaxException;

/**
 * An implementation of a graph file parser in an Eclipse plugin context
 *
 */
public class GW4EParserImpl extends GW4EParser {

	private static final String PERF_BUILDER = Activator.PLUGIN_ID + "/perf/builders/GW4EParserImpl";

	/**
	 * An implementation to parse graphml file
	 */
	public GW4EParserImpl() {
	}

	 
	/**
	 * @param in
	 */
	private void handleNoBuildPoliciesFileForGraphModel(IFile in, Exception e) {
		ParserContextProperties props = new ParserContextProperties();
		props.setProperty(MissingBuildPoliciesFileException.GRAPHMODELPATH, in.getFullPath().toString());
		MarkerManager.addMarker(in, this,
				new ParserException(Location.NULL_LOCATION,
						new MissingBuildPoliciesFileException(Location.NULL_LOCATION, e.getMessage(), props)),
				IMarker.SEVERITY_ERROR);

	} 

	/**
	 * @param in
	 * @param e
	 */
	private void handleNoPoliciesForGraphModel(IFile in, MissingPoliciesForGraphConfigurationException e) {
		ParserContextProperties props = new ParserContextProperties();
		IPath buildPolicyPath = null;
 
		try {
			buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(in);
			ResourceManager.toResource(buildPolicyPath);
		} catch (FileNotFoundException e1) {
			ResourceManager.logException(e1);
		}
		
		props.setProperty(MissingPoliciesForGraphConfigurationException.BUILDPOLICIESPATH, buildPolicyPath.toString());
		props.setProperty(MissingPoliciesForGraphConfigurationException.GRAPHMODELPATH, in.getFullPath().toString());
		MarkerManager
				.addMarker(in, this,
						new ParserException(e.getLocation(), new MissingPoliciesForGraphConfigurationException(
								e.getLocation(), "No policies found for " + in.getName(), props)),
						IMarker.SEVERITY_ERROR);

	}

	private void handleNoBuildRequiredExceptionForGraphModel(IFile in, NoBuildRequiredException e) {
		// do nothing
	}

		
	/**
	 * @param in
	 * @param e
	 */
	private void handleBuildPoliciesMisconfigurationForGraphModel(IFile in, BuildPolicyConfigurationException e) {
		ParserContextProperties props = new ParserContextProperties();
		MarkerManager.addMarker(in, this,
				new ParserException(e.getLocation(),
						new CannotBuildException(e.getLocation(),
								"Build policies misconfiguration. Cannot pursue this build process. Fix the build policies file.",
								props)),
				IMarker.SEVERITY_ERROR);
	}

	/*
	 * Get the resource to be parsed and parse it Any error is reported in the
	 * problem views using addMarker method (non-Javadoc)
	 * 
	 * @see
	 * org.gw4e.eclipse.builder.GW4EParser#parse(org.eclipse.core.
	 * resources. IFile,
	 * org.gw4e.eclipse.builder.GW4EErrorHandler)
	 */
	@Override
	public void doParse(IFile in) {
		ResourceManager.logInfo(in.getProject().getName(),
				"Parsing GraphWalker Model " + in.getFullPath().toOSString());
		BuildPoliciesCache bpc = null;
		try {
			bpc = new BuildPoliciesCache(in);
		} catch (FileNotFoundException | CoreException | InterruptedException e) {
			ResourceManager.logException(e);
			return;
		}
		String modelFileName = null;
		try {
			modelFileName = ResourceManager.getAbsolutePath(in);
		} catch (FileNotFoundException e) {
			ResourceManager.logException(e);
			return;
		}
		List<BuildPolicy> policies = null;
		try {
			policies = BuildPolicyManager.getBuildPolicies(in,true);
		} catch (FileNotFoundException e) {
			removeMarkers(in);
			handleNoBuildPoliciesFileForGraphModel(in, e);
		} catch (MissingPoliciesForGraphConfigurationException e) {
			removeMarkers(in);
			handleNoPoliciesForGraphModel(in, e);
		} catch (NoBuildRequiredException e) {
			removeMarkers(in);
			handleNoBuildRequiredExceptionForGraphModel(in,e);
		} catch (BuildPolicyConfigurationException e) {
			removeMarkers(in);
			handleBuildPoliciesMisconfigurationForGraphModel(in, e);
		}
		if (policies == null)
			return; // here a pb occured in the definition of the build.policies
					// file ... no need to continue

		if (!bpc.needBuild(policies))
			return; // here the resource has not changed and no update in the
					// build.polciies file
					// since the last build, so there is no reason why we should
					// build this resource

		removeMarkers(in);
		Context context = null;
		PerformanceStats stats = PerformanceStatsManager.getStats(in.getProject().getName(), PERF_BUILDER, "Parsing",
				" Model " + new File(modelFileName).getName());

		try {
			stats.start();
			context = GraphWalkerFacade.getContext(modelFileName);
			
			Iterator<BuildPolicy> iter = policies.iterator();
			while (iter.hasNext()) {
				BuildPolicy buildPolicy = (BuildPolicy) iter.next();
				parse(buildPolicy, context);
			}
			
		} catch (ContextFactoryException ex) {
			handleSyntaxException(in);
		} catch (JsonSyntaxException ex) {
			handleSyntaxException(in);
		} catch (IOException e) {
			ResourceManager.logException(e);
		} finally {
			stats.end();
		}

		try {
			bpc.update(policies);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}

	}

	private void handleSyntaxException(IFile in) {
		ParserContextProperties props = new ParserContextProperties();
		IPath buildPoliciesPath=null;
		try {
			buildPoliciesPath = ResourceManager.getBuildPoliciesPathForGraphModel(in);
		} catch (FileNotFoundException e) {
			 ResourceManager.logException(e);
		}
		props.setProperty(PathGeneratorConfigurationException.GRAPHMODELPATH,in.getFullPath().toString());
		props.setProperty(PathGeneratorConfigurationException.BUILDPOLICIESPATH,buildPoliciesPath.toString());

		Location location = Location.NULL_LOCATION;
		 
		MarkerManager.addMarker(in, this,
				new ParserException(
						location, 
						new GraphModelSyntaxException(location, "Syntax Error in graph model", props)),
						IMarker.SEVERITY_ERROR);

		
	}
	
	/**
	 * Parse a graph model file with its directives held by the buildPolicy
	 * parameter Report errors in the pb view
	 * 
	 * @param in
	 * @param buildPolicy
	 * @param contexts
	 */
	private void parse(BuildPolicy buildPolicy, Context context) {

		PerformanceStats stats = PerformanceStatsManager.getStats(buildPolicy.getProjectName(), PERF_BUILDER,
				"Analysing",
				"--> " + buildPolicy.getGraphModel().getFullPath() + " [" + buildPolicy.getPathGenerator() + "]");
		try {
			stats.start();
			List<String> issues = GraphWalkerFacade.parse(context, buildPolicy.getPathGenerator());

			 
			for (Iterator<String> iterator = issues.iterator(); iterator.hasNext();) {
				String msg = buildPolicy.getGraphModel().getName() + "[" + buildPolicy.getPathGenerator() + "] : "
						+ iterator.next();
				ParserContextProperties props = new ParserContextProperties();
				props.setProperty(PathGeneratorConfigurationException.GRAPHMODELPATH,
						buildPolicy.getGraphModel().getFullPath().toString());
				props.setProperty(PathGeneratorConfigurationException.PATH_GENERATOR, buildPolicy.getPathGenerator());
				props.setProperty(PathGeneratorConfigurationException.BUILDPOLICIESPATH,
						buildPolicy.getBuildPolicyFile().getFullPath().toString());

				Location location = Location.NULL_LOCATION;
				 
				MarkerManager.addMarker(buildPolicy.getGraphModel(), this,
						new ParserException(location, new PathGeneratorConfigurationException(location, msg, props)),
						buildPolicy.getSeverity());
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			stats.end();
		}
	}

}
