package com.safetynet.api.service;

import java.io.FileNotFoundException;

import javax.json.JsonArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadDataFileService {
	@Autowired
	ReadPersonDataFromFileImpl  readPersons;
	
	@Autowired
	ReadFireStationDataFromFileImpl  readFireStations;
	
	@Autowired
	ReadMedicalRecordDataFromFileImpl  readMedicalRecords;

	public JsonArray getPersonsFromFile() throws FileNotFoundException {
	 return	readPersons.readFile();
	}
	
	public JsonArray getFireStationsFromFile() throws FileNotFoundException {
		 return	readFireStations.readFile();
		}
	
	public JsonArray getMedicalRecordsFromFile() throws FileNotFoundException {
		 return	readMedicalRecords.readFile();
		}
}
