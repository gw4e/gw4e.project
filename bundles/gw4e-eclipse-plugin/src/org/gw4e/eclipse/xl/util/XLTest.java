package org.gw4e.eclipse.xl.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLTest {
	protected static int ROW_INDEX_TITLE = 0;
	protected static int ROW_INDEX_COLUMNS_DESCRIPTIONS = 1;
	protected static int ROW_START_INDEX_DATA = 2;

	protected XSSFWorkbook wb;
	protected Map<String, CellStyle> styles;
	
	public XLTest(XSSFWorkbook wb) {
		super();
		this.wb = wb;
		this.styles = createStyles();
	}
	
	protected Sheet createSheet(String sheetName) {
		Sheet sheet = wb.createSheet(sheetName);
		return sheet;
	}
	
	protected String getNextDetailsSheetName(String testcaseid) {
		Sheet sheet = wb.getSheet(testcaseid);
		if (sheet == null) {
			return testcaseid;
		}
		int index = 0;
		while (true) {
			String name = testcaseid + "_" + index ;
			sheet = wb.getSheet(name);
			if (sheet == null) {
				return name;
			}
			index++;
		}
	}

	
	protected void configurePrintSetup(Sheet sheet) {
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
	}
	
	protected void autoSize(Sheet sheet, int[] cols) {
		for (int i : cols) {
			sheet.autoSizeColumn(cols[i], true);
		}
	}

	protected void formatCellStatus(Sheet sheet, Cell cell) {
		cell.setCellStyle(styles.get("status"));
		SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
		ConditionalFormattingRule ruleGreen = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL, "1");
		PatternFormatting fill1 = ruleGreen.createPatternFormatting();
		fill1.setFillBackgroundColor(IndexedColors.GREEN.index);
		fill1.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
		//
		ConditionalFormattingRule ruleRed = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL, "0");
		PatternFormatting fill2 = ruleRed.createPatternFormatting();
		fill2.setFillBackgroundColor(IndexedColors.RED.index);
		fill2.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
		//
		ConditionalFormattingRule ruleOrange = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL, "2");
		PatternFormatting fill3 = ruleOrange.createPatternFormatting();
		fill3.setFillBackgroundColor(IndexedColors.ORANGE.index);
		fill3.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
		//
		String name = CellReference.convertNumToColString(cell.getColumnIndex());
		String location = "$" + name + "$" + cell.getRowIndex() + ":$" + name + "$" + (cell.getRowIndex() + 1);

		CellRangeAddress[] regions = { CellRangeAddress.valueOf(location) };
		ConditionalFormattingRule[] cfRules = new ConditionalFormattingRule[] { ruleGreen, ruleRed, ruleOrange };
		sheetCF.addConditionalFormatting(regions, cfRules);
	}
	
	/**
	 * Create a library of cell styles
	 */
	protected Map<String, CellStyle> createStyles() {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(titleFont);
		styles.put("title", style);

		Font monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short) 11);
		monthFont.setColor(IndexedColors.WHITE.getIndex());
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(monthFont);
		style.setWrapText(true);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setWrapText(true);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setWrapText(true);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setFontName("Trebuchet MS");
		titleFont.setBold(true);
		style.setFont(titleFont);
		styles.put("cellSummary", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
		styles.put("formula_2", style);

		style = wb.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		styles.put("status", style);

		CellStyle hlink_style = wb.createCellStyle();
	    Font hlink_font = wb.createFont();
	    hlink_font.setUnderline(Font.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    hlink_style.setFont(hlink_font);
	    styles.put("link", hlink_style);
	    
		return styles;
	}

}
