package com.safetynet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import com.safetynet.api.service.ReadPersonDataFromFileImpl;
import com.safetynet.api.service.UploadDataFileService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {
 @Autowired
 UploadDataFileService  uploadDataService;

	@Override
	public void run(String... args) throws Exception {
		uploadDataService.getPersonsFromFile();
		//uploadDataService.getFireStationsFromFile();
		//uploadDataService.getMedicalRecordsFromFile();
	}

}
