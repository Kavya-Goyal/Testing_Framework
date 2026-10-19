package io.learn.API;

import static io.restassured.RestAssured.given;

import io.learn.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {

	private static final ConfigReader reader = new ConfigReader();
	private static final String BASE_URL = reader.getProperty("api.base.uri"); //end point
	
	private ApiClient() {
		
	}
	private static RequestSpecification getRequestSpecification() {
		return new RequestSpecBuilder()
				.setBaseUri(BASE_URL)
				.setContentType("application/json")
				.build();
	}
	public static Response get(String resource) {
		return given()
				.spec(getRequestSpecification())
				.log().all()
				.when()
				.log().all()
				.get(resource);
	}
	public static Response post(String resource, String body) {
		return given()
				.spec(getRequestSpecification())
				.body(body)
				.when()
				.post(resource);
	}
	public static Response put(String resource, String body) {
		return given()
				.spec(getRequestSpecification())
				.body(body)
				.when()
				.put(resource);
	}
	public static Response delete(String resource) {
		return given()
				.spec(getRequestSpecification())
				.when()
				.delete(resource);
	}
	
}
