package com.demo.airpollutionapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.model.UserInfo;
import com.demo.airpollutionapp.model.UserInfoDTO;
import com.demo.airpollutionapp.repository.UserRepository;

@Service
public class AppServiceImpl implements AppService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
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
	public UserInfoDTO activateUser(UserInfoDTO userInfoInput) throws ResourceNotFoundException {
		userInfo = userRepository.findByUserIdAndRole(userInfoInput.getLoginId(), ADMIN_ROLE)
		        .orElseThrow(() -> new ResourceNotFoundException(userInfoInput.getLoginId() + ADMIN_USER_NOT_VALID));
		userInfo = userRepository.findById(userInfoInput.getUserId())
		        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userInfoInput.getUserId()));
		userInfo.setStatus(USER_ACTIVE);
        return userMapper.toDto(userRepository.save(userInfo));
	}

	@Override
	public UserInfoDTO deactivateUser(UserInfoDTO userInfoInput) throws ResourceNotFoundException {
		userInfo = userRepository.findByUserIdAndRole(userInfoInput.getLoginId(), ADMIN_ROLE)
		        .orElseThrow(() -> new ResourceNotFoundException(userInfoInput.getLoginId() + ADMIN_USER_NOT_VALID));
		userInfo = userRepository.findById(userInfoInput.getUserId())
		        .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userInfoInput.getUserId()));
		userInfo.setStatus(USER_ACTIVE);
        return userMapper.toDto(userRepository.save(userInfo));
	}
}
