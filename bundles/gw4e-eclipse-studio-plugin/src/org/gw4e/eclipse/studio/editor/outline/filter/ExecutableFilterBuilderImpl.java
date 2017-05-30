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

public class ExecutableFilterBuilderImpl implements ExecutableFilterBuilder {
	ActionScriptCriteria actionScriptCriteria;
	BlockedCriteria blockedCriteria;
	DescriptionCriteria descriptionCriteria;
	FilterOnCriteria filterOnCriteria;
	GuardScriptCriteria guardScriptCriteria;
	InitScriptCriteria initScriptCriteria;
	RequirementCriteria requirementCriteria;
	SharedCriteria sharedCriteria;
	WeightCriteria weightCriteria;
	NameCriteria nameCriteria;
	
	public ExecutableFilterBuilderImpl() {
	}

	/**
	 * @param actionScriptCriteria
	 *            the actionScriptCriteria to set
	 */
	public ExecutableFilterBuilder setActionScriptCriteria(ActionScriptCriteria actionScriptCriteria) {
		this.actionScriptCriteria = actionScriptCriteria;
		return this;
	}

	/**
	 * @param blockedCriteria
	 *            the blockedCriteria to set
	 */
	public ExecutableFilterBuilder setBlockedCriteria(BlockedCriteria blockedCriteria) {
		this.blockedCriteria = blockedCriteria;
		return this;
	}

	/**
	 * @param descriptionCriteria
	 *            the descriptionCriteria to set
	 */
	public ExecutableFilterBuilder setDescriptionCriteria(DescriptionCriteria descriptionCriteria) {
		this.descriptionCriteria = descriptionCriteria;
		return this;
	}

	/**
	 * @param filterOnCriteria
	 *            the filterOnCriteria to set
	 */
	public ExecutableFilterBuilder setFilterOnCriteria(FilterOnCriteria filterOnCriteria) {
		this.filterOnCriteria = filterOnCriteria;
		return this;
	}

	/**
	 * @param guardScriptCriteria
	 *            the guardScriptCriteria to set
	 */
	public ExecutableFilterBuilder setGuardScriptCriteria(GuardScriptCriteria guardScriptCriteria) {
		this.guardScriptCriteria = guardScriptCriteria;
		return this;
	}

	/**
	 * @param initScriptCriteria
	 *            the initScriptCriteria to set
	 */
	public ExecutableFilterBuilder setInitScriptCriteria(InitScriptCriteria initScriptCriteria) {
		this.initScriptCriteria = initScriptCriteria;
		return this;
	}

	/**
	 * @param requirementCriteria
	 *            the requirementCriteria to set
	 */
	public ExecutableFilterBuilder setRequirementCriteria(RequirementCriteria requirementCriteria) {
		this.requirementCriteria = requirementCriteria;
		return this;
	}

	/**
	 * @param sharedCriteria
	 *            the sharedCriteria to set
	 */
	public ExecutableFilterBuilder setSharedCriteria(SharedCriteria sharedCriteria) {
		this.sharedCriteria = sharedCriteria;
		return this;
	}

	/**
	 * @param weightCriteria
	 *            the weightCriteria to set
	 */
	public ExecutableFilterBuilder setWeightCriteria(WeightCriteria weightCriteria) {
		this.weightCriteria = weightCriteria;
		return this;
	}
	
	/**
	 * @param nameCriteria the nameCriteria to set
	 */
	public ExecutableFilterBuilder setNameCriteria(NameCriteria nameCriteria) {
		this.nameCriteria = nameCriteria;
		return this;
	}
	

	@Override
	public ExecutableFilter build() {
		filterOnCriteria.setNextCriteria(nameCriteria);
		nameCriteria.setNextCriteria(descriptionCriteria);
		descriptionCriteria.setNextCriteria(blockedCriteria);
		blockedCriteria.setNextCriteria(requirementCriteria);
		requirementCriteria.setNextCriteria(initScriptCriteria);
		initScriptCriteria.setNextCriteria(sharedCriteria);
		sharedCriteria.setNextCriteria(actionScriptCriteria);
		actionScriptCriteria.setNextCriteria(guardScriptCriteria);
		guardScriptCriteria.setNextCriteria(weightCriteria);
		
		ExecutableFilter filter = new ExecutableFilter(filterOnCriteria);
		return filter;
	}



}
