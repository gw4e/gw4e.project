package org.gw4e.eclipse.fwk.project;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ContextMenuHelper;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.gw4e.eclipse.builder.BuildPolicyManager;
import org.gw4e.eclipse.builder.exception.BuildPolicyConfigurationException;
import org.gw4e.eclipse.facade.ClasspathManager;
import org.gw4e.eclipse.facade.JDTManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.fwk.conditions.BuildPoliciesFileDoesNotExistsCondition;
import org.gw4e.eclipse.fwk.conditions.BuildPoliciesFileExistsCondition;
import org.gw4e.eclipse.fwk.conditions.EditorOpenedCondition;
import org.gw4e.eclipse.fwk.conditions.ErrorIsInProblemView;
import org.gw4e.eclipse.fwk.conditions.NoErrorInProblemView;
import org.gw4e.eclipse.fwk.conversion.ConvertDialog;
import org.gw4e.eclipse.fwk.conversion.GraphWalkerTestHookPageTest;
import org.gw4e.eclipse.fwk.conversion.GraphWalkerTestUIPageTest;
import org.gw4e.eclipse.fwk.conversion.JUnitGraphWalkerTestUIPageTest;
import org.gw4e.eclipse.fwk.properties.GraphModelProperties;
import org.gw4e.eclipse.fwk.view.ProblemView;
import org.gw4e.eclipse.launching.GW4ELaunchShortcut;
import org.gw4e.eclipse.preferences.PreferenceManager;
import org.gw4e.eclipse.product.GW4ENature;
import org.gw4e.eclipse.test.project.GW4EProjectTestCase.FileParameters;
import org.gw4e.eclipse.wizard.template.EmptyTemplate;
import org.gw4e.eclipse.wizard.template.NoneTemplate;
import org.gw4e.eclipse.wizard.template.SharedTemplate;
import org.gw4e.eclipse.wizard.template.SimpleTemplate;
import org.gw4e.eclipse.wizard.template.SimpleTemplateWithScript;

public class GW4EProject {

	public static String QUICK_FIX_MSG_MISSING_BULD_POLICIES_FILE = "Add the required build policies file";
	public static String NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG = "No policies found for ShoppingCart.graphml";
	public static String ADD_DEFAULT_POLICIES = "Add default policies";
	public static String ADD_SYNCED_POLICIES = "Add Sync'ed policies";
	public static String ADD_NOCHECK_POLICIES = "Add No Check policies";

	 SWTWorkbenchBot bot;
	String projectName;

	public String getProjectName() {
		return projectName;
	}

	public GW4EProject(SWTWorkbenchBot wbot, String projectName) {
		bot = wbot;
		this.projectName = projectName;
	}
	
	public FileParameters createSimpleProject () throws CoreException {
		createSimpleProjectWithoutGeneration ();
		return generateForSimpleProject();
	}

	public void createSimpleProjectWithoutGeneration () throws CoreException {
		GW4EProject project = new GW4EProject(bot, getProjectName());
		project.createWithSimpleTemplate(getProjectName());
	}

