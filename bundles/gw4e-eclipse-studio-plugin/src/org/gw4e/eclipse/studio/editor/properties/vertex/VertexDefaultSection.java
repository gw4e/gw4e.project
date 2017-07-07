package org.gw4e.eclipse.studio.editor.properties.vertex;

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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.constant.Constant;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.studio.editor.properties.SectionProvider;
import org.gw4e.eclipse.studio.editor.properties.SectionWidgetID;
import org.gw4e.eclipse.studio.model.GWNode;
import org.gw4e.eclipse.studio.model.ModelProperties;
import org.gw4e.eclipse.studio.model.Vertex;
import org.gw4e.eclipse.studio.model.properties.AbstractGW4EEditPartProperties;
import org.gw4e.eclipse.studio.model.properties.GW4EVertexEditPartProperties;
import org.gw4e.eclipse.studio.preference.PreferenceManager;

public class VertexDefaultSection extends AbstractPropertySection implements PropertyChangeListener,  SectionWidgetID {
	
	private int START_LEFT = 10;

	private FormToolkit formToolkit;
	private Text textName;
	private StyledText textDescription;
	private Text textRequirements;
	private Text textSharedName;
	
	private Button btnCheckShrd;
	private Button btnCheckBlocked;
	private SectionProvider sectionProvider;
	private ControlDecoration txtNameDecorator;
	private ControlDecoration txtSharedNameDecorator;
	private boolean notification = true;

	 
	FocusListener textNameFocusOutListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (!notification)
				return;
			GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
					.getAdapter(IPropertySource.class);
			
			txtNameDecorator.hide();
			
			String value = textName.getText();
			if (value == null || value.trim().length() == 0 || (!Character.isJavaIdentifierStart(value.charAt(0)))) {
				txtNameDecorator.show();
				return;
			}
			if (Constant.START_VERTEX_NAME.equalsIgnoreCase(value.trim())) {
				txtNameDecorator.show();
				return;
			}
			int max = value.length();
			for (int i = 1; i < max; i++) {
				if ((!Character.isJavaIdentifierPart(value.charAt(i)))) {
					txtNameDecorator.show();
					return;
				}
			}
			if (!JDTManager.validateClassName(value)) {
				txtNameDecorator.show();
				return;
			}
			
