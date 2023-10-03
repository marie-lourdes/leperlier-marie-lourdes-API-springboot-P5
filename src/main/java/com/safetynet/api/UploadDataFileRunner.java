package com.safetynet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.PersonREADONLYRepositoryImpl;

@Component
public class UploadDataFileRunner implements CommandLineRunner {
 /*@Autowired
 UploadDataFileService  uploadDataService;*/
 @Autowired
PersonREADONLYRepositoryImpl  uploadPersonDataService;

	@Override
	public void run(String... args) throws Exception {
		uploadPersonDataService.getPersonsFromFile();
		/*uploadDataService.getFireStationsFromFile();
		uploadDataService.getMedicalRecordsFromFile();*/
	}

}
