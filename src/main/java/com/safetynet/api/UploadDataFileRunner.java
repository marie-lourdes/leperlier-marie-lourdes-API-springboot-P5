package com.safetynet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.alertssafetynetservice.ChildAlertService;
import com.safetynet.api.service.alertssafetynetservice.FireService;
import com.safetynet.api.service.alertssafetynetservice.PhoneAlertService;
import com.safetynet.api.service.alertssafetynetservice.ResidentsOfStationNumberService;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {

	@Autowired
	PersonService personService;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;

	@Autowired
	ChildAlertService childAlertService ;
	
	@Autowired
	PhoneAlertService   phoneAlertService;
	
	@Autowired
	FireService fireService;
	@Override
	public void run(String... args) throws Exception {
		// medicalRecordService.getAllMedicalRecords();
		// use to logging each element of file json
		personService.getAllPersons();
		fireStationService.getFireStationsById("3");
		
		residentsOfStationNumberService.getListOfResidentsOfStationNumber("3");
		residentsOfStationNumberService.sortAdultsAndChildsOfListOfResidentsWithCountDown("3");
		
		childAlertService.getChildsAndMembersOfHouseHold("1509 Culver St");
		childAlertService.sortAdultsAndChildsOfListOfResidentsWithFullInfo("1509 Culver St");

		phoneAlertService.getListOfPhonesOfResidentsOfStationNumber("3");
		
		fireService.getListOfResidentsAndFireStationNearFire("1509 Culver St");
		// System.out.println("firestation APRES SUPPRESION "+
		// fireStationService.getFireStationsFromFile());
		// fireStationService.getFireStationsFromFile();

		/*
		 * fireStationService.getOneFireStationById("3");
		 * fireStationService.getFireStationsByAddress("489 Manchester St");
		 * personService.getOnePersonById("Tenley Boyd");
		 * medicalRecordService.getOneMedicalRecordById("Tenley Boyd");
		 */
	}

}