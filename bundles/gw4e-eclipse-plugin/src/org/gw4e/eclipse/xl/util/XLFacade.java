
package org.gw4e.eclipse.xl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jface.resource.ImageDescriptor;
import org.gw4e.eclipse.Activator;
import org.gw4e.eclipse.facade.DialogManager;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.launching.runasmanual.StepDetail;
import org.gw4e.eclipse.message.MessageUtil;
import org.gw4e.eclipse.wizard.runasmanual.ITestPersistence;

public class XLFacade implements ITestPersistence {
	private Workbook wb;
	private File file;
	private XLTestSummarySheet summary;
	private XLTestDetailsSheet details;

	private XLFacade() {
		
	}
	
	public XLFacade(XSSFWorkbook wb, File file) {
		super();
		this.file = file;
		this.wb = wb;
		this.summary = new XLTestSummarySheet(wb);
		this.details = new XLTestDetailsSheet(wb);
	}
	
	
	public static ITestPersistence getPersistenceService  () {
		return new XLFacade ();
	}

	public static XLFacade getWorkbook(File file) throws IOException {
		return  createWorkbook(file,null);
	}
	
	public static XLFacade createWorkbook(File file, String title) throws IOException {
		XSSFWorkbook wb = null;
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			wb = new XSSFWorkbook(in);
		} else {
			wb = new XSSFWorkbook();
		}
		if (title!=null){
			POIXMLProperties xmlProps = wb.getProperties();
			xmlProps.getCoreProperties().setTitle(title);
		}
		return new XLFacade(wb, file);
	}

	public static String getWorkBookTitle(File file) {
		XSSFWorkbook wb = null;
		try {
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				wb = new XSSFWorkbook(in);
			} else {
				wb = new XSSFWorkbook();
			}
			POIXMLProperties xmlProps = wb.getProperties();
			return xmlProps.getCoreProperties().getTitle();
		} catch (Exception e) {
			ResourceManager.logException(e);
			return null;
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					ResourceManager.logException(e);
				}
			}
		}
	}

	public void save() throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	public XLTestSummarySheet getSummary() {
		return summary;
	}

	public XLTestDetailsSheet getDetails() {
		return details;
	}

	@Override
	public void persist(File file, String title, boolean exportAsTemplate,String dateFormat,String testcaseid, String component,  String priority,
			String description,boolean updateDetailSheet, List<StepDetail> details) {
		try {
			XLFacade helper = XLFacade.createWorkbook(file,title);
			Sheet summarySheet = helper.getSummary().getOrCreateSummary();
			List<XLTestStep> steps = new ArrayList<XLTestStep> ();
			boolean failed = false;
			for (StepDetail sd : details) {
				if (sd.isFailed()) failed=true;
				String defaultResult = MessageUtil.getString("enter_a_result_if_verification_failed");
				String result = sd.getResult();
				if (defaultResult.equalsIgnoreCase(sd.getResult())) {
					result = "";
				}
				int status = sd.getStatus();
				XLTestStep step = new XLTestStep(sd.getName(), sd.getDescription(), result, status) ;
				steps.add(step);
			}
			Sheet sheetDetails = helper.getDetails().getOrCreateDetailsSheet(testcaseid, updateDetailSheet);
			helper.getDetails().feedDetailsSheet(sheetDetails,exportAsTemplate, steps);

			helper.getSummary().addSummaryResultEntry(
					summarySheet, 
					updateDetailSheet,
					exportAsTemplate,
					new Date (), 
					dateFormat, 
					testcaseid,
					component, 
					failed ? 0 : 1, 
					priority,
					description,
					sheetDetails.getSheetName());
			helper.save();
		} catch (IOException e) {
			ResourceManager.logException(e);
			DialogManager.displayErrorMessage(MessageUtil.getString("error"), MessageUtil.getString("manual_export_an_error_has_occured_while_generating_spreadsheet"), e);
		}
	}

 
}
