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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.dharam.reports.Log;
import com.dharam.utils.WebEventListener;

/**
 * @author dhbhador
 * @description Base Class that includes common methods, that can be reused, by
 *              extending this Class.
 */
abstract public class TestBase {

	protected static WebDriver driver;
	protected static Properties prop;
	protected static EventFiringWebDriver e_driver;
	protected static WebEventListener eventListener;

	protected static Workbook workBook;
	
	public static final String rootDir = System.getProperty("user.dir");
	private static final String CONFIG_PATH = "/resources/config.properties";
	
	public TestBase() {
		if (prop == null) {
			loadPropertyFile();
		}
	}


	/**
	 * @author dhbhador
	 * @description Loading the config.prperties file.
	 */
	private static void loadPropertyFile() {
		String propertyFilePath = rootDir + CONFIG_PATH;
		try(InputStream input = new FileInputStream(propertyFilePath)) {
			prop = new Properties();
			prop.load(input);
			DOMConfigurator.configure("log4j.xml");
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
	}


	/**
	 * @author dhbhador
	 * @param browser
	 * @description Initializing the Webdriver Instance, Deleting the Cookies,
	 *              Registering EventListerHandler with the EventFiringWebDriver and
	 *              Configuring the log4j.xml.
	 */
	@SuppressWarnings("deprecation")
	protected void initialize(final String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", rootDir + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", rootDir + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", rootDir + "\\drivers\\IEDriverServer_32.exe");
			driver = new InternetExplorerDriver(capabilities);
		}

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with
		// EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String url = prop.getProperty("url");
		driver.navigate().to(url);
	}

}
