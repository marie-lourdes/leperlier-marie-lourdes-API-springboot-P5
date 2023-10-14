package com.safetynet.api.service.alertssafetynetservice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class ChildAlertService {
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithCountDownImpl adultsAndChildsOfListOfResidents;
	
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

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber){
		return adultsAndChildsOfListOfResidents.sortAdultsAndChilds(stationNumber);
	}
}
