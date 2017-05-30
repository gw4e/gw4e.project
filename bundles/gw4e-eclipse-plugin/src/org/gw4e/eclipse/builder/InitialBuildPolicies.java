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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

public abstract class InitialBuildPolicies implements Runnable {
	public String getName() {
		return name;
	}

	String name;
	IFile file;
	
	public static List<InitialBuildPolicies> ALL () {
		List<InitialBuildPolicies> ret = new ArrayList<InitialBuildPolicies> ();
		ret.add(new DefaultInitialBuildPolicies());
		ret.add(new SyncInitialBuildPolicies());
		ret.add(new NoCheckInitialBuildPolicies());
		return ret;
	}
	
	public InitialBuildPolicies(String name) {
		super();
		this.name = name;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	 
	public static class DefaultInitialBuildPolicies extends InitialBuildPolicies {

		public DefaultInitialBuildPolicies() {
			super(MessageUtil.getString("default_build_policies"));
		}

		@Override
		public void run() {
			try {
				BuildPolicyManager.addDefaultPolicies(file, new NullProgressMonitor());
			} catch (IOException | CoreException | InterruptedException e) {
				ResourceManager.logException(e);
			}
		}
 
	}

	public static class SyncInitialBuildPolicies extends InitialBuildPolicies {

		public SyncInitialBuildPolicies() {
			super(MessageUtil.getString("sync_build_policies"));
		}

		@Override
		public void run() {
			try {			
				BuildPolicyManager.addSyncPolicies(file, new NullProgressMonitor());
			} catch (IOException | CoreException | InterruptedException e) {
				ResourceManager.logException(e);
			}
		}
	}

	public static class NoCheckInitialBuildPolicies extends InitialBuildPolicies {

		public NoCheckInitialBuildPolicies() {
			super(MessageUtil.getString("nocheck_build_policies"));
		}

		@Override
		public void run() {
			try {
				BuildPolicyManager.addSyncPolicies(file, new NullProgressMonitor());
			} catch (IOException | CoreException | InterruptedException e) {
				ResourceManager.logException(e);
			}
		}
	}
}
