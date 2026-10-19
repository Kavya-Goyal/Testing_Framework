package io.learn.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {	
	private static String remote = System.getProperty("remote");
	private static String hubUrl = "http://localhost:4444/wd/hub";
	
	public static WebDriver createDriver(Browser browser) {
		
		WebDriver driver;
		
		if("true".equalsIgnoreCase(remote)) {
			driver = getRemoteDriver(browser);
		}
		else {
			driver = getLocalDriver(browser);
		}
		return driver;
	}

	public static WebDriver getLocalDriver(Browser browser) {
		WebDriver driver;
		
		switch(browser) {
			case CHROME:
				driver = new ChromeDriver();
				break;
			case EDGE:
				driver = new EdgeDriver();
				break;
			case FIREFOX:
				driver = new FirefoxDriver();
				break;
			default:
				driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	public static WebDriver getRemoteDriver(Browser browser) {
		WebDriver driver;
		
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setBrowserName(browser.getBrowserName());
		try {
			driver = new RemoteWebDriver(new URL(hubUrl).toURI().toURL(),capability);
		}catch(MalformedURLException | URISyntaxException e){
			throw new RuntimeException(e);
		}
		
		return driver;
	}
}
