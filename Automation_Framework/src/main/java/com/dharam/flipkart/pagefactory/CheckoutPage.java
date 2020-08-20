package com.dharam.flipkart.pagefactory;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.dharam.baseclass.TestBase;
import com.dharam.utils.GenericUtil;

public class CheckoutPage extends TestBase {

	@FindBy(xpath = "//*[text()='Add a new address']")
	private WebElement addANewAddress;

	@FindBy(name = "name")
	private WebElement name;

	@FindBy(name = "phone")
	private WebElement mob;

	@FindBy(name = "pincode")
	private WebElement pincode;

	@FindBy(name = "addressLine2")
	private WebElement locality;

	@FindBy(name = "addressLine1")
	private WebElement address;

	@FindBy(name = "city")
	private WebElement city_disctrict_town;

	@FindBy(name = "state")
	private WebElement state;

	@FindBy(name = "landmark")
	private WebElement landmark_optional;

	@FindBy(name = "alternatePhone")
	private WebElement alternate_phone_no_optional;

	@FindBy(xpath = "//*[@name='locationTypeTag']/..")
	private List<WebElement> address_type;

	@FindBys({ @FindBy(tagName = "button"), @FindBy(how = How.XPATH, using = "//*[contains(text(),'Deliver Here')]") })
	private WebElement save_and_deliver_here;

	@FindBy(className = "PaJLWc")
	private List<WebElement> checkedoutProductList;

	@FindBy(id = "to-payment")
	private WebElement continueToPayement;

	@FindBy(id = "UPI")
	private WebElement upiParentPayment;

	@FindBy(id = "PHONEPE")
	private WebElement phonepe;

	@FindBy(id = "UPI_COLLECT")
	private WebElement upiCollect;

	@FindBy(id = "CREDIT")
	private WebElement creditDebitATMPayment;

	@FindBy(name = "cardNumber")
	private WebElement cardNumber;

	@FindBy(name = "month")
	private WebElement monthDropdown;

	@FindBy(id = "year")
	private WebElement yearDropdown;

	@FindBy(xpath = "//*[contains(text(),'PAY') and @type='button']")
	private WebElement payBtn;

	@FindBy(id = "NET_OPTIONS")
	private WebElement netBanking;

	@FindBy(name = "netBanking")
	private WebElement popularBanks;

	@FindBy(xpath = "//*[text()='Other Banks']/following-sibling::*/select")
	private WebElement netBankingSelectBanksDropdown;

	/**
	 * @description Parameterized Constructor for initializing the WebElements of
	 *              Checkout Page
	 */
	public CheckoutPage() {
		PageFactory.initElements(driver, this);
	}

	public void openNewAddressForm() {
		GenericUtil.webdriverWaitUntilElementIsClickable(addANewAddress);
		GenericUtil.scrollIntoView(addANewAddress);
		addANewAddress.click();
	}

	public void enterName(final String personName) {
		name.sendKeys(personName);
	}

	public void enterMob(final String mobile) {
		GenericUtil.clickClearAndType(mob, mobile);
	}

	public void enterPincode(final String pincode) {
		GenericUtil.clickClearAndType(this.pincode, pincode);
	}

	public void enterLocality(final String locality) {
		GenericUtil.clickClearAndType(this.locality, locality);
	}

	public void enterAddress(final String address) {
		GenericUtil.clickClearAndType(this.address, address);
	}

	public void enterCityDistrictTown(final String city) {
		GenericUtil.clickClearAndType(city_disctrict_town, city);
	}

	public void selectStateFromDropdown(final String stateValue) {
		Select stateDropDown = new Select(state);
		stateDropDown.selectByValue(stateValue);
	}

	public void enterLandmark(final String landmark) {
		GenericUtil.clickClearAndType(landmark_optional, landmark);
	}

	public void enterAlternatePhone(final String alternatemob) {
		GenericUtil.clickClearAndType(alternate_phone_no_optional, alternatemob);
	}

	/**
	 * @Description Address Type Selection - Home or Office
	 */
	public void selectAddressTypeRadioBtn(final String addressType) {
		address_type.forEach(homeOrWorkAddress -> {
			if (homeOrWorkAddress.getAttribute("for").equalsIgnoreCase(addressType)) {
				GenericUtil.scrollIntoView(homeOrWorkAddress);
				homeOrWorkAddress.click();
				return;
			}
		});
	}

