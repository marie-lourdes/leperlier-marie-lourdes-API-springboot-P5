package com.safetynet.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.api.model.Person;

public interface IResponseHTTPEmpty404 <T>{
	ResponseEntity<T> returnResponseEntityEmptyAndCode404();	
}
