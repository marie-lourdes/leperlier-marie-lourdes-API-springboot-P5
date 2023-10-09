package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;

@Component
public class MedicalRecordRepositoryImpl implements IMedicalRecordRepository {
	
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException {
		return readMedicalRecords.readFile();
	}


}