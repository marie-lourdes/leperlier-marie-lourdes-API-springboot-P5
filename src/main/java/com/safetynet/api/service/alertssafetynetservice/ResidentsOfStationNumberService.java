package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentsOfStationNumberService {
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithCountDown countDownOfAdultsAndChilds;
	
	private List<Map<String, String>> listOfResidentOfStationNumber;

	public List<Map<String, String>> getListOfResidentsOfStationNumber(String stationNumber){
		listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
		listOfResidentOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);

		for (Map<String, String> residents : listOfResidentOfStationNumber) {
			residents.remove("age");
			System.out.println("residents" + residents);
		}
		System.out.println("list of residents" + listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber){
		return countDownOfAdultsAndChilds.sortAdultsAndChilds(stationNumber);
	}
}
