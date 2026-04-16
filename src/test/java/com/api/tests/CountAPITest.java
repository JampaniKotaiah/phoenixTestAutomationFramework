package com.api.tests;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() throws IOException {
		
		Header authHeader =  new Header("Authorization",getToken(FD));
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.header(authHeader)
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.body("message",Matchers.equalTo("Success"))
			.time(Matchers.lessThan(1000L))
			.body("data",Matchers.notNullValue())
			.body("data.size()",Matchers.equalTo(3))
			.body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));

	}
	@Test
	public void countAPITest_MissingAuthToken() throws IOException {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
		.log().uri()
		.log().method()
		.log().headers()
	.when()
		.get("/dashboard/count")
	.then()
		.log().all()
		.statusCode(401);
		
	}

}
