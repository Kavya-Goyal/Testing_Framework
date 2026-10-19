package io.learn.AppTest;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.learn.Listener.TestListener;

public class CartTest extends BaseTest {
	
	@Test(priority = 1)
	public void addingProductToCart(){
		ExtentTest test = TestListener.getTest();
		test.log(Status.INFO, "Adding product to cart");
		products.addToCart("Sauce Labs Backpack");
		
		test.log(Status.INFO, "Navigating to Cart.");
		cart = products.openCart();
		
		test.log(Status.INFO, "Verifying product is in cart.");
		assertTrue(cart.isProductInCart("Sauce Labs Backpack"),"Product is not in cart.");
		test.log(Status.PASS, "Product is in cart.");
	}
	
	@Test(priority = 2)
	public void removeProductFromCart() {
		ExtentTest test = TestListener.getTest();
		
		test.log(Status.INFO, "Removing product from cart.");
		cart.removeItem("Sauce Labs Backpack");
		
		test.log(Status.INFO, "Verifying product is removed from cart.");
		assertFalse(cart.isProductInCart("Sauce Labs Backpack"));
		test.log(Status.PASS, "Product removed successfully.");
	}
	
}
