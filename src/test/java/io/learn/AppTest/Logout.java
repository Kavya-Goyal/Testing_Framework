package io.learn.AppTest;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class Logout extends BaseTest {
	
	@Test(groups = {"smoke"})
	public void testLogout() {
		
		assertTrue(products.isProductPageDisplayed(),"Product Page not found.");
		
		products.logout();
		
		assertTrue(login.isLoginPageDisplayed(),"Login page not found.");
	}
}
