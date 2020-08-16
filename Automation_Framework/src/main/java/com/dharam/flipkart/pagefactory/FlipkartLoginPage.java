package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dharam.logger.Log;

public class FlipkartLoginPage {
	
	private WebDriver driver;
	
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
	
	@FindBy(xpath="//*[contains(@class,'aUbKa') and not(text()='More')]")
	private WebElement displayedUsername;
	
	@FindBys(value = { @FindBy(xpath="//*[contains(@class,'yG-R_')]//li/a") })
	private WebElement logout;
	
	public FlipkartLoginPage(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void openLoginPopUp() {
		this.loginLink.click();
		Log.info("Login Popup Opened.");
	}
	
	public void enterUsername(final String userName) {
		this.username.sendKeys(userName);
		Log.info("Username typed into the login Form.");
	}
	
	public void enterPassword(final String password) {
		this.password.sendKeys(password);
		Log.info("Password typed into the login Form.");
	}
	
	public void clickOnLoginBtn() {
		this.loginBtn.click();
		Log.info("Logged in.");
	}
	
	public void logout() {
		Actions action = new Actions(driver);
		action.moveToElement(displayedUsername)
		.moveToElement(logout)
		.click().build().perform();
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(loginBtn));
		Log.info("Logged out from the flipkart.");
	}
	
}
