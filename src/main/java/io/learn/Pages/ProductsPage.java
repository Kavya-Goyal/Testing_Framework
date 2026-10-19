package io.learn.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.learn.exception.InvalidProductSelectionException;

public class ProductsPage {
	
	private WebDriver driver;
	
	By menu = By.xpath("//button[text() = 'Open Menu']");
	By logout = By.id("logout_sidebar_link");
	By cart = By.cssSelector(".shopping_cart_link");
	By filter = By.xpath("//select[@class='product_sort_container']");
	By lohi = By.cssSelector("option[value='lohi']");
	By add = By.id("add-to-cart-sauce-labs-backpack");
	By productTitle = By.className("inventory_item_name");
	By price = By.className("inventory_item_price");
	
	public ProductsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isProductPageDisplayed() {
		return driver.findElement(add).isDisplayed();
	}
	public boolean isProductListDisplayed() {
		return !driver.findElements(productTitle).isEmpty();
	}
	
	public void logout() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		driver.findElement(menu).click();
		wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
	}
	
	public CartPage openCart() {
		driver.findElement(cart).click();
		return new CartPage(driver);
	}
	public void filterList() {
		driver.findElement(filter).click();
		driver.findElement(lohi).click();
	}
	public void addToCart(String productName) {
		try {driver.findElement(By
				.xpath("//div[text()='" + productName + "']/parent::a/parent::div/following-sibling::div/button")).click();
	
		} catch(NoSuchElementException e) {
			throw new InvalidProductSelectionException("Product is not avaliable.");
		}
	}
	
	public boolean isProductDisplayed(String productName) {
		return driver.findElement(By.xpath("//div[text()='"+ productName +"']")).isDisplayed();
	}
	
	public boolean isProductPriceDisplayed(String productName) {
		WebElement productNameElement = driver.findElement(By.xpath("//div[text()='"+ productName +"']"));
		
		return driver.findElement(RelativeLocator.with(By.tagName("div"))
				.toLeftOf(add).near(productNameElement,2000)).isDisplayed();
	}
	
	public String getProductPrice() {
		return driver.findElement(RelativeLocator.with(By.tagName("div"))
				.toLeftOf(add)).getText();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public boolean isProductInCart(String productName) {
        driver.findElement(cart).click();
        return driver.getPageSource().contains(productName);
    }
}
