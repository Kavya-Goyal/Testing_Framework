package io.learn.AppTest;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.learn.Listener.TestListener;

public class ProductTest extends BaseTest{
	
	@Test(priority = 1)
	public void testProductDetails() {
		ExtentTest test = TestListener.getTest();
		
		test.log(Status.INFO, "Is product list displayed.");
		assertTrue(products.isProductListDisplayed(),"Product List not found.");
		test.log(Status.PASS, "Product List founded.");
		
		test.log(Status.INFO,"Is product Title displayed.");
		assertTrue(products.isProductDisplayed("Sauce Labs Backpack"),"Product not found.");
		test.log(Status.PASS, "Product found in the list.");
		
		test.log(Status.INFO, "Is product price displayed.");
		assertTrue(products.isProductPriceDisplayed("Sauce Labs Backpack"),"Product price not found.");
		test.log(Status.PASS, "Product price found on the page.");
	}
	
	@Test(priority = 2)
	public void testAddToCart() {
		ExtentTest test = TestListener.getTest();
		
		test.log(Status.INFO, "Adding product to cart.");
		products.addToCart("Sauce Labs Backpack");
				
		test.log(Status.INFO, "Checking product added to cart.");
		assertTrue(products.isProductInCart("Sauce Labs Backpack"),"Product not in cart.");
		test.log(Status.PASS, "Product added to cart.");
	}
}
