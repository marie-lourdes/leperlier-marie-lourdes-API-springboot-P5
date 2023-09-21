package com.safetynet.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.MedicalReport;

public interface IMedicalReportRepository extends CrudRepository<MedicalReport, Long> {

}
