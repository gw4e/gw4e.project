package org.gw4e.eclipse.studio.commands;

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

import org.eclipse.gef.commands.Command;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;

public class GraphUpdateCommand extends Command {
 
	private Map<String, Object> properties;
	private Map<String, Object> old_properties;
	GWNode startElement;
	GWNode old_startElement;
	String description;
	String old_description;
	private GWGraph model;



	public GraphUpdateCommand(Map<String, Object> properties, GWGraph model) {
		super();
		this.properties = properties;
		this.startElement = null;
		this.model = model;
	}
	
	public GraphUpdateCommand(GWNode startElement, GWGraph model) {
		super();
		this.properties = null;
		this.startElement = startElement;
		this.model = model;
	}

	public GraphUpdateCommand(String description, GWGraph model) {
		super();
		this.properties = null;
		this.description = description;
		this.model = model;
	}
	
	@Override
	public void execute() {
		if (this.properties !=null) {
			old_properties = model.getProperties();
			model.update(properties);
		}
		if (this.startElement !=null) {
			old_startElement =  model.getStartElement();
			model.update(startElement);
		}
		if (this.description !=null) {
			old_description =  model.getLabel();
			model.update(description);
		}
	}

	@Override
	public void undo() {
		if (this.properties !=null) {
			model.update(old_properties);
		}
		if (this.startElement !=null) {
			model.update(old_startElement);
		}
	}
 
}
