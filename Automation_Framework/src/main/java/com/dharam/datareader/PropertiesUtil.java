package com.dharam.datareader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties propertyObj = null;

	public static Properties getPropertyObj(final String filePath) {
		if (propertyObj == null) {
			loadPropertyFile(filePath);
		}
		return propertyObj;
	}

	public static void loadPropertyFile(final String filePath) {
		try {
			String propertyFilePath = System.getProperty("user.dir") + filePath;
			InputStream input = new FileInputStream(propertyFilePath);
			propertyObj = new Properties();
			propertyObj.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
