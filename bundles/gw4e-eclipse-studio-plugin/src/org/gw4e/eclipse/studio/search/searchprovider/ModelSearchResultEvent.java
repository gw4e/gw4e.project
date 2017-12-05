
package org.gw4e.eclipse.studio.search.searchprovider;

import org.eclipse.core.resources.IFile;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.SearchResultEvent;

public class ModelSearchResultEvent extends SearchResultEvent {

	private final IFile file;

	protected ModelSearchResultEvent(ISearchResult searchResult, IFile file) {
		super(searchResult);
		this.file = file;
	}

	public IFile getFile() {
		return file;
	}
}
