package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingInfoOfResidentOfStationNumberImpl implements ISearchingInfoOfResident {
	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;
	
	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;
		
	private List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();
	@Override
	public List<Map<String, String>> searchInfoOfResident(String request) {
		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		List<Person> persons = personService.getAllPersons();
		
		fireStationFoundByStationNumber = fireStationService.getFireStationsById(request);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
		
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();

			for (Person person : persons) {
				if (person.getAddress().equals(itrFireStation.getAddress())) {
					Map<String, String> residentOfStationNumber = new HashMap<String, String>();
					residentOfStationNumber.put("firstName", person.getFirstName());
					residentOfStationNumber.put("lastName", person.getLastName());
					residentOfStationNumber.put("address", person.getAddress());
					residentOfStationNumber.put("phone", person.getPhone());
					residentOfStationNumber.put("age", calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
					System.out.println(" personFactory" + residentOfStationNumber);
					listOfResidentOfStationNumber.add(residentOfStationNumber);
				}

			}
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}
	
	
}
