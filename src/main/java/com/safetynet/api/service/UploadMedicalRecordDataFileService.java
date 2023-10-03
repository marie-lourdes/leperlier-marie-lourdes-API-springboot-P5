package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.MedicalRecord;

public class UploadMedicalRecordDataFileService {
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	public List<MedicalRecord> getMedicalRecordsFromFile() throws IOException {
		return readMedicalRecords.readFile();
	}
}
