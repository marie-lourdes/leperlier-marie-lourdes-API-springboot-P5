package com.safetynet.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping("/medicalRecord")
	@ResponseBody
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordCreated);
	}

	@PutMapping("/medicalRecord")
	@ResponseBody
	public ResponseEntity<?> updateOneMedicalRecordById(@Valid @RequestBody MedicalRecord medicalRecord,
			@RequestParam String id) {
		MedicalRecord medicalRecordFoundById = new MedicalRecord();

		try {
			medicalRecordFoundById = medicalRecordService.updateOneMedicalRecordById(id, medicalRecord);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		return ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);
	}

	@DeleteMapping("/medicalRecord")
	public ResponseEntity<?> deleteOneMedicalRecordById(@RequestParam String id) {
		try {
			boolean personIsRemoved = medicalRecordService.deleteOneMedicalRecordById(id);
			if (!personIsRemoved) {
				throw new NullPointerException(" Medical record of " + id + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}