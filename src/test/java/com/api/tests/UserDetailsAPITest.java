package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import java.io.IOException;
import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static com.api.constant.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;


public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() throws IOException {
		
	//	Header authHeader =  new Header("Authorization",getToken(FD));
		
		given()
			.spec(SpecUtil.requestSpecificationWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
			
			
	}

}
