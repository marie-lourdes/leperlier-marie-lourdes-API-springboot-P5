package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.IFireStationREADONLYRepository;

@Service
public class FireStationREADONLYRepositoryImpl implements IFireStationREADONLYRepository{
	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	public List<FireStation> getFireStationsFromFile() throws IOException {
		return  findAll();
	}
	@Override
	public List<FireStation> findAll() throws IOException{
		return readFireStations.readFile();
	}
}
