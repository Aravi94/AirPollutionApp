package com.demo.airpollutionapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "favourite")
public class Favourite {

	private long index;
    private String country;
    private String state;
    private String city;
    private String airQualityIndex;
    private String timestamp;
    private UserInfo userInfo;
    
	public Favourite() {
  	  
    }
    
    public Favourite(long index, String country, String state, String city, String airQualityIndex, String timestamp, UserInfo userInfo) {
    	this.index = index;
        this.country = country;
        this.state = state;
        this.city = city;
        this.airQualityIndex = airQualityIndex;
        this.timestamp = timestamp;
        this.userInfo = userInfo;
    }
    
    public Favourite(String country, String state, String city, String airQualityIndex, String timestamp) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.airQualityIndex = airQualityIndex;
        this.timestamp = timestamp;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "index", unique = true, nullable = false)
	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	@Column(name = "country", nullable = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "state", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "air_quality_index", nullable = false)
	public String getAirQualityIndex() {
		return airQualityIndex;
	}

	public void setAirQualityIndex(String airQualityIndex) {
		this.airQualityIndex = airQualityIndex;
	}

	@Column(name = "timestamp", nullable = false)
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public String toString() {
		return "Favourite [index=" + index + ", country=" + country + ", state=" + state
				+ ", city=" + city + ", airQualityIndex=" + airQualityIndex + ", timestamp=" + timestamp + ", userInfo="
				+ userInfo + ", toString()=" + super.toString() + "]";
	}
   
}
