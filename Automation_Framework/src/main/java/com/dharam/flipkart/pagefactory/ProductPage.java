package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;
import com.dharam.reports.Log;
import com.dharam.utils.GenericUtil;

public class ProductPage extends TestBase {
	@FindBy(xpath = "//h1/*")
	private WebElement productName;

	@FindBy(xpath = "//*[contains(@class,'_1vC4OE') and contains(@class,'_3qQ9m1')]")
	private WebElement productPrice;

	// @FindBy(xpath="//*[text()='Description']/following-sibling::*[1]//p")
	@FindBy(xpath = "//*[text()='Description' or text()='Product Description']/following-sibling::*[1]")
	private WebElement productDescription;

	@FindBy(xpath = "//button[text()='ADD TO CART']")
	private WebElement addToCartBtn;

	public ProductPage() {
		PageFactory.initElements(driver, this);
	}

	public WebElement getProductName() {
		return productName;
	}

	public WebElement getProductPrice() {
		return productPrice;
	}

	public WebElement getProductDescription() {
		return productDescription;
	}

	public ProductCartPage addProductToCart() {
		GenericUtil.scrollIntoView(addToCartBtn);
		addToCartBtn.click();
		return new ProductCartPage();
	}

	public void productTobeAddedUpdateDetailsIntoTheExcel() {
		GenericUtil.updateExcel("productinfo", "productname", productName.getText());
		GenericUtil.updateExcel("productinfo", "productprice", productPrice.getText());
		GenericUtil.updateExcel("productinfo", "productdescription", productDescription.getText());
		Log.info(productName.getText() + " - " + productPrice.getText() 
			+ " - " + productDescription.getText() + " Updated in the Excel.");
	}
}
