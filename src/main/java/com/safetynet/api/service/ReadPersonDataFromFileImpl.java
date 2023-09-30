package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.springframework.stereotype.Component;

@Component
public class ReadPersonDataFromFileImpl implements IDatasFileReader {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonPersonParsed;
	
	//JsonObject dataJson;
	// @SuppressWarnings("rawtypes")
	
	@Override
	public JsonObject readFile() throws FileNotFoundException {
		InputStream is = new FileInputStream(path);
		 JsonReader jsonReader = Json.createReader(is);


	        // get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	return jsonObject;
		//return datasJsonPersonParsed ;
	}
}
