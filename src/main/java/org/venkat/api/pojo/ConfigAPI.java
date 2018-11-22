package org.venkat.api.pojo;

import java.util.Map;

public class ConfigAPI {
	Map<String, String> url;
	Map<String, ServicesClass> services;
	
	public Map<String, String> getUrl() {
		return url;
	}
	public void setUrl(Map<String, String> url) {
		this.url = url;
	}
	public Map<String, ServicesClass> getServices() {
		return services;
	}
	public void setServices(Map<String, ServicesClass> services) {
		this.services = services;
	}
}
