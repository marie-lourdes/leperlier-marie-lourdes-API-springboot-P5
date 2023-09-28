package com.safetynet.api.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecords")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord)
			throws Exception {
		medicalRecordService.saveMedicalRecord(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);		
		// return medicalRecordService.saveMedicalRecord(medicalRecord);
	}

	@GetMapping("/medicalRecords")
	public @ResponseBody List<MedicalRecord> getAllMedicalRecords() {
		List<MedicalRecord> allMedicalRecord =  new ArrayList<MedicalRecord>();
		try {
			allMedicalRecord = medicalRecordService.getAllMedicalRecords();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allMedicalRecord;
	}

	@GetMapping("/medicalRecords/{id}")
	public Optional<MedicalRecord> getOneMedicalRecord(@PathVariable Long id) {
		Optional<MedicalRecord> medicalRecordFoundById = Optional
				.ofNullable(medicalRecordService.getOneMedicalRecordById(id).orElseThrow(() -> new NullPointerException(
						" an error has occured,this medical record" + id + "doesn't exist, try again ")));
		return medicalRecordFoundById;
	}

	// the id, first and last name cannot be modified
	@PutMapping("/medicalRecords/{id}")
	public ResponseEntity<Optional<MedicalRecord>> updateOneMedicalRecordById(
			@RequestBody MedicalRecord medicalRecordModified, @PathVariable Long id) {
		Optional<MedicalRecord> medicalRecordFoundById = this.getOneMedicalRecord(id);

		if (id == medicalRecordFoundById.get().getId()) {
			if (medicalRecordModified.getBirthdate() != null)
				medicalRecordFoundById.get().setBirthdate(medicalRecordModified.getBirthdate());
			if (medicalRecordModified.getMedications() != null)
				medicalRecordFoundById.get().setMedications(medicalRecordModified.getMedications());
			if (medicalRecordModified.getAllergies() != null)
				medicalRecordFoundById.get().setAllergies(medicalRecordModified.getAllergies());
			medicalRecordService.saveMedicalRecord(medicalRecordFoundById.get());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordFoundById);
	}

	@DeleteMapping("/medicalRecords/")
	public ResponseEntity<Long> deleteOneMedicalRecordByName(@RequestParam String firstName,
			@RequestParam String lastName) {
		List<MedicalRecord> medicalRecords = (List<MedicalRecord>) medicalRecordService.getAllMedicalRecords();

		medicalRecords.forEach(elem -> {
			String firstNameOfMedicalRecord = elem.getFirstName();
			String lastNameOfMedicalRecord = elem.getLastName();
			if (firstNameOfMedicalRecord.contains(firstName) && lastNameOfMedicalRecord.contains(lastName)) {
				medicalRecordService.deleteOneMedicalRecordByName(elem);

			}
		});

		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);

	}
}
