package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class FlipkartLoginPage {
	
	@FindBy(how=How.LINK_TEXT, using="Login")
	private WebElement loginLink;
	
	@FindBy(className="mCRfo9")
	@CacheLookup
	private WebElement loginPopup;
	
	@FindBys({
		@FindBy(tagName="button"),
		@FindBy(xpath="//*[text()=\"✕\"]")
	})
	private WebElement closePopup;
	
	@FindBy(xpath="//input[@type='text']")
	private WebElement username;
	
	@FindBy(xpath="//input[@type='password']")
	private WebElement password;
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement loginBtn;
	
	public FlipkartLoginPage(final WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void openLoginPopUp() {
		this.loginLink.click();
	}
	
	public void enterUsername(final String userName) {
		this.username.sendKeys(userName);
	}
	
	public void enterPassword(final String password) {
		this.password.sendKeys(password);
	}
	
	public void clickOnLoginBtn() {
		this.loginBtn.click();
	}
}
