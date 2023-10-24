package com.safetynet.api.service;

import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.safetynet.api.controller.PersonController;
import com.safetynet.api.model.FireStation;

@Component
public class ReadFireStationDataFromFileImpl implements IDatasFileReader<FireStation> {
	private static final Logger log = LogManager.getLogger(ReadFireStationDataFromFileImpl.class);
	private JsonArray datasJsonFireStations;
	private List<FireStation> listOfFireStations;

	@Override
	public List<FireStation> readFile() throws IOException {
		listOfFireStations = new LinkedList<FireStation>();

		// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
		// use method astract  readDataJson from interface generic IDatasFileReader	 
		datasJsonFireStations = readDataJson("firestations");

		// create list linked of fireStations
		for (JsonValue elem : datasJsonFireStations) {
			FireStation fireStation = new FireStation();
			fireStation.setId(elem.asJsonObject().getString("station"));
			fireStation.setStationNumber(elem.asJsonObject().getString("station"));
			fireStation.setAddress(elem.asJsonObject().getString("address"));

			listOfFireStations.add(fireStation);
			System.out.println("element of fireStations" + elem.asJsonObject());
		}
		
		System.out.println("list of fireStations" + listOfFireStations);

		return listOfFireStations;
		
	}

}