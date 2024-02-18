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

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;



public class GetAPITest extends TestBase {

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

	@Test(priority=1)
	public void getAPTTestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(url);
		
		// 2.Status Code
				int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
				System.out.println("Status codd is:" + statusCode);
				Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status code is not 200");

				// 3.Response to Json String
				String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
				JSONObject responseJson = new JSONObject(responseString);
				System.out.println("Response from API is:" + responseJson);
				
				//single value assertion:
				//per_page:
				String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
				System.out.println("value of per page is-->"+ perPageValue);
				Assert.assertEquals(Integer.parseInt(perPageValue), 6);
				
				//total:
				String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
				System.out.println("value of total is-->"+ totalValue);		
				Assert.assertEquals(Integer.parseInt(totalValue), 12);

				//get the value from JSON ARRAY:
				String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
				String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
				String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
				String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

				System.out.println(lastName);
				System.out.println(id);
				System.out.println(avatar);
				System.out.println(firstName);
				
				// 4. Response to Heards STring
				Header[] headersArray = closeableHttpResponse.getAllHeaders();
				HashMap<String, String> allHeaders = new HashMap<String, String>();
				
				for (Header headers : headersArray) {
					allHeaders.put(headers.getName(), headers.getValue());
				}
				System.out.println("All headers Response from API is:" + allHeaders);
			}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test@amazon.com");
//		headerMap.put("password", "test213");
//		headerMap.put("Auth Token", "12345");

		
		closeableHttpResponse = restClient.get(url,headerMap);
		
		//a. Status Code:
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->"+ statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		//b. Json String:
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API---> "+ responseJson);
		
		//single value assertion:
		//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("value of per page is-->"+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("value of total is-->"+ totalValue);		
		Assert.assertEquals(Integer.parseInt(totalValue), 12);

		//get the value from JSON ARRAY:
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		
		
		//c. All Headers
		Header[] headersArray =  closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();	
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());
		}	
		System.out.println("Headers Array-->"+allHeaders);
	}
}

