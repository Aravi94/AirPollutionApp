package com.demo.airpollutionapp.facade;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "airvisual")
public class AirVisConfiguration {

	private String countryUrl;
	private String stateUrl;
	private String cityUrl;
	private String cityInfoUrl;
	private String apiKey;
	private String cityApiKey;
	private String favApiKey;
	
	public String getCountryUrl() {
		return countryUrl;
	}
	
	public void setCountryUrl(String countryUrl) {
		this.countryUrl = countryUrl;
	}
	
	public String getStateUrl() {
		return stateUrl;
	}
	
	public void setStateUrl(String stateUrl) {
		this.stateUrl = stateUrl;
	}
	
	public String getCityUrl() {
		return cityUrl;
	}
	
	public void setCityUrl(String cityUrl) {
		this.cityUrl = cityUrl;
	}
	
	public String getCityInfoUrl() {
		return cityInfoUrl;
	}
	
	public void setCityInfoUrl(String cityInfoUrl) {
		this.cityInfoUrl = cityInfoUrl;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCityApiKey() {
		return cityApiKey;
	}

	public void setCityApiKey(String cityApiKey) {
		this.cityApiKey = cityApiKey;
	}

	public String getFavApiKey() {
		return favApiKey;
	}

	public void setFavApiKey(String favApiKey) {
		this.favApiKey = favApiKey;
	}

}
