package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingInfoOfChildAndMemberOfHouseHoldImpl  implements ISearchingInfoOfResident{
	@Autowired
	PersonService personService;

	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;
		
	private List<Person> residentsFoundByAddress= new ArrayList<Person>();
	@Override
	public List<Map<String, String>> searchInfoOfResident(String request) {
		List<Map<String, String>> listOfResidentsWithSameAddress = new ArrayList<Map<String, String>>();
		List<Person> persons = personService.getAllPersons();
		
		residentsFoundByAddress = personService.getPersonsByAddress(request);
		Iterator<Person> itrPersonsFoundByAddress = residentsFoundByAddress.listIterator();
		
		while (itrPersonsFoundByAddress .hasNext()) {
		Person itrPerson = itrPersonsFoundByAddress.next();

			for (Person person : persons) {
					Map<String, String> ResidentsWithSameAddress = new HashMap<String, String>();
					ResidentsWithSameAddress.put("firstName", person.getFirstName());
					ResidentsWithSameAddress.put("lastName", person.getLastName());
					ResidentsWithSameAddress.put("age", calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
					System.out.println(" personFactory" + ResidentsWithSameAddress);
					listOfResidentsWithSameAddress.add(ResidentsWithSameAddress);
			}
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentsWithSameAddress );
		return listOfResidentsWithSameAddress ;
	}
}
