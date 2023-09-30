package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
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
	public JsonArray readFile() throws IOException {
		InputStream is = new FileInputStream(path);
		 JsonReader jsonReader = Json.createReader(is);


	        // get JsonObject from JsonReader
	        JsonObject jsonObject = jsonReader.readObject();
	        System.out.println("all datas Json Person parsed with its arrays:" + jsonObject.getJsonArray("persons"));
	        jsonReader.close();
	        is.close();
	return jsonObject.getJsonArray("persons");
		//return datasJsonPersonParsed ;
	}
}
