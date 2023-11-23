package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.utils.Constants;
import com.safetynet.api.utils.ICheckingDuplicatedObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalRecordService implements ICheckingDuplicatedObject<MedicalRecord> {
	private static final Logger log = LogManager.getLogger(MedicalRecordService.class);

	private List<MedicalRecord> medicalRecords = new ArrayList<>();

	public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) throws IllegalArgumentException {
		log.debug("Adding medical record: {}", medicalRecord.getFirstName() + " " + medicalRecord.getLastName());

		boolean isObjectDuplicated = this.isMedicalRecordDuplicatedById(medicalRecords, medicalRecord);

		if (isObjectDuplicated) {
			throw new IllegalArgumentException ("Failed to add this medical record, medical record already exist" + medicalRecord);
		} else {
			String fullName = medicalRecord.getFirstName() + " " + medicalRecord.getLastName();
			medicalRecord.setId(fullName);
			medicalRecords.add(medicalRecord);

			log.info("Medical record added successfully: {}", medicalRecord);
			return medicalRecord;
		}
	}

	public MedicalRecord updateOneMedicalRecordById(String id, MedicalRecord updatedMedicalRecord)
			throws NullPointerException {
		log.debug("Updating medical record for: {}", id);

		MedicalRecord existingMedicalRecordUpdated = new MedicalRecord();
		existingMedicalRecordUpdated = medicalRecords.stream().filter(medicalRecord -> medicalRecord.getId().equals(id))
				.findFirst().map(existingMedicalRecord -> {
					existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
					existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
					existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
					return existingMedicalRecord;
				}).orElseThrow(() -> new NullPointerException(
						"Failed to update medical record, " + updatedMedicalRecord.getId() + Constants.NOT_FOUND));

		log.info("Medical record updated successfully for: {}", existingMedicalRecordUpdated);
		return existingMedicalRecordUpdated;
	}

	public boolean deleteOneMedicalRecordById(String id) throws NullPointerException {
		log.debug("Deleting medical record for {}", id);

		boolean result = false;
		result = medicalRecords.removeIf(medicalRecord -> medicalRecord.getId().equals(id));
		if (!result) {
			log.error("Failed to delete medical record for {}", id);
		} else {
			log.info("Medical record deleted successfully for {}", id);
		}

		return result;
	}

	public MedicalRecord getOneMedicalRecordById(String id) throws NullPointerException {
		log.debug("Retrieving  one medical record for {}", id);

		MedicalRecord personFoundById = new MedicalRecord();
		personFoundById = medicalRecords.stream().filter(medicalRecord -> medicalRecord.getId().equals(id)).findFirst()
				.map(existingMedicalRecord -> {
					return existingMedicalRecord;
				}).orElseThrow(() -> new NullPointerException("Medical record for: " + id + Constants.NOT_FOUND));

		log.info("Medical record retrieved successfully for: {}", id);
		return personFoundById;
	}

	public List<MedicalRecord> getAllMedicalRecords() throws NullPointerException {
		log.debug("Retrieving all medical records");

		if (medicalRecords.isEmpty()) {
			log.error("Failed to retrieve all  medical records ");
			throw new NullPointerException("None medical record registered!");
		} else {
			log.info("All medical records retrieved successfully: {}", medicalRecords);
		}

		return medicalRecords;
	}

	public boolean isMedicalRecordDuplicatedById(List<MedicalRecord> medicalRecords, MedicalRecord medicalRecord) {
		return this.isObjectDuplicated(medicalRecords, medicalRecord);
	}

	@Override
	public boolean isObjectDuplicated(List<MedicalRecord> medicalRecords, MedicalRecord medicalRecord) {
		boolean isObjectDuplicated = false;
		for (MedicalRecord medicalRecordExisting : medicalRecords) {
			if (medicalRecordExisting.getFirstName().toString().equals(medicalRecord.getFirstName().toString())
					&& medicalRecordExisting.getLastName().toString().equals(medicalRecord.getLastName().toString())) {
				isObjectDuplicated = true;
			}
		}

		return isObjectDuplicated;
	}
}
