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
	 SearchingInfoPersonByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;

	@Autowired
	PersonService personService;

	private List<Person> personsFoundByFullName = new ArrayList<Person>();
	private List<Person> personsFoundByLastName = new ArrayList<Person>();
	private List<Map<String, String>> listOfResidentsWithMedicalRecord = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getInfoAndMedicalRecordOfPersonByFullName(String firstName, String lastName)
			throws NullPointerException {
		String fullName = firstName + " " + lastName;
		try {
			personsFoundByLastName = personService.getPersonsLastName(lastName);
			Person personFounByIdFullName = personService.getOnePersonById(fullName);

			personsFoundByFullName.add(personFounByIdFullName);

			// add all person with the same lastname than the person searched by fullname
			for (Person person : personsFoundByLastName) {
				listOfResidentsWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
						.searchInfoOfResident(person.getAddress());
			}
			// add person searched by full name
			for (Person person : personsFoundByFullName) {
				listOfResidentsWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
						.searchInfoOfResident(person.getAddress());
			}

			System.out.println(
					"list of person searched by full name and all person with the same last name and its medical record"
							+ listOfResidentsWithMedicalRecord);
		} catch (NullPointerException e) {
			throw new NullPointerException("list of person not found  searched by full name " + fullName);
		}

		return listOfResidentsWithMedicalRecord;
	}
}
