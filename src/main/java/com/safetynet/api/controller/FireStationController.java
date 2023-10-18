package com.safetynet.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.IResponseHTTPEmpty;
import com.safetynet.api.service.dataservice.FireStationService;

import jakarta.validation.Valid;

@RestController
public class FireStationController implements IResponseHTTPEmpty {
	@Autowired
	private FireStationService fireStationService;

	@PostMapping("/firestation")
	public ResponseEntity<?> createStationNumberOfFireStation(@Valid @RequestBody FireStation fireStation,
			@RequestParam String address) throws Exception {
		FireStation fireStationCreated = 	fireStationService.addStationNumberOfExistingFireStation(fireStation, address);
		if (fireStationCreated == null) {
			return returnResponseEntityEmptyAndCode404();
		}
		System.out.println(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
	}

	@PostMapping("/firestation/{stationNumber}")
	public ResponseEntity<?> createAddressOfFireStation(@Valid @RequestBody FireStation fireStation,
			@PathVariable String stationNumber) throws Exception {
		FireStation fireStationCreated = fireStationService.addAddressOfExistingFireStation(fireStation, stationNumber);
		if (fireStationCreated == null) {
			return returnResponseEntityEmptyAndCode404();
		}
		System.out.println(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStationCreated);
	}

	@PutMapping("/firestation/{id}")
	public ResponseEntity<?> updateOneFireStationById(@RequestBody FireStation firestation, @PathVariable String id) {
		FireStation firestationFoundById = fireStationService.updateFireStation(id, firestation);
		if (firestationFoundById == null) {
			return returnResponseEntityEmptyAndCode404();
		} 
		return ResponseEntity.status(HttpStatus.CREATED).body(firestationFoundById);	
	}

	@DeleteMapping("/firestation/{id}")
	public ResponseEntity<Long> deleteFireStationById(@PathVariable String id) {
		boolean isFireStationRemoved = fireStationService.deleteFireStationById(id);
		return isFireStationRemoved ? new ResponseEntity<Long>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/firestation")
	public ResponseEntity<Long> deleteFireStationByAddress(@RequestParam String address) {
		boolean isFireStationRemoved = fireStationService.deleteOneFireStationByAddress(address);
		return isFireStationRemoved ? new ResponseEntity<Long>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/firestation/{id}")
	public ResponseEntity<?> getFireStationById(@PathVariable String id) {
		List<FireStation> fireStationsById = fireStationService.getFireStationsById(id);	
			if (fireStationsById.isEmpty()) {
				return returnResponseEntityEmptyAndCode404();	
			}
		
		 
		return ResponseEntity.status(HttpStatus.OK).body(fireStationsById );
	}

	@GetMapping("/firestation/")
	public @ResponseBody ResponseEntity<?> getAllFireStations() {
		List<FireStation> allFireStations = fireStationService.getAllFireStations();
	/*	if (allFireStations == null) {
			return returnResponseEntityEmptyAndCode404();
		} */
		return ResponseEntity.status(HttpStatus.OK).body(allFireStations);
	}
}
