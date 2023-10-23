package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;

@Component
public class MedicalRecordRepositoryImpl implements IMedicalRecordRepository {
	private static final Logger log = LogManager.getLogger(MedicalRecordRepositoryImpl.class);
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException {
		log.debug("Reading datas medical records from file");
		return readMedicalRecords.readFile();
	}

}