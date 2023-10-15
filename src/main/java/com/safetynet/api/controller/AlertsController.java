package com.safetynet.api.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.alertssafetynetservice.ChildAlertService;
import com.safetynet.api.service.alertssafetynetservice.ResidentsOfStationNumberService;

@RestController
public class AlertsController {
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService ;
	
	@Autowired
	ChildAlertService  childAlertService;
	
	@GetMapping("/firestation")
	public List<Map<String, String>> getAllAdultsAndChildsNearOfFireStations(@RequestParam String stationNumber) throws ParseException {
		List<Map<String, String>> listOfResidentsOfStationNumber =residentsOfStationNumberService. getListOfResidentsOfStationNumber(stationNumber);
		Map<String,Integer> mapOfAdultsAndChild = residentsOfStationNumberService.sortAdultsAndChildsOfListOfResidentsWithCountDown(stationNumber);
		
		for(Map.Entry<String,Integer> entry :mapOfAdultsAndChild .entrySet() ) {
			Map<String,String> mapOfAdultsAndChildConvertedValueString = new HashMap<String, String>();
			mapOfAdultsAndChildConvertedValueString.put(entry.getKey(),entry.getValue().toString());
			listOfResidentsOfStationNumber.add(mapOfAdultsAndChildConvertedValueString);
		}
		return  (listOfResidentsOfStationNumber)  ;
	}
	
	@GetMapping("/childAlert")
	public  List<Map<String, String>> getChildsAndMembersOfHouseHoldByAddress (@RequestParam String address){
		return childAlertService.getChildsAndMembersOfHouseHold(address);
	}
}
