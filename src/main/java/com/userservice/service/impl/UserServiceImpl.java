package com.userservice.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.userservice.dto.UserDto;
import com.userservice.entity.Users;
import com.userservice.exception.ResourceAlreadyFoundException;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import com.userservice.utils.Constants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	final UserRepository userRepository;

	final ModelMapper modelMapper;
	
	final RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.key}")
	private String key;

	@Override
	public String registerUser(UserDto userRequest) {
		Optional<Users> user = userRepository.findByEmail(userRequest.getEmail());
		if (user.isPresent()) {
			throw new ResourceAlreadyFoundException("User with " + userRequest.getEmail() + " already exists");
		}
		Users userCreated = userRepository.save(mapToEntity(userRequest));
		log.info("User " + userRequest.getEmail() + " Created");
		rabbitTemplate.convertAndSend(exchange,key,mapToDto(userCreated));
		return Constants.USER_SAVED.getValue();
	}

	@Override
	public UserDto getUserByEmail(String email) {
		Optional<Users> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		return mapToDto(user.get());
	}

	@Override
	public String updateUser(UserDto userRequest) {
		Optional<Users> user = userRepository.findByEmail(userRequest.getEmail());
		
		if (userRequest.getContactNo() != null && !userRequest.getContactNo().isEmpty()) {
			user.get().setContactNo(userRequest.getContactNo());
		}
		if (userRequest.getAddress() != null && !userRequest.getAddress().isEmpty()) {
			Users userUpdateRequest = mapToEntity(userRequest);
			user.get().setAddress(userUpdateRequest.getAddress());
		}
		userRepository.save(user.get());
		log.info("User " + userRequest.getEmail() + " Updated");
		return Constants.USER_UPDATED.getValue();
	}

	@Override
	public String deleteUser(String email) {
		Optional<Users> user = userRepository.findByEmail(email);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		userRepository.deleteById(user.get().getId());
		log.info("User " + email + " Deleted");
		return Constants.USER_DELETED.getValue();
	}

	public Users mapToEntity(UserDto userDto) {
		return modelMapper.map(userDto, Users.class);
	}

	public UserDto mapToDto(Users users) {
		return modelMapper.map(users, UserDto.class);
	}

}
