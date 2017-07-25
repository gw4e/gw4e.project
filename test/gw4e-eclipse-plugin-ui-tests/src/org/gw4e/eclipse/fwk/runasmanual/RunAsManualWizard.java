package org.gw4e.eclipse.fwk.runasmanual;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotStyledText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.fwk.conditions.ResourceExists;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.xl.util.XLFacade;
import org.gw4e.eclipse.xl.util.XLTestDetailsSheet;
import org.gw4e.eclipse.xl.util.XLTestSummarySheet;

public class RunAsManualWizard {
	SWTWorkbenchBot bot;

	public RunAsManualWizard(SWTWorkbenchBot bot) {
		this.bot = bot;
	}

	public void assertTestPresentatioPageContainsTexts(SWTBotShell page, String[] texts) {
		new TestPresentatioPage().assertTexts(page, texts);
	}

	public void assertNextStepPage(SWTBotShell page, String description, String result) {
		SWTBotButton nextButton = bot.button("&Next >");
		nextButton.click();
		String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
		if (result != null && result.trim().length() > 0 && !result.equals(defaultResult)) {
			new StepPage().setResult(page, result).setFailed(page);
		}
		new StepPage().assertActionAndResult(page, description, result);
	}

	public void assertSummaryExecution(SWTBotShell page, SummaryExecutionRow[] rows) {
		SWTBotButton nextButton = bot.button("&Next >");
		nextButton.click();
		new SummaryExecutionPage().assertContext(page, rows);
	}

	public void feed(boolean exportAsTest, String workbookfile, String workbooktitle, String caseid, boolean updatemode,
			String componentname, String priority, String dateformat) {
		SWTBotButton nextButton = bot.button("&Next >");
		nextButton.click();
		SavePage page = new SavePage();
		page.feed(exportAsTest, workbookfile, workbooktitle, caseid, updatemode, componentname, priority, dateformat);
	}

	public void finish() {
		SWTBotButton finishButton = bot.button("&Finish");
		finishButton.click();
	}

	public long assertManuelTestTemplateSpreadSheet(String workbookfile, SummaryExecutionRow[] rows,
			String workbooktitle, String caseid, String componentname, String priority, Date date, DateFormat format,
			String description, int row,String status,boolean exportAsTemplate) throws IOException {
		WorkBook wb = new WorkBook(workbookfile, rows, workbooktitle, caseid, componentname, priority, date, format,
				description);
		wb.exists();
		if (exportAsTemplate) status =  "";
		wb.assertSummaryCase(row,status,exportAsTemplate);
		wb.assertDetailCase(exportAsTemplate);
		return wb.lastModified();
	}

 
	public class TestPresentatioPage {
		public void assertTexts(SWTBotShell page, String[] texts) {
			SWTBotStyledText styledText = page.bot().styledText();
			for (String text : texts) {
				if (styledText.getText().indexOf(text) == -1) {
					org.junit.Assert.fail("Text does not contain " + text);
				}
			}
		}
	}

	public class StepPage {
		public void assertActionAndResult(SWTBotShell page, String description, String result) {
			SWTBotStyledText styledTextDescription = page.bot().styledTextWithId(
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_STEP_PAGE_DESCRIPTION_ID);
			SWTBotStyledText styledTextResult = page.bot().styledTextWithId(
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_STEP_PAGE_RESULT_ID);

			org.junit.Assert.assertEquals("Invalid Result", result, styledTextResult.getText());
			org.junit.Assert.assertEquals("Invalid Description", description, styledTextDescription.getText());
		}

		public StepPage setResult(SWTBotShell page, String result) {
			SWTBotStyledText styledTextResult = page.bot().styledTextWithId(
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_STEP_PAGE_RESULT_ID);
			styledTextResult.setText(result);
			return this;
		}

		public void setFailed(SWTBotShell page) {
			SWTBotCheckBox button = page.bot().checkBoxWithId(
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_LAUNCH_CONFIGURATION_CONTROL_ID,
					org.gw4e.eclipse.wizard.runasmanual.StepPage.GW4E_STEP_PAGE_BUTTON_FAILED_ID);
			if (!button.isChecked())
				button.click();
		}

	}

	public static class SummaryExecutionRow {
		String status;
		String stepname;
		String result;
		String description;

