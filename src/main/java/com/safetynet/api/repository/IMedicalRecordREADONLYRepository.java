package com.safetynet.api.repository;

import java.util.List;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordREADONLYRepository extends IReadOnlyDatasRepository<MedicalRecord,Long> {
	List<MedicalRecord> findAll();		
}
