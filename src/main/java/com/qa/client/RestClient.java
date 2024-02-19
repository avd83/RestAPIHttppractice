package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;

public class RestClient {

	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {

		// 1. GET MEthod without headers
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);// hit the GETurl

		return closeableHttpResponse;
	}

	// 2. GET Method with Headers:
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); // http get request

		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); // hit the GET URL
		return closeableHttpResponse;
			}
	
	public CloseableHttpResponse post(String url,String entryString,HashMap<String ,String> headerMap) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); // http post request
		httppost.setEntity(new StringEntity(entryString));//for request payload 
				
		//for headers
		for(Map.Entry<String,String> entry : headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(),entry.getValue());			
		}
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost);
		return closeableHttpResponse;		
	}
}
