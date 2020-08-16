package com.dharam.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author dhbhador
 * @description	Chrome, Firefox and IE drivers instance creation.
 */
public class ChromeDriverManager extends DriverManager {
	
	public static final String rootDir = System.getProperty("user.dir");
	
	@Override
	protected void createWebdriver() {
		System.setProperty("webdriver.chrome.driver", rootDir + "\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
	}
}

class FirefoxDriverManager extends DriverManager {
	@Override
	protected void createWebdriver() {
		System.setProperty("webdriver.gecko.driver", ChromeDriverManager.rootDir + "\\drivers\\geckodriver.exe" );
		this.driver = new FirefoxDriver();
	}
}

class IEDriverManager extends DriverManager {
	@SuppressWarnings("deprecation")
	@Override
	protected void createWebdriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		System.setProperty("webdriver.ie.driver", ChromeDriverManager.rootDir + "\\drivers\\IEDriverServer_32.exe");
		this.driver = new InternetExplorerDriver(capabilities);
	}
}
