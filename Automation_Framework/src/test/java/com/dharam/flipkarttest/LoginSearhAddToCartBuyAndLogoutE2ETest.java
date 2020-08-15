package com.dharam.flipkarttest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.dharam.datareader.ExcelUtil;
import com.dharam.driver.DriverManager;
import com.dharam.driver.DriverManagerFactory;
import com.dharam.enums.DriverType;
import com.dharam.flipkart.pagefactory.CheckoutPage;
import com.dharam.flipkart.pagefactory.FlipkartLoginPage;
import com.dharam.flipkart.pagefactory.ProductCartPage;
import com.dharam.flipkart.pagefactory.ProductPage;
import com.dharam.flipkart.pagefactory.ProductSearchPage;

public class LoginSearhAddToCartBuyAndLogoutE2ETest {
	
	DriverManager driverManager;
	WebDriver driver;
	
	FlipkartLoginPage flipKartLoginPage;
	ProductSearchPage productSearchScreenPage;
	ProductPage productPage;
	ProductCartPage productCartPage;
	CheckoutPage checkoutPage;
	
	ExcelUtil excelObj;
	Workbook workBook;
	
	@BeforeClass
	@Parameters({"excelFile"})
	public void setUp(final String excelFile) {
		driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
		driver = driverManager.getWebDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		excelObj = new ExcelUtil();
		workBook = excelObj.getInputWorkBook(excelFile);
		
		String url = ExcelUtil.getSheetData(workBook, "credentials", "url");
		driver.get(url);
	}
		
	@Test
	public void login() {
		flipKartLoginPage = new FlipkartLoginPage(driver);
		flipKartLoginPage.openLoginPopUp();
		
		String username = ExcelUtil.getSheetData(workBook, "credentials", "username");
		flipKartLoginPage.enterUsername(username);
		
		String password = ExcelUtil.getSheetData(workBook, "credentials", "password");
		flipKartLoginPage.enterPassword(password);
		
		flipKartLoginPage.clickOnLoginBtn();
	}
	
	@Test
	public void productSearch() {
		String productName = ExcelUtil.getSheetData(workBook, "productinfo", "searchproduct");
		productSearchScreenPage = new ProductSearchPage(driver);
		productSearchScreenPage.productSearch(productName);
		productSearchScreenPage.randomlySelectTheProduct();
		System.out.println(this.driver.getTitle());
	}
	
	@Test
	public void addToCartAndPlaceOrder() {
		ExcelUtil.updateExcel(workBook, "productinfo", "productname", 
				productPage.getProductName().getText());
		ExcelUtil.updateExcel(workBook, "productinfo", "productprice", 
				productPage.getProductPrice().getText());
		ExcelUtil.updateExcel(workBook, "productinfo", "productdescription", 
				productPage.getProductDescription().getText());
		
		productPage = new ProductPage(driver);
		productPage.addProductToCart();
		
		productCartPage = new ProductCartPage(driver);
		productCartPage.placeOrder();
	}
	
	@Test
	public void verifyAddedProductDetailsOnCheckoutScreen() {
		checkoutPage = new CheckoutPage(driver);
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
		
	}
	
	
}
