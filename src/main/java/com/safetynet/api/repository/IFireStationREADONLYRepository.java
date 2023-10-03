package com.safetynet.api.repository;

import java.util.List;

import com.safetynet.api.model.FireStation;

public interface IFireStationREADONLYRepository extends IReadOnlyDatasRepository<FireStation,Long> {
	List<FireStation> findAll();
}
