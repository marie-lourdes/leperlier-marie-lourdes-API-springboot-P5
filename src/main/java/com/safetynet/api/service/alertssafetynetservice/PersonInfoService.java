package com.safetynet.api.service.alertssafetynetservice;

import java.util.List;
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
	public List<Object> getInfoAndMedicalRecordOfPersonByFullName(String firstName, String lastName) {
		String fullName= firstName + " " + lastName;
		Optional<Person> personFounByIdFullName = personService.getOnePersonById(fullName);
		return null;
	}
}
