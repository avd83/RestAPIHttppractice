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

public class RestClientBook {	
	
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);		
		return closeableHttpResponse;
	}
	
	public CloseableHttpResponse get(String url,HashMap<String,String> headerMap)throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
				
		for(Map.Entry<String,String> entry : headerMap.entrySet()){			
			httpget.addHeader(entry.getKey(),entry.getValue());			
		}	
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget);
		return closeableHttpResponse;
	}
	
	public CloseableHttpResponse post(String url,String entryString,HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
	CloseableHttpClient httpClient = HttpClients.createDefault();//HTTP request created
	HttpPost httpPost = new HttpPost(url);
	httpPost.setEntity(new StringEntity(entryString));
	
	for(Map.Entry<String,String> entry : headerMap.entrySet()) {
		httpPost.addHeader(entry.getKey(),entry.getValue());
	}
	CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpPost);		
	return closeableHttpResponse;
	
	
}
	
	

}
