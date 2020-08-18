package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;

public class ProductPage extends TestBase {
	
	@FindBy(xpath="//h1/*")
	private WebElement productName;
	
	@FindBy(xpath="//*[contains(@class,'_1vC4OE') and contains(@class,'_3qQ9m1')]")
	private WebElement productPrice;
	
	@FindBy(xpath="//*[text()='Description']/following-sibling::*[1]//p")
	private WebElement productDescription;
	
	@FindBy(xpath="//button[text()='ADD TO CART']")
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
	
	public void addProductToCart() {
		addToCartBtn.click();
	}
}
