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
import java.util.Properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.gw4e.eclipse.studio.commands.EdgeElementUpdateCommand;
import org.gw4e.eclipse.studio.commands.GraphElementRenameCommand;
import org.gw4e.eclipse.studio.model.Action;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWGraph;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.GraphElement;
import org.gw4e.eclipse.studio.model.Guard;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.part.editor.EdgePart;

public class EdgeGW4EEditPartProperties extends AbstractGW4EEditPartProperties implements IPropertySource {
	 
	private static final Object PropertiesTable[][] = {
				{ ModelProperties.PROPERTY_NAME, new TextPropertyDescriptor(ModelProperties.PROPERTY_NAME, "Text") },
				{ ModelProperties.PROPERTY_BLOCKED , new TextPropertyDescriptor(ModelProperties.PROPERTY_BLOCKED, "Blocked") },
				{ ModelProperties.PROPERTY_DESCRIPTION,new TextPropertyDescriptor(ModelProperties.PROPERTY_DESCRIPTION, "Description") },
				{ ModelProperties.PROPERTY_EDGE_GUARD,new TextPropertyDescriptor(ModelProperties.PROPERTY_EDGE_GUARD, "Guard") },
				{ ModelProperties.PROPERTY_EDGE_ACTION,new TextPropertyDescriptor(ModelProperties.PROPERTY_EDGE_ACTION, "Action") },
				{ ModelProperties.PROPERTY_EDGE_WEIGHT,new TextPropertyDescriptor(ModelProperties.PROPERTY_EDGE_WEIGHT, "Weight") },
				{ ModelProperties.PROPERTY_EDGE_DEPENDENCY,new TextPropertyDescriptor(ModelProperties.PROPERTY_EDGE_DEPENDENCY, "Dependency") },
				{ ModelProperties.PROPERTY_CUSTOM,new TextPropertyDescriptor(ModelProperties.PROPERTY_CUSTOM, "Custom") },

	};
	
	private EdgePart node;
	private boolean blocked;
	private String description;
	private Action action;
	private Guard guard;
	private Double weight = null;
	private Integer dependency = null ; 
 


	public EdgeGW4EEditPartProperties(EdgePart node) {
		this.node = node;
		this.setName(getModel().getName());
		this.setBlocked(((GWEdge)node.getModel()).isBlocked());
		this.setDescription(((GWEdge)node.getModel()).getLabel());
		this.setAction( ((GWEdge)getModel()).getAction ());
		this.setGuard(((GWEdge)getModel()).getGuard());
		this.setWeight(null);
		Double d = ((GWEdge)getModel()).getWeight();
		if (d!=null) {
			this.setWeight(d);
		}
		this.setDependency(null);
		Integer dep = ((GWEdge)getModel()).getDependency();
		if (dep!=null) {
			this.setDependency(dep);
		}
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
        if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
            return this.getDescription();
        }
        if (id.equals(ModelProperties.PROPERTY_EDGE_GUARD)) {
            return this.getGuard();
        }
        if (id.equals(ModelProperties.PROPERTY_EDGE_ACTION)) {
            return this.getAction();
        }
        if (id.equals(ModelProperties.PROPERTY_EDGE_WEIGHT)) {
            return this.getWeight();
        }   
        if (id.equals(ModelProperties.PROPERTY_EDGE_DEPENDENCY)) {
            return this.getDependency();
        }
        if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
            return this.getProperties();
        }

        return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
        if (id.equals(ModelProperties.PROPERTY_NAME)) {
            return this.getName()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_BLOCKED)) {
            return true;
        }
        if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
            return this.getDescription()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_EDGE_GUARD)) {
            return this.getGuard()!=null;
        }
        if (id.equals(ModelProperties.PROPERTY_EDGE_ACTION)) {
            return this.getAction()!=null;
        }  
        if (id.equals(ModelProperties.PROPERTY_EDGE_WEIGHT)) {
            return this.getWeight()!=null;
        }  
        if (id.equals(ModelProperties.PROPERTY_EDGE_DEPENDENCY)) {
            return this.getDependency()!=null;
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
		Object oldValue = null;
		if (id.equals(ModelProperties.PROPERTY_NAME)) {
			oldValue = this.getName();
			this.setName((String) value);
		}
		if (id.equals(ModelProperties.PROPERTY_BLOCKED)) {
			oldValue = this.isBlocked();
			this.setBlocked((boolean) value);
		}
		if (id.equals(ModelProperties.PROPERTY_DESCRIPTION)) {
			oldValue = this.getDescription();
			this.setDescription((String) value);
		}
		if (id.equals(ModelProperties.PROPERTY_EDGE_GUARD)) {
			oldValue = this.getGuard();
			this.setGuard(new Guard((String) value));
		}
		if (id.equals(ModelProperties.PROPERTY_EDGE_ACTION)) {
			oldValue = this.getAction();
			this.setAction(new Action((String) value));
		}
		if (id.equals(ModelProperties.PROPERTY_EDGE_WEIGHT)) {
			oldValue = this.getWeight();
			String val = (String) value;
			if ( (val==null) || (val.trim().length()==0) ) {
				this.setWeight(null);
			} else {
				this.setWeight(Double.parseDouble((String) value));
			}
		}
		if (id.equals(ModelProperties.PROPERTY_EDGE_DEPENDENCY)) {
			oldValue = this.getDependency();
			String val = (String) value;
			if ( val==null || (val.trim().length()==0) ) {
				this.setDependency(null);
			} else {
				this.setDependency(Integer.parseInt((String) value));
			}
		}		
		if (id.equals(ModelProperties.PROPERTY_CUSTOM)) {
			oldValue = this.getProperties();
			this.setProperties( (Map<String, Object>) value );
		}
		
		firePropertyChanged((String) id, oldValue, value);
	}

	protected void firePropertyChanged(String propName, Object oldValue, Object value) {
		CommandStack stack = node.getViewer().getEditDomain().getCommandStack();
		if (propName.equals(ModelProperties.PROPERTY_NAME)) {
			GraphElementRenameCommand vrc = new GraphElementRenameCommand ();
			vrc.setModel((GraphElement)node.getModel());
			vrc.setOldName((String) oldValue);
			vrc.setNewName((String) value);
			stack.execute(vrc);
			return;
		}
		EdgeElementUpdateCommand command = 
				new EdgeElementUpdateCommand(
							blocked, 
							description, 
							weight,
							action,
							guard, 
							dependency,
							getProperties(),
							(GWEdge)getModel ());
		stack.execute(command);
	}

	@Override
	public GWNode getModel() {
		return (GWNode)node.getModel();
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the guard
	 */
	public Guard getGuard() {
		return guard;
	}

	/**
	 * @param guard the guard to set
	 */
	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public GWGraph getGraph() {
		return ((GWEdge)this.getModel()).getGraph();
	}

 
	public Integer getDependency() {
		return dependency;
	}

	public void setDependency(Integer dependency) {
		this.dependency = dependency;
	}
	 
}
