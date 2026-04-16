package com.api.utils;

import static io.restassured.RestAssured.*;
import java.io.IOException;
import static org.hamcrest.Matchers.*;
import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.pojo.UserDetailsPojo;
import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private AuthTokenProvider() {
		
	}

	public static String getToken(Role role) throws IOException {
		
		
		UserDetailsPojo userDetailsPojo = null;
		
		
		if(role == FD) {
			
			userDetailsPojo = new UserDetailsPojo("iamfd","password");
		}
		else if(role == SUP) {
			
			userDetailsPojo = new UserDetailsPojo("iamsup","password");
		}
		else if(role == ENG) {
			
			userDetailsPojo = new UserDetailsPojo("iameng","password");
		}
		else if(role == QC) {
			
			userDetailsPojo = new UserDetailsPojo("iamqc","password");
		}
		
		String token = given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.contentType(ContentType.JSON)
			.body(userDetailsPojo)
		.when()
			.post("login")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("message",equalTo("Success"))
			.extract()
			.body()
			.jsonPath()
			.getString("data.token");
		
		return token;
		

	}

}
