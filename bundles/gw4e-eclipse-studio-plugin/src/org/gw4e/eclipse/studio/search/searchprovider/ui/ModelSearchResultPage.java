
package org.gw4e.eclipse.studio.search.searchprovider.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.ISearchResultPage;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.IPageSite;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.studio.search.searchprovider.ModelSearchResultEvent;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class ModelSearchResultPage implements ISearchResultPage, ISearchResultListener {

	private String fId;
	private Composite fRootControl;
	private IPageSite fSite;
	private List<IFile> files ;

	@Override
	public Object getUIState() {
		return null;
	}

	@Override
	public void setInput(ISearchResult search, Object uiState) {
		if (search != null) {
			search.addListener(this);
		}
		files = new ArrayList<IFile>();
	}

	@Override
	public void setViewPart(ISearchResultViewPart part) {
	}

	@Override
	public void setID(String id) {
		fId = id;
	}

	@Override
	public String getID() {
		return fId;
	}

	@Override
	public String getLabel() {
		return "GW4E Search Model(s) Result(s)";
	}

	@Override
	public IPageSite getSite() {
		return fSite;
	}

	@Override
	public void init(IPageSite site) throws PartInitException {
		fSite = site;
	}

	TreeViewer viewer;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent) {
		fRootControl = new Composite(parent, SWT.NULL);
		fRootControl.setLayout(new FillLayout(SWT.HORIZONTAL));

		 
		viewer = new TreeViewer(fRootControl, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider(createImageDescriptor())));
		viewer.addDoubleClickListener(new IDoubleClickListener () {

			@Override
			public void doubleClick(DoubleClickEvent evt) {
				TreeSelection sel = (TreeSelection) evt.getSelection();
				IFile file = (IFile) sel.getFirstElement();
				if (isModelFile(file)) {
					JDTManager.openEditor(file, "org.gw4e.eclipse.studio.editor.GW4EEditor", null);	
					return;
				}
				JDTManager.openEditor(file, null); 
			}
			
		});
		files = new ArrayList<IFile>();
	}

	@Override
	public void dispose() {
	}

	@Override
	public Control getControl() {
		return fRootControl;
	}

	@Override
	public void setActionBars(IActionBars actionBars) {
	}

	@Override
	public void setFocus() {
		fRootControl.setFocus();
	}

	@Override
	public void restoreState(IMemento memento) {
	}

	@Override
	public void saveState(IMemento memento) {
	}

	private boolean isModelFile (IFile file) {
		String extension = org.gw4e.eclipse.facade.ResourceManager.getExtensionFile(file);
		if (extension == null)
			return false;
		boolean isModel = PreferenceManager.isConvertable(extension);
		return isModel;
	}
	
	@Override
	public void searchResultChanged(SearchResultEvent event) {
		if (event instanceof ModelSearchResultEvent) {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					IFile f = ((ModelSearchResultEvent) event).getFile();
					files.add(f);
					IFile[] array = files.stream().distinct().toArray(IFile[]::new);
					viewer.setInput(array);
				}
			});
		}
	}

	class ViewContentProvider implements ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return (IFile[]) inputElement;
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			// The call here is not very optimal. It will look for any java file in the workspace even of 
			// we selected a reduced scope in the search UI
			// Notice that the model files are well searched in the ui defined scope.
			List<IFile> files  = JDTManager.findAvailableExecutionContextAncestors((IFile)parentElement);
			IFile[] array = files.stream().distinct().toArray(IFile[]::new);
			return array;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			IFile file = (IFile)element;
			if (!isModelFile(file)) return false;
			// The call here is not very optimal. It will look for any java file in the workspace even of 
			// we selected a reduced scope in the search UI
			// Notice that the model files are well searched in the ui defined scope.
			List<IFile> files  = JDTManager.findAvailableExecutionContextAncestors((IFile)element);
			return files.size() > 0;
		}

	}

	class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private ImageDescriptor directoryImage;
		private ResourceManager resourceManager;

		public ViewLabelProvider(ImageDescriptor directoryImage) {
			this.directoryImage = directoryImage;
		}

		@Override
		public StyledString getStyledText(Object element) {
			if (element instanceof IFile) {
				IFile file = (IFile) element;
				StyledString styledString = new StyledString(file.getFullPath().toString());
				return styledString;
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			return super.getImage(element);
		}

		@Override
		public void dispose() {
			if (resourceManager != null) {
				resourceManager.dispose();
				resourceManager = null;
			}
		}

		protected ResourceManager getResourceManager() {
			if (resourceManager == null) {
				resourceManager = new LocalResourceManager(JFaceResources.getResources());
			}
			return resourceManager;
		}

	 
	}

	private ImageDescriptor createImageDescriptor() {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/folder.png"), null);
		return ImageDescriptor.createFromURL(url);
	}
}
