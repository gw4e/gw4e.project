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
import org.gw4e.eclipse.studio.model.Action;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.Guard;

public class EdgeElementUpdateCommand extends Command {
	private boolean blocked;
	private String description;
	private Action action;
	private Guard guard;
	private Double weight; 
	private Integer dependency;
	private Map<String, Object> properties;
	
	private boolean old_blocked;
	private String old_description;
	private Action old_action;
	private Guard old_guard; 
	private Double old_weight; 
	private Integer old_dependency;
	private Map<String, Object> old_properties;
	
	private GWEdge model;

 

	public EdgeElementUpdateCommand(boolean blocked, String description, Double weight,Action action, Guard guard, Integer dependency,Map<String, Object> properties, GWEdge model) {
		super();
		this.blocked = blocked;
		this.description = description;
		this.action = action;
		this.guard = guard; 
		this.weight = weight; 
		this.dependency = dependency; 
		this.properties = properties;
		this.model = model;
	}
 
	@Override
	public void execute() {
		old_blocked = model.isBlocked();
		old_description=model.getLabel();
		old_action=model.getAction();
		old_guard = model.getGuard();
		old_weight = model.getWeight();
		old_dependency = model.getDependency();
		old_properties = model.getProperties();
		model.update (blocked,description,weight,action,guard,dependency,properties);
	}

	@Override
	public void undo() {
		model.setBlocked(old_blocked);
		model.setLabel(old_description);
		model.setAction(old_action);
		model.setGuard(old_guard);
		model.setWeight(old_weight);
		model.setDependency(old_dependency);
		model.setProperties(old_properties);
		model.update (old_blocked,old_description,old_weight,old_action,old_guard,old_dependency,old_properties);
 	}

}
