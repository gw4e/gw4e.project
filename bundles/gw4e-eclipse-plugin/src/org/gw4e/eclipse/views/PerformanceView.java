package org.gw4e.eclipse.views;

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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.performance.Execution;
import org.gw4e.eclipse.performance.PerformanceStatsManager;

 

public class PerformanceView extends ViewPart  {

	private static final String SHOW_DIALOG_TO_EXPLAIN_HOW_TO_CONFIGURE = "show_dialog_to_explain_how_to_configure";
	public static String PERFORMANCE_VIEW_WIDGET_ID = "performance.view.widget.id"; 
	public static String PERFORMANCE_VIEW_TABLE = "performance.view.table.";
 
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.gw4e.eclipse.views.PerformanceView";

	 
	private static DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS",Locale.getDefault());
		 
	private TableViewer viewer;
	private Action resetAction;
	private Action loadAction;
  
	 
	public PerformanceView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		setColumnsName(viewer);
		
		viewer.setContentProvider(new ExecutionContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setComparator(new PerformanceStatsViewerSorter());
		viewer.getTable().setData(PERFORMANCE_VIEW_WIDGET_ID,PERFORMANCE_VIEW_TABLE);
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		 
		contributeToActionBars();
		
		load () ;
	}
	
	private void reset () {
		PerformanceStatsManager.clear();
		load ();
	}
	
