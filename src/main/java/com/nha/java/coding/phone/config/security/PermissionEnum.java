package com.nha.java.coding.phone.config.security;

import java.util.Set;

import lombok.Getter;

@Getter
public enum PermissionEnum {

	
	
	BRAND_WRITE("brand:write"),
	BRAND_READ("brand:read"),
	MODEL_WRITE("model:write"),
	MODEL_READ("model:read");
	
	private String description;

	PermissionEnum(String des) {
		this.description = des;
	}

}
