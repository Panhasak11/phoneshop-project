package com.nha.java.coding.phone.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiException extends RuntimeException{
	
	private final HttpStatus status;
	private final String message;
}
