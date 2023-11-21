package com.safetynet.api.utils;

import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty <T>{
	 ResponseEntity<T> returnResponseEntityEmptyAndCode404(); 

	
}
