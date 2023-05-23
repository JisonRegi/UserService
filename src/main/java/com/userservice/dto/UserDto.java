package com.userservice.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@Email(message = "Enter a valid Email")
	@NotBlank(message = "Email required")
	private String email;

	@NotBlank(message = "Contact number required")
	private String contactNo;

	@NotBlank(message = "Role required")
	private String role;

	private List<AddressDto> address;

}
