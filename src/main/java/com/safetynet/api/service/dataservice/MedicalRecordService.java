package com.safetynet.api.service.dataservice;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MedicalRecordService {

	Logger log = LogManager.getLogger(MedicalRecordService.class);
	private final List<MedicalRecord> medicalRecords = new ArrayList<>();

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		log.debug("Adding medical record: {}", medicalRecord.getFirstName() + " " + medicalRecord.getLastName());

		medicalRecord.setId(medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		medicalRecords.add(medicalRecord);

		log.info("Medical record added successfully: {}",
				medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		return medicalRecord;
	}

	public MedicalRecord updateMedicalRecord(String id, MedicalRecord updatedMedicalRecord) {
		log.debug("Updating medical record for: {}", id);

		MedicalRecord existingMedicalRecordUpdated = new MedicalRecord();
		existingMedicalRecordUpdated = medicalRecords.stream().filter(medicalRecord -> medicalRecord.getId().equals(id))
				.findFirst().map(existingMedicalRecord -> {
					existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
					existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
					existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
					return existingMedicalRecord;
				}).orElseThrow(() -> new NullPointerException(
						"error occured with updating medical record" + updatedMedicalRecord + "not found "));

		log.info("Medical record updated successfully for: {}", id);
		return existingMedicalRecordUpdated;
	}

	public boolean deleteOneMedicalRecordById(String id) {
		log.debug("Deleting medical record for {} {}", id);

		boolean result = medicalRecords.removeIf(medicalRecord -> medicalRecord.getId().equals(id));
		if (!result) {
			log.error("Failed to delete medical record for {} {}", id);
			throw new NullPointerException("this medical record  to remove doesn't exist");
		} else {
			log.info("Medical record deleted successfully for {} {}", id);
		}
		return result;
	}

	public MedicalRecord getOneMedicalRecordById(String id) {
		log.debug("Retrieving  one medical record for {}", id);

		MedicalRecord personFoundById = new MedicalRecord();
		personFoundById = medicalRecords.stream().filter(medicalRecord -> medicalRecord.getId().equals(id)).findFirst()
				.map(existingMedicalRecord -> {
					return existingMedicalRecord;
				}).orElseThrow(() -> new NullPointerException("medical record not found with id"));

		log.info("Medical record retrieved successfully for: {}", id);
		return personFoundById;
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		// log.debug("Retrieving all medical records");
		if (medicalRecords.isEmpty()) {
			throw new NullPointerException("none medical record registered!");
		}
		log.info("All medical records retrieved successfully for: {}", medicalRecords);
		return medicalRecords;
	}
}