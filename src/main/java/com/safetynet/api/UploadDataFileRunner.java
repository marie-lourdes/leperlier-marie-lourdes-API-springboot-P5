package com.safetynet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {
 @Autowired
 PersonService  personService;
 
 @Autowired
 FireStationService  fireStationService;
 
 @Autowired
 MedicalRecordService  medicalRecordService;

	@Override
	public void run(String... args) throws Exception {
		//use to logging each element of file json 
		personService.getPersonsFromFile();
		fireStationService.getFireStationsFromFile();
		medicalRecordService.getMedicalRecordsFromFile();
		fireStationService.getFireStationsByNumber("3");
		personService.getOnePersonByFullName("Tenley","Boyd");
		
	}

}
