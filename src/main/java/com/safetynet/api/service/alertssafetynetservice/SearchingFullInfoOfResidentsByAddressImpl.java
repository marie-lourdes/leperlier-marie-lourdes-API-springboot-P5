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
public class SearchingFullInfoOfResidentsByAddressImpl implements ISearchingInfoOfResident {
	@Autowired
	PersonService personService;

	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;

	private List<Person> residentsFoundByAddress = new ArrayList<Person>();

	@Override
	public List<Map<String, String>> searchInfoOfResident(String request) {
		List<Map<String, String>> listOfresidentsWithSameAddress = new ArrayList<Map<String, String>>();
		residentsFoundByAddress = personService.getPersonsByAddress(request);

		for (Person person : residentsFoundByAddress) {	
			Map<String, String> residentsWithSameAddress = new HashMap<String, String>();
			residentsWithSameAddress.put("firstName", person.getFirstName());
			residentsWithSameAddress.put("lastName", person.getLastName());
			residentsWithSameAddress.put("address", person.getAddress());
			residentsWithSameAddress.put("city", person.getCity());
			residentsWithSameAddress.put("zip", person.getZip());
			residentsWithSameAddress.put("phone", person.getPhone());
			residentsWithSameAddress.put("email", person.getEmail());
			residentsWithSameAddress.put("age",
					calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
			System.out.println(" personFactory" + residentsWithSameAddress);
			listOfresidentsWithSameAddress.add(residentsWithSameAddress);
		}

		System.out.println("listOfresidentsWithSameAddress" + listOfresidentsWithSameAddress);
		return listOfresidentsWithSameAddress;
	}
}
