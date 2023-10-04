package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;

@Component
public class MedicalRecordREADONLYRepositoryImpl implements IMedicalRecordREADONLYRepository {
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException{
		return readMedicalRecords.readFile();
	}
	
/*	@Override
	public Optional<Person> findByName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}*/
}
