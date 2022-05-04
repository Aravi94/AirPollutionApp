package com.demo.airpollutionapp.model;

public class FavouriteDTO {

    private String country;
    private String state;
    private String city;
    private String airQualityIndex;
    private String timestamp;
    
	public FavouriteDTO() {
  	  
    }
    
    public FavouriteDTO(String country, String state, String city, String airQualityIndex, String timestamp) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.airQualityIndex = airQualityIndex;
        this.timestamp = timestamp;
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

	public String getAirQualityIndex() {
		return airQualityIndex;
	}

	public void setAirQualityIndex(String airQualityIndex) {
		this.airQualityIndex = airQualityIndex;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Favourite [country=" + country + ", state=" + state
				+ ", city=" + city + ", airQualityIndex=" + airQualityIndex + ", timestamp=" + timestamp + ", toString()=" + super.toString() + "]";
	}
   
}
