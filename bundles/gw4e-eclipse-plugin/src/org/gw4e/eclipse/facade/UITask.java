package org.gw4e.eclipse.facade;

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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 *  
 *
 */
public class UITask {
	public static class Task {
		String summary;
		Runnable runner;

		public Task(String summary, Runnable runner) {
			super();
			this.summary = summary;
			this.runner = runner;
		}

		/**
		 * @return the summary
		 */
		public String getSummary() {
			return summary;
		}

		/**
		 * 
		 */
		public void run() {
			runner.run();
		}

	}

	/**
	 * @param task
	 * @param monitor
	 */
	private static void workOnTask(UITask.Task task, IProgressMonitor monitor) {
		task.run();
	}

	/**
	 * @param jobname
	 * @param tasks
	 * @return
	 */
	public static Job createJob(String jobname, List<UITask.Task> tasks) {
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor subMonitor = SubMonitor.convert(monitor, tasks.size());
				for (UITask.Task task : tasks) {
					try {
						subMonitor.setTaskName(task.getSummary());
						workOnTask(task, subMonitor.split(1));
					} catch (Exception e) {
						return Status.CANCEL_STATUS;
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		return job;
	}

}
