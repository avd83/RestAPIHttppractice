package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBaseBook;
import com.qa.client.RestClientBook;
import com.qa.data.Books;
import com.qa.util.TestUtil;



public class GetBookAPITest extends TestBaseBook {
	
	TestBaseBook testBaseBook;
	//Properties prop;
	String appUrl;
	String resourceUrl;
	String url;
	RestClientBook restClientBook;
	CloseableHttpResponse closeableHttpResponse;
	Books actualBook;
	
	@BeforeMethod
	public void setUp(){	
		
		testBaseBook = new TestBaseBook();		
		appUrl = prop.getProperty("URL");
		resourceUrl = prop.getProperty("resource1");
		
		url = appUrl + resourceUrl;		
	}
	
	@Test
	public void getWithHeader() throws ClientProtocolException, IOException {
		
		restClientBook = new RestClientBook();		
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type","application/json");
		
		closeableHttpResponse = restClientBook.get(url,headerMap);
		
		int statusCose = closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCose, RESPONSE_STATUS_CODE_200);
		
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
		String responseString1 = responseString.substring(1);
		String responseString2 = responseString1.substring(0, 72);
		System.out.println(responseString1);
		
		JSONObject responseJson = new JSONObject(responseString2);
		System.out.println(responseJson);
		
		actualBook= new Books();		
		
		String book_name = TestUtil.getValueByJPath(responseJson,"/book_name" );
		System.out.println(book_name);	
		Assert.assertEquals(book_name,"LearnSQL");
		
		String isbn = TestUtil.getValueByJPath(responseJson,"/isbn" );
		System.out.println(isbn);
		Assert.assertEquals(isbn,"SQL");
		String aisle = TestUtil.getValueByJPath(responseJson,"/aisle" );
		System.out.println(aisle);
		Assert.assertEquals(aisle,"112");
		String author = TestUtil.getValueByJPath(responseJson,"/author" );
		System.out.println(author);
		Assert.assertEquals(author,"AS Sharma");
		
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for (Header headers : headersArray) {
			allHeaders.put(headers.getName(), headers.getValue());
		}
		System.out.println("All headers Response from API is:" + allHeaders);
	}

}
