package com.dharam.flipkart.pagefactory;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dharam.baseclass.TestBase;
import com.dharam.reports.Log;
import com.dharam.utils.GenericUtil;

public class ProductSearchPage extends TestBase {

	@FindBy(xpath = "//*[contains(@class,'header-form-search')]//*[@type='text']")
	private WebElement searchTextInputBox;

	@FindBy(xpath = "//*[@class='_3O0U0u']/*")
	private List<WebElement> productList;

	public ProductSearchPage() {
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> getProductList() {
		return productList;
	}

	public void productSearch() {
		String productName = GenericUtil.getSheetData("productinfo", "searchproduct");
		searchTextInputBox.click();
		searchTextInputBox.sendKeys(productName);
		searchTextInputBox.sendKeys(Keys.ENTER);
		Log.info(productName + " searched.");
//		new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfAllElements(getProductList()));
	}

	public ProductPage randomlySelectTheProduct() {
		int random = new Random().nextInt(productList.size());
		WebElement randomWebElement = productList.get(random);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", randomWebElement);
		randomWebElement.click();
		GenericUtil.switchToNthWindow(driver, driver.getWindowHandles().size());
		return new ProductPage();
	}
}
