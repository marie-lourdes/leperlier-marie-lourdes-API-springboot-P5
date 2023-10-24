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

	private static final Logger log = LogManager.getLogger(MedicalRecordService.class);
	private final List<MedicalRecord> medicalRecords = new ArrayList<>();

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
		log.debug("Adding medical record: {}", medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
		try {
			medicalRecord.setId(medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			medicalRecords.add(medicalRecord);
		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in creating medical record");
		}
		log.info("Medical record added successfully: {}", medicalRecord);
		return medicalRecord;
	}

	public MedicalRecord updateMedicalRecord(String id, MedicalRecord updatedMedicalRecord) {
		log.debug("Updating medical record for: {}", id);

		MedicalRecord existingMedicalRecordUpdated = new MedicalRecord();
		try {
			existingMedicalRecordUpdated = medicalRecords.stream()
					.filter(medicalRecord -> medicalRecord.getId().equals(id)).findFirst()
					.map(existingMedicalRecord -> {
						existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
						existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
						existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
						return existingMedicalRecord;
					}).orElseThrow(() -> new NullPointerException(
							"Failed to update medical record, " + updatedMedicalRecord.getId() + " not found"));
		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in updating medical record");
		}
		log.info("Medical record updated successfully for: {}", updatedMedicalRecord);
		return existingMedicalRecordUpdated;
	}

	public boolean deleteOneMedicalRecordById(String id) {
		log.debug("Deleting medical record for {}", id);

		boolean result = false;
		try {
			result = medicalRecords.removeIf(medicalRecord -> medicalRecord.getId().equals(id));
			if (!result) {
				log.error("Failed to delete medical record for {}", id);
				throw new NullPointerException(" Medical record of " + id + "  to delete not found");
			} else {
				log.info("Medical record deleted successfully for {}", id);
			}
		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in deleting  medical record");
		}
		return result;
	}

	public MedicalRecord getOneMedicalRecordById(String id) {
		log.debug("Retrieving  one medical record for {}", id);

		MedicalRecord personFoundById = new MedicalRecord();
		try {
			personFoundById = medicalRecords.stream().filter(medicalRecord -> medicalRecord.getId().equals(id))
					.findFirst().map(existingMedicalRecord -> {
						return existingMedicalRecord;
					}).orElseThrow(() -> new NullPointerException("Medical record not found for: " + id));

		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in getting one  medical record by id");
		}
		log.info("Medical record retrieved successfully for: {}", id);
		return personFoundById;
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		log.debug("Retrieving all medical records");
		try {
			if (medicalRecords.isEmpty()) {
				log.error("Failed to retrieve all  medical records ");
				throw new NullPointerException("None medical record registered!");
			} else {
				log.info("All medical records retrieved successfully: {}", medicalRecords);
			}
		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in getting all medical records");
		}
		return medicalRecords;
	}
}