package com.safetynet.api.repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;

@Component
public class ReadFireStationDataFromFileImpl implements IDatasFileReader<FireStation> {
	private static final Logger log = LogManager.getLogger(ReadFireStationDataFromFileImpl.class);
	
	private JsonArray datasJsonFireStations;
	private List<FireStation> listOfFireStations;

	@Override
	public List<FireStation> readFile() throws IOException {
		listOfFireStations = new LinkedList<FireStation>();
		
		try {
			// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
			// use method astract readDataJson from interface generic IDatasFileReader
			datasJsonFireStations = readDataJson("firestations");

			// create list linked of fireStations
			for (JsonValue elem : datasJsonFireStations) {
				FireStation fireStation = new FireStation();
				fireStation.setStationNumber(elem.asJsonObject().getString("station"));
				fireStation.setAddress(elem.asJsonObject().getString("address"));

				listOfFireStations.add(fireStation);
			}
		} catch (ClassCastException e) {
			log.error(e.getMessage());
		} catch (NullPointerException e) {
			log.error("Missing datas firestations from file Json");
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		log.debug("List of firestations parsed from Json {}", listOfFireStations);
		return listOfFireStations;
	}
}