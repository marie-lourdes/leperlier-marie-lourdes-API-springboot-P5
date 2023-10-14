package com.safetynet.api.service.alertssafetynetservice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.service.dataservice.MedicalRecordService;

@Service
public class ResidentsOfStationNumberService {
	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;

	private List<Map<String, String>> ResidentsOfStationNumberWithAge = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getListOfResidentsOfStationNumber(String stationNumber) throws ParseException {
		listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		listOfResidentOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);

		for (Map<String, String> residents : listOfResidentOfStationNumber) {
			residents.remove("age");
			System.out.println("residents" + residents);
		}
		System.out.println("list des residents" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidents(String stationNumber) throws ParseException {
		Map<String, Integer> mapOfAdultsAndChild = new HashMap<String, Integer>();
		ResidentsOfStationNumberWithAge = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);

		Integer indexChild = 1;
		Integer indexAdult = 1;
		for (Map<String, String> resident : ResidentsOfStationNumberWithAge) {
			System.out.println("resident of map" + resident.get("age"));

			if (Integer.parseInt(resident.get("age")) <= 18) {

				mapOfAdultsAndChild.put("childs", indexChild++);
			} else {
				mapOfAdultsAndChild.put("adults", indexAdult++);
			}
		}

		System.out.println("resident of map" + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
