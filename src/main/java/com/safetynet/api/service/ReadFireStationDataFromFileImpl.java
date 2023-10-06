package com.safetynet.api.service;

import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;

@Component
public class ReadFireStationDataFromFileImpl implements IDatasFileReader<FireStation> {
	private JsonArray datasJsonFireStations;
	private List<FireStation> listOfFireStations;

	@Override
	public List<FireStation> readFile() throws IOException {
		listOfFireStations = new LinkedList<FireStation>();

		// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
		// creer une methode dans l Interface IDatasFileReader pour generer JsonArray et
		// eviter de repeter de tous les class impl le path
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
		// listOfFireStations.add();
		System.out.println("list of fireStations" + listOfFireStations);

		return listOfFireStations;
		// return datasJsonPersonParsed ;
	}

}