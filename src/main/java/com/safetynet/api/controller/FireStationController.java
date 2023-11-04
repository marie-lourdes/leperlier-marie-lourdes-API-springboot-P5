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
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

import jakarta.validation.Valid;

@RestController
public class FireStationController implements IResponseHTTPEmpty {
	private static final Logger log = LogManager.getLogger(FireStationController.class);
	@Autowired
	private FireStationService fireStationService;

	@PostMapping("/firestation")
	@ResponseBody
	public ResponseEntity<?> createStationNumberOfFireStation(@Valid @RequestBody FireStation fireStation,
			@RequestParam String address) {
		FireStation fireStationCreated = new FireStation();

		try {
			fireStationCreated = fireStationService.addStationNumberOfFireStationWithExistingAddress(address,fireStation);
			return ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@PostMapping("/firestation/{stationNumber}")
	@ResponseBody
	public ResponseEntity<?> createAddressOfFireStation(@Valid @RequestBody FireStation fireStation,
			@PathVariable String stationNumber) {
		FireStation fireStationCreated = new FireStation();

		try {
			fireStationCreated = fireStationService.addAddressOfFireStationWithExistingStationNumber(stationNumber,fireStation);
			return ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@PutMapping("/firestation")
	@ResponseBody
	public ResponseEntity<?> updateOneFireStationByAddress(@Valid @RequestBody FireStation firestation,
			@RequestParam String address) {
		FireStation firestationFoundByAddress;

		try {
			firestationFoundByAddress = fireStationService.updateFireStationByAddress(address, firestation);
			return ResponseEntity.status(HttpStatus.OK).body(firestationFoundByAddress);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@DeleteMapping("/firestation/{stationNumber}")
	public ResponseEntity<Long> deleteFireStationByStationNumber(@PathVariable String stationNumber) {
		try {
			boolean fireStationIsRemoved = fireStationService.deleteFireStationByStationNumber(stationNumber);
			if (!fireStationIsRemoved) {
				throw new NullPointerException(
						" Firestation with this station number: " + stationNumber + "  to delete not found");
			}
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/firestation")
	public ResponseEntity<Long> deleteFireStationByAddress(@RequestParam String address) {
		try {
			boolean fireStationIsRemoved = fireStationService.deleteFireStationByAddress(address);
			if (!fireStationIsRemoved) {
				throw new NullPointerException(" Firestation with this address: " + address + "  to delete not found");
			}
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
	}
}
