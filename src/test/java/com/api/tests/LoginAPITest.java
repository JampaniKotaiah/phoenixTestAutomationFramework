package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.pojo.UserDetailsPojo;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	private UserDetailsPojo userPojo;
	
	@BeforeMethod(description="create the payload for login API")
	public void setup() {
		
		 userPojo = new UserDetailsPojo("iamfd","password");
	}
	
	@Test(description = "Verify if login API is working for FD User",groups = {"api","regression","smoke"})
	public void loginAPITest() throws IOException {	
		
		given()
			.spec(SpecUtil.requestSpec(userPojo))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message",equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
