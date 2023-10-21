package com.safetynet.api.controller;

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
public class AlertsController implements IResponseHTTPEmpty {
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;

	@Autowired
	ChildAlertService childAlertService;

	@Autowired
	PhoneAlertService phoneAlertService;

	@Autowired
	FireService fireService;

	@Autowired
	FloodService floodService;

	@Autowired
	PersonInfoService personInfoService;

	@Autowired
	CommunityEmailService communityEmailService;

	@GetMapping("/firestation")
	@ResponseBody
	public ResponseEntity<?> getAllAdultsAndChildsNearOfFireStations(@RequestParam String stationNumber) {
		try {
			List<Map<String, String>> listOfResidentsOfStationNumber = residentsOfStationNumberService
					.getListOfResidentsOfStationNumber(stationNumber);
			Map<String, Integer> mapOfAdultsAndChild = residentsOfStationNumberService
					.sortAdultsAndChildsOfListOfResidentsWithCountDown(stationNumber);

			for (Map.Entry<String, Integer> entry : mapOfAdultsAndChild.entrySet()) {
				Map<String, String> mapOfAdultsAndChildConvertedValueString = new HashMap<String, String>();
				mapOfAdultsAndChildConvertedValueString.put(entry.getKey(), entry.getValue().toString());
				listOfResidentsOfStationNumber.add(mapOfAdultsAndChildConvertedValueString);
			}
			return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsOfStationNumber);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/childAlert")
	@ResponseBody
	public ResponseEntity<?> getChildsAndMembersOfHouseHoldByAddress(@RequestParam String address) {
		try {
			List<Map<String, String>> childs = childAlertService.getChildsAndMembersOfHouseHold(address);
			return ResponseEntity.status(HttpStatus.OK).body(childs);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/phoneAlert")
	@ResponseBody
	public  ResponseEntity<?> getPhonesOfResidentsByStationNumber(@RequestParam String stationNumber) {	
		try {
			List<Map<String, String>> listOfPhonesOfResidentsByStationNumber =phoneAlertService.getListOfPhonesOfResidentsOfStationNumber(stationNumber);
			return ResponseEntity.status(HttpStatus.OK).body(listOfPhonesOfResidentsByStationNumber );
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/fire")
	@ResponseBody
	public ResponseEntity<?> getListOfResidentsAndFireStationNearFire(@RequestParam String address) {
		try {
			List<Object> listOfResidentsAndFireStationNearFire = fireService.getListOfResidentsAndFireStationNearFire(address);
			return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsAndFireStationNearFire);
		}catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/flood/stations")
	@ResponseBody
	public ResponseEntity<?>getListOfHouseHoldByStationNumber(@RequestParam List<String> stations) {
		List<Object> listOfHouseHoldByStationNumber = new ArrayList<Object>();
		List<Object> listOfHouseHoldByStationNumberWithMoreOneRequestParam = new ArrayList<Object>();
	
			for (String station : stations) {
				try {
				listOfHouseHoldByStationNumber = floodService.getListOfHouseHoldByStationNumber(station);	
				listOfHouseHoldByStationNumberWithMoreOneRequestParam.add(listOfHouseHoldByStationNumber);
			
				}catch (NullPointerException e) {
					System.out.println(e.getMessage());	
				}
			}
			
			try {
				if(listOfHouseHoldByStationNumberWithMoreOneRequestParam.isEmpty()) {
					throw new NullPointerException("HouseHold not found at this/theses station(s) to prevent for flood" );	
				}
			}catch (NullPointerException e) {
				System.out.println(e.getMessage());
				return returnResponseEntityEmptyAndCode404();
			}
			
		return ResponseEntity.status(HttpStatus.OK).body(	listOfHouseHoldByStationNumberWithMoreOneRequestParam);
	}
	

	@GetMapping("/personInfo")
	@ResponseBody
	public ResponseEntity<?> getInfoAndMedicalRecordOfPersonByFullName(@RequestParam String firstName,
			@RequestParam String lastName) {
		try {
			List<Map<String, String>> personByFullNameInfoAndMedicalRecord= personInfoService.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
			return ResponseEntity.status(HttpStatus.OK).body(personByFullNameInfoAndMedicalRecord);
		}catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	//	return personInfoService.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
	}

	@GetMapping("/communityEmail")
	@ResponseBody
	public ResponseEntity<?>  getEmailOfResidentsOfCity(@RequestParam String city) {
		try {
			List<Map<String, String>> listOfEmailsOfResidentsOfCity=communityEmailService.getEmailOfResidentsOfCity(city);
			return ResponseEntity.status(HttpStatus.OK).body(listOfEmailsOfResidentsOfCity);
		}catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		//return communityEmailService.getEmailOfResidentsOfCity(city);
	}

}
