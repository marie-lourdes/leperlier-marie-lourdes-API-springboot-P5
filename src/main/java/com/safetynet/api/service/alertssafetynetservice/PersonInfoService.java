package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class PersonInfoService {
	@Autowired
	SearchingFullInfoOfResidentsWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;
	
	@Autowired
	PersonService personService;
	
	private List<Person> personsFoundByLastNameUpdated= new ArrayList<Person>();
	private List<Person> personsFoundByLastName=new ArrayList<Person>();
	
	public List<Map<String, String>> getInfoAndMedicalRecordOfPersonByFullName(String firstName, String lastName) {
		String fullName= firstName + " " + lastName;
		 personsFoundByLastName =personService.getPersonsLastName(lastName);
		Optional<Person> personFounByIdFullName = personService.getOnePersonById(fullName);
	
		personsFoundByLastNameUpdated.add(personFounByIdFullName.get());
		
		 List<Map<String, String>>listOfResidentsWithMedicalRecord= new ArrayList<Map<String, String>>();
	
		 for(Person person:personsFoundByLastName) {
			 listOfResidentsWithMedicalRecord= searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(person.getAddress());	
		}
		 
		for(Person person:personsFoundByLastNameUpdated) {
			listOfResidentsWithMedicalRecord= searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(person.getAddress());		
		}
		
		return listOfResidentsWithMedicalRecord;
	}
}
