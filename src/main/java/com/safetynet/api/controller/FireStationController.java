package com.safetynet.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.utils.ConstantsRequestResponseHttp;
import com.safetynet.api.utils.IResponseHTTPEmpty400;
import com.safetynet.api.utils.IResponseHTTPEmpty404;

import jakarta.validation.Valid;

@RestController
public class FireStationController implements IResponseHTTPEmpty404<FireStation>, IResponseHTTPEmpty400<FireStation> {
	private static final Logger log = LogManager.getLogger(FireStationController.class);

	@Autowired
	private FireStationService fireStationService;

	@PostMapping("/firestation")
	@ResponseBody
	public ResponseEntity<FireStation> createStationNumberOfFireStation(@Valid @RequestBody FireStation fireStation,
			@RequestParam String address) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_POST_STATIONNUMBER_OF_FIRESTATION, fireStation);
		
		FireStation fireStationCreated = new FireStation();
		try {
			fireStationCreated = fireStationService.addStationNumberOfFireStationWithExistingAddress(address,
					fireStation);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<FireStation> ResponseEntityNoValid =  this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.RESPONSE_POST_STATIONNUMBER_OF_FIRESTATION, ResponseEntityNoValid);
			return ResponseEntityNoValid;
		}
		
		ResponseEntity<FireStation> ResponseEntityValid = ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
		log.info(ConstantsRequestResponseHttp.RESPONSE_POST_STATIONNUMBER_OF_FIRESTATION, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@PostMapping("/firestation/{stationNumber}")
	@ResponseBody
	public ResponseEntity<FireStation> createAddressOfFireStation(@Valid @RequestBody FireStation fireStation,
			@PathVariable String stationNumber) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_POST_ADDRESS_OF_FIRESTATION, fireStation);
		
		FireStation fireStationCreated = new FireStation();
		try {
			fireStationCreated = fireStationService.addAddressOfFireStationWithExistingStationNumber(stationNumber,
					fireStation);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<FireStation> ResponseEntityNoValid404 =  this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.RESPONSE_POST_ADDRESS_OF_FIRESTATION, ResponseEntityNoValid404);
			return ResponseEntityNoValid404;		
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			
			ResponseEntity<FireStation> ResponseEntityNoValid400 =  this.returnResponseEntityEmptyAndCode400();
			log.error(ConstantsRequestResponseHttp.RESPONSE_POST_ADDRESS_OF_FIRESTATION, ResponseEntityNoValid400 );
			return ResponseEntityNoValid400;	
		}
		
		ResponseEntity<FireStation> ResponseEntityValid = ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
		log.info(ConstantsRequestResponseHttp.RESPONSE_POST_ADDRESS_OF_FIRESTATION, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@PutMapping("/firestation")
	@ResponseBody
	public ResponseEntity<FireStation> updateOneFireStationByAddress(@Valid @RequestBody FireStation fireStation,
			@RequestParam String address) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_PUT_FIRESTATION, fireStation);
		
		FireStation firestationFoundByAddress = new FireStation();
		try {
			firestationFoundByAddress = fireStationService.updateFireStationByAddress(address, fireStation);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<FireStation> ResponseEntityNoValid404 =  this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.RESPONSE_PUT_FIRESTATION, ResponseEntityNoValid404);
			return ResponseEntityNoValid404;	
		}
		
		ResponseEntity<FireStation> ResponseEntityValid = ResponseEntity.status(HttpStatus.OK).body(firestationFoundByAddress);
		log.info(ConstantsRequestResponseHttp.RESPONSE_PUT_FIRESTATION, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@DeleteMapping("/firestation/{stationNumber}")
	public ResponseEntity<Long> deleteFireStationByStationNumber(@PathVariable String stationNumber) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_DELETE_STATIONNUMBER_OF_FIRESTATION, stationNumber);
		
		try {
			boolean fireStationIsRemoved = fireStationService.deleteFireStationByStationNumber(stationNumber);
			if (!fireStationIsRemoved) {
				throw new NullPointerException(
						" Firestation with this station number: " + stationNumber + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<Long> ResponseEntityNoValid404 =  new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_DELETE_STATIONNUMBER_OF_FIRESTATION, ResponseEntityNoValid404);
			return ResponseEntityNoValid404;	
		}
		
		ResponseEntity<Long> ResponseEntityValid = new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		log.info(ConstantsRequestResponseHttp.RESPONSE_DELETE_STATIONNUMBER_OF_FIRESTATION, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@DeleteMapping("/firestation")
	public ResponseEntity<Long> deleteFireStationByAddress(@RequestParam String address) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_DELETE_ADDRESS_OF_FIRESTATION, address);
		
		try {
			boolean fireStationIsRemoved = fireStationService.deleteFireStationByAddress(address);
			if (!fireStationIsRemoved) {
				throw new NullPointerException(" Firestation with this address: " + address + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<Long> ResponseEntityNoValid404 =  new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_DELETE_ADDRESS_OF_FIRESTATION, ResponseEntityNoValid404);
			return ResponseEntityNoValid404;	
		}
		
		ResponseEntity<Long> ResponseEntityValid = new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		log.info(ConstantsRequestResponseHttp.RESPONSE_DELETE_ADDRESS_OF_FIRESTATION, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@Override
	public ResponseEntity<FireStation> returnResponseEntityEmptyAndCode404() {
		return new ResponseEntity<FireStation>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<FireStation> returnResponseEntityEmptyAndCode400() {
		return new ResponseEntity<FireStation>(HttpStatus.BAD_REQUEST);
	}
}
