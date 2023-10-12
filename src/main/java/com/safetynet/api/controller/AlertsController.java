package com.safetynet.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.alertssafetynetservice.SearchingResidentsOfStationNumberService;

@RestController
public class AlertsController {
	@Autowired
	SearchingResidentsOfStationNumberService residentsOfStationNumberService ;
	
	@GetMapping("/firestation")
	public List<Person> getFireStationById(@RequestParam String stationNumber) {
		return residentsOfStationNumberService.getResidentsOfStationNumber(stationNumber);
	}
}
