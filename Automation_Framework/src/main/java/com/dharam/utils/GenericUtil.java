package com.dharam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dharam.baseclass.TestBase;
import com.dharam.reports.Log;

public class GenericUtil extends TestBase {

	public static void switchToBrowserTab(final WebDriver driver) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
	}

	public static void clickClearAndType(final WebElement element, final String text) {
		element.clear();
		element.click();
		element.sendKeys(text);
	}

	public static void scrollIntoView(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", ele);
	}

	public static void selectDropdownByValue(WebElement dropDownLocator, final String value) {
		Select dropdown = new Select(dropDownLocator);
		dropdown.selectByValue(value);
	}

	public static void switchToNthWindow(final WebDriver driver, final int nthWindow) {
		Set<String> windowHandler = driver.getWindowHandles();
		Iterator<String> iterator = windowHandler.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			if (i == nthWindow - 1) {
				driver.switchTo().window(iterator.next());
				break;
			}
			iterator.next();
		}
	}
	
	public static final WebDriverWait wait = new WebDriverWait(driver, 15);
	
	public static void webdriverWaitUntilElementIsClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static final String TESTDATA_SHEET_PATH = rootDir + "/resources/testdata.xlsx";
	
	static Workbook book;
	static Sheet sheet;
	
	/**
	 * @Description Static Block - Initializing the Workbook.
	 */
	static {
		initializeWorkbook();
	}
	
	private static void initializeWorkbook() {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			Log.error(e.getMessage());
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			Log.fatal(e.getMessage());
		} catch (IOException e) {
			Log.fatal(e.getMessage());
		}
	}
	
	
	/**
	 * @Description Get the data from the {@param sheetName} Sheet. 
	 */
	public static Object[][] getTestData(String sheetName) {
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}
	
	
	/**
	 * @author dhbhador
	 * @return Get Cell data based on the Excel, Sheet and the Column name
	 */
	public static String getSheetData(final String sheet, final String column) {
		Sheet sheetObj = book.getSheet(sheet);
		Row row = sheetObj.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(column)) {
				return sheetObj.getRow(1).getCell(i).getStringCellValue();
			}
		}
		return null;
	}
	
	
	/**
	 * @author dhbhador
	 * @return update Cell data based on the Excel, Sheet and the Column name
	 */
	public static void updateExcel(String sheet, final String column, final String data) {
		Sheet sheetObj = book.getSheet(sheet);
		Row row = sheetObj.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(column)) {
				sheetObj.getRow(1).getCell(i).setCellValue(data);
				break;
			}
		}
	}
	
	
	/**
	 * @Description Saving the Updated Testdata.xlsx as a new named excel file.
	 */
	public static void saveUpdatedExcel() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
			String formatedDateTime = LocalDateTime.now().format(formatter);
			String fileName = TESTDATA_SHEET_PATH;
			fileName = fileName.substring(0, fileName.indexOf(".")).concat("_") + formatedDateTime  
					+ fileName.substring(fileName.indexOf("."));
			FileOutputStream fO = new FileOutputStream(fileName);
			book.write(fO);
		} catch (IOException e) {
			Log.fatal(e.getMessage());
		}
	}
	
	
	/**
	 *@Description For Screenshot taking - During Exception Mainly. 
	 */
	public static void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	/**
	 * @author dhbhador
	 * @description Flushing the Drivers executables from the process, if present.
	 */
	public static void flush() throws IOException {
		Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
		Runtime.getRuntime().exec("TASKKILL /F /IM geckodriver.exe");
		Runtime.getRuntime().exec("TASKKILL /F /IM IEDriverServer_32.exe");
	}
}
