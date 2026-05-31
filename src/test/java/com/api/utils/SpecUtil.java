package com.api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.api.utils.ConfigManager.*;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.pojo.UserDetailsPojo;

public class SpecUtil {
	
	public static RequestSpecification requestSpec() throws IOException {
		//common request sections
		RequestSpecification requestspec = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.log(LogDetail.BODY)
		.log(LogDetail.METHOD)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.build();
		return requestspec;
	}
	
	//POST-PUT-PETCH {BODY}
	//GET-DELETE {No BODY}
	
	public static RequestSpecification requestSpec(Object userPojo) throws IOException {
		//common request sections
		RequestSpecification requestspec = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(userPojo)
		.log(LogDetail.BODY)
		.log(LogDetail.METHOD)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.build();
		return requestspec;
	}
	
	public static RequestSpecification requestSpecificationWithAuth(Role role) throws IOException {
		RequestSpecification requestspec = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization",AuthTokenProvider.getToken(role))
		.log(LogDetail.BODY)
		.log(LogDetail.METHOD)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.build();
		return requestspec;
		
	}
	
	public static RequestSpecification requestSpecificationWithAuth(Role role,Object payload) throws IOException {
		RequestSpecification requestspec = new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization",AuthTokenProvider.getToken(role))
		.setBody(payload)
		.log(LogDetail.BODY)
		.log(LogDetail.METHOD)
		.log(LogDetail.URI)
		.log(LogDetail.HEADERS)
		.build();
		return requestspec;
		
	}
	
	public static ResponseSpecification responseSpec_OK() {
		
		ResponseSpecification responseSepc = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.expectResponseTime(Matchers.lessThan(2000L))
		.log(LogDetail.ALL)
		.build();
		
		return responseSepc;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		
		ResponseSpecification responseSepc = new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(2000L))
		.log(LogDetail.ALL)
		.build();
		
		return responseSepc;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		
		ResponseSpecification responseSepc = new ResponseSpecBuilder()
		.expectStatusCode(statusCode)
		.expectResponseTime(Matchers.lessThan(2000L))
		.log(LogDetail.ALL)
		.build();
		
		return responseSepc;
	}

}
