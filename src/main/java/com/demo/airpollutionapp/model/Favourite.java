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
    private UserInfo userInfo;
    
	public Favourite() {
  	  
    }
    
    public Favourite(long index, String country, String state, String city, UserInfo userInfo) {
    	this.index = index;
        this.country = country;
        this.state = state;
        this.city = city;
        this.userInfo = userInfo;
    }
    
    public Favourite(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
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
				+ ", city=" + city + ", userInfo="
				+ userInfo + ", toString()=" + super.toString() + "]";
	}
   
}
