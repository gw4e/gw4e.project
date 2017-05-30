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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.search.core.text.TextSearchEngine;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.ui.text.FileTextSearchScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.refactoring.Helper;
import org.gw4e.eclipse.wizard.convert.ResourceContext;

public class GeneratorChoiceComposite extends Composite {

	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.conversion.widget.id";
	public static final String GW4E_CONVERSION_COMBO_ANCESTOR_APPEND_TEST = "id.gw4e.conversion.combo.ancestor.append.id";
	public static final String GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST = "id.gw4e.conversion.combo.ancestor.extend.id";

	public static final String GW4E_APPEND_CHECKBOX = "id.gw4e.conversion.choice.append.class";
	public static final String GW4E_EXTEND_CHECKBOX = "id.gw4e.conversion.choice.extend.class";
	public static final String GW4E_NEWCLASS_CHECKBOX = "id.gw4e.conversion.choice.new.class";

	public static final String GW4E_EXTEND_CLASS_TEXT = "id.gw4e.conversion.text.extend.class";
	public static final String GW4E_NEWCLASS_TEXT = "id.gw4e.conversion.text.new.class";

	private Button btnAppendRadioButton;
	private Label lblAppendClassNameLabel;
	private AncestorViewer comboAppendClassnameViewer;
	private Button btnExtendRadioButton;
	private Label lblExtendedLabel;
	private AncestorViewer comboExtendedClassnameViewer;
	private Label lblExtendingLabel;
	private Text extendingClassnameText;
	private Button btnCreateNewRadioButton;
	private Label lblNewClassnameLabel;
	private Text newClassnameText;
	private Listener listener;

	List<IFile> ancestors = null;
	IStructuredSelection selection = null;
	IPackageFragment pkgf = null;

	String classname;
	IPath path;
	String startElement = null;
	public GeneratorChoiceComposite(Composite parent, int style, IStructuredSelection selection, Listener listener) {
		super(parent, style);

		setLayout(new GridLayout(12, false));
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		setLayoutData(gridData);

		this.listener = listener;
		this.selection = selection;

		loadAncestor();
		findStartElement () ;
		Label explanationLabel = new Label(this, SWT.NONE);
		explanationLabel.setLayoutData(new GridData(SWT.FILL));
		explanationLabel.setText(MessageUtil.getString("three_modes_explanation"));

		skip(this);

		createAppendMode();
		createExtendMode();
		createNewMode();

	}

	public IPackageFragment getPackageFragment() {
		return pkgf;
	}

	public ResourceContext.GENERATION_MODE getMode() {
		if (isAppendMode())
			return ResourceContext.GENERATION_MODE.APPEND;
		if (isExtendMode())
			return ResourceContext.GENERATION_MODE.EXTEND;
		return ResourceContext.GENERATION_MODE.CREATE;
	}

	public IPackageFragmentRoot getRoot() {
		IFile file = (IFile) selection.getFirstElement();
		IPackageFragmentRoot root = null;
		try {
			root = JDTManager.findPackageFragmentRoot(file.getProject(), pkgf.getPath());
		} catch (JavaModelException e) {
			ResourceManager.logException(e);
		}

		return root;
	}

	public boolean isAppendMode() {
		return btnAppendRadioButton.getSelection();
	}

	public boolean isExtendMode() {
		return btnExtendRadioButton.getSelection();
	}

	public boolean isCreateMode() {
		return btnCreateNewRadioButton.getSelection();
	}

	private void findStartElement () {
		IFile file = (IFile) selection.getFirstElement();
		try {
			startElement = GraphWalkerFacade.getStartElement(ResourceManager.toFile(file.getFullPath()));
		} catch (IOException e) {
			ResourceManager.logException(e);
		}

	}
	
	public String getClassName() {
		if (isCreateMode()) {
			return newClassnameText.getText();
		}
		if (isExtendMode()) {
			return extendingClassnameText.getText();
		}
		IStructuredSelection selection = (IStructuredSelection) comboAppendClassnameViewer.getSelection();
		ICompilationUnit unit = (ICompilationUnit) selection.getFirstElement();
		return unit.getElementName();
	}

