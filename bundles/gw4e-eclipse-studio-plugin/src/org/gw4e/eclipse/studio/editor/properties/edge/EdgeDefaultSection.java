package org.gw4e.eclipse.studio.editor.properties.edge;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.studio.editor.properties.SectionProvider;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.facade.ResourceManager;
import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.model.properties.AbstractGW4EEditPartProperties;
import org.gw4e.eclipse.studio.model.properties.EdgeGW4EEditPartProperties;
import org.gw4e.eclipse.studio.preference.PreferenceManager;

public class EdgeDefaultSection extends AbstractPropertySection implements PropertyChangeListener,  SectionWidgetID {
	
	private int START_LEFT = 10;

	private FormToolkit formToolkit;
	private Text textName;
	private Text textDescription;
	private Text textWeight;
	private Text textDependency;
	private ControlDecoration textWeightDecorator;
	private ControlDecoration textDependencyDecorator;
	private ControlDecoration txtNameDecorator;
	private Button btnCheckBlocked;
	 

	private SectionProvider node;
	private boolean notification = true; 

	Listener listener = new Listener() {
		public void handleEvent(Event e) {
			txtNameDecorator.hide();
			if (!notification)
				return;
			EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
					.getAdapter(IPropertySource.class);
			String value = textName.getText();
			if (!JDTManager.validateClassName(value.trim())) {
				txtNameDecorator.show();
				return;
			}
			properties.setPropertyValue(ModelProperties.PROPERTY_NAME, value);
		}
	};

