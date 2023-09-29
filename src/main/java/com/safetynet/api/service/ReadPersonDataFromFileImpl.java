package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
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
				int val = 0;
				while (parser.hasNext()) {
					event = parser.next();
					if (event == JsonParser.Event.KEY_NAME) {
						String key = toString();
						event = parser.next();
						if (key.equals("persons")) {
						
							phones = parser.getArray();
						}

						System.out.println(phones + ":" + ++val);
					}
				}

			}
		}
	}
}
