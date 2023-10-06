package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.safetynet.api.model.FireStation;

public interface IFireStationREADONLYRepository extends IReadOnlyDatasRepository<FireStation,String> {
	@Override
	List<FireStation> findAll() throws IOException;
	 List<Optional<FireStation>> findById(String id) ;
	 List<Optional<FireStation>>  findByAddress(String Address);
	

}
