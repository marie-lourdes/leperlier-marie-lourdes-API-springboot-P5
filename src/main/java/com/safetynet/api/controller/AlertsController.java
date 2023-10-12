package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.service.alertssafetynetservice.SearchingResidentsOfStationNumberService;

@RestController
public class AlertsController {
	@Autowired
	SearchingResidentsOfStationNumberService residentsOfStationNumberService ;
}
