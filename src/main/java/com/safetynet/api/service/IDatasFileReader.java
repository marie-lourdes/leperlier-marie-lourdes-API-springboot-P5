package com.safetynet.api.service;

import java.io.FileNotFoundException;

import javax.json.JsonArray;
import javax.json.JsonObject;

public interface IDatasFileReader {
	JsonObject  readFile() throws FileNotFoundException ;
	
}
