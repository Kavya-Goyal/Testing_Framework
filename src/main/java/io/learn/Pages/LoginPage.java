package io.learn.Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.learn.exception.LoginFailedException;


public class LoginPage {
	
	private WebDriver driver;
    
	private By username = By.id("user-name");
	private By password = By.id("password");
	private By loginButton = By.id("login-button") ;
	private By appLogo = By.xpath("//div[@class='app_logo']"); 
	private By error = By.tagName("h3");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void login(String username,String password) {
		this.enterUserName(username);
		this.enterPassword(password);
	}
	
	public void enterUserName(String name) {
		driver.findElement(username).clear();
		driver.findElement(username).sendKeys(name);
	}
	public void enterPassword(String pass) {
		driver.findElement(password).clear();
		driver.findElement(password).sendKeys(pass);
	}
	public ProductsPage enterLogin() {
		driver.findElement(loginButton).click();
		if(isLoginErrorDisplayed()) {
			throw new LoginFailedException("User Credentials not valid");
		}
		return new ProductsPage(driver);
	}

	public String logoText() {
		return driver.findElement(appLogo).getText();
	}
	
	public boolean isLoginErrorDisplayed() {
		try {
			return driver.findElement(error).isDisplayed();
		}
		catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isLoginPageDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.presenceOfElementLocated(username)).isDisplayed();
		
	}
}