	/**
	 * @Desciption Fill the Mew Address Details for the Product Delivery.
	 */
	public void fillAddressFormForProductDelivery(Workbook workBook, final String sheetName) {
		String name = GenericUtil.getSheetData(sheetName, "name");
		enterName(name);

		String mob = GenericUtil.getSheetData(sheetName, "mobile");
		enterMob(mob);

		String pincode = GenericUtil.getSheetData(sheetName, "pincode");
		enterPincode(pincode);

		String locality = GenericUtil.getSheetData(sheetName, "locality");
		enterLocality(locality);

		String address = GenericUtil.getSheetData(sheetName, "address");
		enterAddress(address);

		String city = GenericUtil.getSheetData(sheetName, "city");
		enterCityDistrictTown(city);

		String state = GenericUtil.getSheetData(sheetName, "state");
		selectStateFromDropdown(state);

		String landmark = GenericUtil.getSheetData(sheetName, "landmark");
		enterLandmark(landmark);

		String alternatephone = GenericUtil.getSheetData(sheetName, "alternatephone");
		enterAlternatePhone(alternatephone);

		String addresstype = GenericUtil.getSheetData(sheetName, "addresstype");
		selectAddressTypeRadioBtn(addresstype);
	}

	public void clickOnSaveAndDeliverHereBtn() {
		save_and_deliver_here.click();
	}

	public void continueToPayment() {
		continueToPayement.click();
	}

	public List<WebElement> getcheckedoutProductList() {
		return checkedoutProductList;
	}

	/**
	 * @Description Payment Details for Card Payment Method.
	 */
	public boolean creditDebitATMCardPayment(final String cardno, final String month, final String year,
			final String cvv) {
		GenericUtil.clickClearAndType(cardNumber, cardno);
		GenericUtil.selectDropdownByValue(monthDropdown, month);
		GenericUtil.selectDropdownByValue(yearDropdown, year);
		GenericUtil.clickClearAndType(city_disctrict_town, cvv);
		return payBtn.isEnabled();
	}

	/**
	 * @Description Payment based on the Payment Type Method.
	 */
	public boolean paymentForThePurchase() {
		String paymentType = GenericUtil.getSheetData("payment", "paymenttype");
		Boolean payBtnEnabled = false;
		if (paymentType.equalsIgnoreCase("card")) {
			String cardnumber = GenericUtil.getSheetData("payment", "card_number");
			String month = GenericUtil.getSheetData("payment", "mm");
			String year = GenericUtil.getSheetData("payment", "yy");
			payBtnEnabled = creditDebitATMCardPayment(cardnumber, month, year, "***");
		}
		return payBtnEnabled;
	}

	/**
	 * @Description Verify the Added Product Details on the Checkeout Screen.
	 */
	public boolean verifyAddedProductDetailsOnCheckoutScreen() {
		List<WebElement> cartProductList = getcheckedoutProductList();
		String productNameXpath = "child::*[contains(@class,'vIvU_')]/*[1]";
		String productPriceXpath = "/*[contains(@class,'vZa')]";
		String expectedProductName = GenericUtil.getSheetData("productinfo", "productName");
		String expectedProductPrice = GenericUtil.getSheetData("productinfo", "productprice");

		boolean productPresent = false;
		for (int i = 0; i < cartProductList.size(); i++) {
			WebElement actualProduct = cartProductList.get(i);
			String productName = actualProduct.findElement(By.xpath(productNameXpath)).getText();
			if (expectedProductName.contains(productName)) {
				// Assert.assertEquals(productName, expectedProductName);
				String productPrice = actualProduct.findElement(By.xpath(productPriceXpath)).getText();
				Assert.assertEquals(productPrice, expectedProductPrice);
				productPresent = true;
				break;
			}
		}

		return productPresent;
	}

	/**
	 * @description Payment Btn enability needs to be checked before clicking on it.
	 */
	public void verifyPayBtnIsEnabledThenDoPayment() {
		Assert.assertTrue(payBtn.isEnabled());
		payBtn.click();
	}
}
