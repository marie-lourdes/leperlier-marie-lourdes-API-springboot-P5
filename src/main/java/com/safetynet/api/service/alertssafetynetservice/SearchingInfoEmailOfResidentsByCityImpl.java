package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingInfoEmailOfResidentsByCityImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoEmailOfResidentsByCityImpl.class);

	@Autowired
	private PersonService personService;

	@Override
	public List<Map<String, String>> searchInfoOfResident(String city) {
		log.debug("Searching emails of residents by city");

		List<Person> residentsFoundByCity = new ArrayList<Person>();
		List<Map<String, String>> listOfEmailsResidentsFoundByCity = new ArrayList<Map<String, String>>();

		try {
			residentsFoundByCity = personService.getPersonsByCity(city);
			for (Person person : residentsFoundByCity) {
				Map<String, String> residentFoundByCity = new HashMap<String, String>();
				residentFoundByCity.put("email", person.getEmail());
				if (!listOfEmailsResidentsFoundByCity.contains(residentFoundByCity)) {
					listOfEmailsResidentsFoundByCity.add(residentFoundByCity);
					log.debug(" Emails of residents by city successfully retrieved : {}",
							listOfEmailsResidentsFoundByCity);
				}
			}
		} catch (Exception e) {
			log.error("An error has occured in searching  full info  emails of residents of the city");
		}

		return listOfEmailsResidentsFoundByCity;
	}
}
