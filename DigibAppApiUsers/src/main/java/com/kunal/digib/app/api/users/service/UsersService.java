package com.kunal.digib.app.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kunal.digib.app.api.users.shared.UserDto;

public interface UsersService extends UserDetailsService{
	UserDto createUser(UserDto userDetails);
	UserDto getUserProfileByUserName(String userName);
}
