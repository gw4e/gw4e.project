package org.gw4e.eclipse.fwk.source;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class ImportHelper {

	public static void importProjectFromZip(SWTWorkbenchBot bot, String path) {
		int timeout = 10000;
		bot.menu("File").menu("Import...").click();
		bot.waitUntil(Conditions.shellIsActive("Import"));
		SWTBotShell shell = bot.shell("Import").activate();

		shell.bot().tree().expandNode("General").select("Existing Projects into Workspace");
		shell.bot().button("Next >").click();
		shell.bot().radio("Select archive file:").click();

		shell.bot().comboBox(1).setText(path);
		shell.bot().comboBox(1).pressShortcut(SWT.CR, SWT.LF);
		SWTBotButton finishButton = shell.bot().button("Finish");
		ICondition buttonEnabled = new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				return finishButton.isEnabled();
			}

			@Override
			public String getFailureMessage() {
				return "Finish button not enabled";
			}
		};

		shell.bot().waitUntil(buttonEnabled, timeout);
		finishButton.click();

		bot.waitUntil(Conditions.shellCloses(shell), timeout);
	}

	public static void copyFile(File f, IContainer destFolder) throws CoreException, FileNotFoundException {
		IFile newFile = destFolder.getFile(new Path(f.getName()));
		InputStream in = new FileInputStream(f);
		try {
			newFile.create(in, true, null);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void copyFiles(File srcFolder, IContainer destFolder) throws CoreException, FileNotFoundException {
		for (File f : srcFolder.listFiles()) {
			if (f.isDirectory()) {
				IFolder newFolder = destFolder.getFolder(new Path(f.getName()));
				newFolder.create(true, true, null);
				copyFiles(f, newFolder);
			} else {
				IFile newFile = destFolder.getFile(new Path(f.getName()));
				InputStream in = new FileInputStream(f);
				try {
					newFile.create(in, true, null);
				} finally {
					try {
						if (in != null)
							in.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}
