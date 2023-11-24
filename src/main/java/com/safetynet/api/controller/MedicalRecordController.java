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
import com.safetynet.api.utils.ConstantsRequestResponseHttp;
import com.safetynet.api.utils.IResponseHTTPEmpty400;
import com.safetynet.api.utils.IResponseHTTPEmpty404;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController
		implements IResponseHTTPEmpty404<MedicalRecord>, IResponseHTTPEmpty400<MedicalRecord> {
	private static final Logger log = LogManager.getLogger(MedicalRecordController.class);

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_POST_MEDICALRECORD, medicalRecord);

		MedicalRecord medicalRecordCreated = new MedicalRecord();
		try {
			medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());

			ResponseEntity<MedicalRecord> ResponseEntityNoValid = this.returnResponseEntityEmptyAndCode400();
			log.error(ConstantsRequestResponseHttp.RESPONSE_POST_MEDICALRECORD, ResponseEntityNoValid);
			return ResponseEntityNoValid;
		}
		
		ResponseEntity<MedicalRecord> ResponseEntityValid = ResponseEntity.status(HttpStatus.CREATED)
				.body(medicalRecordCreated);
		log.info(ConstantsRequestResponseHttp.RESPONSE_POST_MEDICALRECORD, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@PutMapping("/medicalRecord")
	public ResponseEntity<MedicalRecord> updateOneMedicalRecordById(@Valid @RequestBody MedicalRecord medicalRecord,
			@RequestParam String id) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_PUT_MEDICALRECORD, medicalRecord);
		
		MedicalRecord medicalRecordFoundById = new MedicalRecord();
		try {
			medicalRecordFoundById = medicalRecordService.updateOneMedicalRecordById(id, medicalRecord);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<MedicalRecord> ResponseEntityNoValid = this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.REQUEST_PUT_MEDICALRECORD, ResponseEntityNoValid);
			return ResponseEntityNoValid;
		}
		
		ResponseEntity<MedicalRecord> ResponseEntityValid = ResponseEntity.status(HttpStatus.OK).body(medicalRecordFoundById);		
		log.info(ConstantsRequestResponseHttp.REQUEST_PUT_MEDICALRECORD, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@DeleteMapping("/medicalRecord")
	public ResponseEntity<Long> deleteOneMedicalRecordById(@RequestParam String id) {
		log.debug(ConstantsRequestResponseHttp.REQUEST_DELETE_MEDICALRECORD, id);
		
		try {
			boolean personIsRemoved = medicalRecordService.deleteOneMedicalRecordById(id);
			if (!personIsRemoved) {
				throw new NullPointerException(" Medical record of " + id + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			
			ResponseEntity<Long> ResponseEntityNoValid =new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_DELETE_MEDICALRECORD, ResponseEntityNoValid);
			return ResponseEntityNoValid;
		}
		
		ResponseEntity<Long> ResponseEntityValid =  new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		log.info(ConstantsRequestResponseHttp.RESPONSE_DELETE_MEDICALRECORD, ResponseEntityValid);
		return ResponseEntityValid;
	}

	@Override
	public ResponseEntity<MedicalRecord> returnResponseEntityEmptyAndCode404() {
		return new ResponseEntity<MedicalRecord>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<MedicalRecord> returnResponseEntityEmptyAndCode400() {
		return new ResponseEntity<MedicalRecord>(HttpStatus.BAD_REQUEST);
	}
}
