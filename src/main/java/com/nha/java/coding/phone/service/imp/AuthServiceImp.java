package com.nha.java.coding.phone.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nha.java.coding.phone.config.security.AuthUser;
import com.nha.java.coding.phone.config.security.JwtUtils;
import com.nha.java.coding.phone.config.security.RoleEnum;
import com.nha.java.coding.phone.dto.LoginRequest;
import com.nha.java.coding.phone.dto.SignupRequest;
import com.nha.java.coding.phone.entity.Role;
import com.nha.java.coding.phone.entity.User;
import com.nha.java.coding.phone.exception.ApiException;
import com.nha.java.coding.phone.repository.RoleRepository;
import com.nha.java.coding.phone.repository.UserRepository;
import com.nha.java.coding.phone.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{

	private final UserRepository userRepository; 
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtils jwtUtils;
	
	@Override
	public String createUser(SignupRequest signUpRequest) {
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "User name is already taken");
		}
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
		}
		
		//create new user account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), 
				passwordEncoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRole = signUpRequest.getRoles();
		
		Set<Role> roles = new HashSet<>();
		List<String> roleName = new ArrayList<>();
		
		if(strRole == null) {
			Role userRole = roleRepository.findByName(RoleEnum.USER.name())
				.orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Role is not found"));
			roles.add(userRole);
			roleName.add("ROLE_" + userRole.getName()); //add
		}else {
			strRole.forEach(role -> {
				Role adminRole = roleRepository.findByName(role)
					.orElseThrow(() ->  new ApiException(HttpStatus.BAD_REQUEST, "Role is not found"));
				roles.add(adminRole);
				
				roleName.add("ROLE_" + adminRole.getName()); //add
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		return jwtUtils.generateJwtToken(signUpRequest.getUsername(),roleName);
	}

	@Override
	public String authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		AuthUser userPrincipal = (AuthUser) authentication.getPrincipal();
		
		System.out.println("User principle ====== " + userPrincipal.getAuthorities());
		
		List<String> roles = userPrincipal.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		
		return jwtUtils.generateJwtToken(userPrincipal.getUsername(),roles);
	}

}
