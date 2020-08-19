package com.dharam.datareader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dharam.baseclass.TestBase;

/**
 * @author dhbhador
 */
public class ExcelUtil extends TestBase {

	// public static Workbook inputWorkBook;

	public Workbook getInputWorkBook(final String excelName) {
		synchronized (ExcelUtil.class) {
			if (workBook == null) {
				synchronized (ExcelUtil.class) {
					createExcelInstance(TestBase.prop.getProperty(excelName));
				}
			}
		}
		return workBook;
	}

	private void setInputWorkBook(Workbook inputFile) {
		workBook = inputFile;
	}

	private void createExcelInstance(final String fileName) {
		String filePath = System.getProperty("user.dir") + fileName;
		try (FileInputStream ip = new FileInputStream(new File(filePath))) {
			if (fileName.contains(".xlsx")) {
				setInputWorkBook(new XSSFWorkbook(ip));
			} else if (fileName.contains(".xls")) {
				setInputWorkBook(new HSSFWorkbook(ip));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int nthProduct = 1;

	/**
	 * @author dhbhador
	 * @return Cell data based on the Excel, Sheet and the Column name
	 */
	public static String getSheetData(Workbook book, final String sheet, final String column) {
		Sheet sheetObj = book.getSheet(sheet);
		Row row = sheetObj.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(column)) {
				return sheetObj.getRow(1).getCell(i).getStringCellValue();
			}
		}
		return null;
	}

	public static void updateExcel(Workbook book, String sheet, final String column, final String data) {
		Sheet sheetObj = book.getSheet(sheet);
		Row row = sheetObj.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(column)) {
				sheetObj.getRow(1).getCell(i).setCellValue(data);
				break;
			}
		}
	}

	public static void saveExcelWithUpdatedData() {
		// TODO
	}
}
