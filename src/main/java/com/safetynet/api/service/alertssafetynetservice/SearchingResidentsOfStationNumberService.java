package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class SearchingResidentsOfStationNumberService {
	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	private List<FireStation> fireStationFoundByStationNumber = new ArrayList<FireStation>();

	// private List<Map<String,String>> listOfResidentOfStationNumber = new
	// ArrayList<Map<String,String>>();
	public List<Map<String, String>> getListOfResidentOfStationNumber(String stationNumber) throws ParseException{
		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		listOfResidentOfStationNumber=searchInfoOfResidentOfStationNumber(stationNumber) ;
		
		for(Map<String, String> residents:listOfResidentOfStationNumber) {
			residents.remove("age");
			/*for(Map.Entry<String,String> resident : residents.entrySet()) {
				resident.
			}*/
			System.out.println("residents"+residents);
		}
		System.out.println("list des residents"+ listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}
	public List<Map<String, String>> searchInfoOfResidentOfStationNumber(String stationNumber) throws ParseException {
		// Map<String,Integer> mapOfAdultsAndChildOfResidents =new
		// HashMap<String,Integer>();
		fireStationFoundByStationNumber = fireStationService.getFireStationsById(stationNumber);
		Iterator<FireStation> itrFireStations = fireStationFoundByStationNumber.listIterator();
		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		List<Person> persons = personService.getAllPersons();

		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();

			for (Person person : persons) {
				if (person.getAddress().equals(itrFireStation.getAddress())) {
					Map<String, String> residentOfStationNumber = new HashMap<String, String>();
					// Person personFactory =new Person(person.getFirstName(),person.getLastName(),
					// person.getAddress(), person.getPhone());
					residentOfStationNumber.put("firstName", person.getFirstName());
					residentOfStationNumber.put("lastName", person.getLastName());
					residentOfStationNumber.put("address", person.getAddress());
					residentOfStationNumber.put("phone", person.getPhone());
					residentOfStationNumber.put("age", getAgeOfPerson(person.getId()).toString());
					System.out.println(" personFactory" + residentOfStationNumber);
					listOfResidentOfStationNumber.add(residentOfStationNumber);
				}

			}
		}
		System.out.println("listOfResidentsOfStationNumber" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

	/*
	 * public List<Person> getFireStationByStationNumber(String stationNumber) { }
	 */

	public BigInteger getAgeOfPerson(String idFirstAndLastName) throws ParseException {

		String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).get()
				.getBirthdate();

		// formatage date birthdate
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date birthdate = format.parse(birthDateOfPerson);

		// calcule de l age
		BigInteger yearInMs = new BigInteger("31536000000");// millisecondes par an
		Long ageOfPerson = new Date().getTime() - birthdate.getTime();
		System.out.println("age " + BigInteger.valueOf(ageOfPerson).divide(yearInMs));

		// getBirthDateOfPersonWithMedicalRecord()
		// calculateAgeOfPerson();
		return BigInteger.valueOf(ageOfPerson).divide(yearInMs);
	}

	public Map<String, Integer> sortAdultsAndChildOfListResident(String stationNumber) throws ParseException {
		Map<String, Integer> mapOfAdultsAndChild = new HashMap<String, Integer>();
		List<Map<String, String>> ResidentsOfStationNumberWithAge = searchInfoOfResidentOfStationNumber(stationNumber);

		Integer indexChild = 1;
		Integer indexAdult = 1;
		for (Map<String, String> resident : ResidentsOfStationNumberWithAge) {
			System.out.println("resident of map" + resident.get("age"));

			if (Integer.parseInt(resident.get("age")) <= 18) {

				mapOfAdultsAndChild.put("childs", indexChild++);
			} else {
				mapOfAdultsAndChild.put("adults", indexAdult++);
			}
			// BigInteger ageOfResident = getAgeOfPerson( person.getId());
		}
		// BigInteger ageOfResident = getAgeOfPerson( person.getId());
		// personService.getOnePersonByAddress(address);
		System.out.println("resident of map" + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