	public String validate() {
		if ((startElement == null) || (startElement.trim().length() == 0)) {
			String msg = MessageUtil.getString("no_start_element_defined_in_the_graph");
			return msg;
		}
		
		if (btnAppendRadioButton != null && btnAppendRadioButton.getSelection()) {
			if (comboAppendClassnameViewer.getCombo().getText().trim().length() == 0) {
				String msg = MessageUtil.getString("you_must_select_an_existing_test");
				return msg;
			}
		}
		if (btnCreateNewRadioButton != null && btnCreateNewRadioButton.getSelection()) {
			String value = newClassnameText.getText();
			if (value == null) {
				String msg = MessageUtil.getString("you_must_select_a_new_test_class");
				newClassnameText.setFocus();
				return msg;
			}

			if (value.trim().length() == 0) {
				String msg = MessageUtil.getString("you_must_select_a_new_test_class");
				newClassnameText.setFocus();
				return msg;
			}

			if (!validateClassName(value.trim())) {
				String msg = MessageUtil.getString("you_must_select_a_valid_test_class_name");
				newClassnameText.setFocus();
				return msg;
			}

			String path = this.getPackageFragment().getPath().append(value + ".java").toString();
			IResource resource = ResourceManager.getResource(path);
			if (resource != null && resource.exists()) {
				String msg = MessageUtil.getString("you_must_select_a_different_test_class_name");
				newClassnameText.setFocus();
				return msg;
			}
		}

		if (btnExtendRadioButton != null && btnExtendRadioButton.getSelection()) {
			String extendedClass = comboExtendedClassnameViewer.getCombo().getText().trim();
			if (extendedClass.length() == 0) {
				String msg = MessageUtil.getString("you_must_select_an_existing_test");
				comboExtendedClassnameViewer.getCombo().setFocus();
				return msg;
			}

			String value = extendingClassnameText.getText();
			if (value == null) {
				String msg = MessageUtil.getString("you_must_select_a_new_test_class");
				extendingClassnameText.setFocus();
				return msg;
			}

			if (value.trim().length() == 0) {
				String msg = MessageUtil.getString("you_must_select_a_new_test_class");
				extendingClassnameText.setFocus();
				return msg;
			}

			if (!validateClassName(value.trim())) {
				String msg = MessageUtil.getString("you_must_select_a_valid_test_class_name");
				extendingClassnameText.setFocus();
				return msg;
			}
			IStructuredSelection selection = (IStructuredSelection) comboExtendedClassnameViewer.getSelection();
			ICompilationUnit unit = (ICompilationUnit) selection.getFirstElement();
			String selectedAncestor = unit.getElementName().split(Pattern.quote("."))[0];
			if (selectedAncestor.equals(value)) {
				String msg = MessageUtil.getString("you_must_select_a_different_test_class_name");
				extendingClassnameText.setFocus();
				return msg;
			}

			String path = this.getPackageFragment().getPath().append(value + ".java").toString();
			IResource resource = ResourceManager.getResource(path);
			if (resource != null && resource.exists()) {
				String msg = MessageUtil.getString("you_must_select_a_different_test_class_name");
				extendingClassnameText.setFocus();
				return msg;
			}
		}

		if (pkgf == null) {
			String msg = MessageUtil.getString("invalid_pkg");
			return msg;
		}

		if (getRoot() == null) {
			String msg = MessageUtil.getString("invalid_root");
			return msg;
		}


		return null;

	}

	public void setTarget(IPath p, String name) {
		IFolder folder = (IFolder) ResourceManager.getResource(p.toString());
		IJavaElement element = JavaCore.create(folder);
		if (element instanceof IPackageFragmentRoot) {
			this.pkgf = ((IPackageFragmentRoot) element).getPackageFragment(IPackageFragment.DEFAULT_PACKAGE_NAME);
		} else {
			this.pkgf = (IPackageFragment) element;
		}
		String value = name.split(Pattern.quote(".")) [0];
		newClassnameText.setText(value);
		extendingClassnameText.setText(value);
	}

