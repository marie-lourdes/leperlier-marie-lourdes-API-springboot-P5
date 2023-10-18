package com.safetynet.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty {
	
	default ResponseEntity <?> returnResponseEntityEmptyAndCode404() {
		ResponseEntity <?>responseEmpty = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new String(" ") );
		return responseEmpty;
	}
}
