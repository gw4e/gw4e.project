package org.gw4e.eclipse.studio.search.searchprovider.operator;

import org.graphwalker.core.model.Model.RuntimeModel;

public interface Criteria {

	public RuntimeModel meetCriteria(RuntimeModel rmodel);
	public Criteria setNextCriteria (Criteria next);
	public RuntimeModel executeNext(RuntimeModel element);
}