		public SummaryExecutionRow(String status, String stepname, String result, String description) {
			super();
			this.status = status;
			this.stepname = stepname;
			this.result = result;
			this.description = description;
		}

		public String getStatus() {
			return status;
		}

		public String getStepname() {
			return stepname;
		}

		public String getResult() {
			return result;
		}

		public String getDescription() {
			return description;
		}
	}

	public class SummaryExecutionPage {
		private String getStatus(SWTBotTableItem item) {
			String[] status = new String[1];
			Display.getDefault().syncExec(() -> {
				org.gw4e.eclipse.launching.runasmanual.StepDetail detail = (org.gw4e.eclipse.launching.runasmanual.StepDetail) item.widget
						.getData();
				status[0] = detail.getStatus() + "";
			});
			return status[0];
		}

		public void assertContext(SWTBotShell page, SummaryExecutionRow[] rows) {
			SWTBotTable table = bot.tableWithId(
					org.gw4e.eclipse.wizard.runasmanual.SummaryExecutionPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SummaryExecutionPage.GW4E_MANUAL_TABLE_VIEWER_SUMMARY_ID);
			int max = table.rowCount();
			for (int i = 0; i < max; i++) {
				SWTBotTableItem item = table.getTableItem(i);

				String status = getStatus(item);
				String step = item.getText(1);
				String result = item.getText(2);
				String description = item.getText(4);

				String statusExpected = rows[i].getStatus() + "";
				String stepExpected = rows[i].getStepname();
				String resultExpected = rows[i].getResult();

				String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
				if (defaultResult.equalsIgnoreCase(resultExpected)) {
					resultExpected = "";
				}

				String descriptionExpected = rows[i].getDescription();
				org.junit.Assert.assertEquals("Invalid status", statusExpected, status);
				org.junit.Assert.assertEquals("Invalid step", stepExpected, step);
				org.junit.Assert.assertEquals("Invalid result", resultExpected, result);
				org.junit.Assert.assertEquals("Invalid description", descriptionExpected, description);
			}

		}
	}

	public class SavePage {
		public SavePage() {
		}

		private SWTBotText getTextCaseId() {
			return bot.textWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_TEXTCASE_ID);
		}

