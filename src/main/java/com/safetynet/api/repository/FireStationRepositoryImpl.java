package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.ReadFireStationDataFromFileImpl;

@Component
public class FireStationRepositoryImpl implements IFireStationRepository {
	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	@Override
	public List<FireStation> findAll() throws IOException {
		return readFireStations.readFile();
	}

}

