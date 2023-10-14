package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

public class SearchingInfoOfResidentImpl implements ISearchingInfoOfResident {
	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;
	
	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;
	
	
	private List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();
	@Override
	public List<Map<String, String>> searchInfoOfResident(String request) {
		// Map<String,Integer> mapOfAdultsAndChildOfResidents =new
		// HashMap<String,Integer>();
		fireStationFoundByStationNumber = fireStationService.getFireStationsById(request);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		List<Person> persons = personService.getAllPersons();

		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();

			for (Person person : persons) {
				if (person.getAddress().equals(itrFireStation.getAddress())) {
					Map<String, String> residentOfStationNumber = new HashMap<String, String>();
					// Person personFactory =new Person(person.getFirstName(),person.getLastName(),
					// person.getAddress(), person.getPhone());
					residentOfStationNumber.put("firstName", person.getFirstName());
					residentOfStationNumber.put("lastName", person.getLastName());
					residentOfStationNumber.put("address", person.getAddress());
					residentOfStationNumber.put("phone", person.getPhone());
					try {
						residentOfStationNumber.put("age", calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(" personFactory" + residentOfStationNumber);
					listOfResidentOfStationNumber.add(residentOfStationNumber);
				}

			}
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}
	
	
}
