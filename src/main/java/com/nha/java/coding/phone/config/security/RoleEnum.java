package com.nha.java.coding.phone.config.security;

import java.util.Set;

import lombok.Getter;

import static com.nha.java.coding.phone.config.security.PermissionEnum.*;

@Getter
public enum RoleEnum {

	ADMIN(Set.of(BRAND_WRITE,BRAND_READ,MODEL_WRITE,MODEL_READ)),
	SALE(Set.of(BRAND_READ,MODEL_READ)),
	USER(Set.of());
	
	private Set<PermissionEnum> permission;

	private RoleEnum(Set<PermissionEnum> permission) {
		this.permission = permission;
	}
	
	
}
