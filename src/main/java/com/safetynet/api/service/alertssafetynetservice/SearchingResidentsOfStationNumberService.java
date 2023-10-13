package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class SearchingResidentsOfStationNumberService {
	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;
	
	public List<Map<String, String>> getResidentsOfStationNumber(String stationNumber) {
		List<FireStation> fireStationFoundByStationNumber = fireStationService.getFireStationsById(stationNumber);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
	
		List<Map<String,String>> listOfResidentOfStationNumber = new ArrayList<Map<String,String>>();
		List<Person> persons = personService.getAllPersons();

		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
		
			for (Person person : persons) {
				if (person.getAddress().equals(itrFireStation.getAddress())) {
					Map<String,String> residentOfStationNumber = new HashMap<String,String>();
					//Person personFactory =new Person(person.getFirstName(),person.getLastName(),  person.getAddress(), person.getPhone());
					residentOfStationNumber.put("firstName", person.getFirstName());
					residentOfStationNumber.put("lastName", person.getLastName());
					residentOfStationNumber.put("address", person.getAddress());
					residentOfStationNumber.put("phone", person.getPhone());
					System.out.println(" personFactory" + residentOfStationNumber);
					listOfResidentOfStationNumber.add(residentOfStationNumber);
				}
				
			}
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

	/*
	 * public List<Person> getFireStationByStationNumber(String stationNumber) { }
	 */

	public Optional<Person> getPersonByAddress(String address) {
		personService.getOnePersonByAddress(address);
		return null;
	}

	public Person getAgeOfPerson() {
		// getBirthDateOfPersonWithMedicalRecord()
		// calculateAgeOfPerson();
		return null;
	}

}
