package com.safetynet.api.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
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

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.UploadMedicalRecordDataFileService;
import com.safetynet.api.service.dataservice.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController {

/*	@Autowired
	MedicalRecordService medicalRecordService;
	
	@Autowired
	private UploadDataFileService uploadDataFileService; 

	@PostMapping("/medicalRecords")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord)
			throws Exception {	
		medicalRecordService.saveMedicalRecord(medicalRecord);
		System.out.println(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
		// return medicalRecordService.saveMedicalRecord(medicalRecord);
	}

	//-----------------requete a partir du fichier json-------------
@GetMapping("/medicalRecords")
	public @ResponseBody List<MedicalRecord>  getAllMedicalRecordsFromFile() throws FileNotFoundException {
		List<MedicalRecord> medicalRecords = new LinkedList<MedicalRecord>();
		
		try {
			medicalRecords= uploadDataFileService.getMedicalRecordsFromFile();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return medicalRecords;
	}

//----------------requete a partir de la base de données--------------
	/*@GetMapping("/medicalRecords")
	public @ResponseBody List<MedicalRecord> getAllMedicalRecords() {
		List<MedicalRecord> allMedicalRecord = new ArrayList<MedicalRecord>();
		try {
			allMedicalRecord = medicalRecordService.getAllMedicalRecords();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allMedicalRecord;
	}*/

/*	@GetMapping("/medicalRecords/{id}")
	public Optional<MedicalRecord> getOneMedicalRecord(@PathVariable Long id) {
		return medicalRecordService.getOneMedicalRecordById(id);
	}

	// the id, first and last name cannot be modified
	@PutMapping("/medicalRecords/{id}")
	public ResponseEntity<Optional<MedicalRecord>> updateOneMedicalRecordById(@RequestBody MedicalRecord medicalRecord,
			@PathVariable Long id) {
		Optional<MedicalRecord> medicalRecordFoundById = medicalRecordService.getOneMedicalRecordById(id);

		if (id.toString().equals(medicalRecordFoundById.get().getId().toString())) {
			medicalRecordFoundById.get().setBirthdate(medicalRecord.getBirthdate());
			medicalRecordFoundById.get().setMedications(medicalRecord.getMedications());
			medicalRecordFoundById.get().setAllergies(medicalRecord.getAllergies());

			medicalRecordService.saveMedicalRecord(medicalRecordFoundById.get());
			System.out.println(medicalRecordFoundById);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordFoundById);
	}

	@DeleteMapping("/medicalRecords/")
	public ResponseEntity<Long> deleteOneMedicalRecordByName(@RequestParam String firstName,
			@RequestParam String lastName) {
		List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();

		medicalRecords.forEach(elem -> {
			String firstNameOfMedicalRecord = elem.getFirstName();
			String lastNameOfMedicalRecord = elem.getLastName();

			if (firstNameOfMedicalRecord.contains(firstName) && lastNameOfMedicalRecord.contains(lastName)) {
				medicalRecordService.deleteOneMedicalRecordByName(elem);
			}
		});

		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);

	}*/
}
