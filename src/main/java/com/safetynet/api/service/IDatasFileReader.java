package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import javax.json.JsonArray;

import com.safetynet.api.model.Person;

public interface IDatasFileReader<T> {
	List<T>   readFile()  throws IOException ;
	
}
