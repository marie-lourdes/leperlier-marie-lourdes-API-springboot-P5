package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class SearchingFullInfoOfResidentsByAddressImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingFullInfoOfResidentsByAddressImpl.class);
	@Autowired
	PersonService personService;

	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;

	private List<Person> residentsFoundByAddress = new ArrayList<Person>();

	@Override
	public List<Map<String, String>> searchInfoOfResident(String address) {
		log.debug("Searching  full info of residents with the same address");
		
		List<Map<String, String>> listOfResidentsWithSameAddress = new ArrayList<Map<String, String>>();
		try {

			residentsFoundByAddress = personService.getPersonsByAddress(address);

			for (Person person : residentsFoundByAddress) {
				Map<String, String> residentFoundByAddress = new LinkedHashMap<String, String>();
				residentFoundByAddress.put("firstName", person.getFirstName());
				residentFoundByAddress.put("lastName", person.getLastName());
				residentFoundByAddress.put("address", person.getAddress());
				residentFoundByAddress.put("city", person.getCity());
				residentFoundByAddress.put("phone", person.getPhone());
				residentFoundByAddress.put("email", person.getEmail());
				String age = calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString();

				if (age == "0") {
					log.error("Age not provided for this resident {} {}", residentFoundByAddress.get("firstName"),
							residentFoundByAddress.get("lastName"));
					residentFoundByAddress.put("age", " error! ");
				} else {
					residentFoundByAddress.put("age", age);
				}
				listOfResidentsWithSameAddress.add(residentFoundByAddress);
			}
		} catch (Exception e) {
			log.error("An error has occured in searching  full info of residents with the same address");

		}
		
		log.debug(" Full info of residents with the same address successfully retrieved : {}",listOfResidentsWithSameAddress);
		return listOfResidentsWithSameAddress;
	}
}
