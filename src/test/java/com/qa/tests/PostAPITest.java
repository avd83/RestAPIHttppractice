package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String appUrl;
	String resourceUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() {
		
		testBase = new TestBase();
		appUrl = prop.getProperty("URL");
		resourceUrl = prop.getProperty("resource");

		url = appUrl + resourceUrl;
	}
	
	@Test
	public void postAITTest() throws ClientProtocolException, IOException {
		restClient=new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		Users actualUsers = new Users("morpheus","leader");
		
		// convert Object to Json file data : Marshaling		
		objectMapper.writeValue(new File("C:\\Users\\Abhay\\eclipse-workspace-RestAssured\\RestAPIPractice\\src\\main\\java\\com\\qa\\data\\users.json"), actualUsers);
		
		//convert Object to json string
		String usersJasonString = objectMapper.writeValueAsString(actualUsers);
		System.out.println(usersJasonString);
		
		closeableHttpResponse = restClient.post(url, usersJasonString, headerMap);//hot the post API
		
		//find and assert status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_201);
		
		// json string
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");		
		JSONObject responseJson  = new JSONObject(responseString);		
		System.out.println(responseJson);
		
		//Json to java object conversion : Un-Marshaling
		Users expectedUsersObject = objectMapper.readValue(responseString, Users.class);
		System.out.println(expectedUsersObject);
		
		System.out.println(actualUsers.getName().equals(expectedUsersObject.getName()));
		System.out.println(actualUsers.getJob().equals(expectedUsersObject.getJob()));
		Assert.assertTrue(actualUsers.getName().equals(expectedUsersObject.getName()));
		Assert.assertTrue(actualUsers.getJob().equals(expectedUsersObject.getJob()));
		
		System.out.println(expectedUsersObject.getId());
		System.out.println(expectedUsersObject.getCreatedAt());
	}
	
	
	
	

}