	private void updateUI() {
		lblAppendClassNameLabel.setEnabled(false);
		comboAppendClassnameViewer.setEnabled(false);
		lblExtendedLabel.setEnabled(false);
		comboExtendedClassnameViewer.setEnabled(false);
		lblExtendingLabel.setEnabled(false);
		extendingClassnameText.setEnabled(false);
		lblNewClassnameLabel.setEnabled(false);
		newClassnameText.setEnabled(false);

		if (btnAppendRadioButton.getSelection()) {
			lblAppendClassNameLabel.setEnabled(true);
			comboAppendClassnameViewer.setEnabled(true);
			comboAppendClassnameViewer.getCombo().setFocus();
		}

		if (btnExtendRadioButton.getSelection()) {
			lblExtendedLabel.setEnabled(true);
			comboExtendedClassnameViewer.setEnabled(true);
			comboExtendedClassnameViewer.getCombo().setFocus();
			lblExtendingLabel.setEnabled(true);
			extendingClassnameText.setEnabled(true);
		}

		if (btnCreateNewRadioButton.getSelection()) {
			lblNewClassnameLabel.setEnabled(true);
			newClassnameText.setEnabled(true);
			newClassnameText.setFocus();
		}

		listener.handleEvent(null);
	}

	private void loadAncestor() {
		Display display = Display.getCurrent();
		Runnable longJob = new Runnable() {
			public void run() {
				display.syncExec(new Runnable() {
					public void run() {
						ancestors = findAvailableAncestors();
					}
				});
				display.wake();
			}
		};
		BusyIndicator.showWhile(display, longJob);
	}

	private List<IFile> findAvailableAncestors() {
		List<IFile> files = new ArrayList<IFile>();
		IFile file = (IFile) selection.getFirstElement();
		IResource[] roots = { file.getProject() };
		String[] fileNamePatterns = new String[] { "*.java" };
		FileTextSearchScope scope = FileTextSearchScope.newSearchScope(roots, fileNamePatterns, false);
		IPath path = Helper.buildGeneratedAnnotationValue(file);
		Pattern pattern = Pattern.compile(Helper.getGeneratedAnnotationRegExp(path));
		TextSearchRequestor collector = new TextSearchRequestor() {
			@Override
			public boolean acceptPatternMatch(TextSearchMatchAccess matchAccess) throws CoreException {
				IFile file = matchAccess.getFile();
				files.add(file);
				return true;
			}
		};
		TextSearchEngine.create().search(scope, collector, pattern, new NullProgressMonitor());
		return files;
	}

	/**
	 * @param parent
	 */
	private void skip(Composite parent) {
		Label lblDummy = new Label(parent, SWT.NONE);
		lblDummy.setText("");
		GridData gd = new GridData(GridData.FILL);
		lblDummy.setLayoutData(gd);
	}

