package com.safetynet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecords")
	public MedicalRecord createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecordCreated) throws Exception {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName(medicalRecordCreated.getFirstName());
		medicalRecord.setLastName(medicalRecordCreated.getLastName());
		medicalRecord.setBirthdate(medicalRecordCreated.getBirthdate());
		medicalRecord.setMedications(medicalRecordCreated.getMedications());
		medicalRecord.setAllergies(medicalRecordCreated.getAllergies());
		return medicalRecordService.saveMedicalRecord(medicalRecord);
	}

	@GetMapping("/medicalRecords")
	public @ResponseBody List<MedicalRecord> getAllMedicalRecords() {
		List<MedicalRecord> allMedicalRecord = null;
		try {
			allMedicalRecord = medicalRecordService.getAllMedicalRecords();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allMedicalRecord;
	}
}
