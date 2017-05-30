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

import java.util.List;

public abstract class JUnitTestMethodExtension extends MethodExtension {
	 
	/**
	 * @param methodName
	 * @param startElement
	 * @param targetVertex
	 */
	public JUnitTestMethodExtension(String classname,List<String> additionalContexts,String methodName,String startElement) {
		super(classname,additionalContexts,methodName,startElement);
		 
	}
	
	 
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.conversion.MethodExtension#getImportedClasses()
	 */
	public String[] getImportedClasses() {
		String[] deflt = new String[] { 
				org.junit.Test.class.getName(),
				org.graphwalker.core.machine.Context.class.getName(),
				org.graphwalker.java.test.TestBuilder.class.getName(), 
				org.graphwalker.java.test.Result.class.getName(),
				java.io.IOException.class.getName(),
		};
		String[] imports = getImportsForMethod();
		
		String[] ret = new String[deflt.length+imports.length];
		System.arraycopy(deflt,0, ret,0, deflt.length);
		System.arraycopy(imports, 0, ret, deflt.length, imports.length);
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.conversion.MethodExtension#getImports()
	 */
	protected abstract String[] getImportsForMethod();
		
	 
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.conversion.MethodExtension#getStaticImportedClasses()
	 */
	public String [] getStaticImportedClasses () {
		 return new String [] {
				 org.junit.Assert.class.getName(),
			};
	}
 
	/**
	 * @return
	 */
	protected abstract String  getMainSetPathGeneratorCall ();
	
	protected String  getAdditionaAddContextCall (String classname,String pathgenerator) {
		return "builder.addContext(new "+ classname + "().setPathGenerator(" + pathgenerator + "), " + classname + ".MODEL_PATH" + ");";
	}
	
	
	/**
	 * @return
	 */
	protected abstract String getPathgenerator ();
	
	
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.preferences.MethodExtension#getSource()
	 */
	@Override
	public String getSource(String [] additionalContext, String value) {
		String newline = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer ();
		sb.append("@Test ").append(newline);
		sb.append("public void "+ this.getName() + "() throws IOException {").append(newline);
		sb.append("    Context context = new " + getClassname() + "();").append(newline);
		sb.append("    TestBuilder builder = new TestBuilder().addContext(context,MODEL_PATH);").append(newline);
		sb.append(getMainSetPathGeneratorCall ()).append(newline);
		if (startElement!=null && startElement.trim().length() >0 ) {
			sb.append("context.setNextElement(context.getModel().findElements(\"" + startElement + "\").get(0));").append(newline);
		} 

		for (int i = 0; i < additionalContext.length; i++) {
			sb.append(getAdditionaAddContextCall (getSimplifiedClassName(additionalContext[i]),getPathgenerator ())).append(newline);
		}
		sb.append("    Result result = builder.execute();").append(newline);
		sb.append("    Assert.assertFalse(result.hasErrors());").append(newline);
		sb.append("}").append(newline);
	 
		return sb.toString();
	}
	
} 