	private void createAppendMode() {
		btnAppendRadioButton = new Button(this, SWT.RADIO);
		btnAppendRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		btnAppendRadioButton.setText(MessageUtil.getString("append_mode"));
		btnAppendRadioButton.setSelection(true);
		btnAppendRadioButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					updateUI();
					break;
				}
			}
		});
		btnAppendRadioButton.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_APPEND_CHECKBOX);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		composite.setLayout(new GridLayout(12, false));

		lblAppendClassNameLabel = new Label(composite, SWT.NONE);
		lblAppendClassNameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblAppendClassNameLabel.setText("Class name");

		comboAppendClassnameViewer = new AncestorViewer(composite);
		comboAppendClassnameViewer.initialize(GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST, false);
		comboAppendClassnameViewer.getCombo().setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_COMBO_ANCESTOR_APPEND_TEST);
		Combo combo = comboAppendClassnameViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		combo.setEnabled(true);
	}

	private boolean validateClassName(String name) {
		return JDTManager.validateClassName(name);
	}

	public String getExtendedClassName() {
		if (btnExtendRadioButton.getSelection()) {
			IStructuredSelection selection = (IStructuredSelection) comboExtendedClassnameViewer.getSelection();
			ICompilationUnit unit = (ICompilationUnit) selection.getFirstElement();
			String selectedAncestor = unit.getElementName().split(Pattern.quote("."))[0];
			return selectedAncestor;
		}

		return null;
	}

	private void createExtendMode() {
		btnExtendRadioButton = new Button(this, SWT.RADIO);
		btnExtendRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		btnExtendRadioButton.setText(MessageUtil.getString("extending_class"));
		btnExtendRadioButton.setSelection(false);
		btnExtendRadioButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					updateUI();
					break;
				}
			}
		});
		btnExtendRadioButton.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_EXTEND_CHECKBOX);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		composite.setLayout(new GridLayout(12, false));

		lblExtendedLabel = new Label(composite, SWT.NONE);
		lblExtendedLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblExtendedLabel.setText(MessageUtil.getString("class_extended"));
		lblExtendedLabel.setEnabled(false);

		comboExtendedClassnameViewer = new AncestorViewer(composite);
		comboExtendedClassnameViewer.initialize(GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST, false);
		comboExtendedClassnameViewer.getCombo().setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_COMBO_ANCESTOR_EXTEND_TEST);
		
		Combo combo = comboExtendedClassnameViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		combo.setEnabled(false);

		lblExtendingLabel = new Label(composite, SWT.NONE);
		lblExtendingLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblExtendingLabel.setText(MessageUtil.getString("classname"));
		lblExtendingLabel.setEnabled(false);

		extendingClassnameText = new Text(composite, SWT.BORDER);
		extendingClassnameText.setEnabled(false);
		extendingClassnameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				listener.handleEvent(null);
			}
		});
		extendingClassnameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		extendingClassnameText.setEnabled(false);
		extendingClassnameText.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_EXTEND_CLASS_TEXT);
	}

	private void createNewMode() {
		btnCreateNewRadioButton = new Button(this, SWT.RADIO);
		btnCreateNewRadioButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		btnCreateNewRadioButton.setText(MessageUtil.getString("standalone_mode"));
		btnCreateNewRadioButton.setSelection(false);
		btnCreateNewRadioButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					updateUI();
					break;
				}
			}
		});
		btnCreateNewRadioButton.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_NEWCLASS_CHECKBOX);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 12, 1));
		composite.setLayout(new GridLayout(12, false));

		lblNewClassnameLabel = new Label(composite, SWT.NONE);
		lblNewClassnameLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lblNewClassnameLabel.setText("Class name");
		lblNewClassnameLabel.setEnabled(false);

		newClassnameText = new Text(composite, SWT.BORDER);
		newClassnameText.setEnabled(false);
		newClassnameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				listener.handleEvent(null);
			}
		});
		newClassnameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		newClassnameText.setEnabled(false);
		newClassnameText.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_NEWCLASS_TEXT);
	}

	public class AncestorViewer extends ComboViewer {

		public AncestorViewer(Composite parent) {
			super(parent);
		}

		public void setEnabled(boolean enable) {
			Combo comboAncestor = getCombo();
			comboAncestor.setEnabled(enable);
		}

		public void initialize(String widgetid, boolean active) {
			setEnabled(active);
			setContentProvider(new IStructuredContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					List<IFile> files = (List<IFile>) inputElement;
					Object[] ret = new Object[files.size()];
					int index = 0;
					for (IFile file : files) {
						ret[index++] = JavaCore.create(file);
					}
					return ret;
				}
			});
			setLabelProvider(new JavaElementLabelProvider(
					JavaElementLabelProvider.SHOW_QUALIFIED | JavaElementLabelProvider.SHOW_ROOT));
			addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (selection.size() > 0) {
						ICompilationUnit element = (ICompilationUnit) selection.getFirstElement();
						GeneratorChoiceComposite.this.pkgf = (IPackageFragment) element.getParent();
						listener.handleEvent(null);
					}
				}
			});
			setData(GW4E_CONVERSION_WIDGET_ID, widgetid);

			setInput(ancestors);
			if (active && hasItems()) {
				setSelection(new StructuredSelection(JavaCore.create(ancestors.get(0))));
			}
		}

		public boolean hasItems() {
			return ancestors.size() > 0;
		}
	}
}
