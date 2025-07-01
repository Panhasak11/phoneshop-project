package com.nha.java.coding.phone.service;

import java.util.Optional;

import com.nha.java.coding.phone.config.security.AuthUser;


public interface UserService {
	
	Optional<AuthUser> findUserByUsername(String username);
}
