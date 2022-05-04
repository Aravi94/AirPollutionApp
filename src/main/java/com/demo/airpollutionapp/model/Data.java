package com.demo.airpollutionapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Data {

    private String country;
    private String state;
    private String city;
    private String timestamp;
    private String airQualityIndex;
    private String message;
    private Current current;
    
	public Data() {
  	  
    }
	
	public Data(String country, String state, String city, String timestamp, String airQualityIndex, String message, Current current) {
		this.country = country;
        this.state = state;
        this.city = city;
        this.timestamp = timestamp;
        this.airQualityIndex = airQualityIndex;
        this.message = message;
        this.current = current;
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAirQualityIndex() {
		return airQualityIndex;
	}

	public void setAirQualityIndex(String airQualityIndex) {
		this.airQualityIndex = airQualityIndex;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Current getCurrent() {
		return current;
	}

	public void setCurrent(Current current) {
		this.current = current;
	}

	@Override
	public String toString() {
		return "Data [country=" + country + ", state=" + state + ", city=" + city + ", timestamp=" + timestamp
				+ ", airQualityIndex=" + airQualityIndex + ", message=" + message + ", current=" + current
				+ ", toString()=" + super.toString() + "]";
	}
    
}
