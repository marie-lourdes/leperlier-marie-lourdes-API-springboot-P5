package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingInfoOfResidentOfStationNumberImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoOfResidentOfStationNumberImpl.class);
	
	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;

	private List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();
	private List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();

	@Override
	public List<Map<String, String>> searchInfoOfResident(String stationNumber) {
		List<Person> persons = personService.getAllPersons();
		try {
			fireStationFoundByStationNumber = fireStationService.getFireStationsById(stationNumber);
			Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();

			while (itrFireStations.hasNext()) {
				FireStation itrFireStation = itrFireStations.next();

				for (Person person : persons) {
					if (person.getAddress().equals(itrFireStation.getAddress())) {
						Map<String, String> residentOfStationNumber = new LinkedHashMap<String, String>();
						residentOfStationNumber.put("firstName", person.getFirstName());
						residentOfStationNumber.put("lastName", person.getLastName());
						residentOfStationNumber.put("address", person.getAddress());
						residentOfStationNumber.put("phone", person.getPhone());
						residentOfStationNumber.put("age",
								calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
						System.out.println(" residentOfStationNumber" + residentOfStationNumber);
						listOfResidentOfStationNumber.add(residentOfStationNumber);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("An error has occured in searching info of residents of station number");
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

}
