package com.safetynet.api.service.dataservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.IMedicalRecordRepository;

@Service
public class MedicalRecordService {
	@Autowired
    private IMedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return (List<MedicalRecord>) medicalRecordRepository.findAll();
    }

    public  Optional<MedicalRecord> getOneMedicalRecordById(Long id) {
        return  medicalRecordRepository.findById(id);
    }
    
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
       return medicalRecordRepository.save(medicalRecord);       
    }
    
    public  MedicalRecord updateOneMedicalRecordById(MedicalRecord medicalRecord, Long id) {	   
        return medicalRecordRepository.save(medicalRecord);       
     }
    
    public void deleteOneMedicalRecordById( MedicalRecord medicalRecord ){ 		
        medicalRecordRepository.delete(medicalRecord);
     }
}
