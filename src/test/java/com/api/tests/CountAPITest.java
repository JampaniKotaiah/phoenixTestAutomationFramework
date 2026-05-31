package com.api.tests;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CountAPITest {
	
	@Test(description = "Verify the count API is giving correct response",groups= {"api","smoke","regression"})
	public void verifyCountAPIResponse() throws IOException {
		
	//	Header authHeader =  new Header("Authorization",getToken(FD));
		given()
			.spec(SpecUtil.requestSpecificationWithAuth(FD))

		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message",Matchers.equalTo("Success"))
			.body("data",Matchers.notNullValue())
			.body("data.size()",Matchers.equalTo(3))
			.body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
			.body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));

	}
	@Test(description = "Verify the countAPI missingAuthtoken",groups= {"api","negative","smoke","regression"})
	public void countAPITest_MissingAuthToken() throws IOException {
		
		given()
			.spec(SpecUtil.requestSpec())
	.when()
		.get("/dashboard/count")
	.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
		
	}

}
