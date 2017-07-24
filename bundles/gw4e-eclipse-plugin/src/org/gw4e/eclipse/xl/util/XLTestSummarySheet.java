package org.gw4e.eclipse.xl.util;

import java.util.Date;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gw4e.eclipse.message.MessageUtil;

public class XLTestSummarySheet extends XLTest {

	final int DATE_FORMAT_COLUMN_INDEX = 0;
	final int TESTCASE_ID_COLUMN_INDEX = 1;
	final int COMPONENT_COLUMN_INDEX = 2;
	final int STATUS_COLUMN_INDEX = 3;
	final int PRIORiTY_COLUMN_INDEX = 4;
	final int DESCRIPTION_COLUMN_INDEX = 5;
	String title;
	private static final String[] titlesSummary = { MessageUtil.getString("xl_Date"),
			MessageUtil.getString("xl_TestCaseId"), MessageUtil.getString("xl_Component"),
			MessageUtil.getString("xl_Status"), MessageUtil.getString("xl_Priority"),
			MessageUtil.getString("xl_Description"), };
	
   

	public XLTestSummarySheet(XSSFWorkbook wb) {
		super(wb);
		POIXMLProperties xmlProps  =  wb.getProperties();
		title = xmlProps.getCoreProperties().getTitle();
	}
	
	private String getValue(int index, int column) {
		Sheet sheet =  getOrCreateSummary();
		Row row = sheet.getRow(index);
		Cell cell = row.getCell(column);
		return cell.getStringCellValue();
	}
	private String getValueAsInt(int index, int column) {
		Sheet sheet =  getOrCreateSummary();
		Row row = sheet.getRow(index);
		Cell cell = row.getCell(column);
		return ((int)cell.getNumericCellValue())+"";
	}
	public Date getDate (int index) {
		Sheet sheet =  getOrCreateSummary();
		Row row = sheet.getRow(index);
		Cell cell = row.getCell(DATE_FORMAT_COLUMN_INDEX);
		return cell.getDateCellValue();
	}

	public String getCaseId(int index) {
		return getValue(index,TESTCASE_ID_COLUMN_INDEX);
	}
	public String getComponent(int index) {
		return getValue(index,COMPONENT_COLUMN_INDEX);
	}
	public String getStatus(int index) {
		try {
			return getValue(index,STATUS_COLUMN_INDEX);
		} catch (Exception e) {
			return getValueAsInt(index,STATUS_COLUMN_INDEX);
		}
	}
	public String getPriority(int index) {
		return getValue(index,PRIORiTY_COLUMN_INDEX);
	}
	public String getDescription(int index) {
		return getValue(index,DESCRIPTION_COLUMN_INDEX);
	}
	public String getTitle ( ) {
		Sheet sheet =  getOrCreateSummary();
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		return cell.getStringCellValue();
	}
	
	public void addSummaryResultEntry(Sheet sheet, boolean update,  boolean exportAsTemplate, Date date, String dateFormat, String testcaseid,
			String component, int status, String priority, String description, String sheetDetailsName) {
		Row row = null;
		if (update) {
			for (Row temp : sheet) {
				Cell cell = temp.getCell(TESTCASE_ID_COLUMN_INDEX);
				if (cell == null)
					continue;
				if (testcaseid.equalsIgnoreCase(cell.getStringCellValue())) {
					row = temp;
					break;
				}
			}
		}
		if (row == null) {
			int rowNum = sheet.getLastRowNum() + 1;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(15);
			Cell cell = row.createCell(DATE_FORMAT_COLUMN_INDEX);
			formatCellDate(sheet, cell, dateFormat);

			cell = row.createCell(TESTCASE_ID_COLUMN_INDEX);
			cell.setCellStyle(styles.get("cell"));

			cell = row.createCell(COMPONENT_COLUMN_INDEX);

			cell = row.createCell(STATUS_COLUMN_INDEX);
			cell.setCellStyle(styles.get("cell"));
			formatCellStatus(sheet, cell);

			cell = row.createCell(PRIORiTY_COLUMN_INDEX);
			cell.setCellStyle(styles.get("cell"));

			cell = row.createCell(DESCRIPTION_COLUMN_INDEX);
			 
		}

		int index = -1;
		for (Cell cell : row) {
			index++;
			switch (index) {
			case DATE_FORMAT_COLUMN_INDEX:
				cell.setCellValue(date);
				break;
			case TESTCASE_ID_COLUMN_INDEX:
				cell.setCellValue(testcaseid);
				CreationHelper createHelper = wb.getCreationHelper();
				Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
			    link.setAddress("'" + sheetDetailsName + "'!A1");
			    cell.setHyperlink(link);
			    cell.setCellStyle(styles.get("link"));
				break;
			case COMPONENT_COLUMN_INDEX:
				cell.setCellValue(component);
				break;
			case STATUS_COLUMN_INDEX:
				if (exportAsTemplate) 
					cell.setCellValue("");
				else
					cell.setCellValue(status);
				break;
			case PRIORiTY_COLUMN_INDEX:
				cell.setCellValue(priority);
				break;
			case DESCRIPTION_COLUMN_INDEX:
				cell.setCellValue(description);
				break;
			}
		}
		autoSize(sheet, new int[] { DATE_FORMAT_COLUMN_INDEX, TESTCASE_ID_COLUMN_INDEX, COMPONENT_COLUMN_INDEX,
				STATUS_COLUMN_INDEX, PRIORiTY_COLUMN_INDEX, DESCRIPTION_COLUMN_INDEX });
		createFilter(sheet);
	}
	
	private void createFilter(Sheet sheet) {
		int startRow = ROW_INDEX_COLUMNS_DESCRIPTIONS;
		int lastRow = ROW_INDEX_COLUMNS_DESCRIPTIONS + 1;
		int startCol = 0;
		int lastCol = sheet.getRow(startRow).getLastCellNum() - 1;
		sheet.setAutoFilter(new CellRangeAddress(startRow, lastRow, startCol, lastCol));

	}
	
	private void formatCellDate(Sheet sheet, Cell cell, String format) {
		CellStyle style = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		style.setDataFormat(createHelper.createDataFormat().getFormat(format));
		cell.setCellStyle(style);
		 
	}
	
	public Sheet getOrCreateSummary() {
		String sheetName = MessageUtil.getString("xl_Summary");
		Sheet sheet = wb.getSheet(sheetName);
		if (sheet == null) {
			sheet = createSheet(sheetName);
			configurePrintSetup(sheet);
			createTitle(sheet, title);
			createSummaryColumns(sheet);
		}
		return sheet;
	}
	
	private void createSummaryColumns(Sheet sheet) {
		Row headerRow = sheet.createRow(ROW_INDEX_COLUMNS_DESCRIPTIONS);
		headerRow.setHeightInPoints(40);
		Cell headerCell;
		for (int i = 0; i < titlesSummary.length; i++) {
			headerCell = headerRow.createCell(i);
			headerCell.setCellValue(titlesSummary[i]);
			headerCell.setCellStyle(styles.get("header"));
		}
	}
	
	private void createTitle(Sheet sheet, String xl_sheet_title) {
		Row titleRow = sheet.createRow(ROW_INDEX_TITLE);
		titleRow.setHeightInPoints(45);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(xl_sheet_title);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$F$1"));
	}

	 

}
