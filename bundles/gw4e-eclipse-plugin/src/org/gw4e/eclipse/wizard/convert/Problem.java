package org.gw4e.eclipse.wizard.convert;

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

public class Problem {

	 
		/**
		 * Constant for no problem.
		 */
		public static final int PROBLEM_NONE = 0;
		
		/**
		 * Constant for resource already exists.
		 */
		public static final int PROBLEM_RESOURCE_EXIST = 2;

		/**
		 * Constant for empty resource.
		 */
		public static final int PROBLEM_RESOURCE_EMPTY = 1;

		
		/**
		 * Constant for invalid path.
		 */
		public static final int PROBLEM_PATH_INVALID = 4;

		/**
		 * Constant for project does not exist.
		 */
		public static final int FOLDER_PROJECT_DOES_NOT_EXIST = 5;
		/**
		 * Constant for project does not exist.
		 */
		public static final int PROBLEM_PROJECT_DOES_NOT_EXIST = 6;
		/**
		 * Constant for invalid name.
		 */
		public static final int PROBLEM_NAME_INVALID = 7;

		private String problemMessage = ""; 
		private int problemType = PROBLEM_NONE;
		
		public Problem() {
			super();
		}
		
		/**
		 * @return the problemMessage
		 */
		public String getProblemMessage() {
			return problemMessage;
		}
		/**
		 * @return the problemType
		 */
		public int getProblemType() {
			return problemType;
		}

		/**
		 * @param problemMessage the problemMessage to set
		 */
		public void raiseProblem(String problemMessage,int problemType) {
			this.problemMessage = problemMessage;
			this.problemType = problemType;
		}
 
}
