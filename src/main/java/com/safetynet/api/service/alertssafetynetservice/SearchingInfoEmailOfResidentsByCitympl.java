package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingInfoEmailOfResidentsByCitympl implements ISearchingInfoOfResident{
	@Autowired
	PersonService personService;
	
	private List<Person> residentsFoundByCity = new ArrayList<Person>();
	
	@Override
	public List<Map<String, String>> searchInfoOfResident(String city) {
		List<Map<String, String>> listOfResidentsFoundByCity= new ArrayList<Map<String, String>>();
		residentsFoundByCity = personService.getPersonsByCity(city);
		
		for (Person person : residentsFoundByCity) {
			Map<String, String> residentFoundByCity = new HashMap<String, String>();	
			residentFoundByCity.put("email", person.getEmail());
		
			System.out.println("residentFoundByCity" +residentFoundByCity);
			listOfResidentsFoundByCity.add(residentFoundByCity);
		}

		System.out.println(" listOfResidentsFoundByCity" + listOfResidentsFoundByCity);
		return listOfResidentsFoundByCity;
	}
}
