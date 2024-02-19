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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBaseBook;
import com.qa.client.RestClientBook;
import com.qa.data.Books;



public class PostBookAPITest extends TestBaseBook {

	TestBaseBook testBaseBook;
	Books actualBook;
	String appUrl;
	String resourceUrl;
	String url;
	String bookJsonString;
	RestClientBook restClientBook;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() {	
		
		testBaseBook = new TestBaseBook();
		appUrl = prop.getProperty("URL");
		resourceUrl = prop.getProperty("resource");
		
		url = appUrl + resourceUrl;		
	}
	
	@Test
	public void postBookAPITest() throws ClientProtocolException, IOException {
		
		restClientBook = new RestClientBook();
		closeableHttpResponse=restClientBook.get(url);
		
		HashMap<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type","application/json");
		
		ObjectMapper objectMapper = new ObjectMapper();
		actualBook = new Books("LearnSQL","SQL","112","AS Sharma");
		objectMapper.writeValue(new File("C:\\Users\\Abhay\\eclipse-workspace-RestAssured\\RestAPIHttpPractice\\src\\main\\java\\com\\qa\\data\\books.json"), actualBook);
		
		bookJsonString = objectMapper.writeValueAsString(actualBook);
		System.out.println(bookJsonString);
		
		closeableHttpResponse = restClientBook.post(url, bookJsonString, headerMap);
		
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
		
		String responseString =EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println(responseJson);
		
		
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Books expectedBookObjcet = objectMapper.readValue(responseString, Books.class);
		System.out.println(expectedBookObjcet);
		

		System.out.println(actualBook.getName().equals(expectedBookObjcet.getName()));
		System.out.println(actualBook.getAuthor().equals(expectedBookObjcet.getAuthor()));
		Assert.assertTrue(actualBook.getName().equals(expectedBookObjcet.getName()));
		Assert.assertTrue(actualBook.getAuthor().equals(expectedBookObjcet.getAuthor()));
	}	
}
