package org.gw4e.eclipse.studio.search.searchprovider.operator;

import org.graphwalker.core.model.Model.RuntimeModel;

public abstract class AbstractCriteria implements Criteria {
	Criteria next;

	public AbstractCriteria() {
	}

	@Override
	public Criteria setNextCriteria(Criteria next) {
		this.next = next;
		return next;
	}

	@Override
	public RuntimeModel executeNext(RuntimeModel rmodel) {
		if (next==null) return rmodel;
		return next.meetCriteria(rmodel);
	}
}
