package com.demo.airpollutionapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.airpollutionapp.exception.ResourceNotFoundException;
import com.demo.airpollutionapp.model.UserInfoDTO;
import com.demo.airpollutionapp.service.AppService;

@RestController
@RequestMapping("/airpollution/api/v1")
public class AppController implements ApplicationContextAware {
	
	@Autowired
	private AppService appService;
	
	private ApplicationContext context;
	
	@GetMapping("/users")
	public List<UserInfoDTO> getAllUsers() {
		return appService.getAllUsers();
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserInfoDTO> getUserById(@PathVariable(value="userId") String userId) 
			throws ResourceNotFoundException {
		return appService.getUserById(userId);
	}
	
	@GetMapping("/user/name/{name}")
	public List<UserInfoDTO> getUsersByName(@PathVariable(value="name") String name) 
			throws ResourceNotFoundException {
		return appService.getUsersByName(name);
	}
	
	@PostMapping("/user")
	public UserInfoDTO createUser (@RequestBody UserInfoDTO userInput) {
		return appService.createUser(userInput);
	}
	
	@PutMapping("/user")
	public ResponseEntity<UserInfoDTO> updateUser(@RequestBody UserInfoDTO userInfoInput) 
			throws ResourceNotFoundException {
        return appService.updateUser(userInfoInput);
	}
	
	@DeleteMapping("/user/{userId}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "userId") String userId) 
			throws ResourceNotFoundException {
		return appService.deleteUser(userId);
	}
	
	@PostMapping("/user/activate")
	public UserInfoDTO activateUser (@RequestBody UserInfoDTO userInput) 
			throws ResourceNotFoundException {
		return appService.activateUser(userInput);
	}
	
	@PostMapping("/user/deactivate")
	public UserInfoDTO deactivateUser (@RequestBody UserInfoDTO userInput) 
			throws ResourceNotFoundException {
		return appService.deactivateUser(userInput);
	}
	
	@PostMapping("/shutdownContext")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
