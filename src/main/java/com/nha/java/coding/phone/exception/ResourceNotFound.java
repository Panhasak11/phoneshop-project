package com.nha.java.coding.phone.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends ApiException{

	public ResourceNotFound(String resourceName, Long id) {
		super(HttpStatus.NOT_FOUND, String.format("%s with id %d not found", resourceName,id));
	}

}
