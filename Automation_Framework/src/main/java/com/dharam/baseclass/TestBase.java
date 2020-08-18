package com.dharam.baseclass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;

import com.dharam.datareader.ExcelUtil;
import com.dharam.driver.DriverManager;
import com.dharam.driver.DriverManagerFactory;
import com.dharam.enums.DriverType;

/**
 * @author dhbhador
 * @description Base Class that includes common methods, 
 * that can be reused, by extending this Class. 
 */
public class TestBase {
	
	protected static WebDriver driver;
	protected static Properties prop;
	protected static ExcelUtil excelObj;
	protected static Workbook workBook;
	
	public TestBase() {
		if(prop == null) {
			loadPropertyFile();
		}
	}
	
	private static final String CONFIG_PATH = "/resources/config.properties";
	
	/**
	 * @author dhbhador
	 * @description Loading the config.prperties file.
	 */
	private static void loadPropertyFile() {
		try {
			String propertyFilePath = System.getProperty("user.dir") + CONFIG_PATH;
			InputStream input = new FileInputStream(propertyFilePath);
			prop = new Properties();
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private DriverManager driverManager;
	/**
	 * @author dhbhador
	 * @param browser
	 * @description Initializing the Webdriver Instance, Deleting the Cookied and 
	 * Configuring the log4j.xml.
	 */
	public void initialize(final String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
		} else if(browser.equalsIgnoreCase("firefox")) {
			driverManager = DriverManagerFactory.getDriverManager(DriverType.FIREFOX);
		} else {
			driverManager = DriverManagerFactory.getDriverManager(DriverType.IE);
		}
		
		driver = driverManager.getWebDriver();
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		excelObj = new ExcelUtil();
		workBook = excelObj.getInputWorkBook(prop.getProperty("inputexcel"));
		
		DOMConfigurator.configure("log4j.xml");
	}
	
	/**
	 * @author dhbhador
	 * @description Flushing the Drivers executables from the process, if present.
	 */
	public void flush() throws IOException {
		Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
		Runtime.getRuntime().exec("TASKKILL /F /IM geckodriver.exe");
		Runtime.getRuntime().exec("TASKKILL /F /IM IEDriverServer_32.exe");
	}
}
