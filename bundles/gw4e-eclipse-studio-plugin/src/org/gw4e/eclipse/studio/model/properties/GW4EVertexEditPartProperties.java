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
import org.gw4e.eclipse.studio.commands.GraphElementRenameCommand;
import org.gw4e.eclipse.studio.commands.GraphVertexUpdateCommand;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.InitScript;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.model.Requirement;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.part.editor.AbstractGW4EEditPart;

public class GW4EVertexEditPartProperties extends AbstractGW4EEditPartProperties implements IPropertySource {
 
	private static final Object PropertiesTable[][] = {
			{ ModelProperties.PROPERTY_NAME, new TextPropertyDescriptor(ModelProperties.PROPERTY_NAME, "Name") },
			{ ModelProperties.PROPERTY_BLOCKED , new TextPropertyDescriptor(ModelProperties.PROPERTY_BLOCKED, "Blocked") },
			{ ModelProperties.PROPERTY_VERTEX_SHARED,new TextPropertyDescriptor(ModelProperties.PROPERTY_VERTEX_SHARED, "Shared") },
			{ ModelProperties.PROPERTY_DESCRIPTION,new TextPropertyDescriptor(ModelProperties.PROPERTY_DESCRIPTION, "Description") },
			{ ModelProperties.PROPERTY_VERTEX_REQUIREMENTS,new TextPropertyDescriptor(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS, "Requirements") },
			{ ModelProperties.PROPERTY_VERTEX_INIT,new TextPropertyDescriptor(ModelProperties.PROPERTY_VERTEX_INIT, "Init") },
			{ ModelProperties.PROPERTY_VERTEX_SHAREDNAME,new TextPropertyDescriptor(ModelProperties.PROPERTY_VERTEX_SHAREDNAME, "SharedName") },
			{ ModelProperties.PROPERTY_CUSTOM,new TextPropertyDescriptor(ModelProperties.PROPERTY_CUSTOM, "Custom") },
	
	};
	
	private AbstractGW4EEditPart node;
	private boolean blocked;
	private boolean shared;
	private String sharedName;
	private String description;
	private Requirement requirement;
	private InitScript init;
 
	
	public GW4EVertexEditPartProperties(AbstractGW4EEditPart node) {
		this.node = node;
		this.setName( ((GWNode)node.getModel()).getName() );
		this.setBlocked(((Vertex)node.getModel()).isBlocked());
		this.setShared(((Vertex)node.getModel()).isShared());
		this.setSharedName(((Vertex)node.getModel()).getSharedName());
		this.setInitScript(((Vertex)node.getModel()).getInitScript());
		this.setDescription(((Vertex)node.getModel()).getLabel());
		this.setRequirement(((Vertex)node.getModel()).getRequirement());
		this.setInitScript(((Vertex)node.getModel()).getInitScript());
		this.setProperties(node.getModel().getProperties());
	}
	
	 
	@Override
	public Object getEditableValue() {
		return this;
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
        if (id.equals(ModelProperties.PROPERTY_BLOCKED)) {
            return this.isBlocked();
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHARED)) {
            return this.isShared();
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHAREDNAME)) {
            return this.getSharedName();
        }
        if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
            return this.getDescription();
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS)) {
            return this.getRequirement();
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_INIT)) {
            return this.getInitScript();
        }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            return this.getProperties();
        }
        return null;
	}
	
	public GWNode getModel () {
		return (GWNode)node.getModel();
	}

	@Override
	public boolean isPropertySet(Object id) {
        if (id.equals(ModelProperties.PROPERTY_NAME)) {
            return this.getName()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_BLOCKED)) {
            return true;
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHARED)) {
            return true;
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHAREDNAME)) {
            return this.getSharedName()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
            return this.getDescription()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS)) {
            return this.getRequirement()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_INIT)) {
            return this.getInitScript()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            return this.getProperties()!=null;
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
		}
        if (id.equals(ModelProperties.PROPERTY_BLOCKED)) {
            this.setBlocked((boolean) value);
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHARED)) {
        	this.setShared((boolean) value);
        }
        if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
        	this.setDescription((String) value);
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS)) {
        	this.setRequirement(new Requirement((String) value));
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_INIT)) {
           this.setInitScript(new InitScript((String)value));
        }
        if (id.equals(ModelProperties.PROPERTY_VERTEX_SHAREDNAME)) {
            this.setSharedName((String) value);
         }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            this.setProperties((Map<String, Object>) value);
         }        
		firePropertyChanged((String) id, value);
	}

	protected void firePropertyChanged(String propName, Object value) {
		CommandStack stack = node.getViewer().getEditDomain().getCommandStack();
		if (propName.equals(ModelProperties.PROPERTY_NAME)) {
			GraphElementRenameCommand vrc = new GraphElementRenameCommand ();
			vrc.setModel((GraphElement)node.getModel());
			vrc.setOldName(((GraphElement)node.getModel()).getName());
			vrc.setNewName(this.getName());
			stack.execute(vrc);
			return;
		}
		GraphVertexUpdateCommand command = 
				new GraphVertexUpdateCommand(
							blocked, 
							shared, 
							sharedName,
							description, 
							requirement,
							init, 
							getProperties(),
							(Vertex)getModel ());
		stack.execute(command);
	}

	/**
	 * @return the blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	/**
	 * @return the shared
	 */
	public boolean isShared() {
		return shared;
	}

	/**
	 * @param shared the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description == null ? "" : description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the requirements
	 */
	public Requirement getRequirement() {
		return requirement;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	/**
	 * @return the InitScript
	 */
	public InitScript getInitScript() {
		return init;
	}

	/**
	 * @param init the InitScript to set
	 */
	public void setInitScript(InitScript init) {
		this.init = init;
	}

	/**
	 * @return the sharedName
	 */
	public String getSharedName() {
		return sharedName;
	}

	/**
	 * @param sharedName the sharedName to set
	 */
	public void setSharedName(String sharedName) {
		this.sharedName = sharedName;
	}
	 
	@Override
	public GWGraph getGraph() {
		return ((Vertex)this.getModel()).getGraph();
	}


}
