package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class PhoneAlertService {
	private static final Logger log = LogManager.getLogger( PhoneAlertService .class);
	
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	

	@Autowired
	FireStationService fireStationService;
	
	@Autowired
	PersonService personService;
	
	private List<Map<String, String>> listOfPhonesOfResidentOfStationNumber =new ArrayList<Map<String, String>>();;
	private List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();
	public List<Map<String, String>> getListOfPhonesOfResidentsOfStationNumber(String stationNumber)
			throws NullPointerException {
		log.debug("Retrieving all phones of residents of firestation");
		try {
		List<Person> persons = personService.getAllPersons();

		fireStationFoundByStationNumber = fireStationService.getFireStationsById(stationNumber);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
		
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();

			for (Person person : persons) {
				if (person.getAddress().equals(itrFireStation.getAddress())) {
					Map<String, String> residentOfStationNumber = new LinkedHashMap<String, String>();
					
					residentOfStationNumber.put("phone", person.getPhone());		
					log.debug("Phone retrieved for each resident of station number: {}",residentOfStationNumber);
					
					listOfPhonesOfResidentOfStationNumber.add(residentOfStationNumber);
				}
			}
		}
		} catch (NullPointerException e) {
			log.error("Failed to retrieve phones of this firestation : {}",stationNumber);
			throw new NullPointerException("Not phone of resident found of this firestation : "+ stationNumber);
		}
		
		log.info("List of phones of residents of firestation retrieved successfully : {}", listOfPhonesOfResidentOfStationNumber);
		return  listOfPhonesOfResidentOfStationNumber;
	}
}
