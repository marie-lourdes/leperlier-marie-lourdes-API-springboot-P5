package com.safetynet.api.utils;

import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty404 <T>{
	ResponseEntity<T> returnResponseEntityEmptyAndCode404();	
}
