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
import com.safetynet.api.utils.ConstantsRequestResponseHttp;
import com.safetynet.api.utils.IResponseHTTPEmpty404;

@RestController
public class AlertsController implements IResponseHTTPEmpty404<Object> {
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
	public ResponseEntity<List<Map<String, String>>> getAllAdultsAndChildsNearOfFireStations(
			@RequestParam String stationNumber) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_ADULTSANDCHILDS_OF_STATIONNUMBER, stationNumber);
		
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
			
			ResponseEntity<List<Map<String, String>>>ResponseValid = ResponseEntity.status(HttpStatus.OK).body(listOfResidentsOfStationNumber);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_ADULTSANDCHILDS_OF_STATIONNUMBER, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
				
			ResponseEntity<List<Map<String, String>>>ResponseNoValid =  new ResponseEntity<List<Map<String, String>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_ADULTSANDCHILDS_OF_STATIONNUMBER, ResponseNoValid);
			return ResponseNoValid;
		}
	}

	@GetMapping("/childAlert")
	@ResponseBody
	public ResponseEntity<Object> getChildsAndMembersOfHouseHoldByAddress(@RequestParam String address) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_CHILDSANDMEMBERSOFHOUSEHOLD_BY_ADDRESS, address);
		
		List<Map<String, String>> childs = new ArrayList<Map<String, String>>();
		try {
			childs = childAlertService.getChildsAndMembersOfHouseHold(address);
			
			ResponseEntity<Object>ResponseValid = ResponseEntity.status(HttpStatus.OK).body(childs);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_CHILDSANDMEMBERSOFHOUSEHOLD_BY_ADDRESS, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
					
			ResponseEntity<Object>ResponseNoValid =  this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_CHILDSANDMEMBERSOFHOUSEHOLD_BY_ADDRESS, ResponseNoValid);
			return ResponseNoValid;
		}
	}

	@GetMapping("/phoneAlert")
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getPhonesOfResidentsByStationNumber(
			@RequestParam String stationNumber) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_PHONES_OF_RESIDENTS_BY_STATIONNUMBER, stationNumber);
		
		List<Map<String, String>> listOfPhonesOfResidentsByStationNumber = new ArrayList<Map<String, String>>();
		try {
			listOfPhonesOfResidentsByStationNumber = phoneAlertService
					.getListOfPhonesOfResidentsOfStationNumber(stationNumber);
			
			ResponseEntity<List<Map<String, String>>> ResponseValid = ResponseEntity.status(HttpStatus.OK).body(listOfPhonesOfResidentsByStationNumber);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_PHONES_OF_RESIDENTS_BY_STATIONNUMBER, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
					
			ResponseEntity<List<Map<String, String>>> ResponseNoValid = new ResponseEntity<List<Map<String, String>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_PHONES_OF_RESIDENTS_BY_STATIONNUMBER, ResponseNoValid);
			return ResponseNoValid;
		}
	}

	@GetMapping("/fire")
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getListOfResidentsAndFireStationNearFire(
			@RequestParam String address) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_RESIDENTSANDSTATIONNUMBER_NEAR_FIRE, address);
		try {
			List<Map<String, String>> listOfResidentsAndFireStationNearFire = new ArrayList<Map<String, String>>();
			listOfResidentsAndFireStationNearFire = fireService.getListOfResidentsAndFireStationNearFire(address);
			
			ResponseEntity<List<Map<String, String>>> ResponseValid =ResponseEntity.status(HttpStatus.OK).body(listOfResidentsAndFireStationNearFire);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_RESIDENTSANDSTATIONNUMBER_NEAR_FIRE, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<List<Map<String, String>>> ResponseNoValid = new ResponseEntity<List<Map<String, String>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_RESIDENTSANDSTATIONNUMBER_NEAR_FIRE, ResponseNoValid);
			return ResponseNoValid;
		}
	}

	@GetMapping("/flood/stations")
	@ResponseBody
	public ResponseEntity<List<List<Map<String, String>>>> getListOfHouseHoldByStationNumberIfFlood(
			@RequestParam List<String> stations) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_HOUSEHOLD_BY_STATIONNUMBER_IF_FLOOD, stations);
		
		List<List<Map<String, String>>> listOfHouseHoldByStationNumberParams = new ArrayList<List<Map<String, String>>>();
		try {
			for (String station : stations) {
				List<List<Map<String, String>>> listOfHouseHoldByStationNumber = new ArrayList<List<Map<String, String>>>();
				listOfHouseHoldByStationNumber = floodService.getListOfHouseHoldByStationNumber(station);
				listOfHouseHoldByStationNumberParams.addAll(listOfHouseHoldByStationNumber);
			}
			
			ResponseEntity<List<List<Map<String, String>>>> ResponseValid =ResponseEntity.status(HttpStatus.OK).body(listOfHouseHoldByStationNumberParams);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_HOUSEHOLD_BY_STATIONNUMBER_IF_FLOOD, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<List<List<Map<String, String>>>> ResponseNoValid = new ResponseEntity<List<List<Map<String, String>>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_HOUSEHOLD_BY_STATIONNUMBER_IF_FLOOD, ResponseNoValid);
			return ResponseNoValid;		
		}
	}

	@GetMapping("/personInfo")
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getInfoAndMedicalRecordOfPersonByFullName(
			@RequestParam String firstName, @RequestParam String lastName) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_PERSONINFO_BY_FULLNAME, firstName, lastName);
		
		List<Map<String, String>> personByFullNameInfoAndMedicalRecord = new ArrayList<Map<String, String>>();
		try {
			personByFullNameInfoAndMedicalRecord = personInfoService
					.getInfoAndMedicalRecordOfPersonByFullName(firstName, lastName);
			
			ResponseEntity<List<Map<String, String>>>ResponseValid = ResponseEntity.status(HttpStatus.OK).body(personByFullNameInfoAndMedicalRecord);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_PERSONINFO_BY_FULLNAME, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<List<Map<String, String>>> ResponseNoValid = new ResponseEntity<List<Map<String, String>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_PERSONINFO_BY_FULLNAME, ResponseNoValid);
			return ResponseNoValid;	
		}
	}

	@GetMapping("/communityEmail")
	@ResponseBody
	public ResponseEntity<List<Map<String, String>>> getEmailOfResidentsOfCity(@RequestParam String city) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_GET_EMAIL_OF_RESIDENTS_BY_CITY, city);
		
		List<Map<String, String>> listOfEmailsOfResidentsOfCity = new ArrayList<Map<String, String>>();
		try {
			listOfEmailsOfResidentsOfCity = communityEmailService.getEmailOfResidentsOfCity(city);
			
			ResponseEntity<List<Map<String, String>>>ResponseValid = ResponseEntity.status(HttpStatus.OK).body(listOfEmailsOfResidentsOfCity);
			log.info(ConstantsRequestResponseHttp.RESPONSE_GET_EMAIL_OF_RESIDENTS_BY_CITY, ResponseValid);
			return ResponseValid;
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			ResponseEntity<List<Map<String, String>>> ResponseNoValid = new ResponseEntity<List<Map<String, String>>>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_GET_EMAIL_OF_RESIDENTS_BY_CITY, ResponseNoValid);
			return ResponseNoValid; 
		}
	}

	@Override
	public ResponseEntity<Object> returnResponseEntityEmptyAndCode404() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
	}
}
