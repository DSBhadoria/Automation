package com.dharam.driver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	
	protected WebDriver driver;
	
	protected abstract void createWebdriver();
	
	public void closeWebDriver() {
		if(this.driver != null) {
			this.driver.close();
			this.driver = null;
		}
	}
	
	public WebDriver getWebDriver() {
		if(this.driver == null) {
			createWebdriver();
		}
		return this.driver;
	}
}
