package com.safetynet.api.service;

import java.io.IOException;

import javax.json.JsonArray;

public interface IDatasFileReader {
	JsonArray  readFile()  throws IOException ;
	
}
