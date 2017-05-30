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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.compiler.BuildContext;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CompilationParticipant;
import org.eclipse.jdt.core.compiler.IProblem;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.product.GW4ENature;

/**
 * 
 *
 */
public class BuildParticipant extends CompilationParticipant {

	/**
	 * 
	 */
	public BuildParticipant() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationParticipant#isAnnotationProcessor()
	 */
	public boolean isAnnotationProcessor() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationParticipant#processAnnotations(org.eclipse.jdt.core.compiler.BuildContext[])
	 */
	public void processAnnotations(BuildContext[] files) {
	
	}
	 
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationParticipant#cleanStarting(org.eclipse.jdt.core.IJavaProject)
	 */
	public void cleanStarting(IJavaProject project) {
		try {
			GW4EBuilder.cleanMarkers(project.getProject());
			// This method is also walled when we remove properties settings so we 
			// need to add the following test because otherwise invalidating cache recreate properties settings for the project
			// and we never get a reseted properties for the project
			if (!GW4ENature.hasGW4ENature(project.getProject())) return ;
			BuildPolicyManager.invalidateCache(project.getProject());
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationParticipant#aboutToBuild(org.eclipse.jdt.core.IJavaProject)
	 */
	@Override
	public int aboutToBuild(IJavaProject project) {
		return READY_FOR_BUILD;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.compiler.CompilationParticipant#isActive(org.eclipse.jdt.core.IJavaProject)
	 */
	@Override
	public boolean isActive(IJavaProject project) {
		// This method is also called when we remove properties settings so we 
		// need to add the following test because otherwise isBuildEnabled method leads to recreate properties settings for the project
		// and we never get a reseted properties for the project
		if (!GW4ENature.hasGW4ENature(project.getProject())) return false;
		return PreferenceManager.isBuildEnabled(project.getProject().getName());
	}
	
	
	class PathGeneratorCategorizedProblem extends CategorizedProblem {
		BuildContext context;
		String pathGenerator;
		private Location location;
		
		public PathGeneratorCategorizedProblem(BuildContext context,String pathGenerator,Location location) {
			super();
			this.context = context;
			this.pathGenerator=pathGenerator;
			this.location=location;
		}

		@Override
		public String[] getArguments() {
			return new String[0];
		}

		@Override
		public int getID() {
			return IProblem.Syntax;
		}

		@Override
		public String getMessage() {
			return "Invalid path generator : '" + pathGenerator + "'";
		}

		@Override
		public char[] getOriginatingFileName() {
			return context.getFile().getName().toCharArray();
		}

		@Override
		public int getSourceEnd() {
			return location.getEnd();
		}

		@Override
		public int getSourceLineNumber() {
			return location.getLineNumber();
		}

		@Override
		public int getSourceStart() {
			return location.getStart();
		}

		@Override
		public boolean isError() {
			return true;
		}

		@Override
		public boolean isWarning() {
			return false;
		}

		@Override
		public void setSourceEnd(int sourceEnd) {
		}

		@Override
		public void setSourceLineNumber(int lineNumber) {
		}

		@Override
		public void setSourceStart(int sourceStart) {
		}

		@Override
		public int getCategoryID() {
			return CAT_SYNTAX;
		}

		@Override
		public String getMarkerType() {
			return GW4EBuilder.MARKER_TYPE;
		}
	}
}
