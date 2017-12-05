 
package org.gw4e.eclipse.studio.search.searchprovider;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;

public class ModelSearchResult implements ISearchResult {

	private final ISearchQuery fQuery;
	private final ListenerList fListeners = new ListenerList();

	private final Collection<IFile> fResult = new HashSet<IFile>();

	public ModelSearchResult(ISearchQuery query) {
		fQuery = query;
	}

	@Override
	public String getLabel() {
		return fResult.size() + " file(s) found";
	}

	@Override
	public String getTooltip() {
		return "Found files in the selected scope";
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public ISearchQuery getQuery() {
		return fQuery;
	}

	@Override
	public void addListener(ISearchResultListener l) {
		if (fListeners.size()>0) fListeners.clear();
		fListeners.add(l);
	}

	@Override
	public void removeListener(ISearchResultListener l) {
		fListeners.remove(l);
	}

	private void notifyListeners(IFile file) {
		SearchResultEvent event = new ModelSearchResultEvent(this, file);

		for (Object listener : fListeners.getListeners())
			((ISearchResultListener) listener).searchResultChanged(event);
	}

	public void addFile(IFile file) {
		fResult.add(file);
		notifyListeners(file);
	}
}
