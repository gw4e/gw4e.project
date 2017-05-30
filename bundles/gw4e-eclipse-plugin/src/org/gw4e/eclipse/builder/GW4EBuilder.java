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

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

/**
 * The builder of the GW4E Nature project
 * This builder builds graph model file.
 * For each parsed graph model file, parsing directives are found in a build.policy file located in the same directory as the parsed file
 * If such policy build file is not found an error is displayed in the problem view.
 * 
 * The build policy file should contain an entry for each graph model file located in the same folder. 
 * If no entry is found for a particular graph model file an error is displayed in the pb view
 * 
 * When an entry is found it should look like the following
 * ShoppingCart.graphml=random(vertex_coverage(100));I;random(edge_coverage(100));I;
 * 
 * Each of graph model has its directives on one line.
 * Each line can contains multiple directives separated by a ';'
 * Each directive is composed of a generator&stopcondition and a character (I,W,E) defining the level of the error severity if the parsing found errors
 *  
 *  If the syntax of the directives is wrong than an error is displayed in the pb view
 */
public class GW4EBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "gw4e-eclipse-plugin.gw4eBuilder";

	public static final String MARKER_TYPE = "gw4e-eclipse-plugin.gw4eProblem";

 
 
	/**
	 * An objects that visits resource deltas. 
	 *
	 */
	class GW4EDeltaVisitor implements IResourceDeltaVisitor {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.
		 * core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			if (!PreferenceManager.isBuildEnabled(resource.getProject().getName())) {
				if (resource instanceof IFile) {
					deleteMarkers((IFile)resource);
				}
				return true;
			}
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				checkResource(resource);
				break;
			case IResourceDelta.REMOVED:
				checkRemovedResource(resource);
				break;
			case IResourceDelta.CHANGED:
				checkResource(resource);
				break;
			}
			// return true to continue visiting children.
			return true;
		}
	}

	/**
	 * A visitor of the resource
	 *
	 */
	class GW4EResourceVisitor implements IResourceVisitor {
		public boolean visit(IResource resource) {
			checkResource(resource);
			// return true to continue visiting children.
			return true;
		}
	}

	/*
	 * For more information see 
	 *  	Platform Plug-in Developer Guide > Programmer's Guide > Advanced resource concepts
	 *  	Incremental project builders
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		ResourceManager.logInfo(getProject().getName(), "Build requested " );

		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	/* 
	 * Delete markers set and files created
	 * (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// delete markers set and files created
		cleanMarkers (getProject());
	}

	/**
	 * @param project
	 * @throws CoreException 
	 */
	public static void cleanMarkers (IProject project) throws CoreException {
		project.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
	}
	
	/**
	 * Do the trick. This is where parsing happen
	 * @param resource
	 */
	void checkResource(IResource resource) {
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			if (!PreferenceManager.isSupportedFileForBuild(file)) return;
			 
			try {
				GW4EParser parser = getParser(file);
				if (parser == null) {
					return;
				}
				parser.parse(file);
			} catch (Exception e) {
				ResourceManager.logException(e);
				return;
			}  
		}
	}

	/**
	 * Do the trick. This is where parsing happen
	 * @param resource
	 */
	void checkRemovedResource(IResource resource) {
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			if (!PreferenceManager.isSupportedFileForBuild(file)) return;
			 
			if (BuildPolicyManager.isBuildPoliciesFile(file)) {
				ResourceManager.touchFolderForRebuild(file);
			}
		}
	}

	
	/**
	 * Delete the markers for this file
	 * @param file
	 */
	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_INFINITE);
		} catch (CoreException ce) {
			ResourceManager.logException(ce);
		}
	}
	
	/**
	 * Delete the markers for this project
	 * @param file
	 */
	private void deleteMarkers(IProject project) {
		try {
			IMarker[] markers = project.findMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			for (int i = 0; i < markers.length; i++) {
				IMarker m = markers[i];
				m.delete();
			}
		} catch (CoreException ce) {
		}
	}

	/**
	 * @param project
	 * @param file
	 */
	public static void removeMarkerForAbstractContextUsed (IFile file) {
		try {
			IMarker[] markers = file.findMarkers(GW4EBuilder.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
			for (int i = 0; i < markers.length; i++) {
				IMarker m = markers[i];
				Integer pbId = (Integer)m.getAttribute(IJavaModelMarker.ID);
				if (pbId!=null) {
					if (GW4EParser.ABSTRACT_CONTEXT_USED==pbId.intValue()) {
						m.delete();
					}
				}
			}
		} catch (CoreException ce) {
		}
	}
	
	/**
	 * Implement a full build 
	 * @param monitor
	 * @throws CoreException
	 */
	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		try {
			 
			IProject project = getProject();
			deleteMarkers(project);
			if (!PreferenceManager.isBuildEnabled(project.getName())) return;
			getProject().accept(new GW4EResourceVisitor());
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}



	/**
	 * We only parse graph model files & build policy files & java files . Skip other files...
	 * @param file
	 * @return
	 */
	private GW4EParser getParser(IFile file) {
		String extension = file.getFileExtension();
		if (extension == null)
			return null;
		GW4EParser parser = null;

		if (PreferenceManager.isGraphModelFile(file) ) {
			parser = new GW4EParserImpl();
		} else if ("java".equalsIgnoreCase(extension)) {
			parser = new GW4ETestParser();
		} else {
			String projectName = file.getProject().getName();
			if (file.getName().equalsIgnoreCase(PreferenceManager.getBuildPoliciesFileName(projectName))) {
				parser = new BuildPolicyFileParserImpl();
			}
		}
		return parser;
	}

	/**
	 * Build only files that have changed. Thanks to the incremental build of the Eclipse platform
	 * @param delta
	 * @param monitor
	 * @throws CoreException
	 */
	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new GW4EDeltaVisitor());
	}

	/**
	 * Do some cleaning. remove markers for the project
	 * @param project
	 * @param monitor
	 */
	public static void removeProjectProblemMarker(IProject project, IProgressMonitor monitor) {
		try {
			project.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}

}
