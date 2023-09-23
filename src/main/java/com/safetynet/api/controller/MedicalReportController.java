package com.safetynet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalReport;
import com.safetynet.api.service.dataservice.MedicalReportService;

import jakarta.validation.Valid;

@RestController
public class MedicalReportController {

	@Autowired
	MedicalReportService medicalReportService;

	@PostMapping("/medicalReport")
	public MedicalReport createMedicalReport(@Valid @RequestBody MedicalReport medicalReportCreated) throws Exception {
		MedicalReport medicalReport = new MedicalReport();
		medicalReport.setFirstName(medicalReportCreated.getFirstName());
		medicalReport.setLastName(medicalReportCreated.getLastName());
		medicalReport.setBirthdate(medicalReportCreated.getBirthdate());
		// medicalReport.setMedications(medicalReportCreated.getMedications());
		// medicalReport.setAllergies(medicalReportCreated.getAllergies());
		return medicalReportService.saveMedicalReport(medicalReport);
	}

	@GetMapping("/medicalReport")
	public @ResponseBody List<MedicalReport> getAllMedicalReports() {
		List<MedicalReport> allMedicalReport = null;
		try {
			allMedicalReport = medicalReportService.getAllMedicalReports();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allMedicalReport;
	}
}
