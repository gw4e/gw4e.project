package org.gw4e.eclipse.wizard.runasmanual;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.xl.util.XLFacade;

public class SaveTestPage extends WizardPage {
	private Text textCaseId;
	private Text textDateFormat;
	private Text textWorkbookTitle;
	private String projectname;
	private String component;
	private Text textWorkBook;
	private ComboViewer comboWorkBookViewer;
	private Text textComponentName;
	private ComboViewer comboViewerPriority;
	private Button exportAsTestTemplateButton;
	private Button exportAsTestResultButton;
	private Button btnUpdateIfTestcaseid;

	public static final String GW4E_MANUAL_ELEMENT_ID = "id.gw4e.manual.id";
	public static final String GW4E_MANUAL_TEXTCASE_ID = "id.gw4e.manual.textCaseId";
	public static final String GW4E_MANUAL_TESTEXT_DATE_FORMAT_ID = "id.gw4e.manual.textDateFormat";
	public static final String GW4E_MANUAL_TEXT_WORKBOOK_TITLE_ID = "id.gw4e.manual.textWorkbookTitle";

	
	protected SaveTestPage(String pageName, String projectname,String component) {
		super(pageName);
		this.projectname = projectname;
		this.component = component;
	}

	@Override
	public void createControl(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		setControl(control);
		control.setLayout(new GridLayout(1, false));

		Label lblNewLabel = new Label(control, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel.setText(MessageUtil.getString("manual_export_explanation"));

		Group grpExportChoice = new Group(control, SWT.NONE);
		grpExportChoice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpExportChoice.setText(MessageUtil.getString("manual_export_choice"));
		grpExportChoice.setLayout(new GridLayout(1, false));

		exportAsTestResultButton = new Button(grpExportChoice, SWT.RADIO);
		exportAsTestResultButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		exportAsTestResultButton.setText(MessageUtil.getString("manual_export_as_test"));
		exportAsTestResultButton.setSelection(true);
		exportAsTestResultButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					validatePage();
					break;
				}
			}
		});
		exportAsTestTemplateButton = new Button(grpExportChoice, SWT.RADIO);
		exportAsTestTemplateButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		exportAsTestTemplateButton.setText(MessageUtil.getString("manual_export_as_template"));
		exportAsTestTemplateButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					validatePage();
					break;
				}
			}
		});
		Label lblNewLabel_1 = new Label(control, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_1.setText(MessageUtil.getString("manual_export_select_a_workbook"));

		comboWorkBookViewer = new ComboViewer(control, SWT.NONE);
		Combo comboWorkbook = comboWorkBookViewer.getCombo();
		comboWorkbook.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		comboWorkBookViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboWorkBookViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IFile) {
					IFile file = (IFile) element;
					return file.getName();
				}
				return super.getText(element);
			}
		});

		comboWorkBookViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() > 0) {
					IFile file = (IFile) selection.getFirstElement();
					try {
						String title = XLFacade.getWorkBookTitle(ResourceManager.toFile(file.getFullPath()));
						textWorkbookTitle.setText(title);
					 
					} catch (FileNotFoundException e) {
						ResourceManager.logException(e);
					}
				}
				validatePage();
			}
		});

		textWorkBook = new Text(control, SWT.BORDER);
		textWorkBook.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textWorkBook.setToolTipText(MessageUtil.getString("manual_export_enter_a_workbook_name_toolitp"));
		textWorkBook.addModifyListener (new ModifyListener () {
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		Label lblNewLabel_4 = new Label(control, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_4.setText(MessageUtil.getString("manual_export_enter_workbooktitle"));

		textWorkbookTitle = new Text(control, SWT.BORDER);
		textWorkbookTitle.setData(GW4E_MANUAL_ELEMENT_ID,GW4E_MANUAL_TEXT_WORKBOOK_TITLE_ID);
		textWorkbookTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textWorkbookTitle.addModifyListener (new ModifyListener () {
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		Label lblNewLabel_2 = new Label(control, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_2.setText(MessageUtil.getString("manual_export_enter_a_testcaseid"));

		textCaseId = new Text(control, SWT.BORDER);
		textCaseId.setData(GW4E_MANUAL_ELEMENT_ID,GW4E_MANUAL_TEXTCASE_ID);
		textCaseId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textCaseId.addModifyListener (new ModifyListener () {
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		btnUpdateIfTestcaseid = new Button(control, SWT.CHECK);
		btnUpdateIfTestcaseid.setText(MessageUtil.getString("manual_export_update_item_if_exists"));
		btnUpdateIfTestcaseid.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					validatePage();
					break;
				}
			}
		});
		Label lblNewLabel_5 = new Label(control, SWT.NONE);
		lblNewLabel_5.setText(MessageUtil.getString("manual_export_enter_a_component"));

		textComponentName = new Text(control, SWT.BORDER);
		textComponentName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textComponentName.addModifyListener (new ModifyListener () {
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		textComponentName.setText(component);
		Label lblNewLabel_6 = new Label(control, SWT.NONE);
		lblNewLabel_6.setText(MessageUtil.getString("manual_export_enter_a_priority"));

		comboViewerPriority = new ComboViewer(control, SWT.NONE);
		Combo combo = comboViewerPriority.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboViewerPriority.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerPriority.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IFile) {
					String p = (String) element;
					return p;
				}
				return super.getText(element);
			}
		});
		comboViewerPriority.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				validatePage();
			}
		});
		Label lblNewLabel_3 = new Label(control, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_3.setText(MessageUtil.getString("manual_export_enter_date_format"));

		textDateFormat = new Text(control, SWT.BORDER);
		textDateFormat.setData(GW4E_MANUAL_ELEMENT_ID,GW4E_MANUAL_TESTEXT_DATE_FORMAT_ID);
		textDateFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textDateFormat.setText("m/d/yy");
		textDateFormat.addModifyListener (new ModifyListener () {
			@Override
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		List<IFile> workbooks = new ArrayList<IFile>();
		try {
			ResourceManager.getAllWorkBookFiles(projectname, workbooks);
			IFile[] files = new IFile[workbooks.size()];
			workbooks.toArray(files);
			comboWorkBookViewer.setInput(files);
		} catch (CoreException e) {
			ResourceManager.logException(e);
		}

		String[] priorities = new String[] { MessageUtil.getString("manual_export_high"),
				MessageUtil.getString("manual_export_medium"), MessageUtil.getString("manual_export_low") };
		comboViewerPriority.setInput(priorities);
		validatePage();
	}

	private boolean isValidName(String name) {
		if (name == null)
			return false;
		if (name.trim().length() == 0)
			return false;
		File f = new File(name);
		try {
			f.getCanonicalPath();
			boolean valid = "xlsx".equalsIgnoreCase(FilenameUtils.getExtension(f.getName()));
			return valid;
		} catch (IOException e) {
			return false;
		}
	}

	private boolean isValidDateFormat(String format) {
		if (format == null)
			return false;
		if (format.trim().length() == 0)
			return false;
		return true;
	}

	private boolean isValidValue(String textCaseId) {
		if (textCaseId == null)
			return false;
		if (textCaseId.trim().length() == 0)
			return false;
		return true;
	}

	public boolean validatePage() {
		setErrorMessage(null);
		setPageComplete(false);

		IStructuredSelection selection = (IStructuredSelection) comboWorkBookViewer.getSelection();

		if (selection.getFirstElement() == null && !(isValidName(textWorkBook.getText()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invalidfilename"));
			return false;
		}
		
		if (!(isValidValue(textWorkbookTitle.getText()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invalid_workbook_title"));
			return false;
		}
		
		if (!(isValidDateFormat(textDateFormat.getText()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invaliddateformat"));
			return false;
		}

		if (!(isValidValue(textCaseId.getText()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invaliddtestcaseid"));
			return false;
		}

		if (!(isValidValue(textComponentName.getText()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invalid_component"));
			return false;
		}

		if (!(isValidValue(getPriority()))) {
			setErrorMessage(MessageUtil.getString("manual_export_invalid_priority"));
			return false;
		}
		 
		setPageComplete(true);
		return true;
	}

	public boolean exportAsTemplate() {
		return this.exportAsTestTemplateButton.getSelection();
	}

	public boolean exportAsTest() {
		return this.exportAsTestResultButton.getSelection();
	}

	public File getWorkbookFile() throws FileNotFoundException {
		if (textWorkBook.getText() != null && textWorkBook.getText().trim().length() > 0) {
			File dir = ResourceManager.getProjectLocation(this.projectname);
			File f = new File(dir, textWorkBook.getText().trim());
			return f;
		}
		IStructuredSelection selection = (IStructuredSelection) comboWorkBookViewer.getSelection();
		IFile file = (IFile) selection.getFirstElement();
		return ResourceManager.toFile(file.getFullPath());
	}

	public String getWorkbookTitle() {
		return textWorkbookTitle.getText();
	}

	public String getTestCaseId() {
		return textCaseId.getText();
	}

	public String getComponentNme() {
		return textComponentName.getText();
	}

	public String getDateFormat() {
		return textDateFormat.getText();
	}

	public String getPriority() {
		IStructuredSelection selection = (IStructuredSelection) comboViewerPriority.getSelection();
		String p = (String) selection.getFirstElement();
		return p;
	}

	public boolean isUpdateMode() {
		return btnUpdateIfTestcaseid.getSelection();
	}
}
