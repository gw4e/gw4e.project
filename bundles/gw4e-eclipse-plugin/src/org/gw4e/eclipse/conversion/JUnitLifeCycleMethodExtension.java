package org.gw4e.eclipse.conversion;

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
import java.util.List;

public class JUnitLifeCycleMethodExtension  extends MethodExtension {
		 
		/**
		 * 
		 */
		protected boolean isstatic;
		/**
		 * 
		 */
		protected Class junitMethodAnnotation;
	
		/**
		 * @param name
		 * @param isstatic
		 * @param startElement
		 * @param junitMethodAnnotation
		 */
		public JUnitLifeCycleMethodExtension(String classname,List<String> additionalContexts,String name, boolean isstatic, String startElement, Class junitMethodAnnotation ) {
			super(  classname,additionalContexts,name,startElement);
			this.junitMethodAnnotation = junitMethodAnnotation;
			this.isstatic = isstatic;
		}
		
		/* (non-Javadoc)
		 * @see org.gw4e.eclipse.conversion.MethodExtension#getImportedClasses()
		 */
		public String [] getImportedClasses () {
			return new String [] {
					this.junitMethodAnnotation.getName()
			};
		}
		
		/* (non-Javadoc)
		 * @see org.gw4e.eclipse.conversion.MethodExtension#getSource(java.lang.String, java.lang.String)
		 */
		@Override
		public String getSource(String [] additionalContext,String value) {
			String source =	"@" + this.junitMethodAnnotation.getSimpleName() + " " + 
					"public " +  getStaticModifier ()  + " void " + this.getName() + " ()  throws Exception {"+
					"}";
			return source;
		}
		
		/**
		 * @return
		 */
		private String getStaticModifier () {
			if (isstatic) return "static";
			return "";
		}
		
		
		/**
		 * @param startElement
		 * @return
		 */
		public static List<MethodExtension> createAllJUnitMethodExtensions (String classname,List<String> additionalContexts,String startElement) {
			List<MethodExtension> extensions = new ArrayList<MethodExtension>();
			extensions.add(new JUnitBeforeClassMethodExtension(  classname,additionalContexts,startElement));
			extensions.add(new JUnitAfterClassMethodExtension(  classname,additionalContexts,startElement));
			extensions.add(new JUnitBeforeMethodExtension(  classname,additionalContexts,startElement));
			extensions.add(new JUnitAfterMethodExtension(  classname,additionalContexts,startElement));
			return extensions;
		}
}
 
