package io.learn.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import io.learn.exception.ItemNotInCartException;

public class CartPage {

	private WebDriver driver;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void removeItem(String productName) {
		try {
		driver.findElement(By.xpath("//div[text()='"+ productName +"']/parent::a/following-sibling::div[2]/descendant::button")).click();
		} catch(NoSuchElementException e){
			throw new ItemNotInCartException("Expected Item is not in the cart.");
		}
	}	
	
	public boolean isProductInCart(String productName) {
		return driver.getPageSource().contains(productName);
	}
}