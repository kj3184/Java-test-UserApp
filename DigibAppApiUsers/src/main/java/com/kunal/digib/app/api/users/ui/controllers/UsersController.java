package com.kunal.digib.app.api.users.ui.controllers;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kunal.digib.app.api.users.service.UsersService;
import com.kunal.digib.app.api.users.shared.UserDto;
import com.kunal.digib.app.users.ui.model.CreateUserRequestModel;
import com.kunal.digib.app.users.ui.model.*;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService usersService;

	@GetMapping("/status/check")
	public String status()
	{
		return "Working on port" + env.getProperty("local.server.port") + ", with token = " + env.getProperty("token.secret");
	}
 
	@PostMapping(value = "/register",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails)
	{
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = usersService.createUser(userDto);
		
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

    @GetMapping(value="/{userName}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userName") String userName) {
       
        UserDto userDto = usersService.getUserProfileByUserName(userName); 
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
        
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
	
	
}
