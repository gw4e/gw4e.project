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
import org.gw4e.eclipse.studio.model.InitScript;
import org.gw4e.eclipse.studio.model.Requirement;
import org.gw4e.eclipse.studio.model.Vertex;

public class GraphVertexUpdateCommand extends Command {
	private boolean blocked;
	private boolean shared;
	private String sharedName;
	private String description;
	private Requirement requirement;
	private InitScript init;
	private Map<String, Object> properties;
	
	private boolean old_blocked;
	private boolean old_shared;
	private String old_description;
	private String old_sharedName;
	private Requirement old_requirement;
	private InitScript old_init;
	private Map<String, Object> old_properties;
	private Vertex model;

	public GraphVertexUpdateCommand(boolean blocked, boolean shared,String sharedName, String description, Requirement requirement,
			InitScript init, Map<String, Object> properties, Vertex model) {
		super();
		this.blocked = blocked;
		this.shared = shared;
		this.description = description;
		this.requirement = requirement;
		this.init = init;
		this.sharedName = sharedName;
		this.properties = properties;
		this.model = model;
	}

	@Override
	public void execute() {
		old_blocked = model.isBlocked();
		old_shared = model.isShared();
		old_sharedName = model.getSharedName();
		old_description=model.getLabel();
		old_requirement=model.getRequirement();
		old_init = model.getInitScript();
		old_properties = model.getProperties();
		model.update(blocked, shared, sharedName , description, requirement, init,properties);
	}

	@Override
	public void undo() {
		model.setBlocked(old_blocked);
		model.setShared(old_shared);
		model.setSharedName(old_sharedName);
		model.setInitScript(old_init);
		model.setRequirement(old_requirement);
		model.setLabel(old_description);
		model.setProperties(old_properties);
		model.update(old_blocked, old_shared, old_sharedName, old_description, old_requirement, old_init,old_properties);
	}

}
