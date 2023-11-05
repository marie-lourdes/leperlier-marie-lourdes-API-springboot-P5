package com.safetynet.api.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger log = LogManager.getLogger(AlertsController.class);
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
			log.error(e.getMessage());
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
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/phoneAlert")
	@ResponseBody
	public ResponseEntity<?> getPhonesOfResidentsByStationNumber(@RequestParam String stationNumber) {
		try {
			List<Map<String, String>> listOfPhonesOfResidentsByStationNumber = phoneAlertService
					.getListOfPhonesOfResidentsOfStationNumber(stationNumber);
			return ResponseEntity.status(HttpStatus.OK).body(listOfPhonesOfResidentsByStationNumber);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/fire")
	@ResponseBody
	public ResponseEntity<?> getListOfResidentsAndFireStationNearFire(@RequestParam String address) {
		try {
			List<Object> listOfResidentsAndFireStationNearFire;
			
				listOfResidentsAndFireStationNearFire = fireService
						.getListOfResidentsAndFireStationNearFire(address);
			
			return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsAndFireStationNearFire);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		} catch (Exception e) {
			log.error(e.getMessage());
			return  returnResponseEntityEmptyAndCode500();
		}
	}

	/*
	 * when testing , after resquest all param stations , if request one no existing
	 * keep all data retrived according to stations param, no fixing this error
	 * refactoring the method getListOfHouseHoldByStationNumberIfFlood() with
	 * list.clear() or removeall() "
	 */
	@GetMapping("/flood/stations")
	@ResponseBody
	public ResponseEntity<?> getListOfHouseHoldByStationNumberIfFlood(@RequestParam List<String> stations) {
		List<Object> listOfHouseHoldByStationNumberparams = new ArrayList<Object>();

		try {
			for (String station : stations) {
				List<Object> listOfHouseHoldByStationNumber = new ArrayList<Object>();
				listOfHouseHoldByStationNumber = floodService.getListOfHouseHoldByStationNumber(station);
				listOfHouseHoldByStationNumberparams.add(listOfHouseHoldByStationNumber);
				
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(listOfHouseHoldByStationNumberparams);

		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/personInfo")
	@ResponseBody
	public ResponseEntity<?> getInfoAndMedicalRecordOfPersonByFullName(@RequestParam String firstName,
			@RequestParam String lastName) {
		try {
			List<Map<String, String>> personByFullNameInfoAndMedicalRecord = personInfoService
					.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
			return ResponseEntity.status(HttpStatus.OK).body(personByFullNameInfoAndMedicalRecord);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/communityEmail")
	@ResponseBody
	public ResponseEntity<?> getEmailOfResidentsOfCity(@RequestParam String city) {
		try {
			List<Map<String, String>> listOfEmailsOfResidentsOfCity = communityEmailService
					.getEmailOfResidentsOfCity(city);
			return ResponseEntity.status(HttpStatus.OK).body(listOfEmailsOfResidentsOfCity);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}
}