		private SWTBotText getTextDateFormat() {
			return bot.textWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_TESTEXT_DATE_FORMAT_ID);
		}

		private SWTBotText getTextWorkbookTitle() {
			return bot.textWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_TEXT_WORKBOOK_TITLE_ID);
		}

		private SWTBotText getTextWorkBook() {
			return bot.textWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_TEXT_WORKBOOK_ID);
		}

		private SWTBotCombo getComboWorkBook() {
			return bot.comboBoxWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_COMBO_WORKBOOK_ID);
		}

		private SWTBotText getTextComponentName() {
			return bot.textWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_TEXT_COMPONENT);
		}

		private SWTBotCombo getComboPriority() {
			return bot.comboBoxWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_COMBO_PRIORITY);
		}

		private SWTBotRadio getExportAsTestTemplateButton() {
			return bot.radioWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_BUTTON_EXPORT_AS_TEMPLATE);
		}

		private SWTBotRadio getExportAsTestResultButton() {
			return bot.radioWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_BUTTON_EXPORT_AS_RESULT);
		}

		private SWTBotCheckBox getBtnUpdateIfTestcaseid() {
			return bot.checkBoxWithId(org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_ELEMENT_ID,
					org.gw4e.eclipse.wizard.runasmanual.SaveTestPage.GW4E_MANUAL_BUTTON_UPDATE_IF_TESTCASE_ID);
		}

		public void feed(boolean exportAsTest, String workbookfile, String workbooktitle, String caseid,
				boolean updatemode, String componentname, String priority, String dateformat) {
			if (exportAsTest) {
				getExportAsTestResultButton().click();
			} else {
				getExportAsTestTemplateButton().click();
			}

			try {
				SWTBotCombo combo = getComboWorkBook();
				combo.setSelection(workbookfile);
			} catch (Exception e) {
				getTextWorkBook().setText(workbookfile);
			}

			getTextWorkbookTitle().setText(workbooktitle);
			getTextCaseId().setText(caseid);
			if (updatemode) {
				getBtnUpdateIfTestcaseid().click();
			}
			getTextComponentName().setText(componentname);
			getComboPriority().setSelection(priority);
			if (dateformat != null)
				getTextDateFormat().setText(dateformat);
		}
	}

	public class WorkBook {
		String workbookfile;
		SummaryExecutionRow[] rows;
		String workbooktitle;
		String caseid;
		String componentname;
		String priority;
		String description;
		Date date;
		DateFormat format;

		public WorkBook(String workbookfile, SummaryExecutionRow[] rows, String workbooktitle, String caseid,
				String componentname, String priority, Date date, DateFormat format, String description) {
			super();
			this.workbookfile = workbookfile;
			this.rows = rows;
			this.workbooktitle = workbooktitle;
			this.caseid = caseid;
			this.componentname = componentname;
			this.priority = priority;
			this.date = date;
			this.description = description;
			this.format = format;
		}
		
		public WorkBook(String workbookfile) {
			super();
			this.workbookfile = workbookfile;
		}

		public void exists() {
			ICondition condition = new ResourceExists(new Path(workbookfile));
			bot.waitUntil(condition);
		}

		public void assertSummaryCase(int row,final String status,boolean exportAsTemplate) throws IOException {
			IResource resource = ResourceManager.getResource(workbookfile);
		 
			File f = ResourceManager.toFile(resource.getFullPath());
			
			System.out.println("XXX FILE DATE2 XXXXXXXXXXXXX " + f.lastModified());
			
			ICondition condition = new DefaultCondition () {
				@Override
				public boolean test() throws Exception {
					try {
						XLFacade helper = XLFacade.getWorkbook(f);
						XLTestSummarySheet summarySheet = helper.getSummary();
						summarySheet.print ();
						org.junit.Assert.assertEquals("Invalid Status", status, summarySheet.getStatus(row));
						return true;
					} catch (Throwable e) {
						 return false;
					}
				}
				@Override
				public String getFailureMessage() {
					try {
						XLFacade helper = XLFacade.getWorkbook(f);
						XLTestSummarySheet summarySheet = helper.getSummary();
						return "Invalid Status. Was expecting " + status + " but found " + summarySheet.getStatus(row) ;
					} catch (IOException e) {
						org.junit.Assert.fail ("Unable to read the spreadsheet");
						return "Unable to read the spreadsheet";
					}
				}
				
			};
			bot.waitUntil(condition,15*1000);
			XLFacade helper = XLFacade.getWorkbook(f);
			XLTestSummarySheet summarySheet = helper.getSummary();
			org.junit.Assert.assertEquals("Invalid Date", format.format(date),format.format(summarySheet.getDate(row)));
			org.junit.Assert.assertEquals("Invalid Case Id", caseid, summarySheet.getCaseId(row));
			org.junit.Assert.assertEquals("Invalid Component", componentname, summarySheet.getComponent(row));
			org.junit.Assert.assertEquals("Invalid Priority", priority, summarySheet.getPriority(row));
			org.junit.Assert.assertEquals("Invalid Description", description, summarySheet.getDescription(row));
			org.junit.Assert.assertEquals("Invalid Title", workbooktitle, summarySheet.getTitle());
		}

		public long lastModified() throws IOException {
			IResource resource = ResourceManager.getResource(workbookfile);
			File f = ResourceManager.toFile(resource.getFullPath());
			return f.lastModified();
		}	
		
		public void assertDetailCase(boolean exportAsTemplate) throws IOException {
			IResource resource = ResourceManager.getResource(workbookfile);
			File f = ResourceManager.toFile(resource.getFullPath());
			XLFacade helper = XLFacade.getWorkbook(f);
			XLTestDetailsSheet details = helper.getDetails();
			for (int i = 0; i < rows.length; i++) {
				SummaryExecutionRow row = rows[i];
				String statusExpected  = row.getStatus();
				if (exportAsTemplate) statusExpected =  "";
				org.junit.Assert.assertEquals("Invalid Status", statusExpected, details.getStatus(caseid, i + 1));
				org.junit.Assert.assertEquals("Invalid Expected/Action",row.getDescription(), details.getExpectedOrAction(caseid, i + 1));
				
				String resultExpected = row.getResult();
				String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
				if (defaultResult.equalsIgnoreCase(resultExpected)) {
					resultExpected = "";
				}
				
				org.junit.Assert.assertEquals("Invalid Result", details.getResult(caseid, i + 1),resultExpected);
				org.junit.Assert.assertEquals("Invalid Step", details.getStep(caseid, i + 1), row.getStepname());
			}
		}
	}

}
