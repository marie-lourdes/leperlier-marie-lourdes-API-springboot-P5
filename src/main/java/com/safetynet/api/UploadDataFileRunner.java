package com.safetynet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.alertssafetynetservice.SearchingResidentsOfStationNumberService;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {
	 
 /*@Autowired
 PersonService  personService;*/
 
 @Autowired
 FireStationService  fireStationService;
 
 @Autowired
 MedicalRecordService  medicalRecordService;
 
 @Autowired
 SearchingResidentsOfStationNumberService  residentsOfStationNumberService;

	@Override
	public void run(String... args) throws Exception {
		//medicalRecordService.getAllMedicalRecords();
		//use to logging each element of file json 
		//personService.getPersonsFromFile();
	
	 
	    fireStationService.getFireStationsById("3");
	    residentsOfStationNumberService.getFireStationByStationNumber("3");
	  //  System.out.println("firestation APRES SUPPRESION "+	fireStationService.getFireStationsFromFile());
	   //fireStationService.getFireStationsFromFile();
	 
	  /*  fireStationService.getOneFireStationById("3");
		fireStationService.getFireStationsByAddress("489 Manchester St");
		personService.getOnePersonById("Tenley Boyd");
		medicalRecordService.getOneMedicalRecordById("Tenley Boyd");*/
	}

}