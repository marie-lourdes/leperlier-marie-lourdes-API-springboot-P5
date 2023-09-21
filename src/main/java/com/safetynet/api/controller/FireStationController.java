package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.dataservice.FireStationService;

import jakarta.validation.Valid;

public class FireStationController {
	@Autowired 
	FireStationService fireStationService; 
	
	@PostMapping("/firestation")
	public FireStation createFireStation( @Valid @RequestBody  FireStation fireStationCreated) {
		FireStation fireStation = new FireStation();
		fireStation.setStationNumber(fireStationCreated.getStationNumber());
		fireStation.setAddress(fireStationCreated.getAddress());
		return	fireStationService.saveFireStation(fireStation);
	}
}
