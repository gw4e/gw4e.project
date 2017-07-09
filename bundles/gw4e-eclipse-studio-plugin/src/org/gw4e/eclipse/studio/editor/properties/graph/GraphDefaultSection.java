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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.studio.editor.properties.SectionProvider;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWLink;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.properties.AbstractGW4EEditPartProperties;
import org.gw4e.eclipse.studio.model.properties.GW4EGraphEditPartProperties;
import org.gw4e.eclipse.studio.model.properties.GW4EVertexEditPartProperties;
import org.gw4e.eclipse.studio.preference.PreferenceManager;

public class GraphDefaultSection extends AbstractPropertySection implements SectionWidgetID {

	private int START_LEFT = 10;

	private FormToolkit formToolkit;
	private Text textName;
	private Text textComponent;
	private StyledText textDescription;
	private SectionProvider node;
	private ComboViewer viewer;
	private boolean notification = true;
 

	FocusListener descriptionListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (!notification)
				return;
			GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node
					.getAdapter(IPropertySource.class);
			properties.setPropertyValue(ModelProperties.PROPERTY_DESCRIPTION, textDescription.getText());
		}
	};

	FocusListener componentListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (!notification)
				return;
			GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node
					.getAdapter(IPropertySource.class);
			properties.setPropertyValue(ModelProperties.PROPERTY_COMPONENT, textComponent.getText());
		}
	};
	
	public GraphDefaultSection() {
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		this.node = (SectionProvider) input;
		AbstractGW4EEditPartProperties properties = (AbstractGW4EEditPartProperties) node
				.getAdapter(IPropertySource.class);
		textName.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_NAME));
		textDescription.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_DESCRIPTION));
		textComponent.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_COMPONENT));
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
		
		textComponent = new Text(composite, SWT.BORDER);
		textComponent.addFocusListener(componentListener);
		FormData fd_component = new FormData();
		fd_component.left = new FormAttachment(labelName, 5);
		fd_component.right = new FormAttachment(100, -5);
		fd_component.top = new FormAttachment(labelName, 0, 5);
		textComponent.setLayoutData(fd_component);
		formToolkit.adapt(textComponent, true, true);

		CLabel labelComponent= new CLabel(composite, SWT.NONE);
		labelComponent.setBackground(composite.getBackground());
		FormData fd_lblComponentl = new FormData();
		fd_lblComponentl.top = new FormAttachment(textComponent, 0, SWT.CENTER);
		fd_lblComponentl.left = new FormAttachment(0, 10);
		fd_lblComponentl.right = new FormAttachment(START_LEFT, 10);
		labelComponent.setLayoutData(fd_lblComponentl);
		labelComponent.setText("Component:");
		
		final CCombo combo = new CCombo(composite, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.BORDER);

		FormData fd_btnCheckBlocked = new FormData();
		fd_btnCheckBlocked.top = new FormAttachment(textComponent, 5);
		fd_btnCheckBlocked.left = new FormAttachment(labelComponent, 5);
		combo.setLayoutData(fd_btnCheckBlocked);
		combo.setData(SectionWidgetID.WIDGET_ID, SectionWidgetID.WIDGET_COMBO_EDGE);
		Rectangle rect = combo.getBounds();
		combo.setBounds(new Rectangle(rect.x, rect.y, rect.width, rect.height + 4));
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
				GWNode element = (GWNode) selection.getFirstElement();
				GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node
						.getAdapter(IPropertySource.class);
				GWNode startElement = properties.getGraph().getStartElement();
				if (startElement != null && startElement.equals(element))
					return;
				IPropertySource p = (IPropertySource) node.getAdapter(IPropertySource.class);
				p.setPropertyValue(ModelProperties.PROPERTY_GRAPH_START_ELEMENT, element);
			}
		});

		textDescription = new StyledText(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textDescription.addFocusListener(descriptionListener);
		textDescription.setData(WIDGET_ID, WIDGET_TEXT_DESCRIPTION);
		FormData fd_description = new FormData();
		fd_description.left = new FormAttachment(labelBlocked, 5);
		fd_description.right = new FormAttachment(100, -5);
		fd_description.top = new FormAttachment(combo, 10);

		setHeight(fd_description, textDescription, PreferenceManager.getRowCountForVertexTextDescription());
		textDescription.setLayoutData(fd_description);
		formToolkit.adapt(textDescription, true, true);

		CLabel labelDescription = new CLabel(composite, SWT.NONE);
		labelDescription.setBackground(composite.getBackground());
		FormData fd_lblDescription = new FormData();
		fd_lblDescription.top = new FormAttachment(textDescription, 0, SWT.CENTER);
		fd_lblDescription.left = new FormAttachment(0, 10);
		fd_lblDescription.right = new FormAttachment(START_LEFT, 10);
		labelDescription.setLayoutData(fd_lblDescription);
		labelDescription.setText("Description:");

	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		formToolkit = new FormToolkit(parent.getDisplay());
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		fillComposite(composite);
	}

	public void refresh() {
		notification = false;
		try {
			GW4EGraphEditPartProperties properties = (GW4EGraphEditPartProperties) node
					.getAdapter(IPropertySource.class);
			textName.setText(properties.getName());

			Set<GWNode> elements = properties.getGraph().getLinks();
			Set<GWNode> vertices = properties.getGraph().getVertices();
			elements.addAll(vertices);

			GWNode[] items = new GWNode[elements.size()];
			elements.toArray(items);
			viewer.setInput(items);

			GWNode startElement = properties.getGraph().getStartElement();
			if (startElement != null) {
				viewer.setSelection(new StructuredSelection(startElement), true);
			}
			textDescription.setText(properties.getDescription());
			textComponent.setText(properties.getComponent());
		} finally {
			notification = true;
		}
	}

	private void setHeight(FormData fd, Control control, int rowcount) {
		GC gc = new GC(control);
		try {
			gc.setFont(control.getFont());
			FontMetrics fm = gc.getFontMetrics();
			fd.height = rowcount * fm.getHeight();
		} finally {
			gc.dispose();
		}
	}

}
