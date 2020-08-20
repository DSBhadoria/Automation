package com.dharam.flipkart.pagefactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.dharam.baseclass.TestBase;
import com.dharam.reports.Log;
import com.dharam.utils.GenericUtil;

public class LoginPage extends TestBase {

	@FindBy(how = How.LINK_TEXT, using = "Login")
	private WebElement loginLink;

	@FindBy(className = "mCRfo9")
	// @CacheLookup
	private WebElement loginPopup;

	@FindBys({ @FindBy(tagName = "button"), @FindBy(xpath = "//*[text()=\"✕\"]") })
	private WebElement closePopup;

	@FindBy(xpath = "//input[contains(@class,'dBPDZ') and @type='text']")
	private WebElement username;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement password;

	@FindBy(xpath = "//button[@type='submit' and (contains(@class,'AkmmA') or contains(@class,'UHT_c'))]")
	private WebElement loginBtn;

	@FindBy(xpath = "//*[contains(@class,'aUbKa') and not(text()='More')]")
	private WebElement displayedUsername;

	@FindBy(xpath = "//*[contains(@class,'yG-R_')]//li/a[contains(text(),'Logout')]")
	private WebElement logout;

	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	// During Automation this Pop-up comes automatically, so throws exception.
	public void openLoginPopUp() {
		try {
			this.loginLink.click();
			Log.info("Login Popup Opened By Clicking.");
		} catch (Exception e) {
			Log.warn(e.getMessage() + " : Login Pop-up Opened Automatically.");
		}
	}

	public ProductSearchPage login(String userName, String password) {
		this.username.sendKeys(userName);
		this.password.sendKeys(password);
		this.loginBtn.click();
		return new ProductSearchPage();
	}

	public String validateLoginPageTitle() {
		return driver.getTitle();
	}

	public boolean validateUsernameDisplayed() {
		return displayedUsername.isDisplayed();
	}

	public void logout() {
		GenericUtil.webdriverWaitUntilElementIsClickable(displayedUsername);
		Actions action = new Actions(driver);
		action.moveToElement(displayedUsername).moveToElement(logout).click().build().perform();
		GenericUtil.webdriverWaitUntilElementIsClickable(loginBtn);
		Log.info("Logged out from the Application.");
	}
}
