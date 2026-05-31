package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.api.utils.SpecUtil;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	@Test(description = "Verify the master API is giving correct response",groups= {"api","smoke","regression"})
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
	@Test(description = "Verify the master API is giving correct status code of invalide token",groups= {"api","smoke","regression","negative"})
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
