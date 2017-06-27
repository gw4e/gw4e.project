
package org.gw4e.eclipse.wizard.staticgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Vertex;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

/**
 * The Generator page that let the end user entering choices for graph model
 * file conversion
 *
 */
public class GraphElementSelectionUIPage extends WizardPage {

	private Table tableSource;
	private Table tableTarget;
	private File source;
	private List<String> ids ;
	private Element sourceSelection;
	private Element targetSelection;
	private TableViewer viewerTarget;
	private TableViewer viewerSource;

	protected GraphElementSelectionUIPage(String pageName, IFile source, List<String> ids) throws FileNotFoundException {
		super(pageName, MessageUtil.getString("move_elements_from_left_to_right_to_select_them"), null);
		this.source = ResourceManager.toFile(source.getFullPath());
		this.ids = ids;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 14;
		container.setLayout(gridLayout);

		Composite compositeL = new Composite(container, SWT.NULL);
		compositeL.setLayout(new GridLayout(1, false));
		GridData gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 6;
		compositeL.setLayoutData(gd);

		viewerSource = new TableViewer(compositeL,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		viewerSource.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn element = new TableViewerColumn(viewerSource, SWT.NONE);

		element.getColumn().setText("Source Elements");
		element.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Element elt = (Element) element;
				return elt.getName();
			}
		});
		element.getColumn().pack();

		tableSource = viewerSource.getTable();
		tableSource.setHeaderVisible(true);
		tableSource.setLinesVisible(true);
		gd = new GridData();
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.horizontalSpan = 1;
		tableSource.setLayoutData(gd);

		Composite compositeC = new Composite(container, SWT.NULL);
		compositeC.setLayout(new GridLayout(1, false));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 1;
		compositeC.setLayoutData(gd);

		Button toright = new Button(compositeC, SWT.PUSH);
		gd = new GridData();
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.horizontalSpan = 1;
		toright.setLayoutData(gd);
		toright.setText("Add");
		toright.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					moveToTarget();
					break;
				}
			}
		});

		Button toleft = new Button(compositeC, SWT.PUSH);
		gd = new GridData();
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.horizontalSpan = 1;
		toleft.setLayoutData(gd);
		toleft.setText("Remove");
		toleft.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					moveToSource();
					break;
				}
			}
		});

		Composite compositeR = new Composite(container, SWT.NULL);
		compositeR.setLayout(new GridLayout(1, false));
		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 6;
		compositeR.setLayoutData(gd);

		viewerTarget = new TableViewer(compositeR,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableTarget = viewerTarget.getTable();
		viewerTarget.setContentProvider(ArrayContentProvider.getInstance());
		element = new TableViewerColumn(viewerTarget, SWT.NONE);

		element.getColumn().setText("Target Elements");
		element.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Element elt = (Element) element;
				return elt.getName();
			}
		});
		element.getColumn().pack();

		tableTarget.setHeaderVisible(true);
		tableTarget.setLinesVisible(true);
		gd = new GridData();
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.horizontalSpan = 1;
		tableTarget.setLayoutData(gd);

		setControl(container);

		Composite compositeUpDown = new Composite(container, SWT.NONE);
		compositeUpDown.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		RowLayout rl_compositeUpDown = new RowLayout(SWT.VERTICAL);
		rl_compositeUpDown.fill = true;
		compositeUpDown.setLayout(rl_compositeUpDown);

		Button btnUpButton = new Button(compositeUpDown, SWT.NONE);
		btnUpButton.setText("Up");
		btnUpButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					moveUp();
					break;
				}
			}
		});

		Button btnDownButton = new Button(compositeUpDown, SWT.NONE);
		btnDownButton.setText("Down");
		btnDownButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					moveDown();
					break;
				}
			}
		});

		viewerTarget.setInput(loadTarget());
		viewerSource.setInput(loadSource());

		viewerTarget.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = viewerTarget.getStructuredSelection();
				targetSelection = (Element) selection.getFirstElement();
			}
		});

		viewerSource.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = viewerSource.getStructuredSelection();
				sourceSelection = (Element) selection.getFirstElement();
			}
		});
	}

	private void moveUp() {
		Element[] currentTarget = (Element[]) viewerTarget.getInput();
		if (targetSelection != null && currentTarget.length > 0) {

			if (targetSelection.equals(currentTarget[0]))
				return;

			List<Element> list = Arrays.asList(currentTarget);
			int index = list.indexOf(targetSelection);
			Element tmp = list.get(index - 1);
			list.set(index, tmp);
			list.set(index - 1, targetSelection);

			Element[] elements = new Element[list.size()];
			elements = list.toArray(elements);

			viewerTarget.setInput(elements);
			
			validatePage();
		}
	}

	private void moveDown() {
		Element[] currentTarget = (Element[]) viewerTarget.getInput();
		if (targetSelection != null && currentTarget.length > 0) {

			if (targetSelection.equals(currentTarget[currentTarget.length - 1]))
				return;

			List<Element> list = Arrays.asList(currentTarget);
			int index = list.indexOf(targetSelection);
			Element tmp = list.get(index + 1);
			list.set(index, tmp);
			list.set(index + 1, targetSelection);

			Element[] elements = new Element[list.size()];
			elements = list.toArray(elements);

			viewerTarget.setInput(elements);
			
			validatePage();
		}
	}

	private void moveToTarget() {
		Element[] currentTarget = (Element[]) viewerTarget.getInput();
		if (sourceSelection != null && currentTarget.length > 0) {

			Element[] elementsTarget = new Element[currentTarget.length + 1];
			System.arraycopy(currentTarget, 0, elementsTarget, 0, currentTarget.length);
			elementsTarget[elementsTarget.length - 1] = sourceSelection;
			viewerTarget.setInput(elementsTarget);
			//
			Element[] currentSource = (Element[]) viewerSource.getInput();
			Element[] elementsSource = new Element[currentSource.length - 1];
			int index = 0;
			for (int i = 0; i < currentSource.length; i++) {
				if (currentSource[i].equals(sourceSelection))
					continue;
				elementsSource[index] = currentSource[i];
				index++;
			}
			viewerSource.setInput(elementsSource);
			validatePage() ;
		}
	}

	private void moveToSource() {
		Element[] currentSource = (Element[]) viewerSource.getInput();
		if (targetSelection != null && currentSource.length > 0) {
			Element[] elementsSource = new Element[currentSource.length + 1];
			System.arraycopy(currentSource, 0, elementsSource, 0, currentSource.length);
			elementsSource[elementsSource.length - 1] = targetSelection;
			viewerSource.setInput(elementsSource);
			//
			Element[] currentTarget = (Element[]) viewerTarget.getInput();
			Element[] elementsTarget = new Element[currentTarget.length - 1];
			int index = 0;
			for (int i = 0; i < currentTarget.length; i++) {
				if (currentTarget[i].equals(targetSelection))
					continue;
				elementsTarget[index] = currentTarget[i];
				index++;
			}
			viewerTarget.setInput(elementsTarget);
			validatePage() ;
		}

	}

	private Element[] loadTarget() {
		try {
			List<Element> elements = new ArrayList<Element>();
			List<Element> sourceElements = GraphWalkerFacade.getElements(source);
			List<Element> modelElements = new ArrayList<Element> ();
			for (String id : ids) {
				 for (Element element : sourceElements) {
					if (id.equals(element.getId())) {
						modelElements.add(element);
					}
 				}
			}
			for (int index = 0; index < modelElements.size(); index++) {
				Element elt = modelElements.get(index);
				elements.add(elt);
			}
			
			Collections.sort(elements,new Comparator () {
				@Override
				public int compare(Object o1, Object o2) {
					Element elt1 = (Element) o1;
					Element elt2 = (Element) o2;
					int id1 = ids.indexOf(elt1.getId());
					int id2 = ids.indexOf(elt2.getId());
					return id1 - id2;
				}
			});
			Element[] namesArr = new Element[elements.size()];
			namesArr = elements.toArray(namesArr);
			return namesArr;
		} catch (IOException e) {
			ResourceManager.logException(e);
		}
		return new Element[0];
	}

	private Element[] loadSource() {
		try {
			List<Element> elements = new ArrayList<Element>();
			List<Element> sourceElements = GraphWalkerFacade.getElements(source);
			List<Element> modelElements = new ArrayList<Element> ();
			for (String id : ids) {
				 for (Element element : sourceElements) {
					if (id.equals(element.getId())) {
						modelElements.add(element);
					}
 				}
			}
			for (int index = 0; index < sourceElements.size(); index++) {
				Element elt = sourceElements.get(index);
				boolean found = false;
				for (Element element : modelElements) {
					if (element.getName().equals(elt.getName())) {
						found = true;
						break;
					}
				}
				if (!found) {
					elements.add(elt);
				}
			}

			Element[] namesArr = new Element[elements.size()];
			namesArr = elements.toArray(namesArr);
			return namesArr;
		} catch (IOException e) {
			ResourceManager.logException(e);
		}
		return new Element[0];
	}

	public void setVisible(boolean visible) {
		((GeneratorToFileCreationWizard) this.getWizard()).setGraphElementPage(null);
		setPageComplete(validatePage());
		super.setVisible(visible);
	}

	protected boolean validatePage() {
		((GeneratorToFileCreationWizard) this.getWizard()).setResourcePage(null);
		this.setErrorMessage(null);
		this.setMessage(null);
		setPageComplete(false);
		String msg  = validateSelection();
		if (msg!=null) {
			this.setErrorMessage(msg);
			return false;
		}
		setPageComplete(true);
		return true;
	}
 
	private String validateSelection() {
		Element[] currentTarget = (Element[]) viewerTarget.getInput();
		boolean shouldbeVertex = true;
		Vertex.RuntimeVertex lastVertex = null;
		Edge.RuntimeEdge lastEdge = null;
		for (int i = 0; i < currentTarget.length; i++) {
			Element model = currentTarget[i];
			if (shouldbeVertex) {
				shouldbeVertex = false;
				if (model instanceof Vertex.RuntimeVertex
						&& (((Vertex.RuntimeVertex) model).getSharedState() != null)) {
					return "Shared Vertex is not supported : " + ((Vertex.RuntimeVertex) model).getName();
				}
				if (model instanceof Vertex.RuntimeVertex) {
					lastVertex = (Vertex.RuntimeVertex) model;
					if (lastEdge != null) {
						if (!lastEdge.getTargetVertex().equals(lastVertex)) {
							return "Was expecting a vertex node : " + lastEdge.getTargetVertex().getName() + " after " + lastEdge.getName();
						}
					}
				} else {
					return "Was expecting a vertex node, but found : " + ((Edge.RuntimeEdge) model).getName();
				}
			} else {
				shouldbeVertex =  true;
				if (model instanceof Edge.RuntimeEdge) {
					lastEdge = (Edge.RuntimeEdge) model;
					Vertex.RuntimeVertex sourceV  = lastEdge.getSourceVertex();
					 
					if (!lastVertex.equals(sourceV)) {
						return "Was expecting an edge, but not this one : " + lastEdge.getName();
					}
				} else {
					return "Was expecting an edge after " + lastVertex.getName();
				}
			}
		}
		if (shouldbeVertex) return "Was expecting a vertex node after : " + lastEdge.getName();
		return null;
	}
}
