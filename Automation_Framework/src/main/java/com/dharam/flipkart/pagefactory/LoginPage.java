package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dharam.baseclass.TestBase;
import com.dharam.logger.Log;

public class LoginPage extends TestBase {
	
	@FindBy(how=How.LINK_TEXT, using="Login")
	private WebElement loginLink;
	
	@FindBy(className="mCRfo9")
//	@CacheLookup
	private WebElement loginPopup;
	
	@FindBys({
		@FindBy(tagName="button"),
		@FindBy(xpath="//*[text()=\"✕\"]")
	})
	private WebElement closePopup;
	
	@FindBy(xpath="//input[contains(@class,'dBPDZ') and @type='text']")
	private WebElement username;
	
	@FindBy(xpath="//input[@type='password']")
	private WebElement password;
	
	@FindBy(xpath="//button[@type='submit' and (contains(@class,'AkmmA') or contains(@class,'UHT_c'))]")
	private WebElement loginBtn;
	
	@FindBy(xpath="//*[contains(@class,'aUbKa') and not(text()='More')]")
	private WebElement displayedUsername;
	
	@FindBy(xpath="//*[contains(@class,'yG-R_')]//li/a[contains(text(),'Logout')]")
	private WebElement logout;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void openLoginPopUp() {
		this.loginLink.click();
		Log.info("Login Popup Opened.");
	}
	
	public ProductSearchPage login(String userName, String password){
		this.username.sendKeys(userName);
		this.password.sendKeys(password);
		this.loginBtn.click();
		return new ProductSearchPage();
	}
	
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
	public boolean validateUsernameDisplayed() {
		return displayedUsername.isDisplayed();
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
