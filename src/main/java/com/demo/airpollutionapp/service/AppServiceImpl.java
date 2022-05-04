package com.demo.airpollutionapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.demo.airpollutionapp.exception.AirVisualException;
import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.facade.AirVisConfiguration;
import com.demo.airpollutionapp.facade.UserMapper;
import com.demo.airpollutionapp.model.AirVisCityResponse;
import com.demo.airpollutionapp.model.AirVisResponse;
import com.demo.airpollutionapp.model.Data;
import com.demo.airpollutionapp.model.Favourite;
import com.demo.airpollutionapp.model.Pollution;
import com.demo.airpollutionapp.model.UserInfo;
import com.demo.airpollutionapp.model.UserInfoDTO;
import com.demo.airpollutionapp.repository.FavouriteRepository;
import com.demo.airpollutionapp.repository.UserRepository;

@Service
public class AppServiceImpl implements AppService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FavouriteRepository favouriteRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AirVisConfiguration airVisConfiguration;
	
	private UserInfo userInfo;
	private UserInfoDTO userInfoDTO;
	
	public List<UserInfoDTO> getAllUsers() {		
		return userRepository.findAll()
				.stream()
				.map(userMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<UserInfoDTO> getUserById(String userId) throws ResourceNotFoundException {
		userInfo = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));
		userInfoDTO = userMapper.toDto(userInfo);
		return ResponseEntity.ok().body(userInfoDTO);
	}

	@Override
	public List<UserInfoDTO> getUsersByName(String name) throws ResourceNotFoundException {
		StringBuilder str = new StringBuilder("%");
		str.append(name).append("%");
		name = str.toString();
		List<UserInfo> resultList = userRepository.findByFirstNameLikeOrLastNameLike(name, name);
		if(resultList.isEmpty()) {
			throw new ResourceNotFoundException(USER_NAME_NOT_FOUND + name);
		}
		return resultList.stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public UserInfoDTO createUser(UserInfoDTO userInfoInput) {
		userInfo = userMapper.toUser(userInfoInput);
		userInfo.setStatus(USER_NOT_ACTIVE);
		return userMapper.toDto(userRepository.save(userInfo));
	}

	@Override
	public ResponseEntity<UserInfoDTO> updateUser(UserInfoDTO userInfoInput) throws ResourceNotFoundException {
		userInfo = userRepository.findById(userInfoInput.getUserId())
		        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userInfoInput.getUserId()));
		userInfo = userMapper.copyDtoToUser(userInfoInput, userInfo);
		userInfoDTO = userMapper.toDto(userRepository.save(userInfo));
        return ResponseEntity.ok(userInfoDTO);
	}

	@Override
	public Map<String, Boolean> deleteUser(String userId) throws ResourceNotFoundException {
		userInfo = userRepository.findById(userId)
			       .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));
		userRepository.delete(userInfo);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@Override
	public UserInfoDTO activateUser(UserInfoDTO userInfoInput, boolean activeFlag) throws ResourceNotFoundException {
		userInfo = userRepository.findByUserIdAndRole(userInfoInput.getLoginId(), ADMIN_ROLE)
		        .orElseThrow(() -> new ResourceNotFoundException(userInfoInput.getLoginId() + ADMIN_USER_NOT_VALID));
		userInfo = userRepository.findById(userInfoInput.getUserId())
		        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userInfoInput.getUserId()));
		String status = activeFlag ? USER_ACTIVE : USER_NOT_ACTIVE;
		userInfo.setStatus(status);
        return userMapper.toDto(userRepository.save(userInfo));
	}

	@Override
	public List<Data> getAllCountries(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException {
		checkStatus(userInfoInput.getUserId());
		Map<String, String> params = new HashMap<>();
		params.put(API_KEY, airVisConfiguration.getApiKey());
		return callAirVisualAPI(airVisConfiguration.getCountryUrl(), params).getData();
	}

	@Override
	public List<Data> getAllStates(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException {
		checkStatus(userInfoInput.getUserId());
		Map<String, String> params = new HashMap<>();
		params.put(COUNTRY, userInfoInput.getCountry());
		params.put(API_KEY, airVisConfiguration.getApiKey());
		return callAirVisualAPI(airVisConfiguration.getStateUrl(), params).getData();
	}
	
	@Override
	public List<Data> getAllCities(UserInfoDTO userInfoInput) throws AirVisualException, ResourceNotFoundException {
		checkStatus(userInfoInput.getUserId());
		boolean backupKey = false;
		Map<String, String> params = new HashMap<>();
		params.put(API_KEY, airVisConfiguration.getApiKey());
		params.put(COUNTRY, userInfoInput.getCountry());
		params.put(STATE, userInfoInput.getState());
		List<Data> citiesList = callAirVisualAPI(airVisConfiguration.getCityUrl(), params).getData();
		for (Data data:citiesList) {
			params.put(CITY, data.getCity());
			AirVisCityResponse response = callAirVisualCityAPI(airVisConfiguration.getCityInfoUrl(), params);
			String message = response.getData().getMessage();
			if (message != null && (message.contains("CALL_LIMIT_REACHED")
					|| message.contains("TOO_MANY_REQUESTS"))) {
				if (!backupKey) {
					params.put(API_KEY, airVisConfiguration.getCityApiKey());
					backupKey = true;
					response = callAirVisualCityAPI(airVisConfiguration.getCityInfoUrl(), params);
				}
				else {
					break;
				}
			}
			Pollution pollution = response.getData().getCurrent().getPollution();
			data.setAirQualityIndex(pollution.getAqius());
			data.setTimestamp(pollution.getTs());
		}
		return citiesList
				.stream()
				.filter(city -> city.getAirQualityIndex()!= null)
				.collect(Collectors.toList());
	}
	
	@Override
	public Map<String, Boolean> addFavourite(UserInfoDTO userInfoInput)
			throws AirVisualException, ResourceNotFoundException {
		String userId = userInfoInput.getUserId();
		checkStatus(userId);
		Map<String, String> params = new HashMap<>();
		params.put(API_KEY, airVisConfiguration.getFavApiKey());
		params.put(COUNTRY, userInfoInput.getCountry());
		params.put(STATE, userInfoInput.getState());
		params.put(CITY, userInfoInput.getCity());
		Data data = callAirVisualCityAPI(airVisConfiguration.getCityInfoUrl(), params).getData();
		Favourite favourite = userMapper.toFavourite(userMapper.toUser(userInfoInput), data);
		favouriteRepository.findByUserInfoUserIdAndCity(userId, userInfoInput.getCity())
			.ifPresent(fav -> favourite.setIndex(fav.getIndex()));
		favouriteRepository.save(favourite);
		Map<String, Boolean> favResponse = new HashMap<>();
		favResponse.put("Favorite city added", Boolean.TRUE);
		return favResponse;
	}
	
	@Override
	public UserInfoDTO getFavourites(String userId) throws ResourceNotFoundException {
		checkStatus(userId);
		List<Favourite> favouriteList = favouriteRepository.findByUserInfoUserId(userId);
		if(favouriteList.isEmpty()) {
			throw new ResourceNotFoundException(FAV_NOT_FOUND + userId);
		}
		return userMapper.favToDto(favouriteList);
	}
	
	private void checkStatus(String userId) throws ResourceNotFoundException {
		userInfo = userRepository.findByUserIdAndStatus(userId, USER_ACTIVE)
		        .orElseThrow(() -> new ResourceNotFoundException(userId + USER_NOT_VALID));
	}
	
	private AirVisResponse callAirVisualAPI(String url, Map<String, String> params) throws AirVisualException {
		AirVisResponse response = restTemplate.getForObject(url, AirVisResponse.class, params);
		if (response == null) {
			throw new AirVisualException(API_EMPTY_RESPONSE);
		}
		return response;
	}
	
	private AirVisCityResponse callAirVisualCityAPI(String url, Map<String, String> params) throws AirVisualException {
		AirVisCityResponse response = null;
		try {
			response = restTemplate.getForObject(url, AirVisCityResponse.class, params);
			if (response == null) {
				throw new AirVisualException(API_EMPTY_RESPONSE);
			}
		} catch (HttpClientErrorException ex) {
			response = new AirVisCityResponse();
			response.getData().setMessage(ex.getStatusCode().toString());	
		}
		return response;
	}

}
