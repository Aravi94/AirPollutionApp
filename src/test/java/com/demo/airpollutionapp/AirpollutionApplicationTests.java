package com.demo.airpollutionapp;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.demo.airpollutionapp.controller.AppController;
//import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.facade.UserMapper;
import com.demo.airpollutionapp.model.Data;
import com.demo.airpollutionapp.model.Favourite;
import com.demo.airpollutionapp.model.FavouriteDTO;
import com.demo.airpollutionapp.model.UserInfo;
import com.demo.airpollutionapp.model.UserInfoDTO;
import com.demo.airpollutionapp.repository.FavouriteRepository;
import com.demo.airpollutionapp.repository.UserRepository;
import com.demo.airpollutionapp.service.AppService;
//import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AirpollutionApplicationTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	AppController appController;
	
	@Autowired
	AppService appService;
	
	@Autowired
	UserMapper userMapper;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	FavouriteRepository favouriteRepository;
	
	UserInfoDTO userDto1 = new UserInfoDTO("Aravi94", "Aravindh Raj", "Venugopal", "aravi94.ofc@gmail.com", "admin", "inactive");
	UserInfoDTO userDto2 = new UserInfoDTO("Dinesh89", "Dinesh", "Sethi", "dinesh.sethi@gmail.com", "member", "inactive");
	UserInfoDTO userDto3 = new UserInfoDTO("Vignesh89", "Vignesh", "Viswalingam", "vignesh.viswalingam@gmail.com", "member", "inactive");

	UserInfo user1 = new UserInfo("Aravi94", "Aravindh Raj", "Venugopal", "aravi94.ofc@gmail.com", "admin");
	UserInfo user2 = new UserInfo("Dinesh89", "Dinesh", "Sethi", "dinesh.sethi@gmail.com", "member");
	UserInfo user3 = new UserInfo("Vignesh89", "Vignesh", "Viswalingam", "vignesh.viswalingam@gmail.com", "member");

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(appController, notNullValue());
		assertThat(appService, notNullValue());
		assertThat(userRepository, notNullValue());
		assertThat(favouriteRepository, notNullValue());
	}
	
	@Test
	@Order(2)
	void getAllUsers() throws Exception {
	    List<UserInfo> records = new ArrayList<>(Arrays.asList(user1, user2, user3));
	    
	    Mockito.when(userRepository.findAll()).thenReturn(records);
	    
	    List<UserInfoDTO> response = appService.getAllUsers();
	    assertThat(response, notNullValue());
	}
	
	@Test
	@Order(2)
	void getUserById() throws Exception {
	    Mockito.when(userRepository.findById(user1.getUserId())).thenReturn(java.util.Optional.of(user1));	    
	    ResponseEntity<UserInfoDTO> response = appService.getUserById(user1.getUserId());
	    assertEquals("Aravindh Raj", response.getBody().getFirstName());
	}
	
	@Test
	@Order(3)
	void getUsersByName() throws Exception {
		List<UserInfo> records = new ArrayList<>(Arrays.asList(user2));
		StringBuilder str = new StringBuilder("%");
		str.append(user2.getFirstName()).append("%");
		String name = str.toString(); 
	    Mockito.when(userRepository.findByFirstNameLikeOrLastNameLike(name, name))
	    .thenReturn(records);
	    
	    List<UserInfoDTO> response = appService.getUsersByName(user2.getFirstName());
	    assertEquals("Dinesh", response.get(0).getFirstName());
	}
	
	@Test
	@Order(4)
	void createUser() throws Exception {
		UserInfo user = new UserInfo("Ramesh95", "Ramesh", "Srini", "ramesh.srini@gmail.com", "member");
	    Mockito.when(userRepository.save(Mockito.any(UserInfo.class))).thenReturn(user);
	    UserInfoDTO response = appService.createUser(userMapper.toDto(user));
	    assertEquals("Ramesh", response.getFirstName());
	}
	
	@Test
	@Order(5)
	void updateUser() throws Exception {
		UserInfo user = new UserInfo("Ramesh95", "Ramesh", "Srini", "ramesh.srini@gmail.com", "member");
		Mockito.when(userRepository.findById("Ramesh95")).thenReturn(java.util.Optional.of(user3));
	    Mockito.when(userRepository.save(Mockito.any(UserInfo.class))).thenReturn(user);
	    ResponseEntity<UserInfoDTO> response = appService.updateUser(userMapper.toDto(user));
	    assertEquals("Srini", response.getBody().getLastName());
	}
	
	@Test
	@Order(6)
	void deleteUser() throws Exception {
		Mockito.when(userRepository.findById(user2.getUserId())).thenReturn(java.util.Optional.of(user2));
		Map<String, Boolean> response = appService.deleteUser(user2.getUserId());
	    assertTrue(response.get("deleted"));
	}
	
	@Test
	@Order(7)
	void activateUser() throws Exception {
		UserInfo user = new UserInfo("Ramesh95", "Ramesh", "Srini", "ramesh.srini@gmail.com", "member");
		user.setStatus("active");
		UserInfoDTO userDto = userMapper.toDto(user);
		userDto.setLoginId("Ramesh95");
		Mockito.when(userRepository.findByUserIdAndRole("Ramesh95", "admin")).thenReturn(java.util.Optional.of(user1));
		Mockito.when(userRepository.findById("Ramesh95")).thenReturn(java.util.Optional.of(user));
	    Mockito.when(userRepository.save(Mockito.any(UserInfo.class))).thenReturn(user);
	    UserInfoDTO response = appService.activateUser(userDto, true);
	    assertEquals("active", response.getStatus());
	    userDto.setLoginId("Aravi94");
	    //Assertions.assertThrowsExactly(ResourceNotFoundException.class, appService.activateUser(userDto, true));
	    
	    user.setStatus("inactive");
	    userDto = userMapper.toDto(user);
		userDto.setLoginId("Ramesh95");
	    response = appService.activateUser(userDto, false);
	    assertEquals("inactive", response.getStatus());
	}
	
	@Test
	@Order(8)
	void getAllCountries() throws Exception {
		Mockito.when(userRepository.findByUserIdAndStatus("Aravi94", "active")).thenReturn(java.util.Optional.of(user1));
	    List<Data> response = appService.getAllCountries(userMapper.toDto(user1));
	    assertTrue(!response.isEmpty());
	}
	
	@Test
	@Order(9)
	void getAllStates() throws Exception {
		UserInfoDTO userDto = userMapper.toDto(user1);
		userDto.setCountry("India");
		Mockito.when(userRepository.findByUserIdAndStatus("Aravi94", "active")).thenReturn(java.util.Optional.of(user1));
	    List<Data> response = appService.getAllStates(userDto);
	    assertTrue(!response.isEmpty());
	}
	
	@Test
	@Order(10)
	void getAllCities() throws Exception {
		UserInfoDTO userDto = userMapper.toDto(user1);
		userDto.setCountry("India");
		userDto.setState("Kerala");
		Mockito.when(userRepository.findByUserIdAndStatus("Aravi94", "active")).thenReturn(java.util.Optional.of(user1));
	    List<Data> response = appService.getAllCities(userDto);
	    assertTrue(!response.isEmpty());
	}
	
	@Test
	@Order(11)
	void addFavourite() throws Exception {
		UserInfoDTO userDto = userMapper.toDto(user1);
		userDto.setCountry("India");
		userDto.setState("Kerala");
		userDto.setCity("Cochin");
		Mockito.when(userRepository.findByUserIdAndStatus("Aravi94", "active")).thenReturn(java.util.Optional.of(user1));
		Map<String, Boolean> response = appService.addFavourite(userDto);
	    assertTrue(response.get("Favorite city added"));
	}
	
	@Test
	@Order(12)
	void getFavourites() throws Exception {
		Favourite fav = new Favourite("India", "Tamil Nadu", "Chennai");
		//FavouriteDTO favDto = new FavouriteDTO("India", "Tamil Nadu", "Chennai", "84", "2022-05-05T13:00:00.000Z");
		fav.setUserInfo(user1);
		List<Favourite> records = new ArrayList<>(Arrays.asList(fav));
		Mockito.when(userRepository.findByUserIdAndStatus("Aravi94", "active")).thenReturn(java.util.Optional.of(user1));
		Mockito.when(favouriteRepository.findByUserInfoUserId("Aravi94")).thenReturn(records);
		UserInfoDTO response = appService.getFavourites("Aravi94");
		assertEquals("Chennai", response.getFavourites().get(0).getCity());
	}
	
}
