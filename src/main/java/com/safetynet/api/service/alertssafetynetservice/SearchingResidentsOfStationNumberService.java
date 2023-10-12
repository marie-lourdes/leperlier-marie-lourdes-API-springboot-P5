package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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



	public List<Person> getResidentsOfStationNumber(String stationNumber) {
		List<FireStation> fireStationFoundByStationNumber = fireStationService.getFireStationsById(stationNumber);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
		 List< Person> listOfResidentOfStationNumber = new ArrayList< Person>();
			List<Person>	persons= personService.getAllPersons();
			
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
	            for(Person person:persons) {
	            	if( person.getAddress().equals(itrFireStation.getAddress())) {
	            		 listOfResidentOfStationNumber.add(person);
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
