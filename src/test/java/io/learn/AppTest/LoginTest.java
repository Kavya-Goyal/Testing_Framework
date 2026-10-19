package io.learn.AppTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.learn.Listener.TestListener;
import io.learn.exception.LoginFailedException;

public class LoginTest extends BaseTest{
	private static final Logger logger = LogManager.getLogger(LoginTest.class);

	@Test(priority = 1, groups = {"smoke"})
	public void testLoginSuccess() {
		ExtentTest test = TestListener.getTest();
		
		test.log(Status.INFO, "Starting Test: testLoginSuccess");

		test.log(Status.INFO, "Is Logo visible");
		assertEquals(login.logoText(), "Swag Labs");
		test.log(Status.INFO, "is product page displayed.");
		assertTrue(products.isProductPageDisplayed(),"Product page not displayed.");
		test.log(Status.PASS, "Product page is displayed.");
		
		test.log(Status.INFO, "Checking Current URL.");
		assertEquals(products.getCurrentUrl(), getExpectedUrl(), "Unexpected url");
		test.log(Status.PASS, "Current Url is correct.");
		
		test.log(Status.INFO, "Verifying product list is displayed.");
		assertTrue(products.isProductListDisplayed(),"Product list is not displayed.");
		test.log(Status.PASS, "Product list founded.");
		
		test.log(Status.INFO, "Test: testLoginSuccess passed");

	}
	
	@Test(priority = 2)
	public void testLoginFailure() {
		ExtentTest test = TestListener.getTest();
		
		test.log(Status.INFO, "Starting Test: testLoginFailure");
		
		test.log(Status.INFO, "Logout");
		products.logout();
		test.log(Status.PASS , "Logout Successful");
		
		test.log(Status.INFO, "is Login page displayed");
		assertTrue(login.isLoginPageDisplayed(),"Login page not displayed.");
		test.log(Status.PASS, "Login Page found");
		
		test.log(Status.INFO, "Entering wrong credentials");
		login.login("Kavya Goyal", "Kavya123");
		try {
			login.enterLogin(); 
			test.log(Status.PASS, "User not found");
		}catch(LoginFailedException e) {
			test.log(Status.FAIL, "Login failed as expected: " + e.getMessage() );
		}
		
		test.log(Status.INFO, "Is login error displayed.");
		assertTrue(login.isLoginErrorDisplayed(),"Error message not found.");
		test.log(Status.PASS, "Error message displayed successfully.");
		
		test.log(Status.INFO, "Test: testLoginFailure passed");
	}
	
	private String getExpectedUrl() {
		logger.debug("Fetching expected page url from configReader.");
		return reader.getProperty("app.url") + "inventory.html";
	}
}
