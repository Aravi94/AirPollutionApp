package com.demo.airpollutionapp.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.demo.airpollutionapp.model.Data;
import com.demo.airpollutionapp.model.Favourite;
import com.demo.airpollutionapp.model.FavouriteDTO;
import com.demo.airpollutionapp.model.Pollution;
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
    
    public Favourite toFavourite (UserInfo userInfo, Data data) {
    	Pollution pollution = data.getCurrent().getPollution();
    	Favourite favourite = new Favourite(data.getCountry(), data.getState(), data.getCity(), pollution.getAqius(), pollution.getTs());
    	favourite.setUserInfo(userInfo);
    	return favourite;
    }
    
    public UserInfoDTO favToDto (List<Favourite> favouriteList) {
    	UserInfo userInfo = favouriteList.get(0).getUserInfo();
    	UserInfoDTO userInfoDto = new UserInfoDTO(userInfo.getUserId(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmailId(), userInfo.getRole(), userInfo.getStatus());
    	List<FavouriteDTO> favourites = favouriteList.stream()
    			.map(fav -> new FavouriteDTO(fav.getCountry(), fav.getState(), fav.getCity(),
    					fav.getAirQualityIndex(), fav.getTimestamp())).collect(Collectors.toList());
    	userInfoDto.setFavourites(favourites);
    	return userInfoDto;
    }
    
    private boolean checkValue (String value) {
    	return value != null && !"".equalsIgnoreCase(value.trim());
    }
}
