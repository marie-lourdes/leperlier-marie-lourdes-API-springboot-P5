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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Service
public class ResidentsOfStationNumberService implements ICalculatorAgeOfResident{
	@Autowired
	MedicalRecordService medicalRecordService;
	@Autowired
	SearchingInfoOfResidentImpl infoOfResident;


	// private List<Map<String,String>> listOfResidentOfStationNumber = new
	// ArrayList<Map<String,String>>();
	public List<Map<String, String>> getListOfResidentsOfStationNumber(String stationNumber) throws ParseException {
		List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		listOfResidentOfStationNumber = infoOfResident.searchInfoOfResident(stationNumber);

		for (Map<String, String> residents : listOfResidentOfStationNumber) {
			residents.remove("age");
			/*
			 * for(Map.Entry<String,String> resident : residents.entrySet()) { resident. }
			 */
			System.out.println("residents" + residents);
		}
		System.out.println("list des residents" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}
	
	public BigInteger getAgeOfResident(String idFirstAndLastName) throws ParseException {
		return calculateAgeOfResident(idFirstAndLastName);
	}

	/*
	 * public List<Person> getFireStationByStationNumber(String stationNumber) { }
	 */

	

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidents(String stationNumber) throws ParseException {
		Map<String, Integer> mapOfAdultsAndChild = new HashMap<String, Integer>();
		List<Map<String, String>> ResidentsOfStationNumberWithAge = infoOfResident.searchInfoOfResident(stationNumber);

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
