package org.gw4e.eclipse.studio.model.properties;

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

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.gw4e.eclipse.studio.commands.GraphUpdateCommand;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.part.editor.AbstractGW4EEditPart;

public class GW4EGraphEditPartProperties extends AbstractGW4EEditPartProperties implements IPropertySource {
 
	private static final Object PropertiesTable[][] = {
				{ ModelProperties.PROPERTY_NAME, new TextPropertyDescriptor(ModelProperties.PROPERTY_NAME, "Text") },
				{ ModelProperties.PROPERTY_CUSTOM,new TextPropertyDescriptor(ModelProperties.PROPERTY_CUSTOM, "Custom") },
				{ ModelProperties.PROPERTY_GRAPH_START_ELEMENT,new TextPropertyDescriptor(ModelProperties.PROPERTY_GRAPH_START_ELEMENT, "StartElement") },
			};

 
	private AbstractGW4EEditPart node;
	private GWNode startElement;
	

	public GW4EGraphEditPartProperties(AbstractGW4EEditPart node) {
		this.node = node;
		this.setName(getModel().getName());
		this.setStartElement(getGraph().getStartElement());
	}

	@Override
	public Object getEditableValue() {
		return this;
	}
	
	public GWNode getStartElement() {
		return startElement;
	}

	public void setStartElement(GWNode startElement) {
		this.startElement = startElement;
	}


	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];
		for (int i = 0; i < PropertiesTable.length; i++) {
			PropertyDescriptor descriptor;
			descriptor = (PropertyDescriptor) PropertiesTable[i][1];
			propertyDescriptors[i] = descriptor;
			descriptor.setCategory("sample");
		}
		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
        if (id.equals(ModelProperties.PROPERTY_NAME)) {
            return this.getName();
        }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            return this.getProperties();
        }
        if (id.equals(ModelProperties.PROPERTY_GRAPH_START_ELEMENT)) {
            return this.getProperties();
        }
        return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
        if (id.equals(ModelProperties.PROPERTY_NAME)) {
            return this.getName()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            return this.getProperties()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_GRAPH_START_ELEMENT)) {
            return this.getStartElement()!=null;
        }
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.equals(ModelProperties.PROPERTY_NAME)) {
			this.setName((String) value);
			return;
		}
		if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
			this.setProperties( (Map<String, Object>) value );
			firePropertyChanged((String) id, null, this.getProperties());
		}
		if (id.equals(ModelProperties.PROPERTY_GRAPH_START_ELEMENT)) {
			this.setStartElement((GWNode) value );
			firePropertyChanged((String) id, null, this.getStartElement());
		}
	}

	protected void firePropertyChanged(String propName, Object oldValue, Map<String, Object> value) {
		CommandStack stack = node.getViewer().getEditDomain().getCommandStack();
		GraphUpdateCommand command = new GraphUpdateCommand (value,(GWGraph)node.getModel());
		stack.execute(command);
	}
	
	protected void firePropertyChanged(String propName, Object oldValue, GWNode value) {
		CommandStack stack = node.getViewer().getEditDomain().getCommandStack();
		GraphUpdateCommand command = new GraphUpdateCommand (value,(GWGraph)node.getModel());
		stack.execute(command);
	}
	
	@Override
	public GWNode getModel() {
		return (GWNode)node.getModel();
	}
 
	@Override
	public GWGraph getGraph() {
		return ((GWGraph)this.getModel());
	}
}