			properties.setPropertyValue(ModelProperties.PROPERTY_NAME, value);
		}
	};
	private Listener buttonSharedListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			switch (e.type) {
			case SWT.Selection:
				GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
						.getAdapter(IPropertySource.class);
				properties.setPropertyValue(ModelProperties.PROPERTY_VERTEX_SHARED, btnCheckShrd.getSelection());
				break;
			}
		}
	};

	private Listener buttonBlockedListener = new Listener() {
		public void handleEvent(Event e) {
			if (!notification)
				return;
			switch (e.type) {
			case SWT.Selection:
				GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
						.getAdapter(IPropertySource.class);
				properties.setPropertyValue(ModelProperties.PROPERTY_BLOCKED, btnCheckBlocked.getSelection());
				break;
			}
		}
	};

	FocusListener descriptionListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!notification)
					return;
				GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
						.getAdapter(IPropertySource.class);
				properties.setPropertyValue(ModelProperties.PROPERTY_DESCRIPTION, textDescription.getText());
			}
	};
	
	FocusListener requirementListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (!notification)
				return;
			GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
					.getAdapter(IPropertySource.class);
			properties.setPropertyValue(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS, textRequirements.getText());
		}
	};
	
	FocusListener sharedTextListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (!notification)
				return;
			GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
					.getAdapter(IPropertySource.class);

			txtSharedNameDecorator.hide();

			String value = textSharedName.getText();
			if (value == null || value.trim().length() == 0) {
				txtSharedNameDecorator.show();
				return;
			}
			
			properties.setPropertyValue(ModelProperties.PROPERTY_VERTEX_SHAREDNAME,value);
		}
	};
			
			
	
	public VertexDefaultSection() {
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		txtNameDecorator.hide();
		txtSharedNameDecorator.hide();
		Object input = ((IStructuredSelection) selection).getFirstElement();
		this.sectionProvider = (SectionProvider) input;
		AbstractGW4EEditPartProperties properties = (AbstractGW4EEditPartProperties) sectionProvider
				.getAdapter(IPropertySource.class);
		textName.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_NAME));
		btnCheckShrd.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_VERTEX_SHARED));
		btnCheckBlocked.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_BLOCKED));
		textDescription.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_DESCRIPTION));
		textRequirements.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_VERTEX_REQUIREMENTS));
		textSharedName.setEnabled(properties.isUpdatable(ModelProperties.PROPERTY_VERTEX_SHAREDNAME));
		((GWNode) this.sectionProvider.getModel()).removePropertyChangeListener(this);
		((GWNode) this.sectionProvider.getModel()).addPropertyChangeListener(this);
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

		textName = new Text(composite, SWT.BORDER);
		textName.setData(WIDGET_ID,WIDGET_TEXT_NAME);
		textName.addFocusListener(textNameFocusOutListener);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(labelName, 20);
		fd_text.right = new FormAttachment(100, -5);
		fd_text.top = new FormAttachment(labelName, 0, SWT.CENTER);
		textName.setLayoutData(fd_text);
		formToolkit.adapt(textName, true, true);

		textName.addKeyListener(new KeyAdapter() {
		      public void keyPressed(KeyEvent event) {
		        switch (event.keyCode) {
		        case SWT.CR:
		          System.out.println(SWT.CR);
		        case SWT.ESC:
		          System.out.println(SWT.ESC);
		          break;
		        }
		      }
		    });
		
		txtNameDecorator = new ControlDecoration(textName, SWT.TOP | SWT.LEFT);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		Image img = fieldDecoration.getImage();
		txtNameDecorator.setImage(img);
		txtNameDecorator.setShowHover(true);
		txtNameDecorator.setDescriptionText("Not a valid name");
		txtNameDecorator.hide();
		
		btnCheckShrd = new Button(composite, SWT.CHECK);
		FormData fd_btnCheckShrd = new FormData();
		fd_btnCheckShrd.top = new FormAttachment(textName, 5);
		fd_btnCheckShrd.left = new FormAttachment(labelName, 20);
		btnCheckShrd.setLayoutData(fd_btnCheckShrd);
		formToolkit.adapt(btnCheckShrd, true, true);
		btnCheckShrd.setText("");
		btnCheckShrd.addListener(SWT.Selection, buttonSharedListener);
		btnCheckShrd.setData(WIDGET_ID,WIDGET_BUTTON_SHARED);

		CLabel labelShrd = new CLabel(composite, SWT.NONE);
		labelShrd.setBackground(composite.getBackground());
		FormData fd_labelShrd = new FormData();
		fd_labelShrd.top = new FormAttachment(btnCheckShrd, 0, SWT.CENTER);
		fd_labelShrd.left = new FormAttachment(0, 10);
		fd_labelShrd.right = new FormAttachment(START_LEFT, 10);
		labelShrd.setLayoutData(fd_labelShrd);
		labelShrd.setText("Shared:");

		textSharedName = new Text(composite, SWT.BORDER);
		textSharedName.addFocusListener(sharedTextListener);
		textSharedName.setData(WIDGET_ID,WIDGET_TEXT_SHAREDNAME);
		FormData fd_sharedName = new FormData();
		fd_sharedName.left = new FormAttachment(btnCheckShrd, 20);
		fd_sharedName.right = new FormAttachment(100, -5);
		fd_sharedName.top =  new FormAttachment(btnCheckShrd, 0, SWT.CENTER);
		textSharedName.setLayoutData(fd_sharedName);
		
		txtSharedNameDecorator = new ControlDecoration(textSharedName, SWT.TOP | SWT.LEFT);
		FieldDecoration fieldDecoration1 = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		Image img1 = fieldDecoration1.getImage();
		txtSharedNameDecorator.setImage(img1);
		txtSharedNameDecorator.setShowHover(true);
		txtSharedNameDecorator.setDescriptionText("Not a valid name");
		txtSharedNameDecorator.hide();
		
		btnCheckBlocked = new Button(composite, SWT.CHECK);
		btnCheckBlocked.addListener(SWT.Selection, buttonBlockedListener);
		btnCheckBlocked.setData(WIDGET_ID,WIDGET_BUTTON_BLOCKED);
		FormData fd_btnCheckBlocked = new FormData();
		fd_btnCheckBlocked.top = new FormAttachment(btnCheckShrd, 5);
		fd_btnCheckBlocked.left = new FormAttachment(labelShrd, 20);
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

		textDescription = new StyledText(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textDescription.addFocusListener(descriptionListener);
		textDescription.setData(WIDGET_ID,WIDGET_TEXT_DESCRIPTION);
		FormData fd_description = new FormData();
		fd_description.left = new FormAttachment(labelBlocked, 20);
		fd_description.right = new FormAttachment(100, -5);
		fd_description.top = new FormAttachment(btnCheckBlocked, 10);
		
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

		textRequirements = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textRequirements.addFocusListener(requirementListener);
		textRequirements.setData(WIDGET_ID,WIDGET_TEXT_REQUIREMENTS);
		FormData fd_requirements = new FormData();
		fd_requirements.left = new FormAttachment(labelDescription, 20);
		fd_requirements.right = new FormAttachment(100, -5);
		fd_requirements.top = new FormAttachment(textDescription, 10);

		setHeight(fd_requirements, textRequirements, PreferenceManager.getRowCountForVertexTextRequirements());
		textRequirements.setLayoutData(fd_requirements);
		formToolkit.adapt(textRequirements, true, true);

		CLabel labelRequirements = new CLabel(composite, SWT.NONE);
		labelRequirements.setBackground(composite.getBackground());
		FormData fd_lblRequirements = new FormData();
		fd_lblRequirements.top = new FormAttachment(textRequirements, 0, SWT.CENTER);
		fd_lblRequirements.left = new FormAttachment(0, 10);
		fd_lblRequirements.right = new FormAttachment(START_LEFT, 10);
		labelRequirements.setLayoutData(fd_lblRequirements);
		labelRequirements.setText("Requirements:");
	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		formToolkit = new FormToolkit(parent.getDisplay());
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);
		fillComposite(composite);
	}

	public void refresh() {
		notification = false;
		GW4EVertexEditPartProperties properties = (GW4EVertexEditPartProperties) sectionProvider
				.getAdapter(IPropertySource.class);
		try {
			textName.setText(properties.getName());
			btnCheckShrd.setSelection(properties.isShared());
			textSharedName.setText(properties.getSharedName() == null ? "" : properties.getSharedName());
			btnCheckBlocked.setSelection(properties.isBlocked());
			textDescription.setText(properties.getDescription());
			textRequirements.setText(properties.getRequirement().getValue());
		} finally {
			notification = true;
		}
	}

	
	// Handle update of the properties view when the name is directly entered in the Figure representing the Vertex
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(Vertex.PROPERTY_NAME_UPDATED)) return;
		notification = false;
		try {
			if (textName.isDisposed()) return;
			textName.setText(((GWNode) VertexDefaultSection.this.sectionProvider.getModel()).getName());
		} finally {
			notification = true;
		}
		textNameFocusOutListener.focusLost(null);
	}
}
