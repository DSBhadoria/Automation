package com.dharam.flipkart.pagefactory;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.logger.Log;

public class ProductSearchPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//*[contains(@class,'header-form-search')]//*[@type='text']")
	private WebElement searchTextInputBox;
	
	@FindBy(xpath="//*[@class='_3O0U0u']/*")
	private List<WebElement> productList;
	
	public ProductSearchPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void productSearch(final String product) {
		searchTextInputBox.click();
		searchTextInputBox.sendKeys(product);
		searchTextInputBox.sendKeys(Keys.ENTER);
		Log.info(product + " searched.");
	}
	
	public void randomlySelectTheProduct() {
		int random = new Random().nextInt(productList.size());
		WebElement randomWebElement = productList.get(random);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", randomWebElement);
		randomWebElement.click();
		Log.info("Random Product selected.");
	}
}
