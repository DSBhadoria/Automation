package com.dharam.driver;

import com.dharam.enums.DriverType;

public class DriverManagerFactory {
	public static DriverManager getDriverManager(final DriverType driverType) {
		DriverManager driverManager;
		switch(driverType) {
			case CHROME:
				driverManager = new ChromeDriverManager();
				break;
			case FIREFOX:
				driverManager = new FirefoxDriverManager();
				break;
			default:
				driverManager = new IEDriverManager();
				break;
		}
		return driverManager;
	}
}
