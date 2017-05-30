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

public class SmokeTestMethodExtension extends JUnitTestMethodExtension {
	String targetVertex;
	public SmokeTestMethodExtension(String classname,List<String> additionalContexts,String startElement,String targetVertex) {
		super(classname,additionalContexts, "runSmokeTest",startElement);
		this.targetVertex=targetVertex;
	}
	
	@Override
	protected String[] getImportsForMethod() {
		return new String[] { 
				org.graphwalker.core.generator.AStarPath.class.getName(),
				org.graphwalker.core.generator.RandomPath.class.getName(),
				org.graphwalker.core.condition.EdgeCoverage.class.getName(),
				org.graphwalker.core.condition.ReachedVertex.class.getName(),
				org.graphwalker.core.model.Edge.class.getName(),
				org.graphwalker.core.model.Model.class.getName(),
				org.graphwalker.core.model.BuilderFactory.class.getName(),
				getQualifiedClassname()
		};
	}
 
 	@Override
	protected String getMainSetPathGeneratorCall() {
		return "context.setPathGenerator(new AStarPath(new ReachedVertex(\"" + this.targetVertex + "\")));";
	}

	@Override
	protected String getPathgenerator() {
		return "new RandomPath(new EdgeCoverage(100))";				 
	}
	
} 
 
