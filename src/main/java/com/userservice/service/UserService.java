package com.userservice.service;

import com.userservice.dto.UserDto;

public interface UserService {
	
	public String registerUser(UserDto userRequest);
	
	public UserDto getUserByEmail(String email);
	
	public String updateUser(UserDto userRequest);
	
	public String deleteUser(String email);

}
