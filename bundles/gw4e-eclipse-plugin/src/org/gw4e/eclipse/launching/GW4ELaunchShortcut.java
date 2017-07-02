package org.gw4e.eclipse.launching;

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
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.junit.ui.TestRunnerViewPart;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.test.LaunchingConstant;
import org.gw4e.eclipse.message.MessageUtil;

public class GW4ELaunchShortcut implements ILaunchShortcut {

	public static String GW4ELAUNCHCONFIGURATIONTYPE = "org.gw4e.eclipse.launching.test.GW4ElaunchConfigurationTypes";
	
	public GW4ELaunchShortcut() {
	}

	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			launch(((IStructuredSelection) selection).toArray(), mode);
		} 
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		ITypeRoot element= JavaUI.getEditorInputTypeRoot(editor.getEditorInput());
		if (element != null) {
			launch(new Object[] { element }, mode);
		}  
	}

	private void launch(Object[] elements, String mode) {
		try {
			IJavaElement[] ijes = null;
			List<IJavaElement> jes =  new ArrayList<IJavaElement>();
			for (int i = 0; i < elements.length; i++) {
				Object selected= elements[i];
				if (selected instanceof IJavaElement) {
					IJavaElement element= (IJavaElement) selected;
					switch (element.getElementType()) {
						case IJavaElement.COMPILATION_UNIT:
							jes.add(element);
							break;
					}
				}
			
			}
			ijes = new IJavaElement[jes.size()];
			jes.toArray(ijes);
			ILaunchConfigurationWorkingCopy wc = buildLaunchConfiguration(ijes);
			if (wc==null) return;
			ILaunchConfiguration config= findExistingORCreateLaunchConfiguration(wc, mode);
			DebugUITools.launch(config, mode);
		} catch (Exception e) {
			ResourceManager.logException(e);
			MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "GW4E Launcher", (Image)null, "Unable to launch. See error in Error view.", MessageDialog.ERROR, new String[] { "Close" }, 0);
			dialog.open();
		}
	}
	
	

	private ILaunchConfiguration findExistingORCreateLaunchConfiguration(ILaunchConfigurationWorkingCopy temp, String mode) throws InterruptedException, CoreException {
		List candidateConfigs= findExistingLaunchConfigurations(temp);
		int candidateCount= candidateConfigs.size();
		if (candidateCount > 0) {
			return (ILaunchConfiguration) candidateConfigs.get(0);
		}
		return temp.doSave();
	}
 
 
	
	protected ILaunchConfigurationWorkingCopy buildLaunchConfiguration(IJavaElement[]  elements) throws CoreException {
		IJavaElement mainElement = null;
		for (int i = 0; i < elements.length; i++) {
			IJavaElement element = elements [i];
			if (JDTManager.hasStartableGraphWalkerAnnotation(element)) {
				mainElement = element;
				break;
			}
		}

		if (mainElement==null) {
		   MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "GW4E Launcher", (Image)null, MessageUtil.getString("nostartvalueinannotation"), MessageDialog.ERROR, new String[] { "Close" }, 0);
		   dialog.open();
		   return null;
		}
		 
		ILaunchConfigurationType configType= DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(GW4ELAUNCHCONFIGURATIONTYPE);
		ILaunchConfigurationWorkingCopy wc= configType.newInstance(null, DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(mainElement.getElementName()));
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, elements[0].getJavaProject().getElementName());
		
		String mainElementName = elementToJavaClassName (mainElement);
		StringBuffer sb = new StringBuffer (mainElementName).append(";");
		String[] classnames = elementToJavaClassName(elements);
		for (int i = 0; i < classnames.length; i++) {
			if (classnames[i].equals(mainElementName)) continue;
			sb.append(classnames[i]).append(";");
		}
		wc.setAttribute(LaunchingConstant.CONFIG_TEST_CLASSES,sb.toString());
		return wc;
	}	
	
	private String[] elementToJavaClassName (IJavaElement[]  elements) throws JavaModelException {
		String[] classnames = new String [elements.length];
		for (int i = 0; i < elements.length; i++) {
			IJavaElement element = elements[i];
			classnames[i]=elementToJavaClassName (element);
		}
		return classnames;
	}
	
	private String elementToJavaClassName (IJavaElement  element) throws JavaModelException {
		String classname = JDTManager.getFullyQualifiedName(element);
		if (classname.endsWith(".java"))
			classname = classname.split("\\.")[0];
		classname = classname.replaceAll("/", ".");
		return classname;
	}
 
	
	private List<ILaunchConfiguration> findExistingLaunchConfigurations(ILaunchConfigurationWorkingCopy temporary) throws CoreException {
		ILaunchConfigurationType configType= temporary.getType();

		ILaunchConfiguration[] configs= DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(configType);
		
		List<ILaunchConfiguration> candidateConfigs= new ArrayList<ILaunchConfiguration>(configs.length);
		for (int i= 0; i < configs.length; i++) {
			ILaunchConfiguration config= configs[i];
			
			String project1 = config.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			String project2 = temporary.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if (!project1.equalsIgnoreCase(project2)) continue;
			
			String classname1 = config.getAttribute( LaunchingConstant.CONFIG_TEST_CLASSES, "");
			StringTokenizer tests1 = new StringTokenizer(classname1,";");
			List<String> names1 = new ArrayList<String>();
			while (tests1.hasMoreTokens()) {
				String test = tests1.nextToken();
				names1.add(test);
			}
			Collections.sort(names1);
			String classname2 = temporary.getAttribute( LaunchingConstant.CONFIG_TEST_CLASSES, "");
			StringTokenizer tests2 = new StringTokenizer(classname2,";");
			List<String> names2 = new ArrayList<String>();
			while (tests2.hasMoreTokens()) {
				String test = tests2.nextToken();
				names2.add(test);
			}
			Collections.sort(names2);
			if (names1.equals(names2)) {
				candidateConfigs.add(config);
			}
		}
		return candidateConfigs;
	}	
	 
  

}
