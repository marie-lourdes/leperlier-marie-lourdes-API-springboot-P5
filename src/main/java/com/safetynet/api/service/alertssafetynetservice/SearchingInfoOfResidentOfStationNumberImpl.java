package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;

@Component("InfoOfResidentOfStationNumber")
public class SearchingInfoOfResidentOfStationNumberImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoOfResidentOfStationNumberImpl.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	@Qualifier("CalculatorAgeOfResident")
	private ICalculatorAge calculatorAgeOfResident;

	@Override
	public List<Map<String, String>> searchInfoOfResident(String stationNumber) {
		log.debug("Searching info of residents by station number");

		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();
		List<Person> persons = personService.getAllPersons();

		try {
			fireStationFoundByStationNumber = fireStationService.getFireStationsByStationNumber(stationNumber);
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
						String age = calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString();

						if ("0".equals(age)) {
							log.error("Age not provided for this resident {} {}",
									residentOfStationNumber.get("firstName"), residentOfStationNumber.get("lastName"));
							residentOfStationNumber.put("age", " error! ");
						} else {
							residentOfStationNumber.put("age", age);
						}

						listOfResidentOfStationNumber.add(residentOfStationNumber);
					}
				}
			}

			log.debug(" Info of residents by station number successfully retrieved : {}",
					listOfResidentOfStationNumber);
		} catch (Exception e) {
			log.error("An error has occured in searching info of residents of station number");
		}

		return listOfResidentOfStationNumber;
	}

}
