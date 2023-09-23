package com.safetynet.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

}
