package com.dharam.flipkart.pagefactory;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.dharam.baseclass.TestBase;
import com.dharam.commons.GenericUtils;
import com.dharam.datareader.ExcelUtil;
import com.dharam.logger.Log;

public class CheckoutPage extends TestBase {

	@FindBy(xpath="//*[text()='Add a new address']")
	private WebElement addANewAddress;

	@FindBy(name="name")
	private WebElement name;

	@FindBy(name="phone")
	private WebElement mob;

	@FindBy(name="pincode")
	private WebElement pincode;

	@FindBy(name="addressLine2")
	private WebElement locality;

	@FindBy(name="addressLine1")
	private WebElement address;

	@FindBy(name="city")
	private WebElement city_disctrict_town;

	@FindBy(name="state")
	private WebElement state;

	@FindBy(name="landmark")
	private WebElement landmark_optional;

	@FindBy(name="alternatePhone")
	private WebElement alternate_phone_no_optional;

	@FindBy(xpath="//*[@name='locationTypeTag']/..")
	private List<WebElement> address_type;

	@FindBys({
		@FindBy(tagName="button"),
		@FindBy(how=How.XPATH, using="//*[contains(text(),'Deliver Here')]")}
			)
	private WebElement save_and_deliver_here;
	
	@FindBy(className="PaJLWc")
	private List<WebElement> checkedoutProductList;
	
	@FindBy(id="to-payment")
	private WebElement continueToPayement;
	
	@FindBy(id="UPI")
	private WebElement upiParentPayment;
	
	@FindBy(id="PHONEPE")
	private WebElement phonepe;
	
	@FindBy(id="UPI_COLLECT")
	private WebElement upiCollect;
	
	@FindBy(id="CREDIT")
	private WebElement creditDebitATMPayment;
	
	@FindBy(name="cardNumber")
	private WebElement cardNumber;
	
	@FindBy(name="month")
	private WebElement monthDropdown;
	
	@FindBy(id="year")
	private WebElement yearDropdown;
	
	@FindBy(xpath="//*[contains(text(),'PAY') and @type='button']")
	private WebElement payBtn;
	
	@FindBy(id="NET_OPTIONS")
	private WebElement netBanking;
	
	@FindBy(name="netBanking")
	private WebElement popularBanks;
	
	@FindBy(xpath="//*[text()='Other Banks']/following-sibling::*/select")
	private WebElement netBankingSelectBanksDropdown;
	
	/**
	 * @description Parameterized Constructor for initializing the 
	 * WebElements of Checkout Page
	 */
	public CheckoutPage() {
		PageFactory.initElements(driver, this); 
	}

	public void openNewAddressForm() {
		GenericUtils.scrollIntoView(driver, addANewAddress);
		addANewAddress.click();
	}

	public void enterName(final String personName) {
		name.sendKeys(personName);
	}

	public void enterMob(final String mobile) {
		GenericUtils.clickClearAndType(mob, mobile);
	}

	public void enterPincode(final String pincode) {
		GenericUtils.clickClearAndType(this.pincode, pincode);
	}

	public void enterLocality(final String locality) {
		GenericUtils.clickClearAndType(this.locality, locality);
	}

	public void enterAddress(final String address) {
		GenericUtils.clickClearAndType(this.address, address);
	}

	public void enterCityDistrictTown(final String city) {
		GenericUtils.clickClearAndType(city_disctrict_town, city);
	}

	public void selectStateFromDropdown(final String stateValue) {
		Select stateDropDown = new Select(state);
		stateDropDown.selectByValue(stateValue);
	}

	public void enterLandmark(final String landmark) {
		GenericUtils.clickClearAndType(landmark_optional, landmark);
	}

	public void enterAlternatePhone(final String alternatemob) {
		GenericUtils.clickClearAndType(alternate_phone_no_optional, alternatemob);
	}

	public void selectAddressTypeRadioBtn(final String addressType) {
		address_type.forEach(homeOrWorkAddress -> {
			if(homeOrWorkAddress.getAttribute("for").equalsIgnoreCase(addressType)) {
				GenericUtils.scrollIntoView(driver, homeOrWorkAddress);
				homeOrWorkAddress.click();
				return;
			}
		});
	}

	public void fillAddressFormForProductDelivery(Workbook workBook, final String sheetName) {
		String name = ExcelUtil.getSheetData(workBook, sheetName, "name");
		enterName(name);

		String mob = ExcelUtil.getSheetData(workBook, sheetName, "mobile");
		enterMob(mob);

		String pincode = ExcelUtil.getSheetData(workBook, sheetName, "pincode");
		enterPincode(pincode);

		String locality = ExcelUtil.getSheetData(workBook, sheetName, "locality");
		enterLocality(locality);

		String address = ExcelUtil.getSheetData(workBook, sheetName, "address");
		enterAddress(address);

		String city = ExcelUtil.getSheetData(workBook, sheetName, "city");
		enterCityDistrictTown(city);

		String state = ExcelUtil.getSheetData(workBook, sheetName, "state");
		selectStateFromDropdown(state);

		String landmark = ExcelUtil.getSheetData(workBook, sheetName, "landmark");
		enterLandmark(landmark);

		String alternatephone = ExcelUtil.getSheetData(workBook, sheetName, "alternatephone");
		enterAlternatePhone(alternatephone);

		String addresstype = ExcelUtil.getSheetData(workBook, sheetName, "addresstype");
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
	
	
	public boolean creditDebitATMCardPayment(final String cardno, final String month, final String year, final String cvv) {
		GenericUtils.clickClearAndType(cardNumber, cardno);
		GenericUtils.selectDropdownByValue(monthDropdown, month);
		GenericUtils.selectDropdownByValue(yearDropdown, year);
		GenericUtils.clickClearAndType(city_disctrict_town, cvv);
		return payBtn.isEnabled();
	}
	
	/**
	 * @description Payment Btn enability needs to be checked before clicking on it.
	 */
	public void verifyPayBtnIsEnabledThenDoPayment() {
		Assert.assertTrue(payBtn.isEnabled());
		payBtn.click();
	}
}

