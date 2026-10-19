package io.learn.AppTest.API;

import static org.testng.Assert.assertEquals;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.learn.API.ApiClient;
import io.learn.Listener.APITestListener;
import io.restassured.response.Response;

public class APITest {
	
	private static final String PET_ID = "7889";
	
	@BeforeClass(alwaysRun = true)
	public void idCheck() {
		Response response = ApiClient.get("/v2/pet/" + PET_ID);
		
		if(response.getStatusCode() == 200) {
			response = ApiClient.delete("/v2/pet/" + PET_ID);
		}
	}
	
	@Test(priority = 1)
	public void testPostMethod(ITestContext context) {
		ExtentTest test = APITestListener.getTest();
		
		Pet pet = new Pet(PET_ID, "elephant", "sold");
		
		test.log(Status.INFO, "Sending POST Request to add new pet.");
		Response response = ApiClient.post("/v2/pet", pet.toJson());
		
		test.log(Status.INFO, "Response received with status code: " + response.getStatusCode());
		
		context.setAttribute("API Request", "/pet/");
		context.setAttribute("API Response", response);
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.jsonPath().getString("name"), "elephant");
		
		test.log(Status.PASS, "New Pet was added with ID: " + PET_ID);
	}
	
	@Test(priority = 2,dependsOnMethods = "testPostMethod")
	public void testPutMethod(ITestContext context) {
		ExtentTest test = APITestListener.getTest();
		
		Pet pet = new Pet(PET_ID, "Cat", "Available");
		
		test.log(Status.INFO, "Sending PUT Request to update pet name.");
		Response response = ApiClient.put("/v2/pet", pet.toJson());

		test.log(Status.INFO, "Response received with status code: " + response.getStatusCode());
		
		context.setAttribute("API Request", "/pet/");
		context.setAttribute("API Response", response);
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.jsonPath().getString("name"), "Cat");
		
		test.log(Status.PASS, "Pet Name with " + PET_ID + " updated.");
	}
	
	@Test(priority = 3,dependsOnMethods = "testPutMethod")
	public void testGetRequest(ITestContext context) {
		ExtentTest test = APITestListener.getTest();
		
		test.log(Status.INFO, "Sending GET Request to retrieve pet with ID: " + PET_ID);
		Response response = ApiClient.get("/v2/pet/" + PET_ID);
		
		
		context.setAttribute("API Request", "/pet/" + PET_ID);
		context.setAttribute("API Response", response);
		
		test.log(Status.INFO, "Response received with status code: " + response.getStatusCode());
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.jsonPath().getString("name"), "Cat");
		
		test.log(Status.PASS, "Pet received successfully with ID: " + PET_ID);
	}
	
	@Test(priority = 4,dependsOnMethods = "testGetRequest")
	public void testDeleteRequest(ITestContext context) {
		ExtentTest test = APITestListener.getTest();
		
		test.log(Status.INFO, "Sending DELETE Request to retrieve pet with ID: " + PET_ID);
		Response response = ApiClient.delete("/v2/pet/" + PET_ID);
		
		
		context.setAttribute("API Request", "/pet/" + PET_ID);
		context.setAttribute("API Response", response);
		
		test.log(Status.INFO, "Response received with status code: " + response.getStatusCode());
		
		assertEquals(response.getStatusCode(), 200);
		
		test.log(Status.PASS, "Pet deleted successfully with ID: " + PET_ID);
	}
	
	@Test(priority = 5,dependsOnMethods = "testDeleteRequest")
	public void testGETReq(ITestContext context) {
		ExtentTest test = APITestListener.getTest();
		
		test.log(Status.INFO, "Sending GET Request to retrieve pet with ID: " + PET_ID);
		Response response = ApiClient.get("/v2/pet/" + PET_ID);
		
		
		context.setAttribute("API Request", "/pet/" + PET_ID);
		context.setAttribute("API Response", response);
		
		test.log(Status.INFO, "Response received with status code: " + response.getStatusCode());
		
		assertEquals(response.getStatusCode(), 404);
		
		test.log(Status.PASS, "Pet not found with ID: " + PET_ID);
	}
	
}
