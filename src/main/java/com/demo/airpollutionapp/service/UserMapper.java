package com.demo.airpollutionapp.service;

import org.springframework.stereotype.Component;

import com.demo.airpollutionapp.model.UserInfo;
import com.demo.airpollutionapp.model.UserInfoDTO;

@Component
public class UserMapper {

	public UserInfoDTO toDto(UserInfo userInfo) {
        return new UserInfoDTO(userInfo.getUserId(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmailId(), userInfo.getRole(), userInfo.getStatus());
    }

    public UserInfo toUser(UserInfoDTO userInfoDTO) {
        return new UserInfo(userInfoDTO.getUserId(), userInfoDTO.getFirstName(), userInfoDTO.getLastName(), userInfoDTO.getEmailId(), userInfoDTO.getRole());
    }
    
    public UserInfo copyDtoToUser(UserInfoDTO userInfoDTO, UserInfo userInfo) {
    	String temp;
    	temp = userInfoDTO.getFirstName();
    	if (checkValue(temp)) {
    		userInfo.setFirstName(temp);
    	}    	
    	temp = userInfoDTO.getLastName();
    	if (checkValue(temp)) {
    		userInfo.setLastName(temp);
    	}
    	temp = userInfoDTO.getEmailId();
    	if (checkValue(temp)) {
    		userInfo.setEmailId(temp);
    	}
    	temp = userInfoDTO.getRole();
    	if (checkValue(temp)) {
    		userInfo.setRole(temp);
    	}
        return userInfo;
    }
    
    private boolean checkValue (String value) {
    	if (value != null && !"".equalsIgnoreCase(value.trim())) {
    		return true;
    	}
    	return false;
    }
}
