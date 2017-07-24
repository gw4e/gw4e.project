package org.gw4e.eclipse.xl.util;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gw4e.eclipse.message.MessageUtil;

public class XLTestDetailsSheet extends XLTest {

	public XLTestDetailsSheet(XSSFWorkbook wb) {
		super(wb);
	}

	static int STEPNAME_INDEX = 0;
	static int EXPECTED_OR_ACTION_INDEX = 1;
	static int RESULT_INDEX = 2;
	static int STATUS_INDEX = 3;

	private String getValueAsString(String caseid, int rowindex, int column) {
		Sheet sheet = wb.getSheet(caseid);
		Row row = sheet.getRow(rowindex);
		return row.getCell(column).getStringCellValue();
	}
	
	private String getValueAsInt(String caseid, int rowindex, int column) {
		Sheet sheet = wb.getSheet(caseid);
		Row row = sheet.getRow(rowindex);
		return ((int)row.getCell(column).getNumericCellValue())+"";
	}

	public String getStep(String caseid, int rowindex) {
		return getValueAsString(caseid, rowindex, STEPNAME_INDEX);
	}
	public String getExpectedOrAction(String caseid, int rowindex) {
		return getValueAsString(caseid, rowindex, EXPECTED_OR_ACTION_INDEX);
	}
	public String getResult(String caseid, int rowindex) {
		return getValueAsString(caseid, rowindex, RESULT_INDEX);
	}	
	public String getStatus(String caseid, int rowindex) {
		try {
			return getValueAsString(caseid, rowindex, STATUS_INDEX);
		} catch (Exception e) {
			return getValueAsInt(caseid, rowindex, STATUS_INDEX);
		}
	}	
	
	public void feedDetailsSheet(Sheet sheet, boolean exportAsTemplate, List<XLTestStep> xLTestSteps) {
		int index = 0;
		for (XLTestStep xLTestStep : xLTestSteps) {
			index++;
			Row row = sheet.createRow(index);
			Cell cell = row.createCell(STEPNAME_INDEX);
			cell.setCellValue(xLTestStep.getName());
			cell = row.createCell(EXPECTED_OR_ACTION_INDEX);
			cell.setCellValue(xLTestStep.getExpected());
			cell = row.createCell(RESULT_INDEX);
			if (exportAsTemplate)
				cell.setCellValue("");
			else
				cell.setCellValue(xLTestStep.getActual());
			cell = row.createCell(STATUS_INDEX);
			formatCellStatus(sheet, cell);
			if (exportAsTemplate)
				cell.setCellValue("");
			else
				cell.setCellValue(xLTestStep.getStatus());
		}
		this.autoSize(sheet, new int[] { 0, 1, 2, 3 });
	}

	public Sheet getOrCreateDetailsSheet(String testcaseid, boolean update) {
		Sheet sheet = wb.getSheet(testcaseid);
		if (sheet == null) {
			sheet = createSheet(testcaseid);
		} else {
			if (update) {
				int index = wb.getSheetIndex(sheet);
				wb.removeSheetAt(index);
				sheet = createSheet(testcaseid);
			} else {
				String name = getNextDetailsSheetName(testcaseid);
				sheet = createSheet(name);
			}
		}
		configurePrintSetup(sheet);
		createHeader(sheet, 0, titlesDetails);

		return sheet;
	}

	private static final String[] titlesDetails = { MessageUtil.getString("xl_Test_Steps"),
			MessageUtil.getString("xl_Expected_Result"), MessageUtil.getString("xl_Actual_Result"),
			MessageUtil.getString("xl_Status"), };

	private int createHeader(Sheet sheet, int rowNum, String[] titles) {
		Row headerRow = sheet.createRow(rowNum);
		headerRow.setHeightInPoints(40);
		Cell headerCell;
		int[] cols = new int[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cols[i] = i;
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(titles[i]);
			headerCell.setCellStyle(styles.get("header"));
		}
		autoSize(sheet, cols);
		return rowNum;
	}

}
