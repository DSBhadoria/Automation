package com.dharam.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author dhbhador
 * description	Chrome, Firefox and IE drivers instance creation.
 */
public class ChromeDriverManager extends DriverManager {
	@Override
	protected void createWebdriver() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\dhbhador\\Downloads\\Windows 7\\ZIP Files\\chromedriver_win32\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		this.driver = new ChromeDriver(options);
	}
}

class FirefoxDriverManager extends DriverManager {
	@Override
	protected void createWebdriver() {
		this.driver = new FirefoxDriver();
	}
}

class IEDriverManager extends DriverManager {
	@Override
	protected void createWebdriver() {
		this.driver = new InternetExplorerDriver();
	}
}
