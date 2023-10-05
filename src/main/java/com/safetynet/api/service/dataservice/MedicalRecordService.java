package com.safetynet.api.service.dataservice;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IMedicalRecordCRUDRepository;
import com.safetynet.api.repository.MedicalRecordREADONLYRepositoryImpl;

@Service
public class MedicalRecordService {
	@Autowired
	private IMedicalRecordCRUDRepository medicalRecordRepository;

	@Autowired
	private MedicalRecordREADONLYRepositoryImpl medicalRecordRepositoryFile;

	// --------------------repository avec source de donnéees BDD---------
	/*
	 * public List<MedicalRecord> getAllMedicalRecords() { return
	 * (List<MedicalRecord>) medicalRecordRepository.findAll(); }
	 */

	// --------------------repository avec source de donnéees fichier Json---------
	public List<MedicalRecord> getMedicalRecordsFromFile() throws IOException {
		return medicalRecordRepositoryFile.findAll();
	}

	/*public Optional<MedicalRecord> getOneMedicalRecordById(Long id) {
		Optional<MedicalRecord> medicalRecordFoundById = Optional
				.ofNullable(medicalRecordRepository.findById(id).orElseThrow(() -> new NullPointerException(
						" an error has occured,this medical record " + id + " doesn't exist, try again ")));
		return medicalRecordFoundById;
	}*/
	
	public List<Optional<MedicalRecord>> getOneMedicalRecordById(String id ) {
		return medicalRecordRepositoryFile.findById(id);
	}
	
	public List<Optional<MedicalRecord>> getOneMedicalRecordByFullName(String firstName, String lastName) {
		return medicalRecordRepositoryFile.findByFirstNameAndLastName(firstName,  lastName );
	}

//use IWriter
	public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord updateOneMedicalRecordById(MedicalRecord medicalRecord, Long id) {
		return medicalRecordRepository.save(medicalRecord);
	}

	public void deleteOneMedicalRecordByName(MedicalRecord medicalRecord) {
		medicalRecordRepository.delete(medicalRecord);
	}
}
