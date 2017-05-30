package org.gw4e.eclipse.perspective;

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

import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;


 
public class GW4EPerspective implements IPerspectiveFactory {

	private IPageLayout factory;

	public GW4EPerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;
		addViews();
		addActionSets();
		addNewWizardShortcuts();
		addPerspectiveShortcuts();
		addViewShortcuts();
	}

	private void addViews() {
		
		IFolderLayout bottom =
			factory.createFolder(
				"bottomRight", //NON-NLS-1
				IPageLayout.BOTTOM,
				0.75f,
				factory.getEditorArea());
	    bottom.addView(IPageLayout.ID_PROP_SHEET);
	    bottom.addView("org.gw4e.eclipse.views.PerformanceView");
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottom.addView(JavaUI.ID_JAVADOC_VIEW);
		bottom.addView(JavaUI.ID_SOURCE_VIEW);
		bottom.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		bottom.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		bottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		bottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
		
		IFolderLayout topLeft =
			factory.createFolder(
				"topLeft", //NON-NLS-1
				IPageLayout.LEFT,
				0.15f,
				factory.getEditorArea());
		topLeft.addView(JavaUI.ID_PACKAGES);
		topLeft.addPlaceholder(JavaUI.ID_TYPE_HIERARCHY);
		topLeft.addPlaceholder(JavaPlugin.ID_RES_NAV);
		topLeft.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER);
		
		IFolderLayout outlineFolder = factory.createFolder("right", IPageLayout.RIGHT, (float) 0.85, factory.getEditorArea()); //$NON-NLS-1$
		outlineFolder.addView(IPageLayout.ID_OUTLINE);
	}

	private void addActionSets() {
		factory.addActionSet("org.eclipse.debug.ui.launchActionSet"); //NON-NLS-1
		factory.addActionSet("org.eclipse.debug.ui.debugActionSet"); //NON-NLS-1
		factory.addActionSet("org.eclipse.debug.ui.profileActionSet"); //NON-NLS-1
		factory.addActionSet("org.eclipse.jdt.debug.ui.JDTDebugActionSet"); //NON-NLS-1
		factory.addActionSet("org.eclipse.jdt.junit.JUnitActionSet"); //NON-NLS-1
		factory.addActionSet(JavaUI.ID_ACTION_SET);
		factory.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET); //NON-NLS-1
	}

	private void addPerspectiveShortcuts() {
		factory.addPerspectiveShortcut("org.eclipse.jdt.ui.JavaPerspective"); //NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective"); //NON-NLS-1
	}

	private void addNewWizardShortcuts() {
		factory.addNewWizardShortcut("org.gw4e.eclipse.wizard.model.GW4EModelWizard");//NON-NLS-1
		factory.addNewWizardShortcut("org.gw4e.eclipse.wizard.GW4EProjectCreationWizard");//NON-NLS-1
	}

	private void addViewShortcuts() {
		factory.addShowViewShortcut("org.gw4e.eclipse.views.PerformanceView");
		factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		factory.addShowViewShortcut(JavaUI.ID_PACKAGES);
		factory.addShowViewShortcut(JavaUI.ID_TYPE_HIERARCHY);
		factory.addShowViewShortcut(JavaUI.ID_SOURCE_VIEW);
		factory.addShowViewShortcut(JavaUI.ID_JAVADOC_VIEW);	
	}

}
