package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class MasterAPITest {
	@Test
	public void masterAPITest() throws IOException {
		
		given()
			.spec(SpecUtil.requestSpecificationWithAuth(FD))
		.when()
			.post("master")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message",Matchers.equalTo("Success"))
			.body("data",Matchers.notNullValue())
			.body("data",Matchers.hasKey("mst_oem"))
			.body("data",Matchers.hasKey("mst_model"))
			.body("$",Matchers.hasKey("message"))
			.body("$", Matchers.hasKey("data"))
			.body("data.mst_oem.size()",Matchers.greaterThan(0))
			.body("data.mst_model.size()",Matchers.greaterThan(0))
			.body("data.mst_oem.id", Matchers.everyItem(Matchers.greaterThan(0)))
			.body("data.mst_oem.name",Matchers.everyItem(Matchers.not(Matchers.empty())))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
	}
	@Test
	public void invalidTokenMasterAPITest() throws IOException {
		
		given()
		.spec(SpecUtil.requestSpec())
		.log().all()
	.when()
		.post("master")
	.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
	}



}
