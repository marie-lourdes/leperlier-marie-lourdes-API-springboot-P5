package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

import org.springframework.stereotype.Component;
@Component
public class ReadPersonDataFromFileImpl implements IDatasFileReader {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonPersonParsed;
	JsonObject dataJson;
	// @SuppressWarnings("rawtypes")
	@Override
	public JsonArray readFile() throws FileNotFoundException {
		InputStream is = new FileInputStream(path);

		JsonParserFactory factory = Json.createParserFactory(null);
		JsonParser parser = factory.createParser(is);

		if (!parser.hasNext() && parser.next() != JsonParser.Event.START_OBJECT) {
			System.exit(-1);
		}
		int i = 0;
		
		while (parser.hasNext() && i <= 2) {
			i++;
			Event event = parser.next();

			// get all array of object Json file
			if (event == JsonParser.Event.START_ARRAY) {
				datasJsonPersonParsed = parser.getArray();
				System.out.println("all datas Json Person parsed with its arrays:" + datasJsonPersonParsed);

			}
			
			//envoit de l objet json complet
			/* dataJson =parser.getObject(); 
			System.out.println("object in array entity"+dataJson);*/
		}
	
		return datasJsonPersonParsed ;
	}
}
