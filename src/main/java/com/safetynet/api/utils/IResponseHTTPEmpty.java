package com.safetynet.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty {

	default ResponseEntity<Object> returnResponseEntityEmptyAndCode404() {
		ResponseEntity<Object> responseEmpty = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new String(" "));
		return responseEmpty;
	}	
	default ResponseEntity<?> returnResponseEntityEmptyAndCode500() {
		ResponseEntity<?> responseEmpty = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new String(" "));
		return responseEmpty;
	}
}
