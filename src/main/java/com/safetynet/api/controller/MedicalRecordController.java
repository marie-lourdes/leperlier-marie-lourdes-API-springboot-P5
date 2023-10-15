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
import com.safetynet.api.service.dataservice.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
public class MedicalRecordController {
	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping("/medicalRecord/")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord)
			throws Exception {
		MedicalRecord medicalRecordCreated = medicalRecordService.addMedicalRecord(medicalRecord);

		// medicalRecords.add(medicalRecord);
		System.out.println(medicalRecord);
		return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordCreated);
	}

	@PutMapping("/medicalRecord/{id}")
	public ResponseEntity<MedicalRecord> updateOneMedicalRecordById(@RequestBody MedicalRecord medicalRecord,
			@PathVariable String id) {
		MedicalRecord medicalRecordFoundById = medicalRecordService.updateMedicalRecord(id, medicalRecord);
		if (medicalRecordFoundById != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordFoundById);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MedicalRecord());
		}

	}
	
	@DeleteMapping("/medicalRecord/{id}")
	public ResponseEntity<Long> deleteOneMedicalRecordByName(@PathVariable String id) {
		boolean isMedicalRecordRemoved = medicalRecordService.deleteOneMedicalRecordById(id);
		return isMedicalRecordRemoved ? new ResponseEntity<Long>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/medicalRecord/{id}")
	public Optional<MedicalRecord> getOneMedicalRecord(@PathVariable String id) {
		return medicalRecordService.getOneMedicalRecordById(id);
	}
	
	@GetMapping("/medicalRecord/")
	public @ResponseBody List<MedicalRecord> getAllMedicalRecords()  {		
			return medicalRecordService.getAllMedicalRecords();		
	}

//----MedicalRecord  getbyFullName  from file json------
	/*
	 * @GetMapping("/medicalRecord") public List<Optional<MedicalRecord>>
	 * getOneMedicalRecordByFullName(@RequestParam String firstName, @RequestParam
	 * String lastName){ return
	 * medicalRecordService.getOneMedicalRecordByFullName(firstName,lastName); }
	 */
	// the id, first and last name cannot be modified
	/*
	 * @PutMapping("/medicalRecord/{id}") public
	 * ResponseEntity<Optional<MedicalRecord>>
	 * updateOneMedicalRecordById(@RequestBody MedicalRecord medicalRecord,
	 * 
	 * @PathVariable Long id) { Optional<MedicalRecord> medicalRecordFoundById =
	 * medicalRecordService.getOneMedicalRecordById(id);
	 * 
	 * if (id.toString().equals(medicalRecordFoundById.get().getId().toString())) {
	 * medicalRecordFoundById.get().setBirthdate(medicalRecord.getBirthdate());
	 * medicalRecordFoundById.get().setMedications(medicalRecord.getMedications());
	 * medicalRecordFoundById.get().setAllergies(medicalRecord.getAllergies());
	 * 
	 * medicalRecordService.saveMedicalRecord(medicalRecordFoundById.get());
	 * System.out.println(medicalRecordFoundById); } return
	 * ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordFoundById); }
	 */

	/*
	 * //List<MedicalRecord> medicalRecords =
	 * medicalRecordService.getAllMedicalRecords(); List<MedicalRecord>
	 * medicalRecords =new LinkedList<MedicalRecord>(); try { medicalRecords =
	 * medicalRecordService.getMedicalRecordsFromFile(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * medicalRecords.forEach(elem -> { String firstNameOfMedicalRecord =
	 * elem.getFirstName(); String lastNameOfMedicalRecord = elem.getLastName();
	 * 
	 * if (firstNameOfMedicalRecord.contains(firstName) &&
	 * lastNameOfMedicalRecord.contains(lastName)) {
	 * medicalRecordService.deleteOneMedicalRecordByName(elem); } });
	 * 
	 * return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	 * 
	 * }
	 */
}
