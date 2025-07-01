package com.nha.java.coding.phone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nha.java.coding.phone.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
}
