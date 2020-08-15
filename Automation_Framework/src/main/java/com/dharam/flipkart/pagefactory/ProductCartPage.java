package com.dharam.flipkart.pagefactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductCartPage {
	
	@FindBy(xpath="//*[text()='Place Order']/parent::*")
	private WebElement placeOrderBtn;
	
	@FindBy(xpath="//*[contains(@style,'box-shadow')][1]//*[@class='PaJLWc']")
	private List<WebElement> cartProductList;
	
	public List<WebElement> getCartProductList() {
		return cartProductList;
	}

	public ProductCartPage(final WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void placeOrder() {
		placeOrderBtn.click();
	}
}
