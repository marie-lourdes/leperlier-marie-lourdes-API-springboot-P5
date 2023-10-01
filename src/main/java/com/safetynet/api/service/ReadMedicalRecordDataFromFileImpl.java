package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.json.stream.JsonParser.Event;

import org.springframework.stereotype.Component;

/*@Component
public class ReadMedicalRecordDataFromFileImpl implements IDatasFileReader {
private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonMedicalRecordParsed;

	@Override
	public JsonArray readFile() throws FileNotFoundException {
		InputStream is = new FileInputStream(path);

		JsonParserFactory factory = Json.createParserFactory(null);
		JsonParser parser = factory.createParser(is);

		if (!parser.hasNext() && parser.next() != JsonParser.Event.START_OBJECT) {
			System.exit(-1);
		}
		
		int i = 0;
		while (parser.hasNext() && i <= 6) {
			++i;
		/*	if(i <4) {
				
				break;
			}*/
			
/*			Event event = parser.next();

			// get all array of object Json file
			if (event == JsonParser.Event.START_ARRAY) {
				datasJsonMedicalRecordParsed= parser.getArray();
				System.out.println("all datas Json medicalRecords parsed with its arrays:" + datasJsonMedicalRecordParsed);

			}
			
		}
	
		return datasJsonMedicalRecordParsed;
	}
}*/