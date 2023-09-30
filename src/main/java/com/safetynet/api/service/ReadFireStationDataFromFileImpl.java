package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;

@Component
public class ReadFireStationDataFromFileImpl implements IDatasFileReader {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonFireStations;
	private List<FireStation> listOfFireStations;

	@Override
	public List<FireStation> readFile() throws IOException {
		InputStream is = new FileInputStream(path);
		JsonReader jsonReader = Json.createReader(is);
		 listOfFireStations = new LinkedList<FireStation>();
		
		// get JsonObject from JsonReader
		JsonObject datasJsonFireStationsObject = jsonReader.readObject();
		datasJsonFireStations = datasJsonFireStationsObject.getJsonArray("firestations");
		System.out.println("all datas Json Person parsed with its arrays:" + datasJsonFireStations);
		
		jsonReader.close();
		is.close();
		
		//create list linked of fireStations
		for( JsonValue elem : datasJsonFireStations) {
			FireStation fireStation =new FireStation(); 
			fireStation.setStationNumber( elem.asJsonObject().getString("stationNumber"));
			fireStation.setAddress( elem.asJsonObject().getString("address"));
			
			
			 listOfFireStations.add(fireStation);
			System.out.println("element of fireStations"+elem.asJsonObject());
			
		}
		// listOfFireStations.add();
		System.out.println("list of fireStations"+ listOfFireStations);

		return  listOfFireStations ;
		// return datasJsonPersonParsed ;
	}
}