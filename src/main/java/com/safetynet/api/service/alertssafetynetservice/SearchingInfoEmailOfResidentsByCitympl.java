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
public class SearchingInfoEmailOfResidentsByCitympl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoEmailOfResidentsByCitympl.class);

	@Autowired
	PersonService personService;

	private List<Person> residentsFoundByCity = new ArrayList<Person>();

	@Override
	public List<Map<String, String>> searchInfoOfResident(String city) {

		List<Map<String, String>> listOfResidentsFoundByCity = new ArrayList<Map<String, String>>();
		try {
			residentsFoundByCity = personService.getPersonsByCity(city);

			for (Person person : residentsFoundByCity) {
				Map<String, String> residentFoundByCity = new HashMap<String, String>();
				residentFoundByCity.put("email", person.getEmail());

				System.out.println("residentFoundByCity" + residentFoundByCity);
				listOfResidentsFoundByCity.add(residentFoundByCity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("An error has occured in searching  full info  emails of residents of the city");
		}
		System.out.println(" listOfResidentsFoundByCity" + listOfResidentsFoundByCity);
		return listOfResidentsFoundByCity;
	}
}
