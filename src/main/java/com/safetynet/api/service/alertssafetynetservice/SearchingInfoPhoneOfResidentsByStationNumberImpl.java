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

@Component("InfoPhoneOfResidentsByStationNumber")
public class SearchingInfoPhoneOfResidentsByStationNumberImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoPhoneOfResidentsByStationNumberImpl.class);

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	private PersonService personService;

	@Override
	public List<Map<String, String>> searchInfoOfResident(String stationNumber) {
		log.debug("Searching phones of residents  by  station number ");

		List<Map<String, String>> listOfPhonesOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();

		try {
			List<Person> persons = personService.getAllPersons();
			fireStationFoundByStationNumber = fireStationService.getFireStationsByStationNumber(stationNumber);

			Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
			while (itrFireStations.hasNext()) {
				FireStation itrFireStation = itrFireStations.next();
				for (Person person : persons) {
					if (person.getAddress().equals(itrFireStation.getAddress())) {
						Map<String, String> residentOfStationNumber = new LinkedHashMap<String, String>();
						residentOfStationNumber.put("phone", person.getPhone());
						if (!listOfPhonesOfResidentOfStationNumber.contains(residentOfStationNumber)) {
							listOfPhonesOfResidentOfStationNumber.add(residentOfStationNumber);
							log.debug("Phone retrieved for each resident of station number: {}",
									residentOfStationNumber);
						}
					}
				}
			}

			log.debug("Phones of residents  by  station number successfull retrieved ; {}",
					listOfPhonesOfResidentOfStationNumber);
		} catch (Exception e) {
			log.error("An error has occured in searching phones of residents of the  station number ");
		}

		return listOfPhonesOfResidentOfStationNumber;
	}
}
