package org.gw4e.eclipse.studio.editor.properties.graph;

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

import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.studio.editor.properties.SectionProvider;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.model.properties.AbstractGW4EEditPartProperties;
import org.gw4e.eclipse.studio.model.properties.GW4EGraphEditPartProperties;

public class GraphDefaultSection extends AbstractPropertySection {

	private int START_LEFT = 10;

	private FormToolkit formToolkit;
	private Text textName;
	private SectionProvider node;
	private ComboViewer viewer;

	public GraphDefaultSection() {
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		this.node = (SectionProvider) input;
		AbstractGW4EEditPartProperties properties = (AbstractGW4EEditPartProperties) node
				.getAdapter(IPropertySource.class);
		textName.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_NAME));

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	protected void fillComposite(Composite composite) {
		composite.setLayout(new FormLayout());

		CLabel labelName = new CLabel(composite, SWT.NONE);
		labelName.setBackground(composite.getBackground());
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		fd_lblNewLabel.right = new FormAttachment(START_LEFT, 10);
		labelName.setLayoutData(fd_lblNewLabel);
		labelName.setText("Name:");

		textName = new Text(composite, SWT.BORDER);

		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(labelName, 5);
		fd_text.right = new FormAttachment(100, -5);
		fd_text.top = new FormAttachment(labelName, 0, SWT.CENTER);
		textName.setLayoutData(fd_text);
		formToolkit.adapt(textName, true, true);

		final CCombo combo = new CCombo(composite, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.BORDER);

		FormData fd_btnCheckBlocked = new FormData();
		fd_btnCheckBlocked.top = new FormAttachment(textName, 5);
		fd_btnCheckBlocked.left = new FormAttachment(labelName, 5);
		combo.setLayoutData(fd_btnCheckBlocked);
		combo.setData(SectionWidgetID.WIDGET_ID,SectionWidgetID.WIDGET_COMBO_EDGE);
		Rectangle rect = combo.getBounds();
		combo.setBounds(new Rectangle(rect.x,rect.y,rect.width,rect.height+4));
		CLabel labelBlocked = new CLabel(composite, SWT.NONE);
		labelBlocked.setBackground(composite.getBackground());
		FormData fd_labelBlocked = new FormData();
		fd_labelBlocked.top = new FormAttachment(combo, 0, SWT.CENTER);
		fd_labelBlocked.left = new FormAttachment(0, 10);
		fd_labelBlocked.right = new FormAttachment(START_LEFT, 10);
		labelBlocked.setLayoutData(fd_labelBlocked);
		labelBlocked.setText("Start Element:");

		viewer = new ComboViewer(combo);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof GWNode) {
					GWNode elt = (GWNode) element;
					return elt.getName();
				}
				return super.getText(element);
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				GWNode element = (GWNode)selection.getFirstElement(); 
				GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node.getAdapter(IPropertySource.class);
				GWNode startElement  = properties.getGraph().getStartElement();
				if (startElement!=null && startElement.equals(element)) return;
				IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
				p.setPropertyValue(ModelProperties.PROPERTY_GRAPH_START_ELEMENT,  element);
			}
		});
	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		formToolkit = new FormToolkit(parent.getDisplay());
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		fillComposite(composite);
	}

	public void refresh() {
		GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node.getAdapter(IPropertySource.class);
		textName.setText(properties.getName());

		Set<GWNode> elements = properties.getGraph().getLinks();
		Set<GWNode> vertices = properties.getGraph().getVertices();
		elements.addAll(vertices);
		
		GWNode[] items = new GWNode[elements.size()];
		elements.toArray(items);
		viewer.setInput(items);
		
		GWNode startElement  = properties.getGraph().getStartElement();
		if (startElement!=null) {
			 viewer.setSelection(new StructuredSelection(startElement), true);
		}
	}
}
