package org.gw4e.eclipse.studio.editor.outline.filter;

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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OutLineFilter {
	public static final String PROPERTY_UPDATED = "FilterPropertyUpdated";
	public static String NO_OPERATOR = "";
	public static String EQUAL_OPERATOR = "==";
	public static String NOT_EQUAL_OPERATOR= "!=";
	public static String UPPER_OPERATOR = ">";
	public static String UPPER_OR_EQUAL_OPERATOR = ">=";
	public static String LOWER_OPERATOR = "<";
	public static String LOWER_OR_EQUAL_OPERATOR = "<=";
	
	public static String YES = ThreeStateChoice.YES.getLabel();
	public static String NO = ThreeStateChoice.NO.getLabel();
	public static String NO_VALUE = ThreeStateChoice.NO_VALUE.getLabel();
	
	private boolean filterOn;
	
	private String  nameText;
	private String  descriptionText;
	private ThreeStateChoice blocked;
	
	private String  requirement;
	private ThreeStateChoice shared;
	private ThreeStateChoice initScript;
	
	private String weight;
	private String operator;
	private ThreeStateChoice guard;
	private ThreeStateChoice action;
	
	private PropertyChangeSupport listeners;
	
	public OutLineFilter(PropertyChangeListener listener) {
		this.listeners = new PropertyChangeSupport(this);
		addPropertyChangeListener (listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	/**
	 * @return the filterOn
	 */
	public boolean isFilterOn() {
		return filterOn;
	}

	/**
	 * @param filterOn the filterOn to set
	 */
	public void setFilterOn(boolean filterOn) {
		this.filterOn = filterOn;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the descriptionText
	 */
	public String getDescription() {
		return descriptionText;
	}

	/**
	 * @param descriptionText the descriptionText to set
	 */
	public void setDescription(String descriptionText) {
		this.descriptionText = descriptionText;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the blocked
	 */
	public ThreeStateChoice isBlocked() {
		return blocked;
	}

	/**
	 * @param blocked the blocked to set
	 */
	public void setBlocked(ThreeStateChoice blocked) {
		this.blocked = blocked;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the requirement
	 */
	public String getRequirement() {
		return requirement;
	}

	/**
	 * @param requirement the requirement to set
	 */
	public void setRequirement(String requirement) {
		this.requirement = requirement;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the shared
	 */
	public ThreeStateChoice isShared() {
		return shared;
	}

	/**
	 * @param shared the shared to set
	 */
	public void setShared(ThreeStateChoice shared) {
		this.shared = shared;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the initScript
	 */
	public ThreeStateChoice getInitScript() {
		return initScript;
	}

	/**
	 * @param initScript the initScript to set
	 */
	public void setInitScript(ThreeStateChoice initScript) {
		this.initScript = initScript;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the guard
	 */
	public ThreeStateChoice getGuardChoice() {
		return guard;
	}

	/**
	 * @param guard the guard to set
	 */
	public void setGuardChoice(ThreeStateChoice guard) {
		this.guard = guard;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the action
	 */
	public ThreeStateChoice getActionChoice() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setActionChoice(ThreeStateChoice action) {
		this.action = action;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}

	/**
	 * @return the nameText
	 */
	public String getNameText() {
		return nameText;
	}

	/**
	 * @param nameText the nameText to set
	 */
	public void setNameText(String nameText) {
		this.nameText = nameText;
		getListeners().firePropertyChange(PROPERTY_UPDATED, null, null);
	}
	
}
