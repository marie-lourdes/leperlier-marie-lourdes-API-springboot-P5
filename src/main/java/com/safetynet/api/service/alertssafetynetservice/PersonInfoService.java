package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class PersonInfoService {
	@Autowired
	SearchingFullInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;
	
	@Autowired
	PersonService personService;
	
	private List<Person> personsFoundByFullName= new ArrayList<Person>();
	private List<Person> 	personsFoundWithTheSameLastName =new ArrayList<Person>();
	
	public List<Map<String, String>> getInfoAndMedicalRecordOfPersonByFullName(String firstName, String lastName) {
		String fullName= firstName + " " + lastName;
		Person personFoundByIdFullName = personService.getOnePersonById(fullName);
		personsFoundByFullName.add(personFoundByIdFullName);
		personsFoundWithTheSameLastName =personService.getPersonsLastName(lastName);
		List<Map<String, String>>listOfResidentsWithMedicalRecord= new ArrayList<Map<String, String>>();
	
		//add medicalrecord for person searched
		for(Person person:personsFoundByFullName) {
			listOfResidentsWithMedicalRecord= searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(person.getAddress());		
		}
		//add medicalrecord for all person with the same last name of  person searched
		 for(Person person:personsFoundWithTheSameLastName ) {
			 listOfResidentsWithMedicalRecord= searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(person.getAddress());	
		}
		 
		return listOfResidentsWithMedicalRecord;
	}
}
