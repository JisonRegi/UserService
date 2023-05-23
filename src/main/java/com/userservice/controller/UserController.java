package com.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.UserDto;
import com.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
	
	final public UserService userService;
	
	@PostMapping("register")
	public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userRequest) {
		return new ResponseEntity<String>(userService.registerUser(userRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("get/{email}")
	public ResponseEntity<UserDto> getUser(@PathVariable String email) {
		return new ResponseEntity<UserDto>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{email}")
	public ResponseEntity<String> deleteUser(@PathVariable String email) {
		return new ResponseEntity<String>(userService.deleteUser(email),HttpStatus.OK);
	}
	
	
	@PatchMapping("update")
	public ResponseEntity<String> updateuser(@RequestBody UserDto userRequest) {
		return new ResponseEntity<String>(userService.updateUser(userRequest),HttpStatus.OK);
	}

}
