package org.venkat.api.pojo;

import java.util.Map;

public class ServicesClass {
	private String serviceUrl;
	private Map<String, String> headers;
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
