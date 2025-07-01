package com.nha.java.coding.phone.service;

import com.nha.java.coding.phone.dto.LoginRequest;
import com.nha.java.coding.phone.dto.SignupRequest;

public interface AuthService {

	String createUser(SignupRequest signUpRequest);

	String authenticateUser(LoginRequest loginRequest);
}
