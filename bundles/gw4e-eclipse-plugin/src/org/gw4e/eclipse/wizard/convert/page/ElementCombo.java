package org.gw4e.eclipse.wizard.convert.page;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.graphwalker.core.machine.Context;
import org.graphwalker.core.model.Edge.RuntimeEdge;
import org.graphwalker.core.model.Element;
import org.graphwalker.core.model.Vertex.RuntimeVertex;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;

public class ElementCombo extends Composite {

	/**
	 * 
	 */
	private Context context = null;

	/**
	 * 
	 */
	private ComboViewer comboViewer;

	/**
	 * 
	 */
	private Combo comboElement;

	/**
	 * 
	 */
	Label labelStartElement;

	/**
	 * 
	 */
	private Element startElement = null;

	/**
	 * 
	 */
	Listener listener;

	public ElementCombo(Composite parent, int style, boolean label, Listener listener) {
		super(parent, style);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);

		this.listener = listener;
		if (label) {
			labelStartElement = new Label(this, SWT.BORDER);
			labelStartElement.setText(MessageUtil.getString("StartElement"));
			GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
			gd.horizontalIndent = 25;
			labelStartElement.setLayoutData(gd);
			labelStartElement.setEnabled(false);
		}
		comboViewer = new ComboViewer(this);
		comboElement = comboViewer.getCombo();
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.horizontalIndent = 25;
		comboElement.setLayoutData(gd);
		comboElement.setEnabled(false);
		comboViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				List<Element> loadElements = (List<Element>) inputElement;
				Object[] ret = new Object[loadElements.size()];
				loadElements.toArray(ret);
				return ret;
			}
		});
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Element) {
					Element elt = (Element) element;
					return elt.getName();
				}
				return "?";
			}
		});
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() > 0) {
					startElement = (Element) selection.getFirstElement();
					Event evt = new Event();
					evt.data = comboViewer;
					listener.handleEvent(evt);
				}
			}
		});
	}

	/**
	 * @return the comboViewer
	 */
	public ComboViewer getComboViewer() {
		return comboViewer;
	}

	/**
	 * @return the comboElement
	 */
	public Combo getCombo() {
		return comboElement;
	}

	/**
	 * @return the labelStartElement
	 */
	public Label getLabelStartElement() {
		return labelStartElement;
	}

	/**
	 * @return the startElement
	 */
	public Element getStartElement() {
		return startElement;
	}

	/**
	 * @return
	 */
	public boolean hasStartElement() {
		return (comboElement.getItemCount() > 0);
	}

	public void loadElements(IStructuredSelection selection) {
		Display display = Display.getCurrent();
		Runnable longJob = new Runnable() {
			public void run() {
				display.syncExec(new Runnable() {
					public void run() {
						IFile ifile = ResourceManager.toIFile(selection);
						String modelFileName = null;

						try {
							modelFileName = ResourceManager.getAbsolutePath(ifile);
							context = GraphWalkerFacade.getContext(modelFileName);
						} catch (Exception e) {
							ResourceManager.logException(e);
							Event evt = new Event();
							evt.text = MessageUtil.getString("unable_to_load_graph_file_see_error_view");
							listener.handleEvent(evt);
							return;
						}
						comboViewer.setInput(_loadElements(context));
						Element startElement = context.getNextElement();
						if (startElement != null) {
							final ISelection selection = new StructuredSelection(startElement);
							comboViewer.setSelection(selection, true);
						}
						listener.handleEvent(null);
					}
				});
				display.wake();
			}
		};
		BusyIndicator.showWhile(display, longJob);
	}

	private List<Element> _loadElements(Context context) {

		List<RuntimeEdge> all = context.getModel().getEdges();
		List<Element> ret = new ArrayList<Element>();
		Map stored = new HashMap();
		for (RuntimeEdge runtimeEdge : all) {
			if (runtimeEdge.getName() == null)
				continue;
			if (stored.get(runtimeEdge.getName()) != null)
				continue;
			stored.put(runtimeEdge.getName(), runtimeEdge);
			ret.add(runtimeEdge);
		}
		List<RuntimeVertex> vertices = context.getModel().getVertices();
		stored = new HashMap();
		for (RuntimeVertex runtimeVertex : vertices) {
			if (runtimeVertex.getName() == null || runtimeVertex.getName().trim().length() == 0)
				continue;
			if (stored.get(runtimeVertex.getName()) != null)
				continue;
			stored.put(runtimeVertex.getName(), runtimeVertex);
			ret.add(runtimeVertex);
		}
		Collections.sort(ret, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Element e1 = (Element) o1;
				Element e2 = (Element) o2;

				return e1.getName().compareToIgnoreCase(e2.getName());
			}

		});
		return ret;

	}

}
