package com.nha.java.coding.phone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nha.java.coding.phone.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String role);
}
