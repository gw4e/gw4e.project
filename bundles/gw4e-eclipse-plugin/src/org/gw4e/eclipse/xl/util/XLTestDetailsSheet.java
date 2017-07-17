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


	public void feedDetailsSheet(Sheet sheet, boolean exportAsTemplate,List<XLTestStep> xLTestSteps) {
		int index = 0;
		for (XLTestStep xLTestStep : xLTestSteps) {
			index++;
			Row row = sheet.createRow(index);
			Cell cell = row.createCell(0);
			cell.setCellValue(xLTestStep.getName());
			cell = row.createCell(1);
			cell.setCellValue(xLTestStep.getExpected());
			cell = row.createCell(2);
			if (exportAsTemplate) 
				cell.setCellValue("");
			else
				cell.setCellValue(xLTestStep.getActual());
			cell = row.createCell(3);
			formatCellStatus(sheet, cell);
			if (exportAsTemplate) 
				cell.setCellValue("");
			else
				cell.setCellValue(xLTestStep.getStatus());
		}
		this.autoSize(sheet, new int [] {0,1,2,3});
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
