package org.gw4e.eclipse.studio.javascript;

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

 
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.jsdt.internal.ui.JavaScriptPlugin;
import org.eclipse.wst.jsdt.internal.ui.javaeditor.JavaSourceViewer;
import org.eclipse.wst.jsdt.internal.ui.text.SimpleJavaSourceViewerConfiguration;
import org.eclipse.wst.jsdt.ui.PreferenceConstants;
import org.eclipse.wst.jsdt.ui.text.IJavaScriptPartitions;
import org.eclipse.wst.jsdt.ui.text.JavaScriptTextTools;

 
public class ViewerHelper {

	public ViewerHelper() {
	}

	public static SourceViewer createEditor(Composite parent) {
		IDocument document= new Document();
		IPreferenceStore store= JavaScriptPlugin.getDefault().getCombinedPreferenceStore();
		JavaScriptTextTools tools= JavaScriptPlugin.getDefault().getJavaTextTools();
		tools.setupJavaDocumentPartitioner(document, IJavaScriptPartitions.JAVA_PARTITIONING);
		SourceViewer viewer= new JavaSourceViewer(parent, null, null, false, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, store);
		SimpleJavaSourceViewerConfiguration configuration= new SimpleJavaSourceViewerConfiguration(tools.getColorManager(), store, null, IJavaScriptPartitions.JAVA_PARTITIONING, false);
		viewer.configure(configuration);
		viewer.setEditable(true);
		viewer.setDocument(document);
		
		Font font = JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
		viewer.getTextWidget().setFont(font);
		
		final IPropertyChangeListener fontChangeListener= new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(PreferenceConstants.EDITOR_TEXT_FONT)) {
					Font font= JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
					viewer.getTextWidget().setFont(font);
				}
			}
		};
		final IPropertyChangeListener propertyChangeListener= new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (configuration.affectsTextPresentation(event)) {
					configuration.handlePropertyChangeEvent(event);
					viewer.invalidateTextPresentation();
				}
			}
		};
		viewer.getTextWidget().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				store.removePropertyChangeListener(propertyChangeListener);
				JFaceResources.getFontRegistry().removeListener(fontChangeListener);
			}
		});
		JFaceResources.getFontRegistry().addListener(fontChangeListener);
		store.addPropertyChangeListener(propertyChangeListener);
		return viewer;
	}
	
	public static void setSource (SourceViewer viewer, String source) {
		if (source==null) source=""; ;
		IDocument document = new Document(source);
		viewer.setDocument(document);
	}

}
