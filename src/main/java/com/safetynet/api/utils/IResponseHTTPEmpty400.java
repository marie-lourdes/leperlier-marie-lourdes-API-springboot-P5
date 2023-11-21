package com.safetynet.api.utils;

import org.springframework.http.ResponseEntity;

public interface IResponseHTTPEmpty400<T>{
	 ResponseEntity<T> returnResponseEntityEmptyAndCode400();
}
