package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;

@Component
public class MedicalRecordRepositoryImpl implements IMedicalRecordRepository {
	@Autowired
	private ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException {
		return readMedicalRecords.readFile();
	}

}
