package com.nha.java.coding.phone.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

	@NotNull(message = "Username can't be null")
	private String username;

	@NotNull(message = "Password can't be null")
	private String password;
	
	@NotNull(message = "Email can't be null")
	private String email;
	
	@NotNull(message = "Role can't be null")
	private Set<String> roles;
}
