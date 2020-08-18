package com.dharam.flipkart.pagefactory;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;

public class ProductSearchPage extends TestBase {
	
	@FindBy(xpath="//*[contains(@class,'header-form-search')]//*[@type='text']")
	private WebElement searchTextInputBox;
	
	@FindBy(xpath="//*[@class='_3O0U0u']/*")
	private List<WebElement> productList;

	public ProductSearchPage() {
		PageFactory.initElements(driver, this);
	}
	
	public List<WebElement> getProductList() {
		return productList;
	}
	
	public void productSearch(final String product) {
		searchTextInputBox.click();
		searchTextInputBox.sendKeys(product);
		searchTextInputBox.sendKeys(Keys.ENTER);
	}
	
	public ProductPage randomlySelectTheProduct() {
		int random = new Random().nextInt(productList.size());
		WebElement randomWebElement = productList.get(random);
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView()", randomWebElement);
		randomWebElement.click();
		return new ProductPage();
	}
}
