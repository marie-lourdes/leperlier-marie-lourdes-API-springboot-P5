package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.ReadFireStationDataFromFileImpl;

@Component
public class FireStationRepositoryImpl implements IFireStationRepository {
	private static final Logger log = LogManager.getLogger(FireStationRepositoryImpl.class);
	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	@Override
	public List<FireStation> findAll() throws IOException {
		log.debug("Reading datas firestations  from file");
		return readFireStations.readFile();
	}

}
