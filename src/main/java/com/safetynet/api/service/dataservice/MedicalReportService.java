package com.safetynet.api.service.dataservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.MedicalReport;
import com.safetynet.api.repository.IMedicalReportRepository;

public class MedicalReportService {
	@Autowired
    private IMedicalReportRepository medicalReportRepository;

    public List<MedicalReport> getAllMedicalReports() {
        return (List<MedicalReport>) medicalReportRepository.findAll();
    }

    public  Optional<MedicalReport> getOneMedicalReportById(Long id) {
        return  medicalReportRepository.findById(id);
    }
    
    public MedicalReport saveOneMedicalReport(MedicalReport medicalReport) {
       return medicalReportRepository.save(medicalReport);       
    }
    
    public  MedicalReport updateOneMedicalReportById(MedicalReport medicalReport, Long id) {	   
        return medicalReportRepository.save(medicalReport);       
     }
    
    public void deleteOnePersonById( MedicalReport medicalReport ){ 		
        medicalReportRepository.delete(medicalReport);
     }
}
