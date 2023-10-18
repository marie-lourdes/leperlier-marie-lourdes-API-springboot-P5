package com.safetynet.api.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.IResponseHTTPEmpty;
import com.safetynet.api.service.dataservice.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController implements IResponseHTTPEmpty {
	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord/")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);
		System.out.println(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordCreated);
	}

	@PutMapping("/medicalRecord/{id}")
	public ResponseEntity<?> updateOneMedicalRecordById(@RequestBody MedicalRecord medicalRecord,
			@PathVariable String id) {
		MedicalRecord medicalRecordFoundById = medicalRecordService.updateMedicalRecord(id, medicalRecord);
		if (medicalRecordFoundById != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordFoundById);
		} else {
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@DeleteMapping("/medicalRecord/{id}")
	public ResponseEntity<?> deleteOneMedicalRecordByName(@PathVariable String id) {
		boolean isMedicalRecordRemoved = medicalRecordService.deleteOneMedicalRecordById(id);
		return isMedicalRecordRemoved ? new ResponseEntity<Long>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/medicalRecord/{id}")
	public ResponseEntity<?> getOneMedicalRecord(@PathVariable String id) {
		Optional<MedicalRecord> medicalRecordFoundById = medicalRecordService.getOneMedicalRecordById(id);
		if (medicalRecordFoundById != null) {
			return ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);
		} else {
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/medicalRecord/")
	public @ResponseBody ResponseEntity<?> getAllMedicalRecords() {
		List<MedicalRecord> allMedicalRecords = medicalRecordService.getAllMedicalRecords();
		if (allMedicalRecords != null) {
			return ResponseEntity.status(HttpStatus.OK).body(allMedicalRecords);
		} else {
			return returnResponseEntityEmptyAndCode404();
		}
	}
}
