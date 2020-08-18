package com.dharam.flipkart.pagefactory;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;

public class ProductCartPage extends TestBase {
	
	@FindBy(xpath="//*[text()='Place Order']/parent::*")
	private WebElement placeOrderBtn;
	
	@FindBy(xpath="//*[contains(@style,'box-shadow')][1]//*[@class='PaJLWc']")
	private List<WebElement> cartProductList;
	
	public List<WebElement> getCartProductList() {
		return cartProductList;
	}

	public ProductCartPage() {
		PageFactory.initElements(driver, this);
	}
	
	public CheckoutPage placeOrder() {
		placeOrderBtn.click();
		return new CheckoutPage();
	}
};
