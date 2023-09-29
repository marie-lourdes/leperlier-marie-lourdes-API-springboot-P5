package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

public class ReadPersonDataFromFileImpl implements IDatasFileReader {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	 private JsonArray phones;
	@SuppressWarnings("rawtypes")
	@Override
	public void readFile() throws FileNotFoundException {
		InputStream is = new FileInputStream(path);
		  
		JsonParserFactory factory = Json.createParserFactory(null);
		JsonParser parser = factory.createParser(is, StandardCharsets.UTF_8);
		
		if (!parser.hasNext() && parser.next() != JsonParser.Event.START_OBJECT) {
			return;
		}

		while (parser.hasNext()) {

			Event event = parser.next();

			// starting Array Person
			if (event == JsonParser.Event.START_ARRAY) {
				 while (parser.hasNext()) {

	                    event = parser.next();
	                    phones =( JsonArray ) parser.getObjectStream().filter(e->e.getKey().equals("phone"))
	                    	                             .map(e->e.getValue())
	                    	                             .findFirst()
	                    	                             .get();
				 }

			}
		}
		
		//System.out.println(phones);
	}

}