	public FileParameters generateForSimpleProject () throws CoreException {
		GW4EProject project = new GW4EProject(bot, getProjectName());
		String mainSourceFolder  = getProjectName() + "/src/main/java";
		String pkgFragmentRoot	 =	"src/main/java";
		String pkg = "com.company";
		String targetFilename = "SimpleImpl";
		String targetFormat = "test";
		String[] graphFilePath = new String []  { getProjectName(), "src/main/resources", pkg, "Simple.json" };
		String checkTestBox = "Java Test Based";
		String [] contexts = new String [0];
		FileParameters fp = new FileParameters(mainSourceFolder, getProjectName(), pkgFragmentRoot, pkg, targetFilename,  targetFormat, graphFilePath);
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean b  = project.prepareconvertTo(
						fp.getProject(),
						fp.getPackageFragmentRoot(),
						fp.getPackage(), 
						fp.getTargetFilename(), 
						targetFormat,
						checkTestBox,
						"e_StartApp","v_VerifyPreferencePage", "e_StartApp",contexts,
						fp.getGraphmlFilePath());
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};
		bot.waitUntil(convertPageCompletedCondition, 3 * 60 * 1000);
		return fp;
	}	
	
	
	
	public void createInitialProjectWithoutError(String resourceFolder, String pkg, String graphMLFilename)
			throws FileNotFoundException, CoreException, BuildPolicyConfigurationException {
		createInitialProject(projectName, resourceFolder, pkg, graphMLFilename);

		ProblemView pv = ProblemView.open(bot);

		ICondition[] conditions = new ICondition[] {
				new ErrorIsInProblemView(pv, GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG),
				new EditorOpenedCondition(bot, PreferenceManager.getBuildPoliciesFileName(projectName)), };

		GW4EProject project = new GW4EProject(bot, projectName);

		pv.executeQuickFixForErrorMessage(
				project.getMissingErroMessage(projectName, resourceFolder, pkg, graphMLFilename),
				GW4EProject.QUICK_FIX_MSG_MISSING_BULD_POLICIES_FILE, conditions);
		pv.close(); // Mandatory

		pv = ProblemView.open(bot);
		pv.executeQuickFixForErrorMessage(GW4EProject.NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG,
				GW4EProject.ADD_DEFAULT_POLICIES, new ICondition[] { new NoErrorInProblemView(pv) });

		String buildPoliciPath = projectName + "/" + resourceFolder + "/" + pkg + "/"
				+ PreferenceManager.getBuildPoliciesFileName(projectName);
		IFile buildPolicyModel = (IFile) ResourceManager.getResource(buildPoliciPath);
		IPath pathModel = buildPolicyModel.getFullPath().removeLastSegments(1).append(graphMLFilename);
		IFile iFileModel = (IFile) ResourceManager.getResource(pathModel.toString());
		String[] policies = BuildPolicyManager.valuesToArray(projectName, buildPolicyModel, iFileModel,
				graphMLFilename);
		String[] expectedPolicies = PreferenceManager.getBasicPolicies(projectName);

		assertArrayEquals("Wrong policies found in the build policies file", expectedPolicies, policies);

	}

	public   void createInitialProject(String gwproject, String resourceFolder, String pkg, String graphMLFilename)
			throws CoreException, FileNotFoundException {
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject();
		project.createGraphMLFile(resourceFolder, pkg, graphMLFilename);
		String[] errors = new String[] {
				project.getMissingErroMessage(gwproject, resourceFolder, pkg, graphMLFilename) };
		project.waitForBuildAndAssertErrors(errors);
	}

	public void resetToJavPerspective() {
		final SWTBotPerspective javaPerspective = bot.perspectiveById(JavaUI.ID_PERSPECTIVE);
		javaPerspective.activate();
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				return javaPerspective.isActive();
			}

			@Override
			public String getFailureMessage() {
				return JavaUI.ID_PERSPECTIVE + " has not become active in time";
			}
		});
	}

	public String getMissingErroMessage(String gwproject, String testResourceFolder, String pkgname,
			String graphMLFilename) {
		return "Expecting a build.policies file in /" + gwproject + "/" + testResourceFolder
				+ (pkgname.length() > 0 ? "/" : "") + pkgname + " including /" + gwproject + "/" + testResourceFolder
				+ (pkgname.length() > 0 ? "/" : "") + pkgname + "/" + graphMLFilename;
	}

	public void createProjectWithoutError(String testResourceFolder, String pkgname, String graphMLFilename)
			throws CoreException, FileNotFoundException {
		createProject();
		createGraphMLFile(testResourceFolder, pkgname, graphMLFilename);
		ProblemView pv = ProblemView.open(bot);

		ICondition[] conditions = new ICondition[] {
				new ErrorIsInProblemView(pv, NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG),
				new EditorOpenedCondition(bot, PreferenceManager.getBuildPoliciesFileName(projectName)), };

		pv.executeQuickFixForErrorMessage(
				getMissingErroMessage(projectName, testResourceFolder, pkgname, graphMLFilename),
				QUICK_FIX_MSG_MISSING_BULD_POLICIES_FILE, conditions);
		pv.close(); // Mandatory

		pv = ProblemView.open(bot);
		pv.executeQuickFixForErrorMessage(NO_POLICIES_FOUND_IN_BUILD_POLICIES_FILE_ERROR_MSG, ADD_NOCHECK_POLICIES,
				new ICondition[] { new NoErrorInProblemView(pv) });
	}

	public void createProject() throws CoreException {
		createProject("None");
		assertNoErrorsInProject();
	}

	public void createProject(String type) throws CoreException {
		SWTBotMenu fileMenu = bot.menu("File");
		SWTBotMenu newMenu = fileMenu.menu("New");
		SWTBotMenu projectMenu = newMenu.menu("Project...");
		projectMenu.click();

		bot.waitUntil(Conditions.shellIsActive("New Project"));
		SWTBotShell shell = bot.shell("New Project").activate();
		SWTBotText text = shell.bot().text();
		text.setText("GW4E");
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				return "GW4E".equals(text.getText());
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Failed to enter 'GW4E' in the text field";
			}
		});

		SWTBotButton button = shell.bot().button("Next >");
		bot.waitUntil(new ICondition() {
			@Override
			public boolean test() throws Exception {
				return button.isEnabled();
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "Failed to select 'Next'  button";
			}
		});
		button.click();

		shell.bot().textWithLabel("Project name:").setText(projectName);

		if (type != null) {
			SWTBotButton button1 = shell.bot().button("Next >");
			shell.bot().waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					return button1.isEnabled();
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Failed to select 'Next'  button";
				}
			});
			button.click();

			shell.bot().waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					SWTBotButton browse = shell.bot().button("Browse...");
					return browse != null;
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Failed to select 'Next'  button";
				}
			});
			SWTBotButton button2 = shell.bot().button("Next >");
			button2.click();

			shell.bot().waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					SWTBotRadio simpleButton = shell.bot().radio(type);
					return simpleButton != null;
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Failed to get the provider page";
				}
			});
			SWTBotRadio simpleButton = shell.bot().radio(type);
			simpleButton.click();
			shell.bot().waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					SWTBotRadio simpleButton = shell.bot().radio(type);
					return simpleButton.isSelected();
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Failed to select '" + type + "' radio button in the provider page";
				}
			});
			
			SWTBotButton button3 = shell.bot().button("Next >");
			button3.click();
			shell.bot().waitUntil(new ICondition() {
				@Override
				public boolean test() throws Exception {
					SWTBotButton button = shell.bot().button("Finish");
					return button.isEnabled();
				}

				@Override
				public void init(SWTBot bot) {
				}

				@Override
				public String getFailureMessage() {
					return "Failed to select 'Next'  button";
				}
			});
		}

		shell.bot().button("Finish").click();

		bot.waitUntil(Conditions.shellCloses(shell),60 * 1000);
		
		
		bot.waitUntil(new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				return isProjectCreated(projectName);
			}

			@Override
			public String getFailureMessage() {
				return "Project doesn't exist";
			}
		},3 * 60 * 1000);
		assertTrue("Project doesn't exist", isProjectCreated(projectName));

		cleanBuild();

		assertTrue("M2_REPO variable does not exist. is Maven installed ?",
				ClasspathManager.isMavenInstalled());

		boolean b = ClasspathManager
				.hasGW4EClassPathContainer(ResourceManager.getProject(projectName));
		assertTrue("Project doesn't have the correct Classpath container ", b);

	}

	public void setPathGenerator(IFile buildPolicyFile, String key, String value)
			throws IOException, CoreException, InterruptedException {
		File f = ResourceManager.toFile(buildPolicyFile.getFullPath());
		Properties p = new Properties();
		InputStream in = new FileInputStream(f);
		try {
			p.load(in);
		} finally {
			 if (in != null) in.close();
		}
		p.setProperty(key, value);
		BuildPolicyManager.savePolicies(buildPolicyFile, p, getNullMonitor());
	}

	public String getPathGenerator(IFile buildPolicyFile, String key) throws IOException {
		File f = ResourceManager.toFile(buildPolicyFile.getFullPath());
		Properties p = new Properties();
		InputStream in = new FileInputStream(f);
		try {
			p.load(in);
			return p.getProperty(key, null);
		} finally {
			 if (in != null) in.close();
		}
	}

	public void clearBuildPoliciesFileFromGraphModel(IFile graphModel)
			throws IOException, CoreException, InterruptedException {
		IPath buildPolicyPath = ResourceManager.getBuildPoliciesPathForGraphModel(graphModel);
		IFile buildPolicyFile = (IFile) ResourceManager.getResource(buildPolicyPath.toString());
		clearBuildPoliciesFile(buildPolicyFile);
	}

	public void clearBuildPoliciesFile(ICompilationUnit cu) throws IOException, CoreException, InterruptedException {
		String graphmlSourcePath = JDTManager.getGW4EGeneratedAnnotationValue(cu, "value");
		IPath path = new Path(this.projectName).append(graphmlSourcePath);
		IFile graphModel = (IFile) ResourceManager.getResource(path.toString());
		clearBuildPoliciesFileFromGraphModel(graphModel);
	}

	public void clearBuildPoliciesFile(IFile buildPolicyFile) throws IOException, CoreException, InterruptedException {
		buildPolicyFile.delete(true, getNullMonitor());

		bot.waitUntil(new BuildPoliciesFileDoesNotExistsCondition(buildPolicyFile));

		BuildPolicyManager.createBuildPoliciesFile(buildPolicyFile, getNullMonitor());

		bot.waitUntil(new BuildPoliciesFileExistsCondition(buildPolicyFile));
	}

	public GraphModelProperties getGraphmlProperties(String folder, String pkg, String filename) {
		SWTBotTreeItem pti = getProjectTreeItem(this.projectName);
		pti.expand();

		SWTBotTreeItem fileItem = null;
		if (pkg == null) {
			fileItem = pti.expandNode(folder, filename);
		} else {
			fileItem = pti.expandNode(folder, pkg, filename);
		}

		bot.waitUntil(new isItemExpandedInTree(pti.getNode(folder)));

		fileItem.setFocus();
		fileItem.select();
		bot.waitUntil(new isItemSelectedInTree(fileItem));

		fileItem.click();

		bot.waitUntil(new isItemSelected(fileItem));

		SWTBotMenu fileMenu = bot.menu("File");
		fileMenu.menu("Properties").click();

		bot.waitUntil(Conditions.shellIsActive("Properties for " + filename));
		SWTBotShell shell = bot.shell("Properties for " + filename);
		GraphModelProperties gp = new GraphModelProperties(shell.bot(), this.projectName, folder, pkg, filename);

		return gp;
	}

	public class isItemSelectedInTree extends DefaultCondition {
		private SWTBotTreeItem fileItem;

		public isItemSelectedInTree(SWTBotTreeItem fileItem) {
			this.fileItem = fileItem;
		}

		public String getFailureMessage() {
			return "item '" + fileItem + "' is not selected in the tree";
		}

		public boolean test() throws Exception {
			try {
				return fileItem.isSelected() & fileItem.isVisible();
			} catch (WidgetNotFoundException e) {
				return false;
			}
		}
	}

	public class isItemExpandedInTree extends DefaultCondition {
		private SWTBotTreeItem item;

		public isItemExpandedInTree(SWTBotTreeItem item) {
			this.item = item;
		}

		public String getFailureMessage() {
			return "item '" + item + "' is not expanded in the tree";
		}

		public boolean test() throws Exception {
			try {
				return item.isExpanded();
			} catch (WidgetNotFoundException e) {
				return false;
			}
		}
	}

	protected void cleanProject() throws CoreException {
		getRoot().getProject(projectName).build(IncrementalProjectBuilder.CLEAN_BUILD, getNullMonitor());
		boolean wasInterrupted = false;
		do {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
				wasInterrupted = false;
			} catch (OperationCanceledException ignore) {
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}

	protected void disableAutomaticBuild() {
		clickOnAutomaticBuild(false);
	}

	protected void enableAutomaticBuild() {
		clickOnAutomaticBuild(true);
	}

	private void clickOnAutomaticBuild(boolean enable) {
		if (buildAutomaticallyMenu().isChecked() == enable)
			return;
		buildAutomaticallyMenu().click();
		assertEquals(enable, buildAutomaticallyMenu().isChecked());
	}

	private SWTBotMenu buildAutomaticallyMenu() {
		return bot.menu("Project", 1).menu("Build Automatically");
	}

	protected boolean isProjectCreated(String name) {
		try {
			getProjectTreeItem(name);
			return true;
		} catch (WidgetNotFoundException e) {
			return false;
		}
	}

	public   SWTBotTree getProjectTree() {
		SWTBotTree tree = getPackageExplorer().bot().tree();
		return tree;
	}

	protected   SWTBotView getPackageExplorer() {
		SWTBotView view = bot.viewByTitle("Package Explorer");
		return view;
	}

	protected SWTBotTreeItem getProjectTreeItem(String myTestProject) {
		return getProjectTree().getTreeItem(myTestProject);
	}

	public void waitForBuildAndAssertNoErrors() throws CoreException {
		fullBuild();
		assertNoErrorsInProject();
	}

	public void waitForNoError () throws CoreException {
		fullBuild();
		ProblemView pv = ProblemView.open(bot);
		DefaultCondition condition = new NoErrorInProblemView(pv) ;
		bot.waitUntil(condition, 15 * 1000);
	}
	
	public void waitForBuildAndAssertErrors(String[] errors) throws CoreException {
		fullBuild();
		assertErrorsInProject(errors);
	}

	
	protected void waitForItem(final SWTBotTreeItem treeItem, String whichItemName) {
		int maxTry = 3;
		int waitTime = 1000;
		int tryCount = 0;
		while (tryCount < maxTry) {
			boolean found = UIThreadRunnable.syncExec(new BoolResult() {
				public Boolean run() {
					boolean found = false;
					TreeItem[] items = treeItem.widget.getItems();
					for (int i = 0; i < items.length; i++) {
						String item = items[i].getText();
						if (whichItemName.endsWith(item)) {
							found = true;
							break;
						}
					}
					return found;
				}
			});
			if (!found) {
				treeItem.collapse();
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
				}
				treeItem.expand();
			}
			tryCount++;
		}
	}

	protected   void assertErrorsInProject(String[] errors) throws CoreException {
		bot.waitUntil(new ICondition() {

			@Override
			public boolean test() throws Exception {
				IMarker[] markers = getRoot().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
				return markers.length == errors.length;
			}

			@Override
			public void init(SWTBot bot) {
			}

			@Override
			public String getFailureMessage() {
				return "expected error markers not found.";
			}

		});
		IMarker[] markers = getRoot().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		assertEquals("expected error markers ", errors.length, markers.length);
		for (int j = 0; j < errors.length; j++) {
			boolean found = false;
			for (int i = 0; i < markers.length; i++) {
				IMarker iMarker = markers[i];
				if (iMarker.getAttribute(IMarker.SEVERITY).toString().equals("" + IMarker.SEVERITY_ERROR)) {
					String error = (String) iMarker.getAttribute(IMarker.MESSAGE);
					if (errors[j].equalsIgnoreCase(error)) {
						found = true;
						break;
					}
				}
			}
			assertEquals("expected error markers '" + errors[j] + "' not found", true, found);
		}
	}

	protected static void assertNoErrorsInProject() throws CoreException {
		IMarker[] markers = getRoot().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		List<IMarker> errorMarkers = new LinkedList<IMarker>();
		for (int i = 0; i < markers.length; i++) {
			IMarker iMarker = markers[i];
			if (iMarker.getAttribute(IMarker.SEVERITY).toString().equals("" + IMarker.SEVERITY_ERROR)) {
				errorMarkers.add(iMarker);
			}
		}
		assertEquals("expected no error markers: " + getMarkers(errorMarkers), 0, errorMarkers.size());
	}

	private static String getMarkers(List<IMarker> errorMarkers) {
		StringBuffer buffer = new StringBuffer();
		for (IMarker iMarker : errorMarkers) {
			try {
				buffer.append(iMarker.getResource() + "\n");
				buffer.append(iMarker.getAttribute(IMarker.MESSAGE) + "\n");
				buffer.append(iMarker.getAttribute(IMarker.SEVERITY) + "\n");
			} catch (CoreException e) {
			}
		}
		return buffer.toString();
	}

	public static IProgressMonitor getNullMonitor() {
		return new NullProgressMonitor();
	}

	public static void fullBuild() throws CoreException {

		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, getNullMonitor());
		boolean interrupted = false;
		do {
			try {
				Job.getJobManager().wakeUp(ResourcesPlugin.FAMILY_MANUAL_BUILD);
				Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
				interrupted = false;
			} catch (OperationCanceledException ignore) {
			} catch (InterruptedException e) {
				interrupted = true;
			}
		} while (interrupted);
	}

	public static void cleanBuild() throws CoreException {
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.CLEAN_BUILD, getNullMonitor());
		boolean wasInterrupted = false;
		do {
			try {
				Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
				Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				wasInterrupted = false;
			} catch (OperationCanceledException ignore) {
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}

	public static int getGW4ETestRunnerConfigurationCount() throws CoreException {
		ILaunchConfigurationType configType = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurationType(GW4ELaunchShortcut.GW4ELAUNCHCONFIGURATIONTYPE);
		ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurations(configType);
		return configs.length;
	}

	public static void cleanWorkspace() throws CoreException {
		IProject[] projects = getRoot().getProjects();
		deleteProjects(projects);
		IProject[] otherProjects = getRoot().getProjects(IContainer.INCLUDE_HIDDEN);
		deleteProjects(otherProjects);

		ILaunchConfigurationType configType = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurationType(GW4ELaunchShortcut.GW4ELAUNCHCONFIGURATIONTYPE);
		ILaunchConfiguration[] configs = DebugPlugin.getDefault().getLaunchManager()
				.getLaunchConfigurations(configType);
		for (int i = 0; i < configs.length; i++) {
			ILaunchConfiguration config = configs[i];
			config.delete();
		}
		 
	}

	protected static void deleteProjects(IProject[] projects) throws CoreException {
		List<IProject> failed = new ArrayList<IProject> ();
		for (IProject iProject : projects) {
			if (iProject.exists()) {
				try {
					Job job = new WorkspaceJob("GraphWalker Delete Job") {
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
							iProject.refreshLocal(IResource.DEPTH_INFINITE, getNullMonitor());
							try {
								iProject.delete(true, true, getNullMonitor());
							} catch (Exception e) {
								e.printStackTrace();
							}
							return Status.OK_STATUS;
						}
					};
					job.schedule(); 
					job.join();
				} catch (Exception e) {
					failed.add(iProject);
				}
			}
		}

	}

	public static String getMarkers(IMarker[] markers) throws CoreException {
		String s = "";
		for (IMarker iMarker : markers) {
			s += "," + iMarker.getAttribute(IMarker.MESSAGE);
		}
		return s;
	}

	public static IWorkspaceRoot getRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public void assertHasFolder(String folder) throws JavaModelException {
		String[] folders = new String[] { folder };
		assertHasSourceFolders(folders);
	}

	public void assertHasSourceFolders(String[] folders) throws JavaModelException {
		IProject project = getRoot().getProject(this.projectName);
		IJavaProject jproject = JavaCore.create(project);
		IPackageFragmentRoot[] pkgs = jproject.getPackageFragmentRoots();

		for (int i = 0; i < folders.length; i++) {
			String folder = folders[i];
			boolean found = false;
			for (int j = 0; j < pkgs.length; j++) {
				IPackageFragmentRoot pkg = pkgs[j];
				IPath path = new Path("/").append(this.projectName).append(folder);
				if (pkg.getPath().toString().equalsIgnoreCase(path.toString())) {
					found = true;
				}
				;
			}
			assertTrue("Expected folder: " + folder, found);
		}
	}

	public   void asyncPrint(final List<MenuItem> list) {
		UIThreadRunnable.asyncExec(bot.getDisplay(), new VoidResult() {
			public void run() {
				for (MenuItem menuItem : list) {
					System.out.println(menuItem.getText());
				}
			}
		});

	}

	private SWTBotTree setupTreeForMenu(String... nodes) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		return tree;
	}

	public void removeNature() {
		SWTBotTree tree = setupTreeForMenu(this.projectName);
		SWTBotMenu menu = new SWTBotMenu(
				ContextMenuHelper.contextMenu(tree, new String[] { "GW4E", "Remove GW4E Nature" }));
		menu.click();

		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				boolean b = ClasspathManager
						.hasGW4EClassPathContainer(ResourceManager.getProject(projectName));
				return !b;
			}

			@Override
			public String getFailureMessage() {
				return "GW4E ClassPath Container not removed";
			}
		});
	}

	public void assertMenuEnabled(String[] menus, boolean[] states, String... nodes) {
		SWTBotTree tree = setupTreeForMenu(nodes);

		SWTBotMenu menu = new SWTBotMenu(ContextMenuHelper.contextMenu(tree, menus[0]));
		assertEquals("Invalid state ", states[0], menu.isEnabled());

		String[] submenus = new String[menus.length - 1];
		System.arraycopy(menus, 1, submenus, 0, menus.length - 1);
		boolean[] substates = new boolean[states.length - 1];
		System.arraycopy(states, 1, substates, 0, states.length - 1);
		for (int i = 0; i < submenus.length; i++) {
			String submenu = submenus[i];
			SWTBotMenu sm = menu.contextMenu(submenu);
			assertEquals("Invalid state ", substates[i], sm.isEnabled());
		}
	}

	public void convertExistingProject() throws CoreException {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(this.projectName);
		item.setFocus();
		item.select();
		SWTBotMenu menu = item.contextMenu("Configure").contextMenu("Convert to GW4E");
		menu.click();
		bot.waitUntil(new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				boolean b = GW4ENature
						.hasGW4ENature(ResourceManager.getProject(projectName));
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "GraphWalker has not GraphWalker Nature ";
			}
		});
		cleanBuild();
	}

	public SWTBotMenu canGenerateSource(boolean enabled, String... nodes) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		SWTBotMenu menu = item.contextMenu("GW4E").contextMenu("Generate Test and Interface");
		assertEquals("Invalid state ", enabled, menu.isEnabled());
		return menu;
	}

	
	
	
	
	private ConvertDialog prepareConvertTo(String project, String packageRootFragment, String pkg,
			String targetFilename, String targetFormat, String... nodes) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.select();
		item.setFocus();

		SWTBotMenu menu = item.contextMenu("GW4E").contextMenu("Convert to...");
		menu.click();

		bot.waitUntil(Conditions.shellIsActive("GW4E Conversion File"));
		SWTBotShell shell = bot.shell("GW4E Conversion File");

		ConvertDialog cd = new ConvertDialog(shell);
		return cd;
	}

	public boolean convertTo(String project, String packageRootFragment, String pkg, String targetFilename,
			String targetFormat, String checkTestBox, String... nodes) {
		ConvertDialog cd = prepareConvertTo(project, packageRootFragment, pkg, targetFilename, targetFormat, nodes);
		boolean ret = cd.create(project, packageRootFragment, pkg, targetFilename, targetFormat, checkTestBox);
		if (!ret) {
			SWTBotShell shell = bot.shell("GW4E Conversion File");
			shell.bot().button("Cancel").click();
			bot.waitUntil(Conditions.shellCloses(shell));
			return false;
		}
		return true;
	}
	
	public boolean walkToToOfflinePage(String project, String packageRootFragment, String pkg, String targetFilename,
			String targetFormat, String checkTestBox, String... nodes) {
		ConvertDialog cd = prepareConvertTo(project, packageRootFragment, pkg, targetFilename, targetFormat, nodes);
		ICondition convertPageCompletedCondition = new DefaultCondition () {
			@Override
			public boolean test() throws Exception {
				boolean ret = cd.create(project, packageRootFragment, pkg, targetFilename, targetFormat, checkTestBox,false);
				if (!ret) {
					SWTBotShell shell = bot.shell("GW4E Conversion File");
					shell.bot().button("Cancel").click();
					bot.waitUntil(Conditions.shellCloses(shell));
					return false;
				}
				return true;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the wizard page.";
			}
		};		
		bot.waitUntil(convertPageCompletedCondition);
		
		bot.button("Next >").click();
		
		return true;
	}

	public boolean prepareconvertTo(String project, String packageRootFragment, String pkg, String targetFilename,
			String targetFormat, String checkTestBox,String annotationStartElement,
			String targetvertex,String startElement,String [] contexts, String... nodes) {
		ConvertDialog cd = prepareConvertTo(project, packageRootFragment, pkg, targetFilename, targetFormat, nodes);
		boolean ret = cd.create(project, packageRootFragment, pkg, targetFilename, targetFormat, checkTestBox, false);
		if (!ret) {
			SWTBotShell shell = bot.shell("GW4E Conversion File");
			shell.bot().button("Cancel").click();
			bot.waitUntil(Conditions.shellCloses(shell));
			return false;
		}

		SWTBotButton fbutton = bot.button("Next >");
		fbutton.click();

		GraphWalkerTestUIPageTest gwid = new GraphWalkerTestUIPageTest(cd.getShell());
		gwid.completeFullPage("random(edge_coverage(100))", "GROUP1;GROUP2", annotationStartElement);
		gwid.nextPage();
		
		JUnitGraphWalkerTestUIPageTest jugw = new JUnitGraphWalkerTestUIPageTest(cd.getShell());
		jugw.completeFullPage(targetvertex, startElement, contexts);
		jugw.nextPage();

		GraphWalkerTestHookPageTest gwth = new GraphWalkerTestHookPageTest(cd.getShell());
		gwth.completeFullPage();
		gwth.finish();

		bot.waitUntil(Conditions.shellCloses(cd.getShell()), 6 * SWTBotPreferences.TIMEOUT);

		return true;
	}

	public void convertToExisting(String project, String packageRootFragment, String pkg, String targetFilename,
			String targetFormat, String checkTestBox, String... nodes) {
		ICondition condition = new DefaultCondition() {
			@Override
			public boolean test() throws Exception {
				ConvertDialog cd = prepareConvertTo(project, packageRootFragment, pkg, targetFilename, targetFormat,
						nodes);
				boolean b = cd.createExisting(project, packageRootFragment, pkg, targetFilename, targetFormat,
						checkTestBox);
				return b;
			}

			@Override
			public String getFailureMessage() {
				return "Unable to complete the convert to wizard";
			}
		};
		bot.waitUntil(condition, 3 * SWTBotPreferences.TIMEOUT);
	}

	public void generateSource(String... nodes) {
		SWTBotTree tree = getProjectTree();
		SWTBotTreeItem item = tree.expandNode(nodes);
		item.setFocus();
		item.select();
		SWTBotMenu menu = item.contextMenu("GW4E").contextMenu("Generate Test and Interface");
		assertEquals("Invalid state ", true, menu.isEnabled());
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				menu.click();
			}
		});

	}

	public String createGraphMLFile(String folder, String pkg, String graphMLFilename)
			throws CoreException, FileNotFoundException {
		String content = readFile(graphMLFilename);
		ResourceManager.createFile(this.projectName, folder, pkg, graphMLFilename, content);
		return graphMLFilename;
	}

	private String readFile(String file) throws FileNotFoundException {
		StringBuilder result = new StringBuilder("");
		Scanner scanner = null;
		try {
			scanner = new Scanner(this.getClass().getResourceAsStream("void.graphml"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
		} finally   {
			if (scanner!=null) scanner.close();
		}
		
		return result.toString();
	}

	public class isItemSelected extends DefaultCondition {
		private SWTBotTreeItem checkedItem;

		public isItemSelected(SWTBotTreeItem item) {
			this.checkedItem = item;
		}

		public String getFailureMessage() {
			return "item '" + checkedItem + "' is not clicked in the problem view";
		}

		public boolean test() throws Exception {
			try {
				return checkedItem.isSelected();
			} catch (WidgetNotFoundException e) {
				return false;
			}
		}
	}

	private void createProjectWithTemplate(String gwproject, String label, String[] resources) throws CoreException {
		String[] folders = PreferenceManager.getAuthorizedFolderForGraphDefinition();
		GW4EProject project = new GW4EProject(bot, gwproject);
		project.resetToJavPerspective();
		project.createProject(label);
		
		project.waitForNoError();
		
 
		for (int i = 0; i < resources.length; i++) {
			if (resources[i] != null && resources[i].trim().length() > 0) {
				bot.waitUntil(new EditorOpenedCondition(bot, (resources[i])), 30000);
			}
		}
	}

	public String[] createWithNoTemplate(String gwproject) throws CoreException {
		String[]  resources =  new String[] { NoneTemplate.RESOURCE };
		createProjectWithTemplate(gwproject, NoneTemplate.LABEL, resources);
		return resources;
	}

	public String[] createWithEmptyTemplate(String gwproject) throws CoreException {
		String[]  resources =  new String[] { EmptyTemplate.RESOURCE };
		createProjectWithTemplate(gwproject, EmptyTemplate.LABEL, resources);
		return resources;
	}

	public String[] createWithSimpleTemplate(String gwproject) throws CoreException {
		String[]  resources =  new String[] { SimpleTemplate.RESOURCE };
		createProjectWithTemplate(gwproject, SimpleTemplate.LABEL, resources);
		return resources;
	}

	public String[]  createtWithSimpleScriptedTemplate(String gwproject) throws CoreException {
		String[]  resources =  new String[] { SimpleTemplateWithScript.RESOURCE };
		createProjectWithTemplate(gwproject, SimpleTemplateWithScript.LABEL,resources);
		return resources;
	}

	public String[]  createWithSharedTemplate(String gwproject) throws CoreException {
		String[]  resources =  SharedTemplate.RESOURCES;
		createProjectWithTemplate(gwproject, SharedTemplate.LABEL, resources);
		return resources;
	}

}
