package com.safetynet.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty {

	default ResponseEntity<Object> returnResponseEntityEmptyAndCode404() {
		ResponseEntity<Object> responseEmpty = ResponseEntity.status(HttpStatus.NOT_FOUND).body(" ");
		return responseEmpty;
	}		
}
