package com.safetynet.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController implements IResponseHTTPEmpty {
	private static final Logger log = LogManager.getLogger(MedicalRecordController.class);
	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord/")
	@ResponseBody
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordCreated);
	}

	@PutMapping("/medicalRecord/{id}")
	@ResponseBody
	public ResponseEntity<?> updateOneMedicalRecordById(@RequestBody MedicalRecord medicalRecord,
			@PathVariable String id) {
		MedicalRecord medicalRecordFoundById;

		try {
			medicalRecordFoundById = medicalRecordService.updateMedicalRecord(id, medicalRecord);
			return ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);
		} catch (NullPointerException e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@DeleteMapping("/medicalRecord/{id}")
	public ResponseEntity<?> deleteOneMedicalRecordByName(@PathVariable String id) {
		try {
			medicalRecordService.deleteOneMedicalRecordById(id);
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/medicalRecord/{id}")
	@ResponseBody
	public ResponseEntity<?> getOneMedicalRecord(@PathVariable String id) {
		MedicalRecord medicalRecordFoundById;

		try {
			medicalRecordFoundById = medicalRecordService.getOneMedicalRecordById(id);
			return ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@GetMapping("/medicalRecord/")
	@ResponseBody
	public ResponseEntity<?> getAllMedicalRecords() {
		List<MedicalRecord> allMedicalRecords;

		try {
			allMedicalRecords = medicalRecordService.getAllMedicalRecords();
			return ResponseEntity.status(HttpStatus.OK).body(allMedicalRecords);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}
}
