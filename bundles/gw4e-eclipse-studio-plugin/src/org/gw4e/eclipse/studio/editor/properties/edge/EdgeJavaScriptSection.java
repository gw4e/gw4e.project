package org.gw4e.eclipse.studio.editor.properties.edge;

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

 
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.gw4e.eclipse.studio.editor.properties.SectionProvider;
import org.gw4e.eclipse.studio.model.properties.EdgeGW4EEditPartProperties;

 
public abstract class EdgeJavaScriptSection extends AbstractPropertySection {

	private SectionProvider node;
    private SourceViewer viewer ;

    public EdgeJavaScriptSection () {
    }
    
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        Object input = ((IStructuredSelection) selection).getFirstElement();
        this.node = (SectionProvider) input;
    }

    protected SourceViewer getViewer () {
    	return viewer;
    }
 
    protected EdgeGW4EEditPartProperties getProperties () {
    	EdgeGW4EEditPartProperties properties = (EdgeGW4EEditPartProperties) node.getAdapter(IPropertySource.class);
        return properties;
    }
    
	public static int setHeight(FormData fd, Control control, int rowcount) {
		int height = 0;
		if (control != null && !control.isDisposed()) {
			GC gc = new GC(control);
			try {
				gc.setFont(JFaceResources.getDialogFont());
				fd.height = Dialog.convertHeightInCharsToPixels(gc.getFontMetrics(), rowcount);
			} finally {
				gc.dispose();
			}
		}
		return height;
	}

	protected abstract SourceViewer createViewer (Composite composite) ;
	
    protected void fillComposite (Composite composite) {
    		composite.setLayout(new FormLayout());
    		
    		viewer = createViewer(composite);
    		Control control = viewer.getControl();
    		control.setEnabled(false);
    		
    		FormData fd_javaScript = new FormData();
    		fd_javaScript.left = new FormAttachment(0, 10);
    		fd_javaScript.right = new FormAttachment(100, -5);
    		fd_javaScript.top = new FormAttachment(0, 10);
      		setHeight (fd_javaScript, control, 10);
    		control.setLayoutData(fd_javaScript);
    	}
    
    
    public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
        super.createControls(parent, aTabbedPropertySheetPage);
        Composite composite = getWidgetFactory().createFlatFormComposite(parent);
        fillComposite (composite);
    }

    public abstract String getSource() ;
    
    public abstract void refresh() ;
 
   
}
