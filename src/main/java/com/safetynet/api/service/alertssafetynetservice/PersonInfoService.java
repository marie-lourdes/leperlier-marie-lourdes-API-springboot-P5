package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class PersonInfoService {
	private static final Logger log = LogManager.getLogger(PersonInfoService.class);
	@Autowired
	 SearchingInfoPersonByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;

	@Autowired
	PersonService personService;

	private List<Person> personsFoundByFullName = new ArrayList<Person>();
	private List<Person> personsFoundByLastName = new ArrayList<Person>();
	private List<Map<String, String>> listOfResidentsWithMedicalRecord = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getInfoAndMedicalRecordOfPersonByFullName(String firstName, String lastName)
			throws NullPointerException {
		
		String fullName = firstName + " " + lastName;
		log.debug("Retrieving resident by full name {} and other residents with the same last name: {}", fullName, lastName);
		
		try {
			personsFoundByLastName = personService.getPersonsLastName(lastName);
			Person personFounByIdFullName = personService.getOnePersonById(fullName);

			personsFoundByFullName.add(personFounByIdFullName);

			// add all person with the same lastname than the person searched by fullname
			log.debug("Retrieving all residents with same last name : {}", lastName);
			for (Person person : personsFoundByLastName) {
				listOfResidentsWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
						.searchInfoOfResident(person.getAddress());
				log.debug("List of  residents retrieved with same last name : {}", listOfResidentsWithMedicalRecord );
			}
			
			// add person searched by full name
			for (Person person : personsFoundByFullName) {
				listOfResidentsWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
						.searchInfoOfResident(person.getAddress());
				log.debug("Resident retrieved by  full name  {} : {}", fullName,listOfResidentsWithMedicalRecord );
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("List of person not found  searched by full name : " + fullName);
		}
		log.info("Resident by full name {} with  all residents with the same last name {} retrieved successfully: {}",fullName, lastName, listOfResidentsWithMedicalRecord);
		return listOfResidentsWithMedicalRecord;
	}
}
