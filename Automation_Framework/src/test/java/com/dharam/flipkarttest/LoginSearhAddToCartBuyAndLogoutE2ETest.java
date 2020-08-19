package com.dharam.flipkarttest;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dharam.baseclass.TestBase;
import com.dharam.datareader.ExcelUtil;
import com.dharam.flipkart.pagefactory.CheckoutPage;
import com.dharam.flipkart.pagefactory.LoginPage;
import com.dharam.flipkart.pagefactory.ProductCartPage;
import com.dharam.flipkart.pagefactory.ProductPage;
import com.dharam.flipkart.pagefactory.ProductSearchPage;
import com.dharam.logger.Log;
import com.dharam.utils.GenericUtils;

public class LoginSearhAddToCartBuyAndLogoutE2ETest extends TestBase {

	LoginPage flipKartLoginPage;
	ProductSearchPage productSearchScreenPage;
	ProductPage productPage;
	ProductCartPage productCartPage;
	CheckoutPage checkoutPage;
	
	private String sheetName = "products";
	
	LoginSearhAddToCartBuyAndLogoutE2ETest() {
		super();
	}
	
	@DataProvider(name = "TestDataProvider")
	public Object[][] getDataFromDataprovider() {
		return GenericUtils.getTestData(sheetName);
	}
	
	/**
	 * @Description Initial Setup for the Webdriver Instantiation based on the param.
	 */
	@BeforeClass
	@Parameters({"browser"})
	public void setUp(final String browser) {
		initialize(browser);
		flipKartLoginPage = new LoginPage();
		Log.startTestCase("Selenium_Test_001 - Flipkart Purchase.");
	}
	
	/**
	 * @Decription Login into the Application.
	 */
	@Test(alwaysRun=true, groups= {"smoke", "regression"}, priority=1)
	public void login() {
		String title = flipKartLoginPage.validateLoginPageTitle();
		Assert.assertEquals(title, 
				"Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!");
		Log.info("Url = '" + prop.getProperty("url") + "' Launched successfully.");
		
		// During Automation this Pop-up comes automatically, so throws exception.
		try {
			flipKartLoginPage.openLoginPopUp();
			System.out.println("Clicked on Login - For open the Login_Popup.");
		} catch (Exception e) {
			Log.warn(e.getMessage());
		}
		
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		productSearchScreenPage = flipKartLoginPage.login(username, password);
		Assert.assertTrue(flipKartLoginPage.validateUsernameDisplayed(), "Not Logged-in Successfully.");
		Log.info("Logged-in Successfully.");
	}

	/**
	 * @Description Product searched and randomly selected from the list displayed.
	 * And also driver control switched to the new TAB - Newly Opened Product Page.
	 */
	@Test(groups= {"regression"}, description = "Product Search and randomly select", priority=2)
	public void productSearch() {
		String productName = ExcelUtil.getSheetData(workBook, "productinfo", "searchproduct");
//		String productName = "camera";
		productSearchScreenPage.productSearch(productName);
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfAllElements(productSearchScreenPage.getProductList()));
		String afterSearchingTheProductPagetitle = driver.getTitle();
		Log.info(productName + " searched.");
		
		productPage = productSearchScreenPage.randomlySelectTheProduct();
		GenericUtils.switchToNthWindow(driver, 2);
		String afterSelectingTheRandomProductPagetitle = driver.getTitle();
		Assert.assertNotSame(afterSearchingTheProductPagetitle, afterSelectingTheRandomProductPagetitle, 
				"Either Random Product not selected or Searched Producty Not-Found.");
		Log.info("Random Product selected.");
	}
	
	/**
	 * @Decription Product Added to the Cart.
	 */
	@Test(groups= {"regression"}, description = "Add a product into a cart and place order.", priority=3)
	public void addToCartAndPlaceOrder() {
		ExcelUtil.updateExcel(workBook, "productinfo", "productname", 
				productPage.getProductName().getText());
		ExcelUtil.updateExcel(workBook, "productinfo", "productprice", 
				productPage.getProductPrice().getText());
		ExcelUtil.updateExcel(workBook, "productinfo", "productdescription", 
				productPage.getProductDescription().getText());

		productCartPage = productPage.addProductToCart();
		Assert.assertTrue(productCartPage.getCartProductList().size()>0);
		
		checkoutPage = productCartPage.placeOrder();
		
		Log.info("Random Product Added to the Cart.");
	}


	@Test(groups= {"regression"}, description = "New Delivery Address Setup", priority=4)
	public void setANewDeliveryAddress() {
		checkoutPage.openNewAddressForm();
		
		checkoutPage.fillAddressFormForProductDelivery(workBook, "deliveryaddress");
		Log.info("Delivery Address Form Filled.");
		
		checkoutPage.clickOnSaveAndDeliverHereBtn();
		
	}


	@Test(groups= {"regression"}, description = "Verify the Added Product Details on the Checkeout Screen", priority=5)
	public void verifyAddedProductDetailsOnCheckoutScreen() {
		List<WebElement> cartProductList = checkoutPage.getcheckedoutProductList();
		String productNameXpath = "child::*[contains(@class,'vIvU_')]/*[1]";
		String productPriceXpath = "*[contains(@class,'vZa')]";
		String expectedProductName = ExcelUtil.getSheetData(workBook, "productinfo", "productName");
		String expectedProductPrice = ExcelUtil.getSheetData(workBook, "productinfo", "productprice");

		boolean productPresent = false;

		for(int i=0; i<cartProductList.size(); i++) {

			WebElement actualProduct = cartProductList.get(i);
			String productName = actualProduct.findElement(By.xpath(productNameXpath)).getText();

			if(productName.contains(expectedProductName)) {
				//				Assert.assertEquals(productName, expectedProductName);
				String productPrice = actualProduct.findElement(By.xpath(productPriceXpath)).getText();
				Assert.assertEquals(productPrice, expectedProductPrice);
				productPresent = true;
				break;
			}
		}
		
		if(!productPresent) {
			Assert.assertFalse(true,"Product Not Found On the Checkout Screen.");
		}
	}
	
	/**
	 * @Desctiption More Options can be added for the Payment 
	 * but as of now only Card Payment added.
	 */
	@Test(groups= {"regression"}, priority=6)
	public void paymentBasedOnThePaymentOption() {
		checkoutPage.continueToPayment();
		String paymentType = ExcelUtil.getSheetData(workBook, "payment", "paymenttype");
		Boolean payBtnEnabled = false;
		if(paymentType.equalsIgnoreCase("card")) {
			String cardnumber = ExcelUtil.getSheetData(workBook, "payment", "card_number");
			String month = ExcelUtil.getSheetData(workBook, "payment", "mm");
			String year = ExcelUtil.getSheetData(workBook, "payment", "yy");
			payBtnEnabled = checkoutPage.creditDebitATMCardPayment(cardnumber, month, year, "***");
		}
		
		Assert.assertTrue(payBtnEnabled, "Payment Button is disabled. Payment can not be done.");
		checkoutPage.verifyPayBtnIsEnabledThenDoPayment();
	}
	
	@AfterClass
	public void tearDown() throws IOException {
		String title = driver.getTitle();
		flipKartLoginPage.logout();
		Assert.assertNotEquals(title, 
				"Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!");
		driver.close();
		GenericUtils.flush();
		Log.endTestCase("Selenium_Test_001");
	}
}
