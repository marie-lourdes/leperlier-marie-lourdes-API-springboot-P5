package com.safetynet.api.service;

import java.io.FileNotFoundException;

import javax.json.JsonArray;

public interface IDatasFileReader {
	JsonArray readFile() throws FileNotFoundException ;

	
}
