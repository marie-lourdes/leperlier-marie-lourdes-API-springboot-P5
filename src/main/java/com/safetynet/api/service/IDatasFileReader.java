package com.safetynet.api.service;

import java.io.FileNotFoundException;

import org.springframework.boot.configurationprocessor.json.JSONArray;

public interface IDatasFileReader {
	void readFile() throws FileNotFoundException ;

	
}
