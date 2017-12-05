package org.gw4e.eclipse.studio.search.searchprovider;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.gw4e.eclipse.message.MessageUtil;

public class ModelSearchQuery implements ISearchQuery {

	private ModelSearchQueryDefinition definition;
 
	private ModelSearchResult fSearchResult;

	public ModelSearchQuery(ModelSearchQueryDefinition definition) {
		this.definition=definition;
		fSearchResult = new ModelSearchResult (this);
	}

	@Override
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		List<IFile> result = definition.execute();
		result.stream().forEach(file -> fSearchResult.addFile(file));
		return Status.OK_STATUS;
	}

	@Override
	public String getLabel() {
		return MessageUtil.getString("search_model_label");
	}

	@Override
	public boolean canRerun() {
		return false;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ISearchResult getSearchResult() {
		return fSearchResult;
	}
}
