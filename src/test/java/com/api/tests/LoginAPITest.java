package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import java.io.IOException;
import org.testng.annotations.Test;
import com.api.pojo.UserDetailsPojo;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test
	public void loginAPITest() throws IOException {
		
		UserDetailsPojo userPojo = new UserDetailsPojo("iamfd","password");
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.contentType(ContentType.JSON)
			.and()
			.accept(ContentType.JSON)
			.and()
			.body(userPojo)
			.log().uri()
			.log().method()
			.log().headers()
			.log().body()
		.when()
			.post("login")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(1500L))
			.and()
			.body("message",equalTo("Success"))
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
