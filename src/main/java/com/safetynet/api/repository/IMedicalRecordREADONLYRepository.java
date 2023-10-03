package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordREADONLYRepository extends IReadOnlyDatasRepository<MedicalRecord,Long> {
	@Override
	List<MedicalRecord> findAll() throws IOException;		
}
