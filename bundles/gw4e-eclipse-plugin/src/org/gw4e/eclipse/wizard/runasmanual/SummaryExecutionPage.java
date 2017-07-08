package org.gw4e.eclipse.wizard.runasmanual;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.gw4e.eclipse.message.MessageUtil;
import org.eclipse.jface.viewers.TableViewerColumn;

public class SummaryExecutionPage extends WizardPage {
	private Table table;

	protected SummaryExecutionPage(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		  Composite control = new Composite(parent, SWT.NONE);
		  setControl(control);	 
		  control.setLayout(new GridLayout(1, false));
		  
		 
		  Table table = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		  TableColumn column = new TableColumn(table, SWT.LEFT);
		   column.setText("");
			column.pack();

			table.setHeaderVisible(true);
			table.setLinesVisible(true);	 
		  
		  CheckboxTableViewer checkboxTableViewer = CheckboxTableViewer.newCheckList(control, SWT.BORDER | SWT.FULL_SELECTION);
		  table = checkboxTableViewer.getTable();
		  table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		  
		  TableViewerColumn tableViewerColumn = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
		  TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		  tblclmnNewColumn.setWidth(100);
		  tblclmnNewColumn.setText(MessageUtil.getString("step"));
		   
	}

 
	
}
