package com.safetynet.api.repository;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;
@Component
public class MedicalRecordREADONLYRepositoryImpl implements IMedicalRecordREADONLYRepository  {
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	public List<MedicalRecord> getMedicalRecordsFromFile() throws IOException {
		return readMedicalRecords.readFile();
	}
	
	@Override
	public List<MedicalRecord> findAll() throws IOException{
		return readMedicalRecords.readFile();
	}
}
