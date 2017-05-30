package org.gw4e.eclipse.builder.marker;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.Document;
import org.eclipse.ui.IMarkerResolution;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.ResourceManager;

public final class InvalidAnnotationPathGeneratorMarkerResolution implements IMarkerResolution {
	ResolutionMarkerDescription resolutionMarkerDescription;

	/**
	 * @return
	 */
	public static IMarkerResolution [] getResolvers () {
		List<IMarkerResolution> resolvers = new ArrayList<IMarkerResolution>();
		List<ResolutionMarkerDescription> resolutionMarkerDescriptions = PathGeneratorDescription.getDescriptions();
		Iterator<ResolutionMarkerDescription> iter = resolutionMarkerDescriptions.iterator();
		while (iter.hasNext()) {
			ResolutionMarkerDescription resolutionMarkerDescription = iter.next();
			resolvers.add(new InvalidAnnotationPathGeneratorMarkerResolution(resolutionMarkerDescription));
		}
		IMarkerResolution []  ret = new IMarkerResolution [resolvers.size()];
		resolvers.toArray(ret);
		return ret;
	}
	
	
	/**
	 * @param label
	 * @param generatorpath
	 */
	public InvalidAnnotationPathGeneratorMarkerResolution(ResolutionMarkerDescription resolutionMarkerDescription) {
		this.resolutionMarkerDescription=resolutionMarkerDescription;
	}

	public String getLabel() {
		return resolutionMarkerDescription.toString();
	}

	/**
	 * @return the generatorpath
	 */
	public String getGeneratorpath() {
		return resolutionMarkerDescription.getGenerator();
	}
	
	public void run(IMarker marker) {
		Job job = new WorkspaceJob("GW4E Fix Job") {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				fix(marker, monitor);
				return Status.OK_STATUS;
			}
		};
		job.setRule(marker.getResource().getProject());
		job.setUser(true);
		job.schedule();
	}

	private void fix(IMarker marker, IProgressMonitor monitor) {
		MarkerResolutionGenerator.printAttributes (marker);
		try {
			String filepath  = (String) marker.getAttribute(BuildPolicyConfigurationException.JAVAFILENAME);
			int start = (int) marker.getAttribute(IMarker.CHAR_START);
			int end =  (int) marker.getAttribute(IMarker.CHAR_END);
			IFile ifile = (IFile) ResourceManager.toResource(new Path(filepath));
			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(ifile);
			String source = cu.getBuffer().getContents();
			String part1 =  source.substring(0,start);
			String part2 =  source.substring(end);
			source = part1 + "value=\"" + resolutionMarkerDescription.getGenerator() + "\"" + part2;
			final Document document = new Document(source);
			cu.getBuffer().setContents(document.get());
		    cu.save(monitor, false);
		} catch (Exception e) {
			ResourceManager.logException(e);
		}
	}

	 
}
