package org.venkat.api.core;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.venkat.api.pojo.ConfigAPI;
import org.venkat.api.pojo.ServicesClass;

public class RestClient {
	private ConfigAPI configAPI = Utility.newInstance().getConfigApi();
	private String url = null;

	public RestClient() {
		if ("prod".equals(GlobalConstants.environment))
			url = configAPI.getUrl().get("prod");
		else
			url = configAPI.getUrl().get("uat");
	}

	public HttpResponse get(String serviceName) {
		try {
			ServicesClass servicesClass = configAPI.getServices().get(serviceName);
			if (servicesClass != null) {
				String serviceUrl = url.concat(servicesClass.getServiceUrl());
				// HTTP Client
				CloseableHttpClient client = HttpClients.createDefault();
				// HTTP Get request
				HttpGet getRequest = new HttpGet(serviceUrl);
				// Add Headers
				Map<String, String> headerMap = servicesClass.getHeaders();
				if (headerMap != null) {
					for (Map.Entry<String, String> entery : headerMap.entrySet()) {
						getRequest.addHeader(entery.getKey(), entery.getValue());
					}
				}
				// Hit the get URL and get HTTP Response
				return client.execute(getRequest);
			} else {
				String errorMsg = serviceName + " service info is not avelable in configAPI";
				System.out.println(errorMsg);
				throw new Exception(errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse get(String serviceName, String breedName) {
		try {
			ServicesClass servicesClass = configAPI.getServices().get(serviceName);
			if (servicesClass != null) {
				String serviceUrl = url.concat(String.format(servicesClass.getServiceUrl(), breedName));
				// HTTP Client
				CloseableHttpClient client = HttpClients.createDefault();
				// HTTP Get request
				HttpGet getRequest = new HttpGet(serviceUrl);
				// Add Headers
				Map<String, String> headerMap = servicesClass.getHeaders();
				if (headerMap != null) {
					for (Map.Entry<String, String> entery : headerMap.entrySet()) {
						getRequest.addHeader(entery.getKey(), entery.getValue());
					}
				}
				// Hit the get URL and get HTTP Response
				return client.execute(getRequest);
			} else {
				String errorMsg = serviceName + " service info is not avelable in configAPI";
				System.out.println(errorMsg);
				throw new Exception(errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse get(String serviceName, String breedName, String subBreedName) {
		try {
			ServicesClass servicesClass = configAPI.getServices().get(serviceName);
			if (servicesClass != null) {
				String serviceUrl = url.concat(String.format(servicesClass.getServiceUrl(), breedName, subBreedName));
				// HTTP Client
				CloseableHttpClient client = HttpClients.createDefault();
				// HTTP Get request
				HttpGet getRequest = new HttpGet(serviceUrl);
				// Add Headers
				Map<String, String> headerMap = servicesClass.getHeaders();
				if (headerMap != null) {
					for (Map.Entry<String, String> entery : headerMap.entrySet()) {
						getRequest.addHeader(entery.getKey(), entery.getValue());
					}
				}
				// Hit the get URL and get HTTP Response
				return client.execute(getRequest);
			} else {
				String errorMsg = serviceName + " service info is not avelable in configAPI";
				System.out.println(errorMsg);
				throw new Exception(errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public HttpResponse post(String serviceName, String entityString) {
		try {
			ServicesClass servicesClass = configAPI.getServices().get(serviceName);
			if (servicesClass != null) {
				String serviceUrl = url.concat(servicesClass.getServiceUrl());
				// HTTP Client
				CloseableHttpClient client = HttpClients.createDefault();
				// HTTP Get request
				HttpPost postRequest = new HttpPost(serviceUrl);
				// Add pay-load
				postRequest.setEntity(new StringEntity(entityString));
				// Add Headers
				Map<String, String> headerMap = servicesClass.getHeaders();
				if (headerMap != null) {
					for (Map.Entry<String, String> entery : headerMap.entrySet()) {
						postRequest.addHeader(entery.getKey(), entery.getValue());
					}
				}
				// Hit the get URL and get HTTP Response
				return client.execute(postRequest);
			} else {
				String errorMsg = serviceName + " service info is not avelable in configAPI";
				System.out.println(errorMsg);
				throw new Exception(errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public HttpResponse post(String url, String entityString, Map<String, String> headerMap)
			throws ClientProtocolException, IOException {
		// HTTP Client
		CloseableHttpClient client = HttpClients.createDefault();
		// HTTP Get request
		HttpPost postRequest = new HttpPost(url);
		// Add pay-load
		postRequest.setEntity(new StringEntity(entityString));
		// Add Headers
		for (Map.Entry<String, String> entery : headerMap.entrySet()) {
			postRequest.addHeader(entery.getKey(), entery.getValue());
		}
		// Hit the get URL and get HTTP Response
		return client.execute(postRequest);
	}
}
