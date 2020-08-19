package com.dharam.flipkart.pagefactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;

public class ProductCartPage extends TestBase {
	@FindBy(xpath = "//*[text()='Place Order']/parent::*")
	private WebElement placeOrderBtn;

	@FindBy(xpath = "//*[contains(@style,'box-shadow')][1]//*[@class='PaJLWc']")
	private List<WebElement> cartProductList;
	
	@FindBy(xpath = "//*[contains(@href,'viewcart')]")
	private WebElement cart;
	
	@FindBy(xpath="//*[contains(@class,'hgEev') and contains(@class,'QHbp')]//*[text()='Remove']")
	private WebElement popUpRemove;
	
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

	public void emptyTheCart() {
		Boolean cartDisplayed = verifyShoppingCartDisplayed();
		if (!cartDisplayed) {
			cart.click();
		}
		while(cartProductList.size() > 0) {
			cartProductList.get(0).findElement(By.xpath(
					"following-sibling::*[contains(@class,'cto')]//*[text()='Remove']")).click();
			popUpRemove.click();
		}
	}
	
	public boolean verifyShoppingCartDisplayed() {
		return driver.getTitle().equalsIgnoreCase("Shopping Cart | Flipkart.com<") ? true : false;
	}
};
