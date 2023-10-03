package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.IMedicalRecordREADONLYRepository;
@Service
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
