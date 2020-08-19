package com.dharam.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

public class GenericUtils extends TestBase {

	public static void switchToBrowserTab(final WebDriver driver) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
	}

	public static void clickClearAndType(final WebElement element, final String text) {
		element.clear();
		element.click();
		element.sendKeys(text);
	}

	public static void scrollIntoView(final WebDriver driver, final WebElement ele) {
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

	private static WebDriverWait wait = new WebDriverWait(driver, 15);

	public static void webdriverWaitUntilElementIsClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static final String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/resources/testdata.xlsx";

	static Workbook book;
	static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

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
