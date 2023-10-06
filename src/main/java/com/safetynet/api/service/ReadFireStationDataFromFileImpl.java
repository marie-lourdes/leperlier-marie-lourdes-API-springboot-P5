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

@Component
public class ReadFireStationDataFromFileImpl implements IDatasFileReader <FireStation>{
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonFireStations;
	private List<FireStation> listOfFireStations;

	@Override
	public List<FireStation> readFile() throws IOException {
		InputStream is = new FileInputStream(path);
		JsonReader jsonReader = Json.createReader(is);
		 listOfFireStations = new LinkedList<FireStation>();
		
		// get JsonObject from JsonReader
		 //creer une methode json reader dans l Interface IDatasFileReader  pour generer  Jsonobjet et et eviter de repeter de tous les class impl  le path
		JsonObject datasJsonFireStationsObject = jsonReader.readObject();
		datasJsonFireStations = datasJsonFireStationsObject.getJsonArray("firestations");
		System.out.println("all datas Json FireStation parsed with its arrays:" + datasJsonFireStations);
		
		jsonReader.close();
		is.close();
		
		//create list linked of fireStations
		for( JsonValue elem : datasJsonFireStations) {
			int index = 1;
			FireStation fireStation =new FireStation(); 
			fireStation.setId(elem.asJsonObject().getString("station")  );
			fireStation.setStationNumber( elem.asJsonObject().getString("station"));
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