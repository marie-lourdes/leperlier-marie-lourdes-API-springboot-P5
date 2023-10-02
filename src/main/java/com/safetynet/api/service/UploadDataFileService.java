package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

@Service
public class UploadDataFileService {
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	public List<Person> getPersonsFromFile() throws IOException {
		return readPersons.readFile();
	}

	public List<FireStation> getFireStationsFromFile() throws IOException {
		return readFireStations.readFile();
	}

	public List<MedicalRecord> getMedicalRecordsFromFile() throws IOException {
		return readMedicalRecords.readFile();
	}
}
