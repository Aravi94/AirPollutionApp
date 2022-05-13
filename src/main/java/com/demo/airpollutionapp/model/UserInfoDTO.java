package com.demo.airpollutionapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserInfoDTO {

	private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String status;
    private String loginId;
    private String country;
    private String state;
    private String city;
    private List<FavouriteDTO> favourites;
    
	public UserInfoDTO() {
  	  
    }
    
    public UserInfoDTO(String userId, String firstName, String lastName, String emailId, String role, String status) {
    	this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.role = role;
        this.status = status;
    }
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public List<FavouriteDTO> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<FavouriteDTO> favourites) {
		this.favourites = favourites;
	}

	@Override
	public String toString() {
		return "UserInfoDTO [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId="
				+ emailId + ", role=" + role + ", status=" + status + ", loginId=" + loginId + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", favourites=" + favourites + ", toString()="
				+ super.toString() + "]";
	}

}
