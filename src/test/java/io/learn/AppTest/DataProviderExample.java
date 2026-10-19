package io.learn.AppTest;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.learn.Pages.LoginPage;
import io.learn.Pages.ProductsPage;
import io.learn.dataProvider.UserData;
import io.learn.exception.LoginFailedException;

public class DataProviderExample{
	
	private WebDriver driver;
	
	protected LoginPage login;
	protected ProductsPage product;
	
	@BeforeMethod
	public void setUp() {
		driver = new EdgeDriver();
		driver.get("https://www.saucedemo.com");
		
		login = new LoginPage(driver);
	}
	
	@Test(dataProvider = "data")
	public void testLoginThroghDifferentUser(String username, String password, boolean shouldLogin) {
		login.login(username, password);
		
		if(shouldLogin) {
			product = login.enterLogin();
			assertTrue(product.isProductPageDisplayed(),"Product page is not displayed.");
			product.logout();
			assertTrue(login.isLoginPageDisplayed(),"Login page is not displayed.");
		} else {
			try {
	            login.enterLogin();
	        } catch (LoginFailedException e) {
	        
	        }
			assertTrue(login.isLoginErrorDisplayed(),"Error message is not displayed.");
		}
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@DataProvider(name = "data")
	private Object[][] userData(){
		return UserData.userData();
	}
}
