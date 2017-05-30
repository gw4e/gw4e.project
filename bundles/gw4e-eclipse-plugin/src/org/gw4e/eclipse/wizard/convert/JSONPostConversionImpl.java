package org.gw4e.eclipse.wizard.convert;

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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.IWorkbenchWindow;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.preferences.PreferenceManager;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * After having converted a file to json format  
 *
 */
public class JSONPostConversionImpl extends AbstractPostConversion {

	/**
	 * @param context
	 */
	public JSONPostConversionImpl(ResourceContext context) {
		super(context);
	}

	 
	/**
	 * Pretty-fy  the json text so that it displays well in an editor
	 * @param monitor
	 */
	private void formatSource(IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		 InputStream in = null;
		 Reader reader = null;
		try {
			 Gson gson = new GsonBuilder().setPrettyPrinting().create();
			   in = convertedFile.getContents();
			   reader = new InputStreamReader(in);
			 subMonitor.split(10);
			 String text = CharStreams.toString(reader);
			 JsonParser jp = new JsonParser();
			 subMonitor.split(20);
			 JsonElement je = jp.parse(text);
			 String formatted = gson.toJson(je);
			 subMonitor.split(40);
			 ResourceManager.createFileDeleteIfExists(
					 convertedFile.getParent().getFullPath().toString(), 
					 convertedFile.getName(), 
					 formatted, 
					 subMonitor.split(30));
		 } catch (Exception e) {
			ResourceManager.logException(e);
		} finally {
			try {
				if (reader!=null) reader.close();
			} catch (Exception e) {
			}
			try {
				if (in!=null) in.close();
			} catch (Exception e) {
			}
			subMonitor.done();
		}
	}

	/* (non-Javadoc)
	 * @see org.gw4e.eclipse.wizard.convert.AbstractPostConversion#afterConversion(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void afterConversion(IProgressMonitor monitor) {
		if (convertedFile != null) {
			formatSource(monitor);
		}
	}
 
	protected void openEditor(IFile file,IWorkbenchWindow aww) {
		super.openEditor(file,PreferenceManager.getGW4EEditorName(),aww);
	}
}
