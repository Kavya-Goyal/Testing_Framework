package io.learn.AppTest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.learn.Pages.CartPage;
import io.learn.Pages.LoginPage;
import io.learn.Pages.ProductsPage;
import io.learn.exception.LoginFailedException;
import io.learn.utils.Browser;
import io.learn.utils.ConfigReader;
import io.learn.utils.WebDriverFactory;

public abstract class BaseTest {
	
	protected WebDriver driver;
	protected LoginPage login;
	protected ProductsPage products;
	protected CartPage cart;
	
	public static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();	
	protected static final ConfigReader reader = new ConfigReader();
	private static final Logger logger = LogManager.getLogger(BaseTest.class);
	Browser browser;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({"browser"})
	public void setUp(String name) {
		
		logger.debug("Initialize the WebDriver for browser: {}", name);
		
		try {
		browser = Browser.valueOf(name.toUpperCase());
		}catch(Exception e) {
			browser = Browser.valueOf("CHROME");
		}
		
		logger.debug("Initialize the WebDriver");
		driver = WebDriverFactory.createDriver(browser);
		driverThreadLocal.set(driver);
		
		logger.info("Navigating to the base URL: {}", reader.getProperty("app.url"));
		driver.get(reader.getProperty("app.url"));
		
		login = new LoginPage(driver);
		
		try {
			login.login(reader.getProperty("username"),reader.getProperty("password"));
			products = login.enterLogin();
			logger.info("Login Successful, navigating to Product Page.");
		}
		catch(LoginFailedException e) {
			logger.error("Login failed: {}", e.getMessage());
			throw e;
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
		driverThreadLocal.remove();
	}
}
