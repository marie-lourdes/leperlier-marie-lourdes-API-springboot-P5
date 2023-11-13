package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import com.safetynet.api.model.FireStation;

public interface IFireStationRepository extends IReadOnlyRepository<FireStation, String> {
	@Override
	List<FireStation> findAll() throws IOException;
}
