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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.gw4e.eclipse.builder.BuildPolicy;
import org.gw4e.eclipse.builder.BuildPolicyManager;

public class BuildPoliciesCheckboxTableViewer extends CheckboxTableViewer {
	
	public static final String GW4E_CONVERSION_WIDGET_ID = "id.gw4e.conversion.widget.id";
	public static final String GW4E_CONVERSION_TABLE_GENERATORS = "id.gw4e.conversion.table.generators.id";
	
	
	IFile file;
	
	public static BuildPoliciesCheckboxTableViewer create (IResource file, Composite parent, int style, GridData gd, Listener listener) {
		Table table = new Table(parent, SWT.CHECK | style);
		table.setData(GW4E_CONVERSION_WIDGET_ID, GW4E_CONVERSION_TABLE_GENERATORS);
		
		BuildPoliciesCheckboxTableViewer bctv =  new BuildPoliciesCheckboxTableViewer(file,table);
		bctv.init(listener,gd);
		return bctv;
	}
	
	public BuildPoliciesCheckboxTableViewer(IResource file, Table table) {
		super(table);
		this.file=null;
		if (file instanceof IFile) {
			this.file=(IFile)file;
		}
	}
	 
    public void setFile (IResource file) {
    	this.file=null;
		if (file instanceof IFile) {
			this.file=(IFile)file;
		}
    	loadBuildPolicies();
    }
    
	public void init (Listener listener,GridData gd) {
		getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		getTable().setLayoutData(gd);
		
				
		setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				BuildPolicy[] ret = (BuildPolicy[]) inputElement;
				return ret;
			}
		});

		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				BuildPolicy bp = (BuildPolicy) element;
				return bp.getPathGenerator();
			}

		});
		getTable().addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableHelper.handleEvent(event);
			}
		});

		TableColumn column = new TableColumn(getTable(), SWT.LEFT);
		column.setText("");
		column.pack();

		getTable().setHeaderVisible(true);
		getTable().setLinesVisible(true);

		getTable().addListener(SWT.Selection, listener);

	}
	
	
	/**
	 * 
	 */
	public void loadBuildPolicies() {
		Display display = Display.getCurrent();
		Runnable longJob = new Runnable() {
			public void run() {
				display.syncExec(new Runnable() {
					public void run() {
						List<BuildPolicy> policies;
						try {
							if (file!=null) {
								policies = BuildPolicyManager.getBuildPolicies(file, false);
							} else {
								policies = new ArrayList<BuildPolicy>();
							}
						} catch (Exception e) {
							// DialogManager.displayErrorMessage(MessageUtil.getString("error"), e.getMessage(),e);
							// throw new RuntimeException(e);
							policies = new ArrayList<BuildPolicy>();
						}
						BuildPolicy[] input = policies.stream().filter(item -> !item.hasTimeDuratioStopCondition())
								.toArray(BuildPolicy[]::new);
						setInput(input);
					}
				});
				display.wake();
			}
		};
		BusyIndicator.showWhile(display, longJob);
	}

}
