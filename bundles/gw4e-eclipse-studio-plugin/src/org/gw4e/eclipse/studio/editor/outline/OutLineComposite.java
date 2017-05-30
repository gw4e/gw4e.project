package org.gw4e.eclipse.studio.editor.outline;

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

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.gw4e.eclipse.studio.editor.outline.filter.OutLineFilter;
import org.gw4e.eclipse.studio.editor.outline.filter.ThreeStateChoice;
import org.gw4e.eclipse.studio.editor.outline.filter.ThreeStateComboChoice;

public class OutLineComposite extends Composite {
	 
	public static final String GW_WIDGET_ID = "GW_WIDGET_ID";
	public static final String GW_OUTLINE_NAME_TEXT = "GW_OUTLINE_NAME_TEXT";
	public static final String GW_OUTLINE_DESCRIPTION_TEXT = "GW_OUTLINE_DESCRIPTION_TEXT";
	public static final String GW_OUTLINE_REQUIREMENT_TEXT = "GW_OUTLINE_REQUIREMENT_TEXT";
	public static final String GW_OUTLINE_WEIGHT_TEXT = "GW_OUTLINE_WEIGHT_TEXT";
	public static final String GW_OUTLINE_FILTER_BUTTON = "GW_OUTLINE_FILTER_BUTTON";
	public static final String GW_OUTLINE_BLOCKED_COMBO = "GW_OUTLINE_BLOCKED_COMBO";
	public static final String GW_OUTLINE_INITSCRIPT_COMBO = "GW_OUTLINE_INITSCRIPT_COMBO";
	public static final String GW_OUTLINE_SHARED_COMBO = "GW_OUTLINE_SHARED_COMBO";
	public static final String GW_OUTLINE_GUARD_COMBO = "GW_OUTLINE_GUARD_COMBO";
	public static final String GW_OUTLINE_ACTION_COMBO = "GW_OUTLINE_ACTION_COMBO";
	public static final String GW_OUTLINE_VERTEX_EXPAND = "GW_OUTLINE_VERTEX_EXPAND";
	public static final String GW_OUTLINE_EDGE_EXPAND = "GW_OUTLINE_EDGE_EXPAND";
	public static final String GW_OUTLINE_WEIGHT_OPERATOR_COMBO = "GW_OUTLINE_WEIGHT_OPERATOR_COMBO";
	
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite composite;
	private Text nameText;
	private Text descriptionText;
	private Text requirementText;
	private Text weightText;
	private ControlDecoration textWeightDecorator;
	private Button btnFilterCheckButton;
	private ThreeStateComboChoice comboBlocked;
	private ThreeStateComboChoice comboInitScripted;
	private ThreeStateComboChoice comboShared;
	private ThreeStateComboChoice comboGuard;
	private ThreeStateComboChoice comboAction;
	private ExpandableComposite expVertexComp;
	private ExpandableComposite expEdgeComp;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public OutLineComposite(final OutLineFilter filter, Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(10, false));

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 10;
		btnFilterCheckButton = new Button(this, SWT.CHECK);
		btnFilterCheckButton.setLayoutData(gridData);
		btnFilterCheckButton.setText("Filter");
		btnFilterCheckButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					updateEnableStatus (btnFilterCheckButton.getSelection());
					filter.setFilterOn(btnFilterCheckButton.getSelection());
					break;
				}
			}
		});
		btnFilterCheckButton.setData(GW_WIDGET_ID ,GW_OUTLINE_FILTER_BUTTON)  ;
		 
		btnFilterCheckButton.setToolTipText("Check the box to enter filtering criteria");
		toolkit.adapt(btnFilterCheckButton, true, true);
		
		
		CLabel labelName = new CLabel(this, SWT.NONE);
		GridData gridData23 = new GridData();
		gridData23.horizontalSpan = 3;
		labelName.setLayoutData(gridData23);
		labelName.setText("Name:");
		labelName.setToolTipText("Graph element 'Name'");
		toolkit.adapt(labelName, true, true);
		
		GridData gridData24 = new GridData();
		gridData24.horizontalAlignment = SWT.FILL;
		gridData24.grabExcessHorizontalSpace = true;
		gridData24.horizontalSpan = 7;
		nameText = new Text(this, SWT.BORDER);
		nameText.setLayoutData(gridData24);
		nameText.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
		    	  filter.setNameText(nameText.getText());
		        }
		});
		nameText.setToolTipText("Enter characters that should be contained in the graph element name");
		nameText.setData(GW_WIDGET_ID ,GW_OUTLINE_NAME_TEXT)  ;
		
		
		toolkit.adapt(nameText, true, true);
		
		CLabel labelDescription = new CLabel(this, SWT.NONE);
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = 3;
		labelDescription.setLayoutData(gridData1);
		labelDescription.setText("Desc:");
		labelDescription.setToolTipText("Graph element description");
		toolkit.adapt(labelDescription, true, true);
		
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = SWT.FILL;
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalSpan = 7;
		descriptionText = new Text(this, SWT.BORDER);
		descriptionText.setLayoutData(gridData2);
		descriptionText.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
		    	  filter.setDescription(descriptionText.getText());
		        }
		});
		descriptionText.setToolTipText("Enter characters that should be contained in the graph element description");
		descriptionText.setData(GW_WIDGET_ID ,GW_OUTLINE_DESCRIPTION_TEXT)  ;
		
		toolkit.adapt(descriptionText, true, true);
		
		CLabel labelBlocked = new CLabel(this, SWT.NONE);
		GridData gridData3 = new GridData();
		gridData3.horizontalSpan = 3;
		labelBlocked.setLayoutData(gridData3);
		labelBlocked.setText("Blocked:");
		labelBlocked.setToolTipText("Graph element 'Blocked' property");
		toolkit.adapt(labelBlocked, true, true);
		 
		ISelectionChangedListener blockedListener = new ISelectionChangedListener() {
		        @Override
		        public void selectionChanged(SelectionChangedEvent event) {
		            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		            ThreeStateChoice choice = (ThreeStateChoice)selection.getFirstElement();
		            filter.setBlocked(choice);
		             
		        }
		};

		comboBlocked = new ThreeStateComboChoice (this,blockedListener);
		comboBlocked.setData (GW_WIDGET_ID,GW_OUTLINE_BLOCKED_COMBO);
		
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = SWT.FILL;
		gridData4.grabExcessHorizontalSpace = true;
		gridData4.horizontalSpan = 7;
		comboBlocked.initialize(gridData4);
		comboBlocked.getControl().setToolTipText("Select the value you want the 'Blocked' property should match. Leave empty if you don't care about this criteria");
		toolkit.adapt(comboBlocked.getControl(), true, true);
		
		expVertexComp = new ExpandableComposite(this, ExpandableComposite.TWISTIE);
		expVertexComp.setData(GW_WIDGET_ID ,GW_OUTLINE_VERTEX_EXPAND)  ;
		expVertexComp.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				OutLineComposite.this.pack();
				OutLineComposite.this.setSize(OutLineComposite.this.getParent().getSize());
			}
		});
		

		expVertexComp.setText("Vertex Filters");
		expVertexComp.setExpanded(true);
		GridData gridDataExComp = new GridData();
		gridDataExComp.horizontalAlignment = SWT.FILL;
		gridDataExComp.grabExcessHorizontalSpace = true;
		gridDataExComp.horizontalSpan = 10;
		expVertexComp.setLayoutData(gridDataExComp);
		expVertexComp.setToolTipText("A section to set criteria specific to Vertices");

		toolkit.adapt(expVertexComp, true, true);
		
		Composite compo = new Composite(expVertexComp, SWT.NONE);
		compo.setLayout(new GridLayout(10, false));
		GridData gridDataCompo = new GridData();
		gridDataCompo.horizontalAlignment = SWT.FILL;
		gridDataCompo.grabExcessHorizontalSpace = true;
		gridDataCompo.horizontalSpan = 10;
		compo.setLayoutData(gridDataCompo);
		toolkit.adapt(compo, true, true);
		expVertexComp.setClient(compo);

		CLabel labelReq = new CLabel(compo, SWT.NONE);
		GridData gridData5 = new GridData();
		gridData5.horizontalSpan = 3;
		labelReq.setLayoutData(gridData5);
		labelReq.setText("Req:");
		labelReq.setToolTipText("Graph element 'Requirement' property");
		toolkit.adapt(labelReq, true, true);
		
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = SWT.FILL;
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.horizontalSpan = 7;
		requirementText = new Text(compo, SWT.BORDER);
		requirementText.setLayoutData(gridData6);
		requirementText.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
		    	  filter.setRequirement(requirementText.getText());
		        }
		});
		requirementText.setToolTipText("Select the value you want the 'Requirement' property should match. Leave empty if you don't care about this criteria");
		requirementText.setData(GW_WIDGET_ID ,GW_OUTLINE_REQUIREMENT_TEXT)  ;
		
		toolkit.adapt(requirementText, true, true);
		
		CLabel labelShared = new CLabel(compo, SWT.NONE);
		GridData gridData7 = new GridData();
		gridData7.horizontalSpan = 3;
		labelShared.setLayoutData(gridData7);
		labelShared.setText("Shared:");
		labelShared.setToolTipText("Graph element 'Shared' property");
		toolkit.adapt(labelShared, true, true);
		
		
		ISelectionChangedListener sharedListener = new ISelectionChangedListener() {
	        @Override
	        public void selectionChanged(SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	            ThreeStateChoice choice = (ThreeStateChoice)selection.getFirstElement();
	            filter.setShared(choice);
	        }
		};

		comboShared = new ThreeStateComboChoice (compo,sharedListener);
		comboShared.setData(GW_WIDGET_ID ,GW_OUTLINE_SHARED_COMBO)  ;
		comboShared.getControl().setToolTipText("Select the value you want the 'Shared' property should match. Leave empty if you don't care about this criteria");
		 
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = SWT.FILL;
		gridData8.grabExcessHorizontalSpace = true;
		gridData8.horizontalSpan = 7;
		comboShared.initialize(gridData8);
		toolkit.adapt(comboShared.getControl(), true, true);
		
		CLabel labelInit = new CLabel(compo, SWT.NONE);
		GridData gridData22 = new GridData();
		gridData22.horizontalSpan = 3;
		labelInit.setLayoutData(gridData22);
		labelInit.setText("Init:");
		labelInit.setToolTipText("Graph element 'Init' property");
		toolkit.adapt(labelInit, true, true);
		
		
		ISelectionChangedListener initScriptListener = new ISelectionChangedListener() {
	        @Override
	        public void selectionChanged(SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	            ThreeStateChoice choice = (ThreeStateChoice)selection.getFirstElement();
	            filter.setInitScript(choice);
	        }
		};

		comboInitScripted = new ThreeStateComboChoice (compo,initScriptListener);
		comboInitScripted.setData(GW_WIDGET_ID ,GW_OUTLINE_INITSCRIPT_COMBO)  ;
		comboInitScripted.getControl().setToolTipText("Select the value you want for the 'Init' property (Whether there is script or not). Leave empty if you don't care about this criteria");

		GridData gridData21 = new GridData();
		gridData21.horizontalAlignment = SWT.FILL;
		gridData21.grabExcessHorizontalSpace = true;
		gridData21.horizontalSpan = 7;
		comboInitScripted.initialize(gridData21);
		toolkit.adapt(comboInitScripted.getControl(), true, true);
		
		expEdgeComp = new ExpandableComposite(this, ExpandableComposite.TWISTIE);
		expEdgeComp.setData(GW_WIDGET_ID ,GW_OUTLINE_EDGE_EXPAND)  ;
		expEdgeComp.setToolTipText("A section to set criteria specific to Edges");

		expEdgeComp.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				OutLineComposite.this.pack();
				OutLineComposite.this.setSize(OutLineComposite.this.getParent().getSize());
			}
		});
		
		expEdgeComp.setText("Edge Filters");
		expEdgeComp.setExpanded(true);
		GridData gridDataEdgeExComp = new GridData();
		gridDataEdgeExComp.horizontalAlignment = SWT.FILL;
		gridDataEdgeExComp.grabExcessHorizontalSpace = true;
		gridDataEdgeExComp.horizontalSpan = 10;
		expEdgeComp.setLayoutData(gridDataEdgeExComp);
		toolkit.adapt(expEdgeComp, true, true);
		
		Composite compoEdge = new Composite(expEdgeComp, SWT.NONE);
		compoEdge.setLayout(new GridLayout(10, false));
		GridData gridDatacompoEdge = new GridData();
		gridDatacompoEdge.horizontalAlignment = SWT.FILL;
		gridDatacompoEdge.grabExcessHorizontalSpace = true;
		gridDatacompoEdge.horizontalSpan = 10;
		compoEdge.setLayoutData(gridDatacompoEdge);
		toolkit.adapt(compoEdge, true, true);
		expEdgeComp.setClient(compoEdge);

		
		CLabel labelWeight = new CLabel(compoEdge, SWT.NONE);
		GridData gridData13 = new GridData();
		gridData13.horizontalSpan = 3;
		labelWeight.setLayoutData(gridData13);
		labelWeight.setText("Weight:");
		labelWeight.setToolTipText("Graph element 'Weight' property");
		toolkit.adapt(labelWeight, true, true);
		
		Composite compoWeight = new Composite (compoEdge ,SWT.None);
		compoWeight.setLayout(new GridLayout(10, false));
		GridData gridData14 = new GridData();
		gridData14.verticalAlignment = SWT.TOP;
		gridData14.horizontalAlignment = SWT.FILL;
		gridData14.grabExcessHorizontalSpace = true;
		gridData14.horizontalSpan = 7;
		compoWeight.setLayoutData(gridData14);
		toolkit.adapt(compoWeight, true, true);

		Combo combo = new Combo (compoWeight, SWT.READ_ONLY);
		combo.setItems(OutLineFilter.NO_OPERATOR, OutLineFilter.EQUAL_OPERATOR, OutLineFilter.NOT_EQUAL_OPERATOR,
				OutLineFilter.UPPER_OPERATOR, OutLineFilter.UPPER_OR_EQUAL_OPERATOR, OutLineFilter.LOWER_OPERATOR,
				OutLineFilter.LOWER_OR_EQUAL_OPERATOR);
		combo.setToolTipText("Select an operator to build a criteria");
		combo.setData(GW_WIDGET_ID,GW_OUTLINE_WEIGHT_OPERATOR_COMBO);
		
		GridData gridData15 = new GridData();
		gridData15.verticalAlignment = SWT.TOP;
		gridData15.horizontalSpan = 3;
		combo.setLayoutData(gridData15);
		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				filter.setOperator(combo.getText());
			}
		});
		 
		
		toolkit.adapt(combo, true, true);
		
		weightText = new Text(compoWeight, SWT.BORDER);
		weightText.setToolTipText("Select the value you want the 'Weight' property should match. Leave empty if you don't care about this criteria");

		textWeightDecorator = new ControlDecoration(weightText, SWT.TOP | SWT.LEFT);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
				.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		Image img = fieldDecoration.getImage();
		textWeightDecorator.setImage(img);
		textWeightDecorator.setShowHover(true);
		textWeightDecorator.setDescriptionText("Not a valid value");
		textWeightDecorator.hide();
		
		GridData gridData16 = new GridData();
		gridData16.verticalAlignment = SWT.TOP;
		gridData16.horizontalAlignment = SWT.FILL;
		gridData16.grabExcessHorizontalSpace = true;
		gridData16.horizontalSpan = 7;
		weightText.setLayoutData(gridData16); 
		weightText.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
					textWeightDecorator.hide();
					String value = weightText.getText();
					if (value != null && value.trim().length() > 0) {
						try {
							Double.parseDouble(value);
							filter.setWeight(weightText.getText());
						} catch (NumberFormatException ex) {
							textWeightDecorator.show();
						}
					}
		        }
		});
		weightText.setData(GW_WIDGET_ID ,GW_OUTLINE_WEIGHT_TEXT)  ;
		
		toolkit.adapt(weightText, true, true);
		
		CLabel labelGuard = new CLabel(compoEdge, SWT.NONE);
		labelGuard.setToolTipText("Graph element 'Guard' property");
		GridData gridData9 = new GridData();
		gridData9.horizontalSpan = 3;
		labelGuard.setLayoutData(gridData9);
		labelGuard.setText("Guard:");
		toolkit.adapt(labelGuard, true, true);
		
		ISelectionChangedListener guardScriptListener = new ISelectionChangedListener() {
	        @Override
	        public void selectionChanged(SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	            ThreeStateChoice choice = (ThreeStateChoice)selection.getFirstElement();
	            filter.setGuardChoice(choice);
	        }
		};
		comboGuard = new ThreeStateComboChoice (compoEdge,guardScriptListener);
		comboGuard.setData(GW_WIDGET_ID ,GW_OUTLINE_GUARD_COMBO)  ;
		comboGuard.getControl().setToolTipText("Select the value you want for the 'Guard' property (Whether there is script or not). Leave empty if you don't care about this criteria");

		GridData gridData10 = new GridData();
		gridData10.horizontalAlignment = SWT.FILL;
		gridData10.grabExcessHorizontalSpace = true;
		gridData10.horizontalSpan = 7;
		comboGuard.initialize(gridData10);
		toolkit.adapt(comboGuard.getControl(), true, true);
		
		CLabel labelAction = new CLabel(compoEdge, SWT.NONE);
		labelAction.setToolTipText("Graph element 'Action' property");
		GridData gridData11 = new GridData();
		gridData11.horizontalSpan = 3;
		labelAction.setLayoutData(gridData11);
		labelAction.setText("Action:");
		toolkit.adapt(labelAction, true, true);
		
		ISelectionChangedListener actionScriptListener = new ISelectionChangedListener() {
	        @Override
	        public void selectionChanged(SelectionChangedEvent event) {
	            IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	            ThreeStateChoice choice = (ThreeStateChoice)selection.getFirstElement();
	            filter.setActionChoice(choice);
	        }
		};
		comboAction = new ThreeStateComboChoice (compoEdge,actionScriptListener);
		comboAction.setData(GW_WIDGET_ID ,GW_OUTLINE_ACTION_COMBO)  ;
		comboAction.getControl().setToolTipText("Select the value you want for the 'Action' property (Whether there is script or not). Leave empty if you don't care about this criteria");
	 
		GridData gridData12 = new GridData();
		gridData12.horizontalAlignment = SWT.FILL;
		gridData12.grabExcessHorizontalSpace = true;
		gridData12.horizontalSpan = 7;
		comboAction.initialize(gridData12);
		toolkit.adapt(comboAction.getControl(), true, true);
		
		composite = new Composite(this, SWT.BORDER);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		composite.setLayout(new GridLayout(1, false));
		 
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);

		initialize (false);
	}
 
	private void initialize (boolean enabled) {
		Display.getDefault().asyncExec(new Runnable (){
			@Override
			public void run() {
				updateEnableStatus(enabled);
				expEdgeComp.setExpanded(false);
				expVertexComp.setExpanded(false);
				OutLineComposite.this.pack();
				OutLineComposite.this.setSize(OutLineComposite.this.getParent().getSize());
			}
		});
	}
	
	private void updateEnableStatus (boolean enabled) {
		nameText.setEnabled(enabled);
		descriptionText.setEnabled(enabled);
		requirementText.setEnabled(enabled);
		weightText.setEnabled(enabled);
		comboBlocked.getControl().setEnabled(enabled);
		comboInitScripted.getControl().setEnabled(enabled);
		comboShared.getControl().setEnabled(enabled);
		comboGuard.getControl().setEnabled(enabled);
		comboAction.getControl().setEnabled(enabled);
		expEdgeComp.setEnabled(enabled);
		expVertexComp.setEnabled(enabled);
	}
	
	/**
	 * @return the composite
	 */
	public Composite getComposite() {
		return composite;
	}
}