	private Listener buttonBlockedListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			switch (e.type) {
			case SWT.Selection:
				EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
						.getAdapter(IPropertySource.class);
				properties.setPropertyValue(ModelProperties.PROPERTY_BLOCKED, btnCheckBlocked.getSelection());
				break;
			}
		}
	};

	Listener descriptionListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
					.getAdapter(IPropertySource.class);
			properties.setPropertyValue(ModelProperties.PROPERTY_DESCRIPTION, textDescription.getText());
		}
	};

	Listener weightListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
					.getAdapter(IPropertySource.class);
			textWeightDecorator.hide();
			String value = textWeight.getText();
			if (value != null && value.trim().length() > 0) {
				try {
					double d = Double.parseDouble(value.trim());
					if (d<0 || d>1) {
						throw new NumberFormatException();
					}
					properties.setPropertyValue(ModelProperties.PROPERTY_EDGE_WEIGHT, value.trim());
				} catch (NumberFormatException ex) {
					textWeightDecorator.show();
				}
			}
		}
	};
	
	
	Listener dependencyListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
					.getAdapter(IPropertySource.class);
			textDependencyDecorator.hide();
			String value = textDependency.getText();
			if (value != null && value.trim().length() > 0) {
				try {
					Integer d = Integer.parseInt(value.trim());
					if (d<0 || d>100) {
						throw new NumberFormatException();
					}
					properties.setPropertyValue(ModelProperties.PROPERTY_EDGE_DEPENDENCY, value.trim());
				} catch (NumberFormatException ex) {
					textDependencyDecorator.show();
				}
			}
		}
	};

	public EdgeDefaultSection() {
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		textWeightDecorator.hide();
		Object input = ((IStructuredSelection) selection).getFirstElement();
		this.node = (SectionProvider) input;
		AbstractGW4EEditPartProperties properties = (AbstractGW4EEditPartProperties) node
				.getAdapter(IPropertySource.class);
		textName.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_NAME));
 
		((GWNode) this.node.getModel()).removePropertyChangeListener(this);
		((GWNode) this.node.getModel()).addPropertyChangeListener(this);
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

	protected void fillComposite(Composite composite) {
		composite.setLayout(new FormLayout());

		workaround_383750 (composite);
		
		CLabel labelName = new CLabel(composite, SWT.NONE);
		labelName.setBackground(composite.getBackground());
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		fd_lblNewLabel.left = new FormAttachment(0, 10);
		fd_lblNewLabel.right = new FormAttachment(START_LEFT, 10);
		labelName.setLayoutData(fd_lblNewLabel);
		labelName.setText("Name:");

		textName =  getWidgetFactory().createText(composite, "");
		textName.setData(WIDGET_ID,WIDGET_TEXT_NAME);
		textName.addListener(SWT.FocusOut, listener);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(labelName, 20);
		fd_text.right = new FormAttachment(100, -5);
		fd_text.top = new FormAttachment(labelName, 0, SWT.CENTER);
		textName.setLayoutData(fd_text);
		formToolkit.adapt(textName, true, true);

		try {
			txtNameDecorator = new ControlDecoration(textName, SWT.TOP | SWT.LEFT);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			Image img = fieldDecoration.getImage();
			txtNameDecorator.setImage(img);
			txtNameDecorator.setShowHover(true);
			txtNameDecorator.setDescriptionText("Not a valid value");
			txtNameDecorator.hide();
		} catch (Exception e) {
		}
		
		
		btnCheckBlocked = new Button(composite, SWT.CHECK);
		btnCheckBlocked.setData(WIDGET_ID,WIDGET_BUTTON_BLOCKED);
		btnCheckBlocked.addListener(SWT.Selection, buttonBlockedListener);
		FormData fd_btnCheckBlocked = new FormData();
		fd_btnCheckBlocked.top = new FormAttachment(textName, 5);
		fd_btnCheckBlocked.left = new FormAttachment(labelName, 20);
		btnCheckBlocked.setLayoutData(fd_btnCheckBlocked);
		formToolkit.adapt(btnCheckBlocked, true, true);
		btnCheckBlocked.setText("");

		CLabel labelBlocked = new CLabel(composite, SWT.NONE);
		labelBlocked.setBackground(composite.getBackground());
		FormData fd_labelBlocked = new FormData();
		fd_labelBlocked.top = new FormAttachment(btnCheckBlocked, 0, SWT.CENTER);
		fd_labelBlocked.left = new FormAttachment(0, 10);
		fd_labelBlocked.right = new FormAttachment(START_LEFT, 10);
		labelBlocked.setLayoutData(fd_labelBlocked);
		labelBlocked.setText("Blocked:");

		textDescription = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textDescription.setData(WIDGET_ID,WIDGET_TEXT_DESCRIPTION);
		textDescription.addListener(SWT.FocusOut, descriptionListener);
		FormData fd_description = new FormData();
		fd_description.left = new FormAttachment(labelBlocked, 20);
		fd_description.right = new FormAttachment(100, -5);
		fd_description.top = new FormAttachment(btnCheckBlocked, 10);

		setHeight(fd_description, textDescription, PreferenceManager.getRowCountForEdgeTextDescription());
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

		
		textWeight = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		textWeight.setData(WIDGET_ID,WIDGET_TEXT_WEIGHT);
		textWeight.setToolTipText("Enter an optional value between 0 ansd 1");
		textWeight.addListener(SWT.FocusOut, weightListener);
		FormData fd_textWeight = new FormData();
		fd_textWeight.left = new FormAttachment(labelName, 20);
		fd_textWeight.right = new FormAttachment(100, -5);
		fd_textWeight.top = new FormAttachment(textDescription, 10);
		textWeight.setLayoutData(fd_textWeight);
		formToolkit.adapt(textWeight, true, true);
		
		CLabel labelWeight = new CLabel(composite, SWT.NONE);
		labelWeight.setBackground(composite.getBackground());
		FormData fd_lblWeight = new FormData();
		fd_lblWeight.top = new FormAttachment(textWeight, 0, SWT.CENTER);
		fd_lblWeight.left = new FormAttachment(0, 10);
		fd_lblWeight.right = new FormAttachment(START_LEFT, 10);
		labelWeight.setLayoutData(fd_lblWeight);
		labelWeight.setText("Weight:");

		try {
			textWeightDecorator = new ControlDecoration(textWeight, SWT.TOP | SWT.LEFT);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			Image img = fieldDecoration.getImage();
			textWeightDecorator.setImage(img);
			textWeightDecorator.setShowHover(true);
			textWeightDecorator.setDescriptionText("Not a valid value");
			textWeightDecorator.hide();
		} catch (Exception e) {
		}
		
		textDependency = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		textDependency.setData(WIDGET_ID,WIDGET_TEXT_DEPENDENCY);
		textDependency.setToolTipText("Enter an optional value between 0 ansd 100");
		textDependency.addListener(SWT.FocusOut, dependencyListener);
		FormData fd_textDependency = new FormData();
		fd_textDependency.left = new FormAttachment(labelName, 20);
		fd_textDependency.right = new FormAttachment(100, -5);
		fd_textDependency.top = new FormAttachment(textWeight, 10);
		textDependency.setLayoutData(fd_textDependency);
		formToolkit.adapt(textDependency, true, true);
		
		CLabel labelDependency = new CLabel(composite, SWT.NONE);
		labelDependency.setBackground(composite.getBackground());
		FormData fd_lblDependency = new FormData();
		fd_lblDependency.top = new FormAttachment(textDependency, 0, SWT.CENTER);
		fd_lblDependency.left = new FormAttachment(0, 10);
		fd_lblDependency.right = new FormAttachment(START_LEFT, 10);
		labelDependency.setLayoutData(fd_lblDependency);
		labelDependency.setText("Dependency:");

		try {
			textDependencyDecorator = new ControlDecoration(textDependency, SWT.TOP | SWT.LEFT);
			FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
					.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
			Image img = fieldDecoration.getImage();
			textDependencyDecorator.setImage(img);
			textDependencyDecorator.setShowHover(true);
			textDependencyDecorator.setDescriptionText("Not a valid value");
			textDependencyDecorator.hide();
		} catch (Exception e) {
		}
	}

	
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=383750
	private void workaround_383750 (Composite composite) {
		// 
		List dummy = new List(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		dummy.setBackground(composite.getBackground());
		FormData fd_dummy = new FormData();
		fd_dummy.top = new FormAttachment(0, 0);
		fd_dummy.left = new FormAttachment(0, 1);
		fd_dummy.right = new FormAttachment(0, 1);
		dummy.setLayoutData(fd_dummy);	
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
			EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node
					.getAdapter(IPropertySource.class);
			textName.setText(properties.getName());
			textDescription.setText(properties.getDescription());
			textWeight.setText("");
			textDependency.setText("");
			if (properties.getWeight() != null) {
				try {
					textWeight.setText(Double.toString(properties.getWeight()));
				} catch (Exception e) {
					ResourceManager.logException(e, "Unable to parse " + properties.getWeight() + " to a double");
				}
			}
			btnCheckBlocked.setSelection(properties.isBlocked());
			if (properties.getDependency() != null) {
				try {
					textDependency.setText(Integer.toString(properties.getDependency()));
				} catch (Exception e) {
					ResourceManager.logException(e, "Unable to parse " + properties.getDependency() + " to an integer");
				}
			}
			 
		} finally {
			notification = true;
		}
	}

	// Handle update of the properties view when the name is directly entered in
	// the Figure representing the GWEdge
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(GWEdge.PROPERTY_NAME_UPDATED))
			return;
		notification = false;
		try {
			if (textName.isDisposed())
				return;
			textName.setText(((GWNode) EdgeDefaultSection.this.node.getModel()).getName());
		} finally {
			notification = true;
		}
	}

}