	private boolean load () {
		Execution[] executions = PerformanceStatsManager.getAllExecutions();
		viewer.setInput (executions);
		
	    for (int i = 0, n = viewer.getTable().getColumnCount(); i < n; i++) {
	    	viewer.getTable().getColumn(i).pack();
	    }
	    viewer.getTable().setHeaderVisible(true);
	    viewer.getTable().setLinesVisible(true);
		
		viewer.refresh();
		return ((executions!=null)&& (executions.length>0));
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PerformanceView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(resetAction);
		manager.add(new Separator());
		manager.add(loadAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(resetAction);
		manager.add(loadAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(resetAction);
		manager.add(loadAction);
	}

	public static String getClearToolBarButtonText () {
		return MessageUtil.getString("resetperformancestatistics");
	}
	public static String getLoadToolBarButtonText () {
		return MessageUtil.getString("refreshperformancestatitsics");
	}	
	private void makeActions() {
		resetAction = new Action() {
			public void run() {
				Display.getDefault().asyncExec(new Runnable(){
					@Override
					public void run() {
						reset ();
					}
				});
			}
		};
		resetAction.setText(getClearToolBarButtonText());
		 
		resetAction.setToolTipText(MessageUtil.getString("resetperformancestatistics"));
		resetAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_ETOOL_CLEAR));
		
		loadAction = new Action() {
			 
			/**
			 * @return
			 */
			private boolean showHowToConfigureDialog () {
				String text = MessageUtil.getString("performance_view_configuration");
				String title = MessageUtil.getString("performance_view_configuration_title");
				String toggletext =  MessageUtil.getString("dont_show_me_again_this_dialog");
				Runnable okRunnable = new Runnable () {
					@Override
					public void run() {
						 
					}
				};
				
				MessageDialogWithToggle dialog = DialogManager.createRememberDecisonDialog(title,text,toggletext,okRunnable);
				dialog.open();
				return dialog.getToggleState();
			}
			
			  
			public void run() {
				Display.getDefault().asyncExec(new Runnable(){
					@Override
					public void run() {
						boolean hasData = load ();
						/*
						if(!hasData && noOptions ()) {
							IDialogSettings ds = DialogManager.getDialogSettings(PerformanceView.class.getName());
							String showDialog = ds.get(SHOW_DIALOG_TO_EXPLAIN_HOW_TO_CONFIGURE);
							if ((showDialog==null) || (showDialog.trim().length()==0)) {
								boolean dontShowMeAgainThisDialog = showHowToConfigureDialog ();
						 		ds.put(SHOW_DIALOG_TO_EXPLAIN_HOW_TO_CONFIGURE, dontShowMeAgainThisDialog);
							} else {
								boolean dontshow = Boolean.parseBoolean(showDialog);
								if (!dontshow) {
									boolean dontShowMeAgainThisDialog = showHowToConfigureDialog ();
									ds.put(SHOW_DIALOG_TO_EXPLAIN_HOW_TO_CONFIGURE, dontShowMeAgainThisDialog);
								}
							}
						}*/
					}
				});
			}
		};
		loadAction.setText(getLoadToolBarButtonText ());
		loadAction.setToolTipText(MessageUtil.getString("refreshperformancestatitsics"));
		loadAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_UP));
		 
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	 
	private void setColumnsName (TableViewer tv) {
		   for (int i = 0; i < ColumnsConst.COLUMNS.length; i++) {
			   TableColumn col = new TableColumn(tv.getTable(), SWT.LEFT );
		        col.setText(ColumnsConst.COLUMNS[i]);
		        col.setData(new Integer (i));
		        col.addSelectionListener(new SelectionAdapter() {
		            public void widgetSelected(SelectionEvent event) {
		            	TableColumn tc  = (TableColumn)event.getSource();
		            	Integer index = (Integer)tc.getData();
		                ((PerformanceStatsViewerSorter) tv.getComparator()).doSort(index);
		                tv.refresh();
		            }
		       });
		   }
	  }  
	
	static class ColumnsConst {
		static String[] COLUMNS = new String[] { 
				MessageUtil.getString("project"),
				MessageUtil.getString("when"),
				MessageUtil.getString("kind"), 
				MessageUtil.getString("context"), 
				MessageUtil.getString("failure"), 
				MessageUtil.getString("elapsedTime") };		
		 
		static final int PROJECT = 0;
		static final int WHEN = 1;
		static final int KIND = 2;
		static final int CONTEXT = 3;
		static final int FAILURE = 4;
		static final int ELAPSED = 5;
	}
	
	class PerformanceStatsViewerSorter extends ViewerComparator {
		  private static final int ASCENDING = 0;

		  private static final int DESCENDING = 1;

		  private int column;

		  private int direction;

		  public void doSort(int column) {
		    if (column == this.column) {
		      direction = 1 - direction;
		    } else {
		      this.column = column;
		      direction = ASCENDING;
		    }
		  }

		  public int compare(Viewer viewer, Object o1, Object o2) {
		    int rc = 0;
		    
		    Execution e1 = (Execution) o1;
		    Execution e2 = (Execution) o2;
		    
		    switch (column) {
		    	case ColumnsConst.PROJECT:
			      rc = e1.getStat().getProject().compareTo(e2.getStat().getProject());
			      break;
			    case ColumnsConst.WHEN:
				      rc = e1.compareTo(e2);
				      break;		
			    case ColumnsConst.KIND:
				      rc = e1.getStat().getKind().compareTo(e2.getStat().getKind());
				      break;				      
			    case ColumnsConst.CONTEXT:
			      rc = e1.getStat().getContext().compareTo(e2.getStat().getContext());
			      break;
			    case ColumnsConst.FAILURE:
			      rc = new Boolean (e1.isFailure()).compareTo(new Boolean(e2.isFailure()));
			      break;
			    case ColumnsConst.ELAPSED:
			      rc = e1.elapsed() >= e2.elapsed() ? 1 : -1;
			      break;
		    }

		    // If descending order, flip the direction
		    if (direction == DESCENDING)
		      rc = -rc;

		    return rc;
		  }
		}
	
	class ExecutionContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			Execution[] executions =  (Execution[]) inputElement;
			return executions;
		}
	}
	
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			Execution execution = (Execution) obj;
			String text = null;
			switch (index) {
			case ColumnsConst.PROJECT:
				text = execution.getStat().getProject();
				break;			
			case ColumnsConst.WHEN:
				text = timeFormatter.format(execution.getDate());
				break;
			case ColumnsConst.KIND:
				text =  execution.getStat().getKind();
				break;					
			case ColumnsConst.CONTEXT:
				text =  execution.getStat().getContext();
				break;				
			case ColumnsConst.FAILURE:
				text = null;
				break;
			case ColumnsConst.ELAPSED:
				text = execution.elapsed()+"";
				break;
			default:
				text = "";
				break;
			}
			return text;
		}
		
		public Image getColumnImage(Object obj, int index) {
			if (index==ColumnsConst.FAILURE)   {
				Execution ips = (Execution) obj;
				return getImage(ips);
			}
			return null;
		}
		
		public Image getImage(Execution execution) {
			if (execution.isFailure())
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
	}

	 
}
