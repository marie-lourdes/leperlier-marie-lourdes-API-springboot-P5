package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;

@Component
public class MedicalRecordREADONLYRepositoryImpl implements IMedicalRecordREADONLYRepository {
	private List<Optional<MedicalRecord>>  listOfMedicalRecordsFoundByFullName;
	private	List<MedicalRecord> medicalRecords;
	private Optional<MedicalRecord> medicalRecordFoundByFullName;
	
	@Autowired
	ReadMedicalRecordDataFromFileImpl readMedicalRecords;

	@Override
	public List<MedicalRecord> findAll() throws IOException{
		return readMedicalRecords.readFile();
	}
	
	@Override
	public  List<Optional<MedicalRecord>> findByFirstNameAndLastName(String firstName, String lastName ){
		medicalRecords = new ArrayList<MedicalRecord>();
		listOfMedicalRecordsFoundByFullName = new ArrayList< Optional<MedicalRecord>>();
		medicalRecordFoundByFullName= Optional.empty();
		try {
			medicalRecords = readMedicalRecords.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 medicalRecords.forEach(elem -> {
			String firstNameMedicalRecord = elem.getFirstName();
			String lastNameMedicalRecord = elem.getLastName();
			if (firstNameMedicalRecord.equals(firstName ) && lastNameMedicalRecord.equals(lastName)) {
				System.out.println("medicalRecord found by first and last name of person" + elem);
				
				medicalRecordFoundByFullName=Optional.ofNullable(elem);
				listOfMedicalRecordsFoundByFullName.add(medicalRecordFoundByFullName); 
			}		 	
	});
		 System.out.println("listOfMedicalRecordsFoundByFullName :" +listOfMedicalRecordsFoundByFullName);
		 return listOfMedicalRecordsFoundByFullName;
	 }
/*	@Override
	public Optional<MedicalRecord> findByName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}*/
}
