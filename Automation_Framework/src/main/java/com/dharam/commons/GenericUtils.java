package com.dharam.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class GenericUtils {
	
	public static void switchToBrowserTab(final WebDriver driver) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"\t");
	}
	
	public static void clickClearAndType(final WebElement element, final String text) {
		element.clear();
		element.click();
		element.sendKeys(text);
	}
	
	public static void scrollIntoView(final WebDriver driver, final WebElement ele) {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", ele);
	}
	
	public static void selectDropdownByValue(WebElement dropDownLocator, final String value) {
		Select dropdown = new Select(dropDownLocator);
		dropdown.selectByValue(value);
	}
}
