package com.safetynet.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.safetynet.api.utils.IResponseHTTPEmpty404;

@RestController
public class AlertsController implements IResponseHTTPEmpty404 <Object>{
	private static final Logger log = LogManager.getLogger(AlertsController.class);

	private ResidentsOfStationNumberService residentsOfStationNumberService;
	private ChildAlertService childAlertService;
	private PhoneAlertService phoneAlertService;
	private FireService fireService;
	private FloodService floodService;
	private PersonInfoService personInfoService;
	private CommunityEmailService communityEmailService;

	public AlertsController(ResidentsOfStationNumberService residentsOfStationNumberService,
			ChildAlertService childAlertService, PhoneAlertService phoneAlertService, FireService fireService,
			FloodService floodService, PersonInfoService personInfoService,
			CommunityEmailService communityEmailService) {
		this.residentsOfStationNumberService = residentsOfStationNumberService;
		this.childAlertService = childAlertService;
		this.phoneAlertService = phoneAlertService;
		this.fireService = fireService;
		this.floodService = floodService;
		this.personInfoService = personInfoService;
		this.communityEmailService = communityEmailService;
	}

	@GetMapping("/firestation")
	@ResponseBody
	public ResponseEntity<Object> getAllAdultsAndChildsNearOfFireStations(@RequestParam String stationNumber) {
		List<Map<String, String>> listOfResidentsOfStationNumber = new ArrayList<Map<String, String>>();
		Map<String, String> mapOfAdultsAndChildSorted = new HashMap<String, String>();
		
		try {
			listOfResidentsOfStationNumber = residentsOfStationNumberService
					.getListOfResidentsOfStationNumber(stationNumber);

			if (listOfResidentsOfStationNumber.isEmpty()) {
				throw new NullPointerException("Residents not found at this station number: " + stationNumber);
			}
			mapOfAdultsAndChildSorted = residentsOfStationNumberService
					.sortAdultsAndChildsOfListOfResidentsWithCountDown(stationNumber);

			listOfResidentsOfStationNumber.add(mapOfAdultsAndChildSorted);
			return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsOfStationNumber);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/childAlert")
	@ResponseBody
	public ResponseEntity<Object> getChildsAndMembersOfHouseHoldByAddress(@RequestParam String address) {
		List<Map<String, String>> childs = new ArrayList<Map<String, String>>();
		
		try {
			childs = childAlertService.getChildsAndMembersOfHouseHold(address);
			return ResponseEntity.status(HttpStatus.OK).body(childs);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/phoneAlert")
	@ResponseBody
	public ResponseEntity<Object> getPhonesOfResidentsByStationNumber(@RequestParam String stationNumber) {
		List<Map<String, String>> listOfPhonesOfResidentsByStationNumber = new ArrayList<Map<String, String>>();
		
		try {
			listOfPhonesOfResidentsByStationNumber = phoneAlertService
					.getListOfPhonesOfResidentsOfStationNumber(stationNumber);
			return ResponseEntity.status(HttpStatus.OK).body(listOfPhonesOfResidentsByStationNumber);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/fire")
	@ResponseBody
	public ResponseEntity<Object> getListOfResidentsAndFireStationNearFire(@RequestParam String address) {
		try {
			List<Object> listOfResidentsAndFireStationNearFire = new ArrayList<Object>();

			listOfResidentsAndFireStationNearFire = fireService.getListOfResidentsAndFireStationNearFire(address);

			return ResponseEntity.status(HttpStatus.OK).body(listOfResidentsAndFireStationNearFire);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/flood/stations")
	@ResponseBody
	public ResponseEntity<Object> getListOfHouseHoldByStationNumberIfFlood(@RequestParam List<String> stations) {
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
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/personInfo")
	@ResponseBody
	public ResponseEntity<Object> getInfoAndMedicalRecordOfPersonByFullName(@RequestParam String firstName,
			@RequestParam String lastName) {
		List<Map<String, String>> personByFullNameInfoAndMedicalRecord = new ArrayList<Map<String, String>>();
		
		try {
			personByFullNameInfoAndMedicalRecord = personInfoService
					.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
			return ResponseEntity.status(HttpStatus.OK).body(personByFullNameInfoAndMedicalRecord);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/communityEmail")
	@ResponseBody
	public ResponseEntity<Object> getEmailOfResidentsOfCity(@RequestParam String city) {
		List<Map<String, String>> listOfEmailsOfResidentsOfCity = new ArrayList<Map<String, String>>();
		
		try {
			listOfEmailsOfResidentsOfCity = communityEmailService.getEmailOfResidentsOfCity(city);

		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
		return ResponseEntity.status(HttpStatus.OK).body(listOfEmailsOfResidentsOfCity);
	}

	@Override
	public ResponseEntity<Object> returnResponseEntityEmptyAndCode404() {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
}
