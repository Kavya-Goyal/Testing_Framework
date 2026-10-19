package io.learn.Listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

public class APITestListener implements ITestListener {
	
	private static final ExtentReports extentReports = new ExtentReports();
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	
	static {
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/api-extent-reports.html");
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
			Response response = (Response) result.getTestContext().getAttribute("API Response");
			extentTest.get().pass("Test Passed");
			extentTest.get().info("Response Body: " + formatJSON(response.getBody().asString()));
		}
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		if(extentTest.get() != null) {
			extentTest.get().fail("Test Failed: " + result.getThrowable());
			
			String request = (String) result.getTestContext().getAttribute("API Request");
			Response response = (Response) result.getTestContext().getAttribute("API Response");
			
			if((request != null) && (response != null)) {
				extentTest.get().info("API Request: " + request);
				extentTest.get().info("Response Status: " + response.getStatusCode());
				extentTest.get().info("Response Body: " + formatJSON(response.getBody().asString()));
			}else {
				extentTest.get().info("API Request/Response data is not available.");
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
	
	private String formatJSON(String jsonString) {
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(new ObjectMapper().readTree(jsonString));
		} catch(Exception e) {
			return jsonString;
		}
	}
}
