package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;

@Component
public class MedicalRecordREADONLYRepositoryImpl implements IMedicalRecordREADONLYRepository {
	private List<Optional<MedicalRecord>> listOfMedicalRecordsFoundByName;
	private Optional<MedicalRecord> medicalRecordFoundByName;
	private Optional<MedicalRecord> medicalRecordFoundById;

	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException {
		return readMedicalRecords.readFile();
	}

	@Override
	public List<Optional<MedicalRecord>> findByFirstNameAndLastName(String firstName, String lastName) {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		listOfMedicalRecordsFoundByName = new ArrayList<Optional<MedicalRecord>>();
		medicalRecordFoundByName = Optional.empty();
		try {
			medicalRecords = readMedicalRecords.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medicalRecords.forEach(elem -> {
			String firstNameMedicalRecord = elem.getFirstName();
			String lastNameMedicalRecord = elem.getLastName();
			if (firstNameMedicalRecord.equals(firstName) && lastNameMedicalRecord.equals(lastName)) {
				System.out.println("medicalRecord found by first and last name of person" + elem);

				medicalRecordFoundByName = Optional.ofNullable(elem);
				listOfMedicalRecordsFoundByName.add(medicalRecordFoundByName);
			}
		});
		System.out.println("listOfMedicalRecordsFoundByName:" + listOfMedicalRecordsFoundByName);
		return listOfMedicalRecordsFoundByName;
	}

	@Override
	public Optional<MedicalRecord> findById(String id) {
		List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		medicalRecordFoundById = Optional.empty();
		try {
			medicalRecords = readMedicalRecords.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medicalRecords.forEach(elem -> {
			String idMedicalRecord = elem.getId();
			if (idMedicalRecord.toString().equals(id.toString())) {
						medicalRecordFoundById = Optional.ofNullable(elem);		
			}
		});
		System.out.println("medicalRecordFoundById:" + medicalRecordFoundById);
		return medicalRecordFoundById;
	}
}
