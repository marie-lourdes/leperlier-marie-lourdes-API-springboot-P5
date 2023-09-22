package com.safetynet.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.FireStation;

public interface IFireStationRepository  extends CrudRepository<FireStation, Long>{
   
	 	
}
