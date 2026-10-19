package io.learn.Listener;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.learn.AppTest.BaseTest;
import io.learn.utils.ScreenshotUtility;


public class TestListener implements ITestListener {
	
	private static final ExtentReports extentReports = new ExtentReports();
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	static {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports.html");
		extentReports.attachReporter(sparkReporter);
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		test.info("Test started: " + result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		if(extentTest.get() != null) {
			extentTest.get().pass("Test Passed");
		}
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		if(extentTest.get() != null) {
			extentTest.get().fail("Test Failed: " + result.getThrowable());
			
			WebDriver driver = BaseTest.driverThreadLocal.get();
			if(driver != null) {
				String screenshotPath = ScreenshotUtility.captureScreenshot(driver, result.getMethod().getMethodName());
				extentTest.get().addScreenCaptureFromPath(screenshotPath.replace("target", ""));
			} else {
				extentTest.get().info("WebDriver instance is null. Unable to capture screenshot.");
			}
		}
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		if(extentTest.get() != null) {
			extentTest.get().skip("Test skipped: " + result.getMethod().getMethodName());
		}
	}
	
	@Override
	public void onFinish(ITestContext context) {
		extentReports.flush();
	}
	
	@Override
	public void onStart(ITestContext context) {
		
	}
	
	public static ExtentTest getTest() {
		return extentTest.get();
	}
}
