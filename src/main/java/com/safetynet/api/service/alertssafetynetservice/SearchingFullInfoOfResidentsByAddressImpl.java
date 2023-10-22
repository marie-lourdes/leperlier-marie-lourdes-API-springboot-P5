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
	public List<Map<String, String>> searchInfoOfResident(String address) {
		List<Map<String, String>> listOfresidentsWithSameAddress = new ArrayList<Map<String, String>>();
		residentsFoundByAddress = personService.getPersonsByAddress(address);

		for (Person person : residentsFoundByAddress) {
			Map<String, String> residentWithSameAddress = new HashMap<String, String>();
			residentWithSameAddress.put("firstName", person.getFirstName());
			residentWithSameAddress.put("lastName", person.getLastName());
			residentWithSameAddress.put("address", person.getAddress());
			residentWithSameAddress.put("city", person.getCity());
			residentWithSameAddress.put("phone", person.getPhone());
			residentWithSameAddress.put("email", person.getEmail());
		
			System.out.println(" residents with same address" + residentWithSameAddress);
			listOfresidentsWithSameAddress.add(residentWithSameAddress);
		}

		System.out.println("listOfresidentsWithSameAddress" + listOfresidentsWithSameAddress);
		return listOfresidentsWithSameAddress;
	}
}
