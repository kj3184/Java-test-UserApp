package com.kunal.digib.app.api.users.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kunal.digib.app.api.users.data.UserEntity;
import com.kunal.digib.app.api.users.data.UsersRepository;
import com.kunal.digib.app.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	Environment environment;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			Environment environment)
	{
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
	}
 
	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		usersRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
 
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByUserName(username);
		
		if(userEntity == null) throw new UsernameNotFoundException(username);	
		
		return new User(userEntity.getUserName(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}



	@Override
	public UserDto getUserProfileByUserName(String userId) {
		
        UserEntity userEntity = usersRepository.findByUserName(userId);     
        if(userEntity == null) throw new UsernameNotFoundException("User not found");
        
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
       
		
		return userDto;
	}
	
	
	

}
