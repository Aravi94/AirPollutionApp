package com.demo.airpollutionapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.demo.airpollutionapp.exception.AirVisualException;
import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.model.Data;
import com.demo.airpollutionapp.model.UserInfoDTO;

public interface AppService {
	static final String COUNTRY = "country";
	static final String STATE = "state";
	static final String CITY = "city";
	static final String API_KEY = "apikey";
	static final String ADMIN_ROLE = "admin";
	static final String USER_ACTIVE = "active";
	static final String USER_NOT_ACTIVE = "inactive";
	static final String USER_NOT_FOUND = "User not found for the id : ";
	static final String USER_NAME_NOT_FOUND = "No User found for the name : ";
	static final String USER_NOT_VALID = " : User not in active state";
	static final String ADMIN_USER_NOT_VALID = " : Admin User not valid";
	static final String FAV_NOT_FOUND = "No Favourites found for the id : ";
	static final String API_EMPTY_RESPONSE = "No response from Air Visual API";
	static final String API_ERROR_RESPONSE = "Air Visual API call failed with error code : ";
	
	public abstract List<UserInfoDTO> getAllUsers();
	public abstract ResponseEntity<UserInfoDTO> getUserById(String userId) throws ResourceNotFoundException;
	public abstract List<UserInfoDTO> getUsersByName(String name) throws ResourceNotFoundException;
	public abstract UserInfoDTO createUser(UserInfoDTO userInfoInput);
	public abstract ResponseEntity<UserInfoDTO> updateUser(UserInfoDTO userInfoInput) throws ResourceNotFoundException;
	public abstract Map<String, Boolean> deleteUser(String userId) throws ResourceNotFoundException;
	public abstract UserInfoDTO activateUser(UserInfoDTO userInfoInput, boolean activeFlag) throws ResourceNotFoundException;
	public abstract List<Data> getAllCountries(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException;
	public abstract List<Data> getAllStates(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException;
	public abstract List<Data> getAllCities(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException;
	public abstract Map<String, Boolean> addFavourite(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException;
	public abstract UserInfoDTO getFavourites(String userId) throws ResourceNotFoundException, AirVisualException;
}
