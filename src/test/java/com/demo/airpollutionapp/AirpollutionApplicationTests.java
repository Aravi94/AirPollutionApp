package com.demo.airpollutionapp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.demo.airpollutionapp.controller.AppController;
import com.demo.airpollutionapp.repository.UserRepository;
import com.demo.airpollutionapp.service.AppService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class AirpollutionApplicationTests {
	
	/*
	 * @Autowired MockMvc mockMvc;
	 * 
	 * @Autowired ObjectMapper mapper;
	 */
	@Autowired
	AppController appController;
	
	@Autowired
	AppService appService;
	
	@MockBean
	UserRepository userRepository;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(appController, notNullValue());
		assertThat(appService, notNullValue());
		assertThat(userRepository, notNullValue());
	}

}
