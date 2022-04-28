package com.demo.airpollutionapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.model.UserInfoDTO;

public interface AppService {
	static final String USER_NOT_FOUND = "User not found for this id : ";
	static final String USER_NAME_NOT_FOUND = "No User found for the name : ";
	static final String ADMIN_USER_NOT_VALID = " : Admin User not valid";
	static final String USER_ACTIVE = "active";
	static final String USER_NOT_ACTIVE = "inactive";
	static final String ADMIN_ROLE = "admin";
	
	public abstract List<UserInfoDTO> getAllUsers();
	public abstract ResponseEntity<UserInfoDTO> getUserById(String userId) throws ResourceNotFoundException;
	public abstract List<UserInfoDTO> getUsersByName(String name) throws ResourceNotFoundException;
	public abstract UserInfoDTO createUser(UserInfoDTO userInfoDto);
	public abstract ResponseEntity<UserInfoDTO> updateUser(UserInfoDTO userInfoInput) throws ResourceNotFoundException;
	public abstract Map<String, Boolean> deleteUser(String userId) throws ResourceNotFoundException;
	public abstract UserInfoDTO activateUser(UserInfoDTO userInfoDto) throws ResourceNotFoundException;
	public abstract UserInfoDTO deactivateUser(UserInfoDTO userInfoDto) throws ResourceNotFoundException;
}
