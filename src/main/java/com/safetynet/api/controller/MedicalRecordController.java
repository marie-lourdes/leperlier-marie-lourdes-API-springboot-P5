package com.safetynet.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
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

import com.safetynet.api.model.IDtoEmpty;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController implements IResponseHTTPEmpty <MedicalRecord>{
	private static final Logger log = LogManager.getLogger(MedicalRecordController.class);
	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord")
	@ResponseBody
	public ResponseEntity<Object> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordCreated = new MedicalRecord();

		try {
			medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordCreated);
	}

	@PutMapping("/medicalRecord")
	@ResponseBody
	public ResponseEntity<MedicalRecord> updateOneMedicalRecordById(@Valid @RequestBody MedicalRecord medicalRecord,
			@RequestParam String id) {
		MedicalRecord medicalRecordFoundById = new MedicalRecord();

		try {
			medicalRecordFoundById = medicalRecordService.updateOneMedicalRecordById(id, medicalRecord);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return this.returnResponseEntityEmptyAndCode404();
		}
		return ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);
	}

	@DeleteMapping("/medicalRecord")
	public ResponseEntity<Long> deleteOneMedicalRecordById(@RequestParam String id) {
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
	
	@Override
	public ResponseEntity<MedicalRecord> returnResponseEntityEmptyAndCode404() {
			ModelMapper modelMapper = new ModelMapper();
			IDtoEmpty dtoEmpty = new IDtoEmpty ("");
			MedicalRecord medicalRecord= modelMapper.map(dtoEmpty, MedicalRecord.class);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(medicalRecord);
		}
}
