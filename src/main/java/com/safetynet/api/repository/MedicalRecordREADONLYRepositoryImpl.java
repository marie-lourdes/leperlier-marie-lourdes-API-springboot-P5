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
public class MedicalRecordREADONLYRepositoryImpl implements IMedicalRecordREADONLYRepository {
	private List<Optional<MedicalRecord>> listOfMedicalRecordsFoundById;
	private List<MedicalRecord> medicalRecords;
	private Optional<MedicalRecord> medicalRecordFoundById;

	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException {
		return readMedicalRecords.readFile();
	}

	@Override
	public List<Optional<MedicalRecord>> findByFirstNameAndLastName(String firstName, String lastName) {
		medicalRecords = new ArrayList<MedicalRecord>();
		listOfMedicalRecordsFoundById = new ArrayList<Optional<MedicalRecord>>();
		medicalRecordFoundById = Optional.empty();
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

				medicalRecordFoundById = Optional.ofNullable(elem);
				listOfMedicalRecordsFoundById.add(medicalRecordFoundById);
			}
		});
		System.out.println("listOfMedicalRecordsFoundById :" + listOfMedicalRecordsFoundById);
		return listOfMedicalRecordsFoundById;
	}

	@Override
	public List<Optional<MedicalRecord>> findById(String id) {
		medicalRecords = new ArrayList<MedicalRecord>();
		listOfMedicalRecordsFoundById = new ArrayList<Optional<MedicalRecord>>();
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
				System.out.println("medicalRecord found by id (first and last name of person)" + elem);

				medicalRecordFoundById = Optional.ofNullable(elem);
				listOfMedicalRecordsFoundById.add(medicalRecordFoundById);
			}
		});
		System.out.println("listOfMedicalRecordsFoundById :" + listOfMedicalRecordsFoundById);
		return listOfMedicalRecordsFoundById;
	}
}
