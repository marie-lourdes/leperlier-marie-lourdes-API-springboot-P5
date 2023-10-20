package com.safetynet.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.alertssafetynetservice.ChildAlertService;
import com.safetynet.api.service.alertssafetynetservice.CommunityEmailService;
import com.safetynet.api.service.alertssafetynetservice.FireService;
import com.safetynet.api.service.alertssafetynetservice.FloodService;
import com.safetynet.api.service.alertssafetynetservice.PersonInfoService;
import com.safetynet.api.service.alertssafetynetservice.PhoneAlertService;
import com.safetynet.api.service.alertssafetynetservice.ResidentsOfStationNumberService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

@RestController
public class AlertsController implements  IResponseHTTPEmpty{
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService ;
	
	@Autowired
	ChildAlertService  childAlertService;
	
	@Autowired
	PhoneAlertService  phoneAlertService;
	
	@Autowired
	FireService  fireService;

	@Autowired
	FloodService  floodService;
	
	@Autowired
	PersonInfoService personInfoService ;
	
	@Autowired
	 CommunityEmailService  communityEmailService;
	
	@GetMapping("/firestation")
	@ResponseBody 
	public ResponseEntity<?> getAllAdultsAndChildsNearOfFireStations(@RequestParam String stationNumber) {
		try {
			List<Map<String, String>> listOfResidentsOfStationNumber =residentsOfStationNumberService. getListOfResidentsOfStationNumber(stationNumber);
			Map<String,Integer> mapOfAdultsAndChild = residentsOfStationNumberService.sortAdultsAndChildsOfListOfResidentsWithCountDown(stationNumber);
		
			for(Map.Entry<String,Integer> entry :mapOfAdultsAndChild .entrySet() ) {
				Map<String,String> mapOfAdultsAndChildConvertedValueString = new HashMap<String, String>();
				mapOfAdultsAndChildConvertedValueString.put(entry.getKey(),entry.getValue().toString());
				listOfResidentsOfStationNumber.add(mapOfAdultsAndChildConvertedValueString);
		}
			 return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsOfStationNumber );
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		
	}

	@GetMapping("/childAlert")
	@ResponseBody 
	public  ResponseEntity<?> getChildsAndMembersOfHouseHoldByAddress  (@RequestParam String address){
		try {
			List<Map<String, String>>  childs =childAlertService.getChildsAndMembersOfHouseHold(address);
		 return ResponseEntity.status(HttpStatus.OK).body(childs);
		}catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		
	/*	 ResponseEntity<?> responseEmpty =ResponseEntity.status(HttpStatus.NOT_FOUND).body(new String(" "));
		 ResponseEntity<?> response=ResponseEntity.status(HttpStatus.OK).body(childs);
		if(childs==null) {
			return responseEmpty;//return string empty
		}		
		return response;*/
	}
	
	@GetMapping("/phoneAlert")
	@ResponseBody 
	public  List<Map<String, String>> getPhonesOfResidentsByStationNumber (@RequestParam String stationNumber){
		return phoneAlertService.getListOfPhonesOfResidentsOfStationNumber(stationNumber);
	}
	
	@GetMapping("/fire")
	@ResponseBody 
	public  List<Object>getListOfResidentsAndFireStationNearFire(@RequestParam String address){
		return fireService.getListOfResidentsAndFireStationNearFire(address);
	}
	
	@GetMapping("/flood/stations")
	@ResponseBody 
	public  List<Object> getListOfHouseHoldByStationNumber(@RequestParam List<String>stations){
		List<Object> listOfHouseHoldByStationNumber =new ArrayList<Object>();
		List<Object> listOfHouseHoldByStationNumberWithMoreOneRequestParam =new ArrayList<Object>();
		for(String station: stations) {
			listOfHouseHoldByStationNumber =floodService.getListOfHouseHoldByStationNumber(station);
			listOfHouseHoldByStationNumberWithMoreOneRequestParam .add(listOfHouseHoldByStationNumber);
		}
		 return listOfHouseHoldByStationNumberWithMoreOneRequestParam ;
	}
	
	
	@GetMapping("/personInfo")
	@ResponseBody 
	public  List<Map<String, String>> getInfoAndMedicalRecordOfPersonByFullName(@RequestParam String  firstName, @RequestParam String  lastName){	
		return personInfoService.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
	}
	
	@GetMapping("/communityEmail")
	@ResponseBody 
	public  List<Map<String, String>> getEmailOfResidentsOfCity(@RequestParam String city){
		return communityEmailService.getEmailOfResidentsOfCity(city);
	}
	
	
}
