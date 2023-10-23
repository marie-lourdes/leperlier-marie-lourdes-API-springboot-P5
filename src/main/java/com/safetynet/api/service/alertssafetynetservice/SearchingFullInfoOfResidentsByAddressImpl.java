package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
			Map<String, String> residentWithSameAddress = new LinkedHashMap<String, String>();
			residentWithSameAddress.put("firstName", person.getFirstName());
			residentWithSameAddress.put("lastName", person.getLastName());
			residentWithSameAddress.put("address", person.getAddress());
			residentWithSameAddress.put("city", person.getCity());
			residentWithSameAddress.put("zip", person.getZip());
			residentWithSameAddress.put("phone", person.getPhone());
			residentWithSameAddress.put("email", person.getEmail());
			residentWithSameAddress.put("age",
					calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
			System.out.println(" residents with same address" + residentWithSameAddress);
			listOfresidentsWithSameAddress.add(residentWithSameAddress);
		}

		System.out.println("listOfresidentsWithSameAddress" + listOfresidentsWithSameAddress);
		return listOfresidentsWithSameAddress;
	}
}
