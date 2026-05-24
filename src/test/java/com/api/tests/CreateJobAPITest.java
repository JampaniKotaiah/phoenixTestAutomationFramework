package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() throws IOException {

		String imenumber = RandomStringUtils.randomNumeric(15);
		Customer customer = new Customer("Kotaiah", "Jampani", "8143737310","","koti.31mca@yahoo.co.in","");
		CustomerAddress customerAddress = new CustomerAddress("D 404","karmikaNagar","yousafguda","Hyderabad", "secunderabad","500045", "India", "Telangana");
		CustomerProduct customerProduct = new CustomerProduct("2026-05-06T18:30:00.000Z", imenumber, imenumber, imenumber, "2026-05-06T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(2,"mobile Hanging issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload createjobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		given()
			.spec(SpecUtil.requestSpecificationWithAuth(Role.FD,createjobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobAPIresponseSchema.json"))
			.body("message",Matchers.equalTo("Job created successfully. "))
			.body("data.job_number",Matchers.startsWith("JOB_"))
			.body("data.mst_service_location_id" , Matchers.equalTo(1));
		
	}

}
