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
	private JsonArray datasJsonParsed;

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

			// get all array of object Json file
			if (event == JsonParser.Event.START_ARRAY) {
				datasJsonParsed = parser.getArray();

				System.out.println("all datas of file json parsed with its arrays:" + datasJsonParsed);
				// event = parser.next();

				while (parser.hasNext()) {

					event =parser.next();

					if (event == JsonParser.Event.KEY_NAME) {
						String key = parser.getString();
						// parser.next();
						System.out.println("key:" + parser.getString());
					}
				}

			}

		}
	}
}
