package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.MedicalReport;
import com.safetynet.api.service.dataservice.MedicalReportService;

import jakarta.validation.Valid;

@RestController
public class MedicalReportController {

	@Autowired 
	MedicalReportService medicalReportService;
	
	@PostMapping("/medicalReport")
	public MedicalReport createMedicalReport(@Valid @RequestBody MedicalReport medicalReportCreated) {
		MedicalReport medicalReport = new MedicalReport();
		medicalReport.setFirstName(medicalReportCreated.getFirstName());
		medicalReport.setLastName(medicalReportCreated.getLastName());
		medicalReport.setBirthdate(medicalReportCreated.getBirthdate());
		medicalReport.setMedications(medicalReportCreated.getMedications());
		medicalReport.setAllergies(medicalReportCreated.getAllergies());
		return medicalReportService.saveMedicalReport(medicalReport);
	}
}
