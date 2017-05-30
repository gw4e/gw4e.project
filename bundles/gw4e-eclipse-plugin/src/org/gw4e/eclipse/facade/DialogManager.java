package org.gw4e.eclipse.facade;

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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.message.MessageUtil;

public class DialogManager   {

	public static int YES = 0;
	public static int NO = 1;

	/**
	 * @param section
	 * @return
	 */
	public static IDialogSettings getDialogSettings(String section) {
		IDialogSettings gds = Activator.getDefault().getDialogSettings();
		IDialogSettings ds = gds.getSection(section);

		if (ds == null) {
			ds = gds.addNewSection(section);
		}

		return ds;
	}

	public static String AUTOMATE_MODE = "gw.automated.mode";
	public static String AUTOMATE_MODE_INTEGER_RETURN_VALUE = "gw.automated.mode.integer.return.value";
	public static boolean isAutomatedMode () {
		String value = System.getProperty(AUTOMATE_MODE,"false");
		return Boolean.parseBoolean(value);
	}
	public static int getIntegerAutomatedModeDefaultValue () {
		String value = System.getProperty(AUTOMATE_MODE_INTEGER_RETURN_VALUE,"0");
		return Integer.parseInt(value);
	}
	
	/**
	 * @param title
	 * @param text
	 */
	public static void displayInformation(String title, String text) {
		if (isAutomatedMode ()) return;
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, text);
	}

	/**
	 * @param title
	 * @param text
	 */
	public static void displayWarning(String title, String text) {
		if (isAutomatedMode ()) return;
		MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, text);
	}

	public static int displayYesNoQuestion(String title, String text) {
		if (isAutomatedMode ()) return getIntegerAutomatedModeDefaultValue();
		MessageDialog dialog = new MessageDialog(null, title, null, text, MessageDialog.QUESTION,
				new String[] { MessageUtil.getString("yes"), MessageUtil.getString("no") }, 0);
		int result = dialog.open();
		return result;
	}

	public static MessageDialogWithToggle createRememberDecisonDialog(String title, String text, String toggletext,
			Runnable okRunnable) {
		final String[] buttons = new String[] { IDialogConstants.CLOSE_LABEL, IDialogConstants.OK_LABEL, };

		return new MessageDialogWithToggle(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title,
				null, text, MessageDialog.QUESTION, buttons, 0, toggletext, false) {
			protected void buttonPressed(int buttonId) {
				if (okRunnable != null && IDialogConstants.OK_ID == buttonId) {
					okRunnable.run();
					return;
				}
				super.buttonPressed(buttonId);
			}
		};
	}

	/**
	 * Create a Status instance from he passed stacktrace
	 * 
	 * @param msg
	 * @param t
	 * @return
	 */
	private static MultiStatus createMultiStatus(String msg, Throwable t) {

		List<Status> childStatuses = new ArrayList<Status>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

		for (StackTraceElement stackTrace : stackTraces) {
			Status status = new Status(IStatus.ERROR, "com.example.e4.rcp.todo", stackTrace.toString());
			childStatuses.add(status);
		}

		MultiStatus ms = new MultiStatus("com.example.e4.rcp.todo", IStatus.ERROR,
				childStatuses.toArray(new Status[] {}), t.toString(), t);
		return ms;
	}

	/**
	 * Display an error message in a Dialog
	 * 
	 * @param message
	 */
	public static void displayErrorMessage(String message) {
		MessageDialog dialog = new MessageDialog(Display.getDefault().getActiveShell(),
				MessageUtil.getString("error"), null, MessageUtil.getString("consult_error_log"),
				MessageDialog.ERROR, new String[] { MessageUtil.getString("close") }, 0);
		int result = dialog.open();
		dialog.close();
	}

	/**
	 * Display an error message in a standard UI ErrorDialog
	 * 
	 * @param title
	 * @param msg
	 * @param t
	 */
	public static void displayErrorMessage(String title, String msg, Throwable t) {
		Display.getDefault().syncExec(new Runnable () {
			@Override
			public void run() {
				MultiStatus status = createMultiStatus(msg, t);
				ErrorDialog.openError(getActiveShell(), title, msg, status);
			}
		});
	}

	public static void asyncDisplayErrorMessage(String title, String msg, Throwable t) {
		Display.getDefault().asyncExec(new Runnable () {
			@Override
			public void run() {
				MultiStatus status = createMultiStatus(msg, t);
				ErrorDialog.openError(getActiveShell(), title, msg, status);
			}
		});
	}

	
	/**
	 * @return The active Shell
	 */
	private static Shell getActiveShell() {
		return Display.getDefault().getActiveShell();
	}

}
