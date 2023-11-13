package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordRepository extends IReadOnlyRepository<MedicalRecord, String> {
	@Override
	List<MedicalRecord> findAll() throws IOException;
}
