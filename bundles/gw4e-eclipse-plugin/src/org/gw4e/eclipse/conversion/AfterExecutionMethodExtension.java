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

/**
 * 
 *
 */
public class AfterExecutionMethodExtension extends StubMethodExtension {
	boolean generatePerformance = false;
	public AfterExecutionMethodExtension(String classname,List<String> additionalContexts,String name,boolean generatePerformance) {
		super(classname,additionalContexts,org.graphwalker.java.annotation.AfterExecution.class, name);
		this.generatePerformance=generatePerformance;
	}
	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.conversion.StubMethodExtension#getSource(java.lang.String[], java.lang.String)
	 */
	public String getSource (String[] additionalContext,String value) {
		if (!generatePerformance) return super.getSource(additionalContext,value);
		String newline = System.getProperty("line.separator");
		return  "@" + this.getAnnotation()  + " " +   newline   +
				"public void " + this.getName() +"() throws IOException { " +   newline   +
				"String newline = System.getProperty(\"line.separator\");" + newline + 
				"String quote = \"" + "\\" + "\"\";" + newline + 
				"Profiler profiler = this.getProfiler();" + newline + 
				"List<Profile> profiles = profiler.getProfiles();" + newline + 
				"StringBuffer sb = new StringBuffer ();" + newline + 
				"sb.append(\"{\").append(newline);" + newline + 
				"sb.append(quote).append(\"performance\").append(quote).append(\" : {\").append(newline);" + newline + 
				"sb.append(quote).append(\"items\").append(quote).append(\" : [\").append(newline);" + newline + 
				"Iterator<Profile> iter = profiles.iterator();" + newline + 
				"while (iter.hasNext()) {" + newline + 
				"	Profile profile = iter.next();" + newline + 
				"	Element element = profile.getElement();" + newline + 
				"	String contextModel = profile.getContext().getModel().getName();" + newline + 
				"	String contextName = profile.getContext().getClass().getName();" + newline + 
				"	String pathGenerator = profile.getContext().getPathGenerator().toString();" + newline + 
				"	sb.append(\"{\").append(newline).append(quote).append(\"name\").append(quote).append(\":\").append(quote).append(element==null? \"\" : element.getName()).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"contextName\").append(quote).append(\":\").append(quote).append(contextName).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"pathGenerator\").append(quote).append(\":\").append(quote).append(pathGenerator).append(quote).append(\",\").append(newline);" + newline + 
				"	if (element instanceof Edge.RuntimeEdge) {" + newline + 
				"		Edge.RuntimeEdge re = (Edge.RuntimeEdge) element;" + newline + 
				"		sb.append(quote).append(\"type\").append(quote).append(\":\").append(quote).append(\"EDGE\").append(quote).append(\",\").append(newline);" + newline + 
				"		sb.append(quote).append(\"source\").append(quote).append(\":\").append(quote).append(re.getSourceVertex()==null ? \"\" : re.getSourceVertex().getName()).append(quote).append(\",\").append(newline);" + newline + 
				"		sb.append(quote).append(\"target\").append(quote).append(\":\").append(quote).append(re.getTargetVertex()==null ? \"\" : re.getTargetVertex().getName()).append(quote).append(\",\").append(newline);" + newline + 
				"	} else {" + newline + 
				"		sb.append(quote).append(\"kind\").append(quote).append(\":\").append(quote).append(\"VERTEX\").append(quote).append(\",\").append(newline);" + newline + 
				"	}" + newline + 
				"	sb.append(quote).append(\"ExecutionCount\").append(quote).append(\":\").append(quote).append(profile.getExecutionCount()).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"FirstExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getFirstExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"LastExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getLastExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"MaxExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getMaxExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"MinExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getMinExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"TotalExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getTotalExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(\",\").append(newline);" + newline + 
				"	sb.append(quote).append(\"AverageExecutionTime\").append(quote).append(\":\").append(quote).append(profile.getAverageExecutionTime(TimeUnit.MILLISECONDS)).append(quote).append(newline);" + newline + 
				"	sb.append(\"}\").append(newline);" + newline + 
				"	if (iter.hasNext()) sb.append(\",\");" + newline + 
				"}" + newline + 
				"sb.append(\"]\").append(\"}\").append(\"}\");"   + newline + 
				"System.out.println(sb.toString());" + newline + 
				"DateFormat formatter = new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss\");" + newline + 
				"String timestamp = formatter.format(new Date());" + newline + 
				"Files.write(Paths.get(\"graphwalker-test-summary-\" + this.getClass().getName() + \"-\" + timestamp + \".json\"), sb.toString().getBytes());" + newline + 
				"}";
	}
	@Override
	public String[] getImportedClasses() {
		return new String [] {
				annotation.getName(), 
				java.util.Iterator.class.getName(),
				java.util.List.class.getName(),
				java.util.Date.class.getName(),
				org.graphwalker.core.statistics.Profile.class.getName(),
				org.graphwalker.core.statistics.Profiler.class.getName(),
				org.graphwalker.core.model.Edge.class.getName(),
				org.graphwalker.core.model.Element.class.getName(),
				java.nio.file.Files.class.getName(),
				java.nio.file.Path.class.getName(),
				java.nio.file.Paths.class.getName(),
				java.io.IOException.class.getName(),
				java.util.concurrent.TimeUnit.class.getName()};
	}
	 
}

 
