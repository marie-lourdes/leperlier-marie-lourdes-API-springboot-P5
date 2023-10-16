package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

public class SearchingInfoMedicalRecordOfResidentsImpl {
	@Autowired
	PersonService personService;
	
	@Autowired
	MedicalRecordService medicalRecordService;
	SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddressImpl;

	private Optional<MedicalRecord> medicalRecordFoundByFullName = Optional.empty();

/*	@Override
	public List<Map<String, String>> searchInfoOfResident(String address) {
		residentsFoundByAddress = personService.getPersonsByAddress(address);
		List<Map<String, ?>> listOfResidentFoundByFullName = new ArrayList<Map<String, ?>>();
		List<Person> persons = personService.getAllPersons();
		medicalRecordFoundByFullName  = medicalRecordService.getOneMedicalRecordById(idFullNameOfPerson);
			for (Person person : persons) {
				
				if (person.getId().equals(medicalRecordFoundByFullName.get().getId())) {
					Map<String, String> residentFoundByFullName = new HashMap<String, String>();
					Map<String, MedicalRecord> medicalRecordFoundByFullName = new HashMap<String, MedicalRecord>();
					residentFoundByFullName.put("lastName", person.getLastName());
					residentFoundByFullName.put("phone", person.getPhone());
					residentFoundByFullName.put("phone", person.getPhone());
					residentFoundByFullName.put("age",calculatorAgeOfResident.calculateAgeOfResident(person.getId()).toString());
					System.out.println(" residentFoundByFullName" + residentFoundByFullName);
					medicalRecordFoundByFullName.put("medicalRecord",medicalRecordFoundByFullName);
					medicalRecordsFoundByFullName .add(residentFoundByFullName);
				}

			}
		
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentFoundByFullName;
	}*/
	
}
